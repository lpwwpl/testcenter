package org.fangsoft.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

import org.fangsoft.testcenter.config.Configuration;

public class DataConverter {
	public static Date str2Date(String val) {
		try {
			return Configuration.getDateFormat().parse(val);
		} catch(ParseException ex) {
			ex.printStackTrace();
		}
		return new Date();
	}

	public static int str2Int(String val) {
		if(val == null) return 0;
		try {
			return Integer.parseInt(val);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}
	
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
