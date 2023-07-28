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
@Schema(description = "외부업체 회원정보 검색 DTO")
public class IoEmpSearchDTO extends CommonDTO {

    @Schema(description = "회원ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String searchIoEmpId;

    @Schema(description = "회원명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String searchIoEmpNm;

    @Schema(description = "업체ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String searchIoCompId;

    @Schema(description = "업체한글명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String searchIoCompNm;

}
