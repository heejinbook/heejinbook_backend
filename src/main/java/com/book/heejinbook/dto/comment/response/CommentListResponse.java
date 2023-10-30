package com.book.heejinbook.dto.comment.response;

import com.book.heejinbook.entity.Comment;
import com.book.heejinbook.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentListResponse {

    private Long commentId;
    private Long reviewId;
    private String contents;
    private String commentAuthor;
    private String commentAuthorProfileUrl;
    private String commentCreatedAt;

    public static CommentListResponse from(Comment comment) {
        return CommentListResponse.builder()
                .reviewId(comment.getReview().getId())
                .commentId(comment.getId())
                .contents(comment.getContents())
                .commentAuthor(comment.getUser().getNickname())
                .commentAuthorProfileUrl(comment.getUser().getProfileUrl())
                .commentCreatedAt(DateUtils.convertToString(comment.getCreatedAt()))
                .build();
    }

}
