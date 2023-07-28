package com.skshieldus.esecurity.model.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.skshieldus.esecurity.common.model.CommonDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@JsonInclude(value = Include.NON_NULL)
@Schema(description = "구성원 차량정보")
public class CoEmpCarInfoViewDTO extends CommonDTO {

    @Schema(description = "compId")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String compId;

    @Schema(description = "compNm")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String compNm;

    @Schema(description = "deptId")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String deptId;

    @Schema(description = "deptNm")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String deptNm;

    @Schema(description = "email")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String email;

    @Schema(description = "empId")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String empId;

    @Schema(description = "empNm")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String empNm;

    @Schema(description = "hcCarKnd")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String hcCarKnd;

    @Schema(description = "hcCarNo")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String hcCarNo;

    @Schema(description = "hpNo")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String hpNo;

    @Schema(description = "ibCarKnd")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String ibCarKnd;

    @Schema(description = "ibCarNo")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String ibCarNo;

    @Schema(description = "idcardId")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String idcardId;

    @Schema(description = "ieoApplyDt")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String ieoApplyDt;

    @Schema(description = "ieoApplyGbn")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String ieoApplyGbn;

    @Schema(description = "ieoStatus")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String ieoStatus;

    @Schema(description = "jwId")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String jwId;

    @Schema(description = "jwNm")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String jwNm;

    @Schema(description = "rowNum")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private String rowNum;

    @Schema(description = "telNo")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String telNo;

    @Schema(description = "telNo1")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String telNo1;

}
