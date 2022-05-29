package com.example.demo.util.calendar;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.example.demo.domain.repository.GoodRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CalendarService {
  private final GoodRepository goodRepository;

  public List<PostingDate> generateCalendar(final LocalDate targetDate, final String mailAddress) {
    List<LocalDate> calendar = generate(targetDate);

    List<LocalDate> monthlyPosts = goodRepository.findMonthlyPosts(mailAddress,
        calendar.get(0),
        calendar.get(calendar.size() - 1));

    Function<LocalDate, PostingDate> func = (ld) -> {
      return monthlyPosts.contains(ld) ? new PostingDate(ld, true) : new PostingDate(ld, false);
    };

    return calendar.stream().map(func).collect(Collectors.toList());
  }

  /**
   * 対象日を含む月のカレンダーを作成する
   *
   * @param targetLocalDate
   * @return
   */
  public static List<LocalDate> generate(final LocalDate targetLocalDate) {

    final int year = targetLocalDate.getYear();
    final int month = targetLocalDate.getMonthValue();

    // 今月の始まり
    DayOfWeek startDay = LocalDate.of(year, month, 1).getDayOfWeek();
    // 今月末日
    int thisMonthLastDay = LocalDate.of(year, month + 1, 1).minusDays(1L).getDayOfMonth();
    DayOfWeek lastDay = LocalDate.of(year, month + 1, 1).minusDays(1L).getDayOfWeek();

    List<LocalDate> lDate = new ArrayList<LocalDate>();

    // 日曜日始まりでなければ前月の日付を格納する
    if (startDay != DayOfWeek.SUNDAY) {
      final int beforeMonthLastDay = LocalDate.of(year, month, 1).minusDays(1L).getDayOfMonth();
      final int beforeMonthDayCount = startDay.getValue();
      int beforeMonthDate = beforeMonthLastDay - startDay.getValue() + 1;
      for (int i = 1; i <= beforeMonthDayCount; i++) {
        lDate.add(LocalDate.of(year, month - 1, beforeMonthDate));
        beforeMonthDate++;
      }
    }

    // 当月分の日付を格納する
    for (int ld = 1; ld <= thisMonthLastDay; ld++) {
      lDate.add(LocalDate.of(year, month, ld));
    }

    // 土曜日終わりでなければ翌月分の日付を格納する。
    if (lastDay != DayOfWeek.SATURDAY) {
      final int nextMonthDayCount = 7 - (lastDay.getValue() + 1);
      int nextMonthDate = 1;
      for (int i = 1; i <= nextMonthDayCount; i++) {
        lDate.add(LocalDate.of(year, month + 1, nextMonthDate));
        nextMonthDate++;
      }
    }

    return lDate;
  }
}
