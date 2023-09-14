package com.project.bookstudy.board.comment.service;

import com.project.bookstudy.board.comment.domain.param.CreateCommentParam;
import com.project.bookstudy.board.comment.domain.Comment;
import com.project.bookstudy.board.comment.dto.CommentDto;
import com.project.bookstudy.board.comment.repository.CommentRepository;
import com.project.bookstudy.board.post.domain.Post;
import com.project.bookstudy.board.post.repository.PostRepository;
import com.project.bookstudy.common.dto.ErrorCode;
import com.project.bookstudy.member.domain.Member;
import com.project.bookstudy.member.repository.MemberRepository;
import com.project.bookstudy.studygroup.domain.param.UpdateCommentParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Null;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    // 최상단 댓글은 parentId에 0 넣기?
    @Transactional
    public CommentDto createComment(Long postId, Long memberId, Long parentId, CreateCommentParam commentParam) {

         Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.POST_NOT_FOUND.getDescription()));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.USER_NOT_FOUND.getDescription()));

        Comment comment = commentRepository.findById(parentId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.PARENT_NOT_FOUND.getDescription()));

        Comment savedComment = commentRepository.save(Comment.from(post, member, comment, commentParam));
        CommentDto commentDto = CommentDto.fromEntity(savedComment);
        return commentDto;

    }

    @Transactional
    public void updateComment(UpdateCommentParam updateParam) {
        Comment comment = commentRepository.findById(updateParam.getId())
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.COMMENT_NOT_FOUND.getDescription()));

        comment.update(updateParam);
    }


    /*public Page<CommentDto> getCommentList(Pageable pageable, Long postId) {

        Page<Comment> comments = commentRepository.findByPostId(pageable, postId);
        Page<CommentDto> commentDtoList = comments
                .map(comment -> CommentDto.fromEntity(comment));

        return commentDtoList;
    }*/

    public List<CommentDto> getRootOrChildCommentList(@Nullable Long parentId) {
        List<Comment> rootOrChildComments = commentRepository.findRootOrChildByParentId(parentId);
        List<CommentDto> commentDtoList = rootOrChildComments
                .stream()
                .map(CommentDto::fromEntity)
                .collect(Collectors.toList());

        return commentDtoList;
    }

    @Transactional
    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.COMMENT_NOT_FOUND.getDescription()));
        if (comment.getIsDeleted()) {
            throw new IllegalArgumentException(ErrorCode.COMMENT_DELETE_FAIL.getDescription());
        }
        deleteChildCommentAndParentComment(comment);
    }

    private void deleteChildCommentAndParentComment(Comment comment) {

        if (comment == null) {
            return;
        }

        List<Comment> childComments = commentRepository.findRootOrChildByParentId(comment.getId());
        for (Comment childComment : childComments) {
            deleteChildCommentAndParentComment(childComment);
        }

        commentRepository.delete(comment);
    }


}
