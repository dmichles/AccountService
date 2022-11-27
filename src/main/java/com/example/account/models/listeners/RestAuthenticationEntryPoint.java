package com.example.account.models.listeners;

import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if (authException instanceof LockedException) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User account locked");
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Not authorized");
        }
    }
}
