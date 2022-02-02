package com.example.demo.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class SignupForm {
	@NotEmpty
	@Size(max = 60)
	private String mailAddress;
	@NotEmpty
	@Size(max = 60)
	private String password;
	@NotEmpty
	@Size(max = 60)
	private String passwordConfirm;
}
