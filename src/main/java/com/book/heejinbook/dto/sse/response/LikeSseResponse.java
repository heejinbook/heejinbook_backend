package com.book.heejinbook.dto.sse.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeSseResponse {

    private String nickname;
    private Long reviewId;
    private String thumbnail;
    private String likeDate;

}
