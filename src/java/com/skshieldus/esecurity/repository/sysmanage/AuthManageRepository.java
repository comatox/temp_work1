package com.skshieldus.esecurity.repository.sysmanage;

import com.skshieldus.esecurity.model.sysmanage.MenuManageDTO;
import java.util.HashMap;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface AuthManageRepository {

    List<Map<String, Object>> selectAuthManageList(Map<String, Object> paramMap);

    int upsertAuthManage(Map<String, Object> paramMap);

    Integer selectAuthManageListCnt(Map<String, Object> paramMap);
    
    //메뉴 목록 조회
    List<MenuManageDTO> selectMenuManageList();

    //메뉴 상세 조회
    MenuManageDTO selectMenuManageDetail(Map<String, Object> paramMap);

    //메뉴 관리 최대 Depth 조회
    int selectMenuManageMaxDepth();

    // 메뉴 관리 새코드 생성
    Map<String, Object> selectMenuManageNewCode(Map<String, Object> paramMap);

    // 메뉴 관리 생성
    int insertMenuManage(Map<String, Object> paramMap);

    // 메뉴 관리 업데이트
    int updateMenuManage(Map<String, Object> paramMap);

    // 메뉴 관리 전체 메뉴 갯수 조회
    int selectMenuManageMenuCount(Map<String, Object> paramMap);

    // 메뉴 관리 정렬 순서 업데이트
    int updateMenuManageSortSeq(Map<String, Object> paramMap);

    // 권한/메뉴관리 권한 목록 조회
    List<Map<String, Object>> selectAuthList(HashMap<String, Object> paramMap);

    // 권한/메뉴관리 권한 메뉴 조회
    List<Map<String, Object>> selectAuthMenuList(Map<String, Object> paramMap);

    void deleteAuthMenuManage(String authId);

    void insertAuthMenuManage(Map<String, Object> reqParam);

    int selectUserAuthCheckCnt(Map<String, Object> paramMap);

    List<Map<String, Object>> selectUserAuthList(Map<String, Object> paramMap);

    Integer selectUserAuthListCnt(Map<String, Object> paramMap);

    void deleteUserAuthManage(Map<String, Object> paramMap);

    void insertUserAuthManage(Map<String, Object> row);

    List<Map<String, Object>> selectUserUseAuthList(Map<String, Object> paramMap);

}