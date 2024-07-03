package com.org.identity.dto;

import lombok.Data;

@Data
public class SmsAuthRequest {
    private String phoneNumber;
    private String code;
}
