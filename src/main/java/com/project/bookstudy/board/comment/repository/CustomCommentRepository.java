package com.project.bookstudy.board.comment.repository;


import com.project.bookstudy.board.comment.domain.Comment;

import java.util.List;

public interface CustomCommentRepository {

    List<Comment> findRootOrChildByParentId(Long id);

}
