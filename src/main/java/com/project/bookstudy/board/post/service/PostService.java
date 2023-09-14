package com.project.bookstudy.board.post.service;

import com.project.bookstudy.board.post.dto.PostDto;
import com.project.bookstudy.board.post.dto.request.CreatePostRequest;
import com.project.bookstudy.board.post.dto.request.PostSearchCond;
import com.project.bookstudy.board.post.dto.request.UpdatePostRequest;
import com.project.bookstudy.board.category.domain.Category;
import com.project.bookstudy.board.post.domain.Post;
import com.project.bookstudy.board.category.repository.CategoryRepository;
import com.project.bookstudy.board.post.repository.PostRepository;
import com.project.bookstudy.common.dto.ErrorCode;
import com.project.bookstudy.member.domain.Member;
import com.project.bookstudy.member.repository.MemberRepository;
import com.project.bookstudy.studygroup.domain.StudyGroup;
import com.project.bookstudy.studygroup.repository.StudyGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final CategoryRepository categoryRepository;
    private final StudyGroupRepository studyGroupRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @Transactional
    public Long createPost(CreatePostRequest request) {
        StudyGroup studyGroup = studyGroupRepository.findById(request.getStudyGroupId())
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.STUDY_GROUP_NOT_FOUND.getDescription()));
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.CATEGORY_NOT_FOUND.getDescription()));
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.USER_NOT_FOUND.getDescription()));

        Post post = Post.of(request.getContents(), request.getSubject(), studyGroup, member, category);

        Post savePost = postRepository.save(post);
        return savePost.getId();
    }

    @Transactional
    public void updatePost(UpdatePostRequest request) {
        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.POST_NOT_FOUND.getDescription()));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.CATEGORY_NOT_FOUND.getDescription()));

        post.update(category, request.getSubject(), request.getContents());
    }

    @Transactional  //미완성
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.POST_NOT_FOUND.getDescription()));
        //댓글 및 파일까지 다 삭제 되어야 한다. dfs 사용해야하는듯. 양방향 설정해서 cascade로 삭제해도 되지만, batch로 삭제하자

        postRepository.delete(post);    //cascade test
    }

    public PostDto getPost(Long postId) {
        Post post = postRepository.findByIdWithAll(postId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.POST_NOT_FOUND.getDescription()));

        PostDto postDto = PostDto.fromEntity(post);
        return postDto;
    }

    public Page<PostDto> getPostList(Pageable pageable, PostSearchCond cond) {
        Page<PostDto> postDtos = postRepository.searchPost(pageable, cond)
                .map(PostDto::fromEntity);
        return postDtos;
    }

}
