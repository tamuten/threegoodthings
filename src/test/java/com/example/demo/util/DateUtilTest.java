package com.example.demo.util;

import static com.example.demo.util.DateUtil.*;
import static org.assertj.core.api.Assertions.*;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DateUtilTest {
	private static final String FORMAT_DATE_HYPHEN = "yyyy-MM-dd";

	@Test
	void sqlToUtilで日付を入力し正しく変換されること() throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE_HYPHEN);
		java.util.Date expected = dateFormat.parse("2022-3-28");

		java.util.Date actual = sqlToUtil(Date.valueOf("2022-3-28"));
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	void sqlToUtilでnullを引数に指定した場合はnullを返すこと() {
		java.util.Date actual = sqlToUtil(null);
		assertThat(actual).isNull();
	}

	@Test
	void utilToSqlで日付を指定し正しく変換される() throws ParseException {
		java.sql.Date expected = Date.valueOf("2022-3-28");
		SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE_HYPHEN);
		java.util.Date actual = utilToSql(dateFormat.parse("2022-3-28"));

		assertThat(actual).isEqualTo(expected);
	}

	@Test
	void utilToSqlでnullを引数に指定した場合はnullを返すこと() {
		java.sql.Date actual = utilToSql(null);
		assertThat(actual).isNull();
	}

	@Test
	void parseDateはnullでnullを返す() {
		java.util.Date actual = parseDate(null);
		assertThat(actual).isNull();
	}
}
