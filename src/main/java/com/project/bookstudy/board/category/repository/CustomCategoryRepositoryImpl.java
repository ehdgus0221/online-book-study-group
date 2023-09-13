package com.project.bookstudy.board.category.repository;

import com.project.bookstudy.board.category.domain.Category;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.project.bookstudy.board.category.domain.QCategory.category;


@RequiredArgsConstructor
public class CustomCategoryRepositoryImpl implements CustomCategoryRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Category> findRootOrChildByParentId(Long id) {

        List<Category> childCategoryList = jpaQueryFactory.selectFrom(category)
                .where(getRootOrChild(id))
                .fetch();

        return childCategoryList;
    }

    private BooleanExpression getRootOrChild(Long id) {
        return id != null ? category.parentCategory.id.eq(id) : null;
    }
}
