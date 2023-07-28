package com.skshieldus.esecurity.service.sysmanage;

import com.skshieldus.esecurity.model.sysmanage.SecEduNotiEmailMngDTO;
import com.skshieldus.esecurity.model.sysmanage.SecEduNotiEmailMngSearchDTO;

public interface SecEduNotiEmailMngService {

    SecEduNotiEmailMngDTO selectSecEduNotiEmailMngView(SecEduNotiEmailMngSearchDTO secEduNotiEmailMngSearch);

    Boolean updateSecEduNotiEmailMng(SecEduNotiEmailMngDTO secEduNotiEmailMng);

}