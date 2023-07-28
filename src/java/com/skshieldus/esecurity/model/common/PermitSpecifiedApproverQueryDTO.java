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
@Schema(description = "자산반출입 허가부서결재자 현황 조회 조건")
public class PermitSpecifiedApproverQueryDTO extends CommonDTO {

    @Schema(description = "품목 그룹")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String articleKndNo;

    @Schema(description = "물품 코드")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String articleGroupId;

    @Schema(description = "결재자명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String empNm;

    @Schema(description = "사용유무")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String useYn;

}
