package com.org.identity.service;

public interface CodeSenderService {
    void sendCode(String recipient, String code);
}
