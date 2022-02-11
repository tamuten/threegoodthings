package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.domain.model.User;
import com.example.demo.domain.repository.UsersDao;
import com.example.demo.form.SignupForm;

@Controller
public class SignupController {
	@Autowired
	private UsersDao usersDao;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/signup")
	public String getSignup(SignupForm form, Model model) {
		return "signup";
	}

	@PostMapping("/signup")
	public String postSignup(@Validated SignupForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "signup";
		}

		User user = new User();
		final String mailAddress = form.getMailAddress();
		final String password = passwordEncoder.encode(form.getPassword());
		user.setPassword(password);
		user.setMailAddress(mailAddress);

		usersDao.insertOne(user);
		return "redirect:/index";
	}
}
