package com.skshieldus.esecurity.service.entmanage.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skshieldus.esecurity.common.component.Mailing;
import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.model.common.ApprovalDTO;
import com.skshieldus.esecurity.model.common.ApprovalDocDTO;
import com.skshieldus.esecurity.model.common.SavedApproverLineDTO;
import com.skshieldus.esecurity.model.entmanage.IparkingDTO;
import com.skshieldus.esecurity.model.entmanage.SendSpmsDTO;
import com.skshieldus.esecurity.repository.entmanage.EntManageCommonRepository;
import com.skshieldus.esecurity.repository.entmanage.ReserveVisitRepository;
import com.skshieldus.esecurity.repository.entmanage.calder.CalderRepository;
import com.skshieldus.esecurity.repository.entmanage.cjvehiclecampus.CjVehicleCampusRepository;
import com.skshieldus.esecurity.repository.entmanage.ialder.IalderRepository;
import com.skshieldus.esecurity.repository.entmanage.icvehiclehaengbok.IcVehicleHaengbokRepository;
import com.skshieldus.esecurity.service.common.ApprovalService;
import com.skshieldus.esecurity.service.entmanage.ReserveVisitService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class ReserveVisitServiceImpl implements ReserveVisitService {

    @Autowired
    private ReserveVisitRepository repository;

    @Autowired
    private EntManageCommonRepository commonRepository;

    @Autowired
    private IcVehicleHaengbokRepository icVehicleHaengbokRepository;

    // @Autowired
    // private IcVstcarGothamRepository icVstcarGothamRepository;

    //	@Autowired
    //	private IparkingApiClient iparkingApiClient;

    @Autowired
    private CjVehicleCampusRepository cjVehicleCampusRepository;

    @Autowired
    private CalderRepository calderRepository;

    @Autowired
    private IalderRepository ialderRepository;

    @Autowired
    private ApprovalService approvalService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Mailing mailing;

    @Autowired
    private Environment environment;

    //	@Value("${ifaccess.nac.ic.ip}")
    //	private String nacIpIc;
    //
    //	@Value("${ifaccess.nac.cj.ip}")
    //	private String nacIpCj;
    //
    //	@Value("${ifaccess.nac.dev.ip}")
    //	private String nacIpDev;

    @Value("${security.extnet.url}")
    private String securityExtnetUrl;

    @Override
    public List<Map<String, Object>> selectVisitorCertiList(Map<String, Object> paramMap) {
        return repository.selectVisitorCertiList(paramMap);
    }

    @Override
    public Map<String, Object> selectVisitorCertiView(Map<String, Object> paramMap) {
        return repository.selectVisitorCertiView(paramMap);
    }

    @Override
    public void updateVisitorCerti(Map<String, Object> paramMap) {
        // 승인
        if ("Y".equals(paramMap.get("certiGubun"))) {
            int count = repository.updateVisitorCerti(paramMap);
            if (count == 1) {
                count = repository.updateVisitorNameCheck(paramMap);
                // TODO repository.updateVisitorNameCheck(paramMap); => _EsecurityHs
                // TODO repository.updateVisitorNameCheck(paramMap); => _EsecuritySi
                if (count != 1)
                    throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            else {
                throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            // 반려
        }
        else {
            int count = repository.deleteVisitorCerti(paramMap);
            if (count != 1)
                throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<Map<String, Object>> selectVisitorProgressList(Map<String, Object> paramMap) {
        return repository.selectVisitorProgressList(paramMap);
    }

    @Override
    public List<Map<String, Object>> selectVisitorProgressAdminList(Map<String, Object> paramMap) {
        return repository.selectVisitorProgressAdminList(paramMap);
    }

    @Override
    public Integer selectVisitorProgressAdminCount(Map<String, Object> paramMap) {
        return repository.selectVisitorProgressAdminCount(paramMap);
    }

    @Override
    public boolean deleteVisitorProgress(Map<String, Object> paramMap) {
        return Objects.equals(repository.deleteVisitorProgress(paramMap), 1);
    }

    @Override
    public Map<String, Object> selectReserveVisitViewReIO(Map<String, Object> paramMap) {
        return repository.selectReserveVisitViewReIO(paramMap);
    }

    @Override
    public List<Map<String, Object>> selectReserveVisitVstManListIO(Map<String, Object> paramMap) {
        return repository.selectReserveVisitVstManListIO(paramMap);
    }

    @Override
    public List<Map<String, Object>> selectInEntrytheBuildingsTop(Map<String, Object> paramMap) {
        return repository.selectInEntrytheBuildingsTop(paramMap);
    }

    @Override
    public List<Map<String, Object>> selectInEntrytheBuildingsList(Map<String, Object> paramMap) {
        return repository.selectInEntrytheBuildingsList(paramMap);
    }

    @Override
    public List<Map<String, Object>> selectReserveVisitSelectIOList(Map<String, Object> paramMap) {
        return repository.selectReserveVisitSelectIOList(paramMap);
    }

    @Override
    public List<Map<String, Object>> selectReserveVisitBuildingIOList(Map<String, Object> paramMap) {
        return repository.selectReserveVisitBuildingIOList(paramMap);
    }

    @Override
    public List<Map<String, Object>> selectCjGateList(Map<String, Object> paramMap) {
        return repository.selectCjGateList();
    }

    @Override
    public boolean updateReserveVisitBuildChkChg(Map<String, Object> paramMap) {
        return repository.updateReserveVisitBuildChkChg(paramMap) > 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean updateReserveVisitBuild(Map<String, Object> paramMap) {
        boolean result = true;

        // 출입건물 등록
        if (paramMap.get("newBuildList") != null) {
            // 출입건물 삭제
            repository.deleteReserveVisitBuild(paramMap);

            List<Map<String, Object>> newBuildList = objectMapper.convertValue(paramMap.get("newBuildList"), List.class);
            List<Map<String, Object>> visitorList = objectMapper.convertValue(paramMap.get("visitorList"), List.class);

            if (visitorList != null) {
                for (Map<String, Object> visitorMap : visitorList) {
                    for (Map<String, Object> newBuildMap : newBuildList) {
                        newBuildMap.put("acIp", paramMap.get("acIp"));
                        newBuildMap.put("crtBy", paramMap.get("crtBy"));
                        newBuildMap.put("vstApplNo", paramMap.get("vstApplNo"));
                        newBuildMap.put("ioEmpId", visitorMap.get("ioEmpId"));
                        repository.insertReserveVisitBuild(newBuildMap);
                    }
                }
            }
            else {
                for (Map<String, Object> newBuildMap : newBuildList) {
                    newBuildMap.put("acIp", paramMap.get("acIp"));
                    newBuildMap.put("crtBy", paramMap.get("crtBy"));
                    newBuildMap.put("vstApplNo", paramMap.get("vstApplNo"));
                    newBuildMap.put("ioEmpId", paramMap.get("ioEmpId"));
                    repository.insertReserveVisitBuild(newBuildMap);
                }
            }
            // 출입건물 등록 (특정 gateId)
        }
        else if (paramMap.get("gateId") != null && paramMap.get("leadYn") != null) {
            // 출입건물 삭제 (특정 gateId)
            paramMap.put("delGateId", paramMap.get("gateId"));
            repository.deleteReserveVisitBuild(paramMap);

            List<Map<String, Object>> visitorList = objectMapper.convertValue(paramMap.get("visitorList"), List.class);
            if (visitorList != null) {
                for (Map<String, Object> visitorMap : visitorList) {
                    // 출입건물 등록 (특정 gateId)
                    paramMap.put("ioEmpId", visitorMap.get("ioEmpId"));
                    repository.insertReserveVisitBuild(paramMap);
                }
            }
            else {
                repository.insertReserveVisitBuild(paramMap);
            }
        }
        else if (paramMap.get("delGateId") != null) {
            // 출입건물 삭제 (특정 gateId)
            repository.deleteReserveVisitBuild(paramMap);
        }
        return result;
    }

    @Override
    public boolean rejectReserveVisit(Map<String, Object> paramMap) {
        boolean result = true;

        // 반려 처리
        paramMap.put("applStat", "Z0331003"); // 접수반려
        repository.updateReserveVisitReject(paramMap);

        // 메일발송
        // ================= NOTE: 메일 발송 시작 =======================
        String title = "[행복한 만남, SK 하이닉스]신청하신 건이 반려되었습니다.";
        String docNm = commonRepository.selectMailDocNm(paramMap);
        Map<String, String> ioEmpInfo = commonRepository.selectMailIoEmp(paramMap.get("ioEmpId").toString());
        String ioEmpNm = ioEmpInfo.get("ioEmpNm");
        String to = paramMap.get("ioEmailAddr").toString();
        String schemaNm = paramMap.get("schemaNm").toString();
        String acIp = paramMap.get("acIp").toString();
        String empNo = paramMap.get("crtBy").toString();

        String content = ioEmpNm + "님께서 신청하신 건(" + docNm + ")이 반려되었습니다.<br /><br /><b>반려사유</b><br /> " + paramMap.get("cancelRsn").toString().replaceAll("\n", "<br />");
        mailing.sendMailExt(title, mailing.applyMailTemplate(title, content), to, empNo, schemaNm, "", acIp);
        // ================= NOTE: 메일 발송 종료 =======================

        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean insertReserveVisitApproval(Map<String, Object> paramMap) {
        boolean result = false;

        try {
            // 결재정보
            ApprovalDTO approval = objectMapper.convertValue(paramMap.get("approval"), ApprovalDTO.class);
            List<SavedApproverLineDTO> requestLine = List.copyOf(approval.getSavedRequestApproverLine());
            List<SavedApproverLineDTO> permitLine = List.copyOf(approval.getSavedPermitApproverLine());

            // html map (기본정보)
            Map<String, Object> htmlMap = objectMapper.convertValue(paramMap.get("htmlMap"), HashMap.class);
            // 방문예약ID
            Integer vstApplNo = (Integer) paramMap.get("vstApplNo");
            paramMap.put("schemaNm", approval.getSchemaNm());

            // 상태값 설정 => 접수완료
            paramMap.put("applStat", "Z0331002");

            if (paramMap.get("meetEmpId") != null) {
                repository.updateReserveVisitMeetEmpId(paramMap);
            }

            // 자가결재
            if ("Y".equals(paramMap.get("selfYn").toString())) {
                // 상태값 설정 => 결재완료
                paramMap.put("applStat", "Z0331005");
                // 승인 (팀장이상) 기능 처리 시 요청부서 결재선 제거
                requestLine = List.of();
            }

            // 실 결재 진행 여부
            boolean isApproval = (requestLine != null && requestLine.size() > 0) || (permitLine != null && permitLine.size() > 0);

            // 상신 처리
            if (isApproval) {
                // 건물 정보 설정
                Map<String, Object> buildParamMap = new HashMap<>();
                buildParamMap.put("vstApplNo", paramMap.get("vstApplNo"));
                List<Map<String, Object>> buildList = repository.selectReserveVisitBuildingIOList(buildParamMap);
                htmlMap.put("buildList", buildList);

                // 방문객 정보 설정
                List<Map<String, Object>> visitorList = objectMapper.convertValue(paramMap.get("visitorList"), List.class);
                visitorList = visitorList.stream().map(data -> {
                    // 전산 저장장치 정보 조회
                    List<Map<String, Object>> selectIOList = repository.selectReserveVisitSelectIOList(data);
                    selectIOList = selectIOList.stream().map(selectIO -> {
                        if (selectIO.get("openBit") != null) {
                            boolean openBit1 = false;
                            boolean openBit2 = false;
                            boolean openBit3 = false;
                            boolean openBit5 = false;
                            boolean openBit6 = false;

                            // open_bit 관련 view data 설정
                            BigInteger openBit = ((BigDecimal) selectIO.get("openBit")).toBigInteger();
                            if (openBit.and(BigInteger.valueOf(1)).compareTo(BigInteger.valueOf(0)) != 0)
                                openBit1 = true;
                            if (openBit.and(BigInteger.valueOf(2)).compareTo(BigInteger.valueOf(0)) != 0)
                                openBit2 = true;
                            if (openBit.and(BigInteger.valueOf(4)).compareTo(BigInteger.valueOf(0)) != 0)
                                openBit3 = true;
                            if (openBit.and(BigInteger.valueOf(16)).compareTo(BigInteger.valueOf(0)) != 0)
                                openBit5 = true;
                            if (openBit.and(BigInteger.valueOf(32)).compareTo(BigInteger.valueOf(0)) != 0)
                                openBit6 = true;
                            selectIO.put("openBit1", openBit1);
                            selectIO.put("openBit2", openBit2);
                            selectIO.put("openBit3", openBit3);
                            selectIO.put("openBit5", openBit5);
                            selectIO.put("openBit6", openBit6);
                        }
                        return selectIO;
                    }).collect(Collectors.toList());
                    data.put("selectIOList", selectIOList);
                    return data;
                }).collect(Collectors.toList());
                htmlMap.put("visitorList", visitorList);

                // 결재정보 설정
                approval.setHtmlMap(htmlMap);
                approval.setLid(vstApplNo);

                log.info("방문예약 결재 => {}", approval);
                // 결재처리
                ApprovalDocDTO approvalDoc = approvalService.insertApproval(approval);

                paramMap.put("docId", approvalDoc.getDocId());
            }

            // 방문예약 정보 업데이트
            repository.updateReserveVisitVstStat(paramMap);

            // ================= NOTE: 메일 발송 시작 =======================
            String title = "[행복한 만남, SK 하이닉스]신청하신 건이 접수되었습니다.";
            String docNm = commonRepository.selectMailDocNm(paramMap);
            String empId = paramMap.get("empId").toString();
            String ioEmpId = paramMap.get("ioEmpId").toString();
            Map<String, String> ioEmpInfo = commonRepository.selectMailIoEmp(ioEmpId);
            String ioEmailAddr = ioEmpInfo.get("ioEmailAddr").toString();
            String ioEmpNm = ioEmpInfo.get("ioEmpNm").toString();
            String schemaNm = paramMap.get("schemaNm").toString();
            String acIp = paramMap.get("acIp") != null
                ? paramMap.get("acIp").toString()
                : "";

            String content = ioEmpNm + "님께서 신청하신 건(" + docNm + ")이 접수되었습니다.";
            content += "<br>";
            content += "SK Hynix 내부로 노트북을 반입하기 위해서는 최신 Windows 보안 업데이트가 설치되어야 하며, 아래 5가지의 Anti Virus 프로그램 중 하나의 백신 프로그램이 설치되어야 합니다. (안랩 V3, 하우리 바이로봇, Kaspersky, McAfee, Norton)<br>";
            content += "1) V3 Internet Security 9.0<br>";
            content += "<a href='Https://www.ahnlab.com/kr/site/download/product/productFreeList.do' target='_new'> Https://www.ahnlab.com/kr/site/download/product/productFreeList.do</a><br>";
            content += "2) 하우리 바이로봇<br>";
            content += "<a href='Http://www.hauri.co.kr/download/trial.html' target='_new'> Http://www.hauri.co.kr/download/trial.html</a><br>";
            content += "3) Kaspersky Internet Security<br>";
            content += "<a href='Http://www.kaspersky.co.kr/free-trials/' target='_new'> Http://www.kaspersky.co.kr/free-trials/</a><br>";
            content += "4) McAfee<br>";
            content += "<a href='Https://home.mcafee.com/downloads/OneClickTrial.aspx' target='_new'> Https://home.mcafee.com/downloads/OneClickTrial.aspx</a><br>";
            content += "5) Norton Security<br>";
            content += "<a href='Http://kr.norton.com/downloads' target='_new'> Http://kr.norton.com/downloads</a><br>";

            mailing.sendMailExt(title, mailing.applyMailTemplateExt(title, content), ioEmailAddr, empId, schemaNm, "", acIp);
            // ================= NOTE: 메일 발송 종료 =======================

            // 자가결재 => 후처리 (연동 및 메일/Kakao 발송) => 실 결재 진행여부 false 인 경우만 수행
            if (!isApproval) {
                this.reserveVisitApprovalPost(paramMap);
            }

            // 처리 완료
            result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public void reserveVisitApprovalPost(Map<String, Object> paramMap) throws Exception {
        try {
            // 운영 여부
            boolean isProd = environment.acceptsProfiles(Profiles.of("prd"));
            Integer lid = paramMap.get("lid") instanceof String
                ? Integer.parseInt(paramMap.get("lid").toString())
                : (Integer) paramMap.get("lid");
            String docId = paramMap.get("docId") != null
                ? paramMap.get("docId").toString()
                : "";

            // 결재상태 업데이트
            repository.updateReserveVisitApplStat(paramMap);

            // ================= NOTE: 메일 발송 시작 =======================
            String title = "[e-Security]신청하신 건이 최종 승인되었습니다.";
            String docNm = commonRepository.selectMailDocNm(paramMap);
            String empId = paramMap.get("empId") != null
                ? paramMap.get("empId").toString()
                : "";
            String applEmpEmail = "";
            String applEmpNm = "";
            if (StringUtils.isNotEmpty(docId)) {
                Map<String, String> empInfo = commonRepository.selectMailApprCoEmp(docId);
                if (empInfo != null) {
                    applEmpEmail = empInfo.get("applEmpEmail") != null
                        ? empInfo.get("applEmpEmail").toString()
                        : "";
                    applEmpNm = empInfo.get("applEmpNm") != null
                        ? empInfo.get("applEmpNm").toString()
                        : "";
                }
            }
            else {
                // 자가결재 시 docId가 없으므로 신청자(=최종결재자)에게 메일 발송
                if (paramMap.get("email") != null && paramMap.get("empNm") != null) {
                    applEmpEmail = paramMap.get("email").toString();
                    applEmpNm = paramMap.get("empNm").toString();
                }
            }

            if (StringUtils.isNotEmpty(applEmpEmail)) {
                String schemaNm = paramMap.get("schemaNm") != null
                    ? paramMap.get("schemaNm").toString()
                    : "";
                String acIp = paramMap.get("acIp") != null
                    ? paramMap.get("acIp").toString()
                    : "";
                boolean hasNetworkMsg = false;

                String content = applEmpNm + "님께서 신청하신 건(" + docNm + ")이 최종승인되었습니다.";

                if ("방문예약".equals(docNm)) {
                    content += "<div style='color:red;margin-top:10px;'>※ 주의 <br />&nbsp;&nbsp;&nbsp;&nbsp;방문객의 보안 문제 발생 시 승인자/접견자에게 책임이 있습니다.</div>";
                    content += "<table><tr><td><img src='https://welcome.skhynix.com/esecurity/assets/common/images/common/vsit_rsrv_inform.jpg' /></td></tr></table>";
                }
                else if ("VIP 방문예약".equals(docNm)) {
                    List<Map<String, String>> list = repository.selectVipNetworkInfo(lid);
                    String vst_strt_dt = "";
                    // String vst_end_dt = "";
                    String vst_tm = "";
                    String netDuration = "";
                    String vst_year = "";
                    String vst_month = "";
                    String vst_day = "";

                    StringBuilder sbAppendMsg = new StringBuilder();
                    sbAppendMsg.append("<br><br>");
                    sbAppendMsg.append("<p>당사는 국가핵심기술 보호를 위해 보다 엄격한 기준으로 산업보안활동을 진행하고 있습니다.             </p>\n");
                    sbAppendMsg.append("<p>외부인의 불필요한 사무실 출입을 제한하며, 각 건물 1층 접견실을 이용하여 주시기 바랍니다.           </p>\n");
                    sbAppendMsg.append("<p>사무실 출입이 필요한 경우 반드시 구성원과 함께 동행하여야 합니다.                                  </p>\n");
                    sbAppendMsg.append("<p>VIP 방문객의 경우 보안검색 미실시에 대한 책임은 인솔자에 있으므로 보안에 각별한 주의 부탁드립니다. </p>\n");

                    content += sbAppendMsg.toString();

                    if (list != null && list.size() > 0) {
                        hasNetworkMsg = true;

                        content += "<br/>";
                        content += "<table cellpadding='0' cellspacing='0' border='1' style='border-collapse: collapse; font-family:Malgun Gothic,  Dotum; font-size:14px;' width='400'>";
                        content += "<tr><td align='center' bgcolor='#F2CB61' width='50%'>계정</td>";
                        content += "<td align='center' bgcolor='#F2CB61' width='50%'>비밀번호</td></tr>";

                        for (Map<String, String> map : list) {
                            content += "<tr><td align='center'>" + map.get("networkUserId") + "</td>";
                            content += "<td align='center'>" + map.get("networkAuthno") + "</td></tr>";

                            vst_strt_dt = map.get("vstStrtDt");
                            vst_year = vst_strt_dt.substring(0, 4);
                            vst_month = vst_strt_dt.substring(4, 6);
                            vst_day = vst_strt_dt.substring(6, 8);
                            // vst_end_dt = map.get("vstEndDt");
                            vst_tm = map.get("vstTm");
                            netDuration = map.get("timeDuration");
                        }
                        content += "</table>";
                        content += "<br>";
                        content += "<font color='red'><b>※무선 토큰은 예약된 시간 동안만 사용 가능 합니다.<br>";
                        content += "&nbsp;&nbsp;사용시간 : " + vst_year + "년 " + vst_month + "월 " + vst_day + "일 " + " " + vst_tm + " ~ " + netDuration + " 시간</b></font>";
                        content += "<br>&nbsp;&nbsp;<font color='blue'>사내무선랜 사용 관련 문의 : 유승민 대리(010-3357-9980), 정길하 수석(010-9911-4028), 곽규봉 TL(010-2614-8287)</font>";
                    }
                }

                if (isProd && "VIP 방문예약".equals(docNm) && hasNetworkMsg) {
                    String[] bcc = { "skhy.I0100676@partner.sk.com", "skhy.X0117424@partner.sk.com", "skhy.X0007359@partner.sk.com" };
                    mailing.sendMailBcc(title, mailing.applyMailTemplate(title, content), applEmpEmail, empId, schemaNm, docId, acIp, bcc);
                }
                else {
                    mailing.sendMail(title, mailing.applyMailTemplate(title, content), applEmpEmail, empId, schemaNm, docId, acIp);
                }
                // ================= NOTE: 메일 발송 종료 =======================
            }

            // Kakao 발송
            List<Map<String, String>> kakaoList = repository.selectVisitListForSMS(paramMap);
            if (kakaoList != null && kakaoList.size() > 0) {
                for (Map<String, String> kakaoInfo : kakaoList) {
                    String smsYn = kakaoInfo.get("smsYn") != null
                        ? kakaoInfo.get("smsYn").toString()
                        : "";

                    // 방문예약
                    if ("Y".equals(smsYn) && kakaoInfo.get("smsNo") != null) {
                        // ================= NOTE: SMS(kakao) 발송 시작 =======================
                        String smsNo = kakaoInfo.get("smsNo").toString().replaceAll("-", "");
                        String callbackNo = kakaoInfo.get("callbackNo");
                        if (!"1101000001".equals(kakaoInfo.get("compId")))
                            callbackNo = "0316302298";

                        String msg = "[SK하이닉스]" + kakaoInfo.get("receiveEmpNm").toString() + "님";
                        msg += "예약완료.코로나 관련증상,접촉자 입문불가.신고:bpshe.skhynix.com/covid";

                        // 주석처리 2023-06-09
                        //						RequestWrapModel<KakaoMessageDTO> wrapParams = new RequestWrapModel<>();
                        //						KakaoMessageDTO kakaoMessageDTO = new KakaoMessageDTO();
                        //						kakaoMessageDTO.setKTemplateCode("SJT_066346");
                        //						kakaoMessageDTO.setSubject("예약완료");
                        //						kakaoMessageDTO.setDstaddr(smsNo);
                        //						kakaoMessageDTO.setCallback(callbackNo);
                        //						kakaoMessageDTO.setText(msg);
                        //						kakaoMessageDTO.setText2(msg);
                        //						kakaoMessageDTO.setKAttach("");
                        //						kakaoMessageDTO.setEmpId(kakaoInfo.get("sendEmpId"));
                        //
                        //						wrapParams.setParams(kakaoMessageDTO);
                        //						commonApiClient.sendKakaoMessage(wrapParams);
                        // ================= NOTE: SMS(kakao) 발송 종료 =======================
                    }
                }
                Map<String, String> kakaoInfo = kakaoList.get(0);
                if ("V".equals(kakaoInfo.get("gbns")) || "F".equals(kakaoInfo.get("gbns"))) {
                    // ================= NOTE: SMS(kakao) 발송 시작 =======================
                    String smsNo = kakaoInfo.get("callbackNo").replaceAll("-", "");

                    String msgGbn = "";
                    if (kakaoInfo.get("gbns").equals("V")) {
                        msgGbn = "VIP 방문예약";
                    }
                    else {
                        msgGbn = "가족 방문예약";
                    }

                    String msg = kakaoInfo.get("sendEmpNm") + "님  " + kakaoInfo.get("visitDay");
                    msg += "(" + kakaoInfo.get("compNm") + ") " + msgGbn + " 완료/";
                    msg += "미 신청 저장장치 반입 금지. e-Security";

                    // 주석처리 2023-06-09
                    //					RequestWrapModel<KakaoMessageDTO> wrapParams = new RequestWrapModel<>();
                    //					KakaoMessageDTO kakaoMessageDTO = new KakaoMessageDTO();
                    //					kakaoMessageDTO.setKTemplateCode("SJT_066347");
                    //					kakaoMessageDTO.setSubject("방문예약");
                    //					kakaoMessageDTO.setDstaddr(smsNo);
                    //					kakaoMessageDTO.setCallback("");
                    //					kakaoMessageDTO.setText(msg);
                    //					kakaoMessageDTO.setText2(msg);
                    //					kakaoMessageDTO.setKAttach("");
                    //
                    //					wrapParams.setParams(kakaoMessageDTO);
                    //					commonApiClient.sendKakaoMessage(wrapParams);
                    // ================= NOTE: SMS(kakao) 발송 종료 =======================
                }
            }

            // 차량 연동 IF
            this.processReserveCarIf(lid);

            // 방문예약 전산저장장치 IF => fmReserve_NacIF
            this.processReserveNacIf(lid);
        } catch (Exception e) {
            log.error("[방문예약 후처리] ERROR : {}", e.getMessage());
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
    }

    public void processReserveCarIf(Integer lid) {
        // 운영 여부
        boolean isProd = environment.acceptsProfiles(Profiles.of("prd"));

        // 방문 사업장에 대하여 별도 정보 검색 후 처리
        // 상신자와 승인자의 사업장이 다를경우 정상적인 사업장으로 .LPR 정보 I/F 불가 20191023 HSK
        String compId = repository.selectIoVstCarVstCompId(lid);

        // 차량시스템 연계 (운영일때만)
        // 이천
        if ("1101000001".equals(compId)) {
            // VIP I/F 차량출입 => fmVip_ICSpmsIF
            List<SendSpmsDTO> vipIcList = repository.selectVipIcSpmsIfInfo(lid);
            for (SendSpmsDTO IF_RequestData : vipIcList) {
                IF_RequestData.setDivision("8");
                IF_RequestData.setMemberType("88");
                // 이천 LPR 차량 연계 => Destination = 1101000001
                log.info("[방문예약 후처리] 이천VIP LPR 차량 연동 처리");
                log.info("[방문예약 후처리] if data => {}", ReflectionToStringBuilder.toString(IF_RequestData));
                if (isProd)
                    icVehicleHaengbokRepository.sendICSpmsInsertMemberInner(IF_RequestData);

                // 행복문 LPR 차량 연계 (행복문~행복5문까지만 연결) => Destination = A0380001
                IF_RequestData.setDestination("A0380001");
                log.info("[방문예약 후처리] 행복문 LPR 차량 연동 처리");
                log.info("[방문예약 후처리] if data => {}", ReflectionToStringBuilder.toString(IF_RequestData));
                if (isProd)
                    icVehicleHaengbokRepository.sendICSpmsInsertMemberInner(IF_RequestData);
            }

            try {
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                DateTimeFormatter dateFormatterNew = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                // 고담 방문객 주차장(기존 현우주자장 이동에의한) 신설로 방문객 주차장에 LPR 시스템 구축 : 이천만 I/F => fmSendICSpms
                // 고담 : 일반 방문예약, VIP 방문예약 => API 방식으로 변경 (iparking - 파킹클라우드) 20211227
                List<SendSpmsDTO> gothamList = repository.selectSendICSpmsIoVstInfoForGotham(lid);
                for (SendSpmsDTO IF_RequestData : gothamList) {
                    // 고담 LPR 차량 연계 (고담 주차장 이용 방문객)
                    log.info("[방문예약 후처리] 고담 LPR 차량 연동 처리 (신규API로 변경)");
                    // log.info("[방문예약 후처리] if data => {}", ReflectionToStringBuilder.toString(IF_RequestData));
                    // if(isProd) icVstcarGothamRepository.sendICSpmsInsertMemberInnerGotham(IF_RequestData);

                    // companyName = 회사명 + 웰컴ID
                    String companyName = StringUtils.defaultIfEmpty(IF_RequestData.getAffiliatedCompany(), "") + " " + StringUtils.defaultIfEmpty(IF_RequestData.getSecurityID(), "");
                    companyName = companyName.trim();

                    // 파킹클라우드 데이터 설정
                    IparkingDTO iparkingDTO = new IparkingDTO();
                    iparkingDTO.setUserName(IF_RequestData.getVisitorName());
                    iparkingDTO.setCompany(companyName);
                    iparkingDTO.setCarNumber(IF_RequestData.getVehicleNumber());
                    iparkingDTO.setStartDate(dateFormatterNew.format(dateFormatter.parse(IF_RequestData.getEnterDateTime())));
                    iparkingDTO.setEndDate(dateFormatterNew.format(dateFormatter.parse(IF_RequestData.getNoEntryDateTime())));
                    iparkingDTO.setDescription(StringUtils.defaultIfEmpty(IF_RequestData.getNote(), ""));
                    iparkingDTO.setRequestType("R");

                    log.info("[방문예약 후처리] if data => {}", ReflectionToStringBuilder.toString(iparkingDTO));
                    if (isProd) {
                        // 주석처리 2023-06-09
                        //						Map<String, Object> iparkingResultMap = iparkingApiClient.freeCarRegistrationProcess(iparkingDTO);
                        //						log.info("[방문예약 후처리] 고담 파킹클라우드(신규API) 연동 응답 :: iparkingResultMap = {}", iparkingResultMap);
                        //						log.info("[방문예약 후처리] 고담 파킹클라우드(신규API) 연동 결과 :: result = {}", iparkingResultMap.get("result"));
                        //						log.info("[방문예약 후처리] 고담 파킹클라우드(신규API) 연동 결과 :: resultMessage = {}", iparkingResultMap.get("resultMessage"));
                    }
                }
            } catch (Exception ex) {
                log.error("파킹클라우드 연동 오류 : {}", ex.getMessage());
            }
            // 청주
        }
        else if ("1102000001".equals(compId)) {
            // 청주 차량 연계 IF => fm_SendCJSPMS
            List<SendSpmsDTO> vipCjList = repository.selectSendCJSpmsIoVipCarInfoForSpms(lid);
            for (SendSpmsDTO IF_RequestData : vipCjList) {
                // VIP/단체방문 (청주 총무팀 요청에 의해 VIP 일 경우 청주1,2,3공장 모두 등록함.)
                IF_RequestData.setDestination("F0029|F0030|F0031|F0032|F0033|F0034|F0035|F0036|F0037|F0038|F0039|F0040|F0041|F0042|F0043|F0044|F0045|F0046|F0047|F0048|F0049|F0050|F0051|F0052|F0053|F0054|F0055|F0056|F0057");
                log.info("[방문예약 후처리] 청주VIP LPR 차량 연동 처리");
                log.info("[방문예약 후처리] if data => {}", ReflectionToStringBuilder.toString(IF_RequestData));
                if (isProd)
                    cjVehicleCampusRepository.sendCJSpmsInsertMemberInner(IF_RequestData);
            }
        }
    }

    @Override
    public void mergeReserveIfManDt(Integer lid) {
        try {
            List<Map<String, Object>> manGateIoList = repository.selectReserveIfManGateIoList(lid);

            if (manGateIoList != null && manGateIoList.size() > 0) {
                for (Map<String, Object> manGateIo : manGateIoList) {
                    try {
                        manGateIo.put("GATE_ID", "0");
                        manGateIo.put("VST_SEQ", "1");
                        manGateIo.put("IOKND", "0");
                        manGateIo.put("IO_CARDNO", "");
                        manGateIo.put("IN_DT", "");
                        manGateIo.put("OUT_DT", "");
                        manGateIo.put("DEL_YN", "N");

                        Date dStart = new SimpleDateFormat("yyyyMMdd").parse(manGateIo.get("vstStrtDt").toString().replace("-", ""));
                        Date dEnd = new SimpleDateFormat("yyyyMMdd").parse(manGateIo.get("vstEndDt").toString().replace("-", ""));
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                        Calendar sCal = new GregorianCalendar();
                        sCal.setTime(dStart);
                        Calendar eCal = new GregorianCalendar();
                        eCal.setTime(dEnd);
                        long diffMillis = eCal.getTimeInMillis() - sCal.getTimeInMillis();
                        long diff = diffMillis / (24 * 60 * 60 * 1000);

                        Date nextDay = new Date();
                        if (diff > 0) {
                            int k = 0;
                            while (diff >= k) {
                                if (k == 0) {
                                    nextDay = sCal.getTime();
                                }
                                else {
                                    sCal.add(Calendar.DATE, 1);
                                    nextDay = sCal.getTime();
                                }
                                manGateIo.put("VST_DT", formatter.format(nextDay));
                                repository.insertReserveIfManGateIoMerge(manGateIo);

                                k++;
                            }
                        }
                        else {
                            nextDay = sCal.getTime();
                            manGateIo.put("VST_DT", formatter.format(nextDay));
                            repository.insertReserveIfManGateIoMerge(manGateIo);
                        }
                    } catch (Exception ex) { } //bypass
                }
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
    }

    @Override
    public void reserveVisitApprovalRejectPost(Map<String, Object> paramMap) throws Exception {
        try {
            // 결재상태 업데이트
            repository.updateReserveVisitApplStat(paramMap);

            // Kakao 발송
            List<Map<String, String>> kakaoList = repository.selectVisitListForSMS(paramMap);
            for (Map<String, String> kakaoInfo : kakaoList) {
                String smsYn = kakaoInfo.get("smsYn").toString();

                // 방문예약
                if (smsYn.equals("Y")) {
                    // ================= NOTE: SMS(kakao) 발송 시작 =======================
                    String smsNo = kakaoInfo.get("smsNo").toString().replaceAll("-", "");
                    String callbackNo = kakaoInfo.get("callbackNo");
                    if (!"1101000001".equals(kakaoInfo.get("compId")))
                        callbackNo = "0316302298";

                    String msg = "[SK하이닉스]" + kakaoInfo.get("receiveEmpNm").toString() + "님";
                    msg += "예약완료.코로나 관련증상,접촉자 입문불가.신고:bpshe.skhynix.com/covid";

                    // 주석처리 2023-06-09
                    //					RequestWrapModel<KakaoMessageDTO> wrapParams = new RequestWrapModel<>();
                    //					KakaoMessageDTO kakaoMessageDTO = new KakaoMessageDTO();
                    //					kakaoMessageDTO.setKTemplateCode("SJT_066358");
                    //					kakaoMessageDTO.setSubject("방문예약 신청 반려");
                    //					kakaoMessageDTO.setDstaddr(smsNo);
                    //					kakaoMessageDTO.setCallback(callbackNo);
                    //					kakaoMessageDTO.setText(msg);
                    //					kakaoMessageDTO.setText2(msg);
                    //					kakaoMessageDTO.setKAttach("");
                    //					kakaoMessageDTO.setEmpId(kakaoInfo.get("sendEmpId"));
                    //
                    //					wrapParams.setParams(kakaoMessageDTO);
                    //					commonApiClient.sendKakaoMessage(wrapParams);
                    // ================= NOTE: SMS(kakao) 발송 종료 =======================
                }
            }

            if (kakaoList != null && kakaoList.size() > 0) {
                Map<String, String> kakaoInfo = kakaoList.get(0);
                if ("V".equals(kakaoInfo.get("gbns")) || "F".equals(kakaoInfo.get("gbns"))) {
                    // ================= NOTE: SMS(kakao) 발송 시작 =======================
                    String smsNo = kakaoInfo.get("callbackNo").replaceAll("-", "");

                    String msgGbn = "";
                    if (kakaoInfo.get("gbns").equals("V")) {
                        msgGbn = "VIP 방문예약";
                    }
                    else {
                        msgGbn = "가족 방문예약";
                    }

                    String msg = kakaoInfo.get("sendEmpNm") + "님  " + kakaoInfo.get("visitDay");
                    msg += "(" + kakaoInfo.get("compNm") + ") " + msgGbn + " 접수가 반려되었습니다. e-Security";

                    // 주석처리 2023-06-09
                    //					RequestWrapModel<KakaoMessageDTO> wrapParams = new RequestWrapModel<>();
                    //					KakaoMessageDTO kakaoMessageDTO = new KakaoMessageDTO();
                    //					kakaoMessageDTO.setKTemplateCode("SJT_066359");
                    //					kakaoMessageDTO.setSubject("VIP/가족 방문예약 반려");
                    //					kakaoMessageDTO.setDstaddr(smsNo);
                    //					kakaoMessageDTO.setCallback("");
                    //					kakaoMessageDTO.setText(msg);
                    //					kakaoMessageDTO.setText2(msg);
                    //					kakaoMessageDTO.setKAttach("");
                    //
                    //					wrapParams.setParams(kakaoMessageDTO);
                    //					commonApiClient.sendKakaoMessage(wrapParams);
                    // ================= NOTE: SMS(kakao) 발송 종료 =======================
                }
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }

    @Override
    public boolean deleteRestrictVisitVisitor(Map<String, Object> paramMap) {
        boolean result = true;

        // 방문객 삭제
        repository.deleteRestrictVisitVisitor(paramMap);
        // 방문객 정보 업데이트(RESY_YN)
        repository.updateRestrictVisitResolve(paramMap);
        repository.deleteRestrictVisitManResolveHist(paramMap);
        repository.updateRestrictVisitResolveHist(paramMap);

        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean insertReserveVisitFamApproval(Map<String, Object> paramMap) {
        boolean result = false;
        final String DATE_FORMAT = "yyyyMMdd";

        try {
            // 결재정보
            ApprovalDTO approval = objectMapper.convertValue(paramMap.get("approval"), ApprovalDTO.class);
            // html map (기본정보)
            Map<String, Object> htmlMap = objectMapper.convertValue(paramMap.get("htmlMap"), HashMap.class);
            List<SavedApproverLineDTO> requestLine = List.copyOf(approval.getSavedRequestApproverLine());
            List<SavedApproverLineDTO> permitLine = List.copyOf(approval.getSavedPermitApproverLine());

            // 신청 가족 목록
            List<Map<String, Object>> famList = objectMapper.convertValue(paramMap.get("famList"), List.class);

            // 방문예약ID 생성
            Integer vstApplNo = repository.selectVstApplNo();
            paramMap.put("vstApplNo", vstApplNo);
            paramMap.put("schemaNm", approval.getSchemaNm());

            // 상태값 설정 => 접수완료
            paramMap.put("applStat", "Z0331002");

            // 자가결재
            if ("Y".equals(paramMap.get("selfYn").toString())) {
                // 상태값 설정 => 결재완료
                paramMap.put("applStat", "Z0331005");
                // 승인 (팀장이상) 기능 처리 시 요청부서 결재선 제거
                requestLine = List.of();
            }

            // 실 결재 진행 여부
            boolean isApproval = (requestLine != null && requestLine.size() > 0) || (permitLine != null && permitLine.size() > 0);

            // 마지막 사용자ID
            String lastIoEmpId = "";

            for (int i = 0; i < famList.size(); i++) {
                Map<String, Object> famInfo = famList.get(i);

                // 미체크 항목 bypass
                if ("N".equals(famInfo.get("checkYn").toString()))
                    continue;

                famInfo.put("acIp", paramMap.get("acIp"));
                famInfo.put("crtBy", paramMap.get("crtBy"));
                famInfo.put("vstApplNo", paramMap.get("vstApplNo"));
                famInfo.put("ioCompId", "FAMILY");

                repository.insertFamReserveVisitIoemp(famInfo);
                // 발급 사용자ID
                String ioEmpId = famInfo.get("ioEmpId").toString();

                famInfo.put("vstCompId", paramMap.get("vstCompId"));
                famInfo.put("vstStrtDt", paramMap.get("vstStrtDt"));
                famInfo.put("vstEndDt", paramMap.get("vstEndDt"));
                famInfo.put("vstTm", paramMap.get("vstTm"));
                famInfo.put("vstObj", "");
                famInfo.put("vstRsn", paramMap.get("vstRsn"));
                famInfo.put("vstRsnNew", paramMap.get("vstRsnNew"));
                famInfo.put("vstBldg", "");
                famInfo.put("hpNo", "");

                if (i == (famList.size() - 1)) {
                    famInfo.put("parking", paramMap.get("parking"));
                    famInfo.put("carNo", paramMap.get("carNo"));
                    famInfo.put("carKnd", paramMap.get("carKnd"));

                    lastIoEmpId = ioEmpId;
                }
                else {
                    famInfo.put("parking", null);
                    famInfo.put("carNo", null);
                    famInfo.put("carKnd", null);
                }

                repository.insertReserveVisitMan(famInfo);

                try {
                    famInfo.put("gateId", "0");
                    famInfo.put("vstSeq", "1");
                    famInfo.put("ioknd", "0");
                    famInfo.put("ioCardno", "");
                    famInfo.put("inDt", "");
                    famInfo.put("outDt", "");
                    famInfo.put("delYn", "N");

                    // 기존 로직 재사용 => 개선 필요
                    Date dStart = new SimpleDateFormat(DATE_FORMAT).parse(famInfo.get("vstStrtDt").toString().replace("-", ""));
                    Date dEnd = new SimpleDateFormat(DATE_FORMAT).parse(famInfo.get("vstEndDt").toString().replace("-", ""));
                    SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
                    Calendar sCal = new GregorianCalendar();
                    sCal.setTime(dStart);
                    Calendar eCal = new GregorianCalendar();
                    eCal.setTime(dEnd);
                    long diffMillis = eCal.getTimeInMillis() - sCal.getTimeInMillis();
                    long diff = diffMillis / (24 * 60 * 60 * 1000);

                    Date nextDay = new Date();
                    if (diff > 0) {
                        int k = 0;
                        while (diff >= k) {
                            if (k == 0) {
                                nextDay = sCal.getTime();
                            }
                            else {
                                sCal.add(Calendar.DATE, 1);
                                nextDay = sCal.getTime();
                            }
                            famInfo.put("vstDt", formatter.format(nextDay));
                            repository.insertVipReserveVisitManGate(famInfo);

                            k++;
                        }
                    }
                    else {
                        nextDay = sCal.getTime();
                        famInfo.put("vstDt", formatter.format(nextDay));
                        repository.insertVipReserveVisitManGate(famInfo);
                    }
                } catch (Exception ex) {
                    // bypass
                    log.error(ex.getMessage());
                }
            }

            paramMap.put("ioCompId", "FAMILY");
            paramMap.put("ioEmpId", lastIoEmpId);
            paramMap.put("vstrCnt", famList.size());
            paramMap.put("vipYn", "N");
            paramMap.put("delYn", "N");

            // 방문예약 정보 등록
            repository.insertFamReserveVisitVst(paramMap);

            // 상신 처리
            if (isApproval) {
                approval.setLid(vstApplNo);
                approval.setHtmlMap(htmlMap);

                log.info("가족방문예약 결재 => {}", approval);
                // 결재처리
                ApprovalDocDTO approvalDoc = approvalService.insertApproval(approval);

                paramMap.put("docId", approvalDoc.getDocId());

                // 결재문서ID 업데이트 => 방문예약
                repository.updateVipReserveVisitVstDocId(paramMap);
                // 자가결재
            }
            else {
                // 차량 연동 IF
                this.processReserveCarIf(vstApplNo);
            }

            // 처리 완료
            result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> selectFamReserveVisit(String empId) {
        return repository.selectFamReserveVisit(empId);
    }

    @Override
    public List<Map<String, Object>> selectFamReserveVisitViewFamList(Map<String, Object> paramMap) {
        return repository.selectFamReserveVisitViewFamList(paramMap);
    }

    @Override
    public Map<String, Object> selectFamReserveVisitView(Map<String, Object> paramMap) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("re", repository.selectFamReserveVisitViewRe(paramMap));
        resultMap.put("info", repository.selectFamReserveVisitViewInfo(paramMap));

        return resultMap;
    }

    @Override
    public List<Map<String, Object>> selectVipReserveVisitParnterList() {
        return repository.selectVipReserveVisitParnterList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean insertReserveVisitVipApproval(Map<String, Object> paramMap) {
        boolean result = false;
        final String DATE_FORMAT = "yyyyMMdd";

        try {
            // 결재정보
            ApprovalDTO approval = objectMapper.convertValue(paramMap.get("approval"), ApprovalDTO.class);
            // html map (기본정보)
            Map<String, Object> htmlMap = objectMapper.convertValue(paramMap.get("htmlMap"), HashMap.class);
            List<SavedApproverLineDTO> requestLine = List.copyOf(approval.getSavedRequestApproverLine());
            List<SavedApproverLineDTO> permitLine = List.copyOf(approval.getSavedPermitApproverLine());

            // 방문객 목록
            List<Map<String, Object>> visitorList = objectMapper.convertValue(paramMap.get("visitorList"), List.class);
            // 출입건물 목록
            List<Map<String, Object>> buildList = objectMapper.convertValue(paramMap.get("buildList"), List.class);
            // 차량 목록
            List<Map<String, Object>> carList = objectMapper.convertValue(paramMap.get("carList"), List.class);

            // 방문예약ID 생성
            Integer vstApplNo = repository.selectVstApplNo();
            paramMap.put("vstApplNo", vstApplNo);
            paramMap.put("schemaNm", approval.getSchemaNm());

            // 상태값 설정 => 접수완료
            paramMap.put("applStat", "Z0331002");

            // 실 결재 진행 여부
            boolean isApproval = (requestLine != null && requestLine.size() > 0) || (permitLine != null && permitLine.size() > 0);

            // 자가결재
            if (!isApproval) {
                // 상태값 설정 => 결재완료
                paramMap.put("applStat", "Z0331005");
            }

            paramMap.put("ioCompId", "VIP");
            paramMap.put("empNm", "VIP 방문예약");
            paramMap.put("nation", "Z0011001");
            // 등록 => IO_EMP
            repository.insertVipReserveVisitIoemp(paramMap);

            paramMap.put("vstrCnt", "1");
            paramMap.put("vipYn", "Y");
            paramMap.put("delYn", "N");

            // 등록 => IO_VST
            repository.insertVipReserveVisitVst(paramMap);

            // 수정 => IO_VIP_NETWORK
            if ("Y".equals(paramMap.get("networkagree"))) {
                repository.updateVipNetwork(paramMap);
            }

            paramMap.put("vstObj", "");
            paramMap.put("hpNo", "");
            paramMap.put("parking", "");
            paramMap.put("carNo", "");
            paramMap.put("carKnd", "");
            // 등록 => IO_VST_MAN
            repository.insertReserveVisitMan(paramMap);

            try {
                paramMap.put("gateId", "0");
                paramMap.put("vstSeq", "1");
                paramMap.put("ioknd", "0");
                paramMap.put("ioCardno", "");
                paramMap.put("inDt", "");
                paramMap.put("outDt", "");
                paramMap.put("delYn", "N");

                // 기존 로직 재사용 => 개선 필요
                Date dStart = new SimpleDateFormat(DATE_FORMAT).parse(paramMap.get("vstStrtDt").toString().replace("-", ""));
                Date dEnd = new SimpleDateFormat(DATE_FORMAT).parse(paramMap.get("vstEndDt").toString().replace("-", ""));
                SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
                Calendar sCal = new GregorianCalendar();
                sCal.setTime(dStart);
                Calendar eCal = new GregorianCalendar();
                eCal.setTime(dEnd);
                long diffMillis = eCal.getTimeInMillis() - sCal.getTimeInMillis();
                long diff = diffMillis / (24 * 60 * 60 * 1000);

                Date nextDay = new Date();
                if (diff > 0) {
                    int k = 0;
                    while (diff >= k) {
                        if (k == 0) {
                            nextDay = sCal.getTime();
                        }
                        else {
                            sCal.add(Calendar.DATE, 1);
                            nextDay = sCal.getTime();
                        }
                        paramMap.put("vstDt", formatter.format(nextDay));
                        repository.insertVipReserveVisitManGate(paramMap);

                        k++;
                    }
                }
                else {
                    nextDay = sCal.getTime();
                    paramMap.put("vstDt", formatter.format(nextDay));
                    repository.insertVipReserveVisitManGate(paramMap);
                }
            } catch (Exception ex) {
                // bypass
                log.error(ex.getMessage());
            }

            String vipGu = paramMap.get("vipGu").toString();
            if (carList != null && carList.size() > 0 && !"C".equals(vipGu) && !("B".equals(vipGu) && "1102000001".equals(paramMap.get("vstCompId")))) {
                for (Map<String, Object> carMap : carList) {
                    carMap.put("vstApplNo", paramMap.get("vstApplNo"));
                    carMap.put("acIp", paramMap.get("acIp"));
                    carMap.put("crtBy", paramMap.get("crtBy"));
                    // 등록 => IO_VIP_CAR_INFO
                    repository.insertReserveVisitCar(carMap);
                }
            }

            if (visitorList != null && visitorList.size() > 0) {
                for (Map<String, Object> visitorMap : visitorList) {
                    visitorMap.put("vstApplNo", paramMap.get("vstApplNo"));
                    visitorMap.put("crtBy", paramMap.get("crtBy"));
                    // 등록 => IO_VST_MAN_VIP
                    repository.insertVipReserveVisitManVst(visitorMap);
                }
            }

            if (buildList != null && buildList.size() > 0) {
                for (Map<String, Object> buildMap : buildList) {
                    buildMap.put("vstApplNo", paramMap.get("vstApplNo"));
                    buildMap.put("ioEmpId", paramMap.get("ioEmpId"));
                    buildMap.put("acIp", paramMap.get("acIp"));
                    buildMap.put("crtBy", paramMap.get("crtBy"));
                    // 등록 => IO_VST_MAN_VIP
                    repository.insertReserveVisitBuild(buildMap);
                }
            }

            // 상신 처리
            if (isApproval) {
                approval.setLid(vstApplNo);
                approval.setHtmlMap(htmlMap);

                log.info("VIP방문예약 결재 => {}", approval);
                // 결재처리
                ApprovalDocDTO approvalDoc = approvalService.insertApproval(approval);

                paramMap.put("docId", approvalDoc.getDocId());

                // 결재문서ID 업데이트 => 방문예약
                repository.updateVipReserveVisitVstDocId(paramMap);
            }

            // 자가결재
            if (!isApproval) {
                // 차량 연동 IF
                this.processReserveCarIf(vstApplNo);
            }

            // 처리 완료
            result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Map<String, Object> selectVipReserveVisitView(Map<String, Object> paramMap) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> reMap = repository.selectVipReserveVisitViewRE(paramMap);
        resultMap.put("re", reMap);
        paramMap.put("ioEmpId", reMap.get("ioEmpId"));
        resultMap.put("info", repository.selectVipReserveVisitViewInfo(paramMap));
        resultMap.put("carList", repository.selectVipReserveVisitCarList(paramMap));
        resultMap.put("visitorList", repository.selectVipReserveVisitManList(paramMap));
        resultMap.put("buildList", repository.selectVipReserveVisitBuildingList(paramMap));

        return resultMap;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean insertOrgVisitApproval(Map<String, Object> paramMap) {
        boolean result = false;

        try {
            // 결재정보
            ApprovalDTO approval = objectMapper.convertValue(paramMap.get("approval"), ApprovalDTO.class);
            // html map (기본정보)
            Map<String, Object> htmlMap = objectMapper.convertValue(paramMap.get("htmlMap"), HashMap.class);
            List<SavedApproverLineDTO> requestLine = List.copyOf(approval.getSavedRequestApproverLine());
            List<SavedApproverLineDTO> permitLine = List.copyOf(approval.getSavedPermitApproverLine());

            // 차량 목록
            List<Map<String, Object>> carList = objectMapper.convertValue(paramMap.get("carList"), List.class);

            // 상태값 설정
            paramMap.put("apprResult", "0");

            // 실 결재 진행 여부
            boolean isApproval = (requestLine != null && requestLine.size() > 0) || (permitLine != null && permitLine.size() > 0);
            // 자가결재의 경우 완료로 설정
            if (!isApproval)
                paramMap.put("apprResult", "1");

            // 단체방문 정보 등록
            repository.insertIoGroup(paramMap);
            // 단체방문ID
            Integer groupNo = (Integer) paramMap.get("groupNo");

            // 차량정보 등록 (분당 제외)
            if (carList != null && carList.size() > 0 && !"1108000001".equals(paramMap.get("vstCompId"))) {
                for (Map<String, Object> carInfo : carList) {
                    carInfo.put("groupNo", groupNo);
                    carInfo.put("crtBy", paramMap.get("crtBy"));
                    carInfo.put("acIp", paramMap.get("acIp"));
                    // 차량정보 등록
                    repository.insertIoGroupCar(carInfo);
                }
            }

            // 상신 처리
            if (isApproval) {
                approval.setLid(groupNo);
                approval.setHtmlMap(htmlMap);

                log.info("단체방문 결재 => {}", approval);
                // 결재처리
                ApprovalDocDTO approvalDoc = approvalService.insertApproval(approval);

                paramMap.put("docId", approvalDoc.getDocId());

                // 결재문서ID 업데이트
                repository.updateOrgVisitDocId(paramMap);
            }
            else {
                // 자가결재
                // 단체방문예약 후처리 => 차량출입IF
                this.reserveVisitOrgApprovalPost(groupNo);
            }

            // 처리 완료
            result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> selectOrgVisitList(Map<String, Object> paramMap) {
        return repository.selectOrgVisitList(paramMap);
    }

    @Override
    public Map<String, Object> selectOrgVisitView(Map<String, Object> paramMap) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("info", repository.selectOrgVisitView(paramMap));
        resultMap.put("carList", repository.selectOrgVisitCarList(paramMap));
        return resultMap;
    }

    public void processReserveNacIf(Integer vstApplNo) {
        Integer nacChk = repository.selectVisitNacIfCount(vstApplNo);
        if (nacChk > 0) {
            List<Map<String, String>> nacList = repository.selectVisitNacIfInfoList(vstApplNo);
            log.info("[방문예약 후처리] NAC연동 - processReserveNacIf => nacList : {}", nacList);

            int nacCnt = nacList.size();
            if (nacList != null && nacCnt > 0) {
                for (int i = 0; i < nacList.size(); i++) {
                    Map<String, String> nacData = nacList.get(0);
                    // 보안프로그램 인스톨 설치 유무 2014.03.05
                    // 방문일자와 반출예정일차이 8일 이상이면 보안 프로그램 설치 할 수있도록 NAC I/F 전송
                    // 2017.11.30 8일 -> 30일로 기준 변경됨
                    int betweenDt = Integer.parseInt(nacData.get("BETWEEN_DT")); // 방문일자와 반출예정일차이
                    if (betweenDt > 30) {
                        nacData.put("INSTALL_YN", "Y");
                    }
                    else {
                        nacData.put("INSTALL_YN", "N");
                    }

                    String macA = ObjectUtils.defaultIfNull(nacData.get("MAC_ADDR"), "").toUpperCase();
                    String wirA = ObjectUtils.defaultIfNull(nacData.get("WIRELESS_MAC_ADDR"), "").toUpperCase();

                    List<Map<String, Object>> uList = new ArrayList<>();

                    try {
                        // 청주
                        if ("1102000001".equals(nacData.get("COMP_ID")) || "1105000001".equals(nacData.get("COMP_ID")) || "1106000001".equals(nacData.get("COMP_ID"))) {
                            // IF Data => CAlder
                            log.info("[방문예약 후처리] NAC연동 - processReserveNacIf - calderRepository.selectVisitNacIfView IF 실행");
                            uList = calderRepository.selectVisitNacIfView(nacData.get("IO_EMP_ID"));
                            log.info("[방문예약 후처리] NAC연동 - processReserveNacIf - calderRepository.selectVisitNacIfView IF 실행 결과 건수 = {}", uList.size());
                            // 이천
                        }
                        else {
                            // IF Data => IAlder
                            log.info("[방문예약 후처리] NAC연동 - processReserveNacIf - calderRepository.selectVisitNacIfView IF 실행");
                            uList = ialderRepository.selectVisitNacIfView(nacData.get("IO_EMP_ID"));
                            log.info("[방문예약 후처리] NAC연동 - processReserveNacIf - calderRepository.selectVisitNacIfView IF 실행 결과 건수 = {}", uList.size());
                        }
                    } catch (Exception ex) {
                        log.error("[방문예약 후처리] NAC연동 - processReserveNacIf - calderRepository.selectVisitNacIfView ERROR : {}", ex.getMessage());
                        // log 출력 후 bypass
                    }

                    int uCnt = uList.size();
                    if (uCnt > 0) {
                        nacData.put("ACTION", "1");
                    }
                    else {
                        nacData.put("ACTION", "0");
                    }

                    // 주석처리 2023-06-09
                    if ("Global Staff".equals(nacData.get("IO_COMP_NM"))) {
                        log.info("[방문예약 후처리] NAC연동 - processReserveNacIf => fnGenianGsNode");
                        //						this.fnGenianGsNode(nacData, macA, wirA);
                    }
                    else {
                        log.info("[방문예약 후처리] NAC연동 - processReserveNacIf => fn_genian");
                        //						this.fnGenian(nacData, macA, wirA);
                    }
                }
            }
        }
    }

    //	private void fnGenian(Map<String, String> nacData, String macAddr, String wirmacAddr) {
    //		int sendalarm = 0;
    //
    //		genian.GnUserT uinfo = new genian.GnUserT();
    //		IntHolder retcode = new IntHolder();
    //		StringHolder retmsg = new StringHolder();
    //		genian.GENIANLocator locator = new genian.GENIANLocator();
    //
    //		genian.UsercustomFieldT usercustom = new genian.UsercustomFieldT();
    //		try {
    //			/* 사용자 사용자정의 필드 */
    //			usercustom.setUserCustom01(wirmacAddr);
    //			usercustom.setUserCustom02("");
    //			usercustom.setUserCustom03("");
    //			usercustom.setUserCustom04(nacData.get("INSTALL_YN")); // 반출예정일자가 8일 이상 되면 보안프로그램설치
    //			usercustom.setUserCustom05("");
    //			usercustom.setUserCustom06("");
    //			usercustom.setUserCustom07("");
    //			usercustom.setUserCustom08("");
    //			usercustom.setUserCustom09("");
    //			uinfo.setUsercustom(usercustom);
    //
    //			Date nowDay = new Date();
    //			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
    //
    //			Date dStart = new SimpleDateFormat("yyyyMMdd").parse(formatter.format(nowDay));
    //			Date dEnd = new SimpleDateFormat("yyyyMMdd").parse(nacData.get("OUT_SCHD_DT").replace("-", ""));
    //
    //			Calendar sCal = new GregorianCalendar();
    //			sCal.setTime(dStart);
    //			Calendar eCal = new GregorianCalendar();
    //			eCal.setTime(dEnd);
    //			long diffMillis = eCal.getTimeInMillis() - sCal.getTimeInMillis();
    //			long diff = diffMillis / (24 * 60 * 60 * 1000);
    //			String depts = "";
    //			String u_type = "GUEST";
    //			if (diff >= 30) {
    //				depts = "_1";
    //				u_type = "USER";
    //			}
    //
    //			uinfo.setUserId(nacData.get("IO_EMP_ID")); /* 사용자계정ID */
    //			uinfo.setUserName(nacData.get("IO_EMP_NM")); /* 사용자이름 */
    //			uinfo.setUserPassword(nacData.get("JUMIN_NO")); /* 비밀번호 */
    //			uinfo.setUserDesc("\uc2e0\uccad\ubd80\uc11c : " + nacData.get("CO_DEPT_NM") + " (" + nacData.get("EMP_NM")
    //					+ "), \ucd9c\uc785\uac74\ubb3c : "
    //					+ nacData.get("GATE_NM")); /* 신청부서(\uc2e0\uccad\ubd80\uc11c),출입건물(\ucd9c\uc785\uac74\ubb3c) */
    //			uinfo.setUserTel(""); /* 전화번호 */
    //			uinfo.setUserMobile(nacData.get("HP_NO")); /* 휴대폰번호 */
    //			uinfo.setUserEmail(nacData.get("IO_EMP_ID")); /* 이메일 */
    //			uinfo.setUserAddress(""); /* 주소 */
    //			uinfo.setUserCompany(nacData.get("IO_COMP_NM") + depts); /* 소속 */
    //			uinfo.setUserDept(""); /* 부서 */
    //			uinfo.setUserPosition(""); /* 직급 */
    //			uinfo.setUserApppurpose(u_type); /* 용도 일반계정 : USER, 임시 : GUEST(def) */
    //			uinfo.setUserExpiretime(nacData.get("OUT_SCHD_DT") + " 23:59:59"); /* 사용자계정 만료시각 */
    //			uinfo.setUserAuthallowmac(macAddr); /* 인증허용 MAC주소 */
    //			uinfo.setUserSync(1); /* 계정생성타입, 정보수정가능여부. 1:사용자정보수정불가능, 2:비밀번호만 수정가능, 3:모든 사용자정보 수정가능 */
    //			uinfo.setUserAuthallowip(""); /* 인증허용 IP */
    //			uinfo.setUserStatus(1); /* 계정상태. 0: 사용중지, 1: 정상 */
    //			uinfo.setUserType(1); /* 계정타입. */
    //			uinfo.setUserAuthstatus("");
    //			uinfo.setUserAuthlast("");
    //			uinfo.setUserCreated("");
    //			uinfo.setUserNodeid("");
    //			uinfo.setUserLastpwchange("");
    //			uinfo.setUserLostauthcode("");
    //
    //			String address = "";
    //			String ip = "";
    //			boolean isProd = environment.acceptsProfiles(Profiles.of("prd"));
    //			if (isProd) { // 운영모드
    //				if ("1102000001".equals(nacData.get("COMP_ID")) || "1105000001".equals(nacData.get("COMP_ID")) || "1106000001".equals(nacData.get("COMP_ID"))) {
    //					ip = nacIpCj; // 청주
    //				} else {
    //					ip = nacIpIc; // 이천
    //				}
    //			} else {
    //				ip = nacIpDev; // 테스트
    //			}
    //			address = "http://" + ip + "/genian";
    //
    //			Boolean pass = true;
    //			if (macAddr.indexOf(":") < 0 || (!wirmacAddr.equals("") && wirmacAddr.indexOf(":") < 0)) {
    //				pass = false;
    //			}
    //
    //			if (!"".equals(address)) {
    //				locator.setGENIANEndpointAddress(address);
    //
    //				Integer action = null;
    //				if (nacData.get("ACTION") != null)
    //					action = Integer.parseInt(nacData.get("ACTION"));
    //
    //				log.info("--------------------------------------------------------");
    //				log.info("[방문예약 후처리] NAC연동 - fnGenian - action : " + action);
    //				log.info("[방문예약 후처리] NAC연동 - fnGenian - macAddrWireless : " + uinfo.getUsercustom().getUserCustom01());
    //				log.info("[방문예약 후처리] NAC연동 - fnGenian - secToolInstall Y/N : " + uinfo.getUsercustom().getUserCustom04());
    //				log.info("[방문예약 후처리] NAC연동 - fnGenian - uinfo info start ");
    //				log.info("[방문예약 후처리] NAC연동 - fnGenian - email : " + uinfo.getUserId());
    //				log.info("[방문예약 후처리] NAC연동 - fnGenian - visitorName : " + uinfo.getUserName());
    //				log.info("[방문예약 후처리] NAC연동 - fnGenian - password : " + uinfo.getUserPassword());
    //				log.info("[방문예약 후처리] NAC연동 - fnGenian - userDesc : " + uinfo.getUserDesc());
    //				log.info("[방문예약 후처리] NAC연동 - fnGenian - phoneNo : " + uinfo.getUserMobile());
    //				log.info("[방문예약 후처리] NAC연동 - fnGenian - compName : " + uinfo.getUserCompany());
    //				log.info("[방문예약 후처리] NAC연동 - fnGenian - getUserExpiretime : " + uinfo.getUserExpiretime());
    //				log.info("[방문예약 후처리] NAC연동 - fnGenian - setUserAuthallowmac : " + uinfo.getUserAuthallowmac());
    //				log.info("[방문예약 후처리] NAC연동 - fnGenian - address : " + locator.getGENIANAddress());
    //				log.info("--------------------------------------------------------");
    //
    //				// NAC 개발서버(166.125.3.244) 연결불가로 인한 조건 추가
    //				if (isProd) {
    //					log.info("[방문예약 후처리] NAC연동 - fnGenian - locator.getGENIAN().gnUser 호출");
    //					if (pass) locator.getGENIAN().gnUser(action, uinfo, sendalarm, retcode, retmsg);
    //				} else {
    //					log.info("[방문예약 후처리] NAC연동 - fnGenian - bypass (개발환경)");
    //				}
    //
    //				log.info("---------------------- NAC I/F -------------------------");
    //				log.info("[방문예약 후처리] NAC연동 - fnGenian - retcode : " + retcode.value);
    //				log.info("[방문예약 후처리] NAC연동 - fnGenian - retmsg  : " + retmsg.value);
    //				log.info("--------------------------------------------------------");
    //
    //				Map<String, String> IF_nacData = new HashMap<>();
    //				IF_nacData.put("COMP_ID", nacData.get("COMP_ID"));
    //				IF_nacData.put("IO_EMP_ID", nacData.get("IO_EMP_ID"));
    //				IF_nacData.put("IO_EMP_NM", nacData.get("IO_EMP_NM"));
    //				IF_nacData.put("IO_COMP_NM", nacData.get("IO_COMP_NM") + depts);
    //				IF_nacData.put("URL_ADDRESS", address);
    //				IF_nacData.put("MAC_ADDRESS", macAddr);
    //				IF_nacData.put("WIR_ADDRESS", wirmacAddr);
    //				IF_nacData.put("PASS_VALUE", pass.toString());
    //				IF_nacData.put("RETCODE", String.valueOf(retcode.value));
    //				IF_nacData.put("RETMSG", retmsg.value);
    //				// 로그 등록
    //				repository.insertNacLogReg(IF_nacData);
    //			}
    //		} catch (Exception e) {
    //			log.error("[방문예약 후처리] NAC연동 - fnGenian - NAC I/F ERROR : {}", e.getMessage());
    //			throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
    //		}
    //	}

    //	private void fnGenianGsTag(Map<String, String> nacData, String macAddr, String wirmacAddr) {
    //		String address = "";
    //		boolean isProd = environment.acceptsProfiles(Profiles.of("prd"));
    //		if (isProd) { // 운영모드
    //			if ("1102000001".equals(nacData.get("COMP_ID")) || "1105000001".equals(nacData.get("COMP_ID")) || "1106000001".equals(nacData.get("COMP_ID"))) {
    //				address = nacIpCj; // 청주
    //			} else {
    //				address = nacIpIc; // 이천
    //			}
    //		} else {
    //			address = nacIpDev; // 테스트
    //		}
    //
    //		if (!"".equals(address)) {
    //			GenianClient clientTag = new GenianClient(address);
    //			// 1. 수행방식을 설정합니다. (1:설정, 0:해제)
    //			int action = 1;
    //
    //			// 2. 장비속성파라미터 항목을 설정합니다.
    //			GnMacPropertyT macproperty = new GnMacPropertyT();
    //
    //			// 2-1. 속성을 부여할 장비의 MAC을 설정합니다.(xx:xx:xx:xx:xx:xx)
    //			macproperty.setMac(macAddr);
    //			// 2-2. 사용자정의속성명을 설정합니다.(태그가 먼저 등록되어 있어야합니다.)
    //			macproperty.setPropertyname("MAC_EXCEPT");
    //
    //			String expireDate = nacData.get("OUT_SCHD_DT") + " 23:59:00";
    //			// 2-3. 속성이 자동 해제될 시간을 설정합니다.(YYYY-MM-DD hh:mm:ss 형식, 입력하지 않을시 태그에 설정되어있는 기간설정값대로 설정)
    //			macproperty.setExpiretime(expireDate);
    //
    //			// 2-4. 속성의 설명을 설정합니다.
    //			macproperty.setDesc("");
    //
    //			// 3. 리턴코드와 리턴메시지를 전달받을 객체를 생성합니다.
    //			IntHolder retcode = new IntHolder();
    //			StringHolder retmsg = new StringHolder();
    //
    //			try {
    //				// 4. TAG API를 수행합니다.
    //				if (isProd) {
    //					log.info("[방문예약 후처리] NAC연동 - fnGenianGsTag - clientTag.gnMACProperty 호출");
    //					clientTag.gnMACProperty(action, macproperty, retcode, retmsg);
    //				} else {
    //					log.info("[방문예약 후처리] NAC연동 - fnGenianGsTag - bypass (개발환경)");
    //				}
    //
    //				log.info("---------------------- NAC I/F -------------------------");
    //				log.info("[방문예약 후처리] NAC연동 - fnGenianGsTag - retcode : " + retcode.value);
    //				log.info("[방문예약 후처리] NAC연동 - fnGenianGsTag - retmsg  : " + retmsg.value);
    //				log.info("--------------------------------------------------------");
    //
    //				if (retcode.value == 0) {
    //					log.info("[방문예약 후처리] NAC연동 - fnGenianGsTag - 태그 등록 성공");
    //				} else if (retcode.value == 1) {
    //					log.info("[방문예약 후처리] NAC연동 - fnGenianGsTag - TAG 이미존재함");
    //				} else if (retcode.value == 2) {
    //					log.info("[방문예약 후처리] NAC연동 - fnGenianGsTag - TAG 잘못된 사용");
    //				} else if (retcode.value == 3) {
    //					log.info("[방문예약 후처리] NAC연동 - fnGenianGsTag - TAG 서버오류");
    //				}
    //			} catch (Exception ex) {
    //				log.error("[방문예약 후처리] NAC연동 - fnGenianGsTag - error = {}", ex.getMessage());
    //			}
    //		}
    //	}

    //	protected void fnGenianGsNode(Map<String, String> nacData, String macAddr, String wirmacAddr) {
    //		String address = "";
    //		boolean isProd = environment.acceptsProfiles(Profiles.of("prd"));
    //		if (isProd) { // 운영모드
    //			if ("1102000001".equals(nacData.get("COMP_ID")) || "1105000001".equals(nacData.get("COMP_ID")) || "1106000001".equals(nacData.get("COMP_ID"))) {
    //				address = nacIpCj; // 청주
    //			} else {
    //				address = nacIpIc; // 이천
    //			}
    //		} else {
    //			address = nacIpDev; // 테스트
    //		}
    //
    //		if (!"".equals(address)) {
    //
    //			GenianClient clientNode = new GenianClient(address);
    //			// 1. 수행방식을 설정합니다. (0:새로운노드, 1:노드삭제, 2:MAC주소가 일치하는 모든노드 삭제, 3:노드삭제금지 설정,
    //			// 4:노드삭제금지 해제, 5:노드사용자정의필드 갱신, 6:예약노드로 등록)
    //			int command = 0;
    //			int nodetype = 20;
    //			int permanent = 0;
    //
    //			// 2. 파라미터 항목을 설정합니다.
    //			GnNodeT reqnode = new GnNodeT();
    //
    //			// 2-1. 노드설명
    //			reqnode.setDesc("");
    //
    //			// 2-2. IP주소 (xxx.xxx.xxx.xxx)
    //			reqnode.setIpstring("30.30.30.30");
    //
    //			// 2-3. MAC주소 (xx:xx:xx:xx:xx:xx)
    //			reqnode.setMacaddr(macAddr);
    //
    //			// 2-4. 노드이름
    //			reqnode.setName("");
    //			// 2-5. 사용자정의 필드 설정
    //			NodecustomFieldT nodecustom = new NodecustomFieldT();
    //			nodecustom.setNlcustom01(""); // 2^0 : 1;
    //			nodecustom.setNlcustom02(""); // 2^1 : 2;
    //			nodecustom.setNlcustom03(""); // 2^2 : 4;
    //			nodecustom.setNlcustom04(""); // 2^3 : 8;
    //			nodecustom.setNlcustom05(""); // 2^4 : 16;
    //			nodecustom.setNlcustom06(""); // 2^5 : 32;
    //			nodecustom.setNlcustom07(""); // 2^6 : 64;
    //			nodecustom.setNlcustom08(""); // 2^7 : 128;
    //			nodecustom.setNlcustom09(""); // 2^8 : 256;
    //			nodecustom.setApplyfield(1); // 적용컬럼 설정
    //			reqnode.setNodecustom(nodecustom);
    //
    //			// 2-6. 노드타입
    //			/**
    //			 * GN_NODETYPE_UNKNOWN = 10, GN_NODETYPE_PC = 20, GN_NODETYPE_MOBILE = 21,
    //			 * GN_NODETYPE_SERVER = 40, GN_NODETYPE_NETDEV = 60, GN_NODETYPE_AP = 61,
    //			 * GN_NODETYPE_ROUTER = 62, GN_NODETYPE_SWITCH = 63, GN_NODETYPE_SECURITY = 64,
    //			 * GN_NODETYPE_PRINTER = 80, GN_NODETYPE_OTHER = 100
    //			 */
    //			reqnode.setNodetype(nodetype);
    //
    //			// 2-7. 노드삭제금지설정여부 (0: 삭제금지 설정안됨, 1: 삭제금지 설정됨)
    //			reqnode.setPermanent(permanent);
    //
    //			// 2-8. 노드 사용자 정의 속성
    //			reqnode.setPropertyname("propertyname");
    //
    //			// 2-9. 센서 IP주소 (xxx.xxx.xxx.xxx와 같은형식)
    //			reqnode.setSensoripstring(address);
    //
    //			// 3. 리턴코드와 리턴메시지를 전달받을 객체를 생성합니다.
    //			IntHolder retcode = new IntHolder();
    //			StringHolder retmsg = new StringHolder();
    //			try {
    //				// 4. API를 수행합니다. => 개발환경 실행 제한 처리
    //				if (isProd) {
    //					log.info("[방문예약 후처리] NAC연동 - fnGenianGsNode - clientNode.gnNode 호출");
    //					clientNode.gnNode(command, reqnode, retcode, retmsg);
    //				} else {
    //					log.info("[방문예약 후처리] NAC연동 - fnGenianGsNode - bypass (개발환경)");
    //				}
    //
    //				log.info("---------------------- NAC I/F -------------------------");
    //				log.info("[방문예약 후처리] NAC연동 - fnGenianGsNode - retcode : " + retcode.value);
    //				log.info("[방문예약 후처리] NAC연동 - fnGenianGsNode - retmsg  : " + retmsg.value);
    //				log.info("--------------------------------------------------------");
    //
    //				if (retcode.value == 0) {
    //					log.info("[방문예약 후처리] NAC연동 - fnGenianGsNode - 노드 등록 성공");
    //					// 태그 등록 시작
    //					this.fnGenianGsTag(nacData, macAddr, wirmacAddr);
    //				} else if (retcode.value == 1) {
    //					log.warn("[방문예약 후처리] NAC연동 - fnGenianGsNode - 노드 실패(잘못된 사용)");
    //				} else if (retcode.value == 2) {
    //					log.warn("[방문예약 후처리] NAC연동 - fnGenianGsNode - 노드 실패(서버오류)");
    //				}
    //			} catch (Exception e) {
    //				log.error("[방문예약 후처리] NAC연동 - fnGenianGsNode - GS NAC I/F ERROR : " + e.getMessage());
    //				throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
    //			}
    //		}
    //	}

    @Override
    public void reserveVisitOrgApprovalPost(Integer lid) throws Exception {
        boolean isProd = environment.acceptsProfiles(Profiles.of("prd"));

        try {
            // VIP I/F 차량출입 => fmVip_ICSpmsIF
            List<SendSpmsDTO> groupIcList = repository.selectGroupIcSpmsIfInfoList(lid);
            for (SendSpmsDTO IF_RequestData : groupIcList) {
                IF_RequestData.setDivision("8");
                IF_RequestData.setMemberType("88");
                // 이천 LPR 차량 연계 => Destination = 1101000001
                log.info("[단체방문예약 후처리] 이천VIP LPR 차량 연동 처리");
                log.info("[단체방문예약 후처리] if data => {}", ReflectionToStringBuilder.toString(IF_RequestData));
                if (isProd)
                    icVehicleHaengbokRepository.sendICSpmsInsertMemberInner(IF_RequestData);
            }

            // 청주 차량 연계 IF => fm_SendCJSPMS
            List<SendSpmsDTO> groupCjList = repository.selectSendCjSpmsGroupCarInfoList(lid);
            for (SendSpmsDTO IF_RequestData : groupCjList) {
                // VIP/단체방문 (청주 총무팀 요청에 의해 VIP 일 경우 청주1,2,3공장 모두 등록함.)
                IF_RequestData.setDestination("F0029|F0030|F0031|F0032|F0033|F0034|F0035|F0036|F0037|F0038|F0039|F0040|F0041|F0042|F0043|F0044|F0045|F0046|F0047|F0048|F0049|F0050|F0051|F0052|F0053|F0054|F0055|F0056|F0057");
                log.info("[단체방문예약 후처리] 청주VIP LPR 차량 연동 처리");
                log.info("[단체방문예약 후처리] if data => {}", ReflectionToStringBuilder.toString(IF_RequestData));
                if (isProd)
                    cjVehicleCampusRepository.sendCJSpmsInsertMemberInner(IF_RequestData);
            }
        } catch (Exception e) {
            log.error("[단체방문예약 후처리] 차량출입IF ERROR : {}", e.getMessage());
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
    }

    @Override
    public Boolean saveVipIoCardno(Map<String, Object> paramMap) {
        try {
            Integer vstApplNo = objectMapper.convertValue(paramMap.get("vstApplNo"), Integer.class);
            String acIp = objectMapper.convertValue(paramMap.get("acIp"), String.class);
            String modBy = objectMapper.convertValue(paramMap.get("modBy"), String.class);

            @SuppressWarnings("unchecked")
            List<HashMap<String, Object>> dataList = objectMapper.convertValue(paramMap.get("data"), List.class);

            if (dataList != null) {
                for (HashMap<String, Object> item : dataList) {
                    item.put("vstApplNo", vstApplNo);
                    item.put("acIp", acIp);
                    item.put("modBy", modBy);
                    repository.updateVipIoCardno(item);
                }
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return true;
    }

    @Override
    public Integer selectReserveVisitVstApplNo() {
        return repository.selectReserveVisitVstApplNo();
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean insertGlobalStaffVstMan(Map<String, Object> paramMap) {
        boolean result = false;

        try {
            // 전산저장장치 목록
            List<Map<String, Object>> storageList = objectMapper.convertValue(paramMap.get("storageList"), List.class);
            String ioEmpId = paramMap.get("ioEmpId").toString();
            Integer vstApplNo = Integer.parseInt(paramMap.get("vstApplNo").toString());

            // 방문예약 방문객 정보 등록
            repository.insertReserveVisitVstMan(paramMap);

            if ("N".equals(repository.selectReserveVisitIoEmpGsCnt(ioEmpId))) {
                // IO_EMP 등록
                repository.insertReserveVisitIoEmp(paramMap);
            }
            else {
                // IO_EMP 수정
                repository.updateReserveVisitIoEmp(paramMap);
            }

            Integer vstrCnt = repository.selectReserveVisitVstManCnt(vstApplNo);
            paramMap.put("vstrCnt", vstrCnt);

            // 방문예약 정보 등록
            repository.insertReserveVisit(paramMap);

            // 전산저장장치 등록
            if (storageList != null && storageList.size() > 0) {
                for (Map<String, Object> storageData : storageList) {
                    storageData.put("vstApplNo", vstApplNo);
                    storageData.put("ioEmpId", ioEmpId);
                    storageData.put("acIp", paramMap.get("acIp"));
                    storageData.put("crtBt", paramMap.get("crtBt"));
                    storageData.put("outSchdDt", storageData.get("outSchdDt").toString().replaceAll("-", ""));
                    storageData.put("siteType", paramMap.get("siteType"));
                    repository.insertReserveVisitVstManIteqmt(storageData);
                }
            }

            // 처리 완료
            result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean insertReserveVisitGsApproval(Map<String, Object> paramMap) {
        boolean result = false;
        final String DATE_FORMAT = "yyyyMMdd";

        try {
            // 결재정보
            ApprovalDTO approval = objectMapper.convertValue(paramMap.get("approval"), ApprovalDTO.class);
            // html map (기본정보)
            Map<String, Object> htmlMap = objectMapper.convertValue(paramMap.get("htmlMap"), HashMap.class);
            List<SavedApproverLineDTO> requestLine = List.copyOf(approval.getSavedRequestApproverLine());
            List<SavedApproverLineDTO> permitLine = List.copyOf(approval.getSavedPermitApproverLine());

            // 방문객 목록
            List<Map<String, Object>> visitorList = objectMapper.convertValue(paramMap.get("visitorList"), List.class);

            // 방문예약ID 생성
            Integer vstApplNo = (Integer) paramMap.get("vstApplNo");
            paramMap.put("schemaNm", approval.getSchemaNm());

            // 상태값 설정 => 접수완료
            paramMap.put("applStat", "Z0331002");

            // 실 결재 진행 여부
            boolean isApproval = (requestLine != null && requestLine.size() > 0) || (permitLine != null && permitLine.size() > 0);

            // 자가결재
            if (!isApproval) {
                // 상태값 설정 => 결재완료
                paramMap.put("applStat", "Z0331005");
            }

            if (visitorList != null && visitorList.size() > 0) {
                for (Map<String, Object> visitorMap : visitorList) {
                    try {
                        visitorMap.put("gateId", "0");
                        visitorMap.put("vstSeq", "1");
                        visitorMap.put("ioknd", "0");
                        visitorMap.put("ioCardno", "");
                        visitorMap.put("inDt", "");
                        visitorMap.put("outDt", "");
                        visitorMap.put("delYn", "N");
                        visitorMap.put("acIp", paramMap.get("acIp"));
                        visitorMap.put("crtBy", paramMap.get("crtBy"));

                        // 기존 로직 재사용 => 개선 필요
                        Date dStart = new SimpleDateFormat(DATE_FORMAT).parse(visitorMap.get("vstStrtDt").toString().replace("-", ""));
                        Date dEnd = new SimpleDateFormat(DATE_FORMAT).parse(visitorMap.get("vstEndDt").toString().replace("-", ""));
                        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
                        Calendar sCal = new GregorianCalendar();
                        sCal.setTime(dStart);
                        Calendar eCal = new GregorianCalendar();
                        eCal.setTime(dEnd);
                        long diffMillis = eCal.getTimeInMillis() - sCal.getTimeInMillis();
                        long diff = diffMillis / (24 * 60 * 60 * 1000);

                        Date nextDay = new Date();
                        if (diff > 0) {
                            int k = 0;
                            while (diff >= k) {
                                if (k == 0) {
                                    nextDay = sCal.getTime();
                                }
                                else {
                                    sCal.add(Calendar.DATE, 1);
                                    nextDay = sCal.getTime();
                                }
                                visitorMap.put("vstDt", formatter.format(nextDay));
                                repository.insertVipReserveVisitManGate(visitorMap);

                                k++;
                            }
                        }
                        else {
                            nextDay = sCal.getTime();
                            visitorMap.put("vstDt", formatter.format(nextDay));
                            repository.insertVipReserveVisitManGate(visitorMap);
                        }
                    } catch (Exception ex) {
                        // bypass
                        log.error(ex.getMessage());
                    }
                }
            }

            // 상신 처리
            if (isApproval) {
                // 방문객 정보 설정
                visitorList = visitorList.stream().map(data -> {
                    // 전산 저장장치 정보 조회
                    List<Map<String, Object>> selectIOList = repository.selectReserveVisitSelectIOList(data);
                    selectIOList = selectIOList.stream().map(selectIO -> {
                        if (selectIO.get("openBit") != null) {
                            boolean openBit1 = false;
                            boolean openBit2 = false;
                            boolean openBit3 = false;
                            boolean openBit5 = false;
                            boolean openBit6 = false;

                            // open_bit 관련 view data 설정
                            BigInteger openBit = ((BigDecimal) selectIO.get("openBit")).toBigInteger();
                            if (openBit.and(BigInteger.valueOf(1)).compareTo(BigInteger.valueOf(0)) != 0)
                                openBit1 = true;
                            if (openBit.and(BigInteger.valueOf(2)).compareTo(BigInteger.valueOf(0)) != 0)
                                openBit2 = true;
                            if (openBit.and(BigInteger.valueOf(4)).compareTo(BigInteger.valueOf(0)) != 0)
                                openBit3 = true;
                            if (openBit.and(BigInteger.valueOf(16)).compareTo(BigInteger.valueOf(0)) != 0)
                                openBit5 = true;
                            if (openBit.and(BigInteger.valueOf(32)).compareTo(BigInteger.valueOf(0)) != 0)
                                openBit6 = true;
                            selectIO.put("openBit1", openBit1);
                            selectIO.put("openBit2", openBit2);
                            selectIO.put("openBit3", openBit3);
                            selectIO.put("openBit5", openBit5);
                            selectIO.put("openBit6", openBit6);
                        }
                        return selectIO;
                    }).collect(Collectors.toList());
                    data.put("selectIOList", selectIOList);
                    return data;
                }).collect(Collectors.toList());
                htmlMap.put("visitorList", visitorList);

                approval.setLid(vstApplNo);
                approval.setHtmlMap(htmlMap);

                log.info("GlobalStaff 방문예약 결재 => {}", approval);
                // 결재처리
                ApprovalDocDTO approvalDoc = approvalService.insertApproval(approval);

                paramMap.put("docId", approvalDoc.getDocId());
            }

            // 방문예약 정보 업데이트
            repository.updateReserveVisitVstStat(paramMap);

            // 자가결재
            if (!isApproval) {
                // 방문예약 전산저장장치 IF => fmReserve_NacIF
                this.processReserveNacIf(vstApplNo);
            }

            List<Map<String, Object>> mailList = repository.selectGlobalStaffMailSend(vstApplNo);

            // ================= NOTE: 메일 발송 시작 =======================
            String title = "The request for your business trip entrance ID has been completed.";
            String empId = paramMap.get("empId").toString();
            String schemaNm = "GS_VST";
            String acIp = paramMap.get("acIp") != null
                ? paramMap.get("acIp").toString()
                : "";

            StringBuffer content = new StringBuffer();
            content.append("					Precautions : <br>");
            content.append("					- You must possess your local employee ID when visiting.<br>");
            content.append("					- You will trade in your local employee ID for a Business trip entrance ID when entering the Icheon and Chungju site(Bundang : 4th floor helpdesk).<br>");
            content.append("					- The business trip entrance ID may be used for the shuttlebus, cafeteria and general office entrance(Please request to HQ Mgmt to gain Entrance for special locations such as LABs, Lines.)<br>");
            content.append("					- Cell phone cameras must be sealed when entering the security device and you must check-in with your ID when entering or exiting the building.<br>");
            content.append("					- Please refrain entering the facility with laptops, and laptops must be pre-approved with all necessary security programs installed before being carried in.<br>");
            content.append(" <br>#Reference<br> \n");
            content.append(" <table border=\"1\"  cellspacing=\"0\" cellpadding=\"5\" style=\"border-collapse:collapse; font-family:Malgun Gothic,  Dotum; font-size:14px;\"> \n");
            content.append(" <tr> \n");
            content.append(" <td bgcolor=\"#eeeeee\" align=\"center\"><b>Division</b></td> \n");
            content.append(" <td bgcolor=\"#eeeeee\"  align=\"center\" width=\"80%\"><b>Contact Person</b></td> \n");
            content.append(" </tr>	     \n");
            content.append(" <tr> \n");
            content.append(" <td align=\"center\" ><b>Security</b></td> \n");
            content.append(" <td align=\"left\" width=\"80%\"><span style=\"font-size:12px;\">Icheon - 최우리 선임 (Woori Choi, woori.choi@sk.com   ) <br> \n");
            content.append(" Chungju - 설미애 TL (MIAE SUL, miae.sul@sk.com)<br> \n");
            content.append(" Bundang - 신익채 TL (Ickchae-Shin, ickchae.shin@sk.com)<br> \n");
            content.append(" </span> \n");
            content.append(" </td> \n");
            content.append(" </tr>	     \n");
            content.append(" <tr> \n");
            content.append(" <td align=\"center\"><b>IT</b></td> \n");
            content.append(" <td align=\"left\" width=\"80%\"><span style=\"font-size:12px;\">NAC:  차호열 과장 (Hoyoul Cha, hoyeol.cha@partner.sk.com) <br> \n");
            content.append(" Firewall:  오대석 수석 (Oh Dae Suk, skhy.i0100557@partner.sk.com)전창용 선임 (jeon chang yong, skhy.i0100757@partner.sk.com) <br> \n");
            content.append(" Main Contact:  박현호 책임 (Hyunho Park, hyunho.park@sk.com)  \n");
            content.append(" </span> \n");
            content.append(" </td> \n");
            content.append(" </tr> \n");
            content.append(" </table>		 \n");
            content.append("</div>");

            for (Map<String, Object> mailInfo : mailList) {
                String message = "<div>";
                message += "Company name:  " + ObjectUtils.defaultIfNull(mailInfo.get("ioCompNm"), "") + "<br>";
                message += "Employee name:   " + ObjectUtils.defaultIfNull(mailInfo.get("empNm"), "") + "<br>";
                message += "Business trip term:  " + ObjectUtils.defaultIfNull(mailInfo.get("vstStrtDt"), "") + " - " + ObjectUtils.defaultIfNull(mailInfo.get("vstEndDt"), "") + "<br>";
                message += "Visit Business:  " + ObjectUtils.defaultIfNull(mailInfo.get("vstCompNm"), "") + "<br>";
                message += content.toString();

                String toEmail = ObjectUtils.defaultIfNull(mailInfo.get("emailAddr"), "").toString();

                mailing.sendMail(title, mailing.applyMailTemplate(title, message), toEmail, empId, schemaNm, "", acIp);
            }
            // ================= NOTE: 메일 발송 종료 =======================

            // 처리 완료
            result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public boolean deleteVisitVisitor(Map<String, Object> paramMap) {
        boolean result = true;

        // 방문객 삭제
        repository.deleteReserveVisitMan(paramMap);
        // 전산저장장치 삭제
        repository.deleteReserveVisitIteqmt(paramMap);

        // 방문객 건수 업데이트
        Integer vstrCnt = repository.selectReserveVisitVstManCnt((Integer) paramMap.get("vstApplNo"));
        paramMap.put("vstrCnt", vstrCnt);
        repository.updateReserveVisitVst(paramMap);

        return result;
    }

    @Override
    public boolean deleteReserveVisitIteqmt(Map<String, Object> paramMap) {
        return Objects.equals(repository.deleteReserveVisitIteqmt(paramMap), 1);
    }

    @Override
    public List<Map<String, Object>> selectAdmVipPartnerList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectAdmVipPartnerList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Integer selectAdmVipPartnerListCnt(Map<String, Object> paramMap) {
        Integer resultCnt = null;

        try {
            resultCnt = repository.selectAdmVipPartnerListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

    @Override
    public Boolean saveAdmVipPartner(Map<String, Object> paramMap) {
        try {
            int resultCnt = 0;
            String acIp = objectMapper.convertValue(paramMap.get("acIp"), String.class);

            @SuppressWarnings("unchecked")
            List<HashMap<String, Object>> dataList = objectMapper.convertValue(paramMap.get("data"), List.class);

            if (dataList != null) {
                for (HashMap<String, Object> item : dataList) {
                    item.put("acIp", acIp);
                    resultCnt += repository.saveVipPartner(item);
                }
            }

            if (dataList != null && resultCnt != dataList.size()) {
                throw new Exception("Failed to execute ReserveVisitService.saveAdmVipPartner >> The number of saves does not match.");
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return true;
    }

    @Override
    public Boolean insertBuildEntranceInfo(Map<String, Object> paramMap) {
        try {
            String empId = objectMapper.convertValue(paramMap.get("empId"), String.class);

            // 이천/분당 도면 Url
            String icUrl = objectMapper.convertValue(paramMap.get("icUrl"), String.class);

            // 청주 도면 Url
            String cjUrl = objectMapper.convertValue(paramMap.get("cjUrl"), String.class);

            if (icUrl != null && !icUrl.trim().equals("")) {
                this.saveBuildEntranceInfo(empId, "1101000001", icUrl, "이천출입구역안내도.pdf");
            }

            if (cjUrl != null && !cjUrl.trim().equals("")) {
                this.saveBuildEntranceInfo(empId, "1102000001", cjUrl, "청주출입구역안내.pdf");
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return true;
    }

    private Boolean saveBuildEntranceInfo(String empId, String entCompId, String fileUrl, String fileName) {
        try {

            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("empId", empId);
            paramMap.put("entCompId", entCompId);
            paramMap.put("orgFileName", fileName);
            paramMap.put("fileUrl", fileUrl);

            // 이전 파일 미사용으로 변경
            repository.updateBuildingEntranceInfoUseYn(entCompId);

            // 등록
            repository.insertBuildingEntranceInfo(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return true;
    }

    @Override
    public ResponseEntity<Object> downloadBuildEntranceInfo(String entCompId, String agent) {
        try {

            // 캠퍼스별 사용중인 파일 검색
            Map<String, Object> infoMap = repository.selectBuildingEntranceInfo(entCompId);

            if (infoMap != null) {
                String filePath = objectMapper.convertValue(infoMap.get("filePath"), String.class);
                String fileUrl = objectMapper.convertValue(infoMap.get("fileUrl"), String.class);

                String oriFileName = "";
                if ("1101000001".equals(entCompId)) {
                    oriFileName = "이천출입구역안내.pdf";
                }
                else if ("1102000001".equals(entCompId)) {
                    oriFileName = "청주출입구역안내.pdf";
                }

                String url = null;
                if (!StringUtils.isEmpty(filePath)) {
                    // NAS로 Upload된 파일 다운로드
                    url = securityExtnetUrl + "/" + filePath.split(";")[0].replaceAll("\\\\", "/");
                }
                else if (!StringUtils.isEmpty(fileUrl)) {
                    // CDN으로 Upload된 파일 다운로드
                    url = fileUrl;
                }

                if (url != null) {
                    Resource resource = new InputStreamResource(new URL(url).openStream()); // get file resource

                    if (resource != null) {
                        if (agent.contains("Trident")) { // Internet Explore
                            oriFileName = URLEncoder.encode(oriFileName, "UTF-8").replaceAll("\\+", " ");
                        }
                        else if (agent.contains("Edge")) { // Micro Edge
                            oriFileName = URLEncoder.encode(oriFileName, "UTF-8");
                        }
                        else { // Chrome or ETC
                            oriFileName = new String(oriFileName.getBytes("UTF-8"), "ISO-8859-1");
                        }

                        return ResponseEntity.ok()
                            .contentType(MediaType.parseMediaType("application/pdf"))
                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + oriFileName)
                            .body(resource);
                    }
                }
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return null;
    }

}
