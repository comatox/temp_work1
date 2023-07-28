package com.skshieldus.esecurity.service.secrtactvy.impl;

import com.skshieldus.esecurity.common.component.Mailing;
import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ListDTO;
import com.skshieldus.esecurity.model.common.CommonXlsViewDTO;
import com.skshieldus.esecurity.repository.secrtactvy.SecurityManageItemRepository;
import com.skshieldus.esecurity.service.secrtactvy.SecurityManageItemService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SecurityManageItemImpl implements SecurityManageItemService {

    @Autowired
    private SecurityManageItemRepository repository;

    @Autowired
    private Mailing mailing;

    // 팀내보안 위규자 목록 조회
    @Override
    public ListDTO<Map<String, Object>> selectSecurityConcernCoEmpViolation(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;
        Integer totalCount = 0;

        try {
            resultList = repository.selectSecurityConcernCoEmpViolation(paramMap);
            totalCount = repository.selectSecurityConcernCoEmpViolationCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return ListDTO.getInstance(resultList, totalCount);
    }

    // 팀내보안 위규자 상세 조회
    @Override
    public Map<String, Object> selectSecurityConcernCoEmpViolationView(Map<String, Object> paramMap) {
        Map<String, Object> resultView = null;

        try {
            resultView = repository.selectSecurityConcernCoEmpViolationView(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultView;
    }

    // 팀내보안 위규자 목록 엑셀다운 - securityConcernCoEmpViolationExcel
    @Override
    public CommonXlsViewDTO securityConcernCoEmpViolationExcel(Map<String, Object> paramMap) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        CommonXlsViewDTO commonXlsViewDTO = new CommonXlsViewDTO();
        commonXlsViewDTO.setFileName("팀내구성원보안위규자" + "_" + sdf.format(new Date()));

        try {
            // set header names
            String[] headerNameArr = { "지적일", "지적시간", "안내일", "안내시각", "사번", "성명", "직위",   "부서명", "위규구분", "위규 내용", "위규 내용 상세", "위규조치", "시정계획서/개선계획서 제출여부", "시정계획서/개선계획서 제출일", "시정계획서/개선계획서 경과일", "시정계획서/개선계획서 처리상태", "14일 초과여부" };
            commonXlsViewDTO.setHeaderNameArr(headerNameArr);

            // set column names (data field name)
            String[] columnNameArr = { "ofendDt", "ofendTm", "actDt", "actTm", "ofendEmpId", "empNm", "jwNm",   "deptNm", "ofendGbnNm", "ofendDetailGbnNm", "ofendSubGbnNm", "actDoNm", "corrPlanYn", "sendDtm", "passDate", "apprResultNm", "over14Yn" };
            commonXlsViewDTO.setColumnNameArr(columnNameArr);

            // set column width
            Integer[] columnWidthArr = { 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000 };
            commonXlsViewDTO.setColumnWidthArr(columnWidthArr);

            List<Map<String, Object>> list = repository.securityConcernCoEmpViolationExcel(paramMap);

            // set excel data
            commonXlsViewDTO.setDataList(list);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return commonXlsViewDTO;
    }

    // 타팀 보안담당자 조회
    @Override
    public List<Map<String, Object>> securityDeputyOtherTeamSecList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.securityDeputyOtherTeamSecList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    // 타팀 휴대용 전산저장장치 조회
    @Override
    public List<Map<String, Object>> otherTeamPortableStorageList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.otherTeamPortableStorageList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    // 외부인 보안 위규자 조회
    @Override
    public List<Map<String, Object>> securityConcernTeamViolationIoEmpMeetList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.securityConcernTeamViolationIoEmpMeetList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    // 외부인 보안 위규자 상세 조회
    @Override
    public Map<String, Object> securityConcernTeamViolationIoEmpMeetView(Map<String, Object> paramMap) {
        Map<String, Object> resultView = null;

        try {
            resultView = repository.securityConcernTeamViolationIoEmpMeetView(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultView;
    }

    // 외부인 보안 위규자 상세 조회 엑셀다운로드
    @Override
    public CommonXlsViewDTO securityConcernTeamViolationIoEmpMeetListExcel(Map<String, Object> paramMap) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        CommonXlsViewDTO commonXlsViewDTO = new CommonXlsViewDTO();
        commonXlsViewDTO.setFileName("외부인보안외규자조회" + "_" + sdf.format(new Date()));

        try {
            // set header names
            String[] headerNameArr = { "지적일자", "지적시간", "적발캠퍼스", "회사", "아이디", "성명", "위규구분", "위규내용", "위규조치" };
            commonXlsViewDTO.setHeaderNameArr(headerNameArr);

            // set column names (data field name)
            String[] columnNameArr = { "ofendDt", "ofendTm", "actCompNm", "ofendCompNm", "ofendEmpId", "ofendEmpNm", "ofendGbnNm", "ofendDetailGbnNm", "actDoNm" };
            commonXlsViewDTO.setColumnNameArr(columnNameArr);

            // set column width
            Integer[] columnWidthArr = { 5000, 5000, 7000, 7000, 5000, 5000, 7000, 7000, 5000 };
            commonXlsViewDTO.setColumnWidthArr(columnWidthArr);

            List<Map<String, Object>> list = repository.securityConcernTeamViolationIoEmpMeetListExcel(paramMap);

            // set excel data
            commonXlsViewDTO.setDataList(list);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return commonXlsViewDTO;
    }

    // 정보보호서약서 조회
    @Override
    public List<Map<String, Object>> securityPledgeList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.securityPledgeList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    // 정보보호서약서 조회 엑셀다운로드
    @Override
    public CommonXlsViewDTO securityPledgeListExcel(Map<String, Object> paramMap) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        CommonXlsViewDTO commonXlsViewDTO = new CommonXlsViewDTO();
        commonXlsViewDTO.setFileName("정보보호서약서현황" + "_" + sdf.format(new Date()));

        try {
            // set header names
            String[] headerNameArr = { "NO", "년도", "부서", "사번", "성명", "직위", "서약현황", "서약일", "메일발송여부", "최종메일발송일시" };
            commonXlsViewDTO.setHeaderNameArr(headerNameArr);

            // set column names (data field name)
            String[] columnNameArr = { "rowNum", "pledgeYear", "deptNm", "empId", "empNm", "jwNm", "pledgeYnNm", "pledgeDt", "mailSendYnNm", "sendDtm" };
            commonXlsViewDTO.setColumnNameArr(columnNameArr);

            // set column width
            Integer[] columnWidthArr = { 5000, 5000, 5000, 5000, 5000, 5000, 5000 };
            commonXlsViewDTO.setColumnWidthArr(columnWidthArr);

            List<Map<String, Object>> list = repository.securityPledgeList(paramMap);

            // set excel data
            commonXlsViewDTO.setDataList(list);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return commonXlsViewDTO;
    }

    // selectSecurityPledgeStatus

    @Override
    public Map<String, Object> selectSecurityPledgeStatusList(Map<String, Object> paramMap) {
        Map<String, Object> result = null;

        try {
            if (paramMap.get("year") == null) {
                paramMap.put("year", 3); // 기본으로 3년간 조회하도록 셋팅
            }

            List<Map<String, Object>> dataList = repository.selectSecurityPledgeStatusList(paramMap);

            if (dataList != null && dataList.size() > 0) {
                result = new LinkedHashMap<>();

                // pivot data
                for (Map<String, Object> item : dataList) {
                    result.put(String.valueOf(item.get("year")), item.get("pledgeYn"));
                }
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    // 보안담당부서 List - secDeptList
    @Override
    public List<Map<String, Object>> secDeptList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.secDeptList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    // 팀 보안감점 항목 조회
    //List<Map<String, Object>> securityDeptSecMinList(Map<String, Object> paramMap);
    @Override
    public List<Map<String, Object>> securityDeptSecMinList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.securityDeptSecMinList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    // 팀 보안감점 항목 상세
    //Map<String, Object> securityDeptSecMinView(Map<String, Object> paramMap);
    @Override
    public Map<String, Object> securityDeptSecMinView(Map<String, Object> paramMap) {
        Map<String, Object> resultView = null;

        try {
            resultView = repository.securityDeptSecMinView(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultView;
    }

    // 팀 보안감점 항목 삭제
    //int securityDeptSecMinDelete(Map<String, Object> paramMap);
    @Override
    public int securityDeptSecMinDelete(Map<String, Object> paramMap) {

        int updatedRows = 0;
        try {
            updatedRows = repository.securityDeptSecMinDelete(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return updatedRows;
    }

    // 팀내 무선기기 조회 - wirelessReqList
    @Override
    public List<Map<String, Object>> wirelessReqList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.wirelessReqList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    // 팀내 무선기기 조회 엑셀다운 - wirelessReqListExcel
    @Override
    public CommonXlsViewDTO wirelessReqListExcel(Map<String, Object> paramMap) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        CommonXlsViewDTO commonXlsViewDTO = new CommonXlsViewDTO();
        commonXlsViewDTO.setFileName("무선기기 사용 신청현황" + "_" + sdf.format(new Date()));

        try {
            // set header names
            String[] headerNameArr = { "승인번호", "순서", "캠퍼스", "부서명", "성명", "사번", "연락처", "장치구분", "장치종류", "무선MACADDRESS", "BSSADDRESS", "사용기간", "사유", "처리상태", "문서번호", "신청일" };
            commonXlsViewDTO.setHeaderNameArr(headerNameArr);

            // set column names (data field name)
            String[] columnNameArr = { "wirelessApplNo", "wirelessSeq", "compNm", "deptNm", "empNm", "empId", "innoNo", "deviceTypeNm", "deviceKndNm", "macAddress", "bssAddress", "useDt", "applRsn", "status", "docId", "crtDt" };
            commonXlsViewDTO.setColumnNameArr(columnNameArr);

            // set column width
            Integer[] columnWidthArr = { 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000 };
            commonXlsViewDTO.setColumnWidthArr(columnWidthArr);

            List<Map<String, Object>> list = repository.wirelessReqListExcel(paramMap);

            // set excel data
            commonXlsViewDTO.setDataList(list);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return commonXlsViewDTO;
    }

    // 팀내 무선기기 조회 상세 > 무선기기사용 신청 목록 - wirelessEqInfoReqList
    @Override
    public List<Map<String, Object>> wirelessEqInfoReqList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.wirelessEqInfoReqList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    // 팀 보안점수 조회 - secureEvalTeamScoreList
    @Override
    public List<Map<String, Object>> secureEvalTeamScoreList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.secureEvalTeamScoreList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    // 팀 보안점수 조회 엑셀다운 - secureEvalTeamScoreListExcel
    @Override
    public CommonXlsViewDTO secureEvalTeamScoreListExcel(Map<String, Object> paramMap) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        CommonXlsViewDTO commonXlsViewDTO = new CommonXlsViewDTO();
        commonXlsViewDTO.setFileName("팀보안점수" + "_" + sdf.format(new Date()));

        try {
            // set header names
            //String[] headerNameArr = {"년도", "반기", "부서", "순서", "점검항목", "점검방법", "평가로직", "가감항목", "배점", "점수", "합계" };
            String[] headerNameArr = { "순서", "점검항목", "점검방법", "평가로직", "가감항목", "배점", "점수" };
            commonXlsViewDTO.setHeaderNameArr(headerNameArr);

            // set column names (data field name)
            String[] columnNameArr = { "itemSeq", "subject", "evalItem", "evalStandard", "gagamItem", "evalScore", "score" };
            commonXlsViewDTO.setColumnNameArr(columnNameArr);

            // set column width
            Integer[] columnWidthArr = { 5000, 10000, 15000, 15000, 5000, 5000, 5000 };
            commonXlsViewDTO.setColumnWidthArr(columnWidthArr);

            List<Map<String, Object>> list = repository.secureEvalTeamScoreListExcel(paramMap);

            // set excel data
            commonXlsViewDTO.setDataList(list);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return commonXlsViewDTO;
    }

    // 팀 보안점수 상세 점검항목 List - secureEvalItemTargetList
    @Override
    public List<Map<String, Object>> secureEvalItemTargetList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.secureEvalItemTargetList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    // 팀 보안점수 상세 점검항목 상세 - secureEvalItemDetail
    @Override
    public Map<String, Object> secureEvalItemDetail(Map<String, Object> paramMap) {
        Map<String, Object> resultView = null;

        try {
            resultView = repository.secureEvalItemDetail(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultView;
    }

    // 팀내 전산저장장치조회 - secureStorageManageList
    @Override
    public List<Map<String, Object>> secureStorageManageList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.secureStorageManageList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    // 팀내 전산저장장치조회 엑셀다운 - secureStorageManageListExcel
    @Override
    public CommonXlsViewDTO secureStorageManageListExcel(Map<String, Object> paramMap) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        CommonXlsViewDTO commonXlsViewDTO = new CommonXlsViewDTO();
        commonXlsViewDTO.setFileName("전산저장장치 목록" + "_" + sdf.format(new Date()));

        try {
            // set header names
            String[] headerNameArr = { "신청일", "승인일", "장치유형", "관리번호", "자산번호", "모델명", "시리얼 번호", "Device ID", "용량", "신청자 부서", "신청자 성명", "신청자 사번", "실사용자 부서", "실사용자 성명", "실사용자 사번", "저장자료", "신청사유", "기타사유", "사용여부", "실사결과", "실뭉없음 상세사유", "최종 실사일시", "신 관리번호", "구 관리번호" };
            commonXlsViewDTO.setHeaderNameArr(headerNameArr);

            // set column names (data field name)
            String[] columnNameArr = { "crtDtm", "firstinputdate", "eqmtLstNm", "acserialnoJoin", "assetMainId", "modelname", "serialno2", "deviceId", "capacity", "installdept", "chargename", "empNo", "usdeptNm", "usempNm", "usempNo", "savedataGbnNm", "rsnNm", "etcRsn", "usekndNm", "existkndNm", "useNoReason", "lastupdate", "acserialno", "acserialnoOld" };
            commonXlsViewDTO.setColumnNameArr(columnNameArr);

            // set column width
            Integer[] columnWidthArr = { 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000 };
            commonXlsViewDTO.setColumnWidthArr(columnWidthArr);

            List<Map<String, Object>> list = repository.secureStorageManageListExcel(paramMap);

            // set excel data
            commonXlsViewDTO.setDataList(list);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return commonXlsViewDTO;
    }

    // 팀내 전산저장장치 실사결과 저장 - saveActualInspection
    @Override
    public int saveActualInspection(List<HashMap<String, Object>> paramMaps) {

        int updatedRows = 0;
        try {
            for (HashMap<String, Object> rMap : paramMaps) {
                updatedRows += repository.saveActualInspection(rMap);
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return updatedRows;
    }

    // 팀내 생활보안점검 조회(보안담당자) - secureCoEmpTeamViolationList
    @Override
    public List<Map<String, Object>> secureCoEmpTeamViolationList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.secureCoEmpTeamViolationList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    // 팀내 생활보안점검 조회(보안담당자 ) 엑셀다운  - secureCoEmpTeamViolationListExcel
    @Override
    public CommonXlsViewDTO secureCoEmpTeamViolationListExcel(Map<String, Object> paramMap) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        CommonXlsViewDTO commonXlsViewDTO = new CommonXlsViewDTO();
        commonXlsViewDTO.setFileName("팀내 생활보안점검 조회" + "_" + sdf.format(new Date()));

        try {
            // set header names
            String[] headerNameArr = { "점검일", "점검시각", "부서 Level 2", "부서 Level 3", "부서 Level 4", "부서 Level 5", "부서", "성명", "사번", "직위", "위규구분", "위규상세구분", "점검자 사번", "점검자 성명" };
            commonXlsViewDTO.setHeaderNameArr(headerNameArr);

            // set column names (data field name)
            String[] columnNameArr = { "ofendDt", "ofendTm", "deptLv2", "deptLv3", "deptLv4", "deptLv5", "ofendDeptNm", "ofendEmpNm", "ofendEmpId", "ofendJwNm", "ofendGbnNm", "ofendDetailGbnNm", "empId", "regEmpNm" };
            commonXlsViewDTO.setColumnNameArr(columnNameArr);

            // set column width
            Integer[] columnWidthArr = { 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000 };
            commonXlsViewDTO.setColumnWidthArr(columnWidthArr);

            List<Map<String, Object>> list = repository.secureCoEmpTeamViolationListExcel(paramMap);

            // set excel data
            commonXlsViewDTO.setDataList(list);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return commonXlsViewDTO;
    }

    // 팀내 생활보안점검 조회(보안담당자 ) 상세 - secureCoEmpTeamViolationView
    @Override
    public Map<String, Object> secureCoEmpTeamViolationView(Map<String, Object> paramMap) {
        Map<String, Object> resultView = null;

        try {
            resultView = repository.secureCoEmpTeamViolationView(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultView;
    }

    // 팀내 생활보안점검 조회(보안담당자) 이력조회 - secureCoEmpTeamViolationHist
    @Override
    public List<Map<String, Object>> secureCoEmpTeamViolationHist(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.secureCoEmpTeamViolationHist(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    // 팀내 생활보안점검 조회(보안담당자) 삭제  - secureCoEmpTeamViolationDelete
    @Override
    public int secureCoEmpTeamViolationDelete(Map<String, Object> paramMap) {

        int updatedRows = 0;
        try {
            updatedRows = repository.secureCoEmpTeamViolationDelete(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return updatedRows;
    }

    // 팀내 생활보안점검 조회(보안담당자) 일괄삭제  - secureCoEmpTeamViolationdel
    @Override
    public Boolean secureCoEmpTeamViolationDel(HashMap<String, Object> paramMap) {
        boolean result = false;

        try {

            repository.secureCoEmpTeamViolationDel(paramMap);

            // 처리완료
            result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    // 팀내 생활보안점검 결과(보안담당자) 조회 - secureCoEmpTeamViolationResultList
    @Override
    public List<Map<String, Object>> secureCoEmpTeamViolationResultList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.secureCoEmpTeamViolationResultList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    // 팀내 생활보안점검 등록(일반사용자) > 점검결과 저장 (지적사항없음) - secCoEmpTeamNoViolationInsert
    @Override
    public int secCoEmpTeamNoViolationInsert(Map<String, Object> paramMap) {

        int updatedRows = 0;
        try {
            @SuppressWarnings("unchecked")
            List<String> arrTargetDept = (List<String>) paramMap.get("arrTargetDept");
            // Loop Start
            for (String targetDept : arrTargetDept) {
                if (StringUtils.isNotBlank(targetDept)) {
                    paramMap.put("targetDept", targetDept); // Set - Target Dept ID

                    // INSERT INTO SC_OFEND_TEAM
                    updatedRows = repository.secCoEmpTeamNoViolationInsert(paramMap);
                }
            }
            // End Loop

        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return updatedRows;
    }

    // 팀내 생활보안점검 등록(일반사용자) > 점검결과 저장 (지적사항 있음) - secCoEmpTeamViolationInsert
    @Override
    public int secCoEmpTeamViolationInsert(Map<String, Object> paramMap) {

        int updatedRows = 0;
        try {
            //@SuppressWarnings("unchecked")
            List<Map<String, Object>> arrItems = (List<Map<String, Object>>) paramMap.get("arrItems");
            // Loop Start
            for (Map<String, Object> objItem : arrItems) {
                // 팀내 생활보안점검 > SC_DOC_NO Sequence - secCoEmpViolationSeq
                String scDocNo = repository.secCoEmpViolationSeq(paramMap);
                paramMap.put("scDocNo", scDocNo); // set - Sequence
                paramMap.putAll(objItem); // Map Merge (paramMap + objItem);

                System.out.println("## secCoEmpTeamViolationInsert for paramMap:" + paramMap.toString());
                // INSERT INTO SC_OFEND_TEAM
                updatedRows = repository.secCoEmpTeamViolationInsert(paramMap);
                System.out.println("## secCoEmpTeamViolationInsert updatedRows:" + updatedRows);
                if (updatedRows > 0) {

                    // 팀내 생활보안점검 등록 > 메일발송 대상자 List - secCoEmpViolationSendMailList
                    List<Map<String, Object>> arrMailList = repository.secCoEmpViolationSendMailList(paramMap);
                    System.out.println("## secCoEmpTeamViolationInsert for arrMailList:" + arrMailList.toString());

                    // 팀내 생활보안점검 등록 > 메일대상자 정보 - secCoEmpViolationSendMailDetail
                    // @ Transaction 처리로 insert 후 select 불가
                    // Map<String, Object> retMailInfo = repository.secCoEmpViolationSendMailDetail(paramMap);
                    // System.out.println("## secCoEmpTeamViolationInsert for retMailInfo:" + retMailInfo.toString());

                    if (arrMailList.size() > 0) {
                        // For Loop Start
                        for (Map<String, Object> objMail : arrMailList) {
                            // objMail : GUBUN, EMP_ID, JW_ID, EMP_NM, EMAIL
                            Map<String, Object> mailMap = new HashMap<String, Object>();
                            // 메일발송 대상자 - 위규자 대상정보
                            mailMap.put("ofendEmpId", String.valueOf(objMail.get("empId"))); // 위규자 사번 : 필수
                            mailMap.put("ofendEmpNm", String.valueOf(objMail.get("empNm"))); // 위규자 이름 : 필수
                            mailMap.put("ofendEmpEmail", String.valueOf(objMail.get("email"))); // 위규자 메일 : 필수

                            //								## secCoEmpTeamViolationInsert for
                            //								paramMap:{empId=2049952, ofendDeptNm=DT PMO 경영시스템PI,
                            //								arrItems=[
                            //									{index=0, selected=false, ofendDetailGbn=C0621011, ofendCompId=1101000001, etcRsn=,
                            //									ofendEmpNm=김정민, ofendEmpId=2049952, ofendDeptId=50080604
                            //									, ofendDeptNm=DT PMO 경영시스템PI, ofendJwId=TA, ofendTelNo=010-2109-3757
                            //									}
                            //								], acIp=10.153.24.190, scDocNo=51475, ofendEmpNm=김정민, deptId=50080604, index=0, violationMin=20, ofendTelNo=010-2109-3757, ofendCompId=1101000001, ofendDeptId=50080604, violationHour=13, etcRsn=, crtBy=2049952, compId=1101000001, violationDate=2021-11-25, jwId=TA, ofendGbn=C0611001, ofendEmpId=2049952, selected=false, ofendDetailGbn=C0621011, ofendJwId=TA}

                            // 메일발송 정보
								/*
								mailMap.put("ofendDeptNm", String.valueOf(retMailInfo.get("ofendDeptNm")));
								mailMap.put("ofendJwNm", String.valueOf(retMailInfo.get("ofendJwNm")));
								mailMap.put("ofendDt", String.valueOf(retMailInfo.get("ofendDt")));
								mailMap.put("ofendTm", String.valueOf(retMailInfo.get("ofendTm")));
								mailMap.put("ofendGbnNm", String.valueOf(retMailInfo.get("ofendGbnNm")));
								mailMap.put("ofendDetailNm", String.valueOf(retMailInfo.get("ofendDetailNm")));
								mailMap.put("ofendDetailGbn", String.valueOf(retMailInfo.get("ofendDetailGbn")));
								*/
                            mailMap.put("ofendDetailGbn", String.valueOf(paramMap.get("ofendDetailGbn")));

                            mailMap.put("schemaNm", "생활보안점검");
                            mailMap.put("docNm", "생활보안점검");
                            mailMap.put("crtBy", String.valueOf(paramMap.get("crtBy")));
                            mailMap.put("acIp", String.valueOf(paramMap.get("acIp")));

                            // gubun = 1 일 경우만
                            if ("1".equals(String.valueOf(objMail.get("gubun")))) {
                                mailMap.put("toEmpId", String.valueOf(objMail.get("empId")));  // 메일 수신자 사번 : 필수
                                mailMap.put("toEmpNm", String.valueOf(objMail.get("empNm")));  // 메일 수신자 이름 : 필수
                                mailMap.put("toEmpEmail", String.valueOf(objMail.get("email"))); // 메일 수신자 메일주소 : 필수
                                mailMap.put("scrtView", "SCRT_VIEW");

                                // Mail Send - 팀내 생활보안점검 등록(일반사용자) > 점검결과 저장 (지적사항 있음)
                                this.sendMailCoEmpTeamViolation(mailMap);
                            }
                        } // End Loop
                    }
                }
            } // End Loop
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return updatedRows;
    }

    /**
     * Mail Send - 팀내 생활보안점검 등록(일반사용자) > 점검결과 저장 (지적사항 있음)
     *
     * @param paramMap asis : FU_SendMail.fmCoEmpViolationTeamMailSend
     */
    @SuppressWarnings("unchecked")
    private void sendMailCoEmpTeamViolation(Map<String, Object> paramMap) {

        System.out.println("# sendMailCoEmpTeamViolation paramMap : " + paramMap.toString());

        try {

            String ofendDetailGbn = String.valueOf(paramMap.get("ofendDetailGbn"));

            String title = "팀내 자율 생활보안점검 결과 안내";
            //String message = "팀내 자율 생활보안점검 결과 안내";
            String schemaNm = StringUtils.defaultIfBlank(String.valueOf(paramMap.get("schemaNm")), "");
            String acIp = String.valueOf(paramMap.get("acIp"));

            String sendToEmail = String.valueOf(paramMap.get("toEmpEmail"));
            String empId = String.valueOf(paramMap.get("toEmpId"));

            StringBuffer str = new StringBuffer();
            str.append("					<table><tr><td>");
            if (ofendDetailGbn.trim().equals("C0621001") || ofendDetailGbn.trim().equals("C0621009")) //개인서랍 /캐비넷
                str.append("						<img src='https://welcome.skhynix.com/esecurity/assets/common/images/common/subTitle/secrt/team_violation_mail_01.jpg' />");
            else if (ofendDetailGbn.trim().equals("C0621002") || ofendDetailGbn.trim().equals("C0621010"))//노트북 /저장매체
                str.append("						<img src='https://welcome.skhynix.com/esecurity/assets/common/images/common/subTitle/secrt/team_violation_mail_02.jpg' />");
            else if (ofendDetailGbn.trim().equals("C0621003")) //업무문서 방치
                str.append("						<img src='https://welcome.skhynix.com/esecurity/assets/common/images/common/subTitle/secrt/team_violation_mail_03.jpg' />");
            else if (ofendDetailGbn.trim().equals("C0621004")) //열쇠방치
                str.append("						<img src='https://welcome.skhynix.com/esecurity/assets/common/images/common/subTitle/secrt/team_violation_mail_04.jpg' />");
            else if (ofendDetailGbn.trim().equals("C0621005")) //PC화면 미잠금
                str.append("						<img src='https://welcome.skhynix.com/esecurity/assets/common/images/common/subTitle/secrt/team_violation_mail_05.jpg' />");
            else if (ofendDetailGbn.trim().equals("C0621006")) //화이트보드 미삭제
                str.append("						<img src='https://welcome.skhynix.com/esecurity/assets/common/images/common/subTitle/secrt/team_violation_mail_06.jpg' />");
            else if (ofendDetailGbn.trim().equals("C0621007")) //일반용지 보관
                str.append("						<img src='https://welcome.skhynix.com/esecurity/assets/common/images/common/subTitle/secrt/team_violation_mail_07.jpg' />");
            else if (ofendDetailGbn.trim().equals("C0621008"))//보안스티커 보관
                str.append("						<img src='https://welcome.skhynix.com/esecurity/assets/common/images/common/subTitle/secrt/team_violation_mail_08.jpg' />");
            else if (ofendDetailGbn.trim().equals("C0621011"))//대외비 방치
                str.append("						<img src='https://welcome.skhynix.com/esecurity/assets/common/images/common/subTitle/secrt/team_violation_mail_09.jpg' />");
            else if (ofendDetailGbn.trim().equals("C0621012"))//Wafer 방치
                str.append("						<img src='https://welcome.skhynix.com/esecurity/assets/common/images/common/subTitle/secrt/team_violation_mail_10.jpg' />");
            else
                str.append("						<a>생활보안 점검 내용 없음</a>");

            str.append("					</td></tr></table>");

            // (String title, String content, String to, String empNo, String schemaNm, String docId, String acIp)
            // Call SendMail
            boolean result = mailing.sendMail(title, mailing.applyMailTemplate(title, str.toString()), sendToEmail, empId, schemaNm, String.valueOf(paramMap.get("docNm")), acIp);
            System.out.println("# sendMailCoEmpTeamViolation result : " + result);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
    }

    // 문서출력량조회 List - selectSecurityPrintingList
    @Override
    public List<Map<String, Object>> selectSecurityPrintingList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {

            String searchDeptId = StringUtils.defaultIfEmpty(String.valueOf(paramMap.get("searchDeptId")), "");

            List<String> listDeptId = new ArrayList<String>();
            List<String> listDeptNm = new ArrayList<String>();

            if ("ALL".equals(searchDeptId)) {
                // 부서 List - selectSecurityPrintingDeptList
                List<Map<String, Object>> dbDeptList = repository.selectSecurityPrintingDeptList(paramMap);
                for (Map<String, Object> objDept : dbDeptList) {
                    listDeptId.add(String.valueOf(objDept.get("deptId")));
                    listDeptNm.add(String.valueOf(objDept.get("deptNm")));
                }
            }
            else {
                listDeptId.add(String.valueOf(paramMap.get("searchDeptId")));
                listDeptNm.add(String.valueOf(paramMap.get("searchDeptNm")));
            }
            String[] searchDeptIdList = listDeptId.toArray(new String[0]);
            //paramMap.put("searchDeptIdList", searchDeptIdList); // String Array
            paramMap.put("searchDeptIdList", listDeptId); // ArrayList
            String[] searchDeptNmList = listDeptNm.toArray(new String[0]);
            //paramMap.put("searchDeptNmList", searchDeptNmList); // String Array
            paramMap.put("searchDeptNmList", listDeptNm); // ArrayList
            //				System.out.println("#### searchDeptIdList  searchDeptIdList:" + Arrays.toString(searchDeptIdList));
            //				System.out.println("#### searchDeptIdList  searchDeptNmList:" + Arrays.toString(searchDeptNmList));
            //				System.out.println("#### selectSecurityPrintingList  paramsMap:" + paramMap.toString());

            // PrintChaserRepository (MSSQL DB)
            // 주석처리 2023-06-09
            //            resultList = printChaserRepository.selectSecurityPrintingList(paramMap);
            // Test Dummy 문서출력량조회
            //resultList = repository.dummyPrintingList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    // 문서출력량조회 > 부서 List - selectSecurityPrintingDeptList
    @Override
    public List<Map<String, Object>> selectSecurityPrintingDeptList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectSecurityPrintingDeptList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    // 문서출력량조회 List 엑셀다운 - selectSecurityPrintingExcel
    @Override
    public CommonXlsViewDTO selectSecurityPrintingExcel(Map<String, Object> paramMap) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        CommonXlsViewDTO commonXlsViewDTO = new CommonXlsViewDTO();
        commonXlsViewDTO.setFileName("문서출력량 현황" + "_" + sdf.format(new Date()));

        // 주석처리 2023-06-09
        //        try {
        //            // set header names
        //            String[] headerNameArr = {"NO", "출력일", "출력시간", "출력량", "부서명", "사번", "성명", "직위", "용지규격"};
        //            commonXlsViewDTO.setHeaderNameArr(headerNameArr);
        //
        //            // set column names (data field name)
        //            String[] columnNameArr = {"rowNum", "printingDate", "printingTime", "printingPageAmount", "printingDeptNm", "printingEmpId", "printingEmpNm", "printingJwNm", "printingPaperType"};
        //            commonXlsViewDTO.setColumnNameArr(columnNameArr);
        //
        //            // set column width
        //            Integer[] columnWidthArr = {5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000};
        //            commonXlsViewDTO.setColumnWidthArr(columnWidthArr);
        //
        //
        //            // set paramMap
        //            String searchDeptId = StringUtils.defaultIfEmpty(String.valueOf(paramMap.get("searchDeptId")), "");
        //
        //            List<String> listDeptId = new ArrayList<String>();
        //            List<String> listDeptNm = new ArrayList<String>();
        //            if ("ALL".equals(searchDeptId)) {
        //                // 부서 List - selectSecurityPrintingDeptList
        //                List<Map<String, Object>> dbDeptList = repository.selectSecurityPrintingDeptList(paramMap);
        //                for (Map<String, Object> objDept : dbDeptList) {
        //                    listDeptId.add(String.valueOf(objDept.get("deptId")));
        //                    listDeptNm.add(String.valueOf(objDept.get("deptNm")));
        //                }
        //            } else {
        //                listDeptId.add(String.valueOf(paramMap.get("searchDeptId")));
        //                listDeptNm.add(String.valueOf(paramMap.get("searchDeptNm")));
        //            }
        //            String[] searchDeptIdList = listDeptId.toArray(new String[0]);
        //            paramMap.put("searchDeptIdList", listDeptId);
        //            String[] searchDeptNmList = listDeptNm.toArray(new String[0]);
        //            paramMap.put("searchDeptNmList", listDeptNm);
        //            System.out.println("#### searchDeptIdList  searchDeptIdList:" + Arrays.toString(searchDeptIdList));
        //            System.out.println("#### searchDeptIdList  searchDeptNmList:" + Arrays.toString(searchDeptNmList));
        //
        //            // PrintChaserRepository (MSSQL DB)
        //            // 주석처리 2023-06-09
        ////            List<Map<String, Object>> list = printChaserRepository.selectSecurityPrintingExcel(paramMap);
        //
        //            // set excel data
        //            commonXlsViewDTO.setDataList(list);
        //        } catch (Exception e) {
        //            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        //        }
        return commonXlsViewDTO;
    }

}
