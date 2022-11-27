package com.example.account.models.listeners;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class DataIntegrityErrorExceptionHandler {
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Duplicate email!")
    public void handleConstraintViolation(DataIntegrityViolationException ex) {
        Throwable cause = ex.getRootCause();
        String error = cause.getMessage();
        System.out.println(error);
    }
}