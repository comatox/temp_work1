package com.skshieldus.esecurity.service.common.impl;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.model.common.CoDeptDTO;
import com.skshieldus.esecurity.repository.common.CoDeptRepository;
import com.skshieldus.esecurity.service.common.CoDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class CoDeptServiceImpl implements CoDeptService {

    @Autowired
    private CoDeptRepository coDeptRepository;

    @Override
    public List<CoDeptDTO> selectCoDeptList(CoDeptDTO coDeptDTO) {
        List<CoDeptDTO> resultList = null;

        try {
            resultList = coDeptRepository.selectCoDeptList(coDeptDTO);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Map<String, Object> selectCoDeptListForKey(CoDeptDTO coDeptDTO) {
        Map<String, Object> resultList = null;

        try {
            List<CoDeptDTO> deptList = coDeptRepository.selectCoDeptList(coDeptDTO);

            // List to Map 변환
            resultList = deptList.stream().collect(Collectors.toMap(CoDeptDTO::getDeptId, i -> i));
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Integer selectCoDeptListCnt(CoDeptDTO coDeptDTO) {
        Integer resultCnt = null;

        try {
            resultCnt = coDeptRepository.selectCoDeptListCnt(coDeptDTO);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

    @Override
    public CoDeptDTO selectCoDept(String deptId) {
        CoDeptDTO result = null;

        try {
            result = coDeptRepository.selectCoDept(deptId);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> selectDeptTreeList() {
        List<Map<String, Object>> listDeptTree = new ArrayList<>();
        final String ROOT_DEPT_ID = "10103709";

        try {
            listDeptTree = coDeptRepository.selectPtOrgList();
            if (listDeptTree != null && listDeptTree.size() > 0) {
                Map<String, List<Map<String, Object>>> updeptMap = listDeptTree.parallelStream().collect(Collectors.groupingBy(map -> String.valueOf(map.get("UPDEPT_CD"))));
                listDeptTree.stream().forEach(data -> {
                    if (updeptMap.get(data.get("DEPT_CD")) != null) {
                        List<Map<String, Object>> listChildren = updeptMap.get(data.get("DEPT_CD"));
                        data.put("children", listChildren);
                    }
                });
                listDeptTree = listDeptTree.stream().filter(data -> data.get("UPDEPT_CD").toString().equals(ROOT_DEPT_ID)).collect(Collectors.toList());
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return listDeptTree;
    }

    @Override
    public List<Map<String, Object>> selectCompDeptList(Map<String, Object> paramMap) {

        List<Map<String, Object>> resultList;

        try {
            // 직책 목록
            resultList = coDeptRepository.selectCompDeptList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }
}