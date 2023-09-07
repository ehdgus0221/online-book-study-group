package com.project.bookstudy.studygroup.repository;

import com.project.bookstudy.studygroup.domain.StudyGroup;
import com.project.bookstudy.studygroup.dto.request.StudyGroupSearchCond;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CustomStudyGroupRepository {

    Optional<StudyGroup> findByIdWithLeader(Long id);
    Page<StudyGroup> searchStudyGroup(Pageable pageable, StudyGroupSearchCond cond);
    Optional<StudyGroup> findByIdWithEnrollments(Long id);

}