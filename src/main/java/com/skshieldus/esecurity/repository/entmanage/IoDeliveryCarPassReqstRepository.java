package com.skshieldus.esecurity.repository.entmanage;

import com.skshieldus.esecurity.model.entmanage.SendSpmsDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface IoDeliveryCarPassReqstRepository {

    /**
     * 납품차량출입 신청 리스트 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 11. 14.
     */
    List<Map<String, Object>> selectIoDeliveryCarPassReqstList(Map<String, Object> paramMap);

    /**
     * 납품차량출입 신청 등록
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 12. 14.
     */
    Integer insertIoDeliveryCarPassReqst(Map<String, Object> paramMap);

    /**
     * 납품차량출입 신청 등록시 업무번호 채번
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 12. 14.
     */
    Integer selectIoDeliveryCarPassReqstNewSeq(Map<String, Object> paramMap);

    /**
     * 납품차량출입 신청 - 통합결재 송신 이후 docId업데이트
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 12. 14.
     */
    Integer updateIoDeliveryCarPassReqstDocId(Map<String, Object> paramMap);

    /**
     * 납품차량출입 신청 - 통합결재 후처리 이천 차량관제 전송용 데이터 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 12. 14.
     */
    List<SendSpmsDTO> selectIoDeliveryCarPassInfoForICSpms(Map<String, Object> paramMap);

    /**
     * 납품차량출입 신청 - 통합결재 후처리 카카오톡 알림메시지 발송대상자 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 12. 14.
     */
    List<Map<String, Object>> selectDeliveryCarPassReqstListForSMS(Map<String, Object> paramMap);

    /**
     * 납품차량출입 신청 - 통합결재 후처리 업데이트
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 12. 14.
     */
    Integer updateIoDeliveryCarPassReqstApplWork(Map<String, Object> paramMap);

}