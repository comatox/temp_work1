package com.skshieldus.esecurity.service.common.impl;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.utils.CollectionUtils;
import com.skshieldus.esecurity.model.common.CoBldgDTO;
import com.skshieldus.esecurity.model.common.CoXempBldgOutDTO;
import com.skshieldus.esecurity.repository.common.BuildingRepository;
import com.skshieldus.esecurity.service.common.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class BuildingServiceImpl implements BuildingService {

    @Autowired
    private BuildingRepository buildingRepository;

    /**
     * 건물정보 상세 조회
     *
     * @return
     *
     * @author : X0121127 <sungbum.oh@partner.sk.com>
     * @since : 2021. 2. 16.
     */
    @Override
    public CoBldgDTO selectCoBldg(String gateId) {
        CoBldgDTO coBldgDTO = null;

        try {
            coBldgDTO = buildingRepository.selectCoBldg(gateId);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return coBldgDTO;
    }

    /**
     * 건물정보 (key, value) 목록 조회
     *
     * @return
     *
     * @author : X0121127 <sungbum.oh@partner.sk.com>
     * @since : 2021. 2. 16.
     */
    public Map<String, CoBldgDTO> selectCoBldgList(List<String> gateIdList) {
        Map<String, CoBldgDTO> resultList = null;

        try {
            // oracle in clause 건수 제한으로 인해 999건씩 조회
            List<CoBldgDTO> mergedList = new ArrayList<>();
            Collection<List<String>> sizedGateIdList = CollectionUtils.partitionBySize(gateIdList, 999);
            sizedGateIdList.stream().forEach(list -> mergedList.addAll(buildingRepository.selectCoBldgList(list)));

            // List to Map 변환
            resultList = mergedList.stream().collect(Collectors.toMap(CoBldgDTO::getGateId, i -> i));
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    /**
     * 건물보안출입문 (건물정보) 목록 조회
     *
     * @return
     *
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2021. 4. 22.
     */
    @Override
    public List<CoXempBldgOutDTO> selectCoXempBldgOut(String compId) {
        List<CoXempBldgOutDTO> resultList = null;

        resultList = buildingRepository.selectCoXempBldgOut(compId);

        return resultList;
    }

}