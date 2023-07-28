package com.skshieldus.esecurity.service.entmanage.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.RequestWrapModel;
import com.skshieldus.esecurity.model.common.ApprovalDTO;
import com.skshieldus.esecurity.model.common.ApprovalDocDTO;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.repository.entmanage.SafetyCarPassRepository;
import com.skshieldus.esecurity.service.common.ApprovalService;
import com.skshieldus.esecurity.service.entmanage.SafetyCarPassService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class SafetyCarPassServiceImpl implements SafetyCarPassService {

    @Autowired
    private SafetyCarPassRepository safetyCarPassRepository;

    @Autowired
    private ApprovalService approvalService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Map<String, Object> selectSafetyCarPassList(Map<String, Object> paramMap) {

        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>> resultList;
        int totalCount;

        try {
            // 목록 조회
            resultList = safetyCarPassRepository.selectSafetyCarPassList(paramMap);
            totalCount = safetyCarPassRepository.selectSafetyCarPassListCnt(paramMap);

            resultMap.put("list", resultList);
            resultMap.put("totalCount", totalCount);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultMap;
    }

    @Override
    public Map<String, Object> selectSafetyCarPassView(Map<String, Object> paramMap) {

        Map<String, Object> resultMap = new HashMap<>();

        try {
            // 목록 조회
            Map<String, Object> safetyCarPassView = safetyCarPassRepository.selectSafetyCarPassView(paramMap);
            List<Map<String, Object>> safetyCarPassUserList = safetyCarPassRepository.selectSafetyCarPassUserList(paramMap);

            resultMap.put("safetyCarPassView", safetyCarPassView);
            resultMap.put("safetyCarPassUserList", safetyCarPassUserList);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultMap;
    }

    @Override
    public boolean insertSafetyCarPassRequest(SessionInfoVO sessionInfoVO, Map<String, Object> paramMap) {
        boolean result = false;
        int insertSafetyCarPassInsertResult = 0;
        int insertSafetyCarPassUpdateResult = 0;
        int tmpcarAppNo = 0;

        try {

            Map<String, String> userInfo = objectMapper.convertValue(paramMap.get("userInfo"),
                new TypeReference<Map<String, String>>() {
                });

            /** 기본 정보 설정 시작 */
            // 결재 업무 번호 채번
            tmpcarAppNo = safetyCarPassRepository.selectSafetyCarPassReqstNewSeq(paramMap);
            String empId = userInfo.getOrDefault("empId", "");

            log.info(">>>> tmpcarAppNo : {}", tmpcarAppNo);
            /** 기본 정보 설정 종료 */

            // 안전작업차량 출입 신청 등록
            paramMap.put("tmpcarAppNo", tmpcarAppNo);
            paramMap.put("acIp", userInfo.get("acIp"));
            paramMap.put("crtBy", empId);
            log.info(">>>> paramMap : {}", paramMap);

            insertSafetyCarPassInsertResult = safetyCarPassRepository.insertSafetyCarPassRequest(paramMap);

            if (insertSafetyCarPassInsertResult != 1) {
                throw new Exception("Failed to execute safetyCarPassRepository.insertSafetyCarPassRequest");
            }

            log.info(">>>> 안전작업차량 출입 신청 등록 성공");

            // 안전작업차량 출입신청 출입자 LIST 등록
            List<Map<String, Object>> reqstList = objectMapper.convertValue(paramMap.get("reqstList"),
                new TypeReference<List<Map<String, Object>>>() {
                });
            log.info(">>>> reqstList : {}", reqstList);

            int safetyCarPassUserInsertResult = 0;
            List<String> ioDtArr = objectMapper.convertValue(paramMap.get("ioDt"), new TypeReference<List<String>>() {
            });
            for (Map<String, Object> reqstUser : reqstList) {

                for (String ioDt : ioDtArr) {
                    // 결재 업무 번호 채번
                    reqstUser.put("tmpcarAppNo", tmpcarAppNo);
                    reqstUser.put("ioDt", ioDt);
                    reqstUser.put("acIp", (userInfo.get("acIp")));
                    reqstUser.put("crtBy", (empId));
                    reqstUser.put("modBy", (empId));

                    safetyCarPassRepository.insertSafetyCarPassRequestUser(reqstUser);
                    safetyCarPassUserInsertResult++;
                }

//                safetyCarPassRepository.updateIoEmpCarInfo(reqstUser);
            }

            if (safetyCarPassUserInsertResult > 0) {
                log.info(">>>> 안전작업차량 출입신청 출입자 정보 등록 성공");
            }
            else {
                throw new Exception("Failed to execute safetyCarPassRepository.insertSafetyCarPassUser");
            }

            // 결재 라인 등록
            // SAVE_TYPE
            // SAVE : 임시보관
            // REPORT : 상신
            // ****************** 결재 관련 정보 설정 시작 ******************
            ApprovalDTO approval = objectMapper.convertValue(paramMap.get("approval"), ApprovalDTO.class);
            approval.setLid(tmpcarAppNo);
            approval.setSchemaNm(String.valueOf(paramMap.get("schemaNm")));

            // 결재 html content 정보 설정
            Map<String, Object> htmlMap = objectMapper.convertValue(paramMap.get("htmlMap"),
                new TypeReference<Map<String, Object>>() {
                });

            log.info(">>>> htmlMap : {}", htmlMap);
            approval.setHtmlMap(htmlMap);

            RequestWrapModel<ApprovalDTO> wrapParams = new RequestWrapModel<>();
            wrapParams.setParams(approval);

            log.info(">>>> RequestWrapModel<ApprovalDTO> wrapParams : {}", wrapParams);

            ApprovalDocDTO approvalDoc = approvalService.insertApproval(approval);
            /*
             * ================= NOTE: [통합결재정보] 저장 종료 =======================
             */

            // 결재 정보 업데이트
            paramMap.put("docId", approvalDoc.getDocId());
            paramMap.put("modBy", empId);
            log.info(">>>> APPROVAL_DOC > docId : {}", approvalDoc.getDocId());
            log.info(">>>> APPROVAL_DOC > modBy : {}", empId);

            insertSafetyCarPassUpdateResult = safetyCarPassRepository.updateSafetyCarPassDocId(paramMap);
            if (insertSafetyCarPassUpdateResult == 1) {
                log.info(">>>> 안전작업차량 출입 신청 결재정보 업데이트 성공");
                result = true;
            }
        } catch (Exception e) {
            result = false;
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return result;
    }

}