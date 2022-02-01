package com.example.demo.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.domain.model.User;
import com.example.demo.domain.repository.UsersDao;
import com.example.demo.form.SignupForm;

@Controller
public class SignupController {
	@Autowired
	private UsersDao usersDao;

	@GetMapping("/signup")
	public String getSignup(SignupForm signupForm, Model model) {
		return "signup";
	}

	@PostMapping("/signup")
	public String postSignup(SignupForm form, Model model) {
		User user = new User();
		BeanUtils.copyProperties(form, user);
		usersDao.insertOne(user);
		return "redirect:/index";
	}
}
