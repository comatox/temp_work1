package com.skshieldus.esecurity.repository.sysmanage;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CodeManageRepository {

    List<Map<String, Object>> selectGroupManageList(Map<String, Object> paramMap);

    int selectGroupManageListCnt(Map<String, Object> paramMap);

    int updateGroupManage(Map<String, Object> paramMap);

    List<Map<String, Object>> selectDetailManageList(Map<String, Object> paramMap);

    int selectDetailManageListCnt(Map<String, Object> paramMap);

    int updateDetailManage(Map<String, Object> dataMap);

    Map<String, Object> selectDetailManageDetail(Map<String, Object> paramMap);

    int updateDetailCodeInfo(Map<String, Object> paramMap);

}