package com.haruse3kki.haruse3kki.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserDTO {

    @Getter
    @AllArgsConstructor
    @Schema(description = "토큰 응답")
    public static class TokenResponse {
        @Schema(description = "액세스 토큰", example = "eyJhbGciOiJIUzI1NiJ9...")
        private String accessToken;
        @Schema(description = "리프레시 토큰", example = "eyJhbGciOiJIUzI1NiJ9...")
        private String refreshToken;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "초대 코드 요청")
    public static class InviteCodeRequest {
        @Schema(description = "커플 초대 코드", example = "A1B2C3")
        private String code;
    }
}
