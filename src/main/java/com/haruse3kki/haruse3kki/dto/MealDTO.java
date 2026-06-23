package com.haruse3kki.haruse3kki.dto;

import com.haruse3kki.haruse3kki.enums.MealType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class MealDTO {

    @Getter
    @Schema(description = "식사 등록 요청")
    public static class UploadMealRequest {
        @Schema(description = "식사 종류", example = "BREAKFAST", allowableValues = {"BREAKFAST", "LUNCH", "DINNER", "DESSERT"})
        private MealType mealType;
        @Schema(description = "식사 내용", example = "된장찌개와 밥")
        private String content;
        @Schema(description = "식사 시각", example = "08:30:00")
        private LocalTime eatenAt;
        @Schema(description = "식사 날짜", example = "2026-06-23")
        private LocalDate date;
    }

    @Getter
    @AllArgsConstructor
    @Schema(description = "식사 정보")
    public static class MealInfo {
        @Schema(description = "식사 ID", example = "1")
        private Long mealId;
        @Schema(description = "기록 여부", example = "true")
        private boolean recorded;
        @Schema(description = "식사 내용", example = "된장찌개와 밥")
        private String content;
        @Schema(description = "사진 URL", example = "https://s3.amazonaws.com/...")
        private String photoUrl;
        @Schema(description = "식사 시각", example = "08:30:00")
        private LocalTime eatenAt;
    }

    @Getter
    @AllArgsConstructor
    @Schema(description = "하루 식사 뷰 (나 + 파트너)")
    public static class DailyMealView {
        @Schema(description = "식사 종류")
        private MealType mealType;
        @Schema(description = "내 식사 정보")
        private MealInfo mine;
        @Schema(description = "파트너 식사 정보")
        private MealInfo partner;
    }

    @Getter
    @AllArgsConstructor
    @Schema(description = "하루 식사 조회 응답")
    public static class DailyResponse {
        @Schema(description = "조회 날짜", example = "2026-06-23")
        private LocalDate date;
        @Schema(description = "식사 목록 (아침/점심/저녁/디저트)")
        private List<DailyMealView> meals;
    }

    @Getter
    @Schema(description = "식사 조회 요청 파라미터")
    public static class ViewMealRequest {
        @Schema(description = "커플 ID", example = "1")
        private Long coupleId;
        @Schema(description = "조회 날짜", example = "2026-06-23")
        private LocalDate date;
    }

    @Getter
    @Schema(description = "식사 수정 요청")
    public static class UpdateMealRequest {
        @Schema(description = "식사 종류", example = "LUNCH", allowableValues = {"BREAKFAST", "LUNCH", "DINNER", "DESSERT"})
        private MealType mealType;
        @Schema(description = "식사 내용", example = "냉면")
        private String content;
        @Schema(description = "식사 시각", example = "12:00:00")
        private LocalTime eatenAt;
        @Schema(description = "식사 날짜", example = "2026-06-23")
        private LocalDate date;
    }
}
