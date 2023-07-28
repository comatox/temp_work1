package com.skshieldus.esecurity.service.entmanage;

import com.skshieldus.esecurity.model.common.CommonXlsViewDTO;
import java.util.List;
import java.util.Map;

public interface CurrentLineStatusService {

    /**
     * 통제구역관리 장소 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 18.
     */
    List<Map<String, Object>> selectBuildingControlList(Map<String, Object> paramMap);

    /**
     * Bay별출입인원(Line현황) 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 18.
     */
    List<Map<String, Object>> selectAccessPersonsList(Map<String, Object> paramMap);

    /**
     * 내부인명단(Line현황) 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 22.
     */
    List<Map<String, Object>> selectStaffCurrentList(Map<String, Object> paramMap);

    /**
     * 내부인명단(Line현황) 목록 건수 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 22.
     */
    Integer selectStaffCurrentListCnt(Map<String, Object> paramMap);

    /**
     * 상시출입객명단(Line현황) 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 22.
     */
    List<Map<String, Object>> selectAlwaysCurrentList(Map<String, Object> paramMap);

    /**
     * 상시출입객명단(Line현황) 목록 건수 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 22.
     */
    Integer selectAlwaysCurrentListCnt(Map<String, Object> paramMap);

    /**
     * 방문객명단(Line현황) 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 22.
     */
    List<Map<String, Object>> selectVisitorCurrentList(Map<String, Object> paramMap);

    /**
     * 방문객명단(Line현황) 목록 건수 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 22.
     */
    Integer selectVisitorCurrentListCnt(Map<String, Object> paramMap);

    /**
     * 상시출입객관리(Line현황) 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 22.
     */
    List<Map<String, Object>> selectAlwaysManageList(Map<String, Object> paramMap);

    /**
     * 상시출입객관리(Line현황) 목록 건수 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 22.
     */
    Integer selectAlwaysManageListCnt(Map<String, Object> paramMap);

    /**
     * 상시출입객관리(Line현황) 상세 조회
     *
     * @param ioRegIoNo
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 22.
     */
    Map<String, Object> selectAlwaysManage(Integer ioRegIoNo);

    /**
     * 상시출입객관리(Line현황) 등록
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 22.
     */
    Boolean insertAlwaysManage(Map<String, Object> paramMap);

    /**
     * 상시출입객관리(Line현황) 수정
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 22.
     */
    Boolean updateAlwaysManage(Map<String, Object> paramMap);

    /**
     * 상시출입객관리(Line현황) 삭제
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 22.
     */
    Boolean deleteAlwaysManages(Map<String, Object> paramMap);

    /**
     * 상시출입객관리(Line현황) 카드번호 조회
     *
     * @param cardNo
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 23.
     */
    String selectAlwaysManageCardNo(String cardNo);

    /**
     * 반입물품현황 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 23.
     */
    List<Map<String, Object>> selectItemsStatusList(Map<String, Object> paramMap);

    /**
     * 반입물품현황 목록 건수 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 23.
     */
    Integer selectItemsStatusListCnt(Map<String, Object> paramMap);

    /**
     * 임직원 명단(기간별현황) 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 23.
     */
    List<Map<String, Object>> selectStaffStatusList(Map<String, Object> paramMap);

    /**
     * 임직원 명단(기간별현황) 목록 건수 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 23.
     */
    Integer selectStaffStatusListCnt(Map<String, Object> paramMap);

    /**
     * 임직원 명단(기간별현황) 엑셀 다운로드
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 23.
     */
    CommonXlsViewDTO selectStaffStatusListExcel(Map<String, Object> paramMap);

    /**
     * 상시출입객 명단(기간별현황) 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 23.
     */
    List<Map<String, Object>> selectAlwaysStatusList(Map<String, Object> paramMap);

    /**
     * 상시출입객 명단(기간별현황) 목록 건수 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 23.
     */
    Integer selectAlwaysStatusListCnt(Map<String, Object> paramMap);

    /**
     * 상시출입객 명단(기간별현황) 엑셀 다운로드
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 23.
     */
    CommonXlsViewDTO selectAlwaysStatusListExcel(Map<String, Object> paramMap);

    /**
     * 방문객명단(기간별현황) 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 23.
     */
    List<Map<String, Object>> selectVisitorStatusList(Map<String, Object> paramMap);

    /**
     * 방문객명단(기간별현황) 목록 건수 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 23.
     */
    Integer selectVisitorStatusListCnt(Map<String, Object> paramMap);

    /**
     * 방문객명단(기간별현황) 엑셀 다운로드
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 23.
     */
    CommonXlsViewDTO selectVisitorStatusListExcel(Map<String, Object> paramMap);

    /**
     * 유해화학물질 취급 차량현황 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 23.
     */
    List<Map<String, Object>> selectHazardousChemicalsVisitList(Map<String, Object> paramMap);

    /**
     * 유해화학물질 취급 차량현황 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 23.
     */
    List<Map<String, Object>> selectHazardousChemicalsTmpcarList(Map<String, Object> paramMap);

    /**
     * 유해화학물질 취급 차량현황 목록 건수 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 23.
     */
    Integer selectHazardousChemicalsTmpcarListCnt(Map<String, Object> paramMap);

}
