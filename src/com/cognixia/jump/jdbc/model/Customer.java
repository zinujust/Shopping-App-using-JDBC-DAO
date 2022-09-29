package com.cognixia.jump.jdbc.model;

import java.io.Serializable;

public class Customer implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long customer_id;
	private String fullname;
	private String email;
	private String password;
	
	public Customer() {
		this(-1, "N/A", "N/A" ,"N/A");
	}

	public Customer(long customer_id, String fullname, String email, String password) {
		super();
		this.customer_id = customer_id;
		this.fullname = fullname;
		this.email = email;
		this.password = password;
	}

	public long getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(long customer_id) {
		this.customer_id = customer_id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	@Override
	public String toString() {
		return "Customer [customer_id=" + customer_id + ", fullname=" + fullname + ", email=" + email + ", password="
				+ password + "]";
	}

	
}
