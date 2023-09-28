package com.book.heejinbook.error.domain;

import com.book.heejinbook.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UtilErrorCode implements ErrorCode {

    NOT_FOUND_FILE(HttpStatus.INTERNAL_SERVER_ERROR.value(), "파일이 존재하지 않습니다."),
    WRONG_PHONENUMBER(HttpStatus.BAD_REQUEST.value(), "잘못된 전화번호 형식입니다.");;


    private final int code;
    private final String message;


}
