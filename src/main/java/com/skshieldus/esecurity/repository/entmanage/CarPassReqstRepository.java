package com.skshieldus.esecurity.repository.entmanage;

import com.skshieldus.esecurity.model.entmanage.SendSpmsDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface CarPassReqstRepository {

    /**
     * 방문차량출입 신청 리스트 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 11. 17.
     */
    List<Map<String, Object>> selectCarPassReqstList(Map<String, Object> paramMap);

    /**
     * 방문차량출입 신청 현황 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 11. 17.
     */
    Map<String, Object> selectCarPassReqstView(Map<String, Object> paramMap);

    /**
     * 방문차량출입 신청 출입자 차량 리스트 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 11. 23.
     */
    List<Map<String, Object>> selectCarPassReqstUserList(Map<String, Object> paramMap);

    /**
     * 방문차량출입 신청 등록
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 11. 17.
     */
    Integer insertCarPassReqst(Map<String, Object> paramMap);

    /**
     * 방문차량출입 신청 출입자(구성원/외부인) 등록
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 11. 17.
     */
    Integer insertCarPassReqstUser(Map<String, Object> paramMap);

    /**
     * 방문차량출입 신청 등록시 업무번호 채번
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 11. 17.
     */
    Integer selectCarPassReqstNewSeq(Map<String, Object> paramMap);

    /**
     * 방문차량출입 신청 출입자 등록시 SEQ NO 채번
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 11. 17.
     */
    Integer selectCarPassReqstUserNewSeq(Map<String, Object> paramMap);

    /**
     * 방문차량출입 신청 - 통합결재 송신 이후 docId업데이트
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 11. 17.
     */
    Integer updateCarPassReqstDocId(Map<String, Object> paramMap);

    /**
     * 방문차량출입 신청 - 차량 출입 쿼터 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 12. 06.
     */
    Map<String, Object> selectCarPassQuotaCheck(Map<String, Object> paramMap);

    /**
     * 방문차량출입 신청 - 통합결재 후처리 이천 차량관제 전송용 데이터 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 11. 17.
     */
    List<SendSpmsDTO> selectIoVstCarInfoForICSpms(Map<String, Object> paramMap);

    /**
     * 방문차량출입 신청 - 통합결재 후처리 청주 차량관제 전송용 데이터 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 11. 19.
     */
    List<SendSpmsDTO> selectIoVstCarInfoForCJSpms(Map<String, Object> paramMap);

    /**
     * 방문차량출입 신청 - 통합결재 후처리 카카오톡 알림메시지 발송대상자 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 11. 19.
     */
    List<Map<String, Object>> selectCarPassListForSMS(Map<String, Object> paramMap);

    /**
     * 방문차량출입 신청 - 외부인 차량정보 업데이트
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 12. 06.
     */
    Integer updateIoEmpCarInfo(Map<String, Object> paramMap);

    /**
     * 방문차량출입 신청 - 통합결재 후처리 업데이트
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 11. 17.
     */
    Integer updateCarPassReqstApplWork(Map<String, Object> paramMap);

}