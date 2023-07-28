package com.skshieldus.esecurity.service.sysmanage;

import com.skshieldus.esecurity.common.model.ListDTO;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.model.sysmanage.MenuManageDTO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface AuthManageService {

    /**
     * 환경설정 권한관리 목록조회
     *
     * @return
     *
     * @author : X0125104 <won.shin@partner.sk.com>
     * @since : 2022. 02. 11
     */
    ListDTO<Map<String, Object>> selectAuthManageList(Map<String, Object> paramMap);

    /**
     * 환경설정 권한관리 저장
     *
     * @return
     *
     * @author : X0125104 <won.shin@partner.sk.com>
     * @since : 2022. 02. 16
     */
    boolean upsertAuthManage(Map<String, Object> paramMap);

    /* 메뉴관리 조회 */
    Map<String, Object> selectMenuManageList(Map<String, Object> paramMap);

    /* 메뉴관리 상세조회 */
    MenuManageDTO selectMenuManageDetail(Map<String, Object> paramMap);

    /* 메뉴관리 신규 menu id 조회 */
    Map<String, Object> selectMenuManageNewCode(Map<String, Object> paramMap);

    /* 메뉴관리 저장 */
    int updateMenuManage(Map<String, Object> paramMap);

    List<Map<String, Object>> selectAuthList(HashMap<String, Object> paramMap);

    /* 권한관리 > 권한/메뉴관리 메뉴조회*/
    Map<String, Object> selectAuthMenuList(Map<String, Object> paramMap);

    /*권한/메뉴관리 저장*/
    boolean saveAuthMenuManage(SessionInfoVO sessionInfoVO, Map<String, Object> paramMap);

    /* 사용자 권한체크(사용자권한관리, 사용자결재권한 관리 사용) */
    int selectUserAuthCheckCnt(Map<String, Object> paramMap);

    /* 사용자 권한 관리 목록조회 */
    ListDTO<Map<String, Object>> selectUserAuthList(Map<String, Object> paramMap);

    boolean saveUserAuthManage(SessionInfoVO sessionInfoVO, Map<String, Object> paramMap);

    ListDTO<Map<String, Object>> selectUserUseAuthList(Map<String, Object> paramMap);

}