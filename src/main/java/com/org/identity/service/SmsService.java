package com.org.identity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SmsService implements CodeSenderService{
    @Autowired
    MessageSource messageSource;
    public void sendSms(String to, String message) {
        // Simulate sending an SMS (replace with actual SMS sending code)
        System.out.println("Sending SMS to " + to + ": " + message);
    }

    @Override
    public void sendCode(String phone, String code) {
        String message = messageSource.getMessage("authentication.code.sms.body", new Object[]{code}, LocaleContextHolder.getLocale());
        sendSms(phone, message);
    }

    public boolean verifyCode(String phoneNumber, String code) {
        return false;
    }
}
