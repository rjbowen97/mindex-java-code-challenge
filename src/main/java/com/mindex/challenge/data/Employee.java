package com.mindex.challenge.data;

import java.util.List;

import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Employee {
    private String employeeId;
    private String firstName;
    private String lastName;
    private String position;
    private String department;
    private List<Employee> directReports;

    public Employee() {
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<Employee> getDirectReports() {
        return directReports;
    }

    public void setDirectReports(List<Employee> directReports) {
        this.directReports = directReports;
    }

    /**
     * This method gets the number of direct reports for an employee. It checks if
     * the directReports field is not null
     * and returns its size, which represents the number of direct reports. If the
     * field is null, it returns 0.
     * 
     * Note: This method is marked as @Transient and @JsonIgnore, which means that
     * it will not be serialized or persisted.
     *
     * @return an integer representing the number of direct reports of the employee
     */
    @Transient
    @JsonIgnore
    public int getDirectReportCount() {
        if (this.directReports != null) {
            return this.directReports.size();
        }

        return 0;
    }
}
