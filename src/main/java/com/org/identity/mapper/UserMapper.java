package com.org.identity.mapper;

import org.springframework.stereotype.Component;

import com.org.identity.dto.UserDTO;
import com.org.identity.entity.User;

@Component
public class UserMapper {
    public UserDTO toDTO(User user) {
        return null;
    }

    public User toEntity(UserDTO userDTO) {
        return null;
    }
} 