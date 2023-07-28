package com.skshieldus.esecurity.service.entmanage.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.repository.entmanage.AdmittedRepository;
import com.skshieldus.esecurity.service.entmanage.AdmittedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class AdmittedServiceImpl implements AdmittedService {

    @Autowired
    private AdmittedRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @SuppressWarnings("unchecked")
    @Override
    public boolean insertAdmittedReg(Map<String, Object> paramMap) {
        boolean result = true;
        try {
            List<String> gatelist = objectMapper.convertValue(paramMap.get("gatelist"), List.class);
            result = gatelist.stream().map(gateId -> {
                paramMap.put("ctrlGateId", gateId);
                // 입실 정보 등록
                return repository.insertAdmittedReg(paramMap);
            }).allMatch(count -> count == 1);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean insertVisitorAdmittedReg(Map<String, Object> paramMap) {
        boolean result = true;
        try {
            List<Map<String, Object>> visitorlist = objectMapper.convertValue(paramMap.get("visitorlist"), List.class);
            result = visitorlist.stream().map(visitorInfo -> {
                visitorInfo.put("crtBy", paramMap.get("crtBy"));
                visitorInfo.put("acIp", paramMap.get("acIp"));
                // 방문객의 경우 추가 업데이트
                repository.updateVisitorAdmittedReg(visitorInfo);
                // 입실 정보 등록
                return repository.insertAdmittedReg(visitorInfo);
            }).allMatch(count -> count == 1);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return result;
    }

    @Override
    public String selectStaffAdmittedChk(Map<String, Object> paramMap) {
        return repository.selectStaffAdmittedChk(paramMap);
    }

    @Override
    public List<Map<String, Object>> selectBuildingControlUp(Map<String, Object> paramMap) {
        List<Map<String, Object>> upList = repository.selectBuildingControlUp(paramMap);

        Map<String, Object> buildingParams = new HashMap<>();
        buildingParams.put("lev", "2");
        List<Map<String, Object>> resultList = upList.stream().map(data -> {
            buildingParams.put("gateId", data.get("gateId"));
            return repository.selectBuildingControlDownList(buildingParams);
        }).flatMap(List::stream).collect(Collectors.toList());

        return resultList;
    }

    @Override
    public List<Map<String, Object>> selectBuildingControlUpExt(Map<String, Object> paramMap) {
        return repository.selectBuildingControlUp(paramMap);
    }

    @Override
    public List<Map<String, Object>> selectBuildingControlDownList(Map<String, Object> paramMap) {
        return repository.selectBuildingControlDownList(paramMap);
    }

    @Override
    public boolean updateStaffCheckout(Map<String, Object> paramMap) {
        boolean result = true;
        // 퇴실여부 체크
        if (repository.selectStaffCheckout(paramMap) < 1) {
            result = false;
        }
        else {
            // 퇴실 처리
            repository.updateStaffCheckout(paramMap);
        }
        return result;
    }

    @Override
    public Map<String, Object> selectAlwaysAdmittedChk(Map<String, Object> paramMap) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> alwaysInfo = repository.selectAlwaysAdmittedRegChk(paramMap);
        if (alwaysInfo != null)
            resultMap.putAll(repository.selectAlwaysAdmittedRegChk(paramMap));
        resultMap.put("ctrlYn", repository.selectAlwaysAdmittedChk(paramMap));
        return resultMap;
    }

    @Override
    public List<Map<String, Object>> selectAlwaysCurrentLineList(Map<String, Object> paramMap) {
        return repository.selectAlwaysCurrentLineList(paramMap);
    }

    @Override
    public List<Map<String, Object>> selectVisitorCurrentLineList(Map<String, Object> paramMap) {
        return repository.selectVisitorCurrentLineList(paramMap);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean updateAlwaysCheckout(Map<String, Object> paramMap) {
        boolean result = true;
        try {
            List<String> ctrlAreaNoList = objectMapper.convertValue(paramMap.get("ctrlAreaNoList"), List.class);
            result = ctrlAreaNoList.stream().map(ctrlAreaNo -> {
                paramMap.put("ctrlAreaNo", ctrlAreaNo);
                return repository.updateCheckout(paramMap);
            }).allMatch(count -> count == 1);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean updateVisitorCheckout(Map<String, Object> paramMap) {
        boolean result = true;
        try {
            List<Map<String, Object>> checkoutList = objectMapper.convertValue(paramMap.get("checkoutList"), List.class);
            result = checkoutList.stream().map(checkoutInfo -> {
                checkoutInfo.put("crtBy", paramMap.get("crtBy"));
                checkoutInfo.put("acIp", paramMap.get("acIp"));
                checkoutInfo.put("ctrlStat", paramMap.get("ctrlStat"));
                repository.updateVisitorCheckout(checkoutInfo);
                return repository.updateCheckout(checkoutInfo);
            }).allMatch(count -> count == 1);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> selectVisitorAdmittedList(Map<String, Object> paramMap) {
        return repository.selectVisitorAdmittedList(paramMap);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean insertItemsImport(Map<String, Object> paramMap) {
        boolean result = true;
        try {
            List<String> gatelist = objectMapper.convertValue(paramMap.get("gatelist"), List.class);
            result = gatelist.stream().map(gateId -> {
                paramMap.put("ctrlGateId", gateId);
                // 입실 정보 등록
                return repository.insertItemsImport(paramMap);
            }).allMatch(count -> count == 1);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> selectItemsExportList(Map<String, Object> paramMap) {
        return repository.selectItemsExportList(paramMap);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean updateItemsExport(Map<String, Object> paramMap) {
        boolean result = true;
        try {
            List<String> ctrlAreaGodsNoList = objectMapper.convertValue(paramMap.get("ctrlAreaGodsNoList"), List.class);
            result = ctrlAreaGodsNoList.stream().map(ctrlAreaGodsNo -> {
                paramMap.put("ctrlAreaGodsNo", ctrlAreaGodsNo);
                // 물품반출 수정
                return repository.updateItemsExport(paramMap);
            }).allMatch(count -> count == 1);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return result;
    }

}
