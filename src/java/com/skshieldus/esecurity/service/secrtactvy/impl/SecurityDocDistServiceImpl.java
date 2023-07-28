package com.skshieldus.esecurity.service.secrtactvy.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.model.common.ApprovalDTO;
import com.skshieldus.esecurity.model.common.ApprovalDocDTO;
import com.skshieldus.esecurity.model.common.CommonXlsViewDTO;
import com.skshieldus.esecurity.repository.secrtactvy.SecurityDocDistRepository;
import com.skshieldus.esecurity.service.common.ApprovalService;
import com.skshieldus.esecurity.service.secrtactvy.SecurityDocDistService;
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
public class SecurityDocDistServiceImpl implements SecurityDocDistService {

    @Autowired
    private ApprovalService approvalService;

    @Autowired
    private SecurityDocDistRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<Map<String, Object>> selectSecurityDocDistItemList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectSecurityDocDistItemList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Map<String, Object> selectSecurityDocDistView(Map<String, Object> paramMap) {
        Map<String, Object> resultList = null;

        try {
            resultList = repository.selectSecurityDocDistView(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public List<Map<String, Object>> selectSecurityDocDistViewList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectSecurityDocDistViewList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean insertSecurityDocDist(Map<String, Object> paramMap) {
        boolean result = false;

        try {

            // 신규 채번
            int scDocDistId = repository.selectSecurityDocDistSeq();
            paramMap.put("scDocDistId", scDocDistId);

            // ================= NOTE: [통합결재정보] 저장 시작 =======================
            ApprovalDTO approval = objectMapper.convertValue(paramMap.get("approval"), ApprovalDTO.class);
            // 결재선이 있는 경우만 결재 상신 (자가결재 관련 처리)
            if ((approval.getSavedRequestApproverLine() != null && approval.getSavedRequestApproverLine().size() > 0)
                || (approval.getSavedPermitApproverLine() != null && approval.getSavedPermitApproverLine().size() > 0)) {

                Map<String, Object> htmlMap = objectMapper.convertValue(paramMap.get("htmlMap"), HashMap.class);

                approval.setLid(scDocDistId);
                approval.setHtmlMap(htmlMap);
                log.info(">>>> insertRepairGateInChange approval setLid: " + approval);

                ApprovalDocDTO approvalDoc = approvalService.insertApproval(approval);
                Integer docId = approvalDoc.getDocId();
                paramMap.put("docId", docId);
            }
            // ================= NOTE: [통합결재정보] 저장 종료 =======================

            // 비문분류표 정보 등록
            repository.insertSecurityDocDist(paramMap);

            if (paramMap.get("tableData") != null) {
                List<Map<String, Object>> tableData = (List<Map<String, Object>>) paramMap.get("tableData");
                for (Map<String, Object> data : tableData) {
                    data.put("scDocDistId", scDocDistId);
                    data.put("empId", paramMap.get("empId"));
                    data.put("acIp", paramMap.get("acIp"));
                    // 비문분류표 작성정보 등록
                    repository.insertSecurityDocItem(data);
                }
            }
            // 처리완료
            result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public CommonXlsViewDTO selectSecurityDocDistViewDownload(Map<String, Object> paramMap) {
        CommonXlsViewDTO commonXlsViewDTO = new CommonXlsViewDTO();
        commonXlsViewDTO.setFileName("비문분류기준표");

        // set header names
        String[] headerNameArr = { "부서명 (그룹명/팀명)", "비밀 (Secret)", "대외비 (Confidential)" };
        commonXlsViewDTO.setHeaderNameArr(headerNameArr);

        // set column names (data field name)
        String[] columnNameArr = { "gubun1", "gubun2", "gubun3" };
        commonXlsViewDTO.setColumnNameArr(columnNameArr);

        // set column width
        Integer[] columnWidthArr = { 7000, 12000, 12000 };
        commonXlsViewDTO.setColumnWidthArr(columnWidthArr);

        List<Map<String, Object>> list = repository.selectSecurityDocDistViewList(paramMap);

        // set excel data
        commonXlsViewDTO.setDataList(list);

        return commonXlsViewDTO;
    }

}
