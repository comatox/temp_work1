package com.skshieldus.esecurity.service.entmanage;

import com.skshieldus.esecurity.model.common.SessionInfoVO;
import java.util.List;
import java.util.Map;

public interface CarPassReqstService {

    // 방문차량출입 신청 현황
    public List<Map<String, Object>> selectCarPassReqstList(Map<String, Object> paramMap);

    public Map<String, Object> selectCarPassReqstView(Map<String, Object> paramMap);

    // 방문차량출입 부서별 쿼터 조회
    public List<Map<String, Object>> selectCarPassQuotaCheck(Map<String, Object> paramMap);

    // 방문차량출입 신청
    public boolean insertCarPassReqst(SessionInfoVO sessionInfoVO, Map<String, Object> paramMap);

}
