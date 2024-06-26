package com.org.identity.dto;

import lombok.Data;

@Data
public class UserAuthRequestDTO {
    private String emailOrPhone;
}