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

                Employee createdEmployee = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class)
                                .getBody();

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

                Employee createdEmployee = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class)
                                .getBody();

                Employee readEmployee = restTemplate
                                .getForEntity(employeeIdUrl, Employee.class, createdEmployee.getEmployeeId()).getBody();

                assertEquals(createdEmployee.getEmployeeId(), readEmployee.getEmployeeId());
                assertEmployeeEquivalence(createdEmployee, readEmployee);
        }

        @Test
        public void testReadReportingStructure() {
                // Create a new Employee object named georgeHarrison and set its attributes
                Employee georgeHarrison = new Employee();
                georgeHarrison.setFirstName("George");
                georgeHarrison.setLastName("Harrison");
                georgeHarrison.setDepartment("Music");
                georgeHarrison.setPosition("Guitarist");

                Employee createdGeorgeHarrison = restTemplate
                                .postForEntity(employeeUrl, georgeHarrison, Employee.class)
                                .getBody();

                // Create a new Employee object named peteBest and set its attributes
                Employee peteBest = new Employee();
                peteBest.setFirstName("Pete");
                peteBest.setLastName("Best");
                peteBest.setDepartment("Music");
                peteBest.setPosition("Drummer");

                Employee createdPeteBest = restTemplate.postForEntity(employeeUrl, peteBest, Employee.class)
                                .getBody();

                // Create a list of direct reports for ringoStarr consisting of createdPeteBest
                // and createdGeorgeHarrison
                List<Employee> ringoStarrDirectReports = Arrays.asList(new Employee[] {
                                createdPeteBest,
                                createdGeorgeHarrison
                });

                // Create a new Employee object named ringoStarr, set its attributes and add the
                // list of direct reports to it
                Employee ringoStarr = new Employee();
                ringoStarr.setFirstName("Ringo");
                ringoStarr.setLastName("Starr");
                ringoStarr.setDepartment("Music");
                ringoStarr.setPosition("Drummer");
                ringoStarr.setDirectReports(ringoStarrDirectReports);

                Employee createdRingoStarr = restTemplate.postForEntity(employeeUrl, ringoStarr, Employee.class)
                                .getBody();

                // Create a new Employee object named paulMcCartney and set its attributes
                Employee paulMcCartney = new Employee();
                paulMcCartney.setFirstName("Paul");
                paulMcCartney.setLastName("McCartney");
                paulMcCartney.setDepartment("Music");
                paulMcCartney.setPosition("Singer");

                Employee createdPaulMcCartney = restTemplate
                                .postForEntity(employeeUrl, paulMcCartney, Employee.class)
                                .getBody();

                // Create a list of direct reports for johnLennon consisting of
                // createdPaulMcCartney and createdRingoStarr
                List<Employee> johnLennonDirectReports = Arrays.asList(new Employee[] {
                                createdPaulMcCartney,
                                createdRingoStarr
                });

                // Create a new Employee object named johnLennon, set its attributes and add the
                // list of direct reports to it
                Employee johnLennon = new Employee();
                johnLennon.setFirstName("John");
                johnLennon.setLastName("Lennon");
                johnLennon.setDepartment("Engineering");
                johnLennon.setPosition("Developer");
                johnLennon.setDirectReports(johnLennonDirectReports);

                Employee createdJohnLennon = restTemplate.postForEntity(employeeUrl, johnLennon, Employee.class)
                                .getBody();

                Employee readJohnLennon = restTemplate
                                .getForEntity(employeeIdUrl, Employee.class, createdJohnLennon.getEmployeeId())
                                .getBody();

                ReportingStructure johnLennonReportingStructure = restTemplate
                                .getForEntity(employeeReportingStructureUrl, ReportingStructure.class,
                                                readJohnLennon.getEmployeeId())
                                .getBody();

                assertEquals(4, johnLennonReportingStructure.getNumberOfReports());
                assertEmployeeEquivalence(createdJohnLennon, johnLennonReportingStructure.getEmployee());
        }

        @Test
        public void testUpdateEmployee() {
                Employee testEmployee = new Employee();
                testEmployee.setFirstName("John");
                testEmployee.setLastName("Doe");
                testEmployee.setDepartment("Engineering");
                testEmployee.setPosition("Developer");

                Employee createdEmployee = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class)
                                .getBody();

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
                expected.equals(actual);
        }
}
