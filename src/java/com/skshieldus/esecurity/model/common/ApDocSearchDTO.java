package com.skshieldus.esecurity.model.common;

import com.skshieldus.esecurity.common.model.CommonDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "결재문서 검색")
public class ApDocSearchDTO extends CommonDTO {

    @Schema(description = "결재문서ID 목록")
    private List<Integer> docIdList;

}