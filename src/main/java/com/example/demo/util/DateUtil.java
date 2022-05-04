package com.example.demo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {
	private DateUtil() {
	}

	/**
	 * Stringをjava.util.Dateに変換する
	 *
	 * @param sDate
	 * @return
	 */
	public static Date parseDate(final String sDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		try {
			return dateFormat.parse(sDate);
		} catch (ParseException | NullPointerException e) {
			return null;
		}
	}

	/**
	 *	java.util.DateをStringに変換する
	 *
	 * @param date
	 * @return
	 */
	public static String parseStr(final Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		if (date != null) {
			return dateFormat.format(date);
		} else {
			return null;
		}
	}

	/**
	 * java.sql.Date型をjava.util.Date型に変換します。<br>
	 * 引数にnullが指定された場合はnullを返却します。
	 *
	 * @param sqlDate
	 * @return
	 */
	public static java.util.Date sqlToUtil(java.sql.Date sqlDate) {
		if (sqlDate == null) {
			return null;
		}
		return new java.util.Date(sqlDate.getTime());
	}

	/**
	 * java.util.Date型をjava.sql.Date型に変換します<br>
	 * 引数にnullが指定された場合はnullを返却します。
	 *
	 * @param utilDate
	 * @return
	 */
	public static java.sql.Date utilToSql(java.util.Date utilDate) {
		if (utilDate == null) {
			return null;
		}
		return new java.sql.Date(utilDate.getTime());
	}

	public static String toStr(LocalDateTime localDateTime, String format) {
		if (localDateTime == null) {
			return null;
		}
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
		return localDateTime.format(dateTimeFormatter);
	}

	public static LocalDateTime toLocalDateTime(String date, String format) {
		if (date == null) {
			return null;
		}
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
		return LocalDateTime.parse(date, dateTimeFormatter);
	}
}
