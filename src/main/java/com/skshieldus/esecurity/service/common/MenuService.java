package com.skshieldus.esecurity.service.common;

import com.skshieldus.esecurity.model.common.MenuApprStatDTO;
import com.skshieldus.esecurity.model.common.MenuDTO;
import com.skshieldus.esecurity.model.common.MenuSearchDTO;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import java.util.List;
import java.util.Map;

public interface MenuService {

    /**
     * 사용자 최근 메뉴목록 조회
     *
     * @return
     *
     * @author : X0121127 <sungbum.oh@partner.sk.com>
     * @since : 2021. 8. 19
     */
    List<MenuDTO> selectMyRecentMenu(String empNo);

    /**
     * 사용자 신청함 메뉴목록 조회
     *
     * @return
     *
     * @author : X0121127 <sungbum.oh@partner.sk.com>
     * @since : 2021. 8. 19
     */
    List<MenuDTO> selectMainApprAuthList(String empNo);

    /**
     * 사용자 메뉴 조회
     *
     * @param empNo
     * @return
     *
     * @author : X0121126 <woogon.kim@partner.sk.com>
     * @since : 2021. 8. 20.
     */
    List<MenuDTO> selectMenuList(String empNo);

    /**
     * 메뉴 검색(keyword기반) 조회
     *
     * @param menuSearchDTO
     * @return
     *
     * @author : X0121127 <sungbum.oh@partner.sk.com>
     * @since : 2021. 8. 24
     */
    List<MenuDTO> selectMenuSearchList(MenuSearchDTO menuSearchDTO);

    /**
     * 신청함 신청건수 조회
     *
     * @param empNo
     * @return
     *
     * @author : X0121127 <sungbum.oh@partner.sk.com>
     * @since : 2021. 8. 25
     */
    MenuApprStatDTO selectApprStatList(String empNo);

    /**
     * 신청함 메뉴 수정(편집)
     *
     * @param menuDTO
     * @return
     *
     * @author : X0121127 <sungbum.oh@partner.sk.com>
     * @since : 2021. 8. 26
     */
    boolean updateMainApprAuth(MenuDTO menuDTO);

    /**
     * 사용자 나의 메뉴목록 조회 (TREE)
     *
     * @return
     *
     * @author : X0125104 <won.shin@partner.sk.com>
     * @since : 2022. 01. 19
     */
    List<MenuDTO> selectHeaderSearchMenuList(MenuSearchDTO menuSearchDTO);

    /**
     * 사용자 나의 메뉴목록 조회 (즐겨찾기)
     *
     * @return
     *
     * @author : X0125104 <won.shin@partner.sk.com>
     * @since : 2022. 01. 19
     */
    List<Map<String, Object>> selectMyMenuList(MenuSearchDTO menuSearchDTO);

    /**
     * 사용자 나의 메뉴목록 등록
     *
     * @return
     *
     * @author : X0125104 <won.shin@partner.sk.com>
     * @since : 2022. 03. 03
     */
    boolean saveMyMenuList(Map<String, Object> paramMap);

    /**
     * 메뉴관리 목록 조회
     *
     * @return
     *
     * @author : X0125104 <won.shin@partner.sk.com>
     * @since : 2022. 02. 07
     */
    List<MenuDTO> selectMenuManageList();

    /**
     * 메뉴관리 상세 조회
     *
     * @return
     *
     * @author : X0125104 <won.shin@partner.sk.com>
     * @since : 2022. 02. 07
     */
    MenuDTO selectMenuManageDetail(MenuSearchDTO menuSearchDTO);

    /**
     * 메뉴관리 저장
     *
     * @return
     *
     * @author : X0125104 <won.shin@partner.sk.com>
     * @since : 2022. 02. 08
     */
    boolean upsertMenuManageDetail(SessionInfoVO sessionInfoVO, Map<String, Object> paramMap);

    /**
     * 메뉴관리 신규 추가 정렬순서 및 메뉴ID 조회
     *
     * @return
     *
     * @author : X0125104 <won.shin@partner.sk.com>
     * @since : 2022. 02. 09
     */
    MenuDTO selectMenuManageNewCode(MenuSearchDTO menuSearchDTO);

}