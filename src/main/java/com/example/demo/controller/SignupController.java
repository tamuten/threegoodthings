package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.domain.repository.UsersDao;
import com.example.demo.domain.service.SignupService;
import com.example.demo.form.SignupForm;
import com.example.demo.form.validator.SignupFormValidator;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SignupController {
	private final UsersDao usersDao;
	private final SignupService tmpUserService;
	private final SignupFormValidator validator;

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
		tmpUserService.createTmpUserAndSendMail(form);
		return "signupComplete";
	}

	@GetMapping("/signup/certificate")
	public String signupComplete() {
		return "";
	}
}
