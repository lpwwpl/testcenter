package org.fangsoft.testcenter.model;

import java.io.Serializable;

public class Customer implements Serializable {
	
	private static final long serialVersionUID = -923117219028546737L;
	private int id;
	private String userId;
	private String password;
	private String email;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String toString() {
		return userId + " " + password + " " + email;
	}
}
