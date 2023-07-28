package com.skshieldus.esecurity.repository.sysmanage;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface OffLimitsRepository {

    //사용자 목록 조회
    List<Map<String, Object>> selectOffLimitsList(Map<String, Object> paramMap);

    //사용자 목록 조회(구버전 쿼리)
    List<Map<String, Object>> selectOffLimitsListOld(Map<String, Object> paramMap);

    //사용자 상세 조회
    Map<String, Object> selectOffLimitsView(Map<String, Object> paramMap);

    //사용자 상세 조회(방문객 관리3344)
    Map<String, Object> selectOffLimitsVisitInfoView(Map<String, Object> paramMap);

    //사용자 상세 이력조회(방문객 관리3344)
    List<Map<String, Object>> selectOffLimitsVisitInfoHistoryList(Map<String, Object> paramMap);

}
