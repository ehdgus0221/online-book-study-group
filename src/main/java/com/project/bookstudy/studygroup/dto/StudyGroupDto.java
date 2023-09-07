package com.project.bookstudy.studygroup.dto;

import com.project.bookstudy.member.domain.Member;
import com.project.bookstudy.studygroup.domain.StudyGroup;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class StudyGroupDto {

    private Long id;
    private String subject;
    private String contents;
    private String contentsDetail;
    private int maxSize;
    private int price;
    private LocalDateTime studyStartDt;
    private LocalDateTime studyEndDt;
    private LocalDateTime recruitmentStartDt;
    private LocalDateTime recruitmentEndDt;
    private Long leaderId;
    private String leaderName;


    @Builder
    public StudyGroupDto(Long id, String subject, String contents, String contentsDetail, int maxSize, int price, LocalDateTime studyStartDt, LocalDateTime studyEndDt,
                         LocalDateTime recruitmentStartDt, LocalDateTime recruitmentEndDt, Long leaderId, String leaderName) {

        this.id = id;
        this.subject = subject;
        this.contents = contents;
        this.contentsDetail = contentsDetail;
        this.studyStartDt = studyStartDt;
        this.studyEndDt = studyEndDt;
        this.recruitmentStartDt = recruitmentStartDt;
        this.recruitmentEndDt = recruitmentEndDt;
        this.maxSize = maxSize;
        this.price = price;
        this.leaderId = leaderId;
        this.leaderName = leaderName;
    }

    public static StudyGroupDto fromEntity(StudyGroup studyGroup, Member leader) {

        return StudyGroupDto.builder()
                .id(studyGroup.getId())
                .subject(studyGroup.getSubject())
                .contents(studyGroup.getContents())
                .contentsDetail(studyGroup.getContentsDetail())
                .studyStartDt(studyGroup.getStudyStartDt())
                .studyEndDt(studyGroup.getStudyEndDt())
                .recruitmentStartDt(studyGroup.getRecruitmentStartDt())
                .recruitmentEndDt(studyGroup.getRecruitmentEndDt())
                .maxSize(studyGroup.getMaxSize())
                .price(studyGroup.getPrice())
                .leaderId(leader.getId())
                .leaderName(leader.getName())
                .build();
    }

}
