package com.example.account.models.mappers;

import com.example.account.models.dtos.PayrollDto;
import com.example.account.models.entities.Payroll;
import org.springframework.context.annotation.Configuration;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class PayrollDtoMapper {
    public Payroll mapFrom(PayrollDto pdto){
        Payroll payroll = new Payroll();
        payroll.setEmail(pdto.getEmployee());
        payroll.setPeriod(YearMonth.parse(pdto.getPeriod(), DateTimeFormatter.ofPattern("MM-yyyy")));
        payroll.setSalary(pdto.getSalary());
        return payroll;
    }

    public List<Payroll> mapFromList(List<PayrollDto> pdtos){
        List<Payroll> payrolls = new ArrayList<>();
        for (PayrollDto pdto: pdtos){
            Payroll payroll = new Payroll();
            payroll.setEmail(pdto.getEmployee());
            payroll.setPeriod(YearMonth.parse(pdto.getPeriod(), DateTimeFormatter.ofPattern("MM-yyyy")));
            payroll.setSalary(pdto.getSalary());
            payrolls.add(payroll);
        }
        return payrolls;
    }
}
