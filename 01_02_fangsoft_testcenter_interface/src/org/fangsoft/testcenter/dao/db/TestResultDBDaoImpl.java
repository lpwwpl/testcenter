package org.fangsoft.testcenter.dao.db;

import java.util.List;

import javax.sql.DataSource;

import org.fangsoft.db.SQLAction;
import org.fangsoft.testcenter.dao.TestResultDao;
import org.fangsoft.testcenter.model.TestResult;

public class TestResultDBDaoImpl extends SQLAction implements TestResultDao{

	public TestResultDBDaoImpl(DataSource ds) {
		super(ds);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<TestResult> findTestResultByCustomer(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(TestResult testResult) {
		// TODO Auto-generated method stub
		
	}

}
