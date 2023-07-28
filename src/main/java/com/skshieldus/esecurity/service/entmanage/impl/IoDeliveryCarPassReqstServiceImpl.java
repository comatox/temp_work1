package com.skshieldus.esecurity.service.entmanage.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.RequestWrapModel;
import com.skshieldus.esecurity.model.common.ApprovalDTO;
import com.skshieldus.esecurity.model.common.ApprovalDocDTO;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.repository.entmanage.IoDeliveryCarPassReqstRepository;
import com.skshieldus.esecurity.service.common.ApprovalService;
import com.skshieldus.esecurity.service.entmanage.IoDeliveryCarPassReqstService;
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
public class IoDeliveryCarPassReqstServiceImpl implements IoDeliveryCarPassReqstService {

    @Autowired
    private IoDeliveryCarPassReqstRepository ioDeliveryCarPassReqstRepository;

    @Autowired
    private ApprovalService approvalService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<Map<String, Object>> selectIoDeliveryCarPassReqstList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            // 목록 조회
            resultList = ioDeliveryCarPassReqstRepository.selectIoDeliveryCarPassReqstList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return resultList;
    }

    @Override
    public Map<String, Object> selectIoDeliveryCarPassReqstView(Map<String, Object> paramMap) {

        Map<String, Object> result = new HashMap<String, Object>();

        try {
            // 상세 조회
            result = ioDeliveryCarPassReqstRepository.selectIoDeliveryCarPassReqstList(paramMap).get(0);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return result;
    }

    @Override
    public boolean insertIoDeliveryCarPassReqst(SessionInfoVO sessionInfo, Map<String, Object> paramMap) {

        boolean result = false;
        int insertIoDeliveryCarPassReqstInsertResult = 0;
        int insertIoDeliveryCarPassReqstUpdateResult = 0;
        int dlvcarAppNo = 0;

        try {

            Map<String, String> userInfo = objectMapper.convertValue(paramMap.get("userInfo"),
                new TypeReference<Map<String, String>>() {
                });

            /** 기본 정보 설정 시작 */
            // 결재 업무 번호 채번
            dlvcarAppNo = ioDeliveryCarPassReqstRepository.selectIoDeliveryCarPassReqstNewSeq(paramMap);
            log.info(">>>> dlvcarAppNo : {}", dlvcarAppNo);
            /** 기본 정보 설정 종료 */

            // 임시차량출입 신청 등록
            paramMap.put("dlvcarAppNo", dlvcarAppNo);
            paramMap.put("acIp", sessionInfo.getIp());
            paramMap.put("crtBy", userInfo.getOrDefault("empId", ""));
            log.info(">>>> paramMap : {}", paramMap);

            insertIoDeliveryCarPassReqstInsertResult = ioDeliveryCarPassReqstRepository.insertIoDeliveryCarPassReqst(paramMap);

            if (insertIoDeliveryCarPassReqstInsertResult != 1) {
                throw new Exception("Failed to execute ioDeliveryCarPassReqstRepository.insertIoDeliveryCarPassReqst");
            }

            log.info(">>>> 임시차량출입  신청 등록 성공");

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
            approval.setLid(dlvcarAppNo);
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

            insertIoDeliveryCarPassReqstUpdateResult = ioDeliveryCarPassReqstRepository.updateIoDeliveryCarPassReqstDocId(paramMap);
            if (insertIoDeliveryCarPassReqstUpdateResult == 1) {
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

}