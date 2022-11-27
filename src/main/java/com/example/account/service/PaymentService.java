package com.example.account.service;

import com.example.account.models.dtos.PayrollDto;
import com.example.account.models.entities.Payroll;
import com.example.account.models.entities.User;
import com.example.account.repository.PayrollRepository;
import com.example.account.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    PayrollRepository payrollRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional
    public void get(List<User> users) {
        for (User user : users) {
            userRepository.save(user);
            for (Payroll payroll : user.getPayroll()) {
                payroll.getPeriod();
            }
        }
    }

    @Transactional
    public void savePayroll(List<Payroll> payrolls) {
        for (Payroll payroll : payrolls) {
            if (!payrollRepository.existsPayrollByEmailAndPeriod(payroll.getEmail(), payroll.getPeriod())) {
                User user = userRepository.findUserByEmail(payroll.getEmail());
                payroll.setUser(user);
                payrollRepository.save(payroll);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Duplicate email and period");
            }
        }
    }

    public void changePayroll(PayrollDto pdto) {
        YearMonth yearMonth = YearMonth.parse(pdto.getPeriod(), DateTimeFormatter.ofPattern("MM-yyyy"));
        if (payrollRepository.existsPayrollByEmailAndPeriod(pdto.getEmployee(), yearMonth)) {
            Payroll payroll = payrollRepository.findPayrollByEmailAndPeriod(pdto.getEmployee(), yearMonth);
            payroll.setSalary(pdto.getSalary());
            payrollRepository.save(payroll);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not found");
        }
    }
}
