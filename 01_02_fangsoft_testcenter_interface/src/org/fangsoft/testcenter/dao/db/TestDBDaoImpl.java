package org.fangsoft.testcenter.dao.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.fangsoft.db.IRS2Object;
import org.fangsoft.db.SQLAction;
import org.fangsoft.db.SQLUtil;
import org.fangsoft.db.Sequence;
import org.fangsoft.testcenter.dao.TestDao;
import org.fangsoft.testcenter.model.Test;
import org.fangsoft.util.DataValidator;

public class TestDBDaoImpl extends SQLAction implements TestDao {

	private static final ResultSet2Test rs2Test = new ResultSet2Test();
	public static final String sql_save = "intert into TEST(TT_NAME,TT_NUMQUESTION,TT_TIMELIMITMIN,TT_DESCRIPTION,TT_SCORE,TT_ID) values(?,?,?,?,?,?)";
	public static final String sql_findTestByName = "select * from TEST where TT_NAME=?";
	
	public TestDBDaoImpl(DataSource ds) {
		super(ds);
	}

	@Override
	public List<Test> findAllTest() {
//		try {
//			return this.query2List(conn, rsProcessor, sql, params)
//		} catch (SQLException ex) {
//			ex.printStackTrace();
//		}
//		return new ArrayList<Test>(0);
		return null;
	}

	@Override
	public Test findTestByName(String testName) {
		// TODO Auto-generated method stub
		return null;
	}

	private Object[] test2SQLParameter(Test test, int tid) {
		Object[] p = new Object[6];
		p[0] = DataValidator.validate(test.getName());
		p[1] = test.getNumQuestion();
		p[2] = test.getTimeLimitMin();
		p[3] = DataValidator.validate(test.getDescription());
		p[4] = test.getScore();
		p[5] = tid;
		return p;
	}

	public static final ResultSet2Test getRS2Test() {
		return rs2Test;
	}

	public void save(Test test) {
		Connection conn = null;
		try {
			conn = this.getDataSource().getConnection();
			conn.setAutoCommit(false);
			int id = Sequence.getSeqValue(conn, Sequence.SEQ_TEST);
			update(conn,sql_save,this.test2SQLParameter(test, id));
//			#####################
//			test.setId(id);
			conn.commit();
		} catch (SQLException ex) {
			SQLUtil.rollback(conn);
			ex.printStackTrace();
		} finally {
			SQLUtil.close(conn);
		}
	}

	private static class ResultSet2Test implements IRS2Object<Test> {
		@Override
		public Test process(ResultSet rs) throws SQLException {
			return ResultSet2Object.rs2Test(rs);
		}
	}

	@Override
	public List<String> findAllTestNames() {
		// TODO Auto-generated method stub
		return null;
	}
}
