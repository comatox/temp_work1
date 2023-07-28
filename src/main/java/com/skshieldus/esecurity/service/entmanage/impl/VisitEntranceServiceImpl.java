package com.skshieldus.esecurity.service.entmanage.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.model.common.CommonXlsViewDTO;
import com.skshieldus.esecurity.model.entmanage.SendSpmsDTO;
import com.skshieldus.esecurity.repository.entmanage.EntManageCommonRepository;
import com.skshieldus.esecurity.repository.entmanage.VisitEntranceRepository;
import com.skshieldus.esecurity.repository.entmanage.cjvehiclecampus.CjVehicleCampusRepository;
import com.skshieldus.esecurity.repository.entmanage.icvehiclehaengbok.IcVehicleHaengbokRepository;
import com.skshieldus.esecurity.repository.entmanage.idcardvisit.IdcardVisitRepository;
import com.skshieldus.esecurity.repository.entmanage.smartTag.SmartTagRepository;
import com.skshieldus.esecurity.service.common.ApprovalService;
import com.skshieldus.esecurity.service.entmanage.FrontDoorService;
import com.skshieldus.esecurity.service.entmanage.VisitEntranceIfService;
import com.skshieldus.esecurity.service.entmanage.VisitEntranceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
public class VisitEntranceServiceImpl implements VisitEntranceService {

    @Autowired
    private Environment environment;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ApprovalService approvalService;

    @Autowired
    private EntManageCommonRepository commonRepository;

    @Autowired
    private VisitEntranceRepository repository;

    @Autowired
    private IdcardVisitRepository idcardVisitRepository;

    @Autowired
    private IcVehicleHaengbokRepository icVehicleHaengbokRepository;

    @Autowired
    private CjVehicleCampusRepository cjVehicleCampusRepository;

    @Autowired
    private SmartTagRepository smartTagRepository;

    @Autowired
    private VisitEntranceIfService ifService;

    @Autowired
    private FrontDoorService frontDoorService;

    @Value("${ifaccess.nca-if.url}")
    private String ncaUrl;

    @Value("${ifaccess.nca-if.token}")
    private String ncaToken;

    @Override
    public List<Map<String, Object>> selectFrontDoorList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectFrontDoorList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Boolean executeFrontDoorPrint(Map<String, Object> paramMap) {
        int resultCnt = 0;

        try {
            Integer vstApplNo = objectMapper.convertValue(paramMap.get("vstApplNo"), Integer.class);

            int cnt = repository.selectIoInoutwriteCnt(vstApplNo);

            if (cnt == 0) {
                String applStat = objectMapper.convertValue(paramMap.get("applStat"), String.class);

                if ("Z0331005".equals(applStat)) {
                    resultCnt = repository.insertIoInoutwriteBySelect(paramMap);

                    if (resultCnt > 0) {
                        resultCnt = repository.insertIoInoutarticlelistBySelect(paramMap);

                        if (resultCnt > 0) {
                            resultCnt = repository.insertIoInarticlehistoryBySelect(paramMap);

                            if (resultCnt > 0) {
                                return true;
                            }
                            else {
                                throw new Exception("Failed to execute executePrintFrontDoor - insertIoInarticlehistoryBySelect");
                            }
                        }
                        else {
                            throw new Exception("Failed to execute executePrintFrontDoor - insertIoInoutarticlelistBySelect");
                        }
                    }
                    else {
                        throw new Exception("Failed to execute executePrintFrontDoor - insertIoInoutwriteBySelect");
                    }
                }
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return true;
    }

    @Override
    public Map<String, Object> executeFrontDoorInprocess(Map<String, Object> paramMap) {
        Map<String, Object> result = new HashMap<>();

        log.info("[START] Page >> 외각입문처리");
        log.info("Page >> parameter: {}", paramMap.toString());

        try {
            result = frontDoorService.executeFrontDoorInprocess(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        log.info("Page >> 외각입문처리 결과: {}", result.toString());
        log.info("[END] Page >> 외각입문처리");

        return result;
    }

    @Override
    public Boolean executeFrontDoorInReset(Map<String, Object> paramMap) {
        Boolean result = false;

        log.info("[START] 외각입문Reset");
        log.info("parameter: {}", paramMap.toString());

        try {
            paramMap.put("inIoknd", "0");
            paramMap.put("ioCardno", objectMapper.convertValue(paramMap.get("cardNo"), String.class));

            int updateCnt = repository.updateFrontDoorInOutprocess(paramMap);

            // 통제구역 삭제
            repository.deleteIoVstManGateIo(paramMap);

            /*
             * 카드키 인터페이스 처리
             * 1. 청정도 체크 (미 이수의 경우 해당 상위 건물을 넘기지 않는다.)
             * 2. 이천의 경우만 처리
             * 3. 건물출입시 선택한 건물(상위 건물만) + (미출입을 선택햇을 경우 포함)
             *    기본 게이트 정문,후문,고담(116,117,118) 포함하여 넘겨야한다.
             */
            paramMap.put("reason", "방문객입문취소");
            ifService.executeFrontDoorCardIF(paramMap);

            result = updateCnt > 0;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        log.info("외각입문Reset 결과: ", result);
        log.info("[END] 외각입문Reset");

        return result;
    }

    @Override
    public Map<String, Object> executeFrontDoorOutprocess(Map<String, Object> paramMap) {
        Map<String, Object> result = new HashMap<>();

        log.info("[START] Page >> 외각출문처리");
        log.info("Page >> parameter: {}", paramMap.toString());

        try {
            result = frontDoorService.executeFrontDoorOutprocess(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        log.info("Page >> 외각출문처리 결과: ", result.toString());
        log.info("[END] Page >> 외각출문처리");

        return result;
    }

    @Override
    public Boolean executeFrontDoorOutReset(Map<String, Object> paramMap) {
        Boolean result = false;

        log.info("[START] 외각출문Reset");
        log.info("parameter: {}", paramMap.toString());

        try {
            paramMap.put("inVstDt", paramMap.get("vstDt"));
            paramMap.put("outIoknd", "1");

            int updateCnt = repository.updateFrontDoorInOutprocess(paramMap);

            if (updateCnt > 0) {
                paramMap.put("guideGbn", "0");
                updateCnt = repository.updateIoVstManGate(paramMap);
            }

            result = updateCnt > 0;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        log.info("외각출문Reset 결과: {}", result);
        log.info("[END] 외각출문Reset");

        return result;
    }

    @Override
    public Boolean executeFrontDoorReIn(Map<String, Object> paramMap) {

        try {
            String acIp = objectMapper.convertValue(paramMap.get("acIp"), String.class);

            @SuppressWarnings("unchecked")
            List<HashMap<String, Object>> dataList = objectMapper.convertValue(paramMap.get("data"), List.class);

            if (dataList != null) {
                for (HashMap<String, Object> item : dataList) {
                    item.put("acIp", acIp);

                    // insert
                    repository.insertFrontDoorIOHIn(item);

                    // update
                    repository.updateFrontDoorReIn(item);

                    // 통제구역 삭제
                    repository.deleteFrontDoorInGateReset(item);
                }
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return true;
    }

    @Override
    public Boolean receieveCubeByFrontDoor(Map<String, Object> paramMap) {
        Boolean result = false;

        try {
            log.info("[START] receieveCubeByFrontDoor");
            log.info("parameter: {}", paramMap.toString());

            boolean isProd = environment.acceptsProfiles(Profiles.of("prd"));

            Map<String, Object> requestMap = new HashMap<>(paramMap);
            requestMap.put("applState", paramMap.get("applState"));
            requestMap.put("gateId", paramMap.get("bldgId"));
            requestMap.put("guideGbn", "1");

            // 안내데스크가 없는 건물이 큐브로 입문 승인 받앗을때 들어 갈수 있도록 처리.
            // 온라인 인솔 승인 플래그 등록.
            requestMap.put("onlineLeadYn", "Y");
            requestMap.put("inGate", paramMap.get("gateId"));

            String ioCompId = objectMapper.convertValue(paramMap.get("inCompId"), String.class);

            // 분당 소프트웨어인 경우, IDcard I/F 를 위해 코드값 삽입.
            if ("1138000001".equals(ioCompId))
                requestMap.put("swBuild", "126");

            log.info("신청항목에 전산인솔로 승인으로 상태 변경");

            repository.updateIoVstManGate(requestMap); // 신청항목에 전산인솔로 승인으로 상태 변경

            log.info("카드에 출입권한 I/F");

            // 카드에 출입권한 I/F
            ifService.executeGateEnterPermissionIf(requestMap);

            /*
             * 전산인솔 승인 이력 추가 동작 방식
             * 1. IO_VST_MAN_GATE_IO_H에 이후 전산인솔 승인 통계를 위해 데이터를 기입.
             * 2. 위 테이블에 PK는 VST_APPL_NO, GATE_ID, VST_SEQ, VST_DT, IO_EMP_ID로 구성되어 있음.
             * 3. 전산인솔의 경우 입문시마다(한개 방문예약건으로 복수번 입문시 : ex: 재입문) 전산인솔승인 기록을 남겨야 함.
             * 4. PK에 해당하는 정보는 IO_VST, IO_VST_MAN_GATE에서 가져오는데, 이 값은 재입문을 하더라고 변경이 없기 때문에
             * 		IO_VST_MAN_GATE_IO_H에 입력시 무결성 제약조건에 위배됨.
             * 5. 해결방안으로 IO_VST_MAN_GATE_IO_H에 입력하는 값중 VST_SEQ를 최대 값에서 한개씩 증가 시킴.
             * 6. 이 경우, 실제 방문 기록의 VST_SEQ와는 값이 달라지지만, IO_VST_MAN_GATE_IO_H의 GUIDE_GBN이 1인경우는
             * 		전산인송 승인건으로 판별되어 VST_SEQ와는 별개로 구별하기로 결정.
             */
            repository.insertIoVstManGateIoH(requestMap); // 전산인솔 승인 상태 변경에 대한 이력 기록

            // Cube 승인 시,
            /* 1. 승인 후처리 알림 Cube I/F
             * 2. 온라인 인솔 시스템 I/F
             * 3. 승인 후처리 알림 Kakao I/F
             */

            // 건물(gate)명 조회
            String bldgNm = repository.selectGateNm(String.valueOf(paramMap.get("bldgId")));

            log.info("건물(gate)명: {}", bldgNm);

            paramMap.put("inVstDt", paramMap.get("vstDt"));
            paramMap.put("inGate", paramMap.get("gateId"));
            paramMap.put("invstDt", paramMap.get("vstDt"));

            Map<String, Object> userInfoMap = repository.selectFrontDoorUserInfo(paramMap);

            // Cube 전송시, Cube에 표시 할 데이터를 DB의 값으로 치환.
            String receiverList = String.valueOf(userInfoMap.get("empId"));

            log.info("userInfoMap: {}", userInfoMap.toString());
            log.info("before receiverList: {}", receiverList);

            // 접수자와 접견자가 동일하지 않을 경우 접견자 추가.
            if (userInfoMap.get("empId") != null
                && userInfoMap.get("meetEmpId") != null
                && !String.valueOf(userInfoMap.get("empId")).equals(String.valueOf(userInfoMap.get("meetEmpId")))) {

                receiverList += ("\",\"" + String.valueOf(userInfoMap.get("meetEmpId")));
            }

            log.info("after receiverList: {}", receiverList);

            String msg = "{\"richnotification\": {\"header\": {\"from\": \"C0000099\",\"token\": \"C0000099-6ACD63AC-ECF9-41C7-B514-FC23AE07C953\",\"fromusername\": [\"방문 인솔 봇\", \"방문 인솔 봇\", \"방문 인솔 봇\", \"방문 인솔 봇\", \"방문 인솔 봇\"],\"to\": "
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
                         + "\"align\": \"left\",\"valign\": \"middle\",\"width\": \"100%\",\"type\": \"label\",\"control\": {\"processid\": \"Sentence\",\"active\": true,\"text\": [\"방문기간\", \"방문기간\", \"방문기간\", \"방문기간\", \"방문기간\"],\"color\": \"#000000\"}}]}]},"
                         + "\"process\": {\"processdata\": \"\",\"processtype\": \"\",\"summary\": [\"\", \"\", \"\", \"\", \"\"],\"session\": {\"sessionid\": \"Bot_HR_0002\",\"sequence\": \"1\"},\"mandatory\": "
                         + "[],\"requestid\": []}}],\"result\": \"\"}}";

            String ioEmpNm = objectMapper.convertValue(userInfoMap.get("ioEmpNm"), String.class);
            String deptNm = objectMapper.convertValue(userInfoMap.get("deptNm"), String.class);
            String empNm = objectMapper.convertValue(userInfoMap.get("empNm"), String.class);
            String jwNm = objectMapper.convertValue(userInfoMap.get("jwNm"), String.class);
            String meetDeptNm = objectMapper.convertValue(userInfoMap.get("meetDeptNm"), String.class);
            String meetEmpNm = objectMapper.convertValue(userInfoMap.get("meetEmpNm"), String.class);
            String meetEmpJw = objectMapper.convertValue(userInfoMap.get("meetEmpJw"), String.class);
            String inDt = objectMapper.convertValue(userInfoMap.get("inDt"), String.class);
            String vstStrtDt = objectMapper.convertValue(userInfoMap.get("vstStrtDt"), String.class);
            String vstEndDt = objectMapper.convertValue(userInfoMap.get("vstEndDt"), String.class);

            String[] titleList = { "방문객", "예약접수", "실접견자", "출입건물", "단지입문", "방문기간" };
            String[] valueList = { ioEmpNm, // 방문객
                deptNm + " " + empNm + " " + jwNm, // 인솔자 정보
                meetDeptNm + " " + meetEmpNm + " " + meetEmpJw, // 접견자 정보
                bldgNm, // 출입 건물 정보
                inDt, // 방문 일시
                vstStrtDt + " ~ " + vstEndDt // 방문 시간
            };

            for (int i = 0; i < titleList.length; i++) {
                msg = msg.replace(titleList[i], titleList[i] + " : " + valueList[i]);
            }

            log.info("return cube msg >>> {}", msg);

            /*
             * 메세지 전송.
             */
            String[] msgList = { msg };
            Map<String, Object> cubeDataMap = new HashMap<>();
            cubeDataMap.put("vstApplNo", paramMap.get("vstApplNo"));
            cubeDataMap.put("cubeMsgList", msgList);

            log.info("Cube OutGate data : *************************** ");
            log.info("receive vstApplNo: {}", paramMap.get("vstApplNo"));
            log.info("receive cubeMsgList: {}", msg);

            ifService.executeFrontDoorCubeIF(cubeDataMap);

            /*
             *  Cube 승인 후 SmartTag I/F
             */
            paramMap.put("inVisitDate", paramMap.get("vstDt"));
            paramMap.put("inVisitSeq", paramMap.get("vstApplNo"));
            paramMap.put("inVisitorId", paramMap.get("ioEmpId"));
            paramMap.put("inIdcardId", paramMap.get("ioCardno"));
            paramMap.put("inBuildingId", paramMap.get("bldgId"));
            paramMap.put("inBuildingName", bldgNm);

            log.info("**************** [START] SmartTag I/F :: proceduteOutGateCubeApprovalSmartTagIF ****************");
            log.info("SmartTag parameter: {}", paramMap.toString());

            if (isProd) {
                try {
                    // dbExecuteProcedure("dmOutGate_Cube_Approval_SmartTag_IF", requestData.getFieldMap(),"_SmartTag", onlineCtx);
                    smartTagRepository.proceduteOutGateCubeApprovalSmartTagIF(paramMap);
                } catch (Exception e) {
                    log.error("Exception proceduteOutGateCubeApprovalSmartTagIF : {}", e.toString());
                }
            }
            log.info("**************** [END] SmartTag I/F :: proceduteOutGateCubeApprovalSmartTagIF ****************");

            // ####################### 방문객 출입 가능 SMS 발송 시작
            // #######################

            String name = userInfoMap.get("ioEmpNm") != null
                ? String.valueOf(userInfoMap.get("ioEmpNm"))
                : "";

            String kakaoMsg = (name.length() > 3
                ? name.substring(0, 3)
                : name) + "님 ";
            kakaoMsg += bldgNm + " 사무실/회의실 구성원 출입 승인 완료. ";
            // kakaoMsg += "사무실/회의실 구성원 출입 승인 완료.";
            kakaoMsg += "1층 안내데스크 직원을 통해 출입등록 부탁드립니다.";

            // Kakao message
            // 주석처리 2023-06-09
            //			RequestWrapModel<KakaoMessageDTO> wrapParams = new RequestWrapModel<>();
            //			KakaoMessageDTO kakaoMessageDTO = new KakaoMessageDTO();
            //			kakaoMessageDTO.setKTemplateCode("SJT_066372");
            //			kakaoMessageDTO.setSubject("구성원 출입 승인 완료");
            //			kakaoMessageDTO.setDstaddr(userInfoMap.get("ioHpNo") != null ? String.valueOf(userInfoMap.get("ioHpNo")).replace("-", "") : null);
            //			kakaoMessageDTO.setCallback("03151854114");
            //			kakaoMessageDTO.setText(kakaoMsg);
            //			kakaoMessageDTO.setText2(kakaoMsg);
            //			kakaoMessageDTO.setKAttach("");
            //			kakaoMessageDTO.setEmpId(objectMapper.convertValue(paramMap.get("empId"), String.class));
            //
            //			wrapParams.setParams(kakaoMessageDTO);
            //			commonApiClient.sendKakaoMessage(wrapParams);

            // ####################### 방문객 출입 가능 SMS 발송 종료
            // #######################

            /* [END] Cube 메세지 전송 */

            result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        log.info("[END] receieveCubeByFrontDoor");

        return result;
    }

    @Override
    public List<Map<String, Object>> selectCarryInPcList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectCarryInPcList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Integer selectCarryInPcListCnt(Map<String, Object> paramMap) {
        Integer resultCnt = null;

        try {
            resultCnt = repository.selectCarryInPcListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

    @Override
    public Boolean updateMobileUseApply(Map<String, Object> paramMap) {
        int updateCnt = 0;

        try {
            updateCnt = repository.updateMobileUseApply(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return updateCnt > 0;
    }

    @Override
    public Map<String, Object> executePrintBarcode(Map<String, Object> paramMap) {
        Map<String, Object> result = new HashMap<>();

        try {
            List<Map<String, Object>> printerList = repository.selectBarcodePrinterList(paramMap);

            if (printerList == null || printerList.size() == 0) {
                result.put("result", "noPrintIp");
                return result;
            }

            Map<String, Object> rcvPcInfoMap = repository.selectCarryInPc(paramMap);
            String resultMsg = "notFoundBarCordId";

            if (rcvPcInfoMap != null) {
                String barcode = objectMapper.convertValue(rcvPcInfoMap.get("inoutPcId"), String.class);

                if (barcode != null && !"".equals(barcode)) {

                    int timeout = 2000;

                    Map<String, Object> printInfoMap = printerList.get(0);

                    String printerIp = objectMapper.convertValue(printInfoMap.get("printerIp"), String.class);
                    int printerPort = objectMapper.convertValue(printInfoMap.get("printerPort"), Integer.class);
                    ;

                    Socket clientSocket = null;
                    DataOutputStream outToServer = null;

                    try {

                        // open connection
                        clientSocket = new Socket();

                        SocketAddress socketAddress = new InetSocketAddress(printerIp, printerPort);
                        clientSocket.connect(socketAddress, timeout);

                        // open data output stream
                        outToServer = new DataOutputStream(clientSocket.getOutputStream());
                        // send data to printer
                        if (barcode.endsWith("-EP")) {
                            outToServer.writeBytes("^XA^PW370^BY2,2,110^FO0,40^FB370,1,C,0^BC^FD" + barcode + "^FS^XZ");
                        }
                        else {
                            outToServer.writeBytes("^XA^PW370^BY2,2,110^FO40,40^FB370,1,C,0^BC^FD" + barcode + "^FS^XZ");
                        }

                        log.info("바코드 프린트 완료: {}", barcode);

                        result.put("result", "OK");
                        result.put("barcode", barcode);
                        return result;
                    } catch (SocketTimeoutException e) {
                        log.error("[ERROR] 바코드 프린터로 연결할 수 없습니다.");
                        resultMsg = "TimeoutException";
                    } catch (Exception e) {
                        throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
                    } finally {
                        // close data stream and socket
                        if (outToServer != null) {
                            try {
                                outToServer.close();
                            } catch (Exception e) {
                                log.error(e.toString());
                            }
                        }

                        if (clientSocket != null) {
                            try {
                                clientSocket.close();
                            } catch (Exception e) {
                                log.error(e.toString());
                            }
                        }
                    }
                }
            }

            result.put("result", resultMsg);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Map<String, Object> selectCarryInPc(Map<String, Object> paramMap) {
        Map<String, Object> result = null;

        try {
            result = repository.selectCarryInPc(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Boolean cancelCarryInPc(Map<String, Object> paramMap) {
        int resultCnt = 0;

        try {

            // PC id 제거, 상태 변경
            resultCnt += repository.updateIoInoutpcMoveByCancel(paramMap);

            // 이동정보 변경
            resultCnt += repository.updateIoInoutpclistByCancel(paramMap);

            // 이력 생성
            resultCnt += repository.insertIoInarticlehistory(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt == 3;
    }

    @Override
    public Boolean intoCarryInPc(Map<String, Object> paramMap) {
        int resultCnt = 0;

        try {

            // PC 반입 확인하면서 IO_INOUTPCLIST 아이디생성, 상태 변경
            resultCnt += repository.updateIoInoutpclistByInto(paramMap); // 디지털카메라의 경우 반입증번호는 업체물품 신청/접수 현황 결재 후처리에서 채번하도록 함. 220113

            // 이동정보 생성
            resultCnt += repository.mergeIoInoutpcMove(paramMap);

            // 이력 생성
            resultCnt += repository.insertIoInarticlehistory(paramMap);

            // EP 반입점검이 불필요한 경우 NAC 사용등록
            String epInChkNeedYn = objectMapper.convertValue(paramMap.get("epInChkNeedYn"), String.class);

            log.info("epInChkNeedYn: {}", epInChkNeedYn);

            boolean isProd = environment.acceptsProfiles(Profiles.of("prd"));

            // 주석처리 2023-06-09
            //			if("Y".equals(epInChkNeedYn)) {
            //				log.info("[START] 업체물품 전산기기IF_NAC사용자등록");
            //
            //				Map<String, Object> nacMap = repository.selectOutNetworkInfoNcaIf(paramMap);
            //				log.info("NAC사용자정보 : {}", nacMap != null ? nacMap.toString() : "not found");
            //
            //
            //				HttpClient client = HttpClientBuilder.create().build();
            //				HttpPost request = new HttpPost(ncaUrl + "/network/usage");
            //				request.setHeader("Accept", "application/json");
            //				request.setHeader("Connection", "keep-alive");
            //				request.setHeader("Content-Type", "application/json");
            //				request.addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzZWN1cml0eUFwaURldiIsImlhdCI6MTU5NTQwNjAyOCwiZXhwIjo0MTAyMzI2MDAwfQ.29b4KIXLqW9IwAgYO1X1TJDcYYf9QfmiPsajWytpGM0");
            //
            //		    	// Convert Object To JSON String
            //				Gson gson = new Gson();
            //				String param = gson.toJson(nacMap);
            //				log.info("NAC Convert Object To JSON String: {}", param);
            //
            //				request.setEntity(new StringEntity(param,"UTF-8"));
            //
            //				if(isProd) {
            //					log.info("Request NAC");
            //					HttpResponse response = client.execute(request);
            //
            //					log.info("response NAC : {}", response != null ? response.toString() : "null");
            //
            //					// Get Response String
            //					if (response != null && response.getStatusLine().getStatusCode() == 200) {
            //						ResponseHandler<String> handler = new BasicResponseHandler();
            //						String result = handler.handleResponse(response);
            //
            //						Type mapType = new TypeToken<Map<String, Object>>() {}.getType();
            //						Map<String, Object> resultMap = gson.fromJson(result, mapType);
            //
            //						int iStatCd = objectMapper.convertValue(resultMap.get("statusCode"), Integer.class);
            //
            //						log.info("iStatCd = {}", iStatCd);
            //
            //						String resultMsg = "";
            //
            //						switch (iStatCd) {
            //						case 2:
            //							resultMsg = "업체물품 전산기기IF_NAC사용자등록(intoCarryInPc) Success!! " + result;
            //							break;
            //						case -1:
            //							resultMsg = "JWT Token 요청 및  에러 메시지 (ID or Password is not correct)";
            //							break;
            //						case -2:
            //							resultMsg = "Http Header에 Token키가 없을때 예외 메시지 (No Have authorized token)";
            //							break;
            //						case -3:
            //							resultMsg = "필수 입력 필드가 없을때 예외 메시지 (There is no neccesary input field)";
            //							break;
            //						case -4:
            //							resultMsg = "필수 입력 필드는 있으나, 값이  없을때 예외 메시지 (There is neccesary input field without input value)";
            //							break;
            //						case -5:
            //							resultMsg = "데이터의 양식이 맞지 않을때 (MAC Address, IP Address is unmatching type)";
            //							break;
            //						case -100:
            //							resultMsg = "JWT 토큰이 변조된 토큰일 경우(The JWT token value has been altered.)";
            //							break;
            //						case -200:
            //							resultMsg = "JWT 토큰이 만료시간이 경과한 메시지 (JWT TOKEN Expired) !!";
            //							break;
            //						case -300:
            //							resultMsg = "JSon 문자열을 Class 객체로 역 직렬화 할때 오류(Json Mapping Exception)";
            //							break;
            //						case -900:
            //							resultMsg = "정의되지 않은 오류(Undefined Exception)";
            //							break;
            //						default:
            //							break;
            //						}
            //
            //						log.info("전산기기 NAC사용자등록 결과: {}", resultMsg);
            //					} else {
            //						log.info("전산기기 NAC사용자등록 Reponse error : {}", response.getStatusLine().toString());
            //					}
            //				}
            //
            //				log.info("[END] 업체물품 전산기기IF_NAC사용자등록");
            //				/* [END] 업체물품 전산기기IF_NAC사용자등록 */
            //			}

            String mobileUseApplyYn = objectMapper.convertValue(paramMap.get("mobileUseApplyYn"), String.class);

            //모바일 이용 동의 한 사람일 경우 모바일 반입증 확인 카카오톡 전송
            if ("Y".equals(mobileUseApplyYn)) {
                String templateCode = "SJT_062419";
                String empId = objectMapper.convertValue(paramMap.get("empId"), String.class);
                String ioTelNo = objectMapper.convertValue(paramMap.get("ioTelNo"), String.class);

                // template 조회
                Map<String, String> messageMap = commonRepository.selectKakaoMsgForm(templateCode);

                // Kakao message
                // 주석처리 2023-06-09
                //				RequestWrapModel<KakaoMessageDTO> wrapParams = new RequestWrapModel<>();
                //				KakaoMessageDTO kakaoMessageDTO = new KakaoMessageDTO();
                //				kakaoMessageDTO.setKTemplateCode(templateCode);
                //				kakaoMessageDTO.setSubject(messageMap.get("kakaoTitle"));
                //				kakaoMessageDTO.setDstaddr(ioTelNo);
                //				kakaoMessageDTO.setCallback(messageMap.get("callbackTel"));
                //				kakaoMessageDTO.setText(messageMap.get("kakaoMsg"));
                //				kakaoMessageDTO.setText2(messageMap.get("kakaoMsg"));
                //				kakaoMessageDTO.setKAttach("");
                //				kakaoMessageDTO.setEmpId(empId);
                //
                //				wrapParams.setParams(kakaoMessageDTO);
                //				commonApiClient.sendKakaoMessage(wrapParams);
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt == 3;
    }

    @Override
    public List<Map<String, Object>> selectBuildingPassList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectBuildingPassList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public List<Map<String, Object>> selectBuildingPassGateList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectBuildingPassGateList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Boolean executeBuildingInprocess(Map<String, Object> paramMap) {
        log.info("[START] executeBuildingInprocess");

        try {
            boolean isProd = environment.acceptsProfiles(Profiles.of("prd"));
            paramMap.put("gateGbn", "B"); // 건물출입시 GATE_GBN을 'B'로 해서 Insert가 되도록 함 : 2015-11-16 by JSH 추가

            @SuppressWarnings("unchecked")
            List<HashMap<String, Object>> gateList = objectMapper.convertValue(paramMap.get("gateList"), List.class);

            /* 건물 입문 처리 & 카드키 인터페이스 처리 - 건물출입시 선택한 건물(상위 건물만) */
            Map<String, Object> manCompMap = new HashMap<>();
            Map<String, Object> ifMap = new HashMap<>();

            String gateId = null;
            StringBuilder gateIds = new StringBuilder();
            StringBuilder gateNms = new StringBuilder();

            log.info("건물입문 Gate 목록 건수: {}", gateList.size());

            for (HashMap<String, Object> item : gateList) {
                gateId = objectMapper.convertValue(item.get("gateId"), String.class);
                paramMap.put("gateId", gateId);

                repository.insertBuildingPassInprocess(paramMap);

                if ("35".equals(gateId)) {
                    paramMap.put("gateId", "35-00");
                }
                else if ("127".equals(gateId)) {
                    paramMap.put("gateId", "127-00");
                }
                else {
                    paramMap.put("gateId", gateId);
                }

                manCompMap = repository.selectBuildingPassManComp(paramMap);

                if (manCompMap != null) {
                    log.info("#### [START] ( " + manCompMap.get("ioEmpId") + " )  ##### 방문객 건물입문 I/F ##############");

                    ifMap = new HashMap<>();
                    ifMap.put("cardNo", paramMap.get("ioCardno") != null
                        ? String.valueOf(paramMap.get("ioCardno")).trim()
                        : paramMap.get("ioCardno"));
                    ifMap.put("juminNo", manCompMap.get("juminNo"));
                    ifMap.put("name", manCompMap.get("empNm"));
                    ifMap.put("passportNo", manCompMap.get("passportNo"));
                    ifMap.put("areaCode", paramMap.get("inCompId"));
                    ifMap.put("doorinAt", manCompMap.get("startDt"));
                    ifMap.put("companyName", manCompMap.get("ioCompNm"));
                    ifMap.put("emailId", manCompMap.get("ioEmpId"));
                    ifMap.put("photoUrl", manCompMap.get("photoUrl"));
                    ifMap.put("building", paramMap.get("gateId"));
                    ifMap.put("leaderEmp", paramMap.get("guideEmpId"));
                    ifMap.put("ioEmpId", manCompMap.get("ioEmpId")); //추가 특수구역 UT동 15분내 미타각시 보안위규 발생 20170125
                    ifMap.put("vstApplNo", manCompMap.get("vstApplNo"));  //추가 특수구역 UT동 15분내 미타각시 보안위규 발생 20170125
                    ifMap.put("leadYn", manCompMap.get("leadYn")); //추가 특수구역 UT동 15분내 미타각시 보안위규 발생 20170125

                    log.info("##### parameter: {} #####", ifMap.toString());

                    if (isProd) { // 운영모드
                        idcardVisitRepository.procedureBuildingPassInIf(ifMap);
                    }

                    log.info("#### [END] ( " + manCompMap.get("ioEmpId") + " )  ##### 방문객 건물입문 I/F ##############");
                }

                gateIds.append("," + item.get("gateId"));
                gateNms.append("," + item.get("gateNm"));
            }

            Map<String, Object> smartTagMap = new HashMap<>();
            smartTagMap.put("inVisitDate", paramMap.get("vstDt"));      // 방문일자
            smartTagMap.put("inVisitSeq", paramMap.get("vstApplNo"));    // 방문증번호
            smartTagMap.put("inVisitorId", paramMap.get("ioEmpId"));    // 방문객 ID
            smartTagMap.put("inIdcardId", paramMap.get("ioCardno"));    // 통합사번
            smartTagMap.put("inBuildingId", paramMap.get("upGateId"));    // 건물 ID
            smartTagMap.put("inBuildingName", paramMap.get("upGateNm"));  // 건물 명
            smartTagMap.put("inDestIds", gateIds.length() > 0
                ? gateIds.toString().substring(1)
                : gateIds);        // 목적지 ID
            smartTagMap.put("inDestNames", gateNms.length() > 0
                ? gateNms.toString().substring(1)
                : gateNms);      // 목적지 명

            log.info("############## [START] 건물 입문 SmartTag Poc 호출 ##############");
            log.info("##### parameter: {} #####", smartTagMap.toString());

            // dbExecuteProcedure("dmBuildingPass_InProc_SmartTag_IF", requestData.getFieldMap(),"_SmartTag", onlineCtx);

            if (isProd) { // 운영모드
                try {
                    // 운영에서 procedure 오류 발생하여 try catch로 묶음 by kwg. 220224
                    smartTagRepository.procedureBuildingPassInProcSmartTagIf(smartTagMap);
                } catch (Exception e) {
                    log.error(e.toString());
                }
            }

            log.info("############## [END] 건물 입문 SmartTag Poc 호출 ##############");
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        log.info("[OUT] executeBuildingInprocess");

        return true;
    }

    @Override
    public Boolean executeBuildingOutprocess(Map<String, Object> paramMap) {
        log.info("[START] executeBuildingOutprocess");

        try {

            boolean isProd = environment.acceptsProfiles(Profiles.of("prd"));

            int resultCnt = repository.updateIoVstManGateIoByBuilingOut(paramMap);
            log.info("updateIoVstManGateIoByBuilingOut result: {}", resultCnt > 0);

            Map<String, Object> manCompMap = repository.selectBuildingPassManComp(paramMap);

            if (manCompMap != null) {
                log.info("#### [START] ( " + manCompMap.get("ioEmpId") + " )  ##### 방문객 건물입문 I/F ##############");

                String ioCardno = paramMap.get("ioCardno") != null
                    ? String.valueOf(paramMap.get("ioCardno"))
                    : "";
                String upGateId = paramMap.get("upGateId") != null
                    ? String.valueOf(paramMap.get("upGateId"))
                    : "";

                Map<String, Object> ifMap = new HashMap<>();
                ifMap.put("cardNo", paramMap.get("ioCardno") != null
                    ? ioCardno.trim()
                    : paramMap.get("ioCardno"));
                ifMap.put("juminNo", manCompMap.get("juminNo"));
                ifMap.put("name", manCompMap.get("empNm"));
                ifMap.put("areaCode", paramMap.get("inCompId"));
                ifMap.put("dooroutAt", manCompMap.get("startDt")); //현재 시간
                ifMap.put("passportNo", manCompMap.get("passportNo"));
                ifMap.put("companyName", manCompMap.get("ioCompNm"));
                ifMap.put("reason", "방문객건물출문");
                ifMap.put("building", upGateId + "-ALL");

                log.info("##### parameter: {} #####", ifMap.toString());

                if (isProd) {
                    idcardVisitRepository.procedureBuildingPassOutIf(ifMap);
                }

                log.info("#### [END] ( " + manCompMap.get("ioEmpId") + " )  ##### 방문객 건물입문 I/F ##############");
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        log.info("[END] executeBuildingOutprocess");

        return true;
    }

    @Override
    public Boolean executeBuildingPassReInOut(Map<String, Object> paramMap) {
        log.info("[START] executeBuildingPassReInOut");

        try {
            String acIp = objectMapper.convertValue(paramMap.get("acIp"), String.class);

            @SuppressWarnings("unchecked")
            List<HashMap<String, Object>> dataList = objectMapper.convertValue(paramMap.get("data"), List.class);

            if (dataList != null) {
                for (HashMap<String, Object> item : dataList) {
                    item.put("acIp", acIp);
                    item.put("gateGbn", "B"); // 건물출입시 GATE_GBN을 'B'로 해서 Insert가 되도록 함 : 2015-11-16 by JSH 추가

                    repository.mergeBuildingPassIOHIn(item);
                    repository.deleteBuildingPassReIn(item);
                }
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        log.info("[END] executeBuildingPassReInOut");

        return true;
    }

    @Override
    public List<Map<String, Object>> selectDeliveryPassList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectDeliveryPassList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Integer selectDeliveryPassListCnt(Map<String, Object> paramMap) {
        Integer resultCnt = null;

        try {
            resultCnt = repository.selectDeliveryPassListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

    @Override
    public Map<String, Object> selectDeliveryPass(Integer dlvAppNo) {
        Map<String, Object> result = null;

        try {
            result = repository.selectDeliveryPass(dlvAppNo);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Map<String, Object> executeDeliveryPassInprocess(Map<String, Object> paramMap) {
        log.info("[START] executeDeliveryPassInprocess");

        Map<String, Object> result = new HashMap<>();

        try {
            boolean isProd = environment.acceptsProfiles(Profiles.of("prd"));
            paramMap.put("inIoknd", "1");

            int chkNum = repository.selectDeliveryPassCardChk(paramMap);

            if (chkNum > 0) {
                result.put("result", "0002");
                return result;
            }

            chkNum = repository.selectDeliveryPassDenyChk(paramMap);

            if (chkNum > 0) {
                result.put("result", "0006");
                return result;
            }

            chkNum = repository.selectDeliveryPassCnt(paramMap);

            if (chkNum > 0) {
                result.put("result", "0007");
                return result;
            }

            repository.insertDeliveryPassInprocess(paramMap);

            paramMap.put("ioknd", "1"); // 입문
            repository.updateDeliveryPassIoKnd(paramMap);


            /* 카드키 인터페이스 처리
             *  1. 청정도 체크 (미 이수의 경우 해당 상위 건물을 넘기지 않는다.)
             *  2. 이천의 경우만 처리
             *  3. 건물출입시 선택한 건물(상위 건물만) + (미출입을 선택햇을 경우 포함) 기본 게이트 정문,후문,고담(116,117,118) 포함하여 넘겨야한다.
             */

            Map<String, Object> manCompMap = repository.selectDeliveryPassManComp(paramMap);

            if (manCompMap != null) {
                log.info("############## [START] 방문객 입문 I/F ##############");
                String ioCardno = paramMap.get("ioCardno") != null
                    ? String.valueOf(paramMap.get("ioCardno"))
                    : "";

                Map<String, Object> ifMap = new HashMap<>();
                ifMap.put("cardNo", ioCardno != null
                    ? ioCardno.trim()
                    : "");
                ifMap.put("juminNo", manCompMap.get("juminNo"));
                ifMap.put("name", manCompMap.get("empNm"));
                ifMap.put("passportNo", manCompMap.get("passportNo"));
                ifMap.put("areaCode", paramMap.get("inCompId"));
                ifMap.put("doorinAt", manCompMap.get("startDt"));
                ifMap.put("companyName", manCompMap.get("ioCompNm"));
                ifMap.put("emailId", manCompMap.get("emailAddr"));
                ifMap.put("photoUrl", manCompMap.get("photoUrl"));
                ifMap.put("ioEmpId", manCompMap.get("ioEmpId"));    //추가 특수구역 UT동 15분내 미타각시 보안위규 발생 20170125
                ifMap.put("vstApplNo", manCompMap.get("vstApplNo"));  //추가 특수구역 UT동 15분내 미타각시 보안위규 발생 20170125
                ifMap.put("leadYn", manCompMap.get("leadYn"));      //추가 특수구역 UT동 15분내 미타각시 보안위규 발생 20170125
                ifMap.put("building", "");
                ifMap.put("leaderEmp", "");

                log.info("parameter: {}", ifMap.toString());
                if (isProd) {
                    idcardVisitRepository.procedureDeliveryPassInIf(ifMap);
                }

                log.info("############## [END] 방문객 입문 I/F ##############");
            }

            String vstCompId = objectMapper.convertValue(paramMap.get("vstCompId"), String.class);

            List<Map<String, String>> carInfoList = repository.selectDeliveryPassCarInfo(paramMap);

            if (!carInfoList.isEmpty()) {

                SendSpmsDTO ifDTO = new SendSpmsDTO();

                for (Map<String, String> item : carInfoList) {
                    log.info("carInfo: {}", item != null
                        ? item.toString()
                        : "null");

                    ifDTO = new SendSpmsDTO();
                    ifDTO.setDivision("9"); // 납품
                    ifDTO.setMemberType("91"); // SK Hynix
                    ifDTO.setVehicleNumber(item.get("vehicleNumber"));
                    ifDTO.setVehicleName(item.get("vehicleName"));
                    ifDTO.setEnterDateTime(item.get("enterDateTime"));
                    ifDTO.setNoEntryDateTime(item.get("noEntryDatetime"));
                    ifDTO.setVisitPurpose(item.get("visitPurpose"));
                    ifDTO.setApproverName(item.get("approverName"));
                    ifDTO.setVisitorName(item.get("visitorName"));
                    ifDTO.setAffiliatedCompany(item.get("affiliatedCompany"));
                    ifDTO.setContactNumber(item.get("contactNumber"));
                    ifDTO.setDestination(item.get("destination"));
                    ifDTO.setNote(item.get("note"));
                    ifDTO.setSecurityID(item.get("securityId"));
                    ifDTO.setUnionNumber(item.get("unionNumber"));

                    if ("1101000001".equals(vstCompId)) { // 이천
                        log.info("############## [START] ##### 이천 납품차량 입문 I/F ##############");
                        log.info("#### vehicleNumber: {} ####", ifDTO.getVehicleNumber());
                        log.info("#### vehicleName: {} ####", ifDTO.getVehicleName());
                        log.info("#### visitorName: {} ####", ifDTO.getVisitorName());
                        log.info("parameter: {}", ReflectionToStringBuilder.toString(ifDTO));

                        if (isProd) {
                            icVehicleHaengbokRepository.sendICSpmsInsertMemberInner(ifDTO);
                        }

                        log.info("############## [END] ##### 이천 납품차량 입문 I/F ##############");
                    }
                    else if ("1109000001".equals(vstCompId)) { // 청주4(M15)
                        log.info("############## [START] ##### 청주4(M15) 납품차량 입문 I/F ##############");
                        log.info("#### vehicleNumber: {} ####", ifDTO.getVehicleNumber());
                        log.info("#### vehicleName: {} ####", ifDTO.getVehicleName());
                        log.info("#### visitorName: {} ####", ifDTO.getVisitorName());
                        log.info("parameter: {}", ReflectionToStringBuilder.toString(ifDTO));

                        if (isProd) {
                            cjVehicleCampusRepository.sendCJSpmsInsertMemberInner(ifDTO);
                        }

                        log.info("############## [END] ##### 청주4(M15) 납품차량 입문 I/F ##############");
                    }
                }
            }

            result.put("result", "OK");
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        log.info("[END] executeDeliveryPassInprocess");

        return result;
    }

    @Override
    public Boolean executeDeliveryPassInReset(Map<String, Object> paramMap) {
        log.info("[START] executeDeliveryPassInReset");
        Boolean result = false;

        try {
            boolean isProd = environment.acceptsProfiles(Profiles.of("prd"));
            paramMap.put("inGate", "0");

            repository.updateDeliveryPassInreset(paramMap);

            repository.updateDeliveryPassInreset2(paramMap);

            /* 카드키 인터페이스 처리
             *  1. 청정도 체크 (미 이수의 경우 해당 상위 건물을 넘기지 않는다.)
             *  2. 이천의 경우만 처리
             *  3. 건물출입시 선택한 건물(상위 건물만) + (미출입을 선택햇을 경우 포함) 기본 게이트 정문,후문,고담(116,117,118) 포함하여 넘겨야한다.
             */

            Map<String, Object> manCompMap = repository.selectDeliveryPassManComp(paramMap);

            if (manCompMap != null) {
                log.info("############## [START] 방문객 입문 Reset(출문) I/F ##############");

                String ioCardno = paramMap.get("ioCardno") != null
                    ? String.valueOf(paramMap.get("ioCardno"))
                    : "";

                Map<String, Object> ifMap = new HashMap<>();
                ifMap.put("cardNo", ioCardno != null
                    ? ioCardno.trim()
                    : "");
                ifMap.put("juminNo", manCompMap.get("juminNo"));
                ifMap.put("name", manCompMap.get("empNm"));
                ifMap.put("areaCode", paramMap.get("inCompId"));
                ifMap.put("dooroutAt", manCompMap.get("startDt")); //현재 시간
                ifMap.put("passportNo", manCompMap.get("passportNo"));
                ifMap.put("companyName", manCompMap.get("ioCompNm"));
                ifMap.put("reason", "방문객입문취소");
                ifMap.put("building", "");

                log.info("parameter: {}", ifMap.toString());

                if (isProd) { // 운영환경
                    idcardVisitRepository.procedureDeliveryPassOutIf(ifMap);
                }

                log.info("############## [END] 방문객 입문 Reset(출문) I/F ##############");
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        log.info("[END] executeDeliveryPassInReset");

        return result;
    }

    @Override
    public Map<String, Object> executeDeliveryPassOutprocess(Map<String, Object> paramMap) {
        Map<String, Object> result = new HashMap<>();

        log.info("[START] executeDeliveryPassOutprocess");

        try {
            boolean isProd = environment.acceptsProfiles(Profiles.of("prd"));

            paramMap.put("outIoknd", "2");
            repository.updateDeliveryPassOutprocess(paramMap);

            paramMap.put("ioknd", "2");
            repository.updateDeliveryPassIoKnd(paramMap);

            /* 카드키 인터페이스 처리
             *  1. 청정도 체크 (미 이수의 경우 해당 상위 건물을 넘기지 않는다.)
             *  2. 이천의 경우만 처리
             *  3. 건물출입시 선택한 건물(상위 건물만) + (미출입을 선택햇을 경우 포함) 기본 게이트 정문,후문,고담(116,117,118) 포함하여 넘겨야한다.
             */
            Map<String, Object> manCompMap = repository.selectDeliveryPassManComp(paramMap);

            if (manCompMap != null) {
                log.info("############## [START] 방문객 출문 I/F ##############");

                String ioCardno = paramMap.get("ioCardno") != null
                    ? String.valueOf(paramMap.get("ioCardno"))
                    : "";

                Map<String, Object> ifMap = new HashMap<>();
                ifMap.put("cardNo", ioCardno != null
                    ? ioCardno.trim()
                    : "");
                ifMap.put("juminNo", manCompMap.get("juminNo"));
                ifMap.put("name", manCompMap.get("empNm"));
                ifMap.put("areaCode", paramMap.get("inCompId"));
                ifMap.put("dooroutAt", manCompMap.get("startDt")); //현재 시간
                ifMap.put("passportNo", manCompMap.get("passportNo"));
                ifMap.put("companyName", manCompMap.get("ioCompNm"));
                ifMap.put("reason", "방문객출문");
                ifMap.put("building", "");

                log.info("parameter: {}", ifMap.toString());

                if (isProd) {
                    idcardVisitRepository.procedureDeliveryPassOutIf(ifMap);
                }

                log.info("############## [END] 방문객 출문 I/F ##############");
            }

            Map<String, Object> userMap = repository.selectDeliveryPassUserInfo(paramMap);

            /* [START] 정문 출입시 출문시 Smart Tag 시스템에 출문 정보 전송 I/F 20160830 FROM 김정민 책임 / HSK */
            Map<String, Object> smartTagMap = new HashMap<>();
            smartTagMap.put("vstApplNo", paramMap.get("dlvAppNo"));
            smartTagMap.put("ioEmpId", paramMap.get("ioEmpId"));
            smartTagMap.put("empNm", userMap.get("ioEmpNm"));
            smartTagMap.put("ioHpNo", userMap.get("ioHpNo"));
            smartTagMap.put("ioCompNm", userMap.get("ioCompNm"));
            smartTagMap.put("ioCardNo", paramMap.get("ioCardno"));
            smartTagMap.put("inGate", paramMap.get("inGate"));
            smartTagMap.put("inGateNm", userMap.get("gateNm"));
            smartTagMap.put("inDt", userMap.get("inDt"));
            smartTagMap.put("outDt", userMap.get("outDt"));
            smartTagMap.put("crtBy", paramMap.get("crtBy"));
            smartTagMap.put("bareaCd", paramMap.get("inCompId"));
            smartTagMap.put("bareaNm", paramMap.get("inCompNm"));
            smartTagMap.put("ioknd", "2");

            log.info("############## [START] SmartTag 출문 I/F ##############");
            log.info("#### parameter: {} ####", smartTagMap.toString());

            // int value = dbInsert("dmSmartTagVisitInOutProcessU", requestData.getFieldMap() ,"SmartTag", onlineCtx);
            if (isProd) {
                smartTagRepository.updateSmartTagVisitInOutProcess(smartTagMap);
            }

            log.info("############## [END] SmartTag 출문 I/F ##############");
            /* [END] 정문 출입시 출문시 Smart Tag 시스템에 출문 정보 전송 I/F 20160830 FROM 김정민 책임 / HSK */

            result.put("result", "OK");
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        log.info("[END] executeDeliveryPassOutprocess");

        return result;
    }

    @Override
    public Boolean executeDeliveryPassOutReset(Map<String, Object> paramMap) {

        log.info("[START] executeDeliveryPassOutReset");

        try {
            paramMap.put("outIoknd", "1");
            repository.updateDeliveryPassOutreset(paramMap);

            paramMap.put("ioknd", "1");
            repository.updateDeliveryPassIoKnd(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        log.info("[END] executeDeliveryPassOutReset");

        return true;
    }

    @Override
    public List<Map<String, Object>> selectBuildingPassHistList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectBuildingPassHistList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Integer selectBuildingPassHistListCnt(Map<String, Object> paramMap) {
        Integer resultCnt = null;

        try {
            resultCnt = repository.selectBuildingPassHistListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

    @Override
    public CommonXlsViewDTO selectBuildingPassHistListExcel(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;
        CommonXlsViewDTO commonXlsViewDTO = new CommonXlsViewDTO();
        commonXlsViewDTO.setFileName("건물출입 현황");

        try {
            resultList = repository.selectBuildingPassHistList(paramMap);

            // set header names
            String[] headerNameArr = { "동반", "방문객", "방문증번호", "방문일자", "입문처리", "출문처리", "예약여부", "인솔자", "출입사유" };

            commonXlsViewDTO.setHeaderNameArr(headerNameArr);

            // set column names (data field name)
            String[] columnNameArr = { "withsNm", "visitorNm", "ioCardno", "vstDt", "bldgInDt", "bldgOutDt", "gateYn", "guideInfo", "entryRsn" };
            commonXlsViewDTO.setColumnNameArr(columnNameArr);

            // set column width
            Integer[] columnWidthArr = { 7500, 9500, 7500, 7500, 8500, 8500, 5500, 7500, 9500 };
            commonXlsViewDTO.setColumnWidthArr(columnWidthArr);

            // set excel data
            commonXlsViewDTO.setDataList(resultList);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return commonXlsViewDTO;
    }

}
