package com.skshieldus.esecurity.service.entmanage;

import com.skshieldus.esecurity.model.common.SessionInfoVO;
import java.util.Map;

public interface SafetyCarPassService {

    // 안전작업차량 출입 신청 현황
    public Map<String, Object> selectSafetyCarPassList(Map<String, Object> paramMap);

    // 안전작업차량 출입 신청 상세현황
    public Map<String, Object> selectSafetyCarPassView(Map<String, Object> paramMap);

    // 안전작업차량 출입 신청
    public boolean insertSafetyCarPassRequest(SessionInfoVO sessionInfoVO, Map<String, Object> paramMap);
}
