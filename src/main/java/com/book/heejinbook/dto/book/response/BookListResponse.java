package com.book.heejinbook.dto.book.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookListResponse {

    private Long bookId;
    private String title;
    private String author;
    private String thumbnail;
    private Long reviewCount;

}
