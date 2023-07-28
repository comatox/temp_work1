package com.skshieldus.esecurity.model.common;

import com.skshieldus.esecurity.common.model.CommonDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "건물정보 검색")
public class CoBldgSearchDTO extends CommonDTO {

    @Schema(description = "건물ID 목록")
    private List<String> gateIdList;

}