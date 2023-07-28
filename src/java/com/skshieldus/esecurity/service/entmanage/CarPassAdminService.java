package com.skshieldus.esecurity.service.entmanage;

import com.skshieldus.esecurity.model.common.CommonXlsViewDTO;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import java.util.Map;

public interface CarPassAdminService {

    // 방문차량 쿼터현황
    public Map<String, Object> selectIoCarPassInOutSttsList(Map<String, Object> paramMap);

    // 방문차량 쿼터관리 LIST
    public Map<String, Object> selectIoIcCarQuotaList(Map<String, Object> paramMap);

    // 방문차량 부서 쿼터관리 조회
    public Map<String, Object> selectIoCarQuota(Map<String, Object> paramMap);

    // 방문차량 부서 쿼터관리 업데이트
    public boolean updateIoCarQuota(SessionInfoVO sessionInfo, Map<String, Object> paramMap);

    // 방문차량 부서 쿼터관리 삭제컬럼 업데이트
    public boolean updateIoCarQuotaDelYn(SessionInfoVO sessionInfo, Map<String, Object> paramMap);

    // 차량출입관리 LIST
    public Map<String, Object> selectIoCarPassProgressList(Map<String, Object> paramMap);

    // 차량출입관리 엑셀 다운로드
    public CommonXlsViewDTO selectIoCarPassProgressListExcel(Map<String, Object> paramMap);

    // 차량출입관리 방문객 입/출문 업데이트
    public boolean updateInOut(SessionInfoVO sessionInfo, Map<String, Object> paramMap);

    // 차량출입신청 관리자 LIST
    public Map<String, Object> selectAllCarPassList(Map<String, Object> paramMap);

    // 차량출입신청 관리자 엑셀 다운로드
    public CommonXlsViewDTO selectCarPassAdminListExcel(Map<String, Object> paramMap);

    // 안전작업차량 출입관리 관리자 LIST
    public Map<String, Object> selectSafetyCarPassProgressList(Map<String, Object> paramMap);

    // 안전작업차량 출입관리 관리자 엑셀 다운로드
    public CommonXlsViewDTO selectSafetyCarPassProgressListExcel(Map<String, Object> paramMap);

    // 임시차량 출입관리 관리자 LIST
    public Map<String, Object> selectTmpcarPassProgressList(Map<String, Object> paramMap);

    // 임시차량 출입관리 관리자 엑셀 다운로드
    public CommonXlsViewDTO selectTmpcarPassProgressListExcel(Map<String, Object> paramMap);
}
