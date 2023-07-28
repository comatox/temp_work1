package com.skshieldus.esecurity.model.common;

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
@JsonInclude(value = Include.NON_NULL)
@Schema(description = "건물정보")
public class CoBldgDTO extends CommonDTO {

    @Schema(description = "건물ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String gateId;

    @Schema(description = "업체ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String compId;

    @Schema(description = "건물명")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String gateNm;

    @Schema(description = "건물코드MDM")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String gateCd;

    @Schema(description = "상위건물ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String upGateId;

    @Schema(description = "건물종류")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer gateKnd;

    @Schema(description = "방문출입가능여부")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String vstrAvailYn;

    @Schema(description = "청정교육여부")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String cleanEduYn;

    @Schema(description = "통제구역여부")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String ctrlYn;

    @Schema(description = "안전교육여부")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String safeEduYn;

    @Schema(description = "소팅순서")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer sortSeq;

    @Schema(description = "빌딩DEPTH")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer depth;

    @Schema(description = "보안담당자")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String apprEmpId;

    @Schema(description = "삭제구분")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String delYn;

    @Schema(description = "트리거플래그")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer trgFlag;

    @Schema(description = "TAGIDKND")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String tagidknd;

    @Schema(description = "접속IP")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String acIp;

    @Schema(description = "등록자")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String crtBy;

    @Schema(description = "등록일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", locale = "ko_KR")
    private Timestamp crtDtm;

    @Schema(description = "수정자")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String modBy;

    @Schema(description = "수정일시")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", locale = "ko_KR")
    private Timestamp modDtm;

    @Schema(description = "건물명 (영문)")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String gateEnNm;

    @Schema(description = "보안승인자")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String apprEmpId2;

}
