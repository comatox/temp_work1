package com.skshieldus.esecurity.service.secrtactvy;

import java.util.List;
import java.util.Map;

public interface SecurityRectifyPlanService {

    /**
     * 시정개선계획서(보안위규 이력조회) 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 10. 07.
     */
    List<Map<String, Object>> selectSecurityRectifyPlanList(Map<String, Object> paramMap);

    /**
     * 시정개선계획서(보안위규 이력조회) 목록 조회 카운트
     *
     * @param paramMap
     * @return
     */
    Integer selectSecurityRectifyPlanListCnt(Map<String, Object> paramMap);

    /**
     * 시정개선계획서(보안위규 이력조회) 상세 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 10. 07.
     */
    Map<String, Object> selectSecurityRectifyPlanView(Map<String, Object> paramMap);

    /**
     * 시정개선계획서 제출 (상신)
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 10. 08.
     */
    boolean insertRectifyPlan(Map<String, Object> paramMap);

}
