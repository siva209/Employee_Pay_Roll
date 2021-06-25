package com.bridgelabz.employee;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

public class DBDemo {
	public static void main(String[] args) throws DatabaseException
	{
		String jdbcurl="jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
		String userName = "siva";
		String password = "siva";
		Connection connection;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver Loaded Successfully");
		}
		catch(ClassNotFoundException exception) {
			throw new DatabaseException("Driver unable to load");	
		}
		listDrivers();
		try {
			System.out.println("Connecting to database: " + jdbcurl);
			connection = DriverManager.getConnection(jdbcurl, userName, password);
			System.out.println(connection);
			System.out.println("Connection is successful: " + connection);
		}
		catch(Exception e) {
			throw new DatabaseException("Connection was unsuccessful");
		}	
	}
	private static void listDrivers() {
		Enumeration<Driver> driverList = DriverManager.getDrivers();
		while(driverList.hasMoreElements()) {
			Driver driverClass = (Driver) driverList.nextElement();
			System.out.println("  " + driverClass.getClass().getName());
		}
	}
}

