package com.example.demo.domain.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TmpUser {
	private Integer id;
	private String mailAddress;
	private String password;
	private String token;
	private LocalDateTime expiryDate;
}
