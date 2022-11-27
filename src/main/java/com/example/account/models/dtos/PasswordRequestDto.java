package com.example.account.models.dtos;

import com.example.account.models.infrastracture.BreachedPasswordValidation;
import com.example.account.models.infrastracture.MinPasswordSizeValidation;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PasswordRequestDto {

    @MinPasswordSizeValidation
    @BreachedPasswordValidation
    public String new_password;

}
