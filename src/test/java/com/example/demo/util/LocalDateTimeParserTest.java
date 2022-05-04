package com.example.demo.util;

import static com.example.demo.util.LocalDateTimeParser.*;
import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LocalDateTimeParserTest {
	private static final String FORMAT_DATETIME_SLASH = "yyyy/MM/dd HH:mm:ss";

	@Test
	void toStrはnullでnullを返す() {
		String actual = toStr(null, FORMAT_DATETIME_SLASH);
		assertThat(actual).isNull();
	}

	@Test
	void toLocalDateTimeはnullでnullを返す() {
		LocalDateTime actual = toLocalDateTime(null, FORMAT_DATETIME_SLASH);
		assertThat(actual).isNull();
	}

	@Test
	void toStrはLocalDateTimeをフォーマッター通りの文字列に変換する() {
		String actual = toStr(toLocalDateTime("2022/05/04 12:00:00", FORMAT_DATETIME_SLASH),
				FORMAT_DATETIME_SLASH);
		assertThat(actual).isEqualTo("2022/05/04 12:00:00");
	}
}
