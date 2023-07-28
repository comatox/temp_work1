package com.skshieldus.esecurity.service.sysmanage.impl;

import com.skshieldus.esecurity.common.component.Mailing;
import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.model.sysmanage.SpamMailDTO;
import com.skshieldus.esecurity.model.sysmanage.SpamMailSearchDTO;
import com.skshieldus.esecurity.repository.sysmanage.BoardRepository;
import com.skshieldus.esecurity.repository.sysmanage.SpamMailRepository;
import com.skshieldus.esecurity.service.sysmanage.SpamMailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SpamMailServiceImpl implements SpamMailService {

    @Autowired
    private SpamMailRepository spamMailRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private Mailing mailing;

    @Override
    public List<SpamMailDTO> selectSpamMailList(SpamMailSearchDTO spamMailSearch) {

        log.info(">>>> selectSpamMailList : " + spamMailSearch);

        return spamMailRepository.selectSpamMailList(spamMailSearch);
    }

    @Override
    public SpamMailDTO selectSpamMailView(SpamMailSearchDTO spamMailSearch) {

        log.info(">>>> selectSpamMailView : " + spamMailSearch);

        return spamMailRepository.selectSpamMailView(spamMailSearch);
    }

    @Override
    public Boolean updateSpamMail(SpamMailDTO spamMail) {
        boolean result = true;
        try {

            log.info(">>>> updateSpamMail : " + spamMail);

            if (spamMailRepository.updateSpamMail(spamMail) != 1) {
                result = false;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Boolean deleteSpamMail(SpamMailDTO spamMail) {
        boolean result = true;
        try {

            log.info(">>>> deleteSpamMail : " + spamMail);

            if (spamMailRepository.deleteSpamMail(spamMail) != 1) {
                result = false;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Boolean insertSpamMail(SpamMailDTO spamMail) {
        boolean result = true;
        try {

            log.info(">>>> insertSpamMail : " + spamMail);

            if (spamMailRepository.insertSpamMail(spamMail) != 1) {
                result = false;
                throw new Exception("Failed to execute spamMailRepository.insertSpamMail");
            }
            else {

                log.info(">>>> 게시판 등록 성공");

                String empId = StringUtils.defaultIfEmpty(spamMail.getCrtBy(), "");
                String schemaNm = StringUtils.defaultIfEmpty(spamMail.getSchemaNm(), "");
                String content = StringUtils.defaultIfEmpty(spamMail.getContent(), "");
                String boardTitle = "";

                List<Map<String, Object>> mailList = boardRepository.listBoardMailReceiver(schemaNm);
                if (mailList.size() == 0) {
                    log.info(">>>> 게시판 메일 수신자 없음 ");
                    //메일 미발송
                    return result;
                }

                //게시판 제목
                String articleTitle = "";
                articleTitle = "제목 : " + StringUtils.defaultIfEmpty(spamMail.getTitle(), "");

                if (mailList != null && mailList.size() > 0) {
                    boardTitle = StringUtils.defaultIfEmpty((String) mailList.get(0).get("boardNm"), "");
                }

                String title = "[e-Security] " + boardTitle + "에 글이 등록되었습니다.";
                for (int k = 0; k < mailList.size(); k++) {

                    Map<String, Object> data = new HashMap<String, Object>();
                    data.put("articleTitle", articleTitle);
                    data.put("title", title);
                    data.put("content", content);
                    data.put("schemaNm", schemaNm);
                    data.put("sendToEmail", StringUtils.defaultIfEmpty((String) mailList.get(k).get("sendToEmail"), ""));
                    data.put("sendToEmpId", StringUtils.defaultIfEmpty((String) mailList.get(k).get("sendToEmpId"), ""));
                    data.put("empId", empId);
                    this.sendMail(data);
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public SpamMailDTO selectSpamMailUserInfo(SpamMailSearchDTO spamMailSearch) {

        log.info(">>>> selectSpamMailUserInfo : " + spamMailSearch);

        return spamMailRepository.selectSpamMailUserInfo(spamMailSearch);
    }

    public Boolean sendMail(Map<String, Object> paramMap) {
        log.info("[e-Security]게시판 메일 발송 ");

        boolean result = true;
        try {

            String schemaNm = StringUtils.defaultIfEmpty(String.valueOf(paramMap.get("schemaNm")), "");
            String title = StringUtils.defaultIfEmpty(String.valueOf(paramMap.get("title")), "");
            String articleTitle = StringUtils.defaultIfEmpty(String.valueOf(paramMap.get("articleTitle")), "");
            String content = StringUtils.defaultIfEmpty(String.valueOf(paramMap.get("content")), "");
            String boardTitle = StringUtils.defaultIfEmpty(String.valueOf(paramMap.get("boardTitle")), "");

            String sendToEmail = StringUtils.defaultIfEmpty(String.valueOf(paramMap.get("sendToEmail")), "");
            String sendToEmpId = StringUtils.defaultIfEmpty(String.valueOf(paramMap.get("sendToEmpId")), "");
            String acIp = StringUtils.defaultIfEmpty((String) paramMap.get("acIp"), "");

            String message = "";

            message += articleTitle;
            message += "<ul>";
            message += "<li>내용	:	" + content + "</li>";
            message += "</ul>";

            result = mailing.sendMail(title, mailing.applyMailTemplate(title, message), sendToEmail, sendToEmpId, schemaNm, "", acIp);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            result = false;
        }

        return result;
    }

}