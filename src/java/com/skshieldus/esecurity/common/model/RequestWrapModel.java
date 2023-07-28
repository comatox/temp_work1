package com.skshieldus.esecurity.common.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 요청 정보 Wrapping 모델 (list요청 시)
 *
 * @param <T>
 * @author X0121128<sungbum.oh @ partner.sk.com>
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestWrapModel<T> {

    @Schema(description = "parameter list")
    public T params;

}
