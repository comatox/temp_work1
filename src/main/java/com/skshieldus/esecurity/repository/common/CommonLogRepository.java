package com.skshieldus.esecurity.repository.common;

import com.skshieldus.esecurity.model.common.CoLogDTO;
import com.skshieldus.esecurity.model.common.CoSyAcLogDTO;
import com.skshieldus.esecurity.model.common.MailLogDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommonLogRepository {

    // API 로그 등록
    int insertCommonLog(CoLogDTO coLogDTO);

    // 메일전송 로그 등록
    int insertMailLog(MailLogDTO mailLogDTO);

    // 메뉴접속 로그 등록
    int insertSyAcLog(CoSyAcLogDTO coSyAcLogDTO);

}