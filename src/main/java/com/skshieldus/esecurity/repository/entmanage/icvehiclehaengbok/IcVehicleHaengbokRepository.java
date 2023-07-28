package com.skshieldus.esecurity.repository.entmanage.icvehiclehaengbok;

import com.skshieldus.esecurity.config.datasource.annotation.IcVehicleHaengbokMapper;
import com.skshieldus.esecurity.model.entmanage.SendSpmsDTO;
import java.util.Map;

@IcVehicleHaengbokMapper
public interface IcVehicleHaengbokRepository {

    /**
     * 이천 차량관제 출입등록 등록
     *
     * @param reqParam
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 12. 08.
     */
    Map<String, Object> sendICSpmsInsertMemberInner(SendSpmsDTO reqParam);

}
