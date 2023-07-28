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
@JsonInclude(value = Include.NON_NULL)
@Schema(description = "업체정보")
public class CoCompDTO extends CommonDTO {

    @Schema(description = "업체ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String compId;

    @Schema(description = "업체명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String compNm;

    @Schema(description = "업체지역구분코드")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer compKnd;

    @Schema(description = "업체구분코드")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer compAreaKnd;

    @Schema(description = "업체코드_MDM")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String compCd;

    @Schema(description = "사업자번호")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String legalNo;

    @Schema(description = "사업자명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String boss;

    @Schema(description = "전화번호")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String telNo;

    @Schema(description = "이메일")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String email;

    @Schema(description = "우편번호1")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String zip1;

    @Schema(description = "우편번호2")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String zip2;

    @Schema(description = "주소")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String addr;

    @Schema(description = "운영자리스트")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String adminList;

    @Schema(description = "운영자이메일")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String adminEmail;

    @Schema(description = "유니크키")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String uniquekey;

    @Schema(description = "삭제구분", allowableValues = "Y, N")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String delYn;

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

}
