package com.project.bookstudy.studygroup.dto;

import com.project.bookstudy.member.domain.Member;
import com.project.bookstudy.member.dto.MemberDto;
import com.project.bookstudy.studygroup.domain.StudyGroup;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    private MemberDto memberDto;

    public static StudyGroupDto fromEntity(StudyGroup studyGroup, Member member) {
        return StudyGroupDto.builder()
                .id(studyGroup.getId())
                .subject(studyGroup.getSubject())
                .contents(studyGroup.getContents())
                .contentsDetail(studyGroup.getContentsDetail())
                .studyStartDt(studyGroup.getStudyStartDt())
                .studyEndDt(studyGroup.getStudyEndDt())
                .maxSize(studyGroup.getMaxSize())
                .price(studyGroup.getPrice())
                .recruitmentStartDt(studyGroup.getRecruitmentStartDt())
                .recruitmentEndDt(studyGroup.getRecruitmentEndDt())
                .memberDto(MemberDto.fromEntity(member))
                .build();
    }

}
