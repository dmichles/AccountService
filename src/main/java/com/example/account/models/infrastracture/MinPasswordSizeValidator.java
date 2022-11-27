package com.example.account.models.infrastracture;

import com.example.account.models.exceptions.MinPasswordSizeException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class MinPasswordSizeValidator implements ConstraintValidator<MinPasswordSizeValidation, String> {
    public boolean isValid(String password, ConstraintValidatorContext cxt) {

        boolean isValid = true;
        if (password != null) {
            isValid = password.length() >= 12;
        }
        if (!isValid) throw new MinPasswordSizeException("The password length must be at least 12 chars!");
        return true;
    }
}
