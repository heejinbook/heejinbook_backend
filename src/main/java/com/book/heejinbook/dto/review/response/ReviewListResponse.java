package com.book.heejinbook.dto.review.response;

import com.book.heejinbook.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewListResponse {

    private Long reviewId;
    private String reviewAuthor;
    private String reviewAuthorProfileUrl;
    private String reviewTitle;
    private String reviewContents;
    private String reviewCreatedAt;
    private String reviewPhrase;
    private Integer reviewRating;
    private Long likeCount;
    private Long commentCount;
    private Boolean isLike;

    public ReviewListResponse(Long reviewId, String reviewAuthor, String reviewAuthorProfileUrl, String reviewTitle, String reviewContents, Instant reviewCreatedAt, String reviewPhrase,  Long likeCount, Long commentCount, Boolean isLike, Integer reviewRating) {
        this.reviewId = reviewId;
        this.reviewAuthor = reviewAuthor;
        this.reviewAuthorProfileUrl = reviewAuthorProfileUrl;
        this.reviewTitle = reviewTitle;
        this.reviewContents = reviewContents;
        this.reviewPhrase = reviewPhrase;
        this.reviewCreatedAt = DateUtils.convertToString(reviewCreatedAt);
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.isLike = isLike;
        this.reviewRating = reviewRating;
    }
}