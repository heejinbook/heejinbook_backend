package com.book.heejinbook.controller;

import com.book.heejinbook.dto.comment.response.CommentListResponse;
import com.book.heejinbook.dto.review.response.MyReviewResponse;
import com.book.heejinbook.dto.review.response.ReviewSwiperResponse;
import com.book.heejinbook.dto.user.request.KakaoLoginRequest;
import com.book.heejinbook.dto.user.request.LoginRequest;
import com.book.heejinbook.dto.user.request.SignupRequest;
import com.book.heejinbook.dto.user.response.LoginResponse;
import com.book.heejinbook.dto.user.response.MyInfoResponse;
import com.book.heejinbook.dto.vo.Response;
import com.book.heejinbook.security.Auth;
import com.book.heejinbook.service.UserService;
import com.book.heejinbook.utils.ApiUtils;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")
@Api(tags = "유저 API")
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "회원 가입 API", description = "폼데이터로 요청")
    public Response<LoginResponse> signup(@ModelAttribute SignupRequest signupRequest) {

        return ApiUtils.success(HttpStatus.CREATED, "회원 가입 성공", userService.signup(signupRequest));
    }

    @PostMapping("/login")
    @Operation(summary = "로그인 API", description = "")
    public Response<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ApiUtils.success(HttpStatus.OK, "로그인 성공", userService.login(loginRequest));
    }

    @PostMapping("/kakao/login")
    public Response<LoginResponse> kakaoLogin(@RequestBody KakaoLoginRequest kakaoLoginRequest) {
        return ApiUtils.success(HttpStatus.OK, "로그인 성공", userService.kakaoLogin(kakaoLoginRequest));
    }

    @Auth
    @GetMapping("/my-info")
    @Operation(summary = "내 정보 조회 API", description = "토큰 체크 용")
    public Response<MyInfoResponse> getMyInfo() {
        return ApiUtils.success(HttpStatus.OK, "내 정보 조회 성공", userService.getMyInfo());
    }

    @Auth
    @GetMapping("/my-info/reviews")
    @Operation(summary = "내가 작성한 리뷰 조회 API")
    public Response<List<MyReviewResponse>> getMyReview() {
        return ApiUtils.success(HttpStatus.OK, "내 정보 조회 성공", userService.getMyReviews());
    }

    @Auth
    @GetMapping("/my-info/comments")
    @Operation(summary = "내가 작성한 댓글 조회 API")
    public Response<List<CommentListResponse>> getMyComment() {
        return ApiUtils.success(HttpStatus.OK, "내 정보 조회 성공", userService.getMyComments());
    }

    @Auth
    @PatchMapping("/nickname")
    @Operation(summary = "내 닉네임 변경")
    public Response<Void> changeMyNickname(@RequestParam String nickname) {
        return ApiUtils.success(HttpStatus.OK, "닉네임 변경 성공", userService.changeMyNickname(nickname));
    }
}
