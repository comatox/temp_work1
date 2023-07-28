package com.skshieldus.esecurity.repository.secrtactvy;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface SecurityDocDistRepository {

    // 부서 비문분류기준표 목록 조회
    List<Map<String, Object>> selectSecurityDocDistItemList(Map<String, Object> paramMap);

    // 부서 비문분류기준표 상세 조회
    Map<String, Object> selectSecurityDocDistView(Map<String, Object> paramMap);

    // 부서 비문분류기준표 상세 비문(비밀/대외비) 목록 조회
    List<Map<String, Object>> selectSecurityDocDistViewList(Map<String, Object> paramMap);

    // 팀 비문분류기준표 순번 조회
    int selectSecurityDocDistSeq();

    // 팀 비문분류기준표 등록
    int insertSecurityDocDist(Map<String, Object> paramMap);

    // 팀 비문분류기준표 등록(작성 목록)
    int insertSecurityDocItem(Map<String, Object> paramMap);

}
