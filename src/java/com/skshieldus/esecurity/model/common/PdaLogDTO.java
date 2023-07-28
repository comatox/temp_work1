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
@Schema(description = "PDA 로그")
public class PdaLogDTO extends CommonDTO {

    @Schema(description = "data1")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String data1;

    @Schema(description = "data2")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String data2;

    @Schema(description = "data3")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String data3;

    @Schema(description = "data4")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String data4;

    @Schema(description = "data5")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String data5;

}
