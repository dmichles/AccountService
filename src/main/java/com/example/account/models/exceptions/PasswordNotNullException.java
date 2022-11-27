package com.example.account.models.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Password can't be null!")
public class PasswordNotNullException extends RuntimeException {
    public PasswordNotNullException() {
    }

    public PasswordNotNullException(String message) {
        super(message);
    }

    public PasswordNotNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordNotNullException(Throwable cause) {
        super(cause);
    }

    public PasswordNotNullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}