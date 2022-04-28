package com.example.demo.domain.service;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.domain.model.TmpUser;
import com.example.demo.domain.repository.TmpUserRepository;
import com.example.demo.form.SignupForm;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SignupService {
	private final TmpUserRepository tmpUserRepository;
	private final PasswordEncoder passwordEncoder;
	private final MailService mailService;

	@Value("${security.tokenLifeTimeSeconds}")
	int tokenLifeTimeSeconds;

	public void createTmpUserAndSendMail(SignupForm form) {
		// ユーザーを一時テーブルに登録する
		LocalDateTime expiryDate = LocalDateTime.now()
			.plusSeconds(tokenLifeTimeSeconds);
		String token = UUID.randomUUID()
			.toString();
		TmpUser tmpUser = new TmpUser();
		tmpUser.setMailAddress(form.getMailAddress());
		tmpUser.setPassword(passwordEncoder.encode(form.getPassword()));
		tmpUser.setUuid(token);
		tmpUser.setExpiryDate(expiryDate);
		tmpUserRepository.saveAndFlush(tmpUser);

		// 確認メールの送信
		mailService.sendCertificationMail(form.getMailAddress(), tmpUser.getUuid(), tmpUser.getExpiryDate());
	}
}
