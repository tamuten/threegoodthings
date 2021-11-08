package com.example.demo.controller;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.domain.model.Good;
import com.example.demo.domain.repository.GoodDao;
import com.example.demo.form.MainForm;
import com.example.demo.util.DateUtil;

@Controller
public class MainController {
	@Autowired
	private GoodDao goodDao;

	private String index(Date searchDate, Model model) {
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

	@GetMapping("/index")
	public String today(Model model) {
		// 本日の日付で表示する
		return index(new Date(), model);
	}

	@PostMapping("/register")
	public String register(MainForm form, Model model) {
		Good good = new Good();
		BeanUtils.copyProperties(form, good);
		good.setDate(new java.sql.Date(form.getDate()
			.getTime()));
		goodDao.insert(good);
		return "index";
	}

	@GetMapping("/yesterday")
	public String yesterday(Model model, HttpServletRequest request) {
		Date today = DateUtil.parseDate(request.getParameter("date"));
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		cal.add(Calendar.DAY_OF_MONTH, -1);

		return index(cal.getTime(), model);
	}

	@GetMapping("/tomorrow")
	public String tomorrow(Model model, HttpServletRequest request) {
		Date today = DateUtil.parseDate(request.getParameter("date"));
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		cal.add(Calendar.DAY_OF_MONTH, 1);

		return index(cal.getTime(), model);
	}
}
