package com.bridgelabz.employee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class EmployeePayrollDBService {
	
	
	private PreparedStatement preparedStatement = null;
	private static EmployeePayrollDBService employeePayrollDBService;
	private EmployeePayrollDBService() {
	}
	//Singleton Object
	public static EmployeePayrollDBService getInstance() {
		if(employeePayrollDBService == null) {
			employeePayrollDBService = new EmployeePayrollDBService();
		}
		return employeePayrollDBService;
	}
	private void getPreparedStatement() throws SQLException, DatabaseException {
		Connection connection = this.getConnection();
		if(preparedStatement == null) {
			String sql = "Select * from employee_payroll where name = ?;";
		preparedStatement = connection.prepareStatement(sql);
		}
	}
	
	
	
	
	
	private Connection getConnection() throws DatabaseException {
		String jdbcurl = "jdbc:mysql://localhost:3306/payroll_service";
		String userName = "siva";
		String password = "siva";
		Connection connection;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcurl, userName, password);
			String sql = "Select * from employee_payroll where name = ?;";
			preparedStatement = connection.prepareStatement(sql);
		}
		catch(Exception e) {
			throw new DatabaseException("Connection was unsuccessful");
		}
		return connection;
	}
	
	public List<Employee> readData() throws DatabaseException {
		String sql = "Select * from employee_payroll; ";
		List<Employee> employeeData = new ArrayList<>();
		try(Connection connection = this.getConnection();){
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			while(result.next()) {
				int id = result.getInt("id");
				String name = result.getString("name");
				double salary = result.getDouble("salary");
				LocalDate start = result.getDate("start").toLocalDate();
				employeeData.add(new Employee(id, name, salary, start));
			}
		}
			catch(DatabaseException exception) {
				System.out.println(exception);
			}
		catch(Exception exception) {
			throw new DatabaseException("Unable to execute query");	
		}
		return employeeData;
	}
	
	private int updateEmployeeUsingPreparedStatement(String name, double salary) throws DatabaseException, SQLException {
		Connection connection = this.getConnection();
		String sql = "Update employee_payroll set salary = ? where name = ? ; " ; 
		PreparedStatement prepareStatement = connection.prepareStatement(sql);
		prepareStatement.setString(2, name);
		prepareStatement.setDouble(1, salary);
		return prepareStatement.executeUpdate();
	}
	public List<Employee> getEmployeeData(String name) throws DatabaseException{
		try {
			getPreparedStatement();
			preparedStatement.setString(1,  name);
			return getEmployeeData(preparedStatement.executeQuery());
		} catch (DatabaseException | SQLException e) {
			throw new DatabaseException("Unable to get results");
		}
	}
	//Update records
	public int updateEmployeeData(String name, double salary) throws DatabaseException, SQLException {
		return this.updateEmployeeUsingPreparedStatement(name, salary);
	}
	public List<Employee> getEmployeeData(ResultSet result) throws DatabaseException{
		List<Employee> employeeData = new ArrayList<Employee>();
		try {
			while(result.next()) {
				int id = result.getInt("id");
				String name = result.getString("name");
				double salary = result.getDouble("salary");
				LocalDate start = result.getDate("start").toLocalDate();
				employeeData.add(new Employee(id, name, salary, start));
			}
		} catch (SQLException e) {
			throw new DatabaseException("Unable to get results");
		}
		return employeeData;
	}
}
