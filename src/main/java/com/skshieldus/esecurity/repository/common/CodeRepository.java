package com.skshieldus.esecurity.repository.common;

import com.skshieldus.esecurity.model.common.CoCodeDetailDTO;
import com.skshieldus.esecurity.model.common.CoCodeMasterDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface CodeRepository {

    List<CoCodeMasterDTO> selectCodeMasterList();

    List<CoCodeDetailDTO> selectCodeDetailList(String grpCd);

}