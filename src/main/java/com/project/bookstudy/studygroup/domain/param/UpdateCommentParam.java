package com.project.bookstudy.studygroup.domain.param;

import lombok.Builder;
import lombok.Getter;


@Getter
public class UpdateCommentParam {

    private Long id;
    private String content;

    @Builder
    private UpdateCommentParam(Long id, String content) {
        this.id = id;
        this.content = content;
    }

}
