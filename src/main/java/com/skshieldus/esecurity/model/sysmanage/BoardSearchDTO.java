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
@Schema(description = "게시판")
public class BoardSearchDTO extends CommonDTO {

    @Schema(description = "게시판구분")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String boardGbn;

    @Schema(description = "제목")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String searchTitle;

    @Schema(description = "작성자명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String searchEmpNm;

    @Schema(description = "내외부망구분")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String inoutGbn;

    @Schema(description = "QNA 구분")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String qnaGbn;

    @Schema(description = "게시판NO")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer boardNo;

    @Schema(description = "권한확인용 사용자ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String usempId;

    @Schema(description = "sessionEmpId")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String sessionEmpId;

    @Schema(description = "encSessionEmpId")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String encSessionEmpId;

    @Schema(description = "템플릿 구분")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String templeteGbn;

    @Schema(description = "템플릿 사용여부")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String templeteUseYn;

}