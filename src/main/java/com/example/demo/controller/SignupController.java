package com.example.demo.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.domain.model.TmpUser;
import com.example.demo.domain.repository.UsersDao;
import com.example.demo.domain.service.MailService;
import com.example.demo.domain.service.TmpUserService;
import com.example.demo.form.SignupForm;
import com.example.demo.form.validator.SignupFormValidator;
import com.example.demo.util.UUIDUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class SignupController {
	private final UsersDao usersDao;
	private final TmpUserService tmpUserService;
	private final MailService mailService;
	private final PasswordEncoder passwordEncoder;
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

		final String password = passwordEncoder.encode(form.getPassword());
		log.info("[encodedpassword]" + password);

		String uuid = UUIDUtil.generateUUID();
		log.info("[GenaratedUUID]" + uuid);

		tmpUserService.create(new TmpUser(form.getMailAddress(), password, uuid));
		mailService.sendRegistrationConfirmationMail(password, uuid);

		return "signupComplete";
	}
}
