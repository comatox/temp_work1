package com.skshieldus.esecurity.service.common.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.model.common.MenuApprStatDTO;
import com.skshieldus.esecurity.model.common.MenuDTO;
import com.skshieldus.esecurity.model.common.MenuSearchDTO;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.repository.common.MenuRepository;
import com.skshieldus.esecurity.service.common.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@Slf4j
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 사용자 최근 메뉴목록 조회
     *
     * @return
     *
     * @author : X0121127 <sungbum.oh@partner.sk.com>
     * @since : 2021. 8. 19
     */
    public List<MenuDTO> selectMyRecentMenu(String empNo) {
        return menuRepository.selectMyRecentMenu(empNo);
    }

    /**
     * 사용자 신청함 메뉴목록 조회
     *
     * @return
     *
     * @author : X0121127 <sungbum.oh@partner.sk.com>
     * @since : 2021. 8. 20
     */
    public List<MenuDTO> selectMainApprAuthList(String empNo) {
        return menuRepository.selectMainApprAuthList(empNo);
    }

    /**
     * 사용자 메뉴 조회
     *
     * @param empNo
     * @return
     *
     * @author : X0121126 <woogon.kim@partner.sk.com>
     * @since : 2021. 8. 20.
     */
    @Override
    public List<MenuDTO> selectMenuList(String empNo) {
        List<MenuDTO> menuList = menuRepository.selectMenuList(empNo);
        return menuList;
    }

    /**
     * 메뉴 검색(keyword기반) 조회
     *
     * @param menuSearchDTO
     * @return
     *
     * @author : X0121127 <sungbum.oh@partner.sk.com>
     * @since : 2021. 8. 24
     */
    @Override
    public List<MenuDTO> selectMenuSearchList(MenuSearchDTO menuSearchDTO) {
        return menuRepository.selectMenuSearchList(menuSearchDTO);
    }

    /**
     * 신청함 신청건수 조회
     *
     * @param empNo
     * @return
     *
     * @author : X0121127 <sungbum.oh@partner.sk.com>
     * @since : 2021. 8. 25
     */
    @Override
    public MenuApprStatDTO selectApprStatList(String empNo) {
        return menuRepository.selectApprStatList(empNo);
    }

    /**
     * 신청함 메뉴 수정(편집)
     *
     * @param menuDTO
     * @return
     *
     * @author : X0121127 <sungbum.oh@partner.sk.com>
     * @since : 2021. 8. 26
     */
    @Override
    public boolean updateMainApprAuth(MenuDTO menuDTO) {
        boolean result = true;

        // 기존 메뉴 제거
        menuRepository.deleteMainApprAuth(menuDTO.getEmpNo());

        // 메뉴 등록
        List<String> menuIds = menuDTO.getMenuIds();
        for (int i = 0; i < menuIds.size(); i++) {
            MenuDTO newMenuDTO = new MenuDTO();
            newMenuDTO.setMenuId(menuIds.get(i));
            newMenuDTO.setSortSeq(i + 1);
            newMenuDTO.setEmpNo(menuDTO.getEmpNo());
            menuRepository.insertMainApprAuth(newMenuDTO);
        }

        return result;
    }

    @Override
    public List<MenuDTO> selectMenuManageList() {
        List<MenuDTO> resultList = null;

        try {
            // 목록 조회
            resultList = menuRepository.selectMenuManageList();
            for (MenuDTO row : resultList) {
                List<String> newPath = row.getPath() != null
                    ? Arrays.asList(row.getPath().split(","))
                    : new ArrayList<>();
                row.setTreePath(newPath);
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return resultList;
    }

    @Override
    public List<MenuDTO> selectHeaderSearchMenuList(MenuSearchDTO menuSearchDTO) {
        List<MenuDTO> resultList = null;

        try {
            // 목록 조회
            resultList = menuRepository.selectHeaderSearchMenuList(menuSearchDTO);
            for (MenuDTO row : resultList) {
                List<String> newPath = row.getPath() != null
                    ? Arrays.asList(row.getPath().split(","))
                    : new ArrayList<>();
                row.setTreePath(newPath);
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return resultList;
    }

    @Override
    public List<Map<String, Object>> selectMyMenuList(MenuSearchDTO menuSearchDTO) {
        List<Map<String, Object>> resultList = null;

        try {
            // 목록 조회
            resultList = menuRepository.selectMyMenuList(menuSearchDTO);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return resultList;
    }

    @Override
    public MenuDTO selectMenuManageDetail(MenuSearchDTO menuSearchDTO) {
        MenuDTO result = new MenuDTO();

        try {
            // 목록 조회
            result = menuRepository.selectMenuManageDetail(menuSearchDTO);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return result;
    }

    @Override
    public boolean upsertMenuManageDetail(SessionInfoVO sessionInfoVO, Map<String, Object> paramMap) {
        boolean result = false;
        int upsertMenuManageDetailResult = 0;

        try {

            String sessionEmpId = sessionInfoVO.getEmpNo();
            String acIp = sessionInfoVO.getIp();
            paramMap.put("sessionEmpId", sessionEmpId);
            paramMap.put("acIp", acIp);

            log.info(">>>> sessionEmpId : {}", sessionEmpId);
            log.info(">>>> acIp : {}", acIp);

            upsertMenuManageDetailResult = menuRepository.upsertMenuManageDetail(paramMap);

            if (upsertMenuManageDetailResult != 1) {
                throw new Exception("Failed to execute menuRepository.upsertMenuManageDetail");
            }

            log.info(">>>> 메뉴관리 저장 성공");
            result = true;
        } catch (Exception e) {
            result = false;
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return result;
    }

    @Override
    public MenuDTO selectMenuManageNewCode(MenuSearchDTO menuSearchDTO) {
        MenuDTO result = new MenuDTO();

        try {
            // 목록 조회
            result = menuRepository.selectMenuManageNewCode(menuSearchDTO);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return result;
    }

    @Override
    public boolean saveMyMenuList(Map<String, Object> paramMap) {
        boolean result = false;
        int insertMyMenuListResult = 0;

        try {

            String empId = String.valueOf(paramMap.getOrDefault("empId", ""));
            if ("".equals(empId)) {
                throw new Exception("Failed to execute menuRepository.saveMyMenuList >>> EMP_ID IS NOT VALID");
            }

            List<Map<String, Object>> updateList = objectMapper.convertValue(paramMap.get("updateList"),
                new TypeReference<List<Map<String, Object>>>() {
                });

            menuRepository.deleteMyMenuList(empId);

            for (Map<String, Object> row : updateList) {

                row.put("empId", empId);
                menuRepository.insertMyMenuList(row);
                insertMyMenuListResult++;
            }

            log.info(">>>> 나의 메뉴 저장 성공 : {}", insertMyMenuListResult);
            result = true;
        } catch (Exception e) {
            result = false;
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return result;
    }

}