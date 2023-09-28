package com.book.heejinbook.dto.user.response;

import com.book.heejinbook.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {

    private String email;
    private String nickname;
    private String accessToken;
    private String profileUrl;

    public static LoginResponse from(User user, String accessToken) {
        return LoginResponse.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .profileUrl(user.getProfileUrl())
                .accessToken(accessToken)
                .build();
    }

}
