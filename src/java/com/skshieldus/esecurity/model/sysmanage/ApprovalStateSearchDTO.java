package com.skshieldus.esecurity.model.sysmanage;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.skshieldus.esecurity.common.model.CommonDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(value = Include.NON_NULL)
@Schema(description = "결재선 미 준수 현황 조회")
public class ApprovalStateSearchDTO extends CommonDTO {

    @Schema(description = "조회기간 시작일자")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String fromDt;

    @Schema(description = "조회기간 종료일자")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String toDt;

    @Schema(description = "결재양식 스키마명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String schemaNm;

    @Schema(description = "결재양식 스키마명")
    @JsonFormat(shape = JsonFormat.Shape.ARRAY)
    private List<String> schemaNms;

}