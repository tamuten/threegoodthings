package com.example.demo.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class User {
	private String userId;
	private String mailAddress;
	private String password;
}
