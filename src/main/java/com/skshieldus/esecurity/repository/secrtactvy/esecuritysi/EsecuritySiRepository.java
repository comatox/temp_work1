package com.skshieldus.esecurity.repository.secrtactvy.esecuritysi;

import com.skshieldus.esecurity.config.datasource.annotation.EsecuritySiMapper;
import java.util.Map;

@EsecuritySiMapper
public interface EsecuritySiRepository {

    int dmSecIoEmp_Violation_PassExprHist_Seq_S();

    int dmSecIoEmp_Violation_PassExprHist_IEtc(Map<String, Object> paramMap);

    Map<String, Object> dmSecIoEmp_Violation_Idcard_InfoEtc(Map<String, Object> paramMap);

    // 외부인 보안 위규자 상세 > 출입 외부 제한 이력 Seq  - offLimitsIoPassExprHistSeq : string
    String offLimitsIoPassExprHistSeq(Map<String, Object> paramMap);

    // 외부인 보안 위규자 상세 > 출입 외부 제한 이력 등록  - offLimitsExprHistoryInsert : insert
    int offLimitsExprHistoryInsert(Map<String, Object> paramMap);

}
