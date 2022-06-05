package com.example.demo.domain.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.demo.domain.repository.GoodRepository;
import com.example.demo.util.calendar.CalendarService;

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

  // private static final List<LocalDate> MONTHLY_POSTS = new ArrayList<>() {
  // {
  // add(LocalDate.of(2022, 3, 10));
  // add(LocalDate.of(2022, 3, 15));
  // add(LocalDate.of(2022, 3, 23));
  // }
  // };

  // private static final List<PostingDate> EXPECTED_CAL = new ArrayList<>() {
  // {
  // add(new PostingDate(LocalDate.of(2022, 2, 27), false, false));
  // add(new PostingDate(LocalDate.of(2022, 2, 28), false, false));
  // add(new PostingDate(LocalDate.of(2022, 3, 1), false, false));
  // add(new PostingDate(LocalDate.of(2022, 3, 2), false, false));
  // add(new PostingDate(LocalDate.of(2022, 3, 3), false, false));
  // add(new PostingDate(LocalDate.of(2022, 3, 4), false, false));
  // add(new PostingDate(LocalDate.of(2022, 3, 5), false, false));
  // add(new PostingDate(LocalDate.of(2022, 3, 6), false, false));
  // add(new PostingDate(LocalDate.of(2022, 3, 7), false, false));
  // add(new PostingDate(LocalDate.of(2022, 3, 8), false, false));
  // add(new PostingDate(LocalDate.of(2022, 3, 9), false, false));
  // add(new PostingDate(LocalDate.of(2022, 3, 10), true, false));
  // add(new PostingDate(LocalDate.of(2022, 3, 11), false, false));
  // add(new PostingDate(LocalDate.of(2022, 3, 12), false, false));
  // add(new PostingDate(LocalDate.of(2022, 3, 13), false, false));
  // add(new PostingDate(LocalDate.of(2022, 3, 14), false, false));
  // add(new PostingDate(LocalDate.of(2022, 3, 15), true, false));
  // add(new PostingDate(LocalDate.of(2022, 3, 16), false, false));
  // add(new PostingDate(LocalDate.of(2022, 3, 17), false, false));
  // add(new PostingDate(LocalDate.of(2022, 3, 18), false, false));
  // add(new PostingDate(LocalDate.of(2022, 3, 19), false, false));
  // add(new PostingDate(LocalDate.of(2022, 3, 20), false, false));
  // add(new PostingDate(LocalDate.of(2022, 3, 21), false, false));
  // add(new PostingDate(LocalDate.of(2022, 3, 22), false, false));
  // add(new PostingDate(LocalDate.of(2022, 3, 23), true, false));
  // add(new PostingDate(LocalDate.of(2022, 3, 24), false, false));
  // add(new PostingDate(LocalDate.of(2022, 3, 25), false, false));
  // add(new PostingDate(LocalDate.of(2022, 3, 26), false, false));
  // add(new PostingDate(LocalDate.of(2022, 3, 27), false, false));
  // add(new PostingDate(LocalDate.of(2022, 3, 28), false, false));
  // add(new PostingDate(LocalDate.of(2022, 3, 29), false, false));
  // add(new PostingDate(LocalDate.of(2022, 3, 30), false, false));
  // add(new PostingDate(LocalDate.of(2022, 3, 31), false, false));
  // add(new PostingDate(LocalDate.of(2022, 4, 1), false, false));
  // add(new PostingDate(LocalDate.of(2022, 4, 2), false, false));
  // }
  // };

  // @Test
  // void generateCalendarは対象日をメイン月とした投稿情報のあるカレンダーを返却する() {
  // when(goodRepository.findMonthlyPosts("tamuten310@gmail.com",
  // LocalDate.of(2022, 2, 27), LocalDate.of(2022, 4, 2)))
  // .thenReturn(MONTHLY_POSTS);

  // List<PostingDate> actualList =
  // calendarService.generateCalendar(LocalDate.of(2022, 3, 28),
  // "tamuten310@gmail.com");

  // assertThat(actualList.size()).isEqualTo(EXPECTED_CAL.size());
  // assertThat(actualList).isEqualTo(EXPECTED_CAL);

  // verify(goodRepository, times(1)).findMonthlyPosts("tamuten310@gmail.com",
  // LocalDate.of(2022, 2, 27),
  // LocalDate.of(2022, 4, 2));
  // }
}
