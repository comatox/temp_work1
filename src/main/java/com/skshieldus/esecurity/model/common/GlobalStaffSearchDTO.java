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
@Schema(description = "GlobalStaff 조회 조건 정보")
public class GlobalStaffSearchDTO extends CommonDTO {

    @Schema(description = "사번")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String empId;

    @Schema(description = "이름")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String empNm;

    @Schema(description = "Request type (A063) A0630004: 우시/충칭, A0630005: 기타법인")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String userGbn;

}
