package org.fangsoft.testcenter.config;

import java.text.DateFormat;
import java.util.Locale;

import javax.sql.DataSource;

import org.fangsoft.db.DBConfig;
import org.fangsoft.db.DriverManagerDataSource;
import org.fangsoft.testcenter.dao.DaoFactory;
import org.fangsoft.testcenter.dao.db.DBDaoFactory;

public abstract class Configuration {
	private static final DateFormat dateFormat = DateFormat
			.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);
	public static final String[] CHOICE_LABEL = { "a", "b", "c", "d", "e" };
	private static final DataSource db2DataSource = new DriverManagerDataSource(
			DBConfig.getDB2Driver(), DBConfig.getDB2User(), DBConfig
					.getDB2Password(), DBConfig.getDB2URL());
	private static DaoFactory dbDaoFactory;
	private static DaoFactory propDaoFactory;
	public static final int MAX_LOGIN = 3;
	
	public static final DateFormat getDateFormat(Locale... locales) {
		if (locales == null || locales.length == 0)
			return dateFormat;
		return DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL,
				locales[0]);
	}

	public static final DataSource getDataSource() {
		return db2DataSource;
	}
	
	public static final DaoFactory getDBDaoFactory() {
		if(dbDaoFactory == null) {
			dbDaoFactory = new DBDaoFactory(Configuration.getDataSource());
		}
		return dbDaoFactory;
	}
	
//	public static final DaoFactory getPropsDaoFactory() {
//		if(propDaoFactory == null) {
//			propDaoFactory = PropDaoFactory.getInstance();
//		}
//		return propDaoFactory;
//	}
}
