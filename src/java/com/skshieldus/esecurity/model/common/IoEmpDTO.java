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
@Schema(description = "외부업체 회원정보 DTO")
public class IoEmpDTO extends CommonDTO {

    @Schema(description = "회원ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String ioEmpId;

    @Schema(description = "회원명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String empNm;

    @Schema(description = "직위명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String jwNm;

    @Schema(description = "핸드폰번호(개인정보처리)")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String hpNo;

    @Schema(description = "핸드폰번호(원본)")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String oriHpNo;

    @Schema(description = "주민번호")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String juminNo;

    @Schema(description = "여권번호")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String passportNo;

    @Schema(description = "주소")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String addr;

    @Schema(description = "국가코드")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String nation;

    @Schema(description = "국가명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String nationNm;

    @Schema(description = "차종")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String carKnd;

    @Schema(description = "차량번호")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String carNo;

    @Schema(description = "차량번호")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String filePhoto;

    @Schema(description = "이메일주소")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String emailAddr;

    @Schema(description = "업체ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String ioCompId;

    @Schema(description = "업체한글명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String compKoNm;

    @Schema(description = "업체영문명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String compEnNm;

    @Schema(description = "업체대표자명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String bossNm;

    @Schema(description = "업체번호")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String telNo;

    @Schema(description = "업체주소")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String compAddr;

    @Schema(description = "업체우편번호1")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String compZip1;

    @Schema(description = "업체우편번호2")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String compZip2;

    @Schema(description = "도급업체여부")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String subcontYn;

    @Schema(description = "휴면여부")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String sleepYn;

    @Schema(description = "휴면예정일")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String sleepDueDate;

}
