package com.example.demo.form;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class LoginForm {
	private String mailAddress;
	private String password;
}
