package com.skshieldus.esecurity.service.entmanage.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skshieldus.esecurity.repository.entmanage.VisitEntranceRepository;
import com.skshieldus.esecurity.repository.entmanage.smartTag.SmartTagRepository;
import com.skshieldus.esecurity.service.common.ApprovalService;
import com.skshieldus.esecurity.service.entmanage.FrontDoorService;
import com.skshieldus.esecurity.service.entmanage.VisitEntranceIfService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class FrontDoorServiceImpl implements FrontDoorService {

    @Autowired
    private Environment environment;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ApprovalService approvalService;

    @Autowired
    private VisitEntranceRepository repository;

    @Autowired
    private SmartTagRepository smartTagRepository;

    @Autowired
    private VisitEntranceIfService ifService;

    @Override
    public Map<String, Object> executeFrontDoorInprocess(Map<String, Object> paramMap) {
        Map<String, Object> result = new HashMap<>();

        log.info("[START] 외각입문처리");
        log.info("parameter: {}", paramMap.toString());

        try {
            boolean isProd = environment.acceptsProfiles(Profiles.of("prd"));
            String empId = objectMapper.convertValue(paramMap.get("empId"), String.class);
            String acIp = objectMapper.convertValue(paramMap.get("acIp"), String.class);

            paramMap.put("vstDt", paramMap.get("vstDt") != null
                ? String.valueOf(paramMap.get("vstDt")).replace("-", "")
                : paramMap.get("vstDt"));

            // 입문한 카드 번호인지 확인
            Integer ioGateCnt = repository.selectFrontDoorCardChk(paramMap);

            if (ioGateCnt > 0) {
                log.info("입문한 카드 번호:: {}", paramMap.get("ioCardno"));

                // 입문한 카드 번호인경우, 종료
                result.put("result", "0002");
                return result;
            }

            // 명함확인
            String compcardYn = objectMapper.convertValue(paramMap.get("compcardYn"), String.class);
            if ("Y".equals(compcardYn)) {
                log.info("명함확인 확인:: mergeFrontDoorCompCardChk : {}", paramMap.get("ioEmpId"));
                repository.mergeFrontDoorCompCardChk(paramMap);
            }

            // 외곽 입문 처리
            paramMap.put("inIoknd", "1");
            int updateCnt = repository.updateFrontDoorInOutprocess(paramMap);

            // 사진 3회체크
            String vstYn = repository.selectFrontDoorPhotoChk(paramMap);
            if ("Y".equals(vstYn)) {
                result.put("result", "0001");
            }
            else {
                result.put("result", updateCnt > 0
                    ? "OK"
                    : "FAIL");
            }

            /*
             * 외곽 입문 등록 및 게이트 정보 등록 완료 후 후처리 작업 수행.
             * 1. 카드키 시스템 I/F(게이트 출입권한 부여)
             * 2. 외부인 보안위규 등록.(상시출입증 소지자 방문예약 시,)
             * 3. 외부인 외곽 입문 구성원 알림.
             * 4. 온라인 인솔 시스템 I/F
             * 5. Cube 시스템 I/F
             * 6. SSM 시스템 I/F
             */

            // 출입할 대상 게이트 목록 등록. 대상 Table : IO_VST_MAN_GATE_IO
            paramMap.put("delYn", "N");

            int insertCnt = repository.insertFrontDoorInGate(paramMap);
            log.info("result insertFrontDoorInGate : {}", insertCnt > 0);

            Map<String, Object> vstInfoDataMap = repository.selectFrontDoorVstInfo(paramMap);

            // 외부 시스템과 I/F 위한 방문예약 메인 데이터 조회
            paramMap.put("inVstDt", paramMap.get("vstDt"));
            Map<String, Object> visitReserveDataMap = repository.selectFrontDoorUserInfo(paramMap);

            if (visitReserveDataMap == null) {
                throw new Exception("방문객정보 없음");
            }

            log.info("방문객 정보 : {}", visitReserveDataMap.toString());

            String strGateInfo = objectMapper.convertValue(visitReserveDataMap.get("gateList"), String.class);
            String[] gateInfoList = strGateInfo != null
                ? strGateInfo.split(":")
                : null;

            if (gateInfoList != null) {
                log.info("strGateInfo : {}", strGateInfo);
                log.info("GATE_LIST length : {}", gateInfoList.length);
            }
            else {
                log.info("GATE_LIST IS Null");
            }

            /*
             *  온라인 인솔 대상게이트  추출.
             */
            List<String> bldgIdList = new ArrayList<>();
            List<String> bldgNmList = new ArrayList<>();

            if (strGateInfo != null && strGateInfo.trim().length() != 0) {
                String[] tempLeadYn = gateInfoList[4].substring(1).split(",");
                String[] tempBldgNmList = gateInfoList[0].substring(1).split(",");
                String[] tempbldgIdList = gateInfoList[1].substring(1).split(",");

                for (int i = 0; i < tempLeadYn.length; i++) {
                    if (tempLeadYn[i].equals("Y")) {  // 온라인 인솔 승인 게이트 추가.
                        bldgIdList.add(tempbldgIdList[i]);
                        bldgNmList.add(tempBldgNmList[i]);
                    }
                }
            }

            String unpermittedLocAgreeYn = objectMapper.convertValue(visitReserveDataMap.get("unpermittedLocAgreeYn"), String.class);
            String inCompId = objectMapper.convertValue(paramMap.get("inCompId"), String.class);

            // 임시 예외 처리 (위치동의값이 'undefined' 으로 들어오고 있음)
            unpermittedLocAgreeYn = "undefined".equals(unpermittedLocAgreeYn) || "null".equals(unpermittedLocAgreeYn)
                ? "N"
                : unpermittedLocAgreeYn;

            // 온라인 인솔 I/F 조건
            boolean enableSendingCube =
                "Y".equals(unpermittedLocAgreeYn) // 위치동의 여부
                && (bldgIdList != null && bldgIdList.size() > 0 && bldgIdList.size() == bldgNmList.size()) // 건물 코드와 이름 여부
                && !"1108000001".equals(inCompId); // 정자 분당사무소 제외

            log.info("in Process : UNPERMITTED_LOC_AGREE_YN : {}", unpermittedLocAgreeYn);
            log.info("in Process : bldgList.size() : {}", bldgIdList.size());
            log.info("in Process : enableSendingCube(온라인 인솔) : {}", enableSendingCube);

            /*
             *  외곽 게이트 입문시, 특정 게이트 권한 부여.
             */
            paramMap.put("enableSendingCube", enableSendingCube);  // 큐브 발송 대상 여부
            ifService.executeGateEnterPermissionIf(paramMap);

            /*
             *  방문목적이 상담이거나 회의이고 사무실,회의실 방문예약인 경우.
             */
            String vstObj = objectMapper.convertValue(vstInfoDataMap.get("vstObj"), String.class);
            String strIsOfficeRoom = objectMapper.convertValue(vstInfoDataMap.get("isOfficeRoom"), String.class);

            boolean isOfficeRoom = ("A0131001".equals(vstObj) || "A0131002".equals(vstObj)) && // 상담 또는 회의 목적
                                   "Y".equals(strIsOfficeRoom);

            visitReserveDataMap.put("REQ_CRT_BY", paramMap.get("crtBy"));

            /*
             * 접견자, 인솔자 목록 데이터 추출.
             */
            StringBuilder itBuildingLeader = new StringBuilder(); //  외부인 방문예약 보안 관리 강화

            List<Map<String, Object>> visitResrveCoEmpList = repository.selectSendFrontDoorInProcessMsgTo(paramMap);

            /*
             *  외부인 방문 내용 구성원 알림.
             */
            int i = 0;
            for (Map<String, Object> visitResrveCoEmp : visitResrveCoEmpList) {

                if (i == 0) {
                    itBuildingLeader.append(visitResrveCoEmp.get("empId"));
                }
                else {
                    itBuildingLeader.append("\",\"");
                    itBuildingLeader.append(visitResrveCoEmp.get("empId"));
                }

                /*
                 * 구성원 알림 데이터 조회
                 */
                // 방문장소, 목적에 따라 kakao, mail 데이터 조회.
                if (isOfficeRoom) {
                    visitReserveDataMap.put("VST_DETL_RSN", vstInfoDataMap.get("vstDetlRsn"));
                    visitReserveDataMap.put("CO_HP_NO", visitResrveCoEmp.get("hpNo"));
                }

                List<Map<String, String>> contentList = ifService.executeGetVisitorGateInNotiData(isOfficeRoom, visitReserveDataMap);

                //				log.info("isOfficeRoom: {}", isOfficeRoom);
                //				log.info("Mail Contents ******************************");
                //				log.info(contentList != null && contentList.get(0) != null ? contentList.get(0).toString() : "null");
                //				log.info("Kakao Contents ******************************");
                //				log.info("Mail Contents ******************************");
                //				log.info(contentList != null && contentList.get(1) != null ? contentList.get(1).toString() : "null");

                if (contentList != null && contentList.get(0) != null) {
                    // Send mail
                    ifService.executeGateInOutVstMail(contentList.get(0), empId, acIp);
                }

                // 알림 내용 발송
                if (isOfficeRoom) {

                    // Send Kakao
                    // 주석처리 2023-06-09
                    //					if(contentList.get(1) != null) {
                    //						Map<String, String> kakaoMap = contentList.get(1);
                    //
                    //						// Kakao message
                    //						RequestWrapModel<KakaoMessageDTO> wrapParams = new RequestWrapModel<>();
                    //						KakaoMessageDTO kakaoMessageDTO = new KakaoMessageDTO();
                    //						kakaoMessageDTO.setKTemplateCode(kakaoMap.get("templateCode"));
                    //						kakaoMessageDTO.setSubject(kakaoMap.get("kakaoTitle"));
                    //						kakaoMessageDTO.setDstaddr(kakaoMap.get("hpNo"));
                    //						kakaoMessageDTO.setCallback(kakaoMap.get("callbackTel"));
                    //						kakaoMessageDTO.setText(kakaoMap.get("kakaoMsg"));
                    //						kakaoMessageDTO.setText2(kakaoMap.get("smsMsg"));
                    //						kakaoMessageDTO.setKAttach(kakaoMap.get("kAttach"));
                    //						kakaoMessageDTO.setEmpId(empId);
                    //
                    //						wrapParams.setParams(kakaoMessageDTO);
                    //						commonApiClient.sendKakaoMessage(wrapParams);
                    //					}
                }

                i++;
            }
            // end loop

            /* 보안위규 자동등록(상시출입증 미소지 사유로 방문예약 했을경우)
             * 외부인 방문예약 보안 관리 강화_E
             */
            String ioPassYn = objectMapper.convertValue(paramMap.get("ioPassYn"), String.class);
            String ioPassGbn = objectMapper.convertValue(paramMap.get("ioPassGbn"), String.class);

            if ("Y".equals(ioPassYn) && "A0490001".equals(ioPassGbn)) {
                Map<String, Object> violatReqMap = ifService.getViolationData(paramMap, visitReserveDataMap);
                // violatReqMap.put("scIoOfendDocNo", du_FrontDoor.dmFrontDoor_Violation_Seq_S(requestData, onlineCtx).getField("SC_IO_OFEND_DOC_NO"));
                repository.insertFrontDoorViolation(violatReqMap); // scIoOfendDocNo는 selectKey 사용으로 인해 violatReqMap 내 존재함
            }

            log.info("gateList: {}", visitReserveDataMap.get("gateList"));

            String strGateList = objectMapper.convertValue(visitReserveDataMap.get("gateList"), String.class);
            String[] reserveGateInfoList = strGateList != null
                ? strGateList.split(":")
                : null;

            // 온라인 인솔 시스템 I/F
            // 건물 상세 위치가 아니라 건물 자체만 지정할 경우, 상위 건물이 0으로 조회 되기 때문에 정보가 없는 값이 조회됨.
            // -> 문자열을 나눌때 구분자는 포함이 되지만 건물이름 값이 없기 때문에 길이0인 문자열이 생성되는 문제 발생.
            // 길이가 0인 경우 list를 null로 변경.
            // index 별 값 - 0:건물이름, 1:건물코드, 2:게이트 이름, 3:게이트코드, 4:게이트별 인솔여부

            visitReserveDataMap.put("VST_APPL_NO", paramMap.get("vstApplNo"));
            visitReserveDataMap.put("IN_VST_DT", paramMap.get("inVstDt"));
            visitReserveDataMap.put("IO_EMP_ID", paramMap.get("ioEmpId"));

            log.info("[START] Online Lead System");

            Map<String, Object> smartTagIFDataMap = ifService.getSmartTagIfData(visitReserveDataMap, reserveGateInfoList);

            if (reserveGateInfoList != null && reserveGateInfoList.length > 0 && smartTagIFDataMap != null) {
                log.info("[START] Call procedureOutGateInProcSmartTagIf");
                log.info("parameter: {}", smartTagIFDataMap.toString());

                if (isProd) {
                    try {
                        // dbExecuteProcedure("dmOutGate_InProc_SmartTag_IF", requestData.getFieldMap(),"_SmartTag", onlineCtx);
                        smartTagRepository.procedureOutGateInProcSmartTagIf(smartTagIFDataMap);
                    } catch (Exception e) {
                        log.error("Exception smartTagRepository: {}", e.toString());
                    }
                }

                log.info("[END] Call procedureOutGateInProcSmartTagIf");
            }

            log.info("[END] Online Lead System");

            if (enableSendingCube) {
                /*
                 * Cube System I/F
                 */
                log.info("************** [START] Cube IF 수행 *******************");

                // 큐브 메세지 수신 대상 설정.
                String receiverList = objectMapper.convertValue(visitReserveDataMap.get("empId"), String.class);

                isOfficeRoom = true;
                // 회의 또는 상담+사무실 방문이면 큐브메시지 발송 대상을 접수구성원+건물인솔자로 엎어침.
                if (isOfficeRoom) {
                    receiverList = itBuildingLeader.toString();
                }
                else {
                    // 접수자와 접견자가 다를 경우, 접견자 추가를 큐브 수신 대상에 추가.

                    String visitEmpId = objectMapper.convertValue(visitReserveDataMap.get("empId"), String.class);
                    String meetEmpId = objectMapper.convertValue(visitReserveDataMap.get("meetEmpId"), String.class);

                    if (!visitEmpId.equals(meetEmpId)) {
                        receiverList = visitReserveDataMap.get("empId") + "\",\"" + visitReserveDataMap.get("meetEmpId");
                    }
                }

                // 큐브 메세지 생성
                String[] cubeMsgList = ifService.getCubeIfData(paramMap, visitReserveDataMap, receiverList, bldgIdList, bldgNmList);

                Map<String, Object> cubeIfDataSetMap = new HashMap<>();
                cubeIfDataSetMap.put("cubeMsgList", cubeMsgList);
                cubeIfDataSetMap.put("vstApplNo", paramMap.get("vstApplNo"));

                // fu_FrontDoor.fmFrontDoor_cubeIF(cubeIfDataSet, onlineCtx);
                ifService.executeFrontDoorCubeIF(cubeIfDataSetMap);

                log.info("************** [END] Cube IF 수행 *******************");
            }
            else {
                log.info("************** Skip Cube IF  *******************");
            }

            /*
             * SSM I/F 처리.
             */
            String compGate = objectMapper.convertValue(visitReserveDataMap.get("compGate"), String.class);
            String terminalId = objectMapper.convertValue(visitReserveDataMap.get("terminalId"), String.class);

            log.info("************** [START] SSM IF 수행 *******************");

            paramMap.put("accessType", 1);
            paramMap.put("terminalId", compGate + "_" + (terminalId != null
                ? terminalId
                : "안내데스크"));

            log.info("accessType: {}", paramMap.get("accessType"));
            log.info("terminalId: {}", paramMap.get("terminalId"));

            ifService.executeFrontDoorSsmIF(paramMap);

            log.info("************** [END] SSM IF 수행 *******************");

            /*
             * 사진정보를 response에 추가.
             */
            if (paramMap.get("sourceSystem") != null && paramMap.get("sourceSystem").equals("KIOSK")) {
                result.put("filePhoto", visitReserveDataMap.get("filePhoto"));
            }

            result.put("result", "OK");
        } catch (Exception e) {
            log.error("외각입문처리 Error: {}", e.toString());
        }

        log.info("외각입문처리 결과: {}", result.toString());
        log.info("[END] 외각입문처리");

        return result;
    }

    @Override
    public Map<String, Object> executeFrontDoorOutprocess(Map<String, Object> paramMap) {
        Map<String, Object> result = new HashMap<>();

        log.info("[START] 외각출문처리");
        log.info("parameter: {}", paramMap.toString());

        try {
            paramMap.put("outIoknd", "2");
            paramMap.put("vstDt", paramMap.get("vstDt") != null
                ? String.valueOf(paramMap.get("vstDt")).replace("-", "")
                : paramMap.get("vstDt"));

            // IDataSet dmFrontDoor_Outprocess = dU_FrontDoor.dmFrontDoor_Outprocess(requestData, onlineCtx);
            int updateCnt = repository.updateFrontDoorInOutprocess(paramMap);

            Map<String, Object> bldgMap = new HashMap<>();
            bldgMap.put("vstApplNo", String.valueOf(paramMap.get("vstApplNo")));
            bldgMap.put("ioEmpId", String.valueOf(paramMap.get("ioEmpId")));

            // IRecordSet bldgRs = dU_FrontDoor.dmBldgInfoList(requestData, onlineCtx).getRecordSet("list");
            String bldgNm = repository.selectBldgNm(bldgMap);

            if (updateCnt > 0 && !"건물미출입".equals(bldgNm)) { // 건물미출입이 아닌경우.
                paramMap.put("guideGbn", "0");
                updateCnt = repository.updateIoVstManGate(paramMap); // dmFrontDoor_CubeRcvMsgs
            }

            boolean isProd = environment.acceptsProfiles(Profiles.of("prd"));

            // String inCompId = objectMapper.convertValue(paramMap.get("inCompId"), String.class);
            String empId = objectMapper.convertValue(paramMap.get("empId"), String.class);
            String acIp = objectMapper.convertValue(paramMap.get("acIp"), String.class);

            /*
             * 카드키 인터페이스 처리
             * 1. 청정도 체크 (미 이수의 경우 해당 상위 건물을 넘기지 않는다.)
             * 2. 이천의 경우만 처리
             * 3. 건물출입시 선택한 건물(상위 건물만) + (미출입을 선택햇을 경우 포함) 기본 게이트 정문,후문,고담(116,117,118)
             * 포함하여 넘겨야한다.
             */
            paramMap.put("reason", "방문객출문");
            ifService.executeFrontDoorCardIF(paramMap);

            // 이용자 정보 조회
            paramMap.put("inVstDt", paramMap.get("vstDt"));
            Map<String, Object> viewMap = repository.selectFrontDoorUserInfo(paramMap);

            /* 외부인 방문예약 보안 관리 강화_S */
            Map<String, Object> vstInfoMap = repository.selectFrontDoorVstInfo(paramMap);

            /*******
             * 정문 출문시 고담 방문객 주차장 사용자에 대하여, 출문 차량 정보를 검색하여 그 결과를 출문 시간까지 포함하여,
             * 고담주차장 LPR 시스템에 연동한다. 행복한 만남 방문 차량(고담 주차장 이용자에 한하여, 출문시 출차 정보 제공)
             * 고담 주차장 방문증 교환이후 차량 방문시 차량 출문 제한 시간을 30분으로 제한하여, 30분 이후 출차시 경고 1회
             * , 누적2회 부터는 1개월 정지, 그 이후는 1개월씩 추가 LPR 시스템에서 관련 문자 발송하고, 출입정치 차량
             * 발생시 프로시져 sp_if_io_vstcaR_deny_hist 를 이용하여 데이터를 제공받아 물리보안팀에서 현황 파악
             * 가능하도록 차량 제한 현황에 보여준다. 관련 테이블 if_io_vstcar_deny_hist, io_cardeny
             * 2019.09.10 HSK
             */
            // TODO 고담주차장 연동 변경에 의해 해당 로직 사용여부 검토 필요 by kwg. 220228
            //				if("1101000001".equals(inCompId)) { // 이천 사업장(정문, 후문, 고담 포함)
            //					try {
            //						// dU_FrontDoor.dmFrontDoor_get_OutProc_LprInfo_IF(requestData, onlineCtx);
            //						ifService.executeFrontDoorOutProcLprInfoIF(Integer.parseInt(String.valueOf(paramMap.get("vstApplNo"))));
            //						result.put("result", "OK");
            //					} catch (Exception e) {
            //						throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
            //					}
            //				}

            /*
             * SSM I/F 처리.
             */
            String compGate = objectMapper.convertValue(viewMap.get("compGate"), String.class);
            String terminalId = objectMapper.convertValue(viewMap.get("terminalId"), String.class);

            log.info("************** [START] SSM IF 수행 *******************");

            paramMap.put("accessType", 2);
            paramMap.put("terminalId", compGate + "_" + (terminalId != null
                ? terminalId
                : "안내데스크"));

            log.info("accessType: {}", paramMap.get("accessType"));
            log.info("terminalId: {}", paramMap.get("terminalId"));

            ifService.executeFrontDoorSsmIF(paramMap);

            log.info("************** [END] SSM IF 수행 *******************");

            /**
             * SmartTag I/F 처리.
             */
            paramMap.put("inVisitDate", paramMap.get("vstDt"));
            paramMap.put("inVisitSeq", paramMap.get("vstApplNo"));
            paramMap.put("inVisitorId", paramMap.get("ioEmpId"));
            paramMap.put("inIdcardId", paramMap.get("ioCardno"));

            try {
                log.info("**************** [START] SmartTag I/F :: procedureOutGateOutProcSmartTagIF ****************");
                log.info("parameter: {}", paramMap.toString());

                if (isProd) { // 운영환경
                    smartTagRepository.procedureOutGateOutProcSmartTagIF(paramMap);
                }

                log.info("**************** [END] SmartTag I/F :: procedureOutGateOutProcSmartTagIF ****************");
            } catch (Exception e) {
                log.error("SmartTag I/F Error: {}", e.toString());
            }

            /**
             * 출문 결과 Mail, 카카오톡 발송
             */
            String vstObj = objectMapper.convertValue(vstInfoMap.get("vstObj"), String.class);
            String isOfficeRoom = objectMapper.convertValue(vstInfoMap.get("isOfficeRoom"), String.class);

            // mail
            Map<String, String> mailReqMap = null;

            if (("A0131001".equals(vstObj) || "A0131002".equals(vstObj)) && // 상담 또는 회의 목적
                "Y".equals(isOfficeRoom)) { // 사무실/회의실 방문 여부

                List<Map<String, Object>> rsSendList = repository.selectSendFrontDoorInProcessMsgTo(paramMap);

                if (rsSendList != null) {
                    // kakako
                    //					RequestWrapModel<KakaoMessageDTO> wrapParams = null;
                    //					KakaoMessageDTO kakaoMessageDTO = null;

                    String vstDetlRsn = "";
                    String[] vstDetlRsnArrs = null;
                    String[] vstDetlRsnArr = null;
                    List<String> vstDetlRsnList = null;

                    String msg = "방문예약으로 (" + vstInfoMap.get("ioCompNm") + ")" + vstInfoMap.get("ioEmpNm") + "님 이 출문하셨습니다.";

                    for (Map<String, Object> item : rsSendList) {

                        // Kakao message
                        // 주석처리 2023-06-09
                        //						wrapParams = new RequestWrapModel<>();
                        //						kakaoMessageDTO = new KakaoMessageDTO();
                        //						kakaoMessageDTO.setKTemplateCode("SJT_066371");
                        //						kakaoMessageDTO.setSubject("방문예약 출문");
                        //						kakaoMessageDTO.setDstaddr(item.get("hpNo") != null ? String.valueOf(item.get("hpNo")).replace("-", "") : null);
                        //						kakaoMessageDTO.setCallback("03151854114");
                        //						kakaoMessageDTO.setText(msg);
                        //						kakaoMessageDTO.setText2(msg);
                        //						kakaoMessageDTO.setKAttach("");
                        //						kakaoMessageDTO.setEmpId(empId);
                        //
                        //						log.info("Send kakao message: {}", kakaoMessageDTO.toString());
                        //						wrapParams.setParams(kakaoMessageDTO);
                        //						commonApiClient.sendKakaoMessage(wrapParams);

                        // mail
                        mailReqMap = new HashMap<>();
                        mailReqMap.put("empId", objectMapper.convertValue(paramMap.get("crtBy"), String.class));
                        mailReqMap.put("ioCompNm", objectMapper.convertValue(viewMap.get("ioCompNm"), String.class));
                        mailReqMap.put("ioEmpNm", objectMapper.convertValue(viewMap.get("ioEmpNm"), String.class));
                        mailReqMap.put("ioJwNm", objectMapper.convertValue(viewMap.get("ioJwNm"), String.class));
                        mailReqMap.put("ioHpNo", objectMapper.convertValue(viewMap.get("ioHpNo"), String.class));
                        // 기존 방문사유 mailReqMap.put("ioVstRsn", objectMapper.convertValue(viewMap.get("ioVstRsn"), String.class));

                        /* 방문상세 사유 파싱_S */
                        vstDetlRsn = objectMapper.convertValue(vstInfoMap.get("vstDetlRsn"), String.class);
                        vstDetlRsnArrs = vstDetlRsn != null
                            ? vstDetlRsn.split(";")
                            : null;

                        vstDetlRsnList = new ArrayList<>();

                        for (String rsns : vstDetlRsnArrs) {
                            vstDetlRsnArr = rsns.split("@");
                            vstDetlRsnList.add(vstDetlRsnArr[vstDetlRsnArr.length - 1]); // 배열의 마지막 값(=방문사유)
                        }

                        StringBuilder vstDetlRsnSB = new StringBuilder();

                        for (int k = 0; k < vstDetlRsnList.size(); k++) {
                            vstDetlRsnSB.append((k + 1) + " : " + vstDetlRsnList.get(k) + "\n");
                        }
                        /* 방문상세 사유 파싱_E */

                        mailReqMap.put("ioVstRsn", vstDetlRsnSB.toString());

                        mailReqMap.put("inDt", objectMapper.convertValue(viewMap.get("inDt"), String.class));
                        mailReqMap.put("outDt", objectMapper.convertValue(viewMap.get("outDt"), String.class));
                        mailReqMap.put("gateNm", objectMapper.convertValue(viewMap.get("gateNm"), String.class));
                        mailReqMap.put("inoutKnd", "OUT");

                        mailReqMap.put("coEmail", objectMapper.convertValue(item.get("email"), String.class)); // 받는 사람 메일

                        ifService.executeGateInOutVstMail(mailReqMap, empId, acIp);
                    } // end loop
                }
            }
            else {

                // mail
                mailReqMap = new HashMap<>();
                mailReqMap.put("empId", objectMapper.convertValue(paramMap.get("crtBy"), String.class));
                mailReqMap.put("ioCompNm", objectMapper.convertValue(viewMap.get("ioCompNm"), String.class));
                mailReqMap.put("ioEmpNm", objectMapper.convertValue(viewMap.get("ioEmpNm"), String.class));
                mailReqMap.put("ioJwNm", objectMapper.convertValue(viewMap.get("ioJwNm"), String.class));
                mailReqMap.put("ioHpNo", objectMapper.convertValue(viewMap.get("ioHpNo"), String.class));
                mailReqMap.put("ioVstRsn", objectMapper.convertValue(viewMap.get("ioVstRsn"), String.class));
                mailReqMap.put("coEmail", objectMapper.convertValue(viewMap.get("coEmail"), String.class));
                mailReqMap.put("inDt", objectMapper.convertValue(viewMap.get("inDt"), String.class));
                mailReqMap.put("outDt", objectMapper.convertValue(viewMap.get("outDt"), String.class));
                mailReqMap.put("gateNm", objectMapper.convertValue(viewMap.get("gateNm"), String.class));
                mailReqMap.put("inoutKnd", "OUT");

                ifService.executeGateInOutVstMail(mailReqMap, empId, acIp);
            } // end if

            log.info("외각입문 모든 처리 완료");
            result.put("result", "OK");

            // 모든 처리 완료 후 Kiosk로 방문증 반납 요청.
            String issueGbn = objectMapper.convertValue(viewMap.get("issueGbn"), String.class);
            String transactionType = objectMapper.convertValue(paramMap.get("transactionType"), String.class);

            // Kiosk에서 발급한 방문증이지만 출문은 e-Security 에서 진행한 내용인지 확인
            if ("1".equals(issueGbn) // Kiosk 에서 발급한 방문증인가?
                && (transactionType == null || "".equals(transactionType))) // e-Security에서 출문처리하는 것인가?
            {

                // transactionType 값이 없을 경우, 반납요청을 KIOSK에 해야 하기 때문에 방문증 정보 관련 JsonObject 를 생성.

                // 반납요청 정보 생성.
                Map<String, Object> sendingData = new HashMap<>();
                sendingData.put("ioEmpId", paramMap.get("ioEmpId"));
                sendingData.put("vstApplNo", paramMap.get("vstApplNo"));
                sendingData.put("cardNo", paramMap.get("ioCardno"));
                sendingData.put("result", "OK");

                // Kiosk I/F service 호출.
                // 주석처리 2023-06-09
                //				kioskifService.returnCard(sendingData);
            }
        } catch (Exception e) {
            log.error("외각출문처리 Error: {}", e.toString());
            result.put("result", "FAIL");
        }

        log.info("외각출문처리 결과: {}", result.toString());
        log.info("[END] 외각출문처리");

        return result;
    }

}
