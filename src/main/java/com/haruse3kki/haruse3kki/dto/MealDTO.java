package com.haruse3kki.haruse3kki.dto;

import com.haruse3kki.haruse3kki.enums.MealType;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

public class MealDTO {
    @Getter
    public static class uploadMealRequest{
        private MealType mealType;
        private String content;
        private String photoUrl;
        private LocalTime eatenAt;
        private LocalDate date;
    }
}
