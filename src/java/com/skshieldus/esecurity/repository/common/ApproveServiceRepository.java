package com.skshieldus.esecurity.repository.common;

import org.apache.ibatis.annotations.Mapper;
import java.util.Map;

@Mapper
public interface ApproveServiceRepository {

    // 사외반출확인서 파일 조회
    Map<String, Object> selectInoutDelayCalling(Integer histSeqNo);

}