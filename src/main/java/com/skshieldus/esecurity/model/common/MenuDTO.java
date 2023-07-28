package com.skshieldus.esecurity.model.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.skshieldus.esecurity.common.model.CommonDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(value = Include.NON_NULL)
@Schema(description = "메뉴정보")
public class MenuDTO extends CommonDTO {

    @Schema(description = "메뉴ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String menuId;

    @Schema(description = "메뉴명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String menuNm;

    @Schema(description = "상위메뉴ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String upMenuId;

    @Schema(description = "순번")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String rownum;

    @Schema(description = "최상위메뉴ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String topMenuId;

    @Schema(description = "정렬순서")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer sortSeq;

    @Schema(description = "신청함YN")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String myAppr;

    @Schema(description = "신청함URL")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String myApprUrl;

    @Schema(description = "신청함명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String myApprNm;

    @Schema(description = "상위메뉴명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String upMenuNm;

    @Schema(description = "메뉴depth")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private int depth;

    @Schema(description = "메뉴URL")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String url;

    @Schema(description = "메뉴이미지명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String imgNm;

    @Schema(description = "서브메뉴갯수")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private int subMenuCount;

    @Schema(description = "사용여부")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String useYn;

    @Schema(description = "개인정보 로그여부")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String prsnlInfoLogYn;

    @Schema(description = "MSA 여부")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String masYn;

    @Schema(description = "메뉴명(path포함)")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String menuLink;

    @Schema(description = "사번")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String empNo;

    @Schema(description = "메뉴ID 목록")
    private List<String> menuIds;

    @Schema(description = "id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String id;

    @Schema(description = "msaFlag")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String msaFlag;

    @Schema(description = "parentid")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String parentid;

    @Schema(description = "prsnlFlag")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String prsnlFlag;

    @Schema(description = "text")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String text;

    @Schema(description = "path")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String path;

    @Schema(description = "treePath")
    @JsonFormat(shape = JsonFormat.Shape.ARRAY)
    private List<String> treePath;

    @Schema(description = "type")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String type;

    @Schema(description = "acIp")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String acIp;

    @Schema(description = "crtBy")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String crtBy;

    @Schema(description = "crtDtm")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String crtDtm;

    @Schema(description = "modBy")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String modBy;

    @Schema(description = "modDtm")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String modDtm;

    // 메뉴 상세항목
    @Schema(description = "approvalYn")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String approvalYn;

    @Schema(description = "displayYn")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String displayYn;

    @Schema(description = "favoritesImg")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String favoritesImg;

    @Schema(description = "iflowUrl")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String iflowUrl;

    @Schema(description = "keyword")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String keyword;

    @Schema(description = "mainYn")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String mainYn;

    @Schema(description = "managerEmpId")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String managerEmpId;

    @Schema(description = "managerName")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String managerName;

    @Schema(description = "menuDesc")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String menuDesc;

    @Schema(description = "msaYn")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String msaYn;

    @Schema(description = "manager")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String manager;

    @Schema(description = "managerNm")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String managerNm;

    @Schema(description = "Index")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer idx;

}