package com.skshieldus.esecurity.service.common.impl;

import com.skshieldus.esecurity.model.sysmanage.BoardDTO;
import com.skshieldus.esecurity.repository.common.CommonBoardRepository;
import com.skshieldus.esecurity.service.common.MainBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class MainBoardServiceImpl implements MainBoardService {

    @Autowired
    private CommonBoardRepository commonBoardRepository;

    /**
     * 메인 게시판 목록 조회(게시판구분 별)
     *
     * @return
     *
     * @author : X0121127 <sungbum.oh@partner.sk.com>
     * @since : 2021. 8. 20
     */
    public List<BoardDTO> selectMainBoardListByBoardGbn(String boardGbn) {
        return commonBoardRepository.selectMainBoardListByBoardGbn(boardGbn);
    }

}