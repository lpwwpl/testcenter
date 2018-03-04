package org.fangsoft.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class SQLAction {

	private DataSource dataSource = null;
	private int batchSize = 100;

	public int getBatchSize() {
		return batchSize;
	}

	public SQLAction(DataSource ds) {
		this.setDataSource(ds);
	}

	protected Connection getConnection() throws SQLException {
		if (this.getDataSource() == null) {
			String errorMesg = "SQLAction cannot get Database Connection!";
			throw new SQLException(errorMesg);
		}
		return this.getDataSource().getConnection();
	}

	// ##################
	protected PreparedStatement prepare(Connection conn, String sql)
			throws SQLException {
		if (sql.trim().startsWith("(")) {
			return conn.prepareCall(sql);
		}
		return conn.prepareStatement(sql);
	}

	protected void fillSQLParameter(PreparedStatement stmt, Object... p)
			throws SQLException {
		if (p == null || p.length == 0)
			return;
		for (int i = 0; i < p.length; i++) {
			stmt.setObject(i + 1, p[i]);
		}
	}

	public int update(Connection conn, String sql, Object... params)
			throws SQLException {
		PreparedStatement stmt = null;
		int rows = 0;
		try {
			stmt = this.prepare(conn, sql);
			fillSQLParameter(stmt, params);
			rows = stmt.executeUpdate();
		} catch (SQLException ex) {
			throw ex;
		} finally {
			SQLUtil.close(stmt);
		}
		return rows;
	}

	public int update(String sql, Object... params) throws SQLException {
		Connection conn = this.getConnection();
		try {
			return update(conn, sql, params);
		} finally {
			SQLUtil.close(conn);
		}
	}

	public <T, P extends IRS2Object<T>> T query2Object(Connection conn,
			P rsProcessor, String sql, Object... params) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		T result = null;
		try {
			stmt = this.prepare(conn, sql);
			fillSQLParameter(stmt, params);
			rs = stmt.executeQuery();
			if (rs.next())
				result = rsProcessor.process(rs);
		} catch (SQLException ex) {
			throw ex;
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(stmt);
		}
		return result;
	}

	public <T, P extends IRS2Object<T>> T query2Object(P rsProcessor,
			String sql, Object... params) throws SQLException {
		Connection conn = this.getConnection();
		try {
			return this.query2Object(conn, rsProcessor, sql, params);
		} finally {
			SQLUtil.close(conn);
		}
	}

	public <T, P extends IRS2Object<T>> List<T> query2List(Connection conn,
			P rsProcessor, String sql, Object... params) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<T> list = new ArrayList<T>();
		try {
			stmt = this.prepare(conn, sql);
			fillSQLParameter(stmt, params);
			rs = stmt.executeQuery();
			while (rs.next()) {
				list.add(rsProcessor.process(rs));
			}
		} catch (SQLException ex) {
			throw ex;
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(stmt);

		}
		return list;
	}

	public ResultSet quesry2ResultSet(Connection conn,String sql,Object...params) throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = this.prepare(conn, sql);
			fillSQLParameter(stmt,params);
			rs = stmt.executeQuery();
		} catch(SQLException ex) {
			throw ex;
		}  
		return rs;
	}
	
	public <T, P extends IRS2Object<T>> List<T> query2List(P rsProcessor,
			String sql, Object... params) throws SQLException {
		Connection conn = this.getConnection();
		try {
			return this.query2List(conn, rsProcessor, sql, params);
		} finally {
			SQLUtil.close(conn);
		}
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public int[] batch(Connection conn, String sql, List<Object[]> paramList)
			throws SQLException {
		PreparedStatement stmt = null;
		int count = 0;
		int[] rows = null;
		try {
			stmt = this.prepare(conn, sql);
			if (this.batchSize <= 0)
				this.batchSize = 100;
			for (Object[] params : paramList) {
				this.fillSQLParameter(stmt, params);
				stmt.addBatch();
				count++;
				if (count == this.batchSize) {
					stmt.executeBatch();
					count = 0;
				}
			}
			if (count != 0)
				rows = stmt.executeBatch();
		} catch (SQLException ex) {
			throw ex;
		} finally {
			SQLUtil.close(stmt);
		}
		return rows;
	}
	
	public int[] batch(String sql,List<Object[]> paramList) throws SQLException {
		Connection conn = this.getConnection();
		try {
			return batch(conn,sql,paramList);
		} finally {
			SQLUtil.close(conn);
		}
	}
}
