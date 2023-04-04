package net.frank.registration.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.frank.registration.dao.EmployeeDao;
import net.frank.registration.model.Employee;

/**
 * Servlet implementation class EmployServlet
 */
@WebServlet("/")
public class EmployServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EmployeeDao employeeDao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployServlet() {
    	this.employeeDao = new EmployeeDao();
//        super();
        // TODO Auto-generated constructor stub
    }

    /**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		all the requests will be defined here
		String path = request.getServletPath();
		
		switch(path){
		case "/new":
			showNewForm(request, response);
			break;
		case "/insert":
			try {
				insertEmployee(request, response);
			} catch (SQLException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/delete":
			try {
				deleteEmployee(request, response);
			} catch (SQLException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;	
		case "/edit":
			try {
				showEditForm(request, response);
			} catch (SQLException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;	
		case "/update":
			try {
				updateEmployee(request, response);
			} catch (SQLException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
//			displays the list
			try {
				listEmployees(request, response);
			} catch (SQLException | IOException | ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		
		
	}

//	Forwards request to the employee-form.jsp
	private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher =request.getRequestDispatcher("views/employee-form.jsp");
		dispatcher.forward(request, response);
	}
	
//	inserts a new employee
	private void insertEmployee(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException{
		try {
			String username = request.getParameter("username");
			String firstname = request.getParameter("firstname");
			String surname = request.getParameter("surname");
			String password = request.getParameter("password");
			String contact = request.getParameter("contact");
			
			Employee employee = new Employee(firstname, surname,username,password,contact);
			
//			inserting employee
			employeeDao.insertEmployee(employee);
		}catch(SQLException s) {
			s.printStackTrace();
		}
//		redirecting to the list screen
		response.sendRedirect("list");
	}
	
//	deleted a user
	private void deleteEmployee(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException{
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			
			employeeDao.deleteEmployee(id);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
//		redirect to list
		response.sendRedirect("list");
	}

//	edits an employee
	private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		
			int id = Integer.parseInt(request.getParameter("id"));
//			getting the employee
			Employee seletcedEmployee = employeeDao.selectEmployeeById(id);
//			redirecting to the form
			RequestDispatcher dispatcher = request.getRequestDispatcher("views/employee-form.jsp");
			
//			loading the attributes
			request.setAttribute("employee", seletcedEmployee);
			
			try {
				dispatcher.forward(request, response);
			} catch (ServletException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
//	update a employee
	private void updateEmployee(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException{
		try {
			int id =Integer.parseInt(request.getParameter("id"));
			String username = request.getParameter("username");
			String firstname = request.getParameter("firstname");
			String surname = request.getParameter("surname");
			String password = request.getParameter("password");
			String contact = request.getParameter("contact");
			
			Employee employee = new Employee(id, firstname, surname, username, password, contact);
			
		employeeDao.updateEmployee(employee);
		System.out.println("#updateEmployee Servlet");
//		System.out.println("#updateEmployee Servlet username "+username);
//		System.out.println("#updateEmployee Servlet firtsname "+firstname);
//		System.out.println("#updateEmployee Servlet surname "+ surname);
//		System.out.println("#updateEmployee Servlet password "+ password);
//		System.out.println("#updateEmployee Servlet contact "+ contact);
//		System.out.println("#updateEmployee Servlet id " + id);
		
		response.sendRedirect("list");
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
//	list all employees
	private void listEmployees(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException{
		try {
			List<Employee> employees = employeeDao.selectAllEmployees();
			request.setAttribute("employeeList", employees);
			RequestDispatcher dispatcher = request.getRequestDispatcher("views/employee-list.jsp");
			dispatcher.forward(request, response);
		}catch(ServletException e) {
			e.printStackTrace();
		}
	}
}
