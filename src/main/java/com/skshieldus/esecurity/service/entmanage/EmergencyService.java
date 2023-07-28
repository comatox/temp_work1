package com.skshieldus.esecurity.service.entmanage;

import java.util.List;
import java.util.Map;

public interface EmergencyService {

    /**
     * 긴급출입 등록
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 12. 16.
     */
    boolean insertEmergencyReg(Map<String, Object> paramMap);

    /**
     * 긴급출입 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 12. 16.
     */
    List<Map<String, Object>> selectEmergencyList(Map<String, Object> paramMap);

    /**
     * 긴급출입 수정
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 12. 16.
     */
    boolean updateEmergency(Map<String, Object> paramMap);

    /**
     * 긴급출입 삭제
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 12. 16.
     */
    boolean deleteEmergency(Map<String, Object> paramMap);

    /**
     * 긴급출입 상세 조회
     *
     * @param emergencyNo
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 12. 16.
     */
    Map<String, Object> selectEmerencyView(Integer emergencyNo);

    /**
     * 긴급출입(VIP) 건수 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 12. 16.
     */
    Integer selectEmerencyVipCnt(Map<String, Object> paramMap);

}
