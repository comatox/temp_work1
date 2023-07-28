package com.skshieldus.esecurity.service.sysmanage;

import com.skshieldus.esecurity.common.model.ListDTO;
import java.util.List;
import java.util.Map;

public interface BuildingManageService {

    // 최상위 건물 목록 조회
    List<Map<String, Object>> selectGateListTop(String compId);

    // 상위 건물 목록 조회
    List<Map<String, Object>> selectGateListSub(Map<String, Object> param);

    // 건물 목록 조회
    ListDTO<Map<String, Object>> selectBuildingList(Map<String, Object> param);

    // 건물 상세 조회
    Map<String, Object> selectBuildingView(String gateId);

    // 건물 등록
    Integer insertBuilding(Map<String, Object> detail);

    // 건물 수정
    Integer updateBuilding(Map<String, Object> detail);

}