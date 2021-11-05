package com.example.demo.controller;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.domain.model.Good;
import com.example.demo.domain.repository.GoodDao;
import com.example.demo.form.MainForm;

@Controller
public class MainController {
	@Autowired
	private GoodDao goodDao;

	@GetMapping("/index")
	public String index(MainForm form, Model model) {
		// 本日の日付で表示する
		Date today = new Date();
		form.setDate(today);
		findPost(form, model);
		return "index";
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
	public String yesterday(MainForm form, Model model) {
		Date today = form.getDate();
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		form.setDate(cal.getTime());
		findPost(form, model);
		return "index";
	}

	@GetMapping("/tomorrow")
	public String tomorrow(MainForm form, Model model) {
		Date today = form.getDate();
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		form.setDate(cal.getTime());
		findPost(form, model);
		return "index";
	}

	private void findPost(MainForm form, Model model) {
		Good good = goodDao.selectOne(new java.sql.Date(form.getDate()
			.getTime()));
		if (good == null)
			return;
		BeanUtils.copyProperties(good, form);
	}
}
