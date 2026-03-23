package com.haruse3kki.haruse3kki.dto;

import com.haruse3kki.haruse3kki.enums.MealType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class MealDTO {
    @Getter
    public static class UploadMealRequest {
        private MealType mealType;
        private String content;
        private LocalTime eatenAt;
        private LocalDate date;
    }

    @Getter
    @AllArgsConstructor
    public static class MealInfo {
        private Long mealId;
        private boolean recorded;
        private String content;
        private String photoUrl;
        private LocalTime eatenAt;
    }
    @Getter
    @AllArgsConstructor
    public static class DailyMealView{
        private MealType mealType;
        private MealInfo mine;
        private MealInfo partner;
    }
    @Getter
    @AllArgsConstructor
    public static class DailyResponse{
        private LocalDate date;
        private List<DailyMealView> meals;
    }

    @Getter
    public static class ViewMealRequest {
        private Long coupleId;
        private LocalDate date;
    }
}
