package com.skshieldus.esecurity.service.common;

import com.skshieldus.esecurity.model.common.SessionInfoVO;
import java.util.List;

public interface AuthorizationService {

    /**
     * 로그인한 사용자에게 인가된 메뉴ID 목록 조회
     *
     * @param headerMeta
     * @param sessionInfo
     * @return 메뉴ID 목록
     */
    List<String> selectAuthorizedMenu(String headerMeta, SessionInfoVO sessionInfo);

    /**
     * 로그인한 사용자에게 인가된 메뉴인지 확인
     *
     * @param headerMeta
     * @param menuId
     * @return
     */
    Boolean selectMenuAuthCheck(String headerMeta, SessionInfoVO sessionInfo, String menuId);

}
