package com.book.heejinbook.dto.user.response;

import com.book.heejinbook.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyInfoResponse {

    private String email;
    private String nickname;
    private String profileUrl;
    private Instant createdAt;

    public static MyInfoResponse from(User user) {
        return MyInfoResponse.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .profileUrl(user.getProfileUrl())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
