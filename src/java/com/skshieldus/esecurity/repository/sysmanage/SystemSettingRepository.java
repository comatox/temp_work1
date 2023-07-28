package com.skshieldus.esecurity.repository.sysmanage;

import org.apache.ibatis.annotations.Mapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface SystemSettingRepository {

    List<Map<String, Object>> selectThemaList(HashMap<String, Object> paramMap);

    int allUpdateThema();

    int updateThema(HashMap<String, Object> paramMap);

}