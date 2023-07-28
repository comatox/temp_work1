package com.skshieldus.esecurity.service.sysmanage.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ListDTO;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.model.sysmanage.MenuManageDTO;
import com.skshieldus.esecurity.repository.sysmanage.AuthManageRepository;
import com.skshieldus.esecurity.service.sysmanage.AuthManageService;
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
public class AuthManageServiceImpl implements AuthManageService {

    @Autowired
    private AuthManageRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 통계 보안담당자 부서조회
     *
     * @return
     *
     * @author : X0125104 <won.shin@partner.sk.com>
     * @since : 2022. 01. 10
     */
    public ListDTO<Map<String, Object>> selectAuthManageList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;
        Integer totalCount = 0;

        try {
            resultList = repository.selectAuthManageList(paramMap);
            totalCount = repository.selectAuthManageListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return ListDTO.getInstance(resultList, totalCount);
    }

    @Override
    public boolean upsertAuthManage(Map<String, Object> paramMap) {
        boolean result = false;
        int upsertAuthManageResult = 0;

        try {

            String empId = String.valueOf(paramMap.getOrDefault("empId", ""));

            if ("".equals(empId)) {
                throw new Exception("Failed to execute AuthManageService.upsertAuthManage >>> empId empty");
            }
            String acIp = String.valueOf(paramMap.getOrDefault("acIp", ""));

            log.info(">>>> empId : {}", empId);
            log.info(">>>> acIp : {}", acIp);

            List<Map<String, Object>> updateList = objectMapper.convertValue(paramMap.get("updateList"), new TypeReference<List<Map<String, Object>>>() { });

            for (Map<String, Object> row : updateList) {
                repository.upsertAuthManage(row);
                upsertAuthManageResult++;
            }

            log.info(">>>> 권한관리 저장 성공 : {}", upsertAuthManageResult);
            result = true;
        } catch (Exception e) {
            result = false;
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return result;
    }

    @Override
    public Map<String, Object> selectMenuManageList(Map<String, Object> paramMap) {

        Map<String, Object> resultMap = new HashMap<>();
        List<MenuManageDTO> resultList;
        int totalCount;
        int maxDepth;

        try {
            // 메뉴 코드 목록
            resultList = repository.selectMenuManageList();
            totalCount = Math.max(resultList.size(), 0);

            // 메뉴 최대 Depth
            maxDepth = repository.selectMenuManageMaxDepth();

            resultMap.put("list", resultList);
            resultMap.put("totalCount", totalCount);
            resultMap.put("maxDepth", maxDepth);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultMap;
    }

    @Override public MenuManageDTO selectMenuManageDetail(Map<String, Object> paramMap) {

        MenuManageDTO result;

        try {

            result = repository.selectMenuManageDetail(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override public Map<String, Object> selectMenuManageNewCode(Map<String, Object> paramMap) {
        Map<String, Object> result;

        try {

            result = repository.selectMenuManageNewCode(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override public int updateMenuManage(Map<String, Object> paramMap) {
        int result;

        try {

            int menuCount = repository.selectMenuManageMenuCount(paramMap);

            // 수정 저장
            if (menuCount > 0) {
                result = repository.updateMenuManage(paramMap);
                // 신규 저장
            }
            else {
                result = repository.insertMenuManage(paramMap);
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> selectAuthList(HashMap<String, Object> paramMap) {
        List<Map<String, Object>> result = null;

        try {
            result = repository.selectAuthList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return result;
    }

    @Override public Map<String, Object> selectAuthMenuList(Map<String, Object> paramMap) {
        Map<String, Object> result = new HashMap<>();

        try {
            // 권한+메뉴 매핑 목록
            result.put("authMenuList", repository.selectAuthMenuList(paramMap));
            // 최고 Depth
            result.put("maxMenuDepth", repository.selectMenuManageMaxDepth());

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
                throw new Exception("Failed to execute authManageRepository.saveAuthMenuManage >>> AUTH_ID IS NOT VALID");
            }

            log.info(">>>> saveAuthMenuManage authId : {}", authId);

            List<Map<String, Object>> updateList = objectMapper.convertValue(paramMap.get("updateList"),
                new TypeReference<List<Map<String, Object>>>() {
                });

            String sessionEmpId =  String.valueOf(paramMap.getOrDefault("empId", ""));

            log.info(">>>> sessionEmpId : {}", sessionEmpId);

            repository.deleteAuthMenuManage(authId);

            for (Map<String, Object> reqParam : updateList) {
                repository.insertAuthMenuManage(reqParam);
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

    @Override
    public int selectUserAuthCheckCnt(Map<String, Object> paramMap) {
        int resultCnt = 0;

        try {
            resultCnt = repository.selectUserAuthCheckCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return resultCnt;
    }

    @Override
    public ListDTO<Map<String, Object>> selectUserAuthList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;
        Integer totalCount = 0;

        try {
            resultList = repository.selectUserAuthList(paramMap);
            totalCount = repository.selectUserAuthListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return ListDTO.getInstance(resultList, totalCount);
    }

    @Override
    public boolean saveUserAuthManage(SessionInfoVO sessionInfoVO, Map<String, Object> paramMap) {
        boolean result = false;
        int insertUserAuthManageResult = 0;

        try {

            String authEmpId = String.valueOf(paramMap.getOrDefault("authEmpId", ""));
            String acIp = String.valueOf(paramMap.getOrDefault("acIp", ""));
            if ("".equals(authEmpId)) {
                throw new Exception("Failed to execute userAuthManageRepository.saveUserAuthManage >>> AUTH_EMP_ID IS NOT VALID");
            }

            String sessionEmpId = String.valueOf(paramMap.get("empId"));

            log.info(">>>> sessionEmpId : {}", sessionEmpId);
            log.info(">>>> acIp : {}", acIp);

            repository.deleteUserAuthManage(paramMap);

            List<Map<String, Object>> selectedRows = objectMapper.convertValue(paramMap.get("selectedRows"),
                new TypeReference<List<Map<String, Object>>>() {});

            for (Map<String, Object> row : selectedRows) {
                row.put("authEmpId", authEmpId);
                row.put("crtBy", sessionEmpId);
                repository.insertUserAuthManage(row);
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

    @Override public ListDTO<Map<String, Object>> selectUserUseAuthList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectUserUseAuthList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return ListDTO.getInstance(resultList, 0);
    }

}