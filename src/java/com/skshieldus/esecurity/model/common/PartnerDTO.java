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
@Schema(description = "상대처정보")
public class PartnerDTO extends CommonDTO {

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer systempartnerid;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private String companyknd;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private String companyareaknd;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String companyno;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String systemid;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String partnercode;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String partnername;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String legalno;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String oldlegalno;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String boss;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String phone;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String zip1;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String zip2;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String address;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String inempNo;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String indeptCd;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String firstinputdate;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String firstinputtime;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String lastupdatedate;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String lastupdatetime;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String faxNo;

}
