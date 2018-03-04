package org.fangsoft.testcenter.dao.db;

import javax.sql.DataSource;

import org.fangsoft.testcenter.dao.CustomerDao;
import org.fangsoft.testcenter.dao.DaoFactory;
import org.fangsoft.testcenter.dao.QuestionDao;
import org.fangsoft.testcenter.dao.QuestionResultDao;
import org.fangsoft.testcenter.dao.TestDao;
import org.fangsoft.testcenter.dao.TestResultDao;

public class DBDaoFactory implements DaoFactory {
	private DataSource dataSource;
	private CustomerDao customerDao;
	private TestDao testDao;
	private QuestionDao questionDao;
	private TestResultDao testResultDao;
	private QuestionResultDao questionResultDao;

	public DBDaoFactory(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public CustomerDao getCustomerDao() {
		// TODO Auto-generated method stub
		if (this.customerDao == null) {
			this.customerDao = new CustomerDBDaoImpl(this.dataSource);
		}
		return this.customerDao;
	}

	@Override
	public QuestionDao getQuestionDao() {
		if (this.questionDao == null) {
			this.questionDao = new QuestionDBDaoImpl(this.dataSource);
		}
		return this.questionDao;
	}

	@Override
	public TestDao getTestDao() {
		if (this.testDao == null) {
			this.testDao = new TestDBDaoImpl(this.dataSource);
		}
		return this.testDao;
	}

	@Override
	public TestResultDao getTestResultDao() {
		if (this.testResultDao == null) {
			this.testResultDao = new TestResultDBDaoImpl(this.dataSource);
		}
		return this.testResultDao;
	}

	public QuestionResultDao getQuestionResultDao() {
		if (this.questionResultDao == null) {
			this.questionResultDao = new QuestionResultDBDaoImpl(
					this.dataSource);
		}
		return this.questionResultDao;
	}
}
