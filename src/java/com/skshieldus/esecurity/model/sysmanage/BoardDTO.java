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
public class BoardDTO extends CommonDTO {

    @Schema(description = "게시판NO")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer boardNo;

    @Schema(description = "상위게시판NO")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer upBoardNo;

    @Schema(description = "내외부망구분")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String inoutGbn;

    @Schema(description = "게시판구분")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String boardGbn;

    @Schema(description = "제목")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String title;

    @Schema(description = "등록자")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String crtBy;

    @Schema(description = "등록자명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String crtNm;

    @Schema(description = "조회수")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer readCnt;

    @Schema(description = "등록일자")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String crtDtm;

    @Schema(description = "등록일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String crtDtmTime;

    @Schema(description = "아이콘(NEW)")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String icon;

    @Schema(description = "접속IP")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String acIp;

    @Schema(description = "URL")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String url;

    @Schema(description = "files")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String files;

    @Schema(description = "filePath")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String filePath;

    @Schema(description = "filePath1")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String filePath1;

    @Schema(description = "fileNameOld")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String fileNameOld;

    @Schema(description = "fileName")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String fileName;

    @Schema(description = "fileUrl")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String fileUrl;

    @Schema(description = "fileId")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String fileId;

    @Schema(description = "fileIdTitle")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String fileIdTitle;

    @Schema(description = "content")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String content;

    @Schema(description = "qnaGbn")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String qnaGbn;

    @Schema(description = "qnaEmp")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String qnaEmp;

    @Schema(description = "b cnt")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer bCnt;

    @Schema(description = "부서명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String deptNm;

    @Schema(description = "reportReply(답변여부)")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String reportReply;

    @Schema(description = "usempId")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String usempId;

    @Schema(description = "schemaNm")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String schemaNm;

    @Schema(description = "isSecuTeam")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String isSecuTeam;

    @Schema(description = "encCrtBy")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String encCrtBy;

    @Schema(description = "grantYn")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String grantYn;

    @Schema(description = "템플릿 구분")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String templeteGbn;

    @Schema(description = "템플릿 사용여부")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String templeteUseYn;

}