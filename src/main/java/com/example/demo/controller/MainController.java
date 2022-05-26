package com.example.demo.controller;

import java.util.Date;
import java.util.List;

import com.example.demo.domain.model.Good;
import com.example.demo.domain.model.UserDetailsImpl;
import com.example.demo.domain.repository.GoodRepository;
import com.example.demo.form.MainForm;
import com.example.demo.util.DateUtil;
import com.example.demo.util.StrUtil;

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

@Controller
public class MainController {
  @Autowired
  private GoodRepository goodDao;

  @GetMapping({ "/", "/index" })
  public String today(Model model, @AuthenticationPrincipal UserDetailsImpl user) {
    // 本日の日付で表示する
    final Date today = new Date();

    Good good = goodDao.findByDate(user.getUsername(), DateUtil.utilToSql(today));

    MainForm form = new MainForm(today);
    if (good != null) {
      BeanUtils.copyProperties(good, form);
    }

    model.addAttribute("calDate", today);
    // model.addAttribute("calendarDay", CalendarService.generate(today));
    model.addAttribute("mainForm", form);
    model.addAttribute("timeline", null);

    return "index";
  }

  @GetMapping("/loadCalendar")
  public String loadCalendar(@RequestParam final Date targetDate, Model model) {
    // List<Week> calendarDay = CalendarService.generate(targetDate);
    model.addAttribute("calDate", targetDate);
    // model.addAttribute("calendarDay", calendarDay);
    return "calendar :: calendar_contents";
  }

  @GetMapping("/loadDiary")
  public String loadDiary(@RequestParam final Date date, Model model, @AuthenticationPrincipal UserDetailsImpl user) {
    Good good = goodDao.findByDate(user.getUsername(), DateUtil.utilToSql(date));
    MainForm form = new MainForm(date);
    if (good != null) {
      BeanUtils.copyProperties(good, form);
    }
    model.addAttribute("mainForm", form);
    return "diary :: diary_contents";
  }

  @GetMapping("/loadTimeline")
  public String loadTimeline(Model model, @AuthenticationPrincipal UserDetailsImpl user) {
    List<Good> timeline = goodDao.findAll(user.getUsername());
    model.addAttribute("timeline", timeline);
    model.addAttribute("diary", null);
    return "timeline :: timeline_contents";
  }

  @GetMapping("/searchDiary")
  public String searchDiary(@RequestParam final String keyword, Model model,
      @AuthenticationPrincipal UserDetailsImpl user) {
    List<Good> timeline = goodDao.likeSearch(user.getUsername(), keyword);
    model.addAttribute("timeline", timeline);
    model.addAttribute("diary", null);
    return "timeline :: timeline_contents";
  }

  @PostMapping("/register")
  @ResponseBody
  public String register(@RequestParam String good, @RequestParam final Date date, @RequestParam final int num,
      @AuthenticationPrincipal UserDetailsImpl user) {
    final java.sql.Date registerDate = DateUtil.utilToSql(date);
    final String mailAddress = user.getUsername();
    if (StringUtils.isEmpty(good))
      good = null;

    if (goodDao.countByDateAndUser(mailAddress, registerDate) <= 0) {
      goodDao.insert(mailAddress, good, num, registerDate);
    } else {
      goodDao.updateOne(mailAddress, good, num, registerDate);
    }

    return StrUtil.getJson(good);
  }
}
