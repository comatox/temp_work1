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
@Schema(description = "결재문서")
public class ApprovalDocDTO extends CommonDTO {

    @Schema(description = "결재문서ID")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer docId;

    @Schema(description = "스키마명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String schemaNm;

    @Schema(description = "결재순번")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer apSeq;

    @Schema(description = "각 업무별 업무번호 LID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String lid;

    @Schema(description = "업체ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String compId;

    @Schema(description = "부서ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String deptId;

    @Schema(description = "직위ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String jwId;

    @Schema(description = "결재직원ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String empId;

    @Schema(description = "결재부서구분")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String apprDeptGbn;

    @Schema(description = "승인일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String apprDtm;

    @Schema(description = "결재상태 0대기 10진행 20완료")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String apprStat;

    @Schema(description = "결재결과 0결과전 1승인 2부결")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String apprResult;

    @Schema(description = "부결기타 (상신자의 경우 상신의견으로 이 필드가 매핑된다.)")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String canceletc;

    @Schema(description = "신청직원ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String applEmpId;

    @Schema(description = "신청직원명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String applEmpNm;

    @Schema(description = "신청부서ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String applDeptId;

    @Schema(description = "신청부서명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String applDeptNm;

    @Schema(description = "신청직위명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String applJwNm;

    @Schema(description = "상신 의견 (이 필드는 Table(AP_DOC, AP_DOC_PRE)에 존재하지만 사용되지 않는다. 상신 의견도 canceletc 필드 사용)")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String docEtc;

    @Schema(description = "삭제구분")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String delYn;

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
