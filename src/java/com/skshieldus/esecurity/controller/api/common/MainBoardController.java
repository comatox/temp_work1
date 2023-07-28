package com.skshieldus.esecurity.controller.api.common;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.model.sysmanage.BoardDTO;
import com.skshieldus.esecurity.service.common.MainBoardService;
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

@Tag(name = "게시판 조회 API")
@RestController
@RequestMapping(value = "/api/common/board", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class MainBoardController {

    @Autowired
    private Environment environment;

    @Autowired
    private MainBoardService mainBoardService;

    /**
     * 메인 게시판 목록 조회(게시판구분 별)
     *
     * @return
     *
     * @author : X0121127 <sungbum.oh@partner.sk.com>
     * @since : 2021. 8. 20
     */
    @Operation(summary = "메인 게시판 목록 조회", description = "메인 게시판 목록을 조회한다. (TOP5)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping(value = "/main/{boardGbn}", produces = { "application/json" })
    public @ResponseBody ResponseModel<List<BoardDTO>> selectMainBoardListByBoardGbn(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @PathVariable String boardGbn) {
        
        return new ResponseModel<>(HttpStatus.OK, mainBoardService.selectMainBoardListByBoardGbn(boardGbn));
    }

}