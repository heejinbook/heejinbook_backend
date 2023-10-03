package com.book.heejinbook.dto.library.response;

import com.book.heejinbook.entity.Library;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyLibraryListResponse {

    private Long bookId;
    private String bookTitle;
    private String bookAuthor;
    private String bookThumbnail;

    public static MyLibraryListResponse from(Library library) {
        return MyLibraryListResponse.builder()
                .bookId(library.getBook().getId())
                .bookTitle(library.getBook().getTitle())
                .bookAuthor(library.getBook().getAuthor())
                .bookThumbnail(library.getBook().getThumbnailUrl())
                .build();
        }

}
