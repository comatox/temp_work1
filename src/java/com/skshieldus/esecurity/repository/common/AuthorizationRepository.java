package com.skshieldus.esecurity.repository.common;

import com.skshieldus.esecurity.model.common.MenuAuthCheckDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface AuthorizationRepository {

    List<String> selectAuthorizedMenu(String empId);

    Integer selectMenuAuthCheck(MenuAuthCheckDTO menuAuthCheckDTO);

}