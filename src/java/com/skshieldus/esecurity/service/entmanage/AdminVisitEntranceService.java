package com.skshieldus.esecurity.service.entmanage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface AdminVisitEntranceService {

    /**
     * 업체정보 정정 신청 현황 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0122721<jeyeon.kim@partner.sk.com>
     * @since : 2022. 01. 18.
     */
    List<Map<String, Object>> selectCompInfoChgReqList(HashMap<String, Object> paramMap);

    /**
     * 업체정보 정정 신청현황 목록 총갯수 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0122721<jeyeon.kim@partner.sk.com>
     * @since : 2022. 01. 18.
     */
    Integer selectCompInfoChgReqListCnt(HashMap<String, Object> paramMap);

    /**
     * 업체정보 정정 신청현황 상세 조회
     *
     * @param ioCompApplNo
     * @return
     *
     * @author : X0122721<jeyeon.kim@partner.sk.com>
     * @since : 2022. 01. 18.
     */
    Map<String, Object> selectCompInfoChgReqView(Integer ioCompApplNo);

    /**
     * 업체정보 정정 신청현황 승인/반려
     *
     * @param paramMap
     * @return
     *
     * @author : X0122721<jeyeon.kim@partner.sk.com>
     * @since : 2022. 01. 18.
     */
    Boolean executeCompInfoChgReqUpdate(HashMap<String, Object> paramMap);

    /**
     * 여권변경 신청 현황 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0122721<jeyeon.kim@partner.sk.com>
     * @since : 2022. 01. 19.
     */
    List<Map<String, Object>> selectPassportChgReqList(HashMap<String, Object> paramMap);

    /**
     * 여권변경 신청현황 목록 총갯수 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0122721<jeyeon.kim@partner.sk.com>
     * @since : 2022. 01. 19.
     */
    Integer selectPassportChgReqListCnt(HashMap<String, Object> paramMap);

    /**
     * 여권변경 신청현황 상세 조회
     *
     * @param passportApplNo
     * @return
     *
     * @author : X0122721<jeyeon.kim@partner.sk.com>
     * @since : 2022. 01. 19.
     */
    Map<String, Object> selectPassportChgReqView(Integer passportApplNo);

    /**
     * 여권변경 신청현황 승인/반려
     *
     * @param paramMap
     * @return
     *
     * @author : X0122721<jeyeon.kim@partner.sk.com>
     * @since : 2022. 01. 19.
     */
    Boolean executePassportChgReqUpdate(HashMap<String, Object> paramMap);

    /**
     * 소속업체 이직 신청현황 승인/반려
     *
     * @param paramMap
     * @return
     *
     * @author : X0122721<jeyeon.kim@partner.sk.com>
     * @since : 2022. 01. 20.
     */
    List<Map<String, Object>> selectCompChgReqList(HashMap<String, Object> paramMap);

    /**
     * 소속업체 이직 신청현황 목록 총갯수 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0122721<jeyeon.kim@partner.sk.com>
     * @since : 2022. 01. 20.
     */
    Integer selectCompChgReqListCnt(HashMap<String, Object> paramMap);

    /**
     * 소속업체 이직 신청현황 상세 조회
     *
     * @param compApplNo
     * @return
     *
     * @author : X0122721<jeyeon.kim@partner.sk.com>
     * @since : 2022. 01. 20.
     */
    Map<String, Object> selectCompChgReqView(Integer compApplNo);

    /**
     * 소속업체 이직 신청현황 승인/반려
     *
     * @param paramMap
     * @return
     *
     * @author : X0122721<jeyeon.kim@partner.sk.com>
     * @since : 2022. 01. 20.
     */
    Boolean executeCompChgReqUpdate(HashMap<String, Object> paramMap);

}
