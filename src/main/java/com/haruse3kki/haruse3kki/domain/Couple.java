package com.haruse3kki.haruse3kki.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name="couple")
public class Couple {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long coupleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user1_id")
    private User user1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user2_id")
    private User user2;
    private LocalDateTime createdAt=LocalDateTime.now();

    @Builder
    public Couple(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
    }
}
