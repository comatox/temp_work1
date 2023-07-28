package com.skshieldus.esecurity.repository.sysmanage;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface SysmanageEnvrEntmngRepository {

    // 출입 차량 제한 관리 > 조회 - selectCarLimitsList
    List<Map<String, Object>> selectCarLimitsList(Map<String, Object> paramMap);

    Integer selectCarLimitsCount(Map<String, Object> paramMap);

    // 출입 차량 제한 관리 > 엑셀다운 - selectCarLimitsListExcel
    List<Map<String, Object>> selectCarLimitsListExcel(Map<String, Object> paramMap);

    // 출입 차량 제한 관리 > 상세 - selectCarLimitsView
    Map<String, Object> selectCarLimitsView(Map<String, Object> paramMap);

    // 출입 차량 제한 관리 > 입력 - insertCarLimits
    int insertCarLimits(Map<String, Object> paramMap);

    // 출입 차량 제한 관리 > 수정 - updateCarLimits
    int updateCarLimits(Map<String, Object> paramMap);

    // 출입 차량 제한 관리 > 삭제 - deleteCarLimits
    int deleteCarLimits(Map<String, Object> paramMap);

    // 구성원 관리(제한관리) > 조회 - offLimitsEmpCardList
    List<Map<String, Object>> offLimitsEmpCardList(Map<String, Object> paramMap);

    // 구성원 관리(제한관리) > 엑셀다운 - offLimitsEmpCardListExcel
    List<Map<String, Object>> offLimitsEmpCardListExcel(Map<String, Object> paramMap);

    // 구성원 관리(제한관리) > 출입제한 등록 - offLimitsEmpCardDenyInsert
    int offLimitsEmpCardDenyInsert(Map<String, Object> paramMap);

    // 구성원 관리(제한관리) > 출입제한 해제 - offLimitsEmpCardDenyUpdate
    int offLimitsEmpCardDenyUpdate(Map<String, Object> paramMap);
    // 구성원 관리(제한관리) > 출입날짜 변경 - offLimitsEmpCardDenyDateUpdate

    int offLimitsEmpCardDenyDateUpdate(Map<String, Object> paramMap);

    // 구성원 관리(제한관리) > 상세 - offLimitsEmpCardView
    Map<String, Object> offLimitsEmpCardView(Map<String, Object> paramMap);

    // 구성원 관리(제한관리) > 출입제한이력 - offLimitsEmpCardHistory
    List<Map<String, Object>> offLimitsEmpCardHistory(Map<String, Object> paramMap);

    // 방문객 관리(제한관리) > 조회 - offLimitsList
    List<Map<String, Object>> offLimitsList(Map<String, Object> paramMap);

    // 방문객 관리(제한관리) > 조회 건수 - offLimitsCount
    Integer offLimitsCount(Map<String, Object> paramMap);

    // 방문객 관리(제한관리) > 엑셀다운 - offLimitsListExcel
    List<Map<String, Object>> offLimitsListExcel(Map<String, Object> paramMap);

    // 방문객 관리(제한관리) > 상세 - offLimitsView
    Map<String, Object> offLimitsView(Map<String, Object> paramMap);

    // 방문객 관리(제한관리) > 출입제한이력 - offLimitsHistoryList
    List<Map<String, Object>> offLimitsHistoryList(Map<String, Object> paramMap);

    // 방문객 관리(제한관리) > 보안위규이력 - offLimitSecViolationHist
    List<Map<String, Object>> offLimitSecViolationHist(Map<String, Object> paramMap);

    int insertOffLimitsHistory(Map<String, Object> paramMap);

    int updateOffLimitsHistory(Map<String, Object> paramMap);

    Map<String, Object> selectPassCheck(Map<String, Object> paramMap);

    int insertOffLimitsExprHistory(Map<String, Object> paramMap);

}
