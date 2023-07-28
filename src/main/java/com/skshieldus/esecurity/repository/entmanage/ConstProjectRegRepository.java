package com.skshieldus.esecurity.repository.entmanage;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface ConstProjectRegRepository {

    /**
     * 공사프로젝트 등록 리스트 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 11. 17.
     */
    List<Map<String, Object>> selectConstProjectRegList(Map<String, Object> paramMap);

    /**
     * 공사프로젝트 등록 현황 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 11. 17.
     */
    Map<String, Object> selectConstProjectRegView(Map<String, Object> paramMap);

    /**
     * 공사프로젝트 등록 출입자 차량 리스트 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 11. 23.
     */
    List<Map<String, Object>> selectConstProjectRegGateList(Map<String, Object> paramMap);

    /**
     * 공사프로젝트 등록 등록
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 11. 17.
     */
    Integer insertConstProjectReg(Map<String, Object> paramMap);

    /**
     * 공사프로젝트 등록 출입자(구성원/외부인) 등록
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 11. 17.
     */
    Integer insertConstProjectRegGate(Map<String, Object> paramMap);

    /**
     * 공사프로젝트 등록 등록시 업무번호 채번
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 11. 17.
     */
    Integer selectConstProjectRegNewSeq(Map<String, Object> paramMap);

    /**
     * 공사프로젝트 등록 - 통합결재 송신 이후 docId업데이트
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 11. 17.
     */
    Integer updateConstProjectRegDocId(Map<String, Object> paramMap);

    /**
     * 공사프로젝트 등록 - 통합결재 후처리 업데이트
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 11. 17.
     */
    Integer updateConstProjectRegApplWork(Map<String, Object> paramMap);

}