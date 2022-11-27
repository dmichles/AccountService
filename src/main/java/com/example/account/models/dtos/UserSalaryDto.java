package com.example.account.models.dtos;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class UserSalaryDto {
    private String name;
    private String lastname;
    private YearMonth period;
    private String salary;

    public UserSalaryDto(){
    }

    public UserSalaryDto(String name, String lastname, YearMonth period, String salary) {
        this.name = name;
        this.lastname = lastname;
        this.period = period;
        this.salary = salary;
    }

    public String getPeriod() {
        return period.format(DateTimeFormatter.ofPattern("MMMM-yyyy"));
    }

    public void setPeriod(YearMonth period) {
        this.period = period;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = String.format("%d dollar(s) %d cents",salary/100,salary%100);
    }
}
