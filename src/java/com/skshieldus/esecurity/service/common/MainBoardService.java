package com.skshieldus.esecurity.service.common;

import com.skshieldus.esecurity.model.sysmanage.BoardDTO;
import java.util.List;

public interface MainBoardService {

    /**
     * 메인 게시판 목록 조회(게시판구분 별)
     *
     * @return
     *
     * @author : X0121127 <sungbum.oh@partner.sk.com>
     * @since : 2021. 8. 20
     */
    public List<BoardDTO> selectMainBoardListByBoardGbn(String boardGbn);

}