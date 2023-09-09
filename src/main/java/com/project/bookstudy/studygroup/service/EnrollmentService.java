package com.project.bookstudy.studygroup.service;

import com.project.bookstudy.studygroup.dto.EnrollmentDto;
import com.project.bookstudy.studygroup.dto.request.CreateEnrollmentRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EnrollmentService {

    /**
     * 스터디 그룹 신청
     */
    Long enroll(CreateEnrollmentRequest request);

    /**
     * 스터디 그룹 신청취소
     */
    void cancel(Long enrollmentId);

    /**
     * 본인 스터디 그룹 신청내역 단적 조회
     */
    EnrollmentDto getEnrollment(Long enrollmentId);

    /**
     * 본인 스터디 그룹 신청내역 전체 조회
     */
    Page<EnrollmentDto> getEnrollmentList(Pageable pageable, Long memberId);
}
