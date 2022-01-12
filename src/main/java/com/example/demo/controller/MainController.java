package com.example.demo.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
import com.example.demo.util.CalendarUtil;
import com.example.demo.util.DateUtil;
import com.example.demo.util.StrUtil;

@Controller
public class MainController {
	@Autowired
	private GoodDao goodDao;

	//	@InitBinder
	//	public void initBinder(WebDataBinder binder) {
	//		// 未入力のStringをnullに設定する
	//		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	//	}

	private String index(Date searchDate, Model model) {
		List<Week> calendarDay = CalendarUtil.generateCalendar(searchDate);
		model.addAttribute("calendarDay", calendarDay);

		Good good = goodDao.selectOne(new java.sql.Date(searchDate
			.getTime()));
		MainForm form = new MainForm();
		form.setDate(searchDate);
		if (good != null) {
			BeanUtils.copyProperties(good, form);
		}
		model.addAttribute("mainForm", form);

		List<Good> timeline = goodDao.findAll();
		model.addAttribute("timeline", timeline);

		return "index";
	}

	@GetMapping("/loadCalendar")
	@ResponseBody
	public String loadCalendar(@RequestParam Date targetDate) {
		return StrUtil.getJson(CalendarUtil.generateCalendar(targetDate));
	}

	@GetMapping({ "/", "/index" })
	public String today(Model model) {
		// 本日の日付で表示する
		return index(new Date(), model);
	}

	@PostMapping("/register")
	@ResponseBody
	public String register(@RequestParam String good, @RequestParam Date date, @RequestParam int num) {
		if (StringUtils.isEmpty(good)) good = null;
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

	@GetMapping("/loadDiary")
	@ResponseBody
	public String loadDiary(@RequestParam Date date) {
		Good good = goodDao.selectOne(DateUtil.parseSqlDate(date));
		if (good == null) {
			good = new Good();
			good.setDate(DateUtil.parseSqlDate(date));
		}
		return StrUtil.getJson(good);
	}

	@GetMapping("/loadTimeline")
	@ResponseBody
	public String loadTimeline() {
		List<Good> timeline = goodDao.findAll();
		return StrUtil.getJson(timeline);
	}
}
