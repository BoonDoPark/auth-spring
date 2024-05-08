package com.example.auth.controller;

import com.example.auth.global.exception.ExistedUserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(ExistedUserException.class)
    public String handleExistedUserException(ExistedUserException e) {
        log.debug(e.getMessage());
        return e.getMessage();
    }
}
