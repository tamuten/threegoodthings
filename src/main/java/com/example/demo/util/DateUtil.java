package com.example.demo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	/**
	 * Stringをjava.util.Dateに変換する
	 *
	 * @param sDate
	 * @return
	 */
	public static Date parseDate(String sDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		try {
			return dateFormat.parse(sDate);
		} catch (ParseException e) {
			// TODO Exceptionクラス作る
			throw new RuntimeException(e);
		}
	}

	/**
	 *	java.util.DateをStringに変換する
	 *
	 * @param date
	 * @return
	 */
	public static String parseStr(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		return dateFormat.format(date);
	}

	public static java.sql.Date parseSqlDate(Date date) {
		return new java.sql.Date(date.getTime());
	}
}
