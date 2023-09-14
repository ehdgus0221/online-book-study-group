package com.project.bookstudy.board.post.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CreatePostRequest {

    private String subject;
    private String contents;
    private Long categoryId;
    private Long memberId;
    private Long studyGroupId;

    private List<CreateFileRequest> files;
}
