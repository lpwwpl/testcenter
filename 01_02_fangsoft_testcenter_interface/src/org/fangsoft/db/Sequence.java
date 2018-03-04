package org.fangsoft.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Sequence {

	public static final String SEQ_TEST = "SEQ_TEST";
	public static final String SEQ_QUESTION = "SEQ_QUESTION";
	public static final String SEQ_CHOICEITEM = "SEQ_CHOICEITEM";
	public static final String SEQ_TESTRESULT = "SEQ_TESTRESULT";
	public static final String SEQ_QUESTIONRESULT = "SEQ_QUESTIONRESULT";
	
	public static final int getSeqValue(Connection conn,String seqName) throws SQLException {
		ResultSet rs = null;
		String sql = "select " + seqName + ".nextval from dual";
		try {
			rs = conn.prepareStatement(sql).executeQuery();
			if(rs.next())return rs.getInt(1);
		} catch (SQLException ex) {
			throw ex;
		} finally {
			SQLUtil.close(rs);
		}
		return -1;
	}
}
