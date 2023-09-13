package com.project.bookstudy.board.post.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateFileRequest {
    private String filePath;
    public CreateFileRequest(String filePath) {
        this.filePath = filePath;
    }

}
