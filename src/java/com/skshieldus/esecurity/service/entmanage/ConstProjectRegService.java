package com.skshieldus.esecurity.service.entmanage;

import com.skshieldus.esecurity.model.common.SessionInfoVO;
import java.util.List;
import java.util.Map;

public interface ConstProjectRegService {

    // 공사프로젝트 등록 현황
    public List<Map<String, Object>> selectConstProjectRegList(Map<String, Object> paramMap);

    public Map<String, Object> selectConstProjectRegView(Map<String, Object> paramMap);

    // 공사프로젝트 등록
    public boolean insertConstProjectReg(SessionInfoVO sessionInfoVO, Map<String, Object> paramMap);

}
