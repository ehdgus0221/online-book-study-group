package com.project.bookstudy.studygroup.domain;

import com.project.bookstudy.member.domain.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "enrollment_id")
    private Long id;
    private EnrollmentStatus enrollmentStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    private StudyGroup studyGroupId;

    @OneToOne(fetch = FetchType.LAZY)
    private Payment paymentId;


    public Enrollment(EnrollmentStatus enrollmentStatus, Member memberId, StudyGroup studyGroupId, Payment paymentId) {
        this.enrollmentStatus = enrollmentStatus;
        this.memberId = memberId;
        this.studyGroupId = studyGroupId;
        this.paymentId = paymentId;
    }
}
