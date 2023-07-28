package com.skshieldus.esecurity.service.entmanage;

import java.util.Map;

public interface FrontDoorService {

    /**
     * 정문 인원 출입 - 입문 처리
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 1.
     */
    Map<String, Object> executeFrontDoorInprocess(Map<String, Object> paramMap);

    /**
     * 정문 인원 출입 - 출문 처리
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 1.
     */
    Map<String, Object> executeFrontDoorOutprocess(Map<String, Object> paramMap);

}
