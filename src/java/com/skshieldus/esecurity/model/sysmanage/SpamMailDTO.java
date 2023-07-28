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
public class SpamMailDTO extends CommonDTO {

    @Schema(description = "스팸메일신고번호")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer spamNo;

    @Schema(description = "캠퍼스_ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String compId;

    @Schema(description = "캠퍼스명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String compNm;

    @Schema(description = "부서ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String deptId;

    @Schema(description = "부서명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String deptNm;

    @Schema(description = "직위ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String jwId;

    @Schema(description = "직위명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String jwNm;

    @Schema(description = "수신자사번")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String recvEmpId;

    @Schema(description = "수신자명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String recvEmpNm;

    @Schema(description = "수신자 직위명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String recvJwNm;

    @Schema(description = "수신자부서명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String recvDeptNm;

    @Schema(description = "수신자이메일")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String recvEmail;

    @Schema(description = "발신자이메일")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String sendEmail;

    @Schema(description = "수신일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String recvDtm;

    @Schema(description = "수신일자")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String recvDt;

    @Schema(description = "수신시간")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String recvHour;

    @Schema(description = "수신분")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String recvMin;

    @Schema(description = "내용")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String content;

    @Schema(description = "contents")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String contents;

    @Schema(description = "첨부파일")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String files;

    @Schema(description = "fileNameOld")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String fileNameOld;

    @Schema(description = "fileName")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String fileName;

    @Schema(description = "fileUrl")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String fileUrl;

    @Schema(description = "접속IP")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String acIp;

    @Schema(description = "등록일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String crtDtm;

    @Schema(description = "삭제구분")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String delYn;

    @Schema(description = "직원ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String empId;

    @Schema(description = "직원명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String empNm;

    @Schema(description = "filePath")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String filePath;

    @Schema(description = "fileId")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String fileId;

    @Schema(description = "fileIdTitle")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String fileIdTitle;

    @Schema(description = "등록자명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String crtNm;

    @Schema(description = "조회수")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer readCnt;

    @Schema(description = "등록자")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String crtBy;

    @Schema(description = "reportReply(답변여부)")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String reportReply;

    @Schema(description = "usempId")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String usempId;

    @Schema(description = "schemaNm")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String schemaNm;

    @Schema(description = "제목")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String title;

}