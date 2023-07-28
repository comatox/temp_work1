package com.skshieldus.esecurity.controller.api.sysmanage;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.service.sysmanage.GroupManageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Tag(name = "환경설정 > 조직관리 API")
@Controller
@RequestMapping(value = "/api/sysmanage/groupManage", produces = { "application/json" })
public class GroupManageController {

    @Autowired
    private Environment environment;

    @Autowired
    private GroupManageService service;

    /**
     * 사업장 관리
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2022. 02. 03.
     */
    @Operation(summary = "사업장관리 조회", description = "사업장관리 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/corpManage")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectCorpManageList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {

        return new ResponseModel<>(HttpStatus.OK, service.selectCorpManageList(paramMap), service.selectCorpManageListCnt(paramMap));
    }

    /**
     * 부서관리
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2022. 02. 03.
     */
    @Operation(summary = "부서관리 조회", description = "부서관리 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/deptManage")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectDeptManageList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {

        return new ResponseModel<>(HttpStatus.OK, service.selectDeptManageList(paramMap), service.selectDeptManageListCnt(paramMap));
    }

    /**
     * 직위관리
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2022. 02. 03.
     */
    @Operation(summary = "직위관리 조회", description = "직위관리 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/jwManage")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectJwManageList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {

        return new ResponseModel<>(HttpStatus.OK, service.selectJwManageList(paramMap), service.selectJwManageListCnt(paramMap));
    }

    /**
     * 부서관리 등록/수정
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     */
    @Operation(summary = "부서관리 등록/수정", description = "부서관리 등록/수정")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/deptManage/save")
    public @ResponseBody ResponseModel<Boolean> saveDeptManage(@RequestBody Map<String, Object> paramMap) throws EsecurityException {

        return new ResponseModel<>(HttpStatus.OK, service.saveDeptManage(paramMap));
    }

    /**
     * 사용자 관리 목록조회
     *
     * @return List<Map < String, Object>>
     *
     * @author : X0125104 <won.shin@partner.sk.com>
     * @since : 2023. 07. 11
     */
    @Operation(summary = "사용자 관리 목록조회", description = "사용자 관리 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/userManage/list", produces = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectUserManageList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam Map<String, Object> paramMap
    ) {
        Map<String, Object> resultMap = service.selectUserManageList(paramMap);
        List<Map<String, Object>> resultList = (List<Map<String, Object>>) resultMap.get("list");
        int totalCount = (int) resultMap.get("totalCount");

        return new ResponseModel<>(HttpStatus.OK, resultList, totalCount);
    }

    /**
     * 사용자 관리 상세조회
     *
     * @return Map<String, Object>
     *
     * @author : X0125104 <won.shin@partner.sk.com>
     * @since : 2023. 07. 12
     */
    @Operation(summary = "사용자 관리 상세조회", description = "사용자 관리 상세를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/userManage/view/{empId}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody ResponseModel<Map<String, Object>> selectUserManageDetail(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam Map<String, Object> paramMap,
        @PathVariable String empId
    ) {
        paramMap.put("empId", empId);
        return new ResponseModel<>(HttpStatus.OK, service.selectUserManageDetail(paramMap));
    }

    /**
     * 사용자 관리 중복아이디 체크
     *
     * @return Map<String, Object>
     *
     * @author : X0125104 <won.shin@partner.sk.com>
     * @since : 2023. 07. 19
     */
    @Operation(summary = "사용자 관리 중복아이디체크", description = "사용자 관리 중복아이디체크를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/userManage/checkId", produces = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody ResponseModel<Map<String, Object>> selectUserManageCheckId(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam Map<String, Object> paramMap
    ) {
        Map<String, Object> resultMap = service.selectUserManageCheckId(paramMap);
        return new ResponseModel<>(HttpStatus.OK, String.valueOf(resultMap.get("message")), resultMap);
    }

    /**
     * 사용자 관리 중복사번 체크
     *
     * @return Map<String, Object>
     *
     * @author : X0125104 <won.shin@partner.sk.com>
     * @since : 2023. 07. 19
     */
    @Operation(summary = "사용자 관리 중복사번체크", description = "사용자 관리 중복사번체크를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/userManage/checkEmpId", produces = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody ResponseModel<Map<String, Object>> selectUserManageCheckEmpId(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam Map<String, Object> paramMap
    ) {
        Map<String, Object> resultMap = service.selectUserManageCheckEmpId(paramMap);
        return new ResponseModel<>(HttpStatus.OK, String.valueOf(resultMap.get("message")), resultMap);
    }

    /**
     * 사용자 관리 직위조회
     *
     * @return List<Map < String, Object>>
     *
     * @author : X0125104 <won.shin@partner.sk.com>
     * @since : 2023. 07. 11
     */
    @Operation(summary = "사용자 관리 직위조회", description = "사용자 관리 직위를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/userManage/jcList", produces = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectUserManageJcList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam Map<String, Object> paramMap
    ) {
        return new ResponseModel<>(HttpStatus.OK, service.selectUserManageJcList(paramMap));
    }

    /**
     * 사용자 관리 직위조회
     *
     * @return List<Map < String, Object>>
     *
     * @author : X0125104 <won.shin@partner.sk.com>
     * @since : 2023. 07. 11
     */
    @Operation(summary = "사용자 관리 직책조회", description = "사용자 관리 직책를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/userManage/jwList", produces = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectUserManageJwList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam Map<String, Object> paramMap
    ) {
        return new ResponseModel<>(HttpStatus.OK, service.selectUserManageJwList(paramMap));
    }

        /**
         * 사용자관리 저장
         *
         * @return
         *
         * @author : X0125104 <won.shin@partner.sk.com>
         * @since : 2023. 07. 24.
         */
        @Operation(summary = "사용자관리 저장", description = "사용자관리를 저장한다.")
        @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
            @ApiResponse(responseCode = "404", description = "Not found") })
        @PostMapping(value = "/userManage/save")
        public @ResponseBody ResponseModel<Integer> updateUserManage(
            @Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody Map<String, Object> paramMap
        ) {

            return new ResponseModel<>(HttpStatus.OK, service.updateUserManage(paramMap));
        }

    /**
     * 하이닉스 사용자 등록 조회
     *
     * @return List<Map < String, Object>>
     *
     * @author : X0125104 <won.shin@partner.sk.com>
     * @since : 2023. 07. 12
     */
    @Operation(summary = "하이닉스 사용자 등록 조회", description = "하이닉스 사용자 등록 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/hynixUser/list", produces = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectHynixUserList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam Map<String, Object> paramMap
    ) {
        Map<String, Object> resultMap = service.selectHynixUserList(paramMap);
        List<Map<String, Object>> resultList = (List<Map<String, Object>>) resultMap.get("list");
        int totalCount = (int) resultMap.get("totalCount");

        return new ResponseModel<>(HttpStatus.OK, resultList, totalCount);
    }

    /**
     * 하이닉스 사용자 등록
     *
     * @return List<Map < String, Object>>
     *
     * @author : X0125104 <won.shin@partner.sk.com>
     * @since : 2023. 07. 12
     */
    @Operation(summary = "하이닉스 사용자 등록", description = " 하이닉스 사용자를 등록한다")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/hynixUser/save")
    public @ResponseBody ResponseModel<Integer> insertHynixUser(@RequestBody Map<String, Object> paramMap) throws EsecurityException {

        return new ResponseModel<>(HttpStatus.OK, service.insertHynixUser(paramMap));
    }

    /**
     * 카드키 결재권한 목록 조회
     *
     * @return List<Map < String, Object>>
     *
     * @author : X0125104 <won.shin@partner.sk.com>
     * @since : 2023. 07. 12
     */
    @Operation(summary = "카드키 결재권한 목록 조회", description = "카드키 결재권한 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/cardKeyUser/list", produces = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectCardKeyUserList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam Map<String, Object> paramMap
    ) {
        Map<String, Object> resultMap = service.selectCardKeyUserList(paramMap);
        List<Map<String, Object>> resultList = (List<Map<String, Object>>) resultMap.get("list");
        int totalCount = (int) resultMap.get("totalCount");

        return new ResponseModel<>(HttpStatus.OK, resultList, totalCount);
    }

}

