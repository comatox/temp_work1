package com.skshieldus.esecurity.model.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.skshieldus.esecurity.common.model.CommonDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@JsonInclude(value = Include.NON_NULL)
@Schema(description = "외부인 차량정보")
public class IoEmpCarInfoViewDTO extends CommonDTO {

    @Schema(description = "bossNm")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String bossNm;

    @Schema(description = "carKnd")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String carKnd;

    @Schema(description = "carNo")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String carNo;

    @Schema(description = "compAddr")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String compAddr;

    @Schema(description = "compEnNm")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String compEnNm;

    @Schema(description = "compKoNm")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String compKoNm;

    @Schema(description = "compTelNo")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String compTelNo;

    @Schema(description = "compZip1")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String compZip1;

    @Schema(description = "compZip2")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String compZip2;

    @Schema(description = "denyDt")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String denyDt;

    @Schema(description = "denyEndDt")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String denyEndDt;

    @Schema(description = "denyStrtDt")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String denyStrtDt;

    @Schema(description = "denyYn")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String denyYn;

    @Schema(description = "emailAddr")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String emailAddr;

    @Schema(description = "eshDt")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String eshDt;

    @Schema(description = "exprYn")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String exprYn;

    @Schema(description = "filePhoto")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String filePhoto;

    @Schema(description = "idcardId")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String idcardId;

    @Schema(description = "ioCompId")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String ioCompId;

    @Schema(description = "ioEmpAddr")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String ioEmpAddr;

    @Schema(description = "ioEmpId")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String ioEmpId;

    @Schema(description = "ioEmpJuminNo")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String ioEmpJuminNo;

    @Schema(description = "ioEmpJuminNo2")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String ioEmpJuminNo2;

    @Schema(description = "ioEmpLocInfoAgree")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String ioEmpLocInfoAgree;

    @Schema(description = "ioEmpNm")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String ioEmpNm;

    @Schema(description = "ioEmpNmHtml")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String ioEmpNmHtml;

    @Schema(description = "ioHpNo")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String ioHpNo;

    @Schema(description = "ioJwNm")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String ioJwNm;

    @Schema(description = "moveYn")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String moveYn;

    @Schema(description = "nameChk")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String nameChk;

    @Schema(description = "nationNm")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String nationNm;

    @Schema(description = "passportNo")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String passportNo;

    @Schema(description = "passYn")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String passYn;

    @Schema(description = "processCount")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer processCount;

    @Schema(description = "processNm")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String processNm;

    @Schema(description = "rowNum")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer rowNum;

    @Schema(description = "subcontYn")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String subcontYn;

}
