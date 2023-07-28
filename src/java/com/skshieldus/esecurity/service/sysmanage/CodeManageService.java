package com.skshieldus.esecurity.service.sysmanage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CodeManageService {

    /**
     * 분류코드 목록
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2022. 02. 15.
     */
    List<Map<String, Object>> selectGroupManageList(HashMap<String, Object> paramMap);

    /**
     * 분류코드 목록 총 갯수
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2022. 02. 15.
     */
    int selectGroupManageListCnt(HashMap<String, Object> paramMap);

    /**
     * 분류코드 등록/수정
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2022. 02. 15.
     */
    Boolean updateGroupManage(HashMap<String, Object> paramMap);

    /**
     * 세부코드 목록
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2022. 02. 15.
     */
    List<Map<String, Object>> selectDetailManageList(HashMap<String, Object> paramMap);

    /**
     * 세부코드 목록 총 갯수
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2022. 02. 15.
     */
    int selectDetailManageListCnt(HashMap<String, Object> paramMap);

    /**
     * 세부코드 등록/수정
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2022. 02. 15.
     */
    Boolean updateDetailManage(HashMap<String, Object> paramMap);

    /**
     * 세부코드 상세정보 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2022. 02. 15.
     */
    Map<String, Object> selectDetailManageDetail(HashMap<String, Object> paramMap);

    /**
     * 세부코드 상세정보 수정
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2022. 02. 15.
     */
    Boolean updateDetailCodeInfo(HashMap<String, Object> paramMap);

}