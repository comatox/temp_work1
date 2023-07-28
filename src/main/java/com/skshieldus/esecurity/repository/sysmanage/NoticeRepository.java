package com.skshieldus.esecurity.repository.sysmanage;

import com.skshieldus.esecurity.model.sysmanage.BbsDTO;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NoticeRepository {

    List<Map<String, Object>> selectNoticeList(Map<String, Object> paramMap);

    Integer selectNoticeListCnt(Map<String, Object> paramMap);

    BbsDTO selectNoticeDetail(String boardNo);

    Integer insertNotice(Map<String, Object> paramMap);

    Integer insertNoticeReply(Map<String, Object> paramMap);

    Integer modifyNotice(Map<String, Object> paramMap);

    Integer modifyNoticeReadUp(Map<String, Object> paramMap);

    Integer deleteNotice(Map<String, Object> paramMap);

    Integer updateNoticeFile(Map<String, Object> paramMap);

}
