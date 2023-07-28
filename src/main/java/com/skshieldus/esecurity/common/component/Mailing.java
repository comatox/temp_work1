package com.skshieldus.esecurity.common.component;

import com.skshieldus.esecurity.model.common.MailLogDTO;
import com.skshieldus.esecurity.service.common.CommonLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class Mailing {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Environment environment;

    @Autowired
    private CommonLogService commonLogService;

    /**
     * 메일 전송
     * - 제목, 내용, 수신자 이메일주소를 전달하연 메일을 전송한다.
     *
     * @param title : 메일 제목(필수)
     * @param content : 메일 내용(필수)
     * @param to : 수신자 이메일주소(필수)
     * @param empNo : 수신자 사번(로그 저장용)
     * @param schemaNm: 결재문서 구분(로그 저장용)
     * @param docId : 결재문서ID(로그 저장용)
     * @param acIp : 발송자IP(로그 저장용)
     * @author: X0121127<sungbum.oh @ partner.sk.com>
     * @since : 2021. 5. 21.
     */
    public boolean sendMail(String title, String content, String to, String empNo, String schemaNm, String docId, String acIp) {
        boolean result = true;
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;

        log.info("sendMail >>>> ");
        log.info("title = {}", title);
        log.info("content = {}", content);
        log.info("to = {}", to);

        try {
            if (environment.acceptsProfiles(Profiles.of("default", "dev", "stg"))) {
                //to = "sungbum.oh@partner.sk.com"; // OHSB Mail Test
                to = "skhy.X0123882@partner.sk.com"; // chabeomchul Mail Test
                log.info("to change (개발환경 메일주소 변경) = {}", to);
            }

            helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setFrom(new InternetAddress("skhynix_security@sk.com", "[e-Security]"));
            helper.setSubject(title);
            helper.setText(content, true);

            // 추후 주석제거 2023-06-09
            // javaMailSender.send(message);
        } catch (Exception e) {
            log.error(e.getMessage());
            result = false;
        }

        // 메일로그 정보를 넘겨준 경우 메일로그 등록
        try {
            List<MailLogDTO> mailLogList = new ArrayList<>();
            MailLogDTO mailLogDTO = new MailLogDTO();
            mailLogDTO.setEmpId(empNo);
            mailLogDTO.setSchemaNm(schemaNm);
            mailLogDTO.setContent(title);
            mailLogDTO.setSendYn(result
                ? "Y"
                : "N");
            mailLogDTO.setApprDeptKnd("");
            mailLogDTO.setDocId(docId);
            mailLogDTO.setAcIp(acIp);
            mailLogDTO.setCrtBy(empNo);
            mailLogDTO.setEmail(to);
            mailLogList.add(mailLogDTO);
            commonLogService.insertMailLog(mailLogList);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }

        return result;
    }

    /**
     * 메일 템플릿 설정 (박스안의 내용만 전달하여 전체 템플릿 구성)
     *
     * @param title
     * @param content
     * @return
     *
     * @author : X0121127<sungbum.oh@partner.sk.com>
     * @since : 2021. 5. 21.
     */
    public String applyMailTemplate(String title, String content) {
        return this.applyMailTemplate(title, content, null);
    }

    /**
     * 메일 템플릿 설정 (박스안의 내용만 전달하여 전체 템플릿 구성, parameter(? 포함) 존재할 경우 사용)
     *
     * @param title
     * @param content
     * @param parameter
     * @return
     *
     * @author : X0121127<sungbum.oh@partner.sk.com>
     * @since : 2021. 5. 21.
     */
    public String applyMailTemplate(String title, String content, String parameter) {
        StringBuffer buffer = new StringBuffer();
        String url = parameter != null
            ? "" + parameter
            : "";

        buffer.append("<!DOCTYPE html>");
        buffer.append("<html>");
        buffer.append("<head>");
        buffer.append("<meta charset='UTF-8' />");
        buffer.append("<meta http-equiv='X-UA-Compatible' content='IE=edge, chrome=1' />");
        buffer.append("<title>행복한 만남, SK하이닉스</title>");
        buffer.append("</head> ");
        buffer.append("<body>");
        buffer.append(" <table cellpadding='0' cellspacing='0' border='0' style='border-collapse: collapse; font-family:Malgun Gothic, Dotum;'>");
        buffer.append("		<tr>");
        buffer.append("			<td height='20' colspan='4' style='background:#f8f8f8;' bgcolor='#f8f8f8'></td> ");
        buffer.append("		</tr> ");
        buffer.append("		<tr valign='middle'> ");
        buffer.append("			<td style='width:20px; background:#f8f8f8;' bgcolor='#f8f8f8'></td> ");
        buffer.append("			<td style='width:150px; background:#f8f8f8;' bgcolor='#f8f8f8'> ");
        buffer.append("				<img src='https://welcome.skhynix.com/esecurity/assets/common/images/c-ci.png'/> ");
        //		buffer.append("				<h5 style='color:#555; font-size:18px; margin:4px 0; font-weight:bold'>e-Security</h5> ");
        buffer.append("			</td> ");
        buffer.append("			<td style='width:530px; background:#f8f8f8; mso-line-height-rule:exactly; line-height:30px; font-size:20px; color: #333; letter-spacing:-1px; font-weight:bold;' bgcolor='#f8f8f8'> " + title + "</td> ");
        buffer.append("			<td style='width:20px; background:#f8f8f8;' bgcolor='#f8f8f8'></td> ");
        buffer.append("		</tr> ");
        buffer.append("		<tr> ");
        buffer.append("			<td height='20' colspan='4' style='background:#f8f8f8' bgcolor='#f8f8f8'></td> ");
        buffer.append("		</tr> ");
        buffer.append("		<tr valign='middle'> ");
        buffer.append("			<td style='width:20px; background:#f8f8f8;' bgcolor='#f8f8f8'></td> ");
        buffer.append("			<td colspan='2' bgcolor='#fff' style='background:#fff; width:620px; padding:30px; border:1px solid #f1f1f1; mso-line-height-rule:exactly; line-height:26px; font-size:14px; letter-spacing: -1px;'> ");
        buffer.append("				<span style='color: blue; font-weight: bold;'> [ e-Security는 크롬에서 최적화 되어 있습니다. ] </span></br> ");
        buffer.append(content);
        buffer.append(" 				<table width='620' role='presentation' cellspacing='0' cellpadding='0' border='0' align='center' style='border-collapse: collapse; font-family:Malgun Gothic, Dotum;'> ");
        buffer.append("          			<tr valign='middle'> ");
        buffer.append("                		<td height='60' style='letter-spacing:-1px; font-size: 14px;position: relative; text-align: center'><hr/></td> ");
        buffer.append("                 	</tr> ");
        buffer.append("					<tr valign='middle'> ");
        buffer.append("						<td style='padding-left: 170px; padding-right: 170px;'> ");
        buffer.append("								<table cellpadding='0' cellmargin='0' border='0' width='250' style='border-collapse: collapse;  font-family:Malgun Gothic, Dotum;'> ");
        buffer.append("									<tr> ");
        buffer.append("										<td bgcolor='#ff7a00' valign='middle' align='center' height='40'> ");
        buffer.append("											<div style='font-size: 15px; color: #ffffff; line-height: 1; margin: 0; padding: 0; mso-table-lspace:0; mso-table-rspace:0; font-weight: bold; text-decoration: none!important;'> ");
        buffer.append(" 												<a href='" + url + "' style='text-decoration: none; color: #ffffff; border: 0; mso-table-lspace:0; mso-table-rspace:0; text-decoration: none!important; text-decoration: inherit;' border='0'> ");
        buffer.append("												<font style='color: #ffffff;letter-spacing:-1.5px;'> ");
        buffer.append("													e-Security 바로가기 ");
        buffer.append("												</font> ");
        buffer.append("												</a> ");
        buffer.append("											</div> ");
        buffer.append("										</td> ");
        buffer.append("									</tr> ");
        buffer.append("								</table> ");
        buffer.append("						</td> ");
        buffer.append("					</tr> ");
        buffer.append("				</table> ");
        buffer.append("			</td> ");
        buffer.append("			<td style='width:20px; background:#f8f8f8;' bgcolor='#f8f8f8'></td> ");
        buffer.append("		</tr> ");
        buffer.append("		<tr> ");
        buffer.append("			<td height='20' colspan='4' style='background:#f8f8f8' bgcolor='#f8f8f8'></td> ");
        buffer.append("		</tr> ");
        buffer.append("		<tr valign='middle'> ");
        buffer.append("			<td height='120' style='width:20px; background:#515151;' bgcolor='#515151'></td> ");
        buffer.append("			<td height='120' style='background:#515151;' bgcolor='#515151'><img src='https://welcome.skhynix.com/esecurity/assets/common/images/g-ci.png'/></td> ");
        buffer.append("			<td height='120' style='background:#515151; color:#f0f0f0; letter-spacing:-0.5px; font-weight:100; font-size:13px; mso-line-height-rule:exactly; line-height:20px; ' bgcolor='#515151'> ");
        buffer.append("				SK 하이닉스 경기도 이천시 부발읍 경충대로 2091<br/> ");
        //		buffer.append("				TEL 031.5185.3344<br/> ");
        buffer.append("				Copyright ⓒ SK Hynix Inc. All Rights Reserved. ");
        buffer.append("			</td> ");
        buffer.append("			<td height='120' style='width:20px; background:#515151;' bgcolor='#515151'></td> ");
        buffer.append("		</tr> ");
        buffer.append("	</table> ");
        buffer.append("</body> ");
        buffer.append("</html> ");

        return buffer.toString();
    }

    /**
     * 메일 전송
     * - 제목, 내용, 수신자 이메일주소를 전달하연 메일을 전송한다.
     *
     * @param title : 메일 제목(필수)
     * @param content : 메일 내용(필수)
     * @param to : 수신자 이메일주소(필수)
     * @param empNo : 수신자 사번(로그 저장용)
     * @param schemaNm: 결재문서 구분(로그 저장용)
     * @param docId : 결재문서ID(로그 저장용)
     * @param acIp : 발송자IP(로그 저장용)
     * @author: X0121127<sungbum.oh @ partner.sk.com>
     * @since : 2021. 5. 21.
     */
    public boolean sendMailBcc(String title, String content, String to, String empNo, String schemaNm, String docId, String acIp, String[] bcc) {
        boolean result = true;
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;

        log.info("sendMail >>>> ");
        log.info("title = {}", title);
        log.info("content = {}", content);
        log.info("to = {}", to);

        try {
            if (environment.acceptsProfiles(Profiles.of("default", "dev", "stg"))) {
                to = "sungbum.oh@partner.sk.com"; // OHSB Mail Test
                log.info("to change (개발환경 메일주소 변경) = {}", to);
            }

            helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setBcc(bcc);
            helper.setFrom(new InternetAddress("skhynix_security@sk.com", "[e-Security]"));
            helper.setSubject(title);
            helper.setText(content, true);

            // 추후 주석제거 2023-06-09
            // javaMailSender.send(message);
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error(e.getMessage());
            result = false;
        }

        // 메일로그 정보를 넘겨준 경우 메일로그 등록
        try {
            List<MailLogDTO> mailLogList = new ArrayList<>();
            MailLogDTO mailLogDTO = new MailLogDTO();
            mailLogDTO.setEmpId(empNo);
            mailLogDTO.setSchemaNm(schemaNm);
            mailLogDTO.setContent(title);
            mailLogDTO.setSendYn(result
                ? "Y"
                : "N");
            mailLogDTO.setApprDeptKnd("");
            mailLogDTO.setDocId(docId);
            mailLogDTO.setAcIp(acIp);
            mailLogDTO.setCrtBy(empNo);
            mailLogDTO.setEmail(to);
            mailLogList.add(mailLogDTO);
            commonLogService.insertMailLog(mailLogList);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }

        return result;
    }

    /**
     * 메일 전송
     * - 제목, 내용, 수신자 이메일주소를 전달하연 메일을 전송한다.
     *
     * @param title : 메일 제목(필수)
     * @param content : 메일 내용(필수)
     * @param to : 수신자 이메일주소(필수)
     * @param empNo : 수신자 사번(로그 저장용)
     * @param schemaNm: 결재문서 구분(로그 저장용)
     * @param docId : 결재문서ID(로그 저장용)
     * @param acIp : 발송자IP(로그 저장용)
     * @author: X0121127<sungbum.oh @ partner.sk.com>
     * @since : 2021. 5. 21.
     */
    public boolean sendMailExt(String title, String content, String to, String empNo, String schemaNm, String docId, String acIp) {
        boolean result = true;
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;

        log.info("sendMail >>>> ");
        log.info("title = {}", title);
        log.info("content = {}", content);
        log.info("to = {}", to);

        try {
            if (environment.acceptsProfiles(Profiles.of("default", "dev", "stg"))) {
                to = "sungbum.oh@partner.sk.com"; // OHSB Mail Test
                log.info("to change (개발환경 메일주소 변경) = {}", to);
            }

            helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setFrom(new InternetAddress("skhynix_security@sk.com", "[행복한 만남]"));
            helper.setSubject(title);
            helper.setText(content, true);

            // 추후 주석제거 2023-06-09
            // javaMailSender.send(message);
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error(e.getMessage());
            result = false;
        }

        // 메일로그 정보를 넘겨준 경우 메일로그 등록
        try {
            List<MailLogDTO> mailLogList = new ArrayList<>();
            MailLogDTO mailLogDTO = new MailLogDTO();
            mailLogDTO.setEmpId(empNo);
            mailLogDTO.setSchemaNm(schemaNm);
            mailLogDTO.setContent(title);
            mailLogDTO.setSendYn(result
                ? "Y"
                : "N");
            mailLogDTO.setApprDeptKnd("");
            mailLogDTO.setDocId(docId);
            mailLogDTO.setAcIp(acIp);
            mailLogDTO.setCrtBy(empNo);
            mailLogDTO.setEmail(to);
            mailLogList.add(mailLogDTO);
            commonLogService.insertMailLog(mailLogList);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }

        return result;
    }

    /**
     * 메일 템플릿 설정 (박스안의 내용만 전달하여 전체 템플릿 구성) - 외부메일
     *
     * @param title
     * @param content
     * @return
     *
     * @author : X0121127<sungbum.oh@partner.sk.com>
     * @since : 2021. 5. 21.
     */
    public String applyMailTemplateExt(String title, String content) {
        StringBuffer buffer = new StringBuffer();

        buffer.append("<!DOCTYPE html>");
        buffer.append("<html>");
        buffer.append("<head>");
        buffer.append("<meta charset='UTF-8' />");
        buffer.append("<meta http-equiv='X-UA-Compatible' content='IE=edge, chrome=1' />");
        buffer.append("<title>행복한 만남, SK하이닉스</title>");
        buffer.append("</head> ");
        buffer.append("<body>");
        buffer.append(" <table cellpadding='0' cellspacing='0' border='0' style='border-collapse: collapse; font-family:Malgun Gothic, Dotum;'>");
        buffer.append("		<tr>");
        buffer.append("			<td height='20' colspan='4' style='background:#f8f8f8;' bgcolor='#f8f8f8'></td> ");
        buffer.append("		</tr> ");
        buffer.append("		<tr valign='middle'> ");
        buffer.append("			<td style='width:20px; background:#f8f8f8;' bgcolor='#f8f8f8'></td> ");
        buffer.append("			<td style='width:150px; background:#f8f8f8;' bgcolor='#f8f8f8'> ");
        buffer.append("				<img src='https://welcome.skhynix.com/esecurity/assets/common/images/c-ci.png'/> ");
        //		buffer.append("				<h5 style='color:#555; font-size:18px; margin:4px 0; font-weight:bold'>e-Security</h5> ");
        buffer.append("			</td> ");
        buffer.append("			<td style='width:530px; background:#f8f8f8; mso-line-height-rule:exactly; line-height:30px; font-size:20px; color: #333; letter-spacing:-1px; font-weight:bold;' bgcolor='#f8f8f8'> " + title + "</td> ");
        buffer.append("			<td style='width:20px; background:#f8f8f8;' bgcolor='#f8f8f8'></td> ");
        buffer.append("		</tr> ");
        buffer.append("		<tr> ");
        buffer.append("			<td height='20' colspan='4' style='background:#f8f8f8' bgcolor='#f8f8f8'></td> ");
        buffer.append("		</tr> ");
        buffer.append("		<tr valign='middle'> ");
        buffer.append("			<td style='width:20px; background:#f8f8f8;' bgcolor='#f8f8f8'></td> ");
        buffer.append("			<td colspan='2' bgcolor='#fff' style='background:#fff; width:620px; padding:30px; border:1px solid #f1f1f1; mso-line-height-rule:exactly; line-height:26px; font-size:14px; letter-spacing: -1px;'> ");
        buffer.append(content);
        buffer.append(" 				<table width='620' role='presentation' cellspacing='0' cellpadding='0' border='0' align='center' style='border-collapse: collapse; font-family:Malgun Gothic, Dotum;'> ");
        buffer.append("          			<tr valign='middle'> ");
        buffer.append("                		<td height='60' style='letter-spacing:-1px; font-size: 14px;position: relative; text-align: center'>저희 SK 하이닉스 '행복한 만남'을 이용해 주셔서 다시 한번 감사드립니다.</td> ");
        buffer.append("                 	</tr> ");
        buffer.append("					<tr valign='middle'> ");
        buffer.append("						<td style='padding-left: 170px; padding-right: 170px;'> ");
        buffer.append("								<table cellpadding='0' cellmargin='0' border='0' width='250' style='border-collapse: collapse;  font-family:Malgun Gothic, Dotum;'> ");
        buffer.append("									<tr> ");
        buffer.append("										<td bgcolor='#ff7a00' valign='middle' align='center' height='40'> ");
        buffer.append("											<div style='font-size: 15px; color: #ffffff; line-height: 1; margin: 0; padding: 0; mso-table-lspace:0; mso-table-rspace:0; font-weight: bold; text-decoration: none!important;'> ");
        buffer.append(" 												<a href='" + "" + "' style='text-decoration: none; color: #ffffff; border: 0; mso-table-lspace:0; mso-table-rspace:0; text-decoration: none!important; text-decoration: inherit;' border='0'> ");
        buffer.append("												<font style='color: #ffffff;letter-spacing:-1.5px;'> ");
        buffer.append("													SK하이닉스 방문예약 시스템 바로가기 ");
        buffer.append("												</font> ");
        buffer.append("												</a> ");
        buffer.append("											</div> ");
        buffer.append("										</td> ");
        buffer.append("									</tr> ");
        buffer.append("								</table> ");
        buffer.append("						</td> ");
        buffer.append("					</tr> ");
        buffer.append("				</table> ");
        buffer.append("			</td> ");
        buffer.append("			<td style='width:20px; background:#f8f8f8;' bgcolor='#f8f8f8'></td> ");
        buffer.append("		</tr> ");
        buffer.append("		<tr> ");
        buffer.append("			<td height='20' colspan='4' style='background:#f8f8f8' bgcolor='#f8f8f8'></td> ");
        buffer.append("		</tr> ");
        buffer.append("		<tr valign='middle'> ");
        buffer.append("			<td height='120' style='width:20px; background:#515151;' bgcolor='#515151'></td> ");
        buffer.append("			<td height='120' style='background:#515151;' bgcolor='#515151'><img src='https://welcome.skhynix.com/esecurity/assets/common/images/g-ci.png'/></td> ");
        buffer.append("			<td height='120' style='background:#515151; color:#f0f0f0; letter-spacing:-0.5px; font-weight:100; font-size:13px; mso-line-height-rule:exactly; line-height:20px; ' bgcolor='#515151'> ");
        buffer.append("				SK 하이닉스 경기도 이천시 부발읍 경충대로 2091<br/> ");
        //		buffer.append("				TEL 031.5185.3344<br/> ");
        buffer.append("				Copyright ⓒ SK Hynix Inc. All Rights Reserved. ");
        buffer.append("			</td> ");
        buffer.append("			<td height='120' style='width:20px; background:#515151;' bgcolor='#515151'></td> ");
        buffer.append("		</tr> ");
        buffer.append("	</table> ");
        buffer.append("</body> ");
        buffer.append("</html> ");

        return buffer.toString();
    }

}