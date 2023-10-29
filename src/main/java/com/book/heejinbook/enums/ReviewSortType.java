package com.book.heejinbook.enums;

import com.book.heejinbook.error.CustomException;
import com.book.heejinbook.error.domain.BookErrorCode;

public enum ReviewSortType {

    COUNT_LIKE,   // 좋아요 많은 수
    CREATED_AT,
    RATING_DESC, // 별점 높은 순
    COUNT_COMMENT // 댓글 개수 순
    
    ;  // 최신 순

    public static ReviewSortType fromString(String value) {
        try {
            if (value == null || "".equals(value)) value = "CREATED_AT";
            return ReviewSortType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomException(BookErrorCode.INVALID_SORT_TYPE);
        }
    }
}
