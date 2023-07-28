package com.skshieldus.esecurity.repository.sysmanage;

import java.util.HashMap;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface AuthMenuManageRepository {

    List<Map<String, Object>> selectAuthList(HashMap<String, Object> paramMap);

    List<Map<String, Object>> selectAuthMenuList(Map<String, Object> paramMap);

    int insertAuthMenuManage(Map<String, Object> paramMap);

    int deleteAuthMenuManage(String authId);

    List<Map<String, Object>> selectAuthMenuManageAuthList();

}