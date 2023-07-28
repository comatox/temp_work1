package com.skshieldus.esecurity.service.entmanage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PassService {

    /**
     * 상시출입증 접수현황 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 10. 18.
     */
    List<Map<String, Object>> selectRegularPassList(Map<String, Object> paramMap);

    /**
     * 상시출입증 접수현황 건수 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 10. 18.
     */
    Integer selectRegularPassListCnt(Map<String, Object> paramMap);

    /**
     * 상시출입증 제재여부 조회 및 처리
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 10. 18.
     */
    String selectRegularPassChkRestrict(Map<String, Object> paramMap);

    /**
     * 상시출입증 접수 상신
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 10. 21.
     */
    Boolean approvalRegularPass(Map<String, Object> paramMap);

    /**
     * 상시출입증 접수 승인
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 10. 21.
     */
    Map<String, Object> approveRegularPass(Map<String, Object> paramMap);

    /**
     * 상시출입증 접수 반려
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 10. 21.
     */
    Boolean rejectRegularPass(Map<String, Object> paramMap);

    /**
     * 상시출입증 관리 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 10. 22.
     */
    List<Map<String, Object>> selectRegularPassMngList(Map<String, Object> paramMap);

    /**
     * 상시출입증 관리 건수 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 10. 22.
     */
    Integer selectRegularPassMngListCnt(Map<String, Object> paramMap);

    /**
     * 상시출입증 관리 상세 조회
     *
     * @param passApplNo
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 10. 22.
     */
    Map<String, Object> selectRegularPassMng(Integer passApplNo);

    /**
     * 상시출입증 관리 변경 이력 목록 조회
     *
     * @param cardNo
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 10. 22.
     */
    List<Map<String, Object>> selectRegularPassMngChangeHistory(String cardNo);

    /**
     * 상시출입증 강제만료 처리
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 10. 27.
     */
    Boolean expiredRegularPassMng(Map<String, Object> paramMap);

    /**
     * 상시출입증 1달 기간연장 처리
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 10. 27.
     */
    Boolean extendRegularPassMng(Map<String, Object> paramMap);

    /**
     * 사전 정지예외 신청현황 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 10. 26.
     */
    List<Map<String, Object>> selectRegularPassExcptList(Map<String, Object> paramMap);

    /**
     * 사전 정지예외 신청현황 목록 건수 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 10. 26.
     */
    Integer selectRegularPassExcptListCnt(Map<String, Object> paramMap);

    /**
     * 사전 정지예외 신청현황 상세 조회
     *
     * @param excptApplNo
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 10. 27.
     */
    Map<String, Object> selectRegularPassExcptDetail(Integer excptApplNo);

    /**
     * 사전 정지예외 신청현황 정보 조회 (결재 상신 시)
     *
     * @param excptApplNo
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 1.
     */
    Map<String, Object> selectRegularPassExcpt(Integer excptApplNo);

    /**
     * 사전 정지예외 신청 상신
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 10. 27.
     */
    Boolean approvalRegularPassExcpt(Map<String, Object> paramMap);

    /**
     * 사전 정지예외 신청 반려
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 10. 27.
     */
    Boolean rejectRegularPassExcpt(Map<String, Object> paramMap);

    /**
     * 사후 정지예외 신청현황 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 02.
     */
    List<Map<String, Object>> selectRegularPassCancelList(Map<String, Object> paramMap);

    /**
     * 사후 정지예외 신청현황 목록 건수 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 02.
     */
    Integer selectRegularPassCancelListCnt(Map<String, Object> paramMap);

    /**
     * 사후 정지예외 신청현황 정보 조회
     *
     * @param cancelApplNo
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 02.
     */
    Map<String, Object> selectRegularPassCancel(Integer cancelApplNo);

    /**
     * 사후 정지예외 신청 상신
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 02.
     */
    Boolean approvalRegularPassCancel(Map<String, Object> paramMap);

    /**
     * 사후 정지예외 신청 반려
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 02.
     */
    Boolean rejectRegularPassCancel(Map<String, Object> paramMap);

    /**
     * 도급업체 인력변경 신청현황 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 02.
     */
    List<Map<String, Object>> selectInsSubcontMoveList(Map<String, Object> paramMap);

    /**
     * 도급업체 인력변경 신청현황 목록 건수 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 02.
     */
    Integer selectInsSubcontMoveListCnt(Map<String, Object> paramMap);

    /**
     * 도급업체 인력변경 신청현황 정보 조회
     *
     * @param moveApplNo
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 02.
     */
    Map<String, Object> selectInsSubcontMove(Integer moveApplNo);

    /**
     * 도급업체 인력변경 신청 승인
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 02.
     */
    Boolean approveInsSubcontMove(Map<String, Object> paramMap);

    /**
     * 도급업체 인력변경 신청 반려
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 02.
     */
    Boolean rejectInsSubcontMove(Map<String, Object> paramMap);

    /**
     * 대표관리자 접수상신 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 09.
     */
    List<Map<String, Object>> selectIoCompCoorpVendorList(Map<String, Object> paramMap);

    /**
     * 대표관리자 접수상신 목록 건수 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 09.
     */
    Integer selectIoCompCoorpVendorListCnt(Map<String, Object> paramMap);

    /**
     * 대표관리자 접수상신 정보 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 11.
     */
    Map<String, Object> selectIoCompCoorpVendor(Map<String, Object> paramMap);

    /**
     * 대표관리자 접수상신 승인
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 09.
     */
    Boolean approveIoCompCoorpVendor(Map<String, Object> paramMap);

    /**
     * 대표관리자 접수상신 반려
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 09.
     */
    Boolean rejectIoCompCoorpVendor(Map<String, Object> paramMap);

    /**
     * 상시출입 건수 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 30.
     */
    Integer selectCountIdCard(Map<String, Object> paramMap);

    /**
     * (관리자) 출입증 발급현황 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 15.
     */
    List<Map<String, Object>> selectAdmPassList(Map<String, Object> paramMap);

    /**
     * (관리자) 출입증 발급현황 목록 건수 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 15.
     */
    Integer selectAdmPassListCnt(Map<String, Object> paramMap);

    /**
     * (관리자) 협력업체 현황 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 15.
     */
    List<Map<String, Object>> selectAdmIoCompCoorpList(Map<String, Object> paramMap);

    /**
     * (관리자) 협력업체 현황 목록 건수 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 15.
     */
    Integer selectAdmIoCompCoorpListCnt(Map<String, Object> paramMap);

    /**
     * (관리자) 협력업체 현황 상세 조회
     *
     * @param ioCompId
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 16.
     */
    Map<String, Object> selectAdmIoCompCoorp(String ioCompId);

    /**
     * (관리자) 협력업체 현황 > 도급업체여부 변경
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 16.
     */
    Boolean updateAdmIoCompCoorpSubcont(Map<String, Object> paramMap);

    /**
     * (관리자) 출입증 강제만료 현황 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 15.
     */
    List<Map<String, Object>> selectAdmPassExpireList(Map<String, Object> paramMap);

    /**
     * (관리자) 출입증 강제만료 현황 목록 건수 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 15.
     */
    Integer selectAdmPassExpireListCnt(Map<String, Object> paramMap);

    /**
     * (관리자) 구성원 기간연장 현황 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 15.
     */
    List<Map<String, Object>> selectAdmPassExtendList(Map<String, Object> paramMap);

    /**
     * (관리자) 구성원 기간연장 현황 목록 건수 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 15.
     */
    Integer selectAdmPassExtendListCnt(Map<String, Object> paramMap);

    /**
     * 장기예외신청현황(산업보안) 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 1. 3.
     */
    List<Map<String, Object>> selectRegularPassExcptJangList(Map<String, Object> paramMap);

    /**
     * 장기예외신청현황(산업보안) 목록 건수 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 1. 3.
     */
    Integer selectRegularPassExcptJangListCnt(Map<String, Object> paramMap);

    /**
     * 장기예외신청현황(산업보안) 상세 조회
     *
     * @param excptApplNo
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 1. 3.
     */
    Map<String, Object> selectRegularPassExcptJang(Integer excptApplNo);

    /**
     * 장기예외신청현황(산업보안) 삭제
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 1. 3.
     */
    Boolean deleteRegularPassExcptJang(Map<String, Object> paramMap);

    /**
     * 상시출입증 장기예외신청 등록
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 1. 3.
     */
    Boolean insertRegularPassExcptJang(Map<String, Object> paramMap);

    /**
     * (관리자) 상시출입증 보안교육 현황 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 1. 5.
     */
    List<Map<String, Object>> selectAdmPassSecEduList(Map<String, Object> paramMap);

    /**
     * (관리자) 상시출입증 보안교육 현황 목록 건수 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 1. 5.
     */
    Integer selectAdmPassSecEduListCnt(Map<String, Object> paramMap);

    /**
     * (관리자) 상시출입증 보안교육 상세 조회
     *
     * @param excptApplNo
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 1. 5.
     */
    Map<String, Object> selectAdmPassSecEdu(Integer excptApplNo);

    /**
     * (관리자) 상시출입증 보안교육 - 교육이수처리
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 1. 7.
     */
    Boolean updateAdmPassSecEduIsuProc(HashMap<String, Object> paramMap);

    /**
     * (관리자) 상시출입증 보안교육 - 위규등록
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 1. 7.
     */
    Boolean registAdmPassSecEduViolation(HashMap<String, Object> paramMap);

    /**
     * (관리자) 상시출입증 보안교육 - 위규취소
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 1. 7.
     */
    Boolean cancelAdmPassSecEduViolation(HashMap<String, Object> paramMap);

    /**
     * 출입증 정지/해지 현황 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 1. 13.
     */
    List<Map<String, Object>> selectRegularPassExprHistList(Map<String, Object> paramMap);

    /**
     * 출입증 정지/해지 현황 목록 건수 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 1. 13.
     */
    Integer selectRegularPassExprHistListCnt(Map<String, Object> paramMap);

    /**
     * 출입증 정지/해지 현황 상세 조회
     *
     * @param exprApplNo
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 1. 13.
     */
    Map<String, Object> selectRegularPassExprHist(Integer exprApplNo);

    /**
     * 통제구역 출입권한 삭제 현황 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 1. 13.
     */
    List<Map<String, Object>> selectSpecialPassCancList(Map<String, Object> paramMap);

    /**
     * 통제구역 출입권한 삭제 현황 목록 건수 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 1. 13.
     */
    Integer selectSpecialPassCancListCnt(Map<String, Object> paramMap);

    /**
     * 통제구역 출입권한 자동 삭제 현황 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 1. 13.
     */
    List<Map<String, Object>> selectSpecialPassAutoCancList(Map<String, Object> paramMap);

    /**
     * 통제구역 출입권한 자동 삭제 현황 목록 건수 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 1. 13.
     */
    Integer selectSpecialPassAutoCancListCnt(Map<String, Object> paramMap);

}
