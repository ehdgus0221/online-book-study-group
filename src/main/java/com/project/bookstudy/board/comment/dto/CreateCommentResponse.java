package com.project.bookstudy.board.comment.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateCommentResponse {

    private Long commentId;

    @Builder
    private CreateCommentResponse(Long commentId) {
        this.commentId = commentId;
    }
}
