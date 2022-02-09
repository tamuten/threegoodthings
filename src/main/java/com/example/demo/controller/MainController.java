package com.example.demo.controller;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.domain.model.Good;
import com.example.demo.domain.model.UserDetailsImpl;
import com.example.demo.domain.model.Week;
import com.example.demo.domain.repository.GoodDao;
import com.example.demo.form.MainForm;
import com.example.demo.util.CalendarUtil;
import com.example.demo.util.StrUtil;

@Controller
public class MainController {
	@Autowired
	private GoodDao goodDao;

	@GetMapping({ "/", "/index" })
	public String today(Model model, @AuthenticationPrincipal UserDetailsImpl user) {
		// 本日の日付で表示する
		final Date TODAY = new Date();

		List<Week> calendarDay = CalendarUtil.generateCalendar(TODAY);
		model.addAttribute("calDate", TODAY);
		model.addAttribute("calendarDay", calendarDay);

		Good good = goodDao.selectOne(user.getUsername(), new java.sql.Date(TODAY
			.getTime()));
		MainForm form = new MainForm();
		form.setDate(TODAY);
		if (good != null) {
			BeanUtils.copyProperties(good, form);
		}
		model.addAttribute("mainForm", form);
		model.addAttribute("timeline", null);

		return "index";
	}

	@GetMapping("/loadCalendar")
	public String loadCalendar(@RequestParam final Date targetDate, Model model) {
		List<Week> calendarDay = CalendarUtil.generateCalendar(targetDate);
		model.addAttribute("calDate", targetDate);
		model.addAttribute("calendarDay", calendarDay);
		return "calendar :: calendar_contents";
	}

	@GetMapping("/loadDiary")
	public String loadDiary(@RequestParam final Date date, Model model, @AuthenticationPrincipal UserDetailsImpl user) {
		Good good = goodDao.selectOne(user.getUsername(), new java.sql.Date(date
			.getTime()));
		MainForm form = new MainForm();
		form.setDate(date);
		if (good != null) {
			BeanUtils.copyProperties(good, form);
		}
		model.addAttribute("mainForm", form);
		return "diary :: diary_contents";
	}

	@GetMapping("/loadTimeline")
	public String loadTimeline(Model model) {
		List<Good> timeline = goodDao.findAll();
		model.addAttribute("timeline", timeline);
		model.addAttribute("diary", null);
		return "timeline :: timeline_contents";
	}

	@GetMapping("/searchDiary")
	public String searchDiary(@RequestParam final String keyword, Model model) {
		List<Good> timeline = goodDao.likeSearch(keyword);
		model.addAttribute("timeline", timeline);
		model.addAttribute("diary", null);
		return "timeline :: timeline_contents";
	}

	@PostMapping("/register")
	@ResponseBody
	public String register(@RequestParam String good, @RequestParam final Date date, @RequestParam final int num) {
		if (StringUtils.isEmpty(good)) good = null;
		final java.sql.Date registerDate = new java.sql.Date(date.getTime());

		if (goodDao.count(registerDate) <= 0) {
			goodDao.insert(good, num, registerDate);
		} else {
			goodDao.updateOne(good, num, registerDate);
		}

		return StrUtil.getJson(good);
	}
}
