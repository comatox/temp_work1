package com.skshieldus.esecurity.service.secrtactvy.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ListDTO;
import com.skshieldus.esecurity.model.common.ApprovalDTO;
import com.skshieldus.esecurity.model.common.ApprovalDocDTO;
import com.skshieldus.esecurity.model.common.CommonXlsViewDTO;
import com.skshieldus.esecurity.repository.secrtactvy.SecurityEduPledgeRepository;
import com.skshieldus.esecurity.service.common.ApprovalService;
import com.skshieldus.esecurity.service.secrtactvy.SecurityEduPledgeService;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class SecurityEduPledgeImpl implements SecurityEduPledgeService {

    @Autowired
    private ApprovalService approvalService;

    @Autowired
    private SecurityEduPledgeRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<Map<String, Object>> selectPositionMovePledgeList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectPositionMovePledgeList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Integer selectPositionMovePledgeListCnt(Map<String, Object> paramMap) {
        Integer resultCnt = null;

        try {
            resultCnt = repository.selectPositionMovePledgeListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

    @Override
    public Map<String, Object> selectPositionMovePledge(Integer coPositionPledgeNo) {
        Map<String, Object> result = null;

        try {
            result = repository.selectPositionMovePledge(coPositionPledgeNo);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Boolean insertPositionMovePledge(Map<String, Object> paramMap) {
        int result = 0;

        try {
            result = repository.insertPositionMovePledge(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result == 1;
    }

    @Override
    public CommonXlsViewDTO selectPositionMovePledgeViewDownload(Map<String, Object> paramMap) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        CommonXlsViewDTO commonXlsViewDTO = new CommonXlsViewDTO();
        commonXlsViewDTO.setFileName("보직이동서약 현황" + "_" + sdf.format(new Date()));

        // set header names
        String[] headerNameArr = { "서약일", "사번", "성명", "부서명", "직위", "내용" };
        commonXlsViewDTO.setHeaderNameArr(headerNameArr);

        // set column names (data field name)
        String[] columnNameArr = { "pledgeDt", "empId", "empNm", "deptNm", "jwNm", "workDesc" };
        commonXlsViewDTO.setColumnNameArr(columnNameArr);

        // set column width
        Integer[] columnWidthArr = { 7000, 7000, 7000, 7000, 7000, 15000 };
        commonXlsViewDTO.setColumnWidthArr(columnWidthArr);

        List<Map<String, Object>> list = repository.selectPositionMovePledgeList(paramMap);

        // set excel data
        commonXlsViewDTO.setDataList(list);

        return commonXlsViewDTO;
    }

    @Override
    public List<Map<String, Object>> selectProtectionPledgeList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectProtectionPledgeList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Integer selectProtectionPledgeListCnt(Map<String, Object> paramMap) {
        Integer resultCnt = null;

        try {
            resultCnt = repository.selectProtectionPledgeListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

    @Override
    public Map<String, Object> selectProtectionPledge(Integer coPositionPledgeNo) {
        Map<String, Object> result = null;

        try {
            result = repository.selectProtectionPledge(coPositionPledgeNo);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> selectIoProtectionPledgeList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectIoProtectionPledgeList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Integer selectIoProtectionPledgeListCnt(Map<String, Object> paramMap) {
        Integer resultCnt = null;

        try {
            resultCnt = repository.selectIoProtectionPledgeListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

    @Override
    public List<Map<String, Object>> selectIoProtectionPledgeListByUser(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectIoProtectionPledgeListByUser(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Integer selectIoProtectionPledgeListCntByUser(Map<String, Object> paramMap) {
        Integer resultCnt = null;

        try {
            resultCnt = repository.selectIoProtectionPledgeListCntByUser(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

    @Override
    public Map<String, Object> selectIoProtectionPledge(Integer specialPledgeNo) {
        Map<String, Object> result = null;

        try {
            result = repository.selectIoProtectionPledge(specialPledgeNo);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Boolean insertProtectionPledge(Map<String, Object> paramMap) {
        int result = 0;

        try {
            result = repository.insertProtectionPledge(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result == 1;
    }

    @Override
    public CommonXlsViewDTO selectProtectionPledgeViewDownload(Map<String, Object> paramMap) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        CommonXlsViewDTO commonXlsViewDTO = new CommonXlsViewDTO();
        commonXlsViewDTO.setFileName("국가핵심기술보안서약 현황" + "_" + sdf.format(new Date()));

        // set header names
        String[] headerNameArr = { "서약일", "사번", "이름", "부서", "직위" };
        commonXlsViewDTO.setHeaderNameArr(headerNameArr);

        // set column names (data field name)
        String[] columnNameArr = { "pledgeDt", "empId", "empNm", "deptNm", "jwNm" };
        commonXlsViewDTO.setColumnNameArr(columnNameArr);

        // set column width
        Integer[] columnWidthArr = { 7000, 7000, 7000, 7000, 7000 };
        commonXlsViewDTO.setColumnWidthArr(columnWidthArr);

        List<Map<String, Object>> list = repository.selectProtectionPledgeList(paramMap);

        // set excel data
        commonXlsViewDTO.setDataList(list);

        return commonXlsViewDTO;
    }

    @Override
    public CommonXlsViewDTO selectIoProtectionPledgeViewDownload(Map<String, Object> paramMap) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        CommonXlsViewDTO commonXlsViewDTO = new CommonXlsViewDTO();
        commonXlsViewDTO.setFileName("국가핵심기술보안서약 현황" + "_" + sdf.format(new Date()));

        // set header names
        String[] headerNameArr = { "서약일", "아이디", "이름", "소속", "직위" };
        commonXlsViewDTO.setHeaderNameArr(headerNameArr);

        // set column names (data field name)
        String[] columnNameArr = { "pledgeDt", "ioEmpId", "ioEmpNm", "ioCompNm", "ioEmpJwNm" };
        commonXlsViewDTO.setColumnNameArr(columnNameArr);

        // set column width
        Integer[] columnWidthArr = { 7000, 7000, 7000, 7000, 7000 };
        commonXlsViewDTO.setColumnWidthArr(columnWidthArr);

        List<Map<String, Object>> list = repository.selectIoProtectionPledgeList(paramMap);

        // set excel data
        commonXlsViewDTO.setDataList(list);

        return commonXlsViewDTO;
    }

    @Override
    public List<Map<String, Object>> selectSecurityEducationRequestList(HashMap<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectSecurityEducationRequestList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Map<String, Object> selectSecurityEducationRequestView(HashMap<String, Object> paramMap) {
        Map<String, Object> result = null;

        try {
            result = repository.selectSecurityEducationRequestView(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> selectEducationTargetList(HashMap<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectEducationTargetList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public List<Map<String, Object>> selectEducationReservationStatus(HashMap<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectEducationReservationStatus(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public List<Map<String, Object>> selectSpecialTaskList(HashMap<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            String type = (String) paramMap.get("type");

            if (type == null || "".equals(type)) {
                throw new Exception("필수파라미터가 없습니다.");
            }

            //type = C:구성원 조회, I:외부인 조회
            if ("C".equals(type)) {
                resultList = repository.selectSpecialTaskCoList(paramMap);
            }
            else {

                String adminYn = paramMap.get("adminYn") != null
                    ? (String) paramMap.get("adminYn")
                    : "N";

                if ("N".equals(adminYn)) {
                    resultList = repository.selectSpecialTaskIoList(paramMap);
                }
                else {
                    resultList = repository.selectSpecialTaskIoListAdm(paramMap);
                }
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Map<String, Object> selectSpecialTaskDetail(HashMap<String, Object> paramMap) {
        Map<String, Object> result = null;

        try {
            String type = (String) paramMap.get("type");

            if (type == null || "".equals(type)) {
                throw new Exception("필수파라미터가 없습니다.");
            }

            //type = C:구성원, I:외부인
            if ("C".equals(type)) {
                result = repository.selectSpecialTaskCoDetail(paramMap);
            }
            else {
                result = repository.selectSpecialTaskIoDetail(paramMap);
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> selectDataProvideList(HashMap<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectDataProvideList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public CommonXlsViewDTO dataProvideListDownload(HashMap<String, Object> paramMap) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        CommonXlsViewDTO commonXlsViewDTO = new CommonXlsViewDTO();
        commonXlsViewDTO.setFileName("자료 제공 요청 및 파기 확인 현황" + "_" + sdf.format(new Date()));

        // set header names
        String[] headerNameArr = { "요청자", "요청자료목록", "요청일", "파기자료목록", "파기일" };
        commonXlsViewDTO.setHeaderNameArr(headerNameArr);

        // set column names (data field name)
        String[] columnNameArr = { "ioEmpNm", "dataDesc", "modDtm", "destroyDataDesc", "destroyDate" };
        commonXlsViewDTO.setColumnNameArr(columnNameArr);

        // set column width
        Integer[] columnWidthArr = { 7000, 7000, 7000, 7000, 7000 };
        commonXlsViewDTO.setColumnWidthArr(columnWidthArr);

        List<Map<String, Object>> list = repository.dataProvideListExcel(paramMap);

        // set excel data
        commonXlsViewDTO.setDataList(list);

        return commonXlsViewDTO;
    }

    @Override
    public Map<String, Object> selectProvideDetail(HashMap<String, Object> paramMap) {
        Map<String, Object> result = null;

        try {
            result = repository.selectProvideDetail(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Map<String, Object> selectDestroyDetail(HashMap<String, Object> paramMap) {
        Map<String, Object> result = null;

        try {
            result = repository.selectDestroyDetail(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> searchIoCompList(HashMap<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            String compKoNm = (String) paramMap.get("compKoNm");

            // 한글 회사 이름이 공백이거나 null 인 경우.
            if (compKoNm.length() < 2 || compKoNm.substring(0, 2).equals("%%") || compKoNm == null || "".equals(compKoNm.trim())) {
                return resultList;
            }

            Object index = paramMap.get("currentPage");
            Object size = paramMap.get("rowPerPage");
            if (index == null)
                paramMap.put("currentPage", "1");
            if (size == null)
                paramMap.put("rowPerPage", "10");

            resultList = repository.searchIoCompList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public int searchIoCompListCnt(HashMap<String, Object> paramMap) {
        int totCnt = 0;
        try {
            String compKoNm = (String) paramMap.get("compKoNm");

            // 한글 회사 이름이 공백이거나 null 인 경우.
            if (compKoNm.length() < 2 || compKoNm.substring(0, 2).equals("%%") || compKoNm == null || "".equals(compKoNm.trim())) {
                return 0;
            }

            totCnt = repository.searchIoCompListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return totCnt;
    }

    @Override
    public List<Map<String, Object>> searchDeptList(HashMap<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {

            Object index = paramMap.get("currentPage");
            Object size = paramMap.get("rowPerPage");
            if (index == null)
                paramMap.put("currentPage", "1");
            if (size == null)
                paramMap.put("rowPerPage", "10");

            resultList = repository.searchDeptList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public int searchDeptListCnt(HashMap<String, Object> paramMap) {
        int totCnt = 0;
        try {

            totCnt = repository.searchDeptListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return totCnt;
    }

    @Override
    public Boolean insertSecurityEdu(HashMap<String, Object> paramMap) {
        boolean result = false;

        // 신규 채번
        String getLecReqNo = repository.getSecurityEduCationSeq(paramMap);
        paramMap.put("lecReqNo", getLecReqNo);

        // 마스터 정보 insert (SC_LEC_REQ_M)
        int resultCnt = repository.insertSecurityEduReq(paramMap);

        List eduTargetList = (List) paramMap.get("eduTargetList");

        if (eduTargetList != null && eduTargetList.size() > 0) {

            repository.insertSecurityEduReqSub(paramMap);
        }

        try {
            // ================= NOTE: [통합결재정보] 저장 시작 =======================
            ApprovalDTO approval = objectMapper.convertValue(paramMap.get("approval"), ApprovalDTO.class);
            Map<String, Object> htmlMap = objectMapper.convertValue(paramMap.get("htmlMap"), HashMap.class);
            int lecReqNo = objectMapper.convertValue(paramMap.get("lecReqNo"), Integer.class);

            approval.setLid(lecReqNo);
            approval.setHtmlMap(htmlMap);
            log.info(">>>> insertRectifyPlan approval setLid: " + approval);

            ApprovalDocDTO approvalDoc = approvalService.insertApproval(approval);
            Integer docId = approvalDoc.getDocId();
            paramMap.put("docId", docId);
            // ================= NOTE: [통합결재정보] 저장 종료 =======================

            // 결재문서번호 UPDATE
            resultCnt = repository.updateSecurityEduDocId(paramMap);

            // 처리완료
            if (resultCnt > 0)
                result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Boolean specialTaskPledgeSave(HashMap<String, Object> paramMap) {
        boolean result = false;

        try {

            int cnt = repository.specialTaskPledgeSave(paramMap);

            // 처리완료
            if (cnt > 0) {
                result = true;
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public CommonXlsViewDTO securityEducationRequestListDownload(HashMap<String, Object> paramMap) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        CommonXlsViewDTO commonXlsViewDTO = new CommonXlsViewDTO();
        commonXlsViewDTO.setFileName("보안교육 신청 현황" + "_" + sdf.format(new Date()));
        // set header names
        String[] headerNameArr = { "교육일시", "부문", "신청부서", "현재부서", "신청자", "교육장소", "대상인원", "처리상태" };
        commonXlsViewDTO.setHeaderNameArr(headerNameArr);

        // set column names (data field name)
        String[] columnNameArr = { "lecDt", "deptBumNm", "applyDeptNm", "currDeptNm", "empNm", "lecPlace", "lecManCnt", "apprResultNm" };
        commonXlsViewDTO.setColumnNameArr(columnNameArr);

        // set column width
        Integer[] columnWidthArr = { 7000, 7000, 7000, 7000, 7000, 7000, 7000, 7000 };
        commonXlsViewDTO.setColumnWidthArr(columnWidthArr);

        List<Map<String, Object>> list = repository.securityEducationRequestExcel(paramMap);

        // set excel data
        commonXlsViewDTO.setDataList(list);

        return commonXlsViewDTO;
    }

    @Override
    public CommonXlsViewDTO specialTaskPledgeListDownload(HashMap<String, Object> paramMap) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        CommonXlsViewDTO commonXlsViewDTO = new CommonXlsViewDTO();

        try {
            String type = (String) paramMap.get("type");

            if (type == null || "".equals(type)) {
                throw new Exception("필수파라미터가 없습니다.");
            }

            //type = C:구성원, I:외부인
            if ("C".equals(type)) {

                commonXlsViewDTO.setFileName("특수업무 수행 서약 현황_구성원" + "_" + sdf.format(new Date()));

                // set header names
                String[] headerNameArr = { "서약일", "사번", "이름", "소속", "직위", "구체적인 업무 내용" };
                commonXlsViewDTO.setHeaderNameArr(headerNameArr);

                // set column names (data field name)
                String[] columnNameArr = { "pledgeDt", "empId", "empNm", "deptNm", "jwNm", "workDesc" };
                commonXlsViewDTO.setColumnNameArr(columnNameArr);

                // set column width
                Integer[] columnWidthArr = { 7000, 7000, 7000, 7000, 7000, 7000 };
                commonXlsViewDTO.setColumnWidthArr(columnWidthArr);

                List<Map<String, Object>> list = repository.selectSpecialTaskCoListExcel(paramMap);

                // set excel data
                commonXlsViewDTO.setDataList(list);
            }
            else {
                commonXlsViewDTO.setFileName("특수업무 수행 서약 현황_외부인" + "_" + sdf.format(new Date()));

                // set header names
                String[] headerNameArr = { "서약일", "사번", "이름", "소속", "직위", "구체적인 업무 내용" };
                commonXlsViewDTO.setHeaderNameArr(headerNameArr);

                // set column names (data field name)
                String[] columnNameArr = { "pledgeDt", "ioEmpId", "ioEmpNm", "ioCompNm", "ioEmpJwNm", "workDesc" };
                commonXlsViewDTO.setColumnNameArr(columnNameArr);

                // set column width
                Integer[] columnWidthArr = { 7000, 7000, 7000, 7000, 7000, 7000 };
                commonXlsViewDTO.setColumnWidthArr(columnWidthArr);

                List<Map<String, Object>> list = repository.selectSpecialTaskIoListExcel(paramMap);

                // set excel data
                commonXlsViewDTO.setDataList(list);
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return commonXlsViewDTO;
    }

    @Override
    public ListDTO<Map<String, Object>> selectSecurityEducationInfoAdmList(HashMap<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;
        Integer count = 0;

        try {
            resultList = repository.selectSecurityEducationInfoAdmList(paramMap);
            count = repository.selectSecurityEducationInfoAdmCount(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return ListDTO.getInstance(resultList, count);
    }

}
