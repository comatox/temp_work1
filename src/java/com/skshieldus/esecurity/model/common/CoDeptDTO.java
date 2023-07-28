package com.skshieldus.esecurity.model.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.skshieldus.esecurity.common.model.CommonDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.sql.Timestamp;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "부서정보")
@JsonInclude(value = Include.NON_NULL)
public class CoDeptDTO extends CommonDTO {

    @Schema(description = "부서")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String deptId;

    @Schema(description = "부서명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String deptNm;

    @Schema(description = "업체ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String compId;

    @Schema(description = "업체명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String compNm;

    @Schema(description = "상위부서ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String updeptId;

    @Schema(description = "상위부서명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String updeptNm;

    @Schema(description = "DIV코드")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String divCd;

    @Schema(description = "DIV명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String divNm;

    @Schema(description = "부서구분 사용_죽은임의")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String gbCd;

    @Schema(description = "부서구분코드_ESECURITY")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String deptDivCd;

    @Schema(description = "부서관리자")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String mgrEmpId;

    @Schema(description = "부서관리자명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String mgrEmpNm;

    @Schema(description = "사용여부", allowableValues = "Y,N")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String useYn;

    @Schema(description = "트리거플래그")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer trgflag;

    @Schema(description = "접속IP")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String acIp;

    @Schema(description = "등록자")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String crtBy;

    @Schema(description = "등록일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", locale = "ko_KR")
    private Timestamp crtDtm;

    @Schema(description = "수정자")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String modBy;

    @Schema(description = "수정일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", locale = "ko_KR")
    private Timestamp modDtm;

    @Schema(description = "")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String orgUnCd;

    @Schema(description = "조직구분 (A:일반,B:대팀제,C:현장조직,0:사용안함)", allowableValues = "A,B,C,0")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String unOrgFlg;

    @Schema(description = "현장조직코드")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String mgeCd;

    @Schema(description = "")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String deptLv1;

    @Schema(description = "")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String deptLv2;

    @Schema(description = "")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String deptLv3;

    @Schema(description = "")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String deptLv4;

    @Schema(description = "")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String deptLv5;

    @Schema(description = "")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String deptLv6;

    @Schema(description = "")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String deptLv7;

    @Schema(description = "")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String deptLv1Id;

    @Schema(description = "")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String deptLv2Id;

    @Schema(description = "")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String deptLv3Id;

    @Schema(description = "")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String deptLv4Id;

    @Schema(description = "")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String deptLv5Id;

    @Schema(description = "")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String deptLv6Id;

    @Schema(description = "")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String deptLv7Id;

    @Schema(description = "")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String deptFpathId;

}
