package com.skshieldus.esecurity.service.common.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ListDTO;
import com.skshieldus.esecurity.model.common.CoEmpDTO;
import com.skshieldus.esecurity.repository.common.CoEmpExtRepository;
import com.skshieldus.esecurity.service.common.CoEmpExtService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CoEmpExtServiceImpl implements CoEmpExtService {

    @Autowired
    private CoEmpExtRepository coEmpExtRepository;

    @Autowired
    private ObjectMapper objectMapper;
    
    @Override
    public ListDTO selectCoEmpExtList(Map<String, Object> paramMap) {
        List<CoEmpDTO> resultList;
        int totalCount;

        try {
            resultList = coEmpExtRepository.selectCoEmpExtList(paramMap);
            totalCount = coEmpExtRepository.selectCoEmpExtListCnt(paramMap);

        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return ListDTO.getInstance(resultList, totalCount);
    }

    @Override
    public ListDTO selectZipCodeList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList;
        int totalCount;

        try {

            if("1".equals(paramMap.get("addrGbn"))){
                resultList = coEmpExtRepository.searchZipCodeList(paramMap);
                totalCount = coEmpExtRepository.searchZipCodeListCnt(paramMap);
            }else{
                resultList = coEmpExtRepository.searchRoadZipCodeList(paramMap);
                totalCount = coEmpExtRepository.searchRoadZipCodeListCnt(paramMap);
            }

        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return ListDTO.getInstance(resultList, totalCount);
    }
}