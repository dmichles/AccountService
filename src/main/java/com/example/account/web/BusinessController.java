package com.example.account.web;

import com.example.account.models.dtos.PayrollDto;
import com.example.account.models.dtos.UserSalaryDto;
import com.example.account.models.entities.Payroll;
import com.example.account.models.entities.User;
import com.example.account.models.mappers.PayrollDtoMapper;
import com.example.account.models.mappers.UserSalaryMapper;
import com.example.account.repository.UserRepository;
import com.example.account.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Validated
@RestController
public class BusinessController {

    @Autowired
    PaymentService paymentService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserSalaryMapper userInfoSalaryMapper;

    @Autowired
    PayrollDtoMapper pdtoMapper;

    @PostMapping("/api/acct/payments")
    public ResponseEntity<?> savePayroll(@RequestBody List<@Valid PayrollDto> pdtos) {
        //List<Payroll> payrolls = pdtoMapper.mapFromList(pdtos);
        List<Payroll> payrolls = pdtos.stream().map(Payroll::of).collect(Collectors.toList());
        paymentService.savePayroll(payrolls);
        return new ResponseEntity<>(Map.of("status","Added successfully!"),HttpStatus.OK);
    }

    @PutMapping("/api/acct/payments")
    public ResponseEntity<?> changePayroll(@RequestBody @Valid PayrollDto pdto) {
        paymentService.changePayroll(pdto);
        return new ResponseEntity<>(Map.of("status","Updated successfully!"),HttpStatus.OK);
    }

    @GetMapping(value = "/api/empl/payment", params = "period")
    public ResponseEntity<?> getPayroll(@RequestParam @Pattern(regexp = "((0[1-9])|(1[0-2]))-(\\d{4})") String period) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findUserByEmail(userDetails.getUsername());
        DateTimeFormatter df = DateTimeFormatter.ofPattern("MM-yyyy");
        UserSalaryDto userInfoSalaryDto = userInfoSalaryMapper.mapDto(user, YearMonth.parse(period, df));
        return new ResponseEntity<>(userInfoSalaryDto, HttpStatus.OK);
    }

    @GetMapping(value = "/api/empl/payment")
    public ResponseEntity<?> getPayrolls() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findUserByEmail(userDetails.getUsername());
        List<UserSalaryDto> list = userInfoSalaryMapper.mapList(user);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
