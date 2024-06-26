package com.org.identity.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
    public void sendEmail(String to, String subject, String body) {
        // Simulate sending an email (replace with actual email sending code)
        System.out.println("Sending email to " + to + ": " + subject + " - " + body);
    }
}
