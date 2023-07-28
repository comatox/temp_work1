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
@Schema(description = "EAI 통합결재 원문 HTML의 F_PARAM 정보")
public class ApprovalHtmlParamDTO extends CommonDTO {

    @Schema(description = "APPL_EMP_ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String APPL_EMP_ID;

    @Schema(description = "DEPT_ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String DEPT_ID;

    @Schema(description = "DOC_ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String DOC_ID;

    @Schema(description = "COMP_ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String COMP_ID;

    @Schema(description = "MENU_ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String MENU_ID;

    @Schema(description = "cnfmUrl")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String cnfmUrl;

    @Schema(description = "APPL_DEPT_NM")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String APPL_DEPT_NM;

    @Schema(description = "APPL_JW_NM")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String APPL_JW_NM;

    @Schema(description = "EMP_ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String EMP_ID;

    @Schema(description = "AC_IP")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String AC_IP;

    @Schema(description = "APPL_EMP_NM")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String APPL_EMP_NM;

    @Schema(description = "DOC_NM")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String DOC_NM;

    @Schema(description = "APPL_DEPT_ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String APPL_DEPT_ID;

    @Schema(description = "SCHEMA_NM")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String SCHEMA_NM;

    @Schema(description = "LID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String LID;

}