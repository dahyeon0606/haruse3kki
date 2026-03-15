package com.haruse3kki.haruse3kki.repository;

import com.haruse3kki.haruse3kki.domain.Couple;
import com.haruse3kki.haruse3kki.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CoupleRepository extends JpaRepository<Couple, Long> {
    List<Couple> findByUser1OrUser2(User user1, User user2);
}
