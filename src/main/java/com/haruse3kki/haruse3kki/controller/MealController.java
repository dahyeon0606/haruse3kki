package com.haruse3kki.haruse3kki.controller;

import com.haruse3kki.haruse3kki.domain.User;
import com.haruse3kki.haruse3kki.dto.MealDTO;
import com.haruse3kki.haruse3kki.service.MealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class MealController {
    private final MealService mealService;

    @PostMapping(value = "/meal/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> upload(
            @AuthenticationPrincipal User user,
            @RequestPart MealDTO.UploadMealRequest request,
            @RequestPart(required = false) MultipartFile image) {
        mealService.uploadMeal(user.getUserId(), request, image);
        return ResponseEntity.ok("식사를 기록했습니다.");
    }

    @GetMapping("/meals")
    public ResponseEntity<MealDTO.DailyResponse> view(@AuthenticationPrincipal User user,
                                                      @RequestParam Long coupleId,
                                                      @RequestParam LocalDate date) {
        MealDTO.DailyResponse response = mealService.viewMeals(user.getUserId(), coupleId,date);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
