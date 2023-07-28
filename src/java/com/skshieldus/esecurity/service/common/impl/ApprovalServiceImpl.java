package com.skshieldus.esecurity.service.common.impl;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.utils.CollectionUtils;
import com.skshieldus.esecurity.model.common.*;
import com.skshieldus.esecurity.repository.common.ApprovalRepository;
import com.skshieldus.esecurity.repository.common.ApproveServiceRepository;
import com.skshieldus.esecurity.repository.common.CoEmpRepository;
import com.skshieldus.esecurity.service.common.ApprovalService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class ApprovalServiceImpl implements ApprovalService {

    @Autowired
    private ApprovalRepository approvalRepository;

    @Autowired
    private CoEmpRepository coEmpRepository;

    //	@Autowired
    //	private EAIApprovalService eAIApprovalService;

    //	@Autowired
    //	private SpringTemplateEngine templateEngine;

    //	@Autowired
    //	private InoutAssetApiClient inoutAssetApiClient;

    //	@Autowired
    //	private IoinoutAssetApiClient ioinoutAssetApiClient;

    //	@Autowired
    //	private CommonApiClient commonApiClient;

    //	@Autowired
    //	private EsecurityMQRepository aapprovalEAIMQRepository;

    @Autowired
    private ApproveServiceRepository approveServiceRepository;

    private String generateConfirmUrl(ApprovalDTO approval) {
        String returnUrl = "";
        try {
            String SECURITYURL = "http://localhost:8080/esecurity";

            String cnfmUrl = approval.getCnfmUrl();
            if (cnfmUrl.indexOf(":docId") >= 0 && approval.getDocId() != null) {
                cnfmUrl = cnfmUrl.replace(":docId", approval.getDocId().toString());
            }
            if (cnfmUrl.indexOf(":writedate") >= 0 && approval.getWritedate() != null) {
                cnfmUrl = cnfmUrl.replace(":writedate", approval.getWritedate());
            }
            if (cnfmUrl.indexOf("writeseq") >= 0 && approval.getWriteseq() != null) {
                cnfmUrl = cnfmUrl.replace(":writeseq", approval.getWriteseq().toString());
            }
            if (cnfmUrl.indexOf(":inoutApplNo") >= 0 && approval.getLid() != null) {
                cnfmUrl = cnfmUrl.replace(":inoutApplNo", approval.getLid().toString());
            }
            if (cnfmUrl.indexOf(":applStat") >= 0 && approval.getApplStat() != null) {
                cnfmUrl = cnfmUrl.replace(":applStat", approval.getApplStat());
            }
            if (cnfmUrl.indexOf(":pcYn") >= 0 && approval.getPcYn() != null) {
                cnfmUrl = cnfmUrl.replace(":pcYn", approval.getPcYn());
            }

            returnUrl = SECURITYURL + "?URL=" + cnfmUrl;
            returnUrl += ";LID=" + approval.getLid().toString();
            returnUrl += ";DOC_ID=" + approval.getDocId().toString();
            returnUrl += ";APPR_EMP_ID=" + approval.getApplEmpId();
            returnUrl += ";APPR_STAT=10";
            returnUrl += ";APPR_RESULT=0";

            log.info("\n >>> getCnfmUrl: " + returnUrl);
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        return returnUrl;
    }

    /**
     * 쉴더스 e-security 결재 상신 (이외 method는 모두 제거 예정)
     *
     * @param approval
     * @return
     */
    @Override
    public ApprovalDocDTO insertApproval(ApprovalDTO approval) {
        ApprovalDocDTO approvalDocDTO = new ApprovalDocDTO();

        // select new doc_id
        Integer docId = approvalRepository.selectNewDocId();
        approval.setDocId(docId);

        // insert ap_doc
        approvalRepository.insertApDoc(approval);

        // delete ap_appr (clear)
        approvalRepository.deleteApAppr(docId);

        List<SavedApproverLineDTO> requestApproverLine = approval.getSavedRequestApproverLine();
        List<SavedApproverLineDTO> permitApproverLine = approval.getSavedPermitApproverLine();

        AtomicInteger apSeq = new AtomicInteger();

        // insert ap_appr (요청부서)
        boolean resultRequestApproverLine = requestApproverLine.stream().map(savedApproverLineDTO -> {
            savedApproverLineDTO.setApSeq(apSeq.getAndIncrement());
            savedApproverLineDTO.setDocId(docId);
            return approvalRepository.insertApAppr(savedApproverLineDTO);
        }).allMatch(count -> count == 1);
        if (!resultRequestApproverLine) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        // insert ap_appr (허가부서)
        boolean resultPermitApproverLine = permitApproverLine.stream().map(savedApproverLineDTO -> {
            savedApproverLineDTO.setApSeq(apSeq.getAndIncrement());
            savedApproverLineDTO.setDocId(docId);
            return approvalRepository.insertApAppr(savedApproverLineDTO);
        }).allMatch(count -> count == 1);
        if (!resultPermitApproverLine) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        // update ap_doc (appr_stat, appr_result 초기화)
        List<ApApprDTO> apApprList = approvalRepository.selectApApprList(docId);
        if (apApprList != null && apApprList.size() > 0) {
            ApApprDTO firstApAppr = apApprList.get(0);
            approval.setApprStat("10");
            approval.setApprResult("0");
            approval.setCompId(firstApAppr.getCompId());
            approval.setDeptId(firstApAppr.getDeptId());
            approval.setJwId(firstApAppr.getJwId());
            approval.setEmpId(firstApAppr.getEmpId());
            approval.setApprDeptGbn(firstApAppr.getApprDeptGbn());
            approval.setApSeq(firstApAppr.getApSeq());
            approvalRepository.updateApDoc(approval);

            approval.setState("0");
            approval.setCnfmUrl("");
            approvalRepository.insertApApprPortal(approval);
        }

        // set return doc_id
        approvalDocDTO.setDocId(docId);
        return approvalDocDTO;
    }

    /**
     * 결재 상신 저장
     *
     * @return
     *
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2021. 3. 2.
     */
    public ApprovalEAIDocDTO insertApprovalWithoutEAI(
        String headerMeta, SessionInfoVO sessionInfo,
        ApprovalDTO approval
    ) {

        ApprovalEAIDocDTO approvalEAIDocDTO;
        try {
            /*
             * 1. 신규 문서이면 docId 생성 dmApprovalLineNewDocId 2. 임시저장이라면 기존에 저장된 결재선 삭제
             * AP_DOC_PRE, AP_APPR_PRE, AP_APPR_PORTAL_PRE, AP_APPR_HTML 삭제 3.1 AP_DOC_PRE
             * 저장 첨부파일 유무는 3.2 요청부서 결재선 저장 3.3 허가부서 결재선 저장 3.4 참조부서 결재선 저장 3.5 AP_APPR_HTML
             * 저장 approval = new ApprovalDTO(); ApprovalEAIDocDTO approvalEAIDocDTODD = new
             * ApprovalEAIDocDTO();
             *
             * eAIApprovalService.sendAPV01703_SO(approvalEAIDocDTODD);
             */
            String empId = approval.getEmpId();
            if (StringUtils.isEmpty(empId)) {
                empId = sessionInfo.getEmpNo();
            }

            if (StringUtils.isEmpty(empId)) {
                throw new EsecurityException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
                    "상신자 사원정보를 필수로 지정하여야 합니다.");
            }

            String realIP = sessionInfo.getIp();
            //			String realIP = "";
            //			JsonObject session = null;
            //			if (sessionInfo != null && !StringUtils.isEmpty(sessionInfo.getIp())) {
            //				realIP = sessionInfo.getIp();
            //			} else if (headerMeta != null
            //					&& (sessionInfo == null || sessionInfo.getEmpNo() == null || realIP == null)) {
            //				session = getSessionFromMeta(headerMeta);
            //				realIP = session.getString("IP", approval.getAcIp());
            //			} else if (approval != null && !StringUtils.isEmpty(approval.getAcIp())) {
            //				realIP = approval.getAcIp();
            //			}

            CoEmpDTO empInfo = coEmpRepository.selectCoEmp(empId);
            log.info(">>>> insertApproval empInfo: " + empInfo);
            log.info(">>>> insertApproval approval: " + approval);
            log.info(">>>> insertApproval headerMeta: " + headerMeta);
            log.info(">>>> insertApproval IP: " + realIP);

            if (empInfo == null || empInfo.getEmpNm().isEmpty()) {
                throw new EsecurityException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
                    "상신자 사원정보를 확인할 수 없습니다. 상신자를 다시 확인하여 주세요.");
            }
            log.info(">>>> insertApproval empId: " + empInfo.getEmpId());
            log.info(">>>> insertApproval Name: " + empInfo.getEmpNm());

            approvalEAIDocDTO = new ApprovalEAIDocDTO();

            if ("Y".equals(approval.getIsImsiYn())) {
                /*
                 * 2. 임시저장이라면 기존에 저장된 결재 관련 정보 삭제 후 재 등록 TODO: 자산반출입에서는 임시저장 기능이 없어서 현재는 구현을 하지
                 * 않았다. 임시 저장기능을 사용한다면 구현해야 한다. AP_DOC_PRE, AP_APPR_PRE, AP_APPR_PORTAL_PRE,
                 * AP_APPR_HTML 삭제
                 */
            }
            Integer docId = approval.getDocId();
            SavedApproverLineDTO firstApprover = new SavedApproverLineDTO();
            /*
             * 요청부서 결재선 저장
             */
            List<SavedApproverLineDTO> savedRequestApproverLine = approval.getSavedRequestApproverLine();

            if (!ObjectUtils.isEmpty(savedRequestApproverLine) && savedRequestApproverLine.size() > 0) {
                firstApprover = savedRequestApproverLine.get(0);
            }

            /*
             * 허가부서 결재선 저장
             */
            List<SavedApproverLineDTO> savedPermitApproverLine = approval.getSavedPermitApproverLine();

            if (StringUtils.isEmpty(firstApprover.getEmpId()) && !ObjectUtils.isEmpty(savedPermitApproverLine)
                && savedPermitApproverLine.size() > 0) {
                firstApprover = savedPermitApproverLine.get(0);
            }

            if (docId == null) {// 신규인 경우
                /*
                 * 신규 DOC_ID 채번 SELECT SEQ_DOC_ID.NEXTVAL AS DOC_ID FROM DUAL
                 */
                docId = approvalRepository.selectNewDocId();
                log.info(">>>> insertApproval docId: " + docId.toString());
                // aprvDoc.docId값 return을 위해 채움
                approvalEAIDocDTO.setLegacyPk(docId);

                approval.setDocId(docId);
                approval.setApSeq(0);
                approval.setCrtBy(empInfo.getEmpId());
                approval.setModBy(empInfo.getEmpId());
                approval.setAcIp(realIP);

                approval.setApplCompId(empInfo.getCompId());
                approval.setApplDeptId(empInfo.getDeptId());
                approval.setApplDeptNm(empInfo.getDeptNm());
                approval.setApplJwNm(empInfo.getJwNm());
                approval.setApplEmpId(empInfo.getEmpId());
                approval.setApplEmpNm(empInfo.getEmpNm());

                approval.setEmpId(firstApprover.getEmpId());
                approval.setCompId(firstApprover.getCompId());
                approval.setDeptId(firstApprover.getDeptId());
                approval.setJwId(firstApprover.getJwId());
                approval.setApprDeptGbn(firstApprover.getApprDeptGbn());
                approval.setApprStat("10");
                approval.setApprResult("0");
                approval.setDelYn("N");
                // TODO: SCHEMA_NM 가 IO_INOUT이고 pc_yn 이 Y인 경우 IOINOUTSERIALNO 대신에 ETC1 값에 "전산기기"
                // 라고 대입한다.
                if (StringUtils.isEmpty(approval.getEtc1())) {
                    approval.setEtc1(approval.getInoutserialno());
                }

                /* AP_DOC_PRE 에 신규저장 */
                int resultCount = approvalRepository.insertApprovalDocPre(approval);
                log.info(">>>> insertApproval insertApprovalDocPre: " + resultCount);
            }
            Integer apSeq = 0;
            /*
             * 요청부서 결재선 저장
             */
            if (!ObjectUtils.isEmpty(savedRequestApproverLine)) {
                for (int i = 0; i < savedRequestApproverLine.size(); i++) {
                    insertApproverPre(apSeq++, savedRequestApproverLine.get(i), docId, empInfo.getEmpId());
                }
            }
            /*
             * 허가부서 결재선 저장
             */
            if (!ObjectUtils.isEmpty(savedPermitApproverLine)) {
                for (int i = 0; i < savedPermitApproverLine.size(); i++) {
                    insertApproverPre(apSeq++, savedPermitApproverLine.get(i), docId, empInfo.getEmpId());
                }
            }

            if (apSeq <= 0) {
                throw new EsecurityException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
                    "요청부서 결재선 허가부서 결재선 중 한 명 이상 필수로 지정하여야 합니다.");
            }
            /*
             * 참조부서 결재선 저장
             */
            List<SavedApproverLineDTO> referenceApproverLine = approvalRepository.selectApprovalLineReferLine(approval);

            if (!ObjectUtils.isEmpty(referenceApproverLine)) {
                for (int i = 0; i < referenceApproverLine.size(); i++) {
                    insertApproverPre(apSeq++, referenceApproverLine.get(i), docId, empInfo.getEmpId());
                }
            }
            /*
             * TODO: AP_DOC_PRE 에 수정저장 해야 함 (필요 여부 판단) 수정은 따로 구현해야 할 지도 AP_APPR_PRE 에 저장된
             * 정보를 조회하여 목록의 첫번째 승인정보 (승인자, 승인 상태 등)을 AP_DOC_PRE에 수정 저장해야 함
             */

            /*
             * 통합결재 원문 HTML 저장
             */
            // 주석처리 2023-06-09
            String htmlContents = "";
            //			String htmlContents = genParamJsonText(approval);
            // log.info(">>>> insertApproval htmlContents: " + htmlContents);
            approval.setHtmlContents(htmlContents);
            approval.setParam("{\"cnfmUrl\": \"" + approval.getCnfmUrl() + "\"}");
            approvalRepository.insertApprovalHtml(approval);
            // log.info(">>>> insertApproval insertApprovalHtml: " + resultCnt);

            /*
             * 포털 AP_APPR_POTRAL_PRE 테이블에 INSERT
             * http://security.skhynix.com/eSecurity/index.jsp?URL=/eSecurity/insNet/
             * PortalAppr/EmpCard/empCardBuildingReg.jsp?LID=900840;DOC_ID=4300101;
             * APPR_EMP_ID=2003928;APPR_STAT=10;APPR_RESULT=0
             * http://10.158.121.230:8081/eSecurity/index.jsp?
             *
             */

            String cnfmUrl = generateConfirmUrl(approval);
            approval.setCnfmUrl(cnfmUrl);

            int resultPrtCnt = approvalRepository.insertApprovalPortalPre(approval);
            log.info(">>>> insertApproval cnfmUrl: " + cnfmUrl);
            log.info(">>>> insertApproval insertApprovalPortalPre: " + resultPrtCnt);

            log.info(">>>> insertApproval selectDocCd: schemaNm=" + approval.getSchemaNm() + " lid="
                     + approval.getLid() + " docId=" + approval.getDocId());

            approvalRepository.insertApprovalDoc(docId);
            approvalRepository.insertApprover(docId);
            approvalRepository.insertApprovalPortal(docId);
            /*
             * EAI 통합결재 전송
             */
            String docCode = "";

            if ("Y".equals(approval.getIsNew()))
                docCode = approvalRepository.selectDocNewCd(approval);
            else
                docCode = approvalRepository.selectDocCd(approval);

            log.info(">>>> insertApproval selectDocCd: DocCd=" + docCode);

            approvalEAIDocDTO.setLegacyPk(approval.getDocId());
            approvalEAIDocDTO.setLegacyGubun("ESecurity");
            approvalEAIDocDTO.setFromPrefix(docCode);
            approvalEAIDocDTO
                .setInterfaceId("Y".equals(approval.getIsNew())
                    ? "APV10295"
                    : "AP11-O-002-ESecurity-ESecurity");
            approvalEAIDocDTO.setCrtBy(empInfo.getEmpId());

            log.info(">>>> insertApproval insertAPNotProcessWsdl: approvalEAIDocDTO=" + approvalEAIDocDTO);
            int resultWsdl = approvalRepository.insertAPNotProcessWsdl(approvalEAIDocDTO);
            log.info(">>>> insertApproval insertAPNotProcessWsdl: resultWsdl=" + resultWsdl);
        } catch (EsecurityException mse) {

            throw mse;
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return approvalEAIDocDTO;
    }

    // 주석처리 2023-06-09
    //	private String genParamJsonText(ApprovalDTO approval) {
    //		String htmlContent = "";
    //
    //		try {
    //			/**
    //			 * thymleaf를 이용한 html contents 생성 by kwg. 210318
    //			 */
    //			String templateNm = null;
    //			Context context = new Context();
    //			Map<String, Object> contentMap = approval.getHtmlMap();
    //
    //			// Template명
    //			if (approval.getSchemaNm().equals("INOUT")) { // 자산반출입 신청
    //				templateNm = "inoutwrite";
    //			} else if (approval.getSchemaNm().equals("INDATE_CHANGE")) { // 자산반출입 반입일자 변경요청
    //				templateNm = "inDateChange";
    //			} else if (approval.getSchemaNm().equals("INOUT_KND_CHANGE")) { // 자산반출입 반입불요 전환요청(자사)
    //				templateNm = "inOutKndChange";
    //			} else if (approval.getSchemaNm().equals("INOUT_KND_CHANGE2")) { // 자산반출입 반입불요 전환요청(외부업체)
    //				templateNm = "inOutKndChangeExternal";
    //			} else if (approval.getSchemaNm().equals("FINISH_CHANGE")) { // 자산반출입 물품반입확인서 신청
    //				templateNm = "finishChange";
    //			} else if (approval.getSchemaNm().equals("CALLING")) { // 자산반출입 물품반입지연 사유서
    //				templateNm = "calling";
    //			} else if (approval.getSchemaNm().equals("INOUT_EMP_CHANGE")) { // 자산반출입 담당자 변경요청
    //				templateNm = "empChange";
    //			} else if (approval.getSchemaNm().equals("OUT_DELAY_CALLING")) { // 사외반출 확인서
    //				templateNm = "outDelayCalling";
    //
    //				// 첨부된 이미지 html에 추가
    //				Map<String, Object> resultMap = approveServiceRepository.selectInoutDelayCalling(approval.getLid());
    //
    //				if (resultMap != null) {
    //					byte[] fileEncImg = (byte[]) resultMap.get("FILE_ENC_IMG");
    //
    //					InputStream is = new BufferedInputStream(new ByteArrayInputStream(fileEncImg));
    //					String mimeType = URLConnection.guessContentTypeFromStream(is);
    //					String imageBase64 = new String(Base64.getEncoder().encode(fileEncImg));
    //
    //					Map<String, Object> htmlMap = approval.getHtmlMap();
    //					htmlMap.put("attchFile", "data:" + mimeType + ";base64," + imageBase64);
    //					approval.setHtmlMap(htmlMap);
    //				}
    //
    //			} else if (approval.getSchemaNm().equals("REPAIR_GATEIN_APPL")) { // 수리세정 반입확인 신청
    //				templateNm = "repairGateInAppl";
    //			} else if (approval.getSchemaNm().equals("IO_OUT_APPL")) { // 업체물품 반출신청
    //				templateNm = "ioInoutTake";
    //			} else if (approval.getSchemaNm().equals("IO_INOUT")) { // 업체물품 반입신청
    //				String pcYn = (String) contentMap.get("pcYn");
    //
    //				if (pcYn != null && pcYn.equals("Y")) {
    //					templateNm = "ioInoutCarryPC";
    //				} else {
    //					templateNm = "ioInoutCarry";
    //				}
    //			} else if (approval.getSchemaNm().equals("IO_INOUT_CHANGE")) { // 업체물품 변경신청
    //				String pcYn = (String) contentMap.get("pcYn");
    //
    //				if (pcYn != null && pcYn.equals("Y")) {
    //					templateNm = "ioInoutChangePC";
    //				} else {
    //					templateNm = "ioInoutChange";
    //				}
    //			} else if (approval.getSchemaNm().equals("INOUT_DOC_PRINT")) { // 사외문서반출 신청
    //				templateNm = "inOutDocPrint";
    //			} else if (approval.getSchemaNm().equals("PHOTO_ING_APPL")) { // 사진촬영허가신청
    //				templateNm = "photoIngAppl";
    //			} else if (approval.getSchemaNm().equals("HYCON_APPL")) { // HyCon포털 사용 신청
    //				templateNm = "hyConRequest";
    //			} else if (approval.getSchemaNm().equals("HYCON_URGENT_APPL")) { // HyCon포털 긴급사용 신청
    //				templateNm = "hyConUrgentRequest";
    //			} else if (approval.getSchemaNm().equals("HYCON_URGENT_END_APPL")) { // HyCon포털 긴급사용종료 신청
    //				templateNm = "hyConUrgentEndRequest";
    //			} else if (approval.getSchemaNm().equals("PASS_SCRT_REQ")) { // 출입보안 요청 신청
    //				templateNm = "passScrtReq";
    //			} else if (approval.getSchemaNm().equals("ITEQMT_RGAD_APPL")) { // 전산저장장치 상시반출 신청
    //				templateNm = "iteqmtRgadAppl";
    //			} else if (approval.getSchemaNm().equals("HDD_DISUSE")) { // HDD 폐기 신청
    //				templateNm = "hddDisuse";
    //			} else if (approval.getSchemaNm().equals("SC_DOC_DIST")) { // 비문분류기준표 등록
    //				templateNm = "scDocDist";
    //			} else if (approval.getSchemaNm().equals("PC_SCRT_MTR_APPL")) { // PC보안매체신청
    //				String lang = (String) contentMap.get("lang");
    //
    //				if (lang != null && lang.equals("en")) {
    //					templateNm = "pcScrtReqEn";
    //				} else {
    //					templateNm = "pcScrtReq";
    //				}
    //			} else if (approval.getSchemaNm().equals("ITEQMT_SCRT_RVW")) { // 전산보안장치 보안성 검토 신청
    //				templateNm = "iteqmtScrtRvwRequest";
    //			} else if (approval.getSchemaNm().equals("SRVCE_SCRT_RVW")) { // 서비스 보안성 검토 신청
    //				templateNm = "srvceScrtRvwRequest";
    //			} else if (approval.getSchemaNm().equals("OUT_NETWORK_APPL")) { // 외부인Network사용신청
    //				templateNm = "outNetworkAppl";
    //			} else if (approval.getSchemaNm().equals("OUTNET_ACCESS_APPL")) { // 방화벽정책 신청
    //				String lang = (String) contentMap.get("lang");
    //
    //				if (lang != null && lang.equals("en")) {
    //					templateNm = "outNetAccessApplEn";
    //				} else {
    //					templateNm = "outNetAccessAppl";
    //				}
    //
    //			} else if (approval.getSchemaNm().equals("BUILD_EMPCARD_APPL")) { // 건물출입 신청(사원증)
    //				templateNm = "buildEmpcardAppl";
    //			} else if (approval.getSchemaNm().equals("BUILD_PASS_APPL")) { // 건물출입 신청(상시출입증)
    //				templateNm = "buildPassAppl";
    //			} else if (approval.getSchemaNm().equals("WEB_ISOLATION_EXCEPT")) { // 웹격리 예외신청
    //				templateNm = "webIsolationRequest";
    //			} else if (approval.getSchemaNm().equals("NAC_EXCPT_APPL")) { // NAC예외신청
    //				templateNm = "nacExcptAppl";
    //			} else if (approval.getSchemaNm().equals("DRM_EDIT_APPL")) { // DRM OFF-line 편집권한 사용신청
    //				String lang = (String) contentMap.get("lang");
    //
    //				if (lang != null && lang.equals("en")) {
    //					templateNm = "drmEditApplEn";
    //				} else {
    //					templateNm = "drmEditAppl";
    //				}
    //
    //			} else if (approval.getSchemaNm().equals("WIRELESS_APPL")) { // 무선기기 사용 신청
    //				templateNm = "wirelessAppl";
    //			} else if (approval.getSchemaNm().equals("SHD_FLDR_USE_APPL")) { // 공유폴더 사용 신청
    //				String lang = (String) contentMap.get("lang");
    //
    //				if (lang != null && lang.equals("en")) {
    //					templateNm = "shdFldrUseApplEn";
    //				} else {
    //					templateNm = "shdFldrUseAppl";
    //				}
    //
    //			} else if (approval.getSchemaNm().equals("3IN1_APPL")) { // 출장자용 3in1 신청
    //				templateNm = "3In1Appl";
    //			} else if (approval.getSchemaNm().equals("PASS_APPL")) { // 상시출입증 접수 신청
    //				templateNm = "passAppl";
    //			} else if (approval.getSchemaNm().equals("PASS_APPL_EXCPT")) { // 사전 정지예외 신청
    //				templateNm = "passApplExcpt";
    //			} else if (approval.getSchemaNm().equals("CORR_PLAN")) { // 시정계획서 제출
    //				templateNm = "corrPlan";
    //			} else if (approval.getSchemaNm().equals("DOC_SEND_APPL")) { // 외부공개자료사전승인신청
    //				templateNm = "docSendAppl";
    //			} else if (approval.getSchemaNm().equals("CO_EMP_EDU")) { // 보안교육신청
    //				templateNm = "coEmpEdu";
    //			} else if (approval.getSchemaNm().equals("VPN_ACCESS_APPL")) { // VPN 사용 신청
    //
    //				String lang = String.valueOf(contentMap.getOrDefault("lang", ""));
    //				if (!"".equals(lang) && "en".equals(lang)) {
    //					templateNm = "vpnAccessRequestEn";
    //				} else {
    //					templateNm = "vpnAccessRequest";
    //				}
    //			} else if (approval.getSchemaNm().equals("PASS_APPL_CANCEL")) { // 사후 정지예외 신청
    //				templateNm = "passApplCancel";
    //			} else if (approval.getSchemaNm().equals("VSIT_RSRV")) { // 방문예약
    //				if ("O".equals(approval.getVipYn())) {
    //					templateNm = "vsitRsrv";
    //				} else if ("F".equals(approval.getVipYn())) {
    //					templateNm = "vsitRsrvFam";
    //				} else if ("V".equals(approval.getVipYn())) {
    //					templateNm = "vsitRsrvVip";
    //				} else if ("G".equals(approval.getVipYn())) {
    //					templateNm = "vsitRsrvGs";
    //				}
    //			} else if (approval.getSchemaNm().equals("CAR_PASS_TEMP")) { // 임시차량출입신청1
    //				String menuId = String.valueOf(contentMap.getOrDefault("menuId", ""));
    //				if ("P01040105".equals(menuId)) {
    //					templateNm = "tempConCarPassReqst";
    //				} else if ("P01040106".equals(menuId)) {
    //					templateNm = "tempLogisticsCarPassReqst";
    //				} else if ("P01040107".equals(menuId)) {
    //					templateNm = "tempWeighingCarPassReqst";
    //				}
    //			} else if (approval.getSchemaNm().equals("SCRT_CHANGE")) { // 보안담당자 신규/변경 신청
    //				templateNm = "scrtChange";
    //			} else if (approval.getSchemaNm().equals("ORG_VISIT")) { // 단체방문 등록
    //				templateNm = "orgVisit";
    //			} else if (approval.getSchemaNm().equals("ITEQMT_APPL")) { // 전산저장 장치 사용 신청
    //				templateNm = "portableStorage";
    //			} else if (approval.getSchemaNm().equals("ITEQMT_CHG_APPL")) { // 전산저장 장치 변경 신청
    //				templateNm = "portableStorageChange";
    //			} else if (approval.getSchemaNm().equals("ITEQMT_REISSUE_APPL")) { // 전산저장 장치 스티커 재발급 신청
    //				templateNm = "portableStorageReissue";
    //			} else if (approval.getSchemaNm().equals("ITEQMT_STOP_APPL")) { // 전산저장 장치 사용 중지 신청
    //				templateNm = "portableStorageStop";
    //			} else if (approval.getSchemaNm().equals("CAR_PASS_VSIT")) { // 방문차량출입신청
    //				templateNm = "carPassReqst";
    //			} else if (approval.getSchemaNm().equals("DELIVERY_CAR_VSIT")) { // 납품차량출입신청
    //				templateNm = "deliveryCarPassReqst";
    //			} else if (approval.getSchemaNm().equals("CCTV_CHG_APPL")) { // CCTV 실사용자 변경 신청
    //				templateNm = "deviceUserUpdateChange";
    //			} else if (approval.getSchemaNm().equals("CCTV_REQ_APPL")) { // 이동형 CCTV장치 사용신청
    //				templateNm = "deviceCCTVRequest";
    //			} else if (approval.getSchemaNm().equals("CCTV_STOP_APPL")) { // 이동형 CCTV장치 사용중지 신청
    //				templateNm = "deviceCCTVStop";
    //			} else if (approval.getSchemaNm().equals("N_ITEQMT_IO_REISSUE_APPL")) { // 휴대용 전산저장장치 스티커 재발급 신청
    //				templateNm = "portableStorageReissueExt";
    //			} else if (approval.getSchemaNm().equals("CONST_PRJ_APPL")) { // 공사프로젝트 등록
    //				templateNm = "constProjectReg";
    //			} else if (approval.getSchemaNm().equals("SWITCH_PORT_OPEN_APPL")) { // 스위치 포트 오픈
    //				templateNm = "switchPortOpenAppl";
    //			} else if (approval.getSchemaNm().equals("NATIONAL_CORE_TECH_APPL")) { // 국가핵심기술 보안진단 결과
    //				templateNm = "nationalCoreTechAppl";
    //			} else if (approval.getSchemaNm().equals("SECRET_BOX_APPL")) { // 비밀문서함 점검결과
    //				templateNm = "secretBoxAppl";
    //			} else if (approval.getSchemaNm().equals("IO_VIOLATION_APPL")) { // 외부인위규자 조치실행
    //				templateNm = "ioViolationAppl";
    //			} else if (approval.getSchemaNm().equals("CO_VIOLATION_APPL")) { // 구성원위규자 조치실행
    //				templateNm = "coViolationAppl";
    //			} else if (approval.getSchemaNm().equals("CONST_PASS_APPL")) { // 공사출입증 지급신청
    //				templateNm = "constPassProv";
    //			}
    //
    //			contentMap.put("canceletc", approval.getCanceletc());
    //			context.setVariable("content", contentMap);
    //
    //			log.info("\n>>>> genParamJsonText templateNm: " + templateNm);
    //			log.info("\n>>>> genParamJsonText context: " + context);
    //			if (templateNm != null && !templateNm.equals("")) {
    //				htmlContent = templateEngine.process(templateNm, context);
    //			}
    //
    //		} catch (Exception e) {
    //			throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
    //					e.toString());
    //		}
    //
    //		return htmlContent;
    //	}

    private void insertApproverPre(int apSeq, SavedApproverLineDTO approver, int docId, String crtBy) throws Exception {
        approver.setDocId(docId);
        approver.setApSeq(apSeq);
        approver.setCrtBy(crtBy);
        log.info(">>>> insertApproval insertApproverPre: docId " + docId + " apSeq: " + apSeq);
        log.info(">>>> insertApproval insertApproverPre: approver " + approver);
        int resultCount = approvalRepository.insertApproverPre(approver);
        log.info(">>>> insertApproval insertApproverPre: " + resultCount);
    }

    /**
     * EAI 통합결재 결과 수신 저장 API
     *
     * @return
     *
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @description 승인 부결 EAI 전문 수신시 각 업무별 후처리를 위해 넘기는 Map 정보 입니다.
     * <p>
     * "APPR_RESULT_VERIFY": "0", //최종승인일때 사용할 비교값 : 0-> 최종승인 처리하고.
     * Otherwise이면 최종승인처리 안하는 용도로 사용 : 2015-11-11 by JSH "SCHEMA_NM":
     * "", "APPR_STAT": "", "APPR_RESULT": "", // 결재결과 0결과전 1승인 2부결.
     * 전문의 p_status Y ->1 or Z -> 2 값 "CANCELETC": "", //전문의 p_comment
     * 값. 승인 or 부결시 통합결재에서 입력한 코멘트를 AP_APPR.CANCEL_ETC에 반영함. :
     * 2015-11-10 by JSH "APPL_EMP_ID": "", "APPL_EMP_NM": "",
     * "APPL_DEPT_ID": "", "APPL_DEPT_NM": "", "APPL_JW_NM": "",
     * "APPR_DEPT_GBN": "", "COMP_ID": "", "DEPT_ID": "", "JW_ID": "",
     * "EMP_ID": "", "AUTO_SIGN": "", "APPR_DTM": "", "LID": "", // 이
     * 필드에 각 업무별 KEY 가 대응 된다. 아래 주석 참조 "UI_URL": "", "DOC_NM": "",
     * "F_PARAM": "", "EMP_ID": "", "p_legacy_pk": "", // DOC_ID [EAI
     * 전문] "p_gubun": "", // 양식코드명 [EAI 전문] "p_apprseq": "", // 결재 순서
     * [EAI 전문] "p_apprid": "", "p_status": "", // 결재결과 Y;승인 Z:부결 [EAI
     * 전문] "p_approvedt": "", "p_comment": "", // CANCELETC과 동일 값 [EAI
     * 전문]
     * <p>
     * 기존 소스 분석해 보면 위의 공통 값 이외에도 "DOC_ID", "INOUT_DOC_PRINT" 등 각 스키마에서만
     * 부분적으로 추가되는 속성들이 기존과 동일하게 추가될 것입니다. (그래서 Map 형식으로 만들었습니다.)
     * <p>
     * LID 에는 각 업무별 KEY 가 되는 값이 매핑 된다. [스키마]: [화면명] = [LID 값에 대응 되는
     * 업무별KEY 컬럼명] INOUT: 자산반출입 = INOUT_APPL_NO IO_OUT_APPL: 업체물품반출입 =
     * INOUT_APPL_NO INDATE_CHANGE: 반입일자 변경신청 = INDATE_APPL_NO CALLING:
     * 물품반입지연 소명 = CALLING_APPL_NO INOUT_EMP_CHANGE: 담당자 변경신청 =
     * INDATE_APPL_NO INOUT_KND_CHANGE: 반입불요 전환신청(자사) =
     * INOUT_CHANGE_APPL_NO INOUT_KND_CHANGE2: 반입불요 전환신청(해외법인/외부업체) =
     * INOUT_CHANGE_APPL_NO FINISH_CHANGE: 물품반입확인서 신청 = FINISH_APPL_NO
     * REPAIR_GATEIN_APPL: 수리세정 반입확인 신청 = REPAIR_APPL_NO
     * INOUT_DOC_PRINT: 사외문서 반출신청 = DOC_APPL_NO
     * @since : 2021. 3. 2.
     */
    @Override
    public boolean updateReceptionApproval(Map<String, Object> approval) {

        boolean result = true;
        try {
            log.info(">>>> updateReceptionApproval Object approval: " + approval.toString());
            log.info(">>>> =====================================================================");
            log.info(">>>>   p_legacy_pk        : " + approval.getOrDefault("p_legacy_pk", ""));
            log.info(">>>>   p_gubun            : " + approval.getOrDefault("p_gubun", ""));
            log.info(">>>>   p_apprseq          : " + approval.getOrDefault("p_apprseq", ""));
            log.info(">>>>   p_apprid           : " + approval.getOrDefault("p_apprid", ""));
            log.info(">>>>   p_status           : " + approval.getOrDefault("p_status", ""));
            log.info(">>>>   p_approvedt        : " + approval.getOrDefault("p_approvedt", ""));
            log.info(">>>>   p_comment          : " + approval.getOrDefault("p_comment", ""));
            log.info(">>>>   EMP_ID             : " + approval.getOrDefault("EMP_ID", ""));
            log.info(">>>>   LID                : " + approval.getOrDefault("LID", ""));
            log.info(">>>>   DOC_ID             : " + approval.getOrDefault("DOC_ID", ""));
            log.info(">>>>   SCHEMA_NM          : " + approval.getOrDefault("SCHEMA_NM", ""));
            log.info(">>>>   cnfmUrl            : " + approval.getOrDefault("cnfmUrl", ""));
            log.info(">>>>   E_CHK_TYPE         : " + approval.getOrDefault("E_CHK_TYPE", ""));
            log.info(">>>>   E_MESSAGE          : " + approval.getOrDefault("E_MESSAGE", ""));
            log.info(">>>>   APPR_RESULT_VERIFY : " + approval.getOrDefault("APPR_RESULT_VERIFY", ""));// 최종승인일때 사용할
            // 비교값 : 0->
            // 처리,
            // Otherwise이면
            // 최종승인처리
            // 안하는 용도로
            // 사용 :
            // 2015-11-11
            // by JSH
            log.info(">>>>   PUBLISHDEPTOKYN    : " + approval.getOrDefault("PUBLISHDEPTOKYN", ""));
            log.info(">>>>   LEAVEDEPTOKYN      : " + approval.getOrDefault("LEAVEDEPTOKYN", ""));

            log.info(">>>> =====================================================================");
            /*
             * 1. 필수값 확인 LID, (DOC_ID || p_legacy_pk), SCHEMA_NM 2. SCHEMA_NM 값에 따라 분기 처리 3.
             * p_status 값에 따라 승인/부결 호출
             *
             */
            String schemaNm = approval.getOrDefault("SCHEMA_NM", "").toString();
            String status = approval.getOrDefault("p_status", "").toString();
            String apprResultVerify = approval.getOrDefault("APPR_RESULT_VERIFY", "").toString();

            if ("Y".equals(status)) {
                /*
                 * 승인 처리 ===============================================
                 */

                if (schemaNm.equals("INOUT")) { // 자산반출입 신청

                    log.info(">>>> 승인 후처리 INOUT =======================");

                    // 주석처리 2023-06-09
                    // inoutAssetApiClient.confirmInoutwrite(approval);

                    log.info(">>>> inoutAssetApiClient.confirmInoutwrite");
                    log.info(">>>> /inoutwrite/approval/confirm");

                    /*
                     * 최종승인 확인하여 모바일 반출입증 발급 알림 발송 - 구성원이 자사간 이동 배송방법 Hand carry 인 경우에만 모바일 발송한다.
                     *
                     * - "모바일 반출입증 발급 알림 발송"만을 위한 분기문이며 해당 업무에 대한 최종승인 처리는
                     * inoutAssetApiClient.confirmInoutwrite 에 구현되어 있다.
                     *
                     * REST API 도 추가하여 재전송 버튼클릭에 대응해야 한다.
                     */
                    if ("0".equals(apprResultVerify)) {
                        log.info(">>>> 최종 승인 후 모바일 반출입증 발급 알림 발송 =======================");
                        Integer inoutApplNo = Integer.parseInt(approval.get("LID").toString());
                        // 주석처리 2023-06-09
                        // InoutWriteSearchDTO searchInoutWrite = new InoutWriteSearchDTO();
                        // searchInoutWrite.setInoutApplNo(inoutApplNo);

                        try {
                            // 주석처리 2023-06-09
                            // notificationService.mobileInoutPrint(searchInoutWrite);
                        } catch (Exception e) {
                            // 카카오 메시지 전송은 실패되어도 무시되도록
                            e.printStackTrace();
                        }
                    }
                }
                else if (schemaNm.equals("INDATE_CHANGE")) { // 자산반출입 반입일자 변경요청

                    log.info(">>>> 승인 후처리 INDATE_CHANGE ===============");
                    // TODO: 후처리 연결해야 함

                }
                else if (schemaNm.equals("INOUT_KND_CHANGE")) { // 자산반출입 반입불요 전환요청(자사)

                    log.info(">>>> 승인 후처리 INOUT_KND_CHANGE ============");

                    // 주석처리 2023-06-09
                    // inoutAssetApiClient.confirmInoutkndchange(approval);

                    log.info(">>>> inoutAssetApiClient.confirmInoutkndchange");
                    log.info(">>>> /inoutkndchange​/approval​/update");
                }
                else if (schemaNm.equals("INOUT_KND_CHANGE2")) { // 자산반출입 반입불요 전환요청(외부업체)

                    log.info(">>>> 승인 후처리 INOUT_KND_CHANGE2 ===========");

                    // 주석처리 2023-06-09
                    // inoutAssetApiClient.confirmInoutkndchange(approval);

                    log.info(">>>> inoutAssetApiClient.confirmInoutkndchange");
                    log.info(">>>> /inoutkndchange​/approval​/update");
                }
                else if (schemaNm.equals("FINISH_CHANGE")) { // 자산반출입 물품반입확인서 신청

                    log.info(">>>> 승인 후처리 FINISH_CHANGE ===============");

                    // 주석처리 2023-06-09
                    // inoutAssetApiClient.confirmFinishchange(approval);

                    log.info(">>>> inoutAssetApiClient.confirmFinishchange");
                    log.info(">>>> /finishchange/approval/confirm");
                }
                else if (schemaNm.equals("CALLING")) { // 자산반출입 물품반입지연 사유서

                    log.info(">>>> 승인 후처리 CALLING =====================");

                    // 주석처리 2023-06-09
                    // inoutAssetApiClient.confirmCalling(approval);

                    log.info(">>>> inoutAssetApiClient.confirmCalling");
                    log.info(">>>> /calling/approval/approve");
                }
                else if (schemaNm.equals("INOUT_EMP_CHANGE")) { // 자산반출입 담당자 변경요청

                    log.info(">>>> 승인 후처리 INOUT_EMP_CHANGE ============");

                    // 주석처리 2023-06-09
                    // inoutAssetApiClient.confirmEmpchange(approval);

                    log.info(">>>> inoutAssetApiClient.confirmEmpchange");
                    log.info(">>>> /empchange/approval/update");
                }
                else if (schemaNm.equals("REPAIR_GATEIN_APPL")) { // 수리세정 반입확인 신청

                    log.info(">>>> 승인 후처리 REPAIR_GATEIN_APPL ==========");

                    // 주석처리 2023-06-09
                    // inoutAssetApiClient.confirmRepairgatein(approval);

                    log.info(">>>> inoutAssetApiClient.confirmRepairgatein");
                    log.info(">>>> /repairgatein/approval/confirm");
                }
                else if (schemaNm.equals("IO_OUT_APPL")) { // 업체물품 반출신청

                    log.info(">>>> 승인 후처리 IO_OUT_APPL =================");

                    // 주석처리 2023-06-09
                    // inoutAssetApiClient.confirmInoutwrite(approval); // 자산반출입과 동일

                    log.info(">>>> inoutAssetApiClient.confirmInoutwrite - EXTERNAL");
                    log.info(">>>> /inoutwrite/approval/confirm");
                }
                else if (schemaNm.equals("IO_INOUT")) { // 업체물품 반입신청

                    log.info(">>>> 승인 후처리 IO_INOUT ====================");

                    // 주석처리 2023-06-09
                    // ioinoutAssetApiClient.confirmIoinoutRcvRequest(approval);

                    log.info(">>>> ioinoutAssetApiClient.confirmIoinoutRcvRequest - EXTERNAL");
                    log.info(">>>> /rcvRequest/approval/confirm");
                }
                else if (schemaNm.equals("INOUT_DOC_PRINT")) { // 사외문서반출 신청

                    log.info(">>>> 승인 후처리 INOUT_DOC_PRINT =================");

                    // 주석처리 2023-06-09
                    // inoutAssetApiClient.confirmInoutDocPrint(approval);

                    log.info(">>>> inoutAssetApiClient.confirmDocPrint");
                    log.info(">>>> /inoutDocPrint/approval/confirm");
                }

                /*
                 * 승인 처리 끝 ===============================================
                 */
            }
            else if ("Z".equals(status)) {
                /*
                 * 부결 처리 ===============================================
                 */

                if (schemaNm.equals("INOUT")) { // 자산반출입 신청

                    log.info(">>>> 부결 후처리 INOUT =======================");

                    // 주석처리 2023-06-09
                    // inoutAssetApiClient.denyInoutwrite(approval);

                    log.info(">>>> inoutAssetApiClient.denyInoutwrite");
                    log.info(">>>> /inoutwrite/approval/deny");
                }
                else if (schemaNm.equals("INDATE_CHANGE")) { // 자산반출입 반입일자 변경요청

                    log.info(">>>> 부결 후처리 INDATE_CHANGE ===============");
                    // TODO: 후처리 연결해야 함

                }
                else if (schemaNm.equals("INOUT_KND_CHANGE")) { // 자산반출입 반입불요 전환요청(자사)

                    log.info(">>>> 부결 후처리 INOUT_KND_CHANGE ============");

                    // 주석처리 2023-06-09
                    // inoutAssetApiClient.denyInoutkndchange(approval);

                    log.info(">>>> inoutAssetApiClient.denyInoutkndchange");
                    log.info(">>>> /inoutkndchange​/approval/reject");
                }
                else if (schemaNm.equals("INOUT_KND_CHANGE2")) { // 자산반출입 반입불요 전환요청(외부업체)

                    log.info(">>>> 부결 후처리 INOUT_KND_CHANGE2 ===========");

                    // 주석처리 2023-06-09
                    // inoutAssetApiClient.denyInoutkndchange(approval);

                    log.info(">>>> inoutAssetApiClient.denyInoutkndchange");
                    log.info(">>>> /inoutkndchange​/approval/reject");
                }
                else if (schemaNm.equals("FINISH_CHANGE")) { // 자산반출입 물품반입확인서 신청

                    log.info(">>>> 부결 후처리 FINISH_CHANGE ===============");

                    // 주석처리 2023-06-09
                    // inoutAssetApiClient.denyFinishchange(approval);

                    log.info(">>>> inoutAssetApiClient.denyFinishchange");
                    log.info(">>>> /finishchange/approval/deny");
                }
                else if (schemaNm.equals("CALLING")) { // 자산반출입 물품반입지연 사유서

                    log.info(">>>> 부결 후처리 CALLING =====================");

                    // 주석처리 2023-06-09
                    // inoutAssetApiClient.denyCalling(approval);

                    log.info(">>>> inoutAssetApiClient.denyCalling");
                    log.info(">>>> /calling/approval/reject");
                }
                else if (schemaNm.equals("INOUT_EMP_CHANGE")) { // 자산반출입 담당자 변경요청

                    log.info(">>>> 부결 후처리 INOUT_EMP_CHANGE ============");

                    // 주석처리 2023-06-09
                    // inoutAssetApiClient.denyEmpchange(approval);

                    log.info(">>>> inoutAssetApiClient.denyEmpchange");
                    log.info(">>>> /empchange/approval/reject");
                }
                else if (schemaNm.equals("REPAIR_GATEIN_APPL")) { // 수리세정 반입확인 신청

                    log.info(">>>> 부결 후처리 REPAIR_GATEIN_APPL ==========");

                    // 주석처리 2023-06-09
                    // inoutAssetApiClient.denyRepairgatein(approval);

                    log.info(">>>> inoutAssetApiClient.denyRepairgatein");
                    log.info(">>>> /repairgatein/approval/deny");
                }
                else if (schemaNm.equals("IO_OUT_APPL")) { // 업체물품 반출신청

                    log.info(">>>> 부결 후처리 IO_OUT_APPL =================");

                    // 주석처리 2023-06-09
                    // inoutAssetApiClient.denyInoutwrite(approval); // 자산반출입과 동일

                    log.info(">>>> inoutAssetApiClient.denyInoutwrite - EXTERNAL");
                    log.info(">>>> /inoutwrite/approval/deny");
                }
                else if (schemaNm.equals("IO_INOUT")) { // 업체물품 반입신청

                    log.info(">>>> 부결 후처리 IO_INOUT ====================");
                    // 주석처리 2023-06-09
                    // ioinoutAssetApiClient.denyIoinoutRcvRequest(approval);

                    log.info(">>>> inoutAssetApiClient.denyInoutwrite - EXTERNAL");
                    log.info(">>>> /rcvRequest/approval/deny");
                }
                else if (schemaNm.equals("INOUT_DOC_PRINT")) { // 사외문서반출 신청
                    log.info(">>>> 부결 후처리 INOUT_DOC_PRINT =================");

                    // 주석처리 2023-06-09
                    // inoutAssetApiClient.denyInoutDocPrint(approval);

                    log.info(">>>> inoutAssetApiClient.denyInoutDocPrint");
                    log.info(">>>> /inoutDocPrint/approval/deny");
                }

                /*
                 * 부결 처리 끝 ===============================================
                 */
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return result;
    }

    /**
     * 결재문서 목록 조회
     *
     * @return
     *
     * @author : X0121127 <sungbum.oh@partner.sk.com>
     * @since : 2021. 2. 2.
     */
    @Override
    public List<ApDocDTO> selectApDocList(List<Integer> docIdList) {
        List<ApDocDTO> resultList = new ArrayList<>();

        try {
            // oracle in clause 건수 제한으로 인해 999건씩 조회
            Collection<List<Integer>> sizedDocIdList = CollectionUtils.partitionBySize(docIdList, 999);
            sizedDocIdList.stream().forEach(list -> resultList.addAll(approvalRepository.selectApDocList(list)));
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return resultList;
    }

    /**
     * 결재문서 (key, value) 목록 조회
     *
     * @return
     *
     * @author : X0121127 <sungbum.oh@partner.sk.com>
     * @since : 2021. 2. 2.
     */
    @Override
    public Map<String, Object> selectApDocListForKey(List<Integer> docIdList) {
        Map<String, Object> resultList = null;

        try {
            // oracle in clause 건수 제한으로 인해 999건씩 조회
            List<ApDocDTO> mergedList = new ArrayList<>();
            Collection<List<Integer>> sizedDocIdList = CollectionUtils.partitionBySize(docIdList, 999);
            sizedDocIdList.stream().forEach(list -> mergedList.addAll(approvalRepository.selectApDocList(list)));

            // List to Map 변환
            resultList = mergedList.stream().collect(Collectors.toMap(a -> String.valueOf(a.getDocId()), i -> i));
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return resultList;
    }

    /**
     * 결재진행현황 사번정보 (key, value) 목록 조회
     *
     * @return
     *
     * @author : X0121127 <sungbum.oh@partner.sk.com>
     * @since : 2021. 2. 17.
     */
    @Override
    public Map<String, ApDocEmpDTO> selectApDocEmpListForKey(List<Integer> docIdList) {
        Map<String, ApDocEmpDTO> resultList = null;

        try {
            // oracle in clause 건수 제한으로 인해 999건씩 조회
            List<ApDocEmpDTO> mergedList = new ArrayList<>();
            Collection<List<Integer>> sizedDocIdList = CollectionUtils.partitionBySize(docIdList, 999);
            sizedDocIdList.stream().forEach(list -> mergedList.addAll(approvalRepository.selectApDocEmpList(list)));

            // List to Map 변환
            resultList = mergedList.stream().collect(Collectors.toMap(ApDocEmpDTO::getDocId, i -> i));
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return resultList;
    }

    @Override
    public Boolean deleteApprovals(List<Integer> docIdList) {
        try {
            //			approvalRepository.deleteApApprDelete(docIdList);
            //			approvalRepository.deleteApApprPortal(docIdList);
            //			approvalRepository.deleteApDoc(docIdList);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return true;
    }

    /**
     * 결재문서 복원 처리
     *
     * @param docId
     * @return
     *
     * @author : X0121127<sungbum.oh@partner.sk.com>
     * @sincce : 2021. 3. 31.
     */
    @Override
    public Boolean updateApDocDelYn(Integer docId) {
        Boolean result = false;
        try {
            result = Objects.equals(approvalRepository.updateApDocDelYn(docId), 1);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return result;
    }

    /**
     * 결재 복원 처리
     *
     * @param docId
     * @return
     *
     * @author : X0121127<sungbum.oh@partner.sk.com>
     * @sincce : 2021. 3. 31.
     */
    @Override
    public Boolean updateApApprDelYn(Integer docId) {
        Boolean result = false;
        try {
            result = Objects.equals(approvalRepository.updateApApprDelYn(docId), 1);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return result;
    }

    /**
     * AP_APPR 목록 조회
     *
     * @param docId
     * @return
     *
     * @author : X0121126<woogon.kim1@partner.sk.com>
     * @since : 2021. 4. 7.
     */
    @Override
    public List<ApApprDTO> selectApApprList(Integer docId) {
        List<ApApprDTO> resultList = null;

        try {
            resultList = approvalRepository.selectApApprList(docId);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return resultList;
    }

    @Override
    public List<SpecifiedApproverLineDTO> selectRequestApprovalLine(ApproverLineQueryDTO param) {
        List<SpecifiedApproverLineDTO> resultList = null;

        try {
            resultList = approvalRepository.selectRequestApprovalLine(param);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return resultList;
    }

    @Override
    public List<SpecifiedApproverLineDTO> selectPermitApprovalLine(ApproverLineQueryDTO param) {
        List<SpecifiedApproverLineDTO> resultList = null;

        try {
            resultList = approvalRepository.selectPermitApprovalLine(param);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return resultList;
    }

    @Override
    public Map<String, Object> selectApprovalInfo(Integer docId) {
        Map<String, Object> result = new HashMap<>();

        try {
            Map<String, Object> docView = approvalRepository.selectApprDocView(docId);
            List<ApApprDTO> saveList = approvalRepository.selectApprovalSaveList(docId);
            result.put("docView", docView);
            result.put("saveList", saveList);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return result;
    }

    @Override
    public List<Map<String, Object>> selectRequestDeptList(String deptId) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = approvalRepository.selectRequestDeptList(deptId);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return resultList;
    }

    @Override
    public List<Map<String, Object>> selectApprEmpListByDept(ApproverLineQueryDTO param) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = approvalRepository.selectApprEmpListByDept(param);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return resultList;
    }

}
