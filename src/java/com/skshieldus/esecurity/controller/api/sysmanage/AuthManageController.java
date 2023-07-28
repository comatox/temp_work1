package com.skshieldus.esecurity.controller.api.sysmanage;

import com.skshieldus.esecurity.common.model.ListDTO;
import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.model.sysmanage.MenuManageDTO;
import com.skshieldus.esecurity.service.sysmanage.AuthManageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Tag(name = "권한관리 조회 API")
@Controller
@RequestMapping(value = "/api/sysmanage/authManage", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class AuthManageController {

    @Autowired
    private Environment environment;

    @Autowired
    private AuthManageService service;

    /**
     * 권한 관리 목록조회
     *
     * @return
     *
     * @author : X0125104 <won.shin@partner.sk.com>
     * @since : 2022. 02. 11
     */
    @Operation(summary = "권한 관리 목록조회", description = "권한 관리 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/authManageList", produces = { "application/json" })
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectAuthManageList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestParam Map<String, Object> paramMap
    ) {
        ListDTO<Map<String, Object>> listDTO = service.selectAuthManageList(paramMap);
        return new ResponseModel<>(HttpStatus.OK, listDTO.getList(), listDTO.getTotalCount());
    }

    /**
     * 권한관리 저장
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2022. 02. 16.
     */
    @Operation(summary = "환경설정 > 권한 관리 > 권한관리 저장", description = "환경설정 > 권한 관리 > 권한관리 저장")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully merged data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @PostMapping(value = "/authManage/upsertAuthManage", produces = { "application/json" })
    public @ResponseBody boolean upsertAuthManage(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody Map<String, Object> paramMap
    ) {
        
        return service.upsertAuthManage(paramMap);
    }

    /**
     * 메뉴 관리 목록조회
     *
     * @return List<MenuManageDTO>
     *
     * @author : X0125104 <won.shin@partner.sk.com>
     * @since : 2023. 07. 06
     */
    @Operation(summary = "메뉴 관리 목록조회", description = "메뉴 관리 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/menuManage/list", produces = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody ResponseModel<Map<String, Object>> selectMenuManageList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam Map<String, Object> paramMap
    ) {

        Map<String, Object> resultMap = service.selectMenuManageList(paramMap);

        return new ResponseModel<>(HttpStatus.OK, resultMap, (int) resultMap.get("totalCount"));
    }

    /**
     * 메뉴 관리 상세조회
     *
     * @return MenuManageDTO
     *
     * @author : X0125104 <won.shin@partner.sk.com>
     * @since : 2023. 07. 06
     */
    @Operation(summary = "메뉴 관리 상세조회", description = "메뉴 관리 상세를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/menuManage/detail", produces = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody ResponseModel<MenuManageDTO> selectMenuManageDetail(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam Map<String, Object> paramMap
    ) {

        return new ResponseModel<>(HttpStatus.OK, service.selectMenuManageDetail(paramMap));
    }

    /**
     * 메뉴관리 신규 메뉴 추가 정렬순서 및 메뉴ID 조회 API
     *
     * @return
     *
     * @author : X0125104 <won.shin@partner.sk.com>
     * @since : 2023. 07. 11.
     */
    @Operation(summary = "메뉴관리 신규 메뉴 추가 정렬순서 및 메뉴ID 조회", description = "메뉴관리 신규 메뉴 추가 정렬순서 및 메뉴ID 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @PostMapping(value = "/menuManage/newCode")
    public @ResponseBody ResponseModel<Map<String, Object>> selectMenuManageNewCode(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody Map<String, Object> paramMap
    ) {

        return new ResponseModel<>(HttpStatus.OK, service.selectMenuManageNewCode(paramMap));
    }

    /**
     * 메뉴관리 저장
     *
     * @return
     *
     * @author : X0125104 <won.shin@partner.sk.com>
     * @since : 2023. 07. 11.
     */
    @Operation(summary = "메뉴관리 저장", description = "메뉴관리를 저장한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @PostMapping(value = "/menuManage/save")
    public @ResponseBody ResponseModel<Integer> updateMenuManage(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody Map<String, Object> paramMap
    ) {

        return new ResponseModel<>(HttpStatus.OK, service.updateMenuManage(paramMap));
    }

    /**
     * 권한/메뉴 관리 목록조회
     *
     * @author : HoonLee
     * @since : 2023. 07. 14
     */
    @Operation(summary = "권한/메뉴 관리 권한목록조회", description = "권한/메뉴 관리 권한목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/authList", produces = { "application/json" })
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectAuthList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap
    ) {
        return new ResponseModel<>(HttpStatus.OK, service.selectAuthList(paramMap));
    }

    /**
     * 권한/메뉴 권한메뉴목록조회 목록조회
     *
     * @return
     *
     * @author : HoonLee
     * @since : 2023. 07. 14
     */
    @Operation(summary = "권한/메뉴 관리 권한메뉴 목록조회", description = "권한/메뉴 관리 권한메뉴 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/authMenuList", produces = { "application/json" })
    public @ResponseBody  ResponseModel<Map<String, Object>> selectAuthMenuList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestParam Map<String, Object> paramMap
    ) {

        return new ResponseModel<>(HttpStatus.OK, service.selectAuthMenuList(paramMap));
    }

    /**
     * 권한/메뉴 관리 저장
     *
     * @param paramMap
     * @return
     *
     * @author : HoonLee
     * @since : 2023. 07. 14
     */
    @Operation(summary = "권한/메뉴 관리 저장", description = "권한/메뉴 관리를 저장한다")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully merged data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @PostMapping(value = "/saveAuthMenuManage", produces = { "application/json" })
    public @ResponseBody boolean saveAuthMenuManage(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody Map<String, Object> paramMap
    ) {
        return service.saveAuthMenuManage(sessionInfoVO, paramMap);
    }


    /**
     * 사용자별 권한 확인
     *
     * @param paramMap
     * @return
     *
     * @author : HoonLee
     * @since : 2023. 07. 14
     */
    @Operation(summary = "사용자별 권한 확인", description = "사용자별 권한 확인")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/userAuthCheckCnt", produces = { "application/json" })
    public @ResponseBody ResponseModel<Integer> selectUserAuthCheckCnt(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestParam Map<String, Object> paramMap
    ) {
        return new ResponseModel<>(HttpStatus.OK, service.selectUserAuthCheckCnt(paramMap));
    }

    /**
     * 사용자/권한 관리 목록조회
     *
     * @param paramMap
     * @return
     *
     * @author : HoonLee
     * @since : 2023. 07. 14
     */
    @Operation(summary = "사용자권한 관리 목록조회", description = "사용자권한 관리 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/userAuthList", produces = { "application/json" })
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectUserAuthList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestParam Map<String, Object> paramMap
    ) {
        ListDTO<Map<String, Object>> listDTO = service.selectUserAuthList(paramMap);
        return new ResponseModel<>(HttpStatus.OK, listDTO.getList(), listDTO.getTotalCount());
    }

    /**
     * 사용자/권한 관리 저장
     *
     * @param paramMap
     * @return
     *
     * @author : HoonLee
     * @since : 2023. 07. 14
     */
    @Operation(summary = "사용자/권한 관리 저장", description = "사용자/권한 관리를 저장한다")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully merged data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @PostMapping(value = "/saveUserAuthManage", produces = { "application/json" })
    public @ResponseBody boolean saveUserAuthManage(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody Map<String, Object> paramMap
    ) {
        return service.saveUserAuthManage(sessionInfoVO, paramMap);
    }

    /**
     * 사용자/권한 관리 저장
     *
     * @param paramMap
     * @return
     *
     * @author : HoonLee
     * @since : 2023. 07. 14
     */
    @Operation(summary = "사용자 권한 관리 부여 권한 목록 조회", description = "사용자 권한 관리 부여 권한 목록 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/selectUserUseAuthList", produces = { "application/json" })
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectUserUseAuthList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestParam Map<String, Object> paramMap
    ) {
        ListDTO<Map<String, Object>> listDTO = service.selectUserUseAuthList(paramMap);
        return new ResponseModel<>(HttpStatus.OK, listDTO.getList(), listDTO.getTotalCount());
    }


}