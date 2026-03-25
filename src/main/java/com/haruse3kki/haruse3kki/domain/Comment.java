package com.haruse3kki.haruse3kki.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name="comment")
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne
    @JoinColumn(name="meal_id")
    private Meal meal;

    @ManyToOne
    @JoinColumn(name="couple_id")
    private Couple couple;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    private String content;

    private LocalDateTime createdAt=LocalDateTime.now();

    @Builder
    public Comment(Meal meal, Couple couple, User user, String content) {
        this.meal = meal;
        this.couple = couple;
        this.user = user;
        this.content = content;
    }
}
