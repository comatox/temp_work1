package com.skshieldus.esecurity.repository.entmanage;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface ConstPassProvRepository {

    /**
     * 공사출입증 지급신청 리스트 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 12. 16.
     */
    List<Map<String, Object>> selectConstPassProvList(Map<String, Object> paramMap);

    /**
     * 공사출입증 지급신청 상세보기
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 12. 16.
     */
    Map<String, Object> selectConstPassProvView(Map<String, Object> paramMap);

    /**
     * 공사출입증 지급관리 현황 리스트 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 12. 22.
     */
    List<Map<String, Object>> selectConstPassMngList(Map<String, Object> paramMap);

    /**
     * 공사출입증 지급관리 현황 사용등록 리스트 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 12. 22.
     */
    List<Map<String, Object>> selectConstPassMngCardList(Map<String, Object> paramMap);

    /**
     * 공사출입증 지급신청 등록시 업무번호 채번
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 01. 27.
     */
    Integer selectConstPassProvNewSeq(Map<String, Object> paramMap);

    /**
     * 공사출입증 지급신청 - 통합결재 송신 이후 docId업데이트
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 01. 27.
     */
    Integer updateConstPassProvDocId(Map<String, Object> paramMap);

    /**
     * 공사출입증 지급신청 - 통합결재 후처리 업데이트
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 01. 27.
     */
    Integer updateConstPassProvApplWork(Map<String, Object> paramMap);

    /**
     * 공사출입증 지급신청 카드정보 등록
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2022. 02. 04.
     */
    Integer insertConstPassCard(Map<String, Object> paramMap);

    /**
     * 공사출입증 지급신청 출입증 발급 갯수 조회
     *
     * @param constPassNo
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 02. 04.
     */
    Integer selectConstPassApplCnt(Integer constPassNo);

    /**
     * 공사출입증 지급신청 출입증 발급 IF용 데이터 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 02. 04.
     */
    Map<String, Object> selectConstPassCardViewForIdCardIF(Map<String, Object> paramMap);

    /**
     * 공사출입증 지급신청 출입증 발급 IF 로그 등록
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2022. 02. 04.
     */
    Integer insertIfIdcardOtherLogInsert(Map<String, Object> paramMap);

    /**
     * 공사출입증 지급신청 담당자 접수반려
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 02. 04.
     */
    Integer rejectConstPassProv(Map<String, Object> paramMap);

}