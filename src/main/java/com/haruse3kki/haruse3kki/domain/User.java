package com.haruse3kki.haruse3kki.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String nickname;

    private LocalDate birthday;

    private String provider;

    @Column(unique = true)
    private String providerId;

    private LocalDateTime createdAt;

    @Builder
    public User(String nickname, String provider, String providerId) {
        this.nickname = nickname;
        this.provider = provider;
        this.providerId = providerId;
        this.createdAt = LocalDateTime.now();
    }
}
