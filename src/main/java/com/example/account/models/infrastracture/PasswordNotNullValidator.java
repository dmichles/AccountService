package com.example.account.models.infrastracture;

import com.example.account.models.exceptions.PasswordNotNullException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class PasswordNotNullValidator implements ConstraintValidator<PasswordNotNullValidation, String> {
    public boolean isValid(String password, ConstraintValidatorContext cxt) {

        boolean isValid = password != null;

        if (!isValid) throw new PasswordNotNullException("Password can't be null!");
        return true;
    }
}
