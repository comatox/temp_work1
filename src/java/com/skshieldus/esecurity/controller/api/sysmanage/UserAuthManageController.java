package com.skshieldus.esecurity.controller.api.sysmanage;

import com.skshieldus.esecurity.common.model.ListDTO;
import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.service.sysmanage.UserAuthManageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "사용자/권한 관리 조회 API")
@Controller
@RequestMapping(value = "/api/sysmanage/userAuth", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class UserAuthManageController {

    @Autowired
    private Environment environment;

    @Autowired
    private UserAuthManageService userAuthManageService;

    /**
     * 사용자/권한 관리 목록조회
     *
     * @return
     *
     * @author : X0125104 <won.shin@partner.sk.com>
     * @since : 2022. 02. 25
     */
    @Operation(summary = "사용자/권한 관리 목록조회", description = "사용자/권한 관리 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/userAuthList", produces = { "application/json" })
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectUserAuthList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestParam Map<String, Object> paramMap
    ) {
        ListDTO<Map<String, Object>> listDTO = userAuthManageService.selectUserAuthList(paramMap);
        return new ResponseModel<>(HttpStatus.OK, listDTO.getList(), listDTO.getTotalCount());
    }

    /**
     * 사용자/권한 관리 권한 목록조회
     *
     * @return
     *
     * @author : X0125104 <won.shin@partner.sk.com>
     * @since : 2022. 02. 25
     */
    @Operation(summary = "사용자/권한 관리 권한 목록조회", description = "사용자/권한 관리 권한 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/authList", produces = { "application/json" })
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectUserAuthManageEmpAuthList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestParam Map<String, Object> paramMap
    ) {
        ListDTO<Map<String, Object>> listDTO = userAuthManageService.selectUserAuthManageEmpAuthList(paramMap);
        return new ResponseModel<>(HttpStatus.OK, listDTO.getList(), listDTO.getTotalCount());
    }

    /**
     * 사용자/권한 관리 저장
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2022. 02. 25.
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
        return userAuthManageService.saveUserAuthManage(sessionInfoVO, paramMap);
    }


}