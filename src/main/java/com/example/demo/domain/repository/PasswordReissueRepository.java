package com.example.demo.domain.repository;

import org.springframework.stereotype.Repository;

import com.example.demo.domain.mapper.PasswordReissueMapper;
import com.example.demo.domain.model.PasswordReissueInfo;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PasswordReissueRepository {
	private final PasswordReissueMapper mapper;

	public int create(PasswordReissueInfo info) {
		return mapper.insertOne(info);
	}

	public PasswordReissueInfo findByToken(String token) {
		return mapper.findOne(token);
	}

	public int deleteByToken(String token) {
		return mapper.deleteByToken(token);
	}
}
