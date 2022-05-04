package com.example.demo.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
	public String signupConfirm(@Validated SignupForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "signup";
		}
		signupService.createTmpUserAndSendMail(form);
		return "signupComplete";
	}

	@GetMapping("/signup/certificate")
	public String signupComplete(@RequestParam String token, RedirectAttributes redirectAttributes,
			HttpServletRequest request) throws ServletException {
		TmpUser tmpUser = null;
		try {
			tmpUser = signupService.findTmpUserByToken(token);
		} catch (BusinessException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			return "redirect:/signup";
		}

		signupService.createUser(tmpUser.getMailAddress(), token);

		// ログアウト処理
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();

		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			SecurityContextHolder.clearContext();
		}

		// リダイレクトしログイン画面に遷移する。
		redirectAttributes.addFlashAttribute("msg", "登録が完了しました。");
		return "redirect:/login";
	}
}
