package com.org.identity.dto;

import lombok.Data;

@Data
public class UserAuthCodeDTO {
    private String emailOrPhone;
    private String authCode;
}
