package com.skshieldus.esecurity.model.sysmanage;

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
@Schema(description = "스팸메일 조회조건")
public class SpamMailSearchDTO extends CommonDTO {

    @Schema(description = "스팸메일신고번호")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer spamNo;

    @Schema(description = "내용")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String searchContent;

    @Schema(description = "작성자")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String searchEmpNm;

}