package com.skshieldus.esecurity.service.secrtactvy;

import com.skshieldus.esecurity.model.common.CommonXlsViewDTO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SecurityDocSendService {

    /**
     * 외부공개 자료 신청현황 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 10. 13.
     */
    List<Map<String, Object>> selectSecurityDocSendList(Map<String, Object> paramMap);

    /**
     * 외부공개 자료 신청현황 목록건수 조회
     *
     * @param paramMap
     * @return Integer
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 9. 13.
     */
    Integer selectSecurityDocSendListCnt(Map<String, Object> paramMap);

    /**
     * 외부공개 자료 신청현황 상세 조회
     *
     * @param docSendapplNo
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 10. 13.
     */
    Map<String, Object> selectSecurityDocSend(Integer docSendapplNo);

    /**
     * 외부공개 자료 신청 (상신)
     *
     * @param paramMap
     * @return
     *
     * @author : X0122721<jeyeon.kim@partner.sk.com>
     * @since : 2021. 11. 02.
     */
    boolean insertDocSend(HashMap<String, Object> paramMap);

    /**
     * 발표논문리스트 조회 SOAP 연동
     *
     * @param paramMap
     * @return
     *
     * @author : X0122721<jeyeon.kim@partner.sk.com>
     * @since : 2021. 11. 02.
     */
    List<Map<String, Object>> getThesisList(HashMap<String, Object> paramMap);

    /**
     * 결재상신후 후처리 SOAP 연동
     *
     * @param paramMap
     * @return
     *
     * @author : X0122721<jeyeon.kim@partner.sk.com>
     * @since : 2021. 11. 02.
     */
    void docSendAppl(Map<String, Object> docMap);

    /**
     * 외부공개 자료 신청현황 목록 엑셀다운로드
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 10.
     */
    CommonXlsViewDTO selectSecurityDocSendListExcel(HashMap<String, Object> paramMap);

}
