package com.haruse3kki.haruse3kki.controller;

import com.haruse3kki.haruse3kki.domain.User;
import com.haruse3kki.haruse3kki.dto.MealDTO;
import com.haruse3kki.haruse3kki.service.MealService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Tag(name = "Meal", description = "식사 기록 API")
@RestController
@RequiredArgsConstructor
public class MealController {
    private final MealService mealService;

    @Operation(summary = "식사 기록 등록", description = "식사 정보와 선택적 이미지를 업로드합니다. multipart/form-data로 전송합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "식사 기록 완료"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "403", description = "본인 또는 커플의 식사만 등록 가능")
    })
    @PostMapping(value = "/meal/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> upload(
            @AuthenticationPrincipal User user,
            @Parameter(description = "식사 정보 (JSON)", required = true, content = @Content(mediaType = "application/json"))
            @RequestPart MealDTO.UploadMealRequest request,
            @Parameter(description = "식사 사진 (선택)")
            @RequestPart(required = false) MultipartFile image) {
        mealService.uploadMeal(user.getUserId(), request, image);
        return ResponseEntity.ok("식사를 기록했습니다.");
    }

    @Operation(summary = "하루 식사 조회", description = "커플의 특정 날짜 식사 기록(아침/점심/저녁/디저트)을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "403", description = "본인 커플의 식사만 조회 가능")
    })
    @GetMapping("/meals")
    public ResponseEntity<MealDTO.DailyResponse> view(
            @AuthenticationPrincipal User user,
            @Parameter(description = "커플 ID", required = true) @RequestParam Long coupleId,
            @Parameter(description = "조회 날짜 (yyyy-MM-dd)", required = true, example = "2026-06-23") @RequestParam LocalDate date) {
        MealDTO.DailyResponse response = mealService.viewMeals(user.getUserId(), coupleId, date);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "식사 기록 수정", description = "등록한 식사 기록의 내용 또는 사진을 수정합니다. multipart/form-data로 전송합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "식사 수정 완료"),
            @ApiResponse(responseCode = "403", description = "본인 식사만 수정 가능"),
            @ApiResponse(responseCode = "404", description = "식사 기록을 찾을 수 없음")
    })
    @PatchMapping(value = "/meal/{mealId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateMeal(
            @AuthenticationPrincipal User user,
            @Parameter(description = "식사 ID", required = true) @PathVariable Long mealId,
            @Parameter(description = "수정할 식사 정보 (JSON)", required = true, content = @Content(mediaType = "application/json"))
            @RequestPart MealDTO.UpdateMealRequest request,
            @Parameter(description = "수정할 사진 (선택)")
            @RequestPart(required = false) MultipartFile image) {
        mealService.updateMeal(user.getUserId(), request, image, mealId);
        return ResponseEntity.status(HttpStatus.OK).body("식사를 수정했습니다.");
    }

    @Operation(summary = "식사 기록 삭제", description = "등록한 식사 기록을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "식사 삭제 완료"),
            @ApiResponse(responseCode = "403", description = "본인 식사만 삭제 가능"),
            @ApiResponse(responseCode = "404", description = "식사 기록을 찾을 수 없음")
    })
    @DeleteMapping("/meal/{mealId}")
    public ResponseEntity<String> deleteMeal(
            @AuthenticationPrincipal User user,
            @Parameter(description = "식사 ID", required = true) @PathVariable Long mealId) {
        mealService.deleteMeal(user.getUserId(), mealId);
        return ResponseEntity.status(HttpStatus.OK).body("식사를 삭제했습니다.");
    }
}
