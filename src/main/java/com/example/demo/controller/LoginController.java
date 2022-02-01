package com.example.demo.controller;

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
	public String getLogin(LoginForm form, Model model) {
		return "login";
	}

	/**
	 * ログイン画面のPOSTメソッド用処理.
	 */
	@PostMapping("/login")
	public String postLogin(Model model) {
		return "redirect:/index";
	}
}