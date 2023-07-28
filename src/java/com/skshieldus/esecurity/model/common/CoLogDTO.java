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
@Schema(description = "공통 로그")
public class CoLogDTO extends CommonDTO {

    @Schema(description = "메뉴ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String menuId;

    @Schema(description = "CRUD 구분")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String crudType;

    @Schema(description = "메소드명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String mthdNm;

    @Schema(description = "접속IP")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String remoteIp;

    @Schema(description = "사유")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String remark;

    @Schema(description = "사유선택(정기점검,모니터링,단순조회,테스트,기타)")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String remarkType;

    @Schema(description = "매개변수")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String reqParam;

    @Schema(description = "등록자")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String regId;

    @Schema(description = "개인정보로그여부")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String prsnlInfoLogYn;

}
