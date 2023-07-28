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
@Schema(description = "구성원 차량정보 조회")
public class CoEmpCarInfoViewSearchDTO extends CommonDTO {

    @Schema(description = "사번")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String searchEmpId;

    @Schema(description = "성명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String searchEmpNm;

    @Schema(description = "부서명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String searchDeptNm;

}
