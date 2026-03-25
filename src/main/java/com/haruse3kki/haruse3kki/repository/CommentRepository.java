package com.haruse3kki.haruse3kki.repository;

import com.haruse3kki.haruse3kki.domain.Comment;
import com.haruse3kki.haruse3kki.domain.Couple;
import com.haruse3kki.haruse3kki.domain.Meal;
import com.haruse3kki.haruse3kki.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByMealAndCouple(Meal meal, Couple couple);
}
