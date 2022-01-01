package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.domain.model.Good;
import com.example.demo.domain.model.Week;
import com.example.demo.domain.repository.GoodDao;
import com.example.demo.form.MainForm;
import com.example.demo.util.DateUtil;
import com.example.demo.util.StrUtil;

@Controller
public class MainController {
	@Autowired
	private GoodDao goodDao;

	private String index(Date searchDate, Model model) {
		Calendar rightNow = Calendar.getInstance();
		int day = rightNow.get(Calendar.DATE);
		int year = rightNow.get(Calendar.YEAR);
		int month = rightNow.get(Calendar.MONTH);

		// 今月の始まり
		rightNow.set(year, month, 1);
		int startWeek = rightNow.get(Calendar.DAY_OF_WEEK);
		// 今月末日
		rightNow.set(year, month + 1, 0);
		int thisMonthLastDay = rightNow.get(Calendar.DATE);
		int lastWeek = rightNow.get(Calendar.DAY_OF_WEEK);

		// 先月末日
		rightNow.set(year, month, 0);
		int beforeMonthLastDay = rightNow.get(Calendar.DATE);

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
		model.addAttribute("calendarDay", calendarDayW);

		Good good = goodDao.selectOne(new java.sql.Date(searchDate
			.getTime()));
		MainForm form = new MainForm();
		form.setDate(searchDate);
		if (good != null) {
			BeanUtils.copyProperties(good, form);
		}
		model.addAttribute("mainForm", form);
		return "index";
	}

	@GetMapping({ "/", "/index" })
	public String today(Model model) {
		// 本日の日付で表示する
		return index(new Date(), model);
	}

	@PostMapping("/register")
	@ResponseBody
	public String register(@RequestParam String good, @RequestParam Date date, @RequestParam int num) {
		java.sql.Date registerDate = new java.sql.Date(date.getTime());

		if (goodDao.count(registerDate) <= 0) {
			goodDao.insert(good, num, registerDate);
		} else {
			goodDao.updateOne(good, num, registerDate);
		}

		return StrUtil.getJson(good);
	}

	@GetMapping("/yesterday")
	@ResponseBody
	public String yesterday(@RequestParam Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return loadDiary(cal.getTime());
	}

	@GetMapping("/tomorrow")
	@ResponseBody
	public String tomorrow(@RequestParam Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		return loadDiary(cal.getTime());
	}

	@GetMapping("/load")
	@ResponseBody
	public String loadDiary(@RequestParam Date date) {
		Good good = goodDao.selectOne(DateUtil.parseSqlDate(date));
		if (good == null) {
			good = new Good();
			good.setDate(DateUtil.parseSqlDate(date));
		}
		return StrUtil.getJson(good);
	}
}
