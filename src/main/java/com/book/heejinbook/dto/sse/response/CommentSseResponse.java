package com.book.heejinbook.dto.sse.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentSseResponse {

    private String nickname;
    private Long reviewId;
    private String thumbnail;
    private String commentTime;
}
