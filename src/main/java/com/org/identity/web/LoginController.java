package com.org.identity.web;

import java.security.Principal;

import com.org.identity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;



import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
//@CrossOrigin(origins = "*")
@AllArgsConstructor
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    @GetMapping("/user")
    public String getUser() {
        return new String("Welcome User");
    }

    @GetMapping("/admin")
    public String getAdmin() {
        return new String("Welcome Admin");
    }

    @GetMapping("/")
    public String getGithub(Principal user) {
        return user.toString();
    }

}
