package net.frank.registration.model;

public class Employee {
	private int id;
	private String firstName;
	private String surname;
	private String userName;
	private String password;
	private String contact;
	
//	constructor
	public Employee(int id, String firstName, String surname, String userName, String password, String contact) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.surname = surname;
		this.userName = userName;
		this.password = password;
		this.contact = contact;
	}


//	constructor without ID
	public Employee(String firstName, String surname, String userName, String password, String contact) {
		super();
		this.firstName = firstName;
		this.surname = surname;
		this.userName = userName;
		this.password = password;
		this.contact = contact;
	}


//	Getters===========================================================================
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getSurnameName() {
		return surname;
	}


	public void setLastName(String lastName) {
		this.surname = lastName;
	}


	public String getUserName() {
		return userName;
	}

//	setters==============================
	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getContact() {
		return contact;
	}


	public void setContact(String contact) {
		this.contact = contact;
	}

	
}
