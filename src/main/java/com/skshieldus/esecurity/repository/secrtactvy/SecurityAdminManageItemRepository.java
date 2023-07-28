package com.skshieldus.esecurity.repository.secrtactvy;

import org.apache.ibatis.annotations.Mapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface SecurityAdminManageItemRepository {

    // 구성원 보안 위규자 조회 - coEmpViolationList
    List<Map<String, Object>> coEmpViolationList(Map<String, Object> paramMap);

    // 구성원 보안 위규자 조회 엑셀다운 - coEmpViolationListExcel
    List<Map<String, Object>> coEmpViolationListExcel(Map<String, Object> paramMap);

    // 담당자 List - scDetlEmpList
    List<Map<String, Object>> scDetlEmpList(Map<String, Object> paramMap);

    // 구성원 보안 위규자 조회 상세 - coEmpViolationView
    Map<String, Object> coEmpViolationView(Map<String, Object> paramMap);

    // 구성원 보안 위규자 조치현황 - coEmpViolationActSumList
    List<Map<String, Object>> coEmpViolationActSumList(Map<String, Object> paramMap);

    // 구성원 보안 위규자 삭제 - secrtCorrPlanOfendDelete
    int secrtCorrPlanOfendDelete(Map<String, Object> paramMap);

    // 구성원 보안 위규자 삭제(문서) - secrtCorrPlanDocDelete
    int secrtCorrPlanDocDelete(Map<String, Object> paramMap);

    // 외부인 보안 위규자 조회 - ioEmpViolationList
    List<Map<String, Object>> ioEmpViolationList(Map<String, Object> paramMap);

    // 외부인 보안 위규자 조회 엑셀다운로드 - ioEmpViolationListExcel
    List<Map<String, Object>> ioEmpViolationListExcel(Map<String, Object> paramMap);

    // 외부인 보안 위규자 상세 - ioEmpViolationView
    Map<String, Object> ioEmpViolationView(Map<String, Object> paramMap);

    // 외부인 보안 위규자 상세 > 보안 위규 이력 - ioCorrPlanViolationList
    List<Map<String, Object>> ioCorrPlanViolationList(Map<String, Object> paramMap);

    //외부인 보안 위규자 상세 > 접견자 목록 - ioEmpViolationViewMeetList
    List<Map<String, Object>> ioEmpViolationViewMeetList(Map<String, Object> paramMap);

    //외부인 보안 위규자 상세 > 조치현황 - ioEmpViolationActSumList
    List<Map<String, Object>> ioEmpViolationActSumList(Map<String, Object> paramMap);

    // 외부인 보안 위규자 삭제 - secrtIoCorrPlanOfendDelete
    int secrtIoCorrPlanOfendDelete(Map<String, Object> paramMap);

    // 외부인 보안 위규자 삭제(문서) - secrtIoCorrPlanDocDelete
    int secrtIoCorrPlanDocDelete(Map<String, Object> paramMap);

    // 외부인 보안 위규자 승인/반려(문서) - ioCorrPlanDocApprUpdate
    int ioCorrPlanDocApprUpdate(Map<String, Object> paramMap);

    // 외부인 보안 위규자 승인/반려 - ioCorrPlanOfendDocUpdate
    int ioCorrPlanOfendDocUpdate(Map<String, Object> paramMap);

    // 외부인 보안 위규자  > 반려시 메일정보  - selectMailInfoForRejectMail
    Map<String, Object> selectMailInfoForRejectMail(Map<String, Object> paramMap);

    // 외부인 보안 위규자 상세 > 출입제한 상세 - offLimitsView
    Map<String, Object> offLimitsView(Map<String, Object> paramMap);

    // 외부인 보안 위규자 상세 > 출입제한 이력  - offLimitsHistoryList
    List<Map<String, Object>> offLimitsHistoryList(Map<String, Object> paramMap);

    // 출입제한 등록  >>>>>>
    // 외부인 보안 위규자 상세 > 출입제한 이력 등록  - offLimitsHistoryInsert : insert
    int offLimitsHistoryInsert(Map<String, Object> paramMap);

    // 외부인 보안 위규자 상세 > 출입제한 이력 Seq  - offLimitsIoEmpDenySeq : string
    String offLimitsIoEmpDenySeq(Map<String, Object> paramMap);

    // 외부인 보안 위규자 상세 > 이전 출입증 여부체크  - passMst2Check : map
    Map<String, Object> passMst2Check(Map<String, Object> paramMap);

    // 외부인 보안 위규자 상세 > 출입 외부 제한 이력 Seq  - offLimitsIoPassExprHistSeq : string
    String offLimitsIoPassExprHistSeq(Map<String, Object> paramMap);

    // 외부인 보안 위규자 상세 > 출입 외부 제한 이력 등록  - offLimitsExprHistoryInsert : insert
    int offLimitsExprHistoryInsert(Map<String, Object> paramMap);

    // 외부인 보안 위규자 상세 > 출입 외부 제한 - 하이스텍 정지신청 하는 I/F Procedure 호출 - callOffLimitsIoPassExprHist  : update
    int callOffLimitsIoPassExprHist(Map<String, Object> paramMap);

    // 외부인 보안 위규자 상세 > 출입 제한 이력 해제 - offLimitsHistoryUpdate
    int offLimitsHistoryUpdate(Map<String, Object> paramMap);

    // 외부인 보안 위규자 > 출입 제한 MAX(SMART_IDCARD) - offLimitsGetCardNo - return String IDCARD
    String offLimitsGetCardNo(Map<String, Object> paramMap);

    // 전사 비문 분류 기준표 조회  - secrtDocDistItemList
    List<Map<String, Object>> secrtDocDistItemList(Map<String, Object> paramMap);

    // 전사 비문 분류 기준표 조회  엑셀다운 - secrtDocDistItemListExcel
    List<Map<String, Object>> secrtDocDistItemListExcel(Map<String, Object> paramMap);

    // 전사 비문 분류 기준표 상세 - secrtDocDistView
    Map<String, Object> secrtDocDistView(Map<String, Object> paramMap);

    // 전사 비문 분류 기준표 상세 비문분류기준표 List - secrtDocItemViewList
    List<Map<String, Object>> secrtDocItemViewList(Map<String, Object> paramMap);

    // 보안 담당자 변경 조회 - secrtChangeList
    List<Map<String, Object>> secrtChangeList(Map<String, Object> paramMap);

    // 보안 담당자 변경 상세 - secrtChangeView
    Map<String, Object> secrtChangeView(Map<String, Object> paramMap);

    // 보안 위규 담당자 관리 조회 - secDetlEmpList
    List<Map<String, Object>> secDetlEmpList(Map<String, Object> paramMap);

    // 보안 위규 담당자 관리 조회(모바일포렌직) - secDetlMobileForEmpList
    List<Map<String, Object>> secDetlMobileForEmpList(Map<String, Object> paramMap);

    // 보안 위규 담당자 관리 상세정보 - secDetlEmpView
    Map<String, Object> secDetlEmpView(Map<String, Object> paramMap);

    // 보안 위규 담당자 관리 상세 > 저장  - secDetlEmpInsert
    int secDetlEmpInsert(Map<String, Object> paramMap);

    // 팀내 생활보안점검 조회(관) 조회 - coEmpTeamViolationList
    List<Map<String, Object>> coEmpTeamViolationList(Map<String, Object> paramMap);

    // 팀내 생활보안점검 조회(관)조회 엑셀다운로드 - coEmpTeamViolationListExcel
    List<Map<String, Object>> coEmpTeamViolationListExcel(Map<String, Object> paramMap);

    // 팀내 생활보안점검 조회(관)상세 - coEmpTeamViolationView
    Map<String, Object> coEmpTeamViolationView(Map<String, Object> paramMap);

    // 팀내 생활보안점검 조회(관)상세 이력조회 - coEmpTeamViolationHist
    List<Map<String, Object>> coEmpTeamViolationHist(Map<String, Object> paramMap);

    // 팀내 생활보안점검 조회(관)상세 삭제  - coEmpTeamViolationDelete
    int coEmpTeamViolationDelete(Map<String, Object> paramMap);

    // 팀내 생활보안점검 조회(관)상세 일괄삭제  - coEmpTeamViolationDel
    int coEmpTeamViolationDel(HashMap<String, Object> paramMap);

    // 보안 위규자 조회권한 조회 - secrtOfendAuthList
    List<Map<String, Object>> secrtOfendAuthList(Map<String, Object> paramMap);

    // 보안 위규자 조회권한 상세정보 List - secrtOfendAuthViewList
    List<Map<String, Object>> secrtOfendAuthViewList(Map<String, Object> paramMap);

    // 보안 위규자 권한 삭제 - secrtOfendAuthDelete
    int secrtOfendAuthDelete(String empId);

    // 보안 위규자 직원권한 삭제 - coEmpAuthDelete
    int coEmpAuthDelete(String empId);

    // 보안 위규자 권한 Insert - secrtOfendAuthInsert
    int secrtOfendAuthInsert(Map<String, Object> paramMap);

    // 보안 위규자 직원 권한 Insert - coEmpAuthInsert
    int coEmpAuthInsert(Map<String, Object> paramMap);

    // 보안교육이수율 관리 조회 - securityTrainingList
    List<Map<String, Object>> securityTrainingList(Map<String, Object> paramMap);

    // 보안교육이수율 관리 저장  - securityTrainingInsert
    int securityTrainingInsert(Map<String, Object> paramMap);

    // 보안교육이수율 관리 수정  - securityTrainingUpdate
    int securityTrainingUpdate(Map<String, Object> paramMap);

    // 보안교육이수율 관리 삭제  - securityTrainingDelete
    int securityTrainingDelete(Map<String, Object> paramMap);

    // 보안교육이수율 관리 상세 - securityTrainingView
    Map<String, Object> securityTrainingView(Map<String, Object> paramMap);

    // 보안점수 기준정보 조회 - secureEvalItemAdminList
    List<Map<String, Object>> secureEvalItemAdminList(Map<String, Object> paramMap);

    // 보안점수 기준정보 순번 생성 - secureEvalItemSeq
    String secureEvalItemSeq(Map<String, Object> paramMap);

    // 보안점수 기준정보 Insert/Update - secureEvalItemInsert
    int secureEvalItemInsert(Map<String, Object> paramMap);

    // 보안점수 기준정보 상세 - secureEvalItemView
    Map<String, Object> secureEvalItemView(Map<String, Object> paramMap);

    // 보안점수 기준정보  삭제 - secureEvalItemDelete
    int secureEvalItemDelete(Map<String, Object> paramMap);

    // 보안점수 기준정보 > 기존 항목 추가 - secureEvalPeriodRefInsert
    int secureEvalPeriodRefInsert(Map<String, Object> paramMap);

    // 보안점수 기준정보 > 기존 평가부서 조회 - secureEvalDeptMappingList
    List<Map<String, Object>> secureEvalDeptMappingList(Map<String, Object> paramMap);

    // 보안점수 기준정보 > 기존 평가부서 삭제 - secureEvalDeptMappingDelete
    int secureEvalDeptMappingDelete(Map<String, Object> paramMap);

    // 보안점수 기준정보 > 평가부서 Insert - secureEvalDeptMappingInsert
    int secureEvalDeptMappingInsert(Map<String, Object> paramMap);

    // 보안점수 기준정보 > 평가부서 Tree List - secureEvalDeptTreeList
    List<Map<String, Object>> secureEvalDeptTreeList(Map<String, Object> paramMap);

    // 전사 팀별 보안담당자 조회(RM부서) - secrtDeptSecDeputyRmList
    List<Map<String, Object>> secrtDeptSecDeputyRmList(Map<String, Object> paramMap);

    // 전사 팀별 보안담당자 조회(전사) - secrtDeptSecDeputyList
    List<Map<String, Object>> secrtDeptSecDeputyList(Map<String, Object> paramMap);

    // 전사 팀별 보안담당자 조회(RM부서) 엑셀다운 - secrtDeptSecDeputyRmListExcel
    List<Map<String, Object>> secrtDeptSecDeputyRmListExcel(Map<String, Object> paramMap);

    // 전사 팀별 보안담당자 조회(전사)엑셀다운 - secrtDeptSecDeputyListExcel
    List<Map<String, Object>> secrtDeptSecDeputyListExcel(Map<String, Object> paramMap);

    // 문서자가점검 이력 조회 - secrtDocSelfCheckList
    List<Map<String, Object>> secrtDocSelfCheckList(Map<String, Object> paramMap);

    // 문서자가점검 이력 조회 엑셀다운 - secrtDocSelfCheckListExcel
    List<Map<String, Object>> secrtDocSelfCheckListExcel(Map<String, Object> paramMap);

    // 개인정보 위탁업체 교육 이수현황 조회 - securityEduNoticeList
    List<Map<String, Object>> securityEduNoticeList(Map<String, Object> paramMap);

    // 개인정보 위탁업체 교육 이수현황 조회 엑셀다운  - securityEduNoticeListExcel
    List<Map<String, Object>> securityEduNoticeListExcel(Map<String, Object> paramMap);

    // 모바일 포렌직서약서 조회 - securityMobilePledgeSignList
    List<Map<String, Object>> securityMobilePledgeSignList(Map<String, Object> paramMap);

    // 모바일 포렌직서약서 상세 - securityMobilePledgeSignView
    Map<String, Object> securityMobilePledgeSignView(Map<String, Object> paramMap);

    // 정보보호서약서 조회 - securityPledgeList
    List<Map<String, Object>> securityPledgeList(Map<String, Object> paramMap);

    // 정보보호서약서 조회 엑셀다운 - securityPledgeListExcel
    List<Map<String, Object>> securityPledgeListExcel(Map<String, Object> paramMap);

    // 정보보호서약서 - 메일본문(코드정보) 저장 - updateMailInfo
    int updateMailInfo(Map<String, Object> paramMap);

    // 정보보호서약서 - 메일발송 목록저장 - updateSecurityPledgeMailList
    int updateSecurityPledgeMailList(Map<String, Object> paramMap);

    // 전사 보안담당자 관리 조회 - secrtChangeAdminList
    List<Map<String, Object>> secrtChangeAdminList(Map<String, Object> paramMap);

    // 전사 보안담당자 관리 엑셀다운 - secrtChangeAdminListExcel
    List<Map<String, Object>> secrtChangeAdminListExcel(Map<String, Object> paramMap);

    // 전사 보안담당자 정보 삭제 - secrtChangeAdminDelete
    int secrtChangeAdminDelete(Map<String, Object> paramMap);

    // 전사 보안담당자 직원정보 삭제 - secrtChangeAdminEmpDelete
    int secrtChangeAdminEmpDelete(Map<String, Object> paramMap);

    // 전사 보안담당자 관리 > 부서검색 - secrtDeptDuptyDeptList
    List<Map<String, Object>> secrtDeptDuptyDeptList(Map<String, Object> paramMap);

    // 전사 보안담당자 관리 > 등록  > Step01. Insert SC_DEPT_SEC - secrtChangeAdminScDeptSecInsert
    int secrtChangeAdminScDeptSecInsert(Map<String, Object> paramMap);

    // 전사 보안담당자 관리 > 등록  > Step02. Delete SC_DEPT_SEC_EMP - secrtChangeAdminScDeptSecEmpDelete
    int secrtChangeAdminScDeptSecEmpDelete(Map<String, Object> paramMap);

    // 전사 보안담당자 관리 > 등록  > Step03. Insert SC_DEPT_SEC_EMP - secrtChangeAdminScDeptSecEmpInsert
    int secrtChangeAdminScDeptSecEmpInsert(Map<String, Object> paramMap);

    // 전사 보안담당자 관리 > 등록  > Step04. MERGE CO_EMP_AUTH - secrtChangeSecrtEmpAuthInsert
    int secrtChangeSecrtEmpAuthInsert(Map<String, Object> paramMap);

    // 전사 보안담당자 관리 > 수정  > Step01. Update SC_DEPT_SEC - secrtChangeAdminScDeptSecUpdate
    int secrtChangeAdminScDeptSecUpdate(Map<String, Object> paramMap);

    // 전사 보안담당자 관리 > 수정  > Step02. Delete SC_DEPT_SEC_EMP - secrtChangeAdminScDeptSecEmpDelete2
    int secrtChangeAdminScDeptSecEmpDelete2(Map<String, Object> paramMap);

    // 전사 보안담당자 관리 > 관리부서Tree - secrtDeptDuptyDeptTreeList
    List<Map<String, Object>> secrtDeptDuptyDeptTreeList(Map<String, Object> paramMap);

    // 전사 보안담당자 관리 > 상세정보 - secrtChangeAdminView
    Map<String, Object> secrtChangeAdminView(Map<String, Object> paramMap);

    // 보안점수 관리 조회 - secureEvalStatusAdminList
    List<Map<String, Object>> secureEvalStatusAdminList(Map<String, Object> paramMap);

    // 보안점수 관리 조회 엑셀다운 - secureEvalStatusAdminListExcel
    List<Map<String, Object>> secureEvalStatusAdminListExcel(Map<String, Object> paramMap);

    // 보안점수 관리 조회 > 삭제 - secureEvalStatusDelete
    int secureEvalStatusDelete(Map<String, Object> paramMap);

    // 보안점수 관리 조회 > 확정/확정취소 - secureEvalStatusModify
    int secureEvalStatusModify(Map<String, Object> paramMap);

    // 보안점수 관리 > 평가항목 List - secureEvalItemTargetList
    List<Map<String, Object>> secureEvalItemTargetList(Map<String, Object> paramMap);

    // 보안점수 관리 > 평가결과저장  - Step01. Insert/Update SC_EVAL_RESULT - secureEvalResultUpdate
    int secureEvalResultUpdate(Map<String, Object> paramMap);

    // 보안점수 관리 > 평가결과저장  - Step02. Update SC_EVAL_TARGET_DEPT - secureEvalTotalScore
    int secureEvalTotalScore(Map<String, Object> paramMap);

    // 구성원 보안 위규자 조회 갯수 - coEmpViolationListCnt
    Integer coEmpViolationListCnt(Map<String, Object> paramMap);

    // 보안 위규 담당자 관리 조회(모바일포렌직) 갯수
    Integer secDetlMobileForEmpListCnt(Map<String, Object> paramMap);

    // 보안 위규 담당자 관리 조회 갯수
    Integer secDetlEmpListCnt(Map<String, Object> paramMap);

    // 외부인 보안 위규자 조회 갯수 - ioEmpViolationListCnt
    Integer ioEmpViolationListCnt(Map<String, Object> paramMap);

}
