package com.example.demo.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.example.demo.domain.model.Week;

public class CalendarUtil {
	/**
	 * 対象日を含む月のカレンダーを作成する
	 *
	 * @param targetDate
	 * @return
	 */
	public static List<Week> generateCalendar(final Date targetDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(targetDate);
		final int year = cal.get(Calendar.YEAR);
		final int month = cal.get(Calendar.MONTH);

		// 今月の始まり
		cal.set(year, month, 1);
		final int start_weekday = cal.get(Calendar.DAY_OF_WEEK);
		// 今月末日
		cal.set(year, month + 1, 0);
		final int this_month_lastday = cal.get(Calendar.DATE);
		final int last_weekday = cal.get(Calendar.DAY_OF_WEEK);

		// 先月末日
		cal.set(year, month, 0);
		final int before_month_lastday = cal.get(Calendar.DATE);

		List<Week> calendarDay = new ArrayList<Week>();
		int count = 0;

		Week week = new Week();

		// 日曜日始まりでなければ前月の日付を格納する
		if (start_weekday != Calendar.SUNDAY) {
			for (int i = start_weekday - 2; i >= 0; i--) {
				cal.set(year, month - 1, before_month_lastday - i);
				final Date DATE = cal.getTime();
				week.setWeekday(count, DATE);
				count++;
			}
		}

		//当月分の日付を格納する
		for (int i = 1; i <= this_month_lastday; i++) {
			cal.set(year, month, i);
			final Date DATE = cal.getTime();
			week.setWeekday(count, DATE);
			if (count == 6) {
				calendarDay.add(week);
				week = new Week();
				count = 0;
			} else {
				count++;
			}
		}

		// 土曜日終わりでなければ翌月分の日付を格納する。
		if (last_weekday != Calendar.SATURDAY) {
			int j = 1;
			for (int i = last_weekday; i < 7; i++) {
				cal.set(year, month + 1, j++);
				final Date DATE = cal.getTime();
				week.setWeekday(count, DATE);
				count++;
			}
			calendarDay.add(week);
		}

		return calendarDay;
	}
}
