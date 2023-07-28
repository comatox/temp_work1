package com.skshieldus.esecurity.controller.api.secrtactvy;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ListDTO;
import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.service.secrtactvy.SecurityAdminViolationService;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Tag(name = "보안위규자 입력")
@Controller
@RequestMapping(value = "/api/secrtactvy/securityAdminViolation", produces = { "application/json" })
public class SecurityAdminViolationController {

    @Autowired
    private Environment environment;

    @Autowired
    private SecurityAdminViolationService service;

    /**
     * 구성원위규자 입력 > 구성원조회(사번검색)
     *
     * @param sessionInfoVO
     * @param paramMap
     * @param scDocDistId
     * @return
     *
     * @throws EsecurityException
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 16.
     */
    @Operation(summary = "구성원위규자 입력 > 구성원 조회(사번검색)", description = "구성원위규자 입력 > 구성원(사번검색)을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/coViolation/coEmp")
    public @ResponseBody ResponseModel<Map<String, Object>> selectCoEmpInfo(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, service.selectCoEmpInfo(paramMap));
    }

    /**
     * 구성원위규자 입력 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 16.
     */
    @Operation(summary = "구성원위규자 입력", description = "구성원위규자를 입력한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/coViolation/save")
    public @ResponseBody ResponseModel<Boolean> coViolationSave(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        return new ResponseModel<Boolean>(HttpStatus.OK, service.coViolationSave(paramMap));
    }

    /**
     * 구성원위규자 입력(ssm) API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 16.
     * 위규내용(OFEND_DETAIL_GBN == "C0531010") && 위규상세내용(OFEND_SUB_GBN=="C0591208") && 모바일 포렌직(MOBILE_FORENSICS_GBN=="C0601001") 인 경우에만
     */
    @Operation(summary = "구성원위규자(ssm) 입력", description = "해당조건에 부합하는 구성원위규자(ssm)를 입력한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/coViolation/ssmSave")
    public @ResponseBody ResponseModel<Boolean> coViolationSsmSave(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        
        paramMap.put("acIp", sessionInfoVO.getIp());

        return new ResponseModel<Boolean>(HttpStatus.OK, service.coViolationSsmSave(paramMap));
    }

    /**
     * 구성원위규자 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 19.
     */
    @Operation(summary = "구성원위규자 목록 조회", description = "구성원위규자 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/coViolation/list")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectCoViolationList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        ListDTO<Map<String, Object>> listDTO = service.selectCoViolationList(paramMap);
        return new ResponseModel<>(HttpStatus.OK, listDTO.getList(), listDTO.getTotalCount());
    }

    /**
     * 구성원위규자 상세 > 동일 위규 이력 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 19.
     */
    @Operation(summary = "구성원위규자 상세 > 동일 위규 이력 조회", description = "구성원위규자 상세 > 동일 위규 이력을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/coViolation/history/same")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectCoViolationSameHistoryList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        List<Map<String, Object>> resultList = service.selectCoViolationSameHistoryList(paramMap);
        return new ResponseModel<>(HttpStatus.OK, resultList, resultList.size());
    }

    /**
     * 구성원위규자 상세 > 조치현황 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 22.
     */
    @Operation(summary = "구성원 위규자 상세 조치현황 조회", description = "구성원 위규자 상세 조치현황을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/coViolation/history/act")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectCoViolationActHistoryList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        List<Map<String, Object>> resultList = service.selectCoViolationActHistoryList(paramMap);

        return new ResponseModel<>(HttpStatus.OK, resultList, resultList.size());
    }

    /**
     * 구성원위규자 상세 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 22.
     */
    @Operation(summary = "구성원 위규자 상세 조회", description = "구성원 위규자를 상세 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/coViolation/detail")
    public @ResponseBody ResponseModel<Map<String, Object>> selectCoViolationDetail(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {

        return new ResponseModel<>(HttpStatus.OK, service.selectCoViolationDetail(paramMap));
    }

    /**
     * 구성원위규자 조치실행
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 22.
     */
    @Operation(summary = "구성원 위규자 조치실행", description = "구성원 위규자 조치실행.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/coViolation/act")
    public @ResponseBody ResponseModel<Boolean> coEmpViolation_Act(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {

        paramMap.put("acIp", sessionInfoVO.getIp());

        return new ResponseModel<Boolean>(HttpStatus.OK, service.coEmpViolation_Act(paramMap));
    }

    /**
     * 구성원위규자 조치실행
     * @author : HoonLee
     */
    @Operation(summary = "구성원 위규자 조치실행", description = "구성원 위규자 조치실행.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/coViolation/actDo")
    public @ResponseBody ResponseModel<Boolean> coEmpViolationActDo(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        return new ResponseModel<Boolean>(HttpStatus.OK, service.coEmpViolationActDo(paramMap));
    }

    /**
     * 2차 안내메일 발송
     * @author : HoonLee
     */
    @Operation(summary = "구성원 위규자 2차 메일 발송", description = "구성원 위규자 2차 메일 발송")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/coViolation/secondaryMail")
    public @ResponseBody ResponseModel<Boolean> coViolationSecondaryMail(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        return new ResponseModel<Boolean>(HttpStatus.OK, service.coViolationSecondaryMail(paramMap));
    }

    /**
     * 구성원위규자 조치실행
     *
     * @param sessionInfoVO C0601099 보안기획 인계(정밀검색) 인경우에만
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 22.
     */
    @Operation(summary = "구성원 위규자 조치실행(Mobile)", description = "구성원 위규자 조치실행(Mobile).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/coViolation/actMobile")
    public @ResponseBody ResponseModel<Boolean> coEmpViolation_Mobile_Act(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        
        paramMap.put("acIp", sessionInfoVO.getIp());

        return new ResponseModel<Boolean>(HttpStatus.OK, service.coEmpViolation_Mobile_Act(paramMap));
    }

    /**
     * 구성원 위규자 조치실행 통합결재 후처리
     *
     * @author : "I0101663"
     * @since : 2022. 02. 15.
     * @param sessionInfoVO
     * @param requestDTO
     * @return
     * @throws Exception
     */
    //	@Operation(summary = "구성원 위규자 조치실행 통합결재 후처리", description = "구성원 위규자 조치실행 통합결재 후처리를 수행한다.")
    //	@ApiResponses(value = {
    //			@ApiResponse(responseCode = "200", description="Successfully search data contents"),
    //			@ApiResponse(responseCode = "404", description="Not found")
    //	})
    //	@PostMapping(value = "/approval/postprocessCo")
    //	public @ResponseBody ResponseModel<EaiResponseDTO> processCoEmpViolationActApproval(
    //			@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody EaiRequestDTO requestDTO)
    //			throws Exception {
    //		return new ResponseModel<>(HttpStatus.OK, securityAdminViolationApprovalPostProcessCo.postProcess(requestDTO));
    //	}

    /**
     * 외부인 위규자 입력 > 회원정보 검색
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 23.
     */
    @Operation(summary = "외부인 위규자 입력 > 회원정보 검색", description = "외부인 위규자 입력 > 회원정보 검색을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/ioViolation/ioEmp")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectIoEmpInfo(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, service.selectIoEmpInfo(paramMap), service.selectIoEmpInfoCnt(paramMap));
    }

    /**
     * 외부인 위규자 입력 > 접견자 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 23.
     */
    @Operation(summary = "외부인 위규자 입력 > 접견자 목록 조회", description = "외부인 위규자 입력 > 접견자 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/ioViolation/interviewer")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectIoViolationInterviewerList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        return new ResponseModel<>(HttpStatus.OK, service.selectIoViolationInterviewerList(paramMap));
    }

    /**
     * 외부인위규자 입력 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 24.
     */
    @Operation(summary = "외부인위규자 입력", description = "외부인위규자를 입력한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/ioViolation/save")
    public @ResponseBody Boolean ioViolationSave(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        return service.ioViolationSave(paramMap);
    }

    /**
     * 외부인위규자 입력 API - Kiosk 출문 처리 시 사용
     *
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 2. 22.
     */
    @Operation(summary = "외부인위규자 입력 - Kiosk", hidden = true)
    @PostMapping(value = "/ioViolation/save/kiosk")
    public @ResponseBody ResponseModel<Boolean> ioViolationSave(@RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        return new ResponseModel<Boolean>(HttpStatus.OK, service.ioViolationSave(paramMap));
    }

    /**
     * 외부인위규자 입력(ssm) API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 24.
     * 위규내용(OFEND_DETAIL_GBN == "C0581009") && 위규상세내용(OFEND_SUB_GBN=="C0591213") && 모바일 포렌직(MOBILE_FORENSICS_GBN=="C0601001") 인 경우에만
     */
    @Operation(summary = "외부인위규자(ssm) 입력", description = "해당조건에 부합하는 외부인위규자(ssm)를 입력한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/ioViolation/ssmSave")
    public @ResponseBody ResponseModel<Boolean> ioViolationSsmSave(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        
        paramMap.put("acIp", sessionInfoVO.getIp());

        return new ResponseModel<Boolean>(HttpStatus.OK, service.ioViolationSsmSave(paramMap));
    }

    /**
     * 외부인위규자 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 25.
     */
    @Operation(summary = "외부인위규자 목록 조회", description = "외부인위규자 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/ioViolation/list")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectIoViolationList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        ListDTO<Map<String, Object>> listDTO = service.selectIoViolationList(paramMap);
        return new ResponseModel<>(HttpStatus.OK, listDTO.getList(), listDTO.getTotalCount());
    }

    /**
     * 외부인위규자 상세 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 26.
     */
    @Operation(summary = "외부인위규자 상세 조회", description = "외부인위규자를 상세 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/ioViolation/detail")
    public @ResponseBody ResponseModel<Map<String, Object>> selectIoViolationDetail(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        return new ResponseModel<>(HttpStatus.OK, service.selectIoViolationDetail(paramMap));
    }

    /**
     * 외부인 위규자 상세 > 접견자 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 26.
     */
    @Operation(summary = "외부인 위규자 상세 > 접견자 목록 조회", description = "외부인 위규자 상세 > 접견자 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/ioViolation/detail/interviewer")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectIoViolationDetailInterviewerList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        return new ResponseModel<>(HttpStatus.OK, service.selectIoViolationDetailInterviewerList(paramMap));
    }

    /**
     * 외부인 위규자 상세 > 동일 위규 이력 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 19.
     */
    @Operation(summary = "외부인 위규자 상세 > 동일 위규 이력 조회", description = "외부인 위규자 상세 > 동일 위규 이력을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/ioViolation/history/same")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectIoViolationSameHistoryList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        List<Map<String, Object>> resultList = service.selectIoViolationSameHistoryList(paramMap);
        return new ResponseModel<>(HttpStatus.OK, resultList, resultList.size());
    }

    /**
     * 외부인 위규자 상세 > 조치현황 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 22.
     */
    @Operation(summary = "외부인 위규자 상세 조치현황 조회", description = "외부인 위규자 상세 조치현황을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/ioViolation/history/act")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectIoViolationActHistoryList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        List<Map<String, Object>> resultList = service.selectIoViolationActHistoryList(paramMap);

        return new ResponseModel<>(HttpStatus.OK, resultList, resultList.size());
    }

    /**
     * 외부인 위규자 조치실행
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 26.
     */
    @Operation(summary = "외부인위규자 조치실행", description = "외부인위규자 조치실행.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/ioViolation/act")
    public @ResponseBody ResponseModel<Boolean> ioEmpViolation_Act(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        return new ResponseModel<Boolean>(HttpStatus.OK, service.ioEmpViolation_Act(paramMap));
    }

    /**
     * 외부인 위규자 조치실행
     *
     * @author hoon lee
     */
    @Operation(summary = "외부인위규자 조치실행", description = "외부인위규자 조치실행.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/ioViolation/actDo")
    public @ResponseBody ResponseModel<Boolean> ioEmpViolationActDo(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        return new ResponseModel<Boolean>(HttpStatus.OK, service.ioEmpViolationActDo(paramMap));
    }

    /**
     * 외부인 위규자 조치실행 통합결재 후처리
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2022. 01. 13.
     * @param sessionInfoVO
     * @param requestDTO
     * @return
     * @throws Exception
     */
    //	@Operation(summary = "외부인 위규자 조치실행 통합결재 후처리", description = "외부인 위규자 조치실행 통합결재 후처리를 수행한다.")
    //	@ApiResponses(value = {
    //			@ApiResponse(responseCode = "200", description="Successfully search data contents"),
    //			@ApiResponse(responseCode = "404", description="Not found")
    //	})
    //	@PostMapping(value = "/approval/postprocess")
    //	public @ResponseBody ResponseModel<EaiResponseDTO> processIoEmpViolationActApproval(
    //			@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody EaiRequestDTO requestDTO)
    //			throws Exception {
    //		return new ResponseModel<>(HttpStatus.OK, securityAdminViolationApprovalPostProcess.postProcess(requestDTO));
    //	}

    /**
     * 외부인 위규자 조치실행
     *
     * @param sessionInfoVO C0601099 보안기획 인계(정밀검색) 인경우에만
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 26.
     */
    @Operation(summary = "외부인 위규자 조치실행(Mobile)", description = "외부인 위규자 조치실행(Mobile).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/ioViolation/actMobile")
    public @ResponseBody ResponseModel<Boolean> ioEmpViolation_Mobile_Act(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        
        paramMap.put("acIp", sessionInfoVO.getIp());

        return new ResponseModel<Boolean>(HttpStatus.OK, service.ioEmpViolation_Mobile_Act(paramMap));
    }

    /**
     * 구성원 위규자 조회(보안요원) 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 02.
     */
    @Operation(summary = "구성원 위규자 조회(보안요원) 목록 조회", description = "구성원 위규자 조회(보안요원) 목록 조회을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/coViolation/sec/list")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectCoViolationSecList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {

        ListDTO<Map<String, Object>> listDTO = service.selectCoViolationSecList(paramMap);
        return new ResponseModel<>(HttpStatus.OK, listDTO.getList(), listDTO.getTotalCount());
    }

    /**
     * 구성원 위규자 조회(보안요원) 삭제
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 02.
     */
    @Operation(summary = "구성원 위규자 조회(보안요원) 삭제", description = "구성원 위규자 조회(보안요원) 내역을 삭제한다")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/coViolation/sec/delete")
    public @ResponseBody ResponseModel<Boolean> coEmpViolationSecDelete(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        return new ResponseModel<Boolean>(HttpStatus.OK, service.coEmpViolationSecDelete(paramMap));
    }

    /**
     * 외부인 위규자 조회(보안요원) 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 02.
     */
    @Operation(summary = "외부인 위규자 조회(보안요원) 목록 조회", description = "외부인 위규자 조회(보안요원) 목록 조회을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/ioViolation/sec/list")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectIoViolationSecList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {

        ListDTO<Map<String, Object>> listDTO = service.selectIoViolationSecList(paramMap);
        return new ResponseModel<>(HttpStatus.OK, listDTO.getList(), listDTO.getTotalCount());
    }

    /**
     * 외부인 위규자 조회(보안요원) 삭제
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 02.
     */
    @Operation(summary = "외부인 위규자 조회(보안요원) 삭제", description = "외부인 위규자 조회(보안요원)내역을 삭제한다")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/ioViolation/sec/delete")
    public @ResponseBody ResponseModel<Boolean> ioEmpViolationSecDelete(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        
        return new ResponseModel<Boolean>(HttpStatus.OK, service.ioEmpViolationSecDelete(paramMap));
    }

    /**
     * 외부인위규자 자동징구 가능여부 조회 (Y/N)
     * 위규산정기간 전년도 10월1일~당해년도 9월30일까지 자동징구가 3차례 이상이면 자동징구 불가
     * 자동징구가능 = "Y"
     * 자동징구불가 = "N"
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 17.
     */
    @Operation(summary = "외부인위규자 자동징구 가능여부 조회", description = "외부인위규자 자동징구 가능여부를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/ioViolation/autoActYn")
    public @ResponseBody ResponseModel<String> selectAutoActYn(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.selectAutoActYn(paramMap));
    }

    /**
     * 외부인위규자 결재목록 조회
     *
     * @param sessionInfoVO
     * @param scIoDocNo
     * @return
     *
     * @throws EsecurityException
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 1. 26.
     */
    @Operation(summary = "외부인위규자 결재 목록 조회", description = "외부인위규자 결재 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/ioViolation/appr/{no}")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectIoViolationApprList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @PathVariable("no") Integer scIoDocNo) throws EsecurityException {
        

        List<Map<String, Object>> resultList = service.selectIoViolationApprList(scIoDocNo);

        return new ResponseModel<>(HttpStatus.OK, resultList);
    }


}

