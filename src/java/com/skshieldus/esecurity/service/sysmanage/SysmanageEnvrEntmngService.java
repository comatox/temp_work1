package com.skshieldus.esecurity.service.sysmanage;

import com.skshieldus.esecurity.common.model.ListDTO;
import com.skshieldus.esecurity.model.common.CommonXlsViewDTO;
import java.util.List;
import java.util.Map;

public interface SysmanageEnvrEntmngService {

    // 출입 차량 제한 관리 > 조회 - selectCarLimitsList
    ListDTO<Map<String, Object>> selectCarLimitsList(Map<String, Object> paramMap);

    // 출입 차량 제한 관리 > 엑셀다운 - selectCarLimitsListExcel
    CommonXlsViewDTO selectCarLimitsListExcel(Map<String, Object> paramMap);

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
    CommonXlsViewDTO offLimitsEmpCardListExcel(Map<String, Object> paramMap);

    // 구성원 관리(제한관리) > 출입제한 등록(다건)- offLimitsEmpCardDenyMultipleInsert
    int offLimitsEmpCardDenyMultipleInsert(Map<String, Object> paramMap);

    // 구성원 관리(제한관리) > 출입제한 해제(다건)- offLimitsEmpCardDenyMultipleUpdate
    int offLimitsEmpCardDenyMultipleUpdate(Map<String, Object> paramMap);

    // 방문객 관리(제한관리) > 출입제한 등록
    int offLimitsEmpCardDenyDateMultipleUpdate(Map<String, Object> paramMap);

    // 구성원 관리(제한관리) > 상세 - offLimitsEmpCardView
    Map<String, Object> offLimitsEmpCardView(Map<String, Object> paramMap);

    // 구성원 관리(제한관리) > 출입제한이력 - offLimitsEmpCardHistory
    List<Map<String, Object>> offLimitsEmpCardHistory(Map<String, Object> paramMap);

    // 방문객 관리(제한관리) > 조회 - offLimitsList
    ListDTO<Map<String, Object>> offLimitsList(Map<String, Object> paramMap);

    // 방문객 관리(제한관리) > 엑셀다운 - offLimitsListExcel
    CommonXlsViewDTO offLimitsListExcel(Map<String, Object> paramMap);

    // 방문객 관리(제한관리) > 상세 - offLimitsView
    Map<String, Object> offLimitsView(Map<String, Object> paramMap);

    // 방문객 관리(제한관리) > 출입제한이력 - offLimitsHistoryList
    List<Map<String, Object>> offLimitsHistoryList(Map<String, Object> paramMap);

    // 방문객 관리(제한관리) > 보안위규이력 - offLimitSecViolationHist
    List<Map<String, Object>> offLimitSecViolationHist(Map<String, Object> paramMap);

    // 방문객 관리(제한관리) > 출입제한 등록
    void insertOffLimitsDeny(Map<String, Object> paramMap);

    // 방문객 관리(제한관리) > 출입제한 해제
    void deleteOffLimitsDeny(Map<String, Object> paramMap);

}
