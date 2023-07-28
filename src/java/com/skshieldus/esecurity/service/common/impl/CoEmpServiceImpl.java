package com.skshieldus.esecurity.service.common.impl;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.model.common.*;
import com.skshieldus.esecurity.repository.common.AuthRepository;
import com.skshieldus.esecurity.repository.common.CoEmpRepository;
import com.skshieldus.esecurity.service.common.CoEmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class CoEmpServiceImpl implements CoEmpService {

    @Autowired
    private CoEmpRepository coEmpRepository;

    @Autowired
    private AuthRepository authRepository;

    @Override
    public List<CoEmpDTO> selectCoEmpList(CoEmpDTO coEmpDTO) {
        List<CoEmpDTO> resultList = null;

        try {
            resultList = coEmpRepository.selectCoEmpList(coEmpDTO);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return resultList;
    }

    @Override
    public Map<String, Object> selectCoEmpListForKey(CoEmpDTO coEmpDTO) {
        Map<String, Object> resultList = null;

        try {
            List<CoEmpDTO> empList = coEmpRepository.selectCoEmpList(coEmpDTO);

            // List to Map 변환
            resultList = empList.stream().collect(Collectors.toMap(CoEmpDTO::getEmpId, i -> i));
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return resultList;
    }

    @Override
    public Integer selectCoEmpListCnt(CoEmpDTO coEmpDTO) {
        Integer resultCnt = null;

        try {
            resultCnt = coEmpRepository.selectCoEmpListCnt(coEmpDTO);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return resultCnt;
    }

    @Override
    public CoEmpDTO selectCoEmp(String empId) {
        CoEmpDTO result = null;

        try {
            result = coEmpRepository.selectCoEmp(empId);

            // 사용하지 않음으로 주석 처리 by kwg. 210703
            //			if(result != null) {
            //				// 권한 목록 조회
            //				List<CoEmpAuthDTO> authList = authRepository.selectCoEmpAuthList(CoEmpAuthDTO.builder().empId(empId).build());
            //
            //				// 접속가능 메뉴목록 조회
            //				if(authList != null && authList.size() > 0) {
            //					List<Integer> authIds = authList.stream().map(CoEmpAuthDTO::getAuthId).collect(Collectors.toList());
            //					result.setAuthIds(authIds);
            //
            //					CoAuthMenuDTO paramDTO = new CoAuthMenuDTO();
            //					paramDTO.setAuthIds(authIds);
            //
            //					List<CoAuthMenuDTO> menuList = authRepository.selectCoAuthMenuList(paramDTO);
            //					result.setMenuIds(menuList.stream().distinct().map(CoAuthMenuDTO::getMenuId).collect(Collectors.toList()));
            //				}
            //			}

        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return result;
    }

    @Override
    public List<CoEmpAuthDTO> selectCoEmpAuthList(String empId) {
        List<CoEmpAuthDTO> resultList = null;

        try {
            // 권한 목록 조회
            resultList = authRepository.selectCoEmpAuthList(CoEmpAuthDTO.builder().empId(empId).build());
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return resultList;
    }

    // 상신문서 docId 로 상신자의 휴대전화번호를 찾아온다.
    @Override
    public String selectTelByDocId(Integer docId) {
        String tel = null;

        try {
            tel = coEmpRepository.selectTelByDocId(docId);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return tel;
    }

    // 메인 팀장,담당자 정보 조회
    @Override
    public List<CoEmpDTO> selectMainScMgrList(String empId) {
        List<CoEmpDTO> result = null;

        try {
            result = coEmpRepository.selectMainScMgrList(empId);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return result;
    }

    // 메인 보안활동 정보 조회(mgr)
    @Override
    public CoEmpStatDTO selectMainMgrStatList(String empId) {
        CoEmpStatDTO result = null;

        try {
            result = coEmpRepository.selectMainMgrStatList(empId);
            if (result == null)
                result = new CoEmpStatDTO();
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return result;
    }

    // 메인 보안활동 정보 조회(sc)
    @Override
    public CoEmpStatDTO selectMainScStatList(String empId) {
        CoEmpStatDTO result = null;

        try {
            result = coEmpRepository.selectMainScStatList(empId);
            if (result == null)
                result = new CoEmpStatDTO();
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return result;
    }

    @Override
    public List<CoEmpDTO> selectCoXEmpList(CoEmpDTO coEmpDTO) {
        List<CoEmpDTO> resultList = null;

        try {
            resultList = coEmpRepository.selectCoXEmpList(coEmpDTO);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return resultList;
    }

    /**
     * 구성원 차량정보 목록 조회
     *
     * @param coEmpCarInfoViewSearchDTO
     * @return List<CoEmpCarInfoViewDTO>
     */
    @Override
    public List<CoEmpCarInfoViewDTO> selectCoEmpCarInfoViewList(CoEmpCarInfoViewSearchDTO coEmpCarInfoViewSearchDTO) {

        List<CoEmpCarInfoViewDTO> resultList = null;

        try {
            resultList = coEmpRepository.selectCoEmpCarInfoViewList(coEmpCarInfoViewSearchDTO);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return resultList;
    }

    @Override
    public List<Map<String, Object>> selectMultiCoEmpInfoList(Map<String, Object> paramMap) {

        List<Map<String, Object>> resultList = null;

        try {
            resultList = coEmpRepository.selectMultiCoEmpInfoList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return resultList;
    }

}