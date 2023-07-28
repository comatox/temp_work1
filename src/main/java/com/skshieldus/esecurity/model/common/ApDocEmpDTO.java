package com.skshieldus.esecurity.model.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.skshieldus.esecurity.common.model.CommonDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "결재진행현황 사번정보")
public class ApDocEmpDTO extends CommonDTO {

    @Schema(description = "결재문서ID")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private String docId;

    @Schema(description = "요청 - 진행중")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String publish0;

    @Schema(description = "요청 - 승인")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String publish1;

    @Schema(description = "요청 - 부결")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String publish2;

    @Schema(description = "승인 - 진행중")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String leave0;

    @Schema(description = "승인 - 승인")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String leave1;

    @Schema(description = "승인 - 부결")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String leave2;

    @Schema(description = "요청 - 진행중")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String publishId0;

    @Schema(description = "요청 - 승인")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String publishId1;

    @Schema(description = "요청 - 부결")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String publishId2;

    @Schema(description = "승인 - 진행중")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String leaveId0;

    @Schema(description = "승인 - 승인")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String leaveId1;

    @Schema(description = "승인 - 부결")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String leaveId2;

    @Schema(description = "")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String publishEmpnm1;

    @Schema(description = "")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String publishJwnm1;

    @Schema(description = "")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String publishDeptnm1;

}