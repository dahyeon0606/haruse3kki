package com.haruse3kki.haruse3kki.controller;

import com.haruse3kki.haruse3kki.domain.User;
import com.haruse3kki.haruse3kki.dto.MealDTO;
import com.haruse3kki.haruse3kki.service.MealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MealController {
    private final MealService mealService;

    @PostMapping("/meal/upload")
    public ResponseEntity<String> upload(@AuthenticationPrincipal User user, @RequestBody MealDTO.uploadMealRequest request) {
        mealService.uploadMeal(user.getUserId(), request);
        return ResponseEntity.ok("식사를 기록했습니다.");
    }
}
