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

public class ServerTestCenterController extends TestCenterController {

	private DaoFactory daoFactory;

	public ServerTestCenterController() {

	}

	public boolean isTestTimeout() {
		return (System.currentTimeMillis() - this.getTestDeadTime() > 0);
	}

	public void handleTimeOut() {

	}

	public Customer login(String userId, String password) {
		return this.getDaoFactory().getCustomerDao().login(userId, password);
	}

	public List<String> findAllTestNames() {
		// TODO Auto-generated method stub
		return this.getDaoFactory().getTestDao().findAllTestNames();
	}

	@Override
	public Test selectTest(String testName) {
		// TODO Auto-generated method stub
		return this.getDaoFactory().getTestDao().findTestByName(testName);
	}

	private Test loanQuestion(Test test) {
		this.getDaoFactory().getQuestionDao().loadQuestion(test);
		return test;
	}

	@Override
	public TestResult startTest(Test test, Customer customer) {
		// TODO Auto-generated method stub
		Test loadedTest = this.loanQuestion(test);
		TestResult testResult = new TestResult();
		testResult.setCustomer(customer);
		testResult.setTest(loadedTest);
		testResult.setCustomerId(customer.getId());
		testResult.setTestId(loadedTest.getId());
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

	@Override
	public void commitTest(TestResult testResult) {
		// TODO Auto-generated method stub
		testResult.setEndTime(new Date());
		testResult.commitTest();
		this.getDaoFactory().getTestResultDao().save(testResult);
	}

	public DaoFactory getDaoFactory() {
		return daoFactory;
	}

	public void setDaoFactory(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
}
