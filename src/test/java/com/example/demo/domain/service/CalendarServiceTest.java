package com.example.demo.domain.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.util.calendar.CalendarService;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CalendarServiceTest {
  @Test
  void generateは対象日をメイン月とするカレンダーを作成する() {
    // Setup
    List<LocalDate> expected = CalendarServiceTestHelper.generateExpectedList();

    List<LocalDate> actual = CalendarService.generate(LocalDate.parse("2022-03-30"));

    assertThat(actual.size()).isEqualTo(expected.size());
    assertThat(actual).isEqualTo(expected);
  }
}
