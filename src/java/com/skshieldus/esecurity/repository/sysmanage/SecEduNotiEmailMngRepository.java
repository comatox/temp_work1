package com.skshieldus.esecurity.repository.sysmanage;

import com.skshieldus.esecurity.model.sysmanage.SecEduNotiEmailMngDTO;
import com.skshieldus.esecurity.model.sysmanage.SecEduNotiEmailMngSearchDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SecEduNotiEmailMngRepository {

    SecEduNotiEmailMngDTO selectSecEduNotiEmailMngView(SecEduNotiEmailMngSearchDTO secEduNotiEmailMngSearch);

    int updateSecEduNotiEmailMng(SecEduNotiEmailMngDTO secEduNotiEmailMng);

}