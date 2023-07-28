package com.skshieldus.esecurity.service.secrtactvy.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skshieldus.esecurity.common.component.Mailing;
import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.model.common.ApprovalDTO;
import com.skshieldus.esecurity.model.common.ApprovalDocDTO;
import com.skshieldus.esecurity.model.common.CommonXlsViewDTO;
import com.skshieldus.esecurity.repository.secrtactvy.SecurityDocSendRepository;
import com.skshieldus.esecurity.service.common.ApprovalService;
import com.skshieldus.esecurity.service.secrtactvy.SecurityDocSendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
@Transactional
public class SecurityDocSendImpl implements SecurityDocSendService {

    @Autowired
    private ApprovalService approvalService;

    @Autowired
    private Environment environment;

    @Autowired
    private SecurityDocSendRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Mailing mailing;

    @Override
    public List<Map<String, Object>> selectSecurityDocSendList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectSecurityDocSendList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Integer selectSecurityDocSendListCnt(Map<String, Object> paramMap) {
        Integer resultCnt = null;

        try {
            resultCnt = repository.selectSecurityDocSendListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

    @Override
    public Map<String, Object> selectSecurityDocSend(Integer docSendapplNo) {
        Map<String, Object> result = null;

        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("docSendapplNo", docSendapplNo);

            boolean isProd = environment.acceptsProfiles(Profiles.of("prd"));

            if (isProd) {
                paramMap.put("operateId", "R_DOCSEND");
            }
            else {
                paramMap.put("operateId", "D_DOCSEND");
            }

            result = repository.selectSecurityDocSend(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean insertDocSend(HashMap<String, Object> paramMap) {
        boolean result = false;

        try {

            // 신규 채번
            int scDocSendApplNo = repository.selectDocSendSeq();
            paramMap.put("scDocSendApplNo", scDocSendApplNo);

            // ================= NOTE: [통합결재정보] 저장 시작 =======================
            ApprovalDTO approval = objectMapper.convertValue(paramMap.get("approval"), ApprovalDTO.class);
            Map<String, Object> htmlMap = objectMapper.convertValue(paramMap.get("htmlMap"), HashMap.class);

            approval.setLid(scDocSendApplNo);
            approval.setHtmlMap(htmlMap);
            log.info(">>>> insertDocSend approval setLid: " + approval);

            ApprovalDocDTO approvalDoc = approvalService.insertApproval(approval);
            Integer docId = approvalDoc.getDocId();
            paramMap.put("docId", docId);
            // ================= NOTE: [통합결재정보] 저장 종료 =======================

            // #######################  허가부서 참조메일 발송 시작 #######################
            // ######################################################################
            // 2015.11.16 FROM. 외부문서 사전승인신청시에만 허가자 모두에게 참조자를 통해서 메일 발송하는
            // 이력이 존재 했었음. FROM 김용범, 김정민 책임의 요청으로 참조자 메일은 삭제됨
            // ######################################################################
            List<Map<String, Object>> rsMailList = repository.selectDocSendMailList(paramMap);
            int mailListCount = rsMailList.size();

            paramMap.put("docNm", "외부공개자료 사전승인"); // 문서 명 : 필수

            for (int i = 0; i < mailListCount; i++) {
                String gubun = (String) rsMailList.get(i).get("gubun");
                if ("0".equals(gubun)) {   //신청자 정보
                    paramMap.put("applEmpId", rsMailList.get(i).get("empId"));// 신청자 사번 : 필수
                    paramMap.put("applEmpNm", rsMailList.get(i).get("empNm"));// 신청자 이름 : 필수
                    paramMap.put("applEmpEmail", rsMailList.get(i).get("email"));// 신청자 메일 : 필수

                    continue;
                }
                if ("2".equals(gubun)) {
                    /* 2017.05.25 : 허가자가 윤국한 수석일 경우 김용범 책임에게 메일을 발송한다. */
                    paramMap.put("toEmpId", rsMailList.get(i).get("empId"));  // 메일 수신자 사번 : 필수
                    paramMap.put("toEmpNm", rsMailList.get(i).get("empNm"));  // 메일 수신자 이름 : 필수
                    paramMap.put("toEmpEmail", rsMailList.get(i).get("email"));// 메일 수신자 메일주소 : 필수

                    DocSendReqMail(paramMap);
                }
            }

            // #######################  허가부서 참조메일 발송 종료 #######################

            // 공개 사유 특수문자 치환
            String publicRsnStr = (String) paramMap.get("publicRsn");

            publicRsnStr = publicRsnStr.replaceAll("%", "");
            publicRsnStr = publicRsnStr.replaceAll("\"", "′");  //&quot;
            publicRsnStr = publicRsnStr.replaceAll(",", ",");  //&#44;
            publicRsnStr = publicRsnStr.replaceAll("[?]", "");
            publicRsnStr = publicRsnStr.replaceAll("&", "");
            publicRsnStr = publicRsnStr.replaceAll("\\\\", "");
            publicRsnStr = publicRsnStr.replaceAll("'", "′"); // 작은 따옴표를 ′ 로 치환

            paramMap.put("publicRsn", publicRsnStr);

            repository.insertDocSend(paramMap);

            // 처리완료
            result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    private void DocSendReqMail(Map<String, Object> paramMap) {

        String sendFromName = (String) paramMap.get("applEmpNm");
        String sendFromID = (String) paramMap.get("applEmpId");
        String sendFromEmail = (String) paramMap.get("applEmpEmail");

        String sendToName = (String) paramMap.get("toEmpId");
        String sendToID = (String) paramMap.get("toEmpNm");
        String sendToEmail = (String) paramMap.get("toEmpEmail");

        String title = "[e-Security]" + sendFromName + "님께서 " + paramMap.get("docNm") + "을 신청하였습니다.";
        String message = sendFromName + "님께서 " + paramMap.get("docNm") + "을 신청하였습니다.<br />";
        message += "산업보안 서비스포털에 접속하셔서 확인하시기 바랍니다.<br />";

        boolean result = mailing.sendMail(title, mailing.applyMailTemplate(title, message), sendToEmail, "", (String) paramMap.get("docNm"), "", (String) paramMap.get("acIp"));
    }

    @Override
    public List<Map<String, Object>> getThesisList(HashMap<String, Object> paramMap) {
        List<Map<String, Object>> resultList = new ArrayList<>();

        // 주석처리 2023-06-09
        //		Object index = paramMap.get("currentPage");
        //		Object size =  paramMap.get("rowPerPage");
        //		if(index == null) paramMap.put("currentPage", "1");
        //		if(size == null) paramMap.put("rowPerPage", "10");
        //
        //	    String endPoint = "";
        //	    if (environment.acceptsProfiles(Profiles.of("default", "dev", "stg"))) {
        //	    	endPoint = "http://10.158.125.161:50000/XIAxisAdapter/MessageServlet?senderParty=&senderService=HQ0092_Q&receiverParty=&receiverService=&interface=LEG20252_SO&interfaceNamespace=http://skhynix.com/LEG/HQ0092/HQ0463";
        //	    }else{
        //	    	// PO운영 endPoint = "http://hympoprd.skhynix.com:50000/XIAxisAdapter/MessageServlet?senderParty=&senderService=HQ0092_P&receiverParty=&receiverService=&interface=LEG20252_SO&interfaceNamespace=http://skhynix.com/LEG/HQ0092/HQ0463";
        //	    	// SAP PO운영(POP)  :
        //	    	endPoint = "http://hyspoprd.skhynix.com:50000/XIAxisAdapter/MessageServlet?senderParty=&senderService=HQ0092_P&receiverParty=&receiverService=&interface=LEG20252_SO&interfaceNamespace=http://skhynix.com/LEG/HQ0092/HQ0463";
        //
        //	    }
        //	    LEG20252_SOProxy webService = new LEG20252_SOProxy(endPoint);
        //	    DT_LEG20252_HQ0092 reqst = new DT_LEG20252_HQ0092();
        //
        //	    reqst.setIDA2A2((String)paramMap.get("thesisIda2a2Number"));
        //	    reqst.setPUBLISHER_NAME((String)paramMap.get("publisherName"));
        //	    reqst.setTHESIS_NUMBER((String)paramMap.get("thesisNumber"));
        //	    reqst.setTITLE((String)paramMap.get("title"));
        //	    reqst.setORG_NAM((String)paramMap.get("orgNam"));
        //	    reqst.setPageIndex((String)paramMap.get("currentPage"));
        //	    reqst.setPageSize((String)paramMap.get("rowPerPage"));
        //	    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ RND PORTAL PO 전송 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        //	    System.out.println("Endpoint : "+ endPoint);
        //
        //
        //	    try{
        //	    	DT_LEG20252_HQ0092_responseOUTPUT[] reviewItemList = webService.LEG20252_SO(reqst);
        //
        //	    	Map<String, Object> resultTmp = null;
        //	    	System.out.println(reviewItemList.length);
        //
        //	    	if(reviewItemList != null) {
        //
        //		    	for(int i=0; i<reviewItemList.length; i++) {
        //
        //		    		DT_LEG20252_HQ0092_responseOUTPUT tmp = reviewItemList[i];
        //		    		resultTmp = new HashMap<String, Object>();
        //		    		resultTmp.put("ida2a2", tmp.getIDA2A2());
        //		    		resultTmp.put("orgNam", tmp.getORG_NAM());
        //		    		resultTmp.put("publishCategory", tmp.getPUBLISH_CATEGORY());
        //		    		resultTmp.put("publishDate", tmp.getPUBLISH_DATE());
        //		    		resultTmp.put("publishLocation", tmp.getPUBLISH_LOCATION());
        //		    		resultTmp.put("publishName", tmp.getPUBLISH_NAME());
        //		    		resultTmp.put("publisherName", tmp.getPUBLISHER_NAME());
        //		    		resultTmp.put("rowNum", tmp.getROW_NUM());
        //		    		resultTmp.put("thesisDate", tmp.getTHESIS_DATE());
        //		    		resultTmp.put("thesisNumber", tmp.getTHESIS_NUMBER());
        //		    		resultTmp.put("title", tmp.getTITLE());
        //		    		resultTmp.put("totCnt", tmp.getTOT_CNT());
        //
        //		    		resultList.add(resultTmp);
        //		    	}
        //	    	}
        //	    } catch (Exception bizEx) {
        //        	throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
        //        			"[MSGE0000] PO 웹서비스 전송중 오류가 발생하였습니다. !!]");
        //        }

        return resultList;
    }

    @Override
    public void docSendAppl(Map<String, Object> docMap) {

        // 주석처리 2023-06-09
        //		String endPoint = "";
        //	    if (environment.acceptsProfiles(Profiles.of("default", "dev", "stg"))) {
        //	    	endPoint = "http://10.158.125.161:50000/XIAxisAdapter/MessageServlet?senderParty=&senderService=HQ0092_Q&receiverParty=&receiverService=&interface=LEG20253_AO&interfaceNamespace=http://skhynix.com/LEG/HQ0092/HQ0463";
        //	    }else{
        //	    	// endPoint = "http://hympoprd.skhynix.com:50000/XIAxisAdapter/MessageServlet?senderParty=&senderService=HQ0092_P&receiverParty=&receiverService=&interface=LEG20253_AO&interfaceNamespace=http://skhynix.com/LEG/HQ0092/HQ0463";
        //	    	// SAP PO운영(POP)  :
        //	    	endPoint = "http://hyspoprd.skhynix.com:50000/XIAxisAdapter/MessageServlet?senderParty=&senderService=HQ0092_P&receiverParty=&receiverService=&interface=LEG20253_AO&interfaceNamespace=http://skhynix.com/LEG/HQ0092/HQ0463";
        //
        //	    }
        //
        //	    LEG20253_AOProxy webService = new LEG20253_AOProxy(endPoint);
        //	    DT_LEG20253_HQ0092INPUT reqst = new DT_LEG20253_HQ0092INPUT();
        //
        //    	reqst.setIDA2A2((String)docMap.get("thesisIda2a2Number"));
        //    	reqst.setTHESIS_NUMBER((String)docMap.get("thesisNumber"));
        //    	reqst.setCOMP_USER((String)docMap.get("empId"));
        //    	reqst.setDOC_SENDAPPL_NO(String.valueOf(docMap.get("docSendapplNo")));
        //
        //	    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ RND PORTAL PO 전송 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        //	    System.out.println("Endpoint : "+ endPoint);
        //
        //	    DT_LEG20253_HQ0092INPUT[] req = {reqst};
        //
        //	    System.out.println("IDA2A2  ::  " + req[0].getIDA2A2());
        //	    System.out.println("THESIS_NUMBER  ::  " + req[0].getTHESIS_NUMBER());
        //	    System.out.println("COMP_USER  ::  " + req[0].getCOMP_USER());
        //	    System.out.println("DOC_SENDAPPL_NO  ::  " + req[0].getDOC_SENDAPPL_NO());
        //
        //	    try{
        //	    	webService.LEG20253_AO(req);
        //
        //	    } catch (Exception bizEx) {
        //        	throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
        //        			"[MSGE0000] PO 웹서비스 전송중 오류가 발생하였습니다. !!]");
        //        }

    }

    @Override
    public CommonXlsViewDTO selectSecurityDocSendListExcel(HashMap<String, Object> paramMap) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        CommonXlsViewDTO commonXlsViewDTO = new CommonXlsViewDTO();
        commonXlsViewDTO.setFileName("전사 외부공개 자료현황" + "_" + sdf.format(new Date()));

        // set header names
        String[] headerNameArr = { "작성일", "자료 구분", "논문 번호", "사업장", "작성자 사번", "작성자", "발표자 사번", "발표자", "공개 장소", "발표 제목", "공개 상세 사유", "첨부파일", "첨부파일2", "첨부파일3", "결제 진행 상태", "승인자 사번", "승인자" };
        commonXlsViewDTO.setHeaderNameArr(headerNameArr);
        // set column names (data field name)
        String[] columnNameArr = { "oathDt", "docSendGbnNm", "thesisIda2a2Number", "compNm", "empId", "empNm", "anncEmpId", "anncEmpNm", "publicPlace", "anncTitle", "publicRsn", "filePath1Nm", "filePath2Nm", "filePath3Nm", "apprResultNm", "apprEmpId", "apprEmpNm" };
        commonXlsViewDTO.setColumnNameArr(columnNameArr);
        // set column width
        Integer[] columnWidthArr = { 7000, 7000, 7000, 7000, 7000, 7000, 7000, 7000, 7000, 7000, 7000, 7000, 7000, 7000, 7000, 7000, 7000 };
        commonXlsViewDTO.setColumnWidthArr(columnWidthArr);

        List<Map<String, Object>> list = repository.selectSecurityDocSendListExcel(paramMap);

        // set excel data
        commonXlsViewDTO.setDataList(list);

        return commonXlsViewDTO;
    }

}
