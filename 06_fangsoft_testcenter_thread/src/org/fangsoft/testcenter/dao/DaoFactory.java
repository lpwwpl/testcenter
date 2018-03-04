package org.fangsoft.testcenter.dao;

public interface DaoFactory {

	public CustomerDao getCustomerDao();
	public TestDao getTestDao();
	public QuestionDao getQuestionDao();
	public TestResultDao getTestResultDao();
	public QuestionResultDao getQuestionResultDao();
}
