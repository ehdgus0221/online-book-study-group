package com.project.bookstudy.studygroup.domain;

import com.project.bookstudy.common.dto.ErrorCode;
import com.project.bookstudy.member.domain.Member;
import com.project.bookstudy.studygroup.domain.param.CreateStudyGroupParam;
import com.project.bookstudy.studygroup.domain.param.UpdateStudyGroupParam;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"enrollments"})
public class StudyGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "study_group_id")
    private Long id;
    private String subject;
    private String contents;
    @Lob
    private String contentsDetail;
    private int maxSize;
    private int price;

    @Enumerated(EnumType.STRING)
    private StudyGroupStatus status;
    private LocalDateTime studyStartDt;
    private LocalDateTime studyEndDt;
    private LocalDateTime recruitmentStartDt;
    private LocalDateTime recruitmentEndDt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leader_id")
    private Member leader;

    //동시성 이슈 발생 가능 → AWS 적용 이후 Redis로 해결할 예정
    @OneToMany(mappedBy = "studyGroup", cascade = CascadeType.ALL)
    private List<Enrollment> enrollments = new ArrayList<>();


    @Builder
    public StudyGroup(String subject, String contents, String contentsDetail, int maxSize, int price, LocalDateTime studyStartDt, LocalDateTime studyEndDt
            ,LocalDateTime recruitmentStartDt, LocalDateTime recruitmentEndDt, Member leader) {

        this.subject = subject;
        this.contents = contents;
        this.contentsDetail = contentsDetail;
        this.maxSize = maxSize;
        this.price = price;
        this.studyStartDt = studyStartDt;
        this.studyEndDt = studyEndDt;
        this.recruitmentStartDt = recruitmentStartDt;
        this.recruitmentEndDt = recruitmentEndDt;
        this.leader = leader;
        this.status = StudyGroupStatus.RECRUIT_WAIT;
    }

    public static StudyGroup from(Member leader, CreateStudyGroupParam studyGroupParam) {

        return StudyGroup.builder()
                .leader(leader)
                .studyStartDt(studyGroupParam.getStudyStartDt())
                .studyEndDt(studyGroupParam.getStudyEndDt())
                .recruitmentStartDt(studyGroupParam.getRecruitmentStartDt())
                .recruitmentEndDt(studyGroupParam.getRecruitmentEndDt())
                .subject(studyGroupParam.getSubject())
                .contents(studyGroupParam.getContents())
                .contentsDetail(studyGroupParam.getContentsDetail())
                .price(studyGroupParam.getPrice())
                .maxSize(studyGroupParam.getMaxSize())
                .build();
    }

    public void update(UpdateStudyGroupParam param) {

        this.subject = param.getSubject();
        this.contents = param.getContents();
        this.contentsDetail = param.getContentsDetail();
        this.maxSize = param.getMaxSize();
        this.price = param.getPrice();
        this.studyStartDt = param.getStudyStartDt();
        this.studyEndDt = param.getStudyEndDt();
        this.recruitmentStartDt = param.getRecruitmentStartDt();
        this.recruitmentEndDt = param.getRecruitmentEndDt();
    }

    public boolean isApplicable() {

        long count = enrollments.stream()
                .filter((i) -> i.getEnrollmentStatus() == EnrollmentStatus.RESERVED)
                .count();

        if ( count < maxSize) {
            return true;
        }
        return false;
    }


    public boolean isStarted() {
        if (LocalDateTime.now().isAfter(studyStartDt)) {
            return true;
        }
        return false;
    }

    public void cancel() {

        if (isStarted()) {
            throw new IllegalStateException(ErrorCode.STUDY_GROUP_CANCEL_FAIL.getDescription());
        }

        enrollments.stream()
                .forEach(e -> e.cancel());
        status = StudyGroupStatus.RECRUIT_CANCEL;
    }

    LocalDate nowDate = LocalDate.now();

    // 스터디 종료
    public boolean isStudyEnded() {
        if (getStudyEndDt().toLocalDate().isBefore(nowDate)) {
            return true;
        } return false;
    }

    // 모집 대기
    public boolean isRecruitmentWaited() {
        if (getRecruitmentStartDt().toLocalDate().isAfter(nowDate)) {
            return true;
        } return false;
    }

    // 모집 진행
    public boolean isRecruitmentStarted() {
        if (getRecruitmentStartDt().toLocalDate().minusDays(1).isBefore(nowDate)
                && nowDate.isBefore(getRecruitmentEndDt().toLocalDate().plusDays(1))) {
            return true;
        } return false;
    }

    // 모집 마감
    public boolean isRecruitmentEnded() {
        if (getRecruitmentEndDt().toLocalDate().isBefore(nowDate)
                && nowDate.isBefore(getStudyStartDt().toLocalDate())) {
            return true;
        } return false;
    }

    // 스터디 진행
    public boolean isStudyStarted() {
        if (getStudyStartDt().toLocalDate().minusDays(1).isBefore(nowDate)
                && nowDate.isBefore(getStudyEndDt().toLocalDate().plusDays(1))) {
            return true;
        } return false;
    }

    public void recruitWait() {
        status = StudyGroupStatus.RECRUIT_WAIT;
    }
    public void recruitIng() {
        status = StudyGroupStatus.RECRUIT_ING;
    }
    public void recruitmentEnd() {
        status = StudyGroupStatus.RECRUITMENT_END;
    }
    public void studyIng() {
        status = StudyGroupStatus.STUDY_ING;
    }
    public void studyEnd() {
        status = StudyGroupStatus.STUDY_END;
    }


}
