package com.skshieldus.esecurity.service.entmanage;

import java.util.List;
import java.util.Map;

public interface BuildingRegService {

    /**
     * 사원 카드번호 조회
     *
     * @param empId
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 7.
     */
    String selectEmpCardNo(String empId);

    /**
     * 사원 카드번호 조회
     *
     * @param empId
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 21.
     */
    String selectCardNo(String empId);

    /**
     * 이천 출입구역 건물 목록 조회 (일반구역)
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 7.
     */
    List<Map<String, Object>> selectCardKeyBldgList(Map<String, Object> paramMap);

    /**
     * 이천 출입구역 건물 목록 조회 - 층 단위 (일반구역)
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 25.
     */
    List<Map<String, Object>> selectCardKeyFloorList(Map<String, Object> paramMap);

    /**
     * 청주 출입구역 건물 목록 조회 (일반구역)
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 7.
     */
    List<Map<String, Object>> selectCardKeyCjBldgList(Map<String, Object> paramMap);

    /**
     * 이천 출입구역 목록 조회 (통제구역)
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 7.
     */
    List<Map<String, Object>> selectCardKeyGateSpeZone1List(Map<String, Object> paramMap);

    /**
     * 청주 출입구역 목록 조회 (통제구역)
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 7.
     */
    List<Map<String, Object>> selectCardKeyCjGateSpeZone1List(Map<String, Object> paramMap);

    /**
     * 이천 출입구역 목록 조회 (제한구역)
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 7.
     */
    List<Map<String, Object>> selectCardKeyGateSpeZone2List(Map<String, Object> paramMap);

    /**
     * 청주 출입구역 목록 조회 (제한구역)
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 7.
     */
    List<Map<String, Object>> selectCardKeyCjGateSpeZone2List(Map<String, Object> paramMap);

    /**
     * 이천/청주 허가부서 결재자 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 18.
     */
    List<Map<String, Object>> selectBuildPermitLine(Map<String, Object> paramMap);

    /**
     * 건물출입 신청 (상신)
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 19.
     */
    boolean insertEmpcardBuildingReg(Map<String, Object> paramMap);

    /**
     * 건물출입 즐겨찾기 등록
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 19.
     */
    boolean insertEmpcardMyGate(Map<String, Object> paramMap);

    /**
     * 건물출입 신청 현황 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 20.
     */
    List<Map<String, Object>> selectEmpCardBuildRegList(Map<String, Object> paramMap);

    /**
     * 건물출입 신청 현황 상세 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 20.
     */
    Map<String, Object> selectEmpCardBuildRegInfo(Map<String, Object> paramMap);

    /**
     * 건물출입 일괄신청
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 22.
     */
    boolean insertBuildingRegAll(Map<String, Object> paramMap);

    /**
     * 건물출입 일괄신청 현황 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 22.
     */
    List<Map<String, Object>> selectEmpCardBuildRegAllList(Map<String, Object> paramMap);

    /**
     * 건물출입 일괄신청 현황 상세 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 22.
     */
    Map<String, Object> selectEmpCardBuildRegAll(Map<String, Object> paramMap);

    /**
     * 건물출입 신청 현황 목록 조회(상시출입증)
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 22.
     */
    List<Map<String, Object>> selectPassBuildRegList(Map<String, Object> paramMap);

    /**
     * 건물출입 신청 현황 상세 조회(상시출입증)
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 25.
     */
    Map<String, Object> selectPassBuildRegView(Map<String, Object> paramMap);

    /**
     * 건물출입 신규 출입(상시출입증) 신청 상시출입증 사용자 건수 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 25.
     */
    Map<String, Object> selectIoPassCountByEmpName(Map<String, Object> paramMap);

    /**
     * 건물출입 신규 출입(상시출입증) 신청 상시출입증 사용자 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 25.
     */
    List<Map<String, Object>> selectIoPassApplNoList(Map<String, Object> paramMap);

    /**
     * 건물출입 신규 출입(상시출입증) 신청 상시출입증 사용자 상세정보 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 25.
     */
    Map<String, Object> selectPassReceiptViewByPassApplNo(Map<String, Object> paramMap);

    /**
     * 상시출입증 건물출입 신청 (상신)
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 26.
     */
    boolean insertPassBuildingReg(Map<String, Object> paramMap);

    /**
     * 통제구역 출입현황 조회조건 사업장 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 29.
     */
    List<Map<String, Object>> selectSpecialPassCompCodeList(Map<String, Object> paramMap);

    /**
     * 통제구역 출입현황 조회조건 건물 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 29.
     */
    List<Map<String, Object>> selectSpecialPassBldgCodeList(Map<String, Object> paramMap);

    /**
     * 통제구역 출입현황 조회조건 게이트 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 29.
     */
    List<Map<String, Object>> selectSpecialPassGateCodeList(Map<String, Object> paramMap);

    /**
     * 통제구역 출입현황 목록 조회(if => idcard_visit)
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 29.
     */
    List<Map<String, Object>> selectSpecialPassList(Map<String, Object> paramMap);

    /**
     * 통제구역 출입현황 건수 조회(if => idcard_visit)
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 12. 7.
     */
    Integer selectSpecialPassCount(Map<String, Object> paramMap);

    /**
     * 통제구역 출입현황 권한 삭제
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 11. 1.
     */
    boolean deleteSpecialPassMulti(Map<String, Object> paramMap);

    /**
     * 통제구역 출입현황 상세 변경이력 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 11. 1.
     */
    List<Map<String, Object>> selectPassReceiptMngChgHistList(Map<String, Object> paramMap);

    /**
     * 기 출입구역 목록 조회 (if => _IDcard_Visit)
     *
     * @param idcardId
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 12. 6.
     */
    List<Map<String, Object>> selectPassBuildingGateOldList(String idcardId);

    /**
     * 건물출입 신청(사원증) 후처리 프로세스
     *
     * @param lid
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 12. 7.
     */
    void processEmpcardBuildingRegApprovalPost(Integer lid);

    /**
     * 건물출입 신청(상시출입증) 후처리 프로세스
     *
     * @param lid
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 12. 7.
     */
    void processPassBuildingRegApprovalPost(Integer lid);

    /**
     * 건물출입 일괄신청(사원증) 후처리 프로세스
     *
     * @param lid
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 12. 7.
     */
    void processEmpcardBuildingAllRegApprovalPost(Integer lid);

    /**
     * 사원증 건물등록현황(관리자) 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2022. 2. 7.
     */
    List<Map<String, Object>> selectEmpCardBuildRegAdmList(Map<String, Object> paramMap);

    /**
     * 사원증 건물등록현황(관리자) 목록 건수 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2022. 2. 7.
     */
    Integer selectEmpCardBuildRegAdmCount(Map<String, Object> paramMap);

    /**
     * 출입증 건물등록현황(관리자) 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2022. 2. 17.
     */
    List<Map<String, Object>> selectPassBuildRegAdmList(Map<String, Object> paramMap);

    /**
     * 출입증 건물등록현황(관리자) 목록 건수 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2022. 2. 17.
     */
    Integer selectPassBuildRegAdmCount(Map<String, Object> paramMap);

}
