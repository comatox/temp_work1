package com.skshieldus.esecurity.repository.entmanage;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SafetyCarPassRepository {

    /**
     * 안전작업차량 출입 리스트 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2023. 07. 27.
     */
    List<Map<String, Object>> selectSafetyCarPassList(Map<String, Object> paramMap);

    /**
     * 안전작업차량 출입 리스트 카운트 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2023. 07. 27.
     */
    int selectSafetyCarPassListCnt(Map<String, Object> paramMap);

    /**
     * 안전작업차량 출입 상세현황 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2023. 07. 27.
     */
    Map<String, Object> selectSafetyCarPassView(Map<String, Object> paramMap);

    /**
     * 안전작업차량 출입 신청 출입자 차량 리스트 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2023. 07. 27.
     */
    List<Map<String, Object>> selectSafetyCarPassUserList(Map<String, Object> paramMap);

    /**
     * 안전작업차량 출입 등록시 SEQ NO 채번
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2023. 07. 27.
     */
    int selectSafetyCarPassReqstNewSeq(Map<String, Object> paramMap);

    /**
     * 안전작업차량 출입자 등록시 SEQ NO 채번
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2023. 07. 27.
     */
    int selectSafetyCarPassReqstUserNewSeq(Map<String, Object> paramMap);

    /**
     * 안전작업차량 출입 신청 등록
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2023. 07. 27.
     */
    Integer insertSafetyCarPassRequest(Map<String, Object> paramMap);

    /**
     * 안전작업차량 출입 신청 출입자 등록
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2023. 07. 27.
     */
    Integer insertSafetyCarPassRequestUser(Map<String, Object> paramMap);

    /**
     * 안전작업차량 출입 신청 - 결재상신 이후 docId업데이트
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2023. 07. 27.
     */
    Integer updateSafetyCarPassDocId(Map<String, Object> paramMap);
}