package com.book.heejinbook.controller;

import com.book.heejinbook.security.Auth;
import com.book.heejinbook.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalTime;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping(value = "/subscribe/{user_id}", produces = "text/event-stream;charset=UTF-8")
    public SseEmitter subscribe(@PathVariable(value = "user_id") Long userId) {
        return notificationService.subscribe(userId);
    }

    @GetMapping(value = "/subscribe/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamSse() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(sequence -> "SSE - " + LocalTime.now().toString());
    }

}
