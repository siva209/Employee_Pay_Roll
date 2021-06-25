package com.java.bridgelabz.employeepayroll;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

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
	    public void givenFile_onReadingFromFile_shouldMatchEmployeeCount() {
	        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
	        employeePayrollService.readDataFromFile(EmployeePayrollService.IOService.FILE_IO);
	        long entries = employeePayrollService.countEntries(EmployeePayrollService.IOService.FILE_IO);
	        Assert.assertEquals(3, entries);
	    }
	}

	
