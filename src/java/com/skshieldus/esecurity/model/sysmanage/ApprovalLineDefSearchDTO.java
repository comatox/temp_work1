package com.skshieldus.esecurity.model.sysmanage;

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
@Schema(description = "결재선 관리 조회 DTO")
public class ApprovalLineDefSearchDTO extends CommonDTO {

    @Schema(description = "메뉴명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String searchMenuNm;

    @Schema(description = "상신부서명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String searchDeptNm;

    @Schema(description = "상신캠퍼스ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String searchCompId;

    @Schema(description = "요청:1 , 허가부서:2 구분")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer searchApprDeptGbn;

    @Schema(description = "사용여부")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String searchUseYn;

    @Schema(description = "결재자명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String searchGbnValNm;

}
