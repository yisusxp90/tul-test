package com.tul.test.ecommerce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EcommerceBusinessException extends RuntimeException {

    private static final long serialVersionUID = 2841830811078618188L;
    private final Integer code;

    public EcommerceBusinessException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    Integer getCode() {
        return code;
    }
}
