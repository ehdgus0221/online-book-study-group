package com.project.bookstudy.studygroup.service.impl;

import com.project.bookstudy.common.dto.ErrorCode;
import com.project.bookstudy.member.domain.Member;
import com.project.bookstudy.member.repository.MemberRepository;
import com.project.bookstudy.studygroup.domain.Enrollment;
import com.project.bookstudy.studygroup.domain.StudyGroup;
import com.project.bookstudy.studygroup.dto.EnrollmentDto;
import com.project.bookstudy.studygroup.dto.request.CreateEnrollmentRequest;
import com.project.bookstudy.studygroup.repository.EnrollmentRepository;
import com.project.bookstudy.studygroup.repository.StudyGroupRepository;
import com.project.bookstudy.studygroup.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudyGroupRepository studyGroupRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public Long enroll(CreateEnrollmentRequest request) {
        //Collection Fetch Join → Batch Size 적용 고려
        StudyGroup studyGroup = studyGroupRepository.findByIdWithEnrollments(request.getStudyGroupId())
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.STUDY_GROUP_NOT_FOUND.getDescription()));

        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.USER_NOT_FOUND.getDescription()));

        if (!studyGroup.isApplicable()) {
            throw new IllegalStateException(ErrorCode.STUDY_GROUP_FULL.getDescription());
        }

        Enrollment enrollment = Enrollment.createEnrollment(member, studyGroup);
        enrollmentRepository.save(enrollment);

        return enrollment.getId();
    }

    @Override
    @Transactional
    public void cancel(Long enrollmentId) {

        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.ENROLLMENT_NOT_FOUND.getDescription()));

        if (!enrollment.getStudyGroup().isStarted()) {
            throw new IllegalStateException(ErrorCode.ENROLLMENT_CANCEL_FAIL.getDescription());
        }
        enrollment.cancel();
    }

    @Override
    public EnrollmentDto getEnrollment(Long enrollmentId) {
        Enrollment enrollment = enrollmentRepository.findByIdWithAll(enrollmentId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.ENROLLMENT_NOT_FOUND.getDescription()));

        //스터디 정보(StudyDto), 신청일(Payment 에 있음), PaymentDto 이렇게 내려 준다.
        return EnrollmentDto.fromEntity(enrollment);
    }

    @Override
    public Page<EnrollmentDto> getEnrollmentList(Pageable pageable, Long memberId) {
        Page<Enrollment> enrollments = enrollmentRepository.searchEnrollment(pageable, memberId);
        return enrollments
                .map(EnrollmentDto::fromEntity);
    }

}
