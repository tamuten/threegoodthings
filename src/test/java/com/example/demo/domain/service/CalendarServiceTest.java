package com.example.demo.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.domain.repository.GoodRepository;
import com.example.demo.util.calendar.CalendarService;
import com.example.demo.util.calendar.PostingDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class CalendarServiceTest {
  @Autowired
  private CalendarService calendarService;
  @MockBean
  private GoodRepository goodRepository;

  @Test
  void generateは対象日をメイン月とするカレンダーを作成する() {
    // Setup
    List<LocalDate> expected = CalendarServiceTestHelper.generate202203Calendar();

    List<LocalDate> actual = CalendarService.generate(LocalDate.parse("2022-03-30"));

    assertThat(actual.size()).isEqualTo(expected.size());
    assertThat(actual).isEqualTo(expected);
  }

  private static final List<LocalDate> MONTHLY_POSTS = new ArrayList<>() {
    {
      add(LocalDate.of(2022, 3, 10));
      add(LocalDate.of(2022, 3, 15));
      add(LocalDate.of(2022, 3, 23));
    }
  };

  private static final List<PostingDate> EXPECTED_CAL = new ArrayList<>() {
    {
      add(new PostingDate(LocalDate.of(2022, 2, 27), false));
      add(new PostingDate(LocalDate.of(2022, 2, 28), false));
      add(new PostingDate(LocalDate.of(2022, 3, 1), false));
      add(new PostingDate(LocalDate.of(2022, 3, 2), false));
      add(new PostingDate(LocalDate.of(2022, 3, 3), false));
      add(new PostingDate(LocalDate.of(2022, 3, 4), false));
      add(new PostingDate(LocalDate.of(2022, 3, 5), false));
      add(new PostingDate(LocalDate.of(2022, 3, 6), false));
      add(new PostingDate(LocalDate.of(2022, 3, 7), false));
      add(new PostingDate(LocalDate.of(2022, 3, 8), false));
      add(new PostingDate(LocalDate.of(2022, 3, 9), false));
      add(new PostingDate(LocalDate.of(2022, 3, 10), true));
      add(new PostingDate(LocalDate.of(2022, 3, 11), false));
      add(new PostingDate(LocalDate.of(2022, 3, 12), false));
      add(new PostingDate(LocalDate.of(2022, 3, 13), false));
      add(new PostingDate(LocalDate.of(2022, 3, 14), false));
      add(new PostingDate(LocalDate.of(2022, 3, 15), true));
      add(new PostingDate(LocalDate.of(2022, 3, 16), false));
      add(new PostingDate(LocalDate.of(2022, 3, 17), false));
      add(new PostingDate(LocalDate.of(2022, 3, 18), false));
      add(new PostingDate(LocalDate.of(2022, 3, 19), false));
      add(new PostingDate(LocalDate.of(2022, 3, 20), false));
      add(new PostingDate(LocalDate.of(2022, 3, 21), false));
      add(new PostingDate(LocalDate.of(2022, 3, 22), false));
      add(new PostingDate(LocalDate.of(2022, 3, 23), true));
      add(new PostingDate(LocalDate.of(2022, 3, 24), false));
      add(new PostingDate(LocalDate.of(2022, 3, 25), false));
      add(new PostingDate(LocalDate.of(2022, 3, 26), false));
      add(new PostingDate(LocalDate.of(2022, 3, 27), false));
      add(new PostingDate(LocalDate.of(2022, 3, 28), false));
      add(new PostingDate(LocalDate.of(2022, 3, 29), false));
      add(new PostingDate(LocalDate.of(2022, 3, 30), false));
      add(new PostingDate(LocalDate.of(2022, 3, 31), false));
      add(new PostingDate(LocalDate.of(2022, 4, 1), false));
      add(new PostingDate(LocalDate.of(2022, 4, 2), false));
    }
  };

  @Test
  void generateCalendarは対象日をメイン月とした投稿情報のあるカレンダーを返却する() {
    when(goodRepository.findMonthlyPosts("tamuten310@gmail.com",
        LocalDate.of(2022, 2, 27), LocalDate.of(2022, 4, 2)))
        .thenReturn(MONTHLY_POSTS);

    List<PostingDate> actualList = calendarService.generateCalendar(LocalDate.of(2022, 3, 28),
        "tamuten310@gmail.com");

    assertThat(actualList.size()).isEqualTo(EXPECTED_CAL.size());
    assertThat(actualList).isEqualTo(EXPECTED_CAL);

    verify(goodRepository, times(1)).findMonthlyPosts("tamuten310@gmail.com",
        LocalDate.of(2022, 2, 27),
        LocalDate.of(2022, 4, 2));
  }
}
