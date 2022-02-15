package com.example.demo.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class User {
	private Integer userId;
	private String mailAddress;
	private String password;
}
