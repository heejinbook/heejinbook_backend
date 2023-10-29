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
public class MyReviewResponse {

    private Long reviewId;
    private Long bookId;
    private String bookThumbnail;
    private String bookTitle;
    private String bookAuthor;
    private String reviewAuthor;
    private String reviewTitle;
    private String reviewPhrase;
    private String reviewAuthorProfileUrl;
    private String reviewContents;
    private String reviewCreatedAt;
    private Integer reviewRating;
    private Long likeCount;
    private Long commentCount;

    public MyReviewResponse(Long reviewId, Long bookId, String bookThumbnail, String bookTitle, String bookAuthor, String reviewAuthor, String reviewTitle, String reviewPhrase, String reviewAuthorProfileUrl, String reviewContents, Instant reviewCreatedAt, Integer reviewRating, Long likeCount, Long commentCount) {
        this.reviewId = reviewId;
        this.bookId = bookId;
        this.bookThumbnail = bookThumbnail;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.reviewAuthor = reviewAuthor;
        this.reviewTitle = reviewTitle;
        this.reviewPhrase = reviewPhrase;
        this.reviewAuthorProfileUrl = reviewAuthorProfileUrl;
        this.reviewContents = reviewContents;
        this.reviewCreatedAt = DateUtils.convertToString(reviewCreatedAt);
        this.reviewRating = reviewRating;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
    }

}
