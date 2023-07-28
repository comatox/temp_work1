package com.skshieldus.esecurity.repository.entmanage.cjvehiclecampus;

import com.skshieldus.esecurity.config.datasource.annotation.CjVehicleCampusMapper;
import com.skshieldus.esecurity.model.entmanage.SendSpmsDTO;
import java.util.Map;

@CjVehicleCampusMapper
public interface CjVehicleCampusRepository {

    /**
     * 청주 차량관제 출입등록 등록
     *
     * @param reqParam
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 12. 08.
     */
    Map<String, Object> sendCJSpmsInsertMemberInner(SendSpmsDTO reqParam);

}
