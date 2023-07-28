package com.skshieldus.esecurity.repository.entmanage;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface CarPassAdminRepository {

    /**
     * 방문차량 쿼터현황 리스트 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 12. 01.
     */
    List<Map<String, Object>> selectIoCarPassInOutSttsList(Map<String, Object> paramMap);

    /**
     * 방문차량 쿼터현황 리스트 카운트 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 12. 01.
     */
    int selectIoCarPassInOutSttsListCnt(Map<String, Object> paramMap);

    /**
     * 방문차량 쿼터관리 리스트 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 12. 01.
     */
    List<Map<String, Object>> selectIoIcCarQuotaList(Map<String, Object> paramMap);

    /**
     * 방문차량 쿼터관리 리스트 카운트 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 12. 01.
     */
    int selectIoIcCarQuotaListCnt(Map<String, Object> paramMap);

    /**
     * 방문차량 부서 쿼터 관리 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 12. 01.
     */
    Map<String, Object> selectIoCarQuota(Map<String, Object> paramMap);

    /**
     * 방문차량 부서 쿼터 관리 업데이트
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 12. 01.
     */
    Integer updateIoCarQuota(Map<String, Object> paramMap);

    /**
     * 방문차량 부서 쿼터 관리 삭제컬럼 업데이트
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 12. 01.
     */
    Integer updateIoCarQuotaDelYn(Map<String, Object> paramMap);

    /**
     * 차량출입관리 리스트 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 12. 08.
     */
    List<Map<String, Object>> selectIoCarPassProgressList(Map<String, Object> paramMap);

    /**
     * 차량출입관리 리스트 카운트 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2023. 07. 14.
     */
    int selectIoCarPassProgressListCnt(Map<String, Object> paramMap);

    /**
     * 차량출입관리 - 방문객 입문/출문 업데이트
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 12. 09.
     */
    Integer updateInOut(Map<String, Object> paramMap);

    /**
     * 차량출입신청 관리자 리스트 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 12. 09.
     */
    List<Map<String, Object>> selectAllCarPassList(Map<String, Object> paramMap);

    /**
     * 차량출입신청 관리자 리스트 엑셀 다운로드
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 12. 09.
     */
    List<Map<String, Object>> selectAllCarPassListExcel(Map<String, Object> paramMap);

    /**
     * 차량출입신청 관리자 리스트 카운트 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2023. 07. 25.
     */
    int selectAllCarPassListCnt(Map<String, Object> paramMap);

    /**
     * 안전작업차량 출입현황 관리자 리스트 조회 / 엑셀 다운로드
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 12. 09.
     */
    List<Map<String, Object>> selectSafetyCarPassProgressList(Map<String, Object> paramMap);

    /**
     * 안전작업차량 출입현황 리스트 카운트 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2023. 07. 25.
     */
    int selectSafetyCarPassProgressListCnt(Map<String, Object> paramMap);

    /**
     * 안전작업차량 출입현황 관리자 리스트 엑셀 다운로드
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 12. 09.
     */
    List<Map<String, Object>> selectSafetyCarPassProgressListExcel(Map<String, Object> paramMap);

    /**
     * 임시차량 출입관리 관리자 리스트 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2023. 07. 25.
     */
    List<Map<String, Object>> selectTmpcarPassProgressList(Map<String, Object> paramMap);

    /**
     * 임시차량 출입관리 관리자 리스트 엑셀 다운로드
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2023. 07. 25.
     */
    List<Map<String, Object>> selectTmpcarPassProgressListExcel(Map<String, Object> paramMap);

    /**
     * 임시차량 출입관리 관리자 리스트 카운트 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2023. 07. 25.
     */
    int selectTmpcarPassProgressListCnt(Map<String, Object> paramMap);
}