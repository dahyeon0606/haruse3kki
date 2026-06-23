package com.haruse3kki.haruse3kki.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

public class CommentDTO {

    @Getter
    @Schema(description = "댓글 작성 요청")
    public static class UploadCommentRequest {
        @NotBlank(message = "댓글 내용을 입력해주세요.")
        @Schema(description = "댓글 내용", example = "오늘 뭐 먹었어?")
        private String comment;
    }

    @Getter
    @AllArgsConstructor
    @Schema(description = "댓글 조회 응답")
    public static class ViewCommentResponse {
        @Schema(description = "댓글 ID", example = "1")
        private Long commentId;
        @Schema(description = "작성자 닉네임", example = "다현")
        private String nickname;
        @Schema(description = "댓글 내용", example = "오늘 뭐 먹었어?")
        private String comment;
        @Schema(description = "작성 시각", example = "2026-06-23T12:00:00")
        private LocalDateTime createdAt;
    }
}
