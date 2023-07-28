package com.skshieldus.esecurity.repository.entmanage.idcardvisit;

import com.skshieldus.esecurity.config.datasource.annotation.IdcardVisitMapper;
import java.util.List;
import java.util.Map;

@IdcardVisitMapper
public interface IdcardVisitRepository {

    // 기 출입구역 목록 조회
    List<Map<String, Object>> selectPassBuildingGateOldList(String idcardId);

    // 통제구역 출입현황 목록 조회
    List<Map<String, Object>> selectSpecialPassList(Map<String, Object> paramMap);

    // 통제구역 출입현황 목록 건수 조회
    Map<String, Object> selectSpecialPassCount(Map<String, Object> paramMap);

    // 통제구역 출입현황 권한 삭제
    int procedureSpecialPassIoEmpBldgCancIf(Map<String, Object> paramMap);

    // 건물 출입 - 입문 처리
    void procedureBuildingPassInIf(Map<String, Object> paramMap);

    // 건물 출입 - 출문 처리
    void procedureBuildingPassOutIf(Map<String, Object> paramMap);

    // 납품 출입 - 입문 처리
    void procedureDeliveryPassInIf(Map<String, Object> paramMap);

    // 납품 출입 - 출문 처리
    void procedureDeliveryPassOutIf(Map<String, Object> paramMap);

    // 상시출입증 장기 예외신청 삭제처리(관리자 > 장기예외신청현황(산업보안))
    void procedurePassExcptDeleteIf(Integer excptApplNo);

    // 상시출입증 장기 예외신청 처리(관리자 > 장기예외신청현황(산업보안))
    void procedurePassExcptIoPassExcptIf(Map<String, Object> paramMap);

    // 카드번호를 이용한 통합사번 조회 I/F
    Map<String, Object> selectOnedayIdCardIf(String cardNo);

    // 일일사원증 처리 I/F
    void procedureOnedayEmpCardInfoIf(Map<String, Object> paramMap);

    // 상시출입증 사전 정지예외 신청 I/F
    void procedureIoPassExcptIf(Map<String, Object> paramMap);

    // 상시출입증 사후 정지예외 신청 I/F
    void procedurePassExcptIFIoPassExprHist(Map<String, Object> paramMap);

    List<Map<String, String>> selectPassReceiptMngListGetLastCnt(String cardNoStr);

    List<Map<String, Object>> selectHazardousChemicalsVisitList(Map<String, Object> paramMap);

    void procedureFrontDoorOutProcIF(Map<String, Object> paramMap);

    void procedureFrontDoorInProcIF(Map<String, Object> paramMap);

    // 환경설정 > 구성원 관리(제한관리) > 출입제한  - procedureOffLimitsEmpCardExprHist
    void procedureOffLimitsEmpCardExprHist(Map<String, Object> paramMap);

    // 환경설정 > 방문객 관리(제한관리) > 출입제한  - procedureOffLimitsIoPassExprHist
    void procedureOffLimitsIoPassExprHist(Map<String, Object> paramMap);

}
