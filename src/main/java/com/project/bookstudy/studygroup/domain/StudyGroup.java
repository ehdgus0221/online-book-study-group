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
    private LocalDateTime studyStartDt;
    private LocalDateTime studyEndDt;
    private LocalDateTime recruitmentStartDt;
    private LocalDateTime recruitmentEndDt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member leader;


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

}
