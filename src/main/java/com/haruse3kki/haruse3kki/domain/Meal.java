package com.haruse3kki.haruse3kki.domain;

import com.haruse3kki.haruse3kki.enums.MealType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@NoArgsConstructor
@Getter
@Table(name="meal")
public class Meal {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mealId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private MealType mealType;

    private String photoUrl;

    private String content;

    private LocalTime eatenAt;

    private LocalDate date;

    private LocalDateTime createdAt=LocalDateTime.now();

    @Builder
    public Meal(User user, MealType mealType, String photoUrl, String content, LocalTime eatenAt, LocalDate date) {
        this.user = user;
        this.mealType = mealType;
        this.photoUrl = photoUrl;
        this.content = content;
        this.eatenAt = eatenAt;
        this.date = date;
    }

    public void update(MealType mealType, String photoUrl, String content, LocalTime eatenAt, LocalDate date) {
        this.mealType = mealType;
        this.photoUrl = photoUrl;
        this.content = content;
        this.eatenAt = eatenAt;
        this.date = date;
    }
}
