package com.example.account.models.entities;

import com.example.account.models.dtos.PayrollDto;
import com.example.account.models.entities.converters.YearMonthConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payroll")
public class Payroll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long payrollid;

    @NotBlank
    @Pattern(regexp = "\\w+@acme\\.com", message = "Validation failed for email")
    private String email;

    @Convert(converter = YearMonthConverter.class)
    private YearMonth period;

    @Min(value = 0)
    private Long salary;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userid")
    private User user;

    public Payroll(String email, YearMonth period, Long salary) {
        this.email = email;
        this.period = period;
        this.salary = salary;
    }
    public static Payroll of(PayrollDto payrollDto){
        return new Payroll(
                payrollDto.getEmployee(),
                YearMonth.parse(payrollDto.getPeriod(), DateTimeFormatter.ofPattern("MM-yyyy")),
                payrollDto.getSalary()
                );
    }
}
