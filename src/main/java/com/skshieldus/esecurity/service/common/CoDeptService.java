package com.skshieldus.esecurity.service.common;

import com.skshieldus.esecurity.model.common.CoDeptDTO;
import java.util.List;
import java.util.Map;

public interface CoDeptService {

    // 목록 조회
    List<CoDeptDTO> selectCoDeptList(CoDeptDTO coDeptDTO);

    // 목록(Key, Value) 조회
    Map<String, Object> selectCoDeptListForKey(CoDeptDTO coDeptDTO);

    // 목록건수 조회
    Integer selectCoDeptListCnt(CoDeptDTO coDeptDTO);

    // 상세 조회
    CoDeptDTO selectCoDept(String deptId);

    // 부서트리 목록 조회
    List<Map<String, Object>> selectDeptTreeList();

    // 부서 조회
    List<Map<String, Object>> selectCompDeptList(Map<String, Object> paramMap);
}
