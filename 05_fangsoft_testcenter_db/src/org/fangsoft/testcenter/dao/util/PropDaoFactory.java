package org.fangsoft.testcenter.dao.util;

import org.fangsoft.testcenter.dao.CustomerDao;
import org.fangsoft.testcenter.dao.DaoFactory;
import org.fangsoft.testcenter.dao.QuestionDao;
import org.fangsoft.testcenter.dao.QuestionResultDao;
import org.fangsoft.testcenter.dao.TestDao;
import org.fangsoft.testcenter.dao.TestResultDao;
import org.fangsoft.testcenter.dao.props.CustomerPropsDaoImpl;
import org.fangsoft.testcenter.dao.props.QuestionPropsDaoImpl;
import org.fangsoft.testcenter.dao.props.QuestionResultPropsDaoImpl;
import org.fangsoft.testcenter.dao.props.TestPropsDaoImpl;
import org.fangsoft.testcenter.dao.props.TestResultPropsDaoImpl;

public class PropDaoFactory implements DaoFactory {

	private static final PropDaoFactory daoFactory = new PropDaoFactory();

	public static final DaoFactory getInstance() {
		return daoFactory;
	}

	public PropDaoFactory() {

	}

	@Override
	public CustomerDao getCustomerDao() {
		// TODO Auto-generated method stub
		return CustomerPropsDaoImpl.getInstance();
	}

	@Override
	public QuestionDao getQuestionDao() {
		// TODO Auto-generated method stub
		return QuestionPropsDaoImpl.getInstance();
	}

	@Override
	public TestDao getTestDao() {
		// TODO Auto-generated method stub
		return TestPropsDaoImpl.getInstance();
	}

	@Override
	public TestResultDao getTestResultDao() {
		// TODO Auto-generated method stub
		return TestResultPropsDaoImpl.getInstance();
	}

	@Override
	public QuestionResultDao getQuestionResultDao() {
		// TODO Auto-generated method stub
		return QuestionResultPropsDaoImpl.getInstance();
	}
}
