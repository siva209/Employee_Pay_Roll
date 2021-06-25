package com.java.bridgelabz.employeepayroll;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.*;

import org.junit.Assert;
import org.junit.Test;

import com.bridgelabz.employee.Employee;
import com.bridgelabz.employee.EmployeePayrollService;

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
		public void givenDatabase_WhenUpdated_ShouldBeInSync() {
			List<Employee> employees = new ArrayList<>();
			EmployeePayrollService eService = new EmployeePayrollService(employees);
			employees = eService.readEmployeePayrollData(EmployeePayrollService.IOService.DB_IO);
			eService.updateEmployeeSalary("Terisa", 5000000);
			eService.readEmployeePayrollData(EmployeePayrollService.IOService.DB_IO);
			boolean result = eService.checkEmployeeDataSync("Terisa");
			System.out.println(result);
			assertTrue(result);
		}
	}
	

	
