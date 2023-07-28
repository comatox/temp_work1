package com.skshieldus.esecurity.repository.common;

import com.skshieldus.esecurity.model.common.CoCompDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface CoCompRepository {

    // 목록 조회
    List<CoCompDTO> selectCoCompList(CoCompDTO coCompDTO);

    // 목록건수 조회
    Integer selectCoCompListCnt(CoCompDTO coCompDTO);

    // 상세 조회
    CoCompDTO selectCoComp(String compId);

}
