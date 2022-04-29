package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.domain.model.TmpUser;
import com.example.demo.domain.service.SignupService;
import com.example.demo.exception.BusinessException;
import com.example.demo.form.SignupForm;
import com.example.demo.form.validator.SignupFormValidator;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SignupController {
	private final SignupService signupService;
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
		signupService.createTmpUserAndSendMail(form);
		return "signupConfirm";
	}

	@GetMapping("/signup/certificate")
	public String signupComplete(@RequestParam String token, RedirectAttributes redirectAttributes) {
		TmpUser tmpUser = null;
		try {
			tmpUser = signupService.findTmpUserByToken(token);
		} catch (BusinessException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			return "redirect:/signup";
		}

		signupService.createUser(tmpUser.getMailAddress(), token);

		// TODO: リダイレクトしログイン後の画面に遷移する。
		return "signupComplete";
	}
}
