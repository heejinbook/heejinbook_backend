package com.book.heejinbook.controller;

import com.book.heejinbook.dto.comment.request.RegisterCommentRequest;
import com.book.heejinbook.dto.comment.response.CommentListResponse;
import com.book.heejinbook.dto.vo.Response;
import com.book.heejinbook.security.Auth;
import com.book.heejinbook.service.CommentService;
import com.book.heejinbook.utils.ApiUtils;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
@Api(tags = "댓글 API")
public class CommentController {

    private final CommentService commentService;

    @Auth
    @PostMapping("/{review_id}")
    @Operation(summary = "댓글 작성")
    public Response<Void> registerComment(@PathVariable("review_id") Long reviewId, @RequestBody RegisterCommentRequest request) {

        commentService.registerComment(reviewId, request);

        return ApiUtils.success(HttpStatus.CREATED, "댓글 작성 성공", null);
    }

    @GetMapping("/{review_id}")
    @Operation(summary = "댓글 리스트 조회")
    public Response<List<CommentListResponse>> getList(@PathVariable("review_id") Long reviewId) {
        return ApiUtils.success(HttpStatus.OK, "댓글 리스트 조회 성공", commentService.getListByReview(reviewId));
    }

    @Auth
    @PutMapping("/{comment_id}")
    @Operation(summary = "댓글 수정")
    public Response<Void> changeComment(@PathVariable("comment_id") Long commentId, @RequestBody RegisterCommentRequest request) {
        commentService.changeComment(commentId, request);
        return ApiUtils.success(HttpStatus.CREATED, "댓글 수정 성공", null);
    }

    @Auth
    @DeleteMapping("/{comment_id}")
    @Operation(summary = "댓글 삭제")
    public Response<Void> deleteComment(@PathVariable("comment_id") Long commentId) {
        commentService.deleteComment(commentId);
        return ApiUtils.success(HttpStatus.CREATED, "댓글 수정 성공", null);
    }
}
