package com.skshieldus.esecurity.service.secrtactvy.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.model.common.ApprovalDTO;
import com.skshieldus.esecurity.model.common.ApprovalDocDTO;
import com.skshieldus.esecurity.repository.secrtactvy.SecurityDeputyRepository;
import com.skshieldus.esecurity.service.common.ApprovalService;
import com.skshieldus.esecurity.service.secrtactvy.SecurityDeputyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
public class SecurityDeputyImpl implements SecurityDeputyService {

    @Autowired
    private ApprovalService approvalService;

    @Autowired
    private SecurityDeputyRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    // 보안담당자 신규/변경 조회 - securityDeputyList
    @Override
    public List<Map<String, Object>> securityDeputyList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.securityDeputyList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    // 보안담당자 신규/변경 상세정보 - securityDeputyView
    @Override
    public Map<String, Object> securityDeputyView(Map<String, Object> paramMap) {
        Map<String, Object> resultView = null;

        try {
            resultView = repository.securityDeputyView(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultView;
    }

    // 보안담당자 신규/변경 상세정보 > 관리부서 Tree - securityDeputyDeptTreeList
    @Override
    public List<Map<String, Object>> securityDeputyDeptTreeList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.securityDeputyDeptTreeList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    // 팀내생활보안점검 > 점검부서 Tree - securityDeputyDeptTreeList3
    @Override
    public List<Map<String, Object>> securityDeputyDeptTreeList3(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.securityDeputyDeptTreeList3(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    // 보안담당자 신규/변경  > 보안담당자 신규/변경 (상신)
    @SuppressWarnings("unchecked")
    @Override
    public boolean insertSecurityDeputy(Map<String, Object> paramMap) {
        boolean result = false;

        try {

            // scChangeNo 시퀀스 채번 - selectScChangeNoSeq
            int scChangeNo = repository.selectScChangeNoSeq();
            paramMap.put("scChangeNo", scChangeNo);

            // ================= NOTE: [통합결재정보] 저장 시작 =======================
            ApprovalDTO approval = objectMapper.convertValue(paramMap.get("approval"), ApprovalDTO.class);
            Map<String, Object> htmlMap = objectMapper.convertValue(paramMap.get("htmlMap"), HashMap.class);
            //int scChangeNo = objectMapper.convertValue(paramMap.get("scChangeNo"), Integer.class);

            approval.setLid(scChangeNo);
            approval.setHtmlMap(htmlMap);
            log.info(">>>> insertSecurityDeputy approval setLid: " + approval);

            ApprovalDocDTO approvalDoc = approvalService.insertApproval(approval);
            Integer docId = approvalDoc.getDocId();
            paramMap.put("docId", docId);
            System.out.println("### [통합결재정보] 저장 종료  > paramMap :" + paramMap.toString());
            // ================= NOTE: [통합결재정보] 저장 종료 =======================

            /**
             * 결재 관련 처리는 Common에서 처리함으로
             * 아래 로직(로그인 사용자와 신임 보안담당자가 다를 경우(newAppr = 'Y')요청부서 결재선 첫번째 결재자로 지정)은
             * Front단에서 구현 by kwg. 220106
             */
            //    		String newAppr = StringUtils.defaultIfEmpty(String.valueOf(paramMap.get("newAppr")), "");
            //    		// 신규보안담당자여부 (로그인사용자와 신규 보안담당자가 다를경우 'Y')
            //    		if ("Y".equals(newAppr)) {
            //    			//허가부서 결재선의 최종값(AP_SEQ)을 증가시키는 로직 제거함..(DU_ApprovalLine.java에서 증가하여 오므로 필요없어짐  20160205 HSK
            //    		    //보안 담당자 자가 승인 NEW_APPR 이 "N" 로 넘어 오지만 , 최종 결재선에 신청자 정보가 결재선에 있어야 하므로 추가하는 로직을 수행함. 20160225 HSK
            //
            //    			String rDocId = StringUtils.defaultIfEmpty(String.valueOf(paramMap.get("docId")), "");
            //    			// Step 00) 보안담당자 신규/변경 신청 > 결재선 AP_SEQ 증가처리 - updateSecrtChangeApAppr
            //    			repository.updateSecrtChangeApAppr(rDocId);
            //
            //
            //    			//보안담당자 변경 로직 : 신임 담당자 승인 후 팀장 결재형태로 변경됨
            //    			Map<String, Object> apprParam = new HashMap<String, Object>();
            //    			apprParam.put("docId", StringUtils.defaultIfEmpty(String.valueOf(paramMap.get("docId")), ""));
            //    			apprParam.put("apSeq", 0);
            //    			apprParam.put("apprDeptGbn", "1");
            //    			apprParam.put("compId", StringUtils.defaultIfEmpty(String.valueOf(paramMap.get("newCompId")), ""));
            //    			apprParam.put("deptId", StringUtils.defaultIfEmpty(String.valueOf(paramMap.get("newDeptId")), ""));
            //    			apprParam.put("jwId", StringUtils.defaultIfEmpty(String.valueOf(paramMap.get("newJwId")), ""));
            //    			apprParam.put("empId", StringUtils.defaultIfEmpty(String.valueOf(paramMap.get("newEmpId")), ""));
            //    			// 보안담당자 서약 정보 종료
            //    			apprParam.put("apprResult", "0");
            //    			apprParam.put("autoSign", "0");
            //    			apprParam.put("delYn", "N");
            //    			apprParam.put("crtBy", StringUtils.defaultIfEmpty(String.valueOf(paramMap.get("empId")), ""));
            //    			apprParam.put("modBy", StringUtils.defaultIfEmpty(String.valueOf(paramMap.get("empId")), ""));
            //
            //    			// Step 01) 보안담당자 신규/변경 신청 > 결재선 추가 - INSERT INTO AP_APPR
            //    			repository.insertSecrtChangeApAppr(apprParam);
            //
            //
            //    			Map<String, Object> docParam = new HashMap<String, Object>();
            //    			docParam.put("apSeq", 0);
            //    			docParam.put("docId", docId);
            //    			docParam.put("apprDeptGbn", "1");
            //    			docParam.put("compId", StringUtils.defaultIfEmpty(String.valueOf(paramMap.get("newCompId")), ""));
            //    			docParam.put("deptId", StringUtils.defaultIfEmpty(String.valueOf(paramMap.get("newDeptId")), ""));
            //    			docParam.put("jwId", StringUtils.defaultIfEmpty(String.valueOf(paramMap.get("newJwId")), ""));
            //    			docParam.put("empId", StringUtils.defaultIfEmpty(String.valueOf(paramMap.get("newEmpId")), ""));
            //    			docParam.put("apprStat", "10");
            //    			docParam.put("apprResult", "0");
            //    			docParam.put("modBy", StringUtils.defaultIfEmpty(String.valueOf(paramMap.get("empId")), ""));
            //
            //    			// Step 02) 보안담당자 신규/변경 신청 > AP_DOC Update
            //    			repository.updateApDocScrtChg(docParam);
            //
            //    		} // end if ("Y".equals(newAppr))

            /* 신청정보 */
            // 보안담당자 신규/변경 신청 > 신청정보 Insert SC_CHANGE
            paramMap.put("crtBy", StringUtils.defaultIfEmpty(String.valueOf(paramMap.get("empId")), ""));
            repository.insertSecrtChange(paramMap);
            // 보안담당자 신규/변경 신청 > 이전 신청정보 Insert - insertScDeptSecEmpPre
            repository.insertScDeptSecEmpPre(paramMap);

            // 처리완료
            result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    // 보안담당자 신규/변경 신청 > 보안담당자 List - secrtDeptDuptyCheckDuptyRenew
    @Override
    public List<Map<String, Object>> secrtDeptDuptyCheckDuptyRenew(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.secrtDeptDuptyCheckDuptyRenew(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    // 보안담당자 신규/변경 신청 > 보안담당자 정보 - secrtDeptDuptyDuptyInfo
    @Override
    public Map<String, Object> secrtDeptDuptyDuptyInfo(Map<String, Object> paramMap) {
        Map<String, Object> resultView = null;

        try {
            resultView = repository.secrtDeptDuptyDuptyInfo(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultView;
    }

}
