package com.skshieldus.esecurity.service.entmanage.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skshieldus.esecurity.common.component.Mailing;
import com.skshieldus.esecurity.model.entmanage.IparkingDTO;
import com.skshieldus.esecurity.model.entmanage.SendSpmsDTO;
import com.skshieldus.esecurity.repository.entmanage.ReserveVisitRepository;
import com.skshieldus.esecurity.repository.entmanage.VisitEntranceRepository;
import com.skshieldus.esecurity.repository.entmanage.idcardvisit.IdcardVisitRepository;
import com.skshieldus.esecurity.service.entmanage.VisitEntranceIfService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class VisitEntranceIfServiceImpl implements VisitEntranceIfService {

    @Autowired
    private Environment environment;

    @Autowired
    private Mailing mailing;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private VisitEntranceRepository repository;

    @Autowired
    private IdcardVisitRepository idcardVisitRepository;

    @Autowired
    private ReserveVisitRepository reserveVisitRepository;

    //	@Autowired
    //	private IparkingApiClient iparkingApiClient;

    //	@Value("${ifaccess.cube.url}")
    //	private String cubeUrl;
    //
    //	@Value("${ifaccess.ssm.url}")
    //	private String ssmUrl;

    @Override
    public Boolean executeGateEnterPermissionIf(Map<String, Object> paramMap) {
        log.info("[START] executeGateEnterPermissionIf");
        boolean result = false;

        try {
            log.info("executeGateEnterPermissionIf parameter: {}", paramMap.toString());

            Map<String, Object> manCompMap = repository.selectFrontDoorManComp(paramMap);
            log.info("executeGateEnterPermissionIf vstApplNo: {}, ioEmpId: {}, manCompMap: {}", paramMap.get("vstApplNo"), paramMap.get("ioEmpId"), manCompMap != null
                ? manCompMap.toString()
                : "NULL");

            if (manCompMap != null) {
                String swBuild = objectMapper.convertValue(paramMap.get("swBuild"), String.class); // 2021-05-20 채수억 : visitReservation.jsp에서 분당 > s/w 입문시 "126" 넘겨줌.

                // 안내데스크가 없는 건물 목록. -> 특정 게이트가 아니라 예약한 전체 게이트 추출 오픈할 경우.
                // index 0: 건물코드, index 1: 건물명
                String[][] nonReceptBuildgList = { { "1", "경영지원본관" }, { "4", "SCM" }, { "150", "M15" }, { "132", "청주원자재창고 1" }, { "160", "청주원자재창고 2" }, { "202", "M14" }, { "209", "M16" } };

                /*
                 * I/F 데이터 추출
                 */
                String ioCardno = objectMapper.convertValue(paramMap.get("ioCardno"), String.class);

                paramMap.put("cardNo", ioCardno != null
                    ? ioCardno.trim()
                    : ioCardno);
                paramMap.put("juminNo", manCompMap.get("juminNo"));
                paramMap.put("name", manCompMap.get("empNm"));
                paramMap.put("passportNo", manCompMap.get("passportNo"));
                paramMap.put("areaCode", paramMap.get("inCompId"));
                paramMap.put("doorinAt", manCompMap.get("startDt"));
                paramMap.put("companyName", manCompMap.get("ioCompNm"));
                paramMap.put("emailId", manCompMap.get("emailAddr"));
                paramMap.put("photoUrl", manCompMap.get("photoUrl"));
                paramMap.put("ioEmpId", manCompMap.get("ioEmpId")); //추가 특수구역 UT동 15분내 미타각시 보안위규 발생 20170125
                paramMap.put("vstApplNo", manCompMap.get("vstApplNo"));  //추가 특수구역 UT동 15분내 미타각시 보안위규 발생 20170125
                paramMap.put("leadYn", manCompMap.get("leadYn"));  //추가 특수구역 UT동 15분내 미타각시 보안위규 발생 20170125

                paramMap.put("building", "");
                paramMap.put("leaderEmp", "");

                // String onlineLeadYn = objectMapper.convertValue(paramMap.get("onlineLeadYn"), String.class);
                String onlineLeadYn = paramMap.get("onlineLeadYn") != null
                    ? String.valueOf(paramMap.get("onlineLeadYn"))
                    : null;
                log.info("onlineLeadYn: {}", onlineLeadYn);

                if (onlineLeadYn == null || "N".equals(onlineLeadYn)) {
                    this.executeProcedureFrontDoorInProcIF(paramMap);
                }

                // 2021-05-20 채수억 분당 S/w정문 출입시 건물 출입 타각이 현재까지 없다고함. 해서 최초 입문시 빌딩 번호 추가해주기로함.
                if ("126".equals(swBuild)) {
                    paramMap.put("building", "802-01"); //2021-05-20 채수억 분당 S/W센터 빌딩번호
                    paramMap.put("leaderEmp", manCompMap.get("empId"));

                    // 최초 외곽 입문시만 수행.
                    if (onlineLeadYn == null) {
                        this.executeProcedureFrontDoorInProcIF(paramMap);
                    }
                }

                List<Map<String, Object>> gateList = repository.selectFrontDoorGateList2(paramMap);

                // 게이트 출입권한 IF 여부
                boolean enableSendingCube = objectMapper.convertValue(paramMap.get("enableSendingCube"), boolean.class); // 온라인인솔 큐브 발송 미대상
                boolean disableSendingCube = !enableSendingCube; // 온라인인솔 큐브 발송 미대상
                log.info("disableSendingCube : {}", disableSendingCube);

                String gateId = "";
                String guideGbn = "";
                String upGate = "";
                List<Map<String, Object>> upGateList = null;

                boolean isPermittedGateEnter = false;

                for (Map<String, Object> item : gateList) {
                    gateId = objectMapper.convertValue(item.get("gateId"), String.class);
                    guideGbn = objectMapper.convertValue(item.get("guideGbn"), String.class);
                    paramMap.put("leaderEmp", item.get("empId"));

                    isPermittedGateEnter = "1".equals(guideGbn);  // 온라인 인솔 승인 받은 경우
                    log.info("isPermittedGateEnter : {} : {}", gateId, isPermittedGateEnter);

                    // 최초 입문시, 청주 1,2,3 캠퍼스 UT인 경우 권한 부여
                    if (onlineLeadYn == null) {
                        this.startIfCjUtGate(paramMap);
                    }

                    // 특정 게이트 권한 부여
                    if (gateId.equals("75-07") || gateId.equals("75-03")) {  //인력개발원 및 폐기홀
                        if (disableSendingCube || isPermittedGateEnter) { // 큐브 미 발송 대상이거나(위치동의 미동의, 인솔로 지정한 게이트가 없는 경우) 큐브 발송대상이며 해당 게이트를 인솔로 선택한 경우
                            paramMap.put("building", "75-07");
                            this.executeProcedureFrontDoorInProcIF(paramMap);

                            paramMap.put("building", "75-03");
                            this.executeProcedureFrontDoorInProcIF(paramMap);
                        }
                    }

                    // 특정 게이트 권한 부여
                    if ((
                            gateId.equals("166-01") //P&T물류센터(완제품창고)
                            || gateId.equals("166-03") // 통합자재창고
                        ) && (disableSendingCube || isPermittedGateEnter)) {
                        paramMap.put("building", gateId);
                        this.executeProcedureFrontDoorInProcIF(paramMap);
                    }

                    // 안내데스크 없는 건물인 경우 방문예약 건물 내 전체 대상 게이트 권한 부여
                    for (String[] bldgList : nonReceptBuildgList) {
                        upGate = objectMapper.convertValue(item.get("upGate"), String.class);

                        if (bldgList[0].equals(upGate)) {

                            if ("202".equals(bldgList[0])) {
                                log.info("Up gate : 202");
                                paramMap.put("upGateId", "202");

                                upGateList = repository.selectFrontDoorGateListByUpGate(paramMap);

                                for (Map<String, Object> upGateInfo : upGateList) {
                                    if (disableSendingCube || isPermittedGateEnter) {
                                        paramMap.put("building", upGateInfo.get("gateId"));
                                        paramMap.put("leaderEmp", upGateInfo.get("empId"));

                                        this.executeProcedureFrontDoorInProcIF(paramMap);
                                    }
                                }
                            }
                            else {
                                log.info("Up gate : {}", upGate);

                                if (disableSendingCube || isPermittedGateEnter) {
                                    paramMap.put("building", gateId);
                                    this.executeProcedureFrontDoorInProcIF(paramMap);
                                }
                            }
                        }
                    }
                }
            }

            result = true;
        } catch (Exception e) {
            log.error("executeGateEnterPermissionIf throw Exception: {}", e.toString());
        }

        log.info("executeGateEnterPermissionIf result: {}", result);
        log.info("[END] executeGateEnterPermissionIf");

        return result;
    }

    @Override
    public List<Map<String, String>> executeGetVisitorGateInNotiData(boolean isOfficeRoom, Map<String, Object> visitReserveDataMap) {
        List<Map<String, String>> resultList = new ArrayList<>();

        //		log.info("[START] executeGetVisitorGateInNotiData");
        //		log.info("isOfficeRoom: {}", isOfficeRoom);
        //		log.info("visitReserveDataMap: {}", visitReserveDataMap.toString());

        try {
            Map<String, String> mailReq = new HashMap<>();
            Map<String, String> kakaoReq = null;

            mailReq.put("empId", objectMapper.convertValue(visitReserveDataMap.get("reqCrtBy"), String.class));
            mailReq.put("ioCompNm", objectMapper.convertValue(visitReserveDataMap.get("ioCompNm"), String.class));
            mailReq.put("ioEmpNm", objectMapper.convertValue(visitReserveDataMap.get("ioEmpNm"), String.class));
            mailReq.put("ioJwNm", objectMapper.convertValue(visitReserveDataMap.get("ioJwNm"), String.class));
            mailReq.put("ioHpNo", objectMapper.convertValue(visitReserveDataMap.get("ioHpNo"), String.class));
            mailReq.put("ioVstRsn", objectMapper.convertValue(visitReserveDataMap.get("ioVstRsn"), String.class));
            mailReq.put("coEmail", objectMapper.convertValue(visitReserveDataMap.get("coEmail"), String.class));
            mailReq.put("inDt", objectMapper.convertValue(visitReserveDataMap.get("inDt"), String.class));
            mailReq.put("outDt", objectMapper.convertValue(visitReserveDataMap.get("outDt"), String.class));
            mailReq.put("gateNm", objectMapper.convertValue(visitReserveDataMap.get("gateNm"), String.class));
            mailReq.put("inoutKnd", "IN");

            if (isOfficeRoom) {
                kakaoReq = new HashMap<>();
                StringBuilder msg = new StringBuilder();
                String coHpNo = objectMapper.convertValue(visitReserveDataMap.get("coHpNo"), String.class);

                msg.append("방문예약으로 (" + visitReserveDataMap.get("ioCompNm") + ")" + visitReserveDataMap.get("ioEmpNm") + "님 이 입문하셨습니다.");

                kakaoReq.put("templateCode", "SJT_066371");
                kakaoReq.put("kakaoTitle", "방문예약 입문");
                kakaoReq.put("hpNo", coHpNo != null
                    ? coHpNo.replaceAll("-", "")
                    : null);
                kakaoReq.put("callbackTel", "03151854114");
                kakaoReq.put("kakaoMsg", msg.toString());
                kakaoReq.put("smsMsg", msg.toString());
                kakaoReq.put("kAttach", "");

                /* 방문상세 사유 파싱_S */
                String vstDetlRsn = objectMapper.convertValue(visitReserveDataMap.get("vstDetlRsn"), String.class);
                String[] vstDetlRsnArrs = vstDetlRsn != null
                    ? vstDetlRsn.split(";")
                    : null;

                List<String> vstDetlRsnList = new ArrayList<>();

                String[] vstDetlRsnArr = null;
                for (String rsns : vstDetlRsnArrs) {
                    vstDetlRsnArr = rsns.split("@");
                    vstDetlRsnList.add(vstDetlRsnArr[vstDetlRsnArr.length - 1]); // 배열의 마지막 값(=방문사유)
                }

                StringBuilder vstDetlRsnSB = new StringBuilder();
                for (int k = 0; k < vstDetlRsnList.size(); k++) {
                    vstDetlRsnSB.append((k + 1) + " : " + vstDetlRsnList.get(k) + "\n");
                }

                /* 방문상세 사유 파싱_E */
                mailReq.put("ioVstRsn", vstDetlRsnSB.toString());
            }

            log.info("mailReq: {}", mailReq.toString());
            log.info("kakaoReq: {}", kakaoReq != null
                ? kakaoReq.toString()
                : "");

            resultList.add(mailReq);
            resultList.add(kakaoReq);
        } catch (Exception e) {
            log.error("executeGetVisitorGateInNotiData throw Exception: {}", e.toString());
        }

        //		log.info("[END] executeGetVisitorGateInNotiData");

        return resultList;
    }

    @Override
    public Boolean executeGateInOutVstMail(Map<String, String> paramMap, String empId, String acIp) {
        boolean result = false;

        //		log.info("[START] executeGateInOutVstMail");
        //		log.info("parameter: {}", paramMap.toString());

        try {

            // 방문자 정보
            String ioCompNm = paramMap.get("ioCompNm");  // 소속
            String ioEmpNm = paramMap.get("ioEmpNm");  // 방문자명
            String ioJwNm = paramMap.get("ioJwNm");    // 직위
            String ioHpNm = paramMap.get("ioHpNo");    // 연락처
            String iOVstRsn = paramMap.get("ioVstRsn");  // 방문사유
            String inoutKnd = paramMap.get("inoutKnd");  // 입/출문구분
            String email = paramMap.get("coEmail");    // 접수자메일
            String inDt = paramMap.get("inDt");      // 입문일자
            // String outDt = paramMap.get("outDt");	// 출문일자
            String gateNm = paramMap.get("gateNm");    // GATE명

            String sendTo = email;
            String title = "";
            String schemaNm = "";

            String message = "";

            if (inoutKnd.equals("IN")) {
                schemaNm = "GATE_VST_IN";
                title = "[e-Security] \"" + ioEmpNm + "\" 님이 입문을 하셨습니다";
                message = "<font style='font-size:11pt;margin-top:9px'>안녕하십니까 산업보안입니다.<br /><br />"
                          + "금일(" + inDt + ") 방문예약이 승인된 <b>" + ioEmpNm + "</b> 님이 " + gateNm + " 안내실에서 신분증 교환(입문)을 하셨습니다.<br /></ br>"
                          + "<b>※ 방문객 인적사항</b>(예약시 기입사항)<br />"
                          + "&nbsp;&nbsp;- 소속/직급/성명/연락처 : " + ioCompNm + " / " + ioJwNm + " / " + ioEmpNm + " / " + ioHpNm + "<br />"
                          + "&nbsp;&nbsp;- 방문사유 : " + iOVstRsn + "<br /><br />"
                          + "방문객에 대한 보안적 책임은 예약접수자(메일 수신인)에게 있사오니, 산업보안 규정 준수토록 안내 부탁드리며<br />"
                          + "방문 목적상 업무가 종료된 이후에 단지내를 배회하는 일이 없도록 관리 부탁드립니다.<br />"
                          + "<div style='color:red;margin-top:11px;'><b>* 주의사항(방문객)</b><br />"
                          + "&nbsp;&nbsp;1) 휴대폰 카메라 렌즈 봉인<br />"
                          + "&nbsp;&nbsp;2) 태블릿 PC 반입 금지<br />"
                          + "&nbsp;&nbsp;3) 미신청 휴대용 전산저장장치 반입금지<br />"
                          + "&nbsp;&nbsp;4) 구성원 인솔하에 출입 및 미허가 구역 출입금지</div><br />"
                          + "감사합니다."
                          + "<br /></font>";
            }
            else {
                schemaNm = "GATE_VST_OUT";
                title = "[e-Security] \"" + ioEmpNm + "\" 님이 출문을 하셨습니다";
                message = "<font style='font-size:11pt;margin-top:9px'>안녕하십니까 산업보안입니다.<br /><br />"
                          + "금일 방문하신 <b>" + ioEmpNm + "</b> 님이 업무 종료 후 " + gateNm + " 안내실에서 신분증 교환(출문)을 하셨습니다.<br /></ br>"
                          + "<b>※ 방문객 인적사항</b>(예약시 기입사항)<br />"
                          + "&nbsp;&nbsp;- 소속/직급/성명/연락처 : " + ioCompNm + " / " + ioJwNm + " / " + ioEmpNm + " / " + ioHpNm + "<br />"
                          + "&nbsp;&nbsp;- 방문사유 : " + iOVstRsn + "<br />"
                          + "감사합니다."
                          + "<br /></font>";
            }

            //	    	log.info("Send mail || schemaNm: {}, title: {}", schemaNm, title);

            // send mail
            mailing.sendMail(title, mailing.applyMailTemplate(title, message), sendTo, empId, schemaNm, "", acIp);

            result = true;
        } catch (Exception e) {
            log.error("executeGetVisitorGateInNotiData throw Exception: {}", e.toString());
        }

        return result;
    }

    @Override
    public Map<String, Object> getViolationData(Map<String, Object> paramMap, Map<String, Object> visitReserveDataMap) {
        //		log.info("[START] getViolationData");

        Map<String, Object> resultMap = new HashMap<>();

        try {
            String oriCompId = objectMapper.convertValue(paramMap.get("compId"), String.class);

            resultMap.put("compId", oriCompId);
            resultMap.put("deptId", paramMap.get("deptId"));
            resultMap.put("jwId", paramMap.get("jwId"));
            resultMap.put("empId", paramMap.get("crtBy"));
            resultMap.put("ofendCompId", visitReserveDataMap.get("ioCompId"));
            resultMap.put("ofendEmpId", visitReserveDataMap.get("ioEmpId"));
            resultMap.put("telNo", visitReserveDataMap.get("ioHpNo"));
            resultMap.put("violationDate", visitReserveDataMap.get("violatDt"));
            resultMap.put("violationHour", visitReserveDataMap.get("violatHour"));
            resultMap.put("violationMin", visitReserveDataMap.get("violatMin"));
            resultMap.put("ofendGbn", "C0521001");
            resultMap.put("ofendDetailGbn", "C0581015");
            resultMap.put("ofendSubGbn", "C0591098");
            resultMap.put("acIp", paramMap.get("acIp"));
            resultMap.put("crtBy", paramMap.get("crtBy"));
            resultMap.put("etcRsn", "상시출입증 단순 미소지_외곽출입적발");

            String compId = "";
            String actBldg = "";

            if ("1105000001".equals(oriCompId) || "1106000001".equals(oriCompId)) {
                compId = "1102000001";
            }
            else if ("1107000001".equals(oriCompId)) {
                compId = "1108000001";
            }
            else {
                compId = oriCompId;
            }

            if ("1101000001".equals(compId)) {
                actBldg = "C0631014";
            }
            else if ("1102000001".equals(compId)) {
                actBldg = "C0632006";
            }
            else {
                actBldg = "C0638001";
            }

            resultMap.put("actCompId", compId);
            resultMap.put("actBldg", actBldg);

            int ioPassGateCnt = objectMapper.convertValue(paramMap.get("ioPassGateCnt"), Integer.class);

            if (ioPassGateCnt > 2) {
                resultMap.put("actDo", "");
            }
            else {
                resultMap.put("actDo", "C0280011");
                resultMap.put("corrPlanSendYn", "C0101003");
                resultMap.put("rmrk", "상시출입자 출입증미소지 자동징구");
            }
        } catch (Exception e) {
            log.error("getViolationData throw Exception: {}", e.toString());
        }

        //		log.info("[END] getViolationData");

        return resultMap;
    }

    @Override
    public Map<String, Object> getSmartTagIfData(Map<String, Object> visitReserveDataMap, String[] reserveGateInfoList) {
        //		log.info("[START] getSmartTagIfData");

        Map<String, Object> smartTagReqMap = null;
        log.info("외곽 게이트 입문 시 SmartTag I/F : {} : {}", visitReserveDataMap.get("vstApplNo"), visitReserveDataMap.get("ioEmpNm"));

        if (reserveGateInfoList != null && reserveGateInfoList.length >= 1) {
            smartTagReqMap = new HashMap<>();

            smartTagReqMap.put("inVisitDate", visitReserveDataMap.get("vstDt"));
            smartTagReqMap.put("inVisitSeq", visitReserveDataMap.get("vstApplNo"));
            smartTagReqMap.put("inVisitorId", visitReserveDataMap.get("ioEmpId"));
            smartTagReqMap.put("inVisitorName", visitReserveDataMap.get("ioEmpNm"));
            smartTagReqMap.put("inVisitorEmail", visitReserveDataMap.get("emailAddr"));
            smartTagReqMap.put("inTel", visitReserveDataMap.get("ioHpNo"));
            smartTagReqMap.put("inCompany", visitReserveDataMap.get("ioCompNm") != null
                ? String.valueOf(visitReserveDataMap.get("ioCompNm"))
                : "");
            smartTagReqMap.put("inIdcardId", visitReserveDataMap.get("ioCardno"));
            smartTagReqMap.put("inIoknd", "1");
            smartTagReqMap.put("inBareaCd", visitReserveDataMap.get("vstCompId"));    // 사업장코드 (ex: 1101000001)
            smartTagReqMap.put("inBareaName", visitReserveDataMap.get("vstCompNm"));  // 사업장 명	(ex: 이천)
            smartTagReqMap.put("inPlaceId", visitReserveDataMap.get("gateId"));      // 출입지역 코드 (ex: 116)
            smartTagReqMap.put("inPlaceName", visitReserveDataMap.get("gateNm"));    // 출입지역 명 (ex: 정문)
            smartTagReqMap.put("inCuser", visitReserveDataMap.get("empId"));      // 등록자
            smartTagReqMap.put("inStaffId", visitReserveDataMap.get("empId"));      // 접수자 사번
            smartTagReqMap.put("inMeetStaffId", visitReserveDataMap.get("meetEmpId"));
            smartTagReqMap.put("inStayTime", visitReserveDataMap.get("vstStayTm"));    // 방문(체류)시간
            smartTagReqMap.put("inVisitObjCd", visitReserveDataMap.get("vstObjCd"));  // 방문목적 코드
            smartTagReqMap.put("unpermittedLocAgreeYn", visitReserveDataMap.get("unpermittedLocAgreeYn")); // 위치동의여부 선택사항

            try {
                smartTagReqMap.put("inBuildingName", reserveGateInfoList[0].substring(1));  // 건물 이름 목록
                smartTagReqMap.put("inBuildingId", reserveGateInfoList[1].substring(1));  // 건물 ID 목록
                smartTagReqMap.put("inDestNames", reserveGateInfoList[2].substring(1));    // 게이트 이름 목록
                smartTagReqMap.put("inDestIds", reserveGateInfoList[3].substring(1));    // 게이트 ID 목록
                smartTagReqMap.put("inTogetherYn", reserveGateInfoList[4].substring(1));  // 인솔 여부
            } catch (Exception e) {
                log.info("외곽 게이트 입문 시 SmartTag I/F 에러 : {} : {}", visitReserveDataMap.get("vstApplNo"), visitReserveDataMap.get("ioEmpNm"));
                smartTagReqMap = null;
            }
        }

        //		log.info("[END] getSmartTagIfData");

        return smartTagReqMap;
    }

    @Override
    public String[] getCubeIfData(Map<String, Object> paramMap, Map<String, Object> visitReserveDataMap, String receiverList, List<String> bldgIdList, List<String> bldgNmList) {
        //		log.info("[START] getCubeIfData");
        //		log.info("getCubeIfData || paramMap: {}", paramMap != null ? paramMap.toString() : "");
        //		log.info("getCubeIfData || visitReserveDataMap: {}", visitReserveDataMap != null ? visitReserveDataMap.toString() : "");
        //		log.info("getCubeIfData || receiverList: {}", receiverList);
        //		log.info("getCubeIfData || bldgIdList size: {}", bldgIdList.size());
        //		log.info("getCubeIfData || bldgNmList size: {}", bldgNmList.size());

        String[] msgList = null;

        try {
            List<String> finalBldgIdList = new ArrayList<>();
            List<String> finalBldgNmList = new ArrayList<>();

            /* 큐브 메세지 중복 발송을 피하기 위해 동일 건물이 복수개 지정되지 않도록 설정. */
            boolean isNewId;

            for (int i = 0; i < bldgIdList.size(); i++) {
                isNewId = true;

                for (String id : finalBldgIdList) {
                    if (id != null && id.equals(bldgIdList.get(i))) {
                        isNewId = false;
                        break;
                    }
                }

                if (isNewId) {
                    log.info("add finalBldgList >> {}: {}", bldgNmList.get(i), bldgIdList.get(i));
                    finalBldgIdList.add(bldgIdList.get(i));
                    finalBldgNmList.add(bldgNmList.get(i));
                }
            }

            /*
             * Cube 메세지 생성부.
             */
            String oldData = "{\"richnotification\": {\"header\": {\"from\": \"C0000099\",\"token\": \"C0000099-6ACD63AC-ECF9-41C7-B514-FC23AE07C953\",\"fromusername\": [\"방문 인솔 봇\", \"방문 인솔 봇\", \"방문 인솔 봇\", \"방문 인솔 봇\", \"방문 인솔 봇\"],\"to\": "
                             + "{\"uniquename\": [\""
                             + receiverList
                             + "\"],\"channelid\": [\"\"]}},\"content\": [{\"header\": {},\"body\": {\"bodystyle\": \"none\",\"row\": [{\"bgcolor\": \"#ffffff\",\"border\": false,\"align\": \"\",\"width\": \"\",\"column\": [{\"bgcolor\": "
                             + "\"#ffffff\",\"border\": false,\"align\": \"left\",\"valign\": \"middle\",\"width\": \"100%\",\"type\": \"label\",\"control\": {\"processid\": \"Sentence\",\"active\": true,\"text\": [\"방문객\", \"방문객\", \"방문객\", \"방문객\", \"방문객\"],\"color\": \"#000000\"}}]}, "
                             + "{\"bgcolor\": \"#ffffff\",\"border\": false,\"align\": \"\",\"width\": \"\",\"column\": [{\"bgcolor\": \"#ffffff\",\"border\": false,\"align\": \"left\",\"valign\": \"middle\",\"width\": \"100%\",\"type\": \"label\",\"control\": {\"processid\": \"Sentence\",\"active\": "
                             + "true,\"text\": [\"예약접수\", \"예약접수\", \"예약접수\", \"예약접수\", \"예약접수\"],\"color\": \"#000000\"}}]}, {\"bgcolor\": \"#ffffff\",\"border\": false,\"align\": \"\",\"width\": \"\",\"column\": [{\"bgcolor\": \"#ffffff\",\"border\": false,\"align\": \"left\",\"valign\": "
                             + "\"middle\",\"width\": \"100%\",\"type\": \"label\",\"control\": {\"processid\": \"Sentence\",\"active\": true,\"text\": [\"실접견자\", \"실접견자\", \"실접견자\", \"실접견자\", \"실접견자\"],\"color\": \"#000000\"}}]}, {\"bgcolor\": \"#ffffff\",\"border\": false,\"align\": "
                             + "\"\",\"width\": \"\",\"column\": [{\"bgcolor\": \"#ffffff\",\"border\": false,\"align\": \"left\",\"valign\": \"middle\",\"width\": \"100%\",\"type\": \"label\",\"control\": {\"processid\": \"Sentence\",\"active\": true,\"text\": [\"출입건물\", \"출입건물\", \"출입건물\", "
                             + "\"출입건물\", \"출입건물\"],\"color\": \"#000000\"}}]}, {\"bgcolor\": \"#ffffff\",\"border\": false,\"align\": \"\",\"width\": \"\",\"column\": [{\"bgcolor\": \"#ffffff\",\"border\": false,\"align\": \"left\",\"valign\": \"middle\",\"width\": \"100%\",\"type\": \"label\","
                             + "\"control\": {\"active\": true,\"text\": [\"단지입문\", \"단지입문\", \"단지입문\", \"단지입문\", \"단지입문\"],\"color\": \"#000000\"}}]}, {\"bgcolor\": \"#ffffff\",\"border\": false,\"align\": \"\",\"width\": \"\",\"column\": [{\"bgcolor\": \"#ffffff\",\"border\": false,"
                             + "\"align\": \"left\",\"valign\": \"middle\",\"width\": \"100%\",\"type\": \"label\",\"control\": {\"processid\": \"Sentence\",\"active\": true,\"text\": [\"방문기간\", \"방문기간\", \"방문기간\", \"방문기간\", \"방문기간\"],\"color\": \"#000000\"}}]}, {\"bgcolor\": \"#ffffff\","
                             + "\"border\": false,\"align\": \"\",\"width\": \"\",\"column\": [{\"bgcolor\": \"#ffffff\",\"border\": false,\"align\": \"left\",\"valign\": \"middle\",\"width\": \"50%\",\"type\": \"button\",\"control\": {\"processid\": \"Sentence\",\"active\": true,\"text\": [\"건물 출입 승인\", "
                             + "\"건물 출입 승인\", \"건물 출입 승인\", \"건물 출입 승인\", \"건물 출입 승인\"],\"confirmmsg\": [],\"value\": \"승인 처리 완료 되었습니다.\",\"bgcolor\": \"\",\"textcolor\": \"\",\"align\": \"center\",\"clickurl\": \"\",\"androidurl\": \"\",\"iosurl\": \"\",\"popupoption\": \"\",\"sso\": false,\"inner\": false}}]}]},"
                             + "\"process\": {\"callbacktype\": \"url\",\"callbackaddress\",\"processdata\": \"\",\"processtype\": \"\",\"summary\": [\"\", \"\", \"\", \"\", \"\"],\"session\": {\"sessionid\": \"Bot_HR_0002\",\"sequence\": \"1\"},\"mandatory\":[],\"requestid\": [\"Sentence\"]}}],\"result\": \"\"}}";

            msgList = new String[finalBldgIdList.size()];
            String[] titleList = { "방문객", "예약접수", "실접견자", "단지입문", "방문기간" };

            // Cube 전송시, Cube에 표시 할 데이터.
            // 회사명 추가 20211207
            String[] valueList = {
                visitReserveDataMap.get("ioCompNm") + " " + visitReserveDataMap.get("ioEmpNm"),
                visitReserveDataMap.get("deptNm") + " " + visitReserveDataMap.get("empNm") + " " + visitReserveDataMap.get("jwNm"),
                visitReserveDataMap.get("meetDeptNm") + " " + visitReserveDataMap.get("meetEmpNm") + " " + visitReserveDataMap.get("meetEmpJw"),
                "" + visitReserveDataMap.get("inDt3"),
                visitReserveDataMap.get("vstStrtDt") + " ~ " + visitReserveDataMap.get("vstEndDt")
            };

            String msg = "";
            for (int i = 0; i < titleList.length; i++) {
                if (i == 0) {
                    msg = oldData.replace(titleList[i], titleList[i] + " : " + valueList[i]);
                }
                else {
                    msg = msg.replace(titleList[i], titleList[i] + " : " + valueList[i]);
                }
            }
            char amp = '&';

            boolean isProd = environment.acceptsProfiles(Profiles.of("prd"));

            String url = isProd
                ? "http://esecurity-entmanage-api-basic-prd.api.hcp01.skhynix.com/esecurity-entmanage-api"
                : "http://esecurity-entmanage-api-basic-stg.api.hcpd01.skhynix.com/esecurity-entmanage-api";
            String param = url + "/visitEntrance/frontDoor/cube/onlineLead"
                           + "?vstApplNo=" + paramMap.get("vstApplNo")
                           + amp + "ioEmpId=" + paramMap.get("ioEmpId")
                           + amp + "empId=" + visitReserveDataMap.get("empId")
                           + amp + "gateId=" + visitReserveDataMap.get("gateId")
                           + amp + "vstDt=" + visitReserveDataMap.get("vstDt")
                           + amp + "vstSeq=" + visitReserveDataMap.get("vstSeq")
                           + amp + "ioCardno=" + visitReserveDataMap.get("ioCardno")
                           + amp + "inCompId=" + paramMap.get("inCompId");

            log.info("cubeParam: {}", param);

            String tempCallBackUrl = "";
            String temMsg = "";

            //			log.info("Start loop cube messsage");

            for (int i = 0; i < finalBldgIdList.size(); i++) {
                tempCallBackUrl = param + amp + "bldgId=" + finalBldgIdList.get(i);
                temMsg = msg.replace("출입건물", "출입건물 : " + finalBldgNmList.get(i)).replace("callbackaddress", "callbackaddress\" : \"" + tempCallBackUrl);

                log.info("vstApplNo = {} || ioEmpId = {} || No.{} Cube message: {}", (i + 1), paramMap.get("vstApplNo"), paramMap.get("ioEmpId"), temMsg);
                msgList[i] = temMsg;
            }

            //			log.info("End loop cube messsage");

        } catch (Exception e) {
            log.error("Error getCubeIfData throw Exception: {}", e.toString());
        }

        //		log.info("[END] getCubeIfData");

        return msgList;
    }

    @Override
    public Boolean executeFrontDoorCubeIF(Map<String, Object> paramMap) {
        //		log.info("[START] executeFrontDoorCubeIF");

        boolean result = false;
        HttpURLConnection conn = null;

        try {
            BufferedReader br = null;
            StringBuilder sb = null;
            String temp2 = null;

            //			boolean isProd = environment.acceptsProfiles(Profiles.of("prd"));
            //			String strUrl = isProd ? "http://cube.skhynix.com:8888/legacy/richnotification" : "http://10.158.122.139:8888/legacy/richnotification";
            //			log.info("send cubeUrl: {}", strUrl);
            //			URL url = new URL(strUrl); // Cube 시스템 주소.

            // 주석처리 2023-06-09
            //			URL url = new URL(cubeUrl); // Cube url. properties로 관리
            //			String[] msgList = paramMap.get("cubeMsgList") != null ? (String[])paramMap.get("cubeMsgList") : null;
            //
            //			int index = 1;
            //			for(String cubeMsg : msgList){
            //				conn = (HttpURLConnection) url.openConnection();
            //				conn.setRequestMethod("POST");
            //				conn.setRequestProperty("Content-Type", "application/json");
            //				conn.setDoInput(true);
            //				conn.setDoOutput(true);
            //				conn.setConnectTimeout(3000);
            //				conn.setReadTimeout(3000);
            //
            //				OutputStream os = conn.getOutputStream();
            //				os.write(cubeMsg.getBytes("UTF-8"));
            //				os.flush();
            //				os.close();
            //
            //				conn.connect();
            //
            //				br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"), conn.getContentLength());
            //				sb = new StringBuilder();
            //				temp2 = null;
            //
            //				while ((temp2 = br.readLine()) != null) {
            //					sb.append(temp2);
            //				}
            //
            //				br.close();
            //
            //				log.info("*********************************************************************************************");
            //				log.info("@@ No.{} 전산인솔 Cube 승인 요청 송신완료", index);
            //				log.info("@@ VST_APPL_NO : {}", paramMap.get("vstApplNo"));
            //				log.info("@@ 생성일 : {}", new SimpleDateFormat("yyyy.MM.dd").format(new Date()) + new SimpleDateFormat("HH:mm:ss").format(new Date()));
            //				log.info("@@ 전문 : {}", cubeMsg);
            //				log.info("@@ 메세지 : {}", sb.toString());
            //				log.info("*********************************************************************************************");
            //
            //				index++;
            //			}

            result = true;
        } catch (Exception e) {
            // throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
            log.error("executeFrontDoorCubeIF throw Exception: {}", e.toString());
        } finally {
            if (conn != null)
                conn.disconnect();
        }

        //		log.info("[END] executeFrontDoorCubeIF");

        return result;
    }

    @Override
    public Boolean executeFrontDoorSsmIF(Map<String, Object> paramMap) {
        //		log.info("[START] executeFrontDoorSsmIF");

        boolean result = false;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

        //		JSONObject jsonObj = new JSONObject();

        // 주석처리 2023-06-09
        // 입/출문 구분
        //	    jsonObj.put("accessType", paramMap.get("accessType"));
        //
        //	    // 입문구분 [사업장]_[입출문Gate]_[무인발급기/안내데스크 발급 구분]
        //	    jsonObj.put("terminalId", paramMap.get("terminalId") != null ? String.valueOf(paramMap.get("terminalId")) :  "정보 부적합");
        //
        //	    // 방문객 ID
        //	    jsonObj.put("userId", paramMap.get("ioEmpId"));
        //
        //	    // 발생 시간
        //	    jsonObj.put("eventTime",sdf.format(new Date()));
        //
        //	    // 고정 값
        //	    jsonObj.put("serviceId", "S79");

        HttpURLConnection conn = null;

        try {
            //			URL url = new URL(ssmUrl); // SSM 시스템 주소
            ////			log.info("SSM URL : {}", url.getHost() + " : " + url.getPath());
            ////			log.info("SSM jsonString : {}", jsonObj.toJSONString());
            //
            //			conn = (HttpURLConnection) url.openConnection();
            //			conn.setRequestMethod("POST");
            //			conn.setRequestProperty("Content-Type", "application/json");
            //			conn.setDoInput(true);
            //			conn.setDoOutput(true);
            //			conn.setConnectTimeout(3000);
            //			conn.setReadTimeout(3000);
            //
            //			OutputStream os = conn.getOutputStream();
            //			os.write(jsonObj.toJSONString().getBytes("UTF-8"));
            //			os.flush();
            //			os.close();
            //
            //			if(HttpURLConnection.HTTP_OK == conn.getResponseCode()){
            //				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            //				StringBuilder sb = new StringBuilder();
            //				String temp2 = null;
            //
            //				while ((temp2 = br.readLine()) != null) {
            //					sb.append(temp2);
            //				}
            //
            //				br.close();
            //
            //				result = true;
            //			}

        } catch (Exception e) {
            // throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
            log.error("executeFrontDoorSsmIF throw Exception: {}", e.toString());
        } finally {
            if (conn != null)
                conn.disconnect();
        }

        log.info("*********************************************************************************************");
        log.info("@@ SSM I/F");
        //	    log.info("@@ SSM 본문 데이터 : {}", jsonObj.toJSONString());
        log.info("*********************************************************************************************");

        //	    log.info("[END] executeFrontDoorSsmIF");

        return result;
    }

    /**
     * 청주 1,2,3 캠퍼스 외곽 입문시 UT 목적지가 있을 경우 UT 출입 권한 부여
     *
     * @param paramMap
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 2. 11.
     */
    private void startIfCjUtGate(Map<String, Object> paramMap) {
        //		log.info("[START] startIfCjUtGate");

        try {
            /* 김현근 선임 요청 (2017.03.10) -HSK
             * 청주 3공장 입문시, 방문예약목록에 UT가 있을 경우 UT는 입문 권한 부여 위한 I/F 수행.
             *
             * 강진수 TL 요청 (2022.01.19) -YKKWON
             * 온라인 인솔전사 확대  청주 1,2 공장 최초 외곽 입문시  목적지에 UT 있을경우 UT 및 특정 게이트 권한 부여.
             **/

            // UT 목록
            String[][] cjCampus_UtList = { { "119", "82-12" },   // 청주 1공장
                { "120", "94-09" }, // 청주 2공장
                { "121", "100-08" } }; // 청주 3공장

            // 1. 외곽 게이트 입문시, 캠퍼스 정보로 대상 캠퍼스 UT gateId를 조회 조건으로 추가.
            String inGate = objectMapper.convertValue(paramMap.get("inGate"), String.class);
            boolean includeUT = false; // 방문목적지 중, 청주 1,2,3 캠퍼스의 UT가 있는지 여부.

            for (String[] gateInfo : cjCampus_UtList) {
                if (inGate.equals(gateInfo[0])) {
                    includeUT = true;
                    paramMap.put("utGateId", gateInfo[1]); // UT 게이트 ID 추가.
                }
            }

            if (includeUT) {
                log.info("UT Include : {}", paramMap.toString());

                // 2. 방문예약 정보에서 입문한 외곽 게이트(청주 1,2,3 공장)의 UT 방문예약인경우 추출.
                List<Map<String, Object>> utList = repository.selectFrontDoorUTList(paramMap);
                log.info("utList record count : : {}", utList != null
                    ? utList.size()
                    : "null");

                // 3. 방문예정대상인 캠퍼스의 GateID(UT), 인솔자 정보 코드를  I/F
                if (utList != null && utList.size() > 0) {
                    //		 			IDataSet dmFrontDoor_GateList_ut = du_FrontDoor.dmFrontDoor_GateList_3Camp_UT(requestData, onlineCtx);
                    //		 		 	IRecordSet upGate3CampUtRs = dmFrontDoor_GateList_ut.getRecordSet("list");

                    // * 3공장 UT 장비 반입구 관련 게이트 ID 및 인솔자 정보를 게이트관리시스템(skhySTEC 윤여일대리관리) 에 I/F
                    for (Map<String, Object> item : utList) {
                        log.info("UT Include : 123 : {}", item.get("gateId"));

                        paramMap.put("building", item.get("gateId"));
                        paramMap.put("leaderEmp", item.get("empId"));

                        this.executeProcedureFrontDoorInProcIF(paramMap);
                    }
                }
            }
            else {
                log.info("UT Not Include");
            }
        } catch (Exception e) {
            log.error("startIfCjUtGate throw Exception: {}", e.toString());
        }

        //		log.info("[END] startIfCjUtGate");
    }

    /**
     * 출입증에 게이트 권한 부여
     * 1. 분당  S/W센터
     * 2. 청주 1,2,3 캠퍼스 UT 목적지.
     * 3. 온라인 인솔 미대상 + 안내데스크 없는 건물 (P&T, 통합자재창고, 인력개발원, SCM, 경영지원본관, M15, M14, 청주 원자재 창고 1,2)
     *
     * @param paramMap
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 2. 11.
     */
    private void executeProcedureFrontDoorInProcIF(Map<String, Object> paramMap) {
        //		log.info("[START] executeProcedureFrontDoorInProcIF");

        try {
            log.info("**************************************************");
            log.info("출입증 권한 부여");
            log.info("VSTAPPLNO ::: {}", paramMap.get("vstApplNo"));
            log.info("cardNo ::: {}", paramMap.get("cardNo"));
            log.info("name ::: {}", paramMap.get("name"));
            log.info("areaCode ::: {}", paramMap.get("areaCode"));
            log.info("doorinAt ::: {}", paramMap.get("doorinAt"));
            log.info("companyName ::: {}", paramMap.get("companyName"));
            log.info("emailId ::: {}", paramMap.get("emailId"));
            log.info("building ::: {}", paramMap.get("building"));
            log.info("leaderEmp ::: {}", paramMap.get("leaderEmp"));
            //				log.info("dmFrontDoor_InProc_IF Data : {}", paramMap.toString());
            log.info("**************************************************");

            boolean isProd = environment.acceptsProfiles(Profiles.of("prd"));

            // 방문객 입문
            if (isProd) {
                // dbExecuteProcedure("dmFrontDoor_InProc_IF", requestData.getFieldMap(),"_IDcard_Visit", onlineCtx);
                idcardVisitRepository.procedureFrontDoorInProcIF(paramMap);
            }
        } catch (Exception e) {
            // throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
            log.error("executeProcedureFrontDoorInProcIF throw Exception: {}", e.toString());
        }

        //		log.info("[END] executeProcedureFrontDoorInProcIF");
    }

    @Override
    public Boolean executeFrontDoorOutProcLprInfoIF(Integer vstApplNo) {
        //		log.info("[START] executeFrontDoorOutProcLprInfoIF");

        boolean result = false;

        try {
            // 운영 여부
            boolean isProd = environment.acceptsProfiles(Profiles.of("prd"));

            log.info("[출문 처리] 고담 LPR 차량 연동 처리 (신규API로 변경)");

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            DateTimeFormatter dateFormatterNew = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            // 고담 방문객 주차장(기존 현우주자장 이동에의한) 신설로 방문객 주차장에 LPR 시스템 구축 : 이천만 I/F => fmSendICSpms
            // 고담 : 일반 방문예약, VIP 방문예약 => API 방식으로 변경 (iparking - 파킹클라우드) 20211227
            List<SendSpmsDTO> gothamList = reserveVisitRepository.selectSendICSpmsIoVstInfoForGotham(vstApplNo);

            for (SendSpmsDTO IF_RequestData : gothamList) {
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

                log.info("[출문 처리] if data => {}", ReflectionToStringBuilder.toString(iparkingDTO));
                if (isProd) {
                    // 주석처리 2023-06-09
                    //					Map<String, Object> iparkingResultMap = iparkingApiClient.freeCarRegistrationProcess(iparkingDTO);
                    //					log.info("[출문 처리] 고담 파킹클라우드(신규API) 연동 응답 :: iparkingResultMap = {}", iparkingResultMap);
                    //					log.info("[출문 처리] 고담 파킹클라우드(신규API) 연동 결과 :: result = {}", iparkingResultMap.get("result"));
                    //					log.info("[출문 처리] 고담 파킹클라우드(신규API) 연동 결과 :: resultMessage = {}", iparkingResultMap.get("resultMessage"));
                }
            } // for

            result = true;
        } catch (Exception e) {
            // throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
            log.error("executeProcedureFrontDoorInProcIF throw Exception: {}", e.toString());
        }

        //		log.info("[END] executeFrontDoorOutProcLprInfoIF");

        return result;
    }

    @Override
    public Boolean executeFrontDoorCardIF(Map<String, Object> paramMap) {
        //		log.info("[START] executeFrontDoorCardIF");

        boolean result = false;

        try {

            // gate 리스트 뽑고 청정도 체크
            Map<String, Object> dataMap = repository.selectFrontDoorManComp(paramMap);

            if (dataMap != null) {
                log.info("############## [START] 방문객 출문 I/F ##############");
                String ioCardno = paramMap.get("ioCardno") != null
                    ? String.valueOf(paramMap.get("ioCardno"))
                    : "";

                Map<String, Object> ifMap = new HashMap<>();
                ifMap.put("cardNo", ioCardno != null
                    ? ioCardno.trim()
                    : "");
                ifMap.put("juminNo", dataMap.get("juminNo"));
                ifMap.put("name", dataMap.get("empNm"));
                ifMap.put("areaCode", dataMap.get("vstCompId"));
                ifMap.put("dooroutAt", dataMap.get("startDt")); //현재 시간
                ifMap.put("passportNo", dataMap.get("passportNo"));
                ifMap.put("companyName", dataMap.get("ioCompNm"));
                ifMap.put("reason", paramMap.get("reason"));
                ifMap.put("building", "");

                log.info("parameter: {}", ifMap.toString());

                boolean isProd = environment.acceptsProfiles(Profiles.of("prd"));

                if (isProd) {
                    // 이천 방문객 출문
                    // dbExecuteProcedure("dmFrontDoor_OutProc_IF", paramMap.getMap(),"_IDcard_Visit", onlineCtx);
                    idcardVisitRepository.procedureFrontDoorOutProcIF(ifMap);
                }

                log.info("############## [END] 방문객 출문 I/F ##############");
            }
        } catch (Exception e) {
            // throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
            log.error("executeFrontDoorCardIF throw Exception: {}", e.toString());
        }

        //		log.info("[END] executeFrontDoorCardIF");

        return result;
    }

}
