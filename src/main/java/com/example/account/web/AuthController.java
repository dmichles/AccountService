package com.example.account.web;

import com.example.account.models.dtos.PasswordRequestDto;
import com.example.account.models.entities.User;
import com.example.account.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {
    @Autowired
    UserService userService;

    @PostMapping("/api/auth/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid User user) {
        ResponseEntity<?> responseEntity = userService.signup(user);
        return responseEntity;
    }

    @PostMapping("/api/auth/changepass")
    public ResponseEntity<?> changePass(@RequestBody @Valid PasswordRequestDto passwordRequestDto, @AuthenticationPrincipal UserDetails userDetails) {
        ResponseEntity<?> responseEntity = userService.changePass(userDetails.getUsername(), passwordRequestDto.getNew_password());
        return responseEntity;
    }
}
