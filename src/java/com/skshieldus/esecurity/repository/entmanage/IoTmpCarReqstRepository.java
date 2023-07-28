package com.skshieldus.esecurity.repository.entmanage;

import com.skshieldus.esecurity.model.entmanage.SendSpmsDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface IoTmpCarReqstRepository {

    /**
     * 임시차량출입 신청 리스트 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 11. 17.
     */
    List<Map<String, Object>> selectIoTmpCarReqstList(Map<String, Object> paramMap);

    /**
     * 임시차량출입 신청 현황 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 11. 17.
     */
    Map<String, Object> selectIoTmpCarReqstView(Map<String, Object> paramMap);

    /**
     * 임시차량출입 신청 출입자 차량 리스트 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 11. 23.
     */
    List<Map<String, Object>> selectIoTmpCarReqstUserList(Map<String, Object> paramMap);

    /**
     * 임시차량출입 신청 등록
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 11. 17.
     */
    Integer insertIoTmpCarReqst(Map<String, Object> paramMap);

    /**
     * 임시차량출입 신청 출입자(구성원/외부인) 등록
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 11. 17.
     */
    Integer insertIoTmpCarReqstUser(Map<String, Object> paramMap);

    /**
     * 임시차량출입 신청 등록시 업무번호 채번
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 11. 17.
     */
    Integer selectIoTmpCarReqstNewSeq(Map<String, Object> paramMap);

    /**
     * 임시차량출입 신청 출입자 등록시 SEQ NO 채번
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 11. 17.
     */
    Integer selectIoTmpCarReqstUserNewSeq(Map<String, Object> paramMap);

    /**
     * 임시차량출입 신청 - 통합결재 송신 이후 docId업데이트
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 11. 17.
     */
    Integer updateIoTmpCarReqstDocId(Map<String, Object> paramMap);

    /**
     * 임시차량출입 신청 - 통합결재 후처리 이천 차량관제 전송용 데이터 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 11. 19.
     */
    List<SendSpmsDTO> selectIoTmpCarInfoForICSpms(Map<String, Object> paramMap);

    /**
     * 임시차량출입 신청 - 통합결재 후처리 청주 차량관제 전송용 데이터 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 11. 19.
     */
    List<SendSpmsDTO> selectIoTmpCarInfoForCJSpms(Map<String, Object> paramMap);

    /**
     * 방문차량출입 신청 - 통합결재 후처리 이천 차량관제 전송용 데이터 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 11. 19.
     */
    List<SendSpmsDTO> selectIoVstInfoForGotham(Map<String, Object> paramMap);

    /**
     * 임시차량출입 신청 - 통합결재 후처리 카카오톡 알림메시지 발송대상자 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 11. 19.
     */
    List<Map<String, Object>> selectCarPassTempListForSMS(Map<String, Object> paramMap);

    /**
     * 임시차량출입 신청 - 통합결재 후처리 업데이트
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 11. 17.
     */
    Integer updateIoTmpCarReqstApplWork(Map<String, Object> paramMap);

    /**
     * 임시차량출입 신청현황 - 엑셀 다운로드
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 11. 17.
     */
    List<Map<String, Object>> selectIoTmpCarReqstListExcel(Map<String, Object> paramMap);

}