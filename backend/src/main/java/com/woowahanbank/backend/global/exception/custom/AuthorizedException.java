package com.woowahanbank.backend.global.exception.custom;

public class AuthorizedException extends RuntimeException {
    public AuthorizedException(String message) {
        super(message);
    }
}
