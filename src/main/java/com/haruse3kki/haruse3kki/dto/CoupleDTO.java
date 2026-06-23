package com.haruse3kki.haruse3kki.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

public class CoupleDTO {

    @Getter
    @AllArgsConstructor
    @Schema(description = "커플 조회 응답")
    public static class GetCoupleResponse {
        @Schema(description = "커플 ID", example = "1")
        private Long coupleId;
        @Schema(description = "파트너 닉네임", example = "지수")
        private String partnerName;
        @Schema(description = "커플 생성 시각", example = "2026-06-01T10:00:00")
        private LocalDateTime createdAt;
    }
}
