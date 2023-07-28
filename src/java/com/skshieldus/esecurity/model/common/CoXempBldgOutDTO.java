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
@Schema(description = "건물보안출입문 (건물정보)")
public class CoXempBldgOutDTO extends CommonDTO {

    @Schema(description = "건물보안출입문번호")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Integer coXempBuildingOutApplNo;

    @Schema(description = "출입문ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String gateId;

    @Schema(description = "출입문명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String gateName;

}
