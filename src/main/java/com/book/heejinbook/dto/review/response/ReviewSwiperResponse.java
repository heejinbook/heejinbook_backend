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
    private Boolean isLike;

    public ReviewSwiperResponse(Long reviewId, Long bookId, String reviewAuthor, String reviewTitle, String reviewPhrase, String reviewAuthorProfileUrl, String reviewContents, Instant reviewCreatedAt, Long likeCount, Boolean isLike) {
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
    }

    public static ReviewSwiperResponse from(Review review, Boolean isLike) {
        return ReviewSwiperResponse.builder()
                .reviewId(review.getId())
                .reviewAuthor(review.getUser().getNickname())
                .reviewAuthorProfileUrl(review.getUser().getProfileUrl())
                .reviewTitle(review.getTitle())
                .reviewContents(review.getContents())
                .reviewPhrase(review.getPhrase())
                .reviewCreatedAt(DateUtils.convertToString(review.getCreatedAt()))
                .bookId(review.getBook().getId())
                .likeCount((long)review.getLikeCount())
                .isLike(isLike)
                .build();
    }

}
