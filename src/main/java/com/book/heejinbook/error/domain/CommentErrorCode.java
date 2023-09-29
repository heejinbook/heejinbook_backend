package com.book.heejinbook.error.domain;

import com.book.heejinbook.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CommentErrorCode implements ErrorCode {

    NOT_FOUND_REVIEW(HttpStatus.NOT_FOUND.value(), "댓글를 찾을 수 없습니다"),
    FORBIDDEN_REVIEW(HttpStatus.FORBIDDEN.value(), "내가 작성한 댓글만 삭제및 수정이 가능합니다")
    ;

    private final int code;
    private final String message;

}
