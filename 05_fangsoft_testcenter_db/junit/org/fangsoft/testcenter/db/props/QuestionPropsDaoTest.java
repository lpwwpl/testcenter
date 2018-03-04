package org.fangsoft.testcenter.db.props;

import org.fangsoft.testcenter.dao.QuestionDao;
import org.fangsoft.testcenter.dao.QuestionDaoTest;
import org.fangsoft.testcenter.dao.props.QuestionPropsDaoImpl;

public class QuestionPropsDaoTest extends QuestionDaoTest{
	public void test() {
		
		QuestionDao dao = QuestionPropsDaoImpl.getInstance();
		this.testAll(dao);
	}
}
