package com.skshieldus.esecurity.controller.api.sysmanage;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.model.sysmanage.ApprovalBoxDTO;
import com.skshieldus.esecurity.model.sysmanage.ApprovalBoxSearchDTO;
import com.skshieldus.esecurity.service.sysmanage.ApprovalBoxService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@Tag(name = "결재함 API")
@RestController
@RequestMapping(value = "/api/sysmanage/approvalbox", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class ApprovalBoxController {

    @Autowired
    private Environment environment;

    @Autowired
    private ApprovalBoxService approvalBoxService;

    /**
     * 결재함 조회
     *
     * @param sessionInfoVO
     * @param approvalBoxSearch
     * @return
     *
     * @author : X0121188<jinsoo2.ahn@partner.sk.com>
     * @since : 2021. 12. 22.
     */
    @Operation(summary = "결재함 조회", description = "결재함을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping(value = "/approval")
    public @ResponseBody ResponseModel<List<ApprovalBoxDTO>> selectApprovalBoxApprovalList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, ApprovalBoxSearchDTO approvalBoxSearch) {
        

        List<ApprovalBoxDTO> result = approvalBoxService.selectApprovalBoxApprovalList(approvalBoxSearch);

        return new ResponseModel<List<ApprovalBoxDTO>>(HttpStatus.OK, result);
    }

    /**
     * 진행함 조회
     *
     * @param sessionInfoVO
     * @param approvalBoxSearch
     * @return
     *
     * @author : X0121188<jinsoo2.ahn@partner.sk.com>
     * @since : 2021. 12. 22.
     */
    @Operation(summary = "진행함 조회", description = "진행함을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping(value = "/request")
    public @ResponseBody ResponseModel<List<ApprovalBoxDTO>> selectApprovalBoxRequestList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, ApprovalBoxSearchDTO approvalBoxSearch) {
        

        List<ApprovalBoxDTO> result = approvalBoxService.selectApprovalBoxRequestList(approvalBoxSearch);

        return new ResponseModel<List<ApprovalBoxDTO>>(HttpStatus.OK, result);
    }

    /**
     * 결재함 조회(발급실)
     *
     * @param sessionInfoVO
     * @param approvalBoxSearch
     * @return
     *
     * @author : X0121188<jinsoo2.ahn@partner.sk.com>
     * @since : 2021. 12. 22.
     */
    @Operation(summary = "결재함 조회(발급실)", description = "결재함을 조회한다.(발급실)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping(value = "/idcard")
    public @ResponseBody ResponseModel<List<ApprovalBoxDTO>> selectApprovalBoxIdCardList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, ApprovalBoxSearchDTO approvalBoxSearch) {
        

        List<ApprovalBoxDTO> result = approvalBoxService.selectApprovalBoxIdCardList(approvalBoxSearch);

        return new ResponseModel<List<ApprovalBoxDTO>>(HttpStatus.OK, result);
    }

}