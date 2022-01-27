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
		final int startWeek = cal.get(Calendar.DAY_OF_WEEK);
		// 今月末日
		cal.set(year, month + 1, 0);
		final int thisMonthLastDay = cal.get(Calendar.DATE);
		final int lastWeek = cal.get(Calendar.DAY_OF_WEEK);

		// 先月末日
		cal.set(year, month, 0);
		final int beforeMonthLastDay = cal.get(Calendar.DATE);

		List<Week> calendarDayW = new ArrayList<Week>();
		int count = 0;

		Week week = new Week();

		// 日曜日始まりでなければ前月の日付を格納する
		if (startWeek != 1) {
			for (int i = startWeek - 2; i >= 0; i--) {
				week.setWeekday(count, beforeMonthLastDay - i);
				count++;
			}
		}

		//当月分の日付を格納する
		for (int i = 1; i <= thisMonthLastDay; i++) {
			week.setWeekday(count, i);
			if (count == 6) {
				calendarDayW.add(week);
				week = new Week();
				count = 0;
			} else {
				count++;
			}
		}

		// 日曜日終わりでなければ翌月分の日付を格納する。
		if (lastWeek != 7) {
			int j = 1;
			for (int i = lastWeek; i < 7; i++) {
				week.setWeekday(count, j++);
				count++;
			}
		}

		calendarDayW.add(week);
		if (calendarDayW.size() == 6) {
			calendarDayW.remove(5);
		}
		return calendarDayW;
	}
}
