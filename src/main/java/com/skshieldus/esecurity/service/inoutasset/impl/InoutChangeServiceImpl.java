package com.skshieldus.esecurity.service.inoutasset.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skshieldus.esecurity.common.component.FileUpload;
import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ListDTO;
import com.skshieldus.esecurity.model.common.ApprovalDTO;
import com.skshieldus.esecurity.model.common.ApprovalDocDTO;
import com.skshieldus.esecurity.repository.inoutasset.InoutChangeRepository;
import com.skshieldus.esecurity.service.common.ApprovalService;
import com.skshieldus.esecurity.service.inoutasset.InoutChangeService;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class InoutChangeServiceImpl implements InoutChangeService {

    private final InoutChangeRepository repository;

    private final ApprovalService approvalService;

    private final ObjectMapper objectMapper;

    private final FileUpload fileUpload;

    @Override
    public ListDTO<Map<String, Object>> selectInDateChangeList(Map<String, Object> paramMap) {
        return ListDTO.getInstance(repository.selectInDateChangeList(paramMap), repository.selectInDateChangeCount(paramMap));
    }

    @Override
    public void insertInDateChange(Map<String, Object> paramMap) {
        try {
            // 반출입 신청 이력 기록, inoutwrite 신청코드 업데이트
            repository.insertInDateDelayHistory(paramMap);
            repository.updateInOutDelay(paramMap);

            ApprovalDTO approval = objectMapper.convertValue(paramMap.get("approval"), ApprovalDTO.class);
            approval.setLid(Integer.parseInt(paramMap.get("indateApplNo").toString()));

            int requestLineLen = approval.getSavedRequestApproverLine().size();
            int permitLineLen = approval.getSavedPermitApproverLine().size();

            if (requestLineLen > 0 || permitLineLen > 0) {
                ApprovalDocDTO approvalDoc = approvalService.insertApproval(approval);
                paramMap.put("docId", approvalDoc.getDocId());
                repository.updateInDateChangeDocId(paramMap);
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
    }

    @Override
    public ListDTO<Map<String, Object>> selectInOutEmpChangeList(Map<String, Object> paramMap) {
        return ListDTO.getInstance(repository.selectInOutEmpChangeList(paramMap), repository.selectInOutEmpChangeCount(paramMap));
    }

    @Override
    public void insertEmpChange(Map<String, Object> paramMap) {
        try {
            repository.insertInOutEmpChange(paramMap);
            repository.updateInOutDocument(paramMap);

            ApprovalDTO approval = objectMapper.convertValue(paramMap.get("approval"), ApprovalDTO.class);
            approval.setLid(Integer.parseInt(paramMap.get("docMoveApplNo").toString()));

            int requestLineLen = approval.getSavedRequestApproverLine().size();
            int permitLineLen = approval.getSavedPermitApproverLine().size();

            if (requestLineLen > 0 || permitLineLen > 0) {
                ApprovalDocDTO approvalDoc = approvalService.insertApproval(approval);
                paramMap.put("docId", approvalDoc.getDocId());
                repository.updateInOutEmpChangeDocId(paramMap);
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
    }

    @Override
    public ListDTO<Map<String, Object>> selectInOutKndChangeList(Map<String, Object> paramMap) {
        return ListDTO.getInstance(repository.selectInOutKndChangeList(paramMap), repository.selectInOutKndChangeCount(paramMap));
    }

    @Override
    public void insertInOutKndChange(Map<String, Object> paramMap) {
        try {
            repository.insertInOutKndChange(paramMap);
            repository.updateInOutKnd(paramMap);

            ApprovalDTO approval = objectMapper.convertValue(paramMap.get("approval"), ApprovalDTO.class);
            approval.setLid(Integer.parseInt(paramMap.get("inoutChangeApplNo").toString()));

            int requestLineLen = approval.getSavedRequestApproverLine().size();
            int permitLineLen = approval.getSavedPermitApproverLine().size();

            if (requestLineLen > 0 || permitLineLen > 0) {
                ApprovalDocDTO approvalDoc = approvalService.insertApproval(approval);
                paramMap.put("docId", approvalDoc.getDocId());
                repository.updateInOutKndChangeDocId(paramMap);
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
    }

    @Override
    public ListDTO<Map<String, Object>> selectFinishChangeList(Map<String, Object> paramMap) {
        return ListDTO.getInstance(repository.selectFinishChangeList(paramMap), repository.selectFinishChangeCount(paramMap));
    }

    @Override
    public void insertFinishChange(Map<String, Object> paramMap, MultipartFile file1, MultipartFile file2) {
        try {
            if (file1 != null && !file1.isEmpty()) {
                Map<String, Object> resultFile1 = fileUpload.uploadFile(file1, "inoutwrite");
                paramMap.put("filename1", resultFile1.get("fileName"));
                paramMap.put("svrFilePath01", resultFile1.get("filePath"));
                paramMap.put("fileid1", resultFile1.get("fileId"));
            }

            if (file2 != null && !file2.isEmpty()) {
                Map<String, Object> resultFile2 = fileUpload.uploadFile(file2, "inoutwrite");
                paramMap.put("filename2", resultFile2.get("fileName"));
                paramMap.put("svrFilePath02", resultFile2.get("filePath"));
                paramMap.put("fileid2", resultFile2.get("fileId"));
            }

            repository.insertFinishChangeHistory(paramMap);
            repository.updateFinishChange(paramMap);

            ApprovalDTO approval = objectMapper.readValue((String) paramMap.get("approval"), ApprovalDTO.class);
            approval.setLid(Integer.parseInt(paramMap.get("finishApplNo").toString()));

            int requestLineLen = approval.getSavedRequestApproverLine().size();
            int permitLineLen = approval.getSavedPermitApproverLine().size();

            if (requestLineLen > 0 || permitLineLen > 0) {
                ApprovalDocDTO approvalDoc = approvalService.insertApproval(approval);
                paramMap.put("docId", approvalDoc.getDocId());
                repository.updateFinishChangeDocId(paramMap);
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
    }

}
