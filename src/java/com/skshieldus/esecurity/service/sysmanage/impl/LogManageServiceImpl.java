/**
 *
 */
package com.skshieldus.esecurity.service.sysmanage.impl;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.repository.sysmanage.LogManageRepository;
import com.skshieldus.esecurity.service.sysmanage.LogManageService;
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
public class LogManageServiceImpl implements LogManageService {

    @Autowired
    private LogManageRepository repository;

    @Override
    public List<Map<String, Object>> selectUserLogList(HashMap<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            Object index = paramMap.get("currentPage");
            Object size = paramMap.get("rowPerPage");
            if (index == null || "".equals(index))
                paramMap.put("currentPage", "1");
            if (size == null || "".equals(size))
                paramMap.put("rowPerPage", "10");

            resultList = repository.selectUserLogList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public int selectUserLogListCnt(HashMap<String, Object> paramMap) {
        int resultCnt = 0;

        try {
            resultCnt = repository.selectUserLogListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

    @Override
    public List<Map<String, Object>> selectUsageStatMenu(HashMap<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            Object index = paramMap.get("currentPage");
            Object size = paramMap.get("rowPerPage");
            if (index == null || "".equals(index))
                paramMap.put("currentPage", "1");
            if (size == null || "".equals(size))
                paramMap.put("rowPerPage", "10");

            resultList = repository.selectUsageStatMenu(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public int selectUsageStatMenuCnt(HashMap<String, Object> paramMap) {
        int resultCnt = 0;

        try {
            resultCnt = repository.selectUsageStatMenuCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

    @Override
    public List<Map<String, Object>> selectUsageStatMonth(HashMap<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            Object index = paramMap.get("currentPage");
            Object size = paramMap.get("rowPerPage");
            if (index == null || "".equals(index))
                paramMap.put("currentPage", "1");
            if (size == null || "".equals(size))
                paramMap.put("rowPerPage", "10");

            resultList = repository.selectUsageStatMonth(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

}
