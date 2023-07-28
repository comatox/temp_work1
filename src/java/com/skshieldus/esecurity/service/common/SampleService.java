package com.skshieldus.esecurity.service.common;

import com.skshieldus.esecurity.model.common.CommonXlsViewDTO;
import com.skshieldus.esecurity.model.common.SampleDTO;
import java.util.HashMap;
import java.util.List;

public interface SampleService {

    List<SampleDTO> selectSampleList(SampleDTO sampleDTO);

    List<SampleDTO> selectSampleListPaging(SampleDTO sampleDTO);

    int selectSampleListCnt(SampleDTO sampleDTO);

    CommonXlsViewDTO selectSampleExcelList(HashMap<String, Object> paramMap);

}
