package com.skshieldus.esecurity.repository.sysmanage;

import org.apache.ibatis.annotations.Mapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface GroupManageRepository {

    List<Map<String, Object>> selectCorpManageList(HashMap<String, Object> paramMap);

    int selectCorpManageListCnt(HashMap<String, Object> paramMap);

    List<Map<String, Object>> selectDeptManageList(HashMap<String, Object> paramMap);

    int selectDeptManageListCnt(HashMap<String, Object> paramMap);

    List<Map<String, Object>> selectUserManageList(HashMap<String, Object> paramMap);

    int selectUserManageListCnt(HashMap<String, Object> paramMap);

    List<Map<String, Object>> selectJwManageList(HashMap<String, Object> paramMap);

    int selectJwManageListCnt(HashMap<String, Object> paramMap);

    int insertDeptManage(Map<String, Object> paramMap);

    int updateDeptManage(Map<String, Object> paramMap);

    List<Map<String, Object>> selectUserManageList(Map<String, Object> paramMap);

    int selectUserManageListCnt(Map<String, Object> paramMap);

    Map<String, Object> selectUserManageDetail(Map<String, Object> paramMap);

    List<Map<String, Object>> selectUserManageCheckId(Map<String, Object> paramMap);

    List<Map<String, Object>> selectUserManageCheckEmpId(Map<String, Object> paramMap);

    List<Map<String, Object>> selectUserManageJcList(Map<String, Object> paramMap);

    List<Map<String, Object>> selectUserManageJwList(Map<String, Object> paramMap);

    int insertUserManage(Map<String, Object> paramMap);

    List<Map<String, Object>> selectHynixUserList(Map<String, Object> paramMap);

    int selectHynixUserListCnt(Map<String, Object> paramMap);

    int insertHynixUser(Map<String, Object> paramMap);

    List<Map<String, Object>> selectCardKeyUserList(Map<String, Object> paramMap);

    int selectCardKeyUserListCnt(Map<String, Object> paramMap);

}