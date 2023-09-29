package com.book.heejinbook.controller;

import com.book.heejinbook.dto.book.response.BookListResponse;
import com.book.heejinbook.dto.review.request.RegisterReviewRequest;
import com.book.heejinbook.dto.review.response.DetailReviewResponse;
import com.book.heejinbook.dto.review.response.ReviewListResponse;
import com.book.heejinbook.dto.review.response.ReviewSwiperResponse;
import com.book.heejinbook.dto.vo.PaginationResponse;
import com.book.heejinbook.dto.vo.Response;
import com.book.heejinbook.security.Auth;
import com.book.heejinbook.service.ReviewService;
import com.book.heejinbook.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Auth
    @PostMapping("/{book_id}")
    public Response<Void> registerReview(@PathVariable("book_id") Long bookId,
                                         @RequestBody RegisterReviewRequest registerReviewRequest) {
        reviewService.registerReview(bookId, registerReviewRequest);
        return ApiUtils.success(HttpStatus.CREATED, "리뷰 등록 완료", null);
    }

    @GetMapping("/swiper/{book_id}")
    public Response<List<ReviewSwiperResponse>> getSwiperList(@PathVariable("book_id") Long bookId, @RequestParam Integer size) {
        return ApiUtils.success(HttpStatus.OK, "리뷰 조회 완료", reviewService.getSwiperList(bookId, size));
    }
    @GetMapping("/list/{book_id}")
    public Response<PaginationResponse<ReviewListResponse>> getList(@PathVariable("book_id") Long bookId, Pageable pageable) {
        return ApiUtils.success(HttpStatus.OK, "리뷰 조회 완료", reviewService.getList(bookId, pageable));
    }

    @Auth
    @PutMapping("/{review_id}")
    public Response<Void> changeReview(@PathVariable("review_id")Long reviewId,
                                       @RequestBody RegisterReviewRequest registerReviewRequest) {
        reviewService.changeReview(reviewId, registerReviewRequest);
        return ApiUtils.success(HttpStatus.OK, "리뷰 수정 완료", null);
    }

    @Auth
    @DeleteMapping("/{review_id}")
    public Response<Void> deleteReview(@PathVariable("review_id")Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ApiUtils.success(HttpStatus.OK, "리뷰 삭제 완료", null);
    }

    @GetMapping("/{review_id}")
    public Response<DetailReviewResponse> detailReview(@PathVariable("review_id") Long reviewId) {
        return ApiUtils.success(HttpStatus.OK, "리뷰 조회 완료", reviewService.getDetailReview(reviewId));
    }


}
