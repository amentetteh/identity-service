package com.org.identity.repository;

import com.org.identity.entity.AuthenticationCode;
import com.org.identity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AuthenticationCodeRepository extends JpaRepository<AuthenticationCode, Long> {
    Optional<AuthenticationCode> findByUser(User user);
    void deleteByUser(User user);

    //@Query("SELECT tc FROM AuthenticationCode tc WHERE tc.user = :user AND tc.expirationTime > :currentTime")
    Optional<AuthenticationCode> findTopByUserAndExpirationTimeAfterOrderByExpirationTimeDesc(User user, LocalDateTime currentTime);
}

