package com.project.bookstudy.board.category.repository;

import com.project.bookstudy.board.category.domain.Category;

import java.util.List;

public interface CustomCategoryRepository {

    List<Category> findRootOrChildByParentId(Long id);

}
