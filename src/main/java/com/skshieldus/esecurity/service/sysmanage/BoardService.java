package com.skshieldus.esecurity.service.sysmanage;

import com.skshieldus.esecurity.model.sysmanage.BoardDTO;
import com.skshieldus.esecurity.model.sysmanage.BoardSearchDTO;
import java.util.List;

public interface BoardService {

    List<BoardDTO> selectBoardList(BoardSearchDTO boardSearch);

    BoardDTO selectBoardView(BoardSearchDTO boardSearch);

    BoardDTO selectBoardTop(BoardSearchDTO boardSearch);

    Boolean updateBoard(BoardDTO board);

    Boolean updateBoardReadUp(BoardDTO board);

    Boolean insertBoard(BoardDTO board);

    Boolean insertBoardUp(BoardDTO board);

    Boolean deleteBoardView(BoardDTO board);

}