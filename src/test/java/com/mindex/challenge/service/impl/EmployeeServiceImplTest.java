package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeServiceImplTest {

    private String employeeUrl;
    private String employeeIdUrl;
    private String employeeReportingStructureUrl;

    @Autowired
    private EmployeeService employeeService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        employeeUrl = "http://localhost:" + port + "/employee";
        employeeIdUrl = "http://localhost:" + port + "/employee/{id}";
        employeeReportingStructureUrl = "http://localhost:" + port + "/employee/reporting-structure/{id}";
    }

    @Test
    public void testCreateEmployee() {
        Employee testEmployee = new Employee();
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");
        testEmployee.setDepartment("Engineering");
        testEmployee.setPosition("Developer");

        Employee createdEmployee = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class).getBody();

        assertNotNull(createdEmployee.getEmployeeId());
        assertEmployeeEquivalence(testEmployee, createdEmployee);
    }

    @Test
    public void testReadEmployee() {
        Employee testEmployee = new Employee();
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");
        testEmployee.setDepartment("Engineering");
        testEmployee.setPosition("Developer");

        Employee createdEmployee = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class).getBody();

        Employee readEmployee = restTemplate
                .getForEntity(employeeIdUrl, Employee.class, createdEmployee.getEmployeeId()).getBody();

        assertEquals(createdEmployee.getEmployeeId(), readEmployee.getEmployeeId());
        assertEmployeeEquivalence(createdEmployee, readEmployee);
    }

    @Test
    public void testReadReportingStructure() {
        Employee georgeHarrison = new Employee();
        georgeHarrison.setFirstName("George");
        georgeHarrison.setLastName("Harrison");
        georgeHarrison.setDepartment("Music");
        georgeHarrison.setPosition("Guitarist");

        Employee createdGeorgeHarrison = restTemplate
                .postForEntity(employeeUrl, georgeHarrison, Employee.class)
                .getBody();

        Employee peteBest = new Employee();
        peteBest.setFirstName("Pete");
        peteBest.setLastName("Best");
        peteBest.setDepartment("Music");
        peteBest.setPosition("Drummer");
        Employee createdPeteBest = restTemplate.postForEntity(employeeUrl, peteBest, Employee.class)
                .getBody();

        List<Employee> ringoStarrDirectReports = Arrays.asList(new Employee[] {
                createdPeteBest,
                createdGeorgeHarrison
        });
        Employee ringoStarr = new Employee();
        ringoStarr.setFirstName("Ringo");
        ringoStarr.setLastName("Starr");
        ringoStarr.setDepartment("Music");
        ringoStarr.setPosition("Drummer");
        ringoStarr.setDirectReports(ringoStarrDirectReports);
        Employee createdRingoStarr = restTemplate.postForEntity(employeeUrl, ringoStarr, Employee.class)
                .getBody();

        Employee paulMcCartney = new Employee();
        paulMcCartney.setFirstName("Paul");
        paulMcCartney.setLastName("McCartney");
        paulMcCartney.setDepartment("Music");
        paulMcCartney.setPosition("Singer");
        Employee createdPaulMcCartney = restTemplate
                .postForEntity(employeeUrl, paulMcCartney, Employee.class)
                .getBody();

        List<Employee> johnLennonDirectReports = Arrays.asList(new Employee[] {
                createdPaulMcCartney,
                createdRingoStarr
        });
        Employee johnLennon = new Employee();
        johnLennon.setFirstName("John");
        johnLennon.setLastName("Lennon");
        johnLennon.setDepartment("Engineering");
        johnLennon.setPosition("Developer");
        johnLennon.setDirectReports(johnLennonDirectReports);
        Employee createdJohnLennon = restTemplate.postForEntity(employeeUrl, johnLennon, Employee.class)
                .getBody();

        ReportingStructure readReportingStructure = restTemplate
                .getForEntity(employeeReportingStructureUrl, ReportingStructure.class,
                        createdJohnLennon.getEmployeeId())
                .getBody();

        assertEquals(4, readReportingStructure.getNumberOfReports());
        assertEmployeeEquivalence(createdJohnLennon, readReportingStructure.getEmployee());
    }

    @Test
    public void testUpdateEmployee() {
        Employee testEmployee = new Employee();
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");
        testEmployee.setDepartment("Engineering");
        testEmployee.setPosition("Developer");

        Employee createdEmployee = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class).getBody();

        Employee readEmployee = restTemplate
                .getForEntity(employeeIdUrl, Employee.class, createdEmployee.getEmployeeId()).getBody();

        readEmployee.setPosition("Development Manager");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Employee updatedEmployee = restTemplate.exchange(employeeIdUrl,
                HttpMethod.PUT,
                new HttpEntity<Employee>(readEmployee, headers),
                Employee.class,
                readEmployee.getEmployeeId()).getBody();

        assertEmployeeEquivalence(readEmployee, updatedEmployee);
    }

    private static void assertEmployeeEquivalence(Employee expected, Employee actual) {
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getDepartment(), actual.getDepartment());
        assertEquals(expected.getPosition(), actual.getPosition());
    }
}
