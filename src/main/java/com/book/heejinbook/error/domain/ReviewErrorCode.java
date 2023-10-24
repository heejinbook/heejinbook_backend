package com.book.heejinbook.error.domain;

import com.book.heejinbook.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ReviewErrorCode implements ErrorCode {

    NOT_FOUND_REVIEW(HttpStatus.NOT_FOUND.value(), "리뷰를 찾을 수 없습니다"),
    FORBIDDEN_REVIEW(HttpStatus.FORBIDDEN.value(), "내가 작성한 리뷰만 삭제및 수정이 가능합니다"),
    ALREADY_WRITTEN_REVIEW(HttpStatus.CONFLICT.value(), "이미 리뷰를 작성한 회원입니다"),
    BAD_REQUEST_RATING_VALUE(HttpStatus.BAD_REQUEST.value(), "별점은 1점부터 5점까지만 가능합니다")
    ;
    


    private final int code;
    private final String message;


}
