package com.skshieldus.esecurity.controller.api.common;

import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.MenuApprStatDTO;
import com.skshieldus.esecurity.model.common.MenuDTO;
import com.skshieldus.esecurity.model.common.MenuSearchDTO;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.service.common.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "메뉴 조회 API")
@RestController
@RequestMapping(value = "/api/common/menu", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class MenuController {

    @Autowired
    private Environment environment;

    @Autowired
    private MenuService menuService;

    /**
     * 사용자 최근 메뉴목록 조회 API
     *
     * @return
     *
     * @author : X0121127 <sungbum.oh@partner.sk.com>
     * @since : 2021. 8. 19.
     */
    @Operation(summary = "사용자 최근 메뉴목록 조회", description = "사용자 최근 메뉴목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/myrecent/{empNo}", produces = { "application/json" })
    public @ResponseBody ResponseModel<List<MenuDTO>> selectMyRecentMenu(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO, @PathVariable String empNo
    ) {
        

        return new ResponseModel<>(HttpStatus.OK, menuService.selectMyRecentMenu(empNo));
    }

    /**
     * 사용자 신청함 메뉴목록 조회 API
     *
     * @return
     *
     * @author : X0121127 <sungbum.oh@partner.sk.com>
     * @since : 2021. 8. 19.
     */
    @Operation(summary = "사용자 신청함 메뉴목록 조회", description = "사용자 신청함 메뉴목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/myappr/{empNo}", produces = { "application/json" })
    public @ResponseBody ResponseModel<List<MenuDTO>> selectMainApprAuthList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO, @PathVariable String empNo
    ) {
        

        return new ResponseModel<>(HttpStatus.OK, menuService.selectMainApprAuthList(empNo));
    }

    /**
     * 사용자 메뉴목록 조회 API
     *
     * @return
     *
     * @author : X0121127 <sungbum.oh@partner.sk.com>
     * @since : 2021. 8. 19.
     */
    @Operation(summary = "사용자 메뉴목록 조회", description = "사용자 메뉴목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/{empNo}", produces = { "application/json" })
    public @ResponseBody ResponseModel<List<MenuDTO>> selectMenuList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO, @PathVariable String empNo
    ) {
        

        return new ResponseModel<>(HttpStatus.OK, menuService.selectMenuList(empNo));
    }

    /**
     * 메뉴 검색(keyword기반) 조회 API
     *
     * @return
     *
     * @author : X0121127 <sungbum.oh@partner.sk.com>
     * @since : 2021. 8. 24.
     */
    @Operation(summary = "메뉴 검색(keyword기반) 조회", description = "keyword기반으로 메뉴정보를 검색한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/search", produces = { "application/json" })
    public @ResponseBody ResponseModel<List<MenuDTO>> selectMenuSearchList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO, MenuSearchDTO menuSearchDTO
    ) {
        

        return new ResponseModel<>(HttpStatus.OK, menuService.selectMenuSearchList(menuSearchDTO));
    }

    /**
     * 신청함 신청건수 조회 API
     *
     * @return
     *
     * @author : X0121127 <sungbum.oh@partner.sk.com>
     * @since : 2021. 8. 25.
     */
    @Operation(summary = "신청함 신청건수 조회", description = "신청함 신청건수를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/myappr/stat/{empNo}", produces = { "application/json" })
    public @ResponseBody ResponseModel<MenuApprStatDTO> selectApprStatList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO, @PathVariable String empNo
    ) {
        

        return new ResponseModel<>(HttpStatus.OK, menuService.selectApprStatList(empNo));
    }

    /**
     * 사용자 신청함 메뉴목록 수정 API
     *
     * @return
     *
     * @author : X0121127 <sungbum.oh@partner.sk.com>
     * @since : 2021. 8. 25.
     */
    @Operation(summary = "사용자 신청함 메뉴목록 수정", description = "사용자 신청함 메뉴목록을 수정한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @PostMapping(value = "/myappr/{empNo}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody ResponseModel<Boolean> updateMainApprAuth(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO, @PathVariable String empNo,
        @RequestBody MenuDTO menuDTO
    ) {
        

        menuDTO.setEmpNo(empNo);
        return new ResponseModel<Boolean>(HttpStatus.OK, menuService.updateMainApprAuth(menuDTO));
    }

    /**
     * 사용자 나의 메뉴목록 조회 (TREE) API
     *
     * @return
     *
     * @author : X0125104 <won.shin@partner.sk.com>
     * @since : 2022. 01. 19.
     */
    @Operation(summary = "사용자 나의 메뉴목록 조회", description = "사용자 나의 메뉴목록(TREE)을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/myMenuLeft")
    public @ResponseBody ResponseModel<List<MenuDTO>> selectHeaderSearchMenuList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO, MenuSearchDTO menuSearchDTO
    ) {
        

        log.info(">>>> menuSearchDTO : {}", menuSearchDTO);
        return new ResponseModel<>(HttpStatus.OK, menuService.selectHeaderSearchMenuList(menuSearchDTO));
    }

    /**
     * 사용자 나의 메뉴목록 조회 (즐겨찾기) API
     *
     * @return
     *
     * @author : X0125104 <won.shin@partner.sk.com>
     * @since : 2022. 03. 03.
     */
    @Operation(summary = "사용자 나의 메뉴목록 조회", description = "사용자 나의 메뉴목록(즐겨찾기)을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/myMenuRight")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectMyMenuList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO, MenuSearchDTO menuSearchDTO
    ) {
        

        log.info(">>>> menuSearchDTO : {}", menuSearchDTO);
        return new ResponseModel<>(HttpStatus.OK, menuService.selectMyMenuList(menuSearchDTO));
    }

    /**
     * 사용자 나의 메뉴목록 저장 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2022. 03. 03.
     */
    @Operation(summary = "사용자 나의 메뉴목록 저장", description = "사용자 나의 메뉴목록을 저장한다")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully merged data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @PostMapping(value = "/saveMyMenuList", produces = { "application/json" })
    public @ResponseBody boolean saveMyMenuList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody Map<String, Object> paramMap
    ) {
        

        return menuService.saveMyMenuList(paramMap);
    }
}