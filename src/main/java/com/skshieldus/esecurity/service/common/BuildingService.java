package com.skshieldus.esecurity.service.common;

import com.skshieldus.esecurity.model.common.CoBldgDTO;
import com.skshieldus.esecurity.model.common.CoXempBldgOutDTO;
import java.util.List;
import java.util.Map;

public interface BuildingService {

    /**
     * 건물정보 상세 조회
     *
     * @return
     *
     * @author : X0121127 <sungbum.oh@partner.sk.com>
     * @since : 2021. 2. 16.
     */
    CoBldgDTO selectCoBldg(String gateId);

    /**
     * 건물정보 (key, value) 목록 조회
     *
     * @return
     *
     * @author : X0121127 <sungbum.oh@partner.sk.com>
     * @since : 2021. 2. 16.
     */
    Map<String, CoBldgDTO> selectCoBldgList(List<String> gateIdList);

    /**
     * 건물보안출입문 (건물정보) 목록 조회
     *
     * @return
     *
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2021. 4. 22.
     */
    List<CoXempBldgOutDTO> selectCoXempBldgOut(String compId);

}