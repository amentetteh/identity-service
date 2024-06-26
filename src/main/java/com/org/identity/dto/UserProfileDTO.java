package com.org.identity.dto;

import lombok.Data;

@Data
public class UserProfileDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private String dateOfBirth;
    private Long userId;
}
