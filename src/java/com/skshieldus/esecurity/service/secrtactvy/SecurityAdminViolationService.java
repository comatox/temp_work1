package com.skshieldus.esecurity.service.secrtactvy;

import com.skshieldus.esecurity.common.model.ListDTO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SecurityAdminViolationService {

    /**
     * 구성원위규자 입력 > 구성원조회(사번검색)
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 16.
     */
    Map<String, Object> selectCoEmpInfo(HashMap<String, Object> paramMap);

    /**
     * 구성원위규자 입력
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 16.
     */
    Boolean coViolationSave(HashMap<String, Object> paramMap);

    /**
     * 구성원위규자(ssm) 입력
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 16.
     */
    Boolean coViolationSsmSave(HashMap<String, Object> paramMap);

    /**
     * 구성원위규자 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 19.
     */
    ListDTO<Map<String, Object>> selectCoViolationList(HashMap<String, Object> paramMap);

    /**
     * 구성원위규자 상세 > 동일 위규 이력 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 19.
     */
    List<Map<String, Object>> selectCoViolationSameHistoryList(HashMap<String, Object> paramMap);

    /**
     * 구성원위규자 상세 > 조치현황 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 22.
     */
    List<Map<String, Object>> selectCoViolationActHistoryList(HashMap<String, Object> paramMap);

    /**
     * 구성원위규자 상세 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 22.
     */
    Map<String, Object> selectCoViolationDetail(HashMap<String, Object> paramMap);

    /**
     * 구성원위규자 조치실행
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 22.
     */
    Boolean coEmpViolation_Act(HashMap<String, Object> paramMap);

    /**
     * 구성원위규자 조치실행(Mobile)
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 22.
     */
    Boolean coEmpViolation_Mobile_Act(HashMap<String, Object> paramMap);

    /**
     * 외부인 위규자 입력 > 회원정보 검색
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 23.
     */
    List<Map<String, Object>> selectIoEmpInfo(HashMap<String, Object> paramMap);

    /**
     * 외부인 위규자 입력 > 회원정보 총갯수 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 23.
     */
    int selectIoEmpInfoCnt(HashMap<String, Object> paramMap);

    /**
     * 접견자 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 23.
     */
    List<Map<String, Object>> selectIoViolationInterviewerList(HashMap<String, Object> paramMap);

    /**
     * 외부인위규자 입력
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 24.
     */
    Boolean ioViolationSave(HashMap<String, Object> paramMap);

    /**
     * 외부인위규자(ssm) 입력
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 24.
     */
    Boolean ioViolationSsmSave(HashMap<String, Object> paramMap);

    /**
     * 외부인위규자 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 25.
     */
    ListDTO<Map<String, Object>> selectIoViolationList(HashMap<String, Object> paramMap);

    /**
     * 외부인위규자 목록 총갯수 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 02.
     */
    int selectIoViolationListCnt(HashMap<String, Object> paramMap);

    /**
     * 외부인위규자 상세 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 26.
     */
    Map<String, Object> selectIoViolationDetail(HashMap<String, Object> paramMap);

    /**
     * 외부인 위규자 상세 > 접견자 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 26.
     */
    List<Map<String, Object>> selectIoViolationDetailInterviewerList(HashMap<String, Object> paramMap);

    /**
     * 외부인 위규자 상세 > 동일 위규 이력 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 26.
     */
    List<Map<String, Object>> selectIoViolationSameHistoryList(HashMap<String, Object> paramMap);

    /**
     * 외부인 위규자 상세 > 조치현황 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 26.
     */
    List<Map<String, Object>> selectIoViolationActHistoryList(HashMap<String, Object> paramMap);

    /**
     * 외부인 위규자 조치실행
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 26.
     */
    Boolean ioEmpViolation_Act(HashMap<String, Object> paramMap);

    /**
     * 외부인 위규자 조치실행(mobile)
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 26.
     */
    Boolean ioEmpViolation_Mobile_Act(HashMap<String, Object> paramMap);

    /**
     * 구성원 위규자조회 (보안요원) 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 02.
     */
    ListDTO<Map<String, Object>> selectCoViolationSecList(HashMap<String, Object> paramMap);

    /**
     * 구성원 위규자조회 (보안요원) 목록 총갯수 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 02.
     */
//    int selectCoViolationSecListCnt(HashMap<String, Object> paramMap);

    /**
     * 구성원 위규자조회 (보안요원) 삭제
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 02.
     */
    Boolean coEmpViolationSecDelete(HashMap<String, Object> paramMap);

    /**
     * 외부인 위규자조회 (보안요원) 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 02.
     */
    ListDTO<Map<String, Object>> selectIoViolationSecList(HashMap<String, Object> paramMap);

    /**
     * 외부인 위규자조회 (보안요원) 목록 총 갯수
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 02.
     */
//    int selectIoViolationSecListCnt(HashMap<String, Object> paramMap);

    /**
     * 외부인 위규자조회 (보안요원) 삭제
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 02.
     */
    Boolean ioEmpViolationSecDelete(HashMap<String, Object> paramMap);

    /**
     * 외부인위규자 자동징구 가능여부 조회 (Y/N)
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 17.
     */
    String selectAutoActYn(HashMap<String, Object> paramMap);

    /**
     * 외부인위규자 결재 목록 조회
     *
     * @param scIoDocNo
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 1. 26.
     */
    List<Map<String, Object>> selectIoViolationApprList(Integer scIoDocNo);

    /**
     * 메인 > 보안활동 > 구성원 위규자 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 2. 16.
     */
    List<Map<String, Object>> selectMainScCoOfendList(Map<String, Object> paramMap);

    /**
     * 메인 > 보안활동 > 구성원 위규자 목록 건수 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 2. 16.
     */
    Integer selectMainScCoOfendListCnt(Map<String, Object> paramMap);

    /**
     * 메인 > 보안활동 > 외부인 위규자 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 2. 16.
     */
    List<Map<String, Object>> selectMainScIoOfendList(Map<String, Object> paramMap);

    /**
     * 메인 > 보안활동 > 외부인 위규자 목록 건수 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 2. 16.
     */
    Integer selectMainScIoOfendListCnt(Map<String, Object> paramMap);

    /** 구성원 위규자 조치 실행  */
    Boolean coEmpViolationActDo(HashMap<String, Object> paramMap);

    /** 구성원 위규자 2차 메일 발송  */
    Boolean coViolationSecondaryMail(HashMap<String, Object> paramMap);

    /** 외부인 위규자 조치실행 */
    Boolean ioEmpViolationActDo(HashMap<String, Object> paramMap);

}
