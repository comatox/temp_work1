package com.skshieldus.esecurity.service.sysmanage;

import java.util.List;
import java.util.Map;

public interface PcEpService {

    List<Map<String, Object>> selectPcEpList(Map<String, Object> paramMap);

    Map<String, Object> selectPcEpView(Map<String, Object> paramMap);

    Map<String, Object> selectInPcEpResult(Map<String, Object> paramMap);

    Map<String, Object> selectOutPcEpResult(Map<String, Object> paramMap);

    boolean insertInPcEp(Map<String, Object> paramMap);

    boolean insertOutPcEp(Map<String, Object> paramMap);

    boolean sendToNcaPcRegister(Map<String, Object> paramMap);

    boolean sendToNcaPcExcept(Map<String, Object> paramMap);

    boolean insertPcEpBuildingOut(Map<String, Object> paramMap);

    boolean insertPcEpBuildingIn(Map<String, Object> paramMap);

    boolean updatePcEpResultCancel(Map<String, Object> paramMap);

    boolean updatePcEpCancelCheck(Map<String, Object> paramMap);

    Map<String, Object> selectPcEpReceiveView(Map<String, Object> paramMap);

}
