package com.skshieldus.esecurity.repository.entmanage;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface EmergencyRepository {

    // 긴급출입 등록
    int insertEmergencyReg(Map<String, Object> paramMap);

    // 긴급출입 목록 조회
    List<Map<String, Object>> selectEmergencyList(Map<String, Object> paramMap);

    // 긴급출입 수정
    int updateEmergency(Map<String, Object> paramMap);

    // 긴급출입 삭제
    int deleteEmergency(Map<String, Object> paramMap);

    // 긴급출입 상세 조회
    Map<String, Object> selectEmerencyView(Integer emergencyNo);

    // 긴급출입(VIP) 건수 조회
    Integer selectEmerencyVipCnt(Map<String, Object> paramMap);

}
