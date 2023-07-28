package com.skshieldus.esecurity.service.secrtactvy.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skshieldus.esecurity.common.component.Mailing;
import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.model.common.CommonXlsViewDTO;
import com.skshieldus.esecurity.repository.secrtactvy.SecurityUSBPortLockRepository;
import com.skshieldus.esecurity.service.secrtactvy.SecurityUSBPortLockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class SecurityUSBPortLockServiceImpl implements SecurityUSBPortLockService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityUSBPortLockRepository repository;

    //	@Autowired
    //	private CommonApiClient commonApiClient;
    //
    //	@Autowired
    //	private Environment environment;

    @Autowired
    private Mailing mailing;

    @Override
    public List<Map<String, Object>> productionMasterKeyList(HashMap<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {

            Object index = paramMap.get("currentPage");
            Object size = paramMap.get("rowPerPage");
            if (index == null)
                paramMap.put("currentPage", "1");
            if (size == null)
                paramMap.put("rowPerPage", "10");

            resultList = repository.productionMasterKeyList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public int productionMasterKeyListCnt(HashMap<String, Object> paramMap) {
        int resultCnt = 0;

        try {
            resultCnt = repository.productionMasterKeyListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

    @Override
    public Boolean productionMasterKeyStatusChange(Map<String, Object> paramMap) {
        boolean result = false;

        try {
            String chgReqType = (String) paramMap.get("chgReqType");

            @SuppressWarnings("unchecked")
            List<String> pkSeqIds = (List<String>) paramMap.get("pkSeqIds");

            if (pkSeqIds == null || pkSeqIds.size() < 1) {
                throw new Exception("키값이 누락되었습니다.");
            }

            repository.productionMasterKeyStatusChange(paramMap);

            // 처리완료
            result = true;

            if (!"1".equals(chgReqType)) {
                //수신자 이메일 주소 조회
                List<HashMap<String, Object>> rsMailList = repository.selectProductionMasterKeyEmailAddr(paramMap);

                paramMap.put("rsMailList", rsMailList);
                /* 메일 발송 */
                USBPortLockMastKeyChangeReqMail(paramMap);
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Boolean productionMasterKeyMgmtChange(HashMap<String, Object> paramMap) {
        boolean result = false;

        try {

            repository.productionMasterKeyMgmtChange(paramMap);

            // 처리완료
            result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Boolean updateScUSBPortLockMastKeyCheck(HashMap<String, Object> paramMap) {
        try {
            String empId = paramMap.get("empId") != null
                ? String.valueOf(paramMap.get("empId"))
                : "";
            List<HashMap<String, Object>> dataList = objectMapper.convertValue(paramMap.get("data"), List.class);

            if (dataList != null) {
                for (HashMap<String, Object> item : dataList) {
                    item.put("empId", empId);
                    repository.updateScUSBPortLockMastKeyCheck(item);
                }
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Boolean updateScUSBPortLockMastKeyCompBld(HashMap<String, Object> paramMap) {
        try {
            String empId = paramMap.get("empId") != null
                ? String.valueOf(paramMap.get("empId"))
                : "";
            List<HashMap<String, Object>> dataList = objectMapper.convertValue(paramMap.get("data"), List.class);

            if (dataList != null) {
                for (HashMap<String, Object> item : dataList) {
                    item.put("empId", empId);
                    repository.updateScUSBPortLockMastKeyCompBld(item);
                }
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return true;
    }

    @SuppressWarnings("unchecked")
    private void USBPortLockMastKeyChangeReqMail(Map<String, Object> paramMap) {
        try {

            Date d = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String reqDateNm = df.format(d).toString();

            List<String> serialNOs = (List<String>) paramMap.get("serialNOs");
            String serialNo = "";
            for (int i = 0; i < serialNOs.size(); i++) {
                if (i < 1) {
                    serialNo = serialNOs.get(i);
                }
                else {
                    serialNo += "<br/>" + serialNOs.get(i);
                }
            }

            String chgReqType = (String) paramMap.get("chgReqType");
            String title = "";
            String reqRsn = "";
            String strContent = "";

            if ("2".equals(chgReqType)) {
                //mailFormFile = "Z:\\NEXCORE\\MailForm\\fmSecrtUSBPortLockMastKeyChangeMail_2.html";
                title = "[e-Security] USB포트락 마스터키 파손에 따른 교체 요청";
                reqRsn = "마스터키 파손(고장)에 따른 교체 요청";
                strContent = "<p>본 메일은 현업부서 팀원이 <font color='blue'>USB포트락 마스터키가 파손(또는 고장)</font>되어 산업보안팀에 교체 요청하였음을 현업부서(요청자/보안담당자)와 산업보안팀에서 인지할 수 있도록 자동 발송되는 알림 메일 입니다.</p>";
            }
            else if ("3".equals(chgReqType)) {
                //mailFormFile = "Z:\\NEXCORE\\MailForm\\fmSecrtUSBPortLockMastKeyChangeMail_3.html";
                title = "[e-Security] USB포트락 마스터키 분실 신고";
                reqRsn = "마스터키 분실 신고";
                strContent = "<p>본 메일은 현업부서 마스터키 관리자가 <font color='blue'>USB포트락 마스터키를 분실</font>하여,보안담당자와 산업보안팀에 신고하였음을 인지할 수 있도록 자동 발송되는 알림 메일 입니다.</p><p>마스터키 분실시에는 분실 경위서를 제출하며, 대책방안 미흡 또는 반복적인 분실시에는 조사 및 패널티가 적용될 수 있습니다.</p>";
            }
            else if ("4".equals(chgReqType)) {
                //mailFormFile = "Z:\\NEXCORE\\MailForm\\fmSecrtUSBPortLockMastKeyChangeMail_4.html";
                title = "[e-Security] 불필요한 USB포트락 마스터키 반납 신청";
                reqRsn = "불필요한 USB포트락 마스터키 반납 신청";
                strContent = "<p>본 메일은 현업부서 팀원이 불필요한 <font color='blue'>USB포트락 마스터키를 산업보안팀에  반납 신청</font> 하였음을 현업부서(요청자/보안담당자)와 산업보안팀에서 인지할 수 있도록 자동 발송되는 알림 메일 입니다.</p>";
            }
            else {
                log.debug("fmUSBPortLockMastKeyChangeReqMail Error: CHG_REQ_TYPE not valid!");
            }

            StringBuffer str = new StringBuffer();
            str.append("       <div>																						");
            str.append("          " + strContent + "                                                                        ");
            str.append("		   <br/>			                                                                                                    ");
            str.append("          <table width='70%' cellpadding='0' cellspacing='0' border='1' style='border-collapse: collapse; font-family:Malgun Gothic,  Dotum;font-size:14px;'>           ");
            str.append("             <tbody>                                                                                                           ");
            str.append("             	 <tr>                                                                                                           ");
            str.append("                   <td align='center' bgcolor='#ffb45c' width='20%'><span class='td_title'>시리얼 번호</span></td>                 ");
            str.append("                   <td align='left' width='80%'>" + serialNo + "</td>                                                               ");
            str.append("                </tr>                                                                                                          ");
            str.append("                                                                                                                               ");
            str.append("                <tr>                                                                                                           ");
            str.append("                   <td align='center' bgcolor='#ffb45c' width='20%'><span class='td_title'>요청 부서</span></td>                  ");
            str.append("                   <td align='left' width='80%'>" + paramMap.get("deptNm") + "</td>                                                             ");
            str.append("                </tr>                                                                                                          ");
            str.append("				                                                                                                                ");
            str.append("				 <tr>                                                                                                           ");
            str.append("                   <td align='center' bgcolor='#ffb45c' width='20%'><span class='td_title'>요청 사유</span></td>                  ");
            str.append("                   <td align='left' width='80%'>" + reqRsn + "</td>                                                                 ");
            str.append("                </tr>                                                                                                          ");
            str.append("                                                                                                                               ");
            str.append("                <tr>                                                                                                           ");
            str.append("                   <td align='center' bgcolor='#ffb45c' width='20%'><span class='td_title'>요청자</span></td>                    ");
            str.append("                   <td align='left'>" + paramMap.get("empNm") + " " + paramMap.get("jwNm") + "(사번 : " + paramMap.get("empId") + ")</td>                                          ");
            str.append("                </tr>                                                                                                          ");
            str.append("                                                                                                                               ");
            str.append("                <tr>                                                                                                           ");
            str.append("                   <td align='center' bgcolor='#ffb45c' width='20%'><span class='td_title'>요청일</span></span></td>             ");
            str.append("                   <td align='left'>" + reqDateNm + "</td>                                                                         ");
            str.append("                </tr>                                                                                                          ");
            str.append("             </tbody>                                                                                                          ");
            str.append("          </table>                                                                                                             ");
            str.append("       </div>                                                                                                                  ");

            List<HashMap<String, Object>> rsMailList = (List<HashMap<String, Object>>) paramMap.get("rsMailList");
            int rsMailListCnt = rsMailList.size();
            List<String> sendToList = new ArrayList<String>();

            for (int i = 0; i < rsMailListCnt; ++i) {
                sendToList.add((String) rsMailList.get(i).get("email"));
            }

            // 중복 제거
            sendToList = sendToList.stream().distinct().collect(Collectors.toList());

            for (String sendTo : sendToList) {

                boolean result = mailing.sendMail(title, mailing.applyMailTemplate(title, str.toString()), sendTo, "", "USB포트락", "", (String) paramMap.get("acIp"));
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    @Override
    public List<Map<String, Object>> industryControlMasterKeyList(HashMap<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.industryControlMasterKeyList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Boolean industryControlMasterKeyStatusChange(HashMap<String, Object> paramMap) {
        boolean result = false;

        try {
            String chgReqType = (String) paramMap.get("chgReqType");

            List pkSeqIds = (List) paramMap.get("pkSeqIds");

            if (pkSeqIds == null || pkSeqIds.size() < 1) {
                throw new Exception("키값이 누락되었습니다.");
            }

            repository.industryControlMasterKeyStatusChange(paramMap);

            // 처리완료
            result = true;

            if (!"1".equals(chgReqType)) {
                //수신자 이메일 주소 조회
                List<HashMap<String, Object>> rsMailList = repository.selectIncMasterKeyEmailAddr(paramMap);

                paramMap.put("rsMailList", rsMailList);
                /* 메일 발송 */
                USBPortLockMastKeyChangeReqMail(paramMap);
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> dataCenterUSBPortLockList(HashMap<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {

            Object index = paramMap.get("currentPage");
            Object size = paramMap.get("rowPerPage");
            if (index == null)
                paramMap.put("currentPage", "1");
            if (size == null)
                paramMap.put("rowPerPage", "10");

            resultList = repository.dataCenterUSBPortLockList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Integer dataCenterUSBPortLockListCnt(HashMap<String, Object> paramMap) {
        int totalCnt = 0;

        try {

            totalCnt = repository.dataCenterUSBPortLockListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return totalCnt;
    }

    @Override
    public Boolean dataCenterUSBPortLockSave(HashMap<String, Object> paramMap) {
        boolean result = false;

        try {

            repository.dataCenterUSBPortLockSave(paramMap);

            // 처리완료
            result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public CommonXlsViewDTO dataCenterUSBPortLockListDownload(HashMap<String, Object> paramMap) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        CommonXlsViewDTO commonXlsViewDTO = new CommonXlsViewDTO();
        commonXlsViewDTO.setFileName("데이터센터_USB포트락_장착결과" + "_" + sdf.format(new Date()));

        // set header names
        String[] headerNameArr = { "자산번호", "랙번호", "자산명", "제조사", "사용구분", "사업장", "건물", "호스트명", "장비모델", "시리얼", "장비유형", "대상여부", "usb포트락 노출 개수", "usb포트락 장착 수량", "비고", "최종수정일" };
        commonXlsViewDTO.setHeaderNameArr(headerNameArr);

        // set column names (data field name)
        String[] columnNameArr = { "assetNum", "locXy", "confNm", "vendorNm", "useType", "regionNm", "bldgNm", "hostNm", "eqModelNm", "serialNo", "eqClassNm", "targetYn", "usbPortCnt", "usbPortLockCnt", "etc", "modDtm" };
        commonXlsViewDTO.setColumnNameArr(columnNameArr);

        // set column width
        Integer[] columnWidthArr = { 7000, 7000, 7000, 7000, 7000, 7000, 7000, 7000, 7000, 7000, 7000, 7000, 7000, 7000, 7000, 7000 };
        commonXlsViewDTO.setColumnWidthArr(columnWidthArr);

        List<Map<String, Object>> list = repository.dataCenterUSBPortLockExcel(paramMap);

        // set excel data
        commonXlsViewDTO.setDataList(list);

        return commonXlsViewDTO;
    }

    @Override
    public List<Map<String, Object>> industryControlUSBPortLockList(HashMap<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            Object index = paramMap.get("currentPage");
            Object size = paramMap.get("rowPerPage");
            if (index == null)
                paramMap.put("currentPage", "1");
            if (size == null)
                paramMap.put("rowPerPage", "10");

            resultList = repository.industryControlUSBPortLockList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Integer industryControlUSBPortLockListCnt(HashMap<String, Object> paramMap) {
        int totalCnt = 0;

        try {

            totalCnt = repository.industryControlUSBPortLockListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return totalCnt;
    }

    @Override
    public Boolean industryControlUSBPortLockSave(List<HashMap<String, Object>> paramMap) {
        boolean result = false;

        try {

            HashMap<String, Object> param = new HashMap<String, Object>();

            if (paramMap.size() > 0) {

                for (int i = 0; i < paramMap.size(); i++) {

                    param = paramMap.get(i);

                    int cnt = repository.industryControlUSBPortLockSave(param);

                    if (cnt != 1) {
                        throw new Exception((i + 1) + "번 데이터 저장 중 오류가 발생하였습니다.");
                    }
                }
            }

            // 처리완료
            result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Boolean industryControlUSBPortLockInsert(HashMap<String, Object> paramMap) {
        boolean result = false;

        try {

            repository.industryControlUSBPortLockInsert(paramMap);

            // 처리완료
            result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public CommonXlsViewDTO industryControlUSBPortLockListDownload(HashMap<String, Object> paramMap) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        CommonXlsViewDTO commonXlsViewDTO = new CommonXlsViewDTO();
        commonXlsViewDTO.setFileName("산업제어_USB포트락_장착결과" + "_" + sdf.format(new Date()));

        // set header names
        String[] headerNameArr = { "관리번호", "장비유형", "ip주소", "네트워크 구분", "대상여부", "사업장", "건물", "usb포트 노출 개수", "usb포트락 장착 수량", "부서명", "비고", "최종수정일" };
        commonXlsViewDTO.setHeaderNameArr(headerNameArr);

        // set column names (data field name)
        String[] columnNameArr = { "pkSeq", "eqClassNm", "ipNum", "netGbn", "targetYn", "siteNm", "bldgNm", "usbPortCnt", "usbPortLockCnt", "deptNm", "etc", "modDtm" };
        commonXlsViewDTO.setColumnNameArr(columnNameArr);

        // set column width
        Integer[] columnWidthArr = { 7000, 7000, 7000, 7000, 7000, 7000, 7000, 7000, 7000, 7000, 7000, 7000 };
        commonXlsViewDTO.setColumnWidthArr(columnWidthArr);

        List<Map<String, Object>> list = repository.industryControlUSBPortLockList(paramMap);

        // set excel data
        commonXlsViewDTO.setDataList(list);

        return commonXlsViewDTO;
    }

    @Override
    public List<Map<String, Object>> productionSafetyStockList(HashMap<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.productionSafetyStockList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public CommonXlsViewDTO productionSafetyStockListDownload(HashMap<String, Object> paramMap) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        CommonXlsViewDTO commonXlsViewDTO = new CommonXlsViewDTO();
        commonXlsViewDTO.setFileName("보안요원 안전재고 현황" + "_" + sdf.format(new Date()));

        // set header names
        String[] headerNameArr = { "ID", "사업장", "건물명", "보안요원", "구성원 성명", "구성원 사번", "품목", "시리얼번호", "지급일", "지급수량", "잔여수량", "최종수정일" };
        commonXlsViewDTO.setHeaderNameArr(headerNameArr);

        // set column names (data field name)
        String[] columnNameArr = { "pkSeq", "compNm", "bldgNm", "secrtEmpNm", "empNm", "empId", "objectTypeNm", "serialNo", "giveDate", "giveQty", "balanceQty", "modDtm" };
        commonXlsViewDTO.setColumnNameArr(columnNameArr);

        // set column width
        Integer[] columnWidthArr = { 7000, 7000, 7000, 7000, 7000, 7000, 7000, 7000, 7000, 7000, 7000, 7000 };
        commonXlsViewDTO.setColumnWidthArr(columnWidthArr);

        List<Map<String, Object>> list = repository.productionSafetyStockList(paramMap);

        // set excel data
        commonXlsViewDTO.setDataList(list);

        return commonXlsViewDTO;
    }

    @Override
    public Boolean productionSafetyStockDelete(HashMap<String, Object> paramMap) {
        boolean result = false;

        try {

            repository.productionSafetyStockDelete(paramMap);

            // 처리완료
            result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Boolean productionUSBPortLockReset(HashMap<String, Object> paramMap) {
        boolean result = false;

        try {

            repository.productionUSBPortLockReset(paramMap);

            // 처리완료
            result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Boolean productionUSBPortLockResetTarget(HashMap<String, Object> paramMap) {
        boolean result = false;

        try {

            repository.productionUSBPortLockResetTarget(paramMap);

            // 처리완료
            result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Boolean productionSafetyStockSave(HashMap<String, Object> paramMap) {
        boolean result = false;

        try {
            Object pkSeq = paramMap.get("pkSeq");
            if (pkSeq == null) {
                paramMap.put("pkSeq", repository.getProductionSafetyStockPkSeq(paramMap));
            }

            int cnt = repository.productionSafetyStockSave(paramMap);

            if (cnt > 0) {
                //품목이 마스터키 일 경우
                if (!"".equals(paramMap.get("serialNoRtn")) && "C0760002".equals(paramMap.get("objectType"))) {
                    //master key 보유여부, 파손으로 변경
                    repository.updateUSBPortLockMasterKeyStatus(paramMap);
                }

                //품목이 마스터키 일 경우
                if (!"".equals(paramMap.get("serialNo")) && "C0760002".equals(paramMap.get("objectType"))) {
                    repository.updateUSBPortLockMasterKeyMerge(paramMap);
                }

                // 처리완료
                result = true;
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Map<String, Object> productionSafetyStockDetail(HashMap<String, Object> paramMap) {
        Map<String, Object> result = null;

        try {
            result = repository.productionSafetyStockDetail(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> productionUSBPortLockList(HashMap<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {

            Object index = paramMap.get("currentPage");
            Object size = paramMap.get("rowPerPage");
            if (index == null)
                paramMap.put("currentPage", "1");
            if (size == null)
                paramMap.put("rowPerPage", "10");

            resultList = repository.productionUSBPortLockList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public int productionUSBPortLockListCnt(HashMap<String, Object> paramMap) {
        int totalCnt = 0;

        try {

            totalCnt = repository.productionUSBPortLockListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return totalCnt;
    }

    @Override
    public CommonXlsViewDTO productionUSBPortLockDownload(HashMap<String, Object> paramMap) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        CommonXlsViewDTO commonXlsViewDTO = new CommonXlsViewDTO();
        commonXlsViewDTO.setFileName("USB포트락 장착결과" + "_" + sdf.format(new Date()));

        // set header names
        String[] headerNameArr = { "ID", "소스시스템", "관리번호", "자산번호", "장비모델", "장비유형", "장비구조", "부서명", "사업장", "건물", "대상여부", "USB포트 노출 개수", "USB포트락 장착 개수", "장착수량 부족", "불일치 사유", "장착결과 최종저장일시", "점검여부", "점검결과 최종저장일시" };
        commonXlsViewDTO.setHeaderNameArr(headerNameArr);

        // set column names (data field name)
        String[] columnNameArr = { "pkSeq", "pkSiteNm", "eqId", "assetNum", "eqModelNm", "eqClassNm", "eqStructType", "deptNm", "siteNm", "bldgNm", "targetYn", "usbPortCnt", "usbPortlockCnt", "usbPortMinus", "etc", "modDtm", "checkYn", "modDtm1" };
        commonXlsViewDTO.setColumnNameArr(columnNameArr);

        // set column width
        Integer[] columnWidthArr = { 7000, 7000, 7000, 7000, 7000, 7000, 7000, 7000, 7000, 7000, 7000, 7000 };
        commonXlsViewDTO.setColumnWidthArr(columnWidthArr);

        List<Map<String, Object>> list = repository.productionUSBPortLockExcel(paramMap);

        // set excel data
        commonXlsViewDTO.setDataList(list);

        return commonXlsViewDTO;
    }

    @Override
    public Boolean productionUSBPortLockInstallResultSave(List<HashMap<String, Object>> paramMap) {
        boolean result = false;

        try {

            HashMap<String, Object> param = new HashMap<String, Object>();

            if (paramMap.size() > 0) {

                for (int i = 0; i < paramMap.size(); i++) {

                    param = paramMap.get(i);

                    int cnt = repository.productionUSBPortLockInstallResultSave(param);

                    if (cnt != 1) {
                        throw new Exception((i + 1) + "번 데이터 저장 중 오류가 발생하였습니다.");
                    }
                }
            }

            // 처리완료
            result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Boolean productionUSBPortLockCheckResultSave(List<HashMap<String, Object>> paramMap) {
        boolean result = false;

        try {

            HashMap<String, Object> param = new HashMap<String, Object>();

            if (paramMap.size() > 0) {

                for (int i = 0; i < paramMap.size(); i++) {

                    param = paramMap.get(i);

                    int cnt = repository.productionUSBPortLockCheckResultSave(param);

                    if (cnt != 1) {
                        throw new Exception((i + 1) + "번 데이터 저장 중 오류가 발생하였습니다.");
                    }
                }
            }

            // 처리완료
            result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> dataCenterMasterKeyList(HashMap<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.dataCenterMasterKeyList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Boolean dataCenterMasterKeyStatusChange(HashMap<String, Object> paramMap) {

        boolean result = false;

        try {
            String chgReqType = (String) paramMap.get("chgReqType");

            List pkSeqIds = (List) paramMap.get("pkSeqIds");

            if (pkSeqIds == null || pkSeqIds.size() < 1) {
                throw new Exception("키값이 누락되었습니다.");
            }

            repository.dataCenterMasterKeyStatusChange(paramMap);

            // 처리완료
            result = true;

            if (!"1".equals(chgReqType)) {
                //수신자 이메일 주소 조회
                List<HashMap<String, Object>> rsMailList = repository.selectDataCenterEmailAddr(paramMap);

                paramMap.put("rsMailList", rsMailList);
                /* 메일 발송 */
                USBPortLockMastKeyChangeReqMail(paramMap);
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Boolean productionUSBPortLockDelete(HashMap<String, Object> paramMap) {
        boolean result = false;

        try {
            List pkSeqIds = (List) paramMap.get("pkSeqIds");

            if (pkSeqIds == null || pkSeqIds.size() < 1) {
                throw new Exception("키값이 누락되었습니다.");
            }

            int cnt = repository.productionUSBPortLockDelete(paramMap);

            // 처리완료
            if (cnt > 0)
                result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public CommonXlsViewDTO productionMasterKeyListDownload(HashMap<String, Object> paramMap) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        CommonXlsViewDTO commonXlsViewDTO = new CommonXlsViewDTO();
        commonXlsViewDTO.setFileName("마스터키 현황" + "_" + sdf.format(new Date()));

        // set header names
        String[] headerNameArr = { "ID", "시리얼 번호", "보안담당자 사번", "보안담당자 성명", "수령인 사번", "수령인 성명", "관리자 사번", "관리자 성명", "부서", "지급일시", "사업장", "건물", "보유여부", "실사결과", "최종실사자", "최종실사일시", "최종수정일" };
        commonXlsViewDTO.setHeaderNameArr(headerNameArr);

        // set column names (data field name)
        String[] columnNameArr = { "pkSeq", "serialNo", "secrtEmpId", "secrtEmpNm", "recvEmpId", "recvEmpNm", "mgmtEmpId", "mgmtEmpNm", "mgmtDeptNm", "giveDate", "siteNm", "bldgNm", "keepStatusNm", "checkYn", "checkBy", "checkDtm", "modDtm" };
        commonXlsViewDTO.setColumnNameArr(columnNameArr);

        // set column width
        Integer[] columnWidthArr = { 7000, 7000, 7000, 7000, 7000, 7000, 7000, 7000, 7000, 7000, 7000, 7000, 7000, 7000, 7000, 7000, 7000 };
        commonXlsViewDTO.setColumnWidthArr(columnWidthArr);

        List<Map<String, Object>> list = repository.productionMasterKeyListDownload(paramMap);

        // set excel data
        commonXlsViewDTO.setDataList(list);

        return commonXlsViewDTO;
    }

    @Override
    public Boolean productionMasterKeyDelete(HashMap<String, Object> paramMap) {
        boolean result = false;

        try {
            List pkSeqs = (List) paramMap.get("pkSeqs");

            if (pkSeqs == null || pkSeqs.size() < 1) {
                throw new Exception("키값이 누락되었습니다.");
            }

            int cnt = repository.productionMasterKeyDelete(paramMap);

            // 처리완료
            if (cnt > 0)
                result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Map<String, Object> selectSecrtEmpMng(HashMap<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;
        Map<String, Object> result = null;

        try {
            resultList = repository.selectSecrtEmpMng(paramMap);

            if (resultList != null && resultList.size() > 0) {
                result = resultList.get(0);
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Map<String, Object> productionMasterKeyDetail(HashMap<String, Object> paramMap) {
        Map<String, Object> result = null;

        try {
            result = repository.productionMasterKeyDetail(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Boolean productionMasterKeySave(HashMap<String, Object> paramMap) {
        boolean result = false;

        try {

            Object pkSeq = paramMap.get("pkSeq");
            if (pkSeq == null) {
                //채번
                String pk = repository.selectProductionMastKeyPkSeq();

                paramMap.put("pkSeq", pk);
            }

            int cnt = repository.productionMasterKeySave(paramMap);

            // 처리완료
            if (cnt > 0)
                result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Boolean dataCenterUSBPortLockDelete(HashMap<String, Object> paramMap) {
        boolean result = false;

        try {
            List assetIds = (List) paramMap.get("assetIds");

            if (assetIds == null || assetIds.size() < 1) {
                throw new Exception("키값이 누락되었습니다.");
            }

            int cnt = repository.dataCenterUSBPortLockDelete(paramMap);

            // 처리완료
            if (cnt > 0)
                result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return result;
    }

    @Override
    public Boolean industryControlUSBPortLockDelete(HashMap<String, Object> paramMap) {
        boolean result = false;

        try {
            List pkSeqs = (List) paramMap.get("pkSeqs");

            if (pkSeqs == null || pkSeqs.size() < 1) {
                throw new Exception("키값이 누락되었습니다.");
            }

            int cnt = repository.industryControlUSBPortLockDelete(paramMap);

            // 처리완료
            if (cnt > 0)
                result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return result;
    }

    @Override
    public Boolean dataCenterMasterKeySave(HashMap<String, Object> paramMap) {
        boolean result = false;

        try {

            Object pkSeq = paramMap.get("pkSeq");
            if (pkSeq == null) {
                //채번
                String pk = repository.selectDataCenterMastKeyPkSeq();

                paramMap.put("pkSeq", pk);
            }

            int cnt = repository.dataCenterMasterKeySave(paramMap);

            // 처리완료
            if (cnt > 0)
                result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public CommonXlsViewDTO dataCenterMasterKeyExcel(HashMap<String, Object> paramMap) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        CommonXlsViewDTO commonXlsViewDTO = new CommonXlsViewDTO();
        commonXlsViewDTO.setFileName("마스터키 현황" + "_" + sdf.format(new Date()));

        // set header names
        String[] headerNameArr = { "시리얼 번호", "수령인 사번", "수령인 성명", "관리자 사번", "관리자 성명", "부서", "지급일시", "사업장", "건물", "보유여부", "최종수정일" };
        commonXlsViewDTO.setHeaderNameArr(headerNameArr);

        // set column names (data field name)
        String[] columnNameArr = { "serialNo", "recvEmpId", "recvEmpNm", "mgmtEmpId", "mgmtEmpNm", "mgmtDeptNm", "giveDate", "siteNm", "bldgNm", "keepStatusNm", "modDtm" };
        commonXlsViewDTO.setColumnNameArr(columnNameArr);

        // set column width
        Integer[] columnWidthArr = { 7000, 7000, 7000, 7000, 7000, 7000, 7000, 7000, 7000, 7000, 7000 };
        commonXlsViewDTO.setColumnWidthArr(columnWidthArr);

        List<Map<String, Object>> list = repository.dataCenterMasterKeyExcel(paramMap);

        // set excel data
        commonXlsViewDTO.setDataList(list);

        return commonXlsViewDTO;
    }

    @Override
    public Boolean dataCenterMasterKeyDelete(HashMap<String, Object> paramMap) {
        boolean result = false;

        try {
            List pkSeqs = (List) paramMap.get("pkSeqs");

            if (pkSeqs == null || pkSeqs.size() < 1) {
                throw new Exception("키값이 누락되었습니다.");
            }

            int cnt = repository.dataCenterMasterKeyDelete(paramMap);

            // 처리완료
            if (cnt > 0)
                result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return result;
    }

    @Override
    public Map<String, Object> dataCenterMasterKeyDetail(HashMap<String, Object> paramMap) {
        Map<String, Object> result = null;

        try {
            result = repository.dataCenterMasterKeyDetail(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public CommonXlsViewDTO industryControlMasterKeyListDownload(HashMap<String, Object> paramMap) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        CommonXlsViewDTO commonXlsViewDTO = new CommonXlsViewDTO();
        commonXlsViewDTO.setFileName("마스터키 현황" + "_" + sdf.format(new Date()));

        // set header names
        String[] headerNameArr = { "시리얼 번호", "수령인 사번", "수령인 성명", "관리자 사번", "관리자 성명", "부서", "지급일시", "사업장", "건물", "보유여부", "최종수정일" };
        commonXlsViewDTO.setHeaderNameArr(headerNameArr);

        // set column names (data field name)
        String[] columnNameArr = { "serialNo", "recvEmpId", "recvEmpNm", "mgmtEmpId", "mgmtEmpNm", "mgmtDeptNm", "giveDate", "siteNm", "bldgNm", "keepStatusNm", "modDtm" };
        commonXlsViewDTO.setColumnNameArr(columnNameArr);

        // set column width
        Integer[] columnWidthArr = { 7000, 7000, 7000, 7000, 7000, 7000, 7000, 7000, 7000, 7000, 7000 };
        commonXlsViewDTO.setColumnWidthArr(columnWidthArr);

        List<Map<String, Object>> list = repository.industryControlMasterKeyListExcel(paramMap);

        // set excel data
        commonXlsViewDTO.setDataList(list);

        return commonXlsViewDTO;
    }

    @Override
    public Map<String, Object> industryControlMasterKeyDetail(HashMap<String, Object> paramMap) {
        Map<String, Object> result = null;

        try {
            result = repository.industryControlMasterKeyDetail(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Boolean industryControlMasterKeySave(HashMap<String, Object> paramMap) {
        boolean result = false;

        try {
            Object pkSeq = paramMap.get("pkSeq");
            if (pkSeq == null) {
                //채번
                String pk = repository.selectindustryControlMastKeyPkSeq();

                paramMap.put("pkSeq", pk);
            }

            int cnt = repository.industryControlMasterKeySave(paramMap);

            // 처리완료
            if (cnt > 0)
                result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Map<String, Object> productionMasterKeyAdminCheck(HashMap<String, Object> paramMap) {
        Map<String, Object> result = null;

        try {

            result = repository.productionMasterKeyAdminCheck(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

}
