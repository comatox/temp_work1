package com.skshieldus.esecurity.service.entmanage;

import com.skshieldus.esecurity.model.common.SessionInfoVO;
import java.util.List;
import java.util.Map;

public interface ConstPassProvService {

    // 공사출입증 지급신청 현황
    public List<Map<String, Object>> selectConstPassProvList(Map<String, Object> paramMap);

    public Map<String, Object> selectConstPassProvView(Map<String, Object> paramMap);

    // 공사출입증 지급관리 현황
    public List<Map<String, Object>> selectConstPassMngList(Map<String, Object> paramMap);

    public List<Map<String, Object>> selectConstPassMngCardList(Map<String, Object> paramMap);

    // 공사출입증 지급신청 등록
    public boolean insertConstPassProv(SessionInfoVO sessionInfoVO, Map<String, Object> paramMap);

    // 공사출입증 지급신청 담당자 접수반려
    public boolean rejectConstPassProv(SessionInfoVO sessionInfoVO, Map<String, Object> paramMap);

    // 공사출입증 지급신청 후처리 IF 등록
    public boolean insertIDCardIfHeifOtherInfo(Integer constPassNo);

}
