package com.skshieldus.esecurity.repository.sysmanage;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface BuildingManageRepository {

    // 최상위 건물 목록 조회
    List<Map<String, Object>> selectGateListTop(String compId);

    // 상위 건물 목록 조회
    List<Map<String, Object>> selectGateListSub(Map<String, Object> param);

    // 건물 목록 조회
    List<Map<String, Object>> selectBuildingList(Map<String, Object> param);

    // 건물 목록 조회
    Integer selectBuildingCount(Map<String, Object> param);

    // 건물 상세 조회
    Map<String, Object> selectBuildingView(String gateId);

    // 건물 등록
    Integer insertBuilding(Map<String, Object> detail);

    // 건물 수정
    Integer updateBuilding(Map<String, Object> detail);

}