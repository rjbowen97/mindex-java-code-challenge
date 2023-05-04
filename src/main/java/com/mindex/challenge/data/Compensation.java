package com.mindex.challenge.data;

import java.util.Date;
import java.util.Objects;

import org.springframework.data.mongodb.core.mapping.DBRef;

/**
 * This class represents the Compensation object.
 * NOTE: I have assumed that Compensations will be 1-to-1 with Employees. I also
 * assumed that only the id of the Employee is to be stored in
 * the Compensation object, rather than the entire Employee
 * 
 * @author RJ Bowen
 */
public class Compensation {
    @DBRef
    private Employee employee;
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
    public Employee getEmployee() {
        return employee;
    }

    /**
     * Setter method for the employee field.
     * 
     * @param employee The employee to associate with this compensation object.
     */
    public void setEmployee(Employee employee) {
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

    @Override
    public boolean equals(Object o) {
        // check if objects are identical
        if (this == o)
            return true;
        // check if object is null or a different type
        if (o == null || getClass() != o.getClass())
            return false;

        Compensation that = (Compensation) o;

        // compare values of fields
        if (Double.compare(that.salary, salary) != 0)
            return false;
        if (!Objects.equals(employee, that.employee))
            return false;
        return Objects.equals(effectiveDate, that.effectiveDate);
    }

}