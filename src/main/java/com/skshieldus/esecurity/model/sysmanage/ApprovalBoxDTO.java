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
@Schema(description = "통합결재함")
public class ApprovalBoxDTO extends CommonDTO {

    @Schema(description = "docNm")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String docNm;

    @Schema(description = "docUrl")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String docUrl;

    @Schema(description = "docUrlParam")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String docUrlParam;

    @Schema(description = "docOdlUrl")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String docOldUrl;

    @Schema(description = "docId")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer docId;

    @Schema(description = "schemaNm")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String schemaNm;

    @Schema(description = "crtDtm")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String crtDtm;

    @Schema(description = "apprStat")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String apprStat;

    @Schema(description = "apprStatNm")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String apprStatNm;

    @Schema(description = "apprResult")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String apprResult;

    @Schema(description = "apprResultNm")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String apprResultNm;

    @Schema(description = "직원ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String empId;

    @Schema(description = "직원명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String empNm;

    @Schema(description = "직위ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String jwId;

    @Schema(description = "직위명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String jwNm;

    @Schema(description = "부서ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String deptId;

    @Schema(description = "부서명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String deptNm;

    @Schema(description = "캠퍼스ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String compId;

    @Schema(description = "캠퍼스명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String compNm;

    @Schema(description = "applJwNm")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String applJwNm;

    @Schema(description = "applDeptNm")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String applDeptNm;

    @Schema(description = "reqEmpId")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String reqEmpId;

    @Schema(description = "reqEmpNm")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String reqEmpNm;

    @Schema(description = "reqCompNm")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String reqCompNm;

    @Schema(description = "modDtm")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String modDtm;

    @Schema(description = "apprDtm")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String apprDtm;

    @Schema(description = "lid")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String lid;

    @Schema(description = "applEmpNm")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String applEmpNm;

    @Schema(description = "apprGbn")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String apprGbn;

    @Schema(description = "applyGbn")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String applyGbn;

}