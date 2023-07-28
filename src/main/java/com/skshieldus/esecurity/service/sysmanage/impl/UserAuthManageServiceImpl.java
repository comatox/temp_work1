package com.skshieldus.esecurity.service.sysmanage.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ListDTO;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.repository.sysmanage.UserAuthManageRepository;
import com.skshieldus.esecurity.service.sysmanage.UserAuthManageService;
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
public class UserAuthManageServiceImpl implements UserAuthManageService {

    @Autowired
    private UserAuthManageRepository userAuthManageRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public ListDTO<Map<String, Object>> selectUserAuthList(Map<String, Object> paramMap) {

        List<Map<String, Object>> resultList = null;
        Integer totalCount = 0;

        try {
            resultList = userAuthManageRepository.selectUserAuthList(paramMap);
            totalCount = userAuthManageRepository.selectUserAuthListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return ListDTO.getInstance(resultList, totalCount);
    }

    @Override
    public Integer selectUserAuthListCnt(Map<String, Object> paramMap) {
        return userAuthManageRepository.selectUserAuthListCnt(paramMap);
    }

    @Override
    public ListDTO<Map<String, Object>> selectUserAuthManageEmpAuthList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = userAuthManageRepository.selectUserAuthManageEmpAuthList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return ListDTO.getInstance(resultList, 0);
    }

    @Override
    public boolean saveUserAuthManage(SessionInfoVO sessionInfoVO, Map<String, Object> paramMap) {
        boolean result = false;
        int insertUserAuthManageResult = 0;

        try {

            String authEmpId = String.valueOf(paramMap.getOrDefault("authEmpId", ""));
            if ("".equals(authEmpId)) {
                throw new Exception("Failed to execute userAuthManageRepository.saveUserAuthManage >>> AUTH_EMP_ID IS NOT VALID");
            }

            String sessionEmpId = String.valueOf(paramMap.get("empId"));
            //String sessionEmpId = sessionInfoVO.getEmpNo();

            log.info(">>>> sessionEmpId : {}", sessionEmpId);

            userAuthManageRepository.deleteUserAuthManage(paramMap);

            List<Map<String, Object>> selectedRows = objectMapper.convertValue(paramMap.get("selectedRows"),
                new TypeReference<List<Map<String, Object>>>() {});


            for (Map<String, Object> row : selectedRows) {

                log.info(">>>> SELECTED AUTH_ID : {}", row.get("authId"));
                row.put("authEmpId", authEmpId);
                row.put("crtBy", sessionEmpId);
                userAuthManageRepository.insertUserAuthManage(row);
                insertUserAuthManageResult++;
            }
            log.info(">>>> 사용자/권한 관리 저장 성공 : {}", insertUserAuthManageResult);

            result = true;
        } catch (Exception e) {
            result = false;
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return result;
    }

}