package com.book.heejinbook.error.domain;

import com.book.heejinbook.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BookErrorCode implements ErrorCode {

    NOT_FOUND_BOOK(HttpStatus.NOT_FOUND.value(), "책을 찾을 수 없습니다");


    private final int code;
    private final String message;


}
