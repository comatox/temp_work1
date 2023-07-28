package com.skshieldus.esecurity.repository.entmanage.icvstcargotham;

import com.skshieldus.esecurity.config.datasource.annotation.IcVstcarGothamMapper;
import com.skshieldus.esecurity.model.entmanage.SendSpmsDTO;
import java.util.Map;

@IcVstcarGothamMapper
public interface IcVstcarGothamRepository {

    /**
     * 이천(고담주차장) 차량관제 출입등록 등록
     *
     * @param reqParam
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 12. 08.
     */
    Map<String, Object> sendICSpmsInsertMemberInnerGotham(SendSpmsDTO reqParam);

    int procedureFrontDoorSpOutState(Map<String, Object> paramMap);

}
