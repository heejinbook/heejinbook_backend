package com.book.heejinbook.controller;

import com.book.heejinbook.dto.user.request.SignupRequest;
import com.book.heejinbook.dto.vo.Response;
import com.book.heejinbook.service.UserService;
import com.book.heejinbook.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user/")
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response<Void> signup(@ModelAttribute SignupRequest signupRequest) {

        String email =  userService.signup(signupRequest);

        return ApiUtils.success(HttpStatus.CREATED, email+" 회원 가입 성공", null);
    }

}
