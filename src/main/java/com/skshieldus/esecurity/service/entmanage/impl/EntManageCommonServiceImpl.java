package com.skshieldus.esecurity.service.entmanage.impl;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.repository.entmanage.EntManageCommonRepository;
import com.skshieldus.esecurity.repository.entmanage.esecuritysi.EsecuritySiRepository;
import com.skshieldus.esecurity.service.entmanage.EntManageCommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
public class EntManageCommonServiceImpl implements EntManageCommonService {

    @Autowired
    private Environment environment;

    @Autowired
    private EntManageCommonRepository repository;

    @Autowired
    private EsecuritySiRepository esecuritySiRepository;

    @Override
    public Map<String, Object> selectPassReceipt(Integer passApplNo) {
        Map<String, Object> result = null;

        try {
            result = repository.selectPassReceipt(passApplNo);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Map<String, Object> selectPassReceiptByBldg(Integer passBldgApplNo) {
        Map<String, Object> result = null;

        try {
            result = repository.selectPassReceiptByBldg(passBldgApplNo);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public String selectPassIDcardId(Map<String, Object> paramMap) {
        String result = "";

        try {
            result = repository.selectPassIDcardId(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> selectOldPassBuildingList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectOldPassBuildingList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public List<Map<String, Object>> selectNewPassBuildingList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectNewPassBuildingList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public List<Map<String, Object>> selectPassRequestCoBldgList(String[] params) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectPassRequestCoBldgList(params);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Map<String, Object> selectPassInsStopDenyInfo(Map<String, Object> paramMap) {
        Map<String, Object> result = null;

        try {
            String siteType = paramMap.get("siteType") != null
                ? (String) paramMap.get("siteType")
                : "";

            if ("HS".equals(siteType)) {
                //				result = esecurityHsRepository.selectPassInsStopDenyInfo(paramMap);
            }
            else if ("SI".equals(siteType)) {
                result = esecuritySiRepository.selectPassInsStopDenyInfo(paramMap);
            }
            else {
                result = repository.selectPassInsStopDenyInfo(paramMap);
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Map<String, Object> selectPassSecEdu(Integer passApplNo) {
        Map<String, Object> result = null;

        try {
            result = repository.selectPassSecEdu(passApplNo);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> selectCompGateList(String compId) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectCompGateList(compId);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public List<Map<String, Object>> selectIoEmpExtList(Map<String, Object> paramMap) {
        return repository.selectIoEmpExtList(paramMap);
    }

    @Override
    public Map<String, Object> selectIoEmpExtCheckInfo(String ioEmpId) {
        Map<String, Object> resultMap = new HashMap<>();

        String photoCheck = repository.selectReserveVisitPhotoChk(ioEmpId);
        resultMap.put("photoCheck", photoCheck);

        Map<String, String> corrPlanMap = repository.selectReserveVisitCorrPlan(ioEmpId);
        resultMap.put("exprPlanYnHn", corrPlanMap != null
            ? corrPlanMap.get("exprPlanYn")
            : "");
        resultMap.put("planYnHn", corrPlanMap != null
            ? corrPlanMap.get("planYn")
            : "");
        //		Map<String, String> corrPlanHsMap = esecurityHsRepository.selectReserveVisitCorrPlan(ioEmpId);
        //		resultMap.put("exprPlanYnHs", corrPlanHsMap != null ? corrPlanHsMap.get("exprPlanYn") : "");
        //		resultMap.put("planYnHs", corrPlanHsMap != null ? corrPlanHsMap.get("planYn") : "");
        Map<String, String> corrPlanSiMap = esecuritySiRepository.selectReserveVisitCorrPlan(ioEmpId);
        resultMap.put("exprPlanYnSi", corrPlanSiMap != null
            ? corrPlanSiMap.get("exprPlanYn")
            : "");
        resultMap.put("planYnSi", corrPlanSiMap != null
            ? corrPlanSiMap.get("planYn")
            : "");

        Map<String, String> denyMap = repository.selectIoEmpDenyYn(ioEmpId);
        resultMap.put("denyYn", denyMap != null
            ? denyMap.get("denyYn")
            : "");
        resultMap.put("denyDt", denyMap != null
            ? denyMap.get("denyDt")
            : "");

        resultMap.put("nameCheck", repository.selectReserveVisitNameChk(ioEmpId));

        return resultMap;
    }

    @Override
    public List<Map<String, Object>> selectBuildingList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectBuildingList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public List<Map<String, Object>> selectIoEmpEnterAllCompList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectIoEmpEnterAllCompList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Integer selectIoEmpEnterAllCompListCnt(Map<String, Object> paramMap) {
        Integer resultCnt = null;

        try {
            resultCnt = repository.selectIoEmpEnterAllCompListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

    @Override
    public Boolean sendSMS(Map<String, Object> paramMap) {
        boolean result = false;

        InetAddress ia = null;

        try {
            ia = InetAddress.getLocalHost();
            if (ia != null) {
                paramMap.put("SOURCE", ia.getHostAddress());
            }
        } catch (UnknownHostException e) {
            log.error(e.toString());
        }

        try {

            String smsNo = paramMap.get("smsNo") != null
                ? String.valueOf(paramMap.get("smsNo"))
                : "";

            boolean isProd = environment.acceptsProfiles(Profiles.of("prd"));

            if (!isProd) {
                // smsNo = "01064175058";
                smsNo = "01086761591";
            }
            else {
                smsNo = smsNo.replaceAll("-", "");
                paramMap.put("smsNo", smsNo);
            }

            String callbackNo = paramMap.get("callbackNo") != null
                ? String.valueOf(paramMap.get("callbackNo"))
                : "";

            // SK하이스텍 인프라서비스팀 박건우 T.031-639-9458
            // LG U+ ID : SM109982_003
            // 등록 발신번호 : 031-630-3344
            // CALLBACK NO 미 등록 후 진행시 문자 발송 안되는 장애 발생함. 20180221 HSK
            if (!"0439071211".equals(callbackNo)) {
                paramMap.put("callbackNo", "03151853344"); /* 변경시 통신실 박건우씨 등록 요청 할것 */
            }

            String sendEmpId = paramMap.get("sendEmpId") != null
                ? String.valueOf(paramMap.get("sendEmpId"))
                : "SYSTEM";
            paramMap.put("crtBy", sendEmpId);
            paramMap.put("modBy", sendEmpId);

            if (!"".equals(smsNo)) {
                //구버전

                // SMS 발송 전 로그 기록
                //dbInsert("dmSendSMSLogInsert", requestData.getFieldMap(), onlineCtx);

                // sms 발송
                //dbInsert("dmSendSMS_IF", requestData.getFieldMap(), "Sms", onlineCtx);

                // SMS 발송 후 로그 기록
                //dbUpdate("dmSendSMSLogUpdate", requestData.getFieldMap(), onlineCtx);

                /*신규
                 * 1. 로그 는 월별로 자동생성됨 SC_LOG_XXXXXX
                 * */
                repository.insertSendSMSNew(paramMap);

                result = true;
            }
            else {
                // SMS 발송 실패 로그 기록
                log.info("Send Log Insert: {}", "휴대폰번호가 없어서 발송하지 못하였습니다.");

                paramMap.put("msg", "휴대폰번호가 없어서 발송하지 못하였습니다.");
                repository.insertSendSMSLog(paramMap);
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

}
