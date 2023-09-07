package com.project.bookstudy.studygroup.repository;

import com.project.bookstudy.studygroup.domain.StudyGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomStudyGroupRepository {

    StudyGroup findByIdWithLeader(Long id);
    Page<StudyGroup> searchStudyGroup(Pageable pageable);
}