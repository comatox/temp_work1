/**
 *
 */
package com.skshieldus.esecurity.service.sysmanage.impl;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.repository.sysmanage.SystemSettingRepository;
import com.skshieldus.esecurity.service.sysmanage.SystemSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SystemSettingServiceImpl implements SystemSettingService {

    @Autowired
    private SystemSettingRepository repository;

    @Override
    public List<Map<String, Object>> selectThemaList(HashMap<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {

            resultList = repository.selectThemaList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Boolean updateThema(HashMap<String, Object> paramMap) {
        boolean result = false;

        try {
            String detlCd = (String) paramMap.get("detlCd");
            if (detlCd == null || "".equals(detlCd)) {
                throw new Exception("키값이 입력되지 않았습니다.");
            }

            int cnt = repository.allUpdateThema();

            if (cnt > 0) {
                int uCnt = repository.updateThema(paramMap);

                if (uCnt > 0)
                    result = true;
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

}
