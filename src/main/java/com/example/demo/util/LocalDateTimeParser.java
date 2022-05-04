package com.example.demo.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeParser {
	private LocalDateTimeParser() {
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
