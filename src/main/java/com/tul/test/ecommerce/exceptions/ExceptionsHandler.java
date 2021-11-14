package com.tul.test.ecommerce.exceptions;

import com.tul.test.ecommerce.model.ProductResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProductResponse> handleGenericException(Exception e){
        ProductResponse productResponse = new ProductResponse(500, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(productResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EcommerceBusinessException.class)
    public ResponseEntity<ProductResponse> handleFlightBusinessException(EcommerceBusinessException e){
        ProductResponse productResponse = new ProductResponse(e.getCode(), e.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(productResponse, HttpStatus.BAD_REQUEST);
    }
}
