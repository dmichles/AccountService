package com.example.account.service;

import com.example.account.event.ChangePasswordEvent;
import com.example.account.event.CreateUserEvent;
import com.example.account.models.dtos.UserDto;
import com.example.account.models.entities.Group;
import com.example.account.models.entities.User;
import com.example.account.repository.GroupRepository;
import com.example.account.repository.PayrollRepository;
import com.example.account.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PayrollRepository payrollRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    public ResponseEntity<?> signup(User user) {
        User user1 = userRepository.findUserByEmail(user.getEmail());
        if (user1 != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User exists!");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        updateUserGroup(user);
        userRepository.save(user);

        User user2 = userRepository.findUserById(user.getId());
        applicationEventPublisher.publishEvent(new CreateUserEvent(user2));

        //UserDtoMapper userInfoDtoMapper = new UserDtoMapper();

        UserDto userInfoDto = UserDto.of(user2);
        return new ResponseEntity<>(userInfoDto, HttpStatus.OK);
    }
    private void updateUserGroup(User user){
        if(userRepository.count() == 0){
            Group group = groupRepository.findGroupByRole("ADMINISTRATOR");
            user.addUserGroup(group);
        } else {
            Group group = groupRepository.findGroupByRole("USER");
            user.addUserGroup(group);
        }
    }
    public User findUser(String email) {
        User user = userRepository.findUserByEmail(email);
        return user;
    }

    public ResponseEntity<?> changePass(String email, String password) {
        User user = userRepository.findUserByEmail(email);
        checkIfPasswordSame(user, password);
        user.setPassword(encoder.encode(password));
        userRepository.save(user);
        applicationEventPublisher.publishEvent(new ChangePasswordEvent(user));
        Map<String, String> response = new LinkedHashMap<>();
        response.put("email", user.getEmail());
        response.put("status", "The password has been updated successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    public void checkIfPasswordSame(User user, String password) {
        if (encoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The passwords must be different!");
        }
    }

    @Transactional
    public void increaseFailedAttempts(User user, int failAttempts) {
        int newFailAttempts = user.getFailedAttempt() + 1;
        userRepository.updateFailedAttempts(failAttempts, user.getEmail());
    }


    public void resetFailedAttempts(String email) {
        userRepository.updateFailedAttempts(0, email);
    }


}