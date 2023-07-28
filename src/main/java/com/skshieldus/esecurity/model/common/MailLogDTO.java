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
@Schema(description = "메일전송 이력")
public class MailLogDTO extends CommonDTO {

    @Schema(description = "이메일주소")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String email;

    @Schema(description = "사원ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String empId;

    @Schema(description = "스키마명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String schemaNm;

    @Schema(description = "메일본문")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String content;

    @Schema(description = "전송여부")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String sendYn;

    @Schema(description = "")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String apprDeptKnd;

    @Schema(description = "문서ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String docId;

    @Schema(description = "접속IP")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String acIp;

    @Schema(description = "등록자")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String crtBy;

}
