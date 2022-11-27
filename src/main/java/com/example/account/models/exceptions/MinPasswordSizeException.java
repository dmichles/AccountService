package com.example.account.models.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "The password length must be at least 12 chars!")
public class MinPasswordSizeException extends RuntimeException {
    public MinPasswordSizeException() {
    }

    public MinPasswordSizeException(String message) {
        super(message);
    }

    public MinPasswordSizeException(String message, Throwable cause) {
        super(message, cause);
    }

    public MinPasswordSizeException(Throwable cause) {
        super(cause);
    }

    public MinPasswordSizeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
