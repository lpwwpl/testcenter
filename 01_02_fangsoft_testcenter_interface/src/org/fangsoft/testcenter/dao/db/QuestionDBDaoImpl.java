package org.fangsoft.testcenter.dao.db;

import javax.sql.DataSource;

import org.fangsoft.db.SQLAction;
import org.fangsoft.testcenter.dao.QuestionDao;
import org.fangsoft.testcenter.model.Question;
import org.fangsoft.testcenter.model.Test;

public class QuestionDBDaoImpl extends SQLAction implements QuestionDao{

	public QuestionDBDaoImpl(DataSource ds) {
		super(ds);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addQuestionToTest(Test test, Question q) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean loadQuestion(Test test) {
		// TODO Auto-generated method stub
		return false;
	}

}
