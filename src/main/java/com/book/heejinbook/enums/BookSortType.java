package com.book.heejinbook.enums;

import com.book.heejinbook.error.CustomException;
import com.book.heejinbook.error.domain.BookErrorCode;

public enum BookSortType {
    COUNT_REVIEW,   // 리뷰 많은 수
    CREATED_AT;  // 최신 순

    public static BookSortType fromString(String value) {
        try {
            if (value == null || "".equals(value)) value = "CREATED_AT";
            return BookSortType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomException(BookErrorCode.INVALID_SORT_TYPE);
        }
    }

}
