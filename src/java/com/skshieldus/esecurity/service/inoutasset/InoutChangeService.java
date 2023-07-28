package com.skshieldus.esecurity.service.inoutasset;

import com.skshieldus.esecurity.common.model.ListDTO;
import java.util.Map;

public interface InoutChangeService {

    ListDTO<Map<String, Object>> selectInDateChangeList(Map<String, Object> paramMap);

    void insertInDateChange(Map<String, Object> paramMap);

    ListDTO<Map<String, Object>> selectInOutEmpChangeList(Map<String, Object> paramMap);

    void insertEmpChange(Map<String, Object> paramMap);

    ListDTO<Map<String, Object>> selectInOutKndChangeList(Map<String, Object> paramMap);

    void insertInOutKndChange(Map<String, Object> paramMap);

}
