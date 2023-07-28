package com.skshieldus.esecurity.service.entmanage;

import com.skshieldus.esecurity.common.model.ListDTO;
import com.skshieldus.esecurity.model.common.CommonXlsViewDTO;
import java.util.List;
import java.util.Map;

public interface EnvrEntmngService {

    // 출입 차량 제한 관리 > 조회 - selectCarLimitsList
    List<Map<String, Object>> selectCarLimitsList(Map<String, Object> paramMap);

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

    // 구성원 관리(제한관리) > 출입날짜 변경(다건) - offLimitsEmpCardDenyDateMultipleUpdate
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

    // 방문객 관리(제한관리) > 상세 > 대표관리자초기화 - initRepresentAdmin
    int initRepresentAdmin(Map<String, Object> paramMap);

    // 방문객 관리(제한관리) > 출입제한 등록(다건) - offLimitsDenyMultipleInsert
    int offLimitsDenyMultiInsert(Map<String, Object> paramMap);

    // 방문객 관리(제한관리) > 날짜변경(다건) - offLimitsDenyDateMultiUpdate
    int offLimitsDenyDateMultiUpdate(Map<String, Object> paramMap);

    // 방문객 관리(제한관리) > 가입승인/전산탈퇴(delYn: N.가입승인, Y.전산탈퇴) - changeUserStatus
    int changeUserStatus(Map<String, Object> paramMap);

    // 방문객 관리(제한관리) > 회원탈퇴  - holdUserDelete
    int holdUserDelete(Map<String, Object> paramMap);

    // 방문객 관리(제한관리) >  상시출입증 강제만료 -  offLimitsReceiptMngExpire
    int offLimitsReceiptMngExpire(Map<String, Object> paramMap);

    // 방문객 관리(제한관리) > 전산탈퇴 > 정보 Select - ioEmpAdminAuhSelect
    Map<String, Object> ioEmpAdminAuhSelect(Map<String, Object> paramMap);

    // 방문객 관리(제한관리) > 임시비밀번호발송 - tempPasswordSend
    int tempPasswordSend(Map<String, Object> paramMap);

    // 방문객 관리(제한관리) > 출입정지 - passReqStop
    Map<String, Object> passReqStop(Map<String, Object> paramMap);

    // 방문객 관리(제한관리) > 출입제한이력 해제   - offLimitsDelete
    int offLimitsDelete(Map<String, Object> paramMap);

    // 방문객 관리(제한관리) > 출입제한 해제(다건)   - offLimitsDenyMultiDelete
    int offLimitsDenyMultiDelete(Map<String, Object> paramMap);

}
