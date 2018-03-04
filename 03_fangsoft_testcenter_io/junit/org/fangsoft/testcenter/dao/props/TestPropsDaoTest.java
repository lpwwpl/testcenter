package org.fangsoft.testcenter.dao.props;

import org.fangsoft.testcenter.dao.TestDao;
import org.fangsoft.testcenter.dao.TestDaoTest;

public class TestPropsDaoTest extends TestDaoTest{

	public void test() {
		TestDao dao = TestPropsDaoImpl.getInstance();
		this.testAll(dao);
	}
}
