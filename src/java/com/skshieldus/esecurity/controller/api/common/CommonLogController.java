package com.skshieldus.esecurity.controller.api.common;

import com.skshieldus.esecurity.common.model.RequestWrapModel;
import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.CoLogDTO;
import com.skshieldus.esecurity.model.common.CoSyAcLogDTO;
import com.skshieldus.esecurity.model.common.MailLogDTO;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.service.common.CommonLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@Tag(name = "공통 로그 API")
@RestController
@RequestMapping(value = "/api/common/log")
public class CommonLogController {

    @Autowired
    private CommonLogService commonLogService;

    /**
     * 로그 저장 API
     *
     * @return
     *
     * @author : X0121127 <sungbum.oh@partner.sk.com>
     * @since : 2021. 4. 13.
     */
    @Operation(summary = "로그 저장", description = "로그 저장")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseModel<Boolean> insertCommonLog(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @Parameter(description = "로그 정보") @RequestBody CoLogDTO coLogDTO
    ) {
        log.info("sessionInfoVO.getEmpNo() = {}", sessionInfoVO.getEmpNo());
        //		log.info("disable log -----------");

        coLogDTO.setRegId(sessionInfoVO.getEmpNo());
        //		return new ResponseModel<>(HttpStatus.OK, true);
        return new ResponseModel<>(HttpStatus.OK, commonLogService.insertCommonLog(coLogDTO));
    }

    /**
     * 메뉴접속로그 저장 API
     *
     * @return
     *
     * @author : X0121127 <sungbum.oh@partner.sk.com>
     * @since : 2021. 7. 5.
     */
    @Operation(summary = "메뉴접속로그 저장", description = "메뉴접속로그 저장")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/syaclog", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseModel<Boolean> insertSyAcLog(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @Parameter(description = "로그 정보") @RequestBody CoSyAcLogDTO coSyAcLogDTO
    ) {
        log.info("insertSyAcLog >>> empId = {}", sessionInfoVO.getEmpNo());
        log.info("insertSyAcLog >>> acIp = {}", sessionInfoVO.getIp());

        coSyAcLogDTO.setEmpId(sessionInfoVO.getEmpNo());
        coSyAcLogDTO.setAcIp(sessionInfoVO.getIp());
        return new ResponseModel<>(HttpStatus.OK, commonLogService.insertSyAcLog(coSyAcLogDTO));
    }

    /**
     * 메일전송 로그 저장 API
     *
     * @return
     *
     * @author : X0121127 <sungbum.oh@partner.sk.com>
     * @since : 2021. 5. 25.
     */
    @Operation(summary = "메일전송 로그 저장", description = "PDA 로그 저장")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/maillog", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseModel<Boolean> insertMailLog(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @Parameter(description = "메일전송 로그 정보") @RequestBody RequestWrapModel<List<MailLogDTO>> wrapModel
    ) {
        return new ResponseModel<>(HttpStatus.OK, commonLogService.insertMailLog(wrapModel.getParams()));
    }

}