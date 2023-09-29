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
    NOT_FOUND_USER(HttpStatus.NOT_FOUND.value(), "로그인 한 유저를 찾을 수 없습니다"),
    BAD_REQUEST_PASSWORD(HttpStatus.BAD_REQUEST.value(), "로그인 정보를 다시 확인하세요"),
    HANDLE_ACCESS_DENIED(HttpStatus.FORBIDDEN.value(), "로그인이 필요합니다."),
    CANT_ACCESS(HttpStatus.UNAUTHORIZED.value(), "접근권한이 없습니다"),
    FAIL_KAKAO_LOGIN(HttpStatus.BAD_REQUEST.value(), "카카오 로그인 실패!"),

    ;

    private final int code;
    private final String message;
}
