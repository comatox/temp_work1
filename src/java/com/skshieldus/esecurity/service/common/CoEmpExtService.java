package com.skshieldus.esecurity.service.common;

import com.skshieldus.esecurity.common.model.ListDTO;
import java.util.Map;

public interface CoEmpExtService {

    // 협력업체 사원 목록 조회
    ListDTO selectCoEmpExtList(Map<String, Object> paramMap);

    // 주소 검색
    ListDTO selectZipCodeList(Map<String, Object> paramMap);
}
