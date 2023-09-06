package com.woowahanbank.backend.global.exception;

import com.woowahanbank.backend.global.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.woowahanbank.backend.gobal.auth")
public class AuthAdvice {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> authFailResponse(Exception ex) {
        return new BaseResponse().fail(ex.getMessage(), 401);
    }
}
