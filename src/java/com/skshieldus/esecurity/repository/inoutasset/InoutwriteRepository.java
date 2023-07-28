package com.skshieldus.esecurity.repository.inoutasset;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InoutwriteRepository {

    // 자산반출입 구분별 그룹정보 목록 조회
    List<Map<String, Object>> selectArticleGroupCodeList(HashMap<String, Object> paramMap);

    // 자산반출입 신규 Sequence 정보 조회
    Map<String, Object> selectInoutwriteNewSeq(HashMap<String, Object> paramMap);

    // 자산반출입 저장
    int mergeInoutwrite(HashMap<String, Object> paramMap);

    // 자산반출입 DOC_ID 업데이트
    int updateInoutwriteDocId(HashMap<String, Object> paramMap);

    int insertArticle(HashMap<String, Object> paramMap);

    // 자산반출입-물품내역 저장
    int mergeInoutarticlelist(HashMap<String, Object> paramMap);

    // 자산반출입-물품내역 삭제
    void deleteInoutarticlelist(int inoutApplNo);

}
