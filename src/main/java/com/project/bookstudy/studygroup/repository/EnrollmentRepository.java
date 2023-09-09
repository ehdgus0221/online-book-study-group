package com.project.bookstudy.studygroup.repository;

import com.project.bookstudy.studygroup.domain.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long>, CustomEnrollmentRepository {
}
