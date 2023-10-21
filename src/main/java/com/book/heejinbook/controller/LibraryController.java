package com.book.heejinbook.controller;

import com.book.heejinbook.dto.library.response.MyLibraryListResponse;
import com.book.heejinbook.dto.vo.Response;
import com.book.heejinbook.security.Auth;
import com.book.heejinbook.service.LibraryService;
import com.book.heejinbook.utils.ApiUtils;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "서재 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/library")
public class LibraryController {

    private final LibraryService libraryService;

    @Auth
    @PostMapping("{book_id}")
    @Operation(summary = "내 서재에 담기, 이미 담은 거 누르면 취소")
    public Response<Void> intoMyLibrary(@PathVariable("book_id") Long bookId) {

        libraryService.postLibrary(bookId);

        return ApiUtils.success(HttpStatus.CREATED, "내 서재에 담기 및 취소 성공", null);
    }

    @Auth
    @DeleteMapping("{book_id}")
    @Operation(summary = "서재에 담은 책 취소")
    public Response<Void> deleteMyLibrary(@PathVariable("book_id") Long bookId) {

        libraryService.deleteLibrary(bookId);

        return ApiUtils.success(HttpStatus.OK, "내 서재 취소 성공", null);
    }

    @Auth
    @GetMapping("")
    @Operation(summary = "내 서재 리스트")
    public Response<List<MyLibraryListResponse>> getMyLibrary() {

        return ApiUtils.success(HttpStatus.OK, "내 서재 리스트 조회 성공", libraryService.getMyLibraryList());
    }




}
