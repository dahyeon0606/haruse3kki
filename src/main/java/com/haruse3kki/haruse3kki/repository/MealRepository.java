package com.haruse3kki.haruse3kki.repository;

import com.haruse3kki.haruse3kki.domain.Meal;
import com.haruse3kki.haruse3kki.domain.User;
import com.haruse3kki.haruse3kki.enums.MealType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface MealRepository extends JpaRepository<Meal, Long> {
    Optional<Meal> findByMealTypeAndDateAndUser(MealType mealType, LocalDate date, User user);
}
