package com.skshieldus.esecurity.service.entmanage.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.utils.FTPUtil;
import com.skshieldus.esecurity.model.common.CommonXlsViewDTO;
import com.skshieldus.esecurity.repository.entmanage.EmpCardRepository;
import com.skshieldus.esecurity.repository.entmanage.EntManageCommonRepository;
import com.skshieldus.esecurity.repository.entmanage.idcard.IdcardRepository;
import com.skshieldus.esecurity.repository.entmanage.idcardvisit.IdcardVisitRepository;
import com.skshieldus.esecurity.service.entmanage.EmpCardService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@Transactional
public class EmpCardServiceImpl implements EmpCardService {

    private final String FTP_UPLOAD_DIR = "/employee";

    @Autowired
    private Environment environment;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EntManageCommonRepository commonRepository;

    @Autowired
    private EmpCardRepository repository;

    @Autowired
    private IdcardRepository idcardRepository;

    @Autowired
    private IdcardVisitRepository idcardVisitRepository;

    @Value("${ifaccess.idcard.ftp.ip}")
    private String ftpIp;

    @Value("${ifaccess.idcard.ftp.port}")
    private int ftpPort;

    @Value("${ifaccess.idcard.ftp.id}")
    private String ftpId;

    @Value("${ifaccess.idcard.ftp.password}")
    private String ftpPwd;

    @Override
    public List<Map<String, Object>> selectEmpCardList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectEmpCardList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Integer selectEmpCardListCnt(Map<String, Object> paramMap) {
        Integer resultCnt = null;

        try {
            resultCnt = repository.selectEmpCardListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

    @Override
    public Map<String, Object> selectEmpCard(Integer empcardApplNo) {
        Map<String, Object> result = null;

        try {
            result = repository.selectEmpCard(empcardApplNo);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> selectNewEmpCardList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectNewEmpCardList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Integer selectNewEmpCardListCnt(Map<String, Object> paramMap) {
        Integer resultCnt = null;

        try {
            resultCnt = repository.selectNewEmpCardListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

    @Override
    public Boolean saveNewEmpCard(MultipartFile file, Map<String, Object> paramMap) {
        int result = 0;

        try {
            boolean isProd = environment.acceptsProfiles(Profiles.of("prd"));
            Integer empcardApplNo = objectMapper.convertValue(paramMap.get("empcardApplNo"), Integer.class);

            if (!isProd) {
                paramMap.remove("juminNo"); // 개발환경 테스트를 위해 값 제거
            }

            paramMap.put("gate", "");

            if (empcardApplNo != null) {
                // 사원증 정보 수정
                result = repository.updateEmpCard(paramMap);
            }
            else {
                // 사원증 정보 등록
                result = repository.insertEmpCard(paramMap);
                empcardApplNo = objectMapper.convertValue(paramMap.get("empcardApplNo"), Integer.class);
            }

            String compId = (String) paramMap.get("compId");

            if (compId != null && !compId.equals("1105000001") && !compId.equals("1106000001")) { // 청주2/청주3 제외
                String fileName = "SKHYNIX_" + paramMap.get("empcardApplNo") + FilenameUtils.EXTENSION_SEPARATOR_STR + FilenameUtils.getExtension(file.getOriginalFilename());

                // 파일업로드
                FTPUtil ftpUtil = new FTPUtil(ftpIp, ftpPort, ftpId, ftpPwd);

                boolean isUpload = false;

                if (isProd) {
                    isUpload = ftpUtil.uploadFileByInputStream(FTP_UPLOAD_DIR, file.getInputStream(), fileName);
                }
                else {
                    isUpload = true;
                }

                if (isUpload) {
                    log.info("@@@@@@@@@@@@ IDCARD 파일({}) FTP 업로드 성공 @@@@@@@@@@@@", fileName);

                    // 사원증 정보 조회(New)
                    Map<String, Object> ifIdcardMap = repository.selectEmpInfoViewForIDCardIFNew(empcardApplNo);
                    paramMap.putAll(ifIdcardMap);

                    // 파일 업로드가 성공할 경우 I/F 테이블에 insert
                    paramMap.put("applNo", empcardApplNo);
                    paramMap.put("schemaNm", "EMPCARD");
                    paramMap.put("status", "10");
                    paramMap.put("attach1Name", fileName);
                    commonRepository.insertIfIdcard(paramMap);

                    // ID CARD
                    String idcardId = "H00" + paramMap.get("empId");
                    paramMap.put("idcardId", idcardId);

                    /* 통합사번 호출 로그 표시 */
                    log.info("@@@@@@@@@@ IDCARD_ID = {}", idcardId);

                    if (ifIdcardMap.get("nameEng") == null)
                        paramMap.put("nameEng", "");

                    log.info("==========>>>>> 하이스텍(IDcard) 사원증 발급 신청 I/F 호출 <<<<<==========");
                    log.info("ifIdcardMap = {}", paramMap.toString());
                    log.info("==========>>>>> 하이스텍(IDcard) 사원증 발급 신청 I/F 호출 <<<<<==========");

                    if (isProd) {
                        // dbInsert("dmIDCardIF_HEIF_Employee_Info_Insert", requestData.getFieldMap(), "IDcard", onlineCtx);
                        idcardRepository.insertIdcardIFHeifEmployeeInfo(paramMap);
                    }
                }
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result > 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Boolean insertNewEmpCardAll(Map<String, Object> paramMap) {
        try {
            boolean isProd = environment.acceptsProfiles(Profiles.of("prd"));
            String acIp = objectMapper.convertValue(paramMap.get("acIp"), String.class);
            String empId = objectMapper.convertValue(paramMap.get("empId"), String.class);
            List<Map<String, Object>> dataList = paramMap.get("dataList") != null
                ? (List<Map<String, Object>>) paramMap.get("dataList")
                : null;

            String compId = null;
            Integer empcardApplNo = null;
            String idcardId = null;

            if (dataList != null) {
                for (Map<String, Object> item : dataList) {
                    item.put("acIp", acIp);
                    item.put("crtBy", empId);

                    if (!isProd) {
                        item.remove("juminNo"); // 개발환경 테스트를 위해 값 제거
                    }

                    // 사원증 신규신청 정보 등록
                    repository.insertEmpCard(item);

                    compId = (String) item.get("compId");
                    empcardApplNo = (Integer) item.get("empcardApplNo");

                    if (compId != null && !compId.equals("1105000001") && !compId.equals("1106000001")) { // 운영환경 & 청주2/청주3 제외
                        // 사원증 정보 조회
                        Map<String, Object> idcardIfNewMap = repository.selectEmpInfoViewForIDCardIFNew(empcardApplNo);
                        item.putAll(idcardIfNewMap);

                        // 파일 업로드가 성공할 경우 I/F 테이블에 insert
                        commonRepository.insertIfIdcard(item);

                        // ID CARD
                        idcardId = "H00" + item.get("empId");
                        item.put("idcardId", idcardId);

                        /* 통합사번 호출 로그 표시 */
                        log.info("@@@@@@@@@@ IDCARD_ID = {}", idcardId);

                        if (item.get("nameEng") == null)
                            item.put("nameEng", "");

                        log.info("==========>>>>> 하이스텍(IDcard) 사원증 발급 신청 I/F 호출 <<<<<==========");
                        log.info("ifIdcardMap = {}", item.toString());
                        log.info("==========>>>>> 하이스텍(IDcard) 사원증 발급 신청 I/F 호출 <<<<<==========");

                        if (isProd) {
                            // // dbInsert("dmIDCardIF_HEIF_Employee_Info_Insert", requestData.getFieldMap(), "IDcard", onlineCtx);
                            idcardRepository.insertIdcardIFHeifEmployeeInfo(item);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return true;
    }

    @Override
    public Boolean insertAccessory(Map<String, Object> paramMap) {
        boolean result = false;

        try {
            @SuppressWarnings("unchecked")
            List<HashMap<String, Object>> dataList = objectMapper.convertValue(paramMap.get("data"), List.class);

            if (dataList != null) {
                Integer empaccApplNo = null;
                Map<String, Object> ifIdcardMap = null;
                Map<String, Object> ifMap = null;

                int insertCnt = 0;
                for (HashMap<String, Object> item : dataList) {

                    // 악세서리 신청
                    repository.insertAccessory(item);

                    empaccApplNo = item.get("empaccApplNo") != null
                        ? Integer.parseInt(String.valueOf(item.get("empaccApplNo")))
                        : null;

                    // I/F 테이블에 insert
                    ifIdcardMap = new HashMap<>();
                    ifIdcardMap.put("applNo", empaccApplNo);
                    ifIdcardMap.put("schemaNm", "EMPACC");
                    ifIdcardMap.put("status", "10");
                    ifIdcardMap.put("crtBy", item.get("crtBy"));
                    commonRepository.insertIfIdcard(ifIdcardMap);

                    boolean isProd = environment.acceptsProfiles(Profiles.of("prd"));

                    // 악세서리 발급 상세 조회
                    ifMap = repository.selectAccessoryViewForIDCardIF(item);
                    item.putAll(ifMap);

                    log.info("==========>>>>> 하이스텍(IDcard) 악세서리 등록 I/F 호출 <<<<<==========");
                    log.info("paramMap = {}", item.toString());
                    log.info("==========>>>>> 하이스텍(IDcard) 악세서리 등록 I/F 호출 <<<<<==========");

                    if (isProd) { // 운영환경
                        // HEIF_Replace_Item_Insert
                        // dbInsert("HEIF_Replace_Item_Insert", requestData.getFieldMap(), "IDcard", onlineCtx);
                        idcardRepository.insertHEIFReplaceItem(item);
                    }

                    insertCnt++;
                }

                result = insertCnt == dataList.size();
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> selectAccessoryList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectAccessoryList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Integer selectAccessoryListCnt(Map<String, Object> paramMap) {
        Integer resultCnt = null;

        try {
            resultCnt = repository.selectAccessoryListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

    @Override
    public Boolean insertEmpCard(MultipartFile file, Map<String, Object> paramMap) {
        int result = 0;

        try {
            boolean isProd = environment.acceptsProfiles(Profiles.of("prd"));

            if (!isProd) {
                paramMap.remove("juminNo"); // 개발환경 테스트를 위해 값 제거
            }

            // 사원증 (재)발급 정보 등록
            result = repository.insertEmpCard(paramMap);

            Integer empcardApplNo = paramMap.get("empcardApplNo") != null
                ? Integer.parseInt(String.valueOf(paramMap.get("empcardApplNo")))
                : null;
            String compId = (String) paramMap.get("compId");

            if (compId != null && !compId.equals("1105000001") && !compId.equals("1106000001")) { // 운영환경 & 청주2/청주3 제외
                String photoRoot = paramMap.get("photoRoot") != null
                    ? String.valueOf(paramMap.get("photoRoot"))
                    : "A0031004";

                boolean isUpload = false;
                String fileName = "";

                if ("A0031001".equals(photoRoot)) { // 신규 파일 사용
                    fileName = "SKHYNIX_" + paramMap.get("empcardApplNo") + FilenameUtils.EXTENSION_SEPARATOR_STR + FilenameUtils.getExtension(file.getOriginalFilename());

                    // 파일업로드
                    FTPUtil ftpUtil = new FTPUtil(ftpIp, ftpPort, ftpId, ftpPwd);

                    if (isProd) {
                        isUpload = ftpUtil.uploadFileByInputStream(FTP_UPLOAD_DIR, file.getInputStream(), fileName);
                    }
                    else {
                        isUpload = true;
                    }

                    if (isUpload) {
                        log.info("@@@@@@@@@@@@ IDCARD 파일({}) FTP 업로드 성공 @@@@@@@@@@@@", fileName);
                    }
                    else {
                        log.info("@@@@@@@@@@@@ IDCARD 파일({}) FTP 업로드 실패 @@@@@@@@@@@@", fileName);
                    }
                }

                if ("A0031004".equals(photoRoot) || isUpload) {  // 기존파일 사용 또는 신규파일 업로드 성공 시

                    // 사원증 정보 조회
                    Map<String, Object> ifIdcardMap = repository.selectEmpInfoViewForIDCardIF(empcardApplNo);
                    paramMap.putAll(ifIdcardMap);

                    // 파일 업로드가 성공할 경우 I/F 테이블에 insert
                    if ("A0031001".equals(photoRoot)) {
                        paramMap.put("attach1Name", fileName);
                    }

                    paramMap.put("applNo", empcardApplNo);
                    paramMap.put("schemaNm", "EMPCARD");
                    paramMap.put("status", "10");
                    commonRepository.insertIfIdcard(paramMap);

                    // ID CARD
                    String idcardId = "H00" + paramMap.get("empId");
                    paramMap.put("idcardId", idcardId);

                    /* 통합사번 호출 로그 표시 */
                    log.info("@@@@@@@@@@ IDCARD_ID = {}", idcardId);

                    if (paramMap.get("nameEng") == null)
                        paramMap.put("nameEng", "");

                    log.info("==========>>>>> 하이스텍(IDcard) 사원증 발급 신청 I/F 호출 <<<<<==========");
                    log.info("paramMap = {}", paramMap.toString());
                    log.info("==========>>>>> 하이스텍(IDcard) 사원증 발급 신청 I/F 호출 <<<<<==========");

                    if (isProd) {
                        // dbInsert("dmIDCardIF_HEIF_Employee_Info_Insert", requestData.getFieldMap(), "IDcard", onlineCtx);
                        idcardRepository.insertIdcardIFHeifEmployeeInfo(paramMap);
                    }
                }
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result > 0;
    }

    @Override
    public Map<String, Object> selectOnedayIdCardIf(Map<String, Object> paramMap) {
        Map<String, Object> result = null;

        try {
            String cardNo = objectMapper.convertValue(paramMap.get("cardNo"), String.class);
            log.info("selectOnedayIdCardIf cardNo : {}", cardNo);

            boolean isPrd = environment.acceptsProfiles(Profiles.of("prd"));

            if (isPrd) {
                // IRecordSet rs = dbSelect("dmOnedayEmpCardGetIdcardIF", requestData.getFieldMap(), "_IDcard_Visit", onlineCtx);
                log.info("==========>>>>> 하이스텍(IDcard_Visit) 일일출입증 조회 I/F 호출 <<<<<==========");
                log.info("CARD_NO = {}", cardNo);
                result = idcardVisitRepository.selectOnedayIdCardIf(cardNo);
                log.info("result = {}", result != null
                    ? result.toString()
                    : null);
                log.info("==========>>>>> 하이스텍(IDcard_Visit) 일일출입증 조회 I/F 호출 <<<<<==========");
            }
            else {
                // dummy
                result = new HashMap<>();
                result.put("cardNo", cardNo);
                result.put("inempId", "O" + RandomStringUtils.randomAlphabetic(9).toUpperCase());
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> selectAdmAccessoryList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectAdmAccessoryList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Integer selectAdmAccessoryListCnt(Map<String, Object> paramMap) {
        Integer resultCnt = null;

        try {
            resultCnt = repository.selectAdmAccessoryListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

    @Override
    public Boolean updateAdmAccessoryStatus(Map<String, Object> paramMap) {

        try {
            String acIp = objectMapper.convertValue(paramMap.get("acIp"), String.class);

            @SuppressWarnings("unchecked")
            List<HashMap<String, Object>> dataList = objectMapper.convertValue(paramMap.get("data"), List.class);

            if (dataList != null) {
                for (HashMap<String, Object> item : dataList) {
                    item.put("acIp", acIp);
                    repository.updateAdmAccessoryStatus(item);
                }
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return true;
    }

    @Override
    public List<Map<String, Object>> selectAdmOnedayCardList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectAdmOnedayCardList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Integer selectAdmOnedayCardListCnt(Map<String, Object> paramMap) {
        Integer resultCnt = null;

        try {
            resultCnt = repository.selectAdmOnedayCardListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

    @Override
    public Boolean returnOnedayCard(Map<String, Object> paramMap) {
        Boolean result = false;

        try {
            boolean isProd = environment.acceptsProfiles(Profiles.of("prd"));

            repository.updateOnedayEmpCardReturn(paramMap);

            paramMap.put("compType", "H00"); // 일일사원증 수령 회사 구분 H : 하이닉스, 8 : 시스템아이씨
            paramMap.put("inOut", "O");

            log.info("==========>>>>> 하이스텍(IDcard_Visit) 일일사원증 반납 I/F 호출 <<<<<==========");
            log.info("cardNo: {}", paramMap.get("cardNo"));
            log.info("empNum(idcardId): {}", paramMap.get("empNum")); // IDCARD_ID
            log.info("compType: {}", paramMap.get("compType"));
            log.info("inOut: {}", paramMap.get("inOut"));
            log.info("==========>>>>> 하이스텍(IDcard_Visit) 일일사원증 반납 I/F 호출 <<<<<==========");

            if (isProd) {
                // dbExecuteProcedure("dmOnedayEmpCardInfo_IF", requestData.getFieldMap(),"_IDcard_Visit", onlineCtx);
                idcardVisitRepository.procedureOnedayEmpCardInfoIf(paramMap);
            }
            else {
                log.info("idcardVisitRepository.procedureOnedayEmpCardInfoIf");
            }

            result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Map<String, Object> selectAdmOnedayCard(Integer empcardApplNo) {
        Map<String, Object> result = null;

        try {
            result = repository.selectAdmOnedayCard(empcardApplNo);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Map<String, Object> insertAdmOnedayCard(Map<String, Object> paramMap) {
        Map<String, Object> result = new HashMap<>();

        try {
            boolean isProd = environment.acceptsProfiles(Profiles.of("prd"));

            String cardNo = objectMapper.convertValue(paramMap.get("cardNo"), String.class);
            String dupYn = repository.selectOnedayEmpCardDupCheck(cardNo);

            String idcardYn = repository.selectOnedayEmpCardIdCardCheck(paramMap);

            result.put("dupYn", dupYn);
            result.put("idcardYn", idcardYn);

            if ("N".equals(dupYn) && "Y".equals(idcardYn)) { // 카드번호가 중복이 아니면 등록 실행

                String empGbn = objectMapper.convertValue(paramMap.get("empGbn"), String.class);
                String applyGbn = objectMapper.convertValue(paramMap.get("applyGbn"), String.class);
                String ofendEmpId = objectMapper.convertValue(paramMap.get("ofendEmpId"), String.class);

                if ("M".equals(empGbn)) { // 구성원

                    if ("A0510001".equals(applyGbn) || "A0510003".equals(applyGbn)) { // 단순미소지 : A0510001, 분실 : A0510003 경우만 SC_OFEND 테이블에 저장

                        Map<String, Object> excptMap = repository.selectSecCoEmpViolationExcptCnt(ofendEmpId);
                        int cnt1 = objectMapper.convertValue(excptMap.get("cnt1"), Integer.class);

                        if (cnt1 < 2) {
                            paramMap.put("defaultActYn", "Y");
                            paramMap.put("actDo", "C0280001"); // 구성원확인
                        }

                        // SC_OFEND 저장
                        // 20210112: Cheyminjung 에 대한 특별 대우로, 보안위규 등록 되지 않도록 처리할것. 물리보안팀 요청 (이명주 TL)
                        if (!"2073479".equals(ofendEmpId)) {
                            repository.insertOnedayEmpCardScOfend(paramMap);
                        }
                    }

                    // 구성원 일일사원증 저장
                    repository.insertOnedayEmpCardCoEmp(paramMap);
                }
                else if ("P".equals(empGbn)) { // 외부인(도급사)

                    if ("A0510001".equals(applyGbn) || "A0510003".equals(applyGbn)) { // 단순미소지 : A0510001, 분실 : A0510003 경우만 SC_IO_OFEND 테이블에 저장

                        Map<String, Object> excptMap = repository.selectSecIoEmpViolationExcptCnt(ofendEmpId);
                        int cnt1 = objectMapper.convertValue(excptMap.get("cnt1"), Integer.class);

                        if (cnt1 < 1) {
                            paramMap.put("defaultActYn", "Y");
                            paramMap.put("actDo", "C0280011"); // 외부인확인
                        }

                        // SC_IO_OFEND 저장
                        repository.insertOnedayEmpCardScIoOfend(paramMap);
                    }

                    // 도급사 일일사원증 저장
                    repository.insertOnedayEmpCardIoEmp(paramMap);
                }

                paramMap.put("empNum", paramMap.get("idcardId")); //IDCARD_ID
                paramMap.put("compType", "H00");//일일사원증 수령 회사 구분 || H : 하이닉스, 8 : 시스템아이씨
                paramMap.put("inOut", "I");

                // dbExecuteProcedure("dmOnedayEmpCardInfo_IF", requestData.getFieldMap(),"_IDcard_Visit", onlineCtx);
                log.info("==========>>>>> 하이스텍(IDcard_Visit) 일일사원증 등록 I/F 호출 <<<<<==========");
                log.info("#### cardNo: {}", paramMap.get("cardNo"));
                log.info("#### empNum(idcardId): {}", paramMap.get("empNum"));
                log.info("#### compType: {}", paramMap.get("compType"));
                log.info("#### inOut: {}", paramMap.get("inOut"));
                log.info("==========>>>>> 하이스텍(IDcard_Visit) 일일사원증 등록 I/F 호출 <<<<<==========");

                if (isProd) { // 운영환경
                    idcardVisitRepository.procedureOnedayEmpCardInfoIf(paramMap);
                }
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public CommonXlsViewDTO admOnedayCardExcelDownload(HashMap<String, Object> paramMap) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        CommonXlsViewDTO commonXlsViewDTO = new CommonXlsViewDTO();
        commonXlsViewDTO.setFileName("일일사원증 발급현황_" + sdf.format(new Date()));

        // set header names
        String[] headerNameArr = { "순번", "발급일자", "소속구분", "회원명(SecurityID)", "소속업체명/부서", "전화번호", "카드번호", "반납일자" };
        commonXlsViewDTO.setHeaderNameArr(headerNameArr);
        // set column names (data field name)
        String[] columnNameArr = { "rnum", "applyDt", "empGbnNm", "mpEmpNm", "mpCompNm", "mpTelNo", "cardNo", "returnDtm" };
        commonXlsViewDTO.setColumnNameArr(columnNameArr);
        // set column width
        Integer[] columnWidthArr = { 2000, 4000, 3000, 5000, 7000, 5000, 5000, 5000 };
        commonXlsViewDTO.setColumnWidthArr(columnWidthArr);

        List<HashMap<String, Object>> list = repository.selectAdmOnedayCardListExcel(paramMap);

        list.forEach((data) -> {
            if (data.get("status").equals("I")) {
                data.replace("returnDtm", "-");
            }
        });

        // set excel data
        commonXlsViewDTO.setDataList(list);

        return commonXlsViewDTO;
    }

}
