package com.skshieldus.esecurity.service.entmanage.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skshieldus.esecurity.common.component.Mailing;
import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.RequestWrapModel;
import com.skshieldus.esecurity.model.common.ApprovalDTO;
import com.skshieldus.esecurity.model.common.ApprovalDocDTO;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.repository.entmanage.ConstPassProvRepository;
import com.skshieldus.esecurity.repository.entmanage.idcard.IdcardRepository;
import com.skshieldus.esecurity.service.common.ApprovalService;
import com.skshieldus.esecurity.service.entmanage.ConstPassProvService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
public class ConstPassProvServiceImpl implements ConstPassProvService {

    @Autowired
    private ApprovalService approvalService;

    @Autowired
    private Environment environment;

    @Autowired
    private ConstPassProvRepository constPassProvRepository;

    @Autowired
    private IdcardRepository idcardRepository;

    @Autowired
    private Mailing mailing;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<Map<String, Object>> selectConstPassProvList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            // 목록 조회
            resultList = constPassProvRepository.selectConstPassProvList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Map<String, Object> selectConstPassProvView(Map<String, Object> paramMap) {

        Map<String, Object> result = new HashMap<String, Object>();

        try {
            // 목록 조회
            result = constPassProvRepository.selectConstPassProvView(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> selectConstPassMngList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            // 목록 조회
            resultList = constPassProvRepository.selectConstPassMngList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return resultList;
    }

    @Override
    public List<Map<String, Object>> selectConstPassMngCardList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            // 목록 조회
            resultList = constPassProvRepository.selectConstPassMngCardList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return resultList;
    }

    @Override
    public boolean insertConstPassProv(SessionInfoVO sessionInfo, Map<String, Object> paramMap) {
        boolean result = false;
        int insertConstPassProvUpdateResult = 0;
        int constPassNo = 0;

        try {

            Map<String, String> userInfo = objectMapper.convertValue(paramMap.get("userInfo"), new TypeReference<Map<String, String>>() { });

            ApprovalDTO approval = objectMapper.convertValue(paramMap.get("approval"), ApprovalDTO.class);
            Integer docId = null;
            int requestLineLen = approval.getSavedRequestApproverLine().size();
            int permitLineLen = approval.getSavedPermitApproverLine().size();

            /** 기본 정보 설정 시작 */
            // 결재 업무 번호 채번
            constPassNo = objectMapper.convertValue(paramMap.get("constPassNo"), Integer.class);
            String empId = userInfo.getOrDefault("empId", "");

            log.info(">>>> constPassNo : {}", constPassNo);
            /** 기본 정보 설정 종료 */

            // 공사출입증 지급신청
            // 자가결재인 경우
            if (requestLineLen == 0 && permitLineLen == 0) {
                paramMap.put("applStat", "Z0331005"); // Z0331005 : 결재완료
            }
            else {
                paramMap.put("applStat", "Z0331002"); // Z0331002 : 접수
            }
            paramMap.put("acIp", sessionInfo.getIp());
            paramMap.put("crtBy", empId);
            paramMap.put("modBy", empId);
            log.info(">>>> paramMap : {}", paramMap);

            if (permitLineLen == 0 && requestLineLen == 0) { // 자가결재 => 업무 DB업데이트 후 결재 프로세스 종료

                insertConstPassProvUpdateResult = constPassProvRepository.updateConstPassProvApplWork(paramMap);
                if (insertConstPassProvUpdateResult == 1) {
                    log.info(">>>> 공사출입증 지급신청 자가결재 업데이트 성공");
                    result = true;
                }

                this.sendMailSelfApproval(sessionInfo, paramMap, empId);
                this.insertIDCardIfHeifOtherInfo(constPassNo);
            }
            else {
                // 결재 라인 등록
                // SAVE_TYPE
                // SAVE : 임시보관
                // REPORT : 상신
                // ****************** 결재 관련 정보 설정 시작 ******************
                /*
                 * ================= NOTE: [통합결재정보] 저장 시작 ======================= NOTE: [통합결재정보]
                 * 저장
                 */

                approval.setLid(constPassNo);
                approval.setSchemaNm(String.valueOf(paramMap.get("schemaNm")));

                // 결재 html content 정보 설정
                Map<String, Object> htmlMap = objectMapper.convertValue(paramMap.get("htmlMap"), new TypeReference<Map<String, Object>>() { });

                log.info(">>>> htmlMap : {}", htmlMap);
                approval.setHtmlMap(htmlMap);

                RequestWrapModel<ApprovalDTO> wrapParams = new RequestWrapModel<>();
                wrapParams.setParams(approval);

                log.info(">>>> RequestWrapModel<ApprovalDTO> wrapParams : {}", wrapParams);

                ApprovalDocDTO approvalDoc = approvalService.insertApproval(approval);
                docId = approvalDoc.getDocId();
                /*
                 * ================= NOTE: [통합결재정보] 저장 종료 =======================
                 */

                // 결재 정보 업데이트
                paramMap.put("docId", docId);
                paramMap.put("modBy", empId);
                log.info(">>>> APPROVAL_DOC > docId : {}", docId);
                log.info(">>>> APPROVAL_DOC > modBy : {}", empId);

                insertConstPassProvUpdateResult = constPassProvRepository.updateConstPassProvDocId(paramMap);
                if (insertConstPassProvUpdateResult == 1) {
                    log.info(">>>> 공사출입증 지급신청 결재정보 업데이트 성공");
                    result = true;
                }
            }

            // ========== 접수 메일 발송 : 신청한 외부회원에게 메일 발송 ================
            // ================= NOTE: 메일 발송 시작 =======================
            String title = "[행복한 만남, SK 하이닉스]신청하신 건이 접수되었습니다.";
            String ioEmpNm = objectMapper.convertValue(paramMap.get("ioEmpNm"), String.class);
            String to = objectMapper.convertValue(paramMap.get("ioEmailAddr"), String.class);
            String schemaNm = objectMapper.convertValue(paramMap.get("schemaNm"), String.class);
            String acIp = objectMapper.convertValue(sessionInfo.getIp(), String.class);

            mailing.sendMail(title, mailing.applyMailTemplate(title, ioEmpNm + "님께서 신청하신 건(공사출입증 지급신청)이 접수되었습니다."), to, empId, schemaNm, docId == null
                ? "자가결재"
                : String.valueOf(docId), acIp);
            // ================= NOTE: 메일 발송 종료 =======================

        } catch (Exception e) {
            result = false;
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    public void sendMailSelfApproval(SessionInfoVO sessionInfo, Map<String, Object> paramMap, String empId) {

        try {
            // ========== 접수 메일 발송 : 신청한 자가결재자에게 메일 발송 ================
            // ================= NOTE: 메일 발송 시작 =======================
            String title = "[e-Security] 공사출입증 지급신청";
            String ioEmpNm = objectMapper.convertValue(paramMap.get("ioEmpNm"), String.class);
            String to = objectMapper.convertValue(paramMap.get("email"), String.class);
            String schemaNm = objectMapper.convertValue(paramMap.get("schemaNm"), String.class);
            String acIp = objectMapper.convertValue(sessionInfo.getIp(), String.class);

            mailing.sendMail(title, mailing.applyMailTemplate(title, ioEmpNm + "님께서 공사출입증 지급을 신청하셨습니다."), to, empId, schemaNm, "자가결재", acIp);
            // ================= NOTE: 메일 발송 종료 =======================

        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
    }

    @Override
    public boolean insertIDCardIfHeifOtherInfo(Integer constPassNo) {

        Map<String, Object> reqParam = new HashMap<String, Object>();
        boolean isProd = environment.acceptsProfiles(Profiles.of("prd")); // 운영환경 여부
        boolean result = false;
        int insertIfIdcardOtherLogInsertResult = 0;

        try {
            // 공사출입증 발급신청건수
            int issueCount = constPassProvRepository.selectConstPassApplCnt(constPassNo);
            reqParam.put("constPassNo", constPassNo);

            for (int seq = 1; seq <= issueCount; seq++) {
                reqParam.put("seq", seq);

                // Insert IO_CONST_PASS_CARD with SEQ number
                constPassProvRepository.insertConstPassCard(reqParam);

                // Select IO_CONST_PASS_VIEW for IDCARD IF
                Map<String, Object> ifIdCardParam = constPassProvRepository.selectConstPassCardViewForIdCardIF(reqParam);

                // IF_IDCARD Insert
                constPassProvRepository.insertIfIdcardOtherLogInsert(ifIdCardParam);
                insertIfIdcardOtherLogInsertResult++;

                log.debug(">>>> insertIfIdcardOtherLogInsert > ES_SN : {}", ifIdCardParam.get("esSn"));

                if (isProd) {
                    // HEIF_OTHER_INFO(기타출입증 신청) Insert
                    idcardRepository.insertIDCardIfHeifOtherInfo(ifIdCardParam);
                    log.debug(">>>> insertIDCardIfHeifOtherInfo > ES_SN : {}", ifIdCardParam.get("esSn"));
                }
            }

            log.info(">>>> insertIDCardIfHeifOtherInfo : {}건 등록완료", insertIfIdcardOtherLogInsertResult);
            result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public boolean rejectConstPassProv(SessionInfoVO sessionInfo, Map<String, Object> paramMap) {
        boolean result = false;

        try {

            constPassProvRepository.rejectConstPassProv(paramMap);
            log.info(">>>> rejectConstPassProv : 공사출입증 지급신청 담당자 접수반려 완료");
            result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

}