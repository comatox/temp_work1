package com.skshieldus.esecurity.repository.sysmanage;

import com.skshieldus.esecurity.model.sysmanage.BoardDTO;
import com.skshieldus.esecurity.model.sysmanage.BoardSearchDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface BoardRepository {

    List<BoardDTO> selectBoardList(BoardSearchDTO boardSearch);

    BoardDTO selectBoardView(BoardSearchDTO boardSearch);

    BoardDTO selectBoardTop(BoardSearchDTO boardSearch);

    int insertBoard(BoardDTO board);

    int insertBoardUp(BoardDTO board);

    int updateBoard(BoardDTO board);

    int updateBoardReadUp(BoardDTO board);

    int deleteBoardView(BoardDTO board);

    List<Map<String, Object>> listBoardMailReceiver(String schemaNm);

    List<Map<String, Object>> listQnaBoardMailReceiver(String qnaEmp);

}