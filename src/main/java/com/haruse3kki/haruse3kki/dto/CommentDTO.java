package com.haruse3kki.haruse3kki.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

public class CommentDTO {
    @Getter
    public static class UploadCommentRequest {
        private String comment;
    }
    @Getter
    @AllArgsConstructor
    public static class ViewCommentResponse{
        private Long commentId;
        private String nickname;
        private String comment;
        private LocalDateTime createdAt;
    }
}
