package com.skshieldus.esecurity.repository.entmanage;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface BuildingRegRepository {

    // 사원 카드번호 조회
    String selectEmpCardNo(String empId);

    // 사원 카드번호 조회
    String selectCardNo(String empId);

    // 이천 출입구역 건물 목록 조회 (일반구역)
    List<Map<String, Object>> selectCardKeyBldgList(Map<String, Object> paramMap);

    // 이천 출입구역 건물 목록 조회 - 층 단위 (일반구역)
    List<Map<String, Object>> selectCardKeyFloorList(Map<String, Object> paramMap);

    // 청주 출입구역 건물 목록 조회 (일반구역)
    List<Map<String, Object>> selectCardKeyCjBldgList(Map<String, Object> paramMap);

    // 이천 출입구역 목록 조회 (통제구역)
    List<Map<String, Object>> selectCardKeyGateSpeZone1List(Map<String, Object> paramMap);

    // 청주 출입구역 목록 조회 (통제구역)
    List<Map<String, Object>> selectCardKeyCjGateSpeZone1List(Map<String, Object> paramMap);

    // 이천 출입구역 목록 조회 (제한구역)
    List<Map<String, Object>> selectCardKeyGateSpeZone2List(Map<String, Object> paramMap);

    // 청주 출입구역 목록 조회 (제한구역)
    List<Map<String, Object>> selectCardKeyCjGateSpeZone2List(Map<String, Object> paramMap);

    // 이천 출입구역 허가부서 결재자 목록 조회
    List<Map<String, Object>> selectBuildPermitLine(Map<String, Object> paramMap);

    // 청주 출입구역 허가부서 결재자 목록 조회
    List<Map<String, Object>> selectCjBuildPermitLine(Map<String, Object> paramMap);

    // 건물출입 등록 (Master)
    int insertEmpcardBldgMaster(Map<String, Object> paramMap);

    // 건물출입 등록 (Detail)
    int insertEmpcardBldgDetail(Map<String, Object> paramMap);

    // 건물출입 결재문서ID 업데이트
    int updateEmpcardBldgMasterDocId(Map<String, Object> paramMap);

    // 건물출입 즐겨찾기 삭제
    int deleteEmpcardMyGate(Map<String, Object> paramMap);

    // 건물출입 즐겨찾기 등록
    int insertEmpcardMyGate(Map<String, Object> paramMap);

    // 건물출입 신청 현황 목록 조회
    List<Map<String, Object>> selectEmpCardBuildRegList(Map<String, Object> paramMap);

    // 건물출입 신청 상세정보 조회
    Map<String, Object> selectBldgEmpApplInfo(Map<String, Object> paramMap);

    // 건물출입 신규 출입 신청 목록 조회
    List<Map<String, Object>> selectNewEmpCardBuildingList(Map<String, Object> paramMap);

    // 출입증 카드ID 조회 (협력업체)
    String selectPassIdcardId(Map<String, Object> paramMap);

    // 건물출입 일괄등록 (Master)
    int insertEmpcardallM(Map<String, Object> paramMap);

    // 건물출입 일괄등록 (Detail) 건물정보 등록
    int insertEmpcardallBldgD(Map<String, Object> paramMap);

    // 건물출입 일괄등록 (Detail) 사원정보 등록
    int insertEmpcardallEmpD(Map<String, Object> paramMap);

    // 건물출입 일괄신청 목록 조회
    List<Map<String, Object>> selectEmpCardBuildRegAllList(Map<String, Object> paramMap);

    // 건물출입 일괄신청 상세조회 기본정보 조회
    Map<String, Object> selectEmpCardBuildRegAll(Map<String, Object> paramMap);

    // 건물출입 일괄신청 상세조회 출입건물 목록(이천) 조회
    List<Map<String, Object>> selectEmpCardBuildRegAllBuildIcList(Map<String, Object> paramMap);

    // 건물출입 일괄신청 상세조회 출입건물 목록(청주) 조회
    List<Map<String, Object>> selectEmpCardBuildRegAllBuildCjList(Map<String, Object> paramMap);

    // 건물출입 일괄신청 상세조회 출입대상자 목록 조회
    List<Map<String, Object>> selectEmpCardBuildRegAllEmpList(Map<String, Object> paramMap);

    // 건물출입 신청현황(상시출입증) 목록 조회
    List<Map<String, Object>> selectPassBuildRegList(Map<String, Object> paramMap);

    // 건물출입 신청현황(상시출입증) 상세 MIG_IDCARD_EMP 조회
    String selectPassApplNoByBldgNO(Map<String, Object> paramMap);

    // 건물출입 신청현황(상시출입증) 출입증 정보 조회
    Map<String, Object> selectPassReceiptViewByPassBldgApplNo(Map<String, Object> paramMap);

    // 건물출입 신청현황(상시출입증) 출입증 정보 조회
    Map<String, Object> selectPassReceiptViewByPassApplNo(Map<String, Object> paramMap);

    // 건물출입 신청현황(상시출입증) 출입건물 신청 정보 조회
    Map<String, Object> selectPassBuildingView(Map<String, Object> paramMap);

    // 건물출입 신규 출입(상시출입증) 신청 출입건물 목록 조회
    List<Map<String, Object>> selectNewPassBuildingList(Map<String, Object> paramMap);

    // 건물출입 신규 출입(상시출입증) 신청 상시출입증 사용자 건수 조회
    Integer selectIoPassCountByEmpName(Map<String, Object> paramMap);

    // 건물출입 신규 출입(상시출입증) 신청ID 정보 조회
    Map<String, Object> selectIoPassApplNoByEmpName(Map<String, Object> paramMap);

    // 상시출입증 사용자 정보 조회
    Map<String, Object> selectPassIoEmpView(Map<String, Object> paramMap);

    // 건물출입 신규 출입(상시출입증) 신청 상시출입증 사용자 목록 조회
    List<Map<String, Object>> selectIoPassApplNoList(Map<String, Object> paramMap);

    // 상시출입증 건물출입 등록 (Master)
    int insertPassBldgMaster(Map<String, Object> paramMap);

    // 상시출입증 건물출입 등록 (Detail)
    int insertPassBldgDetail(Map<String, Object> paramMap);

    // 상시출입증 건물출입 결재문서ID 업데이트
    int updatePassBldgMasterDocId(Map<String, Object> paramMap);

    // 통제구역 출입현황 조회조건 사업장 목록 조회
    List<Map<String, Object>> selectSpecialPassCompCodeList(Map<String, Object> paramMap);

    // 통제구역 출입현황 조회조건 건물 목록 조회
    List<Map<String, Object>> selectSpecialPassBldgCodeList(Map<String, Object> paramMap);

    // 통제구역 출입현황 조회조건 게이트 목록 조회
    List<Map<String, Object>> selectSpecialPassGateCodeList(Map<String, Object> paramMap);

    // 통제구역 출입현황 대상자정보 목록 조회
    List<Map<String, String>> selectSpecialPassIoPassList(Map<String, Object> paramMap);

    // 통제구역 출입현황 제외대상자ID 목록 조회
    List<String> selectSpecialPassExceptList(Map<String, Object> paramMap);

    // 통제구역 출입현황 권한삭제 정보 등록
    int insertSpecialPassIoEmpBldgCanc(Map<String, Object> paramMap);

    // 통제구역 출입현황 상세 변경이력 목록 조회
    List<Map<String, Object>> selectPassReceiptMngChgHistList(Map<String, Object> paramMap);

    // 건물출입 신청(사원증) 후처리 => 신청정보 상세 조회
    Map<String, Object> selectEmpCardBldgRequestInfo(Integer lid);

    // 건물출입 신청(사원증) 후처리 => 신청정보 목록 조회
    List<Map<String, Object>> selectEmpCardBldgRequestList(Map<String, Object> paramMap);

    // 건물출입 신청(사원증) 후처리 => CO_EMP_BLDG 건수 조회
    Integer selectCoEmpBldgCount(Map<String, Object> paramMap);

    // 건물출입 신청(사원증) 후처리 => CO_EMP_BLDG 등록
    int insertCoEmpBldg(Map<String, Object> paramMap);

    // 건물출입 신청(사원증) 후처리 => 신청건물 목록 조회
    List<Map<String, String>> selectEmpCardBuildingRequestList(Map<String, Object> paramMap);

    // 건물출입 신청(사원증) 후처리 => 신청자 정보 조회
    Map<String, String> selectEmpInfoBuildingRequest(Map<String, Object> paramMap);

    // 건물출입 신청(상시출입증) 후처리 => 신청정보 상세 조회
    Map<String, Object> selectPassBldgRequestInfo(Integer lid);

    // 건물출입 신청(상시출입증) 후처리 => 신청정보 목록 조회
    List<Map<String, Object>> selectPassBldgRequestList(Map<String, Object> paramMap);

    // 건물출입 신청(상시출입증) 후처리 => IO_EMP_BLDG 건수 조회
    Integer selectIoEmpBldgCount(Map<String, Object> paramMap);

    // 건물출입 신청(상시출입증) 후처리 => IO_EMP_BLDG 등록
    int insertIoEmpBldg(Map<String, Object> paramMap);

    // 건물출입 신청(상시출입증) 후처리 => 신청건물 목록 조회
    List<Map<String, String>> selectPassBuildingRequestList(Map<String, Object> paramMap);

    // 건물출입 신청(상시출입증) 후처리 => 신청건물 목록 조회(이천)
    List<Map<String, String>> selectPassBuildingRequestIcList(Map<String, Object> paramMap);

    // 건물출입 신청(상시출입증) 후처리 => 신청자 정보 조회
    Map<String, String> selectPassInfoBuildingRequest(Map<String, Object> paramMap);

    // 건물출입 일괄신청(사원증) 후처리 => 신청정보 사용자 목록 조회
    List<Map<String, String>> selectEmpCardEmpAllRequestInfo(Map<String, Object> paramMap);

    // 건물출입 일괄신청(사원증) 후처리 => 신청정보 건물 목록 조회
    List<Map<String, String>> selectEmpCardBldgAllRequestList(Map<String, Object> paramMap);

    // 건물출입 일괄신청(사원증) 후처리 => 신청건물 목록 조회
    List<Map<String, String>> selectEmpCardBuildingAllRequestList(Map<String, Object> paramMap);

    // 건물출입 일괄신청(사원증) 후처리 => 신청자 정보 목록 조회
    List<Map<String, String>> selectEmpInfoBuildingAllRequest(Map<String, Object> paramMap);

    // 사원증 건물등록현황(관리자) 목록 조회
    List<Map<String, Object>> selectEmpCardBuildRegAdmList(Map<String, Object> paramMap);

    // 사원증 건물등록현황(관리자) 목록 건수 조회
    Integer selectEmpCardBuildRegAdmCount(Map<String, Object> paramMap);

    // 출입증 건물등록현황(관리자) 목록 조회
    List<Map<String, Object>> selectPassBuildRegAdmList(Map<String, Object> paramMap);

    // 출입증 건물등록현황(관리자) 목록 건수 조회
    Integer selectPassBuildRegAdmCount(Map<String, Object> paramMap);

}
