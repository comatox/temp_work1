package com.skshieldus.esecurity.repository.entmanage;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface EnvrEntmngRepository {

    // 출입 차량 제한 관리 > 조회 - selectCarLimitsList
    List<Map<String, Object>> selectCarLimitsList(Map<String, Object> paramMap);

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

    // 방문객 관리(제한관리) > 상세 > 대표관리자초기화 - initRepresentAdmin
    int initRepresentAdmin(Map<String, Object> paramMap);

    // 방문객 관리(제한관리)  > 출입제한 이력 Seq  - offLimitsIoEmpDenySeq
    String offLimitsIoEmpDenySeq(Map<String, Object> paramMap);

    // 방문객 관리(제한관리)  > 출입제한 이력 등록  - offLimitsHistoryInsert
    int offLimitsHistoryInsert(Map<String, Object> paramMap);

    // 방문객 관리(제한관리) > 이전 출입증 여부체크  - passMst2Check
    Map<String, Object> passMst2Check(Map<String, Object> paramMap);

    // 방문객 관리(제한관리) > 출입 외부 제한 이력 Seq  - offLimitsIoPassExprHistSeq : string
    String offLimitsIoPassExprHistSeq(Map<String, Object> paramMap);

    // 방문객 관리(제한관리) > 출입 외부 제한 이력 등록  - offLimitsExprHistoryInsert : insert
    int offLimitsExprHistoryInsert(Map<String, Object> paramMap);

    // 방문객 관리(제한관리) > 날짜변경 - offLimitsDenyDateUpdate
    int offLimitsDenyDateUpdate(Map<String, Object> paramMap);

    // 방문객 관리(제한관리) > 가입승인/전산탈퇴(delYn: N.가입승인, Y.전산탈퇴) - changeUserStatus
    int changeUserStatus(Map<String, Object> paramMap);

    // 방문객 관리(제한관리) > 출입증 Count  - ioInOutCount
    String ioInOutCount(Map<String, Object> paramMap);

    // 방문객 관리(제한관리) > 업체물품 반인신청 List - inIoEmpExistIoinoutChgItemList
    List<Map<String, Object>> inIoEmpExistIoinoutChgItemList(Map<String, Object> paramMap);

    // 방문객 관리(제한관리) > 신청자ID -> 대표관리자ID로 변경  - inIoEmpInfoIoInOutChg
    int inIoEmpInfoIoInOutChg(Map<String, Object> paramMap);

    // 방문객 관리(제한관리) > 회원탈퇴  - holdUserDelete
    int holdUserDelete(Map<String, Object> paramMap);

    // 방문객 관리(제한관리) > 상시출입증 강제만료 > 카드정보 Select  - offLimitsReceiptMngExpireInfoSelect
    Map<String, Object> offLimitsReceiptMngExpireInfoSelect(Map<String, Object> paramMap);

    // 방문객 관리(제한관리) > 상시출입증 강제만료 Update  - offLimitsReceiptMngExpireUpdate
    int offLimitsReceiptMngExpireUpdate(Map<String, Object> paramMap);

    // 방문객 관리(제한관리) > 전산탈퇴 > 정보 Select - ioEmpAdminAuhSelect
    Map<String, Object> ioEmpAdminAuhSelect(Map<String, Object> paramMap);

    // 방문객 관리(제한관리) > 전산탈퇴 > violationCount - ioEmpViolationCount
    int ioEmpViolationCount(Map<String, Object> paramMap);

    // 방문객 관리(제한관리) > 전산탈퇴 > gateInCount - ioEmpGateOutCheck
    int ioEmpGateOutCheck(Map<String, Object> paramMap);

    // 방문객 관리(제한관리) > 전산탈퇴 > ioCount - ioInOutCount
    int ioEmpInOutCount(Map<String, Object> paramMap);

    // 방문객 관리(제한관리) > 전산탈퇴 > constPassCount
    int constPassCount(Map<String, Object> paramMap);

    // 방문객 관리(제한관리) > 임시비밀번호발송 > 사용자정보 Select - ioEmpFindInfo
    Map<String, Object> ioEmpFindInfo(Map<String, Object> paramMap);

    // 방문객 관리(제한관리) > 임시비밀번호발송 > 임시비밀번호 Update  - ioEmpPasswordUpdate
    int ioEmpPasswordUpdate(Map<String, Object> paramMap);

    // 방문객 관리(제한관리) > 출입정지 Count - passReqStopCnt
    int passReqStopCnt(Map<String, Object> paramMap);

    // 방문객 관리(제한관리) > 출입정지 Insert - passReqStop
    int passReqStop(Map<String, Object> paramMap);

    // 방문객 관리(제한관리) > 출입제한이력 해제  Update - offLimitsHistoryUpdate
    int offLimitsHistoryUpdate(Map<String, Object> paramMap);

    // 방문객 관리(제한관리) > 출입제한이력 카드ID - offLimitsGetCardNo
    String offLimitsGetCardNo(Map<String, Object> paramMap);

}
