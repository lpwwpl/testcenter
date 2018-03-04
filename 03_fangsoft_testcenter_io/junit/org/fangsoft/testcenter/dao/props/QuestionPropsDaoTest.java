package org.fangsoft.testcenter.dao.props;

import org.fangsoft.testcenter.dao.QuestionDao;
import org.fangsoft.testcenter.dao.QuestionDaoTest;

public class QuestionPropsDaoTest extends QuestionDaoTest{
	public void test() {
		
		QuestionDao dao = QuestionPropsDaoImpl.getInstance();
		this.testAll(dao);
	}
}
