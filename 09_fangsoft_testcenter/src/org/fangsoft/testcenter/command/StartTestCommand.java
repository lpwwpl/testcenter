package org.fangsoft.testcenter.command;

import org.fangsoft.testcenter.model.Customer;
import org.fangsoft.testcenter.model.Test;
import org.fangsoft.testcenter.model.TestResult;

public class StartTestCommand extends Command {

	private static final long serialVersionUID = -8218548677077975441L;

	private Test test;
	private Customer customer;
	private TestResult tr;

	public StartTestCommand() {

	}

	public StartTestCommand(Test test,Customer customer) {
		this.test = test;
		this.customer = customer;
	}
	
	public void execute() {
		if(this.getController() == null) return;
		this.tr = this.getController().startTest(this.getTest(), this.getCustomer());
	}
	
	public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public TestResult getTr() {
		return tr;
	}

	public void setTr(TestResult tr) {
		this.tr = tr;
	}
	
}
