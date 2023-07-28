package com.skshieldus.esecurity.controller.api.entmanage;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.CommonXlsViewDTO;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.service.entmanage.VisitEntranceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "방문출입 API")
@Controller
@RequestMapping(value = "/api/entmanage/visitEntrance", produces = { "application/json" })
public class VisitEntranceController {

    @Autowired
    private Environment environment;

    @Autowired
    private VisitEntranceService service;

    /**
     * 정문 인원 출입 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 26.
     */
    @Operation(summary = "정문 인원 출입 목록 조회", description = "정문 인원 출입 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/frontDoor")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectFrontDoorList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.selectFrontDoorList(paramMap));
    }

    /**
     * 정문 인원 출입 - 인쇄 처리
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 1.
     */
    @Operation(summary = "정문 인원 출입 - 인쇄 처리", description = "정문 인원 출입 - 인쇄 처리한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/frontDoor/print")
    public @ResponseBody ResponseModel<Boolean> executeFrontDoorPrint(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.executeFrontDoorPrint(paramMap));
    }

    /**
     * 정문 인원 출입 - 입문 처리
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 1.
     */
    @Operation(summary = "정문 인원 출입 - 입문 처리", description = "정문 인원 출입 - 입문 처리한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/frontDoor/in")
    public @ResponseBody ResponseModel<Map<String, Object>> executeFrontDoorInprocess(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.executeFrontDoorInprocess(paramMap));
    }

    /**
     * 정문 인원 출입 - 입문 Reset
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 1.
     */
    @Operation(summary = "정문 인원 출입 - 입문 Reset", description = "정문 인원 출입 - 입문 Reset한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/frontDoor/in/reset")
    public @ResponseBody ResponseModel<Boolean> executeFrontDoorInReset(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.executeFrontDoorInReset(paramMap));
    }

    /**
     * 정문 인원 출입 - 출문 처리
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 7.
     */
    @Operation(summary = "정문 인원 출입 - 출문 처리", description = "정문 인원 출입 - 출문 처리한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/frontDoor/out")
    public @ResponseBody ResponseModel<Map<String, Object>> executeFrontDoorOutprocess(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.executeFrontDoorOutprocess(paramMap));
    }

    /**
     * 정문 인원 출입 - 출문 Reset
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 1.
     */
    @Operation(summary = "정문 인원 출입 - 출문 Reset", description = "정문 인원 출입 - 출문 Reset한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/frontDoor/out/reset")
    public @ResponseBody ResponseModel<Boolean> executeFrontDoorOutReset(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.executeFrontDoorOutReset(paramMap));
    }

    /**
     * 정문 인원 출입 - 재출입 처리
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 7.
     */
    @Operation(summary = "정문 인원 출입 - 재출입 처리", description = "정문 인원 출입 - 재출입 처리한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/frontDoor/reInOut")
    public @ResponseBody ResponseModel<Boolean> executeFrontDoorReIn(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.executeFrontDoorReIn(paramMap));
    }

    /**
     * 정문 인원 출입 - Cube 메세지 응답 처리
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 6.
     */
    @Operation(summary = "정문 인원 출입 - Cube 메세지 응답 처리(페이지)", description = "정문 인원 출입 - Cube 메세지 응답 처리한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/frontDoor/receive/cube")
    public @ResponseBody ResponseModel<Boolean> receieveCubeByFrontDoor(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.receieveCubeByFrontDoor(paramMap));
    }

    @Operation(summary = "정문 인원 출입 - Cube 메세지 응답 처리(api)", description = "정문 인원 출입 - Cube 메세지 응답 처리한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/frontDoor/cube/onlineLead")
    public @ResponseBody ResponseModel<Boolean> receieveCubeOnlineLeadByFrontDoorByPost(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.receieveCubeByFrontDoor(paramMap));
    }

    /**
     * 정문 전산기기 반입 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 26.
     */
    @Operation(summary = "정문 전산기기 반입 목록 조회", description = "정문 전산기기 반입 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/carryInPc")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectCarryInPcList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        String pagingYn = paramMap.get("pagingYn") != null
            ? (String) paramMap.get("pagingYn")
            : "N";

        if (pagingYn.equals("Y")) {
            return new ResponseModel<>(HttpStatus.OK, service.selectCarryInPcList(paramMap), service.selectCarryInPcListCnt(paramMap));
        }
        else {
            return new ResponseModel<>(HttpStatus.OK, service.selectCarryInPcList(paramMap));
        }
    }

    /**
     * 정문 전산기기 반입 - 모바일 동의여부 업데이트
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 7.
     */
    @Operation(summary = "정문 전산기기 반입 - 모바일 동의여부 업데이트", description = "정문 전산기기 반입 - 모바일 동의여부를 업데이트한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/carryInPc/mobileUseApply")
    public @ResponseBody ResponseModel<Boolean> updateMobileUseApply(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.updateMobileUseApply(paramMap));
    }

    /**
     * 정문 전산기기 반입 - 바코드 인쇄 실행
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 7.
     */
    @Operation(summary = "정문 전산기기 반입 - 바코드 인쇄 실행", description = "정문 전산기기 반입 - 바코드 인쇄를 실행한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/carryInPc/print/barcode")
    public @ResponseBody ResponseModel<Map<String, Object>> executePrintBarcode(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.executePrintBarcode(paramMap));
    }

    /**
     * 정문 전산기기 반입 상세 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 7.
     */
    @Operation(summary = "정문 전산기기 반입 상세조회", description = "정문 전산기기 반입 상세정보를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/carryInPc/detail")
    public @ResponseBody ResponseModel<Map<String, Object>> selectCarryInPc(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.selectCarryInPc(paramMap));
    }

    /**
     * 정문 전산기기 반입 취소
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 7.
     */
    @Operation(summary = "정문 전산기기 반입 취소", description = "정문 전산기기 반입을 취소한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/carryInPc/cancel")
    public @ResponseBody ResponseModel<Boolean> cancelCarryInPc(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.cancelCarryInPc(paramMap));
    }

    /**
     * 정문 전산기기 반입
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 7.
     */
    @Operation(summary = "정문 전산기기 반입", description = "정문 전산기기를 반입한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/carryInPc/into")
    public @ResponseBody ResponseModel<Boolean> intoCarryInPc(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.intoCarryInPc(paramMap));
    }

    /**
     * 건물 출입 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 21.
     */
    @Operation(summary = "건물 출입 목록 조회", description = "건물 출입 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/buildingPass")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectBuildingPassList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.selectBuildingPassList(paramMap));
    }

    /**
     * 건물 출입 -  Gate 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 21.
     */
    @Operation(summary = "건물 출입 - Gate 목록 조회", description = "건물 출입 - Gate 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/buildingPass/gate")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectBuildingPassGateList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.selectBuildingPassGateList(paramMap));
    }

    /**
     * 건물 출입 - 입문 처리
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 21.
     */
    @Operation(summary = "건물 출입 - 입문 처리", description = "건물 출입 - 입문 처리한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/buildingPass/in")
    public @ResponseBody ResponseModel<Boolean> executeBuildingInprocess(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.executeBuildingInprocess(paramMap));
    }

    /**
     * 건물 출입 - 출문 처리
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 21.
     */
    @Operation(summary = "건물 출입 - 출문 처리", description = "건물 출입 - 출문 처리한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/buildingPass/out")
    public @ResponseBody ResponseModel<Boolean> executeBuildingOutprocess(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.executeBuildingOutprocess(paramMap));
    }

    /**
     * 건물 출입 - 재출입 처리
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 21.
     */
    @Operation(summary = "건물 출입 - 재출입 처리", description = "건물 출입 - 재출입 처리한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/buildingPass/reInOut")
    public @ResponseBody ResponseModel<Boolean> executeBuildingPassReInOut(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.executeBuildingPassReInOut(paramMap));
    }

    /**
     * 납품 출입 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 27.
     */
    @Operation(summary = "납품 출입 목록 조회", description = "납품 출입 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/deliveryPass")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectDeliveryPassList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        String pagingYn = paramMap.get("pagingYn") != null
            ? (String) paramMap.get("pagingYn")
            : "N";

        if (pagingYn.equals("Y")) {
            return new ResponseModel<>(HttpStatus.OK, service.selectDeliveryPassList(paramMap), service.selectDeliveryPassListCnt(paramMap));
        }
        else {
            return new ResponseModel<>(HttpStatus.OK, service.selectDeliveryPassList(paramMap));
        }
    }

    /**
     * 납품 출입 상세 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 27.
     */
    @Operation(summary = "납품 출입 상세 조회", description = "납품 출입 상세정보를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/deliveryPass/{no}")
    public @ResponseBody ResponseModel<Map<String, Object>> selectDeliveryPass(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @PathVariable("no") Integer dlvAppNo) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.selectDeliveryPass(dlvAppNo));
    }

    /**
     * 납품 출입 - 입문 처리
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 28.
     */
    @Operation(summary = "납품 출입 - 입문 처리", description = "납품 출입 - 입문 처리한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/deliveryPass/in")
    public @ResponseBody ResponseModel<Map<String, Object>> executeDeliveryPassInprocess(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.executeDeliveryPassInprocess(paramMap));
    }

    /**
     * 납품 출입 - 입문 Reset
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 28.
     */
    @Operation(summary = "납품 출입 - 입문 Reset", description = "납품 출입 - 입문 Reset한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/deliveryPass/in/reset")
    public @ResponseBody ResponseModel<Boolean> executeDeliveryPassInReset(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.executeDeliveryPassInReset(paramMap));
    }

    /**
     * 납품 출입 - 출문 처리
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 28.
     */
    @Operation(summary = "납품 출입 - 출문 처리", description = "납품 출입 - 출문 처리한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/deliveryPass/out")
    public @ResponseBody ResponseModel<Map<String, Object>> executeDeliveryPassOutprocess(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.executeDeliveryPassOutprocess(paramMap));
    }

    /**
     * 납품 출입 - 출문 Reset
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 28.
     */
    @Operation(summary = "납품 출입 - 출문 Reset", description = "납품 출입 - 출문 Reset한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/deliveryPass/out/reset")
    public @ResponseBody ResponseModel<Boolean> executeDeliveryPassOutReset(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.executeDeliveryPassOutReset(paramMap));
    }

    /**
     * 건물출입 현황 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 2. 8.
     */
    @Operation(summary = "건물출입 현황 목록 조회", description = "건물출입 현황 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/buildingPassHist")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectBuildingPassHistList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        String pagingYn = paramMap.get("pagingYn") != null
            ? (String) paramMap.get("pagingYn")
            : "N";

        if (pagingYn.equals("Y")) {
            return new ResponseModel<>(HttpStatus.OK, service.selectBuildingPassHistList(paramMap), service.selectBuildingPassHistListCnt(paramMap));
        }
        else {
            return new ResponseModel<>(HttpStatus.OK, service.selectBuildingPassHistList(paramMap));
        }
    }

    /**
     * 건물출입 현황 엑셀 다운로드
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 2. 8.
     */
    @Operation(summary = "건물출입 현황 엑셀 다운로드", description = "건물출입 현황 엑셀을 다운로드한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/buildingPassHist/download")
    public String selectAlwaysStatusListExcel(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap, Model model) throws EsecurityException {
        

        CommonXlsViewDTO commonXlsViewDTO = service.selectBuildingPassHistListExcel(paramMap);
        model.addAttribute("data", commonXlsViewDTO);

        return "xlsView";
    }

}
