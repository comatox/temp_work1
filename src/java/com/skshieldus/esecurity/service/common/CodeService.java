package com.skshieldus.esecurity.service.common;

import com.skshieldus.esecurity.model.common.CoCodeDetailDTO;
import com.skshieldus.esecurity.model.common.CoCodeMasterDTO;
import java.util.List;
import java.util.Map;

public interface CodeService {

    /**
     * 공통코드 전체(Master + DetailList) 목록 조회
     *
     * @return
     *
     * @author : X0121127 <sungbum.oh@partner.sk.com>
     * @since : 2020. 01. 21.
     */
    List<CoCodeMasterDTO> selectCodeMasterList();

    /**
     * 공통코드 상세 목록 조회
     *
     * @return
     *
     * @author : X0121127 <sungbum.oh@partner.sk.com>
     * @since : 2020. 01. 21.
     */
    List<CoCodeDetailDTO> selectCodeDetailList(String grpCd);

    /**
     * 공통코드 전체(Master + DetailList) (key, value) 조회
     *
     * @return
     *
     * @author : X0121127 <sungbum.oh@partner.sk.com>
     * @since : 2021. 1. 28.
     */
    Map<String, Object> selectCodeMasterListForKey();

}