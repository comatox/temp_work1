package com.skshieldus.esecurity.service.inoutasset.impl;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.repository.inoutasset.InoutCommonRepository;
import com.skshieldus.esecurity.service.inoutasset.InoutCommonService;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class InoutCommonServiceImpl implements InoutCommonService {

    @Autowired
    private InoutCommonRepository repository;

    @Override
    public Map<String, Object> selectInOutView(Integer inoutApplNo) {
        Map<String, Object> resultMap = new HashMap<>();

        try {
            Map<String, Object> viewInfo = repository.selectInOutWriteView(inoutApplNo);
            resultMap.put("viewInfo", viewInfo);
            resultMap.put("articleList", repository.selectInOutArticleList(inoutApplNo));
            resultMap.put("processInfo", repository.selectInoutProcess(inoutApplNo));

            Map<String, Object> historyMap = new HashMap<>();
            historyMap.put("indate", repository.selectInDateChangeHistory(viewInfo));
            historyMap.put("emp", repository.selectEmpChangeHistory(viewInfo));
            historyMap.put("inoutknd", repository.selectInOutKndChangeHistory(viewInfo));
            historyMap.put("finish", repository.selectFinishChangeHistory(viewInfo));
            historyMap.put("calling", repository.selectCallingHistory(viewInfo));

            resultMap.put("history", historyMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultMap;
    }

}
