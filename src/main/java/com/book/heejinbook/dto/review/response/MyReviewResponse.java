package com.book.heejinbook.dto.review.response;

import com.book.heejinbook.entity.Review;
import com.book.heejinbook.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    public static MyReviewResponse from(Review review) {
        return MyReviewResponse.builder()
                .reviewId(review.getId())
                .reviewAuthor(review.getUser().getNickname())
                .reviewAuthorProfileUrl(review.getUser().getProfileUrl())
                .reviewTitle(review.getTitle())
                .reviewContents(review.getContents())
                .reviewPhrase(review.getPhrase())
                .reviewCreatedAt(DateUtils.convertToString(review.getCreatedAt()))
                .bookId(review.getBook().getId())
                .bookTitle(review.getBook().getTitle())
                .bookThumbnail(review.getBook().getThumbnailUrl())
                .bookAuthor(review.getBook().getAuthor())
                .reviewRating(review.getRating())
                .build();
    }

}
