package com.skshieldus.esecurity.service.common;

import com.skshieldus.esecurity.common.model.ListDTO;
import com.skshieldus.esecurity.model.common.*;
import java.util.List;
import java.util.Map;

public interface IoCompService {

    /**
     * 외부업체 회원 목록 조회
     *
     * @author : HoonLee
     */
    ListDTO<Map<String, Object>> selectIoEmpList(Map<String, Object> paramMap);

    /**
     * 외부업체 회원 정보 조회
     *
     * @param ioEmpId
     * @return
     *
     * @author : X0121126<woogon.kim1@partner.sk.com>
     * @since : 2021. 4. 27.
     */
    IoEmpDTO selectIoEmp(String ioEmpId);

    /**
     * 외부업체 목록 조회
     *
     * @param paramDTO
     * @return
     *
     * @author : X0121126<woogon.kim1@partner.sk.com>
     * @since : 2021. 11. 11.
     */
    List<IoCompDTO> selectIoCompList(IoCompSearchDTO paramDTO);

    /**
     * 외부업체 목록 건수조회
     *
     * @param paramDTO
     * @return
     *
     * @author : X0121126<woogon.kim1@partner.sk.com>
     * @since : 2021. 11. 11.
     */
    Integer selectIoCompListCnt(IoCompSearchDTO paramDTO);

    /**
     * 외부업체 정보 조회
     *
     * @param ioCompId
     * @return
     *
     * @author : X0121126<woogon.kim1@partner.sk.com>
     * @since : 2021. 11. 11.
     */
    IoCompDTO selectIoComp(String ioCompId);

    /**
     * @param coEmpCarInfoViewSearchDTO
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 11. 12.
     */
    List<IoEmpCarInfoViewDTO> selectIoEmpCarInfoViewList(IoEmpCarInfoViewSearchDTO coEmpCarInfoViewSearchDTO);

    /**
     * 도급업체 회원정보 목록 조회
     * @author : HoonLee
     */
    ListDTO<Map<String, Object>> selectIoEmpSubcontList(Map<String, Object> paramMap);

    /**
     * 도급업체 회원정보 목록 건수 조회
     * @author : HoonLee
     */
    Integer selectIoEmpSubcontListCnt(Map<String, Object> paramMap);

}
