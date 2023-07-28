package com.skshieldus.esecurity.service.entmanage;

import java.util.List;
import java.util.Map;

public interface AdmittedService {

    /**
     * 통제구역 입실 처리
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2022. 1. 5.
     */
    boolean insertAdmittedReg(Map<String, Object> paramMap);

    /**
     * 임직원 입실 체크
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2022. 1. 5.
     */
    String selectStaffAdmittedChk(Map<String, Object> paramMap);

    /**
     * 상위 건물 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2022. 1. 5.
     */
    List<Map<String, Object>> selectBuildingControlUp(Map<String, Object> paramMap);

    /**
     * 상위 건물 목록 조회(상시출입객)
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2022. 1. 12.
     */
    List<Map<String, Object>> selectBuildingControlUpExt(Map<String, Object> paramMap);

    /**
     * 하위 건물(Bay) 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2022. 1. 5.
     */
    List<Map<String, Object>> selectBuildingControlDownList(Map<String, Object> paramMap);

    /**
     * 통제구역 퇴실 처리
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2022. 1. 10.
     */
    boolean updateStaffCheckout(Map<String, Object> paramMap);

    /**
     * 상시출입객 입실 정보 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2022. 1. 12.
     */
    Map<String, Object> selectAlwaysAdmittedChk(Map<String, Object> paramMap);

    /**
     * 상시출입객퇴실 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2022. 1. 12.
     */
    List<Map<String, Object>> selectAlwaysCurrentLineList(Map<String, Object> paramMap);

    /**
     * 방문객퇴실 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2022. 1. 14.
     */
    List<Map<String, Object>> selectVisitorCurrentLineList(Map<String, Object> paramMap);

    /**
     * 통제구역 퇴실 정보 수정(상시출입객)
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2022. 1. 12.
     */
    boolean updateAlwaysCheckout(Map<String, Object> paramMap);

    /**
     * 통제구역 퇴실 정보 수정(방문객)
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2022. 1. 14.
     */
    boolean updateVisitorCheckout(Map<String, Object> paramMap);

    /**
     * 방문객입실 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2022. 1. 13.
     */
    List<Map<String, Object>> selectVisitorAdmittedList(Map<String, Object> paramMap);

    /**
     * 통제구역 입실 처리(방문객)
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2022. 1. 14.
     */
    boolean insertVisitorAdmittedReg(Map<String, Object> paramMap);

    /**
     * 물품반입 등록
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2022. 1. 17.
     */
    boolean insertItemsImport(Map<String, Object> paramMap);

    /**
     * 물품반출 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2022. 1. 18.
     */
    List<Map<String, Object>> selectItemsExportList(Map<String, Object> paramMap);

    /**
     * 물품반출 처리
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2022. 1. 18.
     */
    boolean updateItemsExport(Map<String, Object> paramMap);

}
