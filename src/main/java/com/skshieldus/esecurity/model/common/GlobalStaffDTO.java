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
@Schema(description = "GlobalStaff 인사정보")
public class GlobalStaffDTO extends CommonDTO {

    @Schema(description = "회사코드")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String companyCode;

    @Schema(description = "회사명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String companyName;

    @Schema(description = "사번")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String empNum;

    @Schema(description = "사원명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String empNam;

    @Schema(description = "사원영문명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String empEngNam;

    @Schema(description = "직급코드")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String jtitCd;

    @Schema(description = "직급명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String jtitNam;

    @Schema(description = "e-mail")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String emailAddr;

}
