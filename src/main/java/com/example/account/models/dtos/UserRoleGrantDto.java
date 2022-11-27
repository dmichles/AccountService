package com.example.account.models.dtos;

import com.example.account.models.entities.constants.RoleOperation;
import com.example.account.models.infrastracture.RoleValidation;
import lombok.Data;

@Data
public class UserRoleGrantDto {
    private String user;

    @RoleValidation
    private String role;

    private RoleOperation operation;
}
