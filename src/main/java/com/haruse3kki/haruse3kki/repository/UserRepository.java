package com.haruse3kki.haruse3kki.repository;

import com.haruse3kki.haruse3kki.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByProviderId(String providerId);
}
