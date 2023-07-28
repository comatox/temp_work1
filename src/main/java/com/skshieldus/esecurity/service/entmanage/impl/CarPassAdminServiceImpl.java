package com.skshieldus.esecurity.service.entmanage.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.model.common.CommonXlsViewDTO;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.repository.entmanage.CarPassAdminRepository;
import com.skshieldus.esecurity.service.entmanage.CarPassAdminService;
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
public class CarPassAdminServiceImpl implements CarPassAdminService {

    @Autowired
    private CarPassAdminRepository carPassAdminReqstRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Map<String, Object> selectIoCarPassInOutSttsList(Map<String, Object> paramMap) {

        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>> resultList;
        int totalCount;

        try {
            // 목록 조회
            resultList = carPassAdminReqstRepository.selectIoCarPassInOutSttsList(paramMap);
            totalCount = carPassAdminReqstRepository.selectIoCarPassInOutSttsListCnt(paramMap);

            resultMap.put("list", resultList);
            resultMap.put("totalCount", totalCount);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultMap;
    }

    @Override
    public Map<String, Object> selectIoIcCarQuotaList(Map<String, Object> paramMap) {
        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>> resultList;
        int totalCount;

        try {
            // 목록 조회
            resultList = carPassAdminReqstRepository.selectIoIcCarQuotaList(paramMap);
            totalCount = carPassAdminReqstRepository.selectIoIcCarQuotaListCnt(paramMap);

            resultMap.put("list", resultList);
            resultMap.put("totalCount", totalCount);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultMap;
    }

    @Override
    public Map<String, Object> selectIoCarQuota(Map<String, Object> paramMap) {
        Map<String, Object> result = null;

        try {
            // 목록 조회
            result = carPassAdminReqstRepository.selectIoCarQuota(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return result;
    }

    @Override
    public boolean updateIoCarQuota(SessionInfoVO sessionInfo, Map<String, Object> paramMap) {
        boolean result = false;

        try {

            Map<String, String> userInfo = objectMapper.convertValue(paramMap.get("userInfo"),
                new TypeReference<Map<String, String>>() {
                });

            paramMap.put("modBy", String.valueOf(userInfo.get("empId")));
            paramMap.put("acIp", sessionInfo.getIp());

            // 업데이트
            carPassAdminReqstRepository.updateIoCarQuota(paramMap);
            result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return result;
    }

    @Override
    public boolean updateIoCarQuotaDelYn(SessionInfoVO sessionInfo, Map<String, Object> paramMap) {
        boolean result = false;

        try {

            Map<String, String> userInfo = objectMapper.convertValue(paramMap.get("userInfo"),
                new TypeReference<Map<String, String>>() {
                });

            paramMap.put("modBy", String.valueOf(userInfo.get("empId")));

            // 업데이트
            carPassAdminReqstRepository.updateIoCarQuotaDelYn(paramMap);
            result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return result;
    }

    @Override
    public Map<String, Object> selectIoCarPassProgressList(Map<String, Object> paramMap) {

        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>> resultList;
        int totalCount;

        try {
            // 목록 조회
            resultList = carPassAdminReqstRepository.selectIoCarPassProgressList(paramMap);
            totalCount = carPassAdminReqstRepository.selectIoCarPassProgressListCnt(paramMap);

            resultMap.put("list", resultList);
            resultMap.put("totalCount", totalCount);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultMap;
    }

    @Override
    public CommonXlsViewDTO selectIoCarPassProgressListExcel(Map<String, Object> paramMap) {

        List<Map<String, Object>> resultList = null;
        CommonXlsViewDTO commonXlsViewDTO = new CommonXlsViewDTO();
        commonXlsViewDTO.setFileName("임시차량출입 신청 현황");

        try {

            // 페이징 처리 Skip위해 추가
            paramMap.put("excelYn", "Y");

            // 차량출입신청 관리자 리스트 조회
            resultList = carPassAdminReqstRepository.selectIoCarPassProgressList(paramMap);

            // set header names
            String[] headerNameArr = { "요청회사", "구분", "방문일", "성명", "차량번호", "주차장소", "방문목적", "입문", "출문", "진행결과" };

            commonXlsViewDTO.setHeaderNameArr(headerNameArr);

            // set column names (data field name)
            String[] columnNameArr = { "compNm", "gubun", "ioDt", "empNm", "carNo", "vstPlcNm", "vstObjNm", "inDtm", "outDtm", "apprResultNm" };
            commonXlsViewDTO.setColumnNameArr(columnNameArr);

            // set column width
            Integer[] columnWidthArr = { 7000, 5000, 5000, 7000, 7000, 5000, 5000, 5000, 5000, 5000 };
            commonXlsViewDTO.setColumnWidthArr(columnWidthArr);

            // set excel data
            commonXlsViewDTO.setDataList(resultList);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return commonXlsViewDTO;
    }

    @Override
    public boolean updateInOut(SessionInfoVO sessionInfo, Map<String, Object> paramMap) {

        boolean result = false;

        try {

            Map<String, String> userInfo = objectMapper.convertValue(paramMap.get("userInfo"),
                new TypeReference<Map<String, String>>() {
                });

            paramMap.put("modBy", String.valueOf(userInfo.get("empId")));

            // 업데이트
            carPassAdminReqstRepository.updateInOut(paramMap);
            result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return result;
    }

    @Override
    public Map<String, Object> selectAllCarPassList(Map<String, Object> paramMap) {
        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>> resultList;
        int totalCount;

        try {
            // 목록 조회
            resultList = carPassAdminReqstRepository.selectAllCarPassList(paramMap);
            totalCount = carPassAdminReqstRepository.selectAllCarPassListCnt(paramMap);

            resultMap.put("list", resultList);
            resultMap.put("totalCount", totalCount);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return resultMap;
    }

    @Override
    public CommonXlsViewDTO selectCarPassAdminListExcel(Map<String, Object> paramMap) {

        List<Map<String, Object>> resultList = null;
        CommonXlsViewDTO commonXlsViewDTO = new CommonXlsViewDTO();
        commonXlsViewDTO.setFileName("차량출입 신청관리 현황");

        try {

            // 차량출입신청 관리자 리스트 조회
            resultList = carPassAdminReqstRepository.selectAllCarPassListExcel(paramMap);

            // set header names
            String[] headerNameArr = { "신청구분", "신청자", "출입시작일", "출입종료일", "진행상태", "차량번호", "차종", "신청사유" };

            commonXlsViewDTO.setHeaderNameArr(headerNameArr);

            // set column names (data field name)
            String[] columnNameArr = { "gubun", "empJwNm", "strtDt", "endDt", "apprResultNm", "carNo", "carKnd", "rsn" };
            commonXlsViewDTO.setColumnNameArr(columnNameArr);

            // set column width
            Integer[] columnWidthArr = { 4000, 12000, 7000, 7000, 5000, 5000, 5000, 15000 };
            commonXlsViewDTO.setColumnWidthArr(columnWidthArr);

            // set excel data
            commonXlsViewDTO.setDataList(resultList);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return commonXlsViewDTO;
    }

    @Override public Map<String, Object> selectSafetyCarPassProgressList(Map<String, Object> paramMap) {
        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>> resultList;
        int totalCount;

        try {
            // 목록 조회
            resultList = carPassAdminReqstRepository.selectSafetyCarPassProgressList(paramMap);
            totalCount = carPassAdminReqstRepository.selectSafetyCarPassProgressListCnt(paramMap);

            resultMap.put("list", resultList);
            resultMap.put("totalCount", totalCount);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return resultMap;
    }

    @Override public CommonXlsViewDTO selectSafetyCarPassProgressListExcel(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;
        CommonXlsViewDTO commonXlsViewDTO = new CommonXlsViewDTO();
        commonXlsViewDTO.setFileName("안전작업차량 출입 현황");

        try {

            // 안전작업차량 관리자 리스트 조회
            resultList = carPassAdminReqstRepository.selectSafetyCarPassProgressListExcel(paramMap);

            // set header names
            String[] headerNameArr = {
                "승인번호",
                "문서ID",
                "요청회사",
                "신청자부서",
                "신청자이름",
                "출입기간",
                "신청날짜",
                "차량출입구분",
                "작업명",
                "작업목적",
                "작업업체명",
                "장비번호",
                "장비종류",
                "결재상태",
            };

            commonXlsViewDTO.setHeaderNameArr(headerNameArr);

            // set column names (data field name)
            String[] columnNameArr = {
                "tmpcarAppNo",
                "docId",
                "compNm",
                "empNm",
                "BDeptNm",
                "crtDtm",
                "ioDt",
                "appNm",
                "applObj",
                "applRsn",
                "carCompNm",
                "carNo",
                "carKnd",
                "apprResultNm",
            };
            commonXlsViewDTO.setColumnNameArr(columnNameArr);

            // set column width
            Integer[] columnWidthArr = {
               500,
               5000,
               7000,
               7000,
               5000,
               5000,
               5000,
               5000,
               7000,
               5000,
               5000,
               5000,
               5000,
               5000,
            };
            commonXlsViewDTO.setColumnWidthArr(columnWidthArr);

            // set excel data
            commonXlsViewDTO.setDataList(resultList);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return commonXlsViewDTO;
    }

    @Override public Map<String, Object> selectTmpcarPassProgressList(Map<String, Object> paramMap) {
        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>> resultList;
        int totalCount;

        try {
            // 목록 조회
            resultList = carPassAdminReqstRepository.selectTmpcarPassProgressList(paramMap);
            totalCount = carPassAdminReqstRepository.selectTmpcarPassProgressListCnt(paramMap);

            resultMap.put("list", resultList);
            resultMap.put("totalCount", totalCount);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return resultMap;
    }

    @Override public CommonXlsViewDTO selectTmpcarPassProgressListExcel(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;
        CommonXlsViewDTO commonXlsViewDTO = new CommonXlsViewDTO();
        commonXlsViewDTO.setFileName("임시차량 출입관리 현황");

        try {

            // 임시차량 출입관리 관리자 리스트 조회
            resultList = carPassAdminReqstRepository.selectTmpcarPassProgressListExcel(paramMap);

            // set header names
            String[] headerNameArr = { "신청사유", "신규/연장", "출입기간", "출입차종", "출입차량번호", "출입장소", "출입사유", "소속", "직위", "이름", "팀명", "이름", "사번", "결재자 Comment" };

            commonXlsViewDTO.setHeaderNameArr(headerNameArr);

            // set column names (data field name)
            String[] columnNameArr = {
                "appNm",
                "applNm",
                "ioDt",
                "carKnd",
                "carNo",
                "gateNm",
                "applObj",
                "compNm",
                "jwNm",
                "empNm",
                "BDeptNm",
                "BEmpNm",
                "empId",
                "apprComment",
            };
            commonXlsViewDTO.setColumnNameArr(columnNameArr);

            // set column width
            Integer[] columnWidthArr = {
                4000
                , 5000
                , 5000
                , 7000
                , 7000
                , 5000
                , 5000
                , 5000
                , 5000
                , 7000
                , 5000
                , 5000
                , 5000
                , 5000
            };
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