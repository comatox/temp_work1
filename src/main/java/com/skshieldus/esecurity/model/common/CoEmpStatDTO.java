package com.skshieldus.esecurity.model.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.skshieldus.esecurity.common.model.CommonDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "보안활동")
public class CoEmpStatDTO extends CommonDTO {

    @Schema(description = "")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer ofendCnt = 0;

    @Schema(description = "")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer ioOfendCnt = 0;

    @Schema(description = "")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer bepCnt = 0;

    @Schema(description = "")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer delayCnt = 0;

    @Schema(description = "")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer visitCnt = 0;

    @Schema(description = "")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer passCnt = 0;

}
