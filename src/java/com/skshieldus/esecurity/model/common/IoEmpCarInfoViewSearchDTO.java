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
@Schema(description = "외부인 차량정보 조회")
public class IoEmpCarInfoViewSearchDTO extends CommonDTO {

    @Schema(description = "site")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String site;

    @Schema(description = "siteType")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String siteType;

    @Schema(description = "searchDeliveryYn")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String searchDeliveryYn;

    @Schema(description = "searchIoEmpId")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String searchIoEmpId;

    @Schema(description = "searchIoEmpNm")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String searchIoEmpNm;

    @Schema(description = "searchIoCompId")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String searchIoCompId;

    @Schema(description = "searchIoCompNm")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String searchIoCompNm;

    @Schema(description = "passYnList")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String passYnList;

}
