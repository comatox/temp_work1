package com.skshieldus.esecurity.repository.entmanage.smartTag;

import com.skshieldus.esecurity.config.datasource.annotation.SmartTagMapper;
import java.util.Map;

@SmartTagMapper
public interface SmartTagRepository {

    int updateSmartTagVisitInOutProcess(Map<String, Object> paramMap);

    void procedureBuildingPassInProcSmartTagIf(Map<String, Object> paramMap);

    void procedureFrontDoorOutProcSmartTagIf(Map<String, Object> paramMap);

    void procedureOutGateInProcSmartTagIf(Map<String, Object> paramMap);

    void procedureOutGateOutProcSmartTagIF(Map<String, Object> paramMap);

    void proceduteOutGateCubeApprovalSmartTagIF(Map<String, Object> paramMap);

}
