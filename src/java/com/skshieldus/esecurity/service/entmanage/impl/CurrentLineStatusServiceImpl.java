package com.skshieldus.esecurity.service.entmanage.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.model.common.CommonXlsViewDTO;
import com.skshieldus.esecurity.repository.entmanage.CurrentLineStatusRepository;
import com.skshieldus.esecurity.repository.entmanage.esecuritysi.EsecuritySiRepository;
import com.skshieldus.esecurity.repository.entmanage.idcardvisit.IdcardVisitRepository;
import com.skshieldus.esecurity.service.entmanage.CurrentLineStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@Transactional
public class CurrentLineStatusServiceImpl implements CurrentLineStatusService {

    @Autowired
    private Environment environment;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CurrentLineStatusRepository repository;

    @Autowired
    private IdcardVisitRepository idcardVisitRepository;

    @Autowired
    private EsecuritySiRepository esecuritySiRepository;

    @Override
    public List<Map<String, Object>> selectBuildingControlList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectBuildingControlList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public List<Map<String, Object>> selectAccessPersonsList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectAccessPersonsList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public List<Map<String, Object>> selectStaffCurrentList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectStaffCurrentList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Integer selectStaffCurrentListCnt(Map<String, Object> paramMap) {
        Integer resultCnt = 0;

        try {
            resultCnt = repository.selectStaffCurrentListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

    @Override
    public List<Map<String, Object>> selectAlwaysCurrentList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectAlwaysCurrentList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Integer selectAlwaysCurrentListCnt(Map<String, Object> paramMap) {
        Integer resultCnt = 0;

        try {
            resultCnt = repository.selectAlwaysCurrentListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

    @Override
    public List<Map<String, Object>> selectVisitorCurrentList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectVisitorCurrentList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Integer selectVisitorCurrentListCnt(Map<String, Object> paramMap) {
        Integer resultCnt = 0;

        try {
            resultCnt = repository.selectVisitorCurrentListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

    @Override
    public List<Map<String, Object>> selectAlwaysManageList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectAlwaysManageList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Integer selectAlwaysManageListCnt(Map<String, Object> paramMap) {
        Integer resultCnt = 0;

        try {
            resultCnt = repository.selectAlwaysManageListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

    @Override
    public Map<String, Object> selectAlwaysManage(Integer ioRegIoNo) {
        Map<String, Object> result = null;

        try {
            result = repository.selectAlwaysManage(ioRegIoNo);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public Boolean insertAlwaysManage(Map<String, Object> paramMap) {
        int resultCnt = 0;

        try {
            resultCnt = repository.insertAlwaysManage(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt > 0;
    }

    @Override
    public Boolean updateAlwaysManage(Map<String, Object> paramMap) {
        int resultCnt = 0;

        try {
            resultCnt = repository.updateAlwaysManage(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt > 0;
    }

    @Override
    public Boolean deleteAlwaysManages(Map<String, Object> paramMap) {
        Boolean result = false;

        try {
            String modBy = objectMapper.convertValue(paramMap.get("modBy"), String.class);
            Integer[] deleteIds = objectMapper.convertValue(paramMap.get("ids"), Integer[].class);
            int resultCnt = 0;

            for (Integer id : deleteIds) {
                paramMap = new HashMap<>();
                paramMap.put("modBy", modBy);
                paramMap.put("ioRegIoNo", id);

                resultCnt += repository.deleteAlwaysManage(paramMap);
            }

            if (deleteIds.length == resultCnt)
                result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public String selectAlwaysManageCardNo(String cardNo) {
        String result = "";

        try {
            result = repository.selectAlwaysManageCardNo(cardNo);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> selectItemsStatusList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectItemsStatusList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Integer selectItemsStatusListCnt(Map<String, Object> paramMap) {
        Integer resultCnt = 0;

        try {
            resultCnt = repository.selectItemsStatusListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

    @Override
    public List<Map<String, Object>> selectStaffStatusList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectStaffStatusList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Integer selectStaffStatusListCnt(Map<String, Object> paramMap) {
        Integer resultCnt = 0;

        try {
            resultCnt = repository.selectStaffStatusListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

    @Override
    public CommonXlsViewDTO selectStaffStatusListExcel(Map<String, Object> paramMap) {

        List<Map<String, Object>> resultList = null;
        CommonXlsViewDTO commonXlsViewDTO = new CommonXlsViewDTO();
        commonXlsViewDTO.setFileName("임직원 명단(기간별현황)");

        try {
            resultList = repository.selectStaffStatusList(paramMap);

            // set header names
            String[] headerNameArr = { "사번", "직급", "성명", "부서명", "구역", "입실일시", "퇴실일시" };

            commonXlsViewDTO.setHeaderNameArr(headerNameArr);

            // set column names (data field name)
            String[] columnNameArr = { "empId", "jwNm", "empNm", "deptNm", "gateNm", "inDtm", "outDtm" };
            commonXlsViewDTO.setColumnNameArr(columnNameArr);

            // set column width
            Integer[] columnWidthArr = { 7500, 7500, 7500, 7500, 7500, 7500, 7500 };
            commonXlsViewDTO.setColumnWidthArr(columnWidthArr);

            // set excel data
            commonXlsViewDTO.setDataList(resultList);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return commonXlsViewDTO;
    }

    @Override
    public List<Map<String, Object>> selectAlwaysStatusList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectAlwaysStatusList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Integer selectAlwaysStatusListCnt(Map<String, Object> paramMap) {
        Integer resultCnt = 0;

        try {
            resultCnt = repository.selectAlwaysStatusListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

    @Override
    public CommonXlsViewDTO selectAlwaysStatusListExcel(Map<String, Object> paramMap) {

        List<Map<String, Object>> resultList = null;
        CommonXlsViewDTO commonXlsViewDTO = new CommonXlsViewDTO();
        commonXlsViewDTO.setFileName("상시출입객 명단(기간별현황)");

        try {
            resultList = repository.selectAlwaysStatusList(paramMap);

            // set header names
            String[] headerNameArr = { "예약번호", "FAB 번호", "업체명", "상시출입객", "구역", "입실일시", "퇴실일시" };

            commonXlsViewDTO.setHeaderNameArr(headerNameArr);

            // set column names (data field name)
            String[] columnNameArr = { "ctrlAreaNo", "bldgCardNo", "ioCompNm", "ioEmpNm", "gateNm", "inDtm", "outDtm" };
            commonXlsViewDTO.setColumnNameArr(columnNameArr);

            // set column width
            Integer[] columnWidthArr = { 7500, 7500, 7500, 7500, 7500, 7500, 7500 };
            commonXlsViewDTO.setColumnWidthArr(columnWidthArr);

            // set excel data
            commonXlsViewDTO.setDataList(resultList);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return commonXlsViewDTO;
    }

    @Override
    public List<Map<String, Object>> selectVisitorStatusList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectVisitorStatusList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Integer selectVisitorStatusListCnt(Map<String, Object> paramMap) {
        Integer resultCnt = 0;

        try {
            resultCnt = repository.selectVisitorStatusListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

    @Override
    public CommonXlsViewDTO selectVisitorStatusListExcel(Map<String, Object> paramMap) {

        List<Map<String, Object>> resultList = null;
        CommonXlsViewDTO commonXlsViewDTO = new CommonXlsViewDTO();
        commonXlsViewDTO.setFileName("방문객명단(기간별현황)");

        try {
            resultList = repository.selectVisitorStatusList(paramMap);

            // set header names
            String[] headerNameArr = { "예약번호", "방문증", "업체명", "방문객", "구역", "인솔자", "담당자", "입실일시", "퇴실일시" };

            commonXlsViewDTO.setHeaderNameArr(headerNameArr);

            // set column names (data field name)
            String[] columnNameArr = { "ctrlAreaNo", "ioCardNo", "ioCompNm", "ioEmpNm", "ctrlGateNm", "deptNmg", "deptNms", "inDtm", "outDtm" };
            commonXlsViewDTO.setColumnNameArr(columnNameArr);

            // set column width
            Integer[] columnWidthArr = { 7500, 7500, 7500, 7500, 7500, 7500, 7500, 7500, 7500 };
            commonXlsViewDTO.setColumnWidthArr(columnWidthArr);

            // set excel data
            commonXlsViewDTO.setDataList(resultList);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return commonXlsViewDTO;
    }

    @Override
    public List<Map<String, Object>> selectHazardousChemicalsVisitList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = new ArrayList<>();

        try {
            boolean isDefault = environment.acceptsProfiles(Profiles.of("default")); // local
            log.info("selectHazardousChemicalsVisitList parameter >>> {}", paramMap.toString());

            if (isDefault) {
                Map<String, Object> dataMap = new HashMap<>();
                dataMap.put("compname", "상시출입증	");
                dataMap.put("ctrlername", "M10A_2A_UTIL 스막룸(D)_B(M)_04900");
                dataMap.put("doortype", "단문	");
                dataMap.put("machinetime", "2022-02-24 00:34:00.0");
                dataMap.put("username", "윤상현");
                dataMap.put("groupname", "크린팩토메이션");
                dataMap.put("hpno", "010-9999-9999");
                dataMap.put("vstrsn", "생산 Line 내 유지보수/모니터링/운영 등 출입 필요사항 발생\n(자사구성원)");

                resultList.add(dataMap);
            }
            else {
                log.info("EXEC SP_SELE_EventLogByDoorID @Date = {}, @RoleID = {}, @UserName = {}", paramMap.get("searchDate"), paramMap.get("actLocate"), paramMap.get("ioEmpNm"));

                // _IDcard_Visit 구현
                List<Map<String, Object>> dataList = idcardVisitRepository.selectHazardousChemicalsVisitList(paramMap);

                if (dataList != null) {
                    String inempId = "";
                    String idGu = "";
                    String hpNo = "";

                    Map<String, Object> requestMap = null;
                    Map<String, Object> responseMap = null;

                    // 휴대전화 정규식
                    Pattern pattern = Pattern.compile("^(01\\d{1}|02|0505|0502|0506|0\\d{1,2})-?(\\d{3,4})-?(\\d{4})");
                    Matcher matcher = null;

                    for (Map<String, Object> itemMap : dataList) {
                        if (itemMap.get("inempId") != null) {
                            inempId = objectMapper.convertValue(itemMap.get("inempId"), String.class);
                            idGu = inempId.substring(0, 1);

                            requestMap = new HashMap<>();
                            responseMap = null;

                            switch (idGu) {
                                case "H":
                                    requestMap.put("coEmpId", inempId.substring(3));
                                    responseMap = repository.selectHazardousChemicalsVisitCo(requestMap); // Co
                                    break;
                                case "N":
                                    requestMap.put("coEmpId", inempId.substring(3));
                                    // rss = dbSelect("dmHazardousChemicalsVisit_Co", reqnewData.getFieldMap(), "_EsecuritySi", onlineCtx);
                                    responseMap = esecuritySiRepository.selectHazardousChemicalsVisitCo(requestMap);
                                    break;
                                case "S":
                                    requestMap.put("idcardId", inempId);
                                    responseMap = repository.selectHazardousChemicalsVisitPass(requestMap); // Pass

                                    if (responseMap == null) {
                                        // responseMap = dbSelect("dmHazardousChemicalsVisit_Pass", reqnewData.getFieldMap(), "_EsecurityHs", onlineCtx);
                                        //									responseMap = esecurityHsRepository.selectHazardousChemicalsVisitPass(requestMap);

                                        if (responseMap == null) {
                                            // responseMap = dbSelect("dmHazardousChemicalsVisit_Pass", reqnewData.getFieldMap(), "_EsecuritySi", onlineCtx);
                                            responseMap = esecuritySiRepository.selectHazardousChemicalsVisitPass(requestMap);
                                        }
                                    }

                                    break;

                                case "V":
                                    requestMap.put("empNm", itemMap.get("username"));
                                    requestMap.put("juminNo", itemMap.get("personid"));
                                    requestMap.put("vstDt", itemMap.get("machinetime") != null
                                        ? String.valueOf(itemMap.get("machinetime")).substring(0, 10)
                                        : "");
                                    requestMap.put("idcardId", inempId);

                                    responseMap = repository.selectHazardousChemicalsVisitVst(requestMap); // Vst

                                    if (responseMap == null) {
                                        // responseMap = dbSelect("dmHazardousChemicalsVisit_Vst", reqnewData.getFieldMap(), "_EsecurityHs", onlineCtx);
                                        //									responseMap = esecurityHsRepository.selectHazardousChemicalsVisitVst(requestMap);

                                        if (responseMap == null) {
                                            // responseMap = dbSelect("dmHazardousChemicalsVisit_Vst", reqnewData.getFieldMap(), "_EsecuritySi", onlineCtx);
                                            responseMap = esecuritySiRepository.selectHazardousChemicalsVisitVst(requestMap);
                                        }
                                    }

                                    break;
                                default:
                                    break;
                            }

                            // 연락처
                            if (responseMap != null && responseMap.get("hpNo") != null) {
                                hpNo = String.valueOf(responseMap.get("hpNo"));

                                matcher = pattern.matcher(hpNo);
                                if (matcher.matches()) {
                                    hpNo = matcher.group(1) + "-" + matcher.group(2) + "-" + matcher.group(3);
                                }
                                else {
                                    String str1 = hpNo.substring(0, 3);
                                    String str2 = hpNo.substring(3, 7);
                                    String str3 = hpNo.substring(7, 11);
                                    hpNo = str1 + "-" + str2 + "-" + str3;
                                }

                                itemMap.put("hpno", hpNo);
                            }

                            // 출입목적
                            if (idGu.equals("H") || idGu.equals("N") || responseMap == null) {
                                itemMap.put("vstrsn", "생산 Line 내 유지보수/모니터링/운영 등 출입 필요사항 발생\n(자사구성원)"); // 줄바꿈처리를 위함 '\n' 추가 by kwg. 220217
                            }
                            else {
                                itemMap.put("vstrsn", responseMap.get("vstRsn"));
                            }
                        }

                        resultList.add(itemMap);
                    }
                }
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public List<Map<String, Object>> selectHazardousChemicalsTmpcarList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = repository.selectHazardousChemicalsTmpcarList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public Integer selectHazardousChemicalsTmpcarListCnt(Map<String, Object> paramMap) {
        Integer resultCnt = 0;

        try {
            resultCnt = repository.selectHazardousChemicalsTmpcarListCnt(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultCnt;
    }

}
