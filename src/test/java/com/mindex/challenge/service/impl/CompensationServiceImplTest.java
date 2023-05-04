package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {

    private String employeeUrl;

    private String compensationCreateUrl;
    private String compensationReadUrl;

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private CompensationService compensationService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        employeeUrl = "http://localhost:" + port + "/employee";

        compensationCreateUrl = "http://localhost:" + port + "/compensation";
        compensationReadUrl = "http://localhost:" + port + "/compensation/{id}";
    }

    @Test
    public void testCreate() {
        Employee testEmployee = new Employee();
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");
        testEmployee.setDepartment("Engineering");
        testEmployee.setPosition("Developer");

        Employee createdEmployee = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class)
                .getBody();

        Compensation testCompensation = new Compensation();
        testCompensation.setEmployee(createdEmployee);
        testCompensation.setSalary(100000.0);

        Date date = convertStringToDate("2023-05-03");
        testCompensation.setEffectiveDate(date);

        // Create check
        Compensation createdCompensation = restTemplate
                .postForEntity(compensationCreateUrl, testCompensation, Compensation.class).getBody();

        assertNotNull(createdCompensation.getEmployee());
        assertCompensationEquivalence(testCompensation, createdCompensation);
    }

    private Date convertStringToDate(String dateString) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateString, dateFormat);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return date;
    }

    @Test
    public void testRead() {

        Employee testEmployee = new Employee();
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");
        testEmployee.setDepartment("Engineering");
        testEmployee.setPosition("Developer");

        Employee createdEmployee = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class)
                .getBody();

        Compensation testCompensation = new Compensation();
        testCompensation.setEmployee(createdEmployee);
        testCompensation.setSalary(100000.0);

        Date date = convertStringToDate("2023-05-03");
        testCompensation.setEffectiveDate(date);

        Compensation createdCompensation = restTemplate
                .postForEntity(compensationCreateUrl, testCompensation, Compensation.class).getBody();

        // Read check
        Compensation readCompensation = restTemplate
                .getForEntity(compensationReadUrl, Compensation.class,
                        createdCompensation.getEmployee().getEmployeeId())
                .getBody();

        assertNotNull(createdCompensation.getEmployee());
        assertCompensationEquivalence(createdCompensation, readCompensation);
    }

    private static void assertCompensationEquivalence(Compensation expected, Compensation actual) {
        assertTrue(expected.getEmployee().equals(actual.getEmployee()));

        assertEquals(expected.getSalary(), actual.getSalary(), 0.001);
        assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate());
    }
}
