package org.fangsoft.db;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IRS2Object<T> {
	public  T process(ResultSet rs) throws SQLException;
}
