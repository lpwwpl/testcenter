package org.fangsoft.testcenter.dao.array;

import org.fangsoft.testcenter.dao.CustomerDao;
import org.fangsoft.testcenter.data.CustomerData;
import org.fangsoft.testcenter.model.Customer;

public class CustomerArrayDao implements CustomerDao{

	@Override
	public Customer findByUserId(String userId) {
		// TODO Auto-generated method stub
		Customer ret = new Customer();
		for(String[] customer:CustomerData.CUSTOMER_DATA) {
			if(customer[0].equals(userId)) {
				ret.setUserId(customer[0]);
				ret.setPassword(customer[1]);
				ret.setEmail(customer[2]);
				return ret;
			}
		}
		return null;
	}

	@Override
	public Customer login(String userId, String password) {
		// TODO Auto-generated method stub
		Customer ret = new Customer();
		for(String[] customer:CustomerData.CUSTOMER_DATA) {
			if(customer[0].equals(userId)&&customer[1].equals(password)) {
				ret.setUserId(customer[0]);
				ret.setPassword(customer[1]);
				ret.setEmail(customer[2]);
				return ret;
			}
		}
		return null;
	}

	@Override
	public void save(Customer customer) {
		// TODO Auto-generated method stub
		for(String[] theCustomer:CustomerData.CUSTOMER_DATA) {
			if(theCustomer[0].equals(customer.getUserId())) {
				break;
			} else {
				int i = CustomerData.CUSTOMER_DATA.length;
				CustomerData.CUSTOMER_DATA[i+1]=theCustomer;
			}
		}
	}

}
