package com.project.bookstudy.studygroup.dto;

import com.project.bookstudy.studygroup.domain.EnrollmentStatus;
import com.project.bookstudy.studygroup.domain.Enrollment;
import com.project.bookstudy.studygroup.domain.Payment;
import com.project.bookstudy.studygroup.domain.StudyGroup;
import lombok.Builder;
import lombok.Getter;

@Getter
public class EnrollmentDto {

    private Long id;
    private EnrollmentStatus status;
    private StudyGroupDto studyGroup;
    private PaymentDto payment;

    @Builder
    private EnrollmentDto(Long id, EnrollmentStatus status, StudyGroupDto studyGroup, PaymentDto payment) {
        this.id = id;
        this.status = status;
        this.studyGroup = studyGroup;
        this.payment = payment;
    }

    public static EnrollmentDto fromEntity(Enrollment enrollment) {
        StudyGroup studyGroup = enrollment.getStudyGroup();
        Payment payment = enrollment.getPayment();

        return EnrollmentDto.builder()
                .id(enrollment.getId())
                .status(enrollment.getEnrollmentStatus())
                .studyGroup(StudyGroupDto.fromEntity(studyGroup, studyGroup.getLeader()))
                .payment(PaymentDto.fromEntity(payment))
                .build();
    }
}
