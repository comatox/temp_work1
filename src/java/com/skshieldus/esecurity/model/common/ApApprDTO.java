package com.skshieldus.esecurity.model.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.skshieldus.esecurity.common.model.CommonDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(value = Include.NON_NULL)
@Schema(description = "결재(AP_APPR)")
public class ApApprDTO extends CommonDTO {

    @Schema(description = "결재문서ID")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer docId;

    @Schema(description = "결재순번")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer apSeq;

    @Schema(description = "결재부서구분")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String apprDeptGbn;

    @Schema(description = "캠퍼스ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String compId;

    @Schema(description = "캠퍼스명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String compNm;

    @Schema(description = "부서ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String deptId;

    @Schema(description = "부서명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String deptNm;

    @Schema(description = "직위ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String jwId;

    @Schema(description = "직위명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String jwNm;

    @Schema(description = "직책코드")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String jcCd;

    @Schema(description = "직책명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String jcNm;

    @Schema(description = "결재직원ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String empId;

    @Schema(description = "결재직원명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String empNm;

    @Schema(description = "승인일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String apprDtm;

    @Schema(description = "결재결과 0결과전 1승인 2부결")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String apprResult;

    @Schema(description = "부결기타")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String canceletc;

    @Schema(description = "자동결재")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String autoSign;

    @Schema(description = "TRGFLAG")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long trgflag;

    @Schema(description = "삭제구분")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String delYn;

    @Schema(description = "접속IP")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String acIp;

    @Schema(description = "수임자 부서 ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String retrDeptId;

    @Schema(description = "수임자 직위 ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String retrJwId;

    @Schema(description = "수임자 직원 ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String retrEmpId;

    @Schema(description = "수임자 직책코드")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String retrJcCd;

    @Schema(description = "상신자명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String reqEmpNm;

    @Schema(description = "상신자 직위명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String reqJwNm;

    @Schema(description = "상신자 부서명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String reqDeptNm;

    @Schema(description = "entrusEmpId")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String entrusEmpId;

    @Schema(description = "entrusEmpNm")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String entrusEmpNm;

    @Schema(description = "entrusJwNm")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String entrusJwNm;

    @Schema(description = "상신자 비고")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String reqEtc;

    @Schema(description = "등록자")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String crtBy;

    @Schema(description = "등록일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String crtDtm;

    @Schema(description = "수정자")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String modBy;

    @Schema(description = "수정일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String modDtm;

}
