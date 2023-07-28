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
public class ApprovalBoxSearchDTO extends CommonDTO {

    @Schema(description = "검색조건시작일자")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String fromDate;

    @Schema(description = "검색조건종료일자")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String toDate;

    @Schema(description = "신청자이름")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String searchReqEmpNm;

    @Schema(description = "작성자이름")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String searchEmpNm;

    @Schema(description = "진행항목이름")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String searchDocNm;

    @Schema(description = "스키마이름")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String searchSchemaNm;

    @Schema(description = "docType")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String docType;

    @Schema(description = "searchApprResult")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String searchApprResult;

    @Schema(description = "대상회사 이름")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String searchReqCompNm;

    @Schema(description = "applEmpId")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String applEmpId;

    @Schema(description = "empId")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String empId;

}