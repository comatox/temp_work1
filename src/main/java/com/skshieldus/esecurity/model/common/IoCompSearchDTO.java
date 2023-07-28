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
@Schema(description = "외부업체 검색 DTO")
public class IoCompSearchDTO extends CommonDTO {

    @Schema(description = "업체명(한글)")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String compKoNm;

    @Schema(description = "사업자등록번호")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String compId;

    @Schema(description = "업체구분 (1,2,3,4)")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String compType;

    @Schema(description = "도급업체여부 (Y/N)")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String subcontYn;

    @Schema(description = "페이징 여부")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String pagingYn = "N";

    @Schema(description = "페이지번호")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private int currentPage = 1;

    @Schema(description = "페이지당출력개수")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private int rowPerPage = 1;

}
