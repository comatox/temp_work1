package com.skshieldus.esecurity.controller.api.secrtactvy;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ListDTO;
import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.CommonXlsViewDTO;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.service.secrtactvy.SecurityAdminManageItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Tag(name = "관리자 > 보관 관련자  API")
//@RestController
@Controller
@RequestMapping(value = "/api/secrtactvy/securityAdminManageItem", produces = { "application/json" })
public class SecurityAdminManageItemController {

    @Autowired
    private Environment environment;

    @Autowired
    private SecurityAdminManageItemService service;

    /**
     * 구성원 보안 위규자 조회 - coEmpViolationList
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 11. 10
     */
    @Operation(summary = "구성원 보안 위규자 조회", description = "구성원 보안 위규자 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/coEmpViolationList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> coEmpViolationList(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {
        ListDTO<Map<String, Object>> listDTO = service.coEmpViolationList(paramMap);
        return new ResponseModel<>(HttpStatus.OK, listDTO.getList(), listDTO.getTotalCount());
    }

    /**
     * 구성원 보안 위규자 조회 엑셀다운 - coEmpViolationListExcel
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 11. 11
     */
    @Operation(summary = "구성원 보안 위규자 조회 엑셀다운", description = "구성원 보안 위규자 조회 엑셀다운")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/coEmpViolationList/download")
    public String coEmpViolationListExcel(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap,
        Model model
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        

        CommonXlsViewDTO commonXlsViewDTO = service.coEmpViolationListExcel(paramMap);

        model.addAttribute("data", commonXlsViewDTO);
        return "xlsView";
    }

    /**
     * 담당자 List - scDetlEmpList
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 11. 10
     */
    @Operation(summary = "담당자 List", description = "담당자 List")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/scDetlEmpList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> scDetlEmpList(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {
        return new ResponseModel<>(HttpStatus.OK, service.scDetlEmpList(paramMap));
    }

    /**
     * 구성원 보안 위규자 조회 상세 - coEmpViolationView
     *
     * @param sessionInfoVO
     * @param paramMap
     * @param scIoDocNo
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 11. 11
     */
    @Operation(summary = "구성원 보안 위규자 조회 상세", description = "구성원 보안 위규자 조회 상세")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/coEmpViolationView")
    public @ResponseBody ResponseModel<Map<String, Object>> coEmpViolationView(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {
        return new ResponseModel<>(HttpStatus.OK, service.coEmpViolationView(paramMap));
    }

    /**
     * 구성원 보안 위규자 조치현황 - coEmpViolationActSumList
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 11. 11
     */
    @Operation(summary = "구성원 보안 위규자 조치현황", description = "구성원 보안 위규자 조치현황")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/coEmpViolationActSumList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> coEmpViolationActSumList(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {
        return new ResponseModel<>(HttpStatus.OK, service.coEmpViolationActSumList(paramMap));
    }

    /**
     * 구성원 보안 위규자 삭제 - secrtCorrPlanOfendDelete
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 10. 15
     */
    @Operation(summary = "구성원 보안 위규자 삭제", description = "구성원 보안 위규자 삭제")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/secrtCorrPlanOfendDelete/{scDocNo}")
    public @ResponseBody ResponseModel<Boolean> secrtCorrPlanOfendDelete(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestBody HashMap<String, Object> paramMap,
        @PathVariable String scDocNo
    ) {
        paramMap.put("scDocNo", scDocNo);
        int updateRows = service.secrtCorrPlanOfendDelete(paramMap);
        Boolean isSuccess = updateRows > 0;

        return new ResponseModel<Boolean>(HttpStatus.OK, isSuccess);
    }

    /**
     * 외부인 보안 위규자 조회 - ioEmpViolationList
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 11. 12
     */
    @Operation(summary = "외부인 보안 위규자 조회", description = "외부인 보안 위규자 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/ioEmpViolationList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> ioEmpViolationList(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {

        ListDTO<Map<String, Object>> listDTO = service.ioEmpViolationList(paramMap);
        return new ResponseModel<>(HttpStatus.OK, listDTO.getList(), listDTO.getTotalCount());
    }

    /**
     * 외부인 보안 위규자 조회 엑셀다운로드 - ioEmpViolationListExcel
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 11. 12
     */
    @Operation(summary = "외부인 보안 위규자 조회 엑셀다운로드", description = "외부인 보안 위규자 조회 엑셀다운로드")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/ioEmpViolationList/download")
    public String ioEmpViolationListExcel(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap,
        Model model
    ) throws EsecurityException {

        CommonXlsViewDTO commonXlsViewDTO = service.ioEmpViolationListExcel(paramMap);

        model.addAttribute("data", commonXlsViewDTO);
        return "xlsView";
    }

    /**
     * 외부인 보안 위규자 상세 - ioEmpViolationView
     *
     * @param sessionInfoVO
     * @param paramMap
     * @param scIoDocNo
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 11. 12
     */
    @Operation(summary = "외부인 보안 위규자 상세", description = "외부인 보안 위규자 상세")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/ioEmpViolationView/{scIoDocNo}")
    public @ResponseBody ResponseModel<Map<String, Object>> ioEmpViolationView(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap,
        @PathVariable String scIoDocNo
    ) throws EsecurityException {
        paramMap.put("scIoDocNo", scIoDocNo);
        return new ResponseModel<>(HttpStatus.OK, service.ioEmpViolationView(paramMap));
    }

    /**
     * 외부인 보안 위규자 상세 > 보안 위규 이력 - ioCorrPlanViolationList
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 11. 12
     */
    @Operation(summary = "외부인 보안 위규자 상세 > 보안 위규 이력", description = "외부인 보안 위규자 상세 > 보안 위규 이력")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/ioCorrPlanViolationList/{ofendEmpId}")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> ioCorrPlanViolationList(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap,
        @PathVariable String ofendEmpId
    ) throws EsecurityException {

        paramMap.put("ofendEmpId", ofendEmpId);
        return new ResponseModel<>(HttpStatus.OK, service.ioCorrPlanViolationList(paramMap));
    }

    /**
     * 외부인 보안 위규자 상세 > 접견자 목록 - ioEmpViolationViewMeetList
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 11. 12
     */
    @Operation(summary = "외부인 보안 위규자 상세 > 접견자 목록", description = "외부인 보안 위규자 상세 > 접견자 목록")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/ioEmpViolationViewMeetList/{scIoDocNo}")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> ioEmpViolationViewMeetList(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap,
        @PathVariable String scIoDocNo
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        

        paramMap.put("scIoDocNo", scIoDocNo);
        //System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, service.ioEmpViolationViewMeetList(paramMap));
    }

    /**
     * 외부인 보안 위규자 상세 > 조치현황 - ioEmpViolationActSumList
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 11. 12
     */
    @Operation(summary = "외부인 보안 위규자 상세 > 조치현황", description = "외부인 보안 위규자 상세 > 조치현황")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/ioEmpViolationActSumList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> ioEmpViolationActSumList(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        

        //System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, service.ioEmpViolationActSumList(paramMap));
    }

    /**
     * 외부인 보안 위규자 삭제 - secrtIoCorrPlanOfendDelete
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 11. 16
     */
    @Operation(summary = "외부인 보안 위규자 삭제", description = "외부인 보안 위규자 삭제")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/secrtIoCorrPlanOfendDelete/{scIoDocNo}")
    public @ResponseBody ResponseModel<Boolean> secrtIoCorrPlanOfendDelete(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestBody HashMap<String, Object> paramMap,
        @PathVariable String scIoDocNo
    ) {
        paramMap.put("scIoDocNo", scIoDocNo);
        int updateRows = service.secrtIoCorrPlanOfendDelete(paramMap);
        Boolean isSuccess = updateRows > 0;

        return new ResponseModel<Boolean>(HttpStatus.OK, isSuccess);
    }

    /**
     * 외부인 보안 위규자 승인/반려(문서) - ioCorrPlanDocApprUpdate
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 11. 16
     */
    @Operation(summary = "외부인 보안 위규자 승인/반려", description = "외부인 보안 위규자 승인/반려")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/ioCorrPlanDocApprUpdate/{scIoDocNo}")
    public @ResponseBody ResponseModel<Boolean> ioCorrPlanDocApprUpdate(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestBody HashMap<String, Object> paramMap,
        @PathVariable String scIoDocNo
    ) {
        paramMap.put("scIoDocNo", scIoDocNo);
        int updateRows = service.ioCorrPlanDocApprUpdate(paramMap);
        Boolean isSuccess = updateRows > 0;

        return new ResponseModel<Boolean>(HttpStatus.OK, isSuccess);
    }

    /**
     * 외부인 보안 위규자 상세 > 출입제한 상세 - offLimitsView
     *
     * @param sessionInfoVO
     * @param paramMap
     * @param scIoDocNo
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 11. 18
     */
    @Operation(summary = "외부인 보안 위규자 상세 > 출입제한 상세 ", description = "외부인 보안 위규자 상세 > 출입제한 상세 ")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/offLimitsView/{ioEmpId}")
    public @ResponseBody ResponseModel<Map<String, Object>> offLimitsView(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap,
        @PathVariable String ioEmpId
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        

        paramMap.put("ioEmpId", ioEmpId);
        // System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, service.offLimitsView(paramMap));
    }

    /**
     * 외부인 보안 위규자  > 반려시 메일정보  - selectMailInfoForRejectMail
     *
     * @param sessionInfoVO
     * @param paramMap
     * @param scIoDocNo
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 11. 18
     */
    @Operation(summary = "외부인 보안 위규자  > 반려시 메일정보 ", description = "외부인 보안 위규자  > 반려시 메일정보 ")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/selectMailInfoForRejectMail")
    public @ResponseBody ResponseModel<Map<String, Object>> selectMailInfoForRejectMail(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        

        // System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, service.selectMailInfoForRejectMail(paramMap));
    }

    //

    /**
     * 외부인 보안 위규자 상세 > 출입제한 이력  - offLimitsHistoryList
     *
     * @param sessionInfoVO
     * @param paramMap
     * @param scIoDocNo
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 11. 18
     */
    @Operation(summary = "외부인 보안 위규자 상세 > 조치현황", description = "외부인 보안 위규자 상세 > 조치현황")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/offLimitsHistoryList/{ioEmpId}")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> offLimitsHistoryList(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap,
        @PathVariable String ioEmpId
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        
        paramMap.put("ioEmpId", ioEmpId);
        //System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, service.offLimitsHistoryList(paramMap));
    }

    /**
     * 외부인 보안 위규자 상세 > 출입제한 이력 등록  - offLimitsHistoryInsert
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 11. 18
     */
    @Operation(summary = "외부인 보안 위규자 상세 > 출입제한 이력 등록 ", description = "외부인 보안 위규자 상세 > 출입제한 이력 등록 ")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/offLimitsHistoryInsert")
    public @ResponseBody ResponseModel<Boolean> offLimitsHistoryInsert(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestBody HashMap<String, Object> paramMap
    ) {

        

        // System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());
        paramMap.put("acIp", sessionInfoVO.getIp());

        // 외부인 보안 위규자 상세 > 출입제한 이력 등록  - offLimitsHistoryInsert
        int updateRows = service.offLimitsHistoryInsert(paramMap);

        Boolean isSuccess = updateRows > 0;
        // System.out.println("# isSuccess:" + isSuccess.toString());

        return new ResponseModel<Boolean>(HttpStatus.OK, isSuccess);
    }

    /**
     * 외부인 보안 위규자 상세 > 출입제한 이력 해제  - offLimitsHistoryDelete
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 11. 19
     */
    @Operation(summary = "외부인 보안 위규자 상세 > 출입제한 이력 해제 ", description = "외부인 보안 위규자 상세 > 출입제한 이력 해제")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/offLimitsHistoryDelete")
    public @ResponseBody ResponseModel<Boolean> offLimitsHistoryDelete(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestBody HashMap<String, Object> paramMap
    ) {

        

        // System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());
        paramMap.put("acIp", sessionInfoVO.getIp());

        // 외부인 보안 위규자 상세 > 출입제한 이력 해제  - offLimitsHistoryDelete
        int updateRows = service.offLimitsHistoryDelete(paramMap);

        Boolean isSuccess = updateRows > 0;
        // System.out.println("# isSuccess:" + isSuccess.toString());

        return new ResponseModel<Boolean>(HttpStatus.OK, isSuccess);
    }

    /**
     * 전사 비문 분류 기준표 조회  - secrtDocDistItemList
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 12. 03
     */
    @Operation(summary = "전사 비문 분류 기준표 조회", description = "전사 비문 분류 기준표 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/secrtDocDistItemList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> secrtDocDistItemList(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        

        List<Map<String, Object>> resultList = service.secrtDocDistItemList(paramMap);

        int totalCount = 0;
        if (resultList.size() > 0) {
            totalCount = ((BigDecimal) resultList.get(0).get("totalCount")).intValue();
        }

        //System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, resultList, totalCount);
    }

    /**
     * 전사 비문 분류 기준표 조회  엑셀다운 - secrtDocDistItemListExcel
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 12. 03
     */
    @Operation(summary = "전사 비문 분류 기준표 조회  엑셀다운", description = "전사 비문 분류 기준표 조회  엑셀다운")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/secrtDocDistItemList/download")
    public String secrtDocDistItemListExcel(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap,
        Model model
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        

        CommonXlsViewDTO commonXlsViewDTO = service.secrtDocDistItemListExcel(paramMap);

        model.addAttribute("data", commonXlsViewDTO);
        return "xlsView";
    }

    /**
     * 전사 비문 분류 기준표 상세 - secrtDocDistView
     *
     * @param sessionInfoVO
     * @param paramMap
     * @param scIoDocNo
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 12. 03
     */
    @Operation(summary = "전사 비문 분류 기준표 상세", description = "전사 비문 분류 기준표 상세")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/secrtDocDistView/{scDocDistId}")
    public @ResponseBody ResponseModel<Map<String, Object>> secrtDocDistView(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap,
        @PathVariable String scDocDistId
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        

        paramMap.put("scDocDistId", scDocDistId);
        // System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, service.secrtDocDistView(paramMap));
    }

    /**
     * 전사 비문 분류 기준표 상세 비문분류기준표 List - secrtDocItemViewList
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 12. 03
     */
    @Operation(summary = "전사 비문 분류 기준표 조회", description = "전사 비문 분류 기준표 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/secrtDocItemViewList/{scDocDistId}")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> secrtDocItemViewList(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap,
        @PathVariable String scDocDistId
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        

        paramMap.put("scDocDistId", scDocDistId);
        //System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, service.secrtDocItemViewList(paramMap));
    }

    /**
     * 보안 담당자 변경 조회 - secrtChangeList
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 12. 06
     */
    @Operation(summary = "보안 담당자 변경 조회", description = "보안 담당자 변경 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/secrtChangeList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> secrtChangeList(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        

        List<Map<String, Object>> resultList = service.secrtChangeList(paramMap);

        int totalCount = 0;
        if (resultList.size() > 0) {
            totalCount = ((BigDecimal) resultList.get(0).get("totalCount")).intValue();
        }

        //System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, resultList, totalCount);
    }

    /**
     * 보안 담당자 변경 상세 - secrtChangeView
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 12. 06
     */
    @Operation(summary = "보안담당자 변경 상세정보", description = "보안담당자 변경 상세정보")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/secrtChangeView")
    public @ResponseBody ResponseModel<Map<String, Object>> secrtChangeView(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        

        //System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, service.secrtChangeView(paramMap));
    }

    /**
     * 보안 위규 담당자 관리 조회 - secDetlEmpList
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 12. 07
     */
    @Operation(summary = "보안 위규 담당자 관리 조회", description = "보안 위규 담당자 관리 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/secDetlEmpList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> secDetlEmpList(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {

        ListDTO<Map<String, Object>> listDTO = service.secDetlEmpList(paramMap);

        return new ResponseModel<>(HttpStatus.OK, listDTO.getList(), listDTO.getTotalCount());
    }

    /**
     * 보안 위규 담당자 관리 상세정보 - secDetlEmpView
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 12. 07
     */
    @Operation(summary = "보안 위규 담당자 관리 상세정보", description = "보안 위규 담당자 관리 상세정보")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/secDetlEmpView")
    public @ResponseBody ResponseModel<Map<String, Object>> secDetlEmpView(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {
        return new ResponseModel<>(HttpStatus.OK, service.secDetlEmpView(paramMap));
    }

    /**
     * 보안 위규 담당자 관리 상세 > 저장  - secDetlEmpInsert
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 12. 07
     */
    @Operation(summary = "보안 위규 담당자 관리 상세 > 저장", description = "보안 위규 담당자 관리 상세 > 저장")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/secDetlEmpInsert")
    public @ResponseBody ResponseModel<Boolean> secDetlEmpInsert(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestBody HashMap<String, Object> paramMap
    ) {

        

        paramMap.put("acIp", sessionInfoVO.getIp());
        // System.out.println("# paramMap:" + paramMap.toString());
        int updateRows = service.secDetlEmpInsert(paramMap);
        Boolean isSuccess = updateRows > 0;
        // System.out.println("# isSuccess:" + isSuccess.toString());

        return new ResponseModel<Boolean>(HttpStatus.OK, isSuccess);
    }

    /**
     * 팀내 생활보안점검 조회(관) 조회 - coEmpTeamViolationList
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 12. 07
     */
    @Operation(summary = "팀내 생활보안점검 조회(관) 조회", description = "팀내 생활보안점검 조회(관) 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/coEmpTeamViolationList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> coEmpTeamViolationList(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        

        List<Map<String, Object>> resultList = service.coEmpTeamViolationList(paramMap);

        int totalCount = 0;
        if (resultList.size() > 0) {
            totalCount = ((BigDecimal) resultList.get(0).get("totalCount")).intValue();
        }

        //System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, resultList, totalCount);
    }

    /**
     * 팀내 생활보안점검 조회(관)조회 엑셀다운로드 - coEmpTeamViolationListExcel
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 12. 07
     */
    @Operation(summary = "팀내 생활보안점검 조회(관)조회 엑셀다운로드", description = "팀내 생활보안점검 조회(관)조회 엑셀다운로드")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/coEmpTeamViolationList/download")
    public String coEmpTeamViolationListExcel(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap,
        Model model
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        

        CommonXlsViewDTO commonXlsViewDTO = service.coEmpTeamViolationListExcel(paramMap);

        model.addAttribute("data", commonXlsViewDTO);
        return "xlsView";
    }

    /**
     * 팀내 생활보안점검 조회(관)상세 - coEmpTeamViolationView
     *
     * @param sessionInfoVO
     * @param paramMap
     * @param scIoDocNo
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 12. 07
     */
    @Operation(summary = "팀내 생활보안점검 조회(관)상세 ", description = "팀내 생활보안점검 조회(관)상세 ")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/coEmpTeamViolationView/{scDocNo}")
    public @ResponseBody ResponseModel<Map<String, Object>> secureCoEmpTeamViolationView(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap,
        @PathVariable String scDocNo
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        

        paramMap.put("scDocNo", scDocNo);
        System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, service.coEmpTeamViolationView(paramMap));
    }

    /**
     * 팀내 생활보안점검 조회(관) 이력조회 - coEmpTeamViolationHist
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 11. 03
     */
    @Operation(summary = "팀내 생활보안점검 조회(관) 이력조회", description = "팀내 생활보안점검 조회(관) 이력조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/coEmpTeamViolationHist")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> coEmpTeamViolationHist(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());
        

        System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, service.coEmpTeamViolationHist(paramMap));
    }

    /**
     * 팀내 생활보안점검 조회(관) 삭제  - coEmpTeamViolationDelete
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 11. 03
     */
    @Operation(summary = "팀내 생활보안점검 조회(관) 삭제", description = "팀내 생활보안점검 조회(관) 삭제")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/coEmpTeamViolationDelete/{scDocNo}")
    public @ResponseBody ResponseModel<Boolean> coEmpTeamViolationDelete(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestBody HashMap<String, Object> paramMap,
        @PathVariable String scDocNo
    ) {

        
        paramMap.put("scDocNo", scDocNo);
        System.out.println("# paramMap:" + paramMap.toString());
        int updateRows = service.coEmpTeamViolationDelete(paramMap);
        Boolean isSuccess = updateRows > 0;
        System.out.println("# isSuccess:" + isSuccess.toString());

        return new ResponseModel<Boolean>(HttpStatus.OK, isSuccess);
    }

    /**
     * 팀내 생활보안점검 조회(관)  일괄삭제  - coEmpTeamViolation/del
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author :
     * @since : 2022. 1. 28
     */
    @Operation(summary = "팀내 생활보안점검 조회(관)  일괄삭제", description = "팀내 생활보안점검 조회(관) 일괄삭제")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/coEmpTeamViolation/del")
    public @ResponseBody ResponseModel<Boolean> coEmpTeamViolationDel(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        return new ResponseModel<Boolean>(HttpStatus.OK, service.coEmpTeamViolationDel(paramMap));
    }

    /**
     * 보안 위규자 조회권한 조회 - secrtOfendAuthList
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 12. 08
     */
    @Operation(summary = "보안 위규자 조회권한 조회", description = "보안 위규자 조회권한 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/secrtOfendAuthList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> secrtOfendAuthList(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        

        List<Map<String, Object>> resultList = service.secrtOfendAuthList(paramMap);

        int totalCount = 0;
        if (resultList.size() > 0) {
            totalCount = ((BigDecimal) resultList.get(0).get("totalCount")).intValue();
        }

        //System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, resultList, totalCount);
    }

    /**
     * 보안 위규자 조회권한 상세정보 List - secrtOfendAuthViewList
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 12. 08
     */
    @Operation(summary = "보안 위규자 조회권한 상세정보 List", description = "보안 위규자 조회권한 상세정보 List")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/secrtOfendAuthViewList/{empId}")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> secrtOfendAuthList(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap,
        @PathVariable String empId
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        

        paramMap.put("empId", empId);
        //System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, service.secrtOfendAuthViewList(paramMap));
    }

    /**
     * 보안 위규자 권한 Update - secrtOfendAuthUpdate
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 11. 08
     */
    @Operation(summary = "보안 위규자 권한 Update", description = "보안 위규자 권한 Update")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/secrtOfendAuthUpdate")
    public @ResponseBody ResponseModel<Boolean> secrtOfendAuthUpdate(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestBody HashMap<String, Object> paramMap
    ) {

        

        paramMap.put("acIp", sessionInfoVO.getIp());
        int updateRows = service.secrtOfendAuthUpdate(paramMap);
        Boolean isSuccess = updateRows > 0;
        System.out.println("# isSuccess:" + isSuccess.toString());

        return new ResponseModel<Boolean>(HttpStatus.OK, isSuccess);
    }

    /**
     * 보안 위규자 권한 Insert - secrtOfendAuthInsert
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 11. 08
     */
    @Operation(summary = "보안 위규자 권한 Update", description = "보안 위규자 권한 Update")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/secrtOfendAuthInsert")
    public @ResponseBody ResponseModel<Boolean> secrtOfendAuthInsert(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestBody HashMap<String, Object> paramMap
    ) {

        

        paramMap.put("acIp", sessionInfoVO.getIp());
        int updateRows = service.secrtOfendAuthInsert(paramMap);
        Boolean isSuccess = updateRows > 0;
        System.out.println("# isSuccess:" + isSuccess.toString());

        return new ResponseModel<Boolean>(HttpStatus.OK, isSuccess);
    }

    /**
     * 보안교육이수율 관리 조회 - securityTrainingList
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 12. 09
     */
    @Operation(summary = "보안교육이수율 관리 조회", description = "보안교육이수율 관리 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/securityTrainingList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> securityTrainingList(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        

        List<Map<String, Object>> resultList = service.securityTrainingList(paramMap);

        int totalCount = 0;
        if (resultList.size() > 0) {
            totalCount = ((BigDecimal) resultList.get(0).get("totalCount")).intValue();
        }

        //System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, resultList, totalCount);
    }

    /**
     * 보안교육이수율 관리 저장  - securityTrainingInsert
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 12. 09
     */
    @Operation(summary = "보안교육이수율 관리 저장", description = "보안교육이수율 관리 저장")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/securityTrainingInsert")
    public @ResponseBody ResponseModel<Boolean> securityTrainingInsert(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestBody HashMap<String, Object> paramMap
    ) {

        

        paramMap.put("acIp", sessionInfoVO.getIp());
        int updateRows = service.securityTrainingInsert(paramMap);
        Boolean isSuccess = updateRows > 0;
        System.out.println("# isSuccess:" + isSuccess.toString());

        return new ResponseModel<Boolean>(HttpStatus.OK, isSuccess);
    }

    /**
     * 보안교육이수율 관리 수정  - securityTrainingUpdate
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 12. 09
     */
    @Operation(summary = "보안교육이수율 관리 수정", description = "보안교육이수율 관리 수정")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/securityTrainingUpdate")
    public @ResponseBody ResponseModel<Boolean> securityTrainingUpdate(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestBody HashMap<String, Object> paramMap
    ) {

        

        int updateRows = service.securityTrainingUpdate(paramMap);
        Boolean isSuccess = updateRows > 0;
        System.out.println("# isSuccess:" + isSuccess.toString());

        return new ResponseModel<Boolean>(HttpStatus.OK, isSuccess);
    }

    /**
     * 보안교육이수율 관리 삭제  - securityTrainingDelete
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 12. 09
     */
    @Operation(summary = "보안교육이수율 관리 삭제", description = "보안교육이수율 관리 삭제")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/securityTrainingDelete")
    public @ResponseBody ResponseModel<Boolean> securityTrainingDelete(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestBody HashMap<String, Object> paramMap
    ) {

        

        int updateRows = service.securityTrainingDelete(paramMap);
        Boolean isSuccess = updateRows > 0;
        System.out.println("# isSuccess:" + isSuccess.toString());

        return new ResponseModel<Boolean>(HttpStatus.OK, isSuccess);
    }

    /**
     * 보안교육이수율 관리 상세 - securityTrainingView
     *
     * @param sessionInfoVO
     * @param paramMap
     * @param scIoDocNo
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 12. 17
     */
    @Operation(summary = "보안교육이수율 관리 상세 ", description = "보안교육이수율 관리 상세 ")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/securityTrainingView/{stndYyMm}")
    public @ResponseBody ResponseModel<Map<String, Object>> securityTrainingView(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap,
        @PathVariable String stndYyMm
    ) throws EsecurityException {

        

        paramMap.put("stndYyMm", stndYyMm);
        //System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, service.securityTrainingView(paramMap));
    }

    /**
     * 보안점수 기준정보 조회 - secureEvalItemAdminList
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 12. 20
     */
    @Operation(summary = "보안점수 기준정보 조회", description = "보안점수 기준정보 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/secureEvalItemAdminList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> secureEvalItemAdminList(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());
        

        //System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, service.secureEvalItemAdminList(paramMap));
    }

    /**
     * 보안점수 기준정보 Insert/Update - secureEvalItemInsert
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 12. 20
     */
    @Operation(summary = "보안점수 기준정보 Insert/Update", description = "보안점수 기준정보 Insert/Update")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/secureEvalItemInsert")
    public @ResponseBody ResponseModel<Boolean> secureEvalItemInsert(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestBody HashMap<String, Object> paramMap
    ) {

        

        int updateRows = service.secureEvalItemInsert(paramMap);
        Boolean isSuccess = updateRows > 0;
        //System.out.println("# isSuccess:" + isSuccess.toString());

        return new ResponseModel<Boolean>(HttpStatus.OK, isSuccess);
    }

    //

    /**
     * 보안점수 기준정보 상세 - secureEvalItemView
     *
     * @param sessionInfoVO
     * @param paramMap
     * @param scIoDocNo
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 12. 20
     */
    @Operation(summary = "보안점수 기준정보 상세 ", description = "보안점수 기준정보 상세 ")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/secureEvalItemView")
    public @ResponseBody ResponseModel<Map<String, Object>> secureEvalItemView(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {

        

        return new ResponseModel<>(HttpStatus.OK, service.secureEvalItemView(paramMap));
    }

    /**
     * 보안점수 기준정보  삭제 - secureEvalItemDelete
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 12. 20
     */
    @Operation(summary = "보안점수 기준정보  삭제", description = "보안점수 기준정보  삭제")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/secureEvalItemDelete")
    public @ResponseBody ResponseModel<Boolean> secureEvalItemDelete(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestBody HashMap<String, Object> paramMap
    ) {

        

        int updateRows = service.secureEvalItemDelete(paramMap);
        Boolean isSuccess = updateRows > 0;
        //System.out.println("# isSuccess:" + isSuccess.toString());

        return new ResponseModel<Boolean>(HttpStatus.OK, isSuccess);
    }

    /**
     * 보안점수 기준정보 > 기존 항목 추가 - secureEvalPeriodRefInsert
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 12. 21
     */
    @Operation(summary = "보안점수 기준정보 > 기존 항목 추가", description = "보안점수 기준정보 > 기존 항목 추가")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/secureEvalPeriodRefInsert")
    public @ResponseBody ResponseModel<Boolean> secureEvalPeriodRefInsert(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestBody HashMap<String, Object> paramMap
    ) {

        

        int updateRows = service.secureEvalPeriodRefInsert(paramMap);
        Boolean isSuccess = updateRows > 0;
        //System.out.println("# isSuccess:" + isSuccess.toString());

        return new ResponseModel<Boolean>(HttpStatus.OK, isSuccess, updateRows);
    }

    /**
     * 보안점수 기준정보 > 평가부서 Insert - secureEvalDeptMappingInsert
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 12. 21
     */
    @Operation(summary = "보안점수 기준정보 > 평가부서 Insert", description = "보안점수 기준정보 > 기존 평가부서 Insert")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/secureEvalDeptMappingInsert")
    public @ResponseBody ResponseModel<Boolean> secureEvalDeptMappingInsert(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestBody HashMap<String, Object> paramMap
    ) {

        

        int updateRows = service.secureEvalDeptMappingInsert(paramMap);
        Boolean isSuccess = updateRows > 0;
        //System.out.println("# isSuccess:" + isSuccess.toString());

        return new ResponseModel<Boolean>(HttpStatus.OK, isSuccess, updateRows);
    }

    /**
     * 보안점수 기준정보 > 평가부서 Tree List - secureEvalDeptTreeList
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 12. 22
     */
    @Operation(summary = "보안점수 기준정보 > 평가부서 Tree List", description = "보안점수 기준정보 > 평가부서 Tree List")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/secureEvalDeptTreeList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> secureEvalDeptTreeList(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());
        

        //System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, service.secureEvalDeptTreeList(paramMap));
    }

    /**
     * 전사 팀별 보안담당자 조회 - secrtDeptSecDeputyList
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 12. 23
     */
    @Operation(summary = "전사 팀별 보안담당자 조회", description = "전사 팀별 보안담당자 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/secrtDeptSecDeputyList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> secrtDeptSecDeputyList(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        

        List<Map<String, Object>> resultList = service.secrtDeptSecDeputyList(paramMap);

        int totalCount = 0;
        if (resultList.size() > 0) {
            totalCount = ((BigDecimal) resultList.get(0).get("totalCount")).intValue();
        }

        //System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, resultList, totalCount);
    }

    /**
     * 전사 팀별 보안담당자 조회 엑셀다운 - secrtDeptSecDeputyListExcel
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 12. 23
     */
    @Operation(summary = "전사 팀별 보안담당자 조회 엑셀다운", description = "전사 팀별 보안담당자 조회 엑셀다운")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/secrtDeptSecDeputyList/download")
    public String secrtDeptSecDeputyListExcel(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap,
        Model model
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        

        CommonXlsViewDTO commonXlsViewDTO = service.secrtDeptSecDeputyListExcel(paramMap);

        model.addAttribute("data", commonXlsViewDTO);
        return "xlsView";
    }

    /**
     * 문서자가점검 이력 조회 - secrtDocSelfCheckList
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 12. 24
     */
    @Operation(summary = "문서자가점검 이력 조회", description = "문서자가점검 이력 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/secrtDocSelfCheckList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> secrtDocSelfCheckList(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        

        List<Map<String, Object>> resultList = service.secrtDocSelfCheckList(paramMap);

        int totalCount = 0;
        if (resultList.size() > 0) {
            totalCount = ((BigDecimal) resultList.get(0).get("totalCount")).intValue();
        }

        //System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, resultList, totalCount);
    }

    /**
     * 문서자가점검 이력 조회 엑셀다운 - secrtDocSelfCheckListExcel
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 12. 24
     */
    @Operation(summary = "문서자가점검 이력 조회 엑셀다운", description = "문서자가점검 이력 조회 엑셀다운")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/secrtDocSelfCheckList/download")
    public String secrtDocSelfCheckListExcel(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap,
        Model model
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        

        CommonXlsViewDTO commonXlsViewDTO = service.secrtDocSelfCheckListExcel(paramMap);

        model.addAttribute("data", commonXlsViewDTO);
        return "xlsView";
    }

    /**
     * 개인정보 위탁업체 교육 이수현황 조회 - securityEduNoticeList
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 12. 24
     */
    @Operation(summary = "개인정보 위탁업체 교육 이수현황 조회", description = "개인정보 위탁업체 교육 이수현황 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/securityEduNoticeList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> securityEduNoticeList(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        

        List<Map<String, Object>> resultList = service.securityEduNoticeList(paramMap);

        int totalCount = 0;
        if (resultList.size() > 0) {
            totalCount = ((BigDecimal) resultList.get(0).get("totalCount")).intValue();
        }

        //System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, resultList, totalCount);
    }

    /**
     * 개인정보 위탁업체 교육 이수현황 조회 엑셀다운  - securityEduNoticeListExcel
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 12. 24
     */
    @Operation(summary = "개인정보 위탁업체 교육 이수현황 조회 엑셀다운", description = "개인정보 위탁업체 교육 이수현황 조회 엑셀다운")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/securityEduNoticeList/download")
    public String securityEduNoticeListExcel(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap,
        Model model
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        

        CommonXlsViewDTO commonXlsViewDTO = service.securityEduNoticeListExcel(paramMap);

        model.addAttribute("data", commonXlsViewDTO);
        return "xlsView";
    }

    /**
     * 모바일 포렌직서약서 조회 - securityMobilePledgeSignList
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 12. 27
     */
    @Operation(summary = "모바일 포렌직서약서 조회 ", description = "모바일 포렌직서약서 조회 ")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/securityMobilePledgeSignList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> securityMobilePledgeSignList(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        

        List<Map<String, Object>> resultList = service.securityMobilePledgeSignList(paramMap);

        int totalCount = 0;
        if (resultList.size() > 0) {
            totalCount = ((BigDecimal) resultList.get(0).get("totalCount")).intValue();
        }

        //System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, resultList, totalCount);
    }

    /**
     * 모바일 포렌직서약서 상세 - securityMobilePledgeSignView
     *
     * @param sessionInfoVO
     * @param paramMap
     * @param scIoDocNo
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 12. 20
     */
    @Operation(summary = "모바일 포렌직서약서 상세 ", description = "모바일 포렌직서약서 상세 ")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/securityMobilePledgeSignView/{frsPldgNo}")
    public @ResponseBody ResponseModel<Map<String, Object>> securityMobilePledgeSignView(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap,
        @PathVariable String frsPldgNo
    ) throws EsecurityException {

        
        paramMap.put("frsPldgNo", frsPldgNo);
        return new ResponseModel<>(HttpStatus.OK, service.securityMobilePledgeSignView(paramMap));
    }

    /**
     * 정보보호서약서 조회 - securityPledgeList
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2022. 01. 03
     */
    @Operation(summary = "정보보호서약서 조회", description = "정보보호서약서 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/securityPledgeList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> securityPledgeList(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        

        List<Map<String, Object>> resultList = service.securityPledgeList(paramMap);

        int totalCount = 0;
        if (resultList.size() > 0) {
            totalCount = ((BigDecimal) resultList.get(0).get("totalCount")).intValue();
        }

        //System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, resultList, totalCount);
    }

    /**
     * 정보보호서약서 조회 엑셀다운 - securityPledgeListExcel
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2022. 01. 03
     */
    @Operation(summary = "정보보호서약서 조회 엑셀다운", description = "정보보호서약서 조회 엑셀다운")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/securityPledgeList/download")
    public String securityPledgeListExcel(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap,
        Model model
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        

        CommonXlsViewDTO commonXlsViewDTO = service.securityPledgeListExcel(paramMap);

        model.addAttribute("data", commonXlsViewDTO);
        return "xlsView";
    }

    /**
     * 정보보호서약서 - 메일발송 목록저장 - updateSecurityPledgeMailList
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2022. 01. 05
     */
    @Operation(summary = "정보보호서약서 - 메일발송 목록저장 ", description = "정보보호서약서 - 메일발송 목록저장 ")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/updateSecurityPledgeMailList")
    public @ResponseBody ResponseModel<Boolean> updateSecurityPledgeMailList(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestBody HashMap<String, Object> paramMap
    ) {

        

        int updateRows = service.updateSecurityPledgeMailList(paramMap);
        Boolean isSuccess = updateRows > 0;
        //System.out.println("# isSuccess:" + isSuccess.toString());

        return new ResponseModel<Boolean>(HttpStatus.OK, isSuccess, updateRows);
    }

    /**
     * 전사 보안담당자 관리 조회 - secrtChangeAdminList
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2022. 01. 10
     */
    @Operation(summary = "전사 보안담당자 관리 조회", description = "전사 보안담당자 관리 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/secrtChangeAdminList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> secrtChangeAdminList(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        

        List<Map<String, Object>> resultList = service.secrtChangeAdminList(paramMap);

        int totalCount = 0;
        if (resultList.size() > 0) {
            totalCount = ((BigDecimal) resultList.get(0).get("totalCount")).intValue();
        }

        //System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, resultList, totalCount);
    }

    /**
     * 전사 보안담당자 관리 엑셀다운 - secrtChangeAdminListExcel
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2022. 01. 10
     */
    @Operation(summary = "전사 보안담당자 관리 엑셀다운", description = "전사 보안담당자 관리 엑셀다운")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/secrtChangeAdminList/download")
    public String secrtChangeAdminListExcel(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap,
        Model model
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        

        CommonXlsViewDTO commonXlsViewDTO = service.secrtChangeAdminListExcel(paramMap);

        model.addAttribute("data", commonXlsViewDTO);
        return "xlsView";
    }

    /**
     * 전사 보안담당자 정보 삭제 - secrtChangeAdminDelete
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2022. 01. 10
     */
    @Operation(summary = "전사 보안담당자 정보 삭제", description = "전사 보안담당자 정보 삭제")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/secrtChangeAdminDelete")
    public @ResponseBody ResponseModel<Boolean> secrtChangeAdminDelete(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestBody HashMap<String, Object> paramMap
    ) {

        
        // System.out.println("# paramMap:" + paramMap.toString());
        int updateRows = service.secrtChangeAdminDelete(paramMap);
        Boolean isSuccess = updateRows > 0;
        // System.out.println("# isSuccess:" + isSuccess.toString());

        return new ResponseModel<Boolean>(HttpStatus.OK, isSuccess);
    }

    /**
     * 전사 보안담당자 관리 > 부서검색 - secrtDeptDuptyDeptList
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2022. 01. 10
     */
    @Operation(summary = "전사 보안담당자 관리 > 부서검색", description = "전사 보안담당자 관리 > 부서검색")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/secrtDeptDuptyDeptList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> secrtDeptDuptyDeptList(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        

        List<Map<String, Object>> resultList = service.secrtDeptDuptyDeptList(paramMap);

        int totalCount = 0;
        if (resultList.size() > 0) {
            totalCount = ((BigDecimal) resultList.get(0).get("totalCount")).intValue();
        }

        //System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, resultList, totalCount);
    }

    /**
     * 전사 보안담당자 관리 > 등록   secrtChangeAdminInsert
     * > Step01. Insert SC_DEPT_SEC - secrtChangeAdminScDeptSecInsert
     * > Step02. Delete SC_DEPT_SEC_EMP - secrtChangeAdminScDeptSecEmpDelete
     * > Step03. Insert SC_DEPT_SEC_EMP - secrtChangeAdminScDeptSecEmpInsert
     * > Step04. MERGE CO_EMP_AUTH - secrtChangeSecrtEmpAuthInsert
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2022. 01. 10
     */
    @Operation(summary = "전사 보안담당자 관리 > 등록 ", description = "전사 보안담당자 관리 > 등록 ")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/secrtChangeAdminInsert")
    public @ResponseBody ResponseModel<Boolean> secrtChangeAdminInsert(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestBody HashMap<String, Object> paramMap
    ) {

        
        paramMap.put("acIp", sessionInfoVO.getIp());
        // System.out.println("# paramMap:" + paramMap.toString());
        int updateRows = service.secrtChangeAdminInsert(paramMap);
        Boolean isSuccess = updateRows > 0;
        // System.out.println("# isSuccess:" + isSuccess.toString());

        return new ResponseModel<Boolean>(HttpStatus.OK, isSuccess);
    }

    /**
     * 전사 보안담당자 관리 > 수정   secrtChangeAdminUpdate
     * > Step01. Update SC_DEPT_SEC - secrtChangeAdminScDeptSecUpdate
     * > Step02. Delete SC_DEPT_SEC_EMP - secrtChangeAdminScDeptSecEmpDelete2
     * > Step03. Insert SC_DEPT_SEC_EMP - secrtChangeAdminScDeptSecEmpInsert
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2022. 01. 11
     */
    @Operation(summary = "전사 보안담당자 관리 > 수정 ", description = "전사 보안담당자 관리 > 수정")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/secrtChangeAdminUpdate")
    public @ResponseBody ResponseModel<Boolean> secrtChangeAdminUpdate(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestBody HashMap<String, Object> paramMap
    ) {

        
        paramMap.put("acIp", sessionInfoVO.getIp());
        // System.out.println("# paramMap:" + paramMap.toString());
        int updateRows = service.secrtChangeAdminUpdate(paramMap);
        Boolean isSuccess = updateRows > 0;
        // System.out.println("# isSuccess:" + isSuccess.toString());

        return new ResponseModel<Boolean>(HttpStatus.OK, isSuccess);
    }

    /**
     * 전사 보안담당자 관리 > 관리부서Tree - secrtDeptDuptyDeptTreeList
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2022. 01. 11
     */
    @Operation(summary = "전사 보안담당자 관리 > 관리부서Tree", description = "전사 보안담당자 관리 > 관리부서Tree")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/secrtDeptDuptyDeptTreeList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> secrtDeptDuptyDeptTreeList(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());
        

        //System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, service.secrtDeptDuptyDeptTreeList(paramMap));
    }

    /**
     * 전사 보안담당자 관리 > 상세정보 - secrtChangeAdminView
     *
     * @param sessionInfoVO
     * @param paramMap
     * @param scIoDocNo
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2022. 01. 11
     */
    @Operation(summary = "전사 보안담당자 관리 > 상세정보", description = "전사 보안담당자 관리 > 상세정보")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/secrtChangeAdminView")
    public @ResponseBody ResponseModel<Map<String, Object>> secrtChangeAdminView(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {

        

        return new ResponseModel<>(HttpStatus.OK, service.secrtChangeAdminView(paramMap));
    }

    /**
     * 보안점수 관리 조회 - secureEvalStatusAdminList
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2022. 01. 13
     */
    @Operation(summary = "보안점수 관리 조회", description = "보안점수 관리 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/secureEvalStatusAdminList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> secureEvalStatusAdminList(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        

        List<Map<String, Object>> resultList = service.secureEvalStatusAdminList(paramMap);

        int totalCount = 0;
        if (resultList.size() > 0) {
            totalCount = ((BigDecimal) resultList.get(0).get("totalCount")).intValue();
        }

        //System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, resultList, totalCount);
    }

    /**
     * 보안점수 관리 조회 엑셀다운 - secureEvalStatusAdminListExcel
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2022. 01. 13
     */
    @Operation(summary = "보안점수 관리 조회 엑셀다운", description = "보안점수 관리 조회 엑셀다운")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/secureEvalStatusAdminList/download")
    public String secureEvalStatusAdminListExcel(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap,
        Model model
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        

        CommonXlsViewDTO commonXlsViewDTO = service.secureEvalStatusAdminListExcel(paramMap);

        model.addAttribute("data", commonXlsViewDTO);
        return "xlsView";
    }

    /**
     * 보안점수 관리 조회 > 삭제 - secureEvalStatusDelete
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2022. 01. 17
     */
    @Operation(summary = "보안점수 관리 조회 > 삭제 ", description = "보안점수 관리 조회 > 삭제")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/secureEvalStatusDelete")
    public @ResponseBody ResponseModel<Boolean> secureEvalStatusDelete(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestBody HashMap<String, Object> paramMap
    ) {

        

        int updateRows = service.secureEvalStatusDelete(paramMap);
        Boolean isSuccess = updateRows > 0;

        return new ResponseModel<Boolean>(HttpStatus.OK, isSuccess);
    }

    /**
     * 보안점수 관리 조회 > 확정/확정취소 - secureEvalStatusModify
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2022. 01. 17
     */
    @Operation(summary = "보안점수 관리 조회 > 확정/확정취소 ", description = "보안점수 관리 조회 > 확정/확정취소")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/secureEvalStatusModify")
    public @ResponseBody ResponseModel<Boolean> secureEvalStatusModify(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestBody HashMap<String, Object> paramMap
    ) {

        

        int updateRows = service.secureEvalStatusModify(paramMap);
        Boolean isSuccess = updateRows > 0;

        return new ResponseModel<Boolean>(HttpStatus.OK, isSuccess);
    }

    /**
     * 보안점수 관리 > 평가항목 List - secureEvalItemTargetList
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2022. 01. 17
     */
    @Operation(summary = "보안점수 관리 > 평가항목 List", description = "보안점수 관리 > 평가항목 List")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/secureEvalItemTargetList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> secureEvalItemTargetList(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        

        List<Map<String, Object>> resultList = service.secureEvalItemTargetList(paramMap);

        //System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, resultList);
    }

    /**
     * 보안점수 관리 > 평가결과저장  - secureEvalResultUpdate
     * Step01. Insert/Update SC_EVAL_RESULT - secureEvalResultUpdate
     * Step02. Update SC_EVAL_TARGET_DEPT - secureEvalTotalScore
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2022. 01. 17
     */
    @Operation(summary = "보안점수 관리 조회 > 확정/확정취소 ", description = "보안점수 관리 조회 > 확정/확정취소")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/secureEvalResultUpdate")
    public @ResponseBody ResponseModel<Boolean> secureEvalResultUpdate(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestBody HashMap<String, Object> paramMap
    ) {

        

        int updateRows = service.secureEvalResultUpdate(paramMap);
        Boolean isSuccess = updateRows > 0;

        return new ResponseModel<Boolean>(HttpStatus.OK, isSuccess);
    }

}
