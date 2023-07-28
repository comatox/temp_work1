package com.skshieldus.esecurity.service.secrtactvy.impl;

import com.skshieldus.esecurity.common.component.Mailing;
import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.repository.secrtactvy.SecurityManageReportRepository;
import com.skshieldus.esecurity.service.secrtactvy.SecurityManageReportService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SecurityManageReportImpl implements SecurityManageReportService {

    @Autowired
    private SecurityManageReportRepository repository;

    @Autowired
    private Mailing mailing;

    // 현장 보안운영 확인 등록 현황 조회 - securityReportList
    @Override
    public List<Map<String, Object>> securityReportList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.securityReportList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    // 현장 보안운영 확인 등록 현황 상세 - securityReportView
    @Override
    public Map<String, Object> securityReportView(Map<String, Object> paramMap) {
        Map<String, Object> resultView = null;

        try {
            resultView = repository.securityReportView(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultView;
    }

    // 현장 보안운영 RPT_ID Nextval
    @Override
    public String selectRptIdNextval() {
        String retVal = null;

        try {
            retVal = repository.selectRptIdNextval();
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return retVal;
    }

    // 현장 보안운영 RPT_NO Nextval
    @Override
    public String selectRptNoNextval() {
        String retVal = null;

        try {
            retVal = repository.selectRptNoNextval();
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return retVal;
    }

    // 현장 보안운영 확인 등록 - insertSecurityReport
    @Override
    public int insertSecurityReport(Map<String, Object> paramMap) {
        int insertRows = 0;
        try {
            // Step1. 현장 보안운영 RPT_ID, RPT_NO Nextval
            String rptId = this.selectRptIdNextval();
            String rptNo = this.selectRptNoNextval();
            paramMap.put("rptId", rptId); // RPT_ID
            paramMap.put("rptNo", rptNo); // RPT_NO

            // Step2. 현장 보안운영 확인 등록 - insertSecurityReportMaster
            insertRows = this.insertSecurityReportMaster(paramMap);

            String[] arrEduCompIds = { "1101000001", "1102000001", "1108000001" }; // 이천, 청주, 분당
            String[] arrPrefixs = { "ic", "cj", "bd" }; // 이천, 청주, 분당
            int i = 0;
            int insertRowsDetail = 0;
            System.out.println("# paramMap before:" + paramMap);
            // Loop Start
            for (String eduCompId : arrEduCompIds) {

                String preFix = arrPrefixs[i];
                paramMap.put("eduCompId", eduCompId); // EDU_COMP_ID
                // 투입전 교육
                paramMap.put("befEduNmr", paramMap.get(preFix + "BefEduNmr"));
                paramMap.put("befEduDnm", paramMap.get(preFix + "BefEduDnm"));
                paramMap.put("befEduPct", paramMap.get(preFix + "BefEduPct"));
                // CUBE 교육
                paramMap.put("cubeEduNmr", paramMap.get(preFix + "CubeEduNmr"));
                paramMap.put("cubeEduDnm", paramMap.get(preFix + "CubeEduDnm"));
                paramMap.put("cubeEduPct", paramMap.get(preFix + "CubeEduPct"));
                // 집합 교육
                paramMap.put("gthrEduNmr", paramMap.get(preFix + "GthrEduNmr"));
                paramMap.put("gthrEduDnm", paramMap.get(preFix + "GthrEduDnm"));
                paramMap.put("gthrEduPct", paramMap.get(preFix + "GthrEduPct"));
                // ADT캡스(1주차)
                paramMap.put("week1Rslt", paramMap.get(preFix + "Week1Rslt"));
                // ADT캡스(2주차)
                paramMap.put("week2Rslt", paramMap.get(preFix + "Week2Rslt"));

                System.out.println("# i.preFix:" + i + ". " + preFix);
                System.out.println("# paramMap after:" + paramMap);

                // Step3. 현장 보안운영 확인 상세 등록 - insertSecurityReportDetail
                insertRowsDetail += this.insertSecurityReportDetail(paramMap);

                i++;
            } // End Loop

            /* 완료 시 SendMail - fmSecurityReportMail */
            if (insertRows > 0) { // 완료
                // TODO: sendMail 구현
                System.out.println("# sendMail .....");
                paramMap.put("mailType", "REG");  // Mail Type : REG
                // Call Mail Send
                this.ReportSendMail(paramMap);
            }

            System.out.println("# rptId:" + rptId);
            System.out.println("# rptNo:" + rptNo);
            System.out.println("# insertRows:" + insertRowsDetail);
            System.out.println("# insertRowsDetail:" + insertRowsDetail);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return insertRows;
    }

    // 현장 보안운영 확인 등록 - insertSecurityReportMaster
    @Override
    public int insertSecurityReportMaster(Map<String, Object> paramMap) {
        int updatedRows = 0;
        try {
            updatedRows = repository.insertSecurityReportMaster(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return updatedRows;
    }

    // 현장 보안운영 확인 상세 등록 - insertSecurityReportDetail
    @Override
    public int insertSecurityReportDetail(Map<String, Object> paramMap) {
        int updatedRows = 0;
        try {
            updatedRows = repository.insertSecurityReportDetail(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return updatedRows;
    }

    // 현장 보안운영 확인 Update - updateSecurityReport
    @Override
    public int updateSecurityReport(Map<String, Object> paramMap) {
        int updatedRows = 0;
        try {

            int insertRows = 0;

            String saveType = (String) paramMap.get("saveType"); // U.수정, O.완료

            if ("U".equals(saveType)) { // 수정
                // Step1. 현장 보안운영 RPT_NO Nextval
                String rptNo = this.selectRptNoNextval();
                // Step2. 현장 보안운영 확인 등록 - insertSecurityReportMaster
                paramMap.put("rptNo", rptNo); // RPT_NO
                insertRows = this.insertSecurityReportMaster(paramMap);
                // Step3. 현장 보안운영 확인 Update - updateSecurityReportMaster
                paramMap.put("chgRptNo", rptNo); // CHG_RPT_NO
                updatedRows = this.updateSecurityReportMaster(paramMap);
            }
            else if ("O".equals(saveType)) { // 완료
                // Step1. 현장 보안운영 확인 Over Update - updateSecurityReportOverMaster
                updatedRows = this.updateSecurityReportOverMaster(paramMap);
            }

            String[] arrEduCompIds = { "1101000001", "1102000001", "1108000001" }; // 이천, 청주, 분당
            String[] arrPrefixs = { "ic", "cj", "bd" }; // 이천, 청주, 분당
            int i = 0;
            int updateRowsDetail = 0;
            // Loop Start
            for (String eduCompId : arrEduCompIds) {

                String preFix = arrPrefixs[i];
                paramMap.put("eduCompId", eduCompId); // EDU_COMP_ID
                // 투입전 교육
                paramMap.put("befEduNmr", paramMap.get(preFix + "BefEduNmr"));
                paramMap.put("befEduDnm", paramMap.get(preFix + "BefEduDnm"));
                paramMap.put("befEduPct", paramMap.get(preFix + "BefEduPct"));
                // CUBE 교육
                paramMap.put("cubeEduNmr", paramMap.get(preFix + "CubeEduNmr"));
                paramMap.put("cubeEduDnm", paramMap.get(preFix + "CubeEduDnm"));
                paramMap.put("cubeEduPct", paramMap.get(preFix + "CubeEduPct"));
                // 집합 교육
                paramMap.put("gthrEduNmr", paramMap.get(preFix + "GthrEduNmr"));
                paramMap.put("gthrEduDnm", paramMap.get(preFix + "GthrEduDnm"));
                paramMap.put("gthrEduPct", paramMap.get(preFix + "GthrEduPct"));
                // ADT캡스(1주차)
                paramMap.put("week1Rslt", paramMap.get(preFix + "Week1Rslt"));
                // ADT캡스(2주차)
                paramMap.put("week2Rslt", paramMap.get(preFix + "Week2Rslt"));

                if ("U".equals(saveType)) { // 수정
                    // 현장 보안운영 확인 상세 Update - updateSecurityReportDetail
                    updateRowsDetail += this.updateSecurityReportDetail(paramMap);
                }
                else if ("O".equals(saveType)) { // 완료
                    // 현장 보안운영 확인 상세 Over Update - updateSecurityReportOverDetail
                    updateRowsDetail += this.updateSecurityReportOverDetail(paramMap);
                }

                i++;
            } // End Loop

            /* 완료 시 SendMail - fmSecurityReportMail */
            if ("O".equals(saveType) && updatedRows > 0) { // 완료
                // TODO: sendMail 구현
                System.out.println("# sendMail .....");
                paramMap.put("mailType", "CPLT");  // Mail Type : CPLT
                // Call Mail Send
                this.ReportSendMail(paramMap);
            }

            System.out.println("# insertRows:" + insertRows);
            System.out.println("# updatedRows:" + updatedRows);
            System.out.println("# updateRowsDetail:" + updateRowsDetail);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return updatedRows;
    }

    // Mail Send
    @SuppressWarnings("unchecked")
    private void ReportSendMail(Map<String, Object> paramMap) {

        System.out.println("# ReportSendMail paramMap : " + paramMap.toString());

        String mailType = (String) paramMap.get("mailType");
        String title = "";
        String message = "";

        String manualName = (String) paramMap.get("manualName");
        if ("REG".equals(mailType)) {
            title = "[e-Security] 현장 보안운영 상태 확인 Report 등록 안내";
            message = "현장 보안운영 상태 확인 Report [" + manualName + "] 가 등록 되었습니다. <br />"
                      + "보안활동 > 현장 보안운영 확인 > 현장 보안운영 확인 등록 현황 에서 확인하시기 바랍니다.";
        }
        else if ("CPLT".equals(mailType)) {
            title = "[e-Security] 현장 보안운영 상태 확인 Report 완료 안내";
            message = "현장 보안운영 상태 확인 Report [" + manualName + "] 가 완료 되었습니다. <br />"
                      + "보안활동 > 현장 보안운영 확인 > 현장 보안운영 확인 등록 현황 에서 확인하시기 바랍니다.";
        }

        String schemaNm = "SECURITY_REPORT";
        String acIp = StringUtils.defaultIfBlank((String) paramMap.get("acIp"), "SYSTEM");
        // 현장 보안운영 확인 메일 수신자 List - securityReportMailReceiver
        List<Map<String, Object>> arrMailReceiver = repository.securityReportMailReceiver(paramMap);
        // Loop Start
        for (Map<String, Object> mailReceiver : arrMailReceiver) {
            String sendToEmail = (String) mailReceiver.get("empMail");
            String empId = (String) mailReceiver.get("empId");
            // (String title, String content, String to, String empNo, String schemaNm, String docId, String acIp)
            // Call SendMail
            boolean result = mailing.sendMail(title, mailing.applyMailTemplate(title, message), sendToEmail, empId, schemaNm, (String) paramMap.get("docNm"), acIp);
            System.out.println("# ReportSendMail result : " + result);
        }
    }

    // 현장 보안운영 확인 Update - updateSecurityReportMaster
    @Override
    public int updateSecurityReportMaster(Map<String, Object> paramMap) {
        int updatedRows = 0;
        try {
            updatedRows = repository.updateSecurityReportMaster(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return updatedRows;
    }

    // 현장 보안운영 확인 Over Update - updateSecurityReportOverMaster
    @Override
    public int updateSecurityReportOverMaster(Map<String, Object> paramMap) {
        int updatedRows = 0;
        try {
            updatedRows = repository.updateSecurityReportOverMaster(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return updatedRows;
    }

    // 현장 보안운영 확인 상세 Update - updateSecurityReportDetail
    @Override
    public int updateSecurityReportDetail(Map<String, Object> paramMap) {
        int updatedRows = 0;
        try {
            updatedRows = repository.updateSecurityReportDetail(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return updatedRows;
    }

    // 현장 보안운영 확인 상세 Over Update - updateSecurityReportOverDetail
    @Override
    public int updateSecurityReportOverDetail(Map<String, Object> paramMap) {
        int updatedRows = 0;
        try {
            updatedRows = repository.updateSecurityReportOverDetail(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return updatedRows;
    }

}
