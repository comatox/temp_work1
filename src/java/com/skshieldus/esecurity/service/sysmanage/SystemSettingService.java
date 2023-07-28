package com.skshieldus.esecurity.service.sysmanage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SystemSettingService {

    /**
     * 테마 목록
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2022. 02. 15.
     */
    List<Map<String, Object>> selectThemaList(HashMap<String, Object> paramMap);

    /**
     * 테마 변경
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2022. 02. 15.
     */
    Boolean updateThema(HashMap<String, Object> paramMap);

}
