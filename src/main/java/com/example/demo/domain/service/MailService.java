package com.example.demo.domain.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailService {
	private final JavaMailSender mailSender;

	// TODO: メール送信実装
	public void sendCertificationMail(final String mailAddress, final String uuid) {
		final String subject = "[Three good things] メール認証キーのお知らせ";
		final String content = mailAddress + " 様\n"
				+ "\n"
				+ "Three good things（スリー・グッド・シングス）にご登録いただき、ありがとうございます。"
				+ "本メールは、ご登録いただいたメールアドレスが正しいかどうか確認するための認証用メールです。\n"
				+ "\n"
				+ "メール認証を完了するために、下記リンクからThree good thingsにログインしてください。\n"
				+ "\n"
				+ "";

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo("aaa");
		mailMessage.setSubject(subject);
		mailMessage.setText(content);

		mailSender.send(mailMessage);
		log.info("CertificationMail has been Sent to " + mailAddress);
	}
}
