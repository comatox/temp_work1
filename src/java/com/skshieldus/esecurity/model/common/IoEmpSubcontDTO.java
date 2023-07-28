package com.skshieldus.esecurity.model.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.skshieldus.esecurity.common.model.CommonDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@JsonInclude(value = Include.NON_NULL)
@Schema(description = "도급업체 회원정보 DTO")
public class IoEmpSubcontDTO extends CommonDTO {

    @Schema(description = "rowNum")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private int rowNum;

    @Schema(description = "ioEmpId")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String ioEmpId;

    @Schema(description = "idcardId")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String idcardId;

    @Schema(description = "ioEmpNm")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String ioEmpNm;

    @Schema(description = "ioEmpNmHtml")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String ioEmpNmHtml;

    @Schema(description = "ioJwNm")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String ioJwNm;

    @Schema(description = "ioHpNo")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String ioHpNo;

    @Schema(description = "ioCompId")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String ioCompId;

    @Schema(description = "ioEmpJuminNo")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String ioEmpJuminNo;

    @Schema(description = "ioEmpJuminNo2")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String ioEmpJuminNo2;

    @Schema(description = "passportNo")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String passportNo;

    @Schema(description = "ioEmpAddr")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String ioEmpAddr;

    @Schema(description = "compKoNm")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String compKoNm;

    @Schema(description = "compEnNm")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String compEnNm;

    @Schema(description = "bossNm")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String bossNm;

    @Schema(description = "compTelNo")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String compTelNo;

    @Schema(description = "compAddr")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String compAddr;

    @Schema(description = "compZip1")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String compZip1;

    @Schema(description = "compZip2")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String compZip2;

    @Schema(description = "nationNm")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String nationNm;

    @Schema(description = "carKnd")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String carKnd;

    @Schema(description = "carNo")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String carNo;

    @Schema(description = "denyYn")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String denyYn;

    @Schema(description = "denyDt")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String denyDt;

    @Schema(description = "filePhoto")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String filePhoto;

    @Schema(description = "processCount")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String processCount;

    @Schema(description = "processNm")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String processNm;

    @Schema(description = "subcontYn")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String subcontYn;

    @Schema(description = "eshDt")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String eshDt;

    @Schema(description = "nameChk")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String nameChk;

    @Schema(description = "emailAddr")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String emailAddr;

    @Schema(description = "passYn")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String passYn;

    @Schema(description = "moveYn")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String moveYn;

    @Schema(description = "exprYn")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String exprYn;

    @Schema(description = "ieoApplyDt")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String ieoApplyDt;

    @Schema(description = "ieoStatus")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String ieoStatus;

    @Schema(description = "ieoApplyGbn")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String ieoApplyGbn;

}
