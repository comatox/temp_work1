package com.skshieldus.esecurity.repository.common;

import com.skshieldus.esecurity.model.common.CoAuthMenuDTO;
import com.skshieldus.esecurity.model.common.CoEmpAuthDTO;
import com.skshieldus.esecurity.model.common.CoEmpDTO;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthRepository {

    // 사용자-권한 목록 조회
    List<CoEmpAuthDTO> selectCoEmpAuthList(CoEmpAuthDTO coEmpAuthDTO);

    // 사용자-권한 상세 조회
    CoEmpAuthDTO selectCoEmpAuth(CoEmpAuthDTO coEmpAuthDTO);

    // 권한-메뉴 목록 조회
    List<CoAuthMenuDTO> selectCoAuthMenuList(CoAuthMenuDTO coAuthMenuDTO);

    // 권한-메뉴 상세 조회
    CoAuthMenuDTO selectCoAuthMenu(CoAuthMenuDTO coAuthMenuDTO);

    Map<String, Object> selectLoginUserView(CoEmpDTO coEmpDTO);

    List<String> selectLoginUserAuth(CoEmpDTO coEmpDTO);

    int insertLoginUserLog(CoEmpDTO coEmpDTO);

    int insertLoginUserInfo(CoEmpDTO coEmpDTO);

    CoEmpDTO selectLoginUserPassChk(CoEmpDTO coEmpDTO);

    List<CoEmpAuthDTO> selectLoginAdminUserAuth(CoEmpDTO coEmpDTO);

    CoEmpDTO selectLoginUserAccountCheck(String id);

    int updateUpdateLoginUserFailCount(String id);

    int updateUpdateLoginUserFailResetCount(String id);

}
