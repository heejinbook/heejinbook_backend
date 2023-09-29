package com.book.heejinbook.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoUserInfoResponse {

    private String id;
    private KakaoUserProperties properties;
    private KakaoAccount kakao_account;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public class KakaoUserProperties {
        private String nickname;
        private String profile_image;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public class KakaoAccount {
        private String email;
    }
}

