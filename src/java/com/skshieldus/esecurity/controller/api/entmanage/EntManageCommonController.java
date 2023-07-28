package com.skshieldus.esecurity.controller.api.entmanage;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.service.entmanage.EntManageCommonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "출입관리 공통 API")
@RestController
@RequestMapping(value = "/api/entmanage/common", produces = { "application/json" })
public class EntManageCommonController {

    @Autowired
    private Environment environment;

    @Autowired
    private EntManageCommonService service;

    /**
     * 신청대상자 조회
     *
     * @param sessionInfoVO
     * @param passApplNo
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 9. 15.
     */
    @Operation(summary = "신청대상자 상세 조회", description = "신청대상자 상세 정보를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/passReceipt/{no}")
    public @ResponseBody ResponseModel<Map<String, Object>> selectPassReceipt(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @PathVariable("no") Integer passApplNo) throws EsecurityException {
        

        // 신청대상자 조회(출입증신청NO[PASS_APPL_NO] 이용)
        Map<String, Object> passReceiptMap = service.selectPassReceipt(passApplNo);

        if (!MapUtils.isEmpty(passReceiptMap)) { // 기 출입 건물 목록 조회
            Map<String, Object> paramMap = new HashMap<>();

            /*
             * IDCARD_ID는 '신청대상자 조회'를 통해 조회 가능함으로 아래 로직 주석 처리 by kwg. 210915
             * // 출입증-카드키_통합아이디(SHIXXXXXXX) 조회 paramMap.put("passApplNo",
             * passReceiptMap.get("passApplNo")); paramMap.put("ioEmpId",
             * passReceiptMap.get("ioEmpId"));
             *
             * String idCardId = service.selectPassIDcardId(paramMap);
             * passReceiptMap.put("idCardId", idCardId);
             */

            // 출입건물 목록 조회(Old)
            paramMap.put("cardNo", passReceiptMap.get("cardNo"));
            paramMap.put("compId", passReceiptMap.get("compId"));

            List<Map<String, Object>> buildingList = service.selectOldPassBuildingList(paramMap);
            passReceiptMap.put("buildings", buildingList);

            // Procedure 호출 (dmOldPass_CJ_Building_Procedure_List)
            //			paramMap.put("cardType", "N");
            //			paramMap.put("idcardId", passReceiptMap.get("idCardId"));
            //			// rs1 = dbSelect("dmOldPass_CJ_Building_Procedure_List", reqData.getFieldMap(),"IDcard", onlineCtx);
            //			List<Map<String,Object>> cjList = idcardRepository.selectOldPassCJBuildingList(paramMap);
            //			passReceiptMap.put("cjList", cjList);

            // 출입증 신청 시 건물정보 목록 조회
            String gate = passReceiptMap.get("gate") != null
                ? (String) passReceiptMap.get("gate")
                : "";

            if (!"".equals(gate)) {
                String[] gates = gate.split(";");
                List<Map<String, Object>> requestCoBldgList = service.selectPassRequestCoBldgList(gates);
                passReceiptMap.put("gates", requestCoBldgList);
            }

            // 상시출입증 보안교육 현황 정보 상세 조회
            //			Map<String, Object> passSecEdu = service.selectPassSecEdu(passApplNo);
            //			if(passSecEdu != null) passReceiptMap.putAll(passSecEdu);
        }

        return new ResponseModel<>(HttpStatus.OK, passReceiptMap);
    }

    /**
     * 상시출입증 정지및 제한 여부 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 9. 15.
     */
    @Operation(summary = "상시출입증 정지 및 제한 여부 조회", description = "상시출입증 정지 및 제한 여부를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/passInsStopDenyInfo")
    public @ResponseBody ResponseModel<Map<String, Object>> selectPassInsStopDenyInfo(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam Map<String, Object> paramMap) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.selectPassInsStopDenyInfo(paramMap));
    }

    /**
     * 캠퍼스별 반입건물 목록 조회
     *
     * @param sessionInfoVO
     * @param compId
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 14.
     */
    @Operation(summary = "캠퍼스별 반입건물 목록 조회", description = "캠퍼스별 반입건물 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/comp/gate/{id}")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectCompGateList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @PathVariable("id") String compId) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.selectCompGateList(compId));
    }

    /**
     * 외부인 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 12. 15.
     */
    @Operation(summary = "외부인 목록 조회", description = "외부인 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/ioEmpExt")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectIoEmpExtList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam Map<String, Object> paramMap) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.selectIoEmpExtList(paramMap));
    }

    /**
     * 외부인 검증 정보 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 12. 15.
     */
    @Operation(summary = "외부인 검증 정보 조회", description = "외부인 검증 정보를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/ioEmpExt/check/{ioEmpId}")
    public @ResponseBody ResponseModel<Map<String, Object>> selectIoEmpExtCheckInfo(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @PathVariable String ioEmpId) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.selectIoEmpExtCheckInfo(ioEmpId));
    }

    /**
     * 건물 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 21.
     */
    @Operation(summary = "건물 목록 조회", description = "건물 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/building")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectBuildingList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam Map<String, Object> paramMap) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.selectBuildingList(paramMap));
    }

    /**
     * 외부인 목록 조회 (dmIoEmpEnterAllCompList)
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 1. 4.
     */
    @Operation(summary = "외부인 목록 조회 (dmIoEmpEnterAllCompList)", description = "외부인 목록(dmIoEmpEnterAllCompList)을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/selectIoEmpEnterAllComp")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectIoEmpEnterAllCompList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        String pagingYn = paramMap.get("pagingYn") != null
            ? (String) paramMap.get("pagingYn")
            : "N";

        if (pagingYn.equals("Y")) {
            return new ResponseModel<>(HttpStatus.OK, service.selectIoEmpEnterAllCompList(paramMap), service.selectIoEmpEnterAllCompListCnt(paramMap));
        }
        else {
            return new ResponseModel<>(HttpStatus.OK, service.selectIoEmpEnterAllCompList(paramMap));
        }
    }

}

