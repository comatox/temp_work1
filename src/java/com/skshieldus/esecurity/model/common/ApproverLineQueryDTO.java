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
@Schema(description = "결재선 조회 조건")
public class ApproverLineQueryDTO extends CommonDTO {

    @Schema(description = "결재문서ID")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer docId;

    @Schema(description = "메뉴ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String menuId;

    @Schema(description = "부서ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String deptId;

    @Schema(description = "사업장ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String compId;

    @Schema(description = "품목구분 [물품, 휴대용전산장치, 문서, Wafer/Substrate, 원자재/완제품/PKG Chip] (자산반출입 메뉴에서 사용)")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer articlekndno;

    @Schema(description = "품목그룹ID")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer articlegroupid;

    @Schema(description = "상대처구분")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer outcompanyknd;

    @Schema(description = "반입 구분")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String inoutknd;

    @Schema(description = "패턴 구분")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String patternknd;

    @Schema(description = "위반상세구분 시정계획서, 경고장개선계획서에서만 사용 (메뉴ID: P03010402) 1차 MSA 범위 아님")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String ofendDetailGbn;

    @Schema(description = "위반사업장ID 시정계획서, 경고장개선계획서에서만 사용 (메뉴ID: P03010402) 1차 MSA 범위 아님")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String ofendCompId;

    @Schema(description = "문서구분: 시정계획서(C0280002), 경고장개선계획서(C0280003) (메뉴ID: P03010402) 1차 MSA 범위 아님")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String actDo;

    @Schema(description = "검색구분")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String searchType;

    @Schema(description = "사원명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String empNm;

    @Schema(description = "사번")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String empId;

    @Schema(description = "divCd")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String divCd;

    @Schema(description = "empAuth")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String empAuth;

}
