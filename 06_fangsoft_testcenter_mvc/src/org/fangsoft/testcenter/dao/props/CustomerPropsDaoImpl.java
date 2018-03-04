package org.fangsoft.testcenter.dao.props;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import org.fangsoft.testcenter.dao.CustomerDao;
import org.fangsoft.testcenter.dao.util.DaoIOConfig;
import org.fangsoft.testcenter.dao.util.Property2Object;
import org.fangsoft.testcenter.model.Customer;

public class CustomerPropsDaoImpl implements CustomerDao {
	private static final CustomerDao cdao = new CustomerPropsDaoImpl();

	public static final CustomerDao getInstance() {
		return cdao;
	}

	public CustomerPropsDaoImpl() {

	}

	@Override
	public Customer findByUserId(String userId) {
		// TODO Auto-generated method stub
		String path = DaoIOConfig.getCustomerFilePath();
		File[] files = new File(path).listFiles();
		if (files == null || files.length == 0)
			return null;
		for (File f : files) {
			String fileName = f.getName();
			if (f.isFile() && fileName.startsWith(userId)) {
				Properties ps = new Properties();
				try {
					ps.load(new FileReader(f));
				} catch (FileNotFoundException ex) {
					ex.printStackTrace();
				} catch (IOException ex) {
					ex.printStackTrace();
				}

				return Property2Object.toCustomer(ps);
			}
		}
		return null;
	}

	@Override
	public Customer login(String userId, String password) {
		// TODO Auto-generated method stub
		String path = DaoIOConfig.getCustomerFilePath();
		File[] files = new File(path).listFiles();
		if (files == null || files.length == 0)
			return null;
		for (File f : files) {
			String fileName = f.getName();
			if (f.isFile() && fileName.startsWith(userId)) {
				Properties ps = new Properties();
				try {
					ps.load(new FileReader(f));
				} catch (FileNotFoundException ex) {
					ex.printStackTrace();
				} catch (IOException ex) {
					ex.printStackTrace();
				}

				Customer customer = Property2Object.toCustomer(ps);
				if (customer.getPassword().equals(password))
					return customer;
				return null;
			}
		}
		return null;
	}

	@Override
	public void save(Customer customer) {
		// TODO Auto-generated method stub
		Properties ps = Property2Object.toProperties(customer);
		String path = DaoIOConfig.getCustomerFilePath();
		String fileName = DaoIOConfig.getFileName(customer);
		
		new File(path).mkdirs();
		try {
			if(findByUserId(customer.getUserId()) != null) {
				return;
			}
			ps.store(new FileWriter(path+fileName), "");
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}
}
