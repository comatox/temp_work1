package com.skshieldus.esecurity.service.entmanage;

import com.skshieldus.esecurity.model.common.CommonXlsViewDTO;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import java.util.List;
import java.util.Map;

public interface IoTmpCarReqstService {

    // 임시차량출입 신청 현황
    public List<Map<String, Object>> selectIoTmpCarReqstList(Map<String, Object> paramMap);

    public Map<String, Object> selectIoTmpCarReqstView(Map<String, Object> paramMap);

    // 임시차량출입 신청
    public boolean insertIoTmpCarReqst(SessionInfoVO sessionInfoVO, Map<String, Object> paramMap);

    // 임시차량출입 신청 엑셀 다운로드
    public CommonXlsViewDTO selectIoTmpCarReqstListExcel(Map<String, Object> paramMap);

}
