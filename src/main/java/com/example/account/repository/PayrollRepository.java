package com.example.account.repository;

import com.example.account.models.entities.Payroll;
import org.springframework.data.repository.CrudRepository;

import java.time.YearMonth;
import java.util.List;

public interface PayrollRepository extends CrudRepository<Payroll, Long> {
    Payroll findPayrollByEmailAndPeriod(String email, YearMonth period);
    boolean existsPayrollByEmailAndPeriod(String email, YearMonth period);
    List<Payroll> findAllByEmail(String email);
}
