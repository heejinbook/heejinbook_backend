package com.book.heejinbook.dto.review.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
@AllArgsConstructor
public class RegisterReviewRequest {

    private String title;
    private String contents;
    private String phrase;
    private Integer rating;

}
