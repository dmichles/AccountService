package com.example.account.models.infrastracture;

import com.example.account.models.entities.constants.UserRole;
import com.example.account.models.exceptions.RoleNotFoundException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;


public class RoleValidator implements ConstraintValidator<RoleValidation, String> {
    public boolean isValid(String role, ConstraintValidatorContext cxt) {
        List<String> list = (Arrays.asList(UserRole.ACCOUNTANT.getRole(),UserRole.USER.getRole(),UserRole.AUDITOR.getRole(),UserRole.ADMINISTRATOR.getRole()));
        boolean isValid = list.contains(role);

        if (!isValid) throw new RoleNotFoundException();
        return true;
    }
}