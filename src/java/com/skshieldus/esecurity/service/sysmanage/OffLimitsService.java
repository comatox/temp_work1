package com.skshieldus.esecurity.service.sysmanage;

import java.util.List;
import java.util.Map;

public interface OffLimitsService {

    /**
     * 사용자 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121188<jinsoo2.ahn@partner.sk.com>
     * @since : 2021. 11. 16.
     */
    List<Map<String, Object>> selectOffLimitsList(Map<String, Object> paramMap);

    Map<String, Object> selectOffLimitsView(Map<String, Object> paramMap);

    Map<String, Object> selectOffLimitsVisitInfoView(Map<String, Object> paramMap);

}
