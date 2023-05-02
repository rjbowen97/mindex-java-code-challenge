package com.mindex.challenge.data;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class Compensation {
    @Id
    private String employee;
    private double salary;
    private Date effectiveDate;
    
    public Compensation() {
    }
    
    public String getEmployee() {
        return employee;
    }
    
    public void setEmployee(String employee) {
        this.employee = employee;
    }
    
    public double getSalary() {
        return salary;
    }
    
    public void setSalary(double salary) {
        this.salary = salary;
    }
    
    public Date getEffectiveDate() {
        return effectiveDate;
    }
    
    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
}
