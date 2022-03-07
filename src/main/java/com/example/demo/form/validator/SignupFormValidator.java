package com.example.demo.form.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.example.demo.domain.model.User;
import com.example.demo.domain.repository.UsersDao;
import com.example.demo.form.SignupForm;

@Component
public class SignupFormValidator extends AbstractValidator<SignupForm> {
	@Autowired
	private UsersDao usersDao;

	@Override
	protected void doValidate(SignupForm form, Errors errors) {
		final String mailAddress = form.getMailAddress();
		final String password = form.getPassword();
		final String passwordConfirm = form.getPasswordConfirm();

		User user = usersDao.selectOne(mailAddress);
		if (user != null) {
			errors.rejectValue("mailAddress", "signupForm.isAlreadyExist");
		}

		if (!password.equals(passwordConfirm)) {
			errors.rejectValue("passwordConfirm", "signupForm.confirmMistake");
		}
	}

}
