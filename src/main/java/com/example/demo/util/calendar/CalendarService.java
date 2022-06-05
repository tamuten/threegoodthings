package com.example.demo.util.calendar;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.domain.model.MonthlyPost;
import com.example.demo.domain.repository.GoodRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CalendarService {
  private final GoodRepository goodRepository;

  public List<PostingDate> generateCalendar(final LocalDate targetDate, final String mailAddress) {
    List<LocalDate> calendar = generate(targetDate);

    List<MonthlyPost> monthlyPosts = goodRepository.findMonthlyPosts(mailAddress,
        targetDate.withDayOfMonth(1),
        targetDate.withDayOfMonth(targetDate.lengthOfMonth()));

    Function<LocalDate, PostingDate> toPostingDate = (ld) -> {
      if (ld == null) {
        return new PostingDate(null, false, false, false);
      }
      boolean isToday = false;
      boolean isFullyPosted = false;
      boolean isPartlyPosted = false;

      if (ld.isEqual(LocalDate.now())) {
        isToday = true;
      }
      for (MonthlyPost mp : monthlyPosts) {
        if (mp.getLocalDate().isEqual(ld)) {
          if (mp.isFullyPosted()) {
            isFullyPosted = true;
          } else {
            isPartlyPosted = true;
          }
        }
      }

      return new PostingDate(ld, isFullyPosted, isPartlyPosted, isToday);
    };

    return calendar.stream().map(toPostingDate).collect(Collectors.toList());

  }

  /**
   * 対象日を含む月のカレンダーを作成する
   *
   * @param targetLocalDate
   * @return
   */
  public static List<LocalDate> generate(LocalDate targetLocalDate) {

    final int year = targetLocalDate.getYear();
    final int month = targetLocalDate.getMonthValue();
    targetLocalDate = LocalDate.of(year, month, 1);

    // 今月の始まり
    DayOfWeek startDay = targetLocalDate.getDayOfWeek();
    // 今月末日
    int thisMonthLastDay = targetLocalDate.plusMonths(1L).minusDays(1L).getDayOfMonth();
    DayOfWeek lastDay = targetLocalDate.plusMonths(1L).minusDays(1L).getDayOfWeek();

    List<LocalDate> lDate = new ArrayList<LocalDate>();

    // 日曜日始まりでなければ前月の日付を格納する
    if (startDay != DayOfWeek.SUNDAY) {
      final int beforeMonthLastDay = LocalDate.of(year, month, 1).minusDays(1L).getDayOfMonth();
      final int beforeMonthDayCount = startDay.getValue();
      final int beforeMonthValue = targetLocalDate.minusMonths(1L).getMonthValue();
      int beforeMonthDate = beforeMonthLastDay - startDay.getValue() + 1;
      for (int i = 1; i <= beforeMonthDayCount; i++) {
        // lDate.add(LocalDate.of(year, beforeMonthValue, beforeMonthDate));
        lDate.add(null);
        beforeMonthDate++;
      }
    }

    // 当月分の日付を格納する
    for (int ld = 1; ld <= thisMonthLastDay; ld++) {
      lDate.add(LocalDate.of(year, month, ld));
    }

    // 土曜日終わりでなければ翌月分の日付を格納する。
    if (lastDay != DayOfWeek.SATURDAY) {
      final int nextMonthDayCount = calcNextMonthDayCount(lastDay);
      final int nextMonthValue = targetLocalDate.plusMonths(1L).getMonthValue();
      int nextMonthDate = 1;
      for (int i = 1; i <= nextMonthDayCount; i++) {
        // lDate.add(LocalDate.of(year, nextMonthValue, nextMonthDate));
        lDate.add(null);
        nextMonthDate++;
      }
    }

    return lDate;
  }

  private static int calcNextMonthDayCount(DayOfWeek lastDay) {
    if (lastDay != DayOfWeek.SUNDAY) {
      return 7 - (lastDay.getValue() + 1);
    } else {
      return 14 - (lastDay.getValue() + 1);
    }
  }
}
