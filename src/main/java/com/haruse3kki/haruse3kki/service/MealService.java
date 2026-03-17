package com.haruse3kki.haruse3kki.service;

import com.haruse3kki.haruse3kki.domain.Meal;
import com.haruse3kki.haruse3kki.domain.User;
import com.haruse3kki.haruse3kki.dto.MealDTO;
import com.haruse3kki.haruse3kki.exception.CustomException;
import com.haruse3kki.haruse3kki.repository.MealRepository;
import com.haruse3kki.haruse3kki.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class MealService {
    private final MealRepository mealRepository;
    private final UserRepository userRepository;

    public void uploadMeal(Long userId, MealDTO.uploadMealRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(404, "유저를 찾을 수 없습니다."));

        mealRepository.findByMealTypeAndDateAndUser(request.getMealType(), request.getDate(), user)
                .ifPresent(m -> { throw new CustomException(400, "이미 기록했습니다."); });

        mealRepository.save(
                Meal.builder()
                        .user(user)
                        .content(request.getContent())
                        .mealType(request.getMealType())
                        .photoUrl(request.getPhotoUrl())
                        .eatenAt(request.getEatenAt())
                        .date(request.getDate())
                .build());
    }
}
