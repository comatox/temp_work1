package com.skshieldus.esecurity.repository.sysmanage;

import com.skshieldus.esecurity.model.sysmanage.BbsDTO;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FaqRepository {

    List<Map<String, Object>> selectFaqList(Map<String, Object> paramMap);

    Integer selectFaqListCnt(Map<String, Object> paramMap);

    BbsDTO selectFaqDetail(String boardNo);

    Integer insertFaq(Map<String, Object> paramMap);

    Integer insertFaqReply(Map<String, Object> paramMap);

    Integer modifyFaq(Map<String, Object> paramMap);

    Integer modifyFaqReadUp(Map<String, Object> paramMap);

    Integer deleteFaq(Map<String, Object> paramMap);

    Integer updateFaqFile(Map<String, Object> paramMap);

}
