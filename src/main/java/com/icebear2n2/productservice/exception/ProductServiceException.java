package com.icebear2n2.productservice.exception;

import lombok.Getter;

@Getter
public class ProductServiceException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String message;

    public ProductServiceException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }
}
