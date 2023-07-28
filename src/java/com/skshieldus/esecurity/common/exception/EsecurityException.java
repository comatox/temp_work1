package com.skshieldus.esecurity.common.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class EsecurityException extends RuntimeException {

    private HttpStatus httpStatus;

    private Integer statusCode;

    public EsecurityException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        this.statusCode = httpStatus.value();
    }

    public EsecurityException(HttpStatus httpStatus, Integer statusCode) {
        this.httpStatus = httpStatus;
        this.statusCode = statusCode;
    }

    public EsecurityException(HttpStatus httpStatus, Integer statusCode, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.statusCode = statusCode;
    }

}
