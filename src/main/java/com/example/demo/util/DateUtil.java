package com.example.demo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static Date parseDate(String sDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		try {
			return dateFormat.parse(sDate);
		} catch (ParseException e) {
			// TODO Exceptionクラス作る
			throw new RuntimeException(e);
		}
	}

	public static String parseStr(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		return dateFormat.format(date);
	}
}
