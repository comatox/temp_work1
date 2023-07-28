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
@Schema(description = "부서별 결재선 지정자")
public class SpecifiedApproverLineDTO extends CommonDTO {

    @Schema(description = "상신사업장ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String compId;

    @Schema(description = "승인자직위ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String apprJwId;

    @Schema(description = "승인자직위명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String apprJwNm;

    @Schema(description = "승인자직책ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String apprJcId;

    @Schema(description = "승인자직책명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String apprJcNm;

    @Schema(description = "승인자부서ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String apprDeptId;

    @Schema(description = "승인자부서명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String apprDeptNm;

    @Schema(description = "승인자직원ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String apprEmpId;

    @Schema(description = "승인자직원명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String apprEmpNm;

    @Schema(description = "승인자부서DIV명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String apprDivNm;

    @Schema(description = "자동결재선추가 1:자동결재, 0:수동결재")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String autoSign;

    @Schema(description = "직책순")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String jcGrade;

    @Schema(description = "직위순")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String jwGrade;

    @Schema(description = "순번")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String orderSeq;

    @Schema(description = "위임여부")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String entrustYn;

    @Schema(description = "위임자부서ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String entrustDeptId;

    @Schema(description = "위임자직위ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String entrustJwId;

    @Schema(description = "위임자직책ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String entrustJcId;

    @Schema(description = "위임자직원ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String entrustEmpId;

    @Schema(description = "위임자부서명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String entrustDeptNm;

    @Schema(description = "위임자직위명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String entrustJwNm;

    @Schema(description = "위임자직책명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String entrustJcNm;

    @Schema(description = "위임자직원명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String entrustEmpNm;

    @Schema(description = "도급업체일 경우 필수로 들어가야 할 허가부서 결재라인")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String subcontYn;

    @Schema(description = "결재차수")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String apSeq;

    @Schema(description = "(담당,팀장,팀장지정자 조회에서만 사용됨)역할구분코드 - 1:팀장, 2:지정자")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String gbnCd;

    @Schema(description = "허가부서 결재선 설명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String leaveapprovalname;

    @Schema(description = "사전검토자1 직원ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String preAppr1;

    @Schema(description = "사전검토자1 직원명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String preApprNm1;

    @Schema(description = "사전검토자1 직위ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String preApprJwId1;

    @Schema(description = "사전검토자1 직위명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String preApprJwNm1;

    @Schema(description = "사전검토자1 부서ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String preApprDeptId1;

    @Schema(description = "사전검토자1 부서명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String preApprDeptNm1;

}
