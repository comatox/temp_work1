package com.skshieldus.esecurity.service.secrtactvy;

import java.util.List;
import java.util.Map;

public interface SecurityDeputyService {

    // 보안담당자 신규/변경 조회 - securityDeputyList
    List<Map<String, Object>> securityDeputyList(Map<String, Object> paramMap);

    // 보안담당자 신규/변경 상세정보 - securityDeputyView
    Map<String, Object> securityDeputyView(Map<String, Object> paramMap);

    // 보안담당자 신규/변경 상세정보 > 관리부서 Tree - securityDeputyDeptTreeList
    List<Map<String, Object>> securityDeputyDeptTreeList(Map<String, Object> paramMap);

    // 팀내생활보안점검 > 점검부서 Tree - securityDeputyDeptTreeList3
    List<Map<String, Object>> securityDeputyDeptTreeList3(Map<String, Object> paramMap);

    // 보안담당자 신규/변경  > 보안담당자 신규/변경 (상신)
    boolean insertSecurityDeputy(Map<String, Object> paramMap);

    // 보안담당자 신규/변경 신청 > 보안담당자 List - secrtDeptDuptyCheckDuptyRenew
    List<Map<String, Object>> secrtDeptDuptyCheckDuptyRenew(Map<String, Object> paramMap);

    // 보안담당자 신규/변경 신청 > 보안담당자 정보 - secrtDeptDuptyDuptyInfo
    Map<String, Object> secrtDeptDuptyDuptyInfo(Map<String, Object> paramMap);

}
