package com.book.heejinbook.controller;

import com.book.heejinbook.dto.book.request.BookListRequest;
import com.book.heejinbook.dto.book.request.KakaoBookDataRequest;
import com.book.heejinbook.dto.book.response.BookListResponse;
import com.book.heejinbook.dto.book.response.DetailBookResponse;
import com.book.heejinbook.dto.book.response.KakaoBookResponse;
import com.book.heejinbook.dto.vo.PaginationResponse;
import com.book.heejinbook.dto.vo.Response;
import com.book.heejinbook.security.Auth;
import com.book.heejinbook.service.BookService;
import com.book.heejinbook.utils.ApiUtils;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
@Api(tags = "책 API")
public class BookController {

    private final BookService bookService;

    @PostMapping("/insert")
    public Response<KakaoBookResponse> insertBookData(@RequestBody KakaoBookDataRequest kakaoBookDataRequest) {

        return ApiUtils.success(HttpStatus.OK, "ㅇㅇ", bookService.insertData(kakaoBookDataRequest));
    }

    @Operation(summary = "책 리스트 조회")
    @GetMapping("")
    public Response<PaginationResponse<BookListResponse>> getList(BookListRequest bookListRequest,
                                                                  Pageable pageable) {
        return ApiUtils.success(HttpStatus.OK, "북 리스트 조회 성공", bookService.getList(bookListRequest, pageable));
    }

    @Auth
    @Operation(summary = "책 상세 조회")
    @GetMapping("/{book_id}")
    public Response<DetailBookResponse> getDetailBook(@PathVariable("book_id") Long bookId) {

        return ApiUtils.success(HttpStatus.OK, "북 디테일 조회 성공", bookService.getDetail(bookId));
    }


}
