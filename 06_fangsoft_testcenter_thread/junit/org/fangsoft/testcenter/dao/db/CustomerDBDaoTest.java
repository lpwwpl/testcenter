package org.fangsoft.testcenter.dao.db;

import java.sql.SQLException;

import org.fangsoft.db.SQLAction;
import org.fangsoft.testcenter.config.Configuration;
import org.fangsoft.testcenter.dao.CustomerDao;
import org.fangsoft.testcenter.dao.CustomerDaoTest;

public class CustomerDBDaoTest extends CustomerDaoTest{
	public void test() {
//		this.deleteAll();
		CustomerDao tdao = new CustomerDBDaoImpl(Configuration.getDataSource());
		this.testAll(tdao);
//		this.deleteAll();
	}
	private void deleteAll() {
		SQLAction sqlAction = new SQLAction(Configuration.getDataSource());
		try {
			sqlAction.update("delete from customer");
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
}
