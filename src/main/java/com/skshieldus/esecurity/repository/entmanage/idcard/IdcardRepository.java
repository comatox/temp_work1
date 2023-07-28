package com.skshieldus.esecurity.repository.entmanage.idcard;

import com.skshieldus.esecurity.config.datasource.annotation.IdcardMapper;
import java.util.List;
import java.util.Map;

@IdcardMapper
public interface IdcardRepository {

    int insertIDCardIfHeifBuildingInfo(Map<String, String> paramMap);

    Map<String, Object> procedureCheckEnableRegisterBldgIC(Map<String, Object> paramMap);

    int insertIDCardIfHeifOtherInfo(Map<String, Object> paramMap);

    int insertHEIFReplaceItem(Map<String, Object> paramMap);

    List<Map<String, Object>> selectOldPassCJBuildingList(Map<String, Object> paramMap);

    int insertIdcardIfHeifEntranceInfo(Map<String, Object> paramMap);

    int insertIdcardIFHeifEmployeeInfo(Map<String, Object> paramMap);

    int insertPassReceiptMngExtnIdcard(Map<String, Object> paramMap);

    int insertSubcontMoveIfEntrance(Map<String, Object> paramMap);

}
