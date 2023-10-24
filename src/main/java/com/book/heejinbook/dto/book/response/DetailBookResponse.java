package com.book.heejinbook.dto.book.response;

import com.book.heejinbook.entity.Book;
import com.book.heejinbook.enums.Category;
import com.book.heejinbook.utils.DateUtils;
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
    private String isbn;
    private String publisher;
    private String category;
    private String releaseDate;
    private Long reviewCount;
    private Boolean isLibrary;
    private Boolean isWriteReview;
    private Double avgRating;

    public static DetailBookResponse from(Book book, Long reviewCount, Boolean isLibrary, Boolean isWriteReview, Double avgRating) {
        return DetailBookResponse.builder()
                .bookId(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .thumbnail(book.getThumbnailUrl())
                .description(book.getDescription())
                .isbn(book.getIsbn())
                .publisher(book.getPublisher())
                .releaseDate(DateUtils.convertToStringBook(book.getReleaseDate()))
                .category(book.getCategory().getName())
                .reviewCount(reviewCount)
                .isLibrary(isLibrary)
                .isWriteReview(isWriteReview)
                .avgRating(Math.round(avgRating*10)/10.0)
                .build();
    }

}
