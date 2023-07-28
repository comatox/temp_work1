/**
 *
 */
package com.skshieldus.esecurity.model.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.skshieldus.esecurity.common.model.CommonDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author X0115378 <jaehoon5.kim@partner.sk.com>
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "물품반출입증 정보")
public class MobileInoutPermitDTO extends CommonDTO {

    @Schema(description = "작성일자")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String writedate;

    @Schema(description = "작성순번")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer writeseq;

    @Schema(description = "품목구분")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer articlekndno;

    @Schema(description = "품목그룹ID")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer articlegroupid;

    @Schema(description = "결재문서ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String docId;

    @Schema(description = "한부씩인쇄여부YN")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String optionYn;

    @Schema(description = "반출입신청번호")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer inoutApplNo;

}
