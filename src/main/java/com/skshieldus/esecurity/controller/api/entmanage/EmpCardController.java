package com.skshieldus.esecurity.controller.api.entmanage;

import com.google.gson.Gson;
import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.CommonXlsViewDTO;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.service.entmanage.EmpCardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "사원증 신규/재발급 API")
@Controller
@RequestMapping(value = "/api/entmanage/empCard", produces = { "application/json" })
public class EmpCardController {

    @Autowired
    private Environment environment;

    @Autowired
    private EmpCardService service;

    /**
     * 사원증 발급 현황 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 9. 28.
     */
    @Operation(summary = "사원증 발급 현황 조회", description = "사원증 발급 현황을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectEmpCardList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {

        String pagingYn = paramMap.get("pagingYn") != null
            ? (String) paramMap.get("pagingYn")
            : "N";

        if (pagingYn.equals("Y")) {
            return new ResponseModel<>(HttpStatus.OK, service.selectEmpCardList(paramMap), service.selectEmpCardListCnt(paramMap));
        }
        else {
            return new ResponseModel<>(HttpStatus.OK, service.selectEmpCardList(paramMap));
        }
    }

    /**
     * 사원증 발급 현황 조회
     *
     * @param sessionInfoVO
     * @param empcardApplNo
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 9. 28.
     */
    @Operation(summary = "사원증 발급 현황 상세 조회", description = "사원증 발급 현황 상세를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/{no}")
    public @ResponseBody ResponseModel<Map<String, Object>> selectEmpCard(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @PathVariable("no") Integer empcardApplNo) throws EsecurityException {

        return new ResponseModel<>(HttpStatus.OK, service.selectEmpCard(empcardApplNo));
    }

    /**
     * 사원증 신규발급 현황 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 9. 28.
     */
    @Operation(summary = "사원증 신규발급 현황 목록 조회", description = "사원증 신규발급 현황 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/new")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectNewEmpCardList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {

        String pagingYn = paramMap.get("pagingYn") != null
            ? (String) paramMap.get("pagingYn")
            : "N";

        if (pagingYn.equals("Y")) {
            return new ResponseModel<>(HttpStatus.OK, service.selectNewEmpCardList(paramMap), service.selectNewEmpCardListCnt(paramMap));
        }
        else {
            return new ResponseModel<>(HttpStatus.OK, service.selectNewEmpCardList(paramMap));
        }
    }

    /**
     * 사원증 신규발급 저장
     *
     * @param sessionInfoVO
     * @param file
     * @param params
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 9. 15.
     */
    @Operation(summary = "사원증 신규발급 저장", description = "사원증 신규발급 저장한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/save", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public @ResponseBody ResponseModel<Boolean> saveNewEmpCard(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestPart("file") MultipartFile file, @RequestPart("params") String params) throws EsecurityException {

        Gson gson = new Gson();
        @SuppressWarnings("unchecked")
        Map<String, Object> paramMap = gson.fromJson(params, HashMap.class);

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.saveNewEmpCard(file, paramMap));
    }

    /**
     * 사원증 신규발급 일괄신청
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 9. 27.
     */
    @Operation(summary = "사원증 신규발급 일괄 신청", description = "사원증 신규발급 일괄 신청한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/save/excel")
    public @ResponseBody ResponseModel<Boolean> insertNewEmpCardAll(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.insertNewEmpCardAll(paramMap));
    }

    /**
     * 악세서리 신청
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 10. 12.
     */
    @Operation(summary = "악세서리 신청", description = "악세서리를 신청한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully insert data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/accessory")
    public @ResponseBody ResponseModel<Boolean> insertAccessory(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.insertAccessory(paramMap));
    }

    /**
     * 악세서리 신청 현황 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 10. 12.
     */
    @Operation(summary = "악세서리 신청 현황 조회", description = "악세서리 신청 현황을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/accessory")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectAccessoryList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {

        return new ResponseModel<>(HttpStatus.OK, service.selectAccessoryList(paramMap));
    }

    /**
     * 사원증 (재)발급 신청
     *
     * @param sessionInfoVO
     * @param file
     * @param params
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 10. 14.
     */
    @Operation(summary = "사원증 (재)발급 신청", description = "사원증 (재)발급 신청한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public @ResponseBody ResponseModel<Boolean> insertEmpCard(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestPart(value = "file", required = false) MultipartFile file, @RequestPart("params") String params) throws EsecurityException {

        Gson gson = new Gson();
        @SuppressWarnings("unchecked")
        Map<String, Object> paramMap = gson.fromJson(params, HashMap.class);

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.insertEmpCard(file, paramMap));
    }

    /**
     * 카드번호를 이용한 통합사번 조회 I/F
     *
     * @param sessionInfoVO
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 30.
     */
    @Operation(summary = "카드번호를 이용한 통합사번 조회 I/F", description = "카드번호를 이용한 통합사번을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/oneday/idCard")
    public @ResponseBody ResponseModel<Map<String, Object>> selectOnedayIdCardIf(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {

        return new ResponseModel<>(HttpStatus.OK, service.selectOnedayIdCardIf(paramMap));
    }

    /**
     * (관리자) 액세서리 발급현황 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 15.
     */
    @Operation(summary = "(관리자) 액세서리 발급현황 목록 조회", description = "(관리자) 액세서리 발급현황 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/admAccessory")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectAdmAccessoryList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {

        String pagingYn = paramMap.get("pagingYn") != null
            ? (String) paramMap.get("pagingYn")
            : "N";

        if (pagingYn.equals("Y")) {
            return new ResponseModel<>(HttpStatus.OK, service.selectAdmAccessoryList(paramMap), service.selectAdmAccessoryListCnt(paramMap));
        }
        else {
            return new ResponseModel<>(HttpStatus.OK, service.selectAdmAccessoryList(paramMap));
        }
    }

    /**
     * (관리자) 액세서리 수령확인/취소 업데이트
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 17.
     */
    @Operation(summary = "(관리자) 액세서리 수령확인/취소 업데이트", description = "(관리자) 액세서리 수령확인/취소 업데이트한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/admAccessory/status")
    public @ResponseBody ResponseModel<Boolean> updateAdmAccessoryStatus(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.updateAdmAccessoryStatus(paramMap));
    }

    /**
     * (관리자) 일일 사원증 발급 및 현황
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 1. 13.
     */
    @Operation(summary = "(관리자) 일일 사원증 발급 및 현황 목록 조회", description = "(관리자) 일일 사원증 발급 및 현황 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/admOnedayCard")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectAdmOnedayCardList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        return new ResponseModel<>(HttpStatus.OK, service.selectAdmOnedayCardList(paramMap), service.selectAdmOnedayCardListCnt(paramMap));
    }

    /**
     * (관리자) 일일 사원증 반납 처리
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 1. 13.
     */
    @Operation(summary = "(관리자) 일일 사원증 반납 처리", description = "(관리자) 일일 사원증을 반납 처리한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/admOnedayCard/return")
    public @ResponseBody ResponseModel<Boolean> returnOnedayCard(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {

        return new ResponseModel<>(HttpStatus.OK, service.returnOnedayCard(paramMap));
    }

    /**
     * (관리자) 일일 사원증 발급 및 현황 상세
     *
     * @param sessionInfoVO
     * @param empcardApplNo
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 1. 13.
     */
    @Operation(summary = "(관리자) 일일 사원증 발급 및 현황 상세 조회", description = "(관리자) 일일 사원증 발급 및 현황 상세정보를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/admOnedayCard/{no}")
    public @ResponseBody ResponseModel<Map<String, Object>> selectAdmOnedayCard(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @PathVariable("no") Integer empcardApplNo) throws EsecurityException {

        return new ResponseModel<>(HttpStatus.OK, service.selectAdmOnedayCard(empcardApplNo));
    }

    /**
     * (관리자) 일일 사원증 발급 등록
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 10. 12.
     */
    @Operation(summary = "(관리자) 일일 사원증 발급 등록", description = "(관리자) 일일 사원증 발급 등록한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully insert data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/admOnedayCard")
    public @ResponseBody ResponseModel<Map<String, Object>> insertAdmOnedayCard(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {

        // AC IP 설정
        //        String remoteIp = "";
        //        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
        //            remoteIp = sessionInfoVO.getIp();
        //        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.insertAdmOnedayCard(paramMap));
    }

    /**
     * (관리자) 일일 사원증 발급 엑셀 다운로드
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 10. 12.
     */
    @Operation(summary = "(관리자) 일일 사원증 발급 엑셀 다운로드", description = "(관리자) 일일 사원증 엑셀 다운로드.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully insert data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/admOnedayCard/excel")
    public String admOnedayCardExcelDownload(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap, Model model) throws EsecurityException {
        CommonXlsViewDTO commonXlsViewDTO = service.admOnedayCardExcelDownload(paramMap);
        model.addAttribute("data", commonXlsViewDTO);
        return "xlsView";
    }

}

