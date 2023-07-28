package com.skshieldus.esecurity.repository.entmanage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmpCardRepository {

    // 사원증 (재)발급 등록
    int insertEmpCard(Map<String, Object> paramMap);

    // 사원증 (재)발급 수정
    int updateEmpCard(Map<String, Object> paramMap);

    // 사원증 발급 현황 목록 조회
    List<Map<String, Object>> selectEmpCardList(Map<String, Object> paramMap);

    // 사원증 발급 현황 건수 조회
    Integer selectEmpCardListCnt(Map<String, Object> paramMap);

    // 사원증 발급 상세 조회
    Map<String, Object> selectEmpCard(Integer empcardApplNo);

    // 사원증 신규발급 목록 조회
    List<Map<String, Object>> selectNewEmpCardList(Map<String, Object> paramMap);

    // 사원증 신규발급 현황 건수 조회
    Integer selectNewEmpCardListCnt(Map<String, Object> paramMap);

    // 사원정보&사원증 발급 상세 조회
    Map<String, Object> selectEmpInfoViewForIDCardIF(Integer empcardApplNo);

    // 사원정보&사원증 발급 상세 조회(New)
    Map<String, Object> selectEmpInfoViewForIDCardIFNew(Integer empcardApplNo);

    // 악세서리 신청
    int insertAccessory(Map<String, Object> paramMap);

    // 악세서리 발급 상세 조회
    Map<String, Object> selectAccessoryViewForIDCardIF(Map<String, Object> paramMap);

    // 악세서리 신청 현황 목록 조회
    List<Map<String, Object>> selectAccessoryList(Map<String, Object> paramMap);

    // 악세서리 신청 현황 건수 조회
    Integer selectAccessoryListCnt(Map<String, Object> paramMap);

    // (관리자) 액세서리 발급현황 목록 조회
    List<Map<String, Object>> selectAdmAccessoryList(Map<String, Object> paramMap);

    // (관리자) 액세서리 발급현황 목록 건수 조회
    Integer selectAdmAccessoryListCnt(Map<String, Object> paramMap);

    // (관리자) 액세서리 수령확인/취소 업데이트
    Integer updateAdmAccessoryStatus(Map<String, Object> paramMap);

    // (관리자) 일일 사원증 발급 및 현황 목록
    List<Map<String, Object>> selectAdmOnedayCardList(Map<String, Object> paramMap);

    // (관리자) 일일 사원증 발급 및 현황 목록 건수
    Integer selectAdmOnedayCardListCnt(Map<String, Object> paramMap);

    // (관리자) 일일 사원증 반납 처리
    Integer updateOnedayEmpCardReturn(Map<String, Object> paramMap);

    // (관리자) 일일 사원증 발급 및 현황 상세 조회
    Map<String, Object> selectAdmOnedayCard(Integer empcardApplNo);

    // (관리자) 일일 사원증 발급 및 현황 등록 > 중복체크
    String selectOnedayEmpCardDupCheck(String cardNo);

    // (관리자) 일일 사원증 발급 및 현황 등록 > 카드체크
    String selectOnedayEmpCardIdCardCheck(Map<String, Object> paramMap);

    // (관리자) 일일 사원증 발급 및 현황 등록 > 구성원 위규 건수 조회
    Map<String, Object> selectSecCoEmpViolationExcptCnt(String ofendEmpId);

    // (관리자) 일일 사원증 발급 및 현황 등록 > 구성원 위규 등록
    int insertOnedayEmpCardScOfend(Map<String, Object> paramMap);

    // (관리자) 일일 사원증 발급 및 현황 등록 > 구성원 일일사원증 등록
    int insertOnedayEmpCardCoEmp(Map<String, Object> paramMap);

    // (관리자) 일일 사원증 발급 및 현황 등록 > 외부인(도급사) 위규 건수 조회
    Map<String, Object> selectSecIoEmpViolationExcptCnt(String ofendEmpId);

    // (관리자) 일일 사원증 발급 및 현황 등록 > 외부인(도급사) 위규 등록
    int insertOnedayEmpCardScIoOfend(Map<String, Object> paramMap);

    // (관리자) 일일 사원증 발급 및 현황 등록 > 외부인(도급사) 일일사원증 등록
    int insertOnedayEmpCardIoEmp(Map<String, Object> paramMap);

    // 일일 사원증 발급 엑셀 다운로드
    List<HashMap<String, Object>> selectAdmOnedayCardListExcel(HashMap<String, Object> paramMap);

}
