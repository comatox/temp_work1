package com.skshieldus.esecurity.service.entmanage.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skshieldus.esecurity.common.component.Mailing;
import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.model.common.ApprovalDTO;
import com.skshieldus.esecurity.model.common.ApprovalDocDTO;
import com.skshieldus.esecurity.model.common.SavedApproverLineDTO;
import com.skshieldus.esecurity.repository.entmanage.BuildingRegRepository;
import com.skshieldus.esecurity.repository.entmanage.idcard.IdcardRepository;
import com.skshieldus.esecurity.repository.entmanage.idcardvisit.IdcardVisitRepository;
import com.skshieldus.esecurity.service.common.ApprovalService;
import com.skshieldus.esecurity.service.entmanage.BuildingRegService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class BuildingRegServiceImpl implements BuildingRegService {

    @Autowired
    private ApprovalService approvalService;

    @Autowired
    private BuildingRegRepository repository;

    @Autowired
    private IdcardVisitRepository idcardVisitRepository;

    @Autowired
    private IdcardRepository idcardRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Mailing mailing;

    @Autowired
    private Environment environment;

    @Value("${security.insnet.url}")
    private String securityInsnetUrl;

    @Value("${security.extnet.url}")
    private String securityExtnetUrl;

    @Override
    public String selectEmpCardNo(String empId) {
        return repository.selectEmpCardNo(empId);
    }

    @Override
    public String selectCardNo(String empId) {
        return repository.selectCardNo(empId);
    }

    @Override
    public List<Map<String, Object>> selectCardKeyBldgList(Map<String, Object> paramMap) {
        return repository.selectCardKeyBldgList(paramMap);
    }

    @Override
    public List<Map<String, Object>> selectCardKeyFloorList(Map<String, Object> paramMap) {
        return repository.selectCardKeyFloorList(paramMap);
    }

    @Override
    public List<Map<String, Object>> selectCardKeyCjBldgList(Map<String, Object> paramMap) {
        return repository.selectCardKeyCjBldgList(paramMap);
    }

    @Override
    public List<Map<String, Object>> selectCardKeyGateSpeZone1List(Map<String, Object> paramMap) {
        return repository.selectCardKeyGateSpeZone1List(paramMap);
    }

    @Override
    public List<Map<String, Object>> selectCardKeyCjGateSpeZone1List(Map<String, Object> paramMap) {
        return repository.selectCardKeyCjGateSpeZone1List(paramMap);
    }

    @Override
    public List<Map<String, Object>> selectCardKeyGateSpeZone2List(Map<String, Object> paramMap) {
        return repository.selectCardKeyGateSpeZone2List(paramMap);
    }

    @Override
    public List<Map<String, Object>> selectCardKeyCjGateSpeZone2List(Map<String, Object> paramMap) {
        return repository.selectCardKeyCjGateSpeZone2List(paramMap);
    }

    @Override
    public List<Map<String, Object>> selectBuildPermitLine(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;
        if ("1101000001".equals(String.valueOf(paramMap.get("compId")))) {
            resultList = repository.selectBuildPermitLine(paramMap);
        }
        else {
            resultList = repository.selectCjBuildPermitLine(paramMap);
        }
        return resultList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean insertEmpcardBuildingReg(Map<String, Object> paramMap) {
        boolean result = false;

        try {
            // 결재정보
            ApprovalDTO approval = objectMapper.convertValue(paramMap.get("approval"), ApprovalDTO.class);
            List<SavedApproverLineDTO> requestLine = List.copyOf(approval.getSavedRequestApproverLine());
            List<SavedApproverLineDTO> permitLine = List.copyOf(approval.getSavedPermitApproverLine());
            // html map (기본정보)
            Map<String, Object> htmlMap = objectMapper.convertValue(paramMap.get("htmlMap"), HashMap.class);
            // 등록자ID
            String crtBy = String.valueOf(paramMap.get("crtBy"));
            // 등록자IP
            String acIp = String.valueOf(paramMap.get("acIp"));
            // 사업장ID
            String compId = paramMap.get("compId").toString();

            // 신규 출입: 전체
            List<Map<String, Object>> newBuildList = objectMapper.convertValue(paramMap.get("newBuildList"), List.class);

            if (newBuildList != null && newBuildList.size() > 0) {
                // 신규 출입: 일반구역
                List<Map<String, Object>> regBuildList = newBuildList.stream().filter(data -> "0".equals(String.valueOf(data.get("zoneType")))).collect(Collectors.toList());

                // 일반구역 등록 및 상신
                if (regBuildList != null && regBuildList.size() > 0) {
                    // 실결재 여부
                    boolean isApproval = requestLine != null && requestLine.size() > 0;
                    // 건물출입 Master 등록
                    repository.insertEmpcardBldgMaster(paramMap);
                    Integer empcardBldgApplNo = (Integer) paramMap.get("empcardBldgApplNo");

                    // 건물출입 Detail 등록
                    for (int i = 0; i < regBuildList.size(); i++) {
                        Map<String, Object> detailMap = regBuildList.get(i);
                        detailMap.put("empcardBldgApplNo", empcardBldgApplNo);
                        detailMap.put("seq", i + 1);
                        detailMap.put("crtBy", crtBy);
                        detailMap.put("acIp", acIp);
                        // 청주
                        if ("1102000001".equals(compId) || "1105000001".equals(compId) || "1106000001".equals(compId)) {
                            if ("FLOOR".equals(detailMap.get("gateType"))) {
                                detailMap.put("apprId", null);
                            }
                            // 이천
                        }
                        else {
                            if ("BLDG".equals(detailMap.get("gateType"))) {
                                detailMap.put("gateId", null);
                                detailMap.put("flrId", null);
                            }
                            else if ("FLOOR".equals(detailMap.get("gateType"))) {
                                detailMap.put("apprId", null);
                                detailMap.put("gateId", null);
                            }
                        }
                        repository.insertEmpcardBldgDetail(detailMap);
                    }

                    // 결재 처리
                    if (isApproval) {
                        // 결재정보 설정 (첫번째 결재자만)
                        //						approval.setSavedRequestApproverLine(requestLine.stream().limit(1).collect(Collectors.toList()));
                        //						approval.setSavedPermitApproverLine(null);
                        //						htmlMap.put("buildList", regBuildList);
                        //						approval.setHtmlMap(htmlMap);
                        //						approval.setLid(empcardBldgApplNo);
                        //
                        //						log.info("일반구역 결재 => {}", approval);
                        //						// 결재처리
                        //						RequestWrapModel<ApprovalDTO> wrapParams = new RequestWrapModel<>();
                        //						wrapParams.setParams(approval);
                        //			    		ResponseModel<ApprovalDocDTO> resultApprovalDoc = commonApiClient.insertApproval(wrapParams);
                        //			    		ApprovalDocDTO approvalDoc = resultApprovalDoc.getData();
                        //
                        //			    		// 건물출입 Master DOC_ID 업데이트
                        //			    		Map<String, Object> updateMap = new HashMap<>();
                        //			    		updateMap.put("docId", approvalDoc.getDocId());
                        //			    		updateMap.put("empcardBldgApplNo", empcardBldgApplNo);
                        //			    		repository.updateEmpcardBldgMasterDocId(updateMap);
                        // 자가 결재 (팀장 이상)
                    }
                    else {
                        // 후처리 프로세스 호출
                        this.processEmpcardBuildingRegApprovalPost(empcardBldgApplNo);
                    }
                }

                // 통제구역 등록 및 상신 (허가부서 결재자 별 상신 처리)
                if (permitLine != null && permitLine.size() > 0) {
                    // 신규 출입: 통제구역
                    List<Map<String, Object>> speBuildList = newBuildList.stream().filter(data -> !"0".equals(String.valueOf(data.get("zoneType")))).collect(Collectors.toList());

                    for (SavedApproverLineDTO permitUser : permitLine) {
                        // 허가부서 결재자(관리자)에 해당하는 신규 출입 건물 목록
                        List<Map<String, Object>> speBuildListByPermitUser = speBuildList.stream().filter(data -> permitUser.getEmpId().equals(data.get("apprEmpId"))).collect(Collectors.toList());

                        // 건물출입 Master 등록
                        repository.insertEmpcardBldgMaster(paramMap);
                        Integer empcardBldgApplNo = (Integer) paramMap.get("empcardBldgApplNo");

                        // 건물출입 Detail 등록
                        for (int i = 0; i < speBuildListByPermitUser.size(); i++) {
                            Map<String, Object> detailMap = speBuildListByPermitUser.get(i);
                            detailMap.put("empcardBldgApplNo", empcardBldgApplNo);
                            detailMap.put("seq", i + 1);
                            detailMap.put("crtBy", crtBy);
                            detailMap.put("acIp", acIp);
                            repository.insertEmpcardBldgDetail(detailMap);
                        }

                        // 결재정보 설정
                        approval.setSavedPermitApproverLine(permitLine.stream().filter(data -> permitUser.getEmpId().equals(data.getEmpId())).collect(Collectors.toList()));
                        htmlMap.put("buildList", speBuildListByPermitUser);
                        approval.setHtmlMap(htmlMap);
                        approval.setLid(empcardBldgApplNo);

                        log.info("통제구역 결재 => {}", approval);
                        // 결재처리
                        ApprovalDocDTO approvalDoc = approvalService.insertApproval(approval);

                        // 건물출입 Master DOC_ID 업데이트
                        Map<String, Object> updateMap = new HashMap<>();
                        updateMap.put("docId", approvalDoc.getDocId());
                        updateMap.put("empcardBldgApplNo", empcardBldgApplNo);
                        repository.updateEmpcardBldgMasterDocId(updateMap);
                    }
                }

                // 처리 완료
                result = true;
            }
            else {
                log.error("신청대상 건물 정보가 없습니다.");
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean insertEmpcardMyGate(Map<String, Object> paramMap) {
        boolean result = false;

        try {
            String cardType = String.valueOf(paramMap.get("cardType"));
            String areaType = String.valueOf(paramMap.get("areaType"));
            String compId = String.valueOf(paramMap.get("gateCompId"));
            paramMap.put("compId", compId);

            // 즐겨찾기 제거 (delete => insert)
            repository.deleteEmpcardMyGate(paramMap);

            List<String> gateIdList = objectMapper.convertValue(paramMap.get("gateIdList"), List.class);
            for (String gateId : gateIdList) {
                if (("A".equals(compId) && "IO".equals(cardType) && "0".equals(areaType)) || ("B".equals(compId) && "0".equals(areaType))) {
                    String[] gateIdArr = gateId.split("_");
                    if (gateIdArr.length > 1) {
                        paramMap.put("gateId", gateIdArr[0]);
                        paramMap.put("floorId", gateIdArr[1]);
                    }
                }
                else {
                    if (!gateId.equals("")) {
                        paramMap.put("gateId", gateId);
                        paramMap.put("floorId", "");
                    }
                }
                // 즐겨찾기 등록
                repository.insertEmpcardMyGate(paramMap);
            }
            // 처리 완료
            result = true;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> selectEmpCardBuildRegList(Map<String, Object> paramMap) {
        return repository.selectEmpCardBuildRegList(paramMap);
    }

    @Override
    public Map<String, Object> selectEmpCardBuildRegInfo(Map<String, Object> paramMap) {
        Map<String, Object> applInfo = repository.selectBldgEmpApplInfo(paramMap);
        applInfo.put("newBuildList", repository.selectNewEmpCardBuildingList(paramMap));

        return applInfo;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean insertBuildingRegAll(Map<String, Object> paramMap) {
        boolean result = false;

        try {
            // 사업장ID
            String compId = String.valueOf(paramMap.get("compId"));
            // 등록자ID
            String crtBy = String.valueOf(paramMap.get("crtBy"));
            // 등록자IP
            String acIp = String.valueOf(paramMap.get("acIp"));

            // 출입건물 목록
            List<Map<String, Object>> newBuildList = objectMapper.convertValue(paramMap.get("newBuildList"), List.class);
            // 출입대상자 목록
            List<Map<String, Object>> userList = objectMapper.convertValue(paramMap.get("userList"), List.class);

            if (newBuildList != null && newBuildList.size() > 0 && userList != null && userList.size() > 0) {
                // 건물출입 Master 등록
                repository.insertEmpcardallM(paramMap);
                Integer empcardallBldgApplNo = (Integer) paramMap.get("empcardallBldgApplNo");

                // 출입건물 Detail 등록
                for (int i = 0; i < newBuildList.size(); i++) {
                    Map<String, Object> detailMap = newBuildList.get(i);
                    detailMap.put("empcardallBldgApplNo", empcardallBldgApplNo);
                    detailMap.put("seq", i + 1);
                    detailMap.put("crtBy", crtBy);
                    detailMap.put("acIp", acIp);

                    if ("BLDG".equals(detailMap.get("gateType"))) {
                        detailMap.put("gateId", null);
                        detailMap.put("flrId", null);
                    }
                    else if ("FLOOR".equals(detailMap.get("gateType"))) {
                        if ("1102000001".equals(compId) || "1105000001".equals(compId) || "1106000001".equals(compId)) {
                            // 일반구역의 경우 청주는 BLDG_ID에 GATE_ID 입력
                            detailMap.put("bldgId", detailMap.get("gateId"));
                        }
                        detailMap.put("gateId", null);
                    }
                    repository.insertEmpcardallBldgD(detailMap);
                }
                // 출입대상자 Detail 등록
                for (int i = 0; i < userList.size(); i++) {
                    Map<String, Object> detailMap = userList.get(i);
                    detailMap.put("empcardallBldgApplNo", empcardallBldgApplNo);
                    detailMap.put("compId", compId);
                    detailMap.put("seq", i + 1);
                    detailMap.put("crtBy", crtBy);
                    detailMap.put("acIp", acIp);
                    repository.insertEmpcardallEmpD(detailMap);
                }

                // 후처리 프로세스 호출
                this.processEmpcardBuildingAllRegApprovalPost(empcardallBldgApplNo);
                result = true;
            }
            else {
                log.error("출입건물 또는 출입대상자 정보가 없습니다.");
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> selectEmpCardBuildRegAllList(Map<String, Object> paramMap) {
        return repository.selectEmpCardBuildRegAllList(paramMap);
    }

    @Override
    public Map<String, Object> selectEmpCardBuildRegAll(Map<String, Object> paramMap) {
        Map<String, Object> resultMap = repository.selectEmpCardBuildRegAll(paramMap);
        List<Map<String, Object>> newBuildList = null;
        if ("1101000001".equals(resultMap.get("compId"))) {
            newBuildList = repository.selectEmpCardBuildRegAllBuildIcList(paramMap);
        }
        else {
            newBuildList = repository.selectEmpCardBuildRegAllBuildCjList(paramMap);
        }
        resultMap.put("newBuildList", newBuildList);
        resultMap.put("userList", repository.selectEmpCardBuildRegAllEmpList(paramMap));
        return resultMap;
    }

    @Override
    public List<Map<String, Object>> selectPassBuildRegList(Map<String, Object> paramMap) {
        return repository.selectPassBuildRegList(paramMap);
    }

    @Override
    public Map<String, Object> selectPassBuildRegView(Map<String, Object> paramMap) {
        Map<String, Object> resultMap = null;

        // 출입증 정보
        if (paramMap.get("passBldgApplNo") != null) {
            resultMap = repository.selectPassReceiptViewByPassBldgApplNo(paramMap);
        }
        else {
            resultMap = repository.selectPassReceiptViewByPassApplNo(paramMap);
        }

        if (paramMap.get("passApplNo") == null) {
            String cardNo = repository.selectPassApplNoByBldgNO(paramMap);
            if (!StringUtils.isEmpty(cardNo))
                resultMap.put("cardNo", cardNo);
        }

        // 출입건물 신청 정보
        Map<String, Object> buildView = repository.selectPassBuildingView(paramMap);
        resultMap.put("buildView", buildView);

        // 통합사번 조회
        String idcardId = repository.selectPassIdcardId(paramMap);
        resultMap.put("idcardId", idcardId);

        List<Map<String, Object>> newBuildList = repository.selectNewPassBuildingList(paramMap);
        resultMap.put("newBuildList", newBuildList);

        return resultMap;
    }

    @Override
    public Map<String, Object> selectIoPassCountByEmpName(Map<String, Object> paramMap) {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        Integer count = repository.selectIoPassCountByEmpName(paramMap);
        resultMap.put("count", count);
        if (count == 1) {
            Map<String, Object> ioPassApplInfo = repository.selectIoPassApplNoByEmpName(paramMap);
            paramMap.put("ioEmpId", ioPassApplInfo.get("ioEmpId"));
            paramMap.put("passApplNo", ioPassApplInfo.get("passApplNo"));
            resultMap.put("ioEmpInfo", repository.selectPassIoEmpView(paramMap));
            resultMap.put("receiptInfo", repository.selectPassReceiptViewByPassApplNo(paramMap));
        }
        return resultMap;
    }

    @Override
    public List<Map<String, Object>> selectIoPassApplNoList(Map<String, Object> paramMap) {
        return repository.selectIoPassApplNoList(paramMap);
    }

    @Override
    public Map<String, Object> selectPassReceiptViewByPassApplNo(Map<String, Object> paramMap) {
        return repository.selectPassReceiptViewByPassApplNo(paramMap);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean insertPassBuildingReg(Map<String, Object> paramMap) {
        boolean result = false;

        try {
            // 결재정보
            ApprovalDTO approval = objectMapper.convertValue(paramMap.get("approval"), ApprovalDTO.class);
            List<SavedApproverLineDTO> requestLine = List.copyOf(approval.getSavedRequestApproverLine());
            List<SavedApproverLineDTO> permitLine = List.copyOf(approval.getSavedPermitApproverLine());
            // html map (기본정보)
            Map<String, Object> htmlMap = objectMapper.convertValue(paramMap.get("htmlMap"), HashMap.class);
            // 등록자ID
            String crtBy = String.valueOf(paramMap.get("crtBy"));
            // 등록자IP
            String acIp = String.valueOf(paramMap.get("acIp"));
            // 사업장ID
            String compId = paramMap.get("compId").toString();

            // 신규 출입: 전체
            List<Map<String, Object>> newBuildList = objectMapper.convertValue(paramMap.get("newBuildList"), List.class);

            if (newBuildList != null && newBuildList.size() > 0) {
                // 신규 출입: 일반구역
                List<Map<String, Object>> regBuildList = newBuildList.stream().filter(data -> "0".equals(String.valueOf(data.get("zoneType")))).collect(Collectors.toList());

                // 일반구역 등록 및 상신
                if (regBuildList != null && regBuildList.size() > 0) {
                    // 건물출입 Master 등록
                    repository.insertPassBldgMaster(paramMap);
                    Integer passBldgApplNo = (Integer) paramMap.get("passBldgApplNo");

                    // 건물출입 Detail 등록
                    for (int i = 0; i < regBuildList.size(); i++) {
                        Map<String, Object> detailMap = regBuildList.get(i);
                        detailMap.put("passBldgApplNo", passBldgApplNo);
                        detailMap.put("seq", i + 1);
                        detailMap.put("crtBy", crtBy);
                        detailMap.put("acIp", acIp);
                        // 청주
                        if ("1102000001".equals(compId) || "1105000001".equals(compId) || "1106000001".equals(compId)) {
                            if ("FLOOR".equals(detailMap.get("gateType"))) {
                                detailMap.put("apprId", null);

                                // 청주 일반구역 => bldgId <-> gateId (스위칭해서 등록 - AS-IS기준)
                                String bldgId = ObjectUtils.defaultIfNull(detailMap.get("bldgId"), "").toString();
                                String gateId = ObjectUtils.defaultIfNull(detailMap.get("gateId"), "").toString();
                                detailMap.put("gateId", bldgId);
                                detailMap.put("bldgId", gateId);
                            }
                            // 이천
                        }
                        else {
                            if ("FLOOR".equals(detailMap.get("gateType")))
                                detailMap.put("gateId", null);
                        }
                        repository.insertPassBldgDetail(detailMap);
                    }

                    // 결재 처리
                    if (requestLine != null && requestLine.size() > 0) {
                        // 결재정보 설정 (첫번째 결재자만)
                        approval.setSavedRequestApproverLine(requestLine.stream().limit(1).collect(Collectors.toList()));
                        approval.setSavedPermitApproverLine(null);
                        htmlMap.put("buildList", regBuildList);
                        approval.setHtmlMap(htmlMap);
                        approval.setLid(passBldgApplNo);

                        log.info("일반구역 결재 => {}", approval);
                        // 결재처리
                        ApprovalDocDTO approvalDoc = approvalService.insertApproval(approval);

                        // 건물출입 Master DOC_ID 업데이트
                        Map<String, Object> updateMap = new HashMap<>();
                        updateMap.put("docId", approvalDoc.getDocId());
                        updateMap.put("passBldgApplNo", passBldgApplNo);
                        repository.updatePassBldgMasterDocId(updateMap);
                        // 자가 결재 (팀장 이상)
                    }
                    else {
                        // 후처리 프로세스 호출
                        this.processPassBuildingRegApprovalPost(passBldgApplNo);
                    }
                }

                // 통제구역 등록 및 상신 (허가부서 결재자 별 상신 처리)
                if (permitLine != null && permitLine.size() > 0) {
                    // 신규 출입: 통제구역
                    List<Map<String, Object>> speBuildList = newBuildList.stream().filter(data -> !"0".equals(String.valueOf(data.get("zoneType")))).collect(Collectors.toList());

                    for (SavedApproverLineDTO permitUser : permitLine) {
                        // 허가부서 결재자(관리자)에 해당하는 신규 출입 건물 목록
                        List<Map<String, Object>> speBuildListByPermitUser = speBuildList.stream().filter(data -> permitUser.getEmpId().equals(data.get("apprEmpId"))).collect(Collectors.toList());

                        // 건물출입 Master 등록
                        repository.insertPassBldgMaster(paramMap);
                        Integer passBldgApplNo = (Integer) paramMap.get("passBldgApplNo");

                        // 건물출입 Detail 등록
                        for (int i = 0; i < speBuildListByPermitUser.size(); i++) {
                            Map<String, Object> detailMap = speBuildListByPermitUser.get(i);
                            detailMap.put("passBldgApplNo", passBldgApplNo);
                            detailMap.put("seq", i + 1);
                            detailMap.put("crtBy", crtBy);
                            detailMap.put("acIp", acIp);
                            repository.insertPassBldgDetail(detailMap);
                        }

                        // 결재정보 설정
                        approval.setSavedPermitApproverLine(permitLine.stream().filter(data -> permitUser.getEmpId().equals(data.getEmpId())).collect(Collectors.toList()));
                        htmlMap.put("buildList", speBuildListByPermitUser);
                        approval.setHtmlMap(htmlMap);
                        approval.setLid(passBldgApplNo);

                        log.info("통제구역 결재 => {}", approval);
                        // 결재처리
                        ApprovalDocDTO approvalDoc = approvalService.insertApproval(approval);

                        // 건물출입 Master DOC_ID 업데이트
                        Map<String, Object> updateMap = new HashMap<>();
                        updateMap.put("docId", approvalDoc.getDocId());
                        updateMap.put("passBldgApplNo", passBldgApplNo);
                        repository.updatePassBldgMasterDocId(updateMap);
                    }
                }

                // 처리 완료
                result = true;
            }
            else {
                log.error("신청대상 건물 정보가 없습니다.");
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> selectSpecialPassList(Map<String, Object> paramMap) {
        // default empty list
        List<Map<String, Object>> list = List.of();

        try {
            // 출입회수 기본값 설정 (검색조건 없는 경우)
            paramMap.put("userName", ObjectUtils.defaultIfNull(paramMap.get("userName"), ""));
            paramMap.put("groupName", ObjectUtils.defaultIfNull(paramMap.get("groupName"), ""));
            paramMap.put("currentPage", ObjectUtils.defaultIfNull(paramMap.get("currentPage"), 1));
            if (StringUtils.isEmpty(String.valueOf(paramMap.get("ioCnt"))))
                paramMap.put("ioCnt", "100");

            // 예외처리 => 로컬에서는 방화벽문제로 연결되지 않음
            if (environment.acceptsProfiles(Profiles.of("dev", "stg", "prd"))) {
                log.info("selectSpecialPassList :: paramMap >>> {}", paramMap);
                // 목록 조회 (if => idcard_visit)
                list = idcardVisitRepository.selectSpecialPassList(paramMap);
                log.info("selectSpecialPassList :: list >>> {}", list);

                if (list != null && list.size() > 0) {
                    // merge data (selectSpecialPassList + selectSpecialPassIoPassList)
                    List<String> idcardIds = list.stream().map(data -> data.get("inemp_id").toString()).collect(Collectors.toList());
                    paramMap.put("idcardIds", idcardIds);
                    List<Map<String, String>> ioList = repository.selectSpecialPassIoPassList(paramMap);

                    if (ioList != null && ioList.size() > 0) {
                        Map<String, Map<String, String>> keyMap = ioList.stream().collect(Collectors.toMap(m -> m.get("idcardId"), m -> m));
                        list = list.stream().map(d -> {
                            Map<String, String> matchedMap = keyMap.get(d.get("inemp_id"));
                            if (matchedMap != null)
                                d.putAll(matchedMap);
                            return d;
                        }).collect(Collectors.toList());
                        log.info("selectSpecialPassList :: mergedList >>> {}", list);
                    }
                }
            }
        } catch (Exception ex) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), "통제구역 출입현황 조회 중 오류가 발생하였습니다.");
        }
        return list;
    }

    @Override
    public Integer selectSpecialPassCount(Map<String, Object> paramMap) {
        Integer result = 0;
        try {
            // 예외처리 => 로컬에서는 방화벽문제로 연결되지 않음
            if (environment.acceptsProfiles(Profiles.of("dev", "stg", "prd"))) {
                Map<String, Object> resultMap = idcardVisitRepository.selectSpecialPassCount(paramMap);
                if (resultMap != null) {
                    result = (Integer) ObjectUtils.defaultIfNull(resultMap.get("nCount"), 0);
                }
            }
        } catch (Exception ex) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), "통제구역 출입현황 건수 조회 중 오류가 발생하였습니다.");
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> selectSpecialPassCompCodeList(Map<String, Object> paramMap) {
        return repository.selectSpecialPassCompCodeList(paramMap);
    }

    @Override
    public List<Map<String, Object>> selectSpecialPassBldgCodeList(Map<String, Object> paramMap) {
        return repository.selectSpecialPassBldgCodeList(paramMap);
    }

    @Override
    public List<Map<String, Object>> selectSpecialPassGateCodeList(Map<String, Object> paramMap) {
        return repository.selectSpecialPassGateCodeList(paramMap);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean deleteSpecialPassMulti(Map<String, Object> paramMap) {
        boolean result = true;

        try {
            List<Map<String, Object>> delList = objectMapper.convertValue(paramMap.get("delList"), List.class);

            if (delList != null && delList.size() > 0) {
                for (Map<String, Object> delInfo : delList) {
                    delInfo.put("acIp", paramMap.get("acIp"));
                    delInfo.put("empId", paramMap.get("empId"));
                    delInfo.put("gateNm", paramMap.get("gateNm"));
                    delInfo.put("delComment", paramMap.get("delComment"));
                    delInfo.put("empGb", paramMap.get("empGb"));
                    // 통제구역 출입현황 권한삭제 정보 등록
                    repository.insertSpecialPassIoEmpBldgCanc(delInfo);

                    // 하이스텍 I/F 특수구역 정지 처리 연동 => dmSpecialPassIoEmpBldgCanc_IF
                    log.info("[통제구역 권한삭제] if data => {}", delInfo);
                    if (environment.acceptsProfiles(Profiles.of("prd"))) {
                        idcardVisitRepository.procedureSpecialPassIoEmpBldgCancIf(delInfo);
                    }

                    // 특수구역출입권한삭제에 따른 메일발송 처리
                    this.sendMailSpecialPass(delInfo);
                }
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        return result;
    }

    /**
     * 특수구역출입권한삭제에 따른 메일발송 처리
     *
     * @param paramMap
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 11. 1.
     */
    private void sendMailSpecialPass(Map<String, Object> paramMap) {
        try {
            String sendTo = paramMap.get("emailAddr").toString();
            String sendFromId = String.valueOf(paramMap.get("ioEmpId"));
            String schemaNm = "SPECGATECANC";
            String title = "";
            String formattedDate = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss").format(new Date()).toString();
            String domain = "";

            String empGb = paramMap.get("empGb").toString();
            String gateNm = paramMap.get("gateNm").toString();
            String empNm = paramMap.get("userName").toString() + " / " + paramMap.get("posName").toString();
            String delComment = paramMap.get("delComment").toString();

            if ("H".equals(empGb)) {
                title = "[e-Security] 특수구역 출입권한이 삭제되었습니다.";
                domain = securityInsnetUrl;
            }
            else {
                title = "[행복한 만남] 특수구역 출입권한이 삭제되었습니다.";
                domain = securityExtnetUrl;
            }

            StringBuffer content = new StringBuffer();

            content.append(" <div > \n");
            content.append(" 	<div style=\" padding:4px 0px;line-height:15px;\">특수구역 출입권한이 삭제되었습니다.</div> \n");

            if ("H".equals(empGb)) {
                content.append(" 	<div style=\" padding:4px 0px;line-height:15px;\">e-Security :  <a target='_blank' href=\"" + domain + "\">security.skhynix.com </a></div> \n");
            }
            else {
                content.append(" 	<div style=\" padding:4px 0px;line-height:15px;\">행복한 만남 사이트:  <a target='_blank' href=\"" + domain + "\">welcome.skhynix.com </a></div> \n");
            }
            content.append(" <br> \n");

            content.append(" <table width='70%' border=\"1\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-collapse:collapse; font-family:Malgun Gothic,  Dotum; font-size:14px;  \"> \n");
            content.append(" <tbody> \n");
            content.append(" <tr> \n");
            content.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">출입구명</span></td> \n");
            content.append(" <td align=\"left\" width=\"80%\">" + gateNm + "</td> \n");
            content.append(" </tr> \n");
            content.append(" <tr> \n");
            content.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">성명 / 직급</span></td> \n");
            content.append(" <td align=\"left\" width=\"80%\">" + empNm + "</td> \n");
            content.append(" </tr> \n");
            content.append(" <tr> \n");
            content.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">일시</span></td> \n");
            content.append(" <td align=\"left\">" + formattedDate + "</td> \n");
            content.append(" </tr> \n");
            content.append(" <tr> \n");
            content.append(" <td align=\"center\" bgcolor=\"#ffb45c\" width=\"20%\"><span style=\"font-size:13px; font-weight:bold; color:#555555\">삭제 사유</span></td> \n");
            content.append(" <td align=\"left\">" + delComment + "</td> \n");
            content.append(" </tr> \n");
            content.append(" <tr> \n");
            content.append(" </tbody> \n");
            content.append(" </table> \n");
            content.append(" </div> \n");

            if ("H".equals(empGb)) {
                mailing.sendMail(title, mailing.applyMailTemplate(title, content.toString()), sendTo, sendFromId, schemaNm, null, null);
            }
            else {
                mailing.sendMailExt(title, mailing.applyMailTemplateExt(title, content.toString()), sendTo, sendFromId, schemaNm, null, null);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    @Override
    public List<Map<String, Object>> selectPassReceiptMngChgHistList(Map<String, Object> paramMap) {
        return repository.selectPassReceiptMngChgHistList(paramMap);
    }

    @Override
    public List<Map<String, Object>> selectPassBuildingGateOldList(String idcardId) {
        return idcardVisitRepository.selectPassBuildingGateOldList(idcardId);
    }

    @Override
    public void processEmpcardBuildingRegApprovalPost(Integer lid) {
        Map<String, Object> requestData = new HashMap<>();
        requestData.putAll(repository.selectEmpCardBldgRequestInfo(lid));
        requestData.put("lid", lid);
        List<Map<String, Object>> bldgList = repository.selectEmpCardBldgRequestList(requestData);

        if (bldgList != null && bldgList.size() > 0) {
            for (Map<String, Object> bldgInfo : bldgList) {
                requestData.put("empId", bldgInfo.get("empId"));
                if ("".equals(bldgInfo.get("cardNo"))) {
                    requestData.put("cardNo", "H00" + ObjectUtils.defaultIfNull(bldgInfo.get("empId"), "").toString());
                }
                else {
                    requestData.put("cardNo", bldgInfo.get("cardNo"));
                }
                requestData.put("bldgId", bldgInfo.get("bldgId"));
                requestData.put("flrId", bldgInfo.get("flrId"));
                requestData.put("gateId", bldgInfo.get("gateId"));
                requestData.put("speZone", bldgInfo.get("speZone"));
                requestData.put("crtBy", bldgInfo.get("crtBy"));
                // 데이터가 없는 경우만 등록
                if (repository.selectCoEmpBldgCount(requestData) == 0)
                    repository.insertCoEmpBldg(requestData);
            }
        }

        String compId = ObjectUtils.defaultIfNull(requestData.get("compId"), "").toString();

        // requestData.put("SPE_ZONE", "");
        // List<Map<String, String>> listDs = repository.selectEmpCardBuildingRequestList(requestData);

        requestData.put("speZone", "0");
        List<Map<String, String>> listDs0 = repository.selectEmpCardBuildingRequestList(requestData);

        requestData.put("speZone", "1");
        List<Map<String, String>> listDs1 = repository.selectEmpCardBuildingRequestList(requestData);

        requestData.put("speZone", "2");
        List<Map<String, String>> listDs2 = repository.selectEmpCardBuildingRequestList(requestData);

        requestData.put("speZone", "3");
        List<Map<String, String>> listDs3 = repository.selectEmpCardBuildingRequestList(requestData);

        // 신청자 정보 불러오기
        Map<String, String> empInfo = repository.selectEmpInfoBuildingRequest(requestData);
        requestData.putAll(empInfo);

        // 연동정보 설정 (to idcard), 연동데이터 Map은 Camel 처리 안함
        Map<String, String> requestIFData = new HashMap<>();
        if (!compId.equals("1102000001") && !compId.equals("1105000001") && !compId.equals("1106000001")) { // 청주 지역 외
            requestIFData.put("AREA_CODE", "A"); // 소속 지역코드 이천:A, 청주 :B
            requestIFData.put("AREA_APPLY", "A"); // 등록될 건물 지역코드 이천:A, 청주 :B
        }
        else if (compId.equals("1102000001")) {
            requestIFData.put("AREA_CODE", "B"); // 소속 지역코드 이천:A, 청주 :B
            requestIFData.put("AREA_APPLY", "B"); // 등록될 건물 지역코드 이천:A, 청주 :B
        }
        requestIFData.put("ES_SN", ObjectUtils.defaultIfNull(requestData.get("empcardBldgApplNo"), "").toString());
        requestIFData.put("CARD_NO", ObjectUtils.defaultIfNull(requestData.get("cardNo"), "").toString());
        requestIFData.put("MODE", "UPDATE");
        requestIFData.put("CARD_TYPE", "1");
        requestIFData.put("SSN", ObjectUtils.defaultIfNull(requestData.get("juminNo"), "").toString());
        requestIFData.put("NAME", ObjectUtils.defaultIfNull(requestData.get("empNm"), "").toString());
        requestIFData.put("POB_NO", ObjectUtils.defaultIfNull(requestData.get("empId"), "").toString());
        requestIFData.put("POB_NO2", ObjectUtils.defaultIfNull(requestData.get("empId"), "").toString());
        requestIFData.put("PASSPORT", ObjectUtils.defaultIfNull(requestData.get("passportNo"), "").toString());
        requestIFData.put("START_AT", ObjectUtils.defaultIfNull(requestData.get("ioStrtDt"), "").toString().trim());
        requestIFData.put("END_AT", "2999-12-31");
        requestIFData.put("COMPANY_NAME", "SK Hynix");
        requestIFData.put("IDCARD_ID", "H00" + ObjectUtils.defaultIfNull(requestData.get("empId"), "").toString()); /* 통합사번용으로 EMP_ID를 사용함 : 2015-10-23 by JSH 추가 */

		/* 로직 분석 결과 사용 안하는것으로 확인
		StringBuffer gates = new StringBuffer();
		for (int i = 0; i < listDs0.size(); i++) {
			if (i > 0) gates.append(";");
			gates.append(listDs0.get(i).get("gateId"));
		}
		String gateList = gates.substring(0, gates.length());
		requestIFData.put("GATES", gateList); */

        StringBuffer bldgs = new StringBuffer();
        for (int i = 0; i < listDs0.size(); i++) { // 일반 구역 처리
            if (i > 0)
                bldgs.append(";");
            if (compId.equals("1102000001"))
                bldgs.append(listDs0.get(i).get("gateId")); // 청주 일반 구역일때는 GATE_ID 를 넣어야함. 20150327
            else
                bldgs.append(listDs0.get(i).get("bldgId")); // 이천, 영동, 정자 일때는 BLDG_ID 를 넣어야함. 20150601
        }
        String bldgsList = bldgs.substring(0, bldgs.length());
        requestIFData.put("BUILDING_CD", bldgsList);

        StringBuffer specZs = new StringBuffer();
        boolean list1Val = false;
        for (int i = 0; i < listDs1.size(); i++) {
            if (i > 0)
                specZs.append(";");
            specZs.append(listDs1.get(i).get("gateId"));
            list1Val = true;
        }

        for (int i = 0; i < listDs2.size(); i++) {
            if (list1Val || i > 0)
                specZs.append(";");
            specZs.append(listDs2.get(i).get("gateId"));
            list1Val = true;
        }

        for (int i = 0; i < listDs3.size(); i++) {
            if (list1Val || i > 0)
                specZs.append(";");
            specZs.append(listDs3.get(i).get("gateId"));
        }

        String specZList = specZs.substring(0, specZs.length());
        requestIFData.put("GATE_ID", specZList);

        // 운영에서만 연동 처리(idcard)
        log.info("[건물출입 신청(사원증) 후처리] idcard 연동 처리");
        log.info("[건물출입 신청(사원증) 후처리] if data => {}", requestIFData);
        if (environment.acceptsProfiles(Profiles.of("prd"))) {
            // IF DB => dmCO_EMP_HEIF_BLDG_INSERT
            idcardRepository.insertIDCardIfHeifBuildingInfo(requestIFData);
        }
    }

    @Override
    public void processPassBuildingRegApprovalPost(Integer lid) {
        Map<String, Object> requestData = new HashMap<>();
        requestData.putAll(repository.selectPassBldgRequestInfo(lid));
        requestData.put("lid", lid);
        List<Map<String, Object>> bldgList = repository.selectPassBldgRequestList(requestData);

        if (bldgList != null && bldgList.size() > 0) {
            for (Map<String, Object> bldgInfo : bldgList) {
                requestData.put("ioEmpId", bldgInfo.get("ioEmpId"));
                requestData.put("cardNo", bldgInfo.get("cardNo"));
                requestData.put("bldgId", bldgInfo.get("bldgId"));
                requestData.put("flrId", bldgInfo.get("flrId"));
                requestData.put("gateId", bldgInfo.get("gateId"));
                requestData.put("speZone", bldgInfo.get("speZone"));
                requestData.put("crtBy", bldgInfo.get("crtBy"));
                requestData.put("compId", bldgInfo.get("compId"));
                // 데이터가 없는 경우만 등록
                if (repository.selectIoEmpBldgCount(requestData) == 0)
                    repository.insertIoEmpBldg(requestData);
            }
        }

        String delYn = ObjectUtils.defaultIfNull(requestData.get("delYn"), "").toString();
        String compId = ObjectUtils.defaultIfNull(requestData.get("compId"), "").toString();

        // List<Map<String, String>> listDs = null;
        List<Map<String, String>> listDs0 = null;
        List<Map<String, String>> listDs1 = null;
        List<Map<String, String>> listDs2 = null;
        List<Map<String, String>> listDs3 = null;

        //청주지역외 이천/서울/분당
        if (!compId.equals("1102000001") && !compId.equals("1105000001") && !compId.equals("1106000001")) {
            // requestData.put("speZone", "");
            // listDs = repository.selectPassBuildingRequestIcList(requestData);
            requestData.put("speZone", "0");
            listDs0 = repository.selectPassBuildingRequestIcList(requestData);
        }
        else if (compId.equals("1102000001")) {
            // requestData.put("speZone", "");
            // listDs = repository.selectPassBuildingRequestList(requestData);
            requestData.put("speZone", "0");
            listDs0 = repository.selectPassBuildingRequestList(requestData);
        }
        requestData.put("speZone", "1");
        listDs1 = repository.selectPassBuildingRequestList(requestData);

        requestData.put("speZone", "2");
        listDs2 = repository.selectPassBuildingRequestList(requestData);

        requestData.put("speZone", "3");
        listDs3 = repository.selectPassBuildingRequestList(requestData);

        // 신청자 정보 불러오기
        Map<String, String> empInfo = repository.selectPassInfoBuildingRequest(requestData);
        requestData.putAll(empInfo);

        // 연동정보 설정 (to idcard), 연동데이터 Map은 Camel 처리 안함
        Map<String, String> requestIFData = new HashMap<>();
        if (!compId.equals("1102000001") && !compId.equals("1105000001") && !compId.equals("1106000001")) { // 청주 지역 외
            requestIFData.put("AREA_CODE", "A"); // 소속 지역코드 이천:A, 청주 :B
            requestIFData.put("AREA_APPLY", "A"); // 등록될 건물 지역코드 이천:A, 청주 :B
        }
        else if (compId.equals("1102000001")) {
            requestIFData.put("AREA_CODE", "B"); // 소속 지역코드 이천:A, 청주 :B
            requestIFData.put("AREA_APPLY", "B"); // 등록될 건물 지역코드 이천:A, 청주 :B
        }
        requestIFData.put("ES_SN", ObjectUtils.defaultIfNull(requestData.get("passBldgApplNo"), "").toString());
        requestIFData.put("CARD_NO", ObjectUtils.defaultIfNull(requestData.get("cardNo"), "").toString());
        requestIFData.put("MODE", "UPDATE");
        requestIFData.put("CARD_TYPE", "2"); // 출입증
        requestIFData.put("SSN", ObjectUtils.defaultIfNull(requestData.get("juminNo"), "").toString());
        requestIFData.put("NAME", ObjectUtils.defaultIfNull(requestData.get("empNm"), "").toString());
        requestIFData.put("POB_NO", ObjectUtils.defaultIfNull(requestData.get("empId"), "").toString());
        requestIFData.put("POB_NO2", ObjectUtils.defaultIfNull(requestData.get("empId"), "").toString());
        requestIFData.put("PASSPORT", ObjectUtils.defaultIfNull(requestData.get("passportNo"), "").toString());
        requestIFData.put("START_AT", ObjectUtils.defaultIfNull(requestData.get("ioStrtDt"), "").toString().trim() + " 00:00:00");
        requestIFData.put("END_AT", ObjectUtils.defaultIfNull(requestData.get("ioEndDt"), "").toString().trim() + " 23:59:59");
        requestIFData.put("COMPANY_NAME", ObjectUtils.defaultIfNull(requestData.get("ioCompNm"), "").toString());
        requestIFData.put("IDCARD_ID", ObjectUtils.defaultIfNull(requestData.get("idcardId"), "").toString());
    	
    	/* 로직 분석 결과 사용 안하는것으로 확인
		StringBuffer gates = new StringBuffer();
		for (int i = 0; i < listDs0.size(); i++) {
			if (i > 0) gates.append(";");
			gates.append(listDs0.get(i).get("gateId"));
		}
		String gatesList = gates.substring(0, gates.length());
		requestIFData.put("GATES", gatesList); */

        StringBuffer bldgs = new StringBuffer();
        for (int i = 0; i < listDs0.size(); i++) {
            if (i > 0)
                bldgs.append(";");
            bldgs.append(listDs0.get(i).get("bldgId"));
        }
        String bldgsList = bldgs.substring(0, bldgs.length());
        requestIFData.put("BUILDING_CD", bldgsList);

        StringBuffer specZs = new StringBuffer();
        boolean list_1_val = false;
        for (int i = 0; i < listDs1.size(); i++) {
            if (i > 0)
                specZs.append(";");
            specZs.append(listDs1.get(i).get("gateId"));
            list_1_val = true;
        }
        for (int i = 0; i < listDs2.size(); i++) {
            if (list_1_val || i > 0)
                specZs.append(";");
            specZs.append(listDs2.get(i).get("gateId"));
            list_1_val = true;
        }
        for (int i = 0; i < listDs3.size(); i++) {
            if (list_1_val || i > 0)
                specZs.append(";");
            specZs.append(listDs3.get(i).get("gateId"));
        }

        String specZsList = specZs.substring(0, specZs.length());
        requestIFData.put("GATE_ID", specZsList);

        // 탈퇴회원 체크
        if ("N".equals(delYn)) {
            // 운영에서만 연동 처리(idcard)
            log.info("[건물출입 신청(상시출입증) 후처리] idcard 연동 처리");
            log.info("[건물출입 신청(상시출입증) 후처리] if data => {}", requestIFData);
            if (environment.acceptsProfiles(Profiles.of("prd"))) {
                // IF DB => dmCO_EMP_HEIF_BLDG_INSERT
                idcardRepository.insertIDCardIfHeifBuildingInfo(requestIFData);
            }
        }
        else {
            log.info("[건물출입 신청(상시출입증) 후처리] 탈퇴회원 => IF제외");
        }
    }

    @Override
    public void processEmpcardBuildingAllRegApprovalPost(Integer lid) {
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("lid", lid);
        List<Map<String, String>> empList = repository.selectEmpCardEmpAllRequestInfo(requestData);
        List<Map<String, String>> bldgList = repository.selectEmpCardBldgAllRequestList(requestData);

        if (empList != null && empList.size() > 0) {
            for (Map<String, String> empInfo : empList) {
                if (bldgList != null && bldgList.size() > 0) {
                    for (Map<String, String> bldgInfo : bldgList) {
                        requestData.put("empId", empInfo.get("empId"));
                        requestData.put("cardNo", empInfo.get("cardNo"));
                        requestData.put("bldgId", bldgInfo.get("bldgId"));
                        requestData.put("flrId", bldgInfo.get("flrId"));
                        requestData.put("gateId", bldgInfo.get("gateId"));
                        requestData.put("speZone", bldgInfo.get("speZone"));
                        requestData.put("crtBy", bldgInfo.get("crtBy"));
                        // 데이터가 없는 경우만 등록
                        if (repository.selectCoEmpBldgCount(requestData) == 0)
                            repository.insertCoEmpBldg(requestData);
                    }
                }
            }
        }

        String compId = ObjectUtils.defaultIfNull(requestData.get("compId"), "").toString();

        requestData.put("speZone", "0");
        List<Map<String, String>> listDs0 = repository.selectEmpCardBuildingAllRequestList(requestData);

        requestData.put("speZone", "1");
        List<Map<String, String>> listDs1 = repository.selectEmpCardBuildingAllRequestList(requestData);

        requestData.put("speZone", "2");
        List<Map<String, String>> listDs2 = repository.selectEmpCardBuildingAllRequestList(requestData);

        // 신청자 정보 불러오기
        List<Map<String, String>> empInfoList = repository.selectEmpInfoBuildingAllRequest(requestData);

        if (empInfoList != null && empInfoList.size() > 0) {
            for (Map<String, String> empInfo : empInfoList) {
                // 연동정보 설정 (to idcard), 연동데이터 Map은 Camel 처리 안함
                Map<String, String> requestIFData = new HashMap<>();
                if (!compId.equals("1102000001") && !compId.equals("1105000001") && !compId.equals("1106000001")) { // 청주 지역 외
                    requestIFData.put("AREA_CODE", "A"); // 소속 지역코드 이천:A, 청주 :B
                    requestIFData.put("AREA_APPLY", "A"); // 등록될 건물 지역코드 이천:A, 청주 :B
                }
                else if (compId.equals("1102000001")) {
                    requestIFData.put("AREA_CODE", "B"); // 소속 지역코드 이천:A, 청주 :B
                    requestIFData.put("AREA_APPLY", "B"); // 등록될 건물 지역코드 이천:A, 청주 :B
                }
                requestIFData.put("ES_SN", empInfo.get("empcardallEmpApplNo"));
                requestIFData.put("CARD_NO", StringUtils.isEmpty(empInfo.get("cardNo"))
                    ? "H00" + ObjectUtils.defaultIfNull(empInfo.get("empId"), "")
                    : empInfo.get("cardNo"));
                requestIFData.put("MODE", "UPDATE");
                requestIFData.put("CARD_TYPE", "1");
                requestIFData.put("SSN", ObjectUtils.defaultIfNull(empInfo.get("juminNo"), ""));
                requestIFData.put("NAME", ObjectUtils.defaultIfNull(empInfo.get("empNm"), "").toString());
                requestIFData.put("POB_NO", ObjectUtils.defaultIfNull(empInfo.get("empId"), "").toString());
                requestIFData.put("POB_NO2", ObjectUtils.defaultIfNull(empInfo.get("empId"), "").toString());
                requestIFData.put("PASSPORT", ObjectUtils.defaultIfNull(empInfo.get("passportNo"), "").toString());
                requestIFData.put("START_AT", ObjectUtils.defaultIfNull(empInfo.get("ioStrtDt"), "").toString().trim());
                requestIFData.put("END_AT", "2999-12-31");
                requestIFData.put("COMPANY_NAME", "SK Hynix");
                requestIFData.put("IDCARD_ID", "H00" + ObjectUtils.defaultIfNull(empInfo.get("empId"), "").toString()); /* 통합사번용으로 EMP_ID를 사용함 : 2015-10-23 by JSH 추가 */

                StringBuffer bldgs = new StringBuffer();
                for (int i = 0; i < listDs0.size(); i++) { // 일반 구역 처리
                    if (i > 0)
                        bldgs.append(";");
                    bldgs.append(listDs0.get(i).get("bldgId"));
                }
                String bldgsList = bldgs.substring(0, bldgs.length());
                requestIFData.put("BUILDING_CD", bldgsList);

                StringBuffer specZs = new StringBuffer();
                boolean list1Val = false;
                for (int i = 0; i < listDs1.size(); i++) {
                    if (i > 0)
                        specZs.append(";");
                    specZs.append(listDs1.get(i).get("gateId"));
                    list1Val = true;
                }

                for (int i = 0; i < listDs2.size(); i++) {
                    if (list1Val || i > 0)
                        specZs.append(";");
                    specZs.append(listDs2.get(i).get("gateId"));
                }

                String specZList = specZs.substring(0, specZs.length());
                requestIFData.put("GATE_ID", specZList);

                // 운영에서만 연동 처리(idcard)
                log.info("[건물출입 일괄신청(사원증) 후처리] idcard 연동 처리");
                log.info("[건물출입 일괄신청(사원증) 후처리] if data => {}", requestIFData);
                if (environment.acceptsProfiles(Profiles.of("prd"))) {
                    // IF DB => dmCO_EMP_HEIF_BLDG_INSERT
                    idcardRepository.insertIDCardIfHeifBuildingInfo(requestIFData);
                }
            }
        }
    }

    @Override
    public List<Map<String, Object>> selectEmpCardBuildRegAdmList(Map<String, Object> paramMap) {
        return repository.selectEmpCardBuildRegAdmList(paramMap);
    }

    @Override
    public Integer selectEmpCardBuildRegAdmCount(Map<String, Object> paramMap) {
        return repository.selectEmpCardBuildRegAdmCount(paramMap);
    }

    @Override
    public List<Map<String, Object>> selectPassBuildRegAdmList(Map<String, Object> paramMap) {
        return repository.selectPassBuildRegAdmList(paramMap);
    }

    @Override
    public Integer selectPassBuildRegAdmCount(Map<String, Object> paramMap) {
        return repository.selectPassBuildRegAdmCount(paramMap);
    }

}
