package com.skshieldus.esecurity.service.common;

import com.skshieldus.esecurity.model.common.CoCompDTO;
import java.util.List;
import java.util.Map;

public interface CoCompService {

    // 목록 조회
    List<CoCompDTO> selectCoCompList(CoCompDTO coCompDTO);

    // 목록(Key, Value) 조회
    Map<String, Object> selectCoCompListForKey(CoCompDTO coCompDTO);

    // 목록건수 조회
    Integer selectCoCompListCnt(CoCompDTO coCompDTO);

    // 상세 조회
    CoCompDTO selectCoComp(String compId);

}
