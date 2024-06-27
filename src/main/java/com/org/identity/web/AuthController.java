package com.org.identity.web;

import com.org.identity.dto.UserAuthRequestDTO;
import com.org.identity.dto.UserAuthCodeDTO;
import com.org.identity.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/send-code")
    public ResponseEntity<Void> sendAuthCode(@RequestBody UserAuthRequestDTO userAuthRequestDTO) {
        authService.sendAuthenticationCode(userAuthRequestDTO.getEmailOrPhone());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody UserAuthCodeDTO userAuthCodeDTO) {
        String token = authService.authenticate(userAuthCodeDTO);
        return ResponseEntity.ok(token);
    }
}