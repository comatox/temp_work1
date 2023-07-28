package com.skshieldus.esecurity.repository.sysmanage;

import org.apache.ibatis.annotations.Mapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface LogManageRepository {

    List<Map<String, Object>> selectUserLogList(HashMap<String, Object> paramMap);

    int selectUserLogListCnt(HashMap<String, Object> paramMap);

    List<Map<String, Object>> selectUsageStatMenu(HashMap<String, Object> paramMap);

    int selectUsageStatMenuCnt(HashMap<String, Object> paramMap);

    List<Map<String, Object>> selectUsageStatMonth(HashMap<String, Object> paramMap);

}