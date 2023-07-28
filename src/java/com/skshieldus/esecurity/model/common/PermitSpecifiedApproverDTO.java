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
@Schema(description = "자산반출입 허가부서결재자")
public class PermitSpecifiedApproverDTO extends CommonDTO {

    @Schema(description = "결재 ID")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer apprdefNo;

    @Schema(description = "결재 순번")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer apprSeq;

    @Schema(description = "품목 그룹")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String articleKndNo;

    @Schema(description = "품목 그룹명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String articleKndNm;

    @Schema(description = "물품 코드")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String articleGroupId;

    @Schema(description = "물품 코드명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String articleGroupNm;

    @Schema(description = "결재 명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String apprName;

    @Schema(description = "결재자 사번")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String empId;

    @Schema(description = "결재자명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String empNm;

    @Schema(description = "사용유무")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String useYn;

    @Schema(description = "사용유무명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String useYnNm;

    @Schema(description = "생성자")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String crtBy;

    @Schema(description = "생성일")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String crtDtm;

    @Schema(description = "수정자")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String modBy;

    @Schema(description = "수정일")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String modDtm;

    @Schema(description = "자사 결재 필수 체크")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String hynixYn;

    @Schema(description = "외부업체 결재 필수 체크")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String companyYn;

    @Schema(description = "반입필요 결재 필수 체크")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String inoutKnd1;

    @Schema(description = "반입불요(무상) 결재 필수 체크")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String inoutKnd2;

    @Schema(description = "반입불요(유상) 결재 필수 체크")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String inoutKnd3;

    @Schema(description = "Wafer pattern/non Pattern 결재 필수 체크")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String inoutKnd4;

    @Schema(description = "허가자승인이전 검토1 사번")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String preAppr1;

}
