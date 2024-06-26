package com.org.identity.service;

import com.org.identity.dto.UserAuthRequestDTO;
import com.org.identity.dto.UserAuthCodeDTO;
import com.org.identity.entity.TemporaryAuthenticationCode;
import com.org.identity.exception.UserNotFoundException;
import com.org.identity.entity.User;
import com.org.identity.repository.TemporaryAuthenticationCodeRepository;
import com.org.identity.repository.UserRepository;

import com.org.identity.util.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TemporaryAuthenticationCodeRepository temporaryAuthenticationCodeRepository;

    @Autowired
    private SmsService smsService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CodeUtil codeUtil;

    public void sendAuthenticationCode(String identifier) {
        User user = userRepository.findByEmailOrPhoneNumber(identifier)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String code = codeUtil.generateCode();
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(10); // Set expiration time

        TemporaryAuthenticationCode temporaryAuthenticationCode = new TemporaryAuthenticationCode();
        temporaryAuthenticationCode.setCode(code);
        temporaryAuthenticationCode.setExpirationTime(expirationTime);
        temporaryAuthenticationCode.setUser(user);
        temporaryAuthenticationCodeRepository.save(temporaryAuthenticationCode);

        if (identifier.equals(user.getEmail())) {
            emailService.sendCode(user.getEmail(), code);
        } else {
            smsService.sendCode(user.getPhoneNumber(), code);
        }
    }

    public String authenticate(UserAuthCodeDTO userAuthCodeDTO) {
        Optional<User> userOpt = userRepository.findByEmailOrPhoneNumber(userAuthCodeDTO.getEmailOrPhone(), userAuthCodeDTO.getEmailOrPhone());
        User user = userOpt.orElseThrow(() -> new UserNotFoundException("User not found"));

        if (user.getAuthCode().equals(userAuthCodeDTO.getAuthCode())) {
            // Clear the auth code after successful authentication
            user.setAuthCode(null);
            userRepository.save(user);

            // Generate and return JWT token
            // (Token generation code would go here, similar to the previous JwtUtil example)
            return "jwt-token";
        } else {
            throw new UserNotFoundException("Invalid code");
        }
    }
}