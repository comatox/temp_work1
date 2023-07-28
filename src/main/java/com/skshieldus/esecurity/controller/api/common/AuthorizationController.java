package com.skshieldus.esecurity.controller.api.common;

import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.service.common.AuthorizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 인가된 메뉴 확인
 *
 * @author : X0115378 <jaehoon5.kim@partner.sk.com>
 * @since : 2021. 04. 16.
 */
@Tag(name = "인가 API")
@RestController
@RequestMapping(value = "/api/common/auth", produces = { MediaType.APPLICATION_JSON_VALUE })
public class AuthorizationController {

    @Autowired
    private AuthorizationService authorizationService;

    /**
     * 로그인한 사용자에게 인가된 메뉴ID 목록 조회 API
     *
     * @param sessionInfo 세션정보 DTO
     * @return ResponseModel<List < String>>
     *
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2021. 04. 16.
     */
    @Operation(summary = "로그인한 사용자에게 인가된 메뉴ID 목록 조회", description = "로그인한 사용자에게 인가된 메뉴ID 목록 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping(value = "/authorizedMenu")
    public @ResponseBody
    ResponseModel<List<String>> selectAuthorizedMenu(@RequestHeader(value = "META", required = false) String headerMeta, @Parameter(hidden = true) SessionInfoVO sessionInfo) {
        return new ResponseModel<>(HttpStatus.OK, authorizationService.selectAuthorizedMenu(headerMeta, sessionInfo));
    }

    /**
     * 로그인한 사용자에게 인가된 메뉴인지 확인하는 API
     *
     * @param sessionInfo 세션정보 DTO
     * @param menuId 메뉴ID
     * @return ResponseModel<Boolean>
     *
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2021. 04. 16.
     */
    @Operation(summary = "로그인한 사용자에게 인가된 메뉴인지 확인", description = "로그인한 사용자에게 인가된 메뉴인지 확인한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping(value = "/{menuId}")
    public @ResponseBody ResponseModel<Boolean> selectMenuAuthCheck(@RequestHeader(value = "META", required = false) String headerMeta, @Parameter(hidden = true) SessionInfoVO sessionInfo, @PathVariable("menuId") String menuId) {
        return new ResponseModel<>(HttpStatus.OK, authorizationService.selectMenuAuthCheck(headerMeta, sessionInfo, menuId));
    }

}
