package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee create(Employee employee) {
        LOG.debug("Creating employee [{}]", employee);

        employee.setEmployeeId(UUID.randomUUID().toString());
        employeeRepository.insert(employee);

        return employee;
    }

    @Override
    public Employee read(String id) {
        LOG.debug("Creating employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        return employee;
    }

    @Override
    public ReportingStructure readReportingStructure(String id) {
        LOG.debug("Generating Reporting Structure for Employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        int totalNumberOfReports = getTotalNumberOfReports(employee);

        return new ReportingStructure(employee, totalNumberOfReports);

    }

    private int getTotalNumberOfReports(Employee employee) {
        LOG.debug("Counting number of reports for Employee [{}]", employee);

        int directReportCount = employee.getDirectReportCount();

        if (directReportCount > 0) {
            List<Employee> directReports = employee.getDirectReports();
            directReportCount += directReports
                    .stream()
                    .mapToInt(currentDirectReport -> employeeRepository
                            .findByEmployeeId(currentDirectReport.getEmployeeId()).getDirectReportCount())
                    .sum();
        }

        return directReportCount;
    }

    @Override
    public Employee update(Employee employee) {
        LOG.debug("Updating employee [{}]", employee);

        return employeeRepository.save(employee);
    }
}
