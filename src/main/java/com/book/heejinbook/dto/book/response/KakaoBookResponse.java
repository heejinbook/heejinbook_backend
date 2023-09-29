package com.book.heejinbook.dto.book.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KakaoBookResponse {
    private Meta meta;
    private List<Document> documents;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Meta {
        private boolean isEnd;
        private int pageableCount;
        private int totalCount;

        // Getters and setters
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Document {
        private List<String> authors;
        private String contents;
        private String datetime;
        private String isbn;
        private int price;
        private String publisher;
        private int salePrice;
        private String status;
        private String thumbnail;
        private String title;
        private List<String> translators;
        private String url;
    }
}