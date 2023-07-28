package com.skshieldus.esecurity.service.common.impl;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.model.common.CoCompDTO;
import com.skshieldus.esecurity.repository.common.CoCompRepository;
import com.skshieldus.esecurity.service.common.CoCompService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class CoCompServiceImpl implements CoCompService {

    @Autowired
    private CoCompRepository coCompRepository;

    @Override
    public List<CoCompDTO> selectCoCompList(CoCompDTO coCompDTO) {
        List<CoCompDTO> resultList = null;

        try {
            resultList = coCompRepository.selectCoCompList(coCompDTO);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Map<String, Object> selectCoCompListForKey(CoCompDTO coCompDTO) {
        Map<String, Object> resultList = null;

        try {
            List<CoCompDTO> coCompList = coCompRepository.selectCoCompList(coCompDTO);

            // List to Map 변환
            resultList = coCompList.stream().collect(Collectors.toMap(CoCompDTO::getCompId, i -> i));
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Integer selectCoCompListCnt(CoCompDTO coCompDTO) {
        Integer resultCnt = null;

        try {
            resultCnt = coCompRepository.selectCoCompListCnt(coCompDTO);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

    @Override
    public CoCompDTO selectCoComp(String compId) {
        CoCompDTO result = null;

        try {
            result = coCompRepository.selectCoComp(compId);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

}