package com.skshieldus.esecurity.repository.entmanage;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface CurrentLineStatusRepository {

    // 통제구역관리 장소 목록 조회
    List<Map<String, Object>> selectBuildingControlList(Map<String, Object> paramMap);

    // Bay별출입인원(Line현황) 목록 조회
    List<Map<String, Object>> selectAccessPersonsList(Map<String, Object> paramMap);

    // 내부인명단(Line현황) 목록 조회
    List<Map<String, Object>> selectStaffCurrentList(Map<String, Object> paramMap);

    // 내부인명단(Line현황) 목록 건수 조회
    Integer selectStaffCurrentListCnt(Map<String, Object> paramMap);

    // 상시출입객명단(Line현황) 목록 조회
    List<Map<String, Object>> selectAlwaysCurrentList(Map<String, Object> paramMap);

    // 상시출입객명단(Line현황) 목록 건수 조회
    Integer selectAlwaysCurrentListCnt(Map<String, Object> paramMap);

    // 방문객명단(Line현황) 목록 조회
    List<Map<String, Object>> selectVisitorCurrentList(Map<String, Object> paramMap);

    // 방문객명단(Line현황) 목록 건수 조회
    Integer selectVisitorCurrentListCnt(Map<String, Object> paramMap);

    // 상시출입객관리(Line현황) 목록 조회
    List<Map<String, Object>> selectAlwaysManageList(Map<String, Object> paramMap);

    // 상시출입객관리(Line현황) 목록 건수 조회
    Integer selectAlwaysManageListCnt(Map<String, Object> paramMap);

    // 상시출입객관리(Line현황) 상세 조회
    Map<String, Object> selectAlwaysManage(Integer ioRegIoNo);

    // 상시출입객관리(Line현황) 등록
    Integer insertAlwaysManage(Map<String, Object> paramMap);

    // 상시출입객관리(Line현황) 수정
    Integer updateAlwaysManage(Map<String, Object> paramMap);

    // 상시출입객관리(Line현황) 삭제
    Integer deleteAlwaysManage(Map<String, Object> paramMap);

    // 상시출입객관리(Line현황) 카드번호 조회
    String selectAlwaysManageCardNo(String cardNo);

    // 반입물품현황 목록 조회
    List<Map<String, Object>> selectItemsStatusList(Map<String, Object> paramMap);

    // 반입물품현황 목록 건수 조회
    Integer selectItemsStatusListCnt(Map<String, Object> paramMap);

    // 임직원 명단(기간별현황) 목록 조회
    List<Map<String, Object>> selectStaffStatusList(Map<String, Object> paramMap);

    // 임직원 명단(기간별현황) 목록 건수 조회
    Integer selectStaffStatusListCnt(Map<String, Object> paramMap);

    // 상시출입객 명단(기간별현황) 목록 조회
    List<Map<String, Object>> selectAlwaysStatusList(Map<String, Object> paramMap);

    // 상시출입객 명단(기간별현황) 목록 건수 조회
    Integer selectAlwaysStatusListCnt(Map<String, Object> paramMap);

    // 방문객명단(기간별현황) 목록 조회
    List<Map<String, Object>> selectVisitorStatusList(Map<String, Object> paramMap);

    // 방문객명단(기간별현황) 목록 건수 조회
    Integer selectVisitorStatusListCnt(Map<String, Object> paramMap);

    // 유해화학물질 취급구역 출입현황 - Co
    Map<String, Object> selectHazardousChemicalsVisitCo(Map<String, Object> paramMap);

    // 유해화학물질 취급구역 출입현황 - Pass
    Map<String, Object> selectHazardousChemicalsVisitPass(Map<String, Object> paramMap);

    // 유해화학물질 취급구역 출입현황 - Vst
    Map<String, Object> selectHazardousChemicalsVisitVst(Map<String, Object> paramMap);

    // 유해화학물질 취급 차량현황 목록 조회
    List<Map<String, Object>> selectHazardousChemicalsTmpcarList(Map<String, Object> paramMap);

    // 유해화학물질 취급 차량현황 목록 건수 조회
    Integer selectHazardousChemicalsTmpcarListCnt(Map<String, Object> paramMap);

}
