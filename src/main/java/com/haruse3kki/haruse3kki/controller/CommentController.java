package com.haruse3kki.haruse3kki.controller;

import com.haruse3kki.haruse3kki.domain.User;
import com.haruse3kki.haruse3kki.dto.CommentDTO;
import com.haruse3kki.haruse3kki.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Comment", description = "댓글 API")
@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @Operation(summary = "댓글 작성", description = "특정 식사에 댓글을 작성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "댓글 작성 완료"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 (내용 누락 등)"),
            @ApiResponse(responseCode = "403", description = "해당 커플의 식사가 아님")
    })
    @PostMapping("/meals/{mealId}/comments")
    public ResponseEntity<String> uploadComment(
            @AuthenticationPrincipal User user,
            @Parameter(description = "식사 ID", required = true) @PathVariable Long mealId,
            @Parameter(description = "커플 ID", required = true) @RequestParam Long coupleId,
            @Valid @RequestBody CommentDTO.UploadCommentRequest request) {
        commentService.uploadComment(user.getUserId(), coupleId, mealId, request);
        return ResponseEntity.ok().body("댓글 작성 완료");
    }

    @Operation(summary = "댓글 목록 조회", description = "특정 식사의 댓글 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "403", description = "해당 커플의 식사가 아님")
    })
    @GetMapping("/meals/{mealId}/comments")
    public ResponseEntity<List<CommentDTO.ViewCommentResponse>> viewComments(
            @AuthenticationPrincipal User user,
            @Parameter(description = "식사 ID", required = true) @PathVariable Long mealId,
            @Parameter(description = "커플 ID", required = true) @RequestParam Long coupleId) {
        List<CommentDTO.ViewCommentResponse> responses = commentService.viewComment(user.getUserId(), coupleId, mealId);
        return ResponseEntity.ok().body(responses);
    }

    @Operation(summary = "댓글 삭제", description = "자신이 작성한 댓글을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "댓글 삭제 완료"),
            @ApiResponse(responseCode = "403", description = "본인 댓글이 아님"),
            @ApiResponse(responseCode = "404", description = "댓글을 찾을 수 없음")
    })
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<String> deleteComment(
            @AuthenticationPrincipal User user,
            @Parameter(description = "댓글 ID", required = true) @PathVariable Long commentId) {
        commentService.deleteComment(user.getUserId(), commentId);
        return ResponseEntity.ok().body("댓글 삭제 완료");
    }
}
