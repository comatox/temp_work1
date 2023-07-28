package com.skshieldus.esecurity.model.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(value = Include.NON_NULL)
@Schema(description = "sample")
public class SampleDTO {

    @Schema(description = "rnum")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer rnum;

    @Schema(description = "sample")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer sampleNo;

    @Schema(description = "sample")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String title;

    @Schema(description = "sample")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String subTitle;

    @Schema(description = "sample")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String name;

    @Schema(description = "sample")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String junior;

    @Schema(description = "sample")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String nickName;

    @Schema(description = "sample")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String imagePath;

    @Schema(description = "sample")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String imageYn;

    @Schema(description = "sample")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String content;

    @Schema(description = "sample")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String loadDate;

    @Schema(description = "sample")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String loadTime;

    @Schema(description = "sample")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer pageIndex;

    @Schema(description = "sample")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer pageSize;

    @Schema(description = "sample")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String useYn;


}
