package com.skshieldus.esecurity.service.common;

import com.skshieldus.esecurity.model.common.CoLogDTO;
import com.skshieldus.esecurity.model.common.CoSyAcLogDTO;
import com.skshieldus.esecurity.model.common.MailLogDTO;
import java.util.List;

public interface CommonLogService {

    // API 로그 등록
    boolean insertCommonLog(CoLogDTO coLogDTO);

    // 메일전송 로그 등록
    boolean insertMailLog(List<MailLogDTO> mailLogList);

    // 메뉴접속 로그 등록
    boolean insertSyAcLog(CoSyAcLogDTO coSyAcLogDTO);

}


