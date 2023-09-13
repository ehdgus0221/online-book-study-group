package com.project.bookstudy.board.category.service;

import com.project.bookstudy.board.post.domain.Post;
import com.project.bookstudy.board.post.repository.FileRepository;
import com.project.bookstudy.board.post.repository.PostRepository;
import com.project.bookstudy.board.category.domain.Category;
import com.project.bookstudy.board.category.dto.CategoryDto;
import com.project.bookstudy.board.category.dto.CreateCategoryRequest;
import com.project.bookstudy.board.category.dto.UpdateCategoryRequest;
import com.project.bookstudy.board.category.repository.CategoryRepository;
import com.project.bookstudy.common.dto.ErrorCode;
import com.project.bookstudy.studygroup.domain.StudyGroup;
import com.project.bookstudy.studygroup.repository.StudyGroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final StudyGroupRepository studyGroupRepository;
    private final PostRepository postRepository;
    private final FileRepository fileRepository;

    @Transactional
    public Long createCategory(CreateCategoryRequest request) {
        StudyGroup studyGroup = studyGroupRepository.findById(request.getStudyGroupId())
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.STUDY_GROUP_NOT_FOUND.getDescription()));

        Long parentId = request.getParentCategoryId();
        Category parentCategory = parentId != null ?
                categoryRepository.findById(parentId)
                        .orElseThrow(() -> new IllegalArgumentException(ErrorCode.CATEGORY_NOT_FOUND.getDescription()))
                : null;

        Category category = Category.from(parentCategory, studyGroup, request.getSubject());
        categoryRepository.save(category);

        return category.getId();
    }

    //null 입력시 root Category List 반환
    public List<CategoryDto> getRootOrChildCategoryList(@Nullable Long parentId) {
        List<Category> rootOrChildCategories = categoryRepository.findRootOrChildByParentId(parentId);
        List<CategoryDto> categoryDtoList = rootOrChildCategories
                .stream()
                .map(CategoryDto::fromEntity)
                .collect(Collectors.toList());

        return categoryDtoList;
    }

    @Transactional
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.CATEGORY_NOT_FOUND.getDescription()));

        deleteChildCategoryAndPost(category);
    }

    private void deleteChildCategoryAndPost(Category category) {

        if (category == null) return;

        List<Category> childCategories = categoryRepository.findRootOrChildByParentId(category.getId());
        for (Category childCategory : childCategories) {
            deleteChildCategoryAndPost(childCategory);
        }

        List<Post> postList = postRepository.findPostsByCategory(category);
        fileRepository.deleteAllByPostIn(postList);
        postRepository.softDeleteAllByCategory(category);

        //카테고리 삭제
        categoryRepository.delete(category);
    }

    @Transactional
    public void updateCategory(UpdateCategoryRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.CATEGORY_NOT_FOUND.getDescription()));

        category.update(request.getSubject(), toUpdateParentCategory(request));
    }

    private Category toUpdateParentCategory(UpdateCategoryRequest request) {
        if (request.getParentCategoryId() == null) return null;

        Category parentCategory = categoryRepository.findById(request.getParentCategoryId())
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.CATEGORY_NOT_FOUND.getDescription()));

        return parentCategory;
    }
}
