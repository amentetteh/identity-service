package com.org.identity.repository;

import com.org.identity.entity.TemporaryAuthenticationCode;
import com.org.identity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TemporaryAuthenticationCodeRepository extends JpaRepository<TemporaryAuthenticationCode, Long> {
    Optional<TemporaryAuthenticationCode> findByUser(User user);
    void deleteByUser(User user);
}

