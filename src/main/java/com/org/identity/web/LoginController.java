package com.org.identity.web;

import java.security.Principal;

import com.org.identity.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/login")
@AllArgsConstructor
public class LoginController {

 //   private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
 //   private final PasswordEncoder passwordEncoder;



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
