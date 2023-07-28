package com.skshieldus.esecurity.service.secrtactvy;

import com.skshieldus.esecurity.common.model.ListDTO;
import com.skshieldus.esecurity.model.common.CommonXlsViewDTO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SecurityEduPledgeService {

    /**
     * 보직이동서약 목록 조회
     *
     * @param paramMap
     * @return List
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 9. 13.
     */
    List<Map<String, Object>> selectPositionMovePledgeList(Map<String, Object> paramMap);

    /**
     * 보직이동서약 목록건수 조회
     *
     * @param paramMap
     * @return Integer
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 9. 13.
     */
    Integer selectPositionMovePledgeListCnt(Map<String, Object> paramMap);

    /**
     * 보직이동서약 상세 조회
     *
     * @param coPositionPledgeNo
     * @return Map
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 9. 13.
     */
    Map<String, Object> selectPositionMovePledge(Integer coPositionPledgeNo);

    /**
     * 보직이동서약 등록
     *
     * @param paramMap
     * @return Boolean
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 9. 13.
     */
    Boolean insertPositionMovePledge(Map<String, Object> paramMap);

    /**
     * 보직이동서약 엑셀 다운로드
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 10. 8.
     */
    CommonXlsViewDTO selectPositionMovePledgeViewDownload(Map<String, Object> paramMap);

    /**
     * 국가핵심기술보호서약-구성원 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 9. 13.
     */
    List<Map<String, Object>> selectProtectionPledgeList(Map<String, Object> paramMap);

    /**
     * 국가핵심기술보호서약-구성원 목록건수 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 9. 13.
     */
    Integer selectProtectionPledgeListCnt(Map<String, Object> paramMap);

    /**
     * 국가핵심기술보호서약-구성원 상세 조회
     *
     * @param coPositionPledgeNo
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 9. 13.
     */
    Map<String, Object> selectProtectionPledge(Integer coPositionPledgeNo);

    /**
     * 국가핵심기술보호서약-외부인 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 9. 13.
     */
    List<Map<String, Object>> selectIoProtectionPledgeList(Map<String, Object> paramMap);

    /**
     * 국가핵심기술보호서약-외부인 목록건수 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 9. 13.
     */
    Integer selectIoProtectionPledgeListCnt(Map<String, Object> paramMap);

    /**
     * 국가핵심기술보호서약-외부인(사용자화면) 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 9. 13.
     */
    List<Map<String, Object>> selectIoProtectionPledgeListByUser(Map<String, Object> paramMap);

    /**
     * 국가핵심기술보호서약-외부인(사용자화면) 목록건수 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 9. 13.
     */
    Integer selectIoProtectionPledgeListCntByUser(Map<String, Object> paramMap);

    /**
     * 국가핵심기술보호서약-구성원 상세 조회
     *
     * @param specialPledgeNo
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 9. 13.
     */
    Map<String, Object> selectIoProtectionPledge(Integer specialPledgeNo);

    /**
     * 국가핵심기술보호서약-구성원 등록
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 9. 13.
     */
    Boolean insertProtectionPledge(Map<String, Object> paramMap);

    /**
     * 국가핵심기술보호서약-구성원 엑셀 다운로드
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 10. 8.
     */
    CommonXlsViewDTO selectProtectionPledgeViewDownload(Map<String, Object> paramMap);

    /**
     * 국가핵심기술보호서약-외부인 엑셀 다운로드
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 10. 8.
     */
    CommonXlsViewDTO selectIoProtectionPledgeViewDownload(Map<String, Object> paramMap);

    /**
     * 보안교육 신청 현황 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 03.
     */
    List<Map<String, Object>> selectSecurityEducationRequestList(HashMap<String, Object> paramMap);

    /**
     * 보안교육 신청 현황 상세 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 04.
     */
    Map<String, Object> selectSecurityEducationRequestView(HashMap<String, Object> paramMap);

    /**
     * 교육 대상 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 04.
     */
    List<Map<String, Object>> selectEducationTargetList(HashMap<String, Object> paramMap);

    /**
     * 보안교육 신청 예약현황 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 04.
     */
    List<Map<String, Object>> selectEducationReservationStatus(HashMap<String, Object> paramMap);

    /**
     * 특수업무수행서약 현황(구성원/외부인) 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 05.
     */
    List<Map<String, Object>> selectSpecialTaskList(HashMap<String, Object> paramMap);

    /**
     * 특수업무수행서약 현황(구성원/외부인) 상세조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 08.
     */
    Map<String, Object> selectSpecialTaskDetail(HashMap<String, Object> paramMap);

    /**
     * 자료 제공 요청 및 파기 확인 현황 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 09.
     */
    List<Map<String, Object>> selectDataProvideList(HashMap<String, Object> paramMap);

    /**
     * 자료 제공 요청 및 파기 확인 현황 목록 엑셀 다운로드
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 09.
     */
    CommonXlsViewDTO dataProvideListDownload(HashMap<String, Object> paramMap);

    /**
     * 자료 제공 요청서 상세조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 10.
     */
    Map<String, Object> selectProvideDetail(HashMap<String, Object> paramMap);

    /**
     * 자료 파기 확인서 상세조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 10.
     */
    Map<String, Object> selectDestroyDetail(HashMap<String, Object> paramMap);

    /**
     * 협력업체 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 11.
     */
    List<Map<String, Object>> searchIoCompList(HashMap<String, Object> paramMap);

    /**
     * 협력업체 목록갯수 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 11.
     */
    int searchIoCompListCnt(HashMap<String, Object> paramMap);

    /**
     * 본사조직 부서 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 11.
     */
    List<Map<String, Object>> searchDeptList(HashMap<String, Object> paramMap);

    /**
     * 본사조직 부서목록갯수 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 11.
     */
    int searchDeptListCnt(HashMap<String, Object> paramMap);

    /**
     * 보안교육 신청 (상신)
     *
     * @param paramMap
     * @return
     *
     * @author : X0122721<jeyeon.kim@partner.sk.com>
     * @since : 2021. 11. 12.
     */
    Boolean insertSecurityEdu(HashMap<String, Object> paramMap);

    /**
     * 특수업무수행서약 등록
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 15.
     */
    Boolean specialTaskPledgeSave(HashMap<String, Object> paramMap);

    /**
     * 보안교육 신청현황 엑셀 다운로드
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 06.
     */
    CommonXlsViewDTO securityEducationRequestListDownload(HashMap<String, Object> paramMap);

    /**
     * 특수업무수행서약현황 엑셀 다운로드
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 29.
     */
    CommonXlsViewDTO specialTaskPledgeListDownload(HashMap<String, Object> paramMap);

    ListDTO<Map<String, Object>> selectSecurityEducationInfoAdmList(HashMap<String, Object> paramMap);

}
