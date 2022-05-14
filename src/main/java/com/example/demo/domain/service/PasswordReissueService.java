package com.example.demo.domain.service;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Message;
import com.example.demo.domain.model.PasswordReissueInfo;
import com.example.demo.domain.repository.PasswordReissueRepository;
import com.example.demo.domain.repository.UsersRepository;
import com.example.demo.exception.BusinessException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PasswordReissueService {
    private final PasswordReissueRepository passwordReissueRepository;
    private final UsersRepository usersRepository;
    private final MailService mailService;
    private final MessageService messageService;
    private final PasswordEncoder passwordEncoder;
    @Value("${security.tokenLifeTimeSeconds}")
    int tokenLifeTimeSeconds;

    public PasswordReissueInfo findByToken(final String token) {
        PasswordReissueInfo info = passwordReissueRepository.findByToken(token);
        if (info == null) {
            throw new BusinessException("トークンに一致する行が見つかりません。");
        }
        if (LocalDateTime.now()
            .isAfter(info.getExpiryDate())) {
            throw new BusinessException(messageService.getMessage(Message.E001_TOKEN_EXPIRED));
        }
        return info;
    }

    public void createAndSendReissueInfo(final String mailAddress) {
        if (!usersRepository.isExist(mailAddress)) {
            throw new BusinessException(messageService.getMessage(Message.E002_NOT_EXIST_USER));
        }
        PasswordReissueInfo info = createReissueInfo(mailAddress);
        passwordReissueRepository.create(info);
        mailService.sendPasswordReissueInfo(info);
    }

    private PasswordReissueInfo createReissueInfo(final String mailAddress) {
        LocalDateTime expiryDate = LocalDateTime.now()
            .plusSeconds(tokenLifeTimeSeconds);
        String token = UUID.randomUUID()
            .toString();
        PasswordReissueInfo info = new PasswordReissueInfo();
        info.setMailAddress(mailAddress);
        info.setToken(token);
        info.setExpiryDate(expiryDate);

        return info;
    }

    public void resetPassword(final String mailAddress, final String token, final String rawPassword) {
        passwordReissueRepository.deleteByToken(token);
        usersRepository.updatePassword(mailAddress, passwordEncoder.encode(rawPassword));
    }
}
