package com.project.bookstudy.board.post.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UploadFilesRequest {
    private Long postId;
    private List<CreateFileRequest> path;
}
