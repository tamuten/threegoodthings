package com.example.demo.domain.service;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Message;
import com.example.demo.domain.model.TmpUser;
import com.example.demo.domain.model.User;
import com.example.demo.domain.repository.TmpUserRepository;
import com.example.demo.domain.repository.UsersRepository;
import com.example.demo.exception.BusinessException;
import com.example.demo.exception.SystemException;
import com.example.demo.form.SignupForm;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SignupService {
    private final UsersRepository userRepository;
    private final TmpUserRepository tmpUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final MessageService messageService;

    @Value("${security.tokenLifeTimeSeconds}")
    int tokenLifeTimeSeconds;

    public TmpUser findTmpUserByToken(String token) {
        TmpUser tmpUser = tmpUserRepository.findByToken(token);
        if (tmpUser == null) {
            throw new SystemException("メールアドレス認証エラー：トークンに一致するレコードが見つかりません。");
        }
        if (LocalDateTime.now()
            .isAfter(tmpUser.getExpiryDate())) {
            throw new BusinessException(messageService.getMessage(Message.E001_TOKEN_EXPIRED));
        }
        return tmpUser;
    }

    public void createTmpUserAndSendMail(SignupForm form) {
        // ユーザーを一時テーブルに登録する
        LocalDateTime expiryDate = LocalDateTime.now()
            .plusSeconds(tokenLifeTimeSeconds);
        String token = UUID.randomUUID()
            .toString();
        TmpUser tmpUser = new TmpUser();
        tmpUser.setMailAddress(form.getMailAddress());
        tmpUser.setPassword(passwordEncoder.encode(form.getPassword()));
        tmpUser.setToken(token);
        tmpUser.setExpiryDate(expiryDate);
        tmpUserRepository.create(tmpUser);

        // 確認メールの送信
        mailService.sendCertificationMail(tmpUser.getMailAddress(), tmpUser.getToken(), tmpUser.getExpiryDate());
    }

    public User createUser(String mailAddress, String token) {
        if (userRepository.isExist(mailAddress)) {
            throw new BusinessException(messageService.getMessage(Message.E003_IS_ALREADY_EXIST));
        }
        return userRepository.createUser(mailAddress, token);
    }
}
