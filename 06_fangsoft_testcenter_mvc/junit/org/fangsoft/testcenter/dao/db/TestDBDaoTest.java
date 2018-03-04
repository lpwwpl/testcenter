package org.fangsoft.testcenter.dao.db;

import java.sql.SQLException;

import org.fangsoft.db.SQLAction;
import org.fangsoft.testcenter.config.Configuration;
import org.fangsoft.testcenter.dao.TestDao;
import org.fangsoft.testcenter.dao.TestDaoTest;

public class TestDBDaoTest extends TestDaoTest{
	public void test() {
		this.deleteAll();
		TestDao tdao = new TestDBDaoImpl(Configuration.getDataSource());
		this.testAll(tdao);
//		this.deleteAll();
	}
	private void deleteAll() {
		SQLAction sqlAction = new SQLAction(Configuration.getDataSource());
		try {
			sqlAction.update("delete from test");
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
}
