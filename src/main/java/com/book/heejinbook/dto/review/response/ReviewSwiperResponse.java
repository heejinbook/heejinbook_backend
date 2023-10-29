package com.book.heejinbook.dto.review.response;

import com.book.heejinbook.entity.Review;
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
public class ReviewSwiperResponse {

    private Long reviewId;
    private Long bookId;
    private String reviewAuthor;
    private String reviewTitle;
    private String reviewPhrase;
    private String reviewAuthorProfileUrl;
    private String reviewContents;
    private String reviewCreatedAt;
    private Long likeCount;
    private Long commentCount;
    private Boolean isLike;
    private Integer reviewRating;

    public ReviewSwiperResponse(Long reviewId, Long bookId, String reviewAuthor, String reviewTitle, String reviewPhrase, String reviewAuthorProfileUrl, String reviewContents, Instant reviewCreatedAt, Long likeCount, Long commentCount, Boolean isLike, Integer reviewRating) {
        this.reviewId = reviewId;
        this.bookId = bookId;
        this.reviewAuthor = reviewAuthor;
        this.reviewTitle = reviewTitle;
        this.reviewPhrase = reviewPhrase;
        this.reviewAuthorProfileUrl = reviewAuthorProfileUrl;
        this.reviewContents = reviewContents;
        this.reviewCreatedAt = DateUtils.convertToString(reviewCreatedAt);
        this.likeCount = likeCount;
        this.isLike = isLike;
        this.commentCount = commentCount;
        this.reviewRating = reviewRating;
    }

}
