package com.skshieldus.esecurity.repository.sysmanage;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface PcEpRepository {

    List<Map<String, Object>> selectPcEpList(Map<String, Object> paramMap);

    Map<String, Object> selectPcEpView(Map<String, Object> paramMap);

    Map<String, Object> selectPcEpReceiveView(Map<String, Object> paramMap);

    Map<String, Object> selectInPcEpResult(Map<String, Object> paramMap);

    Map<String, Object> selectOutPcEpResult(Map<String, Object> paramMap);

    Map<String, Object> selectInPcEpCheckInDate(Map<String, Object> paramMap);

    int insertInPcEp(Map<String, Object> paramMap);

    int insertOutPcEp(Map<String, Object> paramMap);

    int updatePcEpChkCase(Map<String, Object> paramMap);

    int updatePcListCase(Map<String, Object> paramMap);

    List<Map<String, Object>> selectOutNetworkInfoNcaIF(Map<String, Object> paramMap);

    List<Map<String, Object>> selectNacExceptInfoNcaIF(Map<String, Object> paramMap);

    Map<String, Object> selectPcXempInfo(Map<String, Object> paramMap);

    Map<String, Object> selectPcInfoBuilding(Map<String, Object> paramMap);

    int selectPcEpNotEndCnt(Map<String, Object> paramMap);

    int insertPcBuildingOut(Map<String, Object> paramMap);

    int updatePcBuildingInInoutList(Map<String, Object> paramMap);

    int updatePcBuildingInInoutPcMove(Map<String, Object> paramMap);

    int updatePcBuildingMoveType(Map<String, Object> paramMap);

    int insertPcBuildingInEp(Map<String, Object> paramMap);

}
