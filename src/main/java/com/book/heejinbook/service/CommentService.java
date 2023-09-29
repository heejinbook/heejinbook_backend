package com.book.heejinbook.service;

import com.book.heejinbook.dto.comment.request.RegisterCommentRequest;
import com.book.heejinbook.dto.comment.response.CommentListResponse;
import com.book.heejinbook.entity.Book;
import com.book.heejinbook.entity.Comment;
import com.book.heejinbook.entity.Review;
import com.book.heejinbook.entity.User;
import com.book.heejinbook.error.CustomException;
import com.book.heejinbook.error.domain.BookErrorCode;
import com.book.heejinbook.error.domain.CommentErrorCode;
import com.book.heejinbook.error.domain.ReviewErrorCode;
import com.book.heejinbook.error.domain.UserErrorCode;
import com.book.heejinbook.repository.CommentRepository;
import com.book.heejinbook.repository.ReviewRepository;
import com.book.heejinbook.repository.UserRepository;
import com.book.heejinbook.security.AuthHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    public void registerComment(Long reviewId, RegisterCommentRequest request) {

        User user = validUser(AuthHolder.getUserId());
        Review review = validReview(reviewId);

        Comment comment = Comment.builder()
                .user(user)
                .review(review)
                .isDeleted(false)
                .contents(request.getContents())
                .build();
        commentRepository.save(comment);
    }

    public void changeComment(Long commentId, RegisterCommentRequest request) {

        Comment comment = validComment(commentId);
        validCommentRegisterUser(AuthHolder.getUserId(), comment.getUser().getId());

        comment.setContents(request.getContents());
        commentRepository.save(comment);

    }

    public void deleteComment(Long commentId) {
        Comment comment = validComment(commentId);
        validCommentRegisterUser(AuthHolder.getUserId(), comment.getUser().getId());

        commentRepository.updateIsDeletedById(commentId);
    }

    public List<CommentListResponse> getListByReview(Long reviewId) {
        Review review = validReview(reviewId);
        List<Comment> commentsList = commentRepository.findAllByReviewAndIsDeletedFalse(review);
        return commentsList.stream().map(CommentListResponse::from).collect(Collectors.toList());
    }

    private User validUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new CustomException(UserErrorCode.NOT_FOUND_USER));
    }

    private Review validReview(Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(() -> new CustomException(ReviewErrorCode.NOT_FOUND_REVIEW));
    }

    private Comment validComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new CustomException(CommentErrorCode.NOT_FOUND_REVIEW));
    }

    private void validCommentRegisterUser(Long userId, Long commentRegisterId) {
        if (!Objects.equals(userId, commentRegisterId)) {
            throw new CustomException(CommentErrorCode.FORBIDDEN_REVIEW);
        }
    }
}
