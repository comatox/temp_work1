package com.skshieldus.esecurity.model.sysmanage;

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
@Schema(description = "결재선 미 준수 현황 DTO")
public class ApprovalStateDTO extends CommonDTO {

    @Schema(description = "번호")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer rowNum;

    @Schema(description = "스키마명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String schemaNm;

    @Schema(description = "항목")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String docNm;

    @Schema(description = "신청일")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String crtDtm;

    @Schema(description = "최종 승인자")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String apprEmpNm;

    @Schema(description = "최종 승인자 직책")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String apprJcNm;

    @Schema(description = "최종 승인일")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String apprDtm;

    @Schema(description = "허가부서 승인자")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String permitEmpNm;

    @Schema(description = "허가부서 승인자 직책")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String permitJcNm;

    @Schema(description = "허가부서 승인일")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String permitDtm;

    @Schema(description = "팀장 지정자 여부")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String appointYn;

    @Schema(description = "실제 승인자 직책")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer gradeReal;

    @Schema(description = "최종 승인권자 직책")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer gradeOrg;

    @Schema(description = "최종 승인권자 직책명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String gradeOrgNm;

    @Schema(description = "승인자 전체")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String apprAllEmp;

    @Schema(description = "허가부서 전체")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String permitAllEmp;

    @Schema(description = "허가부서 구분명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String permitGbnNm;

    @Schema(description = "기타")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String remark;

}
