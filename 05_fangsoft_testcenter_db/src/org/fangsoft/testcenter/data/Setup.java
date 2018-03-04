package org.fangsoft.testcenter.data;

import org.fangsoft.testcenter.config.Configuration;
import org.fangsoft.testcenter.dao.CustomerDao;
import org.fangsoft.testcenter.dao.QuestionDao;
import org.fangsoft.testcenter.dao.TestDao;
import org.fangsoft.testcenter.model.Customer;
import org.fangsoft.testcenter.model.Question;
import org.fangsoft.testcenter.model.Test;

public class Setup {
	public static void exportTest2File() {
		for(String[][] data:TestData.allTest) {
			Test test = TestData.newTest(data);
			TestData.loadQuestion(test, data);
			Configuration.getPropsDaoFactory().getTestDao().save(test);
			//adding questions to test################
		}
	}
	public static void exportCustomer2File() {
		for(Customer c:CustomerData.getCustomers()) {
			Configuration.getPropsDaoFactory().getCustomerDao().save(c);
		}
	}
	
	public static void exportTest2Database() throws Exception {
		TestDao tdao = Configuration.getDBDaoFactory().getTestDao();
		QuestionDao qdao = Configuration.getDBDaoFactory().getQuestionDao();
		for(String[][] data:TestData.allTest) {
			Test test = TestData.newTest(data);
			TestData.loadQuestion(test, data);
			tdao.save(test);
			for(Question q:test.getQuestions()) {
				qdao.addQuestionToTest(test, q);
			}
		}
	}
	public static void exportCustomer2Database() throws Exception {
		CustomerDao cdao = Configuration.getDBDaoFactory().getCustomerDao();
		for(Customer c:CustomerData.getCustomers()) {
			cdao.save(c);
		}
	}
	public static void main(String[] args) throws Exception {
//		exportTest2File();
//		exportCustomer2File();
		exportCustomer2Database();
		exportTest2Database();
	}
}
