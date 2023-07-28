package com.skshieldus.esecurity.controller.api.sysmanage;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.service.sysmanage.CodeManageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "환경설정 > 코드관리 API")
@Controller
@RequestMapping(value = "/api/sysmanage/codeManage", produces = { "application/json" })
public class CodeManageController {

    @Autowired
    private Environment environment;

    @Autowired
    private CodeManageService service;

    /**
     * 분류코드 관리
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2022. 02. 15.
     */
    @Operation(summary = "분류코드 조회", description = "분류코드 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/groupManage")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectGroupManageList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.selectGroupManageList(paramMap), service.selectGroupManageListCnt(paramMap));
    }

    /**
     * 분류코드 등록/수정
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2022. 02. 15.
     */
    @Operation(summary = "분류코드 등록/수정", description = "분류코드를 등록/수정한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully update data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/groupManage/update")
    public @ResponseBody ResponseModel<Boolean> updateGroupManage(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) {
        

        paramMap.put("acIp", sessionInfoVO.getIp());

        return new ResponseModel<Boolean>(HttpStatus.OK, service.updateGroupManage(paramMap));
    }

    /**
     * 세부코드 관리
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2022. 02. 15.
     */
    @Operation(summary = "세부코드 조회", description = "세부코드 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/detailManage")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectDetailManageList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.selectDetailManageList(paramMap), service.selectDetailManageListCnt(paramMap));
    }

    /**
     * 세부코드 등록/수정 (목록에서)
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2022. 02. 15.
     */
    @Operation(summary = "세부코드 등록/수정", description = "세부코드를 등록/수정한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully update data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/detailManage/update")
    public @ResponseBody ResponseModel<Boolean> updateDetailManage(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) {
        

        paramMap.put("acIp", sessionInfoVO.getIp());

        return new ResponseModel<Boolean>(HttpStatus.OK, service.updateDetailManage(paramMap));
    }

    /**
     * 세부코드 상세정보 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2022. 02. 15.
     */
    @Operation(summary = "세부코드 상세정보 조회", description = "세부코드 상세정보를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/detailManage/info")
    public @ResponseBody ResponseModel<Map<String, Object>> selectDetailManageDetail(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.selectDetailManageDetail(paramMap));
    }

    /**
     * 세부코드 상세정보 수정 (상세화면에서)
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2022. 02. 15.
     */
    @Operation(summary = "세부코드 상세정보 수정(상세화면)", description = "세부코드 상세정보를 수정 한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully update data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/detailManage/info/update")
    public @ResponseBody ResponseModel<Boolean> updateDetailCodeInfo(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) {
        

        paramMap.put("acIp", sessionInfoVO.getIp());

        return new ResponseModel<Boolean>(HttpStatus.OK, service.updateDetailCodeInfo(paramMap));
    }

}

