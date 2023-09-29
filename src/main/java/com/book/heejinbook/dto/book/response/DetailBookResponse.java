package com.book.heejinbook.dto.book.response;

import com.book.heejinbook.entity.Book;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailBookResponse {

    private Long bookId;
    private String title;
    private String author;
    private String thumbnail;
    private String description;
    private Long reviewCount;

    public static DetailBookResponse from(Book book, Long reviewCount) {
        return DetailBookResponse.builder()
                .bookId(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .thumbnail(book.getThumbnailUrl())
                .description(book.getDescription())
                .reviewCount(reviewCount)
                .build();
    }

}
