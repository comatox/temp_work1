package com.skshieldus.esecurity.repository.common;

import com.skshieldus.esecurity.model.sysmanage.BoardDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface CommonBoardRepository {

    List<BoardDTO> selectMainBoardListByBoardGbn(String boardGbn);

}