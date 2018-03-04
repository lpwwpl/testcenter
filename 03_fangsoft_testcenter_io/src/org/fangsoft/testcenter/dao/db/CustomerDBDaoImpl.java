package org.fangsoft.testcenter.dao.db;

import javax.sql.DataSource;

import org.fangsoft.db.SQLAction;
import org.fangsoft.testcenter.dao.CustomerDao;
import org.fangsoft.testcenter.model.Customer;

public class CustomerDBDaoImpl extends SQLAction implements CustomerDao{

	public CustomerDBDaoImpl(DataSource ds) {
		super(ds);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Customer findByUserId(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer login(String userId, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Customer customer) {
		// TODO Auto-generated method stub
		
	}

	
}
