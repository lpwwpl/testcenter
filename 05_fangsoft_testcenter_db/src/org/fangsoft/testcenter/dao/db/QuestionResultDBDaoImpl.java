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
import org.fangsoft.testcenter.dao.QuestionResultDao;
import org.fangsoft.testcenter.dao.util.ResultSet2Object;
import org.fangsoft.testcenter.model.QuestionResult;
import org.fangsoft.util.DataConverter;

public class QuestionResultDBDaoImpl extends SQLAction implements QuestionResultDao{

	public static final String sql_save = "INSERT INTO QUESTIONRESULT(qr_testResult_id,qr_question_id,qr_score,qr_answer,qr_result,qr_id) values (?,?,?,?,?,?)";
	private static final ResultSet2QuestionResult rs2QuestionResult = new ResultSet2QuestionResult();
	public static final String sql_findQuestionResultByQuestion = "select * from questionResult where qr_question_id=?";
	
	public QuestionResultDBDaoImpl(DataSource ds) {
		super(ds);
	}

	@Override
	public List<QuestionResult> findQuestionResultByCustomer(int questionId) {
		// TODO Auto-generated method stub
		try {
			return this.query2List(getRS2QuestionResult(), sql_findQuestionResultByQuestion,
					questionId);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public void save(QuestionResult questionResult) {
		// TODO Auto-generated method stub
		Connection conn = null;
		try {
			conn = this.getDataSource().getConnection();
			conn.setAutoCommit(false);
			int id = Sequence.getSeqValue(conn, Sequence.SEQ_QUESTIONRESULT);
			update(conn, sql_save, questionResult2SQLParameter(questionResult, id));
			// #####################
			questionResult.setId(id);
			conn.commit();
		} catch (SQLException ex) {
			SQLUtil.rollback(conn);
			ex.printStackTrace();
		} finally {
			SQLUtil.close(conn);
		}
	}
	
private Object[] questionResult2SQLParameter(QuestionResult questionResult, int qrId) {
		
//		qr_testResult_id,qr_question_id,qr_score,qr_answer,qr_result,qr_id
		Object[] p = new Object[6];
		p[0] = questionResult.getTestResultId();
		p[1] = questionResult.getQuestionId();
		p[2] = questionResult.getScore();
		p[3] = questionResult.getAnswer();
		p[4] = DataConverter.boolean2Int(questionResult.isResult());
		p[5] = qrId;
//		for(Object obj:p) {
//			System.out.print(obj+"\t");
//		}
		return p;
	}
	
	public static final ResultSet2QuestionResult getRS2QuestionResult() {
		return rs2QuestionResult;
	}
	
	private static class ResultSet2QuestionResult implements IRS2Object<QuestionResult> {
		@Override
		public QuestionResult process(ResultSet rs) throws SQLException {
			return ResultSet2Object.rs2QuestionResult(rs);
		}
	}
}
