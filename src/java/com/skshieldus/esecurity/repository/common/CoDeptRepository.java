package com.skshieldus.esecurity.repository.common;

import com.skshieldus.esecurity.model.common.CoDeptDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface CoDeptRepository {

    // 목록 조회
    List<CoDeptDTO> selectCoDeptList(CoDeptDTO coDeptDTO);

    // 목록건수 조회
    Integer selectCoDeptListCnt(CoDeptDTO coDeptDTO);

    // 상세 조회
    CoDeptDTO selectCoDept(String deptId);

    // 트리 목록 조회(TBL_PT_ORG)
    List<Map<String, Object>> selectPtOrgList();

    // 부서 조회
    List<Map<String, Object>> selectCompDeptList(Map<String, Object> paramMap);
}
