/**
 * This class represents the Compensation object.
 * 
 * @author RJ Bowen
 */
package com.mindex.challenge.data;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class Compensation {
    @Id
    private String employee;
    private double salary;
    private Date effectiveDate;

    /**
     * Default constructor for the Compensation object.
     */
    public Compensation() {
    }

    /**
     * Getter method for the employee field.
     * 
     * @return The employee associated with this compensation object.
     */
    public String getEmployee() {
        return employee;
    }

    /**
     * Setter method for the employee field.
     * 
     * @param employee The employee to associate with this compensation object.
     */
    public void setEmployee(String employee) {
        this.employee = employee;
    }

    /**
     * Getter method for the salary field.
     * 
     * @return The salary associated with this compensation object.
     */
    public double getSalary() {
        return salary;
    }

    /**
     * Setter method for the salary field.
     * 
     * @param salary The salary to associate with this compensation object.
     */
    public void setSalary(double salary) {
        this.salary = salary;
    }

    /**
     * Getter method for the effective date field.
     * 
     * @return The effective date associated with this compensation object.
     */
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * Setter method for the effective date field.
     * 
     * @param effectiveDate The effective date to associate with this compensation
     *                      object.
     */
    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
}