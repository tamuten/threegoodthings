package com.example.demo.domain.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.domain.model.PasswordReissueInfo;
import com.example.demo.util.LocalDateTimeParser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;
    @Value("${app.applicationBaseUrl}")
    String baseUrl;
    private static final String DATETIME_FORMAT_SLASH = "yyyy/MM/dd HH:mm:ss";

    public SimpleMailMessage sendCertificationMail(final String mailAddress, final String token,
            final LocalDateTime expiryDate) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(baseUrl);
        uriBuilder.pathSegment("signup")
            .pathSegment("certificate")
            .queryParam("token", token);
        final String signupCompleteUrl = uriBuilder.build()
            .encode()
            .toUriString();
        final String subject = "[Three good things] メール認証キーのお知らせ";
        final String content = mailAddress + " 様\n"
                + "\n"
                + "Three good things（スリー・グッド・シングス）にご登録いただき、ありがとうございます。"
                + "本メールは、ご登録いただいたメールアドレスが正しいかどうか確認するための認証用メールです。\n"
                + "\n"
                + "メール認証を完了するために、下記リンクからThree good thingsにログインしてください。\n"
                + "\n"
                + signupCompleteUrl
                + "\n\n"
                + "このURLの有効期限は、 "
                + LocalDateTimeParser.toStr(expiryDate, DATETIME_FORMAT_SLASH)
                + " です。"
                + "メール認証は、上記のリンクにてログインするまで完了しません。"
                + "このメールに心当たりのない方は、お手数をおかけしますがこのメールは破棄してください。";

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mailAddress);
        mailMessage.setSubject(subject);
        mailMessage.setText(content);

        mailSender.send(mailMessage);
        log.info("CertificationMail has been Sent to " + mailAddress);

        return mailMessage;
    }

    public SimpleMailMessage sendPasswordReissueInfo(final PasswordReissueInfo info) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(baseUrl);
        uriBuilder.pathSegment("reissue")
            .pathSegment("resetpassword")
            .queryParam("form")
            .queryParam("token", info.getToken());
        final String passwordReissueUrl = uriBuilder.build()
            .encode()
            .toUriString();

        final String subject = "[Three good things] パスワード再設定URLの送付";
        final String content = info.getMailAddress() + " 様\n"
                + "\n"
                + "Three good things（スリー・グッド・シングス）をいつもご利用いただき、ありがとうございます。"
                + "本メールは、ご入力いただいたメールアドレスが正しいかどうか確認するための認証用メールです。\n"
                + "\n"
                + "下記リンクからパスワードの再設定を完了してください。\n"
                + "\n"
                + passwordReissueUrl
                + "\n\n"
                + "このURLの有効期限は、 "
                + LocalDateTimeParser.toStr(info.getExpiryDate(), DATETIME_FORMAT_SLASH)
                + " です。";

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(info.getMailAddress());
        mailMessage.setSubject(subject);
        mailMessage.setText(content);

        mailSender.send(mailMessage);
        log.info("PasswordReissueMail has been Sent to " + info.getMailAddress());

        return mailMessage;
    }
}
