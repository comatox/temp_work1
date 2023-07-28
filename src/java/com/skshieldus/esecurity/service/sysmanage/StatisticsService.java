package com.skshieldus.esecurity.service.sysmanage;

import com.skshieldus.esecurity.model.common.CommonXlsViewDTO;
import java.util.List;
import java.util.Map;

public interface StatisticsService {

    /**
     * 통계 보안담당자 부서조회
     *
     * @return
     *
     * @author : X0125104 <won.shin@partner.sk.com>
     * @since : 2022. 01. 10
     */
    List<Map<String, Object>> selectSecDeptsCombo(String secEmpId);

    /**
     * 통계 보안담당자 부서조회
     *
     * @return
     *
     * @author : X0125104 <won.shin@partner.sk.com>
     * @since : 2022. 01. 10
     */
    CommonXlsViewDTO selectStatisticsExcel(Map<String, Object> paramMap);

}