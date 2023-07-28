package com.skshieldus.esecurity.service.common.impl;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.utils.CollectionUtils;
import com.skshieldus.esecurity.model.common.PartnerDTO;
import com.skshieldus.esecurity.model.common.PartnerSearchDTO;
import com.skshieldus.esecurity.repository.common.PartnerRepository;
import com.skshieldus.esecurity.service.common.PartnerService;
import org.springframework.beans.BeanUtils;
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
public class PartnerServiceImpl implements PartnerService {

    @Autowired
    private PartnerRepository partnerRepository;

    @Override
    public List<PartnerDTO> selectPartnerList(PartnerSearchDTO partnerSearchDTO) {

        try {
            if (partnerSearchDTO.getSystempartnerids() != null && partnerSearchDTO.getSystempartnerids().size() > 999) {
                List<PartnerDTO> resultList = new ArrayList<>();

                // oracle in clause 건수 제한으로 인해 999건씩 조회
                Collection<List<Integer>> idList = CollectionUtils.partitionBySize(partnerSearchDTO.getSystempartnerids(), 999);
                idList.stream().forEach(list -> {

                    PartnerSearchDTO tmpDTO = new PartnerSearchDTO();
                    BeanUtils.copyProperties(partnerSearchDTO, tmpDTO);
                    tmpDTO.setSystempartnerids(list);

                    List<PartnerDTO> tmpList = partnerRepository.selectPartnerList(tmpDTO);

                    resultList.addAll(tmpList);
                });

                return resultList;
            }
            else {
                return partnerRepository.selectPartnerList(partnerSearchDTO);
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
    }

    @Override
    public Map<Integer, Object> selectPartnerListForKey(PartnerSearchDTO partnerSearchDTO) {
        Map<Integer, Object> resultList = null;

        try {
            List<PartnerDTO> partnerList = partnerRepository.selectPartnerList(partnerSearchDTO);

            // List to Map 변환
            resultList = partnerList.stream().collect(Collectors.toMap(PartnerDTO::getSystempartnerid, i -> i));
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Integer selectPartnerListCnt(PartnerSearchDTO partnerSearchDTO) {
        Integer resultCnt = null;

        try {
            resultCnt = partnerRepository.selectPartnerListCnt(partnerSearchDTO);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

    @Override
    public PartnerDTO selectPartner(String compId) {
        PartnerDTO result = null;

        try {
            result = partnerRepository.selectPartner(compId);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    /****
     * 외부업체 관리 기능(등록, 수정, 삭제) 추가
     * 2021-03-31 박대호
     */
    @Override
    public boolean insertPartner(PartnerDTO partnerDTO) {
        boolean result = true;

        try {
            if (partnerRepository.insertPartner(partnerDTO) != 1) {
                result = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public boolean updatePartner(PartnerDTO partnerDTO) {
        boolean result = true;

        try {
            if (partnerRepository.updatePartner(partnerDTO) != 1) {
                result = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public boolean deletePartner(String systempartnerid) {
        boolean result = true;

        try {
            if (partnerRepository.deletePartner(systempartnerid) != 1) {
                result = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public String partnerLegChk(PartnerDTO partnerDTO) {
        String result = "";

        try {
            result = partnerRepository.partnerLegChk(partnerDTO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

}