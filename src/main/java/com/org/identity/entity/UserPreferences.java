package com.org.identity.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserPreferences {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean emailNotifications;
    private boolean smsNotifications;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}