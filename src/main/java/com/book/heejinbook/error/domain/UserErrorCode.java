package com.book.heejinbook.error.domain;

import com.book.heejinbook.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements ErrorCode {
    DUPLICATE_USER_ID(HttpStatus.CONFLICT.value(), "중복 되는 아이디 입니다."),
    ;

    private final int code;
    private final String message;
}
