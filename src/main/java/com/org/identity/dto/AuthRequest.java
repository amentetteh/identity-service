package com.org.identity.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}