package com.book.heejinbook.error.domain;

import com.book.heejinbook.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum LibraryErrorCode implements ErrorCode {

    NOT_FOUND_BOOK(HttpStatus.BAD_REQUEST.value(), "내 서재에서 해당 책을 찾을 수 없습니다.");


    private final int code;
    private final String message;


}