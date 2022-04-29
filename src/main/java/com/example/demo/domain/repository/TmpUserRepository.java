package com.example.demo.domain.repository;

import org.springframework.stereotype.Repository;

import com.example.demo.domain.mapper.TmpUserMapper;
import com.example.demo.domain.model.TmpUser;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TmpUserRepository {
	private final TmpUserMapper mapper;

	public int create(TmpUser tmpUser) {
		return mapper.insertOne(tmpUser);
	}

	public TmpUser findByToken(String token) {
		return mapper.findByToken(token);
	}
}
