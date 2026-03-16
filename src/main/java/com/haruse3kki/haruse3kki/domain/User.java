package com.haruse3kki.haruse3kki.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user")
public class User implements UserDetails {

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

    // UserDetails 구현 메서드들
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return providerId;
    }
}
