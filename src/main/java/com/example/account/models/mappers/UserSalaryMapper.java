package com.example.account.models.mappers;

import com.example.account.models.dtos.UserSalaryDto;
import com.example.account.models.entities.Payroll;
import com.example.account.models.entities.User;
import com.example.account.repository.PayrollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class UserSalaryMapper {
    @Autowired
    PayrollRepository payrollRepository;

    public UserSalaryDto mapDto(User user, YearMonth period){

        Payroll payroll = payrollRepository.findPayrollByEmailAndPeriod(user.getEmail(), period);
        if (payroll == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Not Found");
        }
        UserSalaryDto userSalaryDto = new UserSalaryDto();
        userSalaryDto.setName(user.getName());
        userSalaryDto.setLastname(user.getLastname());
        userSalaryDto.setPeriod(period);
        userSalaryDto.setSalary(payroll.getSalary());
        return userSalaryDto;
    }


    public List<UserSalaryDto> mapList(User user){


        String name = user.getName();
        String lastname = user.getLastname();

        List<Payroll> payrolls = user.getPayroll();
        List<UserSalaryDto> list = new ArrayList<>();

        for (Payroll payroll: payrolls) {
            UserSalaryDto userSalaryDto = new UserSalaryDto();
            userSalaryDto.setName(name);
            userSalaryDto.setLastname(lastname);
            userSalaryDto.setPeriod(payroll.getPeriod());
            userSalaryDto.setSalary(payroll.getSalary());
            list.add(0,userSalaryDto);
        }
        return list;
    }
}
