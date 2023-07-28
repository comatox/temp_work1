package com.skshieldus.esecurity.repository.secrtactvy;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface SecurityManageReportRepository {

    // 현장 보안운영 확인 등록 현황 조회 - securityReportList
    List<Map<String, Object>> securityReportList(Map<String, Object> paramMap);

    // 현장 보안운영 확인 등록 현황 상세 - securityReportView
    Map<String, Object> securityReportView(Map<String, Object> paramMap);

    // 현장 보안운영 RPT_ID Nextval
    String selectRptIdNextval();

    // 현장 보안운영 RPT_NO Nextval
    String selectRptNoNextval();

    // 현장 보안운영 확인 등록 - insertSecurityReportMaster
    int insertSecurityReportMaster(Map<String, Object> paramMap);

    // 현장 보안운영 확인 상세 등록 - insertSecurityReportDetail
    int insertSecurityReportDetail(Map<String, Object> paramMap);

    // 현장 보안운영 확인 Update - updateSecurityReportMaster
    int updateSecurityReportMaster(Map<String, Object> paramMap);

    // 현장 보안운영 확인 Over Update - updateSecurityReportOverMaster
    int updateSecurityReportOverMaster(Map<String, Object> paramMap);

    // 현장 보안운영 확인 상세 Update - updateSecurityReportDetail
    int updateSecurityReportDetail(Map<String, Object> paramMap);

    // 현장 보안운영 확인 상세 Over Update - updateSecurityReportOverDetail
    int updateSecurityReportOverDetail(Map<String, Object> paramMap);

    // 현장 보안운영 확인 메일 수신자 List - securityReportMailReceiver
    List<Map<String, Object>> securityReportMailReceiver(Map<String, Object> paramMap);

}
