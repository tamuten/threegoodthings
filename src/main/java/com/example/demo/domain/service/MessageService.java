package com.example.demo.domain.service;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.example.demo.Message;

@Service
public class MessageService {
    @Autowired
    private MessageSource messageSource;

    public String getMessage(Message message) {
        return messageSource.getMessage(message.getKey(), null, Locale.JAPAN);
    }
}
