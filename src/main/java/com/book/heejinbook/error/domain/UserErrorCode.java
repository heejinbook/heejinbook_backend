package com.book.heejinbook.error.domain;

import com.book.heejinbook.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements ErrorCode {
    DUPLICATE_USER_ID(HttpStatus.CONFLICT.value(), "중복 되는 아이디 입니다."),
    NOT_MATCH_PASSWORD_CONFIRM(HttpStatus.BAD_REQUEST.value(), "비밀번호와 비밀번호 확인이 일치하지 않습니다"),
    ;

    private final int code;
    private final String message;
}
