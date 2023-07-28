package com.skshieldus.esecurity.service.secrtactvy;

import java.util.List;
import java.util.Map;

public interface SecurityManageReportService {

    // 현장 보안운영 확인 등록 현황 조회 - securityReportList
    List<Map<String, Object>> securityReportList(Map<String, Object> paramMap);

    // 현장 보안운영 확인 등록 현황 상세 - securityReportView
    Map<String, Object> securityReportView(Map<String, Object> paramMap);

    // 현장 보안운영 RPT_ID Nextval
    String selectRptIdNextval();

    // 현장 보안운영 RPT_NO Nextval
    String selectRptNoNextval();

    // 현장 보안운영 확인 등록 - insertSecurityReport
    int insertSecurityReport(Map<String, Object> paramMap);

    // 현장 보안운영 확인 등록 - insertSecurityReportMaster
    int insertSecurityReportMaster(Map<String, Object> paramMap);

    // 현장 보안운영 확인 상세 등록 - insertSecurityReportDetail
    int insertSecurityReportDetail(Map<String, Object> paramMap);

    // 현장 보안운영 확인 Update - updateSecurityReport
    int updateSecurityReport(Map<String, Object> paramMap);

    // 현장 보안운영 확인 Update - updateSecurityReportMaster
    int updateSecurityReportMaster(Map<String, Object> paramMap);

    // 현장 보안운영 확인 Over Update - updateSecurityReportOverMaster
    int updateSecurityReportOverMaster(Map<String, Object> paramMap);

    // 현장 보안운영 확인 상세 Update - updateSecurityReportDetail
    int updateSecurityReportDetail(Map<String, Object> paramMap);

    // 현장 보안운영 확인 상세 Over Update - updateSecurityReportOverDetail
    int updateSecurityReportOverDetail(Map<String, Object> paramMap);

}
