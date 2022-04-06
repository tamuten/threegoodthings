package com.example.demo.domain.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.demo.domain.model.TmpUser;
import com.example.demo.domain.repository.TmpUserDao;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TmpUserService {
	private final TmpUserDao tmpUserDao;

	public TmpUser create(TmpUser tmpUser) {
		return tmpUserDao.saveAndFlush(tmpUser);
	}
}
