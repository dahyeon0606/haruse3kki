package com.haruse3kki.haruse3kki.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

public class UserDTO {
    @Getter
    @AllArgsConstructor
    public static class TokenResponse{
        private String accessToken;
        private String refreshToken;
    }
}
