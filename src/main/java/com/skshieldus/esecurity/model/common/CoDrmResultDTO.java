package com.skshieldus.esecurity.model.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.skshieldus.esecurity.common.model.CommonDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(value = Include.NON_NULL)
@Schema(description = "DRM해제결과")
public class CoDrmResultDTO extends CommonDTO {

    @Schema(description = "DRM해제파일ID")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer drmFileSeq;

    @Schema(description = "DRM해제결과데이터")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String drmResultData;

    @Schema(description = "DRM해제결과데이터(변환)")
    private List<List<String>> drmResultDataList;

    @Schema(description = "DRM해제처리상태")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer drmResultStatus;

    @Schema(description = "생성일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String crtDtm;

    @Schema(description = "생성자ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String crtBy;

    @Schema(description = "수정일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String modDtm;

}