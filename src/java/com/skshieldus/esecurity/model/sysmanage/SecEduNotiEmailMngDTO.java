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
@Schema(description = "보안교육안내메일관리")
public class SecEduNotiEmailMngDTO extends CommonDTO {

    @Schema(description = "캠퍼스 ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String compId;

    @Schema(description = "이메일 구분코드")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String emailGbn;

    @Schema(description = "제목")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String title;

    @Schema(description = "내용")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String content;

    @Schema(description = "등록자")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String crtBy;

}