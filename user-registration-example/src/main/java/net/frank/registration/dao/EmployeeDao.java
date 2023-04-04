package net.frank.registration.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import net.frank.registration.model.Employee;

/// This class performs CRUD on the Employees' table
public class EmployeeDao {
//	Database configurations
	private String jdbcUrl = "jdbc:mysql://localhost:3306/from_java?useSSL=false";
	private String  jdbcUsername = "root";
	private String  jdbcPassword = "";
	
//	insert query
	private static final String INSERT_EMPLOYEE_SQL = "INSERT INTO employees" + "(username, firstname, surname, password, contact) VALUES" + "(?,?,?,?,?);";

//	select by ID
	private static final String SELECT_EMPLOYEE_BY_ID_SQL = "SELECT `id`, `username`, `firstname`, `surname`, `password`, `contact` FROM `employees` WHERE ?";

//	select all employees
	private static final String SELECT_ALL_EMPLOYEES_SQL ="SELECT * FROM employees";
	
//	Delete by ID
	private static final String DELETE_EMPLOYEE_SQL = "DELETE FROM employees WHERE id=?;";
	
//	Update User
	private static final String UPDATE_EMPLOYEE_SQL = "UPDATE `employees` SET `username` = ?, `firstname` = ?, `surname` = ?, `password` = ?, `contact` = ? WHERE `employees`.`id` = ?;";

	
//	get connection method
	protected Connection getConnection() {
		Connection connection = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
		} catch(SQLException e){
			e.printStackTrace();
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return connection;
	}
	
//	insert Employee method
	public void insertEmployee(Employee employee) throws SQLException {
		try(Connection connection = getConnection();PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EMPLOYEE_SQL);) {
			preparedStatement.setString(1, employee.getUserName());
			preparedStatement.setString(2, employee.getFirstName());
			preparedStatement.setString(3, employee.getSurnameName());
			preparedStatement.setString(4, employee.getPassword());
			preparedStatement.setString(5, employee.getContact());
			
//			execute statement
			preparedStatement.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
//	Update Employee Method
	public boolean updateEmployee(Employee employee) throws SQLException {
		boolean rowUpdated = false;
		try(Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_EMPLOYEE_SQL);){
			System.out.println(preparedStatement);
//			preparedStatement.setInt(1, employee.getId());
			preparedStatement.setString(1, employee.getUserName());
			preparedStatement.setString(2, employee.getFirstName());
			preparedStatement.setString(3, employee.getSurnameName());
			preparedStatement.setString(4, employee.getPassword());
			preparedStatement.setString(5, employee.getContact());
			preparedStatement.setInt(6, employee.getId());
			
			System.out.println(preparedStatement);
//			update true OR false
			rowUpdated = preparedStatement.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("Updated record ="+rowUpdated);
		return rowUpdated;
	}
	
//	select user by ID method
	public Employee selectEmployeeById(int id) {
		Employee employee = null;
		try(Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EMPLOYEE_BY_ID_SQL);){
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
//			execute the query
			ResultSet rs = preparedStatement.executeQuery();
			
//			process the ResultSet object
			while(rs.next()) {
				int employeeId = rs.getInt("id");
				String username = rs.getString("username");
				String firstname = rs.getString("firstname");
				String surname =rs.getString("surname");
				String password =rs.getString("password");
				String contact = rs.getString("contact");
				
//				setting the employee
				employee = new Employee(employeeId, firstname, surname,username, password, contact);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return employee;
	}

//	select all employees method
	public List<Employee>  selectAllEmployees() {
		List<Employee> employees = new ArrayList<>();
		try(Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_EMPLOYEES_SQL);){
			
			System.out.println(preparedStatement);
//			execute the query
			ResultSet rs = preparedStatement.executeQuery();
			
//			process the ResultSet object
			while(rs.next()) {
				int employeeId = rs.getInt("id");
				String username = rs.getString("username");
				String firstname = rs.getString("firstname");
				String surname =rs.getString("surname");
				String password =rs.getString("password");
				String contact = rs.getString("contact");
				
//				setting the employees
				employees.add(new Employee(employeeId, firstname, surname, username, password, contact));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return employees;
	}
	
//	delete employee method
	public boolean deleteEmployee(int id) throws SQLException {
		boolean rowDeleted = false;
		try(Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(DELETE_EMPLOYEE_SQL);){
			preparedStatement.setInt(1, id);
//			executing the query
			rowDeleted = preparedStatement.executeUpdate() > 0;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return rowDeleted;
	}
}
