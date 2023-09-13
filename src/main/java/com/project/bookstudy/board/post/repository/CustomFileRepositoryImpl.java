package com.project.bookstudy.board.post.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomFileRepositoryImpl implements CustomFileRepository {

    private final JPAQueryFactory jpaQueryFactory;

}
