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
@Schema(description = "상대처정보 검색 DTO")
public class PartnerSearchDTO extends CommonDTO {

    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    private List<Integer> systempartnerids;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private String companyknd;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private String companyareaknd;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String companyno;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String partnercode;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String partnername;

}
