package com.skshieldus.esecurity.service.common.impl;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.model.common.CoCodeDetailDTO;
import com.skshieldus.esecurity.model.common.CoCodeMasterDTO;
import com.skshieldus.esecurity.repository.common.CodeRepository;
import com.skshieldus.esecurity.service.common.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class CodeServiceImpl implements CodeService {

    @Autowired
    private CodeRepository codeRepository;

    /**
     * 공통코드 전체(Master + DetailList) 목록 조회
     *
     * @return
     *
     * @author : X0121127 <sungbum.oh@partner.sk.com>
     * @since : 2020. 01. 21.
     */
    @Override
    public List<CoCodeMasterDTO> selectCodeMasterList() {
        return codeRepository.selectCodeMasterList();
    }

    /**
     * 공통코드 상세 목록 조회
     *
     * @return
     *
     * @author : X0121127 <sungbum.oh@partner.sk.com>
     * @since : 2020. 01. 21.
     */
    @Override
    public List<CoCodeDetailDTO> selectCodeDetailList(String grpCd) {
        return codeRepository.selectCodeDetailList(grpCd);
    }

    /**
     * 공통코드 전체(Master + DetailList) (key, value) 조회
     *
     * @return
     *
     * @author : X0121127 <sungbum.oh@partner.sk.com>
     * @since : 2021. 1. 28.
     */
    @Override
    public Map<String, Object> selectCodeMasterListForKey() {
        Map<String, Object> resultList = null;

        try {
            List<CoCodeMasterDTO> codeList = codeRepository.selectCodeMasterList();

            // List to Map 변환
            resultList = codeList.stream().collect(Collectors.toMap(CoCodeMasterDTO::getGrpCd, i -> i));
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

}