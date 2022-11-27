package com.example.account.service;

import com.example.account.event.BruteForceEvent;
import com.example.account.models.entities.User;
import com.example.account.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@EnableTransactionManagement
public class LoginAttemptService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AdminService adminService;

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    private final int maxAttemptCount = 5;

    public void loginSucceeded(String name) {
        User user = userRepository.findUserByEmail(name);
        if (user.getFailedAttempt() > 0) {
            userRepository.updateFailedAttempts(0, name);
        }
    }


    public void loginFailed(String name) {
        User user = userRepository.findUserByEmail(name);
        if (user != null) {
            if (user.isAccountNonLocked()) {
                int attemptCount = user.getFailedAttempt() + 1;
                if (attemptCount == maxAttemptCount) {
                    applicationEventPublisher.publishEvent(new BruteForceEvent(name));
                    adminService.lock(user);
                }
                userRepository.updateFailedAttempts(attemptCount, name);
                System.out.println(attemptCount + " " + user.getFailedAttempt());
            }
        }
    }
}