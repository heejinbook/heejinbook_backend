package com.book.heejinbook.controller;

import com.book.heejinbook.dto.vo.Response;
import com.book.heejinbook.security.Auth;
import com.book.heejinbook.service.LikeService;
import com.book.heejinbook.utils.ApiUtils;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
@Api(tags = "좋아요 API")
public class LikeController {

    private final LikeService likeService;

    @Auth
    @PostMapping("/{review_id}")
    @Operation(summary = "좋아요 및 취소")
    public Response<Void> postLike(@PathVariable(name = "review_id") Long reviewId) {
        return ApiUtils.success(HttpStatus.CREATED, "좋아요 성공", likeService.doLike(reviewId));
    }


}
