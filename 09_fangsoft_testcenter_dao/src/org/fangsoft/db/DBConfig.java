package org.fangsoft.db;

public class DBConfig {

	public static final String ORACLE_DRIVER = "oracle.jdbc.OracleDriver";
	public static final String ORACLE_USER = "fangsoft";
	public static final String ORACLE_PASSWORD = "fangsoft";
	public static final String ORACLE_HOST = "localhost";
	public static final String ORACLE_PORT = "1521";
	public static final String ORACLE_DATABASE = "fangsoft";

	public static final String DB2_DRIVER = "com.ibm.db2.jcc.DB2Driver";
	public static final String DB2_USER = "lpw";
	public static final String DB2_PASSWORD = "lpwwpl";
	public static final String DB2_HOST = "localhost";
	public static final String DB2_PORT = "50000";
	public static final String DB2_DATABASE = "fangsoft";

	public static String getOracleDriver() {
		return ORACLE_DRIVER;
	}

	public static String getOracleUser() {
		return ORACLE_USER;
	}

	public static String getOraclePassword() {
		return ORACLE_PASSWORD;
	}

	public static String getOracleURL() {
		return "jdbc:oracle:thin:@" + ORACLE_HOST + ":" + ORACLE_PORT + ":"
				+ ORACLE_DATABASE;
	}

	public static String getDB2Driver() {
		return DB2_DRIVER;
	}

	public static String getDB2User() {
		return DB2_USER;
	}

	public static String getDB2Password() {
		return DB2_PASSWORD;
	}

	public static String getDB2URL() {
		return "jdbc:db2://" + DB2_HOST + ":" + DB2_PORT + "/"
				+ DB2_DATABASE;
	}
}
