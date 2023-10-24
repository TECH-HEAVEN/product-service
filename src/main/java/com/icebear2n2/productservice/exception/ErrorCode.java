package com.icebear2n2.productservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    DUPLICATED_CATEGORY_NAME(HttpStatus.CONFLICT, "CATEGORY NAME IS DUPLICATED."),
    DUPLICATED_PRODUCT_NAME(HttpStatus.CONFLICT, "PRODUCT NAME IS DUPLICATED."),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL SERVER ERROR."),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "CATEGORY NOT FOUND."),
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "PRODUCT NOT FOUND."),
    PRODUCT_DETAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "PRODUCT DETAIL NOT FOUND."),
    ;
    private final HttpStatus httpStatus;
    private final String message;
}
