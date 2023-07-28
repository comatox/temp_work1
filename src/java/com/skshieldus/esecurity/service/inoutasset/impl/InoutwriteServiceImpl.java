package com.skshieldus.esecurity.service.inoutasset.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skshieldus.esecurity.common.component.FileUpload;
import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.model.common.ApprovalDTO;
import com.skshieldus.esecurity.model.common.ApprovalDocDTO;
import com.skshieldus.esecurity.repository.inoutasset.InoutwriteRepository;
import com.skshieldus.esecurity.service.common.ApprovalService;
import com.skshieldus.esecurity.service.inoutasset.InoutwriteService;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class InoutwriteServiceImpl implements InoutwriteService {

    private final ObjectMapper objectMapper;

    private final FileUpload fileUpload;

    private final ApprovalService approvalService;

    private final InoutwriteRepository repository;

    @Override public List<Map<String, Object>> selectArticleGroupCodeList(HashMap<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectArticleGroupCodeList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override public Map<String, Object> saveInoutwrite(HashMap<String, Object> paramMap, List<MultipartFile> fileToUpload) {
        Map<String, Object> resultMap = new HashMap<>();

        try {
            String saveType = objectMapper.convertValue(paramMap.get("saveType"), String.class);
            String articlekndno = objectMapper.convertValue(paramMap.get("articlekndno"), String.class);
            String articlegroupid = objectMapper.convertValue(paramMap.get("articlegroupid"), String.class);
            String companyno = objectMapper.convertValue(paramMap.get("companyno"), String.class);
            String empNo = objectMapper.convertValue(paramMap.get("empNo"), String.class);
            String deptCd = objectMapper.convertValue(paramMap.get("deptCd"), String.class);
            String compId = objectMapper.convertValue(paramMap.get("compId"), String.class);
            String acIp = objectMapper.convertValue(paramMap.get("acIp"), String.class);

            String inoutApplNo = String.valueOf(paramMap.get("inoutApplNo"));
            String writedate = String.valueOf(paramMap.get("writedate"));
            String writeseq = String.valueOf(paramMap.get("writeseq"));
            String inoutserialno = String.valueOf(paramMap.get("inoutserialno"));

            boolean isNew = StringUtils.isEmpty(inoutApplNo) ? true : false; // 신규 여부

            if(isNew){
                // new sequence
                Map<String, Object> sequenceData = repository.selectInoutwriteNewSeq(paramMap);
                inoutApplNo = String.valueOf(sequenceData.get("inoutApplNo"));
                writedate = String.valueOf(sequenceData.get("writedate"));
                writeseq = String.valueOf(sequenceData.get("writeseq"));
                inoutserialno = String.valueOf(sequenceData.get("inoutserialno"));

                paramMap.put("inoutApplNo", inoutApplNo);
                paramMap.put("writedate", writedate);
                paramMap.put("writeseq", writeseq);
                paramMap.put("inoutserialno", inoutserialno);
            }

            // 자산반출입 저장
            repository.mergeInoutwrite(paramMap);

            // 기존 자산반출입-물품내역 삭제
            repository.deleteInoutarticlelist(Integer.valueOf(inoutApplNo));

            // 자산반출입-물품내역 저장
            if (!ObjectUtils.isEmpty(paramMap.get("fileData"))) {
                List<HashMap<String, Object>> fileDataList = Arrays.asList(objectMapper.readValue((String) paramMap.get("fileData"), HashMap[].class));

                Map<String, Object> fileMap = null;
                HashMap<String, Object> infoMap = null;
                String writeaseq, articleid, fileId = null;

                int maxWriteaseq = fileDataList.stream().mapToInt(v -> Integer.parseInt(String.valueOf(v.get("writeaseq")))).max().orElse(0);

                for (int i = 0; i < fileDataList.size(); i++) {
                    infoMap = fileDataList.get(i);
                    infoMap.put("inoutApplNo", inoutApplNo);
                    infoMap.put("writedate", writedate);
                    infoMap.put("writeseq", writeseq);
                    infoMap.put("companyno", companyno);
                    infoMap.put("articlekndno", articlekndno);
                    infoMap.put("articlegroupid", articlegroupid);
                    infoMap.put("empNo", empNo);
                    infoMap.put("deptCd", deptCd);
                    infoMap.put("compId", compId);
                    infoMap.put("acIp", acIp);

                    articleid = objectMapper.convertValue(infoMap.get("articleid"), String.class);
                    if (
                        (
                            (articlekndno.equals("1") && (!articlegroupid.equals("1000000001") && !articlegroupid.equals("1000000002") && !articlegroupid.equals("1000000003") && !articlegroupid.equals("1000000182") && !articlegroupid.equals("1000000004") && !articlegroupid.equals("1000000142")))
                            || (articlekndno.equals("2") && (articlegroupid.equals("1000000056") || articlegroupid.equals("1000000051") || articlegroupid.equals("1000000057")))
                            || articlekndno.equals("5")
                            || articlekndno.equals("6")
                        )
                        &&
                        (StringUtils.isEmpty(articleid) || "0".equals(articleid))
                    ) {
                        repository.insertArticle(infoMap);
                    }

                    fileId = objectMapper.convertValue(infoMap.get("fileId"), String.class);
                    if (StringUtils.isEmpty(fileId) && fileToUpload != null && fileToUpload.get(i) != null && fileToUpload.get(i).getName() != null) {
                        // 파일 저장
                        fileMap = fileUpload.uploadFile(fileToUpload.get(i), "inoutwrite");
                        infoMap.put("filepath", fileMap.get("fullPath"));
                        infoMap.put("filename", fileMap.get("fileName"));
                        infoMap.put("fileid", fileMap.get("fileId"));
                    }

                    writeaseq = objectMapper.convertValue(infoMap.get("writeaseq"), String.class);
                    if(StringUtils.isEmpty(writeaseq) || "".equals(writeaseq)) {
                        infoMap.put("writeaseq",  maxWriteaseq + i);
                    }

                    // 자산반출입-물품내역 저장
                    repository.mergeInoutarticlelist(infoMap);
                }
            }

            if (saveType.equals("REPORT")) {
                // 결재 처리
                ApprovalDTO approval = objectMapper.readValue((String) paramMap.get("approval"), ApprovalDTO.class);
                approval.setLid(Integer.parseInt(inoutApplNo));

                ApprovalDocDTO approvalDoc = approvalService.insertApproval(approval);

                // DOC_ID update
                paramMap.put("docId", approvalDoc.getDocId());

                int requestLineLen = approval.getSavedRequestApproverLine().size();
                int permitLineLen = approval.getSavedPermitApproverLine().size();

                if (requestLineLen > 0) {
                    paramMap.put("publishdeptokyn", "1");
                    if (saveType.equals("REPORT")) {
                        paramMap.put("publishdeptokyn", "0");
                    }
                }
                else {
                    paramMap.put("publishdeptokyn", "0");
                }

                if (permitLineLen > 0) {
                    paramMap.put("leavedeptchkyn", "1");
                    if (saveType.equals("REPORT")) {
                        paramMap.put("leavedeptokyn", "0");
                    }
                }
                else {
                    paramMap.put("leavedeptokyn", "0");
                }

                repository.updateInoutwriteDocId(paramMap);
            }

            resultMap.put("result", HttpStatus.OK.getReasonPhrase());
            resultMap.put("inoutApplNo", inoutApplNo);
            resultMap.put("writedate", writedate);
            resultMap.put("writeseq", writeseq);
            resultMap.put("inoutserialno", inoutserialno);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultMap;
    }

}
