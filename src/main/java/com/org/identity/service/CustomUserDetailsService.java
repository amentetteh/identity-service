package com.org.identity.service;

import com.org.identity.entity.User;
import com.org.identity.model.CustomUserDetails;
import com.org.identity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(identifier)
                .orElse(null);

        if (user == null) {
            user = userRepository.findByPhoneNumber(identifier)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        }
        return new CustomUserDetails(user);
    }
}
