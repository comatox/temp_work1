package com.skshieldus.esecurity.model.common;

import com.skshieldus.esecurity.common.model.CommonDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "메뉴 권한 확인")
public class MenuAuthCheckDTO extends CommonDTO {

    @Schema(description = "사원ID")
    private String empId;

    @Schema(description = "메뉴ID")
    private String menuId;

}
