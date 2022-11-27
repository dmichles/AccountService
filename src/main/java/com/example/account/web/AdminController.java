package com.example.account.web;

import com.example.account.models.dtos.AccessRequestDto;
import com.example.account.models.dtos.UserDto;
import com.example.account.models.dtos.UserRoleGrantDto;
import com.example.account.models.entities.User;
import com.example.account.repository.UserRepository;
import com.example.account.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/user")
    public ResponseEntity<?> getUsers(HttpServletRequest request){
        ResponseEntity<?>  responseEntity = adminService.getUsers();
        return responseEntity;
    }

    @DeleteMapping("/user/{email}")
    public ResponseEntity<?> deleteUser(@PathVariable String email){
        ResponseEntity<?> responseEntity = adminService.deleteUser(email);
        return  responseEntity;
    }

    @PutMapping("/user/role")
    public ResponseEntity<?> alterRole(@RequestBody @Valid UserRoleGrantDto userRoleGrantDto){
        //userRoleGrantDto.setRole("ROLE_"+userRoleGrantDto.getRole());

        UserDto userInfoDto = adminService.alterRole(userRoleGrantDto);
        return new ResponseEntity<>(userInfoDto, HttpStatus.OK);
    }
    @PutMapping("/user/access")
    public ResponseEntity<?> access(@RequestBody AccessRequestDto accessRequestDto){
        User user = userRepository.findUserByEmail(accessRequestDto.getUser());
        ResponseEntity<?> responseEntity = null;
        switch(accessRequestDto.getOperation()){
            case LOCK -> responseEntity = adminService.lock(user);
            case UNLOCK -> responseEntity = adminService.unlock(user);
        }
        return responseEntity;
    }

}
