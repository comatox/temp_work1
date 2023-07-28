/**
 *
 */
package com.skshieldus.esecurity.service.sysmanage.impl;

import com.skshieldus.esecurity.common.model.ListDTO;
import com.skshieldus.esecurity.repository.sysmanage.BuildingManageRepository;
import com.skshieldus.esecurity.service.sysmanage.BuildingManageService;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 건물관리(방문처)
 *
 * @author : X0115378 <jaehoon5.kim@partner.sk.com>
 * @since : 2022. 01. 19.
 *
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BuildingManageServiceImpl implements BuildingManageService {

    @Autowired
    private BuildingManageRepository repository;

    // 최상위 건물 목록 조회
    @Override
    public List<Map<String, Object>> selectGateListTop(String compId) {

        return repository.selectGateListTop(compId);
    }

    // 상위 건물 목록 조회
    @Override
    public List<Map<String, Object>> selectGateListSub(Map<String, Object> param) {

        return repository.selectGateListSub(param);
    }

    // 건물 목록 조회
    @Override
    public ListDTO<Map<String, Object>> selectBuildingList(Map<String, Object> param) {

        return ListDTO.getInstance(repository.selectBuildingList(param), repository.selectBuildingCount(param));
    }

    // 건물 상세 조회
    @Override
    public Map<String, Object> selectBuildingView(String gateId) {

        return repository.selectBuildingView(gateId);
    }

    // 건물 등록
    @Override
    public Integer insertBuilding(Map<String, Object> detail) {

        return repository.insertBuilding(detail);
    }

    // 건물 수정
    @Override
    public Integer updateBuilding(Map<String, Object> detail) {

        return repository.updateBuilding(detail);
    }

}
