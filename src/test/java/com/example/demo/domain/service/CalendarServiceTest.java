package com.example.demo.domain.service;

import static com.example.demo.util.LocalDateParser.toLocalDate;
import static com.example.demo.util.calendar.CalendarService.generate;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CalendarServiceTest {
  private static final String FORMAT_DATE_SLASH = "yyyy/MM/dd";

  @Test
  void generateは対象日をメイン月とするカレンダーを作成する() {
    // Setup
    List<LocalDate> expected = CalendarServiceTestHelper.generateExpectedList();

    List<LocalDate> actual = generate(toLocalDate("2022/03/30", FORMAT_DATE_SLASH));

    assertThat(actual.size()).isEqualTo(expected.size());
    assertThat(actual).isEqualTo(expected);
  }
}
