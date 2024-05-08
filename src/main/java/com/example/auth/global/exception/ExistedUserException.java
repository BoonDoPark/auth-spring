package com.example.auth.global.exception;

public class ExistedUserException extends IllegalArgumentException {
    public ExistedUserException(String email) {
        super(email + " already exists");
    }
}
