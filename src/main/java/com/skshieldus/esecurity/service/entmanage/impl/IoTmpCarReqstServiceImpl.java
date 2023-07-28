package com.skshieldus.esecurity.service.entmanage.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.RequestWrapModel;
import com.skshieldus.esecurity.model.common.ApprovalDTO;
import com.skshieldus.esecurity.model.common.ApprovalDocDTO;
import com.skshieldus.esecurity.model.common.CommonXlsViewDTO;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.repository.entmanage.IoTmpCarReqstRepository;
import com.skshieldus.esecurity.service.common.ApprovalService;
import com.skshieldus.esecurity.service.entmanage.IoTmpCarReqstService;
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
public class IoTmpCarReqstServiceImpl implements IoTmpCarReqstService {

    @Autowired
    private IoTmpCarReqstRepository ioTmpCarReqstRepository;

    @Autowired
    private ApprovalService approvalService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<Map<String, Object>> selectIoTmpCarReqstList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            // 목록 조회
            resultList = ioTmpCarReqstRepository.selectIoTmpCarReqstList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return resultList;
    }

    @Override
    public Map<String, Object> selectIoTmpCarReqstView(Map<String, Object> paramMap) {

        Map<String, Object> result = new HashMap<String, Object>();

        try {
            // 목록 조회
            Map<String, Object> tempCarPassView = ioTmpCarReqstRepository.selectIoTmpCarReqstView(paramMap);
            List<Map<String, Object>> tempCarPassUserList = ioTmpCarReqstRepository
                .selectIoTmpCarReqstUserList(paramMap);
            result.put("tempCarPassView", tempCarPassView);
            result.put("tempCarPassUserList", tempCarPassUserList);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return result;
    }

    @Override
    public boolean insertIoTmpCarReqst(SessionInfoVO sessionInfo, Map<String, Object> paramMap) {

        boolean result = false;
        int insertIoTmpCarReqstInsertResult = 0;
        int insertIoTmpCarReqstUpdateResult = 0;
        int tmpcarAppNo = 0;

        try {

            Map<String, String> userInfo = objectMapper.convertValue(paramMap.get("userInfo"),
                new TypeReference<Map<String, String>>() {
                });

            /** 기본 정보 설정 시작 */
            // 결재 업무 번호 채번
            tmpcarAppNo = ioTmpCarReqstRepository.selectIoTmpCarReqstNewSeq(paramMap);
            log.info(">>>> tmpcarAppNo : {}", tmpcarAppNo);
            /** 기본 정보 설정 종료 */

            // 임시차량출입 신청 등록
            paramMap.put("tmpcarAppNo", tmpcarAppNo);
            paramMap.put("acIp", sessionInfo.getIp());
            paramMap.put("crtBy", userInfo.getOrDefault("empId", ""));
            paramMap.put("applStat", "Z0331002"); // Z0331002 : 접수, Z0331005 : 결재선미지정으로 인한 반려
            log.info(">>>> paramMap : {}", paramMap);

            insertIoTmpCarReqstInsertResult = ioTmpCarReqstRepository.insertIoTmpCarReqst(paramMap);

            if (insertIoTmpCarReqstInsertResult != 1) {
                throw new Exception("Failed to execute ioTmpCarReqstRepository.insertIoTmpCarReqst");
            }

            log.info(">>>> 임시차량출입  신청 등록 성공");

            // 임시차량출입신청 출입자 LIST 등록
            List<Map<String, Object>> reqstList = objectMapper.convertValue(paramMap.get("reqstList"),
                new TypeReference<List<Map<String, Object>>>() {
                });
            log.info(">>>> reqstList : {}", reqstList);

            //			int rowCnt = 1;
            int IoTmpCarReqstUserInsertResult = 0;
            for (Map<String, Object> reqstUser : reqstList) {

                // 결재 업무 번호 채번
                int seq = 0;
                seq = ioTmpCarReqstRepository.selectIoTmpCarReqstUserNewSeq(paramMap);
                reqstUser.put("tmpcarAppNo", tmpcarAppNo);
                reqstUser.put("seq", seq);
                reqstUser.put("ioDt", paramMap.get("strtDt"));
                reqstUser.put("acIp", (sessionInfo.getIp()));
                reqstUser.put("crtBy", (userInfo.getOrDefault("empId", "")));
                reqstUser.put("modBy", (userInfo.getOrDefault("empId", "")));

                ioTmpCarReqstRepository.insertIoTmpCarReqstUser(reqstUser);
                IoTmpCarReqstUserInsertResult++;
                //				rowCnt++;
            }

            if (IoTmpCarReqstUserInsertResult > 0) {
                log.info(">>>> 임시차량출입신청 출입자 정보 등록 성공");
            }
            else {
                throw new Exception("Failed to execute ioTmpCarReqstRepository.insertIoTmpCarReqstUser");
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
            paramMap.put("modBy", userInfo.getOrDefault("empId", ""));
            log.info(">>>> APPROVAL_DOC > docId : {}", approvalDoc.getDocId());
            log.info(">>>> APPROVAL_DOC > applStat : {}", paramMap.get("applStat"));
            log.info(">>>> APPROVAL_DOC > modBy : {}", userInfo.getOrDefault("empId", ""));

            insertIoTmpCarReqstUpdateResult = ioTmpCarReqstRepository.updateIoTmpCarReqstDocId(paramMap);
            if (insertIoTmpCarReqstUpdateResult == 1) {
                log.info(">>>> 임시차량출입  신청 결재정보 업데이트 성공");
                result = true;
            }
        } catch (Exception e) {
            result = false;
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return result;
    }

    @Override
    public CommonXlsViewDTO selectIoTmpCarReqstListExcel(Map<String, Object> paramMap) {

        List<Map<String, Object>> resultList = null;
        CommonXlsViewDTO commonXlsViewDTO = new CommonXlsViewDTO();
        commonXlsViewDTO.setFileName("임시차량출입 신청 현황");

        try {
            // 임시차량 출입 신청 현황 리스트 조회
            resultList = ioTmpCarReqstRepository.selectIoTmpCarReqstListExcel(paramMap);

            // set header names
            String[] headerNameArr = { "신청번호", "신청사유", "신청구분", "출입기간", "출입차종", "출입차량번호", "출입장소", "출입사유", "소속", "직급",
                "출입자 성명", "팀명", "이름", "사번", "결재자Comment" };

            commonXlsViewDTO.setHeaderNameArr(headerNameArr);

            // set column names (data field name)
            String[] columnNameArr = { "tmpcarAppNo", "appNm", "applNm", "ioDt", "carKnd", "carNo", "gateNm", "applObj",
                "compNm", "jwNm", "empNm", "teamDeptNm", "teamEmpNm", "empId", "apprComment" };
            commonXlsViewDTO.setColumnNameArr(columnNameArr);

            // set column width
            Integer[] columnWidthArr = { 4000, 4000, 4000, 7000, 5000, 5000, 15000, 8000, 7000, 3000, 4000, 7000, 4000, 4000,
                8000 };
            commonXlsViewDTO.setColumnWidthArr(columnWidthArr);

            // set excel data
            commonXlsViewDTO.setDataList(resultList);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return commonXlsViewDTO;
    }

}