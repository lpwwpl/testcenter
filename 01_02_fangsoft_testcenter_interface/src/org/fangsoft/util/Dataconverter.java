package org.fangsoft.util;

import java.sql.Timestamp;

public class Dataconverter {
	public static java.sql.Date date2SqlDate(java.util.Date date) {
		if (date == null)
			return new java.sql.Date(System.currentTimeMillis());
		else if (date instanceof java.sql.Date)
			return (java.sql.Date) date;
		return new java.sql.Date(date.getTime());
	}

	public static Timestamp date2SqlTimestamp(java.util.Date date) {
		if (date == null)
			return new Timestamp(System.currentTimeMillis());
		return new Timestamp(date.getTime());
	}

	public static boolean int2Boolean(int intVal) {
		if (intVal == 1)
			return true;
		return false;
	}

	public static int boolean2Int(boolean b) {
		if (b)
			return 1;
		return 0;
	}
}
