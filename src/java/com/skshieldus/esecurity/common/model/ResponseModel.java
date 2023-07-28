package com.skshieldus.esecurity.common.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import java.util.Date;

/**
 * 결과 응답 모델
 *
 * @param <T>
 * @author X0121126<woogon.kim1 @ partner.sk.com>
 */
@Data
@NoArgsConstructor
public class ResponseModel<T> {

    private Integer status;

    private String message;

    private T data;

    private Integer dataCount = 0;

    private Date timestamp = new Date();

    public ResponseModel(HttpStatus status, T data) {
        this.status = status.value();
        this.message = status.name();
        this.data = data;
    }

    public ResponseModel(HttpStatus status, T data, Integer dataCount) {
        this.status = status.value();
        this.message = status.name();
        this.data = data;
        this.dataCount = dataCount;
    }

    public ResponseModel(HttpStatus status, String message, T data) {
        super();
        this.status = status.value();
        this.message = message;
        this.data = data;
    }

    public ResponseModel(HttpStatus status, String message, T data, Integer dataCount) {
        super();
        this.status = status.value();
        this.message = message;
        this.data = data;
        this.dataCount = dataCount;
    }

}
