package com.skshieldus.esecurity.controller.api.sysmanage;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.model.sysmanage.BoardDTO;
import com.skshieldus.esecurity.model.sysmanage.BoardSearchDTO;
import com.skshieldus.esecurity.service.sysmanage.BoardService;
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
@RequestMapping(value = "/api/sysmanage/board", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class BoardController {

    @Autowired
    private Environment environment;

    @Autowired
    private BoardService boardService;

    /**
     * 메인 게시판 목록 조회(게시판구분 별)
     *
     * @param sessionInfoVO
     * @param boardSearch
     * @return
     *
     * @author : Your ID <your email address>
     * @since : 2021. 12. 9.
     */
    @Operation(summary = "게시판 목록 조회", description = "게시판 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping(value = "", produces = { "application/json" })
    public @ResponseBody ResponseModel<List<BoardDTO>> selectBoardList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, BoardSearchDTO boardSearch) {
        

        return new ResponseModel<>(HttpStatus.OK, boardService.selectBoardList(boardSearch));
    }

    @Operation(summary = "게시판 상세조회", description = "게시판 상세를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping(value = "/detail")
    public @ResponseBody ResponseModel<BoardDTO> selectBoardView(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, BoardSearchDTO boardSearch) {
        

        BoardDTO result = boardService.selectBoardView(boardSearch);

        return new ResponseModel<BoardDTO>(HttpStatus.OK, result);
    }

    @Operation(summary = "게시판 수정", description = "게시판  수정한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/update", produces = { "application/json" })
    public @ResponseBody Boolean updateBoard(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody BoardDTO Board) {
        

        return boardService.updateBoard(Board);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @Operation(summary = "게시판 입력", description = "게시판 상세사항을 입력한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "201", description = "Created"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "", produces = { "application/json" })
    public @ResponseBody Boolean insertBoard(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody BoardDTO Board) {
        

        if (Board.getCrtBy() == null) {
            Board.setCrtBy(sessionInfoVO.getEmpNo());
        }
        Board.setAcIp(sessionInfoVO.getIp());

        return boardService.insertBoard(Board);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @Operation(summary = "up 입력", description = "게시판 상세사항을 입력한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "201", description = "Created"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/up", produces = { "application/json" })
    public @ResponseBody Boolean insertBoardUp(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody BoardDTO Board) {
        

        Board.setCrtBy(sessionInfoVO.getEmpNo());
        Board.setAcIp(sessionInfoVO.getIp());

        return boardService.insertBoardUp(Board);
    }

    @Operation(summary = "게시판 top조회", description = "게시판 top을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping(value = "/top")
    public @ResponseBody ResponseModel<BoardDTO> selectBoardTop(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, BoardSearchDTO boardSearch) {
        

        BoardDTO result = boardService.selectBoardTop(boardSearch);

        return new ResponseModel<BoardDTO>(HttpStatus.OK, result);
    }

    @Operation(summary = "게시판 조회수 up", description = "게시판  조회수를 증가한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/readup", produces = { "application/json" })
    public @ResponseBody Boolean updateBoardReadUp(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody BoardDTO Board) {
        

        Board.setCrtBy(sessionInfoVO.getEmpNo());
        Board.setAcIp(sessionInfoVO.getIp());

        return boardService.updateBoardReadUp(Board);
    }

    @Operation(summary = "게시판 글  삭제", description = "게시판 글을 삭제한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "204", description = "No Content"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PostMapping(value = "/delete", produces = { "application/json" })
    public @ResponseBody Boolean deleteBoardView(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody BoardDTO Board) {
        

        Board.setCrtBy(sessionInfoVO.getEmpNo());
        Board.setAcIp(sessionInfoVO.getIp());

        return boardService.deleteBoardView(Board);
    }

}