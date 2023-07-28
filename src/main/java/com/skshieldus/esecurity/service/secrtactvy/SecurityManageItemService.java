package com.skshieldus.esecurity.service.secrtactvy;

import com.skshieldus.esecurity.common.model.ListDTO;
import com.skshieldus.esecurity.model.common.CommonXlsViewDTO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SecurityManageItemService {

    // 팀내보안 위규자 목록 조회
    ListDTO<Map<String, Object>> selectSecurityConcernCoEmpViolation(Map<String, Object> paramMap);

    // 팀내보안 위규자 상세 조회
    Map<String, Object> selectSecurityConcernCoEmpViolationView(Map<String, Object> paramMap);

    // 팀내보안 위규자 목록 엑셀다운 - securityConcernCoEmpViolationExcel
    CommonXlsViewDTO securityConcernCoEmpViolationExcel(Map<String, Object> paramMap);

    // 타팀 보안담당자 조회
    List<Map<String, Object>> securityDeputyOtherTeamSecList(Map<String, Object> paramMap);

    // 타팀 휴대용 전산저장장치 조회
    List<Map<String, Object>> otherTeamPortableStorageList(Map<String, Object> paramMap);

    // 외부인 보안 위규자 조회
    List<Map<String, Object>> securityConcernTeamViolationIoEmpMeetList(Map<String, Object> paramMap);

    // 외부인 보안 위규자 상세 조회
    Map<String, Object> securityConcernTeamViolationIoEmpMeetView(Map<String, Object> paramMap);

    // 외부인 보안 위규자 상세 조회 엑셀 다운로드
    CommonXlsViewDTO securityConcernTeamViolationIoEmpMeetListExcel(Map<String, Object> paramMap);

    // 정보보호서약서 조회
    List<Map<String, Object>> securityPledgeList(Map<String, Object> paramMap);

    // 정보보호서약서 조회 엑셀 다운로드
    CommonXlsViewDTO securityPledgeListExcel(Map<String, Object> paramMap);

    // 구성원 정보보호서약서 동의 현황 조회
    Map<String, Object> selectSecurityPledgeStatusList(Map<String, Object> paramMap);

    // 보안담당부서 List - secDeptList
    List<Map<String, Object>> secDeptList(Map<String, Object> paramMap);

    // 팀 보안감점 항목 조회
    List<Map<String, Object>> securityDeptSecMinList(Map<String, Object> paramMap);

    // 팀 보안감점 항목 상세
    Map<String, Object> securityDeptSecMinView(Map<String, Object> paramMap);

    // 팀 보안감점 항목 삭제
    int securityDeptSecMinDelete(Map<String, Object> paramMap);

    // 팀내 무선기기 조회 - wirelessReqList
    List<Map<String, Object>> wirelessReqList(Map<String, Object> paramMap);

    // 팀내 무선기기 조회 엑셀다운 - wirelessReqListExcel
    CommonXlsViewDTO wirelessReqListExcel(Map<String, Object> paramMap);

    // 팀내 무선기기 조회 상세 > 무선기기사용 신청 목록 - wirelessEqInfoReqList
    List<Map<String, Object>> wirelessEqInfoReqList(Map<String, Object> paramMap);

    // 팀 보안점수 조회 - secureEvalTeamScoreList
    List<Map<String, Object>> secureEvalTeamScoreList(Map<String, Object> paramMap);

    // 팀 보안점수 조회 엑셀다운 - secureEvalTeamScoreListExcel
    CommonXlsViewDTO secureEvalTeamScoreListExcel(Map<String, Object> paramMap);

    // 팀 보안점수 상세 점검항목 List - secureEvalItemTargetList
    List<Map<String, Object>> secureEvalItemTargetList(Map<String, Object> paramMap);

    // 팀 보안점수 상세 점검항목 상세 - secureEvalItemDetail
    Map<String, Object> secureEvalItemDetail(Map<String, Object> paramMap);

    // 팀내 전산저장장치조회 - secureStorageManageList
    List<Map<String, Object>> secureStorageManageList(Map<String, Object> paramMap);

    // 팀내 전산저장장치조회 엑셀다운 - secureStorageManageListExcel
    CommonXlsViewDTO secureStorageManageListExcel(Map<String, Object> paramMap);

    // 팀내 전산저장장치 실사결과 저장 - saveActualInspection
    int saveActualInspection(List<HashMap<String, Object>> paramMaps);

    // 팀내 생활보안점검 조회(보안담당자) - secureCoEmpTeamViolationList
    List<Map<String, Object>> secureCoEmpTeamViolationList(Map<String, Object> paramMap);

    // 팀내 생활보안점검 조회(보안담당자 ) 엑셀다운  - secureCoEmpTeamViolationListExcel
    CommonXlsViewDTO secureCoEmpTeamViolationListExcel(Map<String, Object> paramMap);

    // 팀내 생활보안점검 조회(보안담당자 ) 상세 - secureCoEmpTeamViolationView
    Map<String, Object> secureCoEmpTeamViolationView(Map<String, Object> paramMap);

    // 팀내 생활보안점검 조회(보안담당자) 이력조회 - secureCoEmpTeamViolationHist
    List<Map<String, Object>> secureCoEmpTeamViolationHist(Map<String, Object> paramMap);

    // 팀내 생활보안점검 조회(보안담당자) 삭제  - secureCoEmpTeamViolationDelete
    int secureCoEmpTeamViolationDelete(Map<String, Object> paramMap);

    // 팀내 생활보안점검 조회(보안담당자) 일괄삭제  - secureCoEmpTeamViolationdel
    Boolean secureCoEmpTeamViolationDel(HashMap<String, Object> paramMap);

    // 팀내 생활보안점검 결과(보안담당자) 조회 - secureCoEmpTeamViolationResultList
    List<Map<String, Object>> secureCoEmpTeamViolationResultList(Map<String, Object> paramMap);

    // 팀내 생활보안점검 등록(일반사용자) > 점검결과 저장 (지적사항없음) - secCoEmpTeamNoViolationInsert
    int secCoEmpTeamNoViolationInsert(Map<String, Object> paramMap);

    // 팀내 생활보안점검 등록(일반사용자) > 점검결과 저장 (지적사항 있음) - secCoEmpTeamViolationInsert
    int secCoEmpTeamViolationInsert(Map<String, Object> paramMap);

    // 문서출력량조회 List - selectSecurityPrintingList
    List<Map<String, Object>> selectSecurityPrintingList(Map<String, Object> paramMap);

    // 문서출력량조회 > 부서 List - selectSecurityPrintingDeptList
    List<Map<String, Object>> selectSecurityPrintingDeptList(Map<String, Object> paramMap);

    // 문서출력량조회 List 엑셀다운 - selectSecurityPrintingExcel
    CommonXlsViewDTO selectSecurityPrintingExcel(Map<String, Object> paramMap);

}







