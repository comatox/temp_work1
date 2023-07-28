package com.skshieldus.esecurity.repository.common;

import com.skshieldus.esecurity.model.common.SampleDTO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SampleRepository {

    List<SampleDTO> selectSampleList(SampleDTO sampleDTO);

    List<SampleDTO> selectSampleListPaging(SampleDTO sampleDTO);

    int selectSampleListCnt(SampleDTO sampleDTO);

    List<Map<String, Object>> selectSampleExcelList(HashMap<String, Object> paramMap);

}
