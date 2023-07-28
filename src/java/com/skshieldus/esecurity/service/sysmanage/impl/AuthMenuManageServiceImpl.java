package com.skshieldus.esecurity.service.sysmanage.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.repository.sysmanage.AuthManageRepository;
import com.skshieldus.esecurity.repository.sysmanage.AuthMenuManageRepository;
import com.skshieldus.esecurity.service.sysmanage.AuthMenuManageService;
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
public class AuthMenuManageServiceImpl implements AuthMenuManageService {

    @Autowired
    private AuthMenuManageRepository authMenuManageRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthManageRepository authManageRepository;

    @Override
    public List<Map<String, Object>> selectAuthList(HashMap<String, Object> paramMap) {

        List<Map<String, Object>> result = null;

        try {
            result = authMenuManageRepository.selectAuthList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return result;
    }

    @Override
    public Map<String, Object> selectAuthMenuList(Map<String, Object> paramMap) {

        Map<String, Object> result = new HashMap<>();

        try {
            // 권한+메뉴 매핑 목록
            result.put("authMenuList", authMenuManageRepository.selectAuthMenuList(paramMap));
            // 최고 Depth
            result.put("maxMenuDepth", authManageRepository.selectMenuManageMaxDepth());

        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return result;
    }

    @Override
    public boolean saveAuthMenuManage(SessionInfoVO sessionInfoVO, Map<String, Object> paramMap) {
        boolean result = false;
        int insertAuthMenuManageResult = 0;

        try {

            String authId = String.valueOf(paramMap.getOrDefault("authId", ""));
            if ("".equals(authId)) {
                throw new Exception("Failed to execute authMenuManageRepository.saveAuthMenuManage >>> AUTH_ID IS NOT VALID");
            }

            List<Map<String, Object>> updateList = objectMapper.convertValue(paramMap.get("updateList"),
                new TypeReference<List<Map<String, Object>>>() {
                });

            String sessionEmpId =  String.valueOf(paramMap.getOrDefault("empId", ""));

            log.info(">>>> sessionEmpId : {}", sessionEmpId);

            authMenuManageRepository.deleteAuthMenuManage(authId);

            for (Map<String, Object> reqParam : updateList) {
                authMenuManageRepository.insertAuthMenuManage(reqParam);
                insertAuthMenuManageResult++;
            }

            log.info(">>>> 권한/메뉴 관리 저장 성공 : {}", insertAuthMenuManageResult);
            result = true;
        } catch (Exception e) {

            result = false;
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return result;
    }

}