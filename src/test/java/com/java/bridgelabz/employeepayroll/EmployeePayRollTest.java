package com.java.bridgelabz.employeepayroll;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;


import org.junit.Test;

import com.bridgelabz.employee.DatabaseException;
import com.bridgelabz.employee.Employee;
import com.bridgelabz.employee.EmployeePayrollService;
import com.bridgelabz.employee.EmployeePayrollService.IOService;

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
			long result = employeePayrollService.countEntries(IOService.DB_IO);
			assertEquals(7, result);
		}
	}
		
	
