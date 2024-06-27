package com.org.identity.service;

import com.org.identity.entity.User;
import com.org.identity.model.CustomUserDetails;
import com.org.identity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageSource messageSource;

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(identifier)
                .orElse(null);

        if (user == null) {
            user = userRepository.findByPhoneNumber(identifier)
                    .orElseThrow(() -> new UsernameNotFoundException(messageSource.getMessage("user.not.found", new Object[]{identifier}, LocaleContextHolder.getLocale())));
        }
        return new CustomUserDetails(user,identifier);
    }
}
