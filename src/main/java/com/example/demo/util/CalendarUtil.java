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
		final int YEAR = cal.get(Calendar.YEAR);
		final int MONTH = cal.get(Calendar.MONTH);

		// 今月の始まり
		cal.set(YEAR, MONTH, 1);
		final int STARTWEEK = cal.get(Calendar.DAY_OF_WEEK);
		// 今月末日
		cal.set(YEAR, MONTH + 1, 0);
		final int THISMONTHLASTDAY = cal.get(Calendar.DATE);
		final int LASTWEEK = cal.get(Calendar.DAY_OF_WEEK);

		// 先月末日
		cal.set(YEAR, MONTH, 0);
		final int BEFOREMONTHLASTDAY = cal.get(Calendar.DATE);

		List<Week> calendarDay = new ArrayList<Week>();
		int count = 0;

		Week week = new Week();

		// 日曜日始まりでなければ前月の日付を格納する
		if (STARTWEEK != 1) {
			for (int i = STARTWEEK - 2; i >= 0; i--) {
				cal.set(YEAR, MONTH - 1, BEFOREMONTHLASTDAY - i);
				final Date DATE = cal.getTime();
				week.setWeekday(count, DATE);
				count++;
			}
		}

		//当月分の日付を格納する
		for (int i = 1; i <= THISMONTHLASTDAY; i++) {
			cal.set(YEAR, MONTH, i);
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

		// 日曜日終わりでなければ翌月分の日付を格納する。
		if (LASTWEEK != 7) {
			int j = 1;
			for (int i = LASTWEEK; i < 7; i++) {
				cal.set(YEAR, MONTH + 1, j++);
				final Date DATE = cal.getTime();
				week.setWeekday(count, DATE);
				count++;
			}
		}

		calendarDay.add(week);
		if (calendarDay.size() == 6) {
			calendarDay.remove(5);
		}
		return calendarDay;
	}
}
