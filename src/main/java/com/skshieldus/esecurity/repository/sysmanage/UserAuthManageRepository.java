package com.skshieldus.esecurity.repository.sysmanage;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface UserAuthManageRepository {

    List<Map<String, Object>> selectUserAuthList(Map<String, Object> paramMap);

    int selectUserAuthListCnt(Map<String, Object> paramMap);

    List<Map<String, Object>> selectUserAuthManageEmpAuthList(Map<String, Object> paramMap);

    int insertUserAuthManage(Map<String, Object> paramMap);

    int deleteUserAuthManage(Map<String, Object> paramMap);

    List<Map<String, Object>> selectUserAuthCheckList(Map<String, Object> paramMap);

}