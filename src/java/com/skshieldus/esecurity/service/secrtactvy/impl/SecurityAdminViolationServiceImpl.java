package com.skshieldus.esecurity.service.secrtactvy.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skshieldus.esecurity.common.component.Mailing;
import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ListDTO;
import com.skshieldus.esecurity.model.common.ApprovalDTO;
import com.skshieldus.esecurity.model.common.ApprovalDocDTO;
import com.skshieldus.esecurity.repository.secrtactvy.SecurityAdminViolationRepository;
import com.skshieldus.esecurity.service.common.ApprovalService;
import com.skshieldus.esecurity.service.secrtactvy.SecurityAdminViolationService;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class SecurityAdminViolationServiceImpl implements SecurityAdminViolationService {

    @Autowired
    private ApprovalService approvalService;

    @Autowired
    private SecurityAdminViolationRepository repository;

    //@Autowired
    //private IdcardVisitRepository idCardVisit;
    //	@Autowired
    //	private EsecuritySiRepository siDS;

    @Autowired
    private Environment environment;

    @Autowired
    private Mailing mailing;

    @Value("${security.extnet.url}")
    private String securityExtnetUrl;

    @Value("${security.insnet.url}")
    private String securityInsnetUrl;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Map<String, Object> selectCoEmpInfo(HashMap<String, Object> paramMap) {
        Map<String, Object> result = null;

        try {
            result = repository.selectCoEmpInfo(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Boolean coViolationSave(HashMap<String, Object> paramMap) {
        /*fmSecCoEmpViolation_I*/
        boolean result = false;

        try {

            //채번
            int scDocNo = repository.getScDocNo();
            /*dmSecCoEmp_Violation_Seq_S*/
            paramMap.put("scDocNo", scDocNo);
            log.debug("[SecurityAdminViolationService.coViolationSave] scDocNo >> {}", scDocNo);

            //등록
            Map<String, Object> resultMap = coViolationInsert(paramMap); /*dmSecCoEmpViolation_I*/

            int resultCnt = (int) resultMap.get("resultCnt");

            /* E-MAIL 및 SMS 발송 */
            if (resultCnt > 0) {

                List<Map<String, Object>> mailList = null;

                /* 위규자 E-MAIL 발송 시작 */
                if ("C0280004".equals(paramMap.get("actDo")) || ("C0280003".equals(paramMap.get("actDo")) && paramMap.get("kaEmpId") != null && !paramMap.get("kaEmpId").equals(""))) {
                    mailList = repository.dmSecCoEmp_Discipline_SendMail_List_S(paramMap);
                }
                else {
                    mailList = repository.dmSecCoEmp_Violation_SendMail_List_S(paramMap);
                }

                String ofendEmpId = "";
                //김용범책임 요청에 의해 추가도미 20161124 HSK 해당 쿼리 수정
                Map<String, Object> docInfo = repository.dmSecCoEmp_Violation_Info(paramMap);

                String ofendDeptNm = "";
                String ofendJwNm = "";
                String ofendDt = "";
                String ofendTm = "";
                String ofendGbnNm = "";
                String ofendDetailNm = "";
                String ofendDetailCd = "";// 김용범책임 요청에 으해 추가도미 20161124 HSK
                String ofendSubNm = "";// 김용범책임 요청에 으해 추가도미 20161124 HSK
                String ofendGbn = "";
                String actDoNm = "";
                String ofendLoc = "";

                if (docInfo != null) {
                    ofendDeptNm = String.valueOf(docInfo.get("ofendDeptNm"));
                    ofendJwNm = String.valueOf(docInfo.get("ofendJwNm"));
                    ofendDt = String.valueOf(docInfo.get("ofendDt"));
                    ofendTm = String.valueOf(docInfo.get("ofendTm"));
                    ofendGbnNm = String.valueOf(docInfo.get("ofendGbnNm"));
                    ofendDetailNm = String.valueOf(docInfo.get("ofendDetailNm"));
                    ofendDetailCd = String.valueOf(docInfo.get("ofendDetailCd"));
                    ofendSubNm = String.valueOf(docInfo.get("ofendSubNm"));
                    ofendGbn = String.valueOf(docInfo.get("ofendGbn"));
                    actDoNm =String.valueOf(docInfo.get("actDoNm"));

                    if ("".equals((String) docInfo.get("actGate"))) {
                        ofendLoc = docInfo.get("ofendCompNm") + " > " + docInfo.get("actBldgNm") + " > " + docInfo.get("actLocateNm");
                    }
                    else {
                        ofendLoc = docInfo.get("ofendCompNm") + " > " + docInfo.get("actBldgNm") + " > " + docInfo.get("actLocateNm") + " > " + docInfo.get("actGate");
                    }
                }

                Map<String, String> mailMap = new HashMap<String, String>();

                /* 위규자 E-MAIL 발송 시작 */
                for (int i = 0; i < mailList.size(); i++) {

                    mailMap = new HashMap<String, String>();

                    ofendEmpId = (String) mailList.get(0).get("empId");

                    mailMap.put("ofendEmpId", (String) mailList.get(0).get("empId"));    // 위규자 사번 : 필수
                    mailMap.put("ofendEmpNm", (String) mailList.get(0).get("empNm"));    // 위규자 이름 : 필수
                    mailMap.put("ofendEmpEmail", (String) mailList.get(0).get("email"));  // 위규자 메일 : 필수

                    mailMap.put("ofendDeptNm", ofendDeptNm);
                    mailMap.put("ofendJwNm", ofendJwNm);
                    mailMap.put("ofendDt", ofendDt);
                    mailMap.put("ofendTm", ofendTm);
                    mailMap.put("ofendGbnNm", ofendGbnNm);
                    mailMap.put("ofendDetailNm", ofendDetailNm);
                    mailMap.put("ofendDetailCd", ofendDetailCd);
                    mailMap.put("ofendSubNm", ofendSubNm);
                    mailMap.put("schemaNm", "보안위규자");
                    mailMap.put("docNm", "보안 위규자");        // 문서 명 : 필수
                    mailMap.put("crtBy", (String) paramMap.get("crtBy"));          // 로그인ID
                    mailMap.put("acIp", (String) paramMap.get("acIp"));            // AC_IP
                    mailMap.put("ofendLoc", ofendLoc);                    // 장소


                    /* 구성원 조치 ACT_DO 자동저장이면 메일발송 */
                    log.info("@@@@@ GU_MAIL_YN = {}" , resultMap.get("guMailYn"));

                    if ("Y".equals((String) resultMap.get("guMailYn"))) {
                        if ("1".equals((String) mailList.get(i).get("gubun"))) { // 위규자

                            mailMap.put("toEmpId", (String) mailList.get(i).get("empId"));    // 메일 수신자 사번 : 필수
                            mailMap.put("toEmpNm", (String) mailList.get(i).get("empNm"));    // 메일 수신자 이름 : 필수
                            mailMap.put("toEmpEmail", (String) mailList.get(i).get("email"));  // 메일 수신자 메일주소 : 필수
                            mailMap.put("scrtView", "SCRT_VIEW");

                            // TODO:(hoonLee) 메일보내기 주석풀기
                            //fmSecCoEmpViolationGuVerifyMailSend(mailMap);
                        }
                    }

                    /* 구성원 조치 ACT_DO 자동저장이면 메일발송 */
                    if ("4".equals((String) mailList.get(i).get("gubun"))) { // 위반내용별 > 사업장별 보안담당자에게 메일발송
                        mailMap.put("toEmpId", (String) mailList.get(i).get("empId"));    // 메일 수신자 사번 : 필수
                        mailMap.put("toEmpNm", (String) mailList.get(i).get("empNm"));    // 메일 수신자 이름 : 필수
                        mailMap.put("toEmpEmail", (String) mailList.get(i).get("email"));  // 메일 수신자 메일주소 : 필수
                        mailMap.put("scrtView", "SCRT_VIEWUPPER_SEC");
                        //TODO:(hoonLee) 메일보내기 주석풀기
                        //fmSecCoEmpViolationMailSend(mailMap);
                    }
                }
                /* 위규자 E-MAIL 발송 종료 */
            }
            result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    private void fmSecCoEmpViolationGuVerifyMailSend(Map<String, String> mailMap) {
        String schemaNm = StringUtils.defaultIfEmpty(mailMap.get("schemaNm"), "");
        String sendFromMail = "skhystec_security@skhystec.com";
        String sendFromName = mailMap.get("ofendEmpNm");
        String sendFromID = mailMap.get("crtBy");
        String sendFromEmail = mailMap.get("ofendEmpEmail");
        String scrtView = mailMap.get("scrtView");

        if (scrtView == null) { scrtView = ""; }

        String ofendDeptNm = mailMap.get("ofendDeptNm");
        String ofendJwNm = mailMap.get("ofendJwNm");
        String ofendDt = mailMap.get("ofendDt");
        String ofendTm = mailMap.get("ofendTm");
        String ofendGbnNm = mailMap.get("ofendGbnNm");
        String ofendDetailNm = mailMap.get("ofendDetailNm");
        String ofendDetailCd = mailMap.get("ofendDetailCd"); //김용범책임 요청에 으해 추가도미 20161124 HSK
        String ofendSubNm = mailMap.get("ofendSubNm");//김용범책임 요청에 으해 추가도미 20161124 HSK
        String ofendLocNm = mailMap.get("ofendLoc"); // 장소

        String sendToName = mailMap.get("toEmpNm");
        String sendToID = mailMap.get("toEmpId");
        String sendToEmail = mailMap.get("toEmpEmail");
        //String remark = mailMap.get("remark").replaceAll("\n", "<BR/>"); //비고 산업보안팀 의견

        String domain = securityInsnetUrl;

        String actDoNm = StringUtils.defaultIfEmpty(mailMap.get("actDoNm"), "");

        String title = "[e-Security] 보안위규 관련, 구성원 확인 안내";
        String shotURL = domain + "?SCHEMAID=SCRT_VIEW";

        String message = sendFromName + "님께서는 아래 보안위규 건에 대해, 「구성원 확인」으로 처리되었습니다.";

        if (scrtView.equals("SCRT_VIEWUPPER_SEC")) {
            shotURL = domain + "?SCHEMAID=SCRT_VIEWUPPER_SEC";
        }

        StringBuffer str = new StringBuffer();

        str.append(" <!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'> \n");
        str.append(" <html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"ko\" lang=\"ko\"> \n");
        str.append(" <head> \n");
        str.append(" <title>입주사-행복한 만남 - SK쉴더스</title> \n");
        str.append(" <style>			 \n");
        str.append(" body { margin:0; padding:0; border:0; font-size:0.75em; line-height:1.8em; letter-spacing:0px; color:#7D7D7D;			scrollbar-3dlight-color:#ffffff; scrollbar-arrow-color:#666666; scrollbar-base-color:#ffffff; scrollbar-darkshadow-color:#d7d7d7;			scrollbar-face-color:#f2f2f2; scrollbar-highlight-color:#f2f2f2;	scrollbar-shadow-color:#f2f2f2;	scrollbar-track-color:#f2f2f2; }			 \n");
        str.append(" *html, body{height:100%; overflow-x:hidden;}			 \n");
        str.append(" body, table	 {width:100%;margin:0px;padding:0px;text-align:left}			 \n");
        str.append(" body, select, input,textarea{margin:0px;padding:0px; font-family:'나눔고딕', '맑은 고딕', 'Malgun Gothic', NanumGothicBold, AppleGothic, 'Lucida Grande', Tahoma, Verdana, UnDotum, Dotum, sans-serif;}			 \n");
        str.append(" div, p, ul, li, dl, dt, dd, h1, h2, h3, h4, h5, form	{margin:0px;padding:0px;list-style:none}			 \n");
        str.append(" img, table {border:0px none; font-size:13px;}			 \n");
        str.append(" a {border:0px none}			 \n");
        str.append(" a,img,input {text-align:absmiddle;margin:0px;padding:0px}			 \n");
        str.append(" select{font-size:1em;}			 \n");
        str.append(" @font-face{font-family:NanumGothic; src:url(http://www.skhystec.com/down/esecurity/common/css/NanumGothic.ttf); src:url(http://www.skhystec.com/down/esecurity/common/css/NanumGothic.ttf)  format(truetype);}			@font-face{font-family:NanumGothic; src:url(http://www.skhystec.com/down/esecurity/common/css/NanumGothic.eot); src: url(http://www.skhystec.com/down/esecurity/common/css/NanumGothic.eot?iefix) format(eot);}			@font-face {font-family:NanumGothicBold; src: url(http://www.skhystec.com/down/esecurity/common/css/NanumGothicBold.eot); src: url(http://www.skhystec.com/down/esecurity/common/css/NanumGothicBold.eot?iefix) format(eot), url(http://www.skhystec.com/down/esecurity/common/css/NanumGothicBold.woff) format(woff), url(http://www.skhystec.com/down/esecurity/common/css/font/NanumGothicBold.ttf)  format(truetype), url(http://www.skhystec.com/down/esecurity/common/css/font/NanumGothicBold.svg#svgFontName) format(svg);}			 \n");
        str.append(" .yellow01 { color:#DD9619; font-weight: bold}			 \n");
        str.append(" #popBody01_security {width:700px; margin:30px auto;  padding-bottom:10px; font-family:'나눔고딕', AppleGothic, '맑은 고딕', 'Malgun Gothic', 'Lucida Grande', Tahoma, Verdana,  UnDotum, Dotum, sans-serif;}			 \n");
        str.append(" #popBody01_security #popArea_security {border-bottom:4px solid #F57724; overflow:hidden; }			 \n");
        str.append(" #popBody01_security .pop_title01_security{font-size:1.4em; padding:10px 0 5px 0; color:#F57724; font-weight:bold; letter-spacing:0px; overflow:hidden; height: 80px;}			#popBody01_security .pop_title01_security img{float:left;}			 \n");
        str.append(" #popBody01_security .pop_title01_security span{float:right; text-align:right; margin-top:33px; border:0px solid #000; width:550px;}			 \n");
        str.append(" #popBody01_security .close{float:right; padding:25px 10px 0 0px;}			 \n");
        str.append(" #popBody01_security .pop_content_security{margin:0px auto 10px auto; border:0px solid #000; background:#fff;}			 \n");
        str.append(" .mail_content_security{ width:700px; padding:50px 10px; border:1px solid #DFDFDF; overflow:hidden; margin:30px auto; background:#fff;} 			 \n");
        str.append(" .mail_left_security{float:left; margin-right:20px; }			 \n");
        str.append(" .mail_right_security{float:right; width:470px; border-left:1px dotted #A0B9A9; padding-left:20px; overflow:hidden; font-size:13px;}			 \n");
        str.append(" .mail_right_security dl{border-top:2px solid #000; border-bottom:2px solid #000; padding:10px; overflow:hidden; margin:10px 0 10px 0; font-weight:bold; color:#DD9619;}			.mail_right_security dt{float:left;}			 \n");
        str.append(" .mail_right_security dd{float:left; margin-left:10px;}			 \n");
        str.append(" .mail_title{font-size:14px; font-weight:bold; margin:10px 0; color:#000; padding:0 0 5px 10px; border-bottom:1px solid #DFDFDF; background:url('http://www.skhystec.com/down/esecurity/common/images/common/title06.png') no-repeat 0 3px;}			 \n");
        str.append(" .notice_01{margin-top:25px; padding-left:50px; background:url('http://www.skhystec.com/down/esecurity/common/images/common/notice.jpg') no-repeat 0 5px;}			 \n");
        str.append(" .notice_01 a:link{color:#5EA0D0; font-weight:bold; text-decoration:underline;}			 \n");
        str.append(" .mail_bottom_security{clear:both; font-size:1em; border-top:3px solid #C4C4C4; padding-top:10px; line-height:1.4em;}		 \n");
        str.append(" </style> \n");
        str.append(" </head> \n");
        str.append(" <body> \n");
        str.append(" <div id=\"popBody01_security\"> \n");
        str.append(" <div id=\"popArea_security\"> \n");
        str.append(" <div class=\"pop_title01_security\"><img src=\"http://www.skhystec.com/down/esecurity/common/images/common/mail_logo.jpg\"><span>" + title + "</span></div> \n");
        str.append(" </div> \n");
        str.append(" <div class=\"pop_content_security\"> \n");
        str.append(" <div class=\"mail_content_security\"> \n");
        str.append(" <div class=\"mail_left_security\"><img src=\"http://www.skhystec.com/down/esecurity/common/images/common/mail_img01.jpg\"> \n");
        str.append(" </div> \n");
        str.append(" <div class=\"mail_right_security\"> \n");
        str.append(" 	<div style=\" padding:4px 0px;line-height:15px;\"><span style=\"font-weight:bold;\">" + sendFromName + "</span>님께서는 아래 보안위규 건에 대해, <span style=\"color:#f57724\">「구성원 확인」</span>으로 처리되었습니다.</br></div> \n");
        str.append(" 	<div style=\" padding:4px 0px;line-height:15px;\">「구성원 확인」은 해당 구성원 본인에게 위규내용을 이메일로 통보하는 것으로 해당 구성원이 별도 조치할 사항은 없습니다. </br></div> \n");
        str.append(" 	<div style=\" padding:4px 0px;line-height:15px;\">산업보안 포탈 사이트 : <a target='_blank' href=\"" + shotURL + "\">security.skshieldus.com</a></div> \n");
        str.append(" <br> \n");
        str.append(" <table border=\"1\" bordercolor=\"#e7d1c2\" cellspacing=\"0\" cellpadding=\"5\" style=\"border-collapse:collapse;\"> \n");
        str.append(" <tbody> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">성명 / 직위</span></td> \n");
        str.append(" <td align=\"left\" width=\"80%\">" + sendFromName + " " + ofendJwNm + "</td> \n");
        str.append(" </tr> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">일시</span></td> \n");
        str.append(" <td align=\"left\">" + ofendDt + " " + ofendTm + "</td> \n");
        str.append(" </tr> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">장소</span></td> \n");
        str.append(" <td align=\"left\">" + ofendLocNm + "</td> \n");
        str.append(" </tr> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">위규 내용</span></td> \n");
        // 생활보안 준수사항의 단순 위반(C0541011) 인 경우 상세까지 표시(김용범_20170102)
        if("C0541011".equals(ofendDetailCd)){
            str.append(" <td align=\"left\">" + ofendDetailNm + "<br/>> " + ofendSubNm + "</td> \n");
        }else{
            str.append(" <td align=\"left\">" + ofendDetailNm + "</td> \n");
        }
        str.append(" </tr> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">위규 처리</span></td> \n");
        str.append(" <td align=\"left\"><span style=\"color:#f57724\">구성원 확인 (해당 구성원에게 위규 내용 통보)</span></td> \n");
        str.append(" </tr> \n");
        /*
         * str.append(" <tr> \n"); str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">처리 기준</span></td> \n"); str.append(" <td align=\"left\">- 분기 1~2회 : 구성원 확인</br>- 분기 3회 이상 : 시정계획서</td> \n"); str.append(" </tr> \n");
         */
        str.append(" <tr> \n");
        if(ofendDetailNm.trim().equals("생활보안 준수사항의 단순 위반") || ofendDetailCd.trim().equals("C0541011")){
            str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">주의 사항</span></td> \n");
            str.append(" <td align=\"left\">연간 누적(`16년 9월~`17년 8월) 1회는 「구성원 확인」으로 처리되며, 연간 누적 2회 이상은 시정계획서 대상임</td> \n");
        }else{
            str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">주의 사항</span></td> \n");
            str.append(" <td align=\"left\">분기 1~2회는 「구성원 확인」으로 처리되나,분기 3회 이상인 경우, 「시정계획서」 대상임</td> \n");
        }
        str.append(" </tr> \n");
        str.append(" </tbody> \n");
        str.append(" </table> \n");
        str.append(" </div> \n");
        str.append(" </div> \n");
        str.append(" <div class=\"mail_bottom_security\"><br> \n");
        str.append(" <br> \n");
        str.append(" 본 메일은 발신전용 메일입니다.<br> \n");
        str.append(" <strong>SK쉴더스</strong> 경기도 이천시 부발읍 경충대로 2091 (TEL 031.5185.4114)<br> \n");
        str.append(" Copyrightⓒ SK SHIELDUS Inc. All Rights Reserved.</div> \n");
        str.append(" </div> \n");
        str.append(" </div> \n");
        str.append(" </body> \n");
        str.append(" </html> \n");

        boolean result = mailing.sendMail(title, mailing.applyMailTemplate(title, str.toString()), sendToEmail, "", schemaNm, "", (String) mailMap.get("acIp"));
    }

    private void fmSecCoEmpViolationMailSend(Map<String, String> mailMap) {

        String schemaNm = StringUtils.defaultIfEmpty(mailMap.get("schemaNm"), "");
        String sendFromMail = "skhystec_security@skhystec.com";
        String sendFromName = mailMap.get("ofendEmpNm");
        String sendFromID = mailMap.get("crtBy");
        String sendFromEmail = mailMap.get("ofendEmpEmail");
        String scrtView = mailMap.get("scrtView");

        if (scrtView == null) { scrtView = ""; }

        String ofendDeptNm = mailMap.get("ofendDeptNm");
        String ofendJwNm = mailMap.get("ofendJwNm");
        String ofendDt = mailMap.get("ofendDt");
        String ofendTm = mailMap.get("ofendTm");
        String ofendGbnNm = mailMap.get("ofendGbnNm");
        String ofendDetailNm = mailMap.get("ofendDetailNm");
        String ofendDetailCd = mailMap.get("ofendDetailCd");
        String ofendSubNm = mailMap.get("ofendSubNm");

        String sendToName = mailMap.get("toEmpNm");
        String sendToID = mailMap.get("toEmpId");
        String sendToEmail = mailMap.get("toEmpEmail");
        String domain = securityInsnetUrl;

        String actDoNm = StringUtils.defaultIfEmpty(mailMap.get("actDoNm"), "");

        String title = "";
        String message = sendFromName + "님께서 " + mailMap.get("docNm") + "로 적발되었습니다.<br />";
        String shotURL = domain + "?SCHEMAID=SCRT_VIEW";

        if (scrtView.equals("SCRT_VIEWUPPER_SEC")) {
            title = "[e-Security] 보안위규 조치 내역 처리 필요 안내";
            shotURL = domain + "?SCHEMAID=CO_SCRT_VIEWUPPER_SEC";
        }
        else {
            title = "[e-Security] " + sendFromName + "님께서 " + mailMap.get("docNm") + "로 적발되었습니다.";
        }

        StringBuffer str = new StringBuffer();

        str.append(" <!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'> \n");
        str.append(" <html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"ko\" lang=\"ko\"> \n");
        str.append(" <head> \n");
        str.append(" <title>입주사-행복한 만남 - SK쉴더스</title> \n");
        str.append(" <style>			 \n");
        str.append(" body { margin:0; padding:0; border:0; font-size:0.75em; line-height:1.8em; letter-spacing:0px; color:#7D7D7D;			scrollbar-3dlight-color:#ffffff; scrollbar-arrow-color:#666666; scrollbar-base-color:#ffffff; scrollbar-darkshadow-color:#d7d7d7;			scrollbar-face-color:#f2f2f2; scrollbar-highlight-color:#f2f2f2;	scrollbar-shadow-color:#f2f2f2;	scrollbar-track-color:#f2f2f2; }			 \n");
        str.append(" *html, body{height:100%; overflow-x:hidden;}			 \n");
        str.append(" body, table	 {width:100%;margin:0px;padding:0px;text-align:left}			 \n");
        str.append(" body, select, input,textarea{margin:0px;padding:0px; font-family:'나눔고딕', '맑은 고딕', 'Malgun Gothic', NanumGothicBold, AppleGothic, 'Lucida Grande', Tahoma, Verdana, UnDotum, Dotum, sans-serif;}			 \n");
        str.append(" div, p, ul, li, dl, dt, dd, h1, h2, h3, h4, h5, form	{margin:0px;padding:0px;list-style:none}			 \n");
        str.append(" img, table {border:0px none; font-size:13px;}			 \n");
        str.append(" a {border:0px none}			 \n");
        str.append(" a,img,input {text-align:absmiddle;margin:0px;padding:0px}			 \n");
        str.append(" select{font-size:1em;}			 \n");
        str.append(" @font-face{font-family:NanumGothic; src:url(http://www.skhystec.com/down/esecurity/common/css/NanumGothic.ttf); src:url(http://www.skhystec.com/down/esecurity/common/css/NanumGothic.ttf)  format(truetype);}			@font-face{font-family:NanumGothic; src:url(http://www.skhystec.com/down/esecurity/common/css/NanumGothic.eot); src: url(http://www.skhystec.com/down/esecurity/common/css/NanumGothic.eot?iefix) format(eot);}			@font-face {font-family:NanumGothicBold; src: url(http://www.skhystec.com/down/esecurity/common/css/NanumGothicBold.eot); src: url(http://www.skhystec.com/down/esecurity/common/css/NanumGothicBold.eot?iefix) format(eot), url(http://www.skhystec.com/down/esecurity/common/css/NanumGothicBold.woff) format(woff), url(http://www.skhystec.com/down/esecurity/common/css/font/NanumGothicBold.ttf)  format(truetype), url(http://www.skhystec.com/down/esecurity/common/css/font/NanumGothicBold.svg#svgFontName) format(svg);}			 \n");
        str.append(" .yellow01 { color:#DD9619; font-weight: bold}			 \n");
        str.append(" #popBody01_security {width:700px; margin:30px auto;  padding-bottom:10px; font-family:'나눔고딕', AppleGothic, '맑은 고딕', 'Malgun Gothic', 'Lucida Grande', Tahoma, Verdana,  UnDotum, Dotum, sans-serif;}			 \n");
        str.append(" #popBody01_security #popArea_security {border-bottom:4px solid #F57724; overflow:hidden; }			 \n");
        str.append(" #popBody01_security .pop_title01_security{font-size:1.4em; padding:10px 0 5px 0; color:#F57724; font-weight:bold; letter-spacing:0px; overflow:hidden; height: 80px;}			#popBody01_security .pop_title01_security img{float:left;}			 \n");
        str.append(" #popBody01_security .pop_title01_security span{float:right; text-align:right; margin-top:33px; border:0px solid #000; width:550px;}			 \n");
        str.append(" #popBody01_security .close{float:right; padding:25px 10px 0 0px;}			 \n");
        str.append(" #popBody01_security .pop_content_security{margin:0px auto 10px auto; border:0px solid #000; background:#fff;}			 \n");
        str.append(" .mail_content_security{ width:700px; padding:50px 10px; border:1px solid #DFDFDF; overflow:hidden; margin:30px auto; background:#fff;} 			 \n");
        str.append(" .mail_left_security{float:left; margin-right:20px; }			 \n");
        str.append(" .mail_right_security{float:right; width:470px; border-left:1px dotted #A0B9A9; padding-left:20px; overflow:hidden; font-size:13px;}			 \n");
        str.append(" .mail_right_security dl{border-top:2px solid #000; border-bottom:2px solid #000; padding:10px; overflow:hidden; margin:10px 0 10px 0; font-weight:bold; color:#DD9619;}			.mail_right_security dt{float:left;}			 \n");
        str.append(" .mail_right_security dd{float:left; margin-left:10px;}			 \n");
        str.append(" .mail_title{font-size:14px; font-weight:bold; margin:10px 0; color:#000; padding:0 0 5px 10px; border-bottom:1px solid #DFDFDF; background:url('http://www.skhystec.com/down/esecurity/common/images/common/title06.png') no-repeat 0 3px;}			 \n");
        str.append(" .notice_01{margin-top:25px; padding-left:50px; background:url('http://www.skhystec.com/down/esecurity/common/images/common/notice.jpg') no-repeat 0 5px;}			 \n");
        str.append(" .notice_01 a:link{color:#5EA0D0; font-weight:bold; text-decoration:underline;}			 \n");
        str.append(" .mail_bottom_security{clear:both; font-size:1em; border-top:3px solid #C4C4C4; padding-top:10px; line-height:1.4em;}		 \n");
        str.append(" </style> \n");
        str.append(" </head> \n");
        str.append(" <body> \n");
        str.append(" <div id=\"popBody01_security\"> \n");
        str.append(" <div id=\"popArea_security\"> \n");
        str.append(" <div class=\"pop_title01_security\"><img src=\"http://www.skhystec.com/down/esecurity/common/images/common/mail_logo.jpg\"><span>" + title + "</span></div> \n");
        str.append(" </div> \n");
        str.append(" <div class=\"pop_content_security\"> \n");
        str.append(" <div class=\"mail_content_security\"> \n");
        str.append(" <div class=\"mail_left_security\"><img src=\"http://www.skhystec.com/down/esecurity/common/images/common/mail_img01.jpg\"> \n");
        str.append(" </div> \n");
        str.append(" <div class=\"mail_right_security\"> \n");
        str.append(" 	<div style=\" padding:4px 0px;line-height:15px;\"><span style=\"font-weight:bold;\">" + sendFromName + "</span>님께서 보안 위규자로 적발되었습니다.</br>아래 보안 위규건에 대해 위규 조치 내역을 입력하시기 바랍니다.</div>\n");
        str.append(" 	<div style=\" padding:4px 0px;line-height:15px;\">산업보안 포탈 사이트 : <a target='_blank' href=\"" + shotURL + "\">security.skshieldus.com</a></div> \n");
        str.append(" <br> \n");
        str.append(" <table border=\"1\" bordercolor=\"#e7d1c2\" cellspacing=\"0\" cellpadding=\"5\" style=\"border-collapse:collapse;\"> \n");
        str.append(" <tbody> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">위규자</span></td> \n");
        str.append(" <td align=\"left\" width=\"80%\">" + sendFromName + " " + ofendJwNm + "</td> \n");
        str.append(" </tr> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">위규 일시</span></td> \n");
        str.append(" <td align=\"left\">" + ofendDt + " " + ofendTm + "</td> \n");
        str.append(" </tr> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">위규 내용</span></td> \n");
        str.append(" <td align=\"left\">" + ofendGbnNm + "</td> \n");
        str.append(" </tr> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">위규 내용</span></td> \n");
        // 생활보안 준수사항의 단순 위반(C0541011) 인 경우 상세까지 표시(김용범_20170102)
        if("C0541011".equals(ofendDetailCd)){
            str.append(" <td align=\"left\">" + ofendDetailNm + "<br/>> " + ofendSubNm + "</td> \n");
        }else{
            str.append(" <td align=\"left\">" + ofendDetailNm + "</td> \n");
        }
        str.append(" </tr> \n");

        if(!"".equals(actDoNm)){
            str.append(" <tr> \n");
            str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">조치내용</span></td> \n");
            str.append(" <td align=\"left\">" + actDoNm + "</td> \n");
            str.append(" </tr> \n");
        }

        str.append(" </tbody> \n");
        str.append(" </table> \n");
        str.append(" </div> \n");
        str.append(" </div> \n");
        str.append(" <div class=\"mail_bottom_security\"><br> \n");
        str.append(" <br> \n");
        str.append(" 본 메일은 발신전용 메일입니다.<br> \n");
        str.append(" <strong>SK쉴더스</strong> 경기도 이천시 부발읍 경충대로 2091 (TEL 031.5185.4114)<br> \n");
        str.append(" Copyrightⓒ SK SHIELDUS Inc. All Rights Reserved.</div> \n");
        str.append(" </div> \n");
        str.append(" </div> \n");
        str.append(" </body> \n");
        str.append(" </html> \n");

        boolean result = mailing.sendMail(title, mailing.applyMailTemplate(title, str.toString()), sendToEmail, "", schemaNm, "", (String) mailMap.get("acIp"));
    }

    private Map<String, Object> coViolationInsert(HashMap<String, Object> paramMap) {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        String guMailYn = "N";

        // ---------- ACT_DO 컬럼에 구성원확인으로 Default 입력 되도록 예외처리를 함 : 2016-08-03 by JSH
        if ("C0531011".equals((String) paramMap.get("ofendDetailGbn"))) { // 사원증미패용

            Map<String, Object> rs = repository.selectCoViolationExcptCnt1(paramMap);

            int cnt = ((BigDecimal) rs.get("cnt1")).intValue();

            if (cnt < 2) {
                paramMap.put("defaultActYn", "Y");
                paramMap.put("actDo", "C0280001"); // 구성원확인

                guMailYn = "Y"; //구성원 확인 메일발송 여부
            }
        }
        // 카메라렌즈 보안스티커 미부착, 훼손 AND 1차(보안요원점검시 특이사항 없음) 인 경우만 적용
        else if ("C0531010".equals((String) paramMap.get("ofendDetailGbn")) && "C0601001".equals((String) paramMap.get("mobileForensicsGbn"))) {

            int cnt = repository.selectCoViolationExcptCnt2(paramMap); // 모바일 포렌직 2차 적발건수

            if (cnt < 1) {
                Map<String, Object> rs2 = repository.selectCoViolationExcptCnt1(paramMap);
                int cnt2 = ((BigDecimal) rs2.get("cnt2")).intValue();

                if (cnt2 < 2) {
                    paramMap.put("defaultActYn", "Y");
                    paramMap.put("actDo", "C0280001"); // 구성원확인

                    guMailYn = "Y"; // 구성원 확인 메일발송 여부
                }
            }
        }
        int resultCnt = 0;
        resultCnt = repository.coViolationInsert(paramMap);

        resultMap.put("guMailYn", guMailYn);
        resultMap.put("resultCnt", resultCnt);

        return resultMap;
    }

    public Map<String, Object> fmSecCoEmpViolation_ActDo(HashMap<String, Object> dsActDoData) {

        Map<String, Object> resultMap = new HashMap<>();
        Map<String, String> mailMap = new HashMap<String, String>();

        boolean result = false;

        String actDo = (String) dsActDoData.get("actDo");

        //20210112: Cheyminjung 에 대한 특별 대우로, 보안위규 등록 되지 않도록 처리할것. 물리보안팀 요청 (이명주 TL)
        if ("2073479".equals((String) dsActDoData.get("ofendEmpId"))) {
            result = true;
            resultMap.put("result", result);

            return resultMap;
        }
        else {
            if ("C0280002,C0280003".indexOf(actDo) > -1) { // 시정계획서징구, 경고장발송
                dsActDoData.put("corrPlanSendYn", "C0101002"); // 미제출
            }
            else {
                dsActDoData.put("corrPlanSendYn", "C0101003"); // 해당없음
            }

            int resultCnt = repository.dmSecCoEmp_Violation_Ofend_U(dsActDoData);  // SC_OFEND 저장

            int resultCnt2 = repository.dmSecCoEmp_Violation_Exp_I(dsActDoData);  // SC_EXP_DO 저장

            result = true;

            if ("C0280002,C0280003".indexOf(actDo) > -1) { // 시정계획서징구, 경고장발송
                //채번
                String scCorrPlanNo = repository.dmSecCoEmp_Violation_Corr_Plan_Seq_S(dsActDoData);

                dsActDoData.put("scCorrPlanNo", scCorrPlanNo);

                int resultCnt3 = repository.dmSecCoEmp_Violation_Corr_Plan_I(dsActDoData);

                if (resultCnt3 < 1) {
                    result = false;
                }
            }

            if ("C0280002".equals(actDo)) { // 시정계획서징구 메일 발송

                /* 위규자 E-MAIL 발송 시작 */
                List<Map<String, Object>> mailList = repository.dmSecCoEmp_Violation_SendMail_List_S(dsActDoData);

                String ofendEmpId = "";
                String ofendDeptNm = "";
                String ofendJwNm = "";
                String ofendDt = "";
                String ofendTm = "";
                String ofendGbnNm = "";
                String ofendDetailNm = "";
                String ofendDetailCd = "";
                String ofendSubNm = "";
                String ofendGbn = "";
                String actDoNm = "";
                String ofendLoc = "";
                String limit14Dtm = ""; // 14일
                String limit30Dtm = ""; // 30일
                String remark = ""; //비고 /산업보안팀 의견

                Map<String, Object> docInfo = repository.dmSecCoEmp_Violation_Info(dsActDoData);

                if (docInfo != null) {
                    ofendDeptNm = (String) docInfo.get("ofendDeptNm");
                    ofendJwNm = (String) docInfo.get("ofendJwNm");
                    ofendDt = (String) docInfo.get("ofendDt");
                    ofendTm = (String) docInfo.get("ofendTm");
                    ofendGbnNm = (String) docInfo.get("ofendGbnNm");
                    ofendDetailNm = (String) docInfo.get("ofendDetailNm");
                    ofendDetailCd = (String) docInfo.get("ofendDetailCd");
                    ofendSubNm = (String) docInfo.get("ofendSubNm");
                    ofendGbn = (String) docInfo.get("ofendGbn");
                    actDoNm = (String) docInfo.get("actDoNm");

                    limit14Dtm = (String) docInfo.get("limit14Dtm");
                    limit30Dtm = (String) docInfo.get("limit30Dtm");

                    remark = (String) docInfo.get("remark");

                    if ("".equals((String) docInfo.get("actGate"))) {
                        ofendLoc = (String) docInfo.get("ofendCompNm") + " > " + (String) docInfo.get("actBldgNm") + " > " + (String) docInfo.get("actLocateNm");
                    }
                    else {
                        ofendLoc =
                            (String) docInfo.get("ofendCompNm") + " > " + (String) docInfo.get("actBldgNm") + " > " + (String) docInfo.get("actLocateNm") + " > " + (String) docInfo.get("actGate");
                    }
                }

                for (int i = 0; i < mailList.size(); i++) {

                    mailMap = new HashMap<String, String>();

                    ofendEmpId = (String) mailList.get(0).get("empId");

                    mailMap.put("ofendEmpId", (String) mailList.get(0).get("empId"));    // 위규자 사번 : 필수
                    mailMap.put("ofendEmpNm", (String) mailList.get(0).get("empNm"));    // 위규자 이름 : 필수
                    mailMap.put("ofendEmpEmail", (String) mailList.get(0).get("email"));  // 위규자 메일 : 필수

                    mailMap.put("ofendDeptNm", ofendDeptNm);
                    mailMap.put("ofendJwNm", ofendJwNm);
                    mailMap.put("ofendDt", ofendDt);
                    mailMap.put("ofendTm", ofendTm);
                    mailMap.put("ofendGbnNm", ofendGbnNm);
                    mailMap.put("ofendDetailNm", ofendDetailNm);
                    mailMap.put("ofendDetailCd", ofendDetailCd);
                    mailMap.put("ofendSubNm", ofendSubNm);
                    mailMap.put("schemaNm", "보안위규자");
                    mailMap.put("docNm", "보안 위규자");          // 문서 명 : 필수
                    mailMap.put("crtBy", (String) dsActDoData.get("empId"));    // 로그인ID
                    mailMap.put("acIp", (String) dsActDoData.get("acIp"));    // AC_IP
                    mailMap.put("ofendLoc", ofendLoc); // 장소

                    mailMap.put("limit14Dtm", limit14Dtm); // 14일
                    mailMap.put("limit30Dtm", limit30Dtm); // 30일

                    mailMap.put("remark", remark); // 비고/산업보안팀 의견

                    if ("1".equals(mailList.get(i).get("gubun"))) { // 위규자

                        mailMap.put("toEmpId", (String) mailList.get(i).get("empId"));    // 메일 수신자 사번 : 필수
                        mailMap.put("toEmpNm", (String) mailList.get(i).get("empNm"));    // 메일 수신자 이름 : 필수
                        mailMap.put("toEmpEmail", (String) mailList.get(i).get("email"));  // 메일 수신자 메일주소 : 필수
                        mailMap.put("scrtView", "SCRT_VIEW");

                        fmSecCoEmpViolationJingGuMailSend(mailMap);
                    }
                    else if ("2".equals(mailList.get(i).get("gubun"))) { // 팀장
                        mailMap.put("toEmpId", (String) mailList.get(i).get("empId"));    // 메일 수신자 사번 : 필수
                        mailMap.put("toEmpNm", (String) mailList.get(i).get("empNm"));    // 메일 수신자 이름 : 필수
                        mailMap.put("toEmpEmail", (String) mailList.get(i).get("email"));    // 메일 수신자 메일주소 : 필수
                        mailMap.put("scrtView", "SCRT_VIEWUPPER_SEC");

                        fmSecCoEmpViolationJingGuMailSend(mailMap);
                    }
                    else if ("3".equals(mailList.get(i).get("gubun"))) { //보안 담당자
                        mailMap.put("toEmpId", (String) mailList.get(i).get("empId"));    // 메일 수신자 사번 : 필수
                        mailMap.put("toEmpNm", (String) mailList.get(i).get("empNm"));    // 메일 수신자 이름 : 필수
                        mailMap.put("toEmpEmail", (String) mailList.get(i).get("email"));    // 메일 수신자 메일주소 : 필수
                        mailMap.put("scrtView", "SCRT_VIEWUPPER_SEC");

                        fmSecCoEmpViolationJingGuMailSend(mailMap);
                    }
                }
            }
            else if ("C0280003".equals(actDo)) { // 경고장 메일 발송

                /* 경고장 안내메일 내용 Get */
                String ofendEmpId = "";
                String ofendDeptNm = "";
                String ofendJwNm = "";
                String ofendDt = "";
                String ofendTm = "";
                String ofendGbnNm = "";
                String ofendDetailNm = "";
                String ofendGbn = "";
                String actDoNm = "";
                String ofendLoc = "";
                String limit14Dtm = ""; // 14일
                String limit30Dtm = ""; // 30일

                String remark = ""; //비고/산업보안팀 의견

                Map<String, Object> docInfo = repository.dmSecCoEmp_Violation_Info(dsActDoData);

                if (docInfo != null) {
                    ofendDeptNm = (String) docInfo.get("ofendDeptNm");
                    ofendJwNm = (String) docInfo.get("ofendJwNm");
                    ofendDt = (String) docInfo.get("ofendDt");
                    ofendTm = (String) docInfo.get("ofendTm");
                    ofendGbnNm = (String) docInfo.get("ofendGbnNm");
                    ofendDetailNm = (String) docInfo.get("ofendDetailNm");
                    ofendGbn = (String) docInfo.get("ofendGbn");
                    actDoNm = (String) docInfo.get("actDoNm");
                    limit14Dtm = (String) docInfo.get("limit14Dtm");
                    limit30Dtm = (String) docInfo.get("limit30Dtm");

                    remark = (String) docInfo.get("remark");

                    if ("".equals((String) docInfo.get("actGate"))) {
                        ofendLoc = (String) docInfo.get("ofendCompNm") + " > " + (String) docInfo.get("actBldgNm") + " > " + (String) docInfo.get("actLocateNm");
                    }
                    else {
                        ofendLoc =
                            (String) docInfo.get("ofendCompNm") + " > " + (String) docInfo.get("actBldgNm") + " > " + (String) docInfo.get("actLocateNm") + " > " + (String) docInfo.get("actGate");
                    }
                }

                /* 위규자 E-MAIL 발송 시작 */
                List<Map<String, Object>> mailList = repository.dmSecCoEmp_Violation_SendMail_List_S(dsActDoData);

                mailMap = new HashMap<String, String>();

                mailMap.put("ofendCompNm", (String) dsActDoData.get("ofendCompNm")); //위반 소속사명
                mailMap.put("ofendText", (String) dsActDoData.get("ofendText")); // 위규내용
                mailMap.put("ofendEmpId", (String) dsActDoData.get("ofendEmpId"));
                mailMap.put("ofendEmpNm", (String) dsActDoData.get("ofendEmpNm"));
                mailMap.put("ofendEmpEmail", (String) dsActDoData.get("ofendEmpEmail"));
                mailMap.put("schemaNm", "보안위규자");
                mailMap.put("docNm", "보안 위규자");          // 문서 명 : 필수
                mailMap.put("crtBy", (String) dsActDoData.get("crtBy"));        // 로그인ID
                mailMap.put("acIp", (String) dsActDoData.get("acIp"));        // AC_IP

                mailMap.put("ofendJwNm", ofendJwNm);
                mailMap.put("ofendDt", ofendDt);
                mailMap.put("ofendTm", ofendTm);
                mailMap.put("ofendGbnNm", ofendGbnNm);
                mailMap.put("ofendDetailNm", ofendDetailNm);
                mailMap.put("ofendLoc", ofendLoc); // 장소
                mailMap.put("limit14Dtm", limit14Dtm); // 14일
                mailMap.put("limit30Dtm", limit30Dtm); // 30일

                mailMap.put("remark", remark); // 비고/산업보안팀 의견

                for (int i = 0; i < mailList.size(); i++) {

                    if ("1".equals(mailList.get(i).get("gubun"))) { // 위규자

                        mailMap.put("toEmpId", (String) mailList.get(i).get("empId"));    // 메일 수신자 사번 : 필수
                        mailMap.put("toEmpNm", (String) mailList.get(i).get("empNm"));    // 메일 수신자 이름 : 필수
                        mailMap.put("toEmpEmail", (String) mailList.get(i).get("email"));  // 메일 수신자 메일주소 : 필수
                        mailMap.put("scrtView", "SCRT_VIEW");

                        // 경고장 메일 발송 (관련 메일 컨텐츠 필요)
                        fmSecCoEmpViolationWarningInfoMailSend(mailMap);
                        fmSecCoEmpViolationWarningMailSend(mailMap);
                    }
                    else if ("2".equals(mailList.get(i).get("gubun"))) { // 팀장
                        mailMap.put("toEmpId", (String) mailList.get(i).get("empId"));    // 메일 수신자 사번 : 필수
                        mailMap.put("toEmpNm", (String) mailList.get(i).get("empNm"));    // 메일 수신자 이름 : 필수
                        mailMap.put("toEmpEmail", (String) mailList.get(i).get("email"));    // 메일 수신자 메일주소 : 필수
                        mailMap.put("scrtView", "SCRT_VIEWUPPER_SEC");

                        // 경고장 메일 발송 (관련 메일 컨텐츠 필요)
                        fmSecCoEmpViolationWarningInfoMailSend(mailMap);
                        fmSecCoEmpViolationWarningMailSend(mailMap);
                    }
                    else if ("3".equals(mailList.get(i).get("gubun"))) { //보안 담당자
                        mailMap.put("toEmpId", (String) mailList.get(i).get("empId"));    // 메일 수신자 사번 : 필수
                        mailMap.put("toEmpNm", (String) mailList.get(i).get("empNm"));    // 메일 수신자 이름 : 필수
                        mailMap.put("toEmpEmail", (String) mailList.get(i).get("email"));    // 메일 수신자 메일주소 : 필수
                        mailMap.put("scrtView", "SCRT_VIEWUPPER_SEC");

                        // 경고장 메일 발송 (관련 메일 컨텐츠 필요)
                        fmSecCoEmpViolationWarningInfoMailSend(mailMap);
                        fmSecCoEmpViolationWarningMailSend(mailMap);
                    }
                }
            }
            else if ("C0280001".equals(actDo)) { // 구성원 확인

                List<Map<String, Object>> mailList = repository.dmSecCoEmp_Violation_SendMail_List_S(dsActDoData);
                String ofendEmpId = "";

                Map<String, Object> docInfo = repository.dmSecCoEmp_Violation_Info(dsActDoData);

                String ofendDeptNm = "";
                String ofendJwNm = "";
                String ofendDt = "";
                String ofendTm = "";
                String ofendGbnNm = "";
                String ofendDetailNm = "";
                String ofendDetailCd = "";
                String ofendSubNm = "";
                String ofendGbn = "";
                String actDoNm = "";
                String ofendLoc = "";
                String remark = "";

                if (docInfo != null) {
                    ofendDeptNm = (String) docInfo.get("ofendDeptNm");
                    ofendJwNm = (String) docInfo.get("ofendJwNm");
                    ofendDt = (String) docInfo.get("ofendDt");
                    ofendTm = (String) docInfo.get("ofendTm");
                    ofendGbnNm = (String) docInfo.get("ofendGbnNm");
                    ofendDetailNm = (String) docInfo.get("ofendDetailNm");
                    ofendDetailCd = (String) docInfo.get("ofendDetailCd");
                    ofendSubNm = (String) docInfo.get("ofendSubNm");
                    ofendGbn = (String) docInfo.get("ofendGbn");
                    actDoNm = (String) docInfo.get("actDoNm");
                    remark = (String) docInfo.get("remark");

                    if ("".equals((String) docInfo.get("actGate"))) {
                        ofendLoc = (String) docInfo.get("ofendCompNm") + " > " + (String) docInfo.get("actBldgNm") + " > " + (String) docInfo.get("actLocateNm");
                    }
                    else {
                        ofendLoc =
                            (String) docInfo.get("ofendCompNm") + " > " + (String) docInfo.get("actBldgNm") + " > " + (String) docInfo.get("actLocateNm") + " > " + (String) docInfo.get("actGate");
                    }
                }

                for (int i = 0; i < mailList.size(); i++) {
                    mailMap = new HashMap<String, String>();

                    ofendEmpId = (String) mailList.get(0).get("empId");

                    mailMap.put("ofendEmpId", (String) mailList.get(0).get("empId"));    // 위규자 사번 : 필수
                    mailMap.put("ofendEmpNm", (String) mailList.get(0).get("empNm"));    // 위규자 이름 : 필수
                    mailMap.put("ofendEmpEmail", (String) mailList.get(0).get("email"));  // 위규자 메일 : 필수

                    mailMap.put("ofendDeptNm", ofendDeptNm);
                    mailMap.put("ofendJwNm", ofendJwNm);
                    mailMap.put("ofendDt", ofendDt);
                    mailMap.put("ofendTm", ofendTm);
                    mailMap.put("ofendGbnNm", ofendGbnNm);
                    mailMap.put("ofendDetailNm", ofendDetailNm);
                    mailMap.put("ofendDetailCd", ofendDetailCd);
                    mailMap.put("ofendSubNm", ofendSubNm);
                    mailMap.put("schemaNm", "보안위규자");
                    mailMap.put("docNm", "보안 위규자");          // 문서 명 : 필수
                    mailMap.put("crtBy", (String) dsActDoData.get("empId"));    // 로그인ID
                    mailMap.put("acIp", (String) dsActDoData.get("acIp"));    // AC_IP
                    mailMap.put("actDoNm", actDoNm);
                    mailMap.put("ofendLoc", ofendLoc); // 장소
                    mailMap.put("remark", remark); // 비고/산업보안팀 의견

                    if ("1".equals(mailList.get(i).get("gubun"))) { // 위규자

                        mailMap.put("toEmpId", (String) mailList.get(i).get("empId"));    // 메일 수신자 사번 : 필수
                        mailMap.put("toEmpNm", (String) mailList.get(i).get("empNm"));    // 메일 수신자 이름 : 필수
                        mailMap.put("toEmpEmail", (String) mailList.get(i).get("email"));  // 메일 수신자 메일주소 : 필수
                        mailMap.put("scrtView", "SCRT_VIEW");

                        // 경고장 메일 발송 (관련 메일 컨텐츠 필요)
                        fmSecCoEmpViolationGuVerifyMailSend(mailMap);
                    }
                }
            }
            else if ("C0280004".equals(actDo)) { // 인사징계의뢰 메일 발송

                /* 인사징계의뢰 안내메일 내용 Get */
                String ofendDeptNm = "";
                String ofendJwNm = "";
                String ofendDt = "";
                String ofendTm = "";
                String ofendGbnNm = "";
                String ofendDetailNm = "";
                String ofendDetailCd = "";
                String ofendSubNm = "";
                String ofendGbn = "";
                String actDoNm = "";
                String ofendLoc = "";
                String remark = "";

                Map<String, Object> docInfo = repository.dmSecCoEmp_Violation_Info(dsActDoData);

                if (docInfo != null) {
                    ofendDeptNm = (String) docInfo.get("ofendDeptNm");
                    ofendJwNm = (String) docInfo.get("ofendJwNm");
                    ofendDt = (String) docInfo.get("ofendDt");
                    ofendTm = (String) docInfo.get("ofendTm");
                    ofendGbnNm = (String) docInfo.get("ofendGbnNm");
                    ofendDetailNm = (String) docInfo.get("ofendDetailNm");
                    ofendDetailCd = (String) docInfo.get("ofendDetailCd");
                    ofendSubNm = (String) docInfo.get("ofendSubNm");
                    ofendGbn = (String) docInfo.get("ofendGbn");
                    actDoNm = (String) docInfo.get("actDoNm");
                    remark = (String) docInfo.get("remark");

                    if ("".equals((String) docInfo.get("actGate"))) {
                        ofendLoc = (String) docInfo.get("actCompNm") + " > " + (String) docInfo.get("actBldgNm") + " > " + (String) docInfo.get("actLocateNm");
                    }
                    else {
                        ofendLoc =
                            (String) docInfo.get("actCompNm") + " > " + (String) docInfo.get("actBldgNm") + " > " + (String) docInfo.get("actLocateNm") + " > " + (String) docInfo.get("actGate");
                    }
                }

                /* 위규자 E-MAIL 발송 시작 */
                List<Map<String, Object>> mailList = repository.dmSecCoEmp_Violation_SendMail_List_S(dsActDoData);
                mailMap = new HashMap<String, String>();

                mailMap.put("ofendCompNm", (String) dsActDoData.get("ofendCompNm")); //위반 소속사명
                mailMap.put("ofendText", (String) dsActDoData.get("ofendText")); // 위규내용
                mailMap.put("ofendEmpId", (String) dsActDoData.get("ofendEmpId"));
                mailMap.put("ofendEmpNm", (String) dsActDoData.get("ofendEmpNm"));
                mailMap.put("ofendEmpEmail", (String) dsActDoData.get("ofendEmpEmail"));
                mailMap.put("schemaNm", "보안위규자");
                mailMap.put("docNm", "보안 위규자");          // 문서 명 : 필수
                mailMap.put("crtBy", (String) dsActDoData.get("crtBy"));        // 로그인ID
                mailMap.put("acIp", (String) dsActDoData.get("acIp"));        // AC_IP

                mailMap.put("ofendJwNm", ofendJwNm);
                mailMap.put("ofendDt", ofendDt);
                mailMap.put("ofendTm", ofendTm);
                mailMap.put("ofendGbnNm", ofendGbnNm);
                mailMap.put("ofendDetailNm", ofendDetailNm);
                mailMap.put("ofendDetailCd", ofendDetailCd);
                mailMap.put("ofendSubNm", ofendSubNm);
                mailMap.put("ofendLoc", ofendLoc); // 장소
                mailMap.put("remark", remark); // 비고/산업보안팀 의견

                for (int i = 0; i < mailList.size(); i++) {
                    if ("1".equals(mailList.get(i).get("gubun"))) { // 위규자

                        mailMap.put("toEmpId", (String) mailList.get(i).get("empId"));    // 메일 수신자 사번 : 필수
                        mailMap.put("toEmpNm", (String) mailList.get(i).get("empNm"));    // 메일 수신자 이름 : 필수
                        mailMap.put("toEmpEmail", (String) mailList.get(i).get("email"));  // 메일 수신자 메일주소 : 필수
                        mailMap.put("scrtView", "SCRT_VIEW");

                        // 경고장 메일 발송 (관련 메일 컨텐츠 필요)
                        fmSecCoEmpViolationInsaInfoMailSend(mailMap);
                    }
                    else if ("2".equals(mailList.get(i).get("gubun"))) { // 팀장

                        mailMap.put("toEmpId", (String) mailList.get(i).get("empId"));    // 메일 수신자 사번 : 필수
                        mailMap.put("toEmpNm", (String) mailList.get(i).get("empNm"));    // 메일 수신자 이름 : 필수
                        mailMap.put("toEmpEmail", (String) mailList.get(i).get("email"));  // 메일 수신자 메일주소 : 필수
                        mailMap.put("scrtView", "SCRT_VIEWUPPER_SEC");

                        // 경고장 메일 발송 (관련 메일 컨텐츠 필요)
                        fmSecCoEmpViolationInsaInfoMailSend(mailMap);
                    }
                    else if ("3".equals(mailList.get(i).get("gubun"))) { // 보안 담당자

                        mailMap.put("toEmpId", (String) mailList.get(i).get("empId"));    // 메일 수신자 사번 : 필수
                        mailMap.put("toEmpNm", (String) mailList.get(i).get("empNm"));    // 메일 수신자 이름 : 필수
                        mailMap.put("toEmpEmail", (String) mailList.get(i).get("email"));  // 메일 수신자 메일주소 : 필수
                        mailMap.put("scrtView", "SCRT_VIEWUPPER_SEC");

                        // 경고장 메일 발송 (관련 메일 컨텐츠 필요)
                        fmSecCoEmpViolationInsaInfoMailSend(mailMap);
                    }
                }
            }

            // 구성원인 경우조치 사항 누적수 확인
            Map<String, Object> resultMap4 = repository.dmSecCoEmpViolation_Accum_S(dsActDoData);
            if (resultMap4 != null) {
                String etc1 = String.valueOf(resultMap4.get("etc1")); //기준횟수
                String etc2 = String.valueOf(resultMap4.get("etc2")); //이후로직
                String expCnt = String.valueOf(resultMap4.get("expCnt")); //전체조치건수
                String chkCnt = String.valueOf(resultMap4.get("chkCnt")); //체크조치건수

                int iEtc1 = Integer.parseInt(etc1);
                int iExpCnt = Integer.parseInt(expCnt);
                int iChkCnt = Integer.parseInt(chkCnt);

                // 기준횟수 없고 이후 로직이 없으면 종료
                if (!("0".equals(etc1) || "0".equals(etc2))) {
                    if (iEtc1 <= iChkCnt) { // 보안위규 횟수를 넘긴 경우
                        // SC_OFEND 테이블 NEXT INSERT 수행
                        //채번
                        int nextScDocNo = repository.getScDocNo(); /*dmSecCoEmp_Violation_Seq_S*/
                        dsActDoData.put("nextScDocNo", nextScDocNo);

                        dsActDoData.put("nextOfendDetailGbn", etc2);

                        //20210112: Cheyminjung 에 대한 특별 대우로, 보안위규 등록 되지 않도록 처리할것. 물리보안팀 요청 (이명주 TL)
                        if (!dsActDoData.get("ofendEmpId").equals("2073479")) {
                            int resultNextCnt = repository.dmSecCoEmp_Violation_Next_I(dsActDoData);

                            /* 시정계획서 누적건수 CLEAR 시킴 */
                            int resultClear = repository.dmSecCoEmpViolation_Accum_Clear_U(dsActDoData);
                        }
                    }
                }
                // 구성원인 경우조치 사항 누적수 확인
            }

            if (resultCnt < 1 || resultCnt2 < 1) {
                result = false;
            }

            resultMap.put("result", result);

            return resultMap;
        }
    }

    private void fmSecCoEmpViolationInsaInfoMailSend(Map<String, String> mailMap) {

        String schemaNm = StringUtils.defaultIfEmpty(mailMap.get("schemaNm"), "");
        String sendFromMail = "skhystec_security@skhystec.com";
        String sendFromName = mailMap.get("ofendEmpNm");
        String sendFromID = mailMap.get("crtBy");
        //String sendFromEmail = mailMap.get("ofendEmpEmail");
        String scrtView = mailMap.get("scrtView");
        if (scrtView == null) { scrtView = ""; }

        //String ofendDeptNm = mailMap.get("ofendDeptNm");
        String ofendJwNm = mailMap.get("ofendJwNm");
        String ofendDt = mailMap.get("ofendDt");
        String ofendTm = mailMap.get("ofendTm");
        //String ofendGbnNm = mailMap.get("ofendGbnNm");
        String ofendDetailNm = mailMap.get("ofendDetailNm");
        String ofendLocNm = mailMap.get("ofendLoc"); // 장소

        //String sendToName = mailMap.get("toEmpNm");
        //String sendToID = mailMap.get("toEmpId");
        String sendToEmail = mailMap.get("toEmpEmail");
        String toCCEmail = mailMap.get("toCcEmail");
        //String remark = mailMap.get("remark").replaceAll("\n", "<BR/>");

        String domain = securityInsnetUrl;

        String title = "[e-Security] 보안위규 관련, 인사징계 의뢰 예정";
        String shotURL = domain + "?SCHEMAID=SCRT_VIEW";

        String message = sendFromName + "님의 아래 보안위규 건 관련하여 귀사 인사주관부서에 징계의뢰 요청드리오니 업무협조 바랍니다.해당 구성원의 팀장님께서는 징계의뢰 계획서를 14일 이내 제출 바랍니다.";

        if (scrtView.equals("SCRT_VIEWUPPER_SEC")) {
            shotURL = domain + "?SCHEMAID=SCRT_VIEWUPPER_SEC";
        }

        StringBuffer str = new StringBuffer();

        str.append(" <!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'> \n");
        str.append(" <html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"ko\" lang=\"ko\"> \n");
        str.append(" <head> \n");
        str.append(" <title>입주사-행복한 만남 - SK쉴더스</title> \n");
        str.append(" <style>			 \n");
        str.append(" body { margin:0; padding:0; border:0; font-size:0.75em; line-height:1.8em; letter-spacing:0px; color:#7D7D7D;			scrollbar-3dlight-color:#ffffff; scrollbar-arrow-color:#666666; scrollbar-base-color:#ffffff; scrollbar-darkshadow-color:#d7d7d7;			scrollbar-face-color:#f2f2f2; scrollbar-highlight-color:#f2f2f2;	scrollbar-shadow-color:#f2f2f2;	scrollbar-track-color:#f2f2f2; }			 \n");
        str.append(" *html, body{height:100%; overflow-x:hidden;}			 \n");
        str.append(" body, table	 {width:100%;margin:0px;padding:0px;text-align:left}			 \n");
        str.append(" body, select, input,textarea{margin:0px;padding:0px; font-family:'나눔고딕', '맑은 고딕', 'Malgun Gothic', NanumGothicBold, AppleGothic, 'Lucida Grande', Tahoma, Verdana, UnDotum, Dotum, sans-serif;}			 \n");
        str.append(" div, p, ul, li, dl, dt, dd, h1, h2, h3, h4, h5, form	{margin:0px;padding:0px;list-style:none}			 \n");
        str.append(" img, table {border:0px none; font-size:13px;}			 \n");
        str.append(" a {border:0px none}			 \n");
        str.append(" a,img,input {text-align:absmiddle;margin:0px;padding:0px}			 \n");
        str.append(" select{font-size:1em;}			 \n");
        str.append(" @font-face{font-family:NanumGothic; src:url(http://www.skhystec.com/down/esecurity/common/css/NanumGothic.ttf); src:url(http://www.skhystec.com/down/esecurity/common/css/NanumGothic.ttf)  format(truetype);}			@font-face{font-family:NanumGothic; src:url(http://www.skhystec.com/down/esecurity/common/css/NanumGothic.eot); src: url(http://www.skhystec.com/down/esecurity/common/css/NanumGothic.eot?iefix) format(eot);}			@font-face {font-family:NanumGothicBold; src: url(http://www.skhystec.com/down/esecurity/common/css/NanumGothicBold.eot); src: url(http://www.skhystec.com/down/esecurity/common/css/NanumGothicBold.eot?iefix) format(eot), url(http://www.skhystec.com/down/esecurity/common/css/NanumGothicBold.woff) format(woff), url(http://www.skhystec.com/down/esecurity/common/css/font/NanumGothicBold.ttf)  format(truetype), url(http://www.skhystec.com/down/esecurity/common/css/font/NanumGothicBold.svg#svgFontName) format(svg);}			 \n");
        str.append(" .yellow01 { color:#DD9619; font-weight: bold}			 \n");
        str.append(" #popBody01_security {width:700px; margin:30px auto;  padding-bottom:10px; font-family:'나눔고딕', AppleGothic, '맑은 고딕', 'Malgun Gothic', 'Lucida Grande', Tahoma, Verdana,  UnDotum, Dotum, sans-serif;}			 \n");
        str.append(" #popBody01_security #popArea_security {border-bottom:4px solid #F57724; overflow:hidden; }			 \n");
        str.append(" #popBody01_security .pop_title01_security{font-size:1.4em; padding:10px 0 5px 0; color:#F57724; font-weight:bold; letter-spacing:0px; overflow:hidden; height: 80px;}			#popBody01_security .pop_title01_security img{float:left;}			 \n");
        str.append(" #popBody01_security .pop_title01_security span{float:right; text-align:right; margin-top:33px; border:0px solid #000; width:550px;}			 \n");
        str.append(" #popBody01_security .close{float:right; padding:25px 10px 0 0px;}			 \n");
        str.append(" #popBody01_security .pop_content_security{margin:0px auto 10px auto; border:0px solid #000; background:#fff;}			 \n");
        str.append(" .mail_content_security{ width:700px; padding:50px 10px; border:1px solid #DFDFDF; overflow:hidden; margin:30px auto; background:#fff;} 			 \n");
        str.append(" .mail_left_security{float:left; margin-right:20px; }			 \n");
        str.append(" .mail_right_security{float:right; width:470px; border-left:1px dotted #A0B9A9; padding-left:20px; overflow:hidden; font-size:13px;}			 \n");
        str.append(" .mail_right_security dl{border-top:2px solid #000; border-bottom:2px solid #000; padding:10px; overflow:hidden; margin:10px 0 10px 0; font-weight:bold; color:#DD9619;}			.mail_right_security dt{float:left;}			 \n");
        str.append(" .mail_right_security dd{float:left; margin-left:10px;}			 \n");
        str.append(" .mail_title{font-size:14px; font-weight:bold; margin:10px 0; color:#000; padding:0 0 5px 10px; border-bottom:1px solid #DFDFDF; background:url('http://www.skhystec.com/down/esecurity/common/images/common/title06.png') no-repeat 0 3px;}			 \n");
        str.append(" .notice_01{margin-top:25px; padding-left:50px; background:url('http://www.skhystec.com/down/esecurity/common/images/common/notice.jpg') no-repeat 0 5px;}			 \n");
        str.append(" .notice_01 a:link{color:#5EA0D0; font-weight:bold; text-decoration:underline;}			 \n");
        str.append(" .mail_bottom_security{clear:both; font-size:1em; border-top:3px solid #C4C4C4; padding-top:10px; line-height:1.4em;}		 \n");
        str.append(" </style> \n");
        str.append(" </head> \n");
        str.append(" <body> \n");
        str.append(" <div id=\"popBody01_security\"> \n");
        str.append(" <div id=\"popArea_security\"> \n");
        str.append(" <div class=\"pop_title01_security\"><img src=\"http://www.skhystec.com/down/esecurity/common/images/common/mail_logo.jpg\"><span>" + title + "</span></div> \n");
        str.append(" </div> \n");
        str.append(" <div class=\"pop_content_security\"> \n");
        str.append(" <div class=\"mail_content_security\"> \n");
        str.append(" <div class=\"mail_left_security\"><img src=\"http://www.skhystec.com/down/esecurity/common/images/common/mail_img01.jpg\"> \n");
        str.append(" </div> \n");
        str.append(" <div class=\"mail_right_security\"> \n");
        str.append(" 	<div style=\" padding:4px 0px;line-height:15px;\"><span style=\"font-weight:bold;\">" + sendFromName + "</span>님의 아래 보안위규 건 관련하여 귀사 인사주관부서에 징계의뢰 요청드리오니 업무협조 바랍니다.해당 구성원의 팀장님께서는 징계의뢰 계획서를 14일 이내 제출 바랍니다.</div> \n");
        str.append(" 	<div style=\" padding:4px 0px;line-height:15px;\">산업보안 포탈 사이트 : <a target='_blank' href=\"" + shotURL + "\">security.skshieldus.com</a></div> \n");
        str.append(" <br> \n");
        str.append(" <table border=\"1\" bordercolor=\"#e7d1c2\" cellspacing=\"0\" cellpadding=\"5\" style=\"border-collapse:collapse;\"> \n");
        str.append(" <tbody> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">성명 / 직위</span></td> \n");
        str.append(" <td align=\"left\" width=\"80%\">" + sendFromName + " " + ofendJwNm + "</td> \n");
        str.append(" </tr> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">일시</span></td> \n");
        str.append(" <td align=\"left\">" + ofendDt + " " + ofendTm + "</td> \n");
        str.append(" </tr> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">장소</span></td> \n");
        str.append(" <td align=\"left\">" + ofendLocNm + "</td> \n");
        str.append(" </tr> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">위규 내용</span></td> \n");
        str.append(" <td align=\"left\">" + ofendDetailNm + "</td> \n");
        str.append(" </tr> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">위규 처리</span></td> \n");
        str.append(" <td align=\"left\"><span style=\"color:#f57724\">해당 구성원의 팀장 징계의뢰 계획서 14일 이내 제출</span></td> \n");
        str.append(" </tr> \n");
        /*
         * str.append(" <tr> \n"); str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"30%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">처리 기준</span></td> \n"); str.append(" <td align=\"left\">징계의뢰</td> \n"); str.append(" </tr> \n");
         */
        str.append(" </tbody> \n");
        str.append(" </table> \n");
        str.append(" </div> \n");
        str.append(" </div> \n");
        str.append(" <div class=\"mail_bottom_security\"><br> \n");
        str.append(" <br> \n");
        str.append(" 본 메일은 발신전용 메일입니다.<br> \n");
        str.append(" <strong>SK쉴더스</strong> 경기도 이천시 부발읍 경충대로 2091 (TEL 031.5185.4114)<br> \n");
        str.append(" Copyrightⓒ SK SHIELDUS Inc. All Rights Reserved.</div> \n");
        str.append(" </div> \n");
        str.append(" </div> \n");
        str.append(" </body> \n");
        str.append(" </html> \n");

        boolean result = mailing.sendMail(title, mailing.applyMailTemplate(title, str.toString()), sendToEmail, "", schemaNm, "", (String) mailMap.get("acIp"));
    }

    private void fmSecCoEmpViolationWarningMailSend(Map<String, String> mailMap) {
        String schemaNm = StringUtils.defaultIfEmpty(mailMap.get("schemaNm"), "");
        String sendFromMail = "skhystec_security@skhystec.com";
        String sendFromName = mailMap.get("ofendEmpNm");
        String sendFromID = mailMap.get("crtBy");
        //String sendFromEmail = mailMap.get("ofendEmpEmail");
        String scrtView = mailMap.get("scrtView");

        String ofendCompNm = mailMap.get("ofendCompNm");
        String ofendText = mailMap.get("ofendText");

        String yyyyText = new SimpleDateFormat("yyyy").format(new Date()).toString();
        String mmText = new SimpleDateFormat("MM").format(new Date()).toString();
        String ddText = new SimpleDateFormat("dd").format(new Date()).toString();

        if (scrtView == null) { scrtView = ""; }

        //String sendToName = mailMap.get("toEmpNm");
       // String sendToID = mailMap.get("toEmpId");
        String sendToEmail = mailMap.get("toEmpEmail");
        String toCCEmail = mailMap.get("toCcEmail");
        //String remark = mailMap.get("remark").replaceAll("\n", "<BR/>");

        String domain = securityInsnetUrl;

        String title = "[e-Security]" + sendFromName + "님께 경고장이 발송되었습니다.";
        String message = sendFromName + "님께서 " + mailMap.get("docNm") + "로 적발되었습니다.<br />";

        message += "입주사 Security에 접속하셔서 확인하시기 바랍니다.<br />";
        message += "<a href='" + domain + "' target='_new'>e-Security 바로가기</a>";

        //String linkName = "";

        String shotURL = domain + "?SCHEMAID=SCRT_VIEW";
        String shotURL2 = "";

        if("SCRT_VIEW".equals(scrtView)){
            shotURL2 = "<a target='_blank' href=\"" + shotURL + "\"><img src='http://www.skhystec.com/down/esecurity/common/images/common/warning_mail/btn_link.png'/></a>";
        }

        StringBuffer str = new StringBuffer();
        //=========== 구성원 경고장 메일 컨텐츠 =================//
        str.append("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>");
        str.append("<html xmlns='http://www.w3.org/1999/xhtml' xml:lang='ko' lang='ko'>");
        str.append("<head>");
        str.append("<title>입주사-행복한 만남 - SK쉴더스</title>");
        str.append("<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />");
        str.append("<meta name='title' content='e-security' />");
        str.append("<meta name='Author' content='SK SHIELDUS' />");
        str.append("<meta name='description' content='e-security' />");
        str.append("<meta name='keywords' content='e-security' />");
        str.append("<meta http-equiv='X-UA-Compatible' content='IE=Edge'>");
        str.append("<meta name='viewport' content=' initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0,width=device-width'>");
        str.append("<style>");
        str.append("body { margin:0; padding:0; border:0; font-size:0.75em; line-height:1.8em; letter-spacing:0px; color:#7D7D7D;");
        str.append("scrollbar-3dlight-color:#ffffff; scrollbar-arrow-color:#666666; scrollbar-base-color:#ffffff; scrollbar-darkshadow-color:#d7d7d7;");
        str.append("scrollbar-face-color:#f2f2f2; scrollbar-highlight-color:#f2f2f2;	scrollbar-shadow-color:#f2f2f2;	scrollbar-track-color:#f2f2f2; }");
        str.append("*html, body{height:100%; overflow-x:hidden;}");
        str.append("body, table	 {width:100%;margin:0px;padding:0px;text-align:left}");
        str.append("body, select, input,textarea{margin:0px;padding:0px; font-family:'나눔고딕', '맑은 고딕', 'Malgun Gothic', NanumGothicBold, AppleGothic, 'Lucida Grande', Tahoma, Verdana, UnDotum, Dotum, sans-serif;}");
        str.append("div, p, ul, li, dl, dt, dd, h1, h2, h3, h4, h5, form	{margin:0px;padding:0px;list-style:none}");
        str.append("img, table {border:0px none}");
        str.append("a {border:0px none}");
        str.append("a,img,input {text-align:absmiddle;margin:0px;padding:0px;}");
        str.append("select{font-size:1em;}");
        str.append("@font-face{font-family:NanumGothic; src:url(http://www.skhystec.com/down/esecurity/common/css/NanumGothic.ttf); src:url(http://www.skhystec.com/down/esecurity/common/css/NanumGothic.ttf)  format(truetype);}");
        str.append("@font-face{font-family:NanumGothic; src:url(http://www.skhystec.com/down/esecurity/common/css/NanumGothic.eot); src: url(http://www.skhystec.com/down/esecurity/common/css/NanumGothic.eot?iefix) format(eot);}");
        str.append("@font-face {font-family:NanumGothicBold; src: url(http://www.skhystec.com/down/esecurity/common/css/NanumGothicBold.eot); src: url(http://www.skhystec.com/down/esecurity/common/css/NanumGothicBold.eot?iefix) format(eot), url(http://www.skhystec.com/down/esecurity/common/css/NanumGothicBold.woff) format(woff), url(http://www.skhystec.com/down/esecurity/common/css/font/NanumGothicBold.ttf)  format(truetype), url(http://www.skhystec.com/down/esecurity/common/css/font/NanumGothicBold.svg#svgFontName) format(svg);}");
        str.append(".yellow01 { color:#DD9619; font-weight: bold} ");
        str.append("#popBody01_security {width:636px; margin:30px auto;  padding-bottom:10px; font-family:'나눔고딕', AppleGothic, '맑은 고딕', 'Malgun Gothic', 'Lucida Grande', Tahoma, Verdana,  UnDotum, Dotum, sans-serif;}");
        str.append("#popBody01_security #popArea_security {border-bottom:4px solid #F57724; overflow:hidden; }");
        str.append("#popBody01_security .pop_title01_security{font-size:1.4em; padding:10px 0 5px 0; color:#F57724; font-weight:bold; letter-spacing:0px; overflow:hidden; height: 80px;}");
        str.append("#popBody01_security .pop_title01_security img{float:left;}");
        str.append("#popBody01_security .pop_title01_security span{float:right; text-align:right; margin-top:33px; border:0px solid #000; width:550px;}");
        str.append("#popBody01_security .close{float:right; padding:25px 10px 0 0px;}");
        str.append("#popBody01_security .pop_content_security{margin:0px auto 10px auto; border:0px solid #000; background:#fff;}");
        str.append(".mail_content_security{ width:700px; padding:50px 30px; border:1px solid #DFDFDF; overflow:hidden; margin:30px auto; background:#fff;} ");
        str.append(".mail_left_security{float:left; margin-right:20px; }");
        str.append(".mail_right_security{float:right; width:420px; border-left:1px dotted #A0B9A9; padding-left:20px; overflow:hidden; font-size:12px;}");
        str.append(".mail_right_security dl{border-top:2px solid #000; border-bottom:2px solid #000; padding:10px; overflow:hidden; margin:10px 0 10px 0; font-weight:bold; color:#DD9619;}");
        str.append(".mail_right_security dt{float:left;}");
        str.append(".mail_right_security dd{float:left; margin-left:10px;}");
        str.append(".mail_title{font-size:14px; font-weight:bold; margin:10px 0; color:#000; padding:0 0 5px 10px; border-bottom:1px solid #DFDFDF; background:url('http://www.skhystec.com/down/esecurity/common/images/common/title06.png') no-repeat 0 3px;}");
        str.append(".notice_01{margin-top:25px; padding-left:50px; background:url('http://www.skhystec.com/down/esecurity/common/images/common/notice.jpg') no-repeat 0 5px;}");
        str.append(".notice_01 a:link{color:#5EA0D0; font-weight:bold; text-decoration:underline;}");
        str.append(".mail_bottom_security{clear:both; font-size:1em; border-top:3px solid #C4C4C4; padding-top:10px; line-height:1.4em;}");
        str.append(".fclass1 { font-size:20px;font-family:'궁서체'} ");
        str.append(".td1 {width:60%; line-height:170%; font-size:20px;font-family:'궁서체'} ");
        str.append(".td2 {width:40%;text-align:right;  line-height:170%; font-size:20px;font-family:'궁서체'} ");
        str.append(".tda {width:100%; line-height:170%; font-size:20px;font-family:'궁서체'} ");
        str.append(".tbl1 {width:636px; font-size:20px;font-family:'궁서체'}");
        str.append("");
        str.append("</style>");
        str.append("</head>");
        str.append("<body>");
        str.append("<div id='popArea_security'>");
        str.append("<div id='popBody01_security'>");
        str.append("<img src='http://www.skhystec.com/down/esecurity/common/images/common/warning_mail/warning_mail_head.png' width=\"636px\" height=\"151px\"/>");
        str.append("<br>");
        str.append("<br>");
        str.append("<br>");
        str.append("<span class=\"fclass1\">");
        str.append("	<table class='tbl1'>");
        str.append("		<tr>");
        str.append("			<td class=\"td1\"><img src='http://www.skhystec.com/down/esecurity/common/images/common/warning_mail/warning_mail_1.png' width=\"23px\" height=\"23px\"/>소속 : " + ofendCompNm + "<br/><br/></td>");
        str.append("			<td class=\"td2\"><img src='http://www.skhystec.com/down/esecurity/common/images/common/warning_mail/warning_mail_1.png' width=\"23px\" height=\"23px\"/> 성명 : " + sendFromName + "<br/><br/></td>");
        str.append("		</tr>");
        str.append("		<tr>");
        str.append("			<td class=\"tda\" colspan=\"2\">");
        str.append("			<img src='http://www.skhystec.com/down/esecurity/common/images/common/warning_mail/warning_mail_1.png' width=\"23px\" height=\"23px\"/> 귀하는 산업보안 관련 규정 사항 중 ( " + ofendText + " )을 위반하여 ");
        str.append("			산업보안규정 제48조 보안위규자 관리'에 따라 서면 경고 합니다.<br/><br/>");
        str.append("			</td>");
        str.append("		</tr>");
        str.append("		");
        str.append("		<tr>");
        str.append("			<td class=\"tda\" colspan=\"2\">");
        str.append("				<img src='http://www.skhystec.com/down/esecurity/common/images/common/warning_mail/warning_mail_1.png' width=\"23px\" height=\"23px\"/> 귀하는 보안관련 규정을 준수한다는 서약서를 제출하고 보안 교육을 ");
        str.append("            이수하였음에도 보안위규가 발생하였으므로 서면 경고를 통해 책임을 묻고 재발방지를 위해 엄중한 주의가 필요함을 지적합니다.<br/><br/>");
        str.append("");
        str.append("			</td>");
        str.append("		</tr>");
        str.append("		<tr>");
        str.append("			<td class=\"tda\" colspan=\"2\">");
        str.append("				<img src='http://www.skhystec.com/down/esecurity/common/images/common/warning_mail/warning_mail_1.png' width=\"23px\" height=\"23px\"/> 보안관련 규정 위반이 재차 발생될 경우 사규에 따라 추가 조치가 있을 수 있으며, ");
        str.append("				이로 인한 개인 및 회사에 피해가 발생하지 않도록 각별히 주의하시기 바랍니다.<br/><br/>");
        str.append("			</td>");
        str.append("		</tr>");
        str.append("	</table>");
        str.append("");
        str.append("<br>");
        str.append("<br>");
        str.append("<br>");
        str.append("<br>");
        str.append("<center  class='tbl1'>" + yyyyText + "년 " + mmText + "월 " + ddText + "일</center>");
        str.append("<br>");
        str.append("<br>");
        str.append("<br>");
        str.append("<center  class='tbl1' style=\"text-align:right;\">");
        str.append("<img src='http://www.skhystec.com/down/esecurity/common/images/common/warning_mail/warning_mail_tail.png' width=\"181px\" height=\"41px\"/>");
        str.append("</center>");

        str.append(shotURL2);
        str.append("</span>");

        str.append("</div>");
        str.append("</div>");
        str.append("</body>");
        str.append("</html>  ");

        boolean result = mailing.sendMail(title, mailing.applyMailTemplate(title, str.toString()), sendToEmail, "", schemaNm, "", (String) mailMap.get("acIp"));
    }

    private void fmSecCoEmpViolationWarningInfoMailSend(Map<String, String> mailMap) {
//        java.util.Calendar cal = java.util.Calendar.getInstance();
//        int year = cal.get(cal.YEAR);
//        int preYear = year - 1;
//        int afterYear = year + 1;
//        int month = cal.get(cal.MONTH) + 1;

        String schemaNm = StringUtils.defaultIfEmpty(mailMap.get("schemaNm"), "");
        String sendFromMail = "skhystec_security@skhystec.com";
        String sendFromName = mailMap.get("ofendEmpNm");
        String sendFromID = mailMap.get("crtBy");
        //String sendFromEmail = mailMap.get("ofendEmpEmail");
        String scrtView = mailMap.get("scrtView");
        if (scrtView == null) { scrtView = ""; }

        //String ofendDeptNm = mailMap.get("ofendDeptNm");
        String ofendJwNm = mailMap.get("ofendJwNm");
        String ofendDt = mailMap.get("ofendDt");
        String ofendTm = mailMap.get("ofendTm");
        //String ofendGbnNm = mailMap.get("ofendGbnNm");
        String ofendDetailNm = mailMap.get("ofendDetailNm");
        String ofendLocNm = mailMap.get("ofendLoc"); // 장소
        String limit14Dtm = mailMap.get("limit14Dtm");

        //String sendToName = mailMap.get("toEmpNm");
        //String sendToID = mailMap.get("toEmpId");
        String sendToEmail = mailMap.get("toEmpEmail");
        String toCCEmail = mailMap.get("toCcEmail");
        //String remark = mailMap.get("remark").replaceAll("\n", "<BR/>");

        String domain = securityInsnetUrl;

        String title = "[e-Security] 보안위규 관련, 경고장 개선계획 제출요청";
        String shotURL = domain + "?SCHEMAID=SCRT_VIEW";
        String shotURL2 = "";

        String message = sendFromName + "님께서는 아래 보안위규 건에 대해, 산업보안포털 사이트에 접속하여 「경고장 개선계획」을 제출하시기 바랍니다.";

        if (scrtView.equals("SCRT_VIEWUPPER_SEC")) {
            shotURL = domain + "?SCHEMAID=SCRT_VIEWUPPER_SEC";
            shotURL2 = "<a target='_blank' href=\"" + shotURL + "\">(개선계획 제출 바로가기)</a>";
        }
        else {
            shotURL2 = "<a target='_blank' href=\"" + shotURL + "\">(개선계획 제출 바로가기)</a>";
        }

        StringBuffer str = new StringBuffer();

        str.append(" <!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'> \n");
        str.append(" <html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"ko\" lang=\"ko\"> \n");
        str.append(" <head> \n");
        str.append(" <title>입주사-행복한 만남 - SK쉴더스</title> \n");
        str.append(" <style>			 \n");
        str.append(" body { margin:0; padding:0; border:0; font-size:0.75em; line-height:1.8em; letter-spacing:0px; color:#7D7D7D;			scrollbar-3dlight-color:#ffffff; scrollbar-arrow-color:#666666; scrollbar-base-color:#ffffff; scrollbar-darkshadow-color:#d7d7d7;			scrollbar-face-color:#f2f2f2; scrollbar-highlight-color:#f2f2f2;	scrollbar-shadow-color:#f2f2f2;	scrollbar-track-color:#f2f2f2; }			 \n");
        str.append(" *html, body{height:100%; overflow-x:hidden;}			 \n");
        str.append(" body, table	 {width:100%;margin:0px;padding:0px;text-align:left}			 \n");
        str.append(" body, select, input,textarea{margin:0px;padding:0px; font-family:'나눔고딕', '맑은 고딕', 'Malgun Gothic', NanumGothicBold, AppleGothic, 'Lucida Grande', Tahoma, Verdana, UnDotum, Dotum, sans-serif;}			 \n");
        str.append(" div, p, ul, li, dl, dt, dd, h1, h2, h3, h4, h5, form	{margin:0px;padding:0px;list-style:none}			 \n");
        str.append(" img, table {border:0px none; font-size:13px;}			 \n");
        str.append(" a {border:0px none}			 \n");
        str.append(" a,img,input {text-align:absmiddle;margin:0px;padding:0px}			 \n");
        str.append(" select{font-size:1em;}			 \n");
        str.append(" @font-face{font-family:NanumGothic; src:url(http://www.skhystec.com/down/esecurity/common/css/NanumGothic.ttf); src:url(http://www.skhystec.com/down/esecurity/common/css/NanumGothic.ttf)  format(truetype);}			@font-face{font-family:NanumGothic; src:url(http://www.skhystec.com/down/esecurity/common/css/NanumGothic.eot); src: url(http://www.skhystec.com/down/esecurity/common/css/NanumGothic.eot?iefix) format(eot);}			@font-face {font-family:NanumGothicBold; src: url(http://www.skhystec.com/down/esecurity/common/css/NanumGothicBold.eot); src: url(http://www.skhystec.com/down/esecurity/common/css/NanumGothicBold.eot?iefix) format(eot), url(http://www.skhystec.com/down/esecurity/common/css/NanumGothicBold.woff) format(woff), url(http://www.skhystec.com/down/esecurity/common/css/font/NanumGothicBold.ttf)  format(truetype), url(http://www.skhystec.com/down/esecurity/common/css/font/NanumGothicBold.svg#svgFontName) format(svg);}			 \n");
        str.append(" .yellow01 { color:#DD9619; font-weight: bold}			 \n");
        str.append(" #popBody01_security {width:700px; margin:30px auto;  padding-bottom:10px; font-family:'나눔고딕', AppleGothic, '맑은 고딕', 'Malgun Gothic', 'Lucida Grande', Tahoma, Verdana,  UnDotum, Dotum, sans-serif;}			 \n");
        str.append(" #popBody01_security #popArea_security {border-bottom:4px solid #F57724; overflow:hidden; }			 \n");
        str.append(" #popBody01_security .pop_title01_security{font-size:1.4em; padding:10px 0 5px 0; color:#F57724; font-weight:bold; letter-spacing:0px; overflow:hidden; height: 80px;}			#popBody01_security .pop_title01_security img{float:left;}			 \n");
        str.append(" #popBody01_security .pop_title01_security span{float:right; text-align:right; margin-top:33px; border:0px solid #000; width:550px;}			 \n");
        str.append(" #popBody01_security .close{float:right; padding:25px 10px 0 0px;}			 \n");
        str.append(" #popBody01_security .pop_content_security{margin:0px auto 10px auto; border:0px solid #000; background:#fff;}			 \n");
        str.append(" .mail_content_security{ width:700px; padding:50px 10px; border:1px solid #DFDFDF; overflow:hidden; margin:30px auto; background:#fff;} 			 \n");
        str.append(" .mail_left_security{float:left; margin-right:20px; }			 \n");
        str.append(" .mail_right_security{float:right; width:470px; border-left:1px dotted #A0B9A9; padding-left:20px; overflow:hidden; font-size:13px;}			 \n");
        str.append(" .mail_right_security dl{border-top:2px solid #000; border-bottom:2px solid #000; padding:10px; overflow:hidden; margin:10px 0 10px 0; font-weight:bold; color:#DD9619;}			.mail_right_security dt{float:left;}			 \n");
        str.append(" .mail_right_security dd{float:left; margin-left:10px;}			 \n");
        str.append(" .mail_title{font-size:14px; font-weight:bold; margin:10px 0; color:#000; padding:0 0 5px 10px; border-bottom:1px solid #DFDFDF; background:url('http://www.skhystec.com/down/esecurity/common/images/common/title06.png') no-repeat 0 3px;}			 \n");
        str.append(" .notice_01{margin-top:25px; padding-left:50px; background:url('http://www.skhystec.com/down/esecurity/common/images/common/notice.jpg') no-repeat 0 5px;}			 \n");
        str.append(" .notice_01 a:link{color:#5EA0D0; font-weight:bold; text-decoration:underline;}			 \n");
        str.append(" .mail_bottom_security{clear:both; font-size:1em; border-top:3px solid #C4C4C4; padding-top:10px; line-height:1.4em;}		 \n");
        str.append(" </style> \n");
        str.append(" </head> \n");
        str.append(" <body> \n");
        str.append(" <div id=\"popBody01_security\"> \n");
        str.append(" <div id=\"popArea_security\"> \n");
        str.append(" <div class=\"pop_title01_security\"><img src=\"http://www.skhystec.com/down/esecurity/common/images/common/mail_logo.jpg\"><span>" + title + "</span></div> \n");
        str.append(" </div> \n");
        str.append(" <div class=\"pop_content_security\"> \n");
        str.append(" <div class=\"mail_content_security\"> \n");
        str.append(" <div class=\"mail_left_security\"><img src=\"http://www.skhystec.com/down/esecurity/common/images/common/mail_img01.jpg\"> \n");
        str.append(" </div> \n");
        str.append(" <div class=\"mail_right_security\"> \n");
        str.append(" 	<div style=\" padding:4px 0px;line-height:15px;\"><span style=\"font-weight:bold;\">" + sendFromName + "</span>님께서는 아래 보안위규 건에 대해, 입주사 Security 사이트에 접속하여<br><span style=\"color:#f57724\">「경고장 개선계획」</span>을 제출하시기 바랍니다.<br></div> \n");
        str.append(" 	<div style=\" padding:4px 0px;line-height:15px;\">경고장 발부시 요청한 개선계획을 특별한 사유없이 14일 이내 미제출한 경우,<br><span style=\"color:#f57724\">징계의뢰 대상</span>으로 처리 됩니다.<br></div> \n");
        str.append(" 	<div style=\" padding:4px 0px;line-height:15px;\">산업보안 포탈 사이트 : <a target='_blank' href=\"" + shotURL + "\">security.skshieldus.com</a></div> \n");
        str.append(" <br> \n");
        str.append(" <table border=\"1\" bordercolor=\"#e7d1c2\" cellspacing=\"0\" cellpadding=\"5\" style=\"border-collapse:collapse;\"> \n");
        str.append(" <tbody> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">성명 / 직위</span></td> \n");
        str.append(" <td align=\"left\" width=\"80%\">" + sendFromName + " " + ofendJwNm + "</td> \n");
        str.append(" </tr> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">일시</span></td> \n");
        str.append(" <td align=\"left\">" + ofendDt + " " + ofendTm + "</td> \n");
        str.append(" </tr> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">장소</span></td> \n");
        str.append(" <td align=\"left\">" + ofendLocNm + "</td> \n");
        str.append(" </tr> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">위규 내용</span></td> \n");
        str.append(" <td align=\"left\">" + ofendDetailNm + "</td> \n");
        str.append(" </tr> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">위규 처리</span></td> \n");
        str.append(" <td align=\"left\"><span style=\"color:#f57724\">14 일내 경고장 개선계획 제출 " + shotURL2 + "</span></td> \n");
        str.append(" </tr> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">제출 기한</span></td> \n");
        str.append(" <td align=\"left\">" + limit14Dtm + " 24:00 까지</td> \n");
        str.append(" </tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">주의 사항</span></td> \n");
        str.append(" <td align=\"left\"> \n");
        str.append(" - 경고장 개선계획을 14일 이내 특별한 사유없이 고의로 미제출한 경우 징계의뢰 대상임</br>\n");
        str.append(" - 경고장 개선계획은 소속 그룹장/실장 결재 필요함 (결재선 미준수시에는 반려됨) \n");
        str.append(" </td> \n");
        str.append(" </tr> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">참고 사항</span></td> \n");
        str.append(" <td align=\"left\"> \n");
        str.append(" - 모든 보안위규를 포함하여 연간 누적으로 「시정계획서 4회 이상」 또는 「경고장 3회 이상」인 경우 징계의뢰 대상임 (연간 누적 기간 : 2016년 9월  ~ 2017년 8월 ) \n");
        str.append(" </td> \n");
        str.append(" </tr> \n");
        str.append(" </tbody> \n");
        str.append(" </table> \n");
        str.append(" </div> \n");
        str.append(" </div> \n");
        str.append(" <div class=\"mail_bottom_security\"><br> \n");
        str.append(" <br> \n");
        str.append(" 본 메일은 발신전용 메일입니다.<br> \n");
        str.append(" <strong>SK쉴더스</strong> 경기도 이천시 부발읍 경충대로 2091 (TEL 031.5185.4114)<br> \n");
        str.append(" Copyrightⓒ SK SHIELDUS Inc. All Rights Reserved.</div> \n");
        str.append(" </div> \n");
        str.append(" </div> \n");
        str.append(" </body> \n");
        str.append(" </html> \n");

        boolean result = mailing.sendMail(title, mailing.applyMailTemplate(title, str.toString()), sendToEmail, "", schemaNm, "", (String) mailMap.get("acIp"));
    }

    private void fmSecCoEmpViolationJingGuMailSend(Map<String, String> mailMap) {
//        java.util.Calendar cal = java.util.Calendar.getInstance();
//        int year = cal.get(cal.YEAR);
//        int preYear = year - 1;
//        int afterYear = year + 1;
//        int month = cal.get(cal.MONTH) + 1;

        String schemaNm = StringUtils.defaultIfEmpty(mailMap.get("schemaNm"), "");
        String sendFromMail = "skhystec_security@skhystec.com";
        String sendFromName = mailMap.get("ofendEmpNm");
        String sendFromID = mailMap.get("crtBy");
        //String sendFromEmail = mailMap.get("ofendEmpEmail");
        String scrtView = mailMap.get("scrtView");
        if (scrtView == null) { scrtView = ""; }

        //String ofendDeptNm = mailMap.get("ofendDeptNm");
        String ofendJwNm = mailMap.get("ofendJwNm");
        String ofendDt = mailMap.get("ofendDt");
        String ofendTm = mailMap.get("ofendTm");
        //String ofendGbnNm = mailMap.get("ofendGbnNm");
        String ofendDetailNm = mailMap.get("ofendDetailNm");
        String ofendDetailCd = mailMap.get("ofendDetailCd");
        String ofendSubNm = mailMap.get("ofendSubNm");
        String ofendLocNm = mailMap.get("ofendLoc"); // 장소
        String limit14Dtm = mailMap.get("limit14Dtm");
        String toCCEmail = mailMap.get("toCcEmail");

        //String sendToName = mailMap.get("toEmpNm");
        //String sendToID = mailMap.get("toEmpId");
        String sendToEmail = mailMap.get("toEmpEmail");
        //String remark = mailMap.get("remark").replaceAll("\n", "<BR/>");

        String domain = securityInsnetUrl;

        String title = "[e-Security] 보안위규 관련, 시정계획서 제출요청";
        String shotURL = domain + "?SCHEMAID=SCRT_VIEW";
        String shotURL2 = "";

        String message = sendFromName + "님께서는 아래 보안위규 건에 대해, 산업보안포털 사이트에 접속하여「시정계획서」를 제출하시기 바랍니다.";

        if (scrtView.equals("SCRT_VIEWUPPER_SEC")) {
            shotURL = domain + "?SCHEMAID=SCRT_VIEWUPPER_SEC";
        }
        else {
            shotURL2 = "<a target='_blank' href=\"" + shotURL + "\">(시정계획서 제출 바로가기)</a>";
        }

        StringBuffer str = new StringBuffer();

        str.append(" <!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'> \n");
        str.append(" <html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"ko\" lang=\"ko\"> \n");
        str.append(" <head> \n");
        str.append(" <title>행복한 만남 - SK쉴더스</title> \n");
        str.append(" <style>			 \n");
        str.append(" body { margin:0; padding:0; border:0; font-size:0.75em; line-height:1.8em; letter-spacing:0px; color:#7D7D7D;			scrollbar-3dlight-color:#ffffff; scrollbar-arrow-color:#666666; scrollbar-base-color:#ffffff; scrollbar-darkshadow-color:#d7d7d7;			scrollbar-face-color:#f2f2f2; scrollbar-highlight-color:#f2f2f2;	scrollbar-shadow-color:#f2f2f2;	scrollbar-track-color:#f2f2f2; }			 \n");
        str.append(" *html, body{height:100%; overflow-x:hidden;}			 \n");
        str.append(" body, table	 {width:100%;margin:0px;padding:0px;text-align:left}			 \n");
        str.append(" body, select, input,textarea{margin:0px;padding:0px; font-family:'나눔고딕', '맑은 고딕', 'Malgun Gothic', NanumGothicBold, AppleGothic, 'Lucida Grande', Tahoma, Verdana, UnDotum, Dotum, sans-serif;}			 \n");
        str.append(" div, p, ul, li, dl, dt, dd, h1, h2, h3, h4, h5, form	{margin:0px;padding:0px;list-style:none}			 \n");
        str.append(" img, table {border:0px none; font-size:13px;}			 \n");
        str.append(" a {border:0px none}			 \n");
        str.append(" a,img,input {text-align:absmiddle;margin:0px;padding:0px}			 \n");
        str.append(" select{font-size:1em;}			 \n");
        str.append(" @font-face{font-family:NanumGothic; src:url(http://www.skhystec.com/down/esecurity/common/css/NanumGothic.ttf); src:url(http://www.skhystec.com/down/esecurity/common/css/NanumGothic.ttf)  format(truetype);}			@font-face{font-family:NanumGothic; src:url(http://www.skhystec.com/down/esecurity/common/css/NanumGothic.eot); src: url(http://www.skhystec.com/down/esecurity/common/css/NanumGothic.eot?iefix) format(eot);}			@font-face {font-family:NanumGothicBold; src: url(http://www.skhystec.com/down/esecurity/common/css/NanumGothicBold.eot); src: url(http://www.skhystec.com/down/esecurity/common/css/NanumGothicBold.eot?iefix) format(eot), url(http://www.skhystec.com/down/esecurity/common/css/NanumGothicBold.woff) format(woff), url(http://www.skhystec.com/down/esecurity/common/css/font/NanumGothicBold.ttf)  format(truetype), url(http://www.skhystec.com/down/esecurity/common/css/font/NanumGothicBold.svg#svgFontName) format(svg);}			 \n");
        str.append(" .yellow01 { color:#DD9619; font-weight: bold}			 \n");
        str.append(" #popBody01_security {width:700px; margin:30px auto;  padding-bottom:10px; font-family:'나눔고딕', AppleGothic, '맑은 고딕', 'Malgun Gothic', 'Lucida Grande', Tahoma, Verdana,  UnDotum, Dotum, sans-serif;}			 \n");
        str.append(" #popBody01_security #popArea_security {border-bottom:4px solid #F57724; overflow:hidden; }			 \n");
        str.append(" #popBody01_security .pop_title01_security{font-size:1.4em; padding:10px 0 5px 0; color:#F57724; font-weight:bold; letter-spacing:0px; overflow:hidden; height: 80px;}			#popBody01_security .pop_title01_security img{float:left;}			 \n");
        str.append(" #popBody01_security .pop_title01_security span{float:right; text-align:right; margin-top:33px; border:0px solid #000; width:550px;}			 \n");
        str.append(" #popBody01_security .close{float:right; padding:25px 10px 0 0px;}			 \n");
        str.append(" #popBody01_security .pop_content_security{margin:0px auto 10px auto; border:0px solid #000; background:#fff;}			 \n");
        str.append(" .mail_content_security{ width:700px; padding:50px 10px; border:1px solid #DFDFDF; overflow:hidden; margin:30px auto; background:#fff;} 			 \n");
        str.append(" .mail_left_security{float:left; margin-right:20px; }			 \n");
        str.append(" .mail_right_security{float:right; width:470px; border-left:1px dotted #A0B9A9; padding-left:20px; overflow:hidden; font-size:13px;}			 \n");
        str.append(" .mail_right_security dl{border-top:2px solid #000; border-bottom:2px solid #000; padding:10px; overflow:hidden; margin:10px 0 10px 0; font-weight:bold; color:#DD9619;}			.mail_right_security dt{float:left;}			 \n");
        str.append(" .mail_right_security dd{float:left; margin-left:10px;}			 \n");
        str.append(" .mail_title{font-size:14px; font-weight:bold; margin:10px 0; color:#000; padding:0 0 5px 10px; border-bottom:1px solid #DFDFDF; background:url('http://www.skhystec.com/down/esecurity/common/images/common/title06.png') no-repeat 0 3px;}			 \n");
        str.append(" .notice_01{margin-top:25px; padding-left:50px; background:url('http://www.skhystec.com/down/esecurity/common/images/common/notice.jpg') no-repeat 0 5px;}			 \n");
        str.append(" .notice_01 a:link{color:#5EA0D0; font-weight:bold; text-decoration:underline;}			 \n");
        str.append(" .mail_bottom_security{clear:both; font-size:1em; border-top:3px solid #C4C4C4; padding-top:10px; line-height:1.4em;}		 \n");
        str.append(" </style> \n");
        str.append(" </head> \n");
        str.append(" <body> \n");
        str.append(" <div id=\"popBody01_security\"> \n");
        str.append(" <div id=\"popArea_security\"> \n");
        str.append(" <div class=\"pop_title01_security\"><img src=\"http://www.skhystec.com/down/esecurity/common/images/common/mail_logo.jpg\"><span>" + title + "</span></div> \n");
        str.append(" </div> \n");
        str.append(" <div class=\"pop_content_security\"> \n");
        str.append(" <div class=\"mail_content_security\"> \n");
        str.append(" <div class=\"mail_left_security\"><img src=\"http://www.skhystec.com/down/esecurity/common/images/common/mail_img01.jpg\"> \n");
        str.append(" </div> \n");
        str.append(" <div class=\"mail_right_security\"> \n");
        str.append(" 	<div style=\" padding:4px 0px;line-height:15px;\"><span style=\"font-weight:bold;\">" + sendFromName + "</span>님께서는 아래 보안위규 건에 대해, 입주사 Security 사이트에 접속하여 <br><span style=\"color:#f57724\">「시정계획서」</span>를 제출하시기 바랍니다.<br></div> \n");
        str.append(" 	<div style=\" padding:4px 0px;line-height:15px;\">시정계획서를 특별한 사유없이 14일 이내 미제출시에는 <span style=\"color:#f57724\">「경고장」</span>으로 처리 됩니다.<br></div> \n");
        str.append(" 	<div style=\" padding:4px 0px;line-height:15px;\">산업보안 포탈 사이트 : <a target='_blank' href=\"" + shotURL + "\">security.skshieldus.com</a></div> \n");
        str.append(" <br> \n");
        str.append(" <table border=\"1\" bordercolor=\"#e7d1c2\" cellspacing=\"0\" cellpadding=\"5\" style=\"border-collapse:collapse;\"> \n");
        str.append(" <tbody> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">성명 / 직위</span></td> \n");
        str.append(" <td align=\"left\" width=\"80%\">" + sendFromName + " " + ofendJwNm + "</td> \n");
        str.append(" </tr> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">일시</span></td> \n");
        str.append(" <td align=\"left\">" + ofendDt + " " + ofendTm + "</td> \n");
        str.append(" </tr> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">장소</span></td> \n");
        str.append(" <td align=\"left\">" + ofendLocNm + "</td> \n");
        str.append(" </tr> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">위규 내용</span></td> \n");
        // 생활보안 준수사항의 단순 위반(C0541011) 인 경우 상세까지 표시(김용범_20170102)
        if("C0541011".equals(ofendDetailCd)){
            str.append(" <td align=\"left\">" + ofendDetailNm + "<br/>> " + ofendSubNm + "</td> \n");
        }else{
            str.append(" <td align=\"left\">" + ofendDetailNm + "</td> \n");
        }
        str.append(" </tr> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">위규 처리</span></td> \n");
        str.append(" <td align=\"left\"><span style=\"color:#f57724\">14일 이내 시정계획서 제출 " + shotURL2 + "</span></td> \n");
        str.append(" </tr> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">제출 기한</span></td> \n");
        str.append(" <td align=\"left\">" + limit14Dtm + " 24:00 까지</td> \n");
        str.append(" </tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">주의 사항</span></td> \n");
        str.append(" <td align=\"left\"> \n");
        str.append(" - 시정계획서를 14일 이내 특별한 이유없이 미제출한 경우, 경고장 발부 대상임</br> \n");
        str.append(" - 시정계획서는 소속 팀장(대팀제는 PL) 결재 필요하며, 결재선 미준수시에는 반려함 \n");
        str.append(" </td> \n");
        str.append(" </tr> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">참고 사항</span></td> \n");
        str.append(" <td align=\"left\"> \n");
        str.append(" - 모든 보안위규를 포함하여 연간 누적으로 시정계획서 4회 이상」 또는 「경고장 3회 이상」인 경우 징계의뢰 대상임 (연간 누적 기간 : 2016년 9월  ~ 2017년 8월 )\n");
        str.append(" </td> \n");
        str.append(" </tr> \n");
        str.append(" </tbody> \n");
        str.append(" </table> \n");
        str.append(" </div> \n");
        str.append(" </div> \n");
        str.append(" <div class=\"mail_bottom_security\"><br> \n");
        str.append(" <br> \n");
        str.append(" 본 메일은 발신전용 메일입니다.<br> \n");
        str.append(" <strong>SK쉴더스</strong> 경기도 이천시 부발읍 경충대로 2091 (TEL 031.5185.4114)<br> \n");
        str.append(" Copyrightⓒ SK SHIELDUS Inc. All Rights Reserved.</div> \n");
        str.append(" </div> \n");
        str.append(" </div> \n");
        str.append(" </body> \n");
        str.append(" </html> \n");

        boolean result = mailing.sendMail(title, mailing.applyMailTemplate(title, str.toString()), sendToEmail, "", schemaNm, "", (String) mailMap.get("acIp"));
    }

    @Override
    public Boolean coViolationSsmSave(HashMap<String, Object> paramMap) {
        boolean result = false;

        //채번
        int scDocNo = repository.getScDocNo(); /*dmSecCoEmp_Violation_Seq_S*/
        paramMap.put("scDocNo", scDocNo);

        int resultCnt = repository.dmSecCoEmpViolation_ssm_I(paramMap);

        if (resultCnt > 0) {
            result = true;
        }

        return result;
    }

    @Override
    public ListDTO<Map<String, Object>> selectCoViolationList(HashMap<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;
        Integer totalCount = 0;

        try {
            resultList = repository.selectCoViolationList(paramMap);
            totalCount = repository.selectCoViolationListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return ListDTO.getInstance(resultList, totalCount);
    }

    @Override
    public List<Map<String, Object>> selectCoViolationSameHistoryList(HashMap<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {

            resultList = repository.selectCoViolationSameHistoryList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public List<Map<String, Object>> selectCoViolationActHistoryList(HashMap<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {

            resultList = repository.selectCoViolationActHistoryList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Map<String, Object> selectCoViolationDetail(HashMap<String, Object> paramMap) {
        Map<String, Object> result = null;

        try {

            String operateId = "R_VIOLATION"; // 운영

            if (environment.acceptsProfiles(Profiles.of("default", "dev", "stg"))) { //로컬, 개발
                operateId = "D_VIOLATION";
            }

            paramMap.put("operateId", operateId);

            result = repository.selectCoViolationDetail(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Boolean coEmpViolation_Act(HashMap<String, Object> paramMap) {

        boolean result = false;

        log.info(">>>> scDocNo: " + (String) paramMap.get("scDocNo"));

        try {

            Map<String, Object> resultMap = new HashMap<>();
            paramMap.put("crtBy", (String) paramMap.get("empId"));

            String actDo = (String) paramMap.get("actDo");

            // 1, 인사징계의뢰인 경우 결재를 추가 (C0280004-인사징계의뢰)
            if ("C0280004".equals(actDo)) {

                //결재승인
                if ("Z0331005".equals(paramMap.get("applStat"))) {
                    resultMap = fmSecCoEmpViolation_ActDo(paramMap);
                    result = (boolean) resultMap.get("result");
                }
                else {
                    result = coEmpViolationApproval(paramMap); //결재상신
                }
            }
            else {
                resultMap = fmSecCoEmpViolation_ActDo(paramMap);
                result = (boolean) resultMap.get("result");
            }

            /*
             * paramMap.put("crtBy", (String) paramMap.get("empId"));
             *
             * Map<String, Object> resultMap = fmSecCoEmpViolation_ActDo(paramMap);
             *
             * result = (boolean) resultMap.get("result");
             */

        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Boolean coEmpViolation_Mobile_Act(HashMap<String, Object> paramMap) {
        boolean result = false;
        try {

            String actDo = (String) paramMap.get("actDo");

            int resultCnt = repository.coEmpViolation_Mobile_Act(paramMap);

            if (resultCnt > 0) {

                paramMap.put("mobileForensicsGbn", actDo);

                /* 위규자 E-MAIL 발송 시작 */
                List<Map<String, Object>> mailList = repository.dmSecCoEmp_Violation_SendMail_List_S(paramMap);

                Map<String, String> mailMap = new HashMap<String, String>();

                for (int i = 0; i < mailList.size(); i++) {

                    if ("4".equals((String) mailList.get(i).get("gubun"))) {
                        mailMap = new HashMap<String, String>();

                        mailMap.put("ofendEmpId", (String) mailList.get(0).get("empId"));    // 위규자 사번 : 필수
                        mailMap.put("ofendEmpNm", (String) mailList.get(0).get("empNm"));    // 위규자 이름 : 필수
                        mailMap.put("ofendEmpEmail", (String) mailList.get(0).get("email"));  // 위규자 메일 : 필수

                        mailMap.put("schemaNm", "보안위규자");
                        mailMap.put("docNm", "보안 위규자");        // 문서 명 : 필수
                        mailMap.put("crtBy", (String) paramMap.get("empId"));          // 로그인ID
                        mailMap.put("acIp", (String) paramMap.get("acIp"));            // AC_IP

                        mailMap.put("toEmpId", (String) mailList.get(i).get("empId"));    // 메일 수신자 사번 : 필수
                        mailMap.put("toEmpNm", (String) mailList.get(i).get("empNm"));    // 메일 수신자 이름 : 필수
                        mailMap.put("toEmpEmail", (String) mailList.get(i).get("email"));  // 메일 수신자 메일주소 : 필수
                        mailMap.put("scrtView", "SCRT_VIEWUPPER_SEC");

                        mailMap.put("ofendDeptNm", (String) paramMap.get("ofendDeptNm"));
                        mailMap.put("ofendJwNm", (String) paramMap.get("ofendJwNm"));
                        mailMap.put("ofendDt", (String) paramMap.get("ofendDtMail"));
                        mailMap.put("ofendTm", (String) paramMap.get("ofendTmMail"));
                        mailMap.put("ofendGbnNm", (String) paramMap.get("ofendGbnNmMail"));
                        mailMap.put("ofendDetailNm", (String) paramMap.get("ofendSubGbnNm"));

                        fmSecCoEmpViolationMailSend(mailMap);
                    }
                }
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> selectIoEmpInfo(HashMap<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {

            String ioEmpNm = (String) paramMap.get("ioEmpNm");
            if (ioEmpNm == null || ioEmpNm.length() < 2 || ioEmpNm.substring(0, 2).equals("%%") || ioEmpNm.trim().equals("")) {
                return resultList;
            }

            Object index = paramMap.get("currentPage");
            Object size = paramMap.get("rowPerPage");
            if (index == null || "".equals(index)) { paramMap.put("currentPage", "1"); }
            if (size == null || "".equals(size)) { paramMap.put("rowPerPage", "5"); }

            String onedaySubcontYn = (String) paramMap.get("onedaySubcontYn");
            if (onedaySubcontYn != null) {
                if ("Y".equals(onedaySubcontYn)) { // 일일사원증 발급화면에서 도급업체 회원만 검색됨 : 2016-09-08 by JSH
                    //					sqlId = "dmIoEmpSubcontList";
                    resultList = repository.selectIoEmpSubInfo(paramMap);
                }
            }
            else {
                resultList = repository.selectIoEmpInfo(paramMap);
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public int selectIoEmpInfoCnt(HashMap<String, Object> paramMap) {
        int resultCnt = 0;

        try {

            String ioEmpNm = (String) paramMap.get("ioEmpNm");
            if (ioEmpNm == null || ioEmpNm.length() < 2 || ioEmpNm.substring(0, 2).equals("%%") || ioEmpNm.trim().equals("")) {
                return resultCnt;
            }

            String onedaySubcontYn = (String) paramMap.get("onedaySubcontYn");
            if (onedaySubcontYn != null) {
                if ("Y".equals(onedaySubcontYn)) { // 일일사원증 발급화면에서 도급업체 회원만 검색됨 : 2016-09-08 by JSH
                    resultCnt = repository.selectIoEmpSubInfoCnt(paramMap);
                }
            }
            else {

                resultCnt = repository.selectIoEmpInfoCnt(paramMap);
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return resultCnt;
    }

    @Override
    public List<Map<String, Object>> selectIoViolationInterviewerList(HashMap<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;
        try {
            resultList = repository.selectIoViolationInterviewerList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Boolean ioViolationSave(HashMap<String, Object> paramMap) {
        boolean result = false;
        log.info("**********************************************************************************************************");
        log.info("ioViolationSave param : " + paramMap);
        log.info("**********************************************************************************************************");
        try {
            //채번
            int scIoOfendDocNo = repository.getScIoDocNo(); /*dmSecIoEmp_Violation_Seq_S*/
            paramMap.put("scIoOfendDocNo", scIoOfendDocNo);
            paramMap.put("scIoDocNo", scIoOfendDocNo);

            //등록
            result = ioViolationInsert(paramMap) > 0 ? true : false;

            if(result) {

                List<Map<String, Object>> seqDs3 = repository.dmSecIoEmp_Violation_Info(paramMap);

                String ofendCompNm = "";
                String ofendJwNm = "";
                String ofendDt = "";
                String ofendTm = "";
                String ofendGbnNm = "";
                String ofendDetailNm = "";
                String ofendHpNo = "";

                if (seqDs3 != null && seqDs3.size() > 0) {
                    ofendCompNm = (String) seqDs3.get(0).get("compKoNm");
                    ofendJwNm = (String) seqDs3.get(0).get("jwNm");
                    ofendDt = (String) seqDs3.get(0).get("ofendDt");
                    ofendTm = (String) seqDs3.get(0).get("ofendTm");
                    ofendGbnNm = (String) seqDs3.get(0).get("ofendGbnNm");
                    ofendDetailNm = (String) seqDs3.get(0).get("ofendDetailNm");
                    ofendHpNo = (String) seqDs3.get(0).get("hpNo");
                }

                /* 위규자 E-MAIL 발송 시작 */
                List<Map<String, Object>> mailList = repository.dmSecIoEmp_Violation_SendMail_List_S(paramMap);

                if (mailList != null && mailList.size() > 0) {
                    for (int i = 0; i < mailList.size(); i++) {

                        Map<String, Object> mailReq = new HashMap<String, Object>();

                        mailReq.put("ofendEmpId", (String) mailList.get(0).get("empId"));  // 위규자 사번 : 필수
                        mailReq.put("ofendEmpNm", (String) mailList.get(0).get("empNm"));  // 위규자 이름 : 필수
                        mailReq.put("ofendEmpEmail", (String) mailList.get(0).get("email"));  // 위규자 메일 : 필수

                        mailReq.put("compKoNm", ofendCompNm);
                        mailReq.put("ofendJwNm", ofendJwNm);
                        mailReq.put("ofendDt", ofendDt);
                        mailReq.put("ofendTm", ofendTm);
                        mailReq.put("ofendGbnNm", ofendGbnNm);
                        mailReq.put("ofendDetailNm", ofendDetailNm);

                        if (mailList.get(i).get("gubun").equals("1")) { // 위규자
                            mailReq.put("toEmpId", mailList.get(i).get("empId"));      // 메일 수신자 사번 : 필수
                            mailReq.put("toEmpNm", mailList.get(i).get("empNm"));      // 메일 수신자 이름 : 필수
                            mailReq.put("toEmpEmail", mailList.get(i).get("email"));      // 메일 수신자 메일주소 : 필수
                            mailReq.put("gubun", "1");      // 구분
                            mailReq.put("schemaNm", "보안위규자");
                            mailReq.put("docNm", "보안 위규자");          // 문서 명 : 필수
                            mailReq.put("crtBy", paramMap.get("empId"));        // 로그인ID
                            mailReq.put("acIp", paramMap.get("acIp"));        // AC_IP
                            //callSharedBizComponentByDirect(mailSend, "fmSecIoEmpViolationMailSend", mailReq, onlineCtx);	 ASIS에서 주석되어있었음

                            String msg = "[SK하이닉스]보안위규자 등록, 방문예약시스템에서 시정계획서를 작성하시기 바랍니다";
                            Map<String, Object> SMSReq = new HashMap<String, Object>();
                            //SharedBizComponent SMSSend = lookupSharedBizComponent("common.SendSMS");   ASIS에서 주석되어있었음

                            SMSReq.put("callbackNo", ""); // 보내는 사람 휴대폰 번호
                            SMSReq.put("smsNo", ofendHpNo);      // 받는 사람 휴대폰 번호
                            SMSReq.put("msg", msg); // 문자 내용
                            SMSReq.put("sendEmpId", "SYSTEM"); // 발송자 아이디
                            //callSharedBizComponentByDirect(SMSSend, "fmSendSMS", SMSReq, onlineCtx);			// SMS 발송 ASIS에서 주석되어있었음
                        }
                        else if (mailList.get(i).get("gubun").equals("2")) { // 방문예약 담당자
                            mailReq.put("toEmpId", mailList.get(i).get("empId"));      // 메일 수신자 사번 : 필수
                            mailReq.put("toEmpNm", mailList.get(i).get("empNm"));      // 메일 수신자 이름 : 필수
                            mailReq.put("toEmpEmail", mailList.get(i).get("email"));      // 메일 수신자 메일주소 : 필수
                            mailReq.put("gubun", "2");      // 구분
                            mailReq.put("schemaNm", "보안위규자");
                            mailReq.put("docNm", "보안 위규자");          // 문서 명 : 필수
                            mailReq.put("crtBy", paramMap.get("empId"));        // 로그인ID
                            mailReq.put("acIp", paramMap.get("acIp"));        // AC_IP
                            //callSharedBizComponentByDirect(mailSend, "fmSecIoEmpViolationMailSend", mailReq, onlineCtx);  ASIS에서 주석되어있었음
                        }
                        else if (mailList.get(i).get("gubun").equals("3")) { // 총무팀 허가부서 (Gubun == 3)
                            mailReq.put("toEmpId", mailList.get(i).get("empId"));      // 메일 수신자 사번 : 필수
                            mailReq.put("toEmpNm", mailList.get(i).get("empNm"));      // 메일 수신자 이름 : 필수
                            mailReq.put("toEmpEmail", mailList.get(i).get("email"));      // 메일 수신자 메일주소 : 필수
                            mailReq.put("gubun", "3");      // 구분
                            mailReq.put("schemaNm", "보안위규자");
                            mailReq.put("docNm", "보안 위규자");          // 문서 명 : 필수
                            mailReq.put("crtBy", paramMap.get("empId"));        // 로그인ID
                            mailReq.put("acIp", paramMap.get("acIp"));        // AC_IP
                            //callSharedBizComponentByDirect(mailSend, "fmSecIoEmpViolationMailSend", mailReq, onlineCtx);  ASIS에서 주석되어있었음
                        }
                        else if (mailList.get(i).get("gubun").equals("4")) { // 대표관리자
                            mailReq.put("toEmpId", mailList.get(i).get("empId"));      // 메일 수신자 사번 : 필수
                            mailReq.put("toEmpNm", mailList.get(i).get("empNm"));      // 메일 수신자 이름 : 필수
                            mailReq.put("toEmpEmail", mailList.get(i).get("email"));      // 메일 수신자 메일주소 : 필수
                            mailReq.put("gubun", "4");   // 구분
                            mailReq.put("schemaNm", "보안위규자");
                            mailReq.put("docNm", "보안 위규자");          // 문서 명 : 필수
                            mailReq.put("crtBy", paramMap.get("empId"));        // 로그인ID
                            mailReq.put("acIp", paramMap.get("acIp"));        // AC_IP
                            //callSharedBizComponentByDirect(mailSend, "fmSecIoEmpViolationMailSend", mailReq, onlineCtx);  ASIS에서 주석되어있었음
                        }
                        else if (mailList.get(i).get("gubun").equals("5")) { // 위반내용별 > 사업장별 보안담당자에게 메일발송

                            mailReq.put("toEmpId", mailList.get(i).get("empId"));      // 메일 수신자 사번 : 필수
                            mailReq.put("toEmpNm", mailList.get(i).get("empNm"));      // 메일 수신자 이름 : 필수
                            mailReq.put("toEmpEmail", mailList.get(i).get("email"));      // 메일 수신자 메일주소 : 필수
                            mailReq.put("gubun", "5");   // 구분
                            mailReq.put("schemaNm", "보안위규자");
                            mailReq.put("docNm", "보안 위규자");          // 문서 명 : 필수
                            mailReq.put("crtBy", paramMap.get("empId"));        // 로그인ID
                            mailReq.put("acIp", paramMap.get("acIp"));        // AC_IP
                            //위규 발생시 사업장별 보안담당자에게 메일 발송 후 메일 내용에 .직접 가는 URL 적용할것. 김용범 책임 요청

                            //TODO:(hoonLee) 메일보내기 주석풀기
                            //fmSecIoEmpViolationMailSend(mailReq);
                        }
                    }
                    /* 위규자 E-MAIL 발송 종료 */
                }
            }
            result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    private void fmSecIoEmpViolationMailSend(Map<String, Object> mailReq) {
        String schemaNm = StringUtils.defaultIfEmpty((String) mailReq.get("schemaNm"), "");

        String sendFromName = (String) mailReq.get("ofendEmpNm");
        String sendFromID = (String) mailReq.get("crtBy");
        String sendFromEmail = (String) mailReq.get("ofendEmpEmail");
        String gubun = (String) mailReq.get("gubun");

        String sendFromMail = "skhystec_security@skhystec.com";

        String sendToName = (String) mailReq.get("toEmpNm");
        String sendToID = (String) mailReq.get("toEmpId");
        String sendToEmail = (String) mailReq.get("toEmpEmail");
        String domain = securityExtnetUrl;
        String insDomain = securityInsnetUrl;

        String ofendCompNm = (String) mailReq.get("compKoNm");
        String ofendJwNm = (String) mailReq.get("ofendJwNm");
        String ofendDt = (String) mailReq.get("ofendDt");
        String ofendTm = (String) mailReq.get("ofendTm");
        String ofendGbnNm = (String) mailReq.get("ofendGbnNm");
        String ofendDetailNm = (String) mailReq.get("ofendDetailNm");
        String actDoNm = StringUtils.defaultIfEmpty((String) mailReq.get("actDoNm"), "");

        String docNm = (String) mailReq.get("docNm");

        String title = "";
        String message = "";
        String message2 = "";

        if (gubun.toString().equals("1")) {
            title = "[행복한만남]" + sendFromName + "님께서 " + docNm + "로 적발되었습니다.";
            message = sendFromName + "님께서 " + docNm + "로 적발되었습니다.<br />";
            message += "행복한 만남에 접속하셔서 확인하시기 바랍니다.";
            message    			+= "<a href='"+domain+"' target='_new'>행복한 만남 바로가기</a>";
            message2 = "※주의사항<br>";
            message2 += "- 보안 위규자 3회 이상 등록 시 외부인의 출입이 정지/제한 될 수 있으니<br>";
            message2 += "추가 위규되지 않도록 주의하시기 바랍니다.";
            //mailType = "W";
        }
        else if (gubun.toString().equals("5")) { //산업보안담당자에게만 보내는 메일 내용임.
            title = "[e-Security]보안위규 조치내역처리 필요안내";
            message = sendFromName + "님께서 " + docNm + "로 적발되었습니다.<br />";
            message += "아래 보안 위규건에 대해 , 위규조치내역을 입력하시기 바랍니다.";
            message    	+= "<a href='"+insDomain+"?SCHEMAID=IO_SCRT_VIEWUPPER_SEC' target='_new'>e-Security 바로가기</a>";
            //domain = insDomain + "?SCHEMAID=IO_SCRT_VIEWUPPER_SEC";
        }
        else {
            title = "[e-Security]" + sendFromName + "님께서 " + docNm + "로 적발되었습니다.";
            message = sendFromName + "님께서 " + docNm + "로 적발되었습니다.<br />";
            message2 = "※주의사항<br>";
            message2 += "- 보안 위규자 3회 이상 등록 시 외부인의 출입이 정지/제한 될 수 있으니<br>";
            message2 += "추가 위규되지 않도록 안내 바랍니다.";
            //domain = insDomain;
        }

        StringBuffer str = new StringBuffer();

        str.append(" <!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'> \n");
        str.append(" <html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"ko\" lang=\"ko\"> \n");
        str.append(" <head> \n");
        str.append(" <title>입주사-행복한 만남 - SK쉴더스</title> \n");
        str.append(" <style>			 \n");
        str.append(" body { margin:0; padding:0; border:0; font-size:0.75em; line-height:1.8em; letter-spacing:0px; color:#7D7D7D;			scrollbar-3dlight-color:#ffffff; scrollbar-arrow-color:#666666; scrollbar-base-color:#ffffff; scrollbar-darkshadow-color:#d7d7d7;			scrollbar-face-color:#f2f2f2; scrollbar-highlight-color:#f2f2f2;	scrollbar-shadow-color:#f2f2f2;	scrollbar-track-color:#f2f2f2; }			 \n");
        str.append(" *html, body{height:100%; overflow-x:hidden;}			 \n");
        str.append(" body, table	 {width:100%;margin:0px;padding:0px;text-align:left}			 \n");
        str.append(" body, select, input,textarea{margin:0px;padding:0px; font-family:'나눔고딕', '맑은 고딕', 'Malgun Gothic', NanumGothicBold, AppleGothic, 'Lucida Grande', Tahoma, Verdana, UnDotum, Dotum, sans-serif;}			 \n");
        str.append(" div, p, ul, li, dl, dt, dd, h1, h2, h3, h4, h5, form	{margin:0px;padding:0px;list-style:none}			 \n");
        str.append(" img, table {border:0px none; font-size:13px;}			 \n");
        str.append(" a {border:0px none}			 \n");
        str.append(" a,img,input {text-align:absmiddle;margin:0px;padding:0px}			 \n");
        str.append(" select{font-size:1em;}			 \n");
        str.append(" @font-face{font-family:NanumGothic; src:url(http://www.skhystec.com/down/esecurity/common/css/NanumGothic.ttf); src:url(http://www.skhystec.com/down/esecurity/common/css/NanumGothic.ttf)  format(truetype);}			@font-face{font-family:NanumGothic; src:url(http://www.skhystec.com/down/esecurity/common/css/NanumGothic.eot); src: url(http://www.skhystec.com/down/esecurity/common/css/NanumGothic.eot?iefix) format(eot);}			@font-face {font-family:NanumGothicBold; src: url(http://www.skhystec.com/down/esecurity/common/css/NanumGothicBold.eot); src: url(http://www.skhystec.com/down/esecurity/common/css/NanumGothicBold.eot?iefix) format(eot), url(http://www.skhystec.com/down/esecurity/common/css/NanumGothicBold.woff) format(woff), url(http://www.skhystec.com/down/esecurity/common/css/font/NanumGothicBold.ttf)  format(truetype), url(http://www.skhystec.com/down/esecurity/common/css/font/NanumGothicBold.svg#svgFontName) format(svg);}			 \n");
        str.append(" .yellow01 { color:#DD9619; font-weight: bold}			 \n");
        str.append(" #popBody01_security {width:700px; margin:30px auto;  padding-bottom:10px; font-family:'나눔고딕', AppleGothic, '맑은 고딕', 'Malgun Gothic', 'Lucida Grande', Tahoma, Verdana,  UnDotum, Dotum, sans-serif;}			 \n");
        str.append(" #popBody01_security #popArea_security {border-bottom:4px solid #F57724; overflow:hidden; }			 \n");
        str.append(" #popBody01_security .pop_title01_security{font-size:1.4em; padding:10px 0 5px 0; color:#F57724; font-weight:bold; letter-spacing:0px; overflow:hidden; height: 80px;}			#popBody01_security .pop_title01_security img{float:left;}			 \n");
        str.append(" #popBody01_security .pop_title01_security span{float:right; text-align:right; margin-top:33px; border:0px solid #000; width:550px;}			 \n");
        str.append(" #popBody01_security .close{float:right; padding:25px 10px 0 0px;}			 \n");
        str.append(" #popBody01_security .pop_content_security{margin:0px auto 10px auto; border:0px solid #000; background:#fff;}			 \n");
        str.append(" .mail_content_security{ width:700px; padding:50px 10px; border:1px solid #DFDFDF; overflow:hidden; margin:30px auto; background:#fff;} 			 \n");
        str.append(" .mail_left_security{float:left; margin-right:20px; }			 \n");
        str.append(" .mail_right_security{float:right; width:470px; border-left:1px dotted #A0B9A9; padding-left:20px; overflow:hidden; font-size:13px;}			 \n");
        str.append(" .mail_right_security dl{border-top:2px solid #000; border-bottom:2px solid #000; padding:10px; overflow:hidden; margin:10px 0 10px 0; font-weight:bold; color:#DD9619;}			.mail_right_security dt{float:left;}			 \n");
        str.append(" .mail_right_security dd{float:left; margin-left:10px;}			 \n");
        str.append(" .mail_title{font-size:14px; font-weight:bold; margin:10px 0; color:#000; padding:0 0 5px 10px; border-bottom:1px solid #DFDFDF; background:url('http://www.skhystec.com/down/esecurity/common/images/common/title06.png') no-repeat 0 3px;}			 \n");
        str.append(" .notice_01{margin-top:25px; padding-left:50px; background:url('http://www.skhystec.com/down/esecurity/common/images/common/notice.jpg') no-repeat 0 5px;}			 \n");
        str.append(" .notice_01 a:link{color:#5EA0D0; font-weight:bold; text-decoration:underline;}			 \n");
        str.append(" .mail_bottom_security{clear:both; font-size:1em; border-top:3px solid #C4C4C4; padding-top:10px; line-height:1.4em;}		 \n");
        str.append(" </style> \n");
        str.append(" </head> \n");
        str.append(" <body> \n");
        str.append(" <div id=\"popBody01_security\"> \n");
        str.append(" <div id=\"popArea_security\"> \n");
        str.append(" <div class=\"pop_title01_security\"><img src=\"http://www.skhystec.com/down/esecurity/common/images/common/mail_logo.jpg\"><span>" + title + "</span></div> \n");
        str.append(" </div> \n");
        str.append(" <div class=\"pop_content_security\"> \n");
        str.append(" <div class=\"mail_content_security\"> \n");
        str.append(" <div class=\"mail_left_security\"><img src=\"http://www.skhystec.com/down/esecurity/common/images/common/mail_img01.jpg\"> \n");
        str.append(" </div> \n");
        str.append(" <div class=\"mail_right_security\"> \n");
        str.append(" 	<div style=\" padding:4px 0px;line-height:15px;\"><span style=\"font-weight:bold;\">" + message + "</div> \n");
        str.append(" <br> \n");
        str.append(" <table border=\"1\" bordercolor=\"#e7d1c2\" cellspacing=\"0\" cellpadding=\"5\" style=\"border-collapse:collapse;\"> \n");
        str.append(" <tbody> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">위규자</span></td> \n");
        str.append(" <td align=\"left\" width=\"80%\">" + sendFromName + "( " + ofendCompNm + " )" + ofendJwNm + "</td> \n");
        str.append(" </tr> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">위규일시</span></td> \n");
        str.append(" <td align=\"left\">" + ofendDt + " " + ofendTm + "</td> \n");
        str.append(" </tr> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">위규구분</span></td> \n");
        str.append(" <td align=\"left\">" + ofendGbnNm + "</td> \n");
        str.append(" </tr> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">위규내용</span></td> \n");
        str.append(" <td align=\"left\">" + ofendDetailNm + "</td> \n");
        str.append(" </tr> \n");

        if(!"".equals(actDoNm)){
            str.append(" <tr> \n");
            str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">위규 처리</span></td> \n");
            str.append(" <td align=\"left\"><span style=\"color:#f57724\">" + actDoNm + "</span></td> \n");
            str.append(" </tr> \n");
        }

        str.append(" </tbody> \n");

        str.append("                        <br>");
        str.append("						" + message2);

        str.append(" </table> \n");
        str.append(" </div> \n");
        str.append(" </div> \n");
        str.append(" <div class=\"mail_bottom_security\"><br> \n");
        str.append(" <br> \n");
        str.append(" 본 메일은 발신전용 메일입니다.<br> \n");
        str.append(" <strong>SK쉴더스</strong> 경기도 이천시 부발읍 경충대로 2091 (TEL 031.5185.4114)<br> \n");
        str.append(" Copyrightⓒ SK SHIELDUS Inc. All Rights Reserved.</div> \n");
        str.append(" </div> \n");
        str.append(" </div> \n");
        str.append(" </body> \n");
        str.append(" </html> \n");

        if (!"".equals(actDoNm)) {
            str.append(" <tr> \n");
            str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">위규 처리</span></td> \n");
            str.append(" <td align=\"left\"><span style=\"color:#f57724\">" + actDoNm + "</span></td> \n");
            str.append(" </tr> \n");
        }
        str.append(" </tbody> \n");
        str.append(" </table> \n");
        str.append("                        <br>");
        str.append(message2);
        str.append(" </div> \n");

        boolean result = mailing.sendMail(title, mailing.applyMailTemplate(title, str.toString()), sendToEmail, "", schemaNm, "", (String) mailReq.get("acIp"));
    }

    private Map<String, Object> fmSecIoEmpViolation_ActDo(Map<String, Object> paramMap) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> mailReq = new HashMap<String, Object>();

        boolean result = false;

        String actDo = (String) paramMap.get("actDo");

        if ("C0280002,C0280006".indexOf(actDo) > -1) { // 시정계획서 징구, 대표이상 시정공문 징구
            paramMap.put("corrPlanSendYn", "C0101002"); // 미제출
        }
        else {
            paramMap.put("corrPlanSendYn", "C0101003"); // 해당없음
        }
        log.info("***************************************** fmSecIoEmpViolation_ActDo LOG *****************************************");
        log.info("*****************************dmSecIoEmp_Violation_Ofend_U, dmSecIoEmp_Violation_Exp_I  [param] : " + paramMap);
        log.info("*****************************************************************************************************************");

        int resultDs = repository.dmSecIoEmp_Violation_Ofend_U(paramMap);
        int resultDs2 = repository.dmSecIoEmp_Violation_Exp_I(paramMap);

        /* 추가 : 2016-06-21 by JSH */
        if ("C0280007,C0280008,C0280012".indexOf(actDo) > -1) { // 영구출입정지, 출입정지 1개월, 출입정지 6개월

            int denyNo = repository.dmSecIoEmp_Violation_Deny_Seq_S();
            paramMap.put("denyNo", denyNo);

            log.info("***************************************** fmSecIoEmpViolation_ActDo LOG *****************************************");
            log.info("*****************************dmSecIoEmp_Violation_Deny_I  [param] : " + paramMap);
            log.info("*****************************************************************************************************************");
            int rDs1 = repository.dmSecIoEmp_Violation_Deny_I(paramMap); // IO_EMP_DENY 테이블에 Insert

            if (rDs1 < 1) {
                result = false;
                resultMap.put("result", result);
            }


            String[] siteList = { "HN", "HS", "SI" };
            for (int i = 0; i < siteList.length; i++) {
                String siteType = siteList[i];
                paramMap.put("siteType", siteType);
                // 이천 출입증이 있는지 확인 후 있으면

                Map<String, Object> rDs2 = dmSecIoEmp_Violation_PassExprHist_I(paramMap);

                if (!(rDs2.get("idcardId") == null || "".equals(rDs2.get("idcardId")))) {
                    /* 하이스텍 I/F 실행 */
                    try {
                        String exprGbnCode = "A0460007"; //위규조치(1개월출입정지)

                        if ("C0280007".equals(actDo)) { // 영구출입정지
                            exprGbnCode = "A0460008";  // 위규조치(영구정지)
                        }

                        if ("C0280012".equals(actDo)) { // 6개월 출입정지
                            exprGbnCode = "A0460021";
                        }

                        Map<String, Object> ds = new HashMap<String, Object>();
                        ds.put("exprApplNo", rDs2.get("exprApplNo"));
                        ds.put("sCGbn", "S"); // 정지
                        ds.put("idcardId", rDs2.get("idcardId"));
                        ds.put("exprCode", exprGbnCode);
                        ds.put("siteType", siteType);

                        //---------- 하이스텍 정지 하는 I/F Procedure 호출해야 함 ----------//
                        /*callSharedBizComponentByDirect("common.PassExcptIF", "fmPassExcptIF_Stop", ds);*/
                        fmPassExcptIF_Stop(ds);
                        //---------- 하이스텍 정지 하는 I/F Procedure 호출해야 함 ----------//
                    } catch (Exception e) {
                        System.out.println("---------------------fmPassExcptIF_Stop 확인중 에러가 발생했습니다.   ------------------------------------------------");
                        e.printStackTrace();

                        throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
                    }
                    /* 하이스텍 I/F 실행 */
                }
            }
        }

        result = true;
        resultMap.put("result", result);

        paramMap.put("scIoOfendDocNo", paramMap.get("scIoDocNo"));

        if ("C0280002,C0280006,C0280008,C0280007,C0280012".indexOf(actDo) > -1) { // 시정계획서 징구, 대표이사 시정공문 징구, 1개월출입정지, 영구출입정지, 6개월출입정지

            if ("C0280002,C0280006".indexOf(actDo) > -1) { // 시정계획서 징구, 대표이사 시정공문 징구 이렇게만 시정계획서 테이블에 insert되게 함
                int scIoCorrPlanNo = repository.dmSecIoEmp_Violation_Corr_Plan_Seq_S(paramMap);
                paramMap.put("scIoCorrPlanNo", scIoCorrPlanNo);

                int resultDs3 = repository.dmSecIoEmp_Violation_CorrPlan_I(paramMap);

                if (resultDs3 > 0) {
                    result = true;
                    resultMap.put("result", result);
                }
            }

            /* 위규자 E-MAIL 발송 시작 */
            List<Map<String, Object>> seqDs3 = repository.dmSecIoEmp_Violation_Info(paramMap);

            String ofendCompNm = "";
            String ofendJwNm = "";
            String ofendDt = "";
            String ofendTm = "";
            String ofendGbnNm = "";
            String ofendDetailNm = "";
            String ofendHpNo = "";
            String actCompNm = "";
            String denyStrtDt = "";
            String denyEndDt = "";
            String limit14Dtm = ""; // 14일
            String limit30Dtm = ""; // 30일
            String ofendLoc = "";
            String ofendEmpNm = "";

            if (seqDs3 != null && seqDs3.size() > 0) {
                ofendCompNm = (String) seqDs3.get(0).get("compKoNm");
                ofendJwNm = (String) seqDs3.get(0).get("jwNm");
                ofendDt = (String) seqDs3.get(0).get("ofendDt");
                ofendTm = (String) seqDs3.get(0).get("ofendTm");
                ofendGbnNm = (String) seqDs3.get(0).get("ofendGbnNm");
                ofendDetailNm = (String) seqDs3.get(0).get("ofendDetailNm");

                ofendHpNo = (String) seqDs3.get(0).get("hpNo");
                actCompNm = (String) seqDs3.get(0).get("actCompNm");
                denyStrtDt = (String) seqDs3.get(0).get("denyStrtDt");
                denyEndDt = (String) seqDs3.get(0).get("denyEndDt");
                limit14Dtm = (String) seqDs3.get(0).get("limit14Dtm");
                limit30Dtm = (String) seqDs3.get(0).get("limit30Dtm");
                ofendEmpNm = (String) seqDs3.get(0).get("empNm");
            }

            if (seqDs3 != null && seqDs3.size() > 0) {
                if ("".equals((String) seqDs3.get(0).get("actGate")) || null == seqDs3.get(0).get("actGate")) {
                    ofendLoc = (String) seqDs3.get(0).get("actCompNm") + " > " + (String) seqDs3.get(0).get("actBldgNm") + " > " + (String) seqDs3.get(0).get("actLocateNm");
                }
                else {
                    ofendLoc = (String) seqDs3.get(0).get("actCompNm") + " > " + (String) seqDs3.get(0).get("actBldgNm") + " > " + (String) seqDs3.get(0).get("actLocateNm") + " > "
                               + (String) seqDs3.get(0).get("actGate");
                }
            }

            List<Map<String, Object>> ds = repository.dmSecIoEmp_Violation_SendMail_List_S(paramMap);

            if (ds != null && ds.size() > 0) {
                for (int i = 0; i < ds.size(); i++) {
                    mailReq = new HashMap<String, Object>();

                    mailReq.put("ofendEmpId", ds.get(0).get("empId"));  // 위규자 사번 : 필수
                    mailReq.put("ofendEmpNm", ds.get(0).get("empNm"));  // 위규자 이름 : 필수
                    mailReq.put("ofendEmpEmail", ds.get(0).get("email"));  // 위규자 메일 : 필수

                    mailReq.put("compKoNm", ofendCompNm);
                    mailReq.put("ofendJwNm", ofendJwNm);
                    mailReq.put("ofendDt", ofendDt);
                    mailReq.put("ofendTm", ofendTm);
                    mailReq.put("ofendGbnNm", ofendGbnNm);
                    mailReq.put("ofendDetailNm", ofendDetailNm);
                    mailReq.put("actCompNm", actCompNm); // 적발사업장
                    mailReq.put("denyStrtDt", denyStrtDt); // 출입정지시작일
                    mailReq.put("denyEndDt", denyEndDt); // 출입정지종료일
                    mailReq.put("ofendLoc", ofendLoc); // 장소

                    mailReq.put("schemaNm", "보안위규자");
                    mailReq.put("docNm", "보안 위규자");          // 문서 명 : 필수
                    mailReq.put("crtBy", paramMap.get("crtBy"));        // 로그인ID
                    mailReq.put("acIp", paramMap.get("acIp"));        // AC_IP

                    mailReq.put("limit14Dtm", limit14Dtm); // 14일
                    mailReq.put("limit30Dtm", limit30Dtm); // 30일

                    if ("1".toString().equals(ds.get(i).get("gubun").toString()) || "4".toString().equals(ds.get(i).get("gubun").toString()) // 대표관리자(정)
                        || "6".toString().equals(ds.get(i).get("gubun").toString()) // 대표관리자(부)
                        || "7".toString().equals(ds.get(i).get("gubun").toString()) // 현업접견자(SC_IO_OFEND_MEET 테이블 정보)
                    ) { // 위규자 or 대표관리자(정) or 대표관리자(부) or 현업접견자
                        mailReq.put("toEmpId", ds.get(i).get("empId"));      // 메일 수신자 사번 : 필수
                        mailReq.put("toEmpNm", ds.get(i).get("empNm"));      // 메일 수신자 이름 : 필수
                        mailReq.put("toEmpEmail", ds.get(i).get("email"));      // 메일 수신자 메일주소 : 필수
                        mailReq.put("gubun", ds.get(i).get("gubun"));   // 구분

                        log.info("***************************************** fmSecIoEmpViolation_ActDo LOG *****************************************");
                        log.info("***************************** MAIL FORM  [mailReq] : " + paramMap);
                        log.info("*****************************************************************************************************************");

                        if ("C0280002".indexOf(actDo) > -1) { // 시정계획서 징구
                            fmSecIoEmpViolationJingGuMailSend(mailReq);
                        }
                        else if ("C0280006".indexOf(actDo) > -1) { //  대표이사 시정공문 징구
                            fmSecIoEmpViolationExprFileJingGuMailSend(mailReq);
                        }
                        else if ("C0280008".indexOf(actDo) > -1) { //  1개월출입정지
                            fmSecIoEmpViolationExprOneMonthMailSend(mailReq);
                        }
                        else if ("C0280007".indexOf(actDo) > -1) { //  영구출입정지
                            fmSecIoEmpViolationExprUnlimitMailSend(mailReq);
                        }
                        else if ("C0280012".indexOf(actDo) > -1) { //  6개월출입정지
                            fmSecIoEmpViolationExprSixMonthMailSend(mailReq);
                        }

                        // ========== SMS 발송 ========== //
                        // 주석처리 2023-06-09
                        //						if("1".toString().equals(ds.get(i).get("gubun").toString())  //위규자
                        //				    			||  "4".toString().equals(ds.get(i).get("gubun").toString()) // 대표관리자(정)
                        //				    			||  "6".toString().equals(ds.get(i).get("gubun").toString()) // 대표관리자(부)
                        //				    	   )
                        //						{
                        //							String hpNo = (String) ds.get(i).get("hpNo");
                        //						    if(!"".equals(hpNo) && hpNo.length() > 0) {
                        //						    	String msg = "";
                        //						    	RequestWrapModel<KakaoMessageDTO> wrapParams = new RequestWrapModel<>();
                        //						    	KakaoMessageDTO kakaoMessageDTO = new KakaoMessageDTO();
                        //
                        //						    	if("C0280002".indexOf(actDo) > -1) { // 시정계획서 징구
                        //						    		msg = "[SK하이닉스] " + ofendEmpNm + "께서는 welcome.skhynix.com에서 시정계획서를 즉시 제출 바랍니다.";
                        //						    		kakaoMessageDTO.setKTemplateCode("SJT_066384");
                        //								}
                        //								else if("C0280006".indexOf(actDo) > -1) { //  대표이사 시정공문 징구
                        //									msg = "[SK하이닉스] " + ofendEmpNm + "께서는 welcome.skhynix.com에서 시정공문을 즉시 제출 바랍니다.";
                        //									kakaoMessageDTO.setKTemplateCode("SJT_066384");
                        //								}
                        //								else if("C0280008".indexOf(actDo) > -1) { //  1개월출입정지
                        //									msg = "[SK하이닉스] " + ofendEmpNm + "께서는 보안위규로 1개월 출입정지 처리되었습니다.";
                        //									kakaoMessageDTO.setKTemplateCode("SJT_066385");
                        //								}
                        //								else if("C0280007".indexOf(actDo) > -1) { //  영구출입정지
                        //									msg = "[SK하이닉스] " + ofendEmpNm + "께서는 보안위규로 영구 출입정지 처리되었습니다.";
                        //									kakaoMessageDTO.setKTemplateCode("SJT_066386");
                        //								}
                        //								else if("C0280012".indexOf(actDo) > -1) { //  6개월입정지
                        //									msg = "[SK하이닉스] " + ofendEmpNm + "께서는 보안위규로 6개월 출입정지 처리되었습니다.";
                        //									kakaoMessageDTO.setKTemplateCode("SJT_066388");
                        //								}
                        //
                        //								kakaoMessageDTO.setSubject("보안위규");
                        //								kakaoMessageDTO.setDstaddr(hpNo.replaceAll("-", ""));
                        //								kakaoMessageDTO.setCallback("03151854114");
                        //								kakaoMessageDTO.setText(msg);
                        //								kakaoMessageDTO.setText2(msg);
                        //								kakaoMessageDTO.setKAttach("");
                        //								kakaoMessageDTO.setEmpId((String) ds.get(0).get("empId"));
                        //
                        //								wrapParams.setParams(kakaoMessageDTO);
                        //								commonApiClient.sendKakaoMessage(wrapParams);
                        //
                        //						    }
                        //						}
                        // ========== SMS 발송 ========== //
                    }
                }
            }
        }
        return resultMap;
    }

    private void fmPassExcptIF_Stop(Map<String, Object> ds) {

//        if (ds.get("exprApplNo") == null || "".equals(ds.get("exprApplNo"))) {
//            int exprNo = repository.dmPassExcptGetExprApplNo(); // 하이스텍 IDCARD_VISIT 로 전송시 EXPR_APPL_NO 를 임의로 생성하여 전송하여야함. 관련 테이블에서 키값으로 사용함.
//            ds.put("exprApplNo", exprNo);
//        }
        System.out.println("==========>>>>> 하이스텍 정지신청 I/F 호출 <<<<<==========");
        System.out.println("EXPR_APPL_NO =  " + ds.get("exprApplNo"));
        System.out.println("S_C_GBN =  " + ds.get("sCGbn"));
        System.out.println("IDCARD_ID =  " + ds.get("idcardId"));
        System.out.println("EXPR_CODE =  " + ds.get("exprCode"));
        System.out.println("==========>>>>> 하이스텍 정지신청 I/F 호출 <<<<<==========");

        log.info("***************************************** fmPassExcptIF_Stop LOG *****************************************");
        log.info("*****************************dmPassExcptIF_IoPassExprHist 운영에서만 등록 [param] : " + ds);
        log.info("**************************************************************************************************************************************");

        //---------- 하이스텍 정지신청 하는 I/F Procedure 호출해야 함 ----------//
        //TODO:(hoonLee) 카드 정지 인터페이스 호출 향후 profile(prd) 확인요망, 주석해제
        if (environment.acceptsProfiles(Profiles.of("prd"))) {
            /*dbExecuteProcedure("dmPassExcptIF_IoPassExprHist", requestData.getFieldMap(),"_IDcard_Visit", onlineCtx);*/
            //idCardVisit.dmPassExcptIF_IoPassExprHist(ds);
        }
        else {
            log.info("***************************************** fmPassExcptIF_Stop LOG *****************************************");
            log.info("*****************************운영이 아니라서 실행 하지 않음 [param] : " + ds);
            log.info("**************************************************************************************************************************************");
        }
        //---------- 하이스텍 정지신청 하는 I/F Procedure 호출해야 함 ----------//
    }

    private void fmSecIoEmpViolationExprUnlimitMailSend(Map<String, Object> mailReq) {
        String schemaNm = StringUtils.defaultIfEmpty((String) mailReq.get("schemaNm"), "");

        String sendFromName = (String) mailReq.get("ofendEmpNm");
        String sendFromID = (String) mailReq.get("crtBy");
        String sendFromEmail = (String) mailReq.get("ofendEmpEmail");
        String gubun = (String) mailReq.get("gubun");

        String sendFromMail = "skhystec_security@skhystec.com";

        String sendToName = (String) mailReq.get("toEmpNm");
        String sendToID = (String) mailReq.get("toEmpId");
        String sendToEmail = (String) mailReq.get("toEmpEmail");
        String domain = securityExtnetUrl;

        String actCompNm = (String) mailReq.get("actCompNm");
        String ofendCompNm = (String) mailReq.get("compKoNm");
        String ofendJwNm = (String) mailReq.get("ofendJwNm");
        String ofendDt = (String) mailReq.get("ofendDt");
        String ofendTm = (String) mailReq.get("ofendTm");
        String ofendGbnNm = (String) mailReq.get("ofendGbnNm");
        String ofendDetailNm = (String) mailReq.get("ofendDetailNm");
        String ofendLocNm = (String) mailReq.get("ofendLoc"); // 장소

        String title = "";
        String message = "";

        // 현업부서만 메일제목을 다르게 함 : 2016-08-31 by JSH
        if ("7".equals(gubun)) {
            title = "[e-Security] 외부인 보안위규 발생 관련 당사 구성원 안내";
        }
        else {
            title = "[행복한 만남] 보안위규 관련, 영구 출입정지 안내";
        }

        message = sendFromName + "님께서는 아래 보안위규로 인해, 「영구 출입정지」 되었습니다.";

        StringBuffer str = new StringBuffer();

        str.append(" <!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'> \n");
        str.append(" <html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"ko\" lang=\"ko\"> \n");
        str.append(" <head> \n");
        str.append(" <title>입주사-행복한 만남 - SK쉴더스</title> \n");
        str.append(" <style>			 \n");
        str.append(" body { margin:0; padding:0; border:0; font-size:0.75em; line-height:1.8em; letter-spacing:0px; color:#7D7D7D;			scrollbar-3dlight-color:#ffffff; scrollbar-arrow-color:#666666; scrollbar-base-color:#ffffff; scrollbar-darkshadow-color:#d7d7d7;			scrollbar-face-color:#f2f2f2; scrollbar-highlight-color:#f2f2f2;	scrollbar-shadow-color:#f2f2f2;	scrollbar-track-color:#f2f2f2; }			 \n");
        str.append(" *html, body{height:100%; overflow-x:hidden;}			 \n");
        str.append(" body, table	 {width:100%;margin:0px;padding:0px;text-align:left}			 \n");
        str.append(" body, select, input,textarea{margin:0px;padding:0px; font-family:'나눔고딕', '맑은 고딕', 'Malgun Gothic', NanumGothicBold, AppleGothic, 'Lucida Grande', Tahoma, Verdana, UnDotum, Dotum, sans-serif;}			 \n");
        str.append(" div, p, ul, li, dl, dt, dd, h1, h2, h3, h4, h5, form	{margin:0px;padding:0px;list-style:none}			 \n");
        str.append(" img, table {border:0px none; font-size:13px;}			 \n");
        str.append(" a {border:0px none}			 \n");
        str.append(" a,img,input {text-align:absmiddle;margin:0px;padding:0px}			 \n");
        str.append(" select{font-size:1em;}			 \n");
        str.append(" @font-face{font-family:NanumGothic; src:url(http://www.skhystec.com/down/esecurity/common/css/NanumGothic.ttf); src:url(http://www.skhystec.com/down/esecurity/common/css/NanumGothic.ttf)  format(truetype);}			@font-face{font-family:NanumGothic; src:url(http://www.skhystec.com/down/esecurity/common/css/NanumGothic.eot); src: url(http://www.skhystec.com/down/esecurity/common/css/NanumGothic.eot?iefix) format(eot);}			@font-face {font-family:NanumGothicBold; src: url(http://www.skhystec.com/down/esecurity/common/css/NanumGothicBold.eot); src: url(http://www.skhystec.com/down/esecurity/common/css/NanumGothicBold.eot?iefix) format(eot), url(http://www.skhystec.com/down/esecurity/common/css/NanumGothicBold.woff) format(woff), url(http://www.skhystec.com/down/esecurity/common/css/font/NanumGothicBold.ttf)  format(truetype), url(http://www.skhystec.com/down/esecurity/common/css/font/NanumGothicBold.svg#svgFontName) format(svg);}			 \n");
        str.append(" .yellow01 { color:#DD9619; font-weight: bold}			 \n");
        str.append(" #popBody01_security {width:700px; margin:30px auto;  padding-bottom:10px; font-family:'나눔고딕', AppleGothic, '맑은 고딕', 'Malgun Gothic', 'Lucida Grande', Tahoma, Verdana,  UnDotum, Dotum, sans-serif;}			 \n");
        str.append(" #popBody01_security #popArea_security {border-bottom:4px solid #F57724; overflow:hidden; }			 \n");
        str.append(" #popBody01_security .pop_title01_security{font-size:1.4em; padding:10px 0 5px 0; color:#F57724; font-weight:bold; letter-spacing:0px; overflow:hidden; height: 80px;}			#popBody01_security .pop_title01_security img{float:left;}			 \n");
        str.append(" #popBody01_security .pop_title01_security span{float:right; text-align:right; margin-top:33px; border:0px solid #000; width:550px;}			 \n");
        str.append(" #popBody01_security .close{float:right; padding:25px 10px 0 0px;}			 \n");
        str.append(" #popBody01_security .pop_content_security{margin:0px auto 10px auto; border:0px solid #000; background:#fff;}			 \n");
        str.append(" .mail_content_security{ width:700px; padding:50px 10px; border:1px solid #DFDFDF; overflow:hidden; margin:30px auto; background:#fff;} 			 \n");
        str.append(" .mail_left_security{float:left; margin-right:20px; }			 \n");
        str.append(" .mail_right_security{float:right; width:470px; border-left:1px dotted #A0B9A9; padding-left:20px; overflow:hidden; font-size:13px;}			 \n");
        str.append(" .mail_right_security dl{border-top:2px solid #000; border-bottom:2px solid #000; padding:10px; overflow:hidden; margin:10px 0 10px 0; font-weight:bold; color:#DD9619;}			.mail_right_security dt{float:left;}			 \n");
        str.append(" .mail_right_security dd{float:left; margin-left:10px;}			 \n");
        str.append(" .mail_title{font-size:14px; font-weight:bold; margin:10px 0; color:#000; padding:0 0 5px 10px; border-bottom:1px solid #DFDFDF; background:url('http://www.skhystec.com/down/esecurity/common/images/common/title06.png') no-repeat 0 3px;}			 \n");
        str.append(" .notice_01{margin-top:25px; padding-left:50px; background:url('http://www.skhystec.com/down/esecurity/common/images/common/notice.jpg') no-repeat 0 5px;}			 \n");
        str.append(" .notice_01 a:link{color:#5EA0D0; font-weight:bold; text-decoration:underline;}			 \n");
        str.append(" .mail_bottom_security{clear:both; font-size:1em; border-top:3px solid #C4C4C4; padding-top:10px; line-height:1.4em;}		 \n");
        str.append(" </style> \n");
        str.append(" </head> \n");
        str.append(" <body> \n");
        str.append(" <div id=\"popBody01_security\"> \n");
        str.append(" <div id=\"popArea_security\"> \n");
        str.append(" <div class=\"pop_title01_security\"><img src=\"http://www.skhystec.com/down/esecurity/common/images/common/mail_logo.jpg\"><span>" + title + "</span></div> \n");
        str.append(" </div> \n");
        str.append(" <div class=\"pop_content_security\"> \n");
        str.append(" <div class=\"mail_content_security\"> \n");
        str.append(" <div class=\"mail_left_security\"><img src=\"http://www.skhystec.com/down/esecurity/common/images/common/mail_img01.jpg\"> \n");
        str.append(" </div> \n");
        str.append(" <div class=\"mail_right_security\"> \n");
        str.append(" 	<div style=\" padding:4px 0px;line-height:15px;\"><span style=\"font-weight:bold;\">" + sendFromName + "</span>님께서는 아래 보안위규로 인해, <span style=\"color:#f57724\">「영구 출입정지」</span>되었습니다.</div> \n");
        str.append(" 	<div style=\" padding:4px 0px;line-height:15px;\">행복한만남 사이트 :  <a target='_blank' href=\"" + domain + "\">welcome.skhynix.com > 기본정보 > 시정계획서 및 시정공문</a></div> \n");
        str.append(" <br> \n");
        str.append(" <table border=\"1\" bordercolor=\"#e7d1c2\" cellspacing=\"0\" cellpadding=\"5\" style=\"border-collapse:collapse;\"> \n");
        str.append(" <tbody> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">회사명</span></td> \n");
        str.append(" <td align=\"left\" width=\"80%\">" + ofendCompNm + "</td> \n");
        str.append(" </tr> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">성명 / 직위</span></td> \n");
        str.append(" <td align=\"left\" width=\"80%\">" + sendFromName + " " + ofendJwNm + "</td> \n");
        str.append(" </tr> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">일시</span></td> \n");
        str.append(" <td align=\"left\">" + ofendDt + " " + ofendTm + "</td> \n");
        str.append(" </tr> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">장소</span></td> \n");
        str.append(" <td align=\"left\">" + ofendLocNm + "</td> \n");
        str.append(" </tr> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">위규 내용</span></td> \n");
        str.append(" <td align=\"left\">" + ofendDetailNm + "</td> \n");
        str.append(" </tr> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">위규 처리</span></td> \n");
        str.append(" <td align=\"left\"><span style=\"color:#f57724\">「영구 출입정지」</span></td> \n");
        str.append(" </tr> \n");
        str.append(" </tbody> \n");
        str.append(" </table> \n");
        str.append(" </div> \n");
        str.append(" </div> \n");
        str.append(" <div class=\"mail_bottom_security\"><br> \n");
        str.append(" <br> \n");
        str.append(" 본 메일은 발신전용 메일입니다.<br> \n");
        str.append(" <strong>SK쉴더스</strong> 경기도 이천시 부발읍 경충대로 2091 (TEL 031.5185.4114)<br> \n");
        str.append(" Copyrightⓒ SK SHIELDUS Inc. All Rights Reserved.</div> \n");
        str.append(" </div> \n");
        str.append(" </div> \n");
        str.append(" </body> \n");
        str.append(" </html> \n");

        boolean result = mailing.sendMail(title, mailing.applyMailTemplate(title, str.toString()), sendToEmail, "", schemaNm, "", (String) mailReq.get("acIp"));
    }

    private void fmSecIoEmpViolationExprOneMonthMailSend(Map<String, Object> mailReq) {

        String schemaNm = StringUtils.defaultIfEmpty((String) mailReq.get("schemaNm"), "");

        String sendFromName = (String) mailReq.get("ofendEmpNm");
        String sendFromID = (String) mailReq.get("crtBy");
        String sendFromEmail = (String) mailReq.get("ofendEmpEmail");
        String gubun = (String) mailReq.get("gubun");

        String sendFromMail = "skhystec_security@skhystec.com";

        String sendToName = (String) mailReq.get("toEmpNm");
        String sendToID = (String) mailReq.get("toEmpId");
        String sendToEmail = (String) mailReq.get("toEmpEmail");
        String domain = securityExtnetUrl;

        String actCompNm = (String) mailReq.get("actCompNm");
        String denyStrtDt = (String) mailReq.get("denyStrtDt");
        String denyEndDt = (String) mailReq.get("denyEndDt");

        String ofendCompNm = (String) mailReq.get("compKoNm");
        String ofendJwNm = (String) mailReq.get("ofendJwNm");
        String ofendDt = (String) mailReq.get("ofendDt");
        String ofendTm = (String) mailReq.get("ofendTm");
        String ofendGbnNm = (String) mailReq.get("ofendGbnNm");
        String ofendDetailNm = (String) mailReq.get("ofendDetailNm");

        String ofendLocNm = (String) mailReq.get("ofendLoc"); // 장소
        String limit30Dtm = (String) mailReq.get("limit30Dtm");
        String title = "";
        String message = "";

        // 현업부서만 메일제목을 다르게 함 : 2016-08-31 by JSH
        if("7".equals(gubun)){
            title = "[e-Security] 외부인 보안위규 발생 관련 당사 구성원 안내";
        }else{
            title = "[행복한 만남] 보안위규 관련, 1개월 출입정지 안내";
        }

        message = sendFromName + "님께서는 아래 보안위규로 인해, 「1개월 출입정지」 되었습니다.";

        StringBuffer str = new StringBuffer();

        str.append(" <!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'> \n");
        str.append(" <html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"ko\" lang=\"ko\"> \n");
        str.append(" <head> \n");
        str.append(" <title>입주사-행복한 만남 - SK쉴더스</title> \n");
        str.append(" <style>			 \n");
        str.append(" body { margin:0; padding:0; border:0; font-size:0.75em; line-height:1.8em; letter-spacing:0px; color:#7D7D7D;			scrollbar-3dlight-color:#ffffff; scrollbar-arrow-color:#666666; scrollbar-base-color:#ffffff; scrollbar-darkshadow-color:#d7d7d7;			scrollbar-face-color:#f2f2f2; scrollbar-highlight-color:#f2f2f2;	scrollbar-shadow-color:#f2f2f2;	scrollbar-track-color:#f2f2f2; }			 \n");
        str.append(" *html, body{height:100%; overflow-x:hidden;}			 \n");
        str.append(" body, table	 {width:100%;margin:0px;padding:0px;text-align:left}			 \n");
        str.append(" body, select, input,textarea{margin:0px;padding:0px; font-family:'나눔고딕', '맑은 고딕', 'Malgun Gothic', NanumGothicBold, AppleGothic, 'Lucida Grande', Tahoma, Verdana, UnDotum, Dotum, sans-serif;}			 \n");
        str.append(" div, p, ul, li, dl, dt, dd, h1, h2, h3, h4, h5, form	{margin:0px;padding:0px;list-style:none}			 \n");
        str.append(" img, table {border:0px none; font-size:13px;}			 \n");
        str.append(" a {border:0px none}			 \n");
        str.append(" a,img,input {text-align:absmiddle;margin:0px;padding:0px}			 \n");
        str.append(" select{font-size:1em;}			 \n");
        str.append(" @font-face{font-family:NanumGothic; src:url(http://www.skhystec.com/down/esecurity/common/css/NanumGothic.ttf); src:url(http://www.skhystec.com/down/esecurity/common/css/NanumGothic.ttf)  format(truetype);}			@font-face{font-family:NanumGothic; src:url(http://www.skhystec.com/down/esecurity/common/css/NanumGothic.eot); src: url(http://www.skhystec.com/down/esecurity/common/css/NanumGothic.eot?iefix) format(eot);}			@font-face {font-family:NanumGothicBold; src: url(http://www.skhystec.com/down/esecurity/common/css/NanumGothicBold.eot); src: url(http://www.skhystec.com/down/esecurity/common/css/NanumGothicBold.eot?iefix) format(eot), url(http://www.skhystec.com/down/esecurity/common/css/NanumGothicBold.woff) format(woff), url(http://www.skhystec.com/down/esecurity/common/css/font/NanumGothicBold.ttf)  format(truetype), url(http://www.skhystec.com/down/esecurity/common/css/font/NanumGothicBold.svg#svgFontName) format(svg);}			 \n");
        str.append(" .yellow01 { color:#DD9619; font-weight: bold}			 \n");
        str.append(" #popBody01_security {width:700px; margin:30px auto;  padding-bottom:10px; font-family:'나눔고딕', AppleGothic, '맑은 고딕', 'Malgun Gothic', 'Lucida Grande', Tahoma, Verdana,  UnDotum, Dotum, sans-serif;}			 \n");
        str.append(" #popBody01_security #popArea_security {border-bottom:4px solid #F57724; overflow:hidden; }			 \n");
        str.append(" #popBody01_security .pop_title01_security{font-size:1.4em; padding:10px 0 5px 0; color:#F57724; font-weight:bold; letter-spacing:0px; overflow:hidden; height: 80px;}			#popBody01_security .pop_title01_security img{float:left;}			 \n");
        str.append(" #popBody01_security .pop_title01_security span{float:right; text-align:right; margin-top:33px; border:0px solid #000; width:550px;}			 \n");
        str.append(" #popBody01_security .close{float:right; padding:25px 10px 0 0px;}			 \n");
        str.append(" #popBody01_security .pop_content_security{margin:0px auto 10px auto; border:0px solid #000; background:#fff;}			 \n");
        str.append(" .mail_content_security{ width:700px; padding:50px 10px; border:1px solid #DFDFDF; overflow:hidden; margin:30px auto; background:#fff;} 			 \n");
        str.append(" .mail_left_security{float:left; margin-right:20px; }			 \n");
        str.append(" .mail_right_security{float:right; width:470px; border-left:1px dotted #A0B9A9; padding-left:20px; overflow:hidden; font-size:13px;}			 \n");
        str.append(" .mail_right_security dl{border-top:2px solid #000; border-bottom:2px solid #000; padding:10px; overflow:hidden; margin:10px 0 10px 0; font-weight:bold; color:#DD9619;}			.mail_right_security dt{float:left;}			 \n");
        str.append(" .mail_right_security dd{float:left; margin-left:10px;}			 \n");
        str.append(" .mail_title{font-size:14px; font-weight:bold; margin:10px 0; color:#000; padding:0 0 5px 10px; border-bottom:1px solid #DFDFDF; background:url('http://www.skhystec.com/down/esecurity/common/images/common/title06.png') no-repeat 0 3px;}			 \n");
        str.append(" .notice_01{margin-top:25px; padding-left:50px; background:url('http://www.skhystec.com/down/esecurity/common/images/common/notice.jpg') no-repeat 0 5px;}			 \n");
        str.append(" .notice_01 a:link{color:#5EA0D0; font-weight:bold; text-decoration:underline;}			 \n");
        str.append(" .mail_bottom_security{clear:both; font-size:1em; border-top:3px solid #C4C4C4; padding-top:10px; line-height:1.4em;}		 \n");
        str.append(" </style> \n");
        str.append(" </head> \n");
        str.append(" <body> \n");
        str.append(" <div id=\"popBody01_security\"> \n");
        str.append(" <div id=\"popArea_security\"> \n");
        str.append(" <div class=\"pop_title01_security\"><img src=\"http://www.skhystec.com/down/esecurity/common/images/common/mail_logo.jpg\"><span>" + title + "</span></div> \n");
        str.append(" </div> \n");
        str.append(" <div class=\"pop_content_security\"> \n");
        str.append(" <div class=\"mail_content_security\"> \n");
        str.append(" <div class=\"mail_left_security\"><img src=\"http://www.skhystec.com/down/esecurity/common/images/common/mail_img01.jpg\"> \n");
        str.append(" </div> \n");
        str.append(" <div class=\"mail_right_security\"> \n");
        str.append(" 	<div style=\" padding:4px 0px;line-height:15px;\"><span style=\"font-weight:bold;\">" + sendFromName + "</span>님께서는 아래 보안위규로 인해, <span style=\"color:#f57724\">「1개월 출입정지」</span>되었습니다.</div> \n");
        str.append(" 	<div style=\" padding:4px 0px;line-height:15px;\">행복한만남 사이트 :  <a target='_blank' href=\"" + domain + "\">welcome.skhynix.com > 기본정보 > 시정계획서 및 시정공문</a></div> \n");
        str.append(" <br> \n");
        str.append(" <table border=\"1\" bordercolor=\"#e7d1c2\" cellspacing=\"0\" cellpadding=\"5\" style=\"border-collapse:collapse;\"> \n");
        str.append(" <tbody> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">회사명</span></td> \n");
        str.append(" <td align=\"left\" width=\"80%\">" + ofendCompNm + "</td> \n");
        str.append(" </tr> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">성명 / 직위</span></td> \n");
        str.append(" <td align=\"left\" width=\"80%\">" + sendFromName + " " + ofendJwNm + "</td> \n");
        str.append(" </tr> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">일시</span></td> \n");
        str.append(" <td align=\"left\">" + ofendDt + " " + ofendTm + "</td> \n");
        str.append(" </tr> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">장소</span></td> \n");
        str.append(" <td align=\"left\">" + ofendLocNm + "</td> \n");
        str.append(" </tr> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">위규 내용</span></td> \n");
        str.append(" <td align=\"left\">" + ofendDetailNm + "</td> \n");
        str.append(" </tr> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">위규 처리</span></td> \n");
        str.append(" <td align=\"left\"><span style=\"color:#f57724\">「1개월 출입정지」 (기간 : " + denyStrtDt + " ~ " + denyEndDt + ")</span></td> \n");
        str.append(" </tr> \n");
        /*
         * str.append(" <tr> \n"); str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"30%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">처리 기준</span></td> \n"); str.append(" <td align=\"left\"> \n"); str.append(" 보안 위규 누적관리 기간은 1년이며, 시안에 따라 시정계획서 징구부터 영구 출입정지로 조치됨 \n"); str.append(" </td> \n"); str.append(" </tr> \n");
         */
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">주의 사항</span></td> \n");
        str.append(" <td align=\"left\"> \n");
        str.append(" - 추가 위반시 영구 출입정지 대상임 \n");
        str.append(" </td> \n");
        str.append(" </tr> \n");
        str.append(" </tbody> \n");
        str.append(" </table> \n");
        str.append(" </div> \n");
        str.append(" </div> \n");
        str.append(" <div class=\"mail_bottom_security\"><br> \n");
        str.append(" <br> \n");
        str.append(" 본 메일은 발신전용 메일입니다.<br> \n");
        str.append(" <strong>SK쉴더스</strong> 경기도 이천시 부발읍 경충대로 2091 (TEL 031.5185.4114)<br> \n");
        str.append(" Copyrightⓒ SK SHIELDUS Inc. All Rights Reserved.</div> \n");
        str.append(" </div> \n");
        str.append(" </div> \n");
        str.append(" </body> \n");
        str.append(" </html> \n");

        boolean result = mailing.sendMail(title, mailing.applyMailTemplate(title, str.toString()), sendToEmail, "", schemaNm, "", (String) mailReq.get("acIp"));
    }

    private void fmSecIoEmpViolationExprSixMonthMailSend(Map<String, Object> mailReq) {

        String schemaNm = StringUtils.defaultIfEmpty((String) mailReq.get("schemaNm"), "");

        String sendFromName = (String) mailReq.get("ofendEmpNm");
        String sendFromID = (String) mailReq.get("crtBy");
        String sendFromEmail = (String) mailReq.get("ofendEmpEmail");
        String gubun = (String) mailReq.get("gubun");

        String sendFromMail = "skhynix_security@sk.com";

        String sendToName = (String) mailReq.get("toEmpNm");
        String sendToID = (String) mailReq.get("toEmpId");
        String sendToEmail = (String) mailReq.get("toEmpEmail");
        String domain = securityExtnetUrl;

        String actCompNm = (String) mailReq.get("actCompNm");

        String denyStrtDt = (String) mailReq.get("denyStrtDt");
        String denyEndDt = (String) mailReq.get("denyEndDt");

        String ofendCompNm = (String) mailReq.get("compKoNm");
        String ofendJwNm = (String) mailReq.get("ofendJwNm");
        String ofendDt = (String) mailReq.get("ofendDt");
        String ofendTm = (String) mailReq.get("ofendTm");
        String ofendGbnNm = (String) mailReq.get("ofendGbnNm");
        String ofendDetailNm = (String) mailReq.get("ofendDetailNm");

        String ofendLocNm = (String) mailReq.get("ofendLoc"); // 장소
        String title = "";
        String message = "";
        String msgType = "W";

        // 현업부서만 메일제목을 다르게 함 : 2016-08-31 by JSH
        if ("7".equals(gubun)) {
            title = "[e-Security] 외부인 보안위규 발생 관련 당사 구성원 안내";
            msgType = "S";
        }
        else {
            title = "[행복한 만남] 보안위규 관련, 6개월 출입정지 안내";
        }

        message = sendFromName + "님께서는 아래 보안위규로 인해, 「6개월 출입정지」 되었습니다.";

        StringBuffer str = new StringBuffer();

        str.append(" <div > \n");
        str.append(" 	<div style=\" padding:4px 0px;line-height:15px;\"><span style=\"font-weight:bold;\">" + sendFromName
                   + "</span>님께서는 아래 보안위규로 인해, <span style=\"color:#f57724\">「6개월 출입정지」</span>되었습니다.</div> \n");
        str.append(" 	<div style=\" padding:4px 0px;line-height:15px;\">행복한만남 사이트 :  <a target='_blank' href=\"" + domain + "\">welcome.skhynix.com > 기본정보 > 시정계획서 및 시정공문</a></div> \n");
        str.append(" <br> \n");
        str.append(" <table width='80%' border=\"1\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-collapse:collapse; font-family:Malgun Gothic,  Dotum; font-size:14px;  \"> \n");
        str.append(" <tbody> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">회사명</span></td> \n");
        str.append(" <td align=\"left\" width=\"80%\">" + ofendCompNm + "</td> \n");
        str.append(" </tr> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">성명 / 직위</span></td> \n");
        str.append(" <td align=\"left\" width=\"80%\">" + sendFromName + " " + ofendJwNm + "</td> \n");
        str.append(" </tr> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">일시</span></td> \n");
        str.append(" <td align=\"left\">" + ofendDt + " " + ofendTm + "</td> \n");
        str.append(" </tr> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">장소</span></td> \n");
        str.append(" <td align=\"left\">" + ofendLocNm + "</td> \n");
        str.append(" </tr> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">위규 내용</span></td> \n");
        str.append(" <td align=\"left\">" + ofendDetailNm + "</td> \n");
        str.append(" </tr> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">위규 처리</span></td> \n");
        str.append(" <td align=\"left\"><span style=\"color:#f57724\">「6개월 출입정지」 (기간 : " + denyStrtDt + " ~ " + denyEndDt + ")</span></td> \n");
        str.append(" </tr> \n");
/*	    str.append(" <tr> \n");
	    str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"30%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">처리 기준</span></td> \n");
	    str.append(" <td align=\"left\"> \n");
	    str.append(" 보안 위규 누적관리 기간은 1년이며, 시안에 따라 시정계획서 징구부터 영구 출입정지로 조치됨 \n");
	    str.append(" </td> \n");
	    str.append(" </tr> \n");*/
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">주의 사항</span></td> \n");
        str.append(" <td align=\"left\"> \n");
        str.append(" - 추가 위반시 영구 출입정지 대상임 \n");
        str.append(" </td> \n");
        str.append(" </tr> \n");
        str.append(" </tbody> \n");
        str.append(" </table> \n");
        str.append(" </div> \n");

        boolean result = mailing.sendMail(title, mailing.applyMailTemplate(title, str.toString()), sendToEmail, "", schemaNm, "", (String) mailReq.get("acIp"));
    }

    private void fmSecIoEmpViolationExprFileJingGuMailSend(Map<String, Object> mailReq) {
        String schemaNm = StringUtils.defaultIfEmpty((String) mailReq.get("schemaNm"), "");

        String sendFromName = (String) mailReq.get("ofendEmpNm");
        String sendFromID = (String) mailReq.get("crtBy");
        String sendFromEmail = (String) mailReq.get("ofendEmpEmail");
        String gubun = (String) mailReq.get("gubun");

        String sendFromMail = "skhystec_security@skhystec.com";

        String sendToName = (String) mailReq.get("toEmpNm");
        String sendToID = (String) mailReq.get("toEmpId");
        String sendToEmail = (String) mailReq.get("toEmpEmail");
        String domain = securityExtnetUrl;

        String actCompNm = (String) mailReq.get("actCompNm");
        String ofendCompNm = (String) mailReq.get("compKoNm");
        String ofendJwNm = (String) mailReq.get("ofendJwNm");
        String ofendDt = (String) mailReq.get("ofendDt");
        String ofendTm = (String) mailReq.get("ofendTm");
        String ofendGbnNm = (String) mailReq.get("ofendGbnNm");
        String ofendDetailNm = (String) mailReq.get("ofendDetailNm");

        String ofendLocNm = (String) mailReq.get("ofendLoc"); // 장소
        String limit14Dtm = (String) mailReq.get("limit14Dtm");

        String title = "";
        String message = "";

        // 현업부서만 메일제목을 다르게 함 : 2016-08-31 by JSH
        if("7".equals(gubun)){
            title = "[e-Security] 외부인 보안위규 발생 관련 당사 구성원 안내";
        }else{
            title = "[행복한 만남] 보안위규 관련, 시정공문 제출 필요 안내";
        }

        message = sendFromName + "님께서는 아래 보안위규 건에 대해, 행복한만남 사이트에 접속하여 「대표이사 시정공문」을 제출하시기 바랍니다.";

        String shotURL2 = "<a target='_blank' href=\"" + domain + "\">(시정공문 제출 바로가기)</a>";

        StringBuffer str = new StringBuffer();

        str.append(" <!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'> \n");
        str.append(" <html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"ko\" lang=\"ko\"> \n");
        str.append(" <head> \n");
        str.append(" <title>입주사-행복한 만남 - fSK쉴더스</title> \n");
        str.append(" <style>			 \n");
        str.append(" body { margin:0; padding:0; border:0; font-size:0.75em; line-height:1.8em; letter-spacing:0px; color:#7D7D7D;			scrollbar-3dlight-color:#ffffff; scrollbar-arrow-color:#666666; scrollbar-base-color:#ffffff; scrollbar-darkshadow-color:#d7d7d7;			scrollbar-face-color:#f2f2f2; scrollbar-highlight-color:#f2f2f2;	scrollbar-shadow-color:#f2f2f2;	scrollbar-track-color:#f2f2f2; }			 \n");
        str.append(" *html, body{height:100%; overflow-x:hidden;}			 \n");
        str.append(" body, table	 {width:100%;margin:0px;padding:0px;text-align:left}			 \n");
        str.append(" body, select, input,textarea{margin:0px;padding:0px; font-family:'나눔고딕', '맑은 고딕', 'Malgun Gothic', NanumGothicBold, AppleGothic, 'Lucida Grande', Tahoma, Verdana, UnDotum, Dotum, sans-serif;}			 \n");
        str.append(" div, p, ul, li, dl, dt, dd, h1, h2, h3, h4, h5, form	{margin:0px;padding:0px;list-style:none}			 \n");
        str.append(" img, table {border:0px none; font-size:13px;}			 \n");
        str.append(" a {border:0px none}			 \n");
        str.append(" a,img,input {text-align:absmiddle;margin:0px;padding:0px}			 \n");
        str.append(" select{font-size:1em;}			 \n");
        str.append(" @font-face{font-family:NanumGothic; src:url(http://www.skhystec.com/down/esecurity/common/css/NanumGothic.ttf); src:url(http://www.skhystec.com/down/esecurity/common/css/NanumGothic.ttf)  format(truetype);}			@font-face{font-family:NanumGothic; src:url(http://www.skhystec.com/down/esecurity/common/css/NanumGothic.eot); src: url(http://www.skhystec.com/down/esecurity/common/css/NanumGothic.eot?iefix) format(eot);}			@font-face {font-family:NanumGothicBold; src: url(http://www.skhystec.com/down/esecurity/common/css/NanumGothicBold.eot); src: url(http://www.skhystec.com/down/esecurity/common/css/NanumGothicBold.eot?iefix) format(eot), url(http://www.skhystec.com/down/esecurity/common/css/NanumGothicBold.woff) format(woff), url(http://www.skhystec.com/down/esecurity/common/css/font/NanumGothicBold.ttf)  format(truetype), url(http://www.skhystec.com/down/esecurity/common/css/font/NanumGothicBold.svg#svgFontName) format(svg);}			 \n");
        str.append(" .yellow01 { color:#DD9619; font-weight: bold}			 \n");
        str.append(" #popBody01_security {width:700px; margin:30px auto;  padding-bottom:10px; font-family:'나눔고딕', AppleGothic, '맑은 고딕', 'Malgun Gothic', 'Lucida Grande', Tahoma, Verdana,  UnDotum, Dotum, sans-serif;}			 \n");
        str.append(" #popBody01_security #popArea_security {border-bottom:4px solid #F57724; overflow:hidden; }			 \n");
        str.append(" #popBody01_security .pop_title01_security{font-size:1.4em; padding:10px 0 5px 0; color:#F57724; font-weight:bold; letter-spacing:0px; overflow:hidden; height: 80px;}			#popBody01_security .pop_title01_security img{float:left;}			 \n");
        str.append(" #popBody01_security .pop_title01_security span{float:right; text-align:right; margin-top:33px; border:0px solid #000; width:550px;}			 \n");
        str.append(" #popBody01_security .close{float:right; padding:25px 10px 0 0px;}			 \n");
        str.append(" #popBody01_security .pop_content_security{margin:0px auto 10px auto; border:0px solid #000; background:#fff;}			 \n");
        str.append(" .mail_content_security{ width:700px; padding:50px 10px; border:1px solid #DFDFDF; overflow:hidden; margin:30px auto; background:#fff;} 			 \n");
        str.append(" .mail_left_security{float:left; margin-right:20px; }			 \n");
        str.append(" .mail_right_security{float:right; width:470px; border-left:1px dotted #A0B9A9; padding-left:20px; overflow:hidden; font-size:13px;}			 \n");
        str.append(" .mail_right_security dl{border-top:2px solid #000; border-bottom:2px solid #000; padding:10px; overflow:hidden; margin:10px 0 10px 0; font-weight:bold; color:#DD9619;}			.mail_right_security dt{float:left;}			 \n");
        str.append(" .mail_right_security dd{float:left; margin-left:10px;}			 \n");
        str.append(" .mail_title{font-size:14px; font-weight:bold; margin:10px 0; color:#000; padding:0 0 5px 10px; border-bottom:1px solid #DFDFDF; background:url('http://www.skhystec.com/down/esecurity/common/images/common/title06.png') no-repeat 0 3px;}			 \n");
        str.append(" .notice_01{margin-top:25px; padding-left:50px; background:url('http://www.skhystec.com/down/esecurity/common/images/common/notice.jpg') no-repeat 0 5px;}			 \n");
        str.append(" .notice_01 a:link{color:#5EA0D0; font-weight:bold; text-decoration:underline;}			 \n");
        str.append(" .mail_bottom_security{clear:both; font-size:1em; border-top:3px solid #C4C4C4; padding-top:10px; line-height:1.4em;}		 \n");
        str.append(" </style> \n");
        str.append(" </head> \n");
        str.append(" <body> \n");
        str.append(" <div id=\"popBody01_security\"> \n");
        str.append(" <div id=\"popArea_security\"> \n");
        str.append(" <div class=\"pop_title01_security\"><img src=\"http://www.skhystec.com/down/esecurity/common/images/common/mail_logo.jpg\"><span>" + title + "</span></div> \n");
        str.append(" </div> \n");
        str.append(" <div class=\"pop_content_security\"> \n");
        str.append(" <div class=\"mail_content_security\"> \n");
        str.append(" <div class=\"mail_left_security\"><img src=\"http://www.skhystec.com/down/esecurity/common/images/common/mail_img01.jpg\"> \n");
        str.append(" </div> \n");
        str.append(" <div class=\"mail_right_security\"> \n");
        str.append(" 	<div style=\" padding:4px 0px;line-height:15px;\"><span style=\"font-weight:bold;\">" + sendFromName + "</span>님께서는 아래 보안위규 건에 대해, 행복한만남 사이트에 접속하여 <span style=\"color:#f57724\"> 「대표이사 시정공문」</span>을 제출하시기 바랍니다.</div> \n");
        str.append(" 	<div style=\" padding:4px 0px;line-height:15px;\"><span style=\"color:#f57724\">「대표이사 시정공문」을 14일 이내 미제출시에는 <span style=\"color:#f57724\">「1개월 출입정지」</span> 처리 됩니다.</span></div> \n");
        str.append(" 	<div style=\" padding:4px 0px;line-height:15px;\">행복한만남 사이트 :  <a target='_blank' href=\"" + domain + "\">welcome.skhynix.com > 기본정보 > 시정계획서 및 시정공문</a></div> \n");
        str.append(" <br> \n");
        str.append(" <table border=\"1\" bordercolor=\"#e7d1c2\" cellspacing=\"0\" cellpadding=\"5\" style=\"border-collapse:collapse;\"> \n");
        str.append(" <tbody> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">회사명</span></td> \n");
        str.append(" <td align=\"left\" width=\"80%\">" + ofendCompNm + "</td> \n");
        str.append(" </tr> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">성명 / 직위</span></td> \n");
        str.append(" <td align=\"left\" width=\"80%\">" + sendFromName + " " + ofendJwNm + "</td> \n");
        str.append(" </tr> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">일시</span></td> \n");
        str.append(" <td align=\"left\">" + ofendDt + " " + ofendTm + "</td> \n");
        str.append(" </tr> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">장소</span></td> \n");
        str.append(" <td align=\"left\">" + ofendLocNm + "</td> \n");
        str.append(" </tr> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">위규 내용</span></td> \n");
        str.append(" <td align=\"left\">" + ofendDetailNm + "</td> \n");
        str.append(" </tr> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">위규 처리</span></td> \n");
        str.append(" <td align=\"left\"><span style=\"color:#f57724\">14일 이내 「대표이사 시정공문」 제출 " + shotURL2 + "</span></td> \n");
        str.append(" </tr> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">제출 기한</span></td> \n");
        str.append(" <td align=\"left\"> \n");
        str.append(" " + limit14Dtm + " 24:00 까지 \n");
        str.append(" </td> \n");
        str.append(" </tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">주의 사항</span></td> \n");
        str.append(" <td align=\"left\"> \n");
        str.append(" - 상시출입증이 있는 상시출입자는 시정계획서를 미제출시 1개월 출입정지 처리함(상시출입증은 발급실에 반남)</br> \n");
        str.append(" - 그외 방문객은 시정계획서 제출시까지 방문예약 불가하며, 14일내 미제출시 1개월 출입정지 처리함 \n");
        str.append(" </td> \n");
        str.append(" </tr> \n");
        str.append(" </tbody> \n");
        str.append(" </table> \n");
        str.append(" </div> \n");
        str.append(" </div> \n");
        str.append(" <div class=\"mail_bottom_security\"><br> \n");
        str.append(" <br> \n");
        str.append(" 본 메일은 발신전용 메일입니다.<br> \n");
        str.append(" <strong>SK쉴더스</strong> 경기도 이천시 부발읍 경충대로 2091 (TEL 031.5185.4114)<br> \n");
        str.append(" Copyrightⓒ SK SHIELDUS Inc. All Rights Reserved.</div> \n");
        str.append(" </div> \n");
        str.append(" </div> \n");
        str.append(" </body> \n");
        str.append(" </html> \n");

        boolean result = mailing.sendMail(title, mailing.applyMailTemplate(title, str.toString()), sendToEmail, "", schemaNm, "", (String) mailReq.get("acIp"));
    }

    private void fmSecIoEmpViolationJingGuMailSend(Map<String, Object> mailReq) {

        String schemaNm = StringUtils.defaultIfEmpty((String) mailReq.get("schemaNm"), "");

        String sendFromName = (String) mailReq.get("ofendEmpNm");
        String sendFromID = (String) mailReq.get("crtBy");
        String sendFromEmail = (String) mailReq.get("ofendEmpEmail");
        String gubun = (String) mailReq.get("gubun");

        String sendFromMail = "skhystec_security@skhystec.com";

        String sendToName = (String) mailReq.get("toEmpNm");
        String sendToID = (String) mailReq.get("toEmpId");
        String sendToEmail = (String) mailReq.get("toEmpEmail");
        String domain = securityExtnetUrl;

        String actCompNm = (String) mailReq.get("actCompNm");
        String ofendJwNm = (String) mailReq.get("ofendJwNm");
        String ofendDt = (String) mailReq.get("ofendDt");
        String ofendTm = (String) mailReq.get("ofendTm");
        String ofendGbnNm = (String) mailReq.get("ofendGbnNm");
        String ofendDetailNm = (String) mailReq.get("ofendDetailNm");
        String ofendCompNm = (String) mailReq.get("compKoNm");

        String ofendLocNm = (String) mailReq.get("ofendLoc"); // 장소
        String limit14Dtm = (String) mailReq.get("limit14Dtm");

        String title = "";
        String message = "";

        // 현업부서만 메일제목을 다르게 함 : 2016-08-31 by JSH
        if ("7".equals(gubun)) {
            title = "[입주사-Security] 외부인 보안위규 발생 관련 당사 구성원 안내";
        }
        else {
            title = "[입주사-Security] 보안위규 관련, 시정계획서 작성 필요 안내";
        }

        message = sendFromName + "님께서는 아래 보안위규 건에 대해, 산업보안 서비스포털에 접속하여 「시정계획서」를 제출하시기 바랍니다.";

        String shotURL2 = "<a target='_blank' href=\"" + domain + "\">(시정계획서 제출 바로가기)</a>";

        StringBuffer str = new StringBuffer();

        str.append(" <!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'> \n");
        str.append(" <html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"ko\" lang=\"ko\"> \n");
        str.append("	<head>");
        str.append("		<title>입주사-행복한 만남 - SK쉴더스</title>");
        str.append("		<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />");
        str.append("		<meta name='title' content='e-security' />");
        str.append("		<meta name='Author' content='SK SHIELDUS' />");
        str.append("		<meta name='description' content='e-security' />");
        str.append("		<meta name='keywords' content='e-security' />");
        str.append("		<meta http-equiv='X-UA-Compatible' content='IE=Edge'>");
        str.append("		<meta name='viewport' content=' initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0,width=device-width'>");
        str.append("		<style>");
        str.append("			body { margin:0; padding:0; border:0; font-size:0.75em; line-height:1.8em; letter-spacing:0px; color:#7D7D7D;");
        str.append("			scrollbar-3dlight-color:#ffffff; scrollbar-arrow-color:#666666; scrollbar-base-color:#ffffff; scrollbar-darkshadow-color:#d7d7d7;");
        str.append("			scrollbar-face-color:#f2f2f2; scrollbar-highlight-color:#f2f2f2;	scrollbar-shadow-color:#f2f2f2;	scrollbar-track-color:#f2f2f2; }");
        str.append("			*html, body{height:100%; overflow-x:hidden;}");
        str.append("			body, table	 {width:100%;margin:0px;padding:0px;text-align:left}");
        str.append("			body, select, input,textarea{margin:0px;padding:0px; font-family:'나눔고딕', '맑은 고딕', 'Malgun Gothic', NanumGothicBold, AppleGothic, 'Lucida Grande', Tahoma, Verdana, UnDotum, Dotum, sans-serif;}");
        str.append("			div, p, ul, li, dl, dt, dd, h1, h2, h3, h4, h5, form	{margin:0px;padding:0px;list-style:none}");
        str.append("			img, table {border:0px none}");
        str.append("			a {border:0px none}");
        str.append("			a,img,input {text-align:absmiddle;margin:0px;padding:0px}");
        str.append("			select{font-size:1em;}");
        str.append("			@font-face{font-family:NanumGothic; src:url(http://www.skhystec.com/down/esecurity/common/css/NanumGothic.ttf); src:url(http://www.skhystec.com/down/esecurity/common/css/NanumGothic.ttf)  format(truetype);}");
        str.append("			@font-face{font-family:NanumGothic; src:url(http://www.skhystec.com/down/esecurity/common/css/NanumGothic.eot); src: url(http://www.skhystec.com/down/esecurity/common/css/NanumGothic.eot?iefix) format(eot);}");
        str.append("			@font-face {font-family:NanumGothicBold; src: url(http://www.skhystec.com/down/esecurity/common/css/NanumGothicBold.eot); src: url(http://www.skhystec.com/down/esecurity/common/css/NanumGothicBold.eot?iefix) format(eot), url(http://www.skhystec.com/down/esecurity/common/css/NanumGothicBold.woff) format(woff), url(http://www.skhystec.com/down/esecurity/common/css/font/NanumGothicBold.ttf)  format(truetype), url(http://www.skhystec.com/down/esecurity/common/css/font/NanumGothicBold.svg#svgFontName) format(svg);}");
        str.append("			.yellow01 { color:#DD9619; font-weight: bold}");
        str.append("			#popBody01_security {width:700px; margin:30px auto;  padding-bottom:10px; font-family:'나눔고딕', AppleGothic, '맑은 고딕', 'Malgun Gothic', 'Lucida Grande', Tahoma, Verdana,  UnDotum, Dotum, sans-serif;}");
        str.append("			#popBody01_security #popArea_security {border-bottom:4px solid #F57724; overflow:hidden; }");
        str.append("			#popBody01_security .pop_title01_security{font-size:1.4em; padding:10px 0 5px 0; color:#F57724; font-weight:bold; letter-spacing:0px; overflow:hidden; height: 80px;}");
        str.append("			#popBody01_security .pop_title01_security img{float:left;}");
        str.append("			#popBody01_security .pop_title01_security span{float:right; text-align:right; margin-top:33px; border:0px solid #000; width:550px;}");
        str.append("			#popBody01_security .close{float:right; padding:25px 10px 0 0px;}");
        str.append("			#popBody01_security .pop_content_security{margin:0px auto 10px auto; border:0px solid #000; background:#fff;}");
        str.append("			.mail_content_security{ width:700px; padding:50px 30px; border:1px solid #DFDFDF; overflow:hidden; margin:30px auto; background:#fff;} ");
        str.append("			.mail_left_security{float:left; margin-right:20px; }");
        str.append("			.mail_right_security{float:right; width:420px; border-left:1px dotted #A0B9A9; padding-left:20px; overflow:hidden; font-size:12px;}");
        str.append("			.mail_right_security dl{border-top:2px solid #000; border-bottom:2px solid #000; padding:10px; overflow:hidden; margin:10px 0 10px 0; font-weight:bold; color:#DD9619;}");
        str.append("			.mail_right_security dt{float:left;}");
        str.append("			.mail_right_security dd{float:left; margin-left:10px;}");
        str.append("			.mail_title{font-size:14px; font-weight:bold; margin:10px 0; color:#000; padding:0 0 5px 10px; border-bottom:1px solid #DFDFDF; background:url('http://www.skhystec.com/down/esecurity/common/images/common/title06.png') no-repeat 0 3px;}");
        str.append("			.notice_01{margin-top:25px; padding-left:50px; background:url('http://www.skhystec.com/down/esecurity/common/images/common/notice.jpg') no-repeat 0 5px;}");
        str.append("			.notice_01 a:link{color:#5EA0D0; font-weight:bold; text-decoration:underline;}");
        str.append("			.mail_bottom_security{clear:both; font-size:1em; border-top:3px solid #C4C4C4; padding-top:10px; line-height:1.4em;}");
        str.append("		</style>");
        str.append("	</head>");
        str.append(" <body> \n");
        str.append(" <div id=\"popBody01_security\"> \n");
        str.append(" <div id=\"popArea_security\"> \n");
        str.append(" <div class=\"pop_title01_security\"><img src='http://www.skhystec.com/down/esecurity/common/images/common/mail_logo.jpg' /><span>" + title + "</span></div> \n");
        str.append(" </div> \n");
        str.append(" <div class=\"pop_content_security\"> \n");
        str.append(" <div class=\"mail_content_security\"> \n");
        str.append(" <div class=\"mail_left_security\"><img src='http://www.skhystec.com/down/esecurity/common/images/common/mail_img01.jpg' /> \n");
        str.append(" </div> \n");
        str.append(" <div class=\"mail_right_security\"> \n");
        str.append(" 	<div style=\" padding:4px 0px;line-height:15px;\"><span style=\"font-weight:bold;\">" + sendFromName + "</span>님께서는 아래 보안위규 건에 대해, 행복한만남 사이트에 접속하여 <span style=\"color:#f57724\"> 「시정계획서」</span>를 제출하시기 바랍니다.</div> \n");
        str.append(" 	<div style=\" padding:4px 0px;line-height:15px;\"><span style=\"color:#f57724\">시정계획서를 14일 이내 미제출시에는 <span style=\"color:#f57724\">「1개월 출입정지」</span> 처리 됩니다.</span></div> \n");
        str.append(" 	<div style=\" padding:4px 0px;line-height:15px;\">행복한만남 사이트 :  <a target='_blank' href=\"" + domain + "\">welcome.skhynix.com > 기본정보 > 시정계획서 및 시정공문</a></div> \n");
        str.append(" <br> \n");
        str.append(" <table border=\"1\" bordercolor=\"#e7d1c2\" cellspacing=\"0\" cellpadding=\"5\" style=\"border-collapse:collapse;\"> \n");
        str.append(" <tbody> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">회사명</span></td> \n");
        str.append(" <td align=\"left\" width=\"80%\">" + ofendCompNm + "</td> \n");
        str.append(" </tr> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">성명 / 직위</span></td> \n");
        str.append(" <td align=\"left\" width=\"80%\">" + sendFromName + " " + ofendJwNm + "</td> \n");
        str.append(" </tr> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">일시</span></td> \n");
        str.append(" <td align=\"left\">" + ofendDt + " " + ofendTm + "</td> \n");
        str.append(" </tr> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">장소</span></td> \n");
        str.append(" <td align=\"left\">" + ofendLocNm + "</td> \n");
        str.append(" </tr> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">위규 내용</span></td> \n");
        str.append(" <td align=\"left\">" + ofendDetailNm + "</td> \n");
        str.append(" </tr> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">위규 처리</span></td> \n");
        str.append(" <td align=\"left\"><span style=\"color:#f57724\">14일 이내 시정계획서 제출 " + shotURL2 + "</span></td> \n");
        str.append(" </tr> \n");
        str.append(" <tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">제출 기한</span></td> \n");
        str.append(" <td align=\"left\"> \n");
        str.append(" " + limit14Dtm + " 24:00 까지 \n");
        str.append(" </td> \n");
        str.append(" </tr> \n");
        str.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">주의 사항</span></td> \n");
        str.append(" <td align=\"left\"> \n");
        str.append(" - 상시출입증이 있는 상시출입자는 시정계획서를 미제출시 1개월 출입정지 처리함(상시출입증은 발급실에 반남)</br> \n");
        str.append(" - 그외 방문객은 시정계획서 제출시까지 방문예약 불가하며, 14일내 미제출시 1개월 출입정지 처리함 \n");
        str.append(" </td> \n");
        str.append(" </tr> \n");
        str.append(" </tbody> \n");
        str.append(" </table> \n");
        str.append(" </div> \n");
        str.append(" </div> \n");
        str.append(" <div class=\"mail_bottom_security\"><br> \n");
        str.append(" <br> \n");
        str.append(" 본 메일은 발신전용 메일입니다.<br> \n");
        str.append(" <strong>SK쉴더스</strong> 경기도 이천시 부발읍 경충대로 2091 (TEL 031.5185.4114)<br> \n");
        str.append(" Copyrightⓒ SK SHIELDUS Inc. All Rights Reserved.</div> \n");
        str.append(" </div> \n");
        str.append(" </div> \n");
        str.append(" </body> \n");
        str.append(" </html> \n");

        boolean result = mailing.sendMail(title, mailing.applyMailTemplate(title, str.toString()), sendToEmail, "", schemaNm, "", (String) mailReq.get("acIp"));
    }

    private Map<String, Object> dmSecIoEmp_Violation_PassExprHist_I(Map<String, Object> paramMap) {
        Map<String, Object> result = new HashMap<String, Object>();

        int rsSeq = 0;
        int resultInt = 0;

        rsSeq = repository.dmSecIoEmp_Violation_PassExprHist_Seq_S();

        paramMap.put("exprApplNo", rsSeq);
        result.put("exprApplNo", rsSeq);

        resultInt = repository.dmSecIoEmp_Violation_PassExprHist_I(paramMap);

        if(resultInt < 1) {
            result.put("result", "FAIL");
        }
        else {
            result.put("result", "OK");
        }


        log.info("***************************************** fmSecIoEmpViolation_ActDo > dmSecIoEmp_Violation_PassExprHist_I LOG *****************************************");
        log.info("*****************************  [param] : " + paramMap);
        log.info("*******************************************************************************************************************************************************");

        /* 카드 및 IDCARD_ID 정보 Get */
        String actDo = (String) paramMap.get("actDo");
        if ("C0280007,C0280008".indexOf(actDo) > -1) {
            Map<String, Object> rsCard = new HashMap<String, Object>();

            rsCard = repository.dmSecIoEmp_Violation_Idcard_Info(paramMap);

            if (rsCard == null) {
                result.put("idcardId", "");
            }
            else {
                result.put("idcardId", (String) rsCard.get("idcardId"));
            }

            log.info("***************************************** dmSecIoEmp_Violation_Idcard_Info LOG *****************************************");
            log.info("***************************** [rsCard] : " + rsCard);
            log.info("*************************************************************************************************************************");
        }

        return result;
    }

    private int ioViolationInsert(HashMap<String, Object> paramMap) {


        String ofendGbn = (String) paramMap.get("ofendGbn");
        String ofendDetlGbn = (String) paramMap.get("ofendDetailGbn");
        String ofendSubGbn = (String) paramMap.get("ofendSubGbn");

        // ---------- ACT_DO 컬럼에 외부인확인으로 Default 입력 되도록 예외처리를 함 : 2016-08-08 by JSH
        if ("C0581015".equals(ofendDetlGbn) && "C0591098".equals(ofendSubGbn)) {
            int cnt1 = repository.dmSecIoEmpViolation_Excpt_Cnt1(paramMap);
            if(cnt1 < 1) {
                paramMap.put("defaultActYn", "Y");
                paramMap.put("actDo", "C0280011"); //외부인 확인
            }
        }

        int resultCnt = 0;
        int insertInterviewerCnt = 0;

        // 외부인 위규자 저장
        resultCnt = repository.ioViolationInsert(paramMap); /*dmSecIoEmp_Violation_I*/

        // 접견자 리스트 저장
        List<Map<String, Object>> interviewerList = objectMapper.convertValue(paramMap.get("interviewerList"),
            new TypeReference<List<Map<String, Object>>>() {
            });

        for (Map<String, Object> reqParam : interviewerList) {
            reqParam.put("acIp", paramMap.get("acIp"));
            reqParam.put("empId", paramMap.get("crtBy"));
            reqParam.put("scIoDocNo", paramMap.get("scIoDocNo"));
            repository.ioViolationInterviewerInsert(reqParam);
            insertInterviewerCnt++;
        }

        log.info("[ioViolationInsert]insertInterviewerCnt >> {}", insertInterviewerCnt);

        return resultCnt;
    }

    @Override
    public Boolean ioViolationSsmSave(HashMap<String, Object> paramMap) {
        boolean result = false;
        //채번
        int scIoOfendDocNo = repository.getScIoDocNo(); /*dmSecIoEmp_Violation_Seq_S*/

        paramMap.put("scIoDocNo", scIoOfendDocNo);

        int resultDs = repository.dmSecIoEmp_Violation_ssm_I(paramMap);

        if (resultDs > 0) {
            result = true;
        }

        return result;
    }

    @Override
    public ListDTO<Map<String, Object>> selectIoViolationList(HashMap<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;
        Integer totalCount = 0;

        try {
            resultList = repository.selectIoViolationList(paramMap);
            totalCount = repository.selectIoViolationListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return ListDTO.getInstance(resultList, totalCount);
    }

    @Override
    public int selectIoViolationListCnt(HashMap<String, Object> paramMap) {
        int listCnt = 0;

        try {

            listCnt = repository.selectIoViolationListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return listCnt;
    }

    @Override
    public Map<String, Object> selectIoViolationDetail(HashMap<String, Object> paramMap) {
        Map<String, Object> result = null;

        try {

            String operateId = "R_VIOLATION"; // 운영

            if (environment.acceptsProfiles(Profiles.of("default", "dev", "stg"))) { //로컬, 개발
                operateId = "D_VIOLATION";
            }

            paramMap.put("operateId", operateId);

            result = repository.selectIoViolationDetail(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> selectIoViolationDetailInterviewerList(HashMap<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectIoViolationDetailInterviewerList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public List<Map<String, Object>> selectIoViolationSameHistoryList(HashMap<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectIoViolationSameHistoryList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public List<Map<String, Object>> selectIoViolationActHistoryList(HashMap<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {

            resultList = repository.selectIoViolationActHistoryList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Boolean ioEmpViolation_Act(HashMap<String, Object> paramMap) {
        boolean result = false;

        try {
            Map<String, Object> resultMap = new HashMap<>();
            paramMap.put("crtBy", (String) paramMap.get("empId"));

            String actDo = (String) paramMap.get("actDo");

            // 1, 6개월출입정지와 영구출입정지인 경우 결재를 추가 (C0280007-영구출입정지, C0280008-1개월출입정지, C0280012-6개월출입정지, )
            if ("C0280007".equals(actDo) || "C0280008".equals(actDo) || "C0280012".equals(actDo)) {

                //결재승인
                if ("Z0331005".equals(paramMap.get("applStat"))) {
                    resultMap = fmSecIoEmpViolation_ActDo(paramMap);
                    result = (boolean) resultMap.get("result");
                }
                else {
                    result = ioEmpViolationApproval(paramMap); //결재상신
                }
            }
            else {
                resultMap = fmSecIoEmpViolation_ActDo(paramMap);
                result = (boolean) resultMap.get("result");
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    private boolean ioEmpViolationApproval(HashMap<String, Object> paramMap) {
        boolean result = false;

        try {

            // ================= NOTE: [통합결재정보] 저장 시작 =======================
            ApprovalDTO approval = objectMapper.convertValue(paramMap.get("approval"), ApprovalDTO.class);
            Map<String, Object> htmlMap = objectMapper.convertValue(paramMap.get("htmlMap"), HashMap.class);
            int scIoDocNo = objectMapper.convertValue(paramMap.get("scIoDocNo"), Integer.class);

            approval.setLid(scIoDocNo);
            approval.setHtmlMap(htmlMap);
            log.info(">>>> ioEmpViolation approval setLid: " + approval);

            ApprovalDocDTO approvalDoc = approvalService.insertApproval(approval);
            Integer docId = approvalDoc.getDocId();

            paramMap.put("docId", docId);
            paramMap.put("applStat", "Z0331002"); // Z0331002 : 접수
            // ================= NOTE: [통합결재정보] 저장 종료 =======================

            // 외부인 위규자 결재 등록
            repository.insertScIoOfendDoc(paramMap);

            // 처리완료
            result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return false;
    }

    /* 구성원 위규자 인사징계의뢰(C0280004) 결재상신 */
    private boolean coEmpViolationApproval(HashMap<String, Object> paramMap) {
        boolean result = false;

        try {

            // ================= NOTE: [통합결재정보] 저장 시작 =======================
            ApprovalDTO approval = objectMapper.convertValue(paramMap.get("approval"), ApprovalDTO.class);
            Map<String, Object> htmlMap = objectMapper.convertValue(paramMap.get("htmlMap"), HashMap.class);
            int scDocNo = objectMapper.convertValue(paramMap.get("scDocNo"), Integer.class);

            approval.setLid(scDocNo);
            approval.setHtmlMap(htmlMap);
            log.info(">>>> coEmpViolation approval setLid: " + approval);

            ApprovalDocDTO approvalDoc = approvalService.insertApproval(approval);
            Integer docId = approvalDoc.getDocId();

            paramMap.put("docId", docId);
            paramMap.put("applStat", "Z0331002"); // Z0331002 : 접수
            // ================= NOTE: [통합결재정보] 저장 종료 =======================

            // 구성원 위규자 결재 등록
            repository.insertScOfendDoc(paramMap);

            // 처리완료
            result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return false;
    }

    @Override
    public Boolean ioEmpViolation_Mobile_Act(HashMap<String, Object> paramMap) {

        boolean result = false;

        try {

            paramMap.put("crtBy", (String) paramMap.get("empId"));

            int cnt = repository.dmSecIoEmp_Violation_Mobile_Ofend_U(paramMap);

            if (cnt > 0) {
                result = true;

                //보안위규 담당자에게 MAIL 발송
                paramMap.put("mobileForensicsGbn", (String) paramMap.get("actDo"));

                List<Map<String, Object>> ds = repository.dmSecIoEmp_Violation_SendMail_List_S(paramMap);

                if (ds != null && ds.size() > 0) {
                    for (int i = 0; i < ds.size(); i++) {

                        if (ds.get(i).get("gubun").equals("5")) {
                            Map<String, Object> mailReq = new HashMap<String, Object>();

                            mailReq.put("ofendEmpId", ds.get(0).get("empId"));  // 위규자 사번 : 필수
                            mailReq.put("ofendEmpNm", ds.get(0).get("empNm"));  // 위규자 이름 : 필수
                            mailReq.put("ofendEmpEmail", ds.get(0).get("email"));  // 위규자 메일 : 필수
                            mailReq.put("compKoNm", paramMap.get("ofendCompNm"));
                            mailReq.put("ofendJwNm", paramMap.get("ofendJwNm"));
                            mailReq.put("ofendDt", paramMap.get("ofendDtMail"));
                            mailReq.put("ofendTm", paramMap.get("ofendTmMail"));
                            mailReq.put("ofendGbnNm", paramMap.get("ofendGbnNmMail"));
                            mailReq.put("ofendDetailNm", paramMap.get("dOfendDetailGbnNmMail"));
                            mailReq.put("toEmpId", ds.get(i).get("empId"));      // 메일 수신자 사번 : 필수
                            mailReq.put("toEmpNm", ds.get(i).get("empNm"));      // 메일 수신자 이름 : 필수
                            mailReq.put("toEmpEmail", ds.get(i).get("email"));      // 메일 수신자 메일주소 : 필수
                            mailReq.put("gubun", "5");   // 구분
                            mailReq.put("schemaNm", "보안위규자");
                            mailReq.put("docNm", "보안 위규자");          // 문서 명 : 필수
                            mailReq.put("crtBy", paramMap.get("crtBy"));        // 로그인ID
                            mailReq.put("acIp", paramMap.get("acIp"));        // AC_IP

                            //위규 발생시 사업장별 보안담당자에게 메일 발송 후 메일 내용에 .직접 가는 URL 적용할것. 김용범 책임 요청
                            fmSecIoEmpViolationMailSend(mailReq);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public ListDTO<Map<String, Object>> selectCoViolationSecList(HashMap<String, Object> paramMap) {

        List<Map<String, Object>> resultList = null;
        Integer totalCount = 0;

        try {
            resultList = repository.dmSecCoEmp_Violation_List_Sec_S(paramMap);
            totalCount = repository.dmSecCoEmp_Violation_List_Sec_Count_S(paramMap);
        } catch (Exception e) {

            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return ListDTO.getInstance(resultList, totalCount);
    }


    @Override
    public Boolean coEmpViolationSecDelete(HashMap<String, Object> paramMap) {
        boolean result = false;

        try {
            String scDocNo = (String) paramMap.get("scDocNo");

            if (scDocNo == null || "".equals(scDocNo)) {
                throw new Exception("키값이 누락되었습니다.");
            }

            int cnt = repository.dmSecrtCorrPan_OFEND_D(paramMap);

            if (cnt > 0) {
                result = true;
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public ListDTO<Map<String, Object>> selectIoViolationSecList(HashMap<String, Object> paramMap) {

        List<Map<String, Object>> resultList = null;
        Integer totalCount = 0;
        try {

            resultList = repository.dmSecIoEmp_Violation_List_Sec_S(paramMap);
            totalCount = repository.dmSecIoEmp_Violation_List_Sec_Count_S(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return ListDTO.getInstance(resultList, totalCount);
    }

    @Override
    public Boolean ioEmpViolationSecDelete(HashMap<String, Object> paramMap) {
        boolean result = false;

        try {

            String scIoDocNo = (String) paramMap.get("scIoDocNo");

            if (scIoDocNo == null || "".equals(scIoDocNo)) {
                throw new Exception("키값이 누락되었습니다.");
            }

            int cnt = repository.dmSecrtIoCorrPlan_OFEND_D(paramMap);

            if (cnt > 0) {
                result = true;
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public String selectAutoActYn(HashMap<String, Object> paramMap) {
        String result = "Y";
        try {

            String scIoDocNo = (String) paramMap.get("ofendEmpId");

            if (scIoDocNo == null || "".equals(scIoDocNo)) {
                throw new Exception("위규자 ID가 누락되었습니다.");
            }

            result = repository.selectAutoActYn(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> selectIoViolationApprList(Integer scIoDocNo) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectIoViolationApprList(scIoDocNo);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public List<Map<String, Object>> selectMainScCoOfendList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectMainScCoOfendList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Integer selectMainScCoOfendListCnt(Map<String, Object> paramMap) {
        Integer resultCnt = null;

        try {
            resultCnt = repository.selectMainScCoOfendListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

    @Override
    public List<Map<String, Object>> selectMainScIoOfendList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectMainScIoOfendList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Integer selectMainScIoOfendListCnt(Map<String, Object> paramMap) {
        Integer resultCnt = null;

        try {
            resultCnt = repository.selectMainScIoOfendListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

    @Override
    public Boolean coEmpViolationActDo(HashMap<String, Object> paramMap) {

        Boolean result = false;

        try {

            String actDo = String.valueOf(paramMap.get("actDo"));

            // 시정계획서징구, 경고장발송, 인사징계 의뢰
            List<String> actExcludeCodeList = Arrays.asList("C0280002", "C0280003", "C0280004");

            if (actExcludeCodeList.contains(actDo)) {
                paramMap.put("corrPlanSendYn", "C0101002"); // 미제출
            }
            else {
                paramMap.put("corrPlanSendYn", "C0101003"); // 해당없음
            }

            // SC_OFEND 저장
            int resultCnt = repository.dmSecCoEmp_Violation_Ofend_U(paramMap);
            // SC_EXP_DO 저장
            int resultCnt2 = repository.dmSecCoEmp_Violation_Exp_I(paramMap);

            //responseData.putField("result", "OK");
            result = true;

            if (actExcludeCodeList.contains(actDo)) {// 시정계획서징구, 경고장발송
                //채번
                String scCorrPlanNo = repository.dmSecCoEmp_Violation_Corr_Plan_Seq_S(paramMap);
                paramMap.put("scCorrPlanNo", scCorrPlanNo);
                int resultCnt3 = repository.dmSecCoEmp_Violation_Corr_Plan_I(paramMap);
                if (resultCnt3 < 1) {
                    result = false;
                }
            }

            if ("C0280002".equals(actDo)) { // 시정계획서징구 메일 발송

                /* 위규자 E-MAIL 발송 시작 */
                List<Map<String, Object>> mailList = repository.dmSecCoEmp_Violation_SendMail_List_S(paramMap);

                String ofendEmpId = "";
                String ofendEmpNm = "";
                String ofendDeptNm = "";
                String ofendJwNm = "";
                String ofendDt = "";
                String ofendTm = "";
                String ofendGbnNm = "";
                String ofendDetailNm = "";
                String ofendDetailCd = "";
                String ofendSubNm = "";
                String ofendGbn = "";
                String actDoNm = "";
                String ofendLoc = "";
                String limit14Dtm = ""; // 14일
                String limit30Dtm = ""; // 30일

                Map<String, Object> docInfo = repository.dmSecCoEmp_Violation_Info(paramMap);

                if (docInfo != null) {
                    ofendDeptNm = (String) docInfo.get("ofendDeptNm");
                    ofendJwNm = (String) docInfo.get("ofendJwNm");
                    ofendDt = (String) docInfo.get("ofendDt");
                    ofendTm = (String) docInfo.get("ofendTm");
                    ofendGbnNm = (String) docInfo.get("ofendGbnNm");
                    ofendDetailNm = (String) docInfo.get("ofendDetailNm");
                    ofendDetailCd = (String) docInfo.get("ofendDetailCd");
                    ofendSubNm = (String) docInfo.get("ofendSubNm");
                    ofendGbn = (String) docInfo.get("ofendGbn");
                    actDoNm = (String) docInfo.get("actDoNm");

                    limit14Dtm = (String) docInfo.get("limit14Dtm");
                    limit30Dtm = (String) docInfo.get("limit30Dtm");

                    ofendEmpNm = (String) docInfo.get("ofendEmpNm");
                    //remark = (String) docInfo.get("remark");

                    if ("".equals((String) docInfo.get("actGate"))) {
                        ofendLoc = (String) docInfo.get("ofendCompNm") + " > " + (String) docInfo.get("actBldgNm") + " > " + (String) docInfo.get("actLocateNm");
                    }
                    else {
                        ofendLoc =
                            (String) docInfo.get("ofendCompNm") + " > " + (String) docInfo.get("actBldgNm") + " > " + (String) docInfo.get("actLocateNm") + " > " + (String) docInfo.get("actGate");
                    }
                }

                Map<String, String> mailMap = new HashMap<String, String>();
                String strToCCEmail = "";
                mailMap.put("ofendDeptNm", ofendDeptNm);
                mailMap.put("ofendJwNm", ofendJwNm);
                mailMap.put("ofendDt", ofendDt);
                mailMap.put("ofendTm", ofendTm);
                mailMap.put("ofendGbnNm", ofendGbnNm);
                mailMap.put("ofendDetailNm", ofendDetailNm);
                mailMap.put("ofendDetailCd", ofendDetailCd);
                mailMap.put("ofendSubNm", ofendSubNm);
                mailMap.put("schemaNm", "보안위규자");
                mailMap.put("docNm", "보안 위규자"); // 문서 명 : 필수
                mailMap.put("crtBy", String.valueOf(paramMap.get("crtBy"))); // 로그인ID
                mailMap.put("acIp", String.valueOf(paramMap.get("acIp"))); // AC_IP
                mailMap.put("ofendLoc", ofendLoc); // 장소

                mailMap.put("limit14Dtm", limit14Dtm); // 14일
                mailMap.put("limit30Dtm", limit30Dtm); // 30일

                for (int i = 0; i < mailList.size(); i++) {
                    if (StringUtils.isNotEmpty(String.valueOf(mailList.get(i).get("email")))) {
                        if ("1".equals(mailList.get(i).get("gubun"))) {
                            // 위규자
                            mailMap.put("toEmpEmail", (String) mailList.get(i).get("email"));  // 메일 수신자 메일주소 : 필수
                            mailMap.put("scrtView", "scrtView");
                        }
                        else if ("2".equals(mailList.get(i).get("gubun"))) { // 팀장 참조
                            // 팀장 참조
                            if ("".equals(strToCCEmail)) {
                                strToCCEmail = String.valueOf(mailList.get(i).get("email"));
                            }
                            else {
                                strToCCEmail = strToCCEmail + "," + String.valueOf(mailList.get(i).get("email"));
                            }
                        }
                        else if ("3".equals(mailList.get(i).get("gubun"))) { //보안 담당자
                            if ("".equals(strToCCEmail)) {
                                strToCCEmail = String.valueOf(mailList.get(i).get("email"));
                            }
                            else {
                                strToCCEmail = strToCCEmail + "," + String.valueOf(mailList.get(i).get("email"));
                            }
                        }
                    }

                    String hpNo = String.valueOf(mailList.get(i).get("hpNo"));

                    if (StringUtils.isNotEmpty(hpNo)) {
                        List<String> gubunList = Arrays.asList("1", "2", "3");
                        if (gubunList.contains((String) mailList.get(i).get("gubun"))) {

                            hpNo = hpNo.replaceAll("-", "");

                            String msg = "[SK쉴더스] " + ofendEmpNm + "님께서는 security.skshieldus.com에서 시정계획서를 제출하시기 바랍니다.";

                            Map<String, String> smsSendInfo = new HashMap<String, String>();

                            String holdCallbackNo = "03180947279";
                            //SMSReq.putField("CALLBACK_NO", "0316302294"); // 보내는 사람 휴대폰 번호
                            smsSendInfo.put("holdCallbackNo", holdCallbackNo); // 발송 번호 수정 (0316302294 -> 03180947744)
                            smsSendInfo.put("callbackNo", "03180947279"); // 보내는 사람 휴대폰 번호   (이홍순책임 요청으로 하이스택 김건호과장으로 변경 2017.05.26)
                            smsSendInfo.put("smsNo", hpNo); // 받는 사람 휴대폰 번호
                            smsSendInfo.put("msg", msg); // 문자 내용

                            //TODO:(hoonLee) SMS전송 OR KAKAO메세지
                            //callSharedBizComponentByDirect(SMSSend, "fmSendSMS", SMSReq, onlineCtx);
                        }
                    }
                }

                mailMap.put("toCcEmail", strToCCEmail);

                //TODO:(hoonLee) 메일보내기 주석풀기
                //fmSecCoEmpViolationJingGuMailSend(mailMap);

            }
            else if ("C0280003".equals(actDo)) { // 경고장 메일 발송

                /* 경고장 안내메일 내용 Get */
                //String ofendEmpId = "";
                String ofendEmpNm = "";
                //String ofendDeptNm = "";
                String ofendJwNm = "";
                String ofendDt = "";
                String ofendTm = "";
                String ofendGbnNm = "";
                String ofendDetailNm = "";
                //String ofendGbn = "";
                //String actDoNm = "";
                String ofendLoc = "";
                String limit14Dtm = ""; // 14일
                String limit30Dtm = ""; // 30일

                //String remark = ""; //비고/산업보안팀 의견

                Map<String, Object> docInfo = repository.dmSecCoEmp_Violation_Info(paramMap);

                if (docInfo != null) {
                    //ofendDeptNm = (String) docInfo.get("ofendDeptNm");
                    ofendJwNm = (String) docInfo.get("ofendJwNm");
                    ofendDt = (String) docInfo.get("ofendDt");
                    ofendTm = (String) docInfo.get("ofendTm");
                    ofendGbnNm = (String) docInfo.get("ofendGbnNm");
                    ofendDetailNm = (String) docInfo.get("ofendDetailNm");
                    //ofendGbn = (String) docInfo.get("ofendGbn");
                    //actDoNm = (String) docInfo.get("actDoNm");
                    limit14Dtm = (String) docInfo.get("limit14Dtm");
                    limit30Dtm = (String) docInfo.get("limit30Dtm");
                    ofendEmpNm = (String) docInfo.get("ofendEmpNm");

                    //remark = (String) docInfo.get("remark");

                    if ("".equals((String) docInfo.get("actGate"))) {
                        ofendLoc = (String) docInfo.get("ofendCompNm") + " > " + (String) docInfo.get("actBldgNm") + " > " + (String) docInfo.get("actLocateNm");
                    }
                    else {
                        ofendLoc =
                            (String) docInfo.get("ofendCompNm") + " > " + (String) docInfo.get("actBldgNm") + " > " + (String) docInfo.get("actLocateNm") + " > " + (String) docInfo.get("actGate");
                    }
                }

                /* 위규자 E-MAIL 발송 시작 */
                List<Map<String, Object>> mailList = repository.dmSecCoEmp_Violation_SendMail_List_S(paramMap);

                Map<String, String> mailMap = new HashMap<String, String>();

                mailMap.put("ofendCompNm", (String) paramMap.get("ofendCompNm")); //위반 소속사명
                mailMap.put("ofendText", (String) paramMap.get("ofendText")); // 위규내용
                mailMap.put("ofendEmpId", (String) paramMap.get("ofendEmpId"));
                mailMap.put("ofendEmpNm", (String) paramMap.get("ofendEmpNm"));
                mailMap.put("ofendEmpEmail", (String) paramMap.get("ofendEmpEmail"));
                mailMap.put("schemaNm", "보안위규자");
                mailMap.put("docNm", "보안 위규자");          // 문서 명 : 필수
                mailMap.put("crtBy", (String) paramMap.get("crtBy"));        // 로그인ID
                mailMap.put("acIp", (String) paramMap.get("acIp"));        // AC_IP

                mailMap.put("ofendJwNm", ofendJwNm);
                mailMap.put("ofendDt", ofendDt);
                mailMap.put("ofendTm", ofendTm);
                mailMap.put("ofendGbnNm", ofendGbnNm);
                mailMap.put("ofendDetailNm", ofendDetailNm);
                mailMap.put("ofendLoc", ofendLoc); // 장소
                mailMap.put("limit14Dtm", limit14Dtm); // 14일
                mailMap.put("limit30Dtm", limit30Dtm); // 30일
                String strToCCEmail = "";

                //mailMap.put("remark", remark); // 비고/산업보안팀 의견

                for (int i = 0; i < mailList.size(); i++) {

                    if (StringUtils.isNotEmpty(String.valueOf(mailList.get(i).get("email")))) {

                        if ("1".equals(mailList.get(i).get("gubun"))) { // 위규자
                            mailMap.put("toEmpEmail", (String) mailList.get(i).get("email"));  // 메일 수신자 메일주소 : 필수
                            mailMap.put("scrtView", "SCRT_VIEW");
                        }
                        else if ("2".equals(mailList.get(i).get("gubun"))) { // 팀장
                            if ("".equals(strToCCEmail)) {
                                strToCCEmail = String.valueOf(mailList.get(i).get("email"));
                            }
                            else {
                                strToCCEmail = strToCCEmail + "," + String.valueOf(mailList.get(i).get("email"));
                            }
                        }
                        else if ("3".equals(mailList.get(i).get("gubun"))) { //보안 담당자
                            if ("".equals(strToCCEmail)) {
                                strToCCEmail = String.valueOf(mailList.get(i).get("email"));
                            }
                            else {
                                strToCCEmail = strToCCEmail + "," + String.valueOf(mailList.get(i).get("email"));
                            }
                        }
                    }

                    String hpNo = String.valueOf(mailList.get(i).get("hpNo"));

                    if (StringUtils.isNotEmpty(hpNo)) {
                        List<String> gubunList = Arrays.asList("1", "2", "3");
                        if (gubunList.contains((String) mailList.get(i).get("gubun"))) {

                            hpNo = hpNo.replaceAll("-", "");

                            String msg = "[SK쉴더스] " + ofendEmpNm + "님께서는 security.skshieldus.com에서 개선계획서를 제출하시기 바랍니다.";

                            Map<String, String> smsSendInfo = new HashMap<String, String>();

                            String holdCallbackNo = "03180947279";
                            smsSendInfo.put("holdCallbackNo", holdCallbackNo); // 발송 번호 수정 (0316302294 -> 03180947744)
                            smsSendInfo.put("callbackNo", "03180947279"); // 보내는 사람 휴대폰 번호   (이홍순책임 요청으로 하이스택 김건호과장으로 변경 2017.05.26)
                            smsSendInfo.put("smsNo", hpNo); // 받는 사람 휴대폰 번호
                            smsSendInfo.put("msg", msg); // 문자 내용

                            //TODO:(hoonLee) SMS전송 OR KAKAO메세지
                            //callSharedBizComponentByDirect(SMSSend, "fmSendSMS", SMSReq, onlineCtx);
                        }
                    }
                }

                mailMap.put("toCcEmail", strToCCEmail);

                //TODO:(hoonLee) 메일보내기 주석풀기
                // 경고장 메일 발송 (관련 메일 컨텐츠 필요)
                //fmSecCoEmpViolationWarningInfoMailSend(mailMap);
                //fmSecCoEmpViolationWarningMailSend(mailMap);

            }
            else if ("C0280001".equals(actDo)) { // 구성원 확인

                List<Map<String, Object>> mailList = repository.dmSecCoEmp_Violation_SendMail_List_S(paramMap);
                String ofendEmpId = "";

                Map<String, Object> docInfo = repository.dmSecCoEmp_Violation_Info(paramMap);

                String ofendDeptNm = "";
                String ofendJwNm = "";
                String ofendDt = "";
                String ofendTm = "";
                String ofendGbnNm = "";
                String ofendDetailNm = "";
                String ofendDetailCd = "";
                String ofendSubNm = "";
                String ofendGbn = "";
                String actDoNm = "";
                String ofendLoc = "";
                //String remark = "";
                String ofendEmpNm = "";

                if (docInfo != null) {
                    ofendDeptNm = (String) docInfo.get("ofendDeptNm");
                    ofendJwNm = (String) docInfo.get("ofendJwNm");
                    ofendDt = (String) docInfo.get("ofendDt");
                    ofendTm = (String) docInfo.get("ofendTm");
                    ofendGbnNm = (String) docInfo.get("ofendGbnNm");
                    ofendDetailNm = (String) docInfo.get("ofendDetailNm");
                    ofendDetailCd = (String) docInfo.get("ofendDetailCd");
                    ofendSubNm = (String) docInfo.get("ofendSubNm");
                    ofendGbn = (String) docInfo.get("ofendGbn");
                    actDoNm = (String) docInfo.get("actDoNm");
                    //remark = (String) docInfo.get("remark");

                    ofendEmpNm = (String) docInfo.get("ofendEmpNm");

                    if ("".equals((String) docInfo.get("actGate"))) {
                        ofendLoc = (String) docInfo.get("ofendCompNm") + " > " + (String) docInfo.get("actBldgNm") + " > " + (String) docInfo.get("actLocateNm");
                    }
                    else {
                        ofendLoc =
                            (String) docInfo.get("ofendCompNm") + " > " + (String) docInfo.get("actBldgNm") + " > " + (String) docInfo.get("actLocateNm") + " > " + (String) docInfo.get("actGate");
                    }
                }

                for (int i = 0; i < mailList.size(); i++) {
                    Map<String, String> mailMap = new HashMap<String, String>();

                    mailMap.put("ofendEmpId", (String) mailList.get(0).get("empId"));    // 위규자 사번 : 필수
                    mailMap.put("ofendEmpNm", (String) mailList.get(0).get("empNm"));    // 위규자 이름 : 필수
                    mailMap.put("ofendEmpEmail", (String) mailList.get(0).get("email"));  // 위규자 메일 : 필수

                    mailMap.put("ofendDeptNm", ofendDeptNm);
                    mailMap.put("ofendJwNm", ofendJwNm);
                    mailMap.put("ofendDt", ofendDt);
                    mailMap.put("ofendTm", ofendTm);
                    mailMap.put("ofendGbnNm", ofendGbnNm);
                    mailMap.put("ofendDetailNm", ofendDetailNm);
                    mailMap.put("ofendDetailCd", ofendDetailCd);
                    mailMap.put("ofendSubNm", ofendSubNm);
                    mailMap.put("schemaNm", "보안위규자");
                    mailMap.put("docNm", "보안 위규자");          // 문서 명 : 필수
                    mailMap.put("crtBy", (String) paramMap.get("empId"));    // 로그인ID
                    mailMap.put("acIp", (String) paramMap.get("acIp"));    // AC_IP
                    mailMap.put("actDoNm", actDoNm);
                    mailMap.put("ofendLoc", ofendLoc); // 장소
                    //mailMap.put("remark", remark); // 비고/산업보안팀 의견

                    if ("1".equals(mailList.get(i).get("gubun"))) { // 위규자

                        mailMap.put("toEmpId", (String) mailList.get(i).get("empId"));    // 메일 수신자 사번 : 필수
                        mailMap.put("toEmpNm", (String) mailList.get(i).get("empNm"));    // 메일 수신자 이름 : 필수
                        mailMap.put("toEmpEmail", (String) mailList.get(i).get("email"));  // 메일 수신자 메일주소 : 필수
                        mailMap.put("scrtView", "SCRT_VIEW");

                        //TODO:(hoonLee) 메일보내기 주석풀기
                        //fmSecCoEmpViolationGuVerifyMailSend(mailMap);

                        String hpNo = String.valueOf(mailList.get(i).get("hpNo"));

                        if (StringUtils.isNotEmpty(hpNo)) {

                            hpNo = hpNo.replaceAll("-", "");

                            String msg = "[SK쉴더스] " + ofendEmpNm + "님 구성원 확인으로 위규처리 되었습니다.";

                            Map<String, String> smsSendInfo = new HashMap<String, String>();

                            String holdCallbackNo = "03180947279";
                            smsSendInfo.put("holdCallbackNo", holdCallbackNo); // 발송 번호 수정 (0316302294 -> 03180947744)
                            smsSendInfo.put("callbackNo", "03180947279"); // 보내는 사람 휴대폰 번호   (이홍순책임 요청으로 하이스택 김건호과장으로 변경 2017.05.26)
                            smsSendInfo.put("smsNo", hpNo); // 받는 사람 휴대폰 번호
                            smsSendInfo.put("msg", msg); // 문자 내용

                            //TODO:(hoonLee) SMS전송 OR KAKAO메세지
                            //callSharedBizComponentByDirect(SMSSend, "fmSendSMS", SMSReq, onlineCtx);
                        }
                    }
                }
            }
            else if ("C0280004".equals(actDo)) { // 인사징계의뢰 메일 발송

                /* 인사징계의뢰 안내메일 내용 Get */
                String ofendDeptNm = "";
                String ofendJwNm = "";
                String ofendDt = "";
                String ofendTm = "";
                String ofendGbnNm = "";
                String ofendDetailNm = "";
                String ofendDetailCd = "";
                String ofendSubNm = "";
                String ofendGbn = "";
                String actDoNm = "";
                String ofendLoc = "";
                String ofendEmpNm = "";
                String remark = "";

                Map<String, Object> docInfo = repository.dmSecCoEmp_Violation_Info(paramMap);

                if (docInfo != null) {
                    ofendDeptNm = (String) docInfo.get("ofendDeptNm");
                    ofendJwNm = (String) docInfo.get("ofendJwNm");
                    ofendDt = (String) docInfo.get("ofendDt");
                    ofendTm = (String) docInfo.get("ofendTm");
                    ofendGbnNm = (String) docInfo.get("ofendGbnNm");
                    ofendDetailNm = (String) docInfo.get("ofendDetailNm");
                    ofendDetailCd = (String) docInfo.get("ofendDetailCd");
                    ofendSubNm = (String) docInfo.get("ofendSubNm");
                    ofendGbn = (String) docInfo.get("ofendGbn");
                    actDoNm = (String) docInfo.get("actDoNm");
                    //remark = (String) docInfo.get("remark");
                    ofendEmpNm = (String) docInfo.get("ofendEmpNm");

                    if ("".equals((String) docInfo.get("actGate"))) {
                        ofendLoc = (String) docInfo.get("actCompNm") + " > " + (String) docInfo.get("actBldgNm") + " > " + (String) docInfo.get("actLocateNm");
                    }
                    else {
                        ofendLoc =
                            (String) docInfo.get("actCompNm") + " > " + (String) docInfo.get("actBldgNm") + " > " + (String) docInfo.get("actLocateNm") + " > " + (String) docInfo.get("actGate");
                    }
                }

                /* 위규자 E-MAIL 발송 시작 */
                List<Map<String, Object>> mailList = repository.dmSecCoEmp_Violation_SendMail_List_S(paramMap);
                Map<String, String> mailMap = new HashMap<String, String>();

                mailMap.put("ofendCompNm", (String) paramMap.get("ofendCompNm")); //위반 소속사명
                mailMap.put("ofendText", (String) paramMap.get("ofendText")); // 위규내용
                mailMap.put("ofendEmpId", (String) paramMap.get("ofendEmpId"));
                mailMap.put("ofendEmpNm", (String) paramMap.get("ofendEmpNm"));
                mailMap.put("ofendEmpEmail", (String) paramMap.get("ofendEmpEmail"));
                mailMap.put("schemaNm", "보안위규자");
                mailMap.put("docNm", "보안 위규자");          // 문서 명 : 필수
                mailMap.put("crtBy", (String) paramMap.get("crtBy"));        // 로그인ID
                mailMap.put("acIp", (String) paramMap.get("acIp"));        // AC_IP

                mailMap.put("ofendJwNm", ofendJwNm);
                mailMap.put("ofendDt", ofendDt);
                mailMap.put("ofendTm", ofendTm);
                mailMap.put("ofendGbnNm", ofendGbnNm);
                mailMap.put("ofendDetailNm", ofendDetailNm);
                mailMap.put("ofendDetailCd", ofendDetailCd);
                mailMap.put("ofendSubNm", ofendSubNm);
                mailMap.put("ofendLoc", ofendLoc); // 장소
                //mailMap.put("remark", remark); // 비고/산업보안팀 의견

                String strToCCEmail = "";

                for (int i = 0; i < mailList.size(); i++) {
                    if (StringUtils.isNotEmpty(String.valueOf(mailList.get(i).get("email")))) {

                        if ("2".equals(mailList.get(i).get("gubun"))) { // 팀장
                            mailMap.put("toEmpEmail", (String) mailList.get(i).get("email"));  // 메일 수신자 메일주소 : 필수
                        }
                        else { // 위규자 사업부장  보안 담당자 대표이사는 참조로

                            if ("".equals(strToCCEmail)) {
                                strToCCEmail = String.valueOf(mailList.get(i).get("email"));
                            }
                            else {
                                strToCCEmail = strToCCEmail + "," + String.valueOf(mailList.get(i).get("email"));
                            }
                        }
                    }

                    String hpNo = String.valueOf(mailList.get(i).get("hpNo"));

                    if (StringUtils.isNotEmpty(hpNo)) {

                        hpNo = hpNo.replaceAll("-", "");

                        String msg = "[SK쉴더스] " + ofendEmpNm + "님의 인사 징계의뢰 예정, security.skshieldus.com에서 확인바랍니다.";

                        Map<String, String> smsSendInfo = new HashMap<String, String>();

                        String holdCallbackNo = "03180947279";
                        smsSendInfo.put("holdCallbackNo", holdCallbackNo); // 발송 번호 수정 (0316302294 -> 03180947744)
                        smsSendInfo.put("callbackNo", "03180947279"); // 보내는 사람 휴대폰 번호   (이홍순책임 요청으로 하이스택 김건호과장으로 변경 2017.05.26)
                        smsSendInfo.put("smsNo", hpNo); // 받는 사람 휴대폰 번호
                        smsSendInfo.put("msg", msg); // 문자 내용

                        //TODO:(hoonLee) SMS전송 OR KAKAO메세지
                        //callSharedBizComponentByDirect(SMSSend, "fmSendSMS", SMSReq, onlineCtx);
                    }
                }

                mailMap.put("scrtView", "scrtView");
                mailMap.put("toCcEmail", strToCCEmail);

                //TODO:(hoonLee) 메일보내기 주석풀기
                //인사징계의뢰 메일 발송
                //fmSecCoEmpViolationInsaInfoMailSend(mailMap);

            }

            // 구성원인 경우조치 사항 누적수 확인
            Map<String, Object> resultMap4 = repository.dmSecCoEmpViolation_Accum_S(paramMap);
            if (resultMap4 != null) {
                String etc1 = String.valueOf(resultMap4.get("etc1")); //기준횟수
                String etc2 = String.valueOf(resultMap4.get("etc2")); //이후로직
                String expCnt = String.valueOf(resultMap4.get("expCnt")); //전체조치건수
                String chkCnt = String.valueOf(resultMap4.get("chkCnt")); //체크조치건수

                int iEtc1 = Integer.parseInt(etc1);
                int iExpCnt = Integer.parseInt(expCnt);
                int iChkCnt = Integer.parseInt(chkCnt);

                // 기준횟수 없고 이후 로직이 없으면 종료
                if (!("0".equals(etc1) || "0".equals(etc2))) {
                    if (iEtc1 <= iChkCnt) { // 보안위규 횟수를 넘긴 경우
                        // SC_OFEND 테이블 NEXT INSERT 수행
                        //채번
                        int nextScDocNo = repository.getScDocNo(); /*dmSecCoEmp_Violation_Seq_S*/
                        paramMap.put("nextScDocNo", nextScDocNo);
                        paramMap.put("nextOfendDetailGbn", etc2);

                        int resultNextCnt = repository.dmSecCoEmp_Violation_Next_I(paramMap);

                        /* 시정계획서 누적건수 CLEAR 시킴 */
                        int resultClear = repository.dmSecCoEmpViolation_Accum_Clear_U(paramMap);
                    }
                }
                // 구성원인 경우조치 사항 누적수 확인
            }

            if (resultCnt < 1 || resultCnt2 < 1) {
                result = false;
            }
        }
        catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Boolean coViolationSecondaryMail(HashMap<String, Object> paramMap) {

        Boolean result = false;

        log.info("[coViolationSecondaryMail] scDocNo >> {}", paramMap.get("scDocNo"));

        try {
            /* 인사징계의뢰 안내메일 내용 Get */
            String ofendJwNm = "";
            String ofendDt = "";
            String ofendTm = "";
            String ofendGbnNm = "";
            String ofendDetailNm = "";
            String ofendDetailCd = "";
            String ofendSubNm = "";
            String ofendLoc = "";

            Map<String, Object> docInfo = repository.dmSecCoEmp_Violation_Info(paramMap);

            if (docInfo != null) {
                ofendJwNm = (String) docInfo.get("ofendJwNm");
                ofendDt = (String) docInfo.get("ofendDt");
                ofendTm = (String) docInfo.get("ofendTm");
                ofendGbnNm = (String) docInfo.get("ofendGbnNm");
                ofendDetailNm = (String) docInfo.get("ofendDetailNm");
                ofendDetailCd = (String) docInfo.get("ofendDetailCd");
                ofendSubNm = (String) docInfo.get("ofendSubNm");

                if ("".equals((String) docInfo.get("actGate"))) {
                    ofendLoc = (String) docInfo.get("actCompNm") + " > " + (String) docInfo.get("actBldgNm") + " > " + (String) docInfo.get("actLocateNm");
                }
                else {
                    ofendLoc =
                        (String) docInfo.get("actCompNm") + " > " + (String) docInfo.get("actBldgNm") + " > " + (String) docInfo.get("actLocateNm") + " > " + (String) docInfo.get("actGate");
                }
            }

            paramMap.put("actDo", "C0280004");
            List<Map<String, Object>> mailList = repository.dmSecCoEmp_Violation_SendMail_List_S(paramMap);
            Map<String, String> mailMap = new HashMap<String, String>();

            mailMap.put("ofendCompNm", (String) paramMap.get("ofendCompNm")); // 위반 소속사명
            mailMap.put("ofendText", (String) paramMap.get("ofendText")); // 위규내용
            mailMap.put("ofendEmpId", (String) paramMap.get("ofendEmpId"));
            mailMap.put("ofendEmpNm", (String) paramMap.get("ofendEmpNm"));
            mailMap.put("schemaNm", "보안위규자");
            mailMap.put("docNm", "보안 위규자"); // 문서 명 : 필수
            mailMap.put("crtBy", (String) paramMap.get("crtBy")); // 로그인ID
            mailMap.put("acIp", (String) paramMap.get("acIp")); // AC_IP

            mailMap.put("ofendJwNm", ofendJwNm);
            mailMap.put("ofendDt", ofendDt);
            mailMap.put("ofendTm", ofendTm);
            mailMap.put("ofendGbnNm", ofendGbnNm);
            mailMap.put("ofendDetailNm", ofendDetailNm);
            mailMap.put("ofendDetailCd", ofendDetailCd);
            mailMap.put("ofendSubNm", ofendSubNm);
            mailMap.put("ofendLoc", ofendLoc); // 장소

            String strToCCEmail = "";

            for (int i = 0; i < mailList.size(); i++) {
                if (StringUtils.isNotEmpty(String.valueOf(mailList.get(i).get("email")))) {

                    if ("2".equals(mailList.get(i).get("gubun"))) { // 팀장
                        mailMap.put("toEmpEmail", (String) mailList.get(i).get("email"));  // 메일 수신자 메일주소 : 필수
                    }
                    else { // 위규자 사업부장  보안 담당자 대표이사는 참조로

                        if ("".equals(strToCCEmail)) {
                            strToCCEmail = String.valueOf(mailList.get(i).get("email"));
                        }
                        else {
                            strToCCEmail = strToCCEmail + "," + String.valueOf(mailList.get(i).get("email"));
                        }
                    }
                }
            }

            mailMap.put("scrtView", "scrtView");
            mailMap.put("toCcEmail", strToCCEmail);

            //TODO:(hoonLee) 메일보내기 주석풀기
            //인사징계의뢰 메일 발송
            //fmSecCoEmpViolationInsaInfoMailSend(mailMap);

            // 구성원 위규자 조치 정보 업데이트
            int resultCnt = repository.dmSecCoEmp_Violation_ActDo2ChaMail_U(paramMap);

            result = resultCnt < 1 ? false : true;

        }
        catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return result;
    }

    @Override
    public Boolean ioEmpViolationActDo(HashMap<String, Object> paramMap) {

        log.info("[ioEmpViolationActDo] scIoDocNo >> {}", paramMap.get("scIoDocNo"));

        Boolean result = false;

        String actDo = (String) paramMap.get("actDo");

        if("C0280002,C0280006".indexOf(actDo) > -1){ // 시정계획서 징구, 대표이상 시정공문 징구
            paramMap.put("corrPlanSendYn", "C0101002"); // 미제출
        }
        else{
            paramMap.put("corrPlanSendYn", "C0101003"); // 해당없음
        }

        int resultDs = repository.dmSecIoEmp_Violation_Ofend_U(paramMap); // SC_OFEND 저장
        int resultDs2 = repository.dmSecIoEmp_Violation_Exp_I(paramMap); // SC_EXP_DO 저장

        /* 추가 : 2016-06-21 by JSH */
        if ("C0280007,C0280008,C0280012".indexOf(actDo) > -1) {

            int denyNo = repository.dmSecIoEmp_Violation_Deny_Seq_S();
            paramMap.put("denyNo", denyNo);

            log.info("***************************************** fmSecIoEmpViolation_ActDo LOG *****************************************");
            log.info("*****************************dmSecIoEmp_Violation_Deny_I  [param] : " + paramMap);
            log.info("*****************************************************************************************************************");

            int rDs1 = repository.dmSecIoEmp_Violation_Deny_I(paramMap); // IO_EMP_DENY 테이블에 Insert

            if (rDs1 < 1) {
                result = false;
            }

            Map<String, Object> rDs2 = dmSecIoEmp_Violation_PassExprHist_I(paramMap); // IO_PASS_EXPR_HIST 테이블에 Insert

            if("FAIL".equals(rDs2.get("result"))) {
                result = false;
            }

            if( StringUtils.isNotEmpty(String.valueOf(rDs2.get("idcardId"))) ) {

                /* 하이스텍 I/F 실행 */
                try {
                    String exprGbnCode = "A0460007"; //위규조치(1개월출입정지)

                    if ("C0280007".equals(actDo)) { // 영구출입정지
                        exprGbnCode = "A0460008";  // 위규조치(영구정지)
                    }

                    Map<String, Object> ds = new HashMap<String, Object>();
                    ds.put("exprApplNo", rDs2.get("exprApplNo"));
                    ds.put("sCGbn", "S"); // 정지
                    ds.put("idcardId", rDs2.get("idcardId"));
                    ds.put("exprCode", exprGbnCode);

                    //---------- 하이스텍 정지 하는 I/F Procedure 호출해야 함 ----------//
                    /*callSharedBizComponentByDirect("common.PassExcptIF", "fmPassExcptIF_Stop", ds);*/

                    //---------- 하이스텍 정지 하는 I/F Procedure 호출(운영에서만 호출됨) ----------//
                    fmPassExcptIF_Stop(ds);

                } catch (Exception e) {
                    log.error("================================fmPassExcptIF_Stop ERROR================================");
                    e.printStackTrace();
                    result = false;
                    throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
                }

            } /* 하이스텍 I/F 실행 */

            result = true;

            paramMap.put("scIoOfendDocNo", paramMap.get("scIoDocNo"));

            //C0280012 6개월출입정지

            if ("C0280002,C0280006,C0280008,C0280007".indexOf(actDo) > -1) { // 시정계획서 징구, 대표이사 시정공문 징구, 1개월출입정지, 영구출입정지

                if ("C0280002,C0280006".indexOf(actDo) > -1) { // 시정계획서 징구, 대표이사 시정공문 징구 이렇게만 시정계획서 테이블에 insert되게 함
                    int scIoCorrPlanNo = repository.dmSecIoEmp_Violation_Corr_Plan_Seq_S(paramMap);
                    paramMap.put("scIoCorrPlanNo", scIoCorrPlanNo);

                    int resultDs3 = repository.dmSecIoEmp_Violation_CorrPlan_I(paramMap); // 'Z0401004' 미제출

                    if (resultDs3 < 1) {
                        result = false;
                    }
                }

                /* 위규자 E-MAIL 발송 시작 */
                List<Map<String, Object>> seqDs3 = repository.dmSecIoEmp_Violation_Info(paramMap);

                String ofendCompNm = "";
                String ofendJwNm = "";
                String ofendDt = "";
                String ofendTm = "";
                String ofendGbnNm = "";
                String ofendDetailNm = "";
                String ofendHpNo = "";
                String actCompNm = "";
                String denyStrtDt = "";
                String denyEndDt = "";
                String limit14Dtm = ""; // 14일
                String limit30Dtm = ""; // 30일
                String ofendLoc = "";
                String ofendEmpNm = "";

                if (seqDs3 != null && seqDs3.size() > 0) {
                    ofendCompNm = (String) seqDs3.get(0).get("compKoNm");
                    ofendJwNm = (String) seqDs3.get(0).get("jwNm");
                    ofendDt = (String) seqDs3.get(0).get("ofendDt");
                    ofendTm = (String) seqDs3.get(0).get("ofendTm");
                    ofendGbnNm = (String) seqDs3.get(0).get("ofendGbnNm");
                    ofendDetailNm = (String) seqDs3.get(0).get("ofendDetailNm");

                    ofendHpNo = (String) seqDs3.get(0).get("hpNo");
                    actCompNm = (String) seqDs3.get(0).get("actCompNm");
                    denyStrtDt = (String) seqDs3.get(0).get("denyStrtDt");
                    denyEndDt = (String) seqDs3.get(0).get("denyEndDt");
                    limit14Dtm = (String) seqDs3.get(0).get("limit14Dtm");
                    limit30Dtm = (String) seqDs3.get(0).get("limit30Dtm");
                    ofendEmpNm = (String) seqDs3.get(0).get("empNm");

                    if ("".equals((String) seqDs3.get(0).get("actGate")) || null == seqDs3.get(0).get("actGate")) {
                        ofendLoc = (String) seqDs3.get(0).get("actCompNm") + " > " + (String) seqDs3.get(0).get("actBldgNm") + " > " + (String) seqDs3.get(0).get("actLocateNm");
                    }
                    else {
                        ofendLoc = (String) seqDs3.get(0).get("actCompNm") + " > " + (String) seqDs3.get(0).get("actBldgNm") + " > " + (String) seqDs3.get(0).get("actLocateNm") + " > "
                                   + (String) seqDs3.get(0).get("actGate");
                    }
                }



                List<Map<String, Object>> ds = repository.dmSecIoEmp_Violation_SendMail_List_S(paramMap);

                if (ds != null && ds.size() > 0) {
                    for (int i = 0; i < ds.size(); i++) {
                        Map<String,Object> mailReq = new HashMap<String, Object>();

                        mailReq.put("ofendEmpId", ds.get(0).get("empId"));  // 위규자 사번 : 필수
                        mailReq.put("ofendEmpNm", ds.get(0).get("empNm"));  // 위규자 이름 : 필수
                        mailReq.put("ofendEmpEmail", ds.get(0).get("email"));  // 위규자 메일 : 필수

                        mailReq.put("compKoNm", ofendCompNm);
                        mailReq.put("ofendJwNm", ofendJwNm);
                        mailReq.put("ofendDt", ofendDt);
                        mailReq.put("ofendTm", ofendTm);
                        mailReq.put("ofendGbnNm", ofendGbnNm);
                        mailReq.put("ofendDetailNm", ofendDetailNm);
                        mailReq.put("actCompNm", actCompNm); // 적발사업장
                        mailReq.put("denyStrtDt", denyStrtDt); // 출입정지시작일
                        mailReq.put("denyEndDt", denyEndDt); // 출입정지종료일
                        mailReq.put("ofendLoc", ofendLoc); // 장소

                        mailReq.put("schemaNm", "보안위규자");
                        mailReq.put("docNm", "보안 위규자");          // 문서 명 : 필수
                        mailReq.put("crtBy", paramMap.get("crtBy"));        // 로그인ID
                        mailReq.put("acIp", paramMap.get("acIp"));        // AC_IP

                        mailReq.put("limit14Dtm", limit14Dtm); // 14일
                        mailReq.put("limit30Dtm", limit30Dtm); // 30일

                        if ("1".toString().equals(ds.get(i).get("gubun").toString()) || "4".toString().equals(ds.get(i).get("gubun").toString()) // 대표관리자(정)
                            || "6".toString().equals(ds.get(i).get("gubun").toString()) // 대표관리자(부)
                            || "7".toString().equals(ds.get(i).get("gubun").toString()) // 현업접견자(SC_IO_OFEND_MEET 테이블 정보)
                        ) { // 위규자 or 대표관리자(정) or 대표관리자(부) or 현업접견자
                            mailReq.put("toEmpId", ds.get(i).get("empId"));      // 메일 수신자 사번 : 필수
                            mailReq.put("toEmpNm", ds.get(i).get("empNm"));      // 메일 수신자 이름 : 필수
                            mailReq.put("toEmpEmail", ds.get(i).get("email"));      // 메일 수신자 메일주소 : 필수
                            mailReq.put("gubun", ds.get(i).get("gubun"));   // 구분

                            log.info("***************************************** fmSecIoEmpViolation_ActDo LOG *****************************************");
                            log.info("***************************** MAIL FORM  [mailReq] : " + paramMap);
                            log.info("*****************************************************************************************************************");

                            if ("C0280002".indexOf(actDo) > -1) { // 시정계획서 징구
                                //TODO:(hoonLee) 메일보내기 주석풀기
                                //fmSecIoEmpViolationJingGuMailSend(mailReq);
                            }
                            else if ("C0280006".indexOf(actDo) > -1) { //  대표이사 시정공문 징구
                                //TODO:(hoonLee) 메일보내기 주석풀기
                                //fmSecIoEmpViolationExprFileJingGuMailSend(mailReq);
                            }
                            else if ("C0280008".indexOf(actDo) > -1) { //  1개월출입정지
                                //TODO:(hoonLee) 메일보내기 주석풀기
                                //fmSecIoEmpViolationExprOneMonthMailSend(mailReq);
                            }
                            else if ("C0280007".indexOf(actDo) > -1) { //  영구출입정지
                                //TODO:(hoonLee) 메일보내기 주석풀기
                                //fmSecIoEmpViolationExprUnlimitMailSend(mailReq);
                            }

                            // ========== SMS 발송 ========== //
                             //주석처리 2023-06-09
                            if("1".toString().equals(ds.get(i).get("gubun").toString())  //위규자
                                  ||  "4".toString().equals(ds.get(i).get("gubun").toString()) // 대표관리자(정)
                                  ||  "6".toString().equals(ds.get(i).get("gubun").toString()) // 대표관리자(부)
                                 )
                            {
                              String hpNo = (String) ds.get(i).get("hpNo");
                                if(!"".equals(hpNo) && hpNo.length() > 0) {
                                  String msg = "";

//                            						    	RequestWrapModel<KakaoMessageDTO> wrapParams = new RequestWrapModel<>();
//                            						    	KakaoMessageDTO kakaoMessageDTO = new KakaoMessageDTO();

                                    if("C0280002".indexOf(actDo) > -1) { // 시정계획서 징구
                                        msg = "[SK하이닉스] " + ofendEmpNm + "께서는 welcome.skhynix.com에서 시정계획서를 즉시 제출 바랍니다.";
                                    //kakaoMessageDTO.setKTemplateCode("SJT_066384");
                                    }
                                    else if("C0280006".indexOf(actDo) > -1) { //  대표이사 시정공문 징구
                                      msg = "[SK하이닉스] " + ofendEmpNm + "께서는 welcome.skhynix.com에서 시정공문을 즉시 제출 바랍니다.";
                                      //kakaoMessageDTO.setKTemplateCode("SJT_066384");
                                    }
                                    else if("C0280008".indexOf(actDo) > -1) { //  1개월출입정지
                                      msg = "[SK하이닉스] " + ofendEmpNm + "께서는 보안위규로 1개월 출입정지 처리되었습니다.";
                                      //kakaoMessageDTO.setKTemplateCode("SJT_066385");
                                    }
                                    else if("C0280007".indexOf(actDo) > -1) { //  영구출입정지
                                      msg = "[SK하이닉스] " + ofendEmpNm + "께서는 보안위규로 영구 출입정지 처리되었습니다.";
                                      //kakaoMessageDTO.setKTemplateCode("SJT_066386");
                                    }

                                    Map<String, String> smsSendInfo = new HashMap<String, String>();

                                    String holdCallbackNo = "03180947279";
                                    //SMSReq.putField("CALLBACK_NO", "0316302294"); // 보내는 사람 휴대폰 번호
                                    smsSendInfo.put("holdCallbackNo", holdCallbackNo); // 발송 번호 수정 (0316302294 -> 03180947744 -> 03180947279)
                                    smsSendInfo.put("callbackNo", "03180947279"); // 보내는 사람 휴대폰 번호   (이홍순책임 요청으로 하이스택 김건호과장으로 변경 2017.05.26)
                                    smsSendInfo.put("smsNo", hpNo); // 받는 사람 휴대폰 번호
                                    smsSendInfo.put("msg", msg); // 문자 내용


                                    //TODO:(hoonLee) SMS전송 OR KAKAO메세지
                                    // ========== SMS 발송 ========== //
                                    //callSharedBizComponentByDirect(SMSSend, "fmSendSMS", SMSReq, onlineCtx);


//                                  kakaoMessageDTO.setSubject("보안위규");
//                                  kakaoMessageDTO.setDstaddr(hpNo.replaceAll("-", ""));
//                                  kakaoMessageDTO.setCallback("03151854114");
//                                  kakaoMessageDTO.setText(msg);
//                                  kakaoMessageDTO.setText2(msg);
//                                  kakaoMessageDTO.setKAttach("");
//                                  kakaoMessageDTO.setEmpId((String) ds.get(0).get("empId"));
//
//                                  wrapParams.setParams(kakaoMessageDTO);
//                                  commonApiClient.sendKakaoMessage(wrapParams);

                                }
                            }
                        }
                    }
                }
            }
        }

        return result;
    }

}
