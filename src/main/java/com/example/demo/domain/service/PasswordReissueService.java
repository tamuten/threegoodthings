package com.example.demo.domain.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.demo.domain.model.PasswordReissueInfo;
import com.example.demo.domain.repository.PasswordReissueRepository;
import com.example.demo.exception.SystemException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PasswordReissueService {
	private final PasswordReissueRepository repository;

	public void create(PasswordReissueInfo info) {
		repository.create(info);
	}

	public PasswordReissueInfo findByToken(String token) {
		PasswordReissueInfo info = repository.findByToken(token);
		if (info == null) {
			throw new SystemException("パスワード再発行エラー：トークンに一致する行が見つかりません。");
		}
		return info;
	}
}
