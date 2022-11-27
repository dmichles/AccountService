package com.example.account.service;

import com.example.account.event.AccessUserEvent;
import com.example.account.event.DeleteUserEvent;
import com.example.account.event.UpdateRoleEvent;
import com.example.account.models.dtos.UserDto;
import com.example.account.models.dtos.UserRoleGrantDto;
import com.example.account.models.entities.Group;
import com.example.account.models.entities.User;
import com.example.account.models.entities.constants.AccessOperation;
import com.example.account.models.entities.constants.RoleOperation;
import com.example.account.models.entities.constants.UserRole;
import com.example.account.models.mappers.UserDtoMapper;
import com.example.account.repository.GroupRepository;
import com.example.account.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AdminService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    public ResponseEntity<?> getUsers(){
        List<User> users = userRepository.findAll();
        //UserDtoMapper mapper = new UserDtoMapper();
        //List<UserDto> usersDto = mapper.fromUsers(users);
        List<UserDto> usersDto = users.stream().map(UserDto::of).collect(Collectors.toList());
        return new ResponseEntity<>(usersDto, HttpStatus.OK);
    }

    public ResponseEntity<?> deleteUser(String email){
        User user = userRepository.findUserByEmail(email);
        boolean adminGroup = false;

        if (user != null) {
            adminGroup = user.getUserGroups().stream().map(Group::getRole).allMatch(s->s.equals(UserRole.ADMINISTRATOR.name()));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found!");
        }

        if (adminGroup){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't remove ADMINISTRATOR role!");
        } else {

            user.removeUserGroups();
            
            userRepository.delete(user);
            applicationEventPublisher.publishEvent(new DeleteUserEvent(user));

            Map<String, String> response = new LinkedHashMap<>();
            response.put("user", user.getEmail());
            response.put("status", "Deleted successfully!");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    public UserDto alterRole(UserRoleGrantDto userRoleGrantDto){
        User user = userRepository.findUserByEmail(userRoleGrantDto.getUser());
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found!");
        }

        if (RoleOperation.GRANT.equals(userRoleGrantDto.getOperation())) {
            grantRole(user, UserRole.valueOf(userRoleGrantDto.getRole()));
        } else if(RoleOperation.REMOVE.equals(userRoleGrantDto.getOperation())) {
            removeRole(user, UserRole.valueOf(userRoleGrantDto.getRole()));
        }
        //UserDtoMapper mapper = new UserDtoMapper();
        return UserDto.of(user);
    }

    public void grantRole(User user, UserRole role){
        boolean adminGroup = user.getUserGroups().stream().map(Group::getRole).allMatch(s->s.equals(UserRole.ADMINISTRATOR.name()));
        System.out.println(adminGroup);
        System.out.println(user.getEmail());
        if(!role.isFromAdminGroup() && adminGroup) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"The user cannot combine administrative and business roles!");
        } else if (role.isFromAdminGroup() && !adminGroup) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user cannot combine administrative and business roles!");
        } else {
            Group group = groupRepository.findGroupByRole(role.name());
            System.out.println(group.getRole());
            user.addUserGroup(group);
            userRepository.save(user);

            applicationEventPublisher.publishEvent(new UpdateRoleEvent(user,role,RoleOperation.GRANT));
        }
    }

    public void removeRole(User user, UserRole role){
        List<String> list = user.getUserGroups().stream().map(Group::getRole).collect(Collectors.toList());

        if (!list.contains(role.name())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user does not have a role!");
        } else if (role.equals(UserRole.ADMINISTRATOR)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Can't remove ADMINISTRATOR role!");
        } else if(list.size() == 1){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"The user must have at least one role!");
        } else {
            Group group = groupRepository.findGroupByRole(role.name());
            user.removeUserGroup(group);
            userRepository.save(user);
            applicationEventPublisher.publishEvent(new UpdateRoleEvent(user,role,RoleOperation.REMOVE));
        }
    }

    public ResponseEntity<?> lock(User user) {
        boolean groupAdmin = user.getUserGroups().stream().map(Group::getRole).allMatch(s->s.equals(UserRole.ADMINISTRATOR.name()));
        if (groupAdmin) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Can't lock the ADMINISTRATOR!");
        }
        user.setAccountNonLocked(false);
        userRepository.save(user);
        applicationEventPublisher.publishEvent(new AccessUserEvent(AccessOperation.LOCK,user));
        Map<String, String> response = new LinkedHashMap<>();
        String str = String.format("User %s locked!",user.getEmail());
        response.put("status", str);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    public ResponseEntity<?> unlock(User user) {
        user.setAccountNonLocked(true);
        userRepository.save(user);
        userRepository.updateFailedAttempts(0,user.getEmail());
        applicationEventPublisher.publishEvent(new AccessUserEvent(AccessOperation.UNLOCK,user));
        Map<String, String> response = new LinkedHashMap<>();
        String str = String.format("User %s unlocked!",user.getEmail());
        response.put("status", str);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
