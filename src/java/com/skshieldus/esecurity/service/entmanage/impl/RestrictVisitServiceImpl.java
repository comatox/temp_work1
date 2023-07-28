package com.skshieldus.esecurity.service.entmanage.impl;

import com.skshieldus.esecurity.common.component.Mailing;
import com.skshieldus.esecurity.repository.entmanage.RestrictVisitRepository;
import com.skshieldus.esecurity.service.entmanage.RestrictVisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Transactional
public class RestrictVisitServiceImpl implements RestrictVisitService {

    @Autowired
    private RestrictVisitRepository repository;

    @Autowired
    private Mailing mailing;

    @Override
    public List<Map<String, Object>> selectRestrictCompList(Map<String, Object> paramMap) {
        return repository.selectRestrictCompList(paramMap);
    }

    @Override
    public Map<String, Object> selectRestrictCompView(Map<String, Object> paramMap) {
        return repository.selectRestrictCompView(paramMap);
    }

    @Override
    public List<Map<String, Object>> selectRestrictHistCompList(Map<String, Object> paramMap) {
        return repository.selectRestrictHistCompList(paramMap);
    }

    @Override
    public List<Map<String, Object>> selectRestrictEmpList(Map<String, Object> paramMap) {
        return repository.selectRestrictEmpList(paramMap);
    }

    @Override
    public Map<String, Object> selectRestrictEmpView(Map<String, Object> paramMap) {
        return repository.selectRestrictEmpView(paramMap);
    }

    @Override
    public List<Map<String, Object>> selectRestrictHistEmpList(Map<String, Object> paramMap) {
        return repository.selectRestrictHistEmpList(paramMap);
    }

    @Override
    public boolean updateRestrictCompResolveComp(Map<String, Object> paramMap) {
        repository.updateRestrictCompResolveHist(paramMap);
        return Objects.equals(repository.updateRestrictCompResolveComp(paramMap), 1);
    }

    @Override
    public boolean updateRestrictEmpResolveEmp(Map<String, Object> paramMap) {
        boolean result = true;

        // 방문예약
        if ("visit".equals(paramMap.get("type"))) {
            repository.updateRestrictVisitManResolve(paramMap);
            repository.updateRestrictVisitManResolveHist(paramMap);
            repository.updateRestrictVisitResolve(paramMap);
            repository.updateRestrictVisitResolveHist(paramMap);
            // 상시출입증
        }
        else if ("pass".equals(paramMap.get("type"))) {
            repository.updatePassRestrictFree(paramMap);
            repository.updatePassRestrictHistFree(paramMap);
            // 회원
        }
        else {
            repository.updateRestrictEmpResolveHist(paramMap);
            repository.updateRestrictEmpResolveEmp(paramMap);
        }
        // 제제대상 해제 메일발송
        sendMailRestrict(paramMap);

        return result;
    }

    private void sendMailRestrict(Map<String, Object> paramMap) {
        String title = "";
        StringBuffer content = new StringBuffer();

        if (paramMap.get("email") != null && !"".equals(paramMap.get("email")) &&
            ("visit".equals(paramMap.get("type")) || "pass".equals(paramMap.get("type")))) {
            // current date
            String formattedDate = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").format(new Date()).toString();
            String to = paramMap.get("email").toString();

            // 방문예약
            if ("visit".equals(paramMap.get("type"))) {
                title = "[e-Security] 거래제재 대상 방문예약 제재가 해제되었습니다.";
                // 상시출입증
            }
            else if ("pass".equals(paramMap.get("type"))) {
                title = "[e-Security] 거래제재 대상 상시출입 제재정보가 해제되었습니다.";
            }
            content.append("산업보안 서비스포털에 접속하셔서 확인하시기 바랍니다.<br />");
            content.append("<table width='70%' cellpadding='0' cellspacing='0' border='1' style='border-collapse: collapse; font-family:Malgun Gothic,  Dotum; font-size:14px;'>");
            content.append("<tr>");
            content.append("<td align='center' bgcolor='#F2CB61' width='30%'><b>업체</b></td>");
            content.append("<td align='center' width='70%'>" + (paramMap.get("ioCompNm") != null
                ? paramMap.get("ioCompNm").toString()
                : "") + "</td>");
            content.append("</tr>");
            content.append("<tr>");
            content.append("<td align='center' bgcolor='#F2CB61' width='30%'><b>대상자</b></td>");
            content.append("<td align='center' width='70%'>" + (paramMap.get("ioEmpNm") != null
                ? paramMap.get("ioEmpNm").toString()
                : "") + "</td>");
            content.append("</tr>");
            content.append("<tr>");
            content.append("<td align='center' bgcolor='#F2CB61' width='30%'><b>해제일시</b></td>");
            content.append("<td align='center' width='70%'>" + formattedDate + "</td>");
            content.append("</table>");

            // ================= NOTE: 메일 발송 시작 =======================

            String schemaNm = "VSIT_COMP";
            String acIp = String.valueOf(paramMap.get("acIp"));

            mailing.sendMail(title, mailing.applyMailTemplate(title, content.toString()), to, "SKhynix", schemaNm, "", acIp);
            // ================= NOTE: 메일 발송 종료 =======================
        }
    }

}
