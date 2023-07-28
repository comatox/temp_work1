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
@Schema(description = "업체물품 반입")
public class IoInoutHistoryDTO extends CommonDTO {

    @Schema(description = "반출입신청번호")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer inoutApplNo;

    @Schema(description = "inoutModNo")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer inoutModNo;

    @Schema(description = "반출입번호")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String inoutserialno;

    @Schema(description = "pcYn")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String pcYn;

}
