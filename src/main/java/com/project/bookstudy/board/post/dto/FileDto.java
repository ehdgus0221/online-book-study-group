package com.project.bookstudy.board.post.dto;

import com.project.bookstudy.board.post.domain.File;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FileDto {
    private Long id;
    private String path;

    public static FileDto fromEntity(File file) {
        return new FileDto(file.getId(), file.getFilePath());
    }
}
