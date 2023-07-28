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
@Schema(description = "도급업체 회원정보 조회")
public class IoEmpSubcontSearchDTO extends CommonDTO {

    @Schema(description = "pagingYn")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String pagingYn = "N";

    @Schema(description = "currentPage")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private int currentPage = 1;

    @Schema(description = "rowPerPage")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private int rowPerPage = 10;

    @Schema(description = "ioEmpId")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String ioEmpId;

    @Schema(description = "ioEmpNm")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String ioEmpNm;

    @Schema(description = "ioCompId")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String ioCompId;

    @Schema(description = "ioCompNm")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String ioCompNm;

}
