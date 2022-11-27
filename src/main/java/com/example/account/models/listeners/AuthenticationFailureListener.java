package com.example.account.models.listeners;

import com.example.account.models.entities.User;
import com.example.account.repository.UserRepository;
import com.example.account.service.EventService;
import com.example.account.service.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationFailureLockedEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

@Component
public class AuthenticationFailureListener implements
        ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    EventService eventService;
    @Autowired
    LoginAttemptService loginAttemptService;


    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent e) {
        String email = (String) e.getAuthentication().getPrincipal();
        eventService.logLoginFailed(e, request);
        loginAttemptService.loginFailed(email);
    }
}