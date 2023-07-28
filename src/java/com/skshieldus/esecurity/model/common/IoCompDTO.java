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
@Schema(description = "외부업체 DTO")
public class IoCompDTO extends CommonDTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String ioCompId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String sIoCompId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String compKoNm;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String compEnNm;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String legalNo;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String bossNm;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String telNo;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String faxNo;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String addr;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String zip;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String subcontYn;

}
