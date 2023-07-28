package com.skshieldus.esecurity.service.entmanage.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.RequestWrapModel;
import com.skshieldus.esecurity.model.common.ApprovalDTO;
import com.skshieldus.esecurity.model.common.ApprovalDocDTO;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.repository.entmanage.ConstProjectRegRepository;
import com.skshieldus.esecurity.service.common.ApprovalService;
import com.skshieldus.esecurity.service.entmanage.ConstProjectRegService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
public class ConstProjectRegServiceImpl implements ConstProjectRegService {

    @Autowired
    private ConstProjectRegRepository constProjectRegRepository;

    @Autowired
    private ApprovalService approvalService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<Map<String, Object>> selectConstProjectRegList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            // 목록 조회
            resultList = constProjectRegRepository.selectConstProjectRegList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        return resultList;
    }

    @Override
    public Map<String, Object> selectConstProjectRegView(Map<String, Object> paramMap) {

        Map<String, Object> result = new HashMap<String, Object>();

        try {
            // 목록 조회
            Map<String, Object> constProjectRegView = constProjectRegRepository.selectConstProjectRegView(paramMap);
            List<Map<String, Object>> constProjectRegGateList = constProjectRegRepository
                .selectConstProjectRegGateList(paramMap);
            result.put("constProjectRegView", constProjectRegView);
            result.put("constProjectRegGateList", constProjectRegGateList);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        return result;
    }

    @Override
    public boolean insertConstProjectReg(SessionInfoVO sessionInfo, Map<String, Object> paramMap) {

        boolean result = false;
        int insertConstProjectRegInsertResult = 0;
        int insertConstProjectRegUpdateResult = 0;
        int constPrjNo = 0;

        try {

            Map<String, String> userInfo = objectMapper.convertValue(paramMap.get("userInfo"),
                new TypeReference<Map<String, String>>() {
                });

            ApprovalDTO approval = objectMapper.convertValue(paramMap.get("approval"), ApprovalDTO.class);
            int requestLineLen = approval.getSavedRequestApproverLine().size();
            int permitLineLen = approval.getSavedPermitApproverLine().size();

            /** 기본 정보 설정 시작 */
            // 결재 업무 번호 채번
            constPrjNo = constProjectRegRepository.selectConstProjectRegNewSeq(paramMap);
            String empId = userInfo.getOrDefault("empId", "");
            String constOrderNo = String.valueOf(paramMap.get("constOrderNo"));

            log.info(">>>> constPrjNo : {}", constPrjNo);
            /** 기본 정보 설정 종료 */

            // 공사프로젝트 등록
            paramMap.put("constPrjNo", constPrjNo);
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

            insertConstProjectRegInsertResult = constProjectRegRepository.insertConstProjectReg(paramMap);

            if (insertConstProjectRegInsertResult != 1) {
                throw new Exception("Failed to execute constProjectRegRepository.insertConstProjectReg");
            }

            log.info(">>>> 공사프로젝트 등록 성공");

            // 공사프로젝트 출입건물 등록
            List<Map<String, Object>> newBuildList = objectMapper.convertValue(paramMap.get("newBuildList"),
                new TypeReference<List<Map<String, Object>>>() {
                });
            log.info(">>>> newBuildList : {}", newBuildList);

            int constProjectRegGateInsertResult = 0;

            if (newBuildList.size() > 0) {
                for (Map<String, Object> reqstBuild : newBuildList) {

                    // 결재 업무 번호 채번
                    reqstBuild.put("constPrjNo", constPrjNo);
                    reqstBuild.put("constOrderNo", constOrderNo);
                    reqstBuild.put("acIp", (sessionInfo.getIp()));
                    reqstBuild.put("crtBy", (empId));

                    constProjectRegRepository.insertConstProjectRegGate(reqstBuild);
                    constProjectRegGateInsertResult++;
                }

                if (constProjectRegGateInsertResult > 0) {
                    log.info(">>>> 공사프로젝트 출입지역 등록 성공");
                }
                else {
                    throw new Exception("Failed to execute constProjectRegRepository.insertConstProjectRegGate");
                }
            }

            if (permitLineLen == 0 && requestLineLen == 0) { // 자가결재 => 업무 DB업데이트 후 결재 프로세스 종료

                insertConstProjectRegUpdateResult = constProjectRegRepository.updateConstProjectRegApplWork(paramMap);
                if (insertConstProjectRegUpdateResult == 1) {
                    log.info(">>>> 공사프로젝트 등록 자가결재 업데이트 성공");
                    result = true;
                }
            }
            else {
                // 결재 라인 등록
                // SAVE_TYPE
                // SAVE : 임시보관
                // REPORT : 상신
                // ****************** 결재 관련 정보 설정 시작 ******************
                /*
                 * ================= NOTE: [통합결재정보] 저장 시작 =======================
                 */
                /*
                 * NOTE: [통합결재정보] 저장
                 */

                approval.setLid(constPrjNo);
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

                insertConstProjectRegUpdateResult = constProjectRegRepository.updateConstProjectRegDocId(paramMap);
                if (insertConstProjectRegUpdateResult == 1) {
                    log.info(">>>> 공사프로젝트 등록 결재정보 업데이트 성공");
                    result = true;
                }
            }
        } catch (Exception e) {
            result = false;
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        return result;
    }

}