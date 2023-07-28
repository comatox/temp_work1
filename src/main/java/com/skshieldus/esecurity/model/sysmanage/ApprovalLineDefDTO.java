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
@Schema(description = "결재선 정의 DTO")
public class ApprovalLineDefDTO extends CommonDTO {

    @Schema(description = "결재선정의KEY")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer defSeq;

    @Schema(description = "메뉴ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String menuId;

    @Schema(description = "메뉴명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String menuNm;

    @Schema(description = "상위 메뉴명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String upMenuNm;

    @Schema(description = "상신캠퍼스ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String compId;

    @Schema(description = "상신캠퍼스명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String compNm;

    @Schema(description = "상신부서ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String deptId;

    @Schema(description = "상신부서명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String deptNm;

    @Schema(description = "요청:1 , 허가부서:2 구분")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer apprDeptGbn;

    @Schema(description = "요청:1 , 허가부서:2 구분명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String apprDeptGbnNm;

    @Schema(description = "결재차수")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer apSeq;

    @Schema(description = "구분")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String gbn;

    @Schema(description = "구분명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String gbnNm;

    @Schema(description = "EQUAL = GREAT >=")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String equalGbn;

    @Schema(description = "EQUAL = GREAT >= 명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String equalGbnNm;

    @Schema(description = "결재자ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String gbnVal;

    @Schema(description = "결재자명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String gbnValNm;

    @Schema(description = "결재자직위명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String apprEmpJwNm;

    @Schema(description = "결재자부서명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String apprEmpDeptNm;

    @Schema(description = "자동결재 1:자동결재, 0:수동결재")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String autoSign;

    @Schema(description = "사용여부")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String useYn;

    @Schema(description = "사용여부명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String useYnNm;

    @Schema(description = "등록자")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String crtBy;

    @Schema(description = "등록일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String crtDtm;

    @Schema(description = "수정자")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String modBy;

    @Schema(description = "수정일")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String modDtm;

    @Schema(description = "도급업체일 경우 필수로 들어가야 할 허가부서 결재라인")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String subcontYn;

}
