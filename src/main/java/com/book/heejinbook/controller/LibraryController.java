package com.book.heejinbook.controller;

import com.book.heejinbook.dto.vo.Response;
import com.book.heejinbook.security.Auth;
import com.book.heejinbook.service.LibraryService;
import com.book.heejinbook.utils.ApiUtils;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "서재 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/library")
public class LibraryController {

    private final LibraryService libraryService;

    @PostMapping("{book_id}")
    @Auth
    @Operation(summary = "내 서재에 담기, 이미 담은 거 누르면 취소")
    public Response<Void> intoMyLibrary(@PathVariable("book_id") Long bookId) {

        libraryService.postLibrary(bookId);

        return ApiUtils.success(HttpStatus.CREATED, "내 서재에 담기 및 취소 성공", null);
    }

}
