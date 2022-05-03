package com.example.demo;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.demo.exception.BusinessException;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(BusinessException.class)
	public String handleBusinessException(BusinessException e, Model model) {
		model.addAttribute("error", e);
		e.printStackTrace();
		return "businessError";
	}

	@ExceptionHandler(Exception.class)
	public String handleException(Exception e, Model model) {
		model.addAttribute("error", e);
		e.printStackTrace();
		return "systemError";
	}
}
