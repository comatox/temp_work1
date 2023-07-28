package com.skshieldus.esecurity.service.secrtactvy.impl;

import com.skshieldus.esecurity.common.component.Mailing;
import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ListDTO;
import com.skshieldus.esecurity.model.common.CommonXlsViewDTO;
import com.skshieldus.esecurity.repository.secrtactvy.SecurityAdminManageItemRepository;
import com.skshieldus.esecurity.service.secrtactvy.SecurityAdminManageItemService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class SecurityAdminManageItemImpl implements SecurityAdminManageItemService {

    @Autowired
    private SecurityAdminManageItemRepository repository;

    //	@Autowired
    //	private EsecuritySiRepository siDS;

    //	@Autowired
    //	private IdcardVisitRepository idCardVisit;

    @Autowired
    private Mailing mailing;

    @Autowired
    private Environment environment;

    // 구성원 보안 위규자 조회 - coEmpViolationList
    @Override
    public ListDTO<Map<String, Object>> coEmpViolationList(Map<String, Object> paramMap) {


        List<Map<String, Object>> resultList = null;
        Integer totalCount = 0;

        try {
            resultList = repository.coEmpViolationList(paramMap);
            totalCount = repository.coEmpViolationListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return ListDTO.getInstance(resultList, totalCount);

    }

    // 구성원 보안 위규자 조회 엑셀다운 - coEmpViolationListExcel
    @Override
    public CommonXlsViewDTO coEmpViolationListExcel(Map<String, Object> paramMap) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        CommonXlsViewDTO commonXlsViewDTO = new CommonXlsViewDTO();
        commonXlsViewDTO.setFileName("구성원보안위규자" + "_" + sdf.format(new Date()));

        try {
            // set header names
            String[] headerNameArr =
                { "지적일", "지적시각", "안내일", "안내시각", "사번", "성명", "직위"
                , "부서", "위규 구분", "위규 내용", "위규 내용 상세", "모바일 포렌직", "적발 사업장", "적발 건물"
                , "적발 건물 상세", "적발 GATE",  "보안요원 성명",  "담당자", "등록자", "위규조치" , "시정계획서/개선계획서 제출여부"
                , "시정계획서/개선계획서 제출일", "시정계획서/개선계획서 경과일", "시정계획서/개선계획서 처리상태", "보안요원 검토의견" };
            commonXlsViewDTO.setHeaderNameArr(headerNameArr);

            // set column names (data field name)
            String[] columnNameArr =
                { "ofendDt", "ofendTm", "actDay", "actTtmm", "ofendEmpId", "empNm", "jwNm"
                ,  "deptNm", "ofendGbnNm", "ofendDetailGbnNm", "ofendSubGbnNm", "mobileForensicsGbnNm", "actCompNm", "actBldgNm"
                , "actLocateNm", "actGate", "secManNm", "scEmpNm", "regEmpNm", "actDoNm" , "corrPlanYn"
                , "sendDtm", "passDate", "apprResultNm", "etcRsn" };
            commonXlsViewDTO.setColumnNameArr(columnNameArr);

            // set column width
            Integer[] columnWidthArr =
                {
                5000, 5000, 5000, 5000, 5000, 5000, 5000
                , 5000, 5000, 5000, 5000, 5000, 5000, 5000
                , 5000, 5000, 5000, 5000, 5000, 5000, 5000
                , 5000, 5000, 5000, 30000
                };
            commonXlsViewDTO.setColumnWidthArr(columnWidthArr);

            List<Map<String, Object>> list = repository.coEmpViolationListExcel(paramMap);

            // set excel data
            commonXlsViewDTO.setDataList(list);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return commonXlsViewDTO;
    }

    // 담당자 List - scDetlEmpList
    @Override
    public List<Map<String, Object>> scDetlEmpList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.scDetlEmpList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    // 구성원 보안 위규자 조회 상세 - coEmpViolationView
    @Override
    public Map<String, Object> coEmpViolationView(Map<String, Object> paramMap) {
        Map<String, Object> resultView = null;

        try {
            String operateId = "R_VIOLATION"; // 운영
            if (environment.acceptsProfiles(Profiles.of("default", "dev", "stg"))) { //로컬, 개발
                operateId = "D_VIOLATION";
            }
            paramMap.put("operateId", operateId);

            resultView = repository.coEmpViolationView(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultView;
    }

    // 구성원 보안 위규자 조치현황 - coEmpViolationActSumList
    @Override
    public List<Map<String, Object>> coEmpViolationActSumList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.coEmpViolationActSumList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    // 구성원 보안 위규자 삭제 - secrtCorrPlanOfendDelete
    @Override
    public int secrtCorrPlanOfendDelete(Map<String, Object> paramMap) {

        int updatedRows = 0;
        try {
            // 위규자 정보 삭제 (SC_OFEND)
            updatedRows = repository.secrtCorrPlanOfendDelete(paramMap);
            // 문서정보 삭제 (SC_CORR_PLAN)
            updatedRows += repository.secrtCorrPlanDocDelete(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return updatedRows;
    }

    // 외부인 보안 위규자 조회 - ioEmpViolationList
    @Override
    public ListDTO<Map<String, Object>> ioEmpViolationList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;
        Integer totalCount = 0;

        try {
            resultList = repository.ioEmpViolationList(paramMap);
            totalCount = repository.ioEmpViolationListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return ListDTO.getInstance(resultList, totalCount);
    }

    // 외부인 보안 위규자 조회 엑셀다운로드 - ioEmpViolationListExcel
    @Override
    public CommonXlsViewDTO ioEmpViolationListExcel(Map<String, Object> paramMap) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        CommonXlsViewDTO commonXlsViewDTO = new CommonXlsViewDTO();
        commonXlsViewDTO.setFileName("외부인보안위규자" + "_" + sdf.format(new Date()));

        try {
            // set header names
            String[] headerNameArr = {
                "요청회사", "지적일", "지적시각", "안내일", "안내시각"
                , "아이디", "성명", "직위", "업체명", "위규구분"
                , "위규내용", "위규 내용 상세", "모바일 포렌직", "적발 사업장", "적발 건물"
                , "적발 선물 상세", "적발 GATE", "보안요원 성명", "담당자", "등록자"
                , "위규조치", "시정계획서/시정공문 제출여부", "시정계획서/시정공문 제출일시", "시정계획서/시정공문 경과일", "시정계획서/시정공문 처리상태"
                , "출입정지 기간"
           };
            commonXlsViewDTO.setHeaderNameArr(headerNameArr);

            // set column names (data field name)
            String[] columnNameArr = {
                "regCompNm", "ofendDt", "ofendTm", "actYmd", "actHm"
                , "ofendEmpId", "ofendEmpNm", "ofendJwNm", "ofendCompNm", "ofendGbnNm"
                , "ofendDetailGbnNm", "ofendSubGbnNm", "mobileForensicsGbnNm", "actCompNm", "actBldgNm"
                , "actLocateNm", "actGate", "secManNm", "guEmpNm", "regEmpNm"
                , "actDoNm", "corrPlanYn", "propDtm", "lapDays", "apprGbnNm"
                , "denyPeriod"
            };
            commonXlsViewDTO.setColumnNameArr(columnNameArr);

            // set column width
            Integer[] columnWidthArr = {
                5000, 5000, 5000, 5000, 5000,
                5000, 5000, 5000, 5000, 5000,
                8000, 8000, 5000, 5000, 5000,
                5000, 5000, 5000, 5000, 5000,
                5000
            };
            commonXlsViewDTO.setColumnWidthArr(columnWidthArr);

            List<Map<String, Object>> list = repository.ioEmpViolationListExcel(paramMap);

            // set excel data
            commonXlsViewDTO.setDataList(list);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return commonXlsViewDTO;
    }

    // 외부인 보안 위규자 상세 - ioEmpViolationView
    @Override
    public Map<String, Object> ioEmpViolationView(Map<String, Object> paramMap) {
        Map<String, Object> resultView = null;

        try {
            String operateId = "R_VIOLATION"; // 운영
            if (environment.acceptsProfiles(Profiles.of("default", "dev", "stg"))) { //로컬, 개발
                operateId = "D_VIOLATION";
            }
            paramMap.put("operateId", operateId);
            resultView = repository.ioEmpViolationView(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultView;
    }

    // 외부인 보안 위규자 상세 > 보안 위규 이력 - ioCorrPlanViolationList
    @Override
    public List<Map<String, Object>> ioCorrPlanViolationList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.ioCorrPlanViolationList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    //외부인 보안 위규자 상세 > 접견자 목록 - ioEmpViolationViewMeetList
    @Override
    public List<Map<String, Object>> ioEmpViolationViewMeetList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.ioEmpViolationViewMeetList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    //외부인 보안 위규자 상세 > 조치현황 - ioEmpViolationActSumList
    @Override
    public List<Map<String, Object>> ioEmpViolationActSumList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.ioEmpViolationActSumList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    // 외부인 보안 위규자 삭제 - secrtIoCorrPlanOfendDelete
    @Override
    public int secrtIoCorrPlanOfendDelete(Map<String, Object> paramMap) {

        int updatedRows = 0;
        try {
            // 위규자 정보 삭제 (SC_IO_OFEND)
            updatedRows = repository.secrtIoCorrPlanOfendDelete(paramMap);
            // 문서정보 삭제 (SC_IO_CORR_PLAN)
            updatedRows += repository.secrtIoCorrPlanDocDelete(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return updatedRows;
    }

    // 외부인 보안 위규자 승인/반려(문서) - ioCorrPlanDocApprUpdate
    // 외부인 보안 위규자 승인/반려 - ioCorrPlanOfendDocUpdate
    // 메일발송 처리
    @Override
    public int ioCorrPlanDocApprUpdate(Map<String, Object> paramMap) {

        int updatedRows = 0;
        try {
            // 외부인 보안 위규자 승인/반려(문서) (SC_IO_CORR_PLAN)
            updatedRows = repository.ioCorrPlanDocApprUpdate(paramMap);
            // 외부인 보안 위규자 승인/반려 (SC_IO_OFEND)
            updatedRows += repository.ioCorrPlanOfendDocUpdate(paramMap);

        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return updatedRows;
    }

    // Mail Send
    @SuppressWarnings("unchecked")
    private void IoEmpCorrPlanRejectMail(Map<String, Object> retMailInfo, Map<String, Object> paramMap) {

        //		System.out.println("# IoEmpCorrPlanRejectMail paramMap : " + retMailInfo.toString());

        try {

            String actDo = StringUtils.defaultIfEmpty((String) retMailInfo.get("actDo"), "");

            StringBuffer str = new StringBuffer();
            String title = "";
            String strName = "";

            if ("C0280002".equals(actDo)) {
                title = "[행복한 만남] 제출하신 시정계획서가 반려되었습니다.";
                strName = "시정계획서";
            }
            else if ("C0280006".equals(actDo)) {
                title = "[행복한 만남] 제출하신 시정공문이 반려되었습니다.";
                strName = "대표이사 시정공문";
            }
            else {
                return;
            }

            str.append("    <div>																																");
            str.append("       ▶ 제출된 「" + strName + "」가 반려되었음을 알려드립니다.  <br>                                                                                                                 ");
            str.append("       ▶" + StringUtils.defaultIfEmpty((String) retMailInfo.get("empNm"), "") + "께서는 아래 보안위규 건에 대해, 행복한만남 사이트에                                                                                                          ");
            str.append("          접속하여 반려사유를 확인후 " + strName + "를 재작성하시기 바랍니다. <br>                                                                                                            ");
            str.append("       ▶ <span style='color:#f57724'>「" + strName + "」를 14일 이내 미제출시에는 「1개월 출입정지」 처리 됩니다.</span> <br>                                                             ");
            str.append("       ▶ 행복한만남 사이트 :  welcome.skhynix.com > 기본정보 > 시정계획서 및 시정공문                                                                                         ");
            str.append("       <br>                                                                                                                                                         ");
            str.append("       <table border='1' bordercolor='#e7d1c2' cellspacing='0' cellpadding='5' style='border-collapse:collapse; font-family:Malgun Gothic,  Dotum; font-size:14px;'>                                                   ");
            str.append("          <tbody>                                                                                                                                                   ");
            str.append("          	 <tr>                                                                                                                                                   ");
            str.append("                <td align='center' bgcolor='#ffb45c' width='20%'><span class='td_title'>회사명</span></td>                                                            ");
            str.append("                <td align='left' width='80%'>" + StringUtils.defaultIfEmpty((String) retMailInfo.get("ioCompNm"), "") + "</td>                                                                                                      ");
            str.append("             </tr>                                                                                                                                                  ");
            str.append("                                                                                                                                                                    ");
            str.append("             <tr>                                                                                                                                                   ");
            str.append("                <td align='center' bgcolor='#ffb45c' width='20%'><span class='td_title'>성명 / 직위</span></td>                                                        ");
            str.append("                <td align='left' width='80%'>" + StringUtils.defaultIfEmpty((String) retMailInfo.get("empJwNm"), "") + "</td>                                                                                                       ");
            str.append("             </tr>                                                                                                                                                  ");
            str.append("                                                                                                                                                                    ");
            str.append("             <tr>                                                                                                                                                   ");
            str.append("                <td align='center' bgcolor='#ffb45c' width='20%'><span class='td_title'>일시</span></td>                                                              ");
            str.append("                <td align='left'>" + StringUtils.defaultIfEmpty((String) retMailInfo.get("ofendDt"), "") + "</td>                                                                                                                    ");
            str.append("             </tr>                                                                                                                                                  ");
            str.append("                                                                                                                                                                    ");
            str.append("             <tr>                                                                                                                                                   ");
            str.append("                <td align='center' bgcolor='#ffb45c' width='20%'><span class='td_title'>장소</span></td>                                                              ");
            str.append("                <td align='left'>" + StringUtils.defaultIfEmpty((String) retMailInfo.get("actLocate"), "") + "</td>                                                                                                                  ");
            str.append("             </tr>                                                                                                                                                  ");
            str.append("                                                                                                                                                                    ");
            str.append("             <tr>                                                                                                                                                   ");
            str.append("                <td align='center' bgcolor='#ffb45c' width='20%'><span class='td_title'>위규 내용</span></td>                                                          ");
            str.append("                <td align='left'>" + StringUtils.defaultIfEmpty((String) retMailInfo.get("ofendGbnNm"), "") + "</td>                                                                                                                ");
            str.append("             </tr>                                                                                                                                                  ");
            str.append("                                                                                                                                                                    ");
            str.append("             <tr>                                                                                                                                                   ");
            str.append("                <td align='center' bgcolor='#ffb45c' width='20%'><span class='td_title'>제출기한</span></td>                                                           ");
            str.append("                <td align='left'>" + StringUtils.defaultIfEmpty((String) retMailInfo.get("limitDt"), "") + "</td>                                                                                                                    ");
            str.append("             </tr>                                                                                                                                                  ");
            str.append("                                                                                                                                                                    ");
            str.append("             <tr>                                                                                                                                                   ");
            str.append("                <td align='center' bgcolor='#ffb45c' width='20%'><span class='td_title'>위규 처리</span></td>                                                          ");
            str.append("                <td align='left'><span style='color:#f57724'>14일내 「" + strName + "」제출 <a href='https://welcome.skhynix.com'>(바로가기)</a></span></td>                       ");
            str.append("             </tr>                                                                                                                                                  ");
            str.append("                                                                                                                                                                    ");
            str.append("             <tr>                                                                                                                                                   ");
            str.append("                <td align='center' bgcolor='#ffb45c' width='20%'><span class='td_title'>주의 사항</span></td>                                                          ");
            str.append("                <td align='left'>                                                                                                                                   ");
            str.append("                   - 상시출입증이 있는 상시출입자는 " + strName + "를 14일내 미제출시 1개월 출입정지 처리함(상시출입증은 발급실에 반납)</br>                                                                 ");
            str.append("                   - 그외 방문객은 " + strName + " 제출시까지 방문예약 불가하며, 14일내 미제출시 1개월 출입정지 처리함                                                                                 ");
            str.append("                </td>                                                                                                                                               ");
            str.append("             </tr>                                                                                                                                                  ");
            str.append("                                                                                                                                                                    ");
            str.append("             <tr>                                                                                                                                                   ");
            str.append("                <td align='center' bgcolor='#ffb45c' width='20%'><span class='td_title'>반려 사유</span></td>                                                          ");
            str.append("                <td align='left'>                                                                                                                                   ");
            str.append("                   " + StringUtils.defaultIfEmpty((String) retMailInfo.get("cancelRsn"), "") + "                                                                                                                                    ");
            str.append("                </td>                                                                                                                                               ");
            str.append("             </tr>                                                                                                                                                  ");
            str.append("          </tbody>                                                                                                                                                  ");
            str.append("       </table>                                                                                                                                                     ");
            str.append("    </div>                                                                                                                                                          ");

            String sendToEmail = StringUtils.defaultIfEmpty((String) retMailInfo.get("emailAddr"), "");
            String empId = StringUtils.defaultIfEmpty((String) retMailInfo.get("ioEmpId"), "");
            String schemaNm = "IO_CORR_PLAN";
            String acIp = StringUtils.defaultIfBlank((String) paramMap.get("acIp"), "SYSTEM");
            // (String title, String content, String to, String empNo, String schemaNm, String docId, String acIp)
            // Call SendMail
            boolean result = mailing.sendMail(title, mailing.applyMailTemplate(title, str.toString()), sendToEmail, empId, schemaNm, StringUtils.defaultIfEmpty(String.valueOf(retMailInfo.get("scIoCorrPlanNo")), ""), acIp);
            //	    	System.out.println("# IoEmpCorrPlanRejectMail result : " + result);

        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
    }

    // 외부인 보안 위규자 상세 > 출입제한 상세 - offLimitsView
    @Override
    public Map<String, Object> offLimitsView(Map<String, Object> paramMap) {
        Map<String, Object> resultView = null;

        try {
            resultView = repository.offLimitsView(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultView;
    }

    // 외부인 보안 위규자  > 반려시 메일정보  - selectMailInfoForRejectMail
    @Override
    public Map<String, Object> selectMailInfoForRejectMail(Map<String, Object> paramMap) {
        Map<String, Object> resultView = null;

        try {
            resultView = repository.selectMailInfoForRejectMail(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultView;
    }

    // 외부인 보안 위규자 상세 > 출입제한 이력  - offLimitsHistoryList
    @Override
    public List<Map<String, Object>> offLimitsHistoryList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.offLimitsHistoryList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }
	
	
	
	/*
	// 출입제한 등록  >>>>>> [2022-02-04] Backup
	// 외부인 보안 위규자 상세 > 출입제한 이력 등록  - offLimitsHistoryInsert : insert 
	@Override
	public int offLimitsHistoryInsert(Map<String, Object> paramMap) {
		
		int updatedRows = 0;
		try {
			// 출입제한 이력 Sequence  - EQ_IO_EMP_DENY.NEXTVAL
			String seqIoEmpDeny =  repository.offLimitsIoEmpDenySeq(paramMap);
			paramMap.put("seqIoEmpDeny", seqIoEmpDeny); // 출입제한 이력 Sequence
			// 출입제한 이력 등록  - Insert IO_EMP_DENY
			updatedRows = repository.offLimitsHistoryInsert(paramMap);
			
			// 출입증 여부체크  - passMst2Check
			Map<String, Object> retMap = repository.passMst2Check(paramMap);
			String smartIdcard = StringUtils.defaultIfEmpty((String)retMap.get("smartIdcard"), "") ;
			String cardNo = StringUtils.defaultIfEmpty((String)retMap.get("cardNo"), "") ;
			
			
			if (!"".equals(smartIdcard)) {
				// 출입 외부 제한 이력 Sequence  - SEQ_IO_PASS_EXPR_HIST.NEXTVAL  AS EXPR_APPL_NO 
				String exprApplNo =  repository.offLimitsIoPassExprHistSeq(paramMap);
				paramMap.put("exprApplNo", exprApplNo); // 출입 외부 제한 이력 Sequence
				paramMap.put("smartIdcard", smartIdcard); // 스마트ID카드
				paramMap.put("cardNo", cardNo); // 스마트ID카드
				paramMap.put("scGbn", "S"); // S_C_GBN : 'S'
				
//				System.out.println("### offLimitsHistoryInsert offLimitsExprHistoryInsert  paramMap:" + paramMap.toString());
				// 출입 외부 제한 이력 등록  - INSERT INTO IO_PASS_EXPR_HIST 
				updatedRows = repository.offLimitsExprHistoryInsert(paramMap);
				
				// 운영계일 경우만 - 하이스텍 정지신청 하는 I/F Procedure 호출 - EXEC dbo.uSP_SK_IF_IO_PASS_EXPR_HIST 
				// @TODO - 운영계 반영시 하이스텍 proc 호출정보 확인 필요!!
				if (updatedRows> 0 && environment.acceptsProfiles(Profiles.of("prd"))) {
					paramMap.put("exprCode", (String)paramMap.get("exprGbn"));  // EXPR_CODE
//					System.out.println("### offLimitsHistoryInsert callOffLimitsIoPassExprHist  paramMap:" + paramMap.toString());
					
					updatedRows = repository.callOffLimitsIoPassExprHist(paramMap);
				}
			}
			
			
		} catch(Exception e) {
			throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
		}
		return updatedRows;		
	}
	*/

    // 출입제한 등록  >>>>>>
    // 외부인 보안 위규자 상세 > 출입제한 이력 등록  - offLimitsHistoryInsert : insert
    @Override
    public int offLimitsHistoryInsert(Map<String, Object> paramMap) {

        int updatedRows = 0;
        try {
            // 출입제한 이력 Sequence  - EQ_IO_EMP_DENY.NEXTVAL
            String seqIoEmpDeny = repository.offLimitsIoEmpDenySeq(paramMap);
            paramMap.put("seqIoEmpDeny", seqIoEmpDeny); // 출입제한 이력 Sequence
            // 출입제한 이력 등록  - Insert IO_EMP_DENY
            updatedRows = repository.offLimitsHistoryInsert(paramMap);

            System.out.println("## updatedRows :" + updatedRows);
            // 출입증 여부체크  - passMst2Check
            Map<String, Object> retMap = repository.passMst2Check(paramMap);
            // String smartIdcard = StringUtils.defaultIfEmpty((String)retMap.get("smartIdcard"), "") ;
            // String cardNo = StringUtils.defaultIfEmpty((String)retMap.get("cardNo"), "") ;
            String smartIdcard = "";
            String cardNo = "";
            //System.out.println("## retMap :" + retMap);

            if (!ObjectUtils.isEmpty(retMap)) {
                smartIdcard = ObjectUtils.isEmpty(retMap.get("smartIdcard"))
                    ? ""
                    : String.valueOf(retMap.get("smartIdcard"));
                cardNo = ObjectUtils.isEmpty(retMap.get("cardNo"))
                    ? ""
                    : String.valueOf(retMap.get("cardNo"));
            }

            if (!"".equals(smartIdcard)) {

                String[] siteList = { "HN", "HS", "SI" };
                // Loop Start
                for (int i = 0; i < siteList.length; i++) {
                    String siteType = siteList[i];
                    paramMap.put("siteType", siteType);
                    paramMap.put("smartIdcard", smartIdcard); // 스마트ID카드
                    paramMap.put("cardNo", cardNo); // 스마트ID카드
                    paramMap.put("scGbn", "S"); // S_C_GBN : 'S'

                    // Step_1) 출입 외부 제한 이력 Sequence  - SEQ_IO_PASS_EXPR_HIST.NEXTVAL  AS EXPR_APPL_NO
                    // Step_2) 출입 외부 제한 이력 등록  - INSERT INTO IO_PASS_EXPR_HIST
                    String exprApplNo = dmSecIoEmp_Violation_PassExprHist_I(paramMap);
                    paramMap.put("exprApplNo", exprApplNo); // 출입 외부 제한 이력 Sequence

                    // 운영계일 경우만 - 하이스텍 정지신청 하는 I/F Procedure 호출 - EXEC dbo.uSP_SK_IF_IO_PASS_EXPR_HIST
                    if (updatedRows > 0 && environment.acceptsProfiles(Profiles.of("prd"))) {
                        String exprGbn = ObjectUtils.isEmpty(paramMap.get("exprGbn"))
                            ? ""
                            : String.valueOf(paramMap.get("exprGbn"));
                        paramMap.put("exprCode", exprGbn);  // EXPR_CODE
                        // Step_3) 하이스텍 정지신청 하는 I/F Procedure 호출 - EXEC dbo.uSP_SK_IF_IO_PASS_EXPR_HIST
                        // 주석처리 2023-06-09
                        //						updatedRows = idCardVisit.callOffLimitsIoPassExprHist(paramMap);
                    }
                } // End for
            } // End If
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return updatedRows;
    }

    // 외부인 보안 위규자 상세 > 출입제한 이력 등록
    // Step_1) 출입 외부 제한 이력 Sequence  - SEQ_IO_PASS_EXPR_HIST.NEXTVAL  AS EXPR_APPL_NO
    // Step_2) 출입 외부 제한 이력 등록  - INSERT INTO IO_PASS_EXPR_HIST
    private String dmSecIoEmp_Violation_PassExprHist_I(Map<String, Object> paramMap) {

        Map<String, Object> result = new HashMap<String, Object>();

        String siteType = StringUtils.defaultIfEmpty((String) paramMap.get("siteType"), "");
        int rsSeq = 0;
        int resultInt = 0;
        String exprApplNo = "";
        System.out.println("## dmSecIoEmp_Violation_PassExprHist_I paramMap:" + paramMap.toString());
        System.out.println("## dmSecIoEmp_Violation_PassExprHist_I siteType:" + siteType);

        /*외부DB연동 부분 */
        if ("HS".equals(siteType)) {

            // 출입 외부 제한 이력 Sequence  - SEQ_IO_PASS_EXPR_HIST.NEXTVAL  AS EXPR_APPL_NO
            // 주석처리 2023-06-09
            //	    	exprApplNo = hsDS.offLimitsIoPassExprHistSeq(paramMap);

            paramMap.put("exprApplNo", exprApplNo);
            result.put("exprApplNo", exprApplNo);

            // 출입 외부 제한 이력 등록  - INSERT INTO IO_PASS_EXPR_HIST
            // 주석처리 2023-06-09
            //			resultInt += hsDS.offLimitsExprHistoryInsert(paramMap);

        }
        else if ("SI".equals(siteType)) {

            // 출입 외부 제한 이력 Sequence  - SEQ_IO_PASS_EXPR_HIST.NEXTVAL  AS EXPR_APPL_NO
            //	    	exprApplNo = siDS.offLimitsIoPassExprHistSeq(paramMap);
            //
            //	    	paramMap.put("exprApplNo", exprApplNo);
            //			result.put("exprApplNo", exprApplNo);
            //
            //			// 출입 외부 제한 이력 등록  - INSERT INTO IO_PASS_EXPR_HIST
            //			resultInt += siDS.offLimitsExprHistoryInsert(paramMap);
        }
        else {
            // 출입 외부 제한 이력 Sequence  - SEQ_IO_PASS_EXPR_HIST.NEXTVAL  AS EXPR_APPL_NO
            exprApplNo = repository.offLimitsIoPassExprHistSeq(paramMap);

            paramMap.put("exprApplNo", exprApplNo);
            result.put("exprApplNo", exprApplNo);

            // 출입 외부 제한 이력 등록  - INSERT INTO IO_PASS_EXPR_HIST
            resultInt += repository.offLimitsExprHistoryInsert(paramMap);
        }
        return exprApplNo;
    }

    // 출입제한 해제  >>>>>>
    // 외부인 보안 위규자 상세 > 출입제한 이력 해제  - offLimitsHistoryDelete : insert
    @Override
    public int offLimitsHistoryDelete(Map<String, Object> paramMap) {

        int updatedRows = 0;
        try {
            String denyCtgCd = StringUtils.defaultIfEmpty((String) paramMap.get("denyGbn"), "");

            // 외부인 보안 위규자 상세 > 출입 제한 이력 해제 - UPDATE IO_EMP_DENY
			/*
			 * UPDATE IO_EMP_DENY
			  SET   
			<if test="@org.springframework.util.StringUtils@isEmpty(denyCancelEndDt)">
				DEL_YN = 'Y',
			</if>
			<if test="not @org.springframework.util.StringUtils@isEmpty(denyCancelEndDt)">
				DENY_END_DT = #{denyCancelEndDt},
			</if>
			<if test="not @org.springframework.util.StringUtils@isEmpty(denyCancelRsn)">
				DENY_RSN = #{denyCancelRsn},
			</if>	 
			    MOD_BY = #{modBy}
			    MOD_DTM = SYSDATE
			WHERE  IO_EMP_ID       = #{ioEmpId}
			AND    DENY_NO		   = #{denyNo}
			 */
            updatedRows = repository.offLimitsHistoryUpdate(paramMap);

            // 외부인 보안 위규자 > 출입 제한 MAX(SMART_IDCARD) - offLimitsGetCardNo - return String IDCARD
			/*
			 SELECT
					NVL(MAX(SMART_IDCARD),'----------') as IDCARD
				FROM
					VW_IO_PASS_MST2 A
				WHERE
					A.IO_EMP_ID = #{ioEmpId}
			 */
            String idCardNo = repository.offLimitsGetCardNo(paramMap);

            /** 2021-02-02 채수억 : 출입관리 > 방문객관리(제한관리) > 상세페이지 > 코로나 해지시 IDCARD와 인터페이스 하도록 추가  **/
            if ("A0460015".equals(denyCtgCd) && StringUtils.isNotEmpty(idCardNo)) {

                // 출입 외부 제한 이력 Sequence  - SEQ_IO_PASS_EXPR_HIST.NEXTVAL  AS EXPR_APPL_NO
                String exprApplNo = repository.offLimitsIoPassExprHistSeq(paramMap);
                //---------- 하이스텍 정지 하는 I/F Procedure 호출해야 함 ----------//
                if (environment.acceptsProfiles(Profiles.of("prd"))) {
                    // 운영계일 경우만 - 하이스텍 정지신청 하는 I/F Procedure 호출 - EXEC dbo.uSP_SK_IF_IO_PASS_EXPR_HIST
					/*
					 EXEC [dbo].[uSP_SK_IF_IO_PASS_EXPR_HIST] 
					@EXPR_APPL_NO =#{exprApplNo}
					,@S_C_GBN=#{scGbn}
					,@IDCARD_ID=#{smartIdcard}
					,@EXPR_CODE=#{exprCode}
					<if test='"SI".equals(siteType)'>
					,@PTYPE='8'
					</if>
					 */
                    paramMap.put("exprApplNo", exprApplNo);
                    paramMap.put("scGbn", "C"); // 정지  해제
                    paramMap.put("smartIdcard", idCardNo);
                    paramMap.put("exprCode", (String) paramMap.get("exprGbn"));  // EXPR_CODE
                    //					updatedRows = idCardVisit.callOffLimitsIoPassExprHist(paramMap);
                }
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return updatedRows;
    }

    // 전사 비문 분류 기준표 조회  - secrtDocDistItemList
    @Override
    public List<Map<String, Object>> secrtDocDistItemList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.secrtDocDistItemList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    // 전사 비문 분류 기준표 조회  엑셀다운 - secrtDocDistItemListExcel
    @Override
    public CommonXlsViewDTO secrtDocDistItemListExcel(Map<String, Object> paramMap) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        CommonXlsViewDTO commonXlsViewDTO = new CommonXlsViewDTO();
        commonXlsViewDTO.setFileName("비문분류기준표" + "_" + sdf.format(new Date()));

        try {
            // set header names
            String[] headerNameArr = { "문서번호", "캠퍼스", "부서명", "사번", "성명", "직급", "등록일자", "결재상태", "구분", "비밀", "대외비", "PL부서명" };
            commonXlsViewDTO.setHeaderNameArr(headerNameArr);

            // set column names (data field name)
            String[] columnNameArr = { "scDocDistId", "compNm", "deptNm", "empId", "empNm", "jwNm", "regDt", "apprResultNm", "gubun1", "gubun2", "gubun3", "plDeptNm" };
            commonXlsViewDTO.setColumnNameArr(columnNameArr);

            // set column width
            Integer[] columnWidthArr = { 5000, 5000, 8000, 5000, 5000, 5000, 5000, 5000, 8000, 8000, 8000, 5000 };
            commonXlsViewDTO.setColumnWidthArr(columnWidthArr);

            List<Map<String, Object>> list = repository.secrtDocDistItemListExcel(paramMap);

            // set excel data
            commonXlsViewDTO.setDataList(list);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return commonXlsViewDTO;
    }

    // 전사 비문 분류 기준표 상세 - secrtDocDistView
    @Override
    public Map<String, Object> secrtDocDistView(Map<String, Object> paramMap) {
        Map<String, Object> resultView = null;

        try {
            resultView = repository.secrtDocDistView(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultView;
    }

    // 전사 비문 분류 기준표 상세 비문분류기준표 List - secrtDocItemViewList
    @Override
    public List<Map<String, Object>> secrtDocItemViewList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.secrtDocItemViewList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    // 보안 담당자 변경 조회 - secrtChangeList
    @Override
    public List<Map<String, Object>> secrtChangeList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.secrtChangeList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    // 보안 담당자 변경 상세 - secrtChangeView
    @Override
    public Map<String, Object> secrtChangeView(Map<String, Object> paramMap) {
        Map<String, Object> resultView = null;

        try {
            resultView = repository.secrtChangeView(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultView;
    }

    // 보안 위규 담당자 관리 조회 - secDetlEmpList
    // 보안 위규 담당자 관리 조회(모바일포렌직) - secDetlMobileForEmpList
    @Override
    public ListDTO<Map<String, Object>> secDetlEmpList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;
        Integer totalCount = 0;

        try {
            String searchMobileForGbn = StringUtils.defaultIfEmpty((String) paramMap.get("searchMobileForGbn"), "N");
            // 모바일포렌직 여부
            if ("Y".equals(searchMobileForGbn)) {
                resultList = repository.secDetlMobileForEmpList(paramMap);
                totalCount = repository.secDetlMobileForEmpListCnt(paramMap);
            }
            else {
                resultList = repository.secDetlEmpList(paramMap);
                totalCount = repository.secDetlEmpListCnt(paramMap);
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return ListDTO.getInstance(resultList, totalCount);
    }

    // 보안 위규 담당자 관리 상세정보 - secDetlEmpView
    @Override
    public Map<String, Object> secDetlEmpView(Map<String, Object> paramMap) {
        Map<String, Object> resultView = null;

        try {
            resultView = repository.secDetlEmpView(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultView;
    }

    // 보안 위규 담당자 관리 상세 > 저장  - secDetlEmpInsert
    @Override
    public int secDetlEmpInsert(Map<String, Object> paramMap) {

        int updatedRows = 0;
        try {
            // 보안 위규 담당자 Insert/update (SC_DETL_EMP)
            updatedRows = repository.secDetlEmpInsert(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return updatedRows;
    }

    // 팀내 생활보안점검 조회(관) 조회 - coEmpTeamViolationList
    @Override
    public List<Map<String, Object>> coEmpTeamViolationList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.coEmpTeamViolationList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    // 팀내 생활보안점검 조회(관)조회 엑셀다운로드 - coEmpTeamViolationListExcel
    @Override
    public CommonXlsViewDTO coEmpTeamViolationListExcel(Map<String, Object> paramMap) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        CommonXlsViewDTO commonXlsViewDTO = new CommonXlsViewDTO();
        commonXlsViewDTO.setFileName("구성원팀내생활보안점검위규자" + "_" + sdf.format(new Date()));

        try {
            // set header names
            String[] headerNameArr = { "점검일", "점검시각", "부서 Level 2", "부서 Level 3", "부서 Level 4", "부서 Level 5", "부서", "성명", "사번", "직위", "위규구분", "위규상세구분", "점검자 사번", "점검자 성명" };
            commonXlsViewDTO.setHeaderNameArr(headerNameArr);

            // set column names (data field name)
            String[] columnNameArr = { "ofendDt", "ofendTm", "deptLv2Nm", "deptLv3Nm", "deptLv4Nm", "deptLv5Nm", "ofendDeptNm", "ofendEmpNm", "ofendEmpId", "ofendJwNm", "ofendGbnNm", "ofendDetailGbnNm", "empId", "regEmpNm" };
            commonXlsViewDTO.setColumnNameArr(columnNameArr);

            // set column width
            Integer[] columnWidthArr = { 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000 };
            commonXlsViewDTO.setColumnWidthArr(columnWidthArr);

            List<Map<String, Object>> list = repository.coEmpTeamViolationListExcel(paramMap);

            // set excel data
            commonXlsViewDTO.setDataList(list);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return commonXlsViewDTO;
    }

    // 팀내 생활보안점검 조회(관)상세 - coEmpTeamViolationView
    @Override
    public Map<String, Object> coEmpTeamViolationView(Map<String, Object> paramMap) {
        Map<String, Object> resultView = null;

        try {
            resultView = repository.coEmpTeamViolationView(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultView;
    }

    // 팀내 생활보안점검 조회(관)상세 이력조회 - coEmpTeamViolationHist
    @Override
    public List<Map<String, Object>> coEmpTeamViolationHist(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.coEmpTeamViolationHist(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    // 팀내 생활보안점검 조회(관)상세 삭제  - coEmpTeamViolationDelete
    @Override
    public int coEmpTeamViolationDelete(Map<String, Object> paramMap) {

        int updatedRows = 0;
        try {
            updatedRows = repository.coEmpTeamViolationDelete(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return updatedRows;
    }

    // 팀내 생활보안점검 조회(관) 일괄삭제  - coEmpTeamViolationDel
    @Override
    public Boolean coEmpTeamViolationDel(HashMap<String, Object> paramMap) {
        boolean result = false;

        try {

            repository.coEmpTeamViolationDel(paramMap);

            // 처리완료
            result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    // 보안 위규자 조회권한 조회 - secrtOfendAuthList
    @Override
    public List<Map<String, Object>> secrtOfendAuthList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.secrtOfendAuthList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    // 보안 위규자 조회권한 상세정보 List - secrtOfendAuthViewList
    @Override
    public List<Map<String, Object>> secrtOfendAuthViewList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.secrtOfendAuthViewList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    // 보안 위규자 권한 Update - secrtOfendAuthUpdate
    @Override
    public int secrtOfendAuthUpdate(Map<String, Object> paramMap) {

        int updatedRows = 0;
        try {

            String rEmpId = StringUtils.defaultIfEmpty(String.valueOf(paramMap.get("empId")), "");
            String rCrtBy = StringUtils.defaultIfEmpty(String.valueOf(paramMap.get("crtBy")), "");
            String rAcIp = StringUtils.defaultIfEmpty(String.valueOf(paramMap.get("acIp")), "");
            if (!"".equals(rEmpId)) {
                // Step1. 보안 위규자 권한 삭제 - secrtOfendAuthDelete (ASIS : dmSecrtOfendAuth_D )
                updatedRows = repository.secrtOfendAuthDelete(rEmpId);
                // Step2. 보안 위규자 직원 권한 삭제 - coEmpAuthDelete (ASIS : dmAuth_D )
                updatedRows += repository.coEmpAuthDelete(rEmpId);

                List<Map<String, Object>> arrItems = (List<Map<String, Object>>) paramMap.get("arrItems");
                // Loop Start
                int i = 0;
                for (Map<String, Object> objItem : arrItems) {
                    Map<String, Object> objMap = new HashMap<String, Object>();

                    objMap.put("empId", rEmpId); // 직원ID
                    objMap.put("crtBy", rCrtBy); // 생성자ID
                    objMap.put("acIp", rAcIp); // IP
                    objMap.put("deptId", String.valueOf(objItem.get("deptId"))); // 부서코드

                    if (i == 0) {
                        // Step3. 보안 위규자 직원 권한 Insert - coEmpAuthInsert (ASIS : dmAuth_I )
                        updatedRows += repository.coEmpAuthInsert(objMap);
                    }
                    // Step4. 보안 위규자 권한 Insert - secrtOfendAuthInsert (ASIS : dmSecrtOfendAuth_I )
                    updatedRows += repository.secrtOfendAuthInsert(objMap);
                    i++;
                } // Loop End
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return updatedRows;
    }

    // 보안 위규자 권한 Insert - secrtOfendAuthInsert
    @Override
    public int secrtOfendAuthInsert(Map<String, Object> paramMap) {

        int updatedRows = 0;
        try {

            String rCrtBy = StringUtils.defaultIfEmpty(String.valueOf(paramMap.get("crtBy")), "");
            String rAcIp = StringUtils.defaultIfEmpty(String.valueOf(paramMap.get("acIp")), "");

            @SuppressWarnings("unchecked")
            List<Map<String, Object>> arrItems = (List<Map<String, Object>>) paramMap.get("arrItems");
            // Loop Start
            int i = 0;
            for (Map<String, Object> objItem : arrItems) {
                Map<String, Object> objMap = new HashMap<String, Object>();

                String rEmpId = StringUtils.defaultIfEmpty(String.valueOf(objItem.get("empId")), "");
                objMap.put("empId", String.valueOf(rEmpId)); // 직원ID
                objMap.put("deptId", String.valueOf(objItem.get("deptId"))); // 부서코드
                objMap.put("crtBy", rCrtBy); // 생성자ID
                objMap.put("acIp", rAcIp); // IP

                if (i == 0) {
                    // Step1. 보안 위규자 직원 권한 삭제 - coEmpAuthDelete (ASIS : dmAuth_D )
                    updatedRows += repository.coEmpAuthDelete(rEmpId);
                    // Step2. 보안 위규자 직원 권한 Insert - coEmpAuthInsert (ASIS : dmAuth_I )
                    updatedRows += repository.coEmpAuthInsert(objMap);
                }
                // Step3. 보안 위규자 권한 Insert - secrtOfendAuthInsert (ASIS : dmSecrtOfendAuth_I )
                updatedRows += repository.secrtOfendAuthInsert(objMap);
                i++;
            } // Loop End
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return updatedRows;
    }

    // 보안교육이수율 관리 조회 - securityTrainingList
    @Override
    public List<Map<String, Object>> securityTrainingList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.securityTrainingList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    // 보안교육이수율 관리 저장  - securityTrainingInsert
    @Override
    public int securityTrainingInsert(Map<String, Object> paramMap) {

        int updatedRows = 0;
        try {
            updatedRows = repository.securityTrainingInsert(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return updatedRows;
    }

    // 보안교육이수율 관리 수정  - securityTrainingUpdate
    @Override
    public int securityTrainingUpdate(Map<String, Object> paramMap) {

        int updatedRows = 0;
        try {
            updatedRows = repository.securityTrainingUpdate(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return updatedRows;
    }

    // 보안교육이수율 관리 삭제  - securityTrainingDelete
    @Override
    public int securityTrainingDelete(Map<String, Object> paramMap) {

        int updatedRows = 0;
        try {
            String rCrtBy = StringUtils.defaultIfEmpty(String.valueOf(paramMap.get("crtBy")), "");

            @SuppressWarnings("unchecked")
            List<Map<String, Object>> arrItems = (List<Map<String, Object>>) paramMap.get("arrItems");
            // Loop Start
            int i = 0;
            for (Map<String, Object> objItem : arrItems) {

                Map<String, Object> objMap = new HashMap<String, Object>();

                String rStndYyMm = StringUtils.defaultIfEmpty(String.valueOf(objItem.get("stndYyMm")), "");
                objMap.put("stndYyMm", rStndYyMm); // 기준년월
                objMap.put("crtBy", rCrtBy); // 생성자ID

                // 보안교육이수율 관리 삭제  - securityTrainingDelete
                updatedRows += repository.securityTrainingDelete(objMap);
            } // Loop End
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return updatedRows;
    }

    // 보안교육이수율 관리 상세 - securityTrainingView
    @Override
    public Map<String, Object> securityTrainingView(Map<String, Object> paramMap) {
        Map<String, Object> resultView = null;

        try {
            resultView = repository.securityTrainingView(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultView;
    }

    // 보안점수 기준정보 조회 - secureEvalItemAdminList
    @Override
    public List<Map<String, Object>> secureEvalItemAdminList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.secureEvalItemAdminList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    // 보안점수 기준정보 Insert/Update - secureEvalItemInsert
    @Override
    public int secureEvalItemInsert(Map<String, Object> paramMap) {

        int updatedRows = 0;
        try {
            String itemSeq = ObjectUtils.isEmpty(paramMap.get("itemSeq"))
                ? ""
                : String.valueOf(paramMap.get("itemSeq"));
            //			System.out.println("##### paramMap:"+ paramMap.toString());
            //			System.out.println("##### itemSeq:"+ itemSeq);

            // itemSeq가 없을 경우 생성(신규입력일 경우)
            if ("".equals(itemSeq)) {
                // 보안점수 기준정보 순번 생성 - secureEvalItemSeq
                itemSeq = repository.secureEvalItemSeq(paramMap);
                paramMap.put("itemSeq", itemSeq);
            }
            updatedRows = repository.secureEvalItemInsert(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return updatedRows;
    }

    // 보안점수 기준정보 상세 - secureEvalItemView
    @Override
    public Map<String, Object> secureEvalItemView(Map<String, Object> paramMap) {
        Map<String, Object> resultView = null;

        try {
            resultView = repository.secureEvalItemView(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultView;
    }

    // 보안점수 기준정보  삭제 - secureEvalItemDelete
    @Override
    public int secureEvalItemDelete(Map<String, Object> paramMap) {

        int updatedRows = 0;
        try {
            updatedRows = repository.secureEvalItemDelete(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return updatedRows;
    }

    // 보안점수 기준정보 > 기존 항목 추가 - secureEvalPeriodRefInsert
    @Override
    public int secureEvalPeriodRefInsert(Map<String, Object> paramMap) {

        int updatedRows = 0;
        try {
            updatedRows = repository.secureEvalPeriodRefInsert(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return updatedRows;
    }

    /**
     * 보안점수 기준정보 > 평가부서 Insert - secureEvalDeptMappingInsert
     * Step01. 보안점수 기준정보 > 기존 평가부서 조회 - secureEvalDeptMappingList
     * Step02. 보안점수 기준정보 > 기존 평가부서 삭제 - secureEvalDeptMappingDelete
     * Step03. 보안점수 기준정보 > 평가부서 Insert - secureEvalDeptMappingInsert
     */
    @Override
    public int secureEvalDeptMappingInsert(Map<String, Object> paramMap) {

        int updatedRows = 0;
        try {
            // Step01. 보안점수 기준정보 > 기존 평가부서 조회 - secureEvalDeptMappingList
            List<Map<String, Object>> retDeptList = repository.secureEvalDeptMappingList(paramMap);

            // Step02. 보안점수 기준정보 > 기존 평가부서 삭제 - secureEvalDeptMappingDelete
            int deleteRows = repository.secureEvalDeptMappingDelete(paramMap);
            System.out.println("## secureEvalDeptMappingInsert deleteRows:" + deleteRows);

            List<Map<String, Object>> arrItems = (List<Map<String, Object>>) paramMap.get("arrItems");
            // Loop Start
            int i = 0;
            for (Map<String, Object> objItem : arrItems) {

                String rDeptId = ObjectUtils.isEmpty(objItem.get("deptId"))
                    ? ""
                    : String.valueOf(objItem.get("deptId"));
                if (!"".equals(rDeptId)) {

                    Map<String, Object> objMap = new HashMap<String, Object>();

                    objMap.put("evalYear", String.valueOf(paramMap.get("evalYear"))); // 년도
                    objMap.put("evalGb", String.valueOf(paramMap.get("evalGb"))); // 반기구분
                    objMap.put("deptId", rDeptId); // 부서코드

                    String totalScore = "0"; // 총점 default
                    String confirmYn = "X"; // 확인여부 default
                    // 기존평가부서 정보가져오기 (totalScore, confirmYn)
                    for (Map<String, Object> objDept : retDeptList) {
                        if (rDeptId.equals(String.valueOf(objDept.get("deptId")))) {
                            totalScore = ObjectUtils.isEmpty(objDept.get("totalScore"))
                                ? ""
                                : String.valueOf(objDept.get("totalScore"));
                            confirmYn = ObjectUtils.isEmpty(objDept.get("confirmYn"))
                                ? ""
                                : String.valueOf(objDept.get("confirmYn"));
                        }
                    }
                    objMap.put("totalScore", totalScore); // 총점
                    objMap.put("confirmYn", confirmYn); // 확인여부

                    // Step03. 보안점수 기준정보 > 평가부서 Insert - secureEvalDeptMappingInsert
                    updatedRows += repository.secureEvalDeptMappingInsert(objMap);
                    i++;
                }
            }// Loop End
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return updatedRows;
    }

    // 보안점수 기준정보 > 평가부서 Tree List - secureEvalDeptTreeList
    @Override
    public List<Map<String, Object>> secureEvalDeptTreeList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.secureEvalDeptTreeList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    // 전사 팀별 보안담당자 조회(전사) - secrtDeptSecDeputyList
    // 전사 팀별 보안담당자 조회(RM부서) - secrtDeptSecDeputyRmList
    @Override
    public List<Map<String, Object>> secrtDeptSecDeputyList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {

            String rApplStat = ObjectUtils.isEmpty(paramMap.get("applStat"))
                ? ""
                : String.valueOf(paramMap.get("applStat"));
            if ("1".equals(rApplStat)) {
                // RM부서 일 경우
                resultList = repository.secrtDeptSecDeputyRmList(paramMap);
            }
            else {
                // 전사 일 경우
                resultList = repository.secrtDeptSecDeputyList(paramMap);
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    // 전사 팀별 보안담당자 조회(전사)엑셀다운 - secrtDeptSecDeputyListExcel
    // 전사 팀별 보안담당자 조회(RM부서) 엑셀다운 - secrtDeptSecDeputyRmListExcel
    @Override
    public CommonXlsViewDTO secrtDeptSecDeputyListExcel(Map<String, Object> paramMap) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        CommonXlsViewDTO commonXlsViewDTO = new CommonXlsViewDTO();
        commonXlsViewDTO.setFileName("팀별 보안담당자 현황" + "_" + sdf.format(new Date()));

        try {
            // set header names
            String[] headerNameArr = { "본부명", "팀명", "보안담당자사번", "보안담당자성명/직위", "보안담당자사내전화번호"
                , "승인일", "관리자 사번", "관리자성명/직위", "관리자사내전화번호", "담당사번", "그룹장성명/직위", "그룹장사내전화번호" };
            commonXlsViewDTO.setHeaderNameArr(headerNameArr);

            // set column names (data field name)
            String[] columnNameArr = { "grpDeptNm", "deptNm", "empId", "empJwNm", "empTelNo"
                , "crtDt", "teamEmpId", "teamEmpJwNm", "teamEmpTelNo", "grpEmpId", "grpEmpJwNm", "grpEmpTelNo" };
            commonXlsViewDTO.setColumnNameArr(columnNameArr);

            // set column width
            Integer[] columnWidthArr = { 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000 };
            commonXlsViewDTO.setColumnWidthArr(columnWidthArr);

            List<Map<String, Object>> list = null;
            String rApplStat = ObjectUtils.isEmpty(paramMap.get("applStat"))
                ? ""
                : String.valueOf(paramMap.get("applStat"));
            if ("1".equals(rApplStat)) {
                // RM부서 일 경우
                list = repository.secrtDeptSecDeputyRmListExcel(paramMap);
            }
            else {
                // 전사 일 경우
                list = repository.secrtDeptSecDeputyListExcel(paramMap);
            }

            // set excel data
            commonXlsViewDTO.setDataList(list);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return commonXlsViewDTO;
    }

    // 문서자가점검 이력 조회 - secrtDocSelfCheckList
    @Override
    public List<Map<String, Object>> secrtDocSelfCheckList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.secrtDocSelfCheckList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    // 문서자가점검 이력 조회 엑셀다운 - secrtDocSelfCheckListExcel
    @Override
    public CommonXlsViewDTO secrtDocSelfCheckListExcel(Map<String, Object> paramMap) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        CommonXlsViewDTO commonXlsViewDTO = new CommonXlsViewDTO();
        commonXlsViewDTO.setFileName("문서 자가점검 현황" + "_" + sdf.format(new Date()));

        try {
            // set header names
            String[] headerNameArr = { "그룹/실", "팀/PL", "사번", "성명", "직위", "자가점검일시" };
            commonXlsViewDTO.setHeaderNameArr(headerNameArr);

            // set column names (data field name)
            String[] columnNameArr = { "deptLv4", "deptNm", "empId", "empNm", "jwNm", "crtDtm" };
            commonXlsViewDTO.setColumnNameArr(columnNameArr);

            // set column width
            Integer[] columnWidthArr = { 5000, 5000, 5000, 5000, 5000, 5000 };
            commonXlsViewDTO.setColumnWidthArr(columnWidthArr);

            List<Map<String, Object>> list = repository.secrtDocSelfCheckListExcel(paramMap);

            // set excel data
            commonXlsViewDTO.setDataList(list);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return commonXlsViewDTO;
    }

    // 개인정보 위탁업체 교육 이수현황 조회 - securityEduNoticeList
    @Override
    public List<Map<String, Object>> securityEduNoticeList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.securityEduNoticeList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    // 개인정보 위탁업체 교육 이수현황 조회 엑셀다운  - securityEduNoticeListExcel
    @Override
    public CommonXlsViewDTO securityEduNoticeListExcel(Map<String, Object> paramMap) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        CommonXlsViewDTO commonXlsViewDTO = new CommonXlsViewDTO();
        commonXlsViewDTO.setFileName("개인정보 위탁업체 교육이수 현황" + "_" + sdf.format(new Date()));

        try {
            // set header names
            String[] headerNameArr = { "교육이수일", "교육명(게시글번호)", "ID", "교육대상자", "직위", "소속업체" };
            commonXlsViewDTO.setHeaderNameArr(headerNameArr);

            // set column names (data field name)
            String[] columnNameArr = { "eduCompDtm", "title", "ioEmpId", "ioEmpNm", "jwNm", "compNm" };
            commonXlsViewDTO.setColumnNameArr(columnNameArr);

            // set column width
            Integer[] columnWidthArr = { 5000, 15000, 5000, 5000, 5000, 10000 };
            commonXlsViewDTO.setColumnWidthArr(columnWidthArr);

            List<Map<String, Object>> list = repository.securityEduNoticeListExcel(paramMap);

            // set excel data
            commonXlsViewDTO.setDataList(list);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return commonXlsViewDTO;
    }

    // 모바일 포렌직서약서 조회 - securityMobilePledgeSignList
    @Override
    public List<Map<String, Object>> securityMobilePledgeSignList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.securityMobilePledgeSignList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    // 모바일 포렌직서약서 상세 - securityMobilePledgeSignView
    @Override
    public Map<String, Object> securityMobilePledgeSignView(Map<String, Object> paramMap) {
        Map<String, Object> resultView = null;

        try {
            resultView = repository.securityMobilePledgeSignView(paramMap);

            if (resultView != null && ObjectUtils.isNotEmpty(resultView)) {
                /* Blob type image 전달  */
                // pldgNameFile -> cvtPldgNameFile
                String cvtPldgNameFile = "";
                Object objPldgNameFile = ObjectUtils.isEmpty(resultView.get("pldgNameFile"))
                    ? ""
                    : resultView.get("pldgNameFile");
                if (!"".equals(objPldgNameFile)) {
                    byte[] arrBytePldgNameFile = SerializationUtils.serialize((Serializable) objPldgNameFile); // or convertObjectToBytes(objPldgNameFile);
                    // Array 27 to length
                    if (arrBytePldgNameFile.length > 27) {
                        byte[] rangeBytePldgNameFile = Arrays.copyOfRange(arrBytePldgNameFile, 27, arrBytePldgNameFile.length);
                        cvtPldgNameFile = new String(rangeBytePldgNameFile);
                        // System.out.println("### arrBytePldgNameFile length: " + arrBytePldgNameFile.length);
                        // System.out.println("### arrBytePldgNameFile toString: " + Arrays.toString(arrBytePldgNameFile));
                        // System.out.println("### arrBytePldgNameFile 27: " + arrBytePldgNameFile[27]);
                        // System.out.println("### arrBytePldgNameFile 27: " + new String(rangeBytePldgNameFile));
                    }
                }
                resultView.put("CVT_PLDG_NAME_FILE", cvtPldgNameFile);

                /* Blob type image 전달  */
                // pldgSignFile -> cvtPldgSignFile
                String cvtPldgSignFile = "";
                Object objPldgSignFile = ObjectUtils.isEmpty(resultView.get("pldgSignFile"))
                    ? ""
                    : resultView.get("pldgSignFile");
                if (!"".equals(objPldgSignFile)) {
                    byte[] arrBytePldgSignFile = SerializationUtils.serialize((Serializable) objPldgSignFile); // or convertObjectToBytes(objPldgSignFile);
                    // Array 27 to length
                    if (arrBytePldgSignFile.length > 27) {
                        byte[] rangeBytePldgSignFile = Arrays.copyOfRange(arrBytePldgSignFile, 27, arrBytePldgSignFile.length);
                        // System.out.println("### arrBytePldgSignFile length: " + arrBytePldgSignFile.length);
                        // System.out.println("### arrBytePldgSignFile toString: " + Arrays.toString(arrBytePldgSignFile));
                        // System.out.println("### arrBytePldgSignFile 27: " + arrBytePldgSignFile[27]);
                        // System.out.println("### arrBytePldgSignFile 27: " + new String(rangeBytePldgSignFile));
                        cvtPldgSignFile = new String(rangeBytePldgSignFile);
                    }
                }
                resultView.put("CVT_PLDG_SIGN_FILE", cvtPldgSignFile);
            } // End if
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultView;
    }

    // Convert object to byte[]
    public static byte[] convertObjectToBytes(Object obj) {
        ByteArrayOutputStream boas = new ByteArrayOutputStream();
        try (ObjectOutputStream ois = new ObjectOutputStream(boas)) {
            ois.writeObject(obj);
            return boas.toByteArray();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        throw new RuntimeException();
    }

    // 정보보호서약서 조회 - securityPledgeList
    @Override
    public List<Map<String, Object>> securityPledgeList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.securityPledgeList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    // 정보보호서약서 조회 엑셀다운 - securityPledgeListExcel
    @Override
    public CommonXlsViewDTO securityPledgeListExcel(Map<String, Object> paramMap) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        CommonXlsViewDTO commonXlsViewDTO = new CommonXlsViewDTO();
        commonXlsViewDTO.setFileName("정보보호서약서 현황" + "_" + sdf.format(new Date()));

        try {
            // set header names
            String[] headerNameArr = { "NO", "년도", "부서", "사번", "성명", "직위", "서약현황", "서약일", "메일발송여부", "최종메일발송일시" };
            commonXlsViewDTO.setHeaderNameArr(headerNameArr);

            // set column names (data field name)
            String[] columnNameArr = { "rowNum", "pledgeYear", "deptNm", "empId", "empNm", "jwNm", "pledgeYn", "pledgeDt", "mailSendYn", "sendDtm" };
            commonXlsViewDTO.setColumnNameArr(columnNameArr);

            // set column width
            Integer[] columnWidthArr = { 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000 };
            commonXlsViewDTO.setColumnWidthArr(columnWidthArr);

            List<Map<String, Object>> list = repository.securityPledgeListExcel(paramMap);

            // set excel data
            commonXlsViewDTO.setDataList(list);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return commonXlsViewDTO;
    }

    // 정보보호서약서 - 메일발송 목록저장 - updateSecurityPledgeMailList
    // 정보보호서약서 - 메일본문(코드정보) 저장 - updateMailInfo
    @Override
    public int updateSecurityPledgeMailList(Map<String, Object> paramMap) {

        int updatedRows = 0;
        try {
            // Step01. 메일본문(코드정보)저장 - updateMailInfo
            Map<String, Object> objMap = new HashMap<String, Object>();
            objMap.put("detlCd", "C0790001");
            objMap.put("detlNm", paramMap.get("mailDatetime"));
            updatedRows = repository.updateMailInfo(objMap);
            objMap.clear();
            objMap.put("detlCd", "C0790002");
            objMap.put("detlNm", paramMap.get("mailDesk"));
            updatedRows = repository.updateMailInfo(objMap);

            // Step02. 메일발송 목록저장 - updateSecurityPledgeMailList
            updatedRows += repository.updateSecurityPledgeMailList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return updatedRows;
    }

    // 전사 보안담당자 관리 조회 - secrtChangeAdminList
    @Override
    public List<Map<String, Object>> secrtChangeAdminList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.secrtChangeAdminList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    // 전사 보안담당자 관리 엑셀다운 - secrtChangeAdminListExcel
    @Override
    public CommonXlsViewDTO secrtChangeAdminListExcel(Map<String, Object> paramMap) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        CommonXlsViewDTO commonXlsViewDTO = new CommonXlsViewDTO();
        commonXlsViewDTO.setFileName("전사보안담당자현황" + "_" + sdf.format(new Date()));

        try {
            // set header names
            String[] headerNameArr = { "부문/총괄", "본부", "그룹/실", "팀", "독립파트"
                , "CoE", "MPR/S", "캠퍼스", "부서 유형", "RM부서"
                , "관리 부서", "보안담당자 정/부", "보안담당자 임명일", "보안담당자 사번", "보안담당자 성명/직위"
                , "보안담당자 연락처", "팀장 사번", "팀장 성명/직위", "팀장 연락처", "담당 사번"
                , "담당 성명/직위", "담당 사무실번호", "담당 휴대폰번호" };
            commonXlsViewDTO.setHeaderNameArr(headerNameArr);

            // set column names (data field name)
            String[] columnNameArr = { "deptLv2", "deptLv3", "deptLv4", "deptLv5", "deptLv6"
                , "deptLv7", "mprsGbnNm", "compNm", "teamGbnNm", "rmNm"
                , "deptNm", "jbGbnNm", "crtDt", "empId", "empNmStr"
                , "empTelNo", "teamEmpId", "teamEmpNmStr", "teamTelNo", "groupEmpId"
                , "groupEmpNmStr", "groupTelNo", "groupHpNo" };
            commonXlsViewDTO.setColumnNameArr(columnNameArr);

            // set column width
            Integer[] columnWidthArr = { 5000, 5000, 5000, 5000, 5000
                , 5000, 5000, 5000, 5000, 5000
                , 5000, 5000, 5000, 5000, 5000
                , 5000, 5000, 5000, 5000, 5000
                , 5000, 5000, 5000 };
            commonXlsViewDTO.setColumnWidthArr(columnWidthArr);

            List<Map<String, Object>> list = repository.secrtChangeAdminListExcel(paramMap);

            // set excel data
            commonXlsViewDTO.setDataList(list);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return commonXlsViewDTO;
    }

    // 전사 보안담당자 정보 삭제 - secrtChangeAdminDelete
    // 전사 보안담당자 직원정보 삭제 - secrtChangeAdminEmpDelete
    @Override
    public int secrtChangeAdminDelete(Map<String, Object> paramMap) {

        int updatedRows = 0;
        try {

            List<Map<String, Object>> arrItems = (List<Map<String, Object>>) paramMap.get("arrItems");

            if (arrItems.size() > 0) {
                // Loop Start
                for (Map<String, Object> objItem : arrItems) {
                    // Step01. 전사 보안담당자 정보 삭제 - DELETE FROM SC_DEPT_SEC
                    updatedRows += repository.secrtChangeAdminDelete(objItem);
                    // Step02. 전사 보안담당자 직원정보 삭제 - DELETE FROM SC_DEPT_SEC_EMP
                    updatedRows += repository.secrtChangeAdminEmpDelete(objItem);
                } // Loop End
            } // End if
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return updatedRows;
    }

    // 전사 보안담당자 관리 > 부서검색 - secrtDeptDuptyDeptList
    @Override
    public List<Map<String, Object>> secrtDeptDuptyDeptList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.secrtDeptDuptyDeptList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    // 전사 보안담당자 관리 > 등록  > Step01. Insert SC_DEPT_SEC - secrtChangeAdminScDeptSecInsert
    // 전사 보안담당자 관리 > 등록  > Step02. Delete SC_DEPT_SEC_EMP - secrtChangeAdminScDeptSecEmpDelete
    // 전사 보안담당자 관리 > 등록  > Step03. Insert SC_DEPT_SEC_EMP - secrtChangeAdminScDeptSecEmpInsert
    // 전사 보안담당자 관리 > 등록  > Step04. MERGE CO_EMP_AUTH - secrtChangeSecrtEmpAuthInsert
    @Override
    public int secrtChangeAdminInsert(Map<String, Object> paramMap) {

        int updatedRows = 0;
        try {

            String updSecCompId = StringUtils.defaultIfEmpty(String.valueOf(paramMap.get("updSecCompId")), "");
            String updSecDeptId = StringUtils.defaultIfEmpty(String.valueOf(paramMap.get("updSecDeptId")), "");
            String secEmpId = StringUtils.defaultIfEmpty(String.valueOf(paramMap.get("secEmpId")), "");

            String rCrtBy = StringUtils.defaultIfEmpty(String.valueOf(paramMap.get("crtBy")), "");
            String rAcIp = StringUtils.defaultIfEmpty(String.valueOf(paramMap.get("acIp")), "");

            // Step01. Insert SC_DEPT_SEC - secrtChangeAdminScDeptSecInsert
            repository.secrtChangeAdminScDeptSecInsert(paramMap);
            // Step02. Delete SC_DEPT_SEC_EMP - secrtChangeAdminScDeptSecEmpDelete
            repository.secrtChangeAdminScDeptSecEmpDelete(paramMap);

            List<String> arrDeptIds = (List<String>) paramMap.get("arrDeptIds"); // 관리부서코드  ex) ['123412', '12341']

            if (arrDeptIds.size() > 0) {
                Map<String, Object> objMap = new HashMap<String, Object>();
                // Loop Start
                for (String strDeptId : arrDeptIds) {
                    objMap.clear();
                    objMap.put("enaDeptId", strDeptId);
                    objMap.put("updSecCompId", updSecCompId);
                    objMap.put("updSecDeptId", updSecDeptId);
                    objMap.put("secEmpId", secEmpId);
                    objMap.put("acIp", rAcIp);
                    objMap.put("crtBy", rCrtBy);

                    // Step03. Insert SC_DEPT_SEC_EMP - secrtChangeAdminScDeptSecEmpInsert
                    updatedRows += repository.secrtChangeAdminScDeptSecEmpInsert(objMap);
                } // Loop End
            } // End if

            // Step04. MERGE CO_EMP_AUTH - secrtChangeSecrtEmpAuthInsert
            repository.secrtChangeSecrtEmpAuthInsert(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return updatedRows;
    }

    // 전사 보안담당자 관리 > 수정  > Step01. Update SC_DEPT_SEC - secrtChangeAdminScDeptSecUpdate
    // 전사 보안담당자 관리 > 수정  > Step02. Delete SC_DEPT_SEC_EMP - secrtChangeAdminScDeptSecEmpDelete2
    // 전사 보안담당자 관리 > 수정  > Step03. Insert SC_DEPT_SEC_EMP - secrtChangeAdminScDeptSecEmpInsert
    @Override
    public int secrtChangeAdminUpdate(Map<String, Object> paramMap) {

        int updatedRows = 0;
        try {

            String updSecCompId = StringUtils.defaultIfEmpty(String.valueOf(paramMap.get("updSecCompId")), "");
            String updSecDeptId = StringUtils.defaultIfEmpty(String.valueOf(paramMap.get("updSecDeptId")), "");
            String secEmpId = StringUtils.defaultIfEmpty(String.valueOf(paramMap.get("secEmpId")), "");

            String rCrtBy = StringUtils.defaultIfEmpty(String.valueOf(paramMap.get("crtBy")), "");
            String rAcIp = StringUtils.defaultIfEmpty(String.valueOf(paramMap.get("acIp")), "");

            // Step01. Update SC_DEPT_SEC - secrtChangeAdminScDeptSecUpdate
            repository.secrtChangeAdminScDeptSecUpdate(paramMap);
            // Step02. Step02. Delete SC_DEPT_SEC_EMP - secrtChangeAdminScDeptSecEmpDelete2
            repository.secrtChangeAdminScDeptSecEmpDelete2(paramMap);

            List<String> arrDeptIds = (List<String>) paramMap.get("arrDeptIds"); // 관리부서코드  ex) ['123412', '12341']

            if (arrDeptIds.size() > 0) {
                Map<String, Object> objMap = new HashMap<String, Object>();
                // Loop Start
                for (String strDeptId : arrDeptIds) {
                    objMap.clear();
                    objMap.put("enaDeptId", strDeptId);
                    objMap.put("updSecCompId", updSecCompId);
                    objMap.put("updSecDeptId", updSecDeptId);
                    objMap.put("secEmpId", secEmpId);
                    objMap.put("acIp", rAcIp);
                    objMap.put("crtBy", rCrtBy);

                    // Step03. Insert SC_DEPT_SEC_EMP - secrtChangeAdminScDeptSecEmpInsert
                    updatedRows += repository.secrtChangeAdminScDeptSecEmpInsert(objMap);
                } // Loop End
            } // End if
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return updatedRows;
    }

    // 전사 보안담당자 관리 > 관리부서Tree - secrtDeptDuptyDeptTreeList
    @Override
    public List<Map<String, Object>> secrtDeptDuptyDeptTreeList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.secrtDeptDuptyDeptTreeList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    // 전사 보안담당자 관리 > 상세정보 - secrtChangeAdminView
    @Override
    public Map<String, Object> secrtChangeAdminView(Map<String, Object> paramMap) {
        Map<String, Object> resultView = null;

        try {
            resultView = repository.secrtChangeAdminView(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultView;
    }

    // 보안점수 관리 조회 - secureEvalStatusAdminList
    @Override
    public List<Map<String, Object>> secureEvalStatusAdminList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.secureEvalStatusAdminList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    // 보안점수 관리 조회 엑셀다운 - secureEvalStatusAdminListExcel
    @Override
    public CommonXlsViewDTO secureEvalStatusAdminListExcel(Map<String, Object> paramMap) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        CommonXlsViewDTO commonXlsViewDTO = new CommonXlsViewDTO();
        commonXlsViewDTO.setFileName("전사보안점수" + "_" + sdf.format(new Date()));

        try {

            // set header names
            String[] headerNameArr = { "평가년도", "평가기간", "부문/총괄", "본부", "그룹/실"
                , "팀", "독립파트", "PL", "조직", "확정여부"
                , "등급", "순위", "백분율(상위 %)", "점수(합계)" };
            // array to list
            List<String> headerNameList = new ArrayList<>(Arrays.asList(headerNameArr));

            // set column names (data field name)
            String[] columnNameArr = { "evalYear", "evalGbNm", "deptLv2", "deptLv3", "deptLv4"
                , "deptLv5", "deptLv6", "deptLv7", "deptNm", "confirmYnNm"
                , "evaLv", "teamRank", "ryul", "totalScore" };
            // array to list
            List<String> columnNameList = new ArrayList<>(Arrays.asList(columnNameArr));

            // set column width
            Integer[] columnWidthArr = { 5000, 5000, 5000, 5000, 5000
                , 5000, 5000, 5000, 5000, 5000
                , 5000, 5000, 5000, 5000 };
            // array to list
            List<Integer> columnWidthList = new ArrayList<>(Arrays.asList(columnWidthArr));

            List<Map<String, Object>> dataList = repository.secureEvalStatusAdminListExcel(paramMap);

            /* ++ 항목별 점수 세팅 Start ++++++++++++++++++++++++++++++++++++++++++++++++++++++++  */
            List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
            String strItemScoreList = "";  // 항목별 점수 ex) 1,2,3,4
            String headerName = ""; // 항목명  ex) 항목1, 항목2, ...
            String columnName = ""; // 항목컬럼명 ex) itemScore1, itemScore2, ...
            String columnNameSnakecase = ""; // 항목컬럼DB명 ex) item_score1, item_score2, ...

            int i = 0;
            for (Map<String, Object> objMap : dataList) {
                strItemScoreList = StringUtils.defaultIfEmpty(String.valueOf(objMap.get("itemScoreList")), "");
                // System.out.println("## "+i+" strItemScoreList :"+ strItemScoreList);
                String[] arrItemScoreList = strItemScoreList.split(",");  // to Array
                // System.out.println("## "+i+"arrItemScoreList :"+ arrItemScoreList.toString());

                // 항목점수 만큼 Loop
                int idx = 0;
                for (String itemScore : arrItemScoreList) {
                    headerName = "항목".concat(Integer.toString(idx + 1));
                    columnName = "itemScore".concat(Integer.toString(idx + 1));
                    columnNameSnakecase = "ITEM_SCORE".concat(Integer.toString(idx + 1)); // List Data (SnakeCase)
                    objMap.put(columnNameSnakecase, itemScore); // Data Put
                    // header, column, width 한번만 추가
                    if (i == 0) {
                        headerNameList.add(headerName);  // add header names
                        columnNameList.add(columnName);  // add column names
                        columnWidthList.add(3000); // add column width
                    }
                    // System.out.println("## "+i+"."+idx+". headerName :"+ headerName);
                    // System.out.println("## "+i+"."+idx+".columnName :"+ columnName);
                    // System.out.println("## "+i+"."+idx+".value :"+ itemScore);
                    idx++;
                }
                retList.add(objMap); // add
                i++;
            }
            /* ++ 항목별 점수 세팅 End ++++++++++++++++++++++++++++++++++++++++++++++++++++++++  */
            // System.out.println("### retList:" + retList.toString());

            // set header names
            commonXlsViewDTO.setHeaderNameArr(headerNameList.toArray(new String[headerNameList.size()]));

            // set column names (data field name)
            commonXlsViewDTO.setColumnNameArr(columnNameList.toArray(new String[columnNameList.size()]));

            // set column width
            commonXlsViewDTO.setColumnWidthArr(columnWidthList.toArray(new Integer[columnWidthList.size()]));

            // set excel data
            commonXlsViewDTO.setDataList(retList);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return commonXlsViewDTO;
    }

    // 보안점수 관리 조회 > 삭제 - secureEvalStatusDelete
    @Override
    public int secureEvalStatusDelete(Map<String, Object> paramMap) {

        int updatedRows = 0;
        try {

            List<Map<String, Object>> arrItems = (List<Map<String, Object>>) paramMap.get("arrItems");

            if (arrItems.size() > 0) {
                // Loop Start
                for (Map<String, Object> objItem : arrItems) {
                    // 보안점수 관리 조회 > 삭제 - secureEvalStatusDelete
                    updatedRows += repository.secureEvalStatusDelete(objItem);
                } // Loop End
            } // End if
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return updatedRows;
    }

    // 보안점수 관리 조회 > 확정/확정취소 - secureEvalStatusModify
    @Override
    public int secureEvalStatusModify(Map<String, Object> paramMap) {

        int updatedRows = 0;
        try {

            List<Map<String, Object>> arrItems = (List<Map<String, Object>>) paramMap.get("arrItems");

            if (arrItems.size() > 0) {
                // Loop Start
                for (Map<String, Object> objItem : arrItems) {
                    // 보안점수 관리 조회 > 확정/확정취소 - secureEvalStatusModify
                    updatedRows += repository.secureEvalStatusModify(objItem);
                } // Loop End
            } // End if
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return updatedRows;
    }

    // 보안점수 관리 > 평가항목 List - secureEvalItemTargetList
    @Override
    public List<Map<String, Object>> secureEvalItemTargetList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.secureEvalItemTargetList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    // 보안점수 관리 > 평가결과저장  - Step01. Insert/Update SC_EVAL_RESULT - secureEvalResultUpdate
    // 보안점수 관리 > 평가결과저장  - Step02. Update SC_EVAL_TARGET_DEPT - secureEvalTotalScore
    @Override
    public int secureEvalResultUpdate(Map<String, Object> paramMap) {

        int updatedRows = 0;
        try {

            List<Map<String, Object>> arrItems = (List<Map<String, Object>>) paramMap.get("arrItems");

            //BigDecimal totalScore = new BigDecimal(0);
            //String strScore = "";
            if (arrItems.size() > 0) {
                // Loop Start
                for (Map<String, Object> objItem : arrItems) {
                    // Step01. Insert/Update SC_EVAL_RESULT - secureEvalResultUpdate
                    updatedRows += repository.secureEvalResultUpdate(objItem);
                    //strScore = StringUtils.defaultIfEmpty(String.valueOf(objItem.get("score")), "0");
                    //totalScore = totalScore.add(new BigDecimal(strScore));
                } // Loop End
            } // End if

            // Step02. Update SC_EVAL_TARGET_DEPT - secureEvalTotalScore
            updatedRows += repository.secureEvalTotalScore(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return updatedRows;
    }

}
