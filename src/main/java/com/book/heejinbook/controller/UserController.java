package com.book.heejinbook.controller;

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

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")
@Api(tags = "유저 API")
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "회원 가입 API", description = "폼데이터로 요청")
    public Response<Void> signup(@ModelAttribute SignupRequest signupRequest) {

        String email = userService.signup(signupRequest);

        return ApiUtils.success(HttpStatus.CREATED, email + " 회원 가입 성공", null);
    }

    @PostMapping("/login")
    @Operation(summary = "로그인 API", description = "")
    public Response<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ApiUtils.success(HttpStatus.OK, "로그인 성공", userService.login(loginRequest));
    }

    @Auth
    @GetMapping("/my-info")
    @Operation(summary = "내 정보 조회 API", description = "토큰 체크 용")
    public Response<MyInfoResponse> getMyInfo() {
        return ApiUtils.success(HttpStatus.OK, "내 정보 조회 성공", userService.getMyInfo());
    }
}
