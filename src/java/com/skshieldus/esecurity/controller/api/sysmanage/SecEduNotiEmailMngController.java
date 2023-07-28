package com.skshieldus.esecurity.controller.api.sysmanage;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.model.sysmanage.SecEduNotiEmailMngDTO;
import com.skshieldus.esecurity.model.sysmanage.SecEduNotiEmailMngSearchDTO;
import com.skshieldus.esecurity.service.sysmanage.SecEduNotiEmailMngService;
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

@Tag(name = "보안교육안내메일관리 API")
@RestController
@RequestMapping(value = "/api/sysmanage/secedumail", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class SecEduNotiEmailMngController {

    @Autowired
    private Environment environment;

    @Autowired
    private SecEduNotiEmailMngService secEduNotiEmailMngService;

    /**
     * 보안교육안내메일관리 상세 조회
     *
     * @param sessionInfoVO
     * @param secEduNotiEmailMngSearch
     * @return
     *
     * @author : X0121188<jinsoo2.ahn@partner.sk.com>
     * @since : 2021. 12. 16.
     */
    @Operation(summary = "보안교육안내메일관리 상세조회", description = "보안교육안내메일관리 상세를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping(value = "/detail")
    public @ResponseBody ResponseModel<SecEduNotiEmailMngDTO> selectSecEduNotiEmailMngView(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, SecEduNotiEmailMngSearchDTO secEduNotiEmailMngSearch) {
        

        SecEduNotiEmailMngDTO result = secEduNotiEmailMngService.selectSecEduNotiEmailMngView(secEduNotiEmailMngSearch);

        return new ResponseModel<SecEduNotiEmailMngDTO>(HttpStatus.OK, result);
    }

    @Operation(summary = "보안교육안내메일관리 수정", description = "보안교육안내메일관리  수정한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/update", produces = { "application/json" })
    public @ResponseBody
    Boolean updateSecEduNotiEmailMng(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody SecEduNotiEmailMngDTO SecEduNotiEmailMng) {
        

        SecEduNotiEmailMng.setCrtBy(sessionInfoVO.getEmpNo());

        return secEduNotiEmailMngService.updateSecEduNotiEmailMng(SecEduNotiEmailMng);
    }

}