package com.org.identity.repository;

import com.org.identity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByPhoneNumber(String phoneNumber);

    @Query("SELECT u FROM User u WHERE u.username = :identifier OR u.phoneNumber = :identifier")
    Optional<User> findByUsernameOrPhoneNumber(@Param("identifier") String identifier);

    @Query("SELECT u FROM User u WHERE u.username = :username OR u.phoneNumber = :phoneNumber")
    Optional<User> findByUsernameOrPhoneNumber(@Param("username") String username,@Param("phoneNumber") String phoneNumber);

}