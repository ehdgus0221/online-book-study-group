package com.project.bookstudy.studygroup.service.impl;

import com.project.bookstudy.common.aop.DistributedLock;
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
    @DistributedLock(key = "#request.studyGroupId")
    public Long enroll(CreateEnrollmentRequest request) {
        //Collection Fetch Join → Batch Size 적용 고려
        StudyGroup studyGroup = studyGroupRepository.findByIdWithEnrollments(request.getStudyGroupId())
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.STUDY_GROUP_NOT_FOUND.getDescription()));

        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.USER_NOT_FOUND.getDescription()));

        // 모집 기간 조회
        if (!studyGroup.isRecruitmentStarted()) {
            throw new IllegalStateException(ErrorCode.RECRUITMENT_DATE_END.getDescription());
        }

        // 중복 신청 검사
        validate(member, studyGroup);

        // 현재 신청 인원 체크
        if (!studyGroup.isApplicable()) {
            throw new IllegalStateException(ErrorCode.STUDY_GROUP_FULL.getDescription());
        }

        Enrollment enrollment = Enrollment.createEnrollment(member, studyGroup);
        enrollmentRepository.save(enrollment);

        return enrollment.getId();
    }

    // 중복 검사
    public void validate(Member member, StudyGroup studyGroup) {
        if (studyGroup.getLeader().getId() == member.getId()) {
            throw new IllegalStateException(ErrorCode.LEADER_ENROLLMENT_ERROR.getDescription());
        }

        validateDuplicateApplication(member, studyGroup);
    }

    private void validateDuplicateApplication(Member member, StudyGroup studyGroup) {
        for (Enrollment enrollment : studyGroup.getEnrollments()) {
            if (enrollment.getMember() == member) {
                throw new IllegalStateException(ErrorCode.DUPLICATE_ENROLLMENT_ERROR.getDescription());
            }
        }
    }



    @Override
    @Transactional
    public void cancel(Long enrollmentId) {

        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.ENROLLMENT_NOT_FOUND.getDescription()));

        if (enrollment.getStudyGroup().isStarted()) {
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
        Page<EnrollmentDto> enrollmentDtoList = enrollments
                .map(content -> EnrollmentDto.fromEntity(content));
        return enrollmentDtoList;
    }

}
