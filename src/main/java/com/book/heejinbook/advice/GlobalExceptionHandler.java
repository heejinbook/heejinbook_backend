package com.book.heejinbook.advice;

import com.book.heejinbook.dto.vo.Response;
import com.book.heejinbook.error.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleException(Exception e) {

        // 에러 코드 설정
        Response errorResponse = new Response(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), e.getCause());

        log.error("\u001B[31mcode: "+HttpStatus.INTERNAL_SERVER_ERROR.value()+"\u001B[0m");
        log.error("\u001B[31mmessage: "+ e.getMessage()+"\u001B[0m");

        // 에러 응답 생성
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(errorResponse);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Response> handleCustomException(CustomException e) {
        // 에러 정보를 담은 ErrorResponse 객체 생성
        Response errorResponse = new Response(false, e.getErrorCode().getCode(), e.getErrorMessage(), null);
        log.error("\u001B[31mcode: "+e.getErrorCode().getCode()+"\u001B[0m");
        log.error("\u001B[31mmessage: "+ e.getErrorMessage()+"\u001B[0m");

        // 에러 응답 생성
        return ResponseEntity.status(e.getErrorCode().getCode()).body(errorResponse);
    }
}
