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
import org.fangsoft.testcenter.dao.TestResultDao;
import org.fangsoft.testcenter.dao.util.Field2Property;
import org.fangsoft.testcenter.dao.util.ResultSet2Object;
import org.fangsoft.testcenter.model.TestResult;
import org.fangsoft.util.DataConverter;

public class TestResultDBDaoImpl extends SQLAction implements TestResultDao{

	public static final String sql_save = "INSERT INTO TESTRESULT(tr_test_id,tr_customer_id,tr_score,tr_pass,tr_startTime,tr_endTime,tr_status,tr_id) values (?,?,?,?,?,?,?,?)";
	private static final ResultSet2TestResult rs2TestResult = new ResultSet2TestResult();
	public static final String sql_findTestResultByUserId = "select * from testResult where tr_customer_id=?";;
	public TestResultDBDaoImpl(DataSource ds) {
		super(ds);
		// TODO Auto-generated constructor stub
	}

	//##############################userid==customerÖ÷¼ü
	@Override
	public List<TestResult> findTestResultByCustomer(String userId) {
		// TODO Auto-generated method stub
		try {
			return this.query2List(getRS2TestResult(), sql_findTestResultByUserId,
					userId);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public void save(TestResult testResult) {
		Connection conn = null;
		try {
			conn = this.getDataSource().getConnection();
			conn.setAutoCommit(false);
			int id = Sequence.getSeqValue(conn, Sequence.SEQ_TESTRESULT);
			update(conn, sql_save, testResult2SQLParameter(testResult, id));
			// #####################
			testResult.setId(id);
			conn.commit();
		} catch (SQLException ex) {
			SQLUtil.rollback(conn);
			ex.printStackTrace();
		} finally {
			SQLUtil.close(conn);
		}
	}


	private Object[] testResult2SQLParameter(TestResult testResult, int trId) {
		
//		tr_test_id,tr_customer_id,tr_score,tr_pass,tr_startTime,tr_endTime,tr_idObject[] p = new Object[7];
		Object[] p = new Object[8];
		p[0] = testResult.getTestId();
		p[1] = testResult.getCustomerId();
		p[2] = testResult.getScore();
		p[3] = testResult.getPass();
		p[4] = DataConverter.date2SqlDate(testResult.getStartTime());
		p[5] = DataConverter.date2SqlDate(testResult.getEndTime());
		p[6] = Field2Property.testResultStatus2Int(testResult.getStatus());
		p[7] = trId;
//		for(Object obj:p) {
//			System.out.print(obj+"\t");
//		}
		return p;
	}
	
	public static final ResultSet2TestResult getRS2TestResult() {
		return rs2TestResult;
	}
	
	private static class ResultSet2TestResult implements IRS2Object<TestResult> {
		@Override
		public TestResult process(ResultSet rs) throws SQLException {
			return ResultSet2Object.rs2TestResult(rs);
		}
	}
	
}
