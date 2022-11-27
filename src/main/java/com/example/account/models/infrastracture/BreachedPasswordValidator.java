package com.example.account.models.infrastracture;

import com.example.account.models.exceptions.PasswordBreachedException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;


public class BreachedPasswordValidator implements ConstraintValidator<BreachedPasswordValidation, String> {
    public boolean isValid(String password, ConstraintValidatorContext cxt) {
        List list = Arrays.asList(new String[]{"PasswordForJanuary", "PasswordForFebruary", "PasswordForMarch", "PasswordForApril",
                "PasswordForMay", "PasswordForJune", "PasswordForJuly", "PasswordForAugust",
                "PasswordForSeptember", "PasswordForOctober", "PasswordForNovember", "PasswordForDecember"});
        boolean isValid = !list.contains(password);

        if (!isValid) throw new PasswordBreachedException();
        return true;
    }
}
