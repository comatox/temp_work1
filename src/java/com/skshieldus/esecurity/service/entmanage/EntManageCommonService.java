package com.skshieldus.esecurity.service.entmanage;

import java.util.List;
import java.util.Map;

public interface EntManageCommonService {

    /**
     * 신청대상자 조회(출입증신청NO [PASS_APPL_NO] 이용)
     *
     * @param passApplNo
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 9. 15.
     */
    Map<String, Object> selectPassReceipt(Integer passApplNo);

    /**
     * 신청대상자 조회(출입건물등록신청번호[PASS_BLDG_APPL_NO] 이용)
     *
     * @param passBldgApplNo
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 9. 15.
     */
    Map<String, Object> selectPassReceiptByBldg(Integer passBldgApplNo);

    /**
     * 출입증-카드키_통합아이디(SHIXXXXXXX) 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 9. 15.
     */
    String selectPassIDcardId(Map<String, Object> paramMap);

    /**
     * 출입건물 목록 조회(Old)
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 9. 15.
     */
    List<Map<String, Object>> selectOldPassBuildingList(Map<String, Object> paramMap);

    /**
     * 출입건물 목록 조회(New)
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 9. 15.
     */
    List<Map<String, Object>> selectNewPassBuildingList(Map<String, Object> paramMap);

    /**
     * 출입증 신청 시 건물정보 목록 조회
     *
     * @param params
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 9. 15.
     */
    List<Map<String, Object>> selectPassRequestCoBldgList(String[] params);

    /**
     * 상시출입증 정지및 제한 여부 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 9. 15.
     */
    Map<String, Object> selectPassInsStopDenyInfo(Map<String, Object> paramMap);

    /**
     * 상시출입증 보안교육 현황 상세 조회
     *
     * @param passApplNo
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 9. 16.
     */
    Map<String, Object> selectPassSecEdu(Integer passApplNo);

    /**
     * 캠퍼스별 반입건물 목록 조회
     *
     * @param compId
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 14.
     */
    List<Map<String, Object>> selectCompGateList(String compId);

    /**
     * 외부인 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 12. 15.
     */
    List<Map<String, Object>> selectIoEmpExtList(Map<String, Object> paramMap);

    /**
     * 외부인 검증 정보 조회
     *
     * @param ioEmpId
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 12. 15.
     */
    Map<String, Object> selectIoEmpExtCheckInfo(String ioEmpId);

    /**
     * 건물 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 21.
     */
    List<Map<String, Object>> selectBuildingList(Map<String, Object> paramMap);

    /**
     * 외부인 목록 (dmIoEmpEnterAllCompList)
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 1. 4.
     */
    List<Map<String, Object>> selectIoEmpEnterAllCompList(Map<String, Object> paramMap);

    /**
     * 외부인 목록건수 (dmIoEmpEnterAllCompList)
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 1. 4.
     */
    Integer selectIoEmpEnterAllCompListCnt(Map<String, Object> paramMap);

    Boolean sendSMS(Map<String, Object> paramMap);

}
