/**
 *
 */
package com.skshieldus.esecurity.service.sysmanage.impl;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.repository.sysmanage.GroupManageRepository;
import com.skshieldus.esecurity.service.sysmanage.GroupManageService;
import com.skshieldus.esecurity.service.sysmanage.UserAuthManageService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class GroupManageServiceImpl implements GroupManageService {

    @Autowired
    private GroupManageRepository repository;

    @Autowired
    private UserAuthManageService userAuthManageService;

    @Override
    public List<Map<String, Object>> selectCorpManageList(HashMap<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            Object index = paramMap.get("currentPage");
            Object size = paramMap.get("rowPerPage");
            if (index == null || "".equals(index))
                paramMap.put("currentPage", "1");
            if (size == null || "".equals(size))
                paramMap.put("rowPerPage", "10");

            resultList = repository.selectCorpManageList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public int selectCorpManageListCnt(HashMap<String, Object> paramMap) {
        int resultCnt = 0;

        try {
            resultCnt = repository.selectCorpManageListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

    @Override
    public Boolean saveDeptManage(Map<String, Object> paramMap) {
        Boolean result = false;

        try {
            int count = 0;
            if (ObjectUtils.isEmpty(paramMap.get("deptId"))) {
                count = repository.insertDeptManage(paramMap);
            }
            else {
                count = repository.updateDeptManage(paramMap);
            }
            if (count == 1)
                result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> selectDeptManageList(HashMap<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            Object index = paramMap.get("currentPage");
            Object size = paramMap.get("rowPerPage");
            if (index == null || "".equals(index))
                paramMap.put("currentPage", "1");
            if (size == null || "".equals(size))
                paramMap.put("rowPerPage", "10");

            resultList = repository.selectDeptManageList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public int selectDeptManageListCnt(HashMap<String, Object> paramMap) {
        int resultCnt = 0;

        try {
            resultCnt = repository.selectDeptManageListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

    @Override
    public List<Map<String, Object>> selectJwManageList(HashMap<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            Object index = paramMap.get("currentPage");
            Object size = paramMap.get("rowPerPage");
            if (index == null || "".equals(index))
                paramMap.put("currentPage", "1");
            if (size == null || "".equals(size))
                paramMap.put("rowPerPage", "10");

            resultList = repository.selectJwManageList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public int selectJwManageListCnt(HashMap<String, Object> paramMap) {
        int resultCnt = 0;

        try {
            resultCnt = repository.selectJwManageListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

    @Override
    public Map<String, Object> selectUserManageList(Map<String, Object> paramMap) {

        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>> resultList;
        int totalCount;

        try {
            // 메뉴 코드 목록
            resultList = repository.selectUserManageList(paramMap);
            totalCount = repository.selectUserManageListCnt(paramMap);

            resultMap.put("list", resultList);
            resultMap.put("totalCount", totalCount);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultMap;
    }

    @Override public Map<String, Object> selectUserManageDetail(Map<String, Object> paramMap) {

        Map<String, Object> result;

        try {

            result = repository.selectUserManageDetail(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override public Map<String, Object> selectUserManageCheckId(Map<String, Object> paramMap) {
        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>> resultList;
        try {
            resultList = repository.selectUserManageCheckId(paramMap);
            if (resultList.size() < 1) {
                resultMap.put("message", "OK");
            }
            else {
                resultMap.put("message", "FAIL");
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return resultMap;
    }

    @Override public Map<String, Object> selectUserManageCheckEmpId(Map<String, Object> paramMap) {
        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>> resultList;
        try {
            resultList = repository.selectUserManageCheckEmpId(paramMap);
            if (resultList.size() < 1) {
                resultMap.put("message", "OK");
            }
            else {
                resultMap.put("message", "FAIL");
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return resultMap;
    }

    @Override
    public int updateUserManage(Map<String, Object> paramMap) {

        int result;

        try {
            result = repository.insertUserManage(paramMap);

            if(result > 0) {
                List<Map<String, Object>> authList = new ArrayList<>();
                Map<String, Object> userAuthParam= new HashMap<>();
                String divCd = String.valueOf(paramMap.get("divCd"));
                String authEmpId = String.valueOf(paramMap.get("empId"));
                String authId;

                if("CP".equals(divCd)){
                    authId = "44";
                }else if("S2".equals(divCd) || "S20".equals(divCd) || "AP".equals(divCd)){
                    authId = "74";
                }else{
                    authId = "43";
                }

                userAuthParam.put("authId", authId);
                authList.add(userAuthParam);

                paramMap.put("authEmpId", authEmpId);
                paramMap.put("selectedRows", authList);
                userAuthManageService.saveUserAuthManage(null, paramMap);

            }

        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> selectUserManageJcList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList;
        try {
            // 직책 목록
            resultList = repository.selectUserManageJcList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return resultList;
    }

    @Override
    public List<Map<String, Object>> selectUserManageJwList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList;
        try {
            // 직책 목록
            resultList = repository.selectUserManageJwList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return resultList;
    }

    @Override public Map<String, Object> selectHynixUserList(Map<String, Object> paramMap) {

        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>> resultList;
        int totalCount;

        try {
            // 메뉴 코드 목록
            resultList = repository.selectHynixUserList(paramMap);
            totalCount = repository.selectHynixUserListCnt(paramMap);

            resultMap.put("list", resultList);
            resultMap.put("totalCount", totalCount);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultMap;
    }

    @Override public int insertHynixUser(Map<String, Object> paramMap) {
        int result = 0;

        try {
            result = repository.insertHynixUser(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override public Map<String, Object> selectCardKeyUserList(Map<String, Object> paramMap) {

        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>> resultList;
        int totalCount;

        try {
            // 메뉴 코드 목록
            resultList = repository.selectCardKeyUserList(paramMap);
            totalCount = repository.selectCardKeyUserListCnt(paramMap);

            resultMap.put("list", resultList);
            resultMap.put("totalCount", totalCount);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultMap;
    }

}
