package com.skshieldus.esecurity.service.sysmanage.impl;

import com.skshieldus.esecurity.common.component.Mailing;
import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.model.sysmanage.BoardDTO;
import com.skshieldus.esecurity.model.sysmanage.BoardSearchDTO;
import com.skshieldus.esecurity.repository.sysmanage.BoardRepository;
import com.skshieldus.esecurity.service.sysmanage.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private Mailing mailing;

    @Override
    public List<BoardDTO> selectBoardList(BoardSearchDTO boardSearch) {

        // 주석처리 2023-06-09
        //	    if(StringUtils.equals("REPORT", boardSearch.getBoardGbn())){
        //	    	  try {
        //	    		  CipherUtils chpherUtils = new CipherUtils();
        //	    			String encCrtBy = chpherUtils.encrypt(boardSearch.getSessionEmpId());
        //
        //	    			boardSearch.setEncSessionEmpId(encCrtBy);
        //			    	log.info("CipherUtils>>>>>>>암호화 성공 encCrtBy  :"+encCrtBy);
        //				} catch (Exception e) {
        //					log.info("CipherUtils>>>>>>>암호화 오류");
        //				}
        //	    }
        //		log.info(">>>> selectBoardList : " + boardSearch);

        return boardRepository.selectBoardList(boardSearch);
    }

    @Override
    public BoardDTO selectBoardView(BoardSearchDTO boardSearch) {
        BoardDTO board = new BoardDTO();
        log.info(">>>> selectBoardView : " + boardSearch);
        String encCrtBy = "";
        //REPORT일 경우 암호화해서 전송
        //	    if(StringUtils.equals("REPORT", boardSearch.getBoardGbn())){
        //	    	  try {
        //	    		  CipherUtils chpherUtils = new CipherUtils();
        //	    		    encCrtBy = chpherUtils.encrypt(boardSearch.getSessionEmpId());
        //			    	board.setEncCrtBy(encCrtBy);
        //			    	log.info("CipherUtils>>>>>>>암호화 성공 encCrtBy  :"+encCrtBy);
        //				} catch (Exception e) {
        //					log.info("CipherUtils>>>>>>>암호화 오류");
        //				}
        //	    }

        board = boardRepository.selectBoardView(boardSearch);
        board.setEncCrtBy(encCrtBy);

        // XSS 취약점 대응
        if (board != null && StringUtils.isNotEmpty(board.getContent())) {
            board.setContent(cleanXSS(board.getContent()));
        }

        log.info(">>>> selectBoardView board: " + board);
        return board;
    }

    @Override
    public Boolean updateBoard(BoardDTO board) {
        boolean result = true;
        try {

            log.info(">>>> updateBoard : " + board);

            if (boardRepository.updateBoard(board) != 1) {
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
    public Boolean deleteBoardView(BoardDTO board) {
        boolean result = true;
        try {

            log.info(">>>> deleteBoard : " + board);

            if (boardRepository.deleteBoardView(board) != 1) {
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
    public Boolean insertBoard(BoardDTO board) {
        boolean result = true;
        try {

            log.info(">>>> insertBoard : " + board);
            //REPORT일 경우 암호화해서 전송
            //		    if(StringUtils.equals("REPORT", board.getBoardGbn())){
            //		    	  try {
            //		    		  CipherUtils chpherUtils = new CipherUtils();
            //		    			String encCrtBy = chpherUtils.encrypt(board.getCrtBy());
            //		    			log.info("CipherUtils>>>>>>>encCrtBy  :"+encCrtBy);
            //				    	board.setCrtBy(encCrtBy);
            //				    	board.setEncCrtBy(encCrtBy);
            ////				    	log.info("CipherUtils>>>>>>>암호화 성공 encCrtBy  :"+encCrtBy);
            //					} catch (Exception e) {
            //						log.info("CipherUtils>>>>>>>암호화 오류");
            //					}
            //		    }

            log.info(">>>> insertBoard : " + board);
            if (boardRepository.insertBoard(board) != 1) {
                result = false;
                throw new Exception("Failed to execute boardRepository.insertBoard");
            }
            else {

                log.info(">>>> 게시판 등록 성공");

                //				String empId = requestData.getField("CRT_BY");
                //				String schema_nm = requestData.getField("SCHEMA_NM");
                //				String pTitle 	= requestData.getField("TITLE");
                //			    String pContent = requestData.getField("CONTENT");
                //			    String sendFromMail = "skhynix_security@sk.com";

                String empId = StringUtils.defaultIfEmpty(board.getCrtBy(), "");
                //메일제목
                String schemaNm = StringUtils.defaultIfEmpty(board.getSchemaNm(), "");
                String content = StringUtils.defaultIfEmpty(board.getContent(), "");
                String qnaEmp = StringUtils.defaultIfEmpty(board.getQnaEmp(), "");
                String boardTitle = "";

                //				ArrayList<HashMap<String,String>> resultList = new ArrayList<HashMap<String,String>>();

                List<Map<String, Object>> mailList = new ArrayList<Map<String, Object>>();

                //				List<Map<String, Object>> mailList1 = boardRepository.listBoardMailReceiver(schemaNm);

                if (StringUtils.equals("QNA", board.getBoardGbn())) {
                    schemaNm = "SECURITY_QNA";
                }
                else if (StringUtils.equals("SPAM", board.getBoardGbn())) {
                    schemaNm = "CYBER_SPAM_MAIL";
                }
                else if (StringUtils.equals("OPEN", board.getBoardGbn())) {
                    schemaNm = "CYBER_OPEN_EAR";
                }
                else if (StringUtils.equals("SPY", board.getBoardGbn())) {
                    schemaNm = "CYBER_SPY_REPORT";
                }
                else if (StringUtils.equals("SECURITY", board.getBoardGbn())) {
                    schemaNm = "CYBER_SECURITY_WEAK_REPORT";
                }
                else if (StringUtils.equals("REPORT", board.getBoardGbn())) {
                    schemaNm = "SECURITY_REPORT";
                }
                else if (StringUtils.equals("GNR", board.getBoardGbn())) {
                    schemaNm = "SECURITY_GNR";
                }
                else if (StringUtils.equals("SECU", board.getBoardGbn())) {
                    schemaNm = "SECURITY_SECU";
                }

                log.info(">>>> 게시판 메일 schemaNm : " + schemaNm);
                if (StringUtils.equals("QNA", board.getBoardGbn())) {
                    mailList = boardRepository.listQnaBoardMailReceiver(qnaEmp);
                }
                else {
                    mailList = boardRepository.listBoardMailReceiver(schemaNm);
                }

                if (mailList.size() == 0) {
                    log.info(">>>> 게시판 메일 수신자 없음 ");
                    //메일 미발송
                    return result;
                }

                //게시판 제목
                String articleTitle = "";
                if (!schemaNm.equals("CYBER_SPAM_MAIL")) {
                    articleTitle = "제목 : " + StringUtils.defaultIfEmpty(board.getTitle(), "");
                }
                if (mailList != null && mailList.size() > 0) {
                    boardTitle = StringUtils.defaultIfEmpty((String) mailList.get(0).get("boardNm"), "");
                }

                //제보글  시
                if (board.getBoardGbn() != null && board.getBoardGbn().equals("REPORT")) {
                    boardTitle = "하이보안 게시판";

                    //					log.info(">>>> board.getReportReply() "+board.getReportReply());
                    //제보글 답변 시 제보자에게 메일 발송 x
                    //				   if(board.getReportReply() != null && board.getReportReply().equals("Y")) {
                    //
                    //						if(!board.getCrtBy().equals(board.getUsempId())) {
                    //							return result;
                    //						}
                    //				   }
                }
                else if (board.getBoardGbn() != null && board.getBoardGbn().equals("QNA")) {
                    boardTitle = "하이보안 게시판(문의)";
                }

                String title = "[e-Security] " + boardTitle + "에 글이 등록되었습니다.";
                for (int k = 0; k < mailList.size(); k++) {

                    Map<String, Object> data = new HashMap<String, Object>();
                    //data.put("boardTitle", boardTitle);
                    data.put("articleTitle", articleTitle);
                    data.put("title", title);
                    data.put("content", content);
                    data.put("schemaNm", schemaNm);
                    data.put("sendToEmail", StringUtils.defaultIfEmpty((String) mailList.get(k).get("email"), ""));
                    data.put("sendToEmpId", StringUtils.defaultIfEmpty((String) mailList.get(k).get("empId"), ""));
                    data.put("empId", empId);
                    this.sendMail(data);
                }
                //
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Boolean updateBoardReadUp(BoardDTO board) {
        boolean result = true;
        try {

            log.info(">>>> updateBoardReadUp : " + board);

            if (boardRepository.updateBoardReadUp(board) != 1) {
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
    public Boolean insertBoardUp(BoardDTO board) {
        boolean result = true;
        try {

            log.info(">>>> insertBoardUp : " + board);

            if (boardRepository.insertBoardUp(board) != 1) {
                result = false;
                throw new Exception("Failed to execute boardRepository.insertBoardUp");
            }
            else {

                log.info(">>>> insertBoardUp 등록 성공");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public BoardDTO selectBoardTop(BoardSearchDTO boardSearch) {

        log.info(">>>> selectBoardTop : " + boardSearch);

        return boardRepository.selectBoardTop(boardSearch);
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
            //		String empId = StringUtils.defaultIfEmpty(String.valueOf(paramMap.get("empId")),"");
            //	    String docId = StringUtils.defaultIfEmpty(String.valueOf(paramMap.get("docId")),"");
            //	    String empNo = StringUtils.defaultIfEmpty((String)paramMap.get("empNo"), "");
            String acIp = StringUtils.defaultIfEmpty((String) paramMap.get("acIp"), "");

            //	    String sendTo     = StringUtils.defaultIfEmpty((String)paramMap.get("userEmail"), "");

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

    private String cleanXSS(String value) {
        // value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
        // value = value.replaceAll("'", "&#39;");
        // value = value.replaceAll("\\\"", "&quot;");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        value = value.replaceAll("(\\s)+on[^=]*[=]([^>])*[>]", " >");
        value = value.replaceAll("[<]object(\\s)+data[=]([^>])*[>]", " <object>");
        value = value.replaceAll("[<]applet(\\s)+code[=]([^>])*[>]", " <applet>");
        value = value.replaceAll("<[\\s]*script[\\s]*>", "");
        return value;
    }

}