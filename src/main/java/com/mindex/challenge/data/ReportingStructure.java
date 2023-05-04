package com.mindex.challenge.data;

/**
* The ReportingStructure class represents a structure containing information about an employee and the number of reports they manage. 
*/
public class ReportingStructure {

    /**
     * An Employee object representing the employee for which reporting structure is
     * being created
     */
    private Employee employee;

    /**
     * An integer representing the number of reports the employee manages
     */
    private int numberOfReports;

    /**
     * Constructs an empty ReportingStructure object.
     */
    public ReportingStructure() {
    }

    /**
     * Constructs a ReportingStructure object with specified employee and number of
     * reports.
     * 
     * @param employee        The employee for which reporting structure is being
     *                        created
     * @param numberOfReports The number of reports the employee manages
     */
    public ReportingStructure(Employee employee, int numberOfReports) {
        this.employee = employee;
        this.numberOfReports = numberOfReports;
    }

    /**
     * Returns the employee for which reporting structure is being created.
     * 
     * @return An Employee object representing the employee for which reporting
     *         structure is being created
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * Sets the employee for which reporting structure is being created.
     * 
     * @param employee An Employee object representing the employee for which
     *                 reporting structure is being created
     */
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    /**
     * Returns the number of reports the employee manages.
     * 
     * @return An integer representing the number of reports the employee manages
     */
    public int getNumberOfReports() {
        return numberOfReports;
    }

    /**
     * Sets the number of reports the employee manages.
     * 
     * @param numberOfReports An integer representing the number of reports the
     *                        employee manages
     */
    public void setNumberOfReports(int numberOfReports) {
        this.numberOfReports = numberOfReports;
    }
}
