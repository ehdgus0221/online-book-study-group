package com.project.bookstudy.board.post.repository;

import com.project.bookstudy.board.post.domain.File;
import com.project.bookstudy.board.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long>, CustomFileRepository {

    List<File> findAllByPost(Post post);

    @Transactional
    @Modifying
    @Query("delete from File f where f.post in :post")
    void deleteAllByPostIn(@Param("post") List<Post> post);

}
