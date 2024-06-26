package com.org.identity.service;

import org.springframework.stereotype.Service;

@Service
public class SmsService {
    public void sendSms(String to, String message) {
        // Simulate sending an SMS (replace with actual SMS sending code)
        System.out.println("Sending SMS to " + to + ": " + message);
    }
}
