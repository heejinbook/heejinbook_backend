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
public class DetailReviewResponse {

    private Long reviewId;
    private String reviewAuthor;
    private String reviewAuthorProfileUrl;
    private String reviewTitle;
    private String reviewContents;
    private String reviewPhrase;
    private String reviewCreatedAt;
    private Long commentCount;

    public static DetailReviewResponse from(Review review, Long commentCount) {
        return DetailReviewResponse.builder()
                .reviewId(review.getId())
                .reviewAuthor(review.getUser().getNickname())
                .reviewAuthorProfileUrl(review.getUser().getProfileUrl())
                .reviewTitle(review.getTitle())
                .reviewContents(review.getContents())
                .reviewPhrase(review.getPhrase())
                .reviewCreatedAt(DateUtils.convertToString(review.getCreatedAt()))
                .commentCount(commentCount)
                .build();
    }

}
