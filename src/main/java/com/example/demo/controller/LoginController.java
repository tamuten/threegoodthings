package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.form.LoginForm;

@Controller
public class LoginController {

	/**
	 * ログイン画面のGETメソッド用処理.
	 */
	@GetMapping("/login")
	public String getLogin(LoginForm form, Model model, HttpServletRequest request, HttpServletResponse response) {

		// セッションタイムアウト時の処理
		if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
			// HTTP的に正しいかはともかく、なんとなく近そうなステータス(401)を返す
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
		return "login";
	}

	/**
	 * ログイン画面のPOSTメソッド用処理.
	 */
	@PostMapping("/login")
	public String postLogin(Model model) {
		return "redirect:/index";
	}

	@PostMapping("/guest_login")
	public String guest_login(LoginForm form) {
		System.out.println("guest");
		form.setMailAddress("guest");
		return "redirect:/index";
	}

}