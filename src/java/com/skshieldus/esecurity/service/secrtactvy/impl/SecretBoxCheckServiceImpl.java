/**
 *
 */
package com.skshieldus.esecurity.service.secrtactvy.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.model.common.ApprovalDTO;
import com.skshieldus.esecurity.model.common.ApprovalDocDTO;
import com.skshieldus.esecurity.model.common.CommonXlsViewDTO;
import com.skshieldus.esecurity.repository.secrtactvy.SecretBoxCheckRepository;
import com.skshieldus.esecurity.service.common.ApprovalService;
import com.skshieldus.esecurity.service.secrtactvy.SecretBoxCheckService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author X0115378 <jaehoon5.kim@partner.sk.com>
 * @since : 2022. 02. 09.
 *
 */
@Service
@Transactional
@Slf4j
public class SecretBoxCheckServiceImpl implements SecretBoxCheckService {

    @Autowired
    private ApprovalService approvalService;

    @Autowired
    private SecretBoxCheckRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<Map<String, Object>> selectSecretBoxCheckList(Map<String, Object> paramMap) {
        return repository.selectSecretBoxCheckList(paramMap);
    }

    @Override
    public Map<String, Object> selectSecretBoxCheck(Integer scboxChkApplNo) {
        return repository.selectSecretBoxCheck(scboxChkApplNo);
    }

    @Override
    public Map<String, Object> selectEGSSSecretBoxCheck(Map<String, Object> paramMap) {
        Map<String, Object> result;
        try {

            if ("".equals(paramMap.getOrDefault("srvId", "")) ||
                "".equals(paramMap.getOrDefault("srvGrpId", "")) ||
                "".equals(paramMap.getOrDefault("panelId", ""))) {

                log.info("\n>>> selectEGSSSecretBoxCheck 조회조건을 필수로 지정하여야 합니다.", paramMap);
                throw new EsecurityException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
                    "조회조건을 필수로 지정하여야 합니다.");
            }

            result = repository.selectEGSSSecretBoxCheckMaster(paramMap);
            result.put("CHK_RESULT_LIST", repository.selectEGSSSecretBoxCheckSummary(paramMap));
        } catch (EsecurityException mse) {
            throw mse;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> selectEGSSSecretBoxCheckSummary(Map<String, Object> paramMap) {
        return repository.selectEGSSSecretBoxCheckSummary(paramMap);
    }

    @Override
    public CommonXlsViewDTO selectSecretBoxCheckExcelList(Map<String, Object> paramMap) {
        log.info(">>>> selectOutNetAccessReqExcelList : " + paramMap);
        List<Map<String, Object>> resultList = null;
        CommonXlsViewDTO commonXlsViewDTO = new CommonXlsViewDTO();
        commonXlsViewDTO.setFileName("비밀문서함 점검결과 목록");

        try {
            // 비밀문서함 점검결과 EGSS 점검문항 목록 조회
            List<Map<String, Object>> qstList = repository.selectEGSSSecretBoxCheckQstList(paramMap);
            // 비밀문서함 점검결과 목록 Excel 조회
            resultList = repository.selectSecretBoxCheckExcelList(paramMap);

            log.info("\n>>>> selectSecretBoxCheckExcelList: " + resultList);
            Integer maxQstNo = 0;
            for (Map<String, Object> qstItem : qstList) {
                Integer qstNo = Integer.parseInt(qstItem.getOrDefault("qstNo", 0).toString());
                if (maxQstNo < qstNo) {
                    maxQstNo = qstNo;
                }
            }
            /*
             * 년도, 분기, 비밀문서함 명칭, 점검자 사번, 점검자 성명, 점검일, 1번 점수, 2번 점수, 3번 점수, ~~~~ , 점수, 등급,
             */
            // set header names
            String[] headerNameArr = { "년도", "분기", "비밀문서함 명칭", "관리부서", "점검자 사번", "점검자 성명", "점검일" };
            List<String> headerNameList = new ArrayList<>(Arrays.asList(headerNameArr));
            // set column names (data field name)
            String[] columnNameArr = { "year", "quarter", "scboxNm", "mngDeptNm", "chkrEmpId", "chkrEmpNm", "chkDt" };
            List<String> columnNameList = new ArrayList<>(Arrays.asList(columnNameArr));
            // set column width
            Integer[] columnWidthArr = { 1700, 1400, 7000, 4700, 2800, 2900, 2800 };
            List<Integer> columnWidthList = new ArrayList<>(Arrays.asList(columnWidthArr));
            for (Integer i = 1; i <= maxQstNo; i++) {
                headerNameList.add(i.toString() + "번 점수");
                columnNameList.add("no" + i.toString());
                columnWidthList.add(2100);
            }

            headerNameList.add("점수");
            columnNameList.add("resultPoints");
            columnWidthList.add(1800);

            headerNameList.add("등급");
            columnNameList.add("grade");
            columnWidthList.add(1800);

            headerNameList.add("결재상태");
            columnNameList.add("applStatNm");
            columnWidthList.add(2300);

            headerNameList.add("결재완료일");
            columnNameList.add("approvedDtm");
            columnWidthList.add(2800);

            // set header names
            commonXlsViewDTO.setHeaderNameArr(headerNameList.toArray(new String[headerNameList.size()]));
            // set column names (data field name)
            commonXlsViewDTO.setColumnNameArr(columnNameList.toArray(new String[columnNameList.size()]));
            // set column width
            commonXlsViewDTO.setColumnWidthArr(columnWidthList.toArray(new Integer[columnWidthList.size()]));

            // set excel data
            commonXlsViewDTO.setDataList(resultList);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return commonXlsViewDTO;
    }

    @Override
    public Boolean insertSecretBoxCheck(Map<String, Object> paramMap) {
        Boolean result = false;
        try {
            if ("".equals(paramMap.getOrDefault("crtBy", ""))) {

                log.info("\n>>> insertSecretBoxCheck 필수 지정 확인", paramMap);
                throw new EsecurityException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
                    "정보 저장에 필요한 로그인 정보가 없습니다. 다시 접속해 주세요.");
            }
            String crtBy = paramMap.getOrDefault("crtBy", "").toString();

            Integer scboxChkApplNo = repository.selectSecretBoxCheckSeq();
            paramMap.put("scboxChkApplNo", scboxChkApplNo);

            log.info("\n>>>> insertSecretBoxCheck: " + paramMap);
            int resultCnt = repository.insertSecretBoxCheck(paramMap);

            // ================= NOTE: [통합결재정보] 저장 시작 =======================
            ApprovalDTO approval = objectMapper.convertValue(paramMap.get("approval"), ApprovalDTO.class);

            approval.setLid(scboxChkApplNo);
            log.info(">>>> insertDocSend approval setLid: " + approval);

            ApprovalDocDTO approvalDoc = approvalService.insertApproval(approval);
            Integer docId = approvalDoc.getDocId();
            paramMap.put("docId", docId);
            // ================= NOTE: [통합결재정보] 저장 종료 =======================

            repository.updateSecretBoxCheckDocId(paramMap);

            // 처리완료
            result = true;
        } catch (EsecurityException mse) {
            throw mse;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }
        return result;
    }

    @Override
    public Boolean updateSecretBoxCheckDocId(Map<String, Object> paramMap) {
        return repository.updateSecretBoxCheckDocId(paramMap) > 0;
    }

    @Override
    public Boolean updateSecretBoxCheckApplStat(Map<String, Object> paramMap) {
        return repository.updateSecretBoxCheckApplStat(paramMap) > 0;
    }

}
