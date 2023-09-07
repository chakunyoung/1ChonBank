package com.woowahanbank.backend.global.exception;

import com.woowahanbank.backend.global.exception.custom.AuthorizedException;
import com.woowahanbank.backend.global.exception.custom.ForbiddenException;
import com.woowahanbank.backend.global.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> illegalArgumentResponse(IllegalArgumentException ex) {
        return new BaseResponse().fail(ex.getMessage(), 500);
    }

    @ExceptionHandler(AuthorizedException.class)
    public ResponseEntity<?> unauthorizedResponse(AuthorizedException ex) {
        return new BaseResponse().fail(ex.getMessage(), 401);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<?> unauthorizedResponse(ForbiddenException ex) {
        return new BaseResponse().fail(ex.getMessage(), 403);
    }
}
