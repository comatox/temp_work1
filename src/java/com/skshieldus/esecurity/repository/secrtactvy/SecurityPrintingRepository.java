package com.skshieldus.esecurity.repository.secrtactvy;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface SecurityPrintingRepository {

    // 통계 문서출력량 현황 부서조회
    List<Map<String, Object>> selectSecurityPrintingDeptList(String empId);

}
