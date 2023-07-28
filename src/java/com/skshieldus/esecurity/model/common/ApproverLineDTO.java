package com.skshieldus.esecurity.model.common;

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
@Schema(description = "결재선")
public class ApproverLineDTO extends CommonDTO {

    @Schema(description = "결재문서")
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    private ApprovalDocDTO approvalDoc;

    @Schema(description = "요청부서 부서별 결재선 지정자 목록")
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    private List<SpecifiedApproverLineDTO> requestSpecifiedApproverLine;

    @Schema(description = "허가부서 부서별 결재선 지정자 목록")
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    private List<SpecifiedApproverLineDTO> permitSpecifiedApproverLine;

    @Schema(description = "저장된 요청부서 결재선 목록")
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    private List<SavedApproverLineDTO> savedRequestApproverLine;

    @Schema(description = "저장된 허가부서 결재선 목록")
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    private List<SavedApproverLineDTO> savedPermitApproverLine;

}
