package org.fangsoft.testcenter.db.props;

import org.fangsoft.testcenter.dao.TestResultDao;
import org.fangsoft.testcenter.dao.TestResultDaoTest;
import org.fangsoft.testcenter.dao.props.TestResultPropsDaoImpl;

public class TestResultPropsDaoTest extends TestResultDaoTest {
	public void test() {
		TestResultDao dao = TestResultPropsDaoImpl.getInstance();
		this.testAll(dao);
	}
}
