package org.fangsoft.testcenter.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.fangsoft.db.IRS2Object;
import org.fangsoft.db.SQLAction;
import org.fangsoft.db.SQLUtil;
import org.fangsoft.db.Sequence;
import org.fangsoft.testcenter.dao.QuestionDao;
import org.fangsoft.testcenter.dao.util.ResultSet2Object;
import org.fangsoft.testcenter.model.ChoiceItem;
import org.fangsoft.testcenter.model.Question;
import org.fangsoft.testcenter.model.Test;
import org.fangsoft.util.DataConverter;
import org.fangsoft.util.DataValidator;
import org.fangsoft.util.NumberUtil;

public class QuestionDBDaoImpl extends SQLAction implements QuestionDao {

	public static final String sql_save = "insert into question(qn_name,qn_score,qn_answer,qn_test_id,qn_id) values(?,?,?,?,?)";
	public static final String sql_choiceItem_save = "insert into choiceItem(ch_name,ch_correct,ch_question_id,ch_id) values(?,?,?,?)";
	public static final String sql_addChoiceItemToQuestion = "select * from CHOICEITEM where ch_question_id=?";
	public static final String sql_loadQuestion = "select * from Question where qn_test_id = ?";
	public static final String sql_loadQuestion_count = "select count(*) from question where qn_test_id=?";

	public QuestionDBDaoImpl(DataSource ds) {
		super(ds);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addQuestionToTest(Test test, Question q) {
		// TODO Auto-generated method stub
		Connection conn = null;
		try {
			conn = this.getDataSource().getConnection();
			conn.setAutoCommit(true);
			int qid = Sequence.getSeqValue(conn, Sequence.SEQ_QUESTION);
			Object[] p = this.question2SQLParam(q, qid, test.getId());
			this.update(conn, sql_save, p);
			this.saveChoiceItem(conn,q,qid);
			conn.commit();
			q.setId(qid);
		} catch (SQLException ex) {
			SQLUtil.rollback(conn);
			ex.printStackTrace();
		} finally {
			SQLUtil.close(conn);
		}
	}

	@Override
	public boolean loadQuestion(Test test) {
		// TODO Auto-generated method stub
		int tid = test.getId();
		if (tid == 0) {
			return false;
		}
		Connection conn = null;
		ResultSet qrs = null;
		int count = 0;
		int[] indexs = null;
		int numQuestion = test.getNumQuestion();
		try {
			conn = this.getDataSource().getConnection();
			conn.setAutoCommit(true);
			ResultSet rs = this.quesry2ResultSet(conn, sql_loadQuestion_count,
					tid);
			if (rs.next())
				count = rs.getInt(1);
			rs.close();
			if (count < numQuestion) {
				return false;
			}
			indexs = NumberUtil.uniRandomNumbers(0, count, numQuestion);
			PreparedStatement stmt = conn.prepareStatement(
					sql_loadQuestion, ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			stmt.setInt(1,tid);
			qrs = stmt.executeQuery();
			if(!qrs.next())return false;
			for(int i:indexs) {
				boolean ok = qrs.absolute(i+1);
				if(ok) {
					Question q = rs2Question(qrs);
					this.addChoiceItemToQuestion(q, conn);
					test.addQuestion(q);
				}
			}
			conn.commit();
			return true;
		} catch (SQLException ex) {
			SQLUtil.rollback(conn);
			ex.printStackTrace();
		} finally {
			SQLUtil.close(qrs);
			SQLUtil.close(conn);
		}
		return false;
	}

	protected Object[] question2SQLParam(Question q, int qid, int testId) {
		Object[] p = new Object[5];
		p[0] = DataValidator.validate(q.getName());
		p[1] = q.getScore();
		p[2] = q.getAnswer();
		p[3] = testId;
		p[4] = qid;
		return p;
	}

	protected Object[] choiceItem2SQLPara(ChoiceItem c, int cid, int qid) {
		Object[] p = new Object[4];
		p[0] = DataValidator.validate(c.getName());
		p[1] = DataConverter.boolean2Int(c.isCorrect());
		p[2] = qid;
		p[3] = cid;
		return p;
	}

	private void saveChoiceItem(Connection conn, Question q, int qid)
			throws SQLException {
		List<Integer> cids = new ArrayList<Integer>();
		List<ChoiceItem> items = q.getChoiceItems();
		if (items != null && items.size() > 0) {
			List<Object[]> ps = new ArrayList<Object[]>();
			for (ChoiceItem c : items) {
				int cid = Sequence.getSeqValue(conn, Sequence.SEQ_CHOICEITEM);
				cids.add(cid);
				ps.add(this.choiceItem2SQLPara(c, cid, qid));
			}
			this.batch(conn, sql_choiceItem_save, ps);
			for (int i = 0; i < items.size(); i++) {
				items.get(i).setId(cids.get(i));
			}
		}
	}

	public static Question rs2Question(ResultSet rs) throws SQLException {
		return ResultSet2Object.rs2Question(rs);
	}

	private void addChoiceItemToQuestion(Question q, Connection conn)
			throws SQLException {
		List<ChoiceItem> items = this.query2List(getRS2Item(),
				sql_addChoiceItemToQuestion, q.getId());
		q.setChoiceItems(items);
	}

	private static final ResultSet2ChoiceItem rs2Item = new ResultSet2ChoiceItem();

	public static final ResultSet2ChoiceItem getRS2Item() {
		return rs2Item;
	}

	private static class ResultSet2ChoiceItem implements IRS2Object<ChoiceItem> {

		@Override
		public ChoiceItem process(ResultSet rs) throws SQLException {
			// TODO Auto-generated method stub
			return ResultSet2Object.rs2ChoiceItem(rs);
		}
	}
}
