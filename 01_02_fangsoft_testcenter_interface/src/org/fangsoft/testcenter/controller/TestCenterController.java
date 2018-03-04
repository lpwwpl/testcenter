package org.fangsoft.testcenter.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.fangsoft.testcenter.dao.DaoFactory;
import org.fangsoft.testcenter.model.Customer;
import org.fangsoft.testcenter.model.Question;
import org.fangsoft.testcenter.model.QuestionResult;
import org.fangsoft.testcenter.model.Test;
import org.fangsoft.testcenter.model.TestResult;

public class TestCenterController implements ITestCenterController {

	private DaoFactory daoFactory;
	private Customer customer;
	private TestResult testResult;
	private long testDeadTime;
	private int index;

	public TestCenterController() {
	}

	private Test loanQuestion(Test test) {
		this.getDaoFactory().getQuestionDao().loadQuestion(test);
		return test;
	}

	@Override
	public void commitTest(TestResult testResult) {
		// TODO Auto-generated method stub
		testResult.setEndTime(new Date());
		testResult.commitTest();
		this.getDaoFactory().getTestResultDao().save(testResult);
	}

	@Override
	public List<String> findAllTestNames() {
		// TODO Auto-generated method stub
		return this.getDaoFactory().getTestDao().findAllTestNames();
	}

	@Override
	public Customer login(String userId, String password) {
		// TODO Auto-generated method stub
		return this.getDaoFactory().getCustomerDao().login(userId, password);
	}

	@Override
	public Test selectTest(String testName) {
		// TODO Auto-generated method stub
		return this.getDaoFactory().getTestDao().findTestByName(testName);
	}

	@Override
	public TestResult startTest(Test test, Customer customer) {
		// TODO Auto-generated method stub
		Test loadedTest = this.loanQuestion(test);
		TestResult testResult = new TestResult();
		testResult.setCustomer(customer);
		testResult.setTest(loadedTest);
		testResult.setStartTime(new Date());
		long testDeadTime = testResult.getStartTime().getTime()
				+ loadedTest.getTimeLimitMin() * 60 * 1000;
		testResult.setTestDeadTime(testDeadTime);
		testResult.setQuestionResults(new ArrayList<QuestionResult>(loadedTest
				.getQuestions().size()));
		testResult.setStatus(TestResult.Status.TESTING);
		for (Question q : loadedTest.getQuestions()) {
			QuestionResult qr = new QuestionResult();
			qr.setQuestion(q);
			testResult.getQuestionResults().add(qr);
		}
		return testResult;
	}

	public DaoFactory getDaoFactory() {
		return daoFactory;
	}

	public void setDaoFactory(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
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

}
