package com.skshieldus.esecurity.service.sysmanage.impl;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.repository.sysmanage.PcEpRepository;
import com.skshieldus.esecurity.service.sysmanage.PcEpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PcEpServiceImpl implements PcEpService {

    @Autowired
    private PcEpRepository repository;

    //	@Autowired
    //	private environmentConfig environment;

    @Override
    public List<Map<String, Object>> selectPcEpList(Map<String, Object> paramMap) {
        log.info(">>>> selectPcEpList : " + paramMap);
        return repository.selectPcEpList(paramMap);
    }

    @Override
    public Map<String, Object> selectPcEpView(Map<String, Object> paramMap) {
        log.info(">>>> selectPcEpView : " + paramMap);

        Map<String, Object> pcEpMap = repository.selectPcEpView(paramMap);

        return pcEpMap;
    }

    @Override
    public Map<String, Object> selectInPcEpResult(Map<String, Object> paramMap) {
        log.info(">>>> selectPcEpResult : " + paramMap);

        Map<String, Object> pcEpMap = repository.selectInPcEpResult(paramMap);

        return pcEpMap;
    }

    @Override
    public Map<String, Object> selectOutPcEpResult(Map<String, Object> paramMap) {
        log.info(">>>> selectPcEpResult : " + paramMap);

        Map<String, Object> pcEpMap = repository.selectOutPcEpResult(paramMap);

        return pcEpMap;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean insertInPcEp(Map<String, Object> paramMap) {
        boolean result = true;
        try {

            log.info(">>>> insertPcEp : " + paramMap);

            Map<String, Object> pcEpMap = repository.selectInPcEpCheckInDate(paramMap);

            if (pcEpMap.size() > 0 && pcEpMap != null) {
                paramMap.put("epChkInDtm", pcEpMap.get("epChkInDtm"));
                paramMap.put("epResultCase", "InPcEpResultInsert");

                if (repository.insertInPcEp(paramMap) != 1) {
                    result = false;
                }

                if (repository.updatePcEpChkCase(paramMap) != 1) {
                    result = false;
                }

                if (repository.updatePcListCase(paramMap) != 1) {
                    result = false;
                }
            }

            if (result == true) {

                //업체물품 전산기기IF_NAC사용자등록
                //				sendToNcaPcRegister(paramMap)
                if (!sendToNcaPcRegister(paramMap)) {
                    result = false;
                }
                ;

                if ("Y".equalsIgnoreCase((String) paramMap.get("nacExptYn"))) {
                    //업체물품 전산기기IF_NAC예외신청
                    if (!sendToNcaPcExcept(paramMap)) {
                        result = false;
                    }
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
    public boolean insertOutPcEp(Map<String, Object> paramMap) {
        boolean result = true;
        try {

            log.info(">>>> insertPcEp : " + paramMap);

            Map<String, Object> pcEpMap = repository.selectInPcEpCheckInDate(paramMap);

            if (pcEpMap.size() > 0 && pcEpMap != null) {
                paramMap.put("epChkInDtm", pcEpMap.get("epChkInDtm"));
                paramMap.put("epResultCase", "OutPcEpResultInsert");

                if (repository.insertOutPcEp(paramMap) != 1) {
                    result = false;
                }

                if (repository.updatePcEpChkCase(paramMap) != 1) {
                    result = false;
                }

                if (repository.updatePcListCase(paramMap) != 1) {
                    result = false;
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
    public boolean sendToNcaPcExcept(Map<String, Object> paramMap) {
        log.info("\n>>>> sendToNcaPcExcept : " + paramMap);
        List<Map<String, Object>> requestRecords = repository.selectOutNetworkInfoNcaIF(paramMap);
        log.info("\\n>>>> sendToNcaPcExcept requestRecords - 조회 건수: " + requestRecords.size());

        String nacExcptGbn = "02";
        String urlPath = "nac/except";

        String _BaseURL = "";
        String statusCode = "";

        //		if ("prd".equals(environment.getApplication().getEnv())) {
        //			System.out.println("PcEpServiceImpl.sendToNcaPcExcept ---> 운영");
        //			_BaseURL = "http://nca.skhynix.com:9090/"; // 운영
        //		} else {
        //			System.out.println("PcEpServiceImpl.sendToNcaPcExcept ---> 개발 및 기타");
        //			_BaseURL = "http://ncadev.skhynix.com:9090/";
        //		}

        //		Integer applNo = Integer.parseInt((String)paramMap.get("inoutApplNo"));
        Integer applNo = (Integer) paramMap.get("inoutApplNo");
        //fn_sendToOutNetworkDevices 를 호출한다.

        //		if("01".equals(nacExcptGbn)){
        //			statusCode = CommonUtils.sendToNCA(requestRecords, _BaseURL, urlPath, applNo);
        //		}else {
        //			statusCode = CommonUtils.sendToNCA(requestRecords, _BaseURL, urlPath, applNo);
        //		}

        paramMap.put("urlPath", StringUtils.defaultIfEmpty(urlPath, ""));
        paramMap.put("statusCode", StringUtils.defaultIfEmpty(statusCode, ""));
        paramMap.put("applNo", applNo);
        paramMap.put("crtBy", "pcep");

        System.out.println("sendToNcaPcRegister paramMap >>>>>" + paramMap);

        //		ncaInterfaceRepository.insertNcaLog(paramMap);

        log.info("\n>>>> sendToNcaPcRegister 종료 ");
        return true;
    }

    @Override
    public boolean sendToNcaPcRegister(Map<String, Object> paramMap) {

        log.info("\n>>>> sendToNcaPcRegister : " + paramMap);
        List<Map<String, Object>> requestRecords = repository.selectNacExceptInfoNcaIF(paramMap);
        log.info("\\n>>>> sendToNcaPcRegister requestRecords - 조회 건수: " + requestRecords.size());

        String urlPath = "network/usage";

        String _BaseURL = "";
        String statusCode = "";

        //		if ("prd".equals(environment.getApplication().getEnv())) {
        //			System.out.println("PcEpServiceImpl.sendToNcaPcRegister ---> 운영");
        //			_BaseURL = "http://nca.skhynix.com:9090/"; // 운영
        //		} else {
        //			System.out.println("PcEpServiceImpl.sendToNcaPcRegister ---> 개발 및 기타");
        //			_BaseURL = "http://ncadev.skhynix.com:9090/";
        //		}

        Integer applNo = (Integer) paramMap.get("inoutApplNo");
        //fn_sendToOutNetworkDevices 를 호출한다.
        //		statusCode = CommonUtils.sendToNCA(requestRecords, _BaseURL, urlPath, applNo);

        paramMap.put("urlPath", "nac/register");
        paramMap.put("statusCode", StringUtils.defaultIfEmpty(statusCode, ""));
        paramMap.put("applNo", applNo);
        paramMap.put("crtBy", "pcep");

        System.out.println("sendToNcaPcRegister paramMap >>>>>" + paramMap);
        //		ncaInterfaceRepository.insertNcaLog(paramMap);

        log.info("\n>>>> sendToNcaPcRegister 종료 ");
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean insertPcEpBuildingOut(Map<String, Object> paramMap) {
        boolean result = true;
        try {

            log.info(">>>> insertPcEpBuildingOut : " + paramMap);

            Map<String, Object> xEmpInfo = repository.selectPcXempInfo(paramMap);
            Map<String, Object> pcInfo = repository.selectPcInfoBuilding(paramMap);

            // Check validation
            if ("1101000001".equals((String) xEmpInfo.get("compId")) || "1108000001".equals((String) xEmpInfo.get("compId"))
                || "1101000001".equals((String) pcInfo.get("inCompId")) || "1108000001".equals((String) pcInfo.get("inCompId"))) {  // 이천, 분당(정자)만 체크
                if (!((String) xEmpInfo.get("compId")).equals((String) pcInfo.get("inCompId"))) {

                    throw new EsecurityException(HttpStatus.ACCEPTED, HttpStatus.ACCEPTED.value(),
                        "반출캠퍼스가 다릅니다.");
                }
            }

            if ("O".equals((String) pcInfo.get("itemStat"))) {    // 외곽 반출상태
                throw new EsecurityException(HttpStatus.ACCEPTED, HttpStatus.ACCEPTED.value(),
                    "이미 외곽반출된 전산기기입니다.");
            }

            if ("1".equals((String) pcInfo.get("moveStat"))) { // 이동중
                throw new EsecurityException(HttpStatus.ACCEPTED, HttpStatus.ACCEPTED.value(),
                    "이동중인 전산기기입니다.");
            }

            if ("3".equals((String) xEmpInfo.get("gateKnd"))) {  // EP 센터
                int epNotEndCnt = repository.selectPcEpNotEndCnt(pcInfo);
                if (epNotEndCnt > 0) {
                    throw new EsecurityException(HttpStatus.ACCEPTED, HttpStatus.ACCEPTED.value(),
                        "처리되지 않은 EP 점검 데이터가 있습니다.");
                }
            }

            if (repository.insertPcBuildingOut(paramMap) != 1) {
                result = false;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new EsecurityException(HttpStatus.ACCEPTED, HttpStatus.ACCEPTED.value(), e.toString());
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean insertPcEpBuildingIn(Map<String, Object> paramMap) {
        boolean result = true;
        try {

            log.info(">>>> insertPcEpBuildingIn : " + paramMap);

            if (StringUtils.isEmpty((String) paramMap.get("inoutPcId"))
                || StringUtils.isEmpty((String) paramMap.get("xempId"))
                || StringUtils.isEmpty((String) paramMap.get("moveStrtDtm"))) {

                throw new EsecurityException(HttpStatus.ACCEPTED, HttpStatus.ACCEPTED.value(),
                    "요청데이터 오류입니다.");
            }

            Map<String, Object> xEmpInfo = repository.selectPcXempInfo(paramMap);
            log.info(">>>> xEmpInfo : " + xEmpInfo);

            Map<String, Object> pcInfo = repository.selectPcInfoBuilding(paramMap);
            log.info(">>>> pcInfo : " + pcInfo);

            // Check validation
            if ("1101000001".equals((String) xEmpInfo.get("compId")) || "1108000001".equals((String) xEmpInfo.get("compId"))
                || "1101000001".equals((String) pcInfo.get("inCompId")) || "1108000001".equals((String) pcInfo.get("inCompId"))) {  // 이천, 분당(정자)만 체크
                if (!((String) xEmpInfo.get("compId")).equals((String) pcInfo.get("inCompId"))) {

                    throw new EsecurityException(HttpStatus.ACCEPTED, HttpStatus.ACCEPTED.value(),
                        "반입캠퍼스가 다릅니다.");
                }
            }

            if ("O".equals((String) pcInfo.get("itemStat"))) {    // 외곽 반출상태
                throw new EsecurityException(HttpStatus.ACCEPTED, HttpStatus.ACCEPTED.value(),
                    "이미 외곽반출된 전산기기입니다.");
            }

            if (!"1".equals((String) pcInfo.get("moveStat"))) {
                throw new EsecurityException(HttpStatus.ACCEPTED, HttpStatus.ACCEPTED.value(),
                    "건물내 반입되어 있는 전산기기는 건물 반입처리를 할 수 없습니다.");
            }

            if ("3".equals((String) xEmpInfo.get("gateKnd"))) {  // EP 반입
                log.info(">>>> EP 반입 >>>>>>>>>>>> ");

                if ("1".equals((String) pcInfo.get("moveType"))) {  // 이동유형 : 반입
                    log.info(">>>> 이동유형 : 반입 >>>>>>>>>>>> ");
                    if ("N".equals((String) pcInfo.get("epInChkNeedYn"))) {
                        throw new EsecurityException(HttpStatus.ACCEPTED, HttpStatus.ACCEPTED.value(),
                            "EP 반입 점검이 필요 없는 기기입니다.");
                    }
                    pcInfo.put("epChkType", "1");
                    pcInfo.put("epchktype", "1");  // 반출점검
                }
                else {
                    log.info(">>>> 이동유형 : 반출점검 >>>>>>>>>>>> ");
                    if ("Y".equals((String) pcInfo.get("epOutChkDoneYn"))) {
                        throw new EsecurityException(HttpStatus.ACCEPTED, HttpStatus.ACCEPTED.value(),
                            "EP 반출점검이 완료된 PC입니다.");
                    }
                    pcInfo.put("epChkType", "2");  // 반출점검
                    pcInfo.put("epchktype", "2");  // 반출점검
                }

                //		    	pcInfo.put("testTestTest", "test");
                //		    	log.info(">>>> insertPcBuildingInEp : " + pcInfo);
                //		    	pcInfo.put("testtesttest", "test1");
                log.info(">>>> insertPcBuildingInEp : " + pcInfo);
                repository.insertPcBuildingInEp(pcInfo);
                log.info(">>>> insertPcBuildingInEp 완료: ");
            }
            else {

                if ("Y".equals((String) pcInfo.get("inDenyYn"))) {
                    throw new EsecurityException(HttpStatus.ACCEPTED, HttpStatus.ACCEPTED.value(),
                        "반입 불가 처리된 기기입니다.");
                }
                else {
                    if ("Y".equals((String) pcInfo.get("epInChkNeedYn")) && "N".equals((String) pcInfo.get("epInChkDoneYn"))) {
                        throw new EsecurityException(HttpStatus.ACCEPTED, HttpStatus.ACCEPTED.value(),
                            "EP 반입점검이 필요합니다.");
                    }
                }
            }

            if ("2".equals((String) pcInfo.get("moveType"))) {  // 이동유형이 '반출'일 경우 건물을 들어왔으므로 '건물간 이동'으로 수정
                if (repository.updatePcBuildingMoveType(pcInfo) != 1) {
                    log.info(">>>> updatePcBuildingMoveType >>: " + pcInfo);
                    result = false;
                }
            }

            if (repository.updatePcBuildingInInoutList(paramMap) != 1) {
                log.info(">>>> updatePcBuildingInInoutList >>: " + paramMap);
                result = false;
            }

            if (repository.updatePcBuildingInInoutPcMove(paramMap) != 1) {
                log.info(">>>> updatePcBuildingInInoutPcMove >>: " + paramMap);
                result = false;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new EsecurityException(HttpStatus.ACCEPTED, HttpStatus.ACCEPTED.value(), e.toString());
        }

        //		if(result==false) {
        //			throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        //		}
        log.info(">>>> insertPcEpBuildingIn   >>>>>>>>>>>>> result: " + result);
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean updatePcEpResultCancel(Map<String, Object> paramMap) {
        boolean result = true;
        try {

            log.info(">>>> updatePcEpResultCancel : " + paramMap);

            Map<String, Object> pcEpMap = repository.selectInPcEpCheckInDate(paramMap);

            if (pcEpMap.size() > 0 && pcEpMap != null) {
                paramMap.put("epChkInDtm", pcEpMap.get("epChkInDtm"));
                paramMap.put("epResultCase", "InOutPcEpResultCancelUpdate");

                if (repository.updatePcEpChkCase(paramMap) != 1) {
                    result = false;
                }

                if (repository.updatePcListCase(paramMap) != 1) {
                    result = false;
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean updatePcEpCancelCheck(Map<String, Object> paramMap) {
        boolean result = true;
        try {

            log.info(">>>> updatePcEpResultCancel : " + paramMap);

            Map<String, Object> pcEpMap = repository.selectInPcEpCheckInDate(paramMap);

            if (pcEpMap.size() > 0 && pcEpMap != null) {
                paramMap.put("epChkInDtm", pcEpMap.get("epChkInDtm"));
                paramMap.put("epResultCase", "InPcEptCancelCheckUpdate");

                if (repository.updatePcEpChkCase(paramMap) != 1) {
                    result = false;
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
    public Map<String, Object> selectPcEpReceiveView(Map<String, Object> paramMap) {
        log.info(">>>> selectPcEpReceiveView : " + paramMap);

        Map<String, Object> pcEpMap = repository.selectPcEpReceiveView(paramMap);

        return pcEpMap;
    }

}
