package com.org.identity.service;

import com.org.identity.dto.UserAuthCodeDTO;
import com.org.identity.entity.AuthenticationCode;
import com.org.identity.exception.UserNotFoundException;
import com.org.identity.entity.User;
import com.org.identity.model.CustomUserDetails;
import com.org.identity.repository.AuthenticationCodeRepository;
import com.org.identity.repository.UserRepository;

import com.org.identity.util.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationCodeRepository authenticationCodeRepository;

    @Autowired
    private SmsService smsService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CodeUtil codeUtil;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private JwtService jwtService;

    public void sendAuthenticationCode(String identifier) {
        User user = userRepository.findByEmailOrPhoneNumber(identifier)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String code = codeUtil.generateCode();
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(10); // Set expiration time

        AuthenticationCode temporaryAuthenticationCode = new AuthenticationCode();
        temporaryAuthenticationCode.setCode(code);
        temporaryAuthenticationCode.setExpirationTime(expirationTime);
        temporaryAuthenticationCode.setUser(user);
        authenticationCodeRepository.save(temporaryAuthenticationCode);

        if (identifier.equals(user.getEmail())) {
            emailService.sendCode(user.getEmail(), code);
        } else {
            smsService.sendCode(user.getPhoneNumber(), code);
        }
    }

    public String authenticate(UserAuthCodeDTO userAuthCodeDTO) {
        User user = userRepository.findByEmailOrPhoneNumber(userAuthCodeDTO.getEmailOrPhone())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        AuthenticationCode authenticationCode = authenticationCodeRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Code not found"));

        if (authenticationCode.getCode().equals(userAuthCodeDTO.getAuthCode()) && authenticationCode.getExpirationTime().isAfter(LocalDateTime.now())) {
            authenticationCodeRepository.delete(authenticationCode);

            return jwtService.generateToken(new CustomUserDetails(user,userAuthCodeDTO.getEmailOrPhone()));
        } else {
            throw new UserNotFoundException(messageSource.getMessage("invalid.code", null, LocaleContextHolder.getLocale()));
        }
    }
}