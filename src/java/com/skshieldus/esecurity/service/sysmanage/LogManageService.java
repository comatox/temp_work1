package com.skshieldus.esecurity.service.sysmanage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface LogManageService {

    /**
     * 사용자 로그 목록
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2022. 02. 03.
     */
    List<Map<String, Object>> selectUserLogList(HashMap<String, Object> paramMap);

    /**
     * 사용자 로그 목록 총갯수
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2022. 02. 03.
     */
    int selectUserLogListCnt(HashMap<String, Object> paramMap);

    /**
     * 사용통계 - 메뉴별
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2022. 02. 03.
     */
    List<Map<String, Object>> selectUsageStatMenu(HashMap<String, Object> paramMap);

    /**
     * 사용통계 - 메뉴별 총갯수
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2022. 02. 03.
     */
    int selectUsageStatMenuCnt(HashMap<String, Object> paramMap);

    /**
     * 사용통계 - 월별
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2022. 02. 03.
     */
    List<Map<String, Object>> selectUsageStatMonth(HashMap<String, Object> paramMap);

}