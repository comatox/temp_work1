package com.skshieldus.esecurity.service.secrtactvy.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.model.common.ApprovalDTO;
import com.skshieldus.esecurity.model.common.ApprovalDocDTO;
import com.skshieldus.esecurity.repository.secrtactvy.SecurityNationalCoreTechRepository;
import com.skshieldus.esecurity.service.common.ApprovalService;
import com.skshieldus.esecurity.service.secrtactvy.SecurityNationalCoreTechService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
public class SecurityNationalCoreTechServiceImpl implements SecurityNationalCoreTechService {

    @Autowired
    private ApprovalService approvalService;

    @Autowired
    private SecurityNationalCoreTechRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<Map<String, Object>> selectSecurityNationalCoreTechChecklist(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectSecurityNationalCoreTechChecklist(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Map<String, Object> selectSecurityNationalCoreTechChecklistView(Map<String, Object> paramMap) {
        Map<String, Object> result = null;

        try {
            result = repository.selectSecurityNationalCoreTechChecklistView(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean insertNationalCoreTechChecklist(Map<String, Object> paramMap) {
        boolean result = false;

        try {
            // ================= NOTE: [통합결재정보] 저장 시작 =======================
            //			ApprovalDTO approval = objectMapper.convertValue(paramMap.get("approval"), ApprovalDTO.class);
            //			Map<String, Object> htmlMap = objectMapper.convertValue(paramMap.get("htmlMap"), HashMap.class);
            //			int nctChecklistNo = objectMapper.convertValue(paramMap.get("nctChecklistNo"), Integer.class);
            //
            //			approval.setLid(nctChecklistNo);
            //			approval.setHtmlMap(htmlMap);
            //			log.info(">>>> insertNationalCoreTechChecklist approval setLid: " + approval);
            //
            //			RequestWrapModel<ApprovalDTO> wrapParams = new RequestWrapModel<>();
            //			wrapParams.setParams(approval);
            //    		ResponseModel<ApprovalDocDTO> resultApprovalDoc = commonApiClient.insertApproval(wrapParams);
            //    		ApprovalDocDTO approvalDoc = resultApprovalDoc.getData();
            //    		Integer docId = approvalDoc.getDocId();
            //    		paramMap.put("docId", docId);
            // ================= NOTE: [통합결재정보] 저장 종료 =======================

            if (repository.insertNationalCoreTechChecklist(paramMap) != 1) {
                result = false;
                throw new Exception("Failed to execute insertNationalCoreTechChecklist");
            }
            else {
                result = true;
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> selectSecurityNationalCoreTechList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectSecurityNationalCoreTechList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Map<String, Object> selectSecurityNationalCoreTechView(Map<String, Object> paramMap) {
        Map<String, Object> result = null;

        try {
            result = repository.selectSecurityNationalCoreTechView(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean insertNationalCoreTech(Map<String, Object> paramMap) {
        boolean result = false;
        log.info(">>>> insertNationalCoreTech  paramMap: " + paramMap);
        try {

            ApprovalDTO approval = objectMapper.convertValue(paramMap.get("approval"), ApprovalDTO.class);
            int requestLineLen = approval.getSavedRequestApproverLine().size();
            int permitLineLen = approval.getSavedPermitApproverLine().size();
            // 자가결재인 경우
            if (requestLineLen == 0 && permitLineLen == 0) {
                paramMap.put("applStat", "Z0331005"); //Z0331005 : 결재완료
            }
            else {
                paramMap.put("applStat", "Z0331002"); //Z0331002 : 접수
            }

            //
            if (repository.insertNationalCoreTech(paramMap) != 1) {
                result = false;
            }
            // ================= NOTE: [통합결재정보] 저장 시작 =======================

            //			Map<String, Object> htmlMap = objectMapper.convertValue(paramMap.get("htmlMap"), HashMap.class);

            approval.setLid((Integer) paramMap.get("nationalCoreTechNo"));
            //			approval.setHtmlMap(htmlMap);
            log.info(">>>> insertNationalCoreTech approval setLid: " + approval);

            ApprovalDocDTO approvalDoc = approvalService.insertApproval(approval);
            Integer docId = approvalDoc.getDocId();
            paramMap.put("docId", docId);
            // ================= NOTE: [통합결재정보] 저장 종료 =======================
            //    		log.info(">>>> insertNationalCoreTech paramMap : " + paramMap);

            if (repository.updateNationalCoreTechDocId(paramMap) != 1) {
                result = false;
            }

            result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> selectLastChecklist(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectLastChecklist(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public boolean updateCodeInfo(Map<String, Object> paramMap) {
        boolean result = false;

        try {
            log.info(">>>> updateCodeInfo paramMap : " + paramMap);
            List<Map<String, Object>> dataList = (List<Map<String, Object>>) paramMap.get("dataList");

            if (dataList != null && dataList.size() > 0) {
                for (int i = 0; i < dataList.size(); i++) {
                    Map<String, Object> dataMap = dataList.get(i);

                    dataMap.put("empId", paramMap.get("empId"));
                    dataMap.put("acIp", paramMap.get("acIp"));

                    // 세부코드 등록/수정
                    int resultCnt = repository.updateCodeInfo(dataMap);

                    if (resultCnt < 1) {
                        throw new Exception("수정 시 오류가 발생하였습니다.");
                    }
                }
                result = true;
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

}
