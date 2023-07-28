package com.skshieldus.esecurity.controller.api.entmanage;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ListDTO;
import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.CommonXlsViewDTO;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.service.entmanage.EnvrEntmngService;
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

@Tag(name = "환경설정 > 출입관리 API")
@Controller
@RequestMapping(value = "/api/entmanage/envrEntmng", produces = { "application/json" })
public class EnvrEntmngController {

    @Autowired
    private Environment environment;

    @Autowired
    private EnvrEntmngService service;

    /**
     * 출입 차량 제한 관리 > 조회 - selectCarLimitsList
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2022. 01. 20
     */
    @Operation(summary = "출입 차량 제한 관리 > 조회", description = "출입 차량 제한 관리 > 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/carLimits/list")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectOffLimitsList(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {

        List<Map<String, Object>> resultList = service.selectCarLimitsList(paramMap);

        int totalCount = 0;
        if (resultList.size() > 0) {
            totalCount = ((BigDecimal) resultList.get(0).get("totalCount")).intValue();
        }

        return new ResponseModel<>(HttpStatus.OK, resultList, totalCount);
    }

    /**
     * 출입 차량 제한 관리 > 엑셀다운 - selectCarLimitsListExcel
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2022. 01. 20
     */
    @Operation(summary = "출입 차량 제한 관리 > 엑셀다운", description = "출입 차량 제한 관리 > 엑셀다운")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/carLimits/download")
    public String selectCarLimitsListExcel(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap,
        Model model
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        CommonXlsViewDTO commonXlsViewDTO = service.selectCarLimitsListExcel(paramMap);

        model.addAttribute("data", commonXlsViewDTO);
        return "xlsView";
    }

    /**
     * 출입 차량 제한 관리 > 상세 - selectCarLimitsView
     *
     * @param sessionInfoVO
     * @param paramMap
     * @param carDenyNo
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2022. 01. 20
     */
    @Operation(summary = "출입 차량 제한 관리 > 상세", description = "출입 차량 제한 관리 > 상세")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/carLimits/view/{carDenyNo}")
    public @ResponseBody ResponseModel<Map<String, Object>> selectCarLimitsView(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap,
        @PathVariable String carDenyNo
    ) throws EsecurityException {

        paramMap.put("carDenyNo", carDenyNo);

        return new ResponseModel<>(HttpStatus.OK, service.selectCarLimitsView(paramMap));
    }

    /**
     * 출입 차량 제한 관리 > 입력 - insertCarLimits
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2022. 01. 20
     */
    @Operation(summary = "출입 차량 제한 관리 > 입력", description = "출입 차량 제한 관리 > 입력")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/carLimits")
    public @ResponseBody ResponseModel<Boolean> insertCarLimits(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestBody HashMap<String, Object> paramMap
    ) {

        paramMap.put("acIp", sessionInfoVO.getIp());

        int updateRows = service.insertCarLimits(paramMap);
        Boolean isSuccess = updateRows > 0;

        return new ResponseModel<Boolean>(HttpStatus.OK, isSuccess);
    }

    /**
     * 출입 차량 제한 관리 > 수정 - updateCarLimits
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2022. 01. 20
     */
    @Operation(summary = "출입 차량 제한 관리 > 수정", description = "출입 차량 제한 관리 > 수정")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/carLimits/{carDenyNo}")
    public @ResponseBody ResponseModel<Boolean> updateCarLimits(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestBody HashMap<String, Object> paramMap,
        @PathVariable String carDenyNo
    ) {

        paramMap.put("carDenyNo", carDenyNo);
        paramMap.put("acIp", sessionInfoVO.getIp());

        int updateRows = service.updateCarLimits(paramMap);
        Boolean isSuccess = updateRows > 0;

        return new ResponseModel<Boolean>(HttpStatus.OK, isSuccess);
    }

    /**
     * 출입 차량 제한 관리 > 삭제 - deleteCarLimits
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2022. 01. 20
     */
    @Operation(summary = "출입 차량 제한 관리 > 삭제", description = "출입 차량 제한 관리 > 삭제")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/carLimits/{carDenyNo}/delete")
    public @ResponseBody ResponseModel<Boolean> deleteCarLimits(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestBody HashMap<String, Object> paramMap,
        @PathVariable String carDenyNo
    ) {

        paramMap.put("carDenyNo", carDenyNo);

        int updateRows = service.deleteCarLimits(paramMap);
        Boolean isSuccess = updateRows > 0;

        return new ResponseModel<Boolean>(HttpStatus.OK, isSuccess);
    }

    /**
     * 구성원 관리(제한관리) > 조회 - offLimitsEmpCardList
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2022. 01. 24
     */
    @Operation(summary = "구성원 관리(제한관리) > 조회", description = "구성원 관리(제한관리) > 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/offLimitsEmpCard/list")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> offLimitsEmpCardList(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {

        List<Map<String, Object>> resultList = service.offLimitsEmpCardList(paramMap);

        int totalCount = 0;
        if (resultList.size() > 0) {
            totalCount = ((BigDecimal) resultList.get(0).get("totalCount")).intValue();
        }

        return new ResponseModel<>(HttpStatus.OK, resultList, totalCount);
    }

    /**
     * 구성원 관리(제한관리) > 엑셀다운 - offLimitsEmpCardListExcel
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2022. 01. 24
     */
    @Operation(summary = "구성원 관리(제한관리) > 엑셀다운 ", description = "구성원 관리(제한관리) > 엑셀다운 ")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/offLimitsEmpCard/download")
    public String offLimitsEmpCardListExcel(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap,
        Model model
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        CommonXlsViewDTO commonXlsViewDTO = service.offLimitsEmpCardListExcel(paramMap);

        model.addAttribute("data", commonXlsViewDTO);
        return "xlsView";
    }

    /**
     * 구성원 관리(제한관리) > 출입제한 등록(다건) - offLimitsEmpCardDenyMultipleInsert
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2022. 01. 25
     */
    @Operation(summary = "구성원 관리(제한관리) > 출입제한 등록(다건)", description = "구성원 관리(제한관리) > 출입제한 등록(다건)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/offLimitsEmpCardMultipleInsert")
    public @ResponseBody ResponseModel<Boolean> offLimitsEmpCardDenyMultipleInsert(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestBody HashMap<String, Object> paramMap
    ) {

        int updateRows = service.offLimitsEmpCardDenyMultipleInsert(paramMap);
        Boolean isSuccess = updateRows > 0;

        return new ResponseModel<Boolean>(HttpStatus.OK, isSuccess);
    }

    /**
     * 구성원 관리(제한관리) > 출입제한 해제(다건) - offLimitsEmpCardDenyMultipleUpdate
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2022. 01. 25
     */
    @Operation(summary = "구성원 관리(제한관리) > 출입제한 해제(다건)", description = "구성원 관리(제한관리) > 출입제한 해제(다건)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/offLimitsEmpCardMultipleUpdate")
    public @ResponseBody ResponseModel<Boolean> offLimitsEmpCardDenyMultipleUpdate(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestBody HashMap<String, Object> paramMap
    ) {

        int updateRows = service.offLimitsEmpCardDenyMultipleUpdate(paramMap);
        Boolean isSuccess = updateRows > 0;

        return new ResponseModel<Boolean>(HttpStatus.OK, isSuccess);
    }

    /**
     * 구성원 관리(제한관리) > 출입날짜 변경(다건) - offLimitsEmpCardDenyDateMultipleUpdate
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2022. 01. 25
     */
    @Operation(summary = "구성원 관리(제한관리) > 출입날짜 변경(다건)", description = "구성원 관리(제한관리) > 출입날짜 변경(다건)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/offLimitsEmpCardDenyDateMultipleUpdate")
    public @ResponseBody ResponseModel<Boolean> offLimitsEmpCardDenyDateMultipleUpdate(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestBody HashMap<String, Object> paramMap
    ) {

        int updateRows = service.offLimitsEmpCardDenyDateMultipleUpdate(paramMap);
        Boolean isSuccess = updateRows > 0;

        return new ResponseModel<Boolean>(HttpStatus.OK, isSuccess);
    }

    /**
     * 구성원 관리(제한관리) > 상세 - offLimitsEmpCardView
     *
     * @param sessionInfoVO
     * @param paramMap
     * @param searchEmpId
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2022. 01. 27
     */
    @Operation(summary = "구성원 관리(제한관리) > 상세", description = "구성원 관리(제한관리) > 상세")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/offLimitsEmpCardView/{searchEmpId}")
    public @ResponseBody ResponseModel<Map<String, Object>> offLimitsEmpCardView(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap,
        @PathVariable String searchEmpId
    ) throws EsecurityException {

        paramMap.put("searchEmpId", searchEmpId);

        return new ResponseModel<>(HttpStatus.OK, service.offLimitsEmpCardView(paramMap));
    }

    /**
     * 구성원 관리(제한관리) > 출입제한이력 - offLimitsEmpCardHistory
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2022. 01. 27
     */
    @Operation(summary = "구성원 관리(제한관리) > 출입제한이력", description = "구성원 관리(제한관리) > 출입제한이력")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/offLimitsEmpCardHistory/{searchEmpId}")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> offLimitsEmpCardHistory(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap,
        @PathVariable String searchEmpId
    ) throws EsecurityException {

        paramMap.put("searchEmpId", searchEmpId);

        List<Map<String, Object>> resultList = service.offLimitsEmpCardHistory(paramMap);

        return new ResponseModel<>(HttpStatus.OK, resultList);
    }

    //

    /**
     * 방문객 관리(제한관리) > 조회 - offLimitsList
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2022. 02. 07
     */
    @Operation(summary = "방문객 관리(제한관리) > 조회", description = "방문객 관리(제한관리) > 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/offLimits/list")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> offLimitsList(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {

        ListDTO<Map<String, Object>> listDTO = service.offLimitsList(paramMap);

        return new ResponseModel<>(HttpStatus.OK, listDTO.getList(), listDTO.getTotalCount());
    }

    /**
     * 방문객 관리(제한관리) > 엑셀다운 - offLimitsListExcel
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2022. 02. 07
     */
    @Operation(summary = "방문객 관리(제한관리) > 엑셀다운 ", description = "방문객 관리(제한관리) > 엑셀다운 ")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/offLimits/download")
    public String offLimitsListExcel(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap,
        Model model
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        CommonXlsViewDTO commonXlsViewDTO = service.offLimitsListExcel(paramMap);

        model.addAttribute("data", commonXlsViewDTO);
        return "xlsView";
    }

    /**
     * 방문객 관리(제한관리) > 상세 - offLimitsView
     *
     * @param sessionInfoVO
     * @param paramMap
     * @param ioEmpId
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2022. 02. 07
     */
    @Operation(summary = "방문객 관리(제한관리) > 상세", description = "방문객 관리(제한관리) > 상세")
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

        paramMap.put("ioEmpId", ioEmpId);

        return new ResponseModel<>(HttpStatus.OK, service.offLimitsView(paramMap));
    }

    /**
     * 방문객 관리(제한관리) > 출입제한이력 - offLimitsHistoryList
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2022. 02. 07
     */
    @Operation(summary = "방문객 관리(제한관리) > 출입제한이력", description = "방문객 관리(제한관리) > 출입제한이력")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/offLimitsHistoryList/{ioEmpId}")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> offLimitsHistoryList(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap,
        @PathVariable String ioEmpId
    ) throws EsecurityException {

        paramMap.put("ioEmpId", ioEmpId);

        List<Map<String, Object>> resultList = service.offLimitsHistoryList(paramMap);

        return new ResponseModel<>(HttpStatus.OK, resultList);
    }

    /**
     * 방문객 관리(제한관리) > 보안위규이력 - offLimitSecViolationHist
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2022. 02. 08
     */
    @Operation(summary = "방문객 관리(제한관리) > 보안위규이력", description = "방문객 관리(제한관리) > 보안위규이력")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/offLimitSecViolationHist/{offendEmpId}")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> offLimitSecViolationHist(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap,
        @PathVariable String offendEmpId
    ) throws EsecurityException {

        paramMap.put("offendEmpId", offendEmpId);

        List<Map<String, Object>> resultList = service.offLimitSecViolationHist(paramMap);

        return new ResponseModel<>(HttpStatus.OK, resultList);
    }

    /**
     * 방문객 관리(제한관리) > 상세 > 대표관리자초기화 - initRepresentAdmin
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2022. 02. 10
     */
    @Operation(summary = "방문객 관리(제한관리) > 상세 > 대표관리자초기화", description = "방문객 관리(제한관리) > 상세 > 대표관리자초기화")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/initRepresentAdmin/{ioCompId}")
    public @ResponseBody ResponseModel<Boolean> initRepresentAdmin(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestBody HashMap<String, Object> paramMap,
        @PathVariable String ioCompId
    ) {

        paramMap.put("ioCompId", ioCompId);

        int updateRows = service.initRepresentAdmin(paramMap);

        Boolean isSuccess = updateRows > 0;
        //System.out.println("## initRepresentAdmin updateRows:" + updateRows);
        //System.out.println("## initRepresentAdmin isSuccess:" + isSuccess);

        // @TODO - isSuccess가 false 인데도 status: 200 리턴
        return new ResponseModel<Boolean>(HttpStatus.OK, isSuccess);
    }

    /**
     * 방문객 관리(제한관리) > 출입제한 등록 - offLimitsDenyMultipleInsert
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2022. 02. 10
     */
    @Operation(summary = "방문객 관리(제한관리) > 출입제한 등록", description = "방문객 관리(제한관리) > 출입제한 등록")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/offLimitsDenyMultiInsert")
    public @ResponseBody ResponseModel<Boolean> offLimitsDenyMultiInsert(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestBody HashMap<String, Object> paramMap
    ) {

        paramMap.put("acIp", sessionInfoVO.getIp());

        int updateRows = service.offLimitsDenyMultiInsert(paramMap);
        Boolean isSuccess = updateRows > 0;

        return new ResponseModel<Boolean>(HttpStatus.OK, isSuccess);
    }

    /**
     * 방문객 관리(제한관리) > 날짜변경 - offLimitsDenyDateMultiUpdate
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2022. 02. 11
     */
    @Operation(summary = "방문객 관리(제한관리) > 날짜변경", description = "방문객 관리(제한관리) > 날짜변경")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/offLimitsDenyDateMultiUpdate")
    public @ResponseBody ResponseModel<Boolean> offLimitsDenyDateMultiUpdate(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestBody HashMap<String, Object> paramMap
    ) {

        paramMap.put("acIp", sessionInfoVO.getIp());

        int updateRows = service.offLimitsDenyDateMultiUpdate(paramMap);
        Boolean isSuccess = updateRows > 0;

        return new ResponseModel<Boolean>(HttpStatus.OK, isSuccess);
    }

    /**
     * 방문객 관리(제한관리) > 가입승인/전산탈퇴(delYn: N.가입승인, Y.전산탈퇴) - changeUserStatus
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2022. 02. 11
     */
    @Operation(summary = "방문객 관리(제한관리) > 가입승인/전산탈퇴(delYn: N.가입승인, Y.전산탈퇴)", description = "방문객 관리(제한관리) > 가입승인/전산탈퇴(delYn: N.가입승인, Y.전산탈퇴)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/changeUserStatus")
    public @ResponseBody ResponseModel<Boolean> changeUserStatus(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestBody HashMap<String, Object> paramMap
    ) {

        paramMap.put("acIp", sessionInfoVO.getIp());
        int updateRows = service.changeUserStatus(paramMap);
        Boolean isSuccess = updateRows > 0;

        return new ResponseModel<Boolean>(HttpStatus.OK, isSuccess);
    }

    /**
     * 방문객 관리(제한관리) > 회원탈퇴  - holdUserDelete
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2022. 02. 15
     */
    @Operation(summary = "방문객 관리(제한관리) > 회원탈퇴", description = "방문객 관리(제한관리) > 회원탈퇴")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/holdUserDelete")
    public @ResponseBody ResponseModel<Boolean> holdUserDelete(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestBody HashMap<String, Object> paramMap
    ) {

        int updateRows = service.holdUserDelete(paramMap);
        Boolean isSuccess = updateRows > 0;

        return new ResponseModel<Boolean>(HttpStatus.OK, isSuccess);
    }

    /**
     * 방문객 관리(제한관리) >  상시출입증 강제만료 -  offLimitsReceiptMngExpire
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2022. 02. 15
     */
    @Operation(summary = "방문객 관리(제한관리) > 상시출입증 강제만료", description = "방문객 관리(제한관리) > 상시출입증 강제만료")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/offLimitsReceiptMngExpire")
    public @ResponseBody ResponseModel<Boolean> offLimitsReceiptMngExpire(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestBody HashMap<String, Object> paramMap
    ) {

        paramMap.put("acIp", sessionInfoVO.getIp());
        int updateRows = service.offLimitsReceiptMngExpire(paramMap);
        Boolean isSuccess = updateRows > 0;

        return new ResponseModel<Boolean>(HttpStatus.OK, isSuccess);
    }

    /**
     * 방문객 관리(제한관리) > 전산탈퇴 > 정보 Select - ioEmpAdminAuhSelect
     *
     * @param sessionInfoVO
     * @param paramMap
     * @param ioEmpId
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2022. 02. 15
     */
    @Operation(summary = "방문객 관리(제한관리) > 전산탈퇴 > 정보 Select", description = "방문객 관리(제한관리) > 전산탈퇴 > 정보 Select")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/ioEmpAdminAuhSelect/{ioEmpId}")
    public @ResponseBody ResponseModel<Map<String, Object>> ioEmpAdminAuhSelect(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap,
        @PathVariable String ioEmpId
    ) throws EsecurityException {

        paramMap.put("ioEmpId", ioEmpId);

        return new ResponseModel<>(HttpStatus.OK, service.ioEmpAdminAuhSelect(paramMap));
    }

    /**
     * 방문객 관리(제한관리) > 임시비밀번호발송  - tempPasswordSend
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2022. 02. 16
     */
    @Operation(summary = "방문객 관리(제한관리) > 임시비밀번호발송", description = "방문객 관리(제한관리) > 임시비밀번호발송")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/tempPasswordSend")
    public @ResponseBody ResponseModel<Boolean> tempPasswordSend(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestBody HashMap<String, Object> paramMap
    ) {

        //System.out.println("#####tempPasswordSend paramMap1:" + paramMap.toString());
        paramMap.put("acIp", sessionInfoVO.getIp());
        int updateRows = service.tempPasswordSend(paramMap);
        Boolean isSuccess = updateRows > 0;

        return new ResponseModel<Boolean>(HttpStatus.OK, isSuccess);
    }

    /**
     * 방문객 관리(제한관리) > 출입정지 - passReqStop
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2022. 02. 16
     */
    @Operation(summary = "방문객 관리(제한관리) > 출입정지", description = "방문객 관리(제한관리) > 출입정지")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/passReqStop")
    public @ResponseBody ResponseModel<Map<String, Object>> passReqStop(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestBody HashMap<String, Object> paramMap
    ) {

        //System.out.println("#####passReqStop paramMap1:" + paramMap.toString());
        paramMap.put("acIp", sessionInfoVO.getIp());

        return new ResponseModel<>(HttpStatus.OK, service.passReqStop(paramMap));
    }

    /**
     * 방문객 관리(제한관리) > 출입제한이력 해제   - offLimitsDelete
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2022. 02. 16
     */
    @Operation(summary = "방문객 관리(제한관리) > 출입제한이력 해제", description = "방문객 관리(제한관리) > 출입제한이력 해제")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/offLimitsDelete")
    public @ResponseBody ResponseModel<Boolean> offLimitsDelete(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestBody HashMap<String, Object> paramMap
    ) {

        //System.out.println("#####passReqStop paramMap1:" + paramMap.toString());
        paramMap.put("acIp", sessionInfoVO.getIp());
        int updateRows = service.offLimitsDelete(paramMap);
        Boolean isSuccess = updateRows > 0;

        return new ResponseModel<>(HttpStatus.OK, isSuccess);
    }

    /**
     * 방문객 관리(제한관리) > 출입제한 해제(다건) - offLimitsDenyMultiDelete
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2022. 02. 16
     */
    @Operation(summary = "방문객 관리(제한관리) > 출입제한 해제(다건)", description = "방문객 관리(제한관리) > 출입제한 해제(다건)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/offLimitsDenyMultiDelete")
    public @ResponseBody ResponseModel<Boolean> offLimitsDenyMultiDelete(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestBody HashMap<String, Object> paramMap
    ) {

        //System.out.println("#####passReqStop paramMap1:" + paramMap.toString());
        paramMap.put("acIp", sessionInfoVO.getIp());
        int updateRows = service.offLimitsDenyMultiDelete(paramMap);
        Boolean isSuccess = updateRows > 0;

        return new ResponseModel<>(HttpStatus.OK, isSuccess);
    }

}