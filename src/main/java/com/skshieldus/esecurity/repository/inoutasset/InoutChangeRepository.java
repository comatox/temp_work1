package com.skshieldus.esecurity.repository.inoutasset;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InoutChangeRepository {

    Integer selectInDateChangeCount(Map<String, Object> paramMap);

    List<Map<String, Object>> selectInDateChangeList(Map<String, Object> paramMap);

    int insertInDateDelayHistory(Map<String, Object> paramMap);

    int updateInOutDelay(Map<String, Object> paramMap);

    int updateInDateChangeDocId(Map<String, Object> paramMap);

    Integer selectInOutEmpChangeCount(Map<String, Object> paramMap);

    List<Map<String, Object>> selectInOutEmpChangeList(Map<String, Object> paramMap);

    int insertInOutEmpChange(Map<String, Object> paramMap);

    int updateInOutDocument(Map<String, Object> paramMap);

    int updateInOutEmpChangeDocId(Map<String, Object> paramMap);

    Integer selectInOutKndChangeCount(Map<String, Object> paramMap);

    List<Map<String, Object>> selectInOutKndChangeList(Map<String, Object> paramMap);

    int insertInOutKndChange(Map<String, Object> paramMap);

    int updateInOutKnd(Map<String, Object> paramMap);

    int updateInOutKndChangeDocId(Map<String, Object> paramMap);

    Integer selectFinishChangeCount(Map<String, Object> paramMap);

    List<Map<String, Object>> selectFinishChangeList(Map<String, Object> paramMap);

    int insertFinishChangeHistory(Map<String, Object> paramMap);

    int updateFinishChange(Map<String, Object> paramMap);

    int updateFinishChangeDocId(Map<String, Object> paramMap);

}
