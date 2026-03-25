package com.haruse3kki.haruse3kki.controller;

import com.haruse3kki.haruse3kki.domain.User;
import com.haruse3kki.haruse3kki.dto.CommentDTO;
import com.haruse3kki.haruse3kki.service.CommentService;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/meals/{mealId}/comments")
    public ResponseEntity<String> uploadComment(@AuthenticationPrincipal User user,
                                                @PathVariable Long mealId,
                                                @RequestParam Long coupleId,
                                                @Valid @RequestBody CommentDTO.UploadCommentRequest request) {
        commentService.uploadComment(user.getUserId(), coupleId, mealId, request);
        return ResponseEntity.ok().body("댓글 작성 완료");
    }

    @GetMapping("/meals/{mealId}/comments")
    public ResponseEntity<List<CommentDTO.ViewCommentResponse>> viewComments(@AuthenticationPrincipal User user,
                                                                             @PathVariable Long mealId,
                                                                             @RequestParam Long coupleId){
        List<CommentDTO.ViewCommentResponse> responses = commentService.viewComment(user.getUserId(), coupleId, mealId);
        return ResponseEntity.ok().body(responses);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@AuthenticationPrincipal User user,
                                                @PathVariable Long commentId){
        commentService.deleteComment(user.getUserId(), commentId);
        return ResponseEntity.ok().body("댓글 삭제 완료");
    }

}
