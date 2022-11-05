package com.example.demo.domain.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.domain.model.PasswordReissueInfo;
import com.example.demo.exception.SystemException;
import com.example.demo.util.LocalDateTimeParser;

import lombok.RequiredArgsConstructor;
import lombok.var;
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

        try {
            mailSender.send(mailMessage);
        } catch (MailException e) {
            throw new SystemException(e);
        }

        log.info("CertificationMail has been Sent to " + mailAddress);

        return mailMessage;
    }

    public SimpleMailMessage sendPasswordReissueInfo(final PasswordReissueInfo info) {
        final String subject = "[Three good things] パスワード再設定URLの送付";

        var mailMessage = new SimpleMailMessage();
        mailMessage.setTo(info.getMailAddress());
        mailMessage.setSubject(subject);
        mailMessage.setText(createContent(info));

        mailSender.send(mailMessage);
        log.info("PasswordReissueMail has been Sent to " + info.getMailAddress());

        return mailMessage;
    }

    private String createContent(PasswordReissueInfo info) {
        var text = readTextFile();
        text = text.replace("#{name}", info.getMailAddress());
        text = text.replace("#{reissueUrl}", getReissueUrl(info.getToken()));
        text = text.replace("#{expiryDate}",
                LocalDateTimeParser.toStr(info.getExpiryDate(), DATETIME_FORMAT_SLASH));

        return text;
    }

    private String readTextFile() {
        var text = "";

        try (var is = new ClassPathResource("files/passwordReissueMail.txt").getInputStream();
                var br = new BufferedReader(new InputStreamReader(is));) {
            String line;
            while ((line = br.readLine()) != null) {
                text += line;
            }
        } catch (IOException e) {
            throw new SystemException(e);
        }

        return text;
    }

    private String getReissueUrl(String token) {
        var uriBuilder = UriComponentsBuilder.fromUriString(baseUrl);
        uriBuilder.pathSegment("reissue")
            .pathSegment("resetpassword")
            .queryParam("form")
            .queryParam("token", token);
        return uriBuilder.build()
            .encode()
            .toUriString();
    }
}
