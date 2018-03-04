package org.fangsoft.testcenter.dao.props;

import org.fangsoft.testcenter.dao.TestResultDao;
import org.fangsoft.testcenter.dao.TestResultDaoTest;

public class TestResultPropsDaoTest extends TestResultDaoTest {
	public void test() {
		TestResultDao dao = TestResultPropsDaoImpl.getInstance();
		this.testAll(dao);
	}
}
