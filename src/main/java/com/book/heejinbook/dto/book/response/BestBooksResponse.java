package com.book.heejinbook.dto.book.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BestBooksResponse {

    private Long bookId;
    private String title;
    private String author;
    private String thumbnail;
    private Long reviewCount;
    private Double avgRating;
    private String description;

}
