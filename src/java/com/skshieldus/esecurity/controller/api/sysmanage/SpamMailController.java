package com.skshieldus.esecurity.controller.api.sysmanage;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.model.sysmanage.SpamMailDTO;
import com.skshieldus.esecurity.model.sysmanage.SpamMailSearchDTO;
import com.skshieldus.esecurity.service.sysmanage.SpamMailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "스팸메일 조회 API")
@RestController
@RequestMapping(value = "/api/sysmanage/spamMail", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class SpamMailController {

    @Autowired
    private Environment environment;

    @Autowired
    private SpamMailService spamMailService;

    /**
     * 스팸메일 목록 조회
     *
     * @param sessionInfoVO
     * @param spamMailSearch
     * @return
     *
     * @author : Your ID <your email address>
     * @since : 2021. 12. 9.
     */
    @Operation(summary = "스팸메일 목록 조회", description = "스팸메일 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping(value = "", produces = { "application/json" })
    public @ResponseBody ResponseModel<List<SpamMailDTO>> selectSpamMailList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, SpamMailSearchDTO spamMailSearch) {
        

        return new ResponseModel<>(HttpStatus.OK, spamMailService.selectSpamMailList(spamMailSearch));
    }

    @Operation(summary = "스팸메일 상세조회", description = "스팸메일 상세를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping(value = "/detail")
    public @ResponseBody ResponseModel<SpamMailDTO> selectSpamMailView(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, SpamMailSearchDTO spamMailSearch) {
        

        SpamMailDTO result = spamMailService.selectSpamMailView(spamMailSearch);

        return new ResponseModel<SpamMailDTO>(HttpStatus.OK, result);
    }

    @Operation(summary = "스팸메일 수정", description = "스팸메일  수정한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/update", produces = { "application/json" })
    public @ResponseBody Boolean updateSpamMail(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody SpamMailDTO SpamMail) {
        

        return spamMailService.updateSpamMail(SpamMail);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @Operation(summary = "스팸메일 입력", description = "스팸메일 상세사항을 입력한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "201", description = "Created"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "", produces = { "application/json" })
    public @ResponseBody Boolean insertSpamMail(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody SpamMailDTO SpamMail) {
        

        SpamMail.setCrtBy(sessionInfoVO.getEmpNo());
        SpamMail.setAcIp(sessionInfoVO.getIp());

        return spamMailService.insertSpamMail(SpamMail);
    }

    @Operation(summary = "스팸메일 사용자 조회", description = "스팸메일 사용자를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping(value = "/user")
    public @ResponseBody ResponseModel<SpamMailDTO> selectSpamMailTop(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, SpamMailSearchDTO spamMailSearch) {
        

        SpamMailDTO result = spamMailService.selectSpamMailUserInfo(spamMailSearch);

        return new ResponseModel<SpamMailDTO>(HttpStatus.OK, result);
    }

    @Operation(summary = "스팸메일 글  삭제", description = "스팸메일 글을 삭제한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "204", description = "No Content"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PostMapping(value = "/delete", produces = { "application/json" })
    public @ResponseBody Boolean deleteSpamMail(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody SpamMailDTO SpamMail) {
        

        SpamMail.setCrtBy(sessionInfoVO.getEmpNo());
        SpamMail.setAcIp(sessionInfoVO.getIp());

        return spamMailService.deleteSpamMail(SpamMail);
    }

}