package com.haruse3kki.haruse3kki.service;

import com.haruse3kki.haruse3kki.domain.Comment;
import com.haruse3kki.haruse3kki.domain.Couple;
import com.haruse3kki.haruse3kki.domain.Meal;
import com.haruse3kki.haruse3kki.domain.User;
import com.haruse3kki.haruse3kki.dto.CommentDTO;
import com.haruse3kki.haruse3kki.exception.CustomException;
import com.haruse3kki.haruse3kki.repository.CommentRepository;
import com.haruse3kki.haruse3kki.repository.CoupleRepository;
import com.haruse3kki.haruse3kki.repository.MealRepository;
import com.haruse3kki.haruse3kki.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final CoupleRepository coupleRepository;
    private final MealRepository mealRepository;

    @Transactional
    public void uploadComment(Long userId, Long coupleId, Long mealId,CommentDTO.UploadCommentRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(404, "유저를 찾을 수 없습니다."));
        Couple couple = coupleRepository.findById(coupleId)
                .orElseThrow(() -> new CustomException(404, "커플을 찾을 수 없습니다."));
        Meal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new CustomException(404, "식사를 찾을 수 없습니다."));

        commentRepository.save(
                Comment.builder()
                .user(user)
                .couple(couple)
                .meal(meal)
                .content(request.getComment())
                .build()
        );
    }

    public List<CommentDTO.ViewCommentResponse> viewComment(Long userId, Long coupleId, Long mealId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(404, "유저를 찾을 수 없습니다."));
        Couple couple = coupleRepository.findById(coupleId)
                .orElseThrow(() -> new CustomException(404, "커플을 찾을 수 없습니다."));
        Meal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new CustomException(404, "식사를 찾을 수 없습니다."));
        List<Comment> comments = commentRepository.findByMealAndCouple(meal, couple);


        return comments.stream()
                .map(comment -> new CommentDTO.ViewCommentResponse(
                        comment.getCommentId(),
                        comment.getUser().getNickname(),
                        comment.getContent(),
                        comment.getCreatedAt()))
                .toList();

    }

    @Transactional
    public void deleteComment(Long userId, Long commentId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(404, "유저를 찾을 수 없습니다."));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(404, "댓글을 찾을 수 없습니다."));

        if (!comment.getUser().equals(user)) {
            throw new CustomException(403, "본인의 댓글만 삭제할 수 있습니다.");
        }
        commentRepository.delete(comment);

    }
}
