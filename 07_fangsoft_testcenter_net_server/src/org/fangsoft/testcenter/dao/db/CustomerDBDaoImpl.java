package org.fangsoft.testcenter.dao.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.fangsoft.db.IRS2Object;
import org.fangsoft.db.SQLAction;
import org.fangsoft.db.SQLUtil;
import org.fangsoft.db.Sequence;
import org.fangsoft.testcenter.dao.CustomerDao;
import org.fangsoft.testcenter.dao.util.ResultSet2Object;
import org.fangsoft.testcenter.model.Customer;
import org.fangsoft.util.DataValidator;

public class CustomerDBDaoImpl extends SQLAction implements CustomerDao{

	private static final ResultSet2Customer rs2Customer = new ResultSet2Customer();
	public static final String sql_findCustomerByName = "select * from customer where cm_userId=?";
	public static final String sql_findCustomerByIDAndPassword = "select * from customer where cm_userId=? and cm_password=?";
	public static final String sql_save = "INSERT INTO customer(cm_userId,cm_password,cm_email,cm_id) values (?,?,?,?)";
	
	public CustomerDBDaoImpl(DataSource ds) {
		super(ds);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Customer findByUserId(String userId) {
		// TODO Auto-generated method stub
		try {
			return this.query2Object(getRS2Customer(), sql_findCustomerByName,
					userId);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public Customer login(String userId, String password) {
		// TODO Auto-generated method stub
		
		try {
			return this.query2Object(getRS2Customer(), sql_findCustomerByIDAndPassword,
					userId,password);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public void save(Customer customer) {
		// TODO Auto-generated method stub
		Connection conn = null;
		try {
			conn = this.getDataSource().getConnection();
			conn.setAutoCommit(false);
			int id = Sequence.getSeqValue(conn, Sequence.SEQ_TEST);
			update(conn, sql_save, customer2SQLParameter(customer, id));
			// #####################
			customer.setId(id);
			conn.commit();
		} catch (SQLException ex) {
			SQLUtil.rollback(conn);
			ex.printStackTrace();
		} finally {
			SQLUtil.close(conn);
		}
	}

	private Object[] customer2SQLParameter(Customer customer, int cid) {
		Object[] p = new Object[4];
		p[0] = DataValidator.validate(customer.getUserId());
		p[1] = customer.getPassword();
		p[2] = customer.getEmail();
		p[3] = cid;
		return p;
	}
	
	public static final ResultSet2Customer getRS2Customer() {
		return rs2Customer;
	}
	
	private static class ResultSet2Customer implements IRS2Object<Customer> {
		@Override
		public Customer process(ResultSet rs) throws SQLException {
			return ResultSet2Object.rs2Customer(rs);
		}
	}
}
