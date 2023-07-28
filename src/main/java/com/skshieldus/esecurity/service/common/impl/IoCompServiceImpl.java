package com.skshieldus.esecurity.service.common.impl;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ListDTO;
import com.skshieldus.esecurity.model.common.*;
import com.skshieldus.esecurity.repository.common.IoCompRepository;
import com.skshieldus.esecurity.service.common.IoCompService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class IoCompServiceImpl implements IoCompService {

    @Autowired
    private IoCompRepository repository;

    @Override
    public ListDTO<Map<String, Object>> selectIoEmpList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;
        Integer totalCount = 0;

        try {
            resultList = repository.selectIoEmpList(paramMap);
            totalCount = repository.selectIoEmpListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return ListDTO.getInstance(resultList, totalCount);
    }

    @Override
    public IoEmpDTO selectIoEmp(String ioEmpId) {
        IoEmpDTO result = null;

        try {
            result = repository.selectIoEmp(ioEmpId);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public List<IoCompDTO> selectIoCompList(IoCompSearchDTO paramDTO) {
        List<IoCompDTO> resultList = null;

        try {
            resultList = repository.selectIoCompList(paramDTO);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Integer selectIoCompListCnt(IoCompSearchDTO paramDTO) {
        Integer resultCnt = 0;

        try {
            resultCnt = repository.selectIoCompListCnt(paramDTO);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

    @Override
    public IoCompDTO selectIoComp(String ioCompId) {
        IoCompDTO result = null;

        try {
            result = repository.selectIoComp(ioCompId);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    /**
     * 외부인 차량정보 목록 조회
     *
     * @param ioEmpCarInfoViewSearchDTO
     * @return List<IoEmpCarInfoViewDTO>
     */
    @Override
    public List<IoEmpCarInfoViewDTO> selectIoEmpCarInfoViewList(IoEmpCarInfoViewSearchDTO ioEmpCarInfoViewSearchDTO) {
        List<IoEmpCarInfoViewDTO> resultList = null;

        try {
            resultList = repository.selectIoEmpCarInfoViewList(ioEmpCarInfoViewSearchDTO);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return resultList;
    }

    @Override
    public ListDTO<Map<String, Object>> selectIoEmpSubcontList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;
        Integer totalCount = 0;

        try {
            resultList = repository.selectIoEmpSubcontList(paramMap);
            totalCount = repository.selectIoEmpSubcontListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return ListDTO.getInstance(resultList, totalCount);
    }

    @Override
    public Integer selectIoEmpSubcontListCnt(Map<String, Object> paramMap) {
        Integer resultCnt = 0;

        try {
            resultCnt = repository.selectIoEmpSubcontListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

}
