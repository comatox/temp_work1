package com.skshieldus.esecurity.repository.secrtactvy.idcardvisit;

import com.skshieldus.esecurity.config.datasource.annotation.IdcardVisitMapper;
import java.util.Map;

@IdcardVisitMapper
public interface IdcardVisitRepository {

    int dmSecIoEmp_Violation_OutProc_IF(Map<String, Object> ifData);

    int dmPassExcptIF_IoPassExprHist(Map<String, Object> ds);

    // 외부인 보안 위규자 상세 > 출입 외부 제한 - 하이스텍 정지신청 하는 I/F Procedure 호출  - callOffLimitsIoPassExprHist
    int callOffLimitsIoPassExprHist(Map<String, Object> ds);

}
