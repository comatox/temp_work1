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
@Schema(description = "업체정보")
public class CoCodeDetailDTO extends CommonDTO {

    @Schema(description = "분류코드")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String grpCd;

    @Schema(description = "세부코드")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String detlCd;

    @Schema(description = "세부명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String detlNm;

    @Schema(description = "계정코드")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String detlEnNm;

    @Schema(description = "IDCARD I/F 코드 매핑 컬럼")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String idcardIf;

    @Schema(description = "기타1")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String etc1;

    @Schema(description = "기타2")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String etc2;

    @Schema(description = "기타3")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String etc3;

    @Schema(description = "기타4")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String etc4;

    @Schema(description = "SORT순서")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer sortSeq;

    @Schema(description = "사용여부")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String useYn;

    @Schema(description = "접속IP")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String acIp;

    @Schema(description = "등록자")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String crtBy;

    @Schema(description = "등록일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", locale = "ko_KR")
    private String crtDtm;

    @Schema(description = "수정자")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String modBy;

    @Schema(description = "수정일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", locale = "ko_KR")
    private String modDtm;

    @Schema(description = "비고")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String rmrk;

}