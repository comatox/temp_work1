package com.skshieldus.esecurity.service.entmanage.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skshieldus.esecurity.common.component.Mailing;
import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.repository.entmanage.AdminVisitEntranceRepository;
import com.skshieldus.esecurity.repository.entmanage.esecuritysi.EsecuritySiRepository;
import com.skshieldus.esecurity.repository.entmanage.idcardvisit.IdcardVisitRepository;
import com.skshieldus.esecurity.service.entmanage.AdminVisitEntranceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
public class AdminVisitEntranceServiceImpl implements AdminVisitEntranceService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Environment environment;

    @Autowired
    private AdminVisitEntranceRepository repository;

    @Autowired
    private EsecuritySiRepository siRepository;

    @Autowired
    private IdcardVisitRepository idcardVisitRepository;

    @Autowired
    private Mailing mailing;

    @Value("${security.extnet.url}")
    private String securityExtnetUrl;

    @Value("${security.insnet.url}")
    private String securityInsnetUrl;

    @Override
    public List<Map<String, Object>> selectCompInfoChgReqList(HashMap<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {

            resultList = repository.selectCompInfoChgReqList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return resultList;
    }

    @Override
    public Integer selectCompInfoChgReqListCnt(HashMap<String, Object> paramMap) {
        int cnt = 0;

        try {
            cnt = repository.selectCompInfoChgReqListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return cnt;
    }

    @Override
    public Map<String, Object> selectCompInfoChgReqView(Integer ioCompApplNo) {
        Map<String, Object> result = null;

        try {
            result = repository.selectCompInfoChgReqView(ioCompApplNo);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Boolean executeCompInfoChgReqUpdate(HashMap<String, Object> paramMap) {
        boolean result = false;

        try {
            String ioCompApplNo = (String) paramMap.get("ioCompApplNo");
            String applStatVal = (String) paramMap.get("applStatVal");

            if (ioCompApplNo == null || "".equals(ioCompApplNo)) {
                throw new Exception("키값이 누락되었습니다.");
            }

            int cnt = repository.executeCompInfoChgReqUpdate(paramMap);

            if ("Z0331005".equals(applStatVal)) {
                Map<String, Object> dataMap = repository.coorpVendroView(paramMap);
                dataMap.put("IO_COMP_ID", paramMap.get("ioCompId"));
                dataMap.put("LOGIN_ID", paramMap.get("loginId"));

                repository.coorpVendroCompUpdate(dataMap);
                //				hsRepository.coorpVendroCompUpdate(dataMap);
                siRepository.coorpVendroCompUpdate(dataMap);

                /*현재 필요없는 로직이나 주석처리되어있지 않아서 그대로 놔둠*/
                // 2016-10-25 by JSH ES_SN 신규 채번을 함 //
                // int newEsSn = repository.dmCoorpVendroComp_NEW_ES_SN();
                // 2016-10-25 by JSH ES_SN 신규 채번을 함 //
            }

            if (cnt > 0) {
                result = true;
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> selectPassportChgReqList(HashMap<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {

            resultList = repository.selectPassportChgReqList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return resultList;
    }

    @Override
    public Integer selectPassportChgReqListCnt(HashMap<String, Object> paramMap) {
        int cnt = 0;

        try {
            cnt = repository.selectPassportChgReqListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return cnt;
    }

    @Override
    public Map<String, Object> selectPassportChgReqView(Integer passportApplNo) {
        Map<String, Object> result = null;

        try {
            result = repository.selectPassportChgReqView(passportApplNo);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Boolean executePassportChgReqUpdate(HashMap<String, Object> paramMap) {
        boolean result = false;

        try {
            String passportApplNo = (String) paramMap.get("passportApplNo");
            String applStatVal = (String) paramMap.get("applStatVal");

            if (passportApplNo == null || "".equals(passportApplNo)) {
                throw new Exception("키값이 누락되었습니다.");
            }

            int cnt = repository.executePassportChgReqUpdate(paramMap);

            if ("Z0331005".equals(applStatVal)) {

                repository.dmIoEmpPassportNoUpdate(paramMap);
                //				hsRepository.dmIoEmpPassportNoUpdate(paramMap);
                siRepository.dmIoEmpPassportNoUpdate(paramMap);

                repository.dmOffLimitsIoEmpChgHistInsert(paramMap);
            }

            if (cnt > 0) {
                result = true;
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> selectCompChgReqList(HashMap<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {

            resultList = repository.selectCompChgReqList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return resultList;
    }

    @Override
    public Integer selectCompChgReqListCnt(HashMap<String, Object> paramMap) {
        int cnt = 0;

        try {
            cnt = repository.selectCompChgReqListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return cnt;
    }

    @Override
    public Map<String, Object> selectCompChgReqView(Integer compApplNo) {
        Map<String, Object> result = null;

        try {
            result = repository.selectCompChgReqView(compApplNo);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Boolean executeCompChgReqUpdate(HashMap<String, Object> paramMap) {
        boolean result = false;

        try {
            boolean isProd = environment.acceptsProfiles(Profiles.of("prd"));

            String compApplNo = objectMapper.convertValue(paramMap.get("compApplNo"), String.class);
            String applStatVal = objectMapper.convertValue(paramMap.get("applStatVal"), String.class);

            if (compApplNo == null || "".equals(compApplNo)) {
                throw new Exception("키값이 누락되었습니다.");
            }

            int cnt = repository.executeCompChgReqUpdate(paramMap);

            if (cnt > 0) {

                if ("Z0331005".equals(applStatVal)) {
                    repository.insertOffLimitsIoEmpChgHist(paramMap);

                    int rsPassCnt = 0;
                    String passMst2Yn = objectMapper.convertValue(paramMap.get("passMst2Yn"), String.class);

                    Map<String, Object> idcardVisitIfDataMap = null;

                    String[] siteList = { "HN", "HS", "SI" };

                    for (String siteType : siteList) {
                        rsPassCnt = 0;
                        idcardVisitIfDataMap = null;

                        if (siteType.equals("HN")) {
                            if ("Y".equals(passMst2Yn)) {
                                rsPassCnt = 1;
                            }

                            repository.updateVisitIoEmpCompId(paramMap);

                            // 업체물품 진행중인 DATA도 업체 변경!!
                            repository.updateVisitIoInoutCompId(paramMap);

                            if (rsPassCnt > 0) {
                                repository.insertOffLimitsCompChgExprHist(paramMap);
                                idcardVisitIfDataMap = repository.selectOffLimitsExprHistSeq(paramMap);
                            }
                        }
                        else if (siteType.equals("HS")) {
                            try {
                                //		    					rsPassCnt = hsRepository.selectPassMst2(paramMap);
                                //
                                //		    					hsRepository.updateVisitIoEmpCompId(paramMap);
                                //
                                //								// 업체물품 진행중인 DATA도 업체 변경!!
                                //		    					hsRepository.updateVisitIoInoutCompId(paramMap);
                                //
                                //		    					if(rsPassCnt > 0) {
                                //		    						hsRepository.insertOffLimitsCompChgExprHist(paramMap);
                                //									idcardVisitIfDataMap = hsRepository.selectOffLimitsExprHistSeq(paramMap);
                                //		    					}

                            } catch (Exception e) {
                                log.error("[Hystec] executeCompChgReqUpdate selectPassMst2 error: {}", e.toString());
                            }
                        }
                        else if (siteType.equals("SI")) {
                            try {
                                rsPassCnt = siRepository.selectPassMst2(paramMap);

                                siRepository.updateVisitIoEmpCompId(paramMap);

                                // 업체물품 진행중인 DATA도 업체 변경!!
                                siRepository.updateVisitIoInoutCompId(paramMap);

                                if (rsPassCnt > 0) {
                                    siRepository.insertOffLimitsCompChgExprHist(paramMap);
                                    idcardVisitIfDataMap = siRepository.selectOffLimitsExprHistSeq(paramMap);
                                }
                            } catch (Exception e) {
                                log.error("[SystemIC] executeCompChgReqUpdate selectPassMst2 error: {}", e.toString());
                            }
                        }

                        if (rsPassCnt > 0 && idcardVisitIfDataMap != null) {
                            idcardVisitIfDataMap.put("S_C_GBN", "S");
                            idcardVisitIfDataMap.put("EXPR_CODE", "A0460010");
                            idcardVisitIfDataMap.put("SITE_TYPE", siteType);

                            log.info("==========>>>>> 하이스텍(IDcard) 정지신청 I/F 호출 <<<<<==========");
                            log.info("paramMap = {}", idcardVisitIfDataMap.toString());
                            log.info("==========>>>>> 하이스텍(IDcard) 정지신청 신청 I/F 호출 <<<<<==========");

                            if (isProd) {
                                idcardVisitRepository.procedureOffLimitsIoPassExprHist(idcardVisitIfDataMap);
                            }
                        }
                    }
                }

                Map<String, Object> mailReq = new HashMap<String, Object>();
                //접수 메일 발송 : 신청한 외부회원에게 메일 발송
                if ("Z0331005".equals(applStatVal)) {
                    mailReq.put("MAIL_TYPE", "IO_EMP_COMP_CHG_OK"); // 인증처리
                }
                else {
                    mailReq.put("MAIL_TYPE", "IO_EMP_COMP_CHG_CANCLE"); // 인증반려처리
                }
                mailReq.put("EMAIL", paramMap.get("emailAddr"));// 받는사람메일주소 : 필수
                mailReq.put("COMP_NM", paramMap.get("ioCompNmKo")); // 업체명
                mailReq.put("EMP_NM", paramMap.get("ioEmpNm")); // 회원명
                mailReq.put("AC_IP", paramMap.get("acIp"));  // IP

                fmIoCompChgMail(mailReq);

                result = true;
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    private void fmIoCompChgMail(Map<String, Object> mailReq) {
        // 신청 회원 이메일 id
        String TO_EMAIL = StringUtils.defaultIfEmpty((String) mailReq.get("EMAIL"), "");
        String MAIL_TYPE = StringUtils.defaultIfEmpty((String) mailReq.get("MAIL_TYPE"), "");

        //	    String sendFrom   = "";
        String sendTo = "";
        String title = "";
        String message = "";
        String schemaNm = "IO_EMP_COMP_CHG";

        if ("IO_EMP_COMP_CHG_OK".equals(MAIL_TYPE)) {
            //	    	sendFrom 	= "SK하이닉스";
            sendTo = TO_EMAIL;
            title = "[행복한 만남, SK 하이닉스]소속업체 변경신청 건 승인 완료";
            message = " 회원명 : " + StringUtils.defaultIfEmpty((String) mailReq.get("EMP_NM"), "") + "     업체명 : " + StringUtils.defaultIfEmpty((String) mailReq.get("COMP_NM"), "");
            //message    	+="<br><a href='"+domain+"?SCHEMAID=SCRT_EMP_CERTI' target='_new'>e-Security 바로가기</a>";
        }
        else if ("IO_EMP_COMP_CHG_CANCLE".equals(MAIL_TYPE)) {
            //	    	sendFrom 	= "SK하이닉스";
            sendTo = TO_EMAIL;
            title = "[행복한 만남, SK 하이닉스]소속업체 변경신청 건 반려처리";
            message = " 회원명 : " + StringUtils.defaultIfEmpty((String) mailReq.get("EMP_NM"), "") + "     업체명 : " + StringUtils.defaultIfEmpty((String) mailReq.get("COMP_NM"), "");
        }

        mailing.sendMail(title, mailing.applyMailTemplateExt(title, message), sendTo, "", schemaNm, "", (String) mailReq.get("AC_IP"));
    }

}
