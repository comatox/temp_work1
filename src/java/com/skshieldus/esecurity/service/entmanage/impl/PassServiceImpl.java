package com.skshieldus.esecurity.service.entmanage.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skshieldus.esecurity.common.component.Mailing;
import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.utils.FTPUtil;
import com.skshieldus.esecurity.model.common.ApprovalDTO;
import com.skshieldus.esecurity.model.common.ApprovalDocDTO;
import com.skshieldus.esecurity.model.common.SavedApproverLineDTO;
import com.skshieldus.esecurity.repository.entmanage.EntManageCommonRepository;
import com.skshieldus.esecurity.repository.entmanage.PassRepository;
import com.skshieldus.esecurity.repository.entmanage.esecuritysi.EsecuritySiRepository;
import com.skshieldus.esecurity.repository.entmanage.idcard.IdcardRepository;
import com.skshieldus.esecurity.repository.entmanage.idcardvisit.IdcardVisitRepository;
import com.skshieldus.esecurity.service.common.ApprovalService;
import com.skshieldus.esecurity.service.entmanage.PassService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
@Transactional
public class PassServiceImpl implements PassService {

    @Autowired
    private Environment environment;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Mailing mailing;

    @Autowired
    private ApprovalService approvalService;

    @Autowired
    private PassRepository repository;

    @Autowired
    private EntManageCommonRepository commonRepository;

    @Autowired
    private IdcardRepository idcardRepository;

    @Autowired
    private IdcardVisitRepository idcardVisitRepository;

    @Autowired
    private EsecuritySiRepository esecuritySiRepository;

    @Value("${security.extnet.url}")
    private String securityExtnetUrl;

    @Value("${ifaccess.idcard.ftp.ip}")
    private String ftpIp;

    @Value("${ifaccess.idcard.ftp.port}")
    private int ftpPort;

    @Value("${ifaccess.idcard.ftp.id}")
    private String ftpId;

    @Value("${ifaccess.idcard.ftp.password}")
    private String ftpPwd;

    private final String FTP_UPLOAD_DIR = "/entrance";

    @Override
    public List<Map<String, Object>> selectRegularPassList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectRegularPassList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Integer selectRegularPassListCnt(Map<String, Object> paramMap) {
        Integer resultCnt = null;

        try {
            resultCnt = repository.selectRegularPassListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

    @Override
    public String selectRegularPassChkRestrict(Map<String, Object> paramMap) {
        String restYn = "N";

        try {
            Integer passApplNo = objectMapper.convertValue(paramMap.get("passApplNo"), Integer.class);
            Map<String, Object> resultMap = repository.selectRegularPassChkRestrict(passApplNo);

            String emailAddr = objectMapper.convertValue(paramMap.get("email"), String.class);
            String restChk = objectMapper.convertValue(paramMap.get("restChk"), String.class);

            if (resultMap != null && resultMap.get("restYn") != null && "N".equals(restChk)) {
                emailAddr = objectMapper.convertValue(resultMap.get("email"), String.class);
                restYn = objectMapper.convertValue(resultMap.get("restYn"), String.class);
            }

            /* [START] 윤리경영팀에게 제재대상관련메일 발송 및 IO_PASS_REST_HIST 테이블에 INSERT : 2015-07-30 by JSH */
            if ("Y".equals(restYn) && "N".equals(restChk)) {

                // 접수 메일 발송 : 신청한 외부회원에게 메일 발송
                String title = "[e-Security] 거래제재 대상 상시출입신청 건이 발생 하였습니다.";
                String ioEmpId = objectMapper.convertValue(paramMap.get("ioEmpId"), String.class); // 회원ID(메일주소)
                String ioEmpNm = objectMapper.convertValue(paramMap.get("ioEmpNm"), String.class); // 회원명
                String ioCompNm = objectMapper.convertValue(paramMap.get("ioCompNm"), String.class); // 업체명
                String formattedDate = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").format(new Date());
                String acIp = objectMapper.convertValue(paramMap.get("acIp"), String.class);

                StringBuilder mailContent = new StringBuilder();
                mailContent.append("						산업보안 서비스포털에 접속하셔서 확인하시기 바랍니다.<br />");
                mailContent.append("<table width='70%' cellpadding='0' cellspacing='0' border='1' style='border-collapse: collapse; font-family:Malgun Gothic,  Dotum; font-size:14px;'>");
                mailContent.append("<tr>");
                mailContent.append("<td align='center' bgcolor='#F2CB61' width='30%'><b>업체</b></td>");
                mailContent.append("<td align='center' width='70%'>" + ioCompNm + "</td>");
                mailContent.append("</tr>");

                mailContent.append("<tr>");
                mailContent.append("<td align='center' bgcolor='#F2CB61' width='30%'><b>대상자</b></td>");
                mailContent.append("<td align='center' width='70%'>" + ioEmpNm + "</td>");
                mailContent.append("</tr>");

                mailContent.append("<tr>");
                mailContent.append("<td align='center' bgcolor='#F2CB61' width='30%'><b>등록일시</b></td>");
                mailContent.append("<td align='center' width='70%'>" + formattedDate + "</td>");
                mailContent.append("</table>");

                mailing.sendMail(title,
                    mailing.applyMailTemplate(title, mailContent.toString(), "?SCHEMAID=SCRT_RESTRICT_IOPASS"), emailAddr, ioEmpId, "", "", acIp);

                repository.updateRegularPassRestrictRestYn(passApplNo); // IO_PASS.REST_YN 를 'Y'로 변경
                repository.insertRegularPassRestrictHist(passApplNo); // 제재이력정보 INSERT
            }

            repository.updateRegularPassRestrictRestChk(passApplNo); // IO_PASS.REST_CHK 를 'Y'로 변경
            /* [END] 윤리경영팀에게 제재대상관련메일 발송 및 IO_PASS_REST_HIST 테이블에 INSERT : 2015-07-30 by JSH */

        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return restYn;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Boolean approvalRegularPass(Map<String, Object> paramMap) {
        Boolean result = false;

        try {
            ApprovalDTO approval = objectMapper.convertValue(paramMap.get("approval"), ApprovalDTO.class);
            Integer docId = null;
            String empId = objectMapper.convertValue(paramMap.get("empId"), String.class);

            // 결재선이 있는 경우만 결재 상신 (자가결재 관련 처리)
            if ((approval.getSavedRequestApproverLine() != null && approval.getSavedRequestApproverLine().size() > 0)
                || (approval.getSavedPermitApproverLine() != null && approval.getSavedPermitApproverLine().size() > 0)) {

                /**
                 * 201 ~ 214번 로직은 상시출입증 상신 시 일반업체일 때 허가부서에 이동준TL('2058144')이 들어가는 것을 방지하기 위함 로직
                 * Front 갱신이 안 된 경우를 피하기 위한 로직이니 차후 삭제해도 무방 by kwg. 220311
                 */
                String subcontYn = objectMapper.convertValue(paramMap.get("subcontYn"), String.class);

                if (!"Y".equals(subcontYn) && approval.getSavedPermitApproverLine() != null) { // 일반업체
                    List<SavedApproverLineDTO> checkPermitLine = approval.getSavedPermitApproverLine();
                    List<SavedApproverLineDTO> newPermitApproverLine = new ArrayList<>();

                    for (SavedApproverLineDTO item : checkPermitLine) {
                        if (!"2058144".equals(item.getEmpId())) {
                            newPermitApproverLine.add(item);
                        }
                    }

                    approval.setSavedPermitApproverLine(newPermitApproverLine);
                }

                // ================= NOTE: [통합결재정보] 저장 시작 =======================
                Map<String, Object> htmlMap = objectMapper.convertValue(paramMap.get("htmlMap"), HashMap.class);

                approval.setLid(Integer.parseInt(String.valueOf(paramMap.get("passApplNo"))));
                approval.setHtmlMap(htmlMap);
                log.info(">>>> approvalRegularPass approval setLid: " + approval);

                ApprovalDocDTO approvalDoc = approvalService.insertApproval(approval);
                docId = approvalDoc.getDocId();
                paramMap.put("docId", docId);

                paramMap.put("applStat", "Z0331002"); // 결재 접수완료
                paramMap.put("status", "A0091001"); // 출입증 발급 상태

                // 업데이트
                paramMap.put("modBy", empId);
                repository.updateRegularPass(paramMap);

                // ================= NOTE: [통합결재정보] 저장 종료 =======================
            }
            else {

                // 상시 출입증 자가결재 처리
                paramMap.put("applStat", "Z0331005"); // 결재 승인
                paramMap.put("status", "A0091002"); // 출입증 발급 상태
                this.approveRegularPass(paramMap);
            }

            // ================= NOTE: 메일 발송 시작 =======================
            String title = "[행복한 만남, SK 하이닉스]신청하신 건이 접수되었습니다.";
            String ioEmpNm = objectMapper.convertValue(paramMap.get("ioEmpNm"), String.class);
            String tagGbnNm = objectMapper.convertValue(paramMap.get("tagGbnNm"), String.class);
            String to = objectMapper.convertValue(paramMap.get("ioEmailAddr"), String.class);
            String schemaNm = approval.getSchemaNm();
            String acIp = objectMapper.convertValue(paramMap.get("acIp"), String.class);

            mailing.sendMail(title,
                mailing.applyMailTemplateExt(title, ioEmpNm + "님께서 신청하신 건(상시출입증(" + tagGbnNm + ") 신청)이 접수되었습니다."),
                to, empId, schemaNm, docId == null
                    ? "자가결재"
                    : String.valueOf(docId), acIp);
            // ================= NOTE: 메일 발송 종료 =======================

            result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Map<String, Object> approveRegularPass(Map<String, Object> paramMap) {
        Map<String, Object> result = new HashMap<>();
        Integer passApplNo = objectMapper.convertValue(paramMap.get("passApplNo"), Integer.class);

        try {
            if (passApplNo != null) {

                // 출입증 정보 조회
                Map<String, Object> passInfoMapOrg = repository.selectPassInfoViewForIDCardIF(passApplNo);

                // 일반 Map에 설정
                Map<String, Object> passInfoMap = new HashMap<String, Object>();
                passInfoMap.putAll(passInfoMapOrg);

                if (passInfoMapOrg == null) {
                    log.error("approveRegularPass >> selectPassInfoViewForIDCardIF 에서 조회된 건이 없음!!");
                    result.put("IF_RESULT", "F");
                    return result;
                }

                String applyGbn = objectMapper.convertValue(passInfoMap.get("applyGbn"), String.class);

                // 기간 연장일 경우 이전 출입시작일과 이전 출입종료일이 들어가야 함.
                if ("A0061003".equals(applyGbn)) {
                    // 출입증 기존 출입일자 정보 조회
                    Map<String, Object> passDateMap = repository.selectPassOldIODate(passApplNo);
                    passInfoMap.put("cardNo", passDateMap.get("cardNo"));
                    passInfoMap.put("oldStartDate", passDateMap.get("oldStartDate"));
                    passInfoMap.put("oldEndDate", passDateMap.get("oldEndDate"));
                }

                boolean isProd = environment.acceptsProfiles(Profiles.of("prd"));
                boolean isUpload = false;
                String fileName = "";

                String attach1Name = objectMapper.convertValue(passInfoMap.get("attach1Name"), String.class);

                if (attach1Name != null) { // 운영환경
                    int pos = attach1Name.lastIndexOf(";");
                    attach1Name = attach1Name.substring(0, pos);

                    pos = attach1Name.lastIndexOf(".");
                    String fileExe = attach1Name.substring(pos);
                    fileName = "SKHYNIX_" + paramMap.get("passApplNo") + fileExe;

                    InputStream is = null;

                    log.info("[START] RegularPass :: Call up the image");
                    try {
                        // Welcome에 업로드된 이미지를 File 객체로 변환
                        URL url = new URL(securityExtnetUrl + "/" + attach1Name.replace("\\", "/"));
                        URLConnection conn = url.openConnection();
                        conn.setConnectTimeout(1000 * 5);
                        conn.setReadTimeout(1000 * 5);
                        is = conn.getInputStream();
                    } catch (Exception e) {
                        log.error(e.toString());
                    }
                    log.info("[END] RegularPass :: Call up the image");

                    // 파일업로드
                    FTPUtil ftpUtil = new FTPUtil(ftpIp, ftpPort, ftpId, ftpPwd);

                    if (isProd) {
                        isUpload = is != null
                            ? ftpUtil.uploadFileByInputStream(FTP_UPLOAD_DIR, is, fileName)
                            : false;
                    }
                    else {
                        isUpload = true;
                    }

                    if (isUpload) {
                        log.debug("@@@@@@@@@@@@ IDCARD 파일({}) FTP 업로드 성공 @@@@@@@@@@@@", fileName);
                    }
                    else {
                        log.debug("@@@@@@@@@@@@ IDCARD 파일({}) FTP 업로드 실패 @@@@@@@@@@@@", fileName);
                    }
                }

                // 탈퇴회원이 아닌 경우에만 I/F 하도록 함 : 2015-11-02 by JSH
                // =======================================================================================================//
                // STOP_DENY_YN 이 'N'인 경우에만 I/F 하도록 함 : 2016-08-18 by JSH
                // 신규 or 재발급 (A0061001, A0061002)는 IO_EMP_DENY 테이블에서 검사
                // 기간연장(A0061003)은 IO_PASS_EXPR 테이블에서 검사햐여 I/F 할 지 안할지 결정한다.
                // =======================================================================================================//

                String delYn = objectMapper.convertValue(passInfoMap.get("delYn"), String.class);
                String stopDenyYn = objectMapper.convertValue(passInfoMap.get("stopDenyYn"), String.class);

                if ("N".equals(delYn) && "N".equals(stopDenyYn)) {
                    // 파일 업로드가 성공할 경우 I/F 테이블에 insert
                    if (isUpload) {

                        // 파일 업로드가 성공할 경우 I/F 테이블에 insert
                        passInfoMap.put("applNo", passApplNo);
                        passInfoMap.put("schemaNm", "PASS");
                        passInfoMap.put("status", "10");
                        passInfoMap.put("attach1Name", fileName);

                        commonRepository.insertIfIdcard(passInfoMap);

                        passInfoMap.put("resideName", ""); /* RESIDE_NAME이 없으면 에러가 발생되어서 null로 세팅해서 넘겨줌 : 2015-10-23 by JSH 추가 */

                        // 임시로 처리함  상시출입증 신청정상하 시 제거
                        // 강동구 과장 요청으로 재발급 시 태그구분 null로 던짐 2016-11-23
                        // 출입증 , Smart Tag , 출입증+SmartTag 진행으로 인하여 TagGBN이 필요함 20170109 HSK
                        //if("재발급".equals(requestData.getField("ISSUE_TYPE"))){
                        //	requestData.putField("TAG_GBN", "");
                        //}

                        log.info("==========>>>>> 하이스텍(IDcard) 상시출입증 신청 I/F 호출 <<<<<==========");
                        log.info("paramMap = {}", passInfoMap.toString());
                        log.info("==========>>>>> 하이스텍(IDcard) 상시출입증 신청 I/F 호출 <<<<<==========");

                        if (isProd) {
                            // dbInsert("dmIDCardIF_HEIF_Entrance_Info_Insert", requestData.getFieldMap(), "IDcard", onlineCtx); /* 통합사번 : 2015-10-23 by JSH 추가 */
                            idcardRepository.insertIdcardIfHeifEntranceInfo(passInfoMap);
                        }
                        else {
                            log.info("execute idcardRepository.insertIdcardIfHeifEntranceInfo: {}", passInfoMap.toString());
                        }
                    }
                }
                else {
                    log.debug("@@@@@@@@@@@@ STOP_DENY_YN = {} @@@@@@@@@@@@", stopDenyYn);
                    log.debug("@@@@@@@@@@@@ 탈퇴회원 OR 출입증정지 OR 출입제한 상태이므로 I/F 하지 않도록 합니다. @@@@@@@@@@@@", stopDenyYn);
                }

                // update table
                String empId = objectMapper.convertValue(paramMap.get("empId"), String.class);
                paramMap.put("modBy", empId);
                repository.updateRegularPass(paramMap);

                result.put("IF_RESULT", isUpload
                    ? "S"
                    : "F");
            }
            else {
                log.error("passApplNo is null");
                result.put("IF_RESULT", "F");
                return result;
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Boolean rejectRegularPass(Map<String, Object> paramMap) {
        int resultCnt = 0;

        try {
            // update table
            String empId = objectMapper.convertValue(paramMap.get("empId"), String.class);
            paramMap.put("status", "A0091008");
            paramMap.put("delYn", "Y");
            paramMap.put("applStat", "Z0331003");
            paramMap.put("modBy", empId);
            resultCnt = repository.updateRegularPass(paramMap);

            // ================= NOTE: 메일 발송 시작 =======================
            String title = "[행복한 만남, SK 하이닉스]신청하신 건이 반려되었습니다.";
            String ioEmpNm = objectMapper.convertValue(paramMap.get("ioEmpNm"), String.class);
            String tagGbnNm = objectMapper.convertValue(paramMap.get("tagGbnNm"), String.class);
            String to = objectMapper.convertValue(paramMap.get("ioEmailAddr"), String.class);
            String schemaNm = objectMapper.convertValue(paramMap.get("schemaNm"), String.class);
            String cancelRsn = objectMapper.convertValue(paramMap.get("cancelRsn"), String.class);
            String acIp = objectMapper.convertValue(paramMap.get("acIp"), String.class);

            String content = ioEmpNm + "님께서 신청하신 건(상시출입증(" + tagGbnNm + "))이 반려되었습니다.";
            content += "<b>반려사유</b><br /> " + cancelRsn.replace("\n", "<br />");

            mailing.sendMail(title, mailing.applyMailTemplateExt(title, content), to, empId, schemaNm, "", acIp);
            // ================= NOTE: 메일 발송 종료 =======================

            // ================= NOTE: SMS(kakao) 발송 시작 =======================
            Integer passApplNo = objectMapper.convertValue(paramMap.get("passApplNo"), Integer.class);
            Map<String, Object> resultMap = commonRepository.selectPassReceipt(passApplNo);

            String ioHpNo = objectMapper.convertValue(resultMap.get("ioHpNo"), String.class);
            String coHpNo = objectMapper.convertValue(resultMap.get("coHpNo"), String.class);

            // 주석처리 2023-06-09
            //			RequestWrapModel<KakaoMessageDTO> wrapParams = new RequestWrapModel<>();
            //			KakaoMessageDTO kakaoMessageDTO = new KakaoMessageDTO();
            //			kakaoMessageDTO.setKTemplateCode("SJT_066373");
            //			kakaoMessageDTO.setSubject("출입증 신청 반려");
            //			kakaoMessageDTO.setDstaddr(ioHpNo != null ? ioHpNo.replace("-", "") : null);
            //			kakaoMessageDTO.setCallback(coHpNo != null ? coHpNo.replace("-", "") : null);
            //			kakaoMessageDTO.setText("출입증 신청이 반려되었습니다.-SK hynix-");
            //			kakaoMessageDTO.setText2("출입증 신청이 반려되었습니다.-SK hynix-");
            //			kakaoMessageDTO.setKAttach("");
            //			kakaoMessageDTO.setEmpId(empId);
            //
            //			wrapParams.setParams(kakaoMessageDTO);
            //			commonApiClient.sendKakaoMessage(wrapParams);
            // ================= NOTE: SMS(kakao) 발송 종료 =======================

        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt > 0;
    }

    @Override
    public List<Map<String, Object>> selectRegularPassMngList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            boolean isprd = environment.acceptsProfiles(Profiles.of("prd")); // prd

            log.info("[START] selectRegularPassMngList");
            resultList = repository.selectRegularPassMngList(paramMap);

            if (resultList != null) {
                StringBuilder sb = new StringBuilder();
                for (Map<String, Object> item : resultList) {
                    if (item.get("cardNo") != null && !"".equals(String.valueOf(item.get("cardNo")))) {
                        sb.append("," + item.get("cardNo"));
                    }
                }

                String cardNoStr = sb.toString();
                if (sb.length() > 0) {
                    cardNoStr = cardNoStr.substring(1);
                }

                // I/F >>> 이천 상시출입증만 조회 가능 : 하이스텍과 I/F (MS-SQL임)
                // IRecordSet rs = dbSelect("dmPassReceiptMngListGetLastCnt", requestData.getFieldMap(), "_IDcard_Visit", onlineCtx);
                // exec usP_USER_Count_2Month @u_INPUTDATA=#CARD_NO_STR#
                log.info("==========>>>>> 하이스텍 I/F 호출(상시출입증 관리) <<<<<==========");
                log.info("CARD_NO_STR = {}", cardNoStr);
                log.info("Query = {}", "exec usP_USER_Count_2Month @u_INPUTDATA=" + cardNoStr);
                log.info("==========>>>>> 하이스텍 I/F 호출(상시출입증 관리) <<<<<==========");

                List<Map<String, String>> ifList = null;
                if (isprd) {
                    ifList = idcardVisitRepository.selectPassReceiptMngListGetLastCnt(cardNoStr);
                }

                // resultList 와 I/F 조회 결과 merge
                if (ifList != null) {
                    for (Map<String, Object> itemMap : resultList) {
                        if (itemMap.get("idcardId") != null) {
                            for (Map<String, String> ifMap : ifList) {
                                if (ifMap.get("usercard") != null && itemMap.get("idcardId").equals(ifMap.get("usercard"))) {
                                    itemMap.put("LAST_DATE_TIME_STR", ifMap.get("lastdatetime"));
                                    itemMap.put("PASS_COUNT", ifMap.get("ncount"));
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            log.info("[END] selectRegularPassMngList");
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Integer selectRegularPassMngListCnt(Map<String, Object> paramMap) {
        Integer resultCnt = null;

        try {
            resultCnt = repository.selectRegularPassMngListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

    @Override
    public Map<String, Object> selectRegularPassMng(Integer passApplNo) {
        Map<String, Object> result = null;

        try {
            result = repository.selectRegularPassMng(passApplNo);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> selectRegularPassMngChangeHistory(String cardNo) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectRegularPassMngChangeHistory(cardNo);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Boolean expiredRegularPassMng(Map<String, Object> paramMap) {
        Boolean result = true;

        try {
            // 출입증번호로 강제만료하기 위한 정보 조회
            Map<String, String> infoMap = repository.selectRegularPassMngExpireInfo(paramMap);

            if (infoMap != null) {
                paramMap.putAll(infoMap);

                String exprGbn = "A0460001";
                String ioEmpId = infoMap.get("ioEmpId");

                paramMap.put("exprGbn", exprGbn);
                paramMap.put("ioEmpId", ioEmpId);

                // IO_PASS_MAIL_HIST 테이블 삭제
                repository.deletePassRemoveMailHist(ioEmpId);

                // IO_PASS 테이블 강제만료 필드 업데이트
                repository.updatePassReceiptMngExpireInfo(paramMap);

                // IO_PASS_EXPR_HIST 테이블 등록 (등록 성공 시 paramMap에 exprApplNo 담김)
                repository.insertPassInsPassExprHist(paramMap);

                paramMap.put("sCGbn", "S"); // 정지
                paramMap.put("exprCode", exprGbn); // 구성원 강제만료

                log.info("==========>>>>> 하이스텍 정지신청 I/F 호출 <<<<<==========");
                log.info("EXPR_APPL_NO = {}", paramMap.get("exprApplNo"));
                log.info("S_C_GBN = {}", paramMap.get("sCGbn"));
                log.info("IDCARD_ID = {}", paramMap.get("idcardId"));
                log.info("EXPR_CODE = {}", paramMap.get("exprCode"));

                boolean isProd = environment.acceptsProfiles(Profiles.of("prd"));
                if (isProd) {
                    // dbExecuteProcedure("dmPassExcptIF_IoPassExprHist", requestData.getFieldMap(),"_IDcard_Visit", onlineCtx);
                    idcardVisitRepository.procedurePassExcptIFIoPassExprHist(paramMap);
                }

                log.info("==========>>>>> 하이스텍 정지신청 I/F 호출 <<<<<==========");

                // ================= NOTE: 메일 발송 시작 =======================
                String title = "[행복한 만남, SK 하이닉스] 상시출입증이 정지되었습니다. 반납해주시기 바랍니다.";
                String content = infoMap.get("empNm") + "님의 상시출입증이 최근 출입횟수가 부족 or 출입 목적이 종료되어 정지되었습니다.<br />";
                content += "※ 보안서약서 내용 中<br />";
                content += "\"10. 기간이 만료된 경우 즉시 반납하겠습니다.\"라는 조항을 준수해주시기 바랍니다<br />";

                String to = infoMap.get("emailAddr");
                String schemaNm = "";
                String acIp = objectMapper.convertValue(paramMap.get("acIp"), String.class);

                mailing.sendMail(title, mailing.applyMailTemplateExt(title, content), to, ioEmpId, schemaNm, "", acIp);
                // ================= NOTE: 메일 발송 종료 =======================

            }
            else {
                return false;
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Boolean extendRegularPassMng(Map<String, Object> paramMap) {
        Boolean result = true;

        try {
            Integer passApplNo = objectMapper.convertValue(paramMap.get("passApplNo"), Integer.class);
            Map<String, Object> newPassDateMap = repository.selectRegularPassMngExtInfo(passApplNo);
            paramMap.putAll(newPassDateMap);

            repository.insertRegularPassMngExtension(paramMap);
            repository.insertRegularPassMngExtn(paramMap);
            repository.insertRegularPassMngExtnIdcard(paramMap);

            Map<String, Object> passInfoMap = repository.selectPassInfoViewForIDCardIF(passApplNo);

            if (passInfoMap != null) {
                paramMap.putAll(passInfoMap);

                log.info("==========>>>>> 하이스텍(IDcard) 출입증 강제만료 I/F 호출 <<<<<==========");
                log.info("TAG_GBN = {}", paramMap.get("tagGbn"));
                log.info("SM_TAG_ID = {}", paramMap.get("smTagId"));
                log.info("EMAIL_ADDR = {}", paramMap.get("emailAddr"));
                log.info("==========>>>>> 하이스텍(IDcard) 출입증 I/F 호출 <<<<<==========");

                boolean isProd = environment.acceptsProfiles(Profiles.of("prd"));
                if (isProd) {
                    // dbInsert("dmPassReceiptMngExtnIdcardInsert2", passInfoMap, "IDcard", onlineCtx); /* 통합사번 : 2015-10-23 by JSH 추가 */
                    idcardRepository.insertPassReceiptMngExtnIdcard(paramMap);
                }
                else {
                    log.info("execute idcardRepository.insertPassReceiptMngExtnIdcard: {}", paramMap.toString());
                }
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> selectRegularPassExcptList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectRegularPassExcptList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Integer selectRegularPassExcptListCnt(Map<String, Object> paramMap) {
        Integer resultCnt = null;

        try {
            resultCnt = repository.selectRegularPassExcptListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

    @Override
    public Map<String, Object> selectRegularPassExcptDetail(Integer excptApplNo) {
        Map<String, Object> result = null;

        try {
            result = repository.selectRegularPassExcptDetail(excptApplNo);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Map<String, Object> selectRegularPassExcpt(Integer excptApplNo) {
        Map<String, Object> result = null;

        try {
            result = repository.selectRegularPassExcpt(excptApplNo);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Boolean approvalRegularPassExcpt(Map<String, Object> paramMap) {
        Boolean result = false;

        try {
            ApprovalDTO approval = objectMapper.convertValue(paramMap.get("approval"), ApprovalDTO.class);
            Integer docId = null;

            // ================= NOTE: [통합결재정보] 저장 시작 =======================
            Map<String, Object> htmlMap = objectMapper.convertValue(paramMap.get("htmlMap"), HashMap.class);

            approval.setLid(Integer.parseInt(String.valueOf(paramMap.get("excptApplNo"))));
            approval.setHtmlMap(htmlMap);
            log.info(">>>> approvalRegularPassExcpt approval setLid: " + approval);

            ApprovalDocDTO approvalDoc = approvalService.insertApproval(approval);
            docId = approvalDoc.getDocId();
            log.info("사전정지예외신청현황 DOC_ID : {}", docId);
            // ================= NOTE: [통합결재정보] 저장 종료 =======================

            // update table
            String empId = objectMapper.convertValue(paramMap.get("empId"), String.class);
            paramMap.put("docId", docId);
            paramMap.put("applStat", "Z0331002"); // 결재 접수완료
            paramMap.put("modBy", empId);
            log.info("사전정지예외신청현황상신파라미터: {}", paramMap.toString());
            repository.updateRegularPassExcpt(paramMap);

            // ================= NOTE: 메일 발송 시작 =======================
            String title = "[행복한 만남, SK 하이닉스]신청하신 건이 접수되었습니다.";
            String ioEmpNm = objectMapper.convertValue(paramMap.get("ioEmpNm"), String.class);
            String to = objectMapper.convertValue(paramMap.get("emailAddr"), String.class);
            String schemaNm = approval.getSchemaNm();
            String acIp = objectMapper.convertValue(paramMap.get("acIp"), String.class);

            mailing.sendMail(title,
                mailing.applyMailTemplateExt(title, ioEmpNm + "님께서 신청하신 건(상시출입증 사전 정지예외 신청)이 접수되었습니다."),
                to, empId, schemaNm, String.valueOf(docId), acIp);
            // ================= NOTE: 메일 발송 종료 =======================

            result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Boolean rejectRegularPassExcpt(Map<String, Object> paramMap) {
        int resultCnt = 0;

        try {
            // 업데이트
            String empId = objectMapper.convertValue(paramMap.get("empId"), String.class);
            paramMap.put("applStat", "Z0331003");
            paramMap.put("modBy", empId);
            resultCnt = repository.updateRegularPassExcpt(paramMap);

            // ================= NOTE: 메일 발송 시작 =======================
            String title = "[행복한 만남, SK 하이닉스]신청하신 건이 반려되었습니다.";
            String ioEmpNm = objectMapper.convertValue(paramMap.get("ioEmpNm"), String.class);
            String to = objectMapper.convertValue(paramMap.get("emailAddr"), String.class);
            String schemaNm = objectMapper.convertValue(paramMap.get("schemaNm"), String.class);
            String cancelRsn = objectMapper.convertValue(paramMap.get("cancelRsn"), String.class);
            String acIp = objectMapper.convertValue(paramMap.get("acIp"), String.class);

            String content = ioEmpNm + "님께서 신청하신 건(상시출입증 정지 예외신청)이 반려되었습니다.";
            content += "<b>반려사유</b><br /> " + cancelRsn.replace("\n", "<br />");

            mailing.sendMail(title, mailing.applyMailTemplateExt(title, content), to, empId, schemaNm, "", acIp);
            // ================= NOTE: 메일 발송 종료 =======================

            // ================= NOTE: SMS(kakao) 발송 시작 =======================
            Integer excptApplNo = objectMapper.convertValue(paramMap.get("excptApplNo"), Integer.class);
            Map<String, Object> resultMap = repository.selectRegularPassExcpt(excptApplNo);

            String ioHpNo = objectMapper.convertValue(resultMap.get("ioHpNo"), String.class);
            String coHpNo = objectMapper.convertValue(resultMap.get("coHpNo"), String.class);

            if (ioHpNo != null && coHpNo != null) {
                String message = "상시출입증 정지 예외신청이 반려되었습니다.-SK hynix-";

                // 주석처리 2023-06-09
                //				RequestWrapModel<KakaoMessageDTO> wrapParams = new RequestWrapModel<>();
                //				KakaoMessageDTO kakaoMessageDTO = new KakaoMessageDTO();
                //				kakaoMessageDTO.setKTemplateCode("SJT_066375");
                //				kakaoMessageDTO.setSubject("상시출입증 관련신청 승인 반려");
                //				kakaoMessageDTO.setDstaddr(ioHpNo != null ? ioHpNo.replace("-", "") : "");
                //				kakaoMessageDTO.setCallback(coHpNo != null ? coHpNo.replace("-", "") : "");
                //				kakaoMessageDTO.setText(message);
                //				kakaoMessageDTO.setText2(message);
                //				kakaoMessageDTO.setKAttach("");
                //				kakaoMessageDTO.setEmpId(empId);
                //
                //				wrapParams.setParams(kakaoMessageDTO);
                //				commonApiClient.sendKakaoMessage(wrapParams);
            }
            // ================= NOTE: SMS(kakao) 발송 종료 =======================

        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt > 0;
    }

    @Override
    public List<Map<String, Object>> selectRegularPassCancelList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectRegularPassCancelList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Integer selectRegularPassCancelListCnt(Map<String, Object> paramMap) {
        Integer resultCnt = null;

        try {
            resultCnt = repository.selectRegularPassCancelListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

    @Override
    public Map<String, Object> selectRegularPassCancel(Integer excptApplNo) {
        Map<String, Object> result = null;

        try {
            result = repository.selectRegularPassCancel(excptApplNo);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Boolean approvalRegularPassCancel(Map<String, Object> paramMap) {
        Boolean result = false;

        try {
            ApprovalDTO approval = objectMapper.convertValue(paramMap.get("approval"), ApprovalDTO.class);
            Integer docId = null;

            // 결재선이 있는 경우만 결재 상신 (자가결재 없음)
            if ((approval.getSavedRequestApproverLine() != null && approval.getSavedRequestApproverLine().size() > 0)
                || (approval.getSavedPermitApproverLine() != null && approval.getSavedPermitApproverLine().size() > 0)) {

                // ================= NOTE: [통합결재정보] 저장 시작 =======================
                Map<String, Object> htmlMap = objectMapper.convertValue(paramMap.get("htmlMap"), HashMap.class);

                approval.setLid(Integer.parseInt(String.valueOf(paramMap.get("cancelApplNo"))));
                approval.setHtmlMap(htmlMap);
                log.info(">>>> approvalRegularPass approval setLid: " + approval);

                ApprovalDocDTO approvalDoc = approvalService.insertApproval(approval);
                docId = approvalDoc.getDocId();
                paramMap.put("docId", docId);
                // ================= NOTE: [통합결재정보] 저장 종료 =======================

                // update table
                String empId = objectMapper.convertValue(paramMap.get("empId"), String.class);
                paramMap.put("applStat", "Z0331002"); // 결재 접수완료
                paramMap.put("modBy", empId);
                log.info("사후정지해지신청현황상신파라미터: {}", paramMap.toString());
                repository.updateRegularPassCancel(paramMap);

                // ================= NOTE: 메일 발송 시작 =======================
                String title = "[행복한 만남, SK 하이닉스]신청하신 건이 접수되었습니다.";
                String ioEmpNm = objectMapper.convertValue(paramMap.get("ioEmpNm"), String.class);
                String to = objectMapper.convertValue(paramMap.get("ioEmailAddr"), String.class);
                String schemaNm = approval.getSchemaNm();
                String acIp = objectMapper.convertValue(paramMap.get("acIp"), String.class);

                mailing.sendMail(title,
                    mailing.applyMailTemplateExt(title, ioEmpNm + "님께서 신청하신 건(상시출입증 사후 정지해지 신청)이 접수되었습니다."),
                    to, empId, schemaNm, String.valueOf(docId), acIp);
                // ================= NOTE: 메일 발송 종료 =======================

                result = true;
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Boolean rejectRegularPassCancel(Map<String, Object> paramMap) {
        int resultCnt = 0;

        try {
            // 업데이트
            String empId = objectMapper.convertValue(paramMap.get("empId"), String.class);
            paramMap.put("applStat", "Z0331003");
            paramMap.put("modBy", empId);
            resultCnt = repository.updateRegularPassCancel(paramMap);

            // ================= NOTE: 메일 발송 시작 =======================
            String title = "[행복한 만남, SK 하이닉스]신청하신 건이 반려되었습니다.";
            String ioEmpNm = objectMapper.convertValue(paramMap.get("ioEmpNm"), String.class);
            String to = objectMapper.convertValue(paramMap.get("ioEmailAddr"), String.class);
            String schemaNm = objectMapper.convertValue(paramMap.get("schemaNm"), String.class);
            String cancelRsn = objectMapper.convertValue(paramMap.get("cancelRsn"), String.class);
            String acIp = objectMapper.convertValue(paramMap.get("acIp"), String.class);

            String content = ioEmpNm + "님께서 신청하신 건(상시출입증 정지 해지신청)이 반려되었습니다.";
            content += "<b>반려사유</b><br /> " + cancelRsn.replace("\n", "<br />");

            mailing.sendMail(title, mailing.applyMailTemplateExt(title, content), to, empId, schemaNm, "", acIp);
            // ================= NOTE: 메일 발송 종료 =======================

            // ================= NOTE: SMS(kakao) 발송 시작 =======================
            Integer cancelApplNo = objectMapper.convertValue(paramMap.get("cancelApplNo"), Integer.class);
            Map<String, Object> resultMap = repository.selectRegularPassCancel(cancelApplNo);

            String ioHpNo = objectMapper.convertValue(resultMap.get("oriIoHpNo"), String.class);
            String coHpNo = objectMapper.convertValue(resultMap.get("coHpNo"), String.class);

            // 주석처리 2023-06-09
            //			RequestWrapModel<KakaoMessageDTO> wrapParams = new RequestWrapModel<>();
            //			KakaoMessageDTO kakaoMessageDTO = new KakaoMessageDTO();
            //			kakaoMessageDTO.setKTemplateCode("SJT_066375");
            //			kakaoMessageDTO.setSubject("상시출입증 관련신청 승인 반려");
            //			kakaoMessageDTO.setDstaddr(ioHpNo != null ? ioHpNo.replace("-", "") : null);
            //			kakaoMessageDTO.setCallback(coHpNo != null ? coHpNo.replace("-", "") : null);
            //			kakaoMessageDTO.setText("상시출입증 정지 해지신청이 반려되었습니다.-SK hynix-");
            //			kakaoMessageDTO.setText2("상시출입증 정지 해지신청이 반려되었습니다.-SK hynix-");
            //			kakaoMessageDTO.setKAttach("");
            //			kakaoMessageDTO.setEmpId(empId);
            //
            //			wrapParams.setParams(kakaoMessageDTO);
            //			commonApiClient.sendKakaoMessage(wrapParams);
            // ================= NOTE: SMS(kakao) 발송 종료 =======================

        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt > 0;
    }

    @Override
    public List<Map<String, Object>> selectInsSubcontMoveList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectInsSubcontMoveList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Integer selectInsSubcontMoveListCnt(Map<String, Object> paramMap) {
        Integer resultCnt = null;

        try {
            resultCnt = repository.selectInsSubcontMoveListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

    @Override
    public Map<String, Object> selectInsSubcontMove(Integer moveApplNo) {
        Map<String, Object> result = null;

        try {
            result = repository.selectInsSubcontMove(moveApplNo);

            // 변경할 인력 조회
            List<Map<String, Object>> moveManList = repository.selectInsSubcontMoveManList(moveApplNo);
            result.put("moveManList", moveManList);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Boolean approveInsSubcontMove(Map<String, Object> paramMap) {
        Boolean result = false;

        try {
            paramMap.put("siteType", "HN"); // 도급업체 변경 신청은 현재 SKHYNIX 본사에서만 처리 가능함.

            if (paramMap.get("movemanlist") != null) {
                List<HashMap<String, Object>> manList = objectMapper.convertValue(paramMap.get("movemanlist"), List.class);
                paramMap.remove("movemanlist");

                String passYn = null;
                String ioEmpIdMan = null;

                Map<String, Object> ifDataMap = null;
                Map<String, Object> passApplMap = new HashMap<>();
                Map<String, Object> passInfoMap = new HashMap<>();
                Map<String, Object> ioPassInfoMap = null;
                Map<String, Object> ifEntranceMap = new HashMap<>();

                FTPUtil ftpUtil = new FTPUtil(ftpIp, ftpPort, ftpId, ftpPwd);
                boolean isUpload = false;
                String attach1Name = null;
                String fileExe = null;
                int pos = 0;
                String fileName = null;

                URL url = null;
                URLConnection conn = null;
                InputStream is = null;

                boolean isProd = environment.acceptsProfiles(Profiles.of("prd"));

                for (HashMap<String, Object> itemMap : manList) {
                    itemMap.putAll(paramMap);

                    repository.updateInsSubcontMove(itemMap);
                    repository.updateIoEmpCompId(itemMap);
                    repository.updateIoInoutCompId(itemMap); // 업체물품 진행중인 Data도 업체 변경함

                    try {
                        //						esecurityHsRepository.updateIoEmpCompId(itemMap);
                        //						esecurityHsRepository.updateIoInoutCompId(itemMap);
                    } catch (Exception e) {
                        log.error("[Hystec] approveInsSubcontMove Error: {}", e.toString());
                    }

                    try {
                        esecuritySiRepository.updateIoEmpCompId(itemMap);
                        esecuritySiRepository.updateIoInoutCompId(itemMap);
                    } catch (Exception e) {
                        log.error("[SystemIc] approveInsSubcontMove Error: {}", e.toString());
                    }

                    passYn = objectMapper.convertValue(itemMap.get("passYn"), String.class);

                    if ("Y".equals(passYn)) {

                        // NEW_PASS_APPL_NO & OLD_PASS_APPL_NO 조회
                        passApplMap = repository.selectInsSubcontMovePassApplNo(itemMap);

                        if (passApplMap != null) {
                            itemMap.put("newPassApplNo", objectMapper.convertValue(passApplMap.get("newPassApplNo"), Integer.class));
                            itemMap.put("oldPassApplNo", objectMapper.convertValue(passApplMap.get("oldPassApplNo"), Integer.class));
                        }

                        // IO_PASS 정보 조회 (조회 결과 paramMap에 저장)
                        ioPassInfoMap = repository.selectIoPassInfo(itemMap);

                        if (ioPassInfoMap != null) {
                            itemMap.put("tagGbn", passInfoMap.get("tagGbn"));
                            itemMap.put("smTagId", passInfoMap.get("smTagId"));
                            itemMap.put("emailAddr", passInfoMap.get("emailAddr"));
                        }

                        // Insert IO_PASS
                        repository.insertSubcontMoveIoPass(itemMap);

                        // Insert IF_IDCARD
                        repository.insertSubcontMoveIfIdcard(itemMap);

                        // 출입증 정보 조회
                        ifDataMap = repository.selectSubcontMoveIfEntrance(itemMap);
                        if (ifDataMap != null) {
                            itemMap.putAll(ifDataMap);
                        }

                        itemMap.put("tagGbn", "3");

                        attach1Name = objectMapper.convertValue(itemMap.get("attach1Name"), String.class);

                        /**
                         * 본사에서만 현재 도급 업체 변경 진행
                         * 상시출입증 개인정보 이미지를 IO_EMP 에서 가져와 FTP 업로드 진행 시작 20191119 HSK
                         * 운영에서만 TEST 가 가능하여, 실제 운영 구동시 확인 가능함.
                         */
                        if (attach1Name != null) {
                            pos = attach1Name.lastIndexOf(";");
                            attach1Name = attach1Name.substring(0, pos);
                            pos = attach1Name.lastIndexOf(".");
                            fileExe = attach1Name.substring(pos);

                            fileName = "SKHYNIX_" + itemMap.get("esSn") + fileExe;
                            itemMap.put("attach1Name", fileName);

                            is = null;

                            log.info("[START] approveInsSubcontMove :: Call up the image");
                            try {
                                // Welcome에 업로드된 이미지를 File 객체로 변환
                                url = new URL(securityExtnetUrl + "/" + attach1Name.replace("\\", "/"));
                                conn = url.openConnection();
                                conn.setConnectTimeout(1000 * 5);
                                conn.setReadTimeout(1000 * 5);
                                is = conn.getInputStream();
                            } catch (Exception e) {
                                log.error(e.toString());
                            }
                            log.info("[END] approveInsSubcontMove :: Call up the image");

                            // 파일업로드
                            if (isProd) { // 운영환경
                                isUpload = is != null
                                    ? ftpUtil.uploadFileByInputStream(FTP_UPLOAD_DIR, is, fileName)
                                    : false;
                            }
                            else {
                                isUpload = true;
                            }

                            if (isUpload) {
                                log.debug("@@@@@@@@@@@@ IDCARD 파일({}) FTP 업로드 성공 @@@@@@@@@@@@", FTP_UPLOAD_DIR + "/" + fileName);
                            }
                            else {
                                log.debug("@@@@@@@@@@@@ IDCARD 파일({}) FTP 업로드 실패 @@@@@@@@@@@@", FTP_UPLOAD_DIR + "/" + fileName);
                            }
                        }

                        repository.insertSubcontMoveIfEntranceHn(itemMap);

                        log.info("[START] ID CARD I/F :: idcardRepository.insertSubcontMoveIfEntrance");
                        log.info("parameter: {}", itemMap.toString());

                        if (isProd) {
                            // dbInsert("dmSubcontMoveIfEntranceInsert", requestDataIf.getFieldMap(), "IDcard", onlineCtx); //하이스텍 I/F 테이블에 DATA생성
                            idcardRepository.insertSubcontMoveIfEntrance(itemMap);
                        }
                        log.info("[END] ID CARD I/F :: idcardRepository.insertSubcontMoveIfEntrance");

                        ioEmpIdMan = objectMapper.convertValue(itemMap.get("ioEmpIdMan"), String.class);

                        // Hystec
                        //						try {
                        //							passYn = esecurityHsRepository.selectGetPassYn(ioEmpIdMan); // hystec
                        //
                        //							if("Y".equals(passYn)) {
                        //								itemMap.put("siteType", "HS");
                        //
                        //								passApplMap = esecurityHsRepository.selectSubcontMovePassApplNo(ioEmpIdMan);
                        //
                        //								if(passApplMap != null) {
                        //									itemMap.put("newPassApplNo", objectMapper.convertValue(passApplMap.get("newPassApplNo"), Integer.class));
                        //									itemMap.put("oldPassApplNo", objectMapper.convertValue(passApplMap.get("oldPassApplNo"), Integer.class));
                        //								}
                        //
                        //								passInfoMap = esecurityHsRepository.selectIoPassInfo(itemMap);
                        //
                        //								if(ioPassInfoMap != null) {
                        //									itemMap.put("tagGbn", passInfoMap.get("tagGbn"));
                        //									itemMap.put("smTagId", passInfoMap.get("smTagId"));
                        //									itemMap.put("emailAddr", passInfoMap.get("emailAddr"));
                        //								}
                        //
                        //								esecurityHsRepository.insertSubcontMoveIoPass(itemMap);
                        //								esecurityHsRepository.insertSubcontMoveIfIdcard(itemMap);
                        //
                        //								ifEntranceMap = esecurityHsRepository.selectSubcontMoveIfEntrance(itemMap);
                        //
                        //								if(ifEntranceMap != null) {
                        //									itemMap.putAll(ifEntranceMap);
                        //								}
                        //
                        //								itemMap.put("tagGbn", itemMap.get("tagGbn"));
                        //								itemMap.put("oldPassApplNo", itemMap.get("oldPassApplNo"));
                        //
                        //								log.debug("TAG_GBN++++++++++++++++++++++++++>>>>>", itemMap.get("tagGbn"));
                        //								log.debug("OLD_PASS_APPL_NO++++++++++++++++++++++++++>>>>>", itemMap.get("oldPassApplNo"));
                        //
                        //								log.info("[START] Hystec I/F :: idcardRepository.insertSubcontMoveIfEntrance");
                        //								log.info("parameter: {}", itemMap.toString());
                        //
                        //								if(isProd) {
                        //									try {
                        //										esecurityHsRepository.insertSubcontMoveIfEntranceHs(itemMap); // 하이스텍 I/F 테이블에 DATA생성
                        //									} catch(Exception e) {
                        //										log.error("[Hystec] insertSubcontMoveIfEntranceHs Error: {}", e.toString());
                        //									}
                        //								}
                        //
                        //								log.info("[END] Hystec I/F :: esecurityHsRepository.insertSubcontMoveIfEntranceHs");
                        //							}
                        //						} catch (Exception e) {
                        //							log.error("[Hystec] approveInsSubcontMove Error: {}", e.toString());
                        //						}

                        // SystemIC
                        try {
                            passYn = esecuritySiRepository.selectGetPassYn(ioEmpIdMan); // SystemIC

                            if ("Y".equals(passYn)) {
                                itemMap.put("siteType", "SI");

                                passApplMap = esecuritySiRepository.selectSubcontMovePassApplNo(ioEmpIdMan);

                                if (passApplMap != null) {
                                    itemMap.put("newPassApplNo", objectMapper.convertValue(passApplMap.get("newPassApplNo"), Integer.class));
                                    itemMap.put("oldPassApplNo", objectMapper.convertValue(passApplMap.get("oldPassApplNo"), Integer.class));
                                }

                                passInfoMap = esecuritySiRepository.selectIoPassInfo(itemMap);

                                if (ioPassInfoMap != null) {
                                    itemMap.put("tagGbn", passInfoMap.get("tagGbn"));
                                    itemMap.put("smTagId", passInfoMap.get("smTagId"));
                                    itemMap.put("emailAddr", passInfoMap.get("emailAddr"));
                                }

                                esecuritySiRepository.insertSubcontMoveIoPass(itemMap);
                                esecuritySiRepository.insertSubcontMoveIfIdcard(itemMap);

                                ifEntranceMap = esecuritySiRepository.selectSubcontMoveIfEntrance(itemMap);

                                if (ifEntranceMap != null) {
                                    itemMap.putAll(ifEntranceMap);
                                }

                                itemMap.put("tagGbn", "3");

                                log.info("[START] SystemIC I/F :: esecuritySiRepository.insertSubcontMoveIfEntrance");
                                log.info("parameter: {}", itemMap.toString());

                                if (isProd) {
                                    try {
                                        esecuritySiRepository.insertSubcontMoveIfEntranceSi(itemMap); // SystemIC I/F 테이블에 DATA생성
                                    } catch (Exception e) {
                                        log.error("[SystemIC] insertSubcontMoveIfEntranceHs Error: {}", e.toString());
                                    }
                                }

                                log.info("[END] SystemIC I/F :: esecuritySiRepository.insertSubcontMoveIfEntrance");
                            }
                        } catch (Exception e) {
                            log.error("[SystemIC] approveInsSubcontMove Error: {}", e.toString());
                        }
                    }
                }
            }

            // update table
            paramMap.put("applStat", "Z0331005"); // 승인완료
            repository.updateInsSubcontMove(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Boolean rejectInsSubcontMove(Map<String, Object> paramMap) {
        int resultCnt = 0;

        try {
            // 업데이트
            String empId = objectMapper.convertValue(paramMap.get("empId"), String.class);
            paramMap.put("applStat", "Z0331004");
            paramMap.put("modBy", empId);
            repository.updateInsSubcontMove(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt > 0;
    }

    @Override
    public List<Map<String, Object>> selectIoCompCoorpVendorList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectIoCompCoorpVendorList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Integer selectIoCompCoorpVendorListCnt(Map<String, Object> paramMap) {
        Integer resultCnt = null;

        try {
            resultCnt = repository.selectIoCompCoorpVendorListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

    @Override
    public Map<String, Object> selectIoCompCoorpVendor(Map<String, Object> paramMap) {
        Map<String, Object> result = null;

        try {
            result = repository.selectIoCompCoorpVendor(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Boolean approveIoCompCoorpVendor(Map<String, Object> paramMap) {
        Boolean result = false;

        try {
            String empId = objectMapper.convertValue(paramMap.get("empId"), String.class);
            Integer adminAppNo = objectMapper.convertValue(paramMap.get("adminAppNo"), Integer.class);

            paramMap.put("apprYn", "Z0331005"); // 승인완료
            paramMap.put("modBy", empId);

            repository.updateIoAdminAppl(paramMap);

            // 기본정보 조회
            Map<String, Object> ioAdminApplInfo = repository.selectIoAdminAppl(adminAppNo);
            paramMap.putAll(ioAdminApplInfo);

            // update table
            repository.updateIoComp(paramMap);

            log.info("[START] Hysetc & SystemIC I/F :: approveIoCompCoorpVendor");
            log.info("parameter: {}", paramMap.toString());

            //			try {
            //				esecurityHsRepository.updateIoComp(paramMap); // hystec
            //			} catch(Exception e) {
            //				log.error("[Hystec] updateIoComp Error: {}", e.toString());
            //			}

            try {
                esecuritySiRepository.updateIoComp(paramMap); // SystemIC
            } catch (Exception e) {
                log.error("[SystemIC] updateIoComp Error: {}", e.toString());
            }

            log.info("[END] Hysetc & SystemIC I/F :: approveIoCompCoorpVendor");

            // ================= NOTE: SMS(kakao) 발송 시작 =======================
            Map<String, Object> smsInfo = repository.selectIoAdminApplSmsInfo(adminAppNo);

            if (smsInfo != null) {
                String callbackNo = "";
                String compId = objectMapper.convertValue(paramMap.get("compId"), String.class);

                if ("1101000001".equals(compId)) {
                    callbackNo = objectMapper.convertValue(smsInfo.get("callbackNo"), String.class);
                }
                else {
                    callbackNo = "03151853344";
                }

                String smsNo = objectMapper.convertValue(smsInfo.get("smsNo"), String.class);

                // 주석처리 2023-06-09
                //				RequestWrapModel<KakaoMessageDTO> wrapParams = new RequestWrapModel<>();
                //				KakaoMessageDTO kakaoMessageDTO = new KakaoMessageDTO();
                //				kakaoMessageDTO.setKTemplateCode("SJT_048089");
                //				kakaoMessageDTO.setSubject("대표관리자 신청 승인");
                //				kakaoMessageDTO.setDstaddr(smsNo != null ? smsNo.replace("-", "") : null);
                //				kakaoMessageDTO.setCallback(callbackNo);
                //				kakaoMessageDTO.setText("대표관리자 승인 완료되었습니다. 감사합니다.-SK hynix-");
                //				kakaoMessageDTO.setText2("대표관리자 승인 완료되었습니다. 감사합니다.-SK hynix-");
                //				kakaoMessageDTO.setKAttach("");
                //				kakaoMessageDTO.setEmpId(empId);
                //
                //				wrapParams.setParams(kakaoMessageDTO);
                //				commonApiClient.sendKakaoMessage(wrapParams);
            }
            // ================= NOTE: SMS(kakao) 발송 종료 =======================

            result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Boolean rejectIoCompCoorpVendor(Map<String, Object> paramMap) {
        boolean result = false;

        try {
            String empId = objectMapper.convertValue(paramMap.get("empId"), String.class);
            String chgYn = objectMapper.convertValue(paramMap.get("chgYn"), String.class);
            paramMap.put("modBy", empId);

            log.info("[START] Hysetc & SystemIC I/F :: rejectIoCompCoorpVendor");
            log.info("parameter: {}", paramMap.toString());

            if ("Y".equals(chgYn)) {
                // 대표관리자 변경시에는 temp_admin_email 만 초기화함.
                paramMap.put("initEmail", "Y");
                repository.updateCoorpVendorAdminApprRecevieReject2(paramMap);

                //				try {
                //					esecurityHsRepository.updateCoorpVendorAdminApprRecevieReject2(paramMap); // hystec
                //				} catch(Exception e) {
                //					log.error("[Hystec] updateCoorpVendorAdminApprRecevieReject2 Error: {}", e.toString());
                //				}

                try {
                    esecuritySiRepository.updateCoorpVendorAdminApprRecevieReject2(paramMap); // SystemIC
                } catch (Exception e) {
                    log.error("[SystemIC] updateCoorpVendorAdminApprRecevieReject2 Error: {}", e.toString());
                }
            }
            else {
                paramMap.put("apprYn", "Z0331003");
                repository.updateCoorpVendorAdminApprRecevieReject(paramMap);

                //				try {
                //					esecurityHsRepository.updateCoorpVendorAdminApprRecevieReject(paramMap); // hystec
                //				} catch(Exception e) {
                //					log.error("[Hystec] updateCoorpVendorAdminApprRecevieReject2 Error: {}", e.toString());
                //				}

                try {
                    esecuritySiRepository.updateCoorpVendorAdminApprRecevieReject(paramMap); // SystemIC
                } catch (Exception e) {
                    log.error("[SystemIC] updateCoorpVendorAdminApprRecevieReject Error: {}", e.toString());
                }
            }

            log.info("[END] Hysetc & SystemIC I/F :: rejectIoCompCoorpVendor");

            paramMap.put("apprYn", "Z0331003");
            paramMap.put("modBy", empId);
            repository.updateIoAdminAppl(paramMap);

            // ================= NOTE: SMS(kakao) 발송 시작 =======================
            String ioCompId = objectMapper.convertValue(paramMap.get("ioCompId"), String.class);
            Map<String, Object> smsInfo = repository.selectCoorpVendorAdminReceiveRejectGetSMSInfo(ioCompId);

            log.info("rejectIoCompCoorpVendor :: ioCompId = {}", ioCompId);

            if (smsInfo != null) {
                String smsNo = objectMapper.convertValue(smsInfo.get("smsNo"), String.class);
                String callbackNo = objectMapper.convertValue(smsInfo.get("callbackNo"), String.class);

                log.info("rejectIoCompCoorpVendor :: smsNo = {}", smsNo);
                log.info("rejectIoCompCoorpVendor :: callbackNo = {}", callbackNo);

                // 주석처리 2023-06-09
                //				RequestWrapModel<KakaoMessageDTO> wrapParams = new RequestWrapModel<>();
                //				KakaoMessageDTO kakaoMessageDTO = new KakaoMessageDTO();
                //				kakaoMessageDTO.setKTemplateCode("SJT_048089");
                //				kakaoMessageDTO.setSubject("대표관리자 신청 반려");
                //				kakaoMessageDTO.setDstaddr(smsNo != null ? smsNo.replace("-", "") : null);
                //				kakaoMessageDTO.setCallback(callbackNo != null ? callbackNo.replace("-", "") : null);
                //				kakaoMessageDTO.setText("대표관리자 접수 반려되었습니다. 신청 정보를 확인해주십시오.-SK hynix-");
                //				kakaoMessageDTO.setText2("대표관리자 접수 반려되었습니다. 신청 정보를 확인해주십시오.-SK hynix-");
                //				kakaoMessageDTO.setKAttach("");
                //				kakaoMessageDTO.setEmpId(empId);
                //
                //				log.info("rejectIoCompCoorpVendor :: Start send kakao");
                //				wrapParams.setParams(kakaoMessageDTO);
                //				commonApiClient.sendKakaoMessage(wrapParams);
                log.info("rejectIoCompCoorpVendor :: End send kakao");
            }
            // ================= NOTE: SMS(kakao) 발송 종료 =======================

            result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Integer selectCountIdCard(Map<String, Object> paramMap) {
        Integer result = 0;

        try {
            List<Map<String, Object>> dataList = repository.selectVwIoPassMst2List(paramMap);

            if (dataList != null && dataList.size() > 0) {
                Map<String, Object> dataMap = dataList.get(0);
                String stopYn = objectMapper.convertValue(dataMap.get("stopYn"), String.class);
                String denyStatus = objectMapper.convertValue(dataMap.get("denyStatus"), String.class);

                if ("N".equals(stopYn) && "N".equals(denyStatus)) {
                    String cardNo = objectMapper.convertValue(dataMap.get("cardNo"), String.class);
                    String idcardId = objectMapper.convertValue(dataMap.get("idcardId"), String.class);

                    /* 통합사번 호출 로그 표시 */
                    log.info("@@@@@@@@@@ selectCountIdCard.EMP_ID    = {}", cardNo);
                    log.info("@@@@@@@@@@ selectCountIdCard.CARD_TYPE = {}", "N");
                    log.info("@@@@@@@@@@ selectCountIdCard.IDCARD_ID = {}", idcardId);
                    log.info("@@@@@@@@@@ DataSource = IDcard @@@@@@@@@@@@");

                    paramMap.put("empId", cardNo);
                    paramMap.put("cardType", "N");
                    paramMap.put("idcardId", idcardId);

                    boolean isProd = environment.acceptsProfiles(Profiles.of("prd"));
                    Map<String, Object> resultMap = null;

                    log.info("[START] ID CARD I/F :: idcardRepository.procedureCheckEnableRegisterBldgIC");
                    log.info("parameter: {}", paramMap.toString());

                    if (isProd) {
                        resultMap = idcardRepository.procedureCheckEnableRegisterBldgIC(paramMap);
                    }
                    log.info("[END] ID CARD I/F :: idcardRepository.procedureCheckEnableRegisterBldgIC");

                    if (resultMap != null) {
                        result = resultMap.get("cnt") != null
                            ? Integer.parseInt(String.valueOf(resultMap.get("cnt")))
                            : 0;
                    }
                }
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> selectAdmPassList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectAdmPassList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Integer selectAdmPassListCnt(Map<String, Object> paramMap) {
        Integer resultCnt = null;

        try {
            resultCnt = repository.selectAdmPassListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

    @Override
    public List<Map<String, Object>> selectAdmIoCompCoorpList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectAdmIoCompCoorpList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Integer selectAdmIoCompCoorpListCnt(Map<String, Object> paramMap) {
        Integer resultCnt = null;

        try {
            resultCnt = repository.selectAdmIoCompCoorpListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

    @Override
    public Map<String, Object> selectAdmIoCompCoorp(String ioCompId) {
        Map<String, Object> result = null;

        try {
            result = repository.selectAdmIoCompCoorp(ioCompId);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Boolean updateAdmIoCompCoorpSubcont(Map<String, Object> paramMap) {
        boolean result = false;

        try {
            repository.updateCoorpVendorInfoSubcontYn(paramMap);

            log.info("[START] Hystec & SystemIC I/F :: updateCoorpVendorInfoSubcontYn");
            log.info("parameter: {}", paramMap.toString());

            //			try {
            //				esecurityHsRepository.updateCoorpVendorInfoSubcontYn(paramMap); // hystec
            //			} catch(Exception e) {
            //				log.error("[Hystec] updateCoorpVendorInfoSubcontYn Error: {}", e.toString());
            //			}

            try {
                esecuritySiRepository.updateCoorpVendorInfoSubcontYn(paramMap); // SystemIC
            } catch (Exception e) {
                log.error("[SystemIC] updateCoorpVendorInfoSubcontYn Error: {}", e.toString());
            }

            log.info("[END] Hystec & SystemIC I/F :: updateCoorpVendorInfoSubcontYn");

            result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> selectAdmPassExpireList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectAdmPassExpireList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Integer selectAdmPassExpireListCnt(Map<String, Object> paramMap) {
        Integer resultCnt = null;

        try {
            resultCnt = repository.selectAdmPassExpireListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

    @Override
    public List<Map<String, Object>> selectAdmPassExtendList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectAdmPassExtendList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Integer selectAdmPassExtendListCnt(Map<String, Object> paramMap) {
        Integer resultCnt = null;

        try {
            resultCnt = repository.selectAdmPassExtendListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

    @Override
    public List<Map<String, Object>> selectRegularPassExcptJangList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectRegularPassExcptJangList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Integer selectRegularPassExcptJangListCnt(Map<String, Object> paramMap) {
        Integer resultCnt = null;

        try {
            resultCnt = repository.selectRegularPassExcptJangListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

    @Override
    public Map<String, Object> selectRegularPassExcptJang(Integer excptApplNo) {
        Map<String, Object> result = null;

        try {
            result = repository.selectRegularPassExcptJang(excptApplNo);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Boolean deleteRegularPassExcptJang(Map<String, Object> paramMap) {
        boolean result = false;

        try {
            String acIp = objectMapper.convertValue(paramMap.get("acIp"), String.class);

            @SuppressWarnings("unchecked")
            List<HashMap<String, Object>> dataList = objectMapper.convertValue(paramMap.get("data"), List.class);

            if (dataList != null) {
                Integer excptApplNo = null;
                boolean isProd = environment.acceptsProfiles(Profiles.of("prd"));

                for (HashMap<String, Object> item : dataList) {
                    item.put("acIp", acIp);
                    excptApplNo = objectMapper.convertValue(item.get("excptApplNo"), Integer.class);

                    if (excptApplNo != null) {
                        // delete
                        repository.deleteIoPassExcpt(excptApplNo);
                        repository.deleteIfIoPassExcpt(excptApplNo);

                        log.info("[START] Hystec I/F :: 상시출입증 장기 예외신청 삭제처리 :: idcardVisitRepository.procedurePassExcptDeleteIf");
                        log.info("excptApplNo: {}", excptApplNo);

                        if (isProd) { // 운영환경
                            // dbExecuteProcedure("dmPassExcptIF_Excpt_Delete", requestData.getFieldMap(),"_IDcard_Visit", onlineCtx);
                            idcardVisitRepository.procedurePassExcptDeleteIf(excptApplNo);
                        }

                        log.info("[END] Hystec I/F :: 상시출입증 장기 예외신청 삭제처리 :: idcardVisitRepository.procedurePassExcptDeleteIf");
                    }
                }
            }

            result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Boolean insertRegularPassExcptJang(Map<String, Object> paramMap) {
        boolean result = false;

        try {
            boolean isProd = environment.acceptsProfiles(Profiles.of("prd"));
            String acIp = objectMapper.convertValue(paramMap.get("acIp"), String.class);

            paramMap.put("acIp", acIp);

            // Insert
            repository.insertIoPassExcpt(paramMap);

            int excptApplNo = objectMapper.convertValue(paramMap.get("excptApplNo"), Integer.class);
            repository.insertIfIoPassExcpt(excptApplNo);

            // 사전 예외신청 현황 기본정보 조회
            Map<String, Object> basicInfo = repository.selectRegularPassExcptBasicInfo(excptApplNo);

            log.info("==========>>>>> 하이스텍 장기예외신청 I/F 호출 <<<<<==========");
            log.info("EXCPT_APPL_NO = {}", basicInfo.get("excptApplNo"));
            log.info("IDCARD_ID = {}", basicInfo.get("idcardId"));
            log.info("EXCPT_STRT_DT = {}", basicInfo.get("excptStrtDt"));
            log.info("EXCPT_END_DT = {}", basicInfo.get("excptEndDt"));
            log.info("==========>>>>> 하이스텍 장기예외신청 I/F 호출 <<<<<==========");

            if (isProd) {
                // dbExecuteProcedure("dmPassExcptIF_IoPassExcpt", requestData.getFieldMap(),"_IDcard_Visit", onlineCtx);
                idcardVisitRepository.procedurePassExcptIoPassExcptIf(basicInfo);
            }
            else {
                log.info("idcardVisitRepository.procedurePassExcptIoPassExcptIf: {}", basicInfo.toString());
            }

            result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> selectAdmPassSecEduList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectAdmPassSecEduList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Integer selectAdmPassSecEduListCnt(Map<String, Object> paramMap) {
        Integer resultCnt = null;

        try {
            resultCnt = repository.selectAdmPassSecEduListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

    @Override
    public Map<String, Object> selectAdmPassSecEdu(Integer passApplNo) {
        Map<String, Object> result = null;

        try {
            result = repository.selectAdmPassSecEdu(passApplNo);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Boolean updateAdmPassSecEduIsuProc(HashMap<String, Object> paramMap) {
        Boolean result = false;

        try {
            String acIp = objectMapper.convertValue(paramMap.get("acIp"), String.class);

            @SuppressWarnings("unchecked")
            List<HashMap<String, Object>> dataList = objectMapper.convertValue(paramMap.get("data"), List.class);

            if (dataList != null) {
                for (HashMap<String, Object> item : dataList) {
                    item.put("acIp", acIp);
                    repository.updateAdmPassSecEduIsuProc(item);
                }

                result = true;
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Boolean registAdmPassSecEduViolation(HashMap<String, Object> paramMap) {
        boolean result = false;

        try {
            String acIp = objectMapper.convertValue(paramMap.get("acIp"), String.class);

            @SuppressWarnings("unchecked")
            List<HashMap<String, Object>> dataList = objectMapper.convertValue(paramMap.get("data"), List.class);

            if (dataList != null) {

                //위규내용 조회
                Map<String, String> codeMap = new HashMap<>();
                codeMap.put("grpCd", "C065");
                codeMap.put("detlCd", "C0651001");
                String detlNm = repository.selectIoOfendRsn(codeMap);

                StringBuilder mailContent = null;
                String title = "[행복한 만남] 보안위규 관련, 시정계획서 작성 필요 안내";
                String schemaNm = "보안위규자";

                String empId = "";
                String ioEmpNm = "";
                String ioCompNm = "";
                String ioJwNm = "";
                String ofendDt = "";
                String ofendTm = "";
                String rcvCompNm = "";
                String limit14Dtm = "";

                String admEmailAddr = "";
                String ofendEmpEmail = "";

                for (HashMap<String, Object> item : dataList) {
                    item.put("acIp", acIp);

                    // 보안위규내역 등록
                    repository.insertIoOfendInfo(item);

                    // 교육대상자 정보 업데이트
                    repository.updateAdmPassSecEdu(item);

                    // 보안위규조치내역 시정계획서 등록
                    repository.insertIoCorrPlan‌Info(item);

                    // 보안위규자NO 등록
                    repository.insertIoOfendMeet(item);

                    // ================= NOTE: 위규자 메일 발송 시작 =======================
                    empId = objectMapper.convertValue(item.get("empId"), String.class);
                    ioEmpNm = objectMapper.convertValue(item.get("ioEmpNm"), String.class);
                    ioCompNm = objectMapper.convertValue(item.get("ioCompNm"), String.class);
                    ioJwNm = objectMapper.convertValue(item.get("ioJwNm"), String.class);
                    ofendDt = objectMapper.convertValue(item.get("ofendDt"), String.class);
                    ofendTm = objectMapper.convertValue(item.get("ofendTm"), String.class);
                    rcvCompNm = objectMapper.convertValue(item.get("rcvCompNm"), String.class);
                    limit14Dtm = objectMapper.convertValue(item.get("limit14Dtm"), String.class);

                    mailContent = new StringBuilder();
                    mailContent.append(" <div > \n");
                    mailContent.append(" 	<div style=\" padding:4px 0px;line-height:15px;\"><span style=\"font-weight:bold;\">" + ioEmpNm + "</span>님께서는 아래 보안위규 건에 대해, 행복한만남 사이트에 접속하여 <span style=\"color:#f57724\"> 「시정계획서」</span>를 제출하시기 바랍니다.</div> \n");
                    mailContent.append(" 	<div style=\" padding:4px 0px;line-height:15px;\"><span style=\"color:#f57724\">시정계획서를 14일 이내 미제출시에는 <span style=\"color:#f57724\">「1개월 출입정지」</span> 처리 됩니다.</span></div> \n");
                    mailContent.append(" 	<div style=\" padding:4px 0px;line-height:15px;\">행복한만남 사이트 :  <a target='_blank' href=\"" + securityExtnetUrl + "\">welcome.skhynix.com > 기본정보 > 시정계획서 및 시정공문</a></div> \n");
                    mailContent.append(" <br> \n");
                    mailContent.append(" <table border=\"1\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-collapse:collapse; font-family:Malgun Gothic,  Dotum; font-size:14px;  \"> \n");
                    mailContent.append(" <tbody> \n");
                    mailContent.append(" <tr> \n");
                    mailContent.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">회사명</span></td> \n");
                    mailContent.append(" <td align=\"left\" width=\"80%\">" + ioCompNm + "</td> \n");
                    mailContent.append(" </tr> \n");
                    mailContent.append(" <tr> \n");
                    mailContent.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">성명 / 직위</span></td> \n");
                    mailContent.append(" <td align=\"left\" width=\"80%\">" + ioEmpNm + " " + ioJwNm + "</td> \n");
                    mailContent.append(" </tr> \n");
                    mailContent.append(" <tr> \n");
                    mailContent.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">일시</span></td> \n");
                    mailContent.append(" <td align=\"left\">" + ofendDt + " " + ofendTm + "</td> \n");
                    mailContent.append(" </tr> \n");
                    mailContent.append(" <tr> \n");
                    mailContent.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">장소</span></td> \n");
                    mailContent.append(" <td align=\"left\">" + rcvCompNm + "</td> \n");
                    mailContent.append(" </tr> \n");
                    mailContent.append(" <tr> \n");
                    mailContent.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">위규 내용</span></td> \n");
                    mailContent.append(" <td align=\"left\">" + detlNm + "</td> \n");
                    mailContent.append(" </tr> \n");
                    mailContent.append(" <tr> \n");
                    mailContent.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">위규 처리</span></td> \n");
                    mailContent.append(" <td align=\"left\"><span style=\"color:#f57724\">14일 이내 시정계획서 제출 <a target='_blank' href=\"" + securityExtnetUrl + "\">(시정계획서 제출 바로가기)</a></span></td> \n");
                    mailContent.append(" </tr> \n");
                    mailContent.append(" <tr> \n");
                    mailContent.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">제출 기한</span></td> \n");
                    mailContent.append(" <td align=\"left\"> \n");
                    mailContent.append(" " + limit14Dtm + " 24:00 까지 \n");
                    mailContent.append(" </td> \n");
                    mailContent.append(" </tr> \n");
                    mailContent.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">주의 사항</span></td> \n");
                    mailContent.append(" <td align=\"left\"> \n");
                    mailContent.append(" - 상시출입증이 있는 상시출입자는 시정계획서를 미제출시 1개월 출입정지 처리함(상시출입증은 발급실에 반납)</br> \n");
                    mailContent.append(" - 그외 방문객은 시정계획서 제출시까지 방문예약 불가하며, 14일내 미제출시 1개월 출입정지 처리함 \n");
                    mailContent.append(" </td> \n");
                    mailContent.append(" </tr> \n");
                    mailContent.append(" </tbody> \n");
                    mailContent.append(" </table> \n");
                    mailContent.append(" </div> \n");

                    admEmailAddr = objectMapper.convertValue(item.get("admEmailAddr"), String.class);
                    if (!StringUtils.isEmpty(admEmailAddr)) {
                        // 대표 관리자
                        mailing.sendMail(title, mailing.applyMailTemplateExt(title, mailContent.toString()), admEmailAddr, empId, schemaNm, "", acIp);
                    }

                    ofendEmpEmail = objectMapper.convertValue(item.get("ofendEmpEmail"), String.class);
                    mailing.sendMail(title, mailing.applyMailTemplateExt(title, mailContent.toString()), ofendEmpEmail, empId, schemaNm, "", acIp);
                    // ================= NOTE: 위규자 메일 발송 종료 =======================

                    result = true;
                }
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Boolean cancelAdmPassSecEduViolation(HashMap<String, Object> paramMap) {
        boolean result = false;

        try {
            String acIp = objectMapper.convertValue(paramMap.get("acIp"), String.class);

            @SuppressWarnings("unchecked")
            List<HashMap<String, Object>> dataList = objectMapper.convertValue(paramMap.get("data"), List.class);

            if (dataList != null) {

                //위규내용 조회
                Map<String, String> codeMap = new HashMap<>();
                codeMap.put("grpCd", "C065");
                codeMap.put("detlCd", "C0651001");
                String detlNm = repository.selectIoOfendRsn(codeMap);

                StringBuilder mailContent = null;
                String title = "[정정][행복한 만남] 보안 위규 관련 메일이 잘못 발송되어 정정합니다.";
                String schemaNm = "보안위규취소";

                String empId = "";
                String ioEmpNm = "";
                String ioJwNm = "";

                String admEmailAddr = "";
                String ofendEmpEmail = "";

                for (HashMap<String, Object> item : dataList) {
                    item.put("acIp", acIp);

                    // 보안위규내역 업데이트
                    repository.updateIoOfendInfo(item);

                    // 보안위규조치내역 업데이트
                    repository.updateIoCorrPlan‌Info(item);

                    // 보안위규조치내역 시정계획서 등록
                    item.put("ofendDocNo", "");
                    repository.updateAdmPassSecEdu(item);

                    // ================= NOTE: 위규자 메일 발송 시작 =======================
                    empId = objectMapper.convertValue(item.get("empId"), String.class);
                    ioEmpNm = objectMapper.convertValue(item.get("ioEmpNm"), String.class);
                    ioJwNm = objectMapper.convertValue(item.get("ioJwNm"), String.class);

                    mailContent = new StringBuilder();
                    mailContent.append(" <div > \n");
                    mailContent.append(" 	<div style=\" padding:4px 0px;line-height:15px;\">안녕하세요. 행복한 만남입니다.</div><br/> \n");
                    mailContent.append(" 	<div style=\" padding:4px 0px;line-height:15px;\">보안 위규 관련 오류로 인해 위규 처리 메일이 잘못 발송되었습니다.</div>");
                    mailContent.append(" 	<div style=\" padding:4px 0px;line-height:15px;\">해당 위규 건은 자동으로 취소가 이루어질 예정입니다.</div> \n");
                    mailContent.append(" 	<div style=\" padding:4px 0px;line-height:15px;\">감사합니다.</div> \n");
                    mailContent.append(" <br> \n");
                    mailContent.append(" <table border=\"1\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-collapse:collapse; font-family:Malgun Gothic,  Dotum; font-size:14px; \"> \n");
                    mailContent.append(" <tbody> \n");
                    mailContent.append(" <tr> \n");
                    mailContent.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">성명 / 직위</span></td> \n");
                    mailContent.append(" <td align=\"left\" width=\"80%\">" + ioEmpNm + " " + ioJwNm + "</td> \n");
                    mailContent.append(" </tr> \n");
                    mailContent.append(" <tr> \n");
                    mailContent.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">위규 내용</span></td> \n");
                    mailContent.append(" <td align=\"left\">" + detlNm + "</td> \n");
                    mailContent.append(" </tr> \n");
                    mailContent.append(" <tr> \n");
                    mailContent.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">위규 처리</span></td> \n");
                    mailContent.append(" <td align=\"left\">시정계획서 징구</td> \n");
                    mailContent.append(" </tr> \n");
                    mailContent.append(" <tr> \n");
                    mailContent.append(" <td colspan=\"2\" align=\"left\"> \n");
                    mailContent.append("  * 해당 위규 처리 건은 취소처리 되었습니다. \n");
                    mailContent.append(" </td> \n");
                    mailContent.append(" </tr> \n");
                    mailContent.append(" </tbody> \n");
                    mailContent.append(" </table> \n");
                    mailContent.append(" </div> \n");

                    admEmailAddr = objectMapper.convertValue(item.get("admEmailAddr"), String.class);
                    if (!StringUtils.isEmpty(admEmailAddr)) {
                        // 대표 관리자
                        mailing.sendMail(title, mailing.applyMailTemplateExt(title, mailContent.toString()), admEmailAddr, empId, schemaNm, "", acIp);
                    }

                    ofendEmpEmail = objectMapper.convertValue(item.get("ofendEmpEmail"), String.class);
                    mailing.sendMail(title, mailing.applyMailTemplateExt(title, mailContent.toString()), ofendEmpEmail, empId, schemaNm, "", acIp);
                    // ================= NOTE: 위규자 메일 발송 종료 =======================
                }

                result = true;
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> selectRegularPassExprHistList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectRegularPassExprHistList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Integer selectRegularPassExprHistListCnt(Map<String, Object> paramMap) {
        Integer resultCnt = null;

        try {
            resultCnt = repository.selectRegularPassExprHistListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

    @Override
    public Map<String, Object> selectRegularPassExprHist(Integer exprApplNo) {
        Map<String, Object> result = null;

        try {
            result = repository.selectRegularPassExprHist(exprApplNo);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> selectSpecialPassCancList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectSpecialPassCancList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Integer selectSpecialPassCancListCnt(Map<String, Object> paramMap) {
        Integer resultCnt = null;

        try {
            resultCnt = repository.selectSpecialPassCancListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

    @Override
    public List<Map<String, Object>> selectSpecialPassAutoCancList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectSpecialPassAutoCancList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Integer selectSpecialPassAutoCancListCnt(Map<String, Object> paramMap) {
        Integer resultCnt = null;

        try {
            resultCnt = repository.selectSpecialPassAutoCancListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

}
