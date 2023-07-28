package com.skshieldus.esecurity.service.sysmanage.impl;

import com.skshieldus.esecurity.repository.sysmanage.OffLimitsRepository;
import com.skshieldus.esecurity.service.sysmanage.OffLimitsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OffLimitsServiceImpl implements OffLimitsService {

    @Autowired
    private OffLimitsRepository repository;

    @Override
    public List<Map<String, Object>> selectOffLimitsList(Map<String, Object> paramMap) {
        log.info(">>>> selectOffLimitsList : " + paramMap);
        return repository.selectOffLimitsList(paramMap);
    }

    @Override
    public Map<String, Object> selectOffLimitsVisitInfoView(Map<String, Object> paramMap) {
        log.info(">>>> selectOffLimitsView : " + paramMap);

        Map<String, Object> offLimitsMap = repository.selectOffLimitsVisitInfoView(paramMap);

        log.info(">>>> selectOffLimitsVisitInfoView offLimitsMap: " + offLimitsMap);

        List<Map<String, Object>> historyList = repository.selectOffLimitsVisitInfoHistoryList(paramMap);

        log.info(">>>> selectOffLimitsVisitInfoHistoryList historyList: " + historyList);

        offLimitsMap.put("historyList", historyList);

        log.info(">>>> offLimitsMap result: " + offLimitsMap);

        return offLimitsMap;
    }

    @Override
    public Map<String, Object> selectOffLimitsView(Map<String, Object> paramMap) {
        log.info(">>>> selectOffLimitsView : " + paramMap);

        Map<String, Object> offLimitsMap = repository.selectOffLimitsView(paramMap);

        return offLimitsMap;
    }

}
