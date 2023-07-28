package com.skshieldus.esecurity.model.entmanage;

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
@Schema(description = "iparking api 연계 정보")
public class IparkingDTO extends CommonDTO {

    @Schema(description = "이름(한글2~10자리)")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String userName;

    @Schema(description = "회사 정보(사번/회사명)")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String company;

    @Schema(description = "차량번호(key)")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String carNumber;

    @Schema(description = "무료차량 시작 날짜(yyyy-MM-dd)")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String startDate;

    @Schema(description = "무료차량 종료 날짜(yyyy-MM-dd)")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String endDate;

    @Schema(description = "비고")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String description;

    @Schema(description = "R: 등록 / M: 수정 / D: 삭제")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String requestType;

}
