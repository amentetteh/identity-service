package com.org.identity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements CodeSenderService{
    @Autowired
    private MessageSource messageSource;
    public void sendEmail(String to, String subject, String body) {
        // Simulate sending an email (replace with actual email sending code)
        System.out.println("Sending email to " + to + ": " + subject + " - " + body);
    }
    @Override
    public void sendCode(String email, String code) {
        String subject = messageSource.getMessage("authentication.code.email.subject", null, LocaleContextHolder.getLocale());
        String body = createEmailBody(code);
        sendEmail(email, subject, body);
    }

    private String createEmailBody(String code) {
        return messageSource.getMessage("authentication.code.email.body",new Object[]{code}, LocaleContextHolder.getLocale());
    }

}
