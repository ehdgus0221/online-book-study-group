package com.project.bookstudy.studygroup.repository;

import com.project.bookstudy.studygroup.domain.StudyGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyGroupRepository extends JpaRepository<StudyGroup, Long>, CustomStudyGroupRepository {
}