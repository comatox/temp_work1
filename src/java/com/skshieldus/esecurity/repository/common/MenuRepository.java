package com.skshieldus.esecurity.repository.common;

import com.skshieldus.esecurity.model.common.MenuApprStatDTO;
import com.skshieldus.esecurity.model.common.MenuDTO;
import com.skshieldus.esecurity.model.common.MenuSearchDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface MenuRepository {

    List<MenuDTO> selectMyRecentMenu(String empNo);

    List<MenuDTO> selectMainApprAuthList(String empNo);

    List<MenuDTO> selectMenuList(String empNo);

    List<MenuDTO> selectMenuSearchList(MenuSearchDTO menuSearchDTO);

    MenuApprStatDTO selectApprStatList(String empNo);

    int deleteMainApprAuth(String empNo);

    int insertMainApprAuth(MenuDTO menuDTO);

    List<MenuDTO> selectHeaderSearchMenuList(MenuSearchDTO menuSearchDTO);

    List<Map<String, Object>> selectMyMenuList(MenuSearchDTO menuSearchDTO);

    int deleteMyMenuList(String empNo);

    int insertMyMenuList(Map<String, Object> paramMap);

    List<MenuDTO> selectMenuManageList();

    MenuDTO selectMenuManageDetail(MenuSearchDTO menuSearchDTO);

    int upsertMenuManageDetail(Map<String, Object> paramMap);

    MenuDTO selectMenuManageNewCode(MenuSearchDTO menuSearchDTO);

}