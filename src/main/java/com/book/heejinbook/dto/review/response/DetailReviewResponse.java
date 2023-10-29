package com.book.heejinbook.dto.review.response;

import com.book.heejinbook.entity.Comment;
import com.book.heejinbook.entity.Review;
import com.book.heejinbook.utils.DateUtils;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailReviewResponse {

    private Long reviewId;
    private String reviewAuthor;
    private String reviewAuthorProfileUrl;
    private String reviewTitle;
    private String reviewContents;
    private String reviewPhrase;
    private String reviewCreatedAt;
    private Integer reviewRating;
    private Long likeCount;
    private Boolean isLike;
    private Boolean isMine;
    private Long commentCount;
    @Setter
    private List<CommentList> comments = new ArrayList<>();


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CommentList {
        private Long commentId;
        private Long reviewId;
        private String contents;
        private String commentAuthor;
        private String commentCreatedAt;
        private Boolean isMine;

        public CommentList(Long commentId, Long reviewId, String contents, String commentAuthor, Instant commentCreatedAt, Boolean isMine) {
            this.commentId = commentId;
            this.reviewId = reviewId;
            this.contents = contents;
            this.commentAuthor = commentAuthor;
            this.commentCreatedAt = DateUtils.convertToString(commentCreatedAt);
            this.isMine = isMine;
        }
    }

    public DetailReviewResponse(Long reviewId, String reviewAuthor, String reviewAuthorProfileUrl, String reviewTitle, String reviewContents, String reviewPhrase, Instant reviewCreatedAt, Integer reviewRating, Long likeCount, Boolean isLike, Boolean isMine, Long commentCount) {
        this.reviewId = reviewId;
        this.reviewAuthor = reviewAuthor;
        this.reviewAuthorProfileUrl = reviewAuthorProfileUrl;
        this.reviewTitle = reviewTitle;
        this.reviewContents = reviewContents;
        this.reviewPhrase = reviewPhrase;
        this.reviewCreatedAt = DateUtils.convertToString(reviewCreatedAt);
        this.reviewRating = reviewRating;
        this.likeCount = likeCount;
        this.isLike = isLike;
        this.isMine = isMine;
        this.commentCount = commentCount;
    }
}
