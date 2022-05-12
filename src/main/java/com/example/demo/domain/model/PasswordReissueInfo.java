package com.example.demo.domain.model;

import lombok.Data;

@Data
public class PasswordReissueInfo {
	private String mailAddress;
	private String token;
}
