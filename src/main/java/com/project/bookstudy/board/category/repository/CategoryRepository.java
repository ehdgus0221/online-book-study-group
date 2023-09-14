package com.project.bookstudy.board.category.repository;

import com.project.bookstudy.board.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long>, CustomCategoryRepository {
}
