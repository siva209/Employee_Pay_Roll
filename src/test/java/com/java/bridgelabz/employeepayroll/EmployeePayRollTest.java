package com.java.bridgelabz.employeepayroll;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

import org.junit.Before;
import org.junit.Test;

import com.bridgelabz.employee.DatabaseException;
import com.bridgelabz.employee.Employee;
import com.bridgelabz.employee.EmployeePayrollService;
import com.bridgelabz.employee.EmployeePayrollService.IOService;
import com.google.gson.Gson;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class EmployeePayRollTest {
	@Test
	public void given3Employee_WhenWrittenToFile_ShouldMatchEmployeeEntries() {
		Employee[] arrayOfEmps = { new Employee(1, "siva", 100000), new Employee(2, "ramu", 200000),
				new Employee(3, "sravani", 300000) };
		EmployeePayrollService eService = new EmployeePayrollService(Arrays.asList(arrayOfEmps));
		eService.writeData(EmployeePayrollService.IOService.FILE_IO);
		long result = eService.countEntries(EmployeePayrollService.IOService.FILE_IO);
		assertEquals(3, result);
	}
	@Test
	public void given3Employee_WhenWrittenToFile_ShouldPrintEmployeeEntries() {
		Employee[] arrayOfEmps = { new Employee(1, "siva ", 100000), new Employee(2, "ramu", 200000),
				new Employee(3, "sravani", 300000) };
		EmployeePayrollService eService = new EmployeePayrollService(Arrays.asList(arrayOfEmps));
		eService.writeData(EmployeePayrollService.IOService.FILE_IO);
		eService.printData(EmployeePayrollService.IOService.FILE_IO);
		long result = eService.countEntries(EmployeePayrollService.IOService.FILE_IO);
		assertEquals(3, result);
	}
	
	//count entries
	@Test
		public void given3Employee_WhenWrittenToFile_ShouldReturnEmployeeEntries() {
			Employee[] arrayOfEmps = { new Employee(1, "siva", 100000), new Employee(2, "ramu", 200000),
					new Employee(3, "sravani", 300000) };
			EmployeePayrollService eService = new EmployeePayrollService(Arrays.asList(arrayOfEmps));
			eService.writeData(EmployeePayrollService.IOService.FILE_IO);
			eService.printData(EmployeePayrollService.IOService.FILE_IO);
			long result = eService.countEntries(EmployeePayrollService.IOService.FILE_IO);
			assertEquals(3, result);
		}

		@Test
		public void given3Employee_WhenRetrieved_ShouldMatchEmployeeCount() {
			List<Employee> employees = new ArrayList<>();
			EmployeePayrollService eService = new EmployeePayrollService(employees);
			employees = eService.readEmployeePayrollData(EmployeePayrollService.IOService.DB_IO);
			assertEquals(3, employees.size());
		}
		@Test
		public void givenDatabase_WhenUpdated_ShouldBeInSync() throws SQLException, DatabaseException {
			List<Employee> employees = new ArrayList<>();
			EmployeePayrollService eService = new EmployeePayrollService(employees);
			employees = eService.readEmployeePayrollData(EmployeePayrollService.IOService.DB_IO);
			eService.updateEmployeeSalary("Terisa", 6000000);
			boolean result = eService.checkEmployeeDataSync("Terisa");
//			System.out.println(result);
			assertTrue(result);
		}
		@Test
		public void givenEmployees_WhenRetrievedByName_ShouldReturnTrue() {
			List<Employee> employees = new ArrayList<>();
			EmployeePayrollService eService = new EmployeePayrollService();
			employees = eService.readEmployeePayrollData(EmployeePayrollService.IOService.DB_IO);
			assertTrue(eService.checkEmployeeDataSync("Terisa"));
		}
		@Test
		public void givenDateRange_WhenRetrieved_ShouldMatchEmployeeCount() throws DatabaseException {
			List<Employee> employees = new ArrayList<>();
			EmployeePayrollService eService = new EmployeePayrollService();
			eService.readEmployeePayrollData(EmployeePayrollService.IOService.DB_IO);
			LocalDate start = LocalDate.of(2018, 01, 01);
			LocalDate end = LocalDate.now();
			employees = eService.getEmployeeByDate(start, end);
			assertEquals(2, employees.size());
		}
		
		@Test
		public void givenEmployees_WhenRetrievedAverage_ShouldReturnTrue() throws DatabaseException {
			EmployeePayrollService eService = new EmployeePayrollService();
			eService.readEmployeePayrollData(EmployeePayrollService.IOService.DB_IO);
			assertTrue(eService.getEmployeeAverageByGender().get("M").equals(3000000.0));
		}
		@Test
		public void givenEmployees_WhenRetrievedMaximumSalaryByGender_ShouldReturnTrue() throws DatabaseException {
			EmployeePayrollService eService = new EmployeePayrollService();
			eService.readEmployeePayrollData(EmployeePayrollService.IOService.DB_IO);
			assertTrue(eService.getEmployeeMaximumSalaryByGender().get("M").equals(5000000.0));
		}
		@Test
		public void givenEmployees_WhenRetrievedMinimumSalaryByGender_ShouldReturnTrue() throws DatabaseException {
			EmployeePayrollService eService = new EmployeePayrollService();
			eService.readEmployeePayrollData(EmployeePayrollService.IOService.DB_IO);
			assertTrue(eService.getEmployeeMinimumSalaryByGender().get("M").equals(2000000.0));
		}
		@Test
		public void givenEmployees_WhenRetrievedSumByGender_ShouldReturnTrue() throws DatabaseException {
			EmployeePayrollService eService = new EmployeePayrollService();
			eService.readEmployeePayrollData(EmployeePayrollService.IOService.DB_IO);
			assertTrue(eService.getEmployeeSumByGender().get("M").equals(9000000.0));
		}
		@Test
		public void givenEmployees_WhenRetrievedCountByGender_ShouldReturnTrue() throws DatabaseException {
			EmployeePayrollService eService = new EmployeePayrollService();
			eService.readEmployeePayrollData(EmployeePayrollService.IOService.DB_IO);
			System.out.println(eService.getEmployeeAverageByGender().get("M"));
			assertTrue(eService.getEmployeeCountByGender().get("M").equals(3.0));
		}
		@Test
		public void givenNewEmployee_WhenAdded_ShouldSyncWithDB() throws SQLException, DatabaseException {
			EmployeePayrollService employeePayrollService = new EmployeePayrollService();
			employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
			employeePayrollService.addEmployeeToPayrollAndDepartment("Mark", "M", 5000000.0, LocalDate.now(), "Marketing");
			boolean result = employeePayrollService.checkEmployeeDataSync("Mark");
			assertEquals(true, result);
		}
		@Test
		public void givenEmployeeDB_WhenAnEmployeeIsDeleted_ShouldSyncWithDB() throws DatabaseException {
			EmployeePayrollService employeeService = new EmployeePayrollService();
			employeeService.readEmployeePayrollData(IOService.DB_IO);
			List<Employee> list = employeeService.deleteEmployee("Mark");
			assertEquals(3, list.size());
		}
		@Test
		public void givenNewEmployee_WhenAddedToPayroll_ShouldBeAddedToDepartment() throws SQLException, DatabaseException {
			EmployeePayrollService employeePayrollService = new EmployeePayrollService();
			employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
			employeePayrollService.addEmployeeToPayrollAndDepartment("Max", "M", 4000000.0, LocalDate.now(), "Sales");
			boolean result = employeePayrollService.checkEmployeeDataSync("Max");
			assertEquals(true, result);
		}
		@Test
		void givenEmployeeId_WhenRemoved_shouldReturnNumberOfActiveEmployees() throws DatabaseException {
			EmployeePayrollService employeePayrollService = new EmployeePayrollService();
			List<Employee> ActiveEmployees = employeePayrollService.removeEmployeeFromPayroll(3);
			assertEquals(3, ActiveEmployees.size());
		}
		@Test
		public void given6Employees_WhenAddedToDB_ShouldMatchEmployeeEntries() throws DatabaseException {
			Employee[] arrayOfEmp = { new Employee(1, "Jeff Bezos","M", 100000.0,LocalDate.now(),"Sales"),
					new Employee(2, "Bill Gates","M", 200000.0,LocalDate.now(), "Marketing"),
					new Employee(3, "Mark ","M", 150000.0,LocalDate.now(), "Technical"),
					new Employee(4, "Sundar","M", 400000.0,LocalDate.now(), "Sales"),
					new Employee(5, "Mukesh ","M", 4500000.0,LocalDate.now(),"Sales"),
					new Employee(6, "Anil","M", 300000.0,LocalDate.now(), "Sales") };
			EmployeePayrollService employeePayrollService = new EmployeePayrollService();
			employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
			Instant start = Instant.now();
			employeePayrollService.addEmployeesToPayroll(Arrays.asList(arrayOfEmp));
			Instant end = Instant.now();
			System.out.println("Duration without Thread: " + Duration.between(start, end));
			Instant threadStart = Instant.now();
			employeePayrollService.addEmployeesToPayrollWithThreads(Arrays.asList(arrayOfEmp));
			Instant threadEnd = Instant.now();
			System.out.println("Duration with Thread: " + Duration.between(threadStart, threadEnd));
			long result = employeePayrollService.countEntries(IOService.DB_IO);
			assertEquals(13, result);
		}
		@Test
		public void given2Employees_WhenUpdatedSalary_ShouldSyncWithDB() throws DatabaseException {
			Map<String, Double> salaryMap = new HashMap<>();
			salaryMap.put("Bill Gates",700000.0);
			salaryMap.put("Mukesh",800000.0);
			EmployeePayrollService employeePayrollService = new EmployeePayrollService();
			employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
			Instant start = Instant.now();
			employeePayrollService.updatePayroll(salaryMap);
			Instant end = Instant.now();
			System.out.println("Duration with Thread: " + Duration.between(start, end));
			boolean result = employeePayrollService.checkEmployeeListSync(Arrays.asList("Bill Gates,Mukesh"));
			assertEquals(true,result);
		}
		
		
		@Before
		public void setup() {
			RestAssured.baseURI = "http://localhost";
			RestAssured.port = 3000;
		}
		private Employee[] getEmployeeList() {
			Response response = RestAssured.get("/employees");
			System.out.println("Employee payroll entries in JSONServer:\n"+response.asString());
			Employee[] arrayOfEmp = new Gson().fromJson(response.asString(),Employee[].class);
			return arrayOfEmp;
		}
		private Response addEmployeeToJsonServer(Employee employee) {
			String empJson = new Gson().toJson(employee);
			RequestSpecification request = RestAssured.given();
			request.header("Content-Type","application/json");
			request.body(empJson);
			return request.post("/employees");
		}
		@Test
		public void givenNewEmployee_WhenAdded_ShouldMatch201ResponseAndCount() {
			Employee[] arrayOfEmp = getEmployeeList();
			EmployeePayrollService eService = new EmployeePayrollService(Arrays.asList(arrayOfEmp));
			Employee employee = null; 
			employee = new Employee(0,"Mark Zuckerberg","M",3000000.0,LocalDate.now());
			Response response = addEmployeeToJsonServer(employee);
			int statusCode = response.getStatusCode();
			assertEquals(201,statusCode);
			employee = new Gson().fromJson(response.asString(), Employee.class);
			eService.addEmployeeToPayroll(employee);
			long count = eService.countEntries(IOService.REST_IO);
			assertEquals(3,count);	
		}
	}
	

		
	
