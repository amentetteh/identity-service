package com.org.identity.service;

import com.org.identity.dto.AuthResponse;
import com.org.identity.dto.UserAuthCodeDTO;
import com.org.identity.entity.AuthenticationCode;
import com.org.identity.exception.UserNotFoundException;
import com.org.identity.entity.User;
import com.org.identity.model.CustomUserDetails;
import com.org.identity.repository.AuthenticationCodeRepository;
import com.org.identity.repository.UserRepository;

import com.org.identity.util.CodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
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

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthResponse authenticateByPassword(String username, String password) {
        log.info("Info ------------ -1");
        try {
            log.info("Info ------------ 0");
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            log.info("Info ------------ 1");
            final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            log.info("Info ------------ 2");
            final String accessToken = jwtService.generateToken(userDetails);
            log.info("Info ------------ 3");
            final String refreshToken = jwtService.generateRefreshToken(userDetails);
            log.info("Info ------------ 4");
            return new AuthResponse(accessToken, refreshToken);
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid credentials", e);
        }
    }

    public AuthResponse authenticateBySms(String phoneNumber, String code) {
        if (smsService.verifyCode(phoneNumber, code)) {
            final UserDetails userDetails = userDetailsService.loadUserByPhoneNumber(phoneNumber); // Assuming phoneNumber is username
            final String accessToken = jwtService.generateToken(userDetails);
            final String refreshToken = jwtService.generateRefreshToken(userDetails);
            return new AuthResponse(accessToken, refreshToken);
        } else {
            throw new RuntimeException("Invalid SMS code");
        }
    }

    public AuthResponse refreshToken(String refreshToken) {
        if (jwtService.validateRefreshToken(refreshToken)) {
            String username = jwtService.extractUsernameFromRefreshToken(refreshToken);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            String newAccessToken = jwtService.generateToken(userDetails);
            return new AuthResponse(newAccessToken, refreshToken);
        } else {
            throw new RuntimeException("Invalid refresh token");
        }
    }

    public void sendAuthenticationCode(String identifier) {
        User user = userRepository.findByUsernameOrPhoneNumber(identifier)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String code = codeUtil.generateCode();
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(10); // Set expiration time

        AuthenticationCode temporaryAuthenticationCode = new AuthenticationCode();
        temporaryAuthenticationCode.setCode(code);
        temporaryAuthenticationCode.setExpirationTime(expirationTime);
        temporaryAuthenticationCode.setUser(user);
        authenticationCodeRepository.save(temporaryAuthenticationCode);

        if (identifier.equals(user.getUsername())) {
            emailService.sendCode(user.getUsername(), code);
        } else {
            smsService.sendCode(user.getPhoneNumber(), code);
        }
    }

    public void verifyAuthenticationCode(UserAuthCodeDTO userAuthCodeDTO) {
        System.out.println("X");
    }
}