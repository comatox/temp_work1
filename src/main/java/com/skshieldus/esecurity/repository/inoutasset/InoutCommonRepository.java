package com.skshieldus.esecurity.repository.inoutasset;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InoutCommonRepository {

    Map<String, Object> selectInOutWriteView(Integer inoutApplNo);

    List<Map<String, Object>> selectInOutArticleList(Integer inoutApplNo);

    Map<String, Object> selectInoutProcess(Integer inoutApplNo);

    List<Map<String, Object>> selectInDateChangeHistory(Map<String, Object> param);

    List<Map<String, Object>> selectEmpChangeHistory(Map<String, Object> param);

    List<Map<String, Object>> selectInOutKndChangeHistory(Map<String, Object> param);

    List<Map<String, Object>> selectFinishChangeHistory(Map<String, Object> param);

    List<Map<String, Object>> selectCallingHistory(Map<String, Object> param);

}
