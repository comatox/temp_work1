package com.skshieldus.esecurity.repository.common;

import com.skshieldus.esecurity.model.common.CoBldgDTO;
import com.skshieldus.esecurity.model.common.CoXempBldgOutDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface BuildingRepository {

    CoBldgDTO selectCoBldg(String gateId);

    List<CoBldgDTO> selectCoBldgList(List<String> gateIdList);

    List<CoXempBldgOutDTO> selectCoXempBldgOut(String compId);

}