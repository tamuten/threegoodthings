package com.example.demo.domain.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PasswordReissueInfo {
	private String mailAddress;
	private String token;
	private LocalDateTime expiryDate;
}
