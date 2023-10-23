package com.book.heejinbook.error.domain;

import com.book.heejinbook.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BookErrorCode implements ErrorCode {

    NOT_FOUND_BOOK(HttpStatus.NOT_FOUND.value(), "책을 찾을 수 없습니다"),
    INVALID_DATE(HttpStatus.BAD_REQUEST.value(), "날짜 형식이 이상합니다"),
    INVALID_SORT_TYPE(HttpStatus.BAD_REQUEST.value(), "정렬값을 다시 확인해주세요")
    ;


    private final int code;
    private final String message;


}
