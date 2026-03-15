package com.haruse3kki.haruse3kki.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

public class CoupleDTO {
    @Getter
    @AllArgsConstructor
    public static class GetCoupleResponse{
        private Long coupleId;
        private String partnerName;
        private LocalDateTime createdAt;
    }
}
