package com.example.account.models.dtos;

import com.example.account.models.entities.constants.AccessOperation;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class AccessRequestDto {
    @NotEmpty
    private String user;
    @NotNull
    private AccessOperation operation;
}
