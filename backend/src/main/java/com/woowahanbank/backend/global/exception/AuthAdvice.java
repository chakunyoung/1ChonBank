package com.woowahanbank.backend.global.exception;

import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.woowahanbank.backend.gobal.auth")
public class AuthAdvice {
	// @ExceptionHandler(Exception.class)
	// public ResponseEntity<?> authFailResponse(Exception ex) {
	//     return new BaseResponse().fail(ex.getMessage(), 401);
	// }
}
