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
@Schema(description = "메뉴검색")
public class MenuSearchDTO extends CommonDTO {

    @Schema(description = "사번")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String empNo;

    @Schema(description = "검색어")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String keyword;

    @Schema(description = "상위메뉴 ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String upMenuId;

    @Schema(description = "메뉴 ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String menuId;

    @Schema(description = "Depth")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer depth;

}