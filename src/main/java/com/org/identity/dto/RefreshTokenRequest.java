package com.org.identity.dto;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String refreshToken;
}