package com.example.demo.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class SignupForm {
	@NotEmpty(message = "{require_check}")
	@Size(max = 60, min = 1, message = "{max_check}")
	@Email(message = "{email_check}")
	private String mailAddress;
	@NotEmpty(message = "{require_check}")
	@Size(max = 60, min = 1, message = "{max_check}")
	private String password;
	@NotEmpty(message = "{require_check}")
	@Size(max = 60, min = 1, message = "{max_check}")
	private String passwordConfirm;
}
