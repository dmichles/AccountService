package com.example.account.models.mappers;

import com.example.account.models.dtos.UserDto;
import com.example.account.models.entities.Group;
import com.example.account.models.entities.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDtoMapper {
    public UserDto fromUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setLastname(user.getLastname());
        userDto.setEmail(user.getEmail());

        List<String> list = user.getUserGroups().stream().map(Group::getName).sorted().collect(Collectors.toList());
        userDto.setRoles(list);
        return userDto;
    }

    public List<UserDto> fromUsers(List<User> users) {
        List<UserDto> listUsers = new ArrayList<>();
        Collections.sort(users);
        for (User user : users) {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setName(user.getName());
            userDto.setLastname(user.getLastname());
            userDto.setEmail(user.getEmail());

            List<String> list = user.getUserGroups().stream().map(Group::getName).sorted().collect(Collectors.toList());

            userDto.setRoles(list);
            listUsers.add(userDto);
        }
        return listUsers;
    }
}
