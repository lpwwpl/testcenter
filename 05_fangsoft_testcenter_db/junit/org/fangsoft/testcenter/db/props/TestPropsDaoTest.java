package org.fangsoft.testcenter.db.props;

import org.fangsoft.testcenter.dao.TestDao;
import org.fangsoft.testcenter.dao.TestDaoTest;
import org.fangsoft.testcenter.dao.props.TestPropsDaoImpl;

public class TestPropsDaoTest extends TestDaoTest{

	public void test() {
		TestDao dao = TestPropsDaoImpl.getInstance();
		this.testAll(dao);
	}
}
