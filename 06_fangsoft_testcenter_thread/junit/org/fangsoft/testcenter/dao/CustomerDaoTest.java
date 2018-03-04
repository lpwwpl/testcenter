package org.fangsoft.testcenter.dao;

import junit.framework.TestCase;

import org.fangsoft.testcenter.model.Customer;
import org.fangsoft.testcenter.model.TestResult;

public class CustomerDaoTest extends TestCase{
	protected void testAll(CustomerDao tdao) {

		String userId = "fangsoft.org@gmail.com";
		Customer customer = tdao.findByUserId((userId));
		this.assertEquals(customer.getPassword(), "fangsoft");
	}

	private String toString(TestResult tr) {
		return tr.toString();
	}
}
