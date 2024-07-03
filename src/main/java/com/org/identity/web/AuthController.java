package com.org.identity.web;

import com.org.identity.dto.AuthRequest;
import com.org.identity.dto.AuthResponse;
import com.org.identity.dto.UserAuthRequestDTO;
import com.org.identity.dto.UserAuthCodeDTO;
import com.org.identity.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private AuthService authService;

    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint() {
        return ResponseEntity.ok("Authenticated Endpoint Reached");
    }

    @PostMapping("/send-code")
    public ResponseEntity<String> sendAuthenticationCode(@RequestBody UserAuthRequestDTO userAuthRequestDTO) {
        log.info("sendAuthenticationCode entered");
        authService.sendAuthenticationCode(userAuthRequestDTO.getEmailOrPhone());
        log.info("----------------------");
        return ResponseEntity.ok("Code sended successfully");
    }

    @PostMapping("/verify-code")
    public ResponseEntity<String>verifyCode(@RequestBody UserAuthCodeDTO userAuthCodeDTO){
        authService.verifyAuthenticationCode(userAuthCodeDTO);
        return ResponseEntity.ok("Code verified successfully");
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        log.info("authenticate entered");
        var authRequestDTO = authService.authenticateByPassword(authRequest.getUsername(),authRequest.getPassword());
        return ResponseEntity.ok(authRequestDTO);
    }

}