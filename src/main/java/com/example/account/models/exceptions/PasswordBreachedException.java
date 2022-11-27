package com.example.account.models.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST,reason = "The password is in the hacker's database!")
public class PasswordBreachedException extends RuntimeException{
    public PasswordBreachedException() {
    }

    public PasswordBreachedException(String message) {
        super(message);
    }

    public PasswordBreachedException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordBreachedException(Throwable cause) {
        super(cause);
    }

    public PasswordBreachedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
