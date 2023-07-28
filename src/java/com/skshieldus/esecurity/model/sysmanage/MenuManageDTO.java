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
@Schema(description = "메뉴 관리")
public class MenuManageDTO extends CommonDTO {

    @Schema(description = "메뉴 코드")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String menuId;

    @Schema(description = "메뉴명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String menuNm;

    @Schema(description = "상위 메뉴 코드")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String upMenuId;

    @Schema(description = "상위메뉴 명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String upMenuNm;

    @Schema(description = "메뉴 depth")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer depth;

    @Schema(description = "메뉴 URL")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String url;

    @Schema(description = "정렬순서")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer sortSeq;

    @Schema(description = "사용 유무")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String useYn;

    @Schema(description = "접근 IP")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String acIp;

    @Schema(description = "등록자 사번")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String crtBy;

    @Schema(description = "등록일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String crtDtm;

    @Schema(description = "수정자 사번")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String modBy;

    @Schema(description = "수정일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String modDtm;

    @Schema(description = "하위메뉴 Count")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer subMenuCount;

    @Schema(description = "이미지 명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String imgNm;

    @Schema(description = "표시 여부")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String displayYn;

    @Schema(description = "결재 여부")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String approvalYn;

}