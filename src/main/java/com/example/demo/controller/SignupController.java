package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.domain.model.User;
import com.example.demo.domain.repository.UsersDao;
import com.example.demo.form.SignupForm;
import com.example.demo.form.validator.SignupFormValidator;

@Controller
public class SignupController {
	@Autowired
	private UsersDao usersDao;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private SignupFormValidator validator;

	@InitBinder("signupForm")
	public void validatorBinder(WebDataBinder binder) {
		binder.addValidators(validator);
	}

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
		System.out.println(password);
		user.setPassword(password);
		user.setMailAddress(mailAddress);

		usersDao.insertOne(user);
		return "signupComplete";
	}
}
