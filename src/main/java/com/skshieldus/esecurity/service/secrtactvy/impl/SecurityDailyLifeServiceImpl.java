package com.skshieldus.esecurity.service.secrtactvy.impl;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.model.common.CommonXlsViewDTO;
import com.skshieldus.esecurity.repository.secrtactvy.SecurityDailyLifeRepository;
import com.skshieldus.esecurity.service.secrtactvy.SecurityDailyLifeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SecurityDailyLifeServiceImpl implements SecurityDailyLifeService {

    @Autowired
    private SecurityDailyLifeRepository repository;

    @Override
    public List<Map<String, Object>> licenseManageAdminManageList(HashMap<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.licenseManageAdminManageList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public List<Map<String, Object>> licenseManageAdminManageEmpList(HashMap<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            Object index = paramMap.get("currentPage");
            Object size = paramMap.get("rowPerPage");
            if (index == null)
                paramMap.put("currentPage", "1");
            if (size == null)
                paramMap.put("rowPerPage", "10");

            resultList = repository.licenseManageAdminManageEmpList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Boolean licenseManageAdminManageEmpDelete(HashMap<String, Object> paramMap) {
        boolean result = false;

        try {
            int cnt = repository.licenseManageAdminManageEmpDelete(paramMap);

            if (cnt > 0)
                result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> licenseManageAdminEmpList(HashMap<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.licenseManageAdminEmpList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public CommonXlsViewDTO licenseManageAdminEmpListExcel(HashMap<String, Object> paramMap) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        CommonXlsViewDTO commonXlsViewDTO = new CommonXlsViewDTO();
        commonXlsViewDTO.setFileName("임직원 자격증 정보 현황" + "_" + sdf.format(new Date()));

        // set header names
        String[] headerNameArr = { "사번", "성명", "부서", "직종기호", "분야", "직종명", "관련자격면허", "등록일자" };
        commonXlsViewDTO.setHeaderNameArr(headerNameArr);
        // set column names (data field name)
        String[] columnNameArr = { "empId", "empNm", "deptNm", "licenseCd", "licenseCtg", "licenseNm", "licenseJob", "crtDtm" };
        commonXlsViewDTO.setColumnNameArr(columnNameArr);
        // set column width
        Integer[] columnWidthArr = { 4000, 5000, 5000, 7000, 7000, 5000, 5000, 5000 };
        commonXlsViewDTO.setColumnWidthArr(columnWidthArr);

        List<Map<String, Object>> list = repository.licenseManageAdminEmpListExcel(paramMap);

        // set excel data
        commonXlsViewDTO.setDataList(list);

        return commonXlsViewDTO;
    }

    @Override
    public List<Map<String, Object>> licenseManageList(HashMap<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {

            Object index = paramMap.get("currentPage");
            Object size = paramMap.get("rowPerPage");
            if (index == null)
                paramMap.put("currentPage", "1");
            if (size == null)
                paramMap.put("rowPerPage", "10");

            resultList = repository.licenseManageList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Boolean licenseManageRemove(HashMap<String, Object> paramMap) {
        boolean result = false;

        try {
            int cnt = repository.licenseManageRemove(paramMap);

            if (cnt > 0)
                result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Boolean licenseManageInsert(HashMap<String, Object> paramMap) {
        boolean result = false;

        try {

            int scLicenseId = repository.dmLicenseManage_LicenseSeq_S();
            paramMap.put("scLicenseId", scLicenseId);

            int cnt = repository.licenseManageInsert(paramMap);

            if (cnt > 0)
                result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public int licenseManageListCnt(HashMap<String, Object> paramMap) {
        int result = 0;
        try {

            result = repository.licenseManageListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return result;
    }

    @Override
    public int licenseManageAdminManageEmpListCnt(HashMap<String, Object> paramMap) {
        int result = 0;
        try {

            result = repository.licenseManageAdminManageEmpListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return result;
    }

}
