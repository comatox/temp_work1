package com.skshieldus.esecurity.service.common.impl;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.model.common.CommonXlsViewDTO;
import com.skshieldus.esecurity.model.common.SampleDTO;
import com.skshieldus.esecurity.repository.common.SampleRepository;
import com.skshieldus.esecurity.service.common.SampleService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SampleServiceImpl implements SampleService {

    @Autowired
    private SampleRepository sampleRepository;

    @Override
    public List<SampleDTO> selectSampleList(SampleDTO sampleDTO) {
        return sampleRepository.selectSampleList(sampleDTO);
    }

    @Override
    public List<SampleDTO> selectSampleListPaging(SampleDTO sampleDTO) {
        return sampleRepository.selectSampleListPaging(sampleDTO);
    }

    @Override
    public int selectSampleListCnt(SampleDTO sampleDTO) {
        return sampleRepository.selectSampleListCnt(sampleDTO);
    }

    @Override
    public CommonXlsViewDTO selectSampleExcelList(HashMap<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;
        CommonXlsViewDTO commonXlsViewDTO = new CommonXlsViewDTO();
        commonXlsViewDTO.setFileName("Sample Data");

        try {
            resultList = sampleRepository.selectSampleExcelList(paramMap);

            // set header names
            String[] headerNameArr = { "순번", "제목", "서브제목", "컬럼", "이름", "주니어", "닉네임", "이미지경로" };

            commonXlsViewDTO.setHeaderNameArr(headerNameArr);

            // set column names (data field name)
            String[] columnNameArr = { "sampleNo", "title", "subTitle", "columns", "name", "junior", "nickname", "imagePath" };
            commonXlsViewDTO.setColumnNameArr(columnNameArr);

            // set column width
            Integer[] columnWidthArr = { 7500, 7500, 7500, 7500, 7500, 7500, 7500, 7500 };
            commonXlsViewDTO.setColumnWidthArr(columnWidthArr);

            // set excel data
            commonXlsViewDTO.setDataList(resultList);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return commonXlsViewDTO;
    }

}
