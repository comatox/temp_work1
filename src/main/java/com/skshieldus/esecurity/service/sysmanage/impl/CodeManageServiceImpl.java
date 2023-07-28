/**
 *
 */
package com.skshieldus.esecurity.service.sysmanage.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.repository.sysmanage.CodeManageRepository;
import com.skshieldus.esecurity.service.sysmanage.CodeManageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CodeManageServiceImpl implements CodeManageService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CodeManageRepository repository;

    @Override
    public List<Map<String, Object>> selectGroupManageList(HashMap<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            Object index = paramMap.get("currentPage");
            Object size = paramMap.get("rowPerPage");
            if (index == null || "".equals(index))
                paramMap.put("currentPage", "1");
            if (size == null || "".equals(size))
                paramMap.put("rowPerPage", "10");

            resultList = repository.selectGroupManageList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public int selectGroupManageListCnt(HashMap<String, Object> paramMap) {
        int resultCnt = 0;

        try {
            resultCnt = repository.selectGroupManageListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Boolean updateGroupManage(HashMap<String, Object> paramMap) {
        boolean result = false;

        try {
            List<Map<String, Object>> dataList = (List<Map<String, Object>>) paramMap.get("dataList");

            if (dataList != null && dataList.size() > 0) {
                for (int i = 0; i < dataList.size(); i++) {
                    Map<String, Object> dataMap = dataList.get(i);
                    dataMap.put("empId", paramMap.get("empId"));
                    dataMap.put("acIp", paramMap.get("acIp"));
                    String grpCd = (String) dataMap.get("grpCd");

                    if (StringUtils.isEmpty(grpCd)) {
                        throw new Exception("분류코드가 입력되지 않았습니다.");
                    }
                    else if (grpCd.length() > 4) {
                        throw new Exception("분류코드는 4자리까지 입력가능 합니다.");
                    }

                    // 분류코드 등록/수정
                    int resultCnt = repository.updateGroupManage(dataMap);

                    if (resultCnt < 1) {
                        throw new Exception("분류코드 등록/수정시 오류가 발생하였습니다.");
                    }
                }
                result = true;
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> selectDetailManageList(HashMap<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            Object index = paramMap.get("currentPage");
            Object size = paramMap.get("rowPerPage");
            if (index == null || "".equals(index))
                paramMap.put("currentPage", "1");
            if (size == null || "".equals(size))
                paramMap.put("rowPerPage", "10");

            resultList = repository.selectDetailManageList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public int selectDetailManageListCnt(HashMap<String, Object> paramMap) {
        int resultCnt = 0;

        try {
            resultCnt = repository.selectDetailManageListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Boolean updateDetailManage(HashMap<String, Object> paramMap) {
        boolean result = false;

        try {
            List<Map<String, Object>> dataList = (List<Map<String, Object>>) paramMap.get("dataList");

            if (dataList != null && dataList.size() > 0) {
                for (int i = 0; i < dataList.size(); i++) {
                    Map<String, Object> dataMap = dataList.get(i);
                    dataMap.put("empId", paramMap.get("empId"));
                    dataMap.put("acIp", paramMap.get("acIp"));

                    String grpCd = (String) dataMap.get("grpCd");
                    String detlCd = (String) dataMap.get("detlCd");

                    if (StringUtils.isEmpty(grpCd)) {
                        throw new Exception("분류코드가 입력되지 않았습니다.");
                    }
                    else if (grpCd.length() > 4) {
                        throw new Exception("분류코드는 4자리까지 입력가능 합니다.");
                    }
                    else if (StringUtils.isEmpty(detlCd)) {
                        throw new Exception("상세코드가 입력되지 않았습니다.");
                    }

                    // 세부코드 등록/수정
                    int resultCnt = repository.updateDetailManage(dataMap);

                    if (resultCnt < 1) {
                        throw new Exception("세부코드 등록/수정시 오류가 발생하였습니다.");
                    }
                }
                result = true;
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Map<String, Object> selectDetailManageDetail(HashMap<String, Object> paramMap) {
        Map<String, Object> result = null;

        try {
            String grpCd = (String) paramMap.get("grpCd");
            String detlCd = (String) paramMap.get("detlCd");

            if ("".equals(grpCd) || "".equals(detlCd)) {
                throw new Exception("필수 값이 누락되었습니다.");
            }

            result = repository.selectDetailManageDetail(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Boolean updateDetailCodeInfo(HashMap<String, Object> paramMap) {
        boolean result = false;

        try {
            String grpCd = (String) paramMap.get("grpCd");
            String detlCd = (String) paramMap.get("detlCd");

            if ("".equals(grpCd) || "".equals(detlCd)) {
                throw new Exception("필수 값이 누락되었습니다.");
            }

            // 세부코드 상세정보 수정
            int resultCnt = repository.updateDetailCodeInfo(paramMap);

            if (resultCnt < 1) {
                throw new Exception("세부코드 수정시 오류가 발생하였습니다.");
            }

            result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

}
