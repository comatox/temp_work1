package com.skshieldus.esecurity.service.secrtactvy.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.model.common.ApprovalDTO;
import com.skshieldus.esecurity.model.common.ApprovalDocDTO;
import com.skshieldus.esecurity.repository.secrtactvy.SecurityRectifyPlanRepository;
import com.skshieldus.esecurity.service.common.ApprovalService;
import com.skshieldus.esecurity.service.secrtactvy.SecurityRectifyPlanService;
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
public class SecurityRectifyPlanServiceImpl implements SecurityRectifyPlanService {

    @Autowired
    private ApprovalService approvalService;

    @Autowired
    private SecurityRectifyPlanRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<Map<String, Object>> selectSecurityRectifyPlanList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectSecurityRectifyPlanList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override public Integer selectSecurityRectifyPlanListCnt(Map<String, Object> paramMap) {
        Integer resultCnt = null;

        try {
            resultCnt = repository.selectSecurityRectifyPlanListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

    @Override
    public Map<String, Object> selectSecurityRectifyPlanView(Map<String, Object> paramMap) {
        Map<String, Object> result = null;

        try {
            result = repository.selectSecurityRectifyPlanView(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean insertRectifyPlan(Map<String, Object> paramMap) {
        boolean result = false;

        try {
            // ================= NOTE: [통합결재정보] 저장 시작 =======================
            ApprovalDTO approval = objectMapper.convertValue(paramMap.get("approval"), ApprovalDTO.class);
            Map<String, Object> htmlMap = objectMapper.convertValue(paramMap.get("htmlMap"), HashMap.class);
            int scDocNo = objectMapper.convertValue(paramMap.get("scDocNo"), Integer.class);

            approval.setLid(scDocNo);
            approval.setHtmlMap(htmlMap);
            log.info(">>>> insertRectifyPlan approval setLid: " + approval);

            //결재문서 등록
            ApprovalDocDTO approvalDoc = approvalService.insertApproval(approval);
            Integer docId = approvalDoc.getDocId();
            paramMap.put("docId", docId);
            // ================= NOTE: [통합결재정보] 저장 종료 =======================

            //보안위규자 시정계획서/경고장 업데이트
            repository.updateCorrPlanDoc(paramMap);
            //보안위규자 시정계획서/경고장 내역 등록
            repository.insertCorrPlanHist(paramMap);
            //보안위규자 건물출입위반시정계획제출여부 업데이트
            repository.updateCorrPlanOfend(paramMap);

            // 처리완료
            result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

}
