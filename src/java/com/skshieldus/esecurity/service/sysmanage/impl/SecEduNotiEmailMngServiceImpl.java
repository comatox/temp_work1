package com.skshieldus.esecurity.service.sysmanage.impl;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.model.sysmanage.SecEduNotiEmailMngDTO;
import com.skshieldus.esecurity.model.sysmanage.SecEduNotiEmailMngSearchDTO;
import com.skshieldus.esecurity.repository.sysmanage.SecEduNotiEmailMngRepository;
import com.skshieldus.esecurity.service.sysmanage.SecEduNotiEmailMngService;
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
public class SecEduNotiEmailMngServiceImpl implements SecEduNotiEmailMngService {

    @Autowired
    private SecEduNotiEmailMngRepository secEduNotiEmailMngRepository;

    @Override
    public SecEduNotiEmailMngDTO selectSecEduNotiEmailMngView(SecEduNotiEmailMngSearchDTO secEduNotiEmailMngSearch) {

        log.info(">>>> selectSecEduNotiEmailMngView : " + secEduNotiEmailMngSearch);

        return secEduNotiEmailMngRepository.selectSecEduNotiEmailMngView(secEduNotiEmailMngSearch);
    }

    @Override
    public Boolean updateSecEduNotiEmailMng(SecEduNotiEmailMngDTO secEduNotiEmailMng) {
        boolean result = true;
        try {

            log.info(">>>> updateSecEduNotiEmailMng : " + secEduNotiEmailMng);

            secEduNotiEmailMng.setContent(secEduNotiEmailMng.getContent().replaceAll("'''", ""));

            if (secEduNotiEmailMngRepository.updateSecEduNotiEmailMng(secEduNotiEmailMng) != 1) {
                result = false;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

}