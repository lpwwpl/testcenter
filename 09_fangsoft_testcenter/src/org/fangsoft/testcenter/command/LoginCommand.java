package org.fangsoft.testcenter.command;

import org.fangsoft.testcenter.model.Customer;

public class LoginCommand extends Command {
	private static final long serialVersionUID = 1631604324564599575L;
	private String userId;
	private String password;
	private Customer customer;
	public LoginCommand() {
		
	}
	
	public LoginCommand(String userId,String password) {
		this.userId = userId;
		this.password = password;
	}
	
	public void execute() {
		if(this.getController()==null) {
			return;
		}
		this.customer = this.getController().login(this.getUserId(), this.getPassword());
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}
