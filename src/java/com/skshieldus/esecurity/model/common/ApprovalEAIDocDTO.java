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
@Schema(description = "EAI 결재문서")
public class ApprovalEAIDocDTO extends CommonDTO {

    @Schema(description = "P_LEGACY_PK 결재문서ID DOC_ID")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer legacyPk;

    @Schema(description = "P_LEGACY_GUBUN ESecurity")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String legacyGubun;

    @Schema(description = "P_FORM_PREFIX 결재문서 코드")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String fromPrefix;

    @Schema(description = "pINTERFACE_ID AP11-O-002-ESecurity-ESecurity")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String interfaceId;

    @Schema(description = "등록자")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String crtBy;

}