package com.skshieldus.esecurity.controller.api.common;

import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.PartnerDTO;
import com.skshieldus.esecurity.model.common.PartnerSearchDTO;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.service.common.PartnerService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "외부업체 관리 API")
@RestController
@RequestMapping(value = "/api/common/partner", produces = { MediaType.APPLICATION_JSON_VALUE })
public class PartnerController {

    @Autowired
    private Environment environment;

    @Autowired
    private PartnerService partnerService;

    /**
     * 상대처 목록 조회 API
     *
     * @param sessionInfoVO 세션정보 DTO
     * @param partnerSearchDTO 상대처정보 DTO
     * @return ResponseModel<List < PartnerDTO>>
     *
     * @author : X0121126<woogon.kim1@partner.sk.com>
     * @since : 2021. 3. 3.
     */
    @Operation(summary = "상대처 목록 조회", description = "상대처 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"), @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "")
    public @ResponseBody ResponseModel<List<PartnerDTO>> selectPartnerListByGet(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, PartnerSearchDTO partnerSearchDTO) {
        

        // 목록 조회
        List<PartnerDTO> resultList = partnerService.selectPartnerList(partnerSearchDTO);

        // 목록건수 조회
        Integer resultCnt = partnerService.selectPartnerListCnt(partnerSearchDTO);

        return new ResponseModel<>(HttpStatus.OK, resultList, resultCnt);
    }

    /**
     * 상대처 목록 조회 API (POST방식)
     *
     * @param sessionInfoVO 세션정보 DTO
     * @param partnerSearchDTO 상대처정보 DTO
     * @return ResponseModel<List < PartnerDTO>>
     *
     * @author : X0121126<woogon.kim1@partner.sk.com>
     * @since : 2021. 3. 3.
     */
    @Operation(summary = "상대처 목록 조회", description = "상대처 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"), @ApiResponse(responseCode = "404", description = "Not found") })
    @PostMapping(value = "/list")
    public @ResponseBody ResponseModel<List<PartnerDTO>> selectPartnerListByPost(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody PartnerSearchDTO partnerSearchDTO) {
        

        // 목록 조회
        List<PartnerDTO> resultList = partnerService.selectPartnerList(partnerSearchDTO);

        return new ResponseModel<>(HttpStatus.OK, resultList);
    }

    /**
     * 상대처 목록(key, value) 조회 API
     *
     * @param sessionInfoVO 세션정보 DTO
     * @param partnerSearchDTO 상대처정보 DTO
     * @return ResponseModel<List < PartnerDTO>>
     *
     * @author : X0121126<woogon.kim1@partner.sk.com>
     * @since : 2021. 3. 3.
     */
    @Operation(summary = "상대처 목록(Key, Value) 조회", description = "상대처 목록(Key, Value)을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"), @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/key")
    public @ResponseBody ResponseModel<Map<Integer, Object>> selectPartnerListForKey(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, PartnerSearchDTO partnerSearchDTO) {
        

        // 목록 조회
        Map<Integer, Object> resultList = partnerService.selectPartnerListForKey(partnerSearchDTO);

        return new ResponseModel<>(HttpStatus.OK, resultList);
    }

    /**
     * 상대처 상세 조회 API
     *
     * @param sessionInfoVO 세션정보 DTO
     * @param partnerId 상대처ID
     * @return ResponseModel<PartnerDTO>
     *
     * @author : X0121126<woogon.kim1@partner.sk.com>
     * @since : 2021. 3. 3.
     */
    @Operation(summary = "상대처 상세 조회", description = "상대처 상세정보를 조회한다.")
    @GetMapping(value = "/{id}")
    public @ResponseBody ResponseModel<PartnerDTO> selectPartner(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @PathVariable("id") String partnerId) {
        

        // 상세 조회
        PartnerDTO result = partnerService.selectPartner(partnerId);

        return new ResponseModel<>(HttpStatus.OK, result);
    }

    /**
     * 외부업체 정보 등록 API
     *
     * @param sessionInfoVO 세션정보 DTO
     * @param partnerDTO 상대처정보 DTO
     * @return Boolean
     *
     * @author : X0119268<daeho2.park@partner.sk.com>
     * @since : 2021. 4. 5.
     */
    @Operation(summary = "외부업체 정보 등록", description = "외부업체 정보를 등록한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"), @ApiResponse(responseCode = "404", description = "Not found") })
    @PostMapping(value = "/insertPartner")
    public @ResponseBody Boolean insertPartner(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody PartnerDTO partnerDTO) {
        

        return partnerService.insertPartner(partnerDTO);
    }

    /**
     * 외부업체 정보 수정 API
     *
     * @param sessionInfoVO 세션정보 DTO
     * @param partnerDTO 상대처정보 DTO
     * @return Boolean
     *
     * @author : X0119268<daeho2.park@partner.sk.com>
     * @since : 2021. 4. 5.
     */
    @Operation(summary = "외부업체 정보 수정", description = "외부업체 정보를 수정한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"), @ApiResponse(responseCode = "404", description = "Not found") })
    @PostMapping(value = "/updatePartner")
    public @ResponseBody Boolean updatePartner(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody PartnerDTO partnerDTO) {
        

        return partnerService.updatePartner(partnerDTO);
    }

    /**
     * 외부업체 정보 삭제 API
     *
     * @param sessionInfoVO 세션정보 DTO
     * @param partnerId 외부업체ID
     * @return Boolean
     *
     * @author : X0119268<daeho2.park@partner.sk.com>
     * @since : 2021. 4. 5.
     */
    @Operation(summary = "외부업체 정보 삭제", description = "외부업체 정보를 수정한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"), @ApiResponse(responseCode = "404", description = "Not found") })
    @PostMapping(value = "/deletePartner")
    public @ResponseBody Boolean deletePartner(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody String partnerId) {
        

        return partnerService.deletePartner(partnerId);
    }

    /**
     * 외부업체 사업자 번호 중복 여부 check
     *
     * @param sessionInfoVO 세션정보 DTO
     * @param partnerDTO 중복 여부 check 사업자 번호
     * @return String
     *
     * @author : X0119268<daeho2.park@partner.sk.com>
     * @since : 2021. 4. 5.
     */
    @Operation(summary = "외부업체 사업자 번호 중복 여부 check", description = "외부업체 사업자 번호 중복 여부 check 한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"), @ApiResponse(responseCode = "404", description = "Not found") })
    @PostMapping(value = "/partnerLegChk")
    public @ResponseBody String partnerLegChk(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody PartnerDTO partnerDTO) {
        

        return partnerService.partnerLegChk(partnerDTO);
    }

}