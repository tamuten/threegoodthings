package com.example.demo.form.validator;

import org.springframework.validation.Errors;

import com.example.demo.form.SignupForm;

public class SignupFormValidator extends AbstractValidator<SignupForm> {

	@Override
	protected void doValidate(SignupForm form, Errors errors) {
		final String password = form.getPassword();
		final String passwordConfirm = form.getPasswordConfirm();
		if (!password.equals(passwordConfirm)) {
			errors.rejectValue("password", "");
		}
	}

}
