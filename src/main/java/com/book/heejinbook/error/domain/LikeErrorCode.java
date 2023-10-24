package com.book.heejinbook.error.domain;

import com.book.heejinbook.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum LikeErrorCode implements ErrorCode {

    NOT_SUPPOSED_SELF_LIKE(HttpStatus.FORBIDDEN.value(), "내가 작성한 리뷰는 좋아요 할 수 없습니다"),
    ;


    private final int code;
    private final String message;


}
