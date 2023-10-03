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
public class ReviewSwiperResponse {

    private Long reviewId;
    private String reviewAuthor;
    private String reviewTitle;
    private String reviewAuthorProfileUrl;
    private String reviewContents;
    private String reviewCreatedAt;

    public static ReviewSwiperResponse from(Review review) {
        return ReviewSwiperResponse.builder()
                .reviewId(review.getId())
                .reviewAuthor(review.getUser().getNickname())
                .reviewAuthorProfileUrl(review.getUser().getProfileUrl())
                .reviewTitle(review.getTitle())
                .reviewContents(review.getContents())
                .reviewCreatedAt(DateUtils.convertToString(review.getCreatedAt()))
                .build();
    }

}
