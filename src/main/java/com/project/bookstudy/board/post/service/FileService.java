package com.project.bookstudy.board.post.service;

import com.project.bookstudy.board.post.domain.File;
import com.project.bookstudy.board.post.domain.Post;
import com.project.bookstudy.board.post.dto.FileDto;
import com.project.bookstudy.board.post.dto.request.CreateFileRequest;
import com.project.bookstudy.board.post.dto.request.DeleteFilesRequest;
import com.project.bookstudy.board.post.repository.FileRepository;
import com.project.bookstudy.board.post.repository.PostRepository;
import com.project.bookstudy.common.dto.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileService {

    private final FileRepository fileRepository;
    private final PostRepository postRepository;

    public void upload(Long PostId, List<CreateFileRequest> requests) {
        Post post = postRepository.findById(PostId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.POST_NOT_FOUND.getDescription()));

        List<File> files = requests.stream()
                .map(f -> File.of(f.getFilePath(), post))
                .collect(Collectors.toList());

        fileRepository.saveAll(files);
    }

    public void delete(DeleteFilesRequest request) {
        fileRepository.deleteAllByIdInBatch(request.getFileIds());
    }

    public List<FileDto> getFilesByPostId(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.POST_NOT_FOUND.getDescription()));
        List<FileDto> fileDtoList = fileRepository.findAllByPost(post)
                .stream()
                .map(FileDto::fromEntity)
                .collect(Collectors.toList());
        return fileDtoList;
    }
}
