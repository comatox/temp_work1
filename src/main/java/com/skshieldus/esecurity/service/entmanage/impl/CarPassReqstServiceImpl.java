package com.skshieldus.esecurity.service.entmanage.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.RequestWrapModel;
import com.skshieldus.esecurity.model.common.ApprovalDTO;
import com.skshieldus.esecurity.model.common.ApprovalDocDTO;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.repository.entmanage.CarPassReqstRepository;
import com.skshieldus.esecurity.service.common.ApprovalService;
import com.skshieldus.esecurity.service.entmanage.CarPassReqstService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
public class CarPassReqstServiceImpl implements CarPassReqstService {

    @Autowired
    private ApprovalService approvalService;

    @Autowired
    private CarPassReqstRepository carPassReqstRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<Map<String, Object>> selectCarPassReqstList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            // 목록 조회
            resultList = carPassReqstRepository.selectCarPassReqstList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return resultList;
    }

    @Override
    public Map<String, Object> selectCarPassReqstView(Map<String, Object> paramMap) {

        Map<String, Object> result = new HashMap<>();

        try {
            // 목록 조회
            Map<String, Object> carPassReqstView = carPassReqstRepository.selectCarPassReqstView(paramMap);
            List<Map<String, Object>> carPassReqstUserList = carPassReqstRepository.selectCarPassReqstUserList(paramMap);
            result.put("carPassReqstView", carPassReqstView);
            result.put("carPassReqstUserList", carPassReqstUserList);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> selectCarPassQuotaCheck(Map<String, Object> paramMap) {

        List<Map<String, Object>> result = new ArrayList<>();
        List<String> ioDtArr = objectMapper.convertValue(paramMap.get("ioDt"), new TypeReference<List<String>>() {
        });
        Map<String, Object> searchParam = new HashMap<String, Object>();
        String deptId = String.valueOf(paramMap.get("deptId"));

        try {
            // 목록 조회
            for (String ioDt : ioDtArr) {
                searchParam.put("ioDt", ioDt);
                searchParam.put("deptId", deptId);

                Map<String, Object> carPassQuota = carPassReqstRepository.selectCarPassQuotaCheck(searchParam);

                if (carPassQuota != null) {
                    result.add(carPassQuota);
                }
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return result;
    }

    @Override
    public boolean insertCarPassReqst(SessionInfoVO sessionInfo, Map<String, Object> paramMap) {

        boolean result = false;
        int insertCarPassReqstInsertResult = 0;
        int insertCarPassReqstUpdateResult = 0;
        int vstcarApplNo = 0;

        try {

            Map<String, String> userInfo = objectMapper.convertValue(paramMap.get("userInfo"),
                new TypeReference<Map<String, String>>() {
                });

            /** 기본 정보 설정 시작 */
            // 결재 업무 번호 채번
            vstcarApplNo = carPassReqstRepository.selectCarPassReqstNewSeq(paramMap);
            String empId = userInfo.getOrDefault("empId", "");

            log.info(">>>> vstcarApplNo : {}", vstcarApplNo);
            /** 기본 정보 설정 종료 */

            // 방문차량출입 신청 등록
            paramMap.put("vstcarApplNo", vstcarApplNo);
            paramMap.put("acIp", sessionInfo.getIp());
            paramMap.put("crtBy", empId);
            log.info(">>>> paramMap : {}", paramMap);

            insertCarPassReqstInsertResult = carPassReqstRepository.insertCarPassReqst(paramMap);

            if (insertCarPassReqstInsertResult != 1) {
                throw new Exception("Failed to execute carPassReqstRepository.insertCarPassReqst");
            }

            log.info(">>>> 방문차량출입  신청 등록 성공");

            // 방문차량출입신청 출입자 LIST 등록
            List<Map<String, Object>> reqstList = objectMapper.convertValue(paramMap.get("reqstList"),
                new TypeReference<List<Map<String, Object>>>() {
                });
            log.info(">>>> reqstList : {}", reqstList);

            int carPassReqstUserInsertResult = 0;
            List<String> ioDtArr = objectMapper.convertValue(paramMap.get("ioDt"), new TypeReference<List<String>>() {
            });
            for (Map<String, Object> reqstUser : reqstList) {

                for (String ioDt : ioDtArr) {
                    // 결재 업무 번호 채번
                    reqstUser.put("vstcarApplNo", vstcarApplNo);
                    reqstUser.put("ioDt", ioDt);
                    reqstUser.put("acIp", (sessionInfo.getIp()));
                    reqstUser.put("crtBy", (empId));
                    reqstUser.put("modBy", (empId));

                    carPassReqstRepository.insertCarPassReqstUser(reqstUser);
                    carPassReqstUserInsertResult++;
                }

                carPassReqstRepository.updateIoEmpCarInfo(reqstUser);
            }

            if (carPassReqstUserInsertResult > 0) {
                log.info(">>>> 방문차량출입신청 출입자 정보 등록 성공");
            }
            else {
                throw new Exception("Failed to execute carPassReqstRepository.insertCarPassReqstUser");
            }

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
            ApprovalDTO approval = objectMapper.convertValue(paramMap.get("approval"), ApprovalDTO.class);
            approval.setLid(vstcarApplNo);
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

            insertCarPassReqstUpdateResult = carPassReqstRepository.updateCarPassReqstDocId(paramMap);
            if (insertCarPassReqstUpdateResult == 1) {
                log.info(">>>> 방문차량출입  신청 결재정보 업데이트 성공");
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