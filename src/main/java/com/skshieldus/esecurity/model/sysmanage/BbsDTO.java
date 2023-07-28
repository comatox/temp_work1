package com.skshieldus.esecurity.model.sysmanage;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.skshieldus.esecurity.common.model.CommonDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.sql.Timestamp;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "산업보안 공지사항 정보")
@JsonInclude(value = Include.NON_NULL)
public class BbsDTO extends CommonDTO {

    @Schema(description = "row num")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String rowNum;

    @Schema(description = "게시판 NO")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String boardNo;

    @Schema(description = "내외부망 구분")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String inoutGbn;

    @Schema(description = "게시판 구분")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String boardGbn;

    @Schema(description = "등록자")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String empId;

    @Schema(description = "게시판 제목")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String title;

    @Schema(description = "게시판 내용")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String content;

    @Schema(description = "파일 경로")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String filePath;

    @Schema(description = "파일 ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String fileId;

    @Schema(description = "파일 이름")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String fileName;

    @Schema(description = "게시판 url")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String url;

    @Schema(description = "상위 게시글 ID")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer upBoardNo;

    @Schema(description = "메뉴뎁스")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer depth;

    @Schema(description = "게시판 조회수")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer readCnt;

    @Schema(description = "상위 게시글 존재여부")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer upBoardNoCnt;

    @Schema(description = "게시판 삭제여부", allowableValues = "Y,N")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String delYn;

    @Schema(description = "접속IP")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String acIp;

    @Schema(description = "게시판 등록자")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String crtBy;

    @Schema(description = "게시판 등록일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", locale = "ko_KR")
    private Timestamp crtDtm;

    @Schema(description = "게시판 수정자")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String modBy;

    @Schema(description = "게시판 등록일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", locale = "ko_KR")
    private Timestamp modDtm;

    @Schema(description = "")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String usempId;

}
