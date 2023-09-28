package com.book.heejinbook.controller;

import com.book.heejinbook.dto.vo.Response;
import com.book.heejinbook.utils.ApiUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health-check")
public class HealthCheckController {

    @GetMapping("")
    public Response<Void> checkHealth() {
        return ApiUtils.success(HttpStatus.OK, "서버 살아 있습니다", null);
    }

}
