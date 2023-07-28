package com.skshieldus.esecurity.service.entmanage;

import com.skshieldus.esecurity.model.common.SessionInfoVO;
import java.util.List;
import java.util.Map;

public interface IoDeliveryCarPassReqstService {

    // 납품차량출입 신청 현황
    public List<Map<String, Object>> selectIoDeliveryCarPassReqstList(Map<String, Object> paramMap);

    public Map<String, Object> selectIoDeliveryCarPassReqstView(Map<String, Object> paramMap);

    // 납품차량출입 신청
    public boolean insertIoDeliveryCarPassReqst(SessionInfoVO sessionInfoVO, Map<String, Object> paramMap);

}
