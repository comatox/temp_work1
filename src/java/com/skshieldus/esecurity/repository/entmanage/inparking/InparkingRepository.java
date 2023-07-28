package com.skshieldus.esecurity.repository.entmanage.inparking;

import com.skshieldus.esecurity.config.datasource.annotation.InparkingMapper;
import java.util.Map;

@InparkingMapper
public interface InparkingRepository {

    int insertDeliveryPassIoDeliveryCarInfoForInpec(Map<String, Object> paramMap);

}
