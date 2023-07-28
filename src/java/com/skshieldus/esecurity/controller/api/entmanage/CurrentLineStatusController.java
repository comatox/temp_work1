package com.skshieldus.esecurity.controller.api.entmanage;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.CommonXlsViewDTO;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.service.entmanage.CurrentLineStatusService;
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

@Tag(name = "통제구역관리 현황 API")
@Controller
@RequestMapping(value = "/api/entmanage/currentLineStatus", produces = { "application/json" })
public class CurrentLineStatusController {

    @Autowired
    private Environment environment;

    @Autowired
    private CurrentLineStatusService service;

    /**
     * 통제구역관리 장소 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 18.
     */
    @Operation(summary = "통제구역관리 장소 목록 조회", description = "통제구역관리 장소 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/buildingControl")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectBuildingControlList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.selectBuildingControlList(paramMap));
    }

    /**
     * Bay별출입인원(Line현황) 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 18.
     */
    @Operation(summary = "Bay별출입인원(Line현황) 목록 조회", description = "Bay별출입인원(Line현황) 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/accessPersons")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectAccessPersonsList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.selectAccessPersonsList(paramMap));
    }

    /**
     * 내부인명단(Line현황) 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 18.
     */
    @Operation(summary = "내부인명단(Line현황) 목록 조회", description = "내부인명단(Line현황) 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/staffCurrentList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectStaffCurrentList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        String pagingYn = paramMap.get("pagingYn") != null
            ? (String) paramMap.get("pagingYn")
            : "N";

        if (pagingYn.equals("Y")) {
            return new ResponseModel<>(HttpStatus.OK, service.selectStaffCurrentList(paramMap), service.selectStaffCurrentListCnt(paramMap));
        }
        else {
            return new ResponseModel<>(HttpStatus.OK, service.selectStaffCurrentList(paramMap));
        }
    }

    /**
     * 상시출입객명단(Line현황) 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 22.
     */
    @Operation(summary = "상시출입객명단(Line현황) 목록 조회", description = "상시출입객명단(Line현황) 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/alwaysCurrentList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectAlwaysCurrentList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        String pagingYn = paramMap.get("pagingYn") != null
            ? (String) paramMap.get("pagingYn")
            : "N";

        if (pagingYn.equals("Y")) {
            return new ResponseModel<>(HttpStatus.OK, service.selectAlwaysCurrentList(paramMap), service.selectAlwaysCurrentListCnt(paramMap));
        }
        else {
            return new ResponseModel<>(HttpStatus.OK, service.selectAlwaysCurrentList(paramMap));
        }
    }

    /**
     * 방문객명단(Line현황) 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 22.
     */
    @Operation(summary = "방문객명단(Line현황) 목록 조회", description = "방문객명단(Line현황) 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/visitorCurrentList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectVisitorCurrentList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        String pagingYn = paramMap.get("pagingYn") != null
            ? (String) paramMap.get("pagingYn")
            : "N";

        if (pagingYn.equals("Y")) {
            return new ResponseModel<>(HttpStatus.OK, service.selectVisitorCurrentList(paramMap), service.selectVisitorCurrentListCnt(paramMap));
        }
        else {
            return new ResponseModel<>(HttpStatus.OK, service.selectVisitorCurrentList(paramMap));
        }
    }

    /**
     * 상시출입객관리(Line현황) 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 22.
     */
    @Operation(summary = "상시출입객관리(Line현황) 목록 조회", description = "상시출입객관리(Line현황) 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/alwaysManage")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectAlwaysManageList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        String pagingYn = paramMap.get("pagingYn") != null
            ? (String) paramMap.get("pagingYn")
            : "N";

        if (pagingYn.equals("Y")) {
            return new ResponseModel<>(HttpStatus.OK, service.selectAlwaysManageList(paramMap), service.selectAlwaysManageListCnt(paramMap));
        }
        else {
            return new ResponseModel<>(HttpStatus.OK, service.selectAlwaysManageList(paramMap));
        }
    }

    /**
     * 상시출입객관리(Line현황) 상세 조회
     *
     * @param sessionInfoVO
     * @param ioRegIoNo
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 22.
     */
    @Operation(summary = "상시출입객관리(Line현황) 상세 조회", description = "상시출입객관리(Line현황) 정보를 상세 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/alwaysManage/{no}")
    public @ResponseBody ResponseModel<Map<String, Object>> selectAlwaysManage(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @PathVariable("no") Integer ioRegIoNo) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.selectAlwaysManage(ioRegIoNo));
    }

    /**
     * 상시출입객관리(Line현황) 등록
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 22.
     */
    @Operation(summary = "상시출입객관리(Line현황) 등록", description = "상시출입객관리(Line현황) 정보를 등록한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/alwaysManage")
    public @ResponseBody ResponseModel<Boolean> insertAlwaysManage(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.insertAlwaysManage(paramMap));
    }

    /**
     * 상시출입객관리(Line현황) 수정
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 22.
     */
    @Operation(summary = "상시출입객관리(Line현황) 수정", description = "상시출입객관리(Line현황) 정보를 수정한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/alwaysManage/update")
    public @ResponseBody ResponseModel<Boolean> updateAlwaysManage(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.updateAlwaysManage(paramMap));
    }

    /**
     * 상시출입객관리(Line현황) 삭제
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 22.
     */
    @Operation(summary = "상시출입객관리(Line현황) 삭제", description = "상시출입객관리(Line현황) 정보를 삭제한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/alwaysManage/delete")
    public @ResponseBody ResponseModel<Boolean> deleteAlwaysManages(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.deleteAlwaysManages(paramMap));
    }

    /**
     * 상시출입객관리(Line현황) 카드번호 조회
     *
     * @param sessionInfoVO
     * @param cardNo
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 23.
     */
    @Operation(summary = "상시출입객관리(Line현황) 카드번호 조회", description = "상시출입객관리(Line현황) 카드번호를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/alwaysManage/cardNo")
    public @ResponseBody ResponseModel<String> selectAlwaysManageCardNo(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam String cardNo) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.selectAlwaysManageCardNo(cardNo));
    }

    /**
     * 반입물품현황 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 23.
     */
    @Operation(summary = "반입물품현황 목록 조회", description = "반입물품현황 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/itemsStatusList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectItemsStatusList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        String pagingYn = paramMap.get("pagingYn") != null
            ? (String) paramMap.get("pagingYn")
            : "N";

        if (pagingYn.equals("Y")) {
            return new ResponseModel<>(HttpStatus.OK, service.selectItemsStatusList(paramMap), service.selectItemsStatusListCnt(paramMap));
        }
        else {
            return new ResponseModel<>(HttpStatus.OK, service.selectItemsStatusList(paramMap));
        }
    }

    /**
     * 임직원 명단(기간별현황) 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 23.
     */
    @Operation(summary = "임직원 명단(기간별현황) 목록 조회", description = "임직원 명단(기간별현황) 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/staffStatusList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectStaffStatusList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        String pagingYn = paramMap.get("pagingYn") != null
            ? (String) paramMap.get("pagingYn")
            : "N";

        if (pagingYn.equals("Y")) {
            return new ResponseModel<>(HttpStatus.OK, service.selectStaffStatusList(paramMap), service.selectStaffStatusListCnt(paramMap));
        }
        else {
            return new ResponseModel<>(HttpStatus.OK, service.selectStaffStatusList(paramMap));
        }
    }

    /**
     * 임직원 명단(기간별현황) 엑셀 다운로드
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 23.
     */
    @Operation(summary = "임직원 명단(기간별현황) 엑셀 다운로드", description = "임직원 명단(기간별현황) 엑셀 다운로드한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/staffStatusList/download")
    public String selectStaffStatusListExcel(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap, Model model) throws EsecurityException {
        

        CommonXlsViewDTO commonXlsViewDTO = service.selectStaffStatusListExcel(paramMap);
        model.addAttribute("data", commonXlsViewDTO);

        return "xlsView";
    }

    /**
     * 상시출입객 명단(기간별현황) 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 23.
     */
    @Operation(summary = "상시출입객 명단(기간별현황) 목록 조회", description = "상시출입객 명단(기간별현황) 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/alwaysStatusList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectAlwaysStatusList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        String pagingYn = paramMap.get("pagingYn") != null
            ? (String) paramMap.get("pagingYn")
            : "N";

        if (pagingYn.equals("Y")) {
            return new ResponseModel<>(HttpStatus.OK, service.selectAlwaysStatusList(paramMap), service.selectAlwaysStatusListCnt(paramMap));
        }
        else {
            return new ResponseModel<>(HttpStatus.OK, service.selectAlwaysStatusList(paramMap));
        }
    }

    /**
     * 상시출입객 명단(기간별현황) 엑셀 다운로드
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 23.
     */
    @Operation(summary = "상시출입객 명단(기간별현황) 엑셀 다운로드", description = "상시출입객 명단(기간별현황) 엑셀 다운로드한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/alwaysStatusList/download")
    public String selectAlwaysStatusListExcel(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap, Model model) throws EsecurityException {
        

        CommonXlsViewDTO commonXlsViewDTO = service.selectAlwaysStatusListExcel(paramMap);
        model.addAttribute("data", commonXlsViewDTO);

        return "xlsView";
    }

    /**
     * 방문객명단(기간별현황) 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 23.
     */
    @Operation(summary = "방문객명단(기간별현황) 목록 조회", description = "방문객명단(기간별현황)목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/visitorStatusList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectVisitorStatusList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        String pagingYn = paramMap.get("pagingYn") != null
            ? (String) paramMap.get("pagingYn")
            : "N";

        if (pagingYn.equals("Y")) {
            return new ResponseModel<>(HttpStatus.OK, service.selectVisitorStatusList(paramMap), service.selectVisitorStatusListCnt(paramMap));
        }
        else {
            return new ResponseModel<>(HttpStatus.OK, service.selectVisitorStatusList(paramMap));
        }
    }

    /**
     * 방문객명단(기간별현황) 엑셀 다운로드
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 23.
     */
    @Operation(summary = "방문객명단(기간별현황) 엑셀 다운로드", description = "방문객명단(기간별현황) 엑셀 다운로드한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/visitorStatusList/download")
    public String selectVisitorStatusListExcel(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap, Model model) throws EsecurityException {
        

        CommonXlsViewDTO commonXlsViewDTO = service.selectVisitorStatusListExcel(paramMap);
        model.addAttribute("data", commonXlsViewDTO);

        return "xlsView";
    }

    /**
     * 유해화학물질 취급구역 출입현황 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 23.
     */
    @Operation(summary = "유해화학물질 취급구역 출입현황 목록 조회", description = "유해화학물질 취급구역 출입현황 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/hazardousChemicalsVisitList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectHazardousChemicalsVisitList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.selectHazardousChemicalsVisitList(paramMap));
    }

    /**
     * 유해화학물질 취급 차량현황 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 23.
     */
    @Operation(summary = "유해화학물질 취급 차량현황 목록 조회", description = "유해화학물질 취급 차량현황 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/hazardousChemicalsTmpcarList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectHazardousChemicalsTmpcarList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        String pagingYn = paramMap.get("pagingYn") != null
            ? (String) paramMap.get("pagingYn")
            : "N";

        if (pagingYn.equals("Y")) {
            return new ResponseModel<>(HttpStatus.OK, service.selectHazardousChemicalsTmpcarList(paramMap), service.selectHazardousChemicalsTmpcarListCnt(paramMap));
        }
        else {
            return new ResponseModel<>(HttpStatus.OK, service.selectHazardousChemicalsTmpcarList(paramMap));
        }
    }

}