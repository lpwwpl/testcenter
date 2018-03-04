package org.fangsoft.testcenter.controller;

import java.util.List;

import org.fangsoft.testcenter.model.Customer;
import org.fangsoft.testcenter.model.Test;
import org.fangsoft.testcenter.model.TestResult;

public class TestCenterController implements ITestCenterController {

	private Customer customer;
	private TestResult testResult;
	private long testDeadTime;
	private int index;

	public TestCenterController() {
	}


	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public TestResult getTestResult() {
		return testResult;
	}

	public void setTestResult(TestResult testResult) {
		this.testResult = testResult;
	}

	public long getTestDeadTime() {
		return testDeadTime;
	}

	public void setTestDeadTime(long testDeadTime) {
		this.testDeadTime = testDeadTime;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public boolean isTestTimeout() {
		return (System.currentTimeMillis() - this.getTestDeadTime() > 0);
	}

	public void handleTimeOut() {
		
	}


	@Override
	public void commitTest(TestResult testResult) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public List<String> findAllTestNames() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Customer login(String userId, String password) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Test selectTest(String testName) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public TestResult startTest(Test test, Customer customer) {
		// TODO Auto-generated method stub
		return null;
	}

}
