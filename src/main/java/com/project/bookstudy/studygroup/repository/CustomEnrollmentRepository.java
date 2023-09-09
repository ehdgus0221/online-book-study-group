package com.project.bookstudy.studygroup.repository;


import com.project.bookstudy.studygroup.domain.Enrollment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CustomEnrollmentRepository {

    public List<Enrollment> findByStudyGroupIdWithMemberAndPayment(Long id);

    public Optional<Enrollment> findByIdWithAll(Long id);

    public Page<Enrollment> searchEnrollment(Pageable pageable, Long memberId);
}

