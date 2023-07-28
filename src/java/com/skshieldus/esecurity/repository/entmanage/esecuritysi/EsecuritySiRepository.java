package com.skshieldus.esecurity.repository.entmanage.esecuritysi;

import com.skshieldus.esecurity.config.datasource.annotation.EsecuritySiMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EsecuritySiMapper
public interface EsecuritySiRepository {

    // 외부인 체크 - 시정공문/시정계획서 승인 여부
    Map<String, String> selectReserveVisitCorrPlan(String ioEmpId);

    // 상시출입증 정지및 제한 여부 조회
    Map<String, Object> selectPassInsStopDenyInfo(Map<String, Object> paramMap);

    int coorpVendroCompUpdate(Map<String, Object> dataMap);

    int dmIoEmpPassportNoUpdate(HashMap<String, Object> paramMap);

    Map<String, Object> selectHazardousChemicalsVisitCo(Map<String, Object> paramMap);

    Map<String, Object> selectHazardousChemicalsVisitPass(Map<String, Object> paramMap);

    Map<String, Object> selectHazardousChemicalsVisitVst(Map<String, Object> paramMap);

    int updateIoEmpCompId(Map<String, Object> paramMap);

    int updateIoInoutCompId(Map<String, Object> paramMap);

    String selectGetPassYn(String ioEmpIdMan);

    Map<String, Object> selectSubcontMovePassApplNo(String ioEmpIdMan);

    Map<String, Object> selectIoPassInfo(Map<String, Object> paramMap);

    int insertSubcontMoveIoPass(Map<String, Object> paramMap);

    int insertSubcontMoveIfIdcard(Map<String, Object> paramMap);

    Map<String, Object> selectSubcontMoveIfEntrance(Map<String, Object> paramMap);

    int insertSubcontMoveIfEntranceSi(Map<String, Object> paramMap);

    int updateIoComp(Map<String, Object> paramMap);

    int updateCoorpVendorAdminApprRecevieReject(Map<String, Object> paramMap);

    int updateCoorpVendorAdminApprRecevieReject2(Map<String, Object> paramMap);

    int updateCoorpVendorInfoSubcontYn(Map<String, Object> paramMap);

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

    // 방문객 관리(제한관리) > 업체물품 반입신청 List - inIoEmpExistIoinoutChgItemList
    List<Map<String, Object>> inIoEmpExistIoinoutChgItemList(Map<String, Object> paramMap);

    // 방문객 관리(제한관리) > 신청자ID -> 대표관리자ID로 변경  - inIoEmpInfoIoInOutChg
    int inIoEmpInfoIoInOutChg(Map<String, Object> paramMap);

    // 방문객 관리(제한관리) > 전산탈퇴 > violationCount - ioEmpViolationCount
    int ioEmpViolationCount(Map<String, Object> paramMap);

    // 방문객 관리(제한관리) > 전산탈퇴 > gateInCount - ioEmpGateOutCheck
    int ioEmpGateOutCheck(Map<String, Object> paramMap);

    // 방문객 관리(제한관리) > 전산탈퇴 > ioCount - ioInOutCount
    int ioEmpInOutCount(Map<String, Object> paramMap);

    // 방문객 관리(제한관리) > 임시비밀번호발송 > 임시비밀번호 Update  - ioEmpPasswordUpdate
    int ioEmpPasswordUpdate(Map<String, Object> paramMap);

    // 방문객 관리(제한관리) > 출입제한이력 해제  Update - offLimitsHistoryUpdate
    int offLimitsHistoryUpdate(Map<String, Object> paramMap);

    // 방문객 관리(제한관리) > 출입제한이력 카드ID - offLimitsGetCardNo
    String offLimitsGetCardNo(Map<String, Object> paramMap);

    // 보안 위규 건수 조회
    int selectIoEmpVioCnt(String ioEmpId);

    int selectPassMst2(Map<String, Object> paramMap);

    int updateVisitIoEmpCompId(Map<String, Object> paramMap);

    int updateVisitIoInoutCompId(Map<String, Object> paramMap);

    int insertOffLimitsCompChgExprHist(Map<String, Object> paramMap);

    Map<String, Object> selectOffLimitsExprHistSeq(Map<String, Object> paramMap);

}
