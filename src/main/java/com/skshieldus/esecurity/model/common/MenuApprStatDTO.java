package com.skshieldus.esecurity.model.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.skshieldus.esecurity.common.model.CommonDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "신청함 신청건수")
public class MenuApprStatDTO extends CommonDTO {

    @Schema(description = "")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer requestCount = 0;

    @Schema(description = "")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer receiveVst = 0;

    @Schema(description = "")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer receiveTmp = 0;

    @Schema(description = "")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer receiveReg = 0;

    @Schema(description = "")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer receiveComp = 0;

    @Schema(description = "")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer visitCertiCount = 0;

    @Schema(description = "")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer excptCount = 0;

    @Schema(description = "")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer stopCount = 0;

    @Schema(description = "")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer domoveCount = 0;

    @Schema(description = "")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer adminCount = 0;

    @Schema(description = "")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer picCount = 0;

    @Schema(description = "")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer ioinoutCount = 0;

}