package com.org.identity.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
public class EmailService implements CodeSenderService{
    private static final Logger log = LoggerFactory.getLogger(EmailService.class);
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private JavaMailSender mailSender;
    public void sendEmail(String to, String subject, String body) {
        log.info("Sending email ...");
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom("no-reply@uditech.tg");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
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
